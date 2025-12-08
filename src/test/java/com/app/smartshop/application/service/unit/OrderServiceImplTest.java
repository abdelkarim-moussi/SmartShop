package com.app.smartshop.application.service.unit;

import com.app.smartshop.application.dto.order.OrderItemRequestDTO;
import com.app.smartshop.application.dto.order.OrderRequestDTO;
import com.app.smartshop.application.dto.order.OrderResponseDTO;
import com.app.smartshop.application.exception.BusinessRuleException;
import com.app.smartshop.application.exception.DataNotExistException;
import com.app.smartshop.application.exception.InvalidParameterException;
import com.app.smartshop.application.mapper.OrderMapper;
import com.app.smartshop.application.service.ILoyaltyService;
import com.app.smartshop.application.service.OrderServiceImpl;
import com.app.smartshop.domain.entity.*;
import com.app.smartshop.domain.enums.LoyaltyLevel;
import com.app.smartshop.domain.enums.OrderStatus;
import com.app.smartshop.domain.repository.JpaClientRepository;
import com.app.smartshop.domain.repository.JpaOrderRepository;
import com.app.smartshop.domain.repository.JpaProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private JpaClientRepository clientRepository;
    @Mock
    private JpaProductRepository productRepository;
    @Mock
    private JpaOrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private ILoyaltyService loyaltyService;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Client mockClient;
    private Product mockProduct;
    private OrderRequestDTO validOrderRequest;
    private OrderItemRequestDTO validItemRequest;
    final long PROMO_CODE_MAX_USAGE = 10;

    @BeforeEach
    void setUp() {
        mockClient = Client.builder()
                .id("C1")
                .loyaltyLevel(LoyaltyLevel.BASIC)
                .build();

        mockProduct = Product.builder()
                .id("P1")
                .unitPrice(BigDecimal.valueOf(100.00))
                .stock(10)
                .build();

        validItemRequest = OrderItemRequestDTO.builder()
                .productId("P1")
                .quantity(3)
                .build();

        validOrderRequest = OrderRequestDTO.builder()
                .clientId("C1")
                .itemsList(Collections.singletonList(validItemRequest))
                .promotionCode("PROMO-A1B2")
                .build();
    }

    // --- CREATE ORDER TESTS ---

    @Test
    void createOrder_ShouldCreateAndSaveOrder_WhenValid() {
        // Arrange
        when(clientRepository.findById("C1")).thenReturn(Optional.of(mockClient));
        when(productRepository.findById("P1")).thenReturn(Optional.of(mockProduct));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        when(orderMapper.toResponseDto(any(Order.class)))
                .thenReturn(OrderResponseDTO.builder().id("O1").build());

        // Act
        orderService.createOrder(validOrderRequest);

        // Assert
        verify(orderRepository).save(any(Order.class));
        verify(productRepository, times(2)).findById("P1");
    }

    @Test
    void createOrder_ShouldThrowBusinessRuleException_WhenInsufficientStock() {
        // Arrange
        validItemRequest.setQuantity(15);
        when(clientRepository.findById("C1")).thenReturn(Optional.of(mockClient));
        when(productRepository.findById("P1")).thenReturn(Optional.of(mockProduct));

        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        // Act & Assert
        BusinessRuleException exception = assertThrows(BusinessRuleException.class,
                () -> orderService.createOrder(validOrderRequest));

        assertEquals("order is rejected due to insuffisant stock", exception.getMessage());

        verify(orderRepository).save(argThat(order -> order.getStatus().equals(OrderStatus.REJECTED)));

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void createOrder_ShouldIgnorePromoCode_WhenUsageExceeded() {
        // Arrange
        validOrderRequest.setPromotionCode("PROMO-USED");
        when(clientRepository.findById("C1")).thenReturn(Optional.of(mockClient));
        when(productRepository.findById("P1")).thenReturn(Optional.of(mockProduct));
        when(orderRepository.countByPromotionCode("PROMO-USED")).thenReturn(PROMO_CODE_MAX_USAGE);
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));
        when(orderMapper.toResponseDto(any(Order.class))).thenReturn(OrderResponseDTO.builder().id("O1").build());

        // Act
        orderService.createOrder(validOrderRequest);

        // Assert
        verify(orderRepository).save(argThat(order -> order.getPromotionCode() == null));
    }

    @Test
    void createOrder_ShouldIgnorePromoCode_WhenPatternIsInvalid() {
        // Arrange
        validOrderRequest.setPromotionCode("INVALIDCODE");
        when(clientRepository.findById("C1")).thenReturn(Optional.of(mockClient));
        when(productRepository.findById("P1")).thenReturn(Optional.of(mockProduct));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));
        when(orderMapper.toResponseDto(any(Order.class))).thenReturn(OrderResponseDTO.builder().id("O1").build());

        // Act
        orderService.createOrder(validOrderRequest);

        // Assert
        verify(orderRepository).save(argThat(order -> order.getPromotionCode() == null));
    }

    @Test
    void createOrder_ShouldThrowDataNotExistException_WhenClientNotFound() {
        // Arrange
        when(clientRepository.findById("C1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotExistException.class,
                () -> orderService.createOrder(validOrderRequest),
                "there no client with this ID: C1");
    }

    @Test
    void createOrder_ShouldThrowDataNotExistException_WhenProductNotFound() {
        // Arrange
        when(clientRepository.findById("C1")).thenReturn(Optional.of(mockClient));
        when(productRepository.findById("P1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotExistException.class,
                () -> orderService.createOrder(validOrderRequest),
                "no product exist with this ID: P1");
    }

    @Test
    void createOrder_ShouldThrowInvalidParameterException_WhenRequestIsNull() {
        // Act & Assert
        assertThrows(InvalidParameterException.class,
                () -> orderService.createOrder(null),
                "order request can not be null");
    }

    // --- CONFIRM ORDER TESTS ---

    @Test
    void confirmOrder_ShouldSetStatusConfirmedAndAssignLoyaltyLevel_WhenFullyPaid() {
        // Arrange
        Order mockOrder = Order.builder()
                .id("O1")
                .client(mockClient)
                .status(OrderStatus.PENDING)
                .restAmount(BigDecimal.ZERO)
                .build();

        when(orderRepository.findById("O1")).thenReturn(Optional.of(mockOrder));
        when(orderMapper.toResponseDto(any(Order.class))).thenReturn(OrderResponseDTO.builder().build());

        // Act
        orderService.confirmOrder("O1");

        // Assert
        assertEquals(OrderStatus.CONFIRMED, mockOrder.getStatus());
        verify(loyaltyService).assignLoyaltyLevel(mockClient.getId());
        verify(orderMapper).toResponseDto(mockOrder);
    }

    @Test
    void confirmOrder_ShouldThrowException_WhenNotFullyPaid() {
        // Arrange
        Order mockOrder = Order.builder()
                .id("O1")
                .status(OrderStatus.PENDING)
                .restAmount(BigDecimal.valueOf(50.00))
                .build();

        when(orderRepository.findById("O1")).thenReturn(Optional.of(mockOrder));

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> orderService.confirmOrder("O1"),
                "order can not be confirm until is fully payed");

        assertEquals(OrderStatus.PENDING, mockOrder.getStatus());
        verify(loyaltyService, never()).assignLoyaltyLevel(anyString());
    }

    @Test
    void confirmOrder_ShouldThrowDataNotExistException_WhenOrderNotFound() {
        // Arrange
        when(orderRepository.findById("O_MISSING")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotExistException.class,
                () -> orderService.confirmOrder("O_MISSING"),
                "no order found with this id: O_MISSING");
    }

    @Test
    void confirmOrder_ShouldThrowInvalidParameterException_WhenIdIsEmpty() {
        // Act & Assert
        assertThrows(InvalidParameterException.class,
                () -> orderService.confirmOrder(""),
                "id is required");
    }

    @Test
    void confirmOrder_ShouldThrowInvalidParameterException_WhenIdIsNull() {
        // Act & Assert
        assertThrows(InvalidParameterException.class,
                () -> orderService.confirmOrder(null),
                "id is required");
    }

    // --- CANCEL ORDER TESTS ---

    @Test
    void cancelOrder_ShouldCancelAndReturnStock_WhenPending() {
        // Arrange
        Order mockOrder = Order.builder()
                .id("O1")
                .status(OrderStatus.PENDING)
                .itemsList(List.of(OrderItem.builder().product(mockProduct).quantity(3).build()))
                .build();

        when(orderRepository.findById("O1")).thenReturn(Optional.of(mockOrder));

        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);

        when(orderMapper.toResponseDto(mockOrder)).thenReturn(OrderResponseDTO.builder().build());

        // Act
        orderService.cancelOrder("O1");

        // Assert
        assertEquals(OrderStatus.CANCELED, mockOrder.getStatus());
        verify(orderRepository).save(mockOrder);
    }

    @Test
    void cancelOrder_ShouldThrowException_WhenAlreadyConfirmed() {
        // Arrange
        Order mockOrder = Order.builder().id("O1").status(OrderStatus.CONFIRMED).build();
        when(orderRepository.findById("O1")).thenReturn(Optional.of(mockOrder));

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> orderService.cancelOrder("O1"),
                "order can only be canceled if it still PENDING");

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void cancelOrder_ShouldThrowException_WhenAlreadyRejected() {
        // Arrange
        Order mockOrder = Order.builder().id("O1").status(OrderStatus.REJECTED).build();
        when(orderRepository.findById("O1")).thenReturn(Optional.of(mockOrder));

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> orderService.cancelOrder("O1"),
                "order can only be canceled if it still PENDING");

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void cancelOrder_ShouldThrowException_WhenAlreadyCanceled() {
        // Arrange
        Order mockOrder = Order.builder().id("O1").status(OrderStatus.CANCELED).build();
        when(orderRepository.findById("O1")).thenReturn(Optional.of(mockOrder));

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> orderService.cancelOrder("O1"),
                "order can only be canceled if it still PENDING");

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void cancelOrder_ShouldThrowDataNotExistException_WhenOrderNotFound() {
        // Arrange
        when(orderRepository.findById("O_MISSING")).thenReturn(Optional.empty());
        // Act & Assert
        assertThrows(DataNotExistException.class,
                () -> orderService.cancelOrder("O_MISSING"),
                "no order found with this id: O_MISSING");
    }

    @Test
    void cancelOrder_ShouldThrowInvalidParameterException_WhenIdIsNull() {
        // Act & Assert
        assertThrows(InvalidParameterException.class,
                () -> orderService.cancelOrder(null),
                "id is required");
    }
}
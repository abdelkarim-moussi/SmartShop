package com.app.smartshop.application.service;

import com.app.smartshop.application.dto.*;
import com.app.smartshop.application.exception.BusinessRuleException;
import com.app.smartshop.application.exception.DataNotExistException;
import com.app.smartshop.application.exception.InvalidParameterException;
import com.app.smartshop.application.mapper.OrderModelDTOMapper;
import com.app.smartshop.domain.entity.*;
import com.app.smartshop.domain.enums.OrderStatus;
import com.app.smartshop.domain.repository.JpaClientRepository;
import com.app.smartshop.domain.repository.JpaOrderRepository;
import com.app.smartshop.domain.repository.JpaProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    final String PROMO_CODE_PATTERN = "PROMO-[A-Z0-9]{4}";
    final long PROMO_CODE_MAX_USAGE = 10;

    private final JpaClientRepository clientRepository;
    private final JpaProductRepository productRepository;
    private final JpaOrderRepository orderRepository;
    private final OrderModelDTOMapper orderMapper;
    private final ILoyaltyService loyaltyService;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO order) {
        if(order == null) throw new InvalidParameterException("order request can not be null");

        Client client = clientRepository.findById(order.getClientId()).orElseThrow(
                () -> new DataNotExistException("there no client with this ID: "+order.getClientId())
        );

        Order newOrder = Order.builder()
                .date(LocalDateTime.now())
                .client(client)
                .status(OrderStatus.PENDING)
                .build();

        if(validatePromoCode(order.getPromotionCode())){
            newOrder.setPromotionCode(order.getPromotionCode());
        }else {
            newOrder.setPromotionCode(null);
        }

        List<OrderItem> items = mapAndProcessOrderItems(order.getItemsList(),newOrder);
        newOrder.setItemsList(items);
        newOrder.calculateAmounts();

        if(newOrder.getStatus().equals(OrderStatus.REJECTED)){
            orderRepository.save(newOrder);
            throw new BusinessRuleException("order is rejected due to insuffisant stock");
        }

        updateProductStock(newOrder);
        Order savedOrder = orderRepository.save(newOrder);
        loyaltyService.assignLoyaltyLevel(savedOrder.getClient().getId());

        return orderMapper.toResponseDto(savedOrder);
    }

    private List<OrderItem> mapAndProcessOrderItems(List<OrderItemRequestDTO> items, Order order){
        return items.stream().map(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(()-> new DataNotExistException("no product exist with this ID: "+item.getProductId()));

                    OrderItem orderItem = OrderItem.builder()
                            .product(product)
                            .unitPrice(product.getUnitPrice())
                            .quantity(item.getQuantity())
                            .build();
                    orderItem.calculateTotalLine();
                    order.addItem(orderItem);

                    if(!isStockValid(product.getId(), item.getQuantity())){
                        order.setStatus(OrderStatus.REJECTED);
                    }
                    return orderItem;

                }).toList();
    }

    private boolean isStockValid(String productId,int demandedQuantity){
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new DataNotExistException("no product exist with this ID: "+productId));

        return product.getStock() >= demandedQuantity;
    }

    private void updateProductStock(Order order){
        for (OrderItem item : order.getItemsList()){
            Product product = item.getProduct();

            int quantity = item.getQuantity();

            if(order.getStatus().equals(OrderStatus.REJECTED) || order.getStatus().equals(OrderStatus.CANCELED)){
                product.incrementStock(quantity);
            }else {
                product.decrementStock(quantity);
            }
        }
    }

    @Override
    public OrderResponseDTO confirmOrder(String orderId){
        if(orderId == null || orderId.isEmpty()){
            throw new InvalidParameterException("id is required");
        }

        Order order = orderRepository.findById(orderId).orElseThrow(
                ()-> new DataNotExistException("no order found with this id: "+orderId)
        );

        if(order.getRestAmount().compareTo(BigDecimal.ZERO) > 0){
            throw new IllegalArgumentException("order can not be confirm until is fully payed");
        }

        order.setStatus(OrderStatus.CONFIRMED);

        return orderMapper.toResponseDto(order);
    }

    @Override
    public OrderResponseDTO cancelOrder(String orderId) {
        if(orderId == null || orderId.isEmpty()){
            throw new InvalidParameterException("id is required");
        }

        Order order = orderRepository.findById(orderId).orElseThrow(
                ()-> new DataNotExistException("no order found with this id: "+orderId)
        );

        if(order.getStatus().equals(OrderStatus.REJECTED) || order.getStatus().equals(OrderStatus.CANCELED) || order.getStatus().equals(OrderStatus.CONFIRMED)){
            throw new IllegalArgumentException("order can only be canceled if it still PENDING");
        }

        order.setStatus(OrderStatus.CANCELED);
        updateProductStock(order);
        return orderMapper.toResponseDto(orderRepository.save(order));
    }


    private boolean validatePromoCode(String promoCode){
        if(promoCode != null && !promoCode.isEmpty()){
            if (!promoCode.matches(PROMO_CODE_PATTERN)){
               return false;
            }
        }

        long currentUsage = orderRepository.countByPromotionCode(promoCode);

        if(currentUsage >= PROMO_CODE_MAX_USAGE){
            return false;
        }

        return true;

    }
}

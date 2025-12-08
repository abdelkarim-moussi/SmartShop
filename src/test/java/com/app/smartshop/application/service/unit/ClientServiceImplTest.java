package com.app.smartshop.application.service.unit;

import com.app.smartshop.application.dto.DomainPageRequest;
import com.app.smartshop.application.dto.client.ClientOrdersResponse;
import com.app.smartshop.application.dto.client.ClientRequestDTO;
import com.app.smartshop.application.dto.client.ClientResponseDTO;
import com.app.smartshop.application.dto.client.ClientStatistiques;
import com.app.smartshop.application.exception.DataNotExistException;
import com.app.smartshop.application.exception.EmailAleadyUsedException;
import com.app.smartshop.application.exception.InvalidParameterException;
import com.app.smartshop.application.mapper.ClientMapper;
import com.app.smartshop.application.mapper.ClientOrderMapper;
import com.app.smartshop.application.service.ClientServiceImpl;
import com.app.smartshop.domain.entity.Client;
import com.app.smartshop.domain.entity.Order;
import com.app.smartshop.domain.entity.search.ClientCriteria;
import com.app.smartshop.domain.enums.LoyaltyLevel;
import com.app.smartshop.domain.enums.OrderStatus;
import com.app.smartshop.domain.repository.JpaClientRepository;
import com.app.smartshop.domain.repository.JpaOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private JpaClientRepository clientRepository;
    @Mock
    private ClientMapper clientMapper;
    @Mock
    private JpaOrderRepository orderRepository;
    @Mock
    private ClientOrderMapper clientOrderMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client mockClient;
    private ClientRequestDTO mockRequestDTO;
    private ClientResponseDTO mockResponseDTO;
    private final String CLIENT_ID = "C001";

    @BeforeEach
    void setUp() {
        mockClient = Client.builder()
                .id(CLIENT_ID)
                .email("test@example.com")
                .name("Alice Smith")
                .loyaltyLevel(LoyaltyLevel.BASIC)
                .build();

        mockRequestDTO = ClientRequestDTO.builder()
                .email("test@example.com")
                .name("Alice Smith")
                .build();

        mockResponseDTO = ClientResponseDTO.builder()
                .id(CLIENT_ID)
                .email("test@example.com")
                .name("Alice Smith")
                .loyaltyLevel(LoyaltyLevel.BASIC)
                .build();
    }

    @Test
    void createClient_ShouldSaveNewClientWithBasicLevel_WhenEmailIsUnique() {
        // Arrange
        when(clientRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(clientMapper.toEntity(mockRequestDTO)).thenReturn(mockClient);
        when(clientRepository.save(any(Client.class))).thenReturn(mockClient);
        when(clientMapper.toResponseDTO(mockClient)).thenReturn(mockResponseDTO);

        // Act
        ClientResponseDTO result = clientService.createClient(mockRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(CLIENT_ID, result.getId());
        verify(clientRepository).save(argThat(client -> client.getLoyaltyLevel() == LoyaltyLevel.BASIC));
        verify(clientRepository, times(1)).existsByEmail("test@example.com");
    }

    @Test
    void createClient_ShouldThrowEmailUsedException_WhenEmailExists() {
        // Arrange
        when(clientRepository.existsByEmail("test@example.com")).thenReturn(true);

        // Act & Assert
        assertThrows(EmailAleadyUsedException.class,
                () -> clientService.createClient(mockRequestDTO),
                "Should throw exception if email is already in use.");

        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void createClient_ShouldThrowInvalidParameterException_WhenRequestIsNull() {
        // Act & Assert
        assertThrows(InvalidParameterException.class,
                () -> clientService.createClient(null),
                "client data can not be null");
    }

    @Test
    void updateClient_ShouldUpdateClientDetails_WhenIdExistsAndEmailIsUnique() {
        // Arrange
        ClientRequestDTO updateRequest = ClientRequestDTO.builder().name("Alice Updated").email("new@email.com").build();
        Client existingClient = Client.builder().id(CLIENT_ID).email("old@email.com").name("Old Name").build();

        when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(existingClient));
        when(clientRepository.existsByEmail("new@email.com")).thenReturn(false);
        when(clientRepository.save(any(Client.class))).thenReturn(existingClient);
        when(clientMapper.toResponseDTO(existingClient)).thenReturn(mockResponseDTO);

        // Act
        clientService.updateClient(CLIENT_ID, updateRequest);

        // Assert
        assertEquals("new@email.com", existingClient.getEmail());
        assertEquals("Alice Updated", existingClient.getName());
        verify(clientRepository, times(1)).save(existingClient);
    }

    @Test
    void updateClient_ShouldThrowDataNotExistException_WhenIdNotFound() {
        // Arrange
        when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotExistException.class,
                () -> clientService.updateClient(CLIENT_ID, mockRequestDTO),
                "Should throw exception if client ID is not found.");
    }

    @Test
    void updateClient_ShouldThrowEmailUsedException_WhenNewEmailIsUsedByAnother() {
        // Arrange
        Client existingClient = Client.builder().id(CLIENT_ID).email("old@email.com").build();
        ClientRequestDTO updateRequest = ClientRequestDTO.builder().email("used@email.com").build();

        when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(existingClient));
        when(clientRepository.existsByEmail("used@email.com")).thenReturn(true);

        // Act & Assert
        assertThrows(EmailAleadyUsedException.class,
                () -> clientService.updateClient(CLIENT_ID, updateRequest),
                "Should throw exception if the new email is already in use.");

        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void updateClient_ShouldThrowInvalidParameterException_WhenIdIsNull() {
        // Act & Assert
        assertThrows(InvalidParameterException.class,
                () -> clientService.updateClient(null, mockRequestDTO),
                "data can not be null");
    }

    @Test
    void findClientById_ShouldReturnClient_WhenIdExists() {
        // Arrange
        when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(mockClient));
        when(clientMapper.toResponseDTO(mockClient)).thenReturn(mockResponseDTO);

        // Act
        ClientResponseDTO result = clientService.findClientById(CLIENT_ID);

        // Assert
        assertNotNull(result);
        assertEquals(CLIENT_ID, result.getId());
        verify(clientRepository, times(1)).findById(CLIENT_ID);
    }

    @Test
    void findClientById_ShouldThrowDataNotExistException_WhenIdNotFound() {
        // Arrange
        when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotExistException.class,
                () -> clientService.findClientById(CLIENT_ID),
                "there is no client with this id");
    }

    @Test
    void findClientById_ShouldThrowInvalidParameterException_WhenIdIsEmpty() {
        // Act & Assert
        assertThrows(InvalidParameterException.class,
                () -> clientService.findClientById(""),
                "id can not be null or empty");
    }

    @Test
    void deleteClientById_ShouldCallDelete_WhenIdExists() {
        // Arrange
        when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(mockClient));

        // Act
        clientService.deleteClientById(CLIENT_ID);

        // Assert
        verify(clientRepository, times(1)).deleteById(CLIENT_ID);
    }

    @Test
    void deleteClientById_ShouldThrowDataNotExistException_WhenIdNotFound() {
        // Arrange
        when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotExistException.class,
                () -> clientService.deleteClientById(CLIENT_ID),
                "there is no client with this id");

        verify(clientRepository, never()).deleteById(anyString());
    }

    @Test
    void deleteClientById_ShouldThrowInvalidParameterException_WhenIdIsNull() {
        // Act & Assert
        assertThrows(InvalidParameterException.class,
                () -> clientService.deleteClientById(null),
                "id can not be null or empty");
    }

    @Test
    void findAllClients_ShouldReturnPageOfDTOs_WhenCalledWithPaginationAndFilters() {
        // Arrange
        DomainPageRequest pageRequest = DomainPageRequest.builder()
                .page(0).size(10).sortBy("name").sortDir("desc").build();
        ClientCriteria filters = ClientCriteria.builder().search("Alice").build();

        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").descending());

        Page<Client> mockClientPage = new PageImpl<>(Collections.singletonList(mockClient), pageable, 1);
        when(clientRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(mockClientPage);

        when(clientMapper.toResponseDTO(mockClient)).thenReturn(mockResponseDTO);

        // Act
        Page<ClientResponseDTO> resultPage = clientService.findAllClients(pageRequest, filters);

        // Assert
        assertNotNull(resultPage);
        assertFalse(resultPage.isEmpty());
        assertEquals(1, resultPage.getTotalElements());

        // Verify repository interaction
        verify(clientRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findClientStatistiques_ShouldCalculateStatsCorrectly_WhenOrdersExist() {
        // Arrange
        LocalDateTime date1 = LocalDateTime.of(2025, Month.JANUARY, 1, 10, 0);
        LocalDateTime date2 = LocalDateTime.of(2025, Month.DECEMBER, 31, 20, 0);

        List<Order> clientOrders = List.of(
                Order.builder().date(date2).status(OrderStatus.PENDING).total(BigDecimal.valueOf(500)).build(),
                Order.builder().date(date1).status(OrderStatus.CONFIRMED).total(BigDecimal.valueOf(1000)).build(),
                Order.builder().date(date1.plusDays(1)).status(OrderStatus.CONFIRMED).total(BigDecimal.valueOf(500)).build()
        );

        when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(mockClient));
        when(orderRepository.findAllByClient(mockClient)).thenReturn(clientOrders);

        when(orderRepository.sumTotalConfirmedOrdersByClient(mockClient)).thenReturn(Optional.of(BigDecimal.valueOf(1500.00)));

        // Act
        ClientStatistiques stats = clientService.findClientStatistiques(CLIENT_ID);

        // Assert
        assertEquals(3, stats.getTotalNumberOfOrders());
        assertEquals(BigDecimal.valueOf(1500.00).setScale(2, RoundingMode.HALF_UP), stats.getConfirmedOrdersTotalAmounts());

        assertEquals("01-01-2025 | 10:00:00", stats.getFirstOrderDate());
        assertEquals("31-12-2025 | 20:00:00", stats.getLastOrderDate());
    }

    @Test
    void findClientStatistiques_ShouldReturnN_A_WhenNoOrdersExist() {
        // Arrange
        when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(mockClient));
        when(orderRepository.findAllByClient(mockClient)).thenReturn(Collections.emptyList());

        when(orderRepository.sumTotalConfirmedOrdersByClient(mockClient)).thenReturn(Optional.empty());

        // Act
        ClientStatistiques stats = clientService.findClientStatistiques(CLIENT_ID);

        // Assert
        assertEquals(0, stats.getTotalNumberOfOrders());
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), stats.getConfirmedOrdersTotalAmounts());
        assertEquals("N/A", stats.getFirstOrderDate());
        assertEquals("N/A", stats.getLastOrderDate());
    }

    @Test
    void findClientOrders_ShouldReturnListOfOrderDTOs() {
        // Arrange
        List<Order> clientOrders = Collections.singletonList(Order.builder().id("O1").build());
        ClientOrdersResponse mockOrderResponse = ClientOrdersResponse.builder().id("O1").build();

        when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(mockClient));
        when(orderRepository.findAllByClient(mockClient)).thenReturn(clientOrders);
        when(clientOrderMapper.toResponse(any(Order.class))).thenReturn(mockOrderResponse);

        // Act
        List<ClientOrdersResponse> result = clientService.findClientOrders(CLIENT_ID);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("O1", result.get(0).getId());
    }

    @Test
    void findClientOrders_ShouldThrowDataNotExistException_WhenClientNotFound() {
        // Arrange
        when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotExistException.class,
                () -> clientService.findClientOrders(CLIENT_ID),
                "no client exist with this id");
    }

}

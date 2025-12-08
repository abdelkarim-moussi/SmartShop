package com.app.smartshop.application.service;

import com.app.smartshop.application.dto.*;
import com.app.smartshop.application.dto.client.ClientOrdersResponse;
import com.app.smartshop.application.dto.client.ClientRequestDTO;
import com.app.smartshop.application.dto.client.ClientResponseDTO;
import com.app.smartshop.application.dto.client.ClientStatistiques;
import com.app.smartshop.application.mapper.ClientOrderMapper;
import com.app.smartshop.domain.entity.Order;
import com.app.smartshop.domain.entity.search.ClientCriteria;
import com.app.smartshop.application.exception.DataNotExistException;
import com.app.smartshop.application.exception.EmailAleadyUsedException;
import com.app.smartshop.application.exception.InvalidParameterException;
import com.app.smartshop.application.mapper.ClientMapper;
import com.app.smartshop.domain.enums.LoyaltyLevel;
import com.app.smartshop.domain.entity.Client;
import com.app.smartshop.domain.enums.OrderStatus;
import com.app.smartshop.domain.repository.JpaClientRepository;
import com.app.smartshop.domain.repository.JpaOrderRepository;
import com.app.smartshop.domain.repository.specification.ClientSpecification;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Format;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService{
    final DateTimeFormatter DATE_FORMATER = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss");

    private final JpaClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final JpaOrderRepository orderRepository;
    private final ClientOrderMapper clientOrderMapper;

    public ClientResponseDTO createClient(ClientRequestDTO clientRequestDTO){
        if(clientRequestDTO == null){
            throw new InvalidParameterException("client data can not be null");
        }

        boolean exist = clientRepository.existsByEmail(clientRequestDTO.getEmail());

        if(exist){
            throw new EmailAleadyUsedException("there is already a client with this email : "+clientRequestDTO.getEmail());
        }

        Client client = clientMapper.toEntity(clientRequestDTO);
        client.setLoyaltyLevel(LoyaltyLevel.BASIC);

        Client savedClient = clientRepository.save(client);

        return clientMapper.toResponseDTO(savedClient);
    }

    @Override
    public ClientResponseDTO updateClient(String id, ClientRequestDTO client) {
        if(id == null || id.trim().isEmpty() || client == null){
            throw new InvalidParameterException("data can not be null");
        }

        Client existClient = clientRepository.findById(id).orElseThrow(
                () -> new DataNotExistException("there is no client with this id : "+id)
        );

        boolean existByEmail = clientRepository.existsByEmail(client.getEmail());
        if(existByEmail){
            throw new EmailAleadyUsedException("there is already a client with this email: "+client.getEmail());
        }

        existClient.setEmail(client.getEmail());
        existClient.setName(client.getName());

        Client updatedClient = clientRepository.save(existClient);
        return clientMapper.toResponseDTO(updatedClient);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponseDTO findClientById(String id) {

        if(id == null || id.trim().isEmpty()){
            throw new InvalidParameterException("id can not be null or empty");
        }
        Client existClient = clientRepository.findById(id).orElseThrow(
                ()-> new DataNotExistException("there is no client with this id : "+id)
        );

        return clientMapper.toResponseDTO(existClient);
    }

    @Override
    public void deleteClientById(String id) {
        if(id == null || id.trim().isEmpty()){
            throw new InvalidParameterException("id can not be null or empty");
        }
        clientRepository.findById(id).orElseThrow(
                () -> new DataNotExistException("there is no client with this id: "+id)
        );
        clientRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientResponseDTO> findAllClients(DomainPageRequest domainPageRequest, ClientCriteria filters) {
        Pageable pageable = PageRequest.of(domainPageRequest.getPage(),domainPageRequest.getSize(), Sort.Direction.valueOf(domainPageRequest.getSortBy()));

        Specification<Client> specification = ClientSpecification.byFilters(filters);
        org.springframework.data.domain.Page<Client> clients = clientRepository.findAll(specification,pageable);
        return new Page<>(
                clients.getContent().stream().map(clientMapper::toResponseDTO).toList(),
                clients.getTotalElements(),
                clients.getTotalPages()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientOrdersResponse> findClientOrders(String clientId){
        if(clientId == null || clientId.isEmpty()){
            throw new InvalidParameterException("id can not be null or empty");
        }

        Client client = clientRepository.findById(clientId).orElseThrow(
                ()-> new DataNotExistException("no client exist with this id: "+clientId)
        );

        List<Order> clientOrders = orderRepository.findAllByClient(client);

        return clientOrders.stream().map(clientOrderMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClientStatistiques findClientStatistiques(String clientId){
        if(clientId == null || clientId.isEmpty()){
            throw new InvalidParameterException("id can not be null or empty");
        }

        Client client = clientRepository.findById(clientId).orElseThrow(
                ()-> new DataNotExistException("no client exist with this id: "+clientId)
        );

        List<Order> clientOrders = orderRepository.findAllByClient(client);

        BigDecimal confirmedOrdersTotalAmount = getConfirmedOrdersTotalAmount(client);
        String firstOrderDate = getFirstOrderDate(clientOrders);
        String lastOrderDate = getLastOrderDate(clientOrders);

        return ClientStatistiques.builder()
                .totalNumberOfOrders(clientOrders.size())
                .confirmedOrdersTotalAmounts(confirmedOrdersTotalAmount)
                .firstOrderDate(firstOrderDate)
                .lastOrderDate(lastOrderDate)
                .build();
    }

    private BigDecimal getConfirmedOrdersTotalAmount(Client client){
        BigDecimal total = orderRepository.sumTotalConfirmedOrdersByClient(client)
                .orElse(BigDecimal.ZERO);
        return total.setScale(2,RoundingMode.HALF_UP);
    }

    private String getFirstOrderDate(List<Order> orders){
        Optional<LocalDateTime> date = orders.stream()
                .map(Order::getDate)
                .min(Comparator.naturalOrder());

        return date.map(dateTime -> dateTime.format(DATE_FORMATER)).orElse("N/A");

    }

    private String getLastOrderDate(List<Order> orders){
        Optional<LocalDateTime> date = orders.stream()
                .map(Order::getDate)
                .max(Comparator.naturalOrder());
        return date.map(dateTime -> dateTime.format(DATE_FORMATER)).orElse("N/A");
    }
}

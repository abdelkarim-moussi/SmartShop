package com.app.smartshop.application.service;

import com.app.smartshop.application.dto.OrderRequestDTO;
import com.app.smartshop.application.dto.OrderResponseDTO;
import com.app.smartshop.application.exception.DataNotExistException;
import com.app.smartshop.application.exception.InvalidParameterException;
import com.app.smartshop.application.mapper.OrderModelDTOMapper;
import com.app.smartshop.domain.model.Client;
import com.app.smartshop.domain.model.Order;
import com.app.smartshop.domain.model.OrderItem;
import com.app.smartshop.domain.model.Product;
import com.app.smartshop.domain.model.search.OrderCriteria;
import com.app.smartshop.application.dto.DomainPageRequest;
import com.app.smartshop.application.dto.Page;
import com.app.smartshop.domain.repository.IClientRepository;
import com.app.smartshop.domain.repository.IOrderRepository;
import com.app.smartshop.domain.repository.IProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService{

    private final IClientRepository clientRepository;
    private final IProductRepository productRepository;
    private final IOrderRepository orderRepository;
    private final OrderModelDTOMapper mapper;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO order) {
        if(order == null) throw new InvalidParameterException("order request can not be null");

        Client client = clientRepository.findById(order.getClientId()).orElseThrow(
                () -> new DataNotExistException("there no client with this ID: "+order.getClientId())
        );

        List<OrderItem> items = order.getItemsList()
                .stream().map(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(()-> new DataNotExistException("no product exist with this ID: "+item.getProductId()));

                    return OrderItem.builder()
                            .product(product)
                            .unitPrice(product.getUnitPrice())
                            .quantity(item.getQuantity())
                            .build();
                }).toList();

        Order newOrder = Order.builder()
                .date(LocalDateTime.now())
                .client(client)
                .promotionCode(order.getPromotionCode())
                .itemsList(items)
                .build();

        Order savedOrder = orderRepository.save(newOrder);

        return mapper.toResponseDto(savedOrder);
    }

    @Override
    public OrderResponseDTO updateOrder(String id, OrderRequestDTO order) {
        return null;
    }

    @Override
    public OrderResponseDTO findOrderById(String id) {
        return null;
    }

    @Override
    public void deleteOrderById(String id) {

    }

    @Override
    public Page<OrderResponseDTO> findAllOrders(DomainPageRequest domainPageRequest, OrderCriteria filters) {
        return null;
    }
}

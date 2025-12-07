package com.app.smartshop.application.service;

import com.app.smartshop.application.dto.*;
import com.app.smartshop.application.exception.BusinessRuleException;
import com.app.smartshop.application.exception.DataNotExistException;
import com.app.smartshop.application.exception.InvalidParameterException;
import com.app.smartshop.application.mapper.OrderModelDTOMapper;
import com.app.smartshop.domain.enums.OrderStatus;
import com.app.smartshop.domain.entity.Client;
import com.app.smartshop.domain.entity.Order;
import com.app.smartshop.domain.entity.OrderItem;
import com.app.smartshop.domain.entity.Product;
import com.app.smartshop.domain.entity.search.OrderCriteria;
import com.app.smartshop.domain.repository.JpaClientRepository;
import com.app.smartshop.domain.repository.JpaOrderRepository;
import com.app.smartshop.domain.repository.JpaProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService{

    private final JpaClientRepository clientRepository;
    private final JpaProductRepository productRepository;
    private final JpaOrderRepository orderRepository;
    private final OrderModelDTOMapper mapper;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO order) {
        if(order == null) throw new InvalidParameterException("order request can not be null");

        Client client = clientRepository.findById(order.getClientId()).orElseThrow(
                () -> new DataNotExistException("there no client with this ID: "+order.getClientId())
        );

        Order newOrder = Order.builder()
                .date(LocalDateTime.now())
                .client(client)
                .promotionCode(order.getPromotionCode())
                .status(OrderStatus.PENDING)
                .build();

        List<OrderItem> items = mapAndProcessOrderItems(order.getItemsList(),newOrder);
        newOrder.setItemsList(items);
        newOrder.calculateAmounts();

        if(newOrder.getStatus().equals(OrderStatus.REJECTED)){
            orderRepository.save(newOrder);
            throw new BusinessRuleException("order is rejected due to insuffisant stock");
        }
        updateProductStock(newOrder);

        Order savedOrder = orderRepository.save(newOrder);

        return mapper.toResponseDto(savedOrder);
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

            product.decrementStock(quantity);
            productRepository.save(product);
        }
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

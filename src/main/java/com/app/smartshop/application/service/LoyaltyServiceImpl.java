package com.app.smartshop.application.service;

import com.app.smartshop.application.exception.DataNotExistException;
import com.app.smartshop.application.exception.InvalidParameterException;
import com.app.smartshop.domain.entity.Client;
import com.app.smartshop.domain.entity.Order;
import com.app.smartshop.domain.enums.LoyaltyLevel;
import com.app.smartshop.domain.repository.JpaClientRepository;
import com.app.smartshop.domain.repository.JpaOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LoyaltyServiceImpl implements ILoyaltyService {
    private final JpaClientRepository clientRepository;
    private final JpaOrderRepository orderRepository;

    @Override
    public void assignLoyaltyLevel(String clientId) {
        if(clientId == null || clientId.isEmpty()){
            throw new InvalidParameterException("client id is required");
        }

        Client client = clientRepository.findById(clientId).orElseThrow(
                ()-> new DataNotExistException("no client exist with this id: "+clientId )
        );

        List<Order> clientOrders = orderRepository.findAllByClient(client);
        BigDecimal ordersTotalPayed = clientOrders.stream()
                .map(Order::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if(clientOrders.size() >= 20 || ordersTotalPayed.compareTo(BigDecimal.valueOf(15000)) >= 0){
            client.setLoyaltyLevel(LoyaltyLevel.PLATINUM);
        }
        else if(clientOrders.size() >= 10 || ordersTotalPayed.compareTo(BigDecimal.valueOf(5000)) >= 0){
            client.setLoyaltyLevel(LoyaltyLevel.GOLD);
        }
        else if(clientOrders.size() >= 3 || ordersTotalPayed.compareTo(BigDecimal.valueOf(1000)) >= 0){
            client.setLoyaltyLevel(LoyaltyLevel.SILVER);
        }
        else {
            client.setLoyaltyLevel(LoyaltyLevel.BASIC);
        }

        clientRepository.save(client);

    }
}

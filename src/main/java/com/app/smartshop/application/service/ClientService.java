package com.app.smartshop.application.service;

import com.app.smartshop.application.dto.client.ClientRequestDTO;
import com.app.smartshop.application.dto.client.ClientResponseDTO;
import com.app.smartshop.application.mapper.ClientModelDTOMapper;
import com.app.smartshop.domain.enums.LoyaltyLevel;
import com.app.smartshop.domain.model.Client;
import com.app.smartshop.domain.repository.IClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {
    private final IClientRepository clientRepository;
    private final ClientModelDTOMapper clientModelDTOMapper;

    public ClientResponseDTO createClient(ClientRequestDTO clientRequestDTO){
        if(clientRequestDTO == null){
            throw new InvalidParameterException("client data can not be null");
        }

        Client client = clientModelDTOMapper.toDomainModel(clientRequestDTO);
        client.setLoyaltyLevel(LoyaltyLevel.BASIC);

        Client savedClient = clientRepository.save(client);

        return clientModelDTOMapper.toResponseDTO(savedClient);
    }
}

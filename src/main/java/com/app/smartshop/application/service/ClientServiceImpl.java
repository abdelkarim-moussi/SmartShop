package com.app.smartshop.application.service;

import com.app.smartshop.application.dto.client.ClientRequestDTO;
import com.app.smartshop.application.dto.client.ClientResponseDTO;
import com.app.smartshop.application.exception.DataNotExistException;
import com.app.smartshop.application.exception.EmailAleadyUsedException;
import com.app.smartshop.application.exception.InvalidParameterException;
import com.app.smartshop.application.mapper.ClientModelDTOMapper;
import com.app.smartshop.domain.enums.LoyaltyLevel;
import com.app.smartshop.domain.model.Client;
import com.app.smartshop.domain.repository.IClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService{
    private final IClientRepository clientRepository;
    private final ClientModelDTOMapper clientModelDTOMapper;

    public ClientResponseDTO createClient(ClientRequestDTO clientRequestDTO){
        if(clientRequestDTO == null){
            throw new InvalidParameterException("client data can not be null");
        }

        boolean exist = clientRepository.existByEmail(clientRequestDTO.getEmail());

        if(exist){
            throw new EmailAleadyUsedException("there is already a client with this email : "+clientRequestDTO.getEmail());
        }

        Client client = clientModelDTOMapper.toDomainModel(clientRequestDTO);
        client.setLoyaltyLevel(LoyaltyLevel.BASIC);

        Client savedClient = clientRepository.save(client);

        return clientModelDTOMapper.toResponseDTO(savedClient);
    }

    @Override
    public ClientResponseDTO findClientById(String id) {

        Client existClient = clientRepository.findById(id).orElseThrow(
                ()-> new DataNotExistException("there is no client with this id : "+id)
        );
        return null;
    }

    @Override
    public void deleteClient(ClientResponseDTO client) {

    }

    @Override
    public List<ClientResponseDTO> findAllClients() {
        return List.of();
    }
}

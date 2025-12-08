package com.app.smartshop.application.service;

import com.app.smartshop.application.dto.ClientRequestDTO;
import com.app.smartshop.application.dto.ClientResponseDTO;
import com.app.smartshop.domain.entity.Order;
import com.app.smartshop.domain.entity.search.ClientCriteria;
import com.app.smartshop.application.exception.DataNotExistException;
import com.app.smartshop.application.exception.EmailAleadyUsedException;
import com.app.smartshop.application.exception.InvalidParameterException;
import com.app.smartshop.application.mapper.ClientModelDTOMapper;
import com.app.smartshop.domain.enums.LoyaltyLevel;
import com.app.smartshop.domain.entity.Client;
import com.app.smartshop.application.dto.Page;
import com.app.smartshop.application.dto.DomainPageRequest;
import com.app.smartshop.domain.repository.JpaClientRepository;
import com.app.smartshop.domain.repository.JpaOrderRepository;
import com.app.smartshop.domain.repository.JpaPaymentRepository;
import com.app.smartshop.domain.repository.specification.ClientSpecification;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService{
    private final JpaClientRepository clientRepository;
    private final ClientModelDTOMapper clientModelDTOMapper;
    private final JpaOrderRepository orderRepository;
    private final JpaPaymentRepository paymentRepository;

    public ClientResponseDTO createClient(ClientRequestDTO clientRequestDTO){
        if(clientRequestDTO == null){
            throw new InvalidParameterException("client data can not be null");
        }

        boolean exist = clientRepository.existsByEmail(clientRequestDTO.getEmail());

        if(exist){
            throw new EmailAleadyUsedException("there is already a client with this email : "+clientRequestDTO.getEmail());
        }

        Client client = clientModelDTOMapper.toEntity(clientRequestDTO);
        client.setLoyaltyLevel(LoyaltyLevel.BASIC);

        Client savedClient = clientRepository.save(client);

        return clientModelDTOMapper.toResponseDTO(savedClient);
    }

    @Override
    public ClientResponseDTO updateClient(String id,ClientRequestDTO client) {
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
        return clientModelDTOMapper.toResponseDTO(updatedClient);
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

        return clientModelDTOMapper.toResponseDTO(existClient);
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
                clients.getContent().stream().map(clientModelDTOMapper::toResponseDTO).toList(),
                clients.getTotalElements(),
                clients.getTotalPages()
        );
    }

}

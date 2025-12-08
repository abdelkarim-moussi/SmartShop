package com.app.smartshop.application.service;

import com.app.smartshop.application.dto.*;
import com.app.smartshop.domain.entity.search.ClientCriteria;

import java.util.List;

public interface IClientService {
    ClientResponseDTO createClient(ClientRequestDTO client);
    ClientResponseDTO updateClient(String id,ClientRequestDTO client);
    ClientResponseDTO findClientById(String id);
    void deleteClientById(String id);
    Page<ClientResponseDTO> findAllClients(DomainPageRequest domainPageRequest, ClientCriteria clientCriteria);
    List<ClientOrdersResponse> findClientOrders(String clientId);
}

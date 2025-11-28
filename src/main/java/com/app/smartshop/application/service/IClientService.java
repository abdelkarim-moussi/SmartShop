package com.app.smartshop.application.service;

import com.app.smartshop.infrastructure.controller.dto.ClientRequestDTO;
import com.app.smartshop.infrastructure.controller.dto.ClientResponseDTO;
import com.app.smartshop.domain.model.search.ClientCriteria;
import com.app.smartshop.infrastructure.controller.dto.Page;
import com.app.smartshop.infrastructure.controller.dto.DomainPageRequest;

public interface IClientService {
    ClientResponseDTO createClient(ClientRequestDTO client);
    ClientResponseDTO updateClient(String id,ClientRequestDTO client);
    ClientResponseDTO findClientById(String id);
    void deleteClientById(String id);
    Page<ClientResponseDTO> findAllClients(DomainPageRequest domainPageRequest, ClientCriteria clientCriteria);
}

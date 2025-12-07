package com.app.smartshop.application.service;

import com.app.smartshop.application.dto.ClientRequestDTO;
import com.app.smartshop.application.dto.ClientResponseDTO;
import com.app.smartshop.domain.entity.search.ClientCriteria;
import com.app.smartshop.application.dto.Page;
import com.app.smartshop.application.dto.DomainPageRequest;

public interface IClientService {
    ClientResponseDTO createClient(ClientRequestDTO client);
    ClientResponseDTO updateClient(String id,ClientRequestDTO client);
    ClientResponseDTO findClientById(String id);
    void deleteClientById(String id);
    Page<ClientResponseDTO> findAllClients(DomainPageRequest domainPageRequest, ClientCriteria clientCriteria);
}

package com.app.smartshop.application.service;

import com.app.smartshop.application.dto.client.ClientRequestDTO;
import com.app.smartshop.application.dto.client.ClientResponseDTO;
import com.app.smartshop.application.dto.client.ClientFilters;
import com.app.smartshop.domain.repository.specification.Page;
import com.app.smartshop.domain.repository.specification.DomainPageRequest;

public interface IClientService {
    ClientResponseDTO createClient(ClientRequestDTO client);
    ClientResponseDTO updateClient(String id,ClientRequestDTO client);
    ClientResponseDTO findClientById(String id);
    void deleteClientById(String id);
    Page<ClientResponseDTO> findAllClients(DomainPageRequest domainPageRequest, ClientFilters clientFilters);
}

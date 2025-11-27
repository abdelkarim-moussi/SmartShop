package com.app.smartshop.application.service;

import com.app.smartshop.application.dto.client.ClientRequestDTO;
import com.app.smartshop.application.dto.client.ClientResponseDTO;

import java.util.List;

public interface IClientService {
    ClientResponseDTO createClient(ClientRequestDTO client);
    ClientResponseDTO findClientById(String id);
    void deleteClient(ClientResponseDTO client);
    List<ClientResponseDTO> findAllClients();
}

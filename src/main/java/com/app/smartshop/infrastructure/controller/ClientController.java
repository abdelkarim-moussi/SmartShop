package com.app.smartshop.infrastructure.controller;
import com.app.smartshop.application.dto.client.ClientRequestDTO;
import com.app.smartshop.application.dto.client.ClientResponseDTO;
import com.app.smartshop.application.service.ClientServiceImpl;
import com.app.smartshop.application.service.IClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final IClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody @Valid ClientRequestDTO clientRequestDTO){
        ClientResponseDTO clientResponseDTO = clientService.createClient(clientRequestDTO);
        return ResponseEntity.ok(clientResponseDTO);
    }

    @GetMapping
    public ResponseEntity<ClientResponseDTO> getClientById(@RequestParam(value = "id") String id){
        return ResponseEntity.
                ok(clientService.findClientById(id));
    }
}

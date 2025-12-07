package com.app.smartshop.infrastructure.controller;
import com.app.smartshop.application.dto.ClientRequestDTO;
import com.app.smartshop.application.dto.ClientResponseDTO;
import com.app.smartshop.domain.entity.search.ClientCriteria;
import com.app.smartshop.application.service.IClientService;
import com.app.smartshop.application.dto.DomainPageRequest;
import com.app.smartshop.application.dto.Page;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping(params = "id")
    public ResponseEntity<ClientResponseDTO> getClientById(@RequestParam(value = "id") String id){
        return ResponseEntity.
                ok(clientService.findClientById(id));
    }
    @GetMapping
    public ResponseEntity<Page<ClientResponseDTO>> getAllClient(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "DESC") String sortDir,
            @RequestParam(name = "loyaltyLevel", required = false) String loyaltyLevel,
            @RequestParam(name = "search", required = false) String search
    ){

        ClientCriteria clientCriteria = ClientCriteria.builder()
                .loyaltyLevel(loyaltyLevel)
                .search(search)
                .build();

        DomainPageRequest domainPageRequest = DomainPageRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDir(sortDir)
                .build();

        Page<ClientResponseDTO> responsePage = clientService.findAllClients(domainPageRequest, clientCriteria);

        return ResponseEntity.
                ok(responsePage);
    }

    @PutMapping("update")
    public ResponseEntity<ClientResponseDTO> updateClient(@RequestBody @Valid ClientRequestDTO clientRequest,@RequestParam(value = "id") String id){
        return ResponseEntity.ok(clientService.updateClient(id,clientRequest));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteClient(@RequestParam(value = "id") String id){
        clientService.deleteClientById(id);
        return ResponseEntity.ok("client with id:"+id+"deleted successfully");
    }

}

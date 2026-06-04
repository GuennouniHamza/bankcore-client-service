package ma.bankcore.client_service.service;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;

import ma.bankcore.client_service.dto.ClientRequest;
import ma.bankcore.client_service.dto.ClientResponse;

public interface ClientService {
	
	ClientResponse creerClient(ClientRequest request);
	
    ClientResponse getClientById(Long id);
    
    Page<ClientResponse> getAllClients(Pageable pageable);
    
    ClientResponse updateClient(Long id, ClientRequest request);

    void supprimerClient(Long id);

	
}

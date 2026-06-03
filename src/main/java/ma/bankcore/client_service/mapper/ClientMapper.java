package ma.bankcore.client_service.mapper;

import org.springframework.stereotype.Component;

import ma.bankcore.client_service.dto.ClientRequest;
import ma.bankcore.client_service.dto.ClientResponse;
import ma.bankcore.client_service.entity.Client;


@Component
public class ClientMapper {
	
	public Client toEntity(ClientRequest request) {
        return Client.builder()
            .nom(request.getNom())
            .prenom(request.getPrenom())
            .email(request.getEmail())
            .telephone(request.getTelephone())
            .build();
    }
	
	public ClientResponse toResponse(Client client) {
        return ClientResponse.builder()
            .id(client.getId())
            .nom(client.getNom())
            .prenom(client.getPrenom())
            .email(client.getEmail())
            .telephone(client.getTelephone())
            .statut(client.getStatut())
            .dateCreation(client.getDateCreation())
            .build();
    }
}

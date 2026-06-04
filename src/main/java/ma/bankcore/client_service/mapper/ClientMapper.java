package ma.bankcore.client_service.mapper;

import org.springframework.stereotype.Component;
import ma.bankcore.client_service.dto.ClientRequest;
import ma.bankcore.client_service.dto.ClientResponse;
import ma.bankcore.client_service.entity.Client;

@Component
public class ClientMapper {

    public Client toEntity(ClientRequest request) {
        Client client = new Client();
        client.setNom(request.getNom());
        client.setPrenom(request.getPrenom());
        client.setEmail(request.getEmail());
        client.setTelephone(request.getTelephone());
        return client;
    }

    public ClientResponse toResponse(Client client) {
        ClientResponse response = new ClientResponse();
        response.setId(client.getId());
        response.setNom(client.getNom());
        response.setPrenom(client.getPrenom());
        response.setEmail(client.getEmail());
        response.setTelephone(client.getTelephone());
        response.setStatut(client.getStatut());
        response.setDateCreation(client.getDateCreation());
        return response;
    }
}
package ma.bankcore.client_service.dto;

import lombok.Builder;
import lombok.Data;
import ma.bankcore.client_service.entity.StatutClient;

import java.time.LocalDateTime;

//Dès que tu as une classe avec des champs privés que tu veux lire ou modifier →
//mets @Data

@Data
@Builder
public class ClientResponse {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private StatutClient statut;
    private LocalDateTime dateCreation;
}
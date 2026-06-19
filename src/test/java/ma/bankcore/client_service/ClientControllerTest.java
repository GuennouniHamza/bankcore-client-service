package ma.bankcore.client_service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ma.bankcore.client_service.controller.ClientController;
import ma.bankcore.client_service.dto.ClientRequest;
import ma.bankcore.client_service.dto.ClientResponse;
import ma.bankcore.client_service.service.ClientService;

//Test intégration: controller + HTTP

@WebMvcTest(ClientController.class)
public class ClientControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	//MockMvc simule des requêtes HTTP sans démarrer un vrai serveur.
	//Spring l'injecte automatiquement.
	
	@Autowired
    private ObjectMapper objectMapper;	
	//transforme nos objets Java en JSON pour les envoyer dans les requêtes HTTP.
	
    @MockitoBean 
    private ClientService clientService;
    
    @Test
    @WithMockUser(roles ="ADMIN")
    
    void testCreerClient_Succes() throws Exception {
        // PRÉPARER
        ClientRequest request = new ClientRequest();
        request.setNom("Hamza");
        request.setPrenom("Guennouni");
        request.setEmail("hamza@bankcore.ma");

        ClientResponse response = new ClientResponse();
        response.setId(1L);
        response.setNom("Hamza");
        response.setEmail("hamza@bankcore.ma");
        
        when(clientService.creerClient(any(ClientRequest.class)))
        	.thenReturn(response);
        // VÉRIFIER
        mockMvc.perform(post("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.nom").value("Hamza"));
    
}
    }

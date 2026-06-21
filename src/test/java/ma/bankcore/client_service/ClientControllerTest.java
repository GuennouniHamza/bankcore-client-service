package ma.bankcore.client_service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ma.bankcore.client_service.config.SecurityConfig;
import ma.bankcore.client_service.controller.ClientController;
import ma.bankcore.client_service.dto.ClientRequest;
import ma.bankcore.client_service.dto.ClientResponse;
import ma.bankcore.client_service.exception.ClientNotFoundException;
import ma.bankcore.client_service.service.ClientService;

//Test intégration: controller + HTTP

@WebMvcTest(ClientController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")

//Démarre un contexte Spring partiel 
//— uniquement la couche Web du ClientController
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
                .contentType(MediaType.APPLICATION_JSON)//the request's body is JSON"
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.nom").value("Hamza"));
}
    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetClient_Succes() throws Exception {
        // PRÉPARER
        ClientResponse response = new ClientResponse();
        response.setId(1L);
        response.setNom("Hamza");

        // SIMULER
        when(clientService.getClientById(1L))
            .thenReturn(response);

        // VÉRIFIER
        mockMvc.perform(get("/api/v1/clients/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.nom").value("Hamza"));
    }
    
    @Test
    @WithMockUser(roles="ADMIN")
    void testGetClient_NotFound() throws Exception{
    	
    		when(clientService.getClientById(999L))
    			.thenThrow(new ClientNotFoundException(999L));
    			
    		mockMvc.perform(get("/api/v1/clients/999"))
    			.andExpect(status().isNotFound())
    			.andExpect(jsonPath("$.status").value(404));
    			
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreerClient_ValidationEchouee() throws Exception {
        // PRÉPARER — request invalide sans email
        ClientRequest request = new ClientRequest();
        request.setNom("Hamza");
        // pas d'email → @Email échoue

        // VÉRIFIER
        mockMvc.perform(post("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    void testGetClient_SansToken() throws Exception {
        // Pas de @WithMockUser → pas de token

        mockMvc.perform(get("/api/v1/clients/1"))
            .andExpect(status().isUnauthorized()); // 401
    }
    @Test
    @WithMockUser(roles = "CONSEILLER") // ← pas ADMIN
    void testCreerClient_RoleInsuffisant() throws Exception {
        ClientRequest request = new ClientRequest();
        request.setNom("Hamza");
        request.setPrenom("Test");
        request.setEmail("hamza@test.ma");

        mockMvc.perform(post("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isForbidden())
        		.andDo(print());// 403
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void testSupprimerClient_Succes() throws Exception {
        // SIMULER
        doNothing().when(clientService).supprimerClient(1L);

        // VÉRIFIER
        mockMvc.perform(delete("/api/v1/clients/1"))
            .andExpect(status().isNoContent()); // 204
    }
    
    }

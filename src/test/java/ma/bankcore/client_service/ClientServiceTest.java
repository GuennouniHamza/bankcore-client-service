package ma.bankcore.client_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ma.bankcore.client_service.dto.ClientRequest;
import ma.bankcore.client_service.dto.ClientResponse;
import ma.bankcore.client_service.entity.Client;
import ma.bankcore.client_service.entity.StatutClient;
import ma.bankcore.client_service.exception.ClientNotFoundException;
import ma.bankcore.client_service.exception.EmailDejaUtiliseException;
import ma.bankcore.client_service.mapper.ClientMapper;
import ma.bankcore.client_service.repository.ClientRepository;
import ma.bankcore.client_service.service.impl.ClientServiceImpl;
// méthode des 3A Arrange/ACT/Assert
//à garder : "Est-ce que le service appelle les bonnes méthodes dans le bon ordre 
//et retourne le bon résultat ?"

@ExtendWith(MockitoExtension.class)//permet d'utiliser @Mock et @InjectMocks
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;
    // → vrai service avec faux repository et faux mapper
    
    @Test//JUnit l'exécute automatiquement, c une meth de test 
    void testCreerClient_Success() {
        // GIVEN — préparer les données
        ClientRequest request = new ClientRequest();
        request.setNom("Hamza");
        request.setPrenom("Guennouni");
        request.setEmail("hamza@bankcore.ma");

        Client entity = new Client();
        entity.setId(1L);
        entity.setNom("Hamza");

        ClientResponse response = new ClientResponse();
        response.setId(1L);
        response.setNom("Hamza");

        // WHEN — définir le comportement des mocks
        when(clientRepository.existsByEmail("hamza@bankcore.ma"))
            .thenReturn(false);
        when(clientMapper.toEntity(request)).thenReturn(entity);
        when(clientRepository.save(entity)).thenReturn(entity);
        when(clientMapper.toResponse(entity)).thenReturn(response);

        // THEN — appeler la méthode et vérifier
        ClientResponse result = clientService.creerClient(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Hamza", result.getNom());
    }
    
    @Test
    void testCreerClient_EmailDejaUtilise() {
        // GIVEN
        ClientRequest request = new ClientRequest();
        request.setEmail("hamza@bankcore.ma");

        // WHEN
        when(clientRepository.existsByEmail("hamza@bankcore.ma"))
            .thenReturn(true); // ← email déjà utilisé !

        // THEN
        //vérifier exception levée
        assertThrows(EmailDejaUtiliseException.class,
            () -> clientService.creerClient(request));

        // Vérifier que save() n'a jamais été appelé
        verify(clientRepository, never()).save(any());
    }
    @Test
    void testGetClientById_NotFound() {
    	Long id =999L;
    	
    	when(clientRepository.findById(999L))
    		.thenReturn(Optional.empty());
    	
    	assertThrows(ClientNotFoundException.class,
    		()->clientService.getClientById(id));
    	
    }
    @Test
    void testGetClientById_Succes() {
        // PRÉPARER
        Client client = new Client();
        client.setId(1L);
        client.setNom("Hamza");

        ClientResponse response = new ClientResponse();
        response.setId(1L);
        response.setNom("Hamza");

        // SIMULER
        when(clientRepository.findById(1L))
            .thenReturn(Optional.of(client));
        when(clientMapper.toResponse(client))
            .thenReturn(response);

        // VÉRIFIER
        ClientResponse result = clientService.getClientById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Hamza", result.getNom());
    }
    @Test
    void testUpdateClient_Succes() {
    	//GIVEN				
    	ClientRequest request = new ClientRequest();
    	request.setPrenom("HAMZA UPDATES");
    	request.setNom("GUENNOUNI");
    	
    	Client client =new Client();
    	client.setId(1L);
    	client.setPrenom("HAMZA");
    	
    	ClientResponse response = new ClientResponse();
        response.setId(1L);
        response.setNom("Hamza Updated");
        
        //WHEN 
        when(clientRepository.findById(1L))
        	.thenReturn(Optional.of(client));
        when(clientMapper.toResponse(client))
        	.thenReturn(response);
        
        //THEN
        ClientResponse result = clientService.updateClient(1L, request);
        assertNotNull(result);
        assertEquals("HAMZA UPDATED",result.getNom());
        
    }
    @Test
    void testUpdateClient_ClientIntrouvable() {
        
        ClientRequest request = new ClientRequest();

        when(clientRepository.findById(999L))
            .thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class,
            () -> clientService.updateClient(999L, request));
    }
    @Test
    void testSupprimerClient_Succes() {
        // PRÉPARER
        Client client = new Client();
        client.setId(1L);
        client.setStatut(StatutClient.ACTIF);

        // SIMULER
        when(clientRepository.findById(1L))
            .thenReturn(Optional.of(client));

        // VÉRIFIER
        clientService.supprimerClient(1L);
        assertEquals(StatutClient.CLOTURE, client.getStatut());
        verify(clientRepository, times(1)).findById(1L);
        //vérifie que findById(1L) a été appelé exactement 1 fois pendant l'exécution du test.
    }
    @Test
    void testSupprimerClient_ClientIntrouvable() {
        // SIMULER
        when(clientRepository.findById(999L))
            .thenReturn(Optional.empty());

        // VÉRIFIER
        assertThrows(ClientNotFoundException.class,
            () -> clientService.supprimerClient(999L));
    }

}
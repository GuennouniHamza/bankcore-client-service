package ma.bankcore.client_service.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import ma.bankcore.client_service.dto.ClientRequest;
import ma.bankcore.client_service.dto.ClientResponse;
import ma.bankcore.client_service.service.ClientService;

@RestController
@RequestMapping("/api/v1/clients")
@Tag(name = "Clients", description = "Gestion des clients bancaires")

public class ClientController {
	
	private ClientService clientService;
	
	public ClientController(ClientService clientService) {
	    this.clientService = clientService;
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Créer un nouveau client")
	public ResponseEntity<ClientResponse> creerClient(
	        @Valid @RequestBody ClientRequest request) {
	    ClientResponse response = clientService.creerClient(request);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'CONSEILLER')")
	@Operation(summary = "Lister tous les clients")

	public ResponseEntity <Page<ClientResponse>> getAllClients(
		@PageableDefault(size = 20, sort = "nom") Pageable pageable){
		return ResponseEntity.ok(clientService.getAllClients(pageable));
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Modifier un client")
	public ResponseEntity <ClientResponse> updateClient(
			@PathVariable Long id,
			@Valid @RequestBody ClientRequest request){
		return ResponseEntity.ok(clientService.updateClient(id, request));
	}
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'CONSEILLER')")
	@Operation(summary = "Récupérer un client par ID")
	public ResponseEntity<ClientResponse> getClient(@PathVariable Long id) {
	    return ResponseEntity.ok(clientService.getClientById(id));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Clôturer un client")
	public ResponseEntity<Void> supprimerClient(@PathVariable Long id) {
	    clientService.supprimerClient(id);
	    return ResponseEntity.noContent().build();
	}
	
}

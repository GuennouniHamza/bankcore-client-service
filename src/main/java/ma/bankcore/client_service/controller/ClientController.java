package ma.bankcore.client_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ma.bankcore.client_service.dto.ClientRequest;
import ma.bankcore.client_service.dto.ClientResponse;
import ma.bankcore.client_service.service.ClientService;

@RestController
@RequestMapping("/api/v1/clients")

public class ClientController {
	
	private ClientService clientService;
	
	@PostMapping
	public ResponseEntity<ClientResponse> creerClient(
	        @Valid @RequestBody ClientRequest request) {
	    ClientResponse response = clientService.creerClient(request);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}

package ma.bankcore.client_service.service.impl;

import org.springframework.data.domain.Pageable;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import ma.bankcore.client_service.dto.ClientRequest;
import ma.bankcore.client_service.dto.ClientResponse;
import ma.bankcore.client_service.entity.Client;
import ma.bankcore.client_service.entity.StatutClient;
import ma.bankcore.client_service.exception.ClientNotFoundException;
import ma.bankcore.client_service.exception.EmailDejaUtiliseException;
import ma.bankcore.client_service.mapper.ClientMapper;
import ma.bankcore.client_service.repository.ClientRepository;
import ma.bankcore.client_service.service.ClientService;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
	
	private final ClientRepository clientRepository;
	private final ClientMapper clientMapper;
	
	@Override
	@Transactional
	public ClientResponse creerClient(ClientRequest request) {
		if(clientRepository.existsByEmail(request.getEmail())) {
			throw new EmailDejaUtiliseException(request.getEmail());
		}
		Client client = clientMapper.toEntity(request);
		Client saved =clientRepository.save(client);
		
		return clientMapper.toResponse(saved);
	}
	@Override
	@Transactional(readOnly = true)
	public ClientResponse getClientById(Long id) {
		Client client =clientRepository.findById(id)
			.orElseThrow(()->new ClientNotFoundException(id));
		return clientMapper.toResponse(client);
	}
	@Override
	@Transactional(readOnly = true)
	public Page<ClientResponse> getAllClients(Pageable pageable) {
		return clientRepository.findAll(pageable)
		        .map(clientMapper::toResponse);
	}
	@Override
	@Transactional
	public ClientResponse updateClient(Long id, ClientRequest request) {
		Client client =clientRepository.findById(id)
				.orElseThrow(()->new ClientNotFoundException(id));
		client.setNom(request.getNom());
	    client.setPrenom(request.getPrenom());
	    client.setTelephone(request.getTelephone());
	    
	    return clientMapper.toResponse(client);
	}
	@Override
	@Transactional 
	public void supprimerClient(Long id) {
		Client client =clientRepository.findById(id)
				.orElseThrow(()->new ClientNotFoundException(id));
		client.setStatut(StatutClient.CLOTURE);
		
	}
	
	
}

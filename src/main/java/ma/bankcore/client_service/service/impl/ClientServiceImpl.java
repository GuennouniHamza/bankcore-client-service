package ma.bankcore.client_service.service.impl;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ma.bankcore.client_service.dto.ClientRequest;
import ma.bankcore.client_service.dto.ClientResponse;
import ma.bankcore.client_service.entity.Client;
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
	public ClientResponse creerClient(ClientRequest request) {
		if(clientRepository.existByEmail(request.getEmail())) {
			throw new EmailDejaUtiliseException(request.getEmail())
		}
		Client client = clientMapper.toEntity(request);
		Client saved =clientRepository.save(client);
		
		return clientMapper.toResponse(saved);
	}
	@Override
	public ClientResponse getClientById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Page<ClientResponse> getAllClients(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ClientResponse updateClient(Long id, ClientRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void supprimerClient(Long id) {
		// TODO Auto-generated method stub
		
	}
	
	
}

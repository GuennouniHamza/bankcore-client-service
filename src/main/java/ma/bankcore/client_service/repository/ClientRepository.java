package ma.bankcore.client_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ma.bankcore.client_service.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
	
	boolean existByEmail(String email);
	
	Optional<Client>findByEmail(String email);
	
	 @Query("SELECT c FROM Client c WHERE c.email = :email AND c.statut = 'ACTIF'")
	 Optional<Client> findClientActifByEmail(String email);
}

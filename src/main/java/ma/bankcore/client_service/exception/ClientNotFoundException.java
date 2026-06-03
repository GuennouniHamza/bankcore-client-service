package ma.bankcore.client_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends RuntimeException {
	
	public ClientNotFoundException(Long id) {
        super("Client introuvable avec l'ID : " + id);
    }

    public ClientNotFoundException(String email) {
        super("Client introuvable avec l'email : " + email);
    }

}

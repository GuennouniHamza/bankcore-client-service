package ma.bankcore.client_service.dto;

import ma.bankcore.client_service.entity.StatutClient;

import java.time.LocalDateTime;



public class ClientResponse {

    private Long id;
    private String nom;
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public StatutClient getStatut() {
		return statut;
	}
	public void setStatut(StatutClient statut) {
		this.statut = statut;
	}
	public LocalDateTime getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}
	private String prenom;
    private String email;
    private String telephone;
    private StatutClient statut;
    private LocalDateTime dateCreation;
}
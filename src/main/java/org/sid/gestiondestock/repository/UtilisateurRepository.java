package org.sid.gestiondestock.repository;

import org.sid.gestiondestock.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findUtilisateurByNumTel(String numTel);
    Optional<Utilisateur> findUtilisateurByEmail(String email);
}

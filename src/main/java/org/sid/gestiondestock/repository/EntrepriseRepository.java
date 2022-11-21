package org.sid.gestiondestock.repository;

import org.sid.gestiondestock.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer> {

    Optional<Entreprise> findEntrepriseByNumTel(String numTel);
    Optional<Entreprise> findEntrepriseByEmail(String email);
    Optional<Entreprise> findEntrepriseByNom(String nom);
}

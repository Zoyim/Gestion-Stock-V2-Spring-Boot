package org.sid.gestiondestock.repository;

import org.sid.gestiondestock.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Integer> {

    Optional<Fournisseur> findFournisseurByNumTel(String numTel);
    Optional<Fournisseur> findFournisseurByEmail(String email);
}

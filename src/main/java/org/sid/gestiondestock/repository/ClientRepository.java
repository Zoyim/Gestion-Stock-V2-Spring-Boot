package org.sid.gestiondestock.repository;

import org.sid.gestiondestock.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findClientByNumTel(String numTel);
    Optional<Client> findClientByEmail(String email);
}

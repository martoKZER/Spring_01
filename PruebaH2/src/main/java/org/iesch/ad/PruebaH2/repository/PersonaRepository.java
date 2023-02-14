package org.iesch.ad.PruebaH2.repository;

import org.iesch.ad.PruebaH2.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
}

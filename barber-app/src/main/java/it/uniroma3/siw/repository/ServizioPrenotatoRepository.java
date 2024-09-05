package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.ServizioPrenotato;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface ServizioPrenotatoRepository extends CrudRepository<ServizioPrenotato, Long> {
    public boolean existsByUtenteIdAndGiornoLavorativo_Data(Long utenteId, LocalDate dataPrenotazione);
}

package it.uniroma3.siw.validator;

import it.uniroma3.siw.model.ServizioPrenotato;
import it.uniroma3.siw.service.BarbiereService;
import it.uniroma3.siw.service.ServizioPrenotatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class ServizioPrenotatoValidator implements Validator {

    @Autowired
    private ServizioPrenotatoService servizioPrenotatoService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ServizioPrenotato.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ServizioPrenotato servizioPrenotato = (ServizioPrenotato) target;
        Long utenteId = servizioPrenotato.getUtente().getId();
        LocalDate dataPrenotazione = servizioPrenotato.getGiornoLavorativo().getData();

        // Controllo se esiste già una prenotazione per questo utente nello stesso giorno
        boolean haGiaPrenotato = servizioPrenotatoService.existsByUtenteIdAndGiornoLavorativo_Data(utenteId, dataPrenotazione);

        if (haGiaPrenotato) {
            errors.rejectValue("giornoLavorativo", "prenotazione.giaEsistente", "Hai già una prenotazione per questo giorno.");
        }
    }
}

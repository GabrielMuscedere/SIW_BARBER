package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.CustomUserDetails;
import it.uniroma3.siw.model.ServizioPrenotato;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

   @PostMapping("/user/profile/filtraPerData")
    public String filtraPerData(Model model,
                                @AuthenticationPrincipal CustomUserDetails userDetails,
                                @RequestParam LocalDate data) {
       List<ServizioPrenotato> prenotazioni = userDetails.getUtente().getPrenotazioni();

       prenotazioni.removeIf(p -> !p.getGiornoLavorativo().getData().equals(data));

       model.addAttribute("prenotazioni", prenotazioni);

       if (userDetails != null) {
           if (userDetails.getCredentials().getRole().equals("ROLE_DEFAULT") || userDetails.getCredentials().getRole().equals("DEFAULT")) {
               LocalDate today = LocalDate.now();
               model.addAttribute("today", today);
               model.addAttribute("authentication", userDetails);
               model.addAttribute("prenotazioni", userDetails.getUtente().getPrenotazioni());
               return "/user/profile";
           } else if (userDetails.getCredentials().getRole().equals("ROLE_ADMIN")) {
               model.addAttribute("barbiere", userDetails);
               return "/admin/profile";
           }
       }
       // Se l'utente non è autenticato, reindirizza alla pagina di login
       return "redirect:/login";
   }

}

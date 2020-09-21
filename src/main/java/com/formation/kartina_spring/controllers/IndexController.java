package com.formation.kartina_spring.controllers;


import com.formation.kartina_spring.enums.RoleUtilisateur;
import com.formation.kartina_spring.models.UserType;
import com.formation.kartina_spring.models.Utilisateur;
import com.formation.kartina_spring.services.UserTypeService;
import com.formation.kartina_spring.services.UtilisateurService;
import com.formation.kartina_spring.utils.MotPassCodifier;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Base64;
import java.util.Optional;

@Controller
public class IndexController {

    private UtilisateurService utilisateurService;
    private final UserTypeService userTypeService;

    @Autowired
    public IndexController(UtilisateurService utilisateurService, UserTypeService userTypeService) {
        this.utilisateurService = utilisateurService;
        this.userTypeService = userTypeService;
    }

    //Index, page principale
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("fragment", "index");
        return "index";
    }

    //Page parcours des photographies
    @GetMapping("/explore")
    public String getPhotographies(Model model) {
        model.addAttribute("fragment", "parcours_photo");
        return "index";
    }

    //Page artiste
    @GetMapping("/artist")
    public String getArtiste(Model model) {
        model.addAttribute("fragment", "artiste");
        return "index";
    }

    // Page connexion du formulaire
    @GetMapping("/connexion")
    public String getConnexion(Model model) {
        model.addAttribute("fragment", "connexion");
        return "index";
    }

    @PostMapping("/connexion")
    public String postConnexion(Model model) {
        model.addAttribute("fragment", "connexion");
        return "index";


    } // Page inscription du formulaire

    @GetMapping("/inscription")
    public String getInscription(Model model) {
        model.addAttribute("fragment", "inscription");
        return "index";
    }

    @PostMapping("/inscription")
    public String postInscription(Model model,
                                  @Valid @ModelAttribute(name = "utilisateur") Utilisateur utilisateur,
                                  BindingResult utilisateurBinding
    ) {

        RoleUtilisateur roleUtilisateur;
        if (utilisateurService.findAll().isEmpty()){
            roleUtilisateur = RoleUtilisateur.ADMIN;
        }else{
            roleUtilisateur = RoleUtilisateur.UTILISATEUR;
        }
        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findByEmail(utilisateur.getEmail());
        Optional<UserType> userType = userTypeService.findAll()
                .stream()
                .filter(
                        ut-> ut.getUserEnum().equals(roleUtilisateur)
                )
                .findAny();
        if (!utilisateurBinding.hasErrors() && optionalUtilisateur.isEmpty()) {
            MotPassCodifier mdpCodifier = new MotPassCodifier();

            byte[] salt = mdpCodifier.genererSalt();
            utilisateur.setSalt(Base64.getEncoder().encodeToString(salt));

            byte[] motpasse = mdpCodifier.genererMotPasse(utilisateur.getPassword(), salt);
            utilisateur.setPasswordHash(Base64.getEncoder().encodeToString(motpasse));

            utilisateur.setRole(userType.orElse(userTypeService.findByRole(RoleUtilisateur.UTILISATEUR)));

            utilisateurService.save(utilisateur);
            return "redirect:/";
        }

        model.addAttribute("fragment", "inscription");
        return "index";
    }


}

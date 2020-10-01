package com.formation.kartina_spring.controllers;

import com.formation.kartina_spring.enums.RoleUtilisateur;
import com.formation.kartina_spring.models.Adresse;
import com.formation.kartina_spring.models.UserType;
import com.formation.kartina_spring.models.Utilisateur;
import com.formation.kartina_spring.services.UserTypeService;
import com.formation.kartina_spring.services.UtilisateurService;
import com.formation.kartina_spring.utils.MotPassCodifier;
import org.springframework.beans.factory.annotation.Autowired;
import com.formation.kartina_spring.utils.RemplissageBDD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
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

    @Autowired
    private RemplissageBDD remplissageBDD;

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



    // Page inscription du formulaire

    @GetMapping("/inscription")
    public String getInscription(Model model, Utilisateur utilisateur, Adresse adresse) {
        model.addAttribute("fragment", "inscription");
        return "index";
    }

    @PostMapping("/inscription")
    public String postInscription(Model model,
                                  @Valid @ModelAttribute(name = "adresse") Adresse adresse,
                                  BindingResult adresseBinding,
                                  @Valid @ModelAttribute(name = "utilisateur") Utilisateur utilisateur,
                                  BindingResult utilisateurBinding

    ) {

        UserType ut1 = new UserType();
        ut1.setUserEnum(RoleUtilisateur.ADMIN);
        userTypeService.save(ut1);
        ut1.setUserEnum(null);
        ut1.setUserEnum(RoleUtilisateur.UTILISATEUR);
        userTypeService.save(ut1);

        RoleUtilisateur roleUtilisateur;
        if (utilisateurService.findAll().isEmpty()) {
            roleUtilisateur = RoleUtilisateur.ADMIN;
        } else {
            roleUtilisateur = RoleUtilisateur.UTILISATEUR;
        }
        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findByEmail(utilisateur.getEmail());
        Optional<UserType> userType = userTypeService.findAll()
                .stream()
                .filter(
                        ut -> ut.getUserEnum().equals(roleUtilisateur)
                )
                .findAny();

        if (optionalUtilisateur.isPresent()) {
            model.addAttribute("mailExiste", "Une compte existe déjà avec cette adresse");
        }

        if (!utilisateurBinding.hasErrors() && optionalUtilisateur.isEmpty() && !adresseBinding.hasErrors()) {

            MotPassCodifier mdpCodifier = new MotPassCodifier();

            byte[] salt = mdpCodifier.genererSalt();
            utilisateur.setSalt(Base64.getEncoder().encodeToString(salt));

            byte[] motpasse = mdpCodifier.genererMotPasse(utilisateur.getPassword(), salt);
            utilisateur.setPasswordHash(Base64.getEncoder().encodeToString(motpasse));

            utilisateur.setRole(userType.orElse(userTypeService.findByRole(RoleUtilisateur.UTILISATEUR)));
            utilisateur.setAdresse(adresse);
            utilisateurService.save(utilisateur);
            return "redirect:/";
        }

        model.addAttribute("fragment", "inscription");
        return "index";
    }

    // Page connexion du formulaire
    @GetMapping("/login")
    public String  getLogin(Model model, Utilisateur utilisateur){
        model.addAttribute("fragment", "login");
        return "index";
    }


    @PostMapping("/login")
    public String  PostLogin(Model model,
                             @Valid @ModelAttribute(name = "utilisateur") Utilisateur utilisateur,
                             BindingResult utilisateurBinding,
                             HttpSession session

    ) {
        if(!utilisateurBinding.hasErrors()){
            Optional<Utilisateur> utilisateur1 = utilisateurService.findByEmail(utilisateur.getEmail());

            if (utilisateur1.isPresent()) {
                MotPassCodifier mdpCodifier = new MotPassCodifier();
                byte[] h = mdpCodifier.genererMotPasse(utilisateur.getPassword(), Base64.getDecoder().decode(utilisateur1.get().getSalt()));
                if (Base64.getEncoder().encodeToString(h).equals(utilisateur1.get().getPasswordHash())){
                    session.setAttribute("utilisateur", utilisateur1.get());
                    return "redirect:/";
                }
            }

            model.addAttribute("errorCompte", "Email ou le mdp incorrect");
        }

        model.addAttribute("fragment", "login");
        return "index";

    }


    @GetMapping("/remplissage")
    public String remplissage(){
        remplissageBDD.saveDB();
        return "redirect:/";
    }
}

package com.formation.kartina_spring.controllers;

import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {
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

    // Page connexion  du formulaire
    @GetMapping("/connexion")
    public  String  getConnexion(Model model) {
        model.addAttribute("fragment", "connexion");
        return "index";
    }

    @PostMapping("/connexion")
    public  String  postConnexion(Model model) {
        model.addAttribute("fragment", "connexion");
        return "index";
    }
}

package com.formation.kartina_spring.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Utilisateur {

    @Id
    @Column(nullable = false)
    private String email;

    @Column(length = 10, nullable = false)
    private String civilite;

    @Column(nullable = false)
    @Pattern(regexp = "^[a-z \\-À-ÖØ-öø-ÿ]{2,155}$", flags = {Pattern.Flag.CASE_INSENSITIVE, Pattern.Flag.DOTALL})
    private String nom;

    @Column(nullable = false)
    @Pattern(regexp = "^[a-z \\-À-ÖØ-öø-ÿ]{2,155}$", flags = {Pattern.Flag.CASE_INSENSITIVE, Pattern.Flag.DOTALL})
    private String prenom;

    @Column(length = 16, nullable = false)
    private String telephone;

    @Transient
    @Pattern(regexp = "^.{6,32}$", flags = {Pattern.Flag.CASE_INSENSITIVE, Pattern.Flag.DOTALL})
    private String password;

    @Column(length = 150, nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String salt;

    //Liaison entre tables
    @ManyToOne
    @JoinColumn(nullable = false) //Il ne peut pas d'user sans role
    private UserType role;

    @OneToOne
    private Adresse adresse;

    @ManyToOne
    private Artiste artiste;

    @OneToMany(mappedBy = "user")
    private List<Commande> commandes = new ArrayList<>();
}

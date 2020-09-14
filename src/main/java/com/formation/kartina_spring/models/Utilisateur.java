package com.formation.kartina_spring.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Utilisateur {

    @Id
    private String email;

    @Column(length = 10)
    private String civilite;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(length = 16)
    private String telephone;

    @Transient
    private String password;

    @Column(length = 150, nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String salt;

    //Liaison entre tables
    @ManyToOne
    private UserType role;

    @OneToOne
    private Adresse adresse;

    @ManyToOne
    private Artiste artiste;

    @OneToMany(mappedBy = "user")
    private List<Commande> commandes = new ArrayList<>();
}

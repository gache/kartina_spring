package com.formation.kartina_spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Adresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adresseId;

    @Column(length = 10, nullable = false)
    private String num;

    @Column(length = 40, nullable = false)
    private String voie;

    private String complementVoie;

    @Column(nullable = false)
    private String ville;

    @Column(length = 10, nullable = false)
    private Long codePostal;

    @Column(length = 100, nullable = false)
    private String pays;

    @Column(length = 50)
    private String nomAdresse;

    //Liaison
    @OneToOne(mappedBy = "adresse")
    @JoinColumn(nullable = false) //Il ne peut pas exister d'adresse sans utilisateur
    private Utilisateur user;
}

package com.formation.kartina_spring.models;

import com.formation.kartina_spring.enums.RoleUtilisateur;
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
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long type_id;

    @Enumerated(EnumType.STRING)
    private RoleUtilisateur user_emun;

    //Liaison entre tables
    @OneToMany(mappedBy = "role") //Plus gros max donc donne sa clé à user (fk)
    private List<Utilisateur> user = new ArrayList<>();
}

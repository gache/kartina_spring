package com.formation.kartina_spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DetailCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detailCommandeId;

    private Integer quantiteArticle;

    @Column(length = 100)
    private String format;

    @Column(length = 100)
    private String finition;

    @Column(length = 100)
    private String cadre;
}

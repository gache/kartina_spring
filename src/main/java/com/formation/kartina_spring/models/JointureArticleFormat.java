package com.formation.kartina_spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class JointureArticleFormat {

    @EmbeddedId
    private JointureArticleFormatId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("articleId")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("formatId")
    private Format format;

    private Float coefficientPrix;
}

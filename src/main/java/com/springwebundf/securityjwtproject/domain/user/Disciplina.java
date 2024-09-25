package com.springwebundf.securityjwtproject.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "disciplinas")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(columnDefinition = "text")
    private String nome;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToMany(mappedBy = "disciplinas")
    private List<Aluno> alunos;

}

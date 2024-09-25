package com.springwebundf.securityjwtproject.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "professors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Professor extends User{

    @OneToMany(mappedBy = "professor", fetch = FetchType.EAGER)
    private List<Disciplina> disciplinas;

    @Column(columnDefinition = "text")
    private String cpf;
}

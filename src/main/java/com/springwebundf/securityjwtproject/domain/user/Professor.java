package com.springwebundf.securityjwtproject.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "professores")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Professor extends User {

    @Serial
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "professor", fetch = FetchType.EAGER)
    private List<Disciplina> disciplinas;

}

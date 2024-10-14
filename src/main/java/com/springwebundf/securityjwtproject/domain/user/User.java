package com.springwebundf.securityjwtproject.domain.user;

import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    public User(String tipo, String cpf) {
       setCpf(cpf);
       this.tipo = tipo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(columnDefinition = "text")
    private String name;
    @Column(columnDefinition = "text")
    private String password;
    @Column(columnDefinition = "text")
    private String cpf;

    @Setter(AccessLevel.NONE)
    private String tipo;

}

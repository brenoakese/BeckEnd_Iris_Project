package com.springwebundf.securityjwtproject.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "coordenadores")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Coordenador extends User{


    @Setter(AccessLevel.NONE)
    private String role = "ROLE_COORDENADOR";
}

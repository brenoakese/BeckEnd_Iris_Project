package com.springwebundf.securityjwtproject.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Entity
@Table(name = "coordenadores")
@AllArgsConstructor
@Getter
@Setter
public class Coordenador extends User{
    @Serial
    private static final long serialVersionUID = 1L;
}

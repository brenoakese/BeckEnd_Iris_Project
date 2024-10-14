package com.springwebundf.securityjwtproject.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "coordenadores")
@AllArgsConstructor
@Getter
@Setter
public class Coordenador extends User{


}

package com.springwebundf.securityjwtproject.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.springwebundf.securityjwtproject.domain.user.Permissions.*;

@RequiredArgsConstructor
public enum Roles {

    ROLE_PROFESSOR(
            Set.of(
                    PROFESSOR_READ,
                    PROFESSOR_WRITE,
                    PROFESSOR_UPDATE,
                    PROFESSOR_DELETE
            )
    ),


    ROLE_ALUNO(
            Set.of(
                    ALUNO_READ
            )
    ),

    ROLE_COORDENADOR(
            Set.of(
                    COORDENADOR_WRITE,
                    COORDENADOR_READ,
                    COORDENADOR_UPDATE,
                    COORDENADOR_DELETE
            )
    );

    @Getter
    private final Set<Permissions> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {

            var authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
            authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

            return authorities;
    }
}

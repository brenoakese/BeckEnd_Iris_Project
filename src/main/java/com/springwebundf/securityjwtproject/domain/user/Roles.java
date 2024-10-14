package com.springwebundf.securityjwtproject.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

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
}

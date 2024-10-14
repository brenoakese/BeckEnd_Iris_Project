package com.springwebundf.securityjwtproject.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permissions {

    PROFESSOR_READ("professor:read"),
    PROFESSOR_WRITE("professor:write"),
    PROFESSOR_UPDATE("professor:update"),
    PROFESSOR_DELETE("professor:delete"),

    COORDENADOR_READ("coordenador:read"),
    COORDENADOR_WRITE("coordenador:write"),
    COORDENADOR_UPDATE("coordenador:update"),
    COORDENADOR_DELETE("coordenador:delete"),

    ALUNO_READ("aluno:read");

    @Getter
    private final String permission;
}

package com.springwebundf.securityjwtproject.controllers;

import com.springwebundf.securityjwtproject.domain.user.Aluno;
import com.springwebundf.securityjwtproject.domain.user.Coordenador;
import com.springwebundf.securityjwtproject.domain.user.Professor;
import com.springwebundf.securityjwtproject.dto.LoginRequestDTO;
import com.springwebundf.securityjwtproject.dto.RegisterRequestDTO;
import com.springwebundf.securityjwtproject.dto.ResponseDTO;
import com.springwebundf.securityjwtproject.infra.security.TokenData;
import com.springwebundf.securityjwtproject.infra.security.TokenService;
import com.springwebundf.securityjwtproject.repositories.AlunoRepository;
import com.springwebundf.securityjwtproject.repositories.CoordenadorRepository;
import com.springwebundf.securityjwtproject.repositories.ProfessorRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CoordenadorRepository coordenadorRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        String user = typeUser(body.cpf());
        if(user == null) return ResponseEntity.badRequest().build();

        if (user.equals("coordenador")) {
            Optional<Coordenador> coordenador = coordenadorRepository.findByCpf(body.cpf());
            if (
                coordenador.isPresent() &&
                passwordEncoder.matches(
                    body.password(),
                    coordenador.get().getPassword()
                )
            ) {
                TokenData token = tokenService.generateToken(coordenador.get());
                return ResponseEntity.ok(
                    new ResponseDTO(token, coordenador.get().getName(), "coordenador")
                );
            }
        }

        if (user.equals("professor")) {
            Optional<Professor> professor = professorRepository.findByCpf(
                body.cpf()
            );
            if (
                professor.isPresent() &&
                passwordEncoder.matches(
                    body.password(),
                    professor.get().getPassword()
                )
            ) {
                TokenData token = tokenService.generateToken(professor.get());
                return ResponseEntity.ok(
                    new ResponseDTO(
                        token,
                        professor.get().getName(),
                        "professor"
                    )
                );
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register/professor")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        String user = typeUser(body.cpf());

        if(user != null){
            return ResponseEntity.badRequest().build();
        }

        Professor newprofessor = new Professor();

        newprofessor.setName(body.name());
        newprofessor.setCpf(body.cpf());
        newprofessor.setPassword(passwordEncoder.encode(body.password()));

        professorRepository.save(newprofessor);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register/coordenador")
    public ResponseEntity registerCoordenador(@RequestBody RegisterRequestDTO body){
        String user = typeUser(body.cpf());

        if(user != null){
            return ResponseEntity.badRequest().build();
        }

        Coordenador newCoordenador = new Coordenador();

        newCoordenador.setName(body.name());
        newCoordenador.setCpf(body.cpf());
        newCoordenador.setPassword(passwordEncoder.encode(body.password()));

        coordenadorRepository.save(newCoordenador);

        return ResponseEntity.ok().build();
    }

    private String typeUser(String cpf) {
        Optional<Aluno> aluno = alunoRepository.findByCpf(cpf);

        if(aluno.isPresent()) return "aluno";

        Optional<Professor> professor = professorRepository.findByCpf(cpf);

        if(professor.isPresent()) return "professor";

        Optional<Coordenador> coordenador = coordenadorRepository.findByCpf(cpf);
        if(coordenador.isPresent()) return "coordenador";

        return null;
    }
}

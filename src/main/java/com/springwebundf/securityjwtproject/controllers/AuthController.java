package com.springwebundf.securityjwtproject.controllers;

import com.springwebundf.securityjwtproject.domain.user.Aluno;
import com.springwebundf.securityjwtproject.domain.user.Coordenador;
import com.springwebundf.securityjwtproject.domain.user.Professor;
import com.springwebundf.securityjwtproject.domain.user.User;
import com.springwebundf.securityjwtproject.dto.RegisterRequestDTO;
import com.springwebundf.securityjwtproject.dto.ResponseDTO;
import com.springwebundf.securityjwtproject.infra.security.TokenService;
import com.springwebundf.securityjwtproject.repositories.AlunoRepository;
import com.springwebundf.securityjwtproject.repositories.CoordenadorRepository;
import com.springwebundf.securityjwtproject.repositories.ProfessorRepository;
import com.springwebundf.securityjwtproject.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springwebundf.securityjwtproject.dto.LoginRequestDTO;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CoordenadorRepository coordenadorRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){

/*        String typeUser = typeUser(body.cpf());
        if(typeUser == null) return ResponseEntity.badRequest().build();

        else if (typeUser.equals("aluno")) {
            Optional<Aluno> aluno = alunoRepository.findByCpf(body.cpf());
            if(aluno.isPresent() && passwordEncoder.matches(body.password(), aluno.get().getPassword())){
                String token = tokenService.generateToken(aluno.get());
                return ResponseEntity.ok(new ResponseDTO(token, aluno.get().getName(), "aluno"));
            }
        }
        else if (typeUser.equals("professor")) {
            Optional<Professor> professor = professorRepository.findByCpf(body.cpf());
            if(professor.isPresent() && passwordEncoder.matches(body.password(), professor.get().getPassword())){
                String token = tokenService.generateToken(professor.get());
                return ResponseEntity.ok(new ResponseDTO(token, professor.get().getName(), "professor"));
            }

        }*/
        Map<String, UserRepository<? extends User>> userRepositories = Map.of(
                "aluno", alunoRepository,
                "professor", professorRepository,
                "coordenador", coordenadorRepository
        );

        for(Map.Entry<String, UserRepository<? extends User>> entry : userRepositories.entrySet()){
            Optional<? extends User> user = entry.getValue().findByCpf(body.cpf());
            if(user.isPresent() && passwordEncoder.matches(body.password(), user.get().getPassword())){
                String token = tokenService.generateToken(user.get());
                return ResponseEntity.ok(new ResponseDTO(token, user.get().getName(), entry.getKey()));
            }
        }

        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
    @Transactional
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<Professor> professor = professorRepository.findByCpf(body.cpf());

        if(professor.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        else {
            Professor newprofessor = new Professor();
            newprofessor.setName(body.name());
            newprofessor.setCpf(body.cpf());
            newprofessor.setPassword(passwordEncoder.encode(body.password()));
            userRepository.save(newprofessor);
            professorRepository.save(newprofessor);
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/register/coordenador")
    @Transactional
    public ResponseEntity registerCoordenador(@RequestBody RegisterRequestDTO body){
        Optional<Coordenador> coordenador = coordenadorRepository.findByCpf(body.cpf());

        if(coordenador.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        else {
            Coordenador newCoordenador = new Coordenador();
            newCoordenador.setName(body.name());
            newCoordenador.setCpf(body.cpf());
            newCoordenador.setPassword(passwordEncoder.encode(body.password()));
            userRepository.save(newCoordenador);
            coordenadorRepository.save(newCoordenador);
            return ResponseEntity.ok().build();
        }
    }



    private String typeUser(String cpf) {
        Optional<Aluno> aluno = alunoRepository.findByCpf(cpf);

        if(aluno.isPresent()) return "aluno";

        Optional<Professor> professor = professorRepository.findByCpf(cpf);

        if(professor.isPresent()) return "professor";

        return null;
    }
}

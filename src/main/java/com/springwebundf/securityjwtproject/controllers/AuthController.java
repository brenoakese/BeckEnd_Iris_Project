package com.springwebundf.securityjwtproject.controllers;

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
import lombok.RequiredArgsConstructor;
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
        Optional<User> user = userRepository.findByCpf(body.cpf());
        if (user.isPresent() && passwordEncoder.matches(body.password(), user.get().getPassword())) {
            String token = tokenService.generateToken(user.get());
            return ResponseEntity.ok(new ResponseDTO(token, user.get().getName(), user.get().getClass().getSimpleName().toUpperCase()));
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register/professor")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<User> user = userRepository.findByCpf(body.cpf());

        if(user.isPresent()){
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
        Optional<User> user = userRepository.findByCpf(body.cpf());

        if(user.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        Coordenador newCoordenador = new Coordenador();

        newCoordenador.setName(body.name());
        newCoordenador.setCpf(body.cpf());
        newCoordenador.setPassword(passwordEncoder.encode(body.password()));

        coordenadorRepository.save(newCoordenador);

        return ResponseEntity.ok().build();
    }
}

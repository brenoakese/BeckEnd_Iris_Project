package com.springwebundf.securityjwtproject.controllers;

import com.springwebundf.securityjwtproject.domain.user.Aluno;
import com.springwebundf.securityjwtproject.domain.user.Professor;
import com.springwebundf.securityjwtproject.domain.user.User;
import com.springwebundf.securityjwtproject.dto.RegisterRequestDTO;
import com.springwebundf.securityjwtproject.dto.ResponseDTO;
import com.springwebundf.securityjwtproject.infra.security.TokenService;
import com.springwebundf.securityjwtproject.repositories.AlunoRepository;
import com.springwebundf.securityjwtproject.repositories.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springwebundf.securityjwtproject.dto.LoginRequestDTO;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        Professor professor = professorRepository.findByCpf(body.cpf()).orElseThrow(() -> new RuntimeException("User not found."));
        if (passwordEncoder.matches(body.password(), professor.getPassword())) {
            String token = tokenService.generateToken(professor);
            return ResponseEntity.ok( new ResponseDTO(token, professor.getName()));
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
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
            professorRepository.save(newprofessor);
            String token = tokenService.generateToken(newprofessor);
            return ResponseEntity.ok(new ResponseDTO(token, newprofessor.getName()));
        }
    }
    private User getUser(String cpf) {
        Optional<Aluno> aluno = alunoRepository.findByCpf(cpf);

        if(aluno.isPresent()) return aluno.get();

        Optional<Professor> professor = professorRepository.findByCpf(cpf);

        return professor.orElse(null);
    }
}

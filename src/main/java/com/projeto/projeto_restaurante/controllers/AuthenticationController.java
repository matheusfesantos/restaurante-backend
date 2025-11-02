package com.projeto.projeto_restaurante.controllers;

import com.projeto.projeto_restaurante.dto.AuthenticationDTO;
import com.projeto.projeto_restaurante.dto.LoginResponseDTO;
import com.projeto.projeto_restaurante.dto.RegisterDTO;
import com.projeto.projeto_restaurante.entity.Usuarios;
import com.projeto.projeto_restaurante.infra.security.TokenService;
import com.projeto.projeto_restaurante.repositories.UsuariosRepository;
import com.projeto.projeto_restaurante.services.RegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuarios) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO data){
        try {
            if (registerService.existByEmail(data.email())){
                return new ResponseEntity<String>("Email já está cadastrado", HttpStatus.BAD_REQUEST);
            }

            String mensagem = registerService.save(data);
            return new ResponseEntity<String>(mensagem, HttpStatus.OK);

        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}

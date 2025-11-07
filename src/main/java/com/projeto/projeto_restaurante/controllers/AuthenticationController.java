package com.projeto.projeto_restaurante.controllers;

import com.projeto.projeto_restaurante.models.dto.AuthenticationDTO;
import com.projeto.projeto_restaurante.models.dto.LoginResponseDTO;
import com.projeto.projeto_restaurante.models.dto.RegisterDTO;
import com.projeto.projeto_restaurante.models.entity.Usuarios;
import com.projeto.projeto_restaurante.infra.security.TokenService;
import com.projeto.projeto_restaurante.services.RegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data){
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
            var auth = authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((Usuarios) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        }
        catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of("message","Credenciais invalidas"));
        }
        catch (UsernameNotFoundException e){
            return ResponseEntity.badRequest().body(
                    Map.of("message","Usuário não encontrado"));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(
                    Map.of("message","Problema ao se conectar com o servidor"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data){
        try {
            boolean emailExiste = registerService.existByEmail(data.email());

            if (emailExiste){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        Map.of("message", "Esse email já foi cadastrado"));
            }

            boolean mensagem = registerService.save(data);

            if (!mensagem){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        Map.of("message", "Erro ao registrar suas informações")
                );
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    Map.of("message", "Usuário cadastrado com sucesso")
            );
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("message", "Erro interno ao cadastrar usuário")
            );
        }
    }
}

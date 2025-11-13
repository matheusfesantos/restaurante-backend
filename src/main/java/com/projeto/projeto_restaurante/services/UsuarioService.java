package com.projeto.projeto_restaurante.services;

import com.projeto.projeto_restaurante.infra.security.TokenService;
import com.projeto.projeto_restaurante.models.entity.Usuarios;
import com.projeto.projeto_restaurante.repositories.UsuariosRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class UsuarioService {

    private final UsuariosRepository repository;
    private final TokenService tokenService;

    public UsuarioService(UsuariosRepository repository, TokenService tokenService) {
        this.repository = repository;
        this.tokenService = tokenService;
    }

    public UUID recuperarUUIDporToken(String authorizationHeader){
        try{
            String token = authorizationHeader.replace("Bearer ", "");
            String email = tokenService.getSubject(token);

            Usuarios usuarios = repository.findUsuarioByEmail(email);

            if (usuarios == null){
                throw new EntityNotFoundException
                        ("O email: "+ email +", não pertence a nenhum usuário");
            }

            return usuarios.getId();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

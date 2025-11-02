package com.projeto.projeto_restaurante.services;

import com.projeto.projeto_restaurante.dto.RegisterDTO;
import com.projeto.projeto_restaurante.entity.Usuarios;
import com.projeto.projeto_restaurante.repositories.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    UsuariosRepository usuariosRepository;

    public String save (RegisterDTO data){
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuarios usuario = new Usuarios(data.nome(), data.email(), encryptedPassword);
        usuariosRepository.save(usuario);
        return "Usuário registrado com sucesso!";
    }

    public Boolean existByEmail (String email){
        return usuariosRepository.existsByEmail(email);
    }
}


package com.projeto.projeto_restaurante.services;

import com.projeto.projeto_restaurante.models.dto.RegisterDTO;
import com.projeto.projeto_restaurante.models.entity.Usuarios;
import com.projeto.projeto_restaurante.repositories.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class RegisterService {

    @Autowired
    UsuariosRepository usuariosRepository;

    private Logger logger = Logger.getLogger(RegisterService.class.getName());

    public Boolean save (RegisterDTO data){
        try{
            String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());

            logger.info("Senha criptografada: " + encryptedPassword);
            Usuarios usuarioNovo = new Usuarios();
            usuarioNovo.setNome(data.nome());
            usuarioNovo.setEmail(data.email());
            usuarioNovo.setSenha(encryptedPassword);

            //Usuarios usuario = new Usuarios(data.nome(), data.email(), encryptedPassword);

            usuariosRepository.save(usuarioNovo);
            return true;
        }
        catch (Exception e) {
            logger.warning("");
            throw new RuntimeException(e);
        }
    }

    public Boolean existByEmail (String email){
        return usuariosRepository.existsByEmail(email);
    }
}


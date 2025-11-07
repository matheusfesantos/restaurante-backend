package com.projeto.projeto_restaurante.repositories;

import com.projeto.projeto_restaurante.models.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UsuariosRepository extends JpaRepository<Usuarios, UUID> {
    UserDetails findByEmail(String email);

    boolean existsByEmail(String email);

    Usuarios findUsuarioByEmail(String email);
}


package com.projeto.projeto_restaurante.repositories;

import com.projeto.projeto_restaurante.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuariosRepository extends JpaRepository<Usuarios, String> {
    UserDetails findByEmail(String email);
}

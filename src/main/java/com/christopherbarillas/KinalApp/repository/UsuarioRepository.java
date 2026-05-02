package com.christopherbarillas.KinalApp.repository;// UsuarioRepository.java
import com.christopherbarillas.KinalApp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    List<Usuario> findByEstado(int i);

    @Query("SELECT u FROM Usuario u WHERE u.Username = :username")
    Optional<Usuario> findByUsername(@Param("username") String username);
}

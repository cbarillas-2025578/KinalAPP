package com.christopherbarillas.KinalApp.repository;

import com.christopherbarillas.KinalApp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,String>{
}

package com.christopherbarillas.KinalApp.security;

import com.christopherbarillas.KinalApp.entity.Usuario;
import com.christopherbarillas.KinalApp.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado: " + username));

        if (usuario.getEstado() != 1) {
            throw new UsernameNotFoundException("Usuario inactivo: " + username);
        }

        // El rol en BD se guarda como "ADMIN" o "USUARIO"
        // Spring Security espera el prefijo "ROLE_"
        String role = "ROLE_" + usuario.getRol().toUpperCase();

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(),
                List.of(new SimpleGrantedAuthority(role))
        );
    }
}

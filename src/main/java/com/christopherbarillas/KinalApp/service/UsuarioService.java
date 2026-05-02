package com.christopherbarillas.KinalApp.service;

import com.christopherbarillas.KinalApp.entity.Usuario;
import com.christopherbarillas.KinalApp.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        validarUsuario(usuario);
        if (usuario.getEstado() == 0) {
            usuario.setEstado(1);
        }
        // Si es nuevo usuario o se cambió la contraseña, encriptar
        if (usuario.getPassword() != null && !usuario.getPassword().startsWith("$2a$")) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorCodigo(String codigo) {
        return usuarioRepository.findById(codigo);
    }

    @Override
    public Usuario actualizar(String codigo, Usuario usuario) {
        Usuario existente = usuarioRepository.findById(codigo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con codigo: " + codigo));

        existente.setUsername(usuario.getUsername());
        existente.setEmail(usuario.getEmail());
        existente.setRol(usuario.getRol());
        existente.setEstado(usuario.getEstado());

        // Solo re-encriptar si se ingresó una nueva contraseña
        if (usuario.getPassword() != null && !usuario.getPassword().isBlank()
                && !usuario.getPassword().startsWith("$2a$")) {
            existente.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        return usuarioRepository.save(existente);
    }

    @Override
    public void eliminar(String codigo) {
        if (!usuarioRepository.existsById(codigo)) {
            throw new RuntimeException("El Usuario no se encontró con el codigo: " + codigo);
        }
        usuarioRepository.deleteById(codigo);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigo(String codigo) {
        return usuarioRepository.existsById(codigo);
    }

    @Override
    public List<Usuario> listarPorActivo() {
        return usuarioRepository.findByEstado(1);
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario.getCodigoUsuario() == null || usuario.getCodigoUsuario().trim().isEmpty()) {
            throw new IllegalArgumentException("El Codigo es un dato obligatorio");
        }
        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        if (usuario.getRol() == null || usuario.getRol().trim().isEmpty()) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorUsername(String username) {
        return usuarioRepository.findByUsername(username).isPresent();
    }

}

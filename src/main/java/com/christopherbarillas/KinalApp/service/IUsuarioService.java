package com.christopherbarillas.KinalApp.service;

import com.christopherbarillas.KinalApp.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> listarTodos();
    Usuario guardar(Usuario usuario);
    Optional<Usuario> buscarPorCodigo(String codigo);
    Usuario actualizar(String codigo, Usuario usuario);
    void eliminar(String codigo);
    boolean existePorCodigo(String codigo);
    List<Usuario> listarPorActivo();
    boolean existePorUsername(String username);
}

package com.christopherbarillas.KinalApp.service;

import com.christopherbarillas.KinalApp.entity.Cliente;
import com.christopherbarillas.KinalApp.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    //Interfaz: Es un contrato  que dice QUE metodos debe tener
    //cualquier: servicio de Usuarios, no tiene
    //implementacion, solo la definicion de los metodos

    //Metodo que devuelve una lista de todos los Usuarios
    List<Usuario> listarTodos();
    //List<Usuario> lo que hace es devolver una lista de objetos de la entidad Usuario

    //Metodo que guatda un cliente en BD
    Usuario guardar(Usuario usuario);
    //Parametros - Recibe un objeto de tipo Usuario con los datos a guardar

    //Optional - Contenedor que puede o no tener un valor
    //evita el error de NullPointerException
    Optional<Usuario> buscarPorCodigo(String codigo);

    //Metodo que actualiza un Cliente
    Usuario actualizar(String codigo, Usuario usuario);
    //Parametros - codigo: codigo del cliente a actualizar
    //Usuario usuario: Objeto con los datos nuevos
    //Retorna un objeto de tipo Usuario ya actualizado

    //Metodo de tipo void para eliminar a un usuario
    //void: no retorna ningun dato
    //Elimina un cliente por su codigo
    void eliminar(String codigo);

    //boolean - Retorna true  si existe, false si no existe
    boolean existePorCodigo(String codigo);

    //Metodo que devuelve una lista por estado
    List<Usuario> listarPorActivo();
}

package com.christopherbarillas.KinalApp.service;

import com.christopherbarillas.KinalApp.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteService {
    //Interfaz: Es un contrato  que dice QUE metodos debe tener
    //cualquier: servicio de Clientes, no tiene
    //implementacion, solo la definicion de los metodos

    //Metodo que devuelve una lista de todos los clientes
    List<Cliente> listarTodos();
    //List<Cliente> lo que hace es devolver una lista de objetos de la entidad Clientes

    //Metodo que guatda un cliente en BD
    Cliente guardar(Cliente cliente);
    //Parametros - Recibe un objeto de tipo cliente con los datos a guardar

    //Optional - Contenedor que puede o no tener un valor
    //evita el error de NullPointerException
    Optional<Cliente> buscarPorDPI(String dpi);

    //Metodo que actualiza un Cliente
    Cliente actualizar(String dpi, Cliente cliente);
    //Parametros - dpi: DPI del cliente a actualizar
    //Cliente cliente: Objeto con los datos nuevos
    //Retorna un objeto de tipo Cliente ya actualizado

    //Metodo de tipo void para eliminar a un cliente
    //void: no retorna ningun dato
    //Elimina un cliente por su DPI
    void eliminar(String dpi);

    //boolean - Retorna true  si existe, false si no existe
    boolean existePorDPI(String dpi);

    //Metodo que devuelve una lista por estado
    List<Cliente> listarPorActivo();


}

package com.christopherbarillas.KinalApp.service;

import com.christopherbarillas.KinalApp.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    //Interfaz: Es un contrato  que dice QUE metodos debe tener
    //cualquier: servicio de Productos, no tiene
    //implementacion, solo la definicion de los metodos

    //Metodo que devuelve una lista de todos los productos
    List<Producto> listarTodos();
    //List<Producto> lo que hace es devolver una lista de objetos de la entidad Producto

    //Metodo que guarda un producto en BD
    Producto guardar(Producto producto);
    //Parametros - Recibe un objeto de tipo cliente con los datos a guardar

    //Optional - Contenedor que puede o no tener un valor
    //evita el error de NullPointerException
    Optional<Producto> buscarPorCodigoProducto(String codigo);

    //Metodo que actualiza un Cliente
    Producto actualizar(String codigo, Producto producto);
    //Parametros - codigo: codigo del producto a actualizar
    //Producto producto: Objeto con los datos nuevos
    //Retorna un objeto de tipo Producto ya actualizado

    //Metodo de tipo void para eliminar a un producto
    //void: no retorna ningun dato
    //Elimina un producto por su codigo
    void eliminar(String codigo);

    //boolean - Retorna true  si existe, false si no existe
    boolean existePorCodigoProducto(String codigo);

    //Metodo que devuelve una lista por estado
    List<Producto> listarPorActivo();


}


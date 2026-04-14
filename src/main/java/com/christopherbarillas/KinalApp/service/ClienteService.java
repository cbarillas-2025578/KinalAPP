package com.christopherbarillas.KinalApp.service;

import com.christopherbarillas.KinalApp.entity.Cliente;
import com.christopherbarillas.KinalApp.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//Anotacion que registra un Bean como un Bean de Spring
//Que la clase contiene la logica del negocio
@Service
//Por defecto todos los metodos de estra ckase seran transaccionales
//Una transaccion es que puede o no  ocurrir algo
@Transactional
public class ClienteService implements IClienteService {
    /*private: solo accesible dentro de la clase
    ClienteRepository: es el repositorio para acceder a la BD
    Inyeccion de dependecias Spring nos da el repositorio
    */
    private final ClienteRepository clienteRepository;

    /*
    * Constructor: Este se ejecuta al crear el objeto
    * Parametros: Spring pasa el repositorio automatiucamente y a esto se le conoce como Inyeccion de Dependemcias
    * Asignamos el repositorio a nuestra variable de clase
    * */

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
/*
* @Override: Indicar que estamos implementando un metodo de la interfaz
* */
    @Override
    /*
    * readOnly = true: lo que hace es optimizar la consulta, no bloquea la BD
    * */
    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
        /*
        * Llama al metodo findAll() del repositorio de Spring Data JPA
        * este metodo hace exactamente el Select * from Clientes
        * */
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        /*
        * Metodo de guardar crea un Cliente
        * Aca es donde colocamos la logica del negocio antes de guardar
        * Primero validamos el dato
        * */
        validarCliente(cliente);
        if(cliente.getEstado() == 0){
            cliente.setEstado(1);
        }
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorDPI(String dpi) {
        //Buscar un cliente por DPI
        return clienteRepository.findById(dpi);
        //Optional nos evita el NullPointerException
    }

    @Override
    public Cliente actualizar(String dpi, Cliente cliente) {
        //Actualiza un cliente existente
        if(!clienteRepository.existsById(dpi)){
            throw new RuntimeException("Cliente no se encontro con DPI" + dpi);
            //Si no existe, se lanza una excepcion(error controlado)
        }

        cliente.setDPICliente(dpi);
        validarCliente(cliente);

        return clienteRepository.save(cliente);
    }

    @Override
    public void eliminar(String dpi) {
        //Eliminar un cliente
        if(!clienteRepository.existsById(dpi)){
            throw new RuntimeException("El cliente no se encontro con el DPI" +dpi);

        }
        clienteRepository.deleteById(dpi);

    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorDPI(String dpi) {
        //verificar si existe el cliente
        return clienteRepository.existsById(dpi);
        //retorns true o false
    }

    @Override
    public List<Cliente> listarPorActivo() {
        return clienteRepository.findByEstado(1);

    }

    //Metodo Privado (solo puede  utilizarse dentro de la clase)
    private void validarCliente(Cliente cliente){
        /*
        * Validaciones del negocio: Este metodo se hara privado porque es algo interno del servidpr
        * */
        if(cliente.getDPICliente() == null  || cliente.getDPICliente().trim().isEmpty()){
            //Si el dpi es null o esta vacio espues de quitar espacios lanza una exception con un mensaje
            throw new IllegalArgumentException("El DPI ws un dato obligatorio");

        }
        if (cliente.getNombreCliente() == null  || cliente.getNombreCliente().trim().isEmpty()){
            throw  new IllegalArgumentException("El nombre es un dato obligatorio");
        }
        if (cliente.getApellidoCliente() == null  || cliente.getApellidoCliente().trim().isEmpty()){
            throw  new IllegalArgumentException("El apellido es un dato obligatorio");
        }
    }


}

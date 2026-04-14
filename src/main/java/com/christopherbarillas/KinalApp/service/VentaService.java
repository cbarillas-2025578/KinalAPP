package com.christopherbarillas.KinalApp.service;

import com.christopherbarillas.KinalApp.entity.Venta;
import com.christopherbarillas.KinalApp.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

//Anotacion que registra un Bean como un Bean de Spring
//Que la clase contiene la logica del negocio
@Service
//Por defecto todos los metodos de esta clase seran transaccionales
//Una transaccion es que puede o no ocurrir algo
@Transactional
public class VentaService implements IVentaService {

    /*private: solo accesible dentro de la clase
    VentaRepository: es el repositorio para acceder a la BD
    Inyeccion de dependecias Spring nos da el repositorio
    */
    private final VentaRepository ventaRepository;

    /*
     * Constructor: Este se ejecuta al crear el objeto
     * Parametros: Spring pasa el repositorio automaticamente y a esto se le conoce como Inyeccion de Dependencias
     * Asignamos el repositorio a nuestra variable de clase
     * */
    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    /*
     * @Override: Indicar que estamos implementando un metodo de la interfaz
     * */
    @Override
    /*
     * readOnly = true: lo que hace es optimizar la consulta, no bloquea la BD
     * */
    @Transactional(readOnly = true)
    public List<Venta> listarTodos() {
        return ventaRepository.findAll();
        /*
         * Llama al metodo findAll() del repositorio de Spring Data JPA
         * este metodo hace exactamente el Select * from Ventas
         * */
    }

    @Override
    public Venta guardar(Venta venta) {
        /*
         * Metodo de guardar crea una Venta
         * Aca es donde colocamos la logica del negocio antes de guardar
         * Primero validamos el dato
         * */
        validarVenta(venta);
        if(venta.getEstado() == 0){
            venta.setEstado(1);
        }
        //Si la fecha es nula, asignamos la fecha actual
        if(venta.getFechaVenta() == null){
            venta.setFechaVenta(new Date());
        }
        return ventaRepository.save(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> buscarPorCodigo(Integer codigo) {
        //Buscar una venta por codigo
        return ventaRepository.findById(codigo);
        //Optional nos evita el NullPointerException
    }

    @Override
    public Venta actualizar(Integer codigo, Venta venta) {
        //Actualiza una venta existente
        if(!ventaRepository.existsById(codigo)){
            throw new RuntimeException("Venta no encontrada con codigo: " + codigo);
            //Si no existe, se lanza una excepcion(error controlado)
        }

        venta.setCodigoVenta(codigo);
        validarVenta(venta);

        return ventaRepository.save(venta);
    }

    @Override
    public void eliminar(Integer codigo) {
        //Eliminar una venta
        if(!ventaRepository.existsById(codigo)){
            throw new RuntimeException("La venta no se encontro con el codigo: " + codigo);
        }
        ventaRepository.deleteById(codigo);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigo(Integer codigo) {
        //verificar si existe la venta
        return ventaRepository.existsById(codigo);
        //retorna true o false
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarPorActivo() {
        return ventaRepository.findByEstado(1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarPorRangoFechas(Date fechaInicio, Date fechaFin) {
        return ventaRepository.findByFechaVentaBetween(fechaInicio, fechaFin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarPorCliente(String dpiCliente) {
        return ventaRepository.findByCliente_DPICliente(dpiCliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarPorUsuario(String codigoUsuario) {
        return ventaRepository.findByUsuario_CodigoUsuario(codigoUsuario);
    }

    //Metodo Privado (solo puede utilizarse dentro de la clase)
    private void validarVenta(Venta venta){
        /*
         * Validaciones del negocio: Este metodo se hara privado porque es algo interno del servicio
         * */
        if(venta.getCliente() == null){
            throw new IllegalArgumentException("El cliente es un dato obligatorio");
        }
        if(venta.getCliente().getDPICliente() == null || venta.getCliente().getDPICliente().trim().isEmpty()){
            throw new IllegalArgumentException("El DPI del cliente es un dato obligatorio");
        }
        if(venta.getUsuario() == null){
            throw new IllegalArgumentException("El usuario es un dato obligatorio");
        }
        if(venta.getUsuario().getCodigoUsuario() == null || venta.getUsuario().getCodigoUsuario().trim().isEmpty()){
            throw new IllegalArgumentException("El codigo del usuario es un dato obligatorio");
        }
        if(venta.getTotal() == null){
            throw new IllegalArgumentException("El total es un dato obligatorio");
        }
        if(venta.getTotal().compareTo(java.math.BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("El total debe ser mayor a cero");
        }
    }
}
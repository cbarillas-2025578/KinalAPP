package com.christopherbarillas.KinalApp.service;

import com.christopherbarillas.KinalApp.entity.DetalleVenta;
import com.christopherbarillas.KinalApp.repository.DetalleVentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

//Anotacion que registra un Bean como un Bean de Spring
//Que la clase contiene la logica del negocio
@Service
//Por defecto todos los metodos de esta clase seran transaccionales
//Una transaccion es que puede o no ocurrir algo
@Transactional
public class DetalleVentaService implements IDetalleVentaService {

    /*private: solo accesible dentro de la clase
    DetalleVentaRepository: es el repositorio para acceder a la BD
    Inyeccion de dependecias Spring nos da el repositorio
    */
    private final DetalleVentaRepository detalleVentaRepository;

    /*
     * Constructor: Este se ejecuta al crear el objeto
     * Parametros: Spring pasa el repositorio automaticamente y a esto se le conoce como Inyeccion de Dependencias
     * Asignamos el repositorio a nuestra variable de clase
     * */
    public DetalleVentaService(DetalleVentaRepository detalleVentaRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
    }

    /*
     * @Override: Indicar que estamos implementando un metodo de la interfaz
     * */
    @Override
    /*
     * readOnly = true: lo que hace es optimizar la consulta, no bloquea la BD
     * */
    @Transactional(readOnly = true)
    public List<DetalleVenta> listarTodos() {
        return detalleVentaRepository.findAll();
        /*
         * Llama al metodo findAll() del repositorio de Spring Data JPA
         * este metodo hace exactamente el Select * from DetalleVenta
         * */
    }

    @Override
    public DetalleVenta guardar(DetalleVenta detalleVenta) {
        /*
         * Metodo de guardar crea un DetalleVenta
         * Aca es donde colocamos la logica del negocio antes de guardar
         * Primero validamos el dato
         * */
        validarDetalleVenta(detalleVenta);

        //Calculamos el subtotal si no viene o si es necesario recalcular
        if(detalleVenta.getSubtotal() == null || detalleVenta.getSubtotal().compareTo(BigDecimal.ZERO) == 0){
            BigDecimal subtotal = detalleVenta.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(detalleVenta.getCantidad()));
            detalleVenta.setSubtotal(subtotal);
        }

        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetalleVenta> buscarPorCodigo(Integer codigo) {
        //Buscar un detalle de venta por codigo
        return detalleVentaRepository.findById(codigo);
        //Optional nos evita el NullPointerException
    }

    @Override
    public DetalleVenta actualizar(Integer codigo, DetalleVenta detalleVenta) {
        //Actualiza un detalle de venta existente
        if(!detalleVentaRepository.existsById(codigo)){
            throw new RuntimeException("Detalle de venta no encontrado con codigo: " + codigo);
            //Si no existe, se lanza una excepcion(error controlado)
        }

        detalleVenta.setCodigoDetalleVenta(codigo);
        validarDetalleVenta(detalleVenta);

        //Recalculamos el subtotal por si cambiaron cantidad o precio
        BigDecimal subtotal = detalleVenta.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(detalleVenta.getCantidad()));
        detalleVenta.setSubtotal(subtotal);

        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    public void eliminar(Integer codigo) {
        //Eliminar un detalle de venta
        if(!detalleVentaRepository.existsById(codigo)){
            throw new RuntimeException("El detalle de venta no se encontro con el codigo: " + codigo);
        }
        detalleVentaRepository.deleteById(codigo);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigo(Integer codigo) {
        //verificar si existe el detalle de venta
        return detalleVentaRepository.existsById(codigo);
        //retorna true o false
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> listarPorVenta(Integer codigoVenta) {
        return detalleVentaRepository.findByVentasCodigoVenta(codigoVenta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> listarPorProducto(Integer codigoProducto) {
        return detalleVentaRepository.findByProductosCodigoProducto(codigoProducto);
    }

    //Metodo Privado (solo puede utilizarse dentro de la clase)
    private void validarDetalleVenta(DetalleVenta detalleVenta){
        /*
         * Validaciones del negocio: Este metodo se hara privado porque es algo interno del servicio
         * */
        if(detalleVenta.getVenta() == null){
            throw new IllegalArgumentException("La venta es un dato obligatorio");
        }
        if(detalleVenta.getVenta().getCodigoVenta() == null){
            throw new IllegalArgumentException("El codigo de la venta es un dato obligatorio");
        }
        if(detalleVenta.getProducto() == null){
            throw new IllegalArgumentException("El producto es un dato obligatorio");
        }
        if(detalleVenta.getProducto().getCodigoProducto() == null){
            throw new IllegalArgumentException("El codigo del producto es un dato obligatorio");
        }
        if(detalleVenta.getCantidad() <= 0){
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        if(detalleVenta.getPrecioUnitario() == null){
            throw new IllegalArgumentException("El precio unitario es un dato obligatorio");
        }
        if(detalleVenta.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("El precio unitario debe ser mayor a cero");
        }
    }
}
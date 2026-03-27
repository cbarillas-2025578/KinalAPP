package com.christopherbarillas.KinalApp.service;

import com.christopherbarillas.KinalApp.entity.Producto;
import com.christopherbarillas.KinalApp.entity.Usuario;
import com.christopherbarillas.KinalApp.repository.ProductoRepository;
import com.christopherbarillas.KinalApp.repository.UsuarioRepository;
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
public class ProductoService implements IProductoService {

        /*private: solo accesible dentro de la clase
        ProductoRepository: es el repositorio para acceder a la BD
        Inyeccion de dependecias Spring nos da el repositorio
        */
        private final ProductoRepository productoRepository;

        /*
         * Constructor: Este se ejecuta al crear el objeto
         * Parametros: Spring pasa el repositorio automatiucamente y a esto se le conoce como Inyeccion de Dependemcias
         * Asignamos el repositorio a nuestra variable de clase
         * */

        public ProductoService(ProductoRepository productoRepository) {
            this.productoRepository = productoRepository;
        }
        /*
         * @Override: Indicar que estamos implementando un metodo de la interfaz
         * */
        @Override
        /*
         * readOnly = true: lo que hace es optimizar la consulta, no bloquea la BD
         * */
        @Transactional(readOnly = true)
        public List<Producto> listarTodos() {
            return productoRepository.findAll();
            /*
             * Llama al metodo findAll() del repositorio de Spring Data JPA
             * este metodo hace exactamente el Select * from Clientes
             * */
        }

        @Override
        public Producto guardar(Producto producto) {
            /*
             * Metodo de guardar crea un Producto
             * Aca es donde colocamos la logica del negocio antes de guardar
             * Primero validamos el dato
             * */
            validarProducto(producto);
            if(producto.getEstado() == 0){
                producto.setEstado(1);
            }
            return productoRepository.save(producto);
        }


        @Override
        @Transactional(readOnly = true)
        public Optional<Producto> buscarPorCodigoProducto(String codigo) {
            //Buscar un producto por codigo
            return productoRepository.findById(codigo);
            //Optional nos evita el NullPointerException
        }

        @Override
        public Producto actualizar(String codigo, Producto producto) {
            //Actualiza un producto existente
            if(!productoRepository.existsById(codigo)){
                throw new RuntimeException("Producto no se encontro con codigo" + codigo);
                //Si no existe, se lanza una excepcion(error controlado)
            }

            producto.setCodigoProducto(producto.getCodigoProducto());
            validarProducto(producto);

            return productoRepository.save(producto);
        }

        @Override
        public void eliminar(String codigo) {
            //Eliminar un cliente
            if(!productoRepository.existsById(codigo)){
                throw new RuntimeException("El Producto no se encontro con el codigo" +codigo);

            }
            productoRepository.deleteById(codigo);

        }

        @Override
        @Transactional(readOnly = true)
        public boolean existePorCodigoProducto(String codigo) {
            //verificar si existe el cliente
            return productoRepository.existsById(codigo);
            //retorns true o false
        }

        @Override
        public List<Producto> listarPorActivo() {
            return productoRepository.findByEstado(1);

        }

        //Metodo Privado (solo puede  utilizarse dentro de la clase)
        private void validarProducto(Producto producto){
            /*
             * Validaciones del negocio: Este metodo se hara privado porque es algo interno del servidpr
             * */
            if(producto.getCodigoProducto() == null  || producto.getCodigoProducto().trim().isEmpty()){
                //Si el dpi es null o esta vacio espues de quitar espacios lanza una exception con un mensaje
                throw new IllegalArgumentException("El Codigo es un dato obligatorio");

            }
            if (producto.getNombreProducto() == null  || producto.getNombreProducto().trim().isEmpty()){
                throw  new IllegalArgumentException("El nombre es un dato obligatorio");
            }
            if (producto.getPrecio() == null  || producto.getPrecio().trim().isEmpty()){
                throw  new IllegalArgumentException("El apellido es un dato obligatorio");
            }
        }

    }



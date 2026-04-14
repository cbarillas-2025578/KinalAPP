package com.christopherbarillas.KinalApp.service;

import com.christopherbarillas.KinalApp.entity.Cliente;
import com.christopherbarillas.KinalApp.entity.Usuario;
import com.christopherbarillas.KinalApp.repository.ClienteRepository;
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
public class UsuarioService implements IUsuarioService{

        /*private: solo accesible dentro de la clase
        UsuarioRepository: es el repositorio para acceder a la BD
        Inyeccion de dependecias Spring nos da el repositorio
        */
        private final UsuarioRepository usuarioRepository;

        /*
         * Constructor: Este se ejecuta al crear el objeto
         * Parametros: Spring pasa el repositorio automatiucamente y a esto se le conoce como Inyeccion de Dependemcias
         * Asignamos el repositorio a nuestra variable de clase
         * */

        public UsuarioService(UsuarioRepository usuarioRepository) {
            this.usuarioRepository = usuarioRepository;
        }
        /*
         * @Override: Indicar que estamos implementando un metodo de la interfaz
         * */
        @Override
        /*
         * readOnly = true: lo que hace es optimizar la consulta, no bloquea la BD
         * */
        @Transactional(readOnly = true)
        public List<Usuario> listarTodos() {
            return usuarioRepository.findAll();
            /*
             * Llama al metodo findAll() del repositorio de Spring Data JPA
             * este metodo hace exactamente el Select * from Clientes
             * */
        }

        @Override
        public Usuario guardar(Usuario usuario) {
            /*
             * Metodo de guardar crea un Cliente
             * Aca es donde colocamos la logica del negocio antes de guardar
             * Primero validamos el dato
             * */
            validarUsuario(usuario);
            if(usuario.getEstado() == 0){
                usuario.setEstado(1);
            }
            return usuarioRepository.save(usuario);
        }


    @Override
        @Transactional(readOnly = true)
        public Optional<Usuario> buscarPorCodigo(String codigo) {
            //Buscar un cliente por DPI
            return usuarioRepository.findById(codigo);
            //Optional nos evita el NullPointerException
        }

        @Override
        public Usuario actualizar(String codigo, Usuario usuario) {
            //Actualiza un cliente existente
            if(!usuarioRepository.existsById(codigo)){
                throw new RuntimeException("Usuario no se encontro con codigo" + codigo);
                //Si no existe, se lanza una excepcion(error controlado)
            }

            usuario.setCodigoUsuario(usuario);
            validarUsuario(usuario);

            return usuarioRepository.save(usuario);
        }

        @Override
        public void eliminar(String codigo) {
            //Eliminar un cliente
            if(!usuarioRepository.existsById(codigo)){
                throw new RuntimeException("El Usuario no se encontro con el codigo" +codigo);

            }
            usuarioRepository.deleteById(codigo);

        }

        @Override
        @Transactional(readOnly = true)
        public boolean existePorCodigo(String codigo) {
            //verificar si existe el cliente
            return usuarioRepository.existsById(codigo);
            //retorns true o false
        }

        @Override
        public List<Usuario> listarPorActivo() {
            return usuarioRepository.findByEstado(1);

        }

        //Metodo Privado (solo puede  utilizarse dentro de la clase)
        private void validarUsuario(Usuario usuario){
            /*
             * Validaciones del negocio: Este metodo se hara privado porque es algo interno del servidpr
             * */
            if(usuario.getCodigoUsuario() == null  || usuario.getCodigoUsuario().trim().isEmpty()){
                //Si el dpi es null o esta vacio espues de quitar espacios lanza una exception con un mensaje
                throw new IllegalArgumentException("El Codigo es un dato obligatorio");

            }
            if (usuario.getUsername() == null  || usuario.getUsername().trim().isEmpty()){
                throw  new IllegalArgumentException("El nombre es un dato obligatorio");
            }
            if (usuario.getPassword() == null  || usuario.getPassword().trim().isEmpty()){
                throw  new IllegalArgumentException("El apellido es un dato obligatorio");
            }
        }

    }

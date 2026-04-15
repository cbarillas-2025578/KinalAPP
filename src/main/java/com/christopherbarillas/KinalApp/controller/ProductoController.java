package com.christopherbarillas.KinalApp.controller;

import com.christopherbarillas.KinalApp.entity.Producto;
import com.christopherbarillas.KinalApp.service.IProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Necesario para enviar datos al HTML
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // Cambiado de @RestController a @Controller para manejar vistas HTML
@RequestMapping("/productos") // Usa minúsculas para coincidir con tu navegación
public class ProductoController {

    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }


    // Este método es el que carga la página principal de productos
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "productos/lista"; // Sin .html al final
    }

    // Ruta para mostrar el formulario de crear nuevo
    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return "productos/formulario";
    }

    // Ruta para recibir los datos del formulario y guardar
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("producto") Producto producto) {
        productoService.guardar(producto);
        return "redirect:/productos"; // Redirige a la lista después de guardar
    }

    // Ruta para eliminar
    @GetMapping("/eliminar/{codigo}")
    public String eliminar(@PathVariable String codigo) {
        productoService.eliminar(codigo);
        return "redirect:/productos";
    }

    @GetMapping("/editar/{codigo}")
    public String editar(@PathVariable String codigo, Model model) {
        Producto producto = productoService.buscarPorCodigoProducto(codigo)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        model.addAttribute("producto", producto);
        return "productos/formulario";
    }
}
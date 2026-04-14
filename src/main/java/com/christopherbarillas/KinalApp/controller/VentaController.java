package com.christopherbarillas.KinalApp.controller;

import com.christopherbarillas.KinalApp.entity.Venta;
import com.christopherbarillas.KinalApp.service.IClienteService;
import com.christopherbarillas.KinalApp.service.IProductoService;
import com.christopherbarillas.KinalApp.service.IVentaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    private final IVentaService ventaService;
    private final IProductoService productoService;
    private final IClienteService clienteService;

    public VentaController(IVentaService ventaService, IProductoService productoService, IClienteService clienteService) {
        this.ventaService = ventaService;
        this.productoService = productoService;
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ventas", ventaService.listarTodos());
        return "ventas/lista";
    }

    @GetMapping("/nueva")
    public String nuevaVenta(Model model) {
        model.addAttribute("venta", new Venta());
        // Enviamos listas para llenar los <select> del HTML
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("clientes", clienteService.listarTodos());
        return "ventas/formulario";
    }
}
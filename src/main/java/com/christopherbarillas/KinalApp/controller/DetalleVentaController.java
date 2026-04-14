package com.christopherbarillas.KinalApp.controller;

import com.christopherbarillas.KinalApp.entity.DetalleVenta;
import com.christopherbarillas.KinalApp.service.IDetalleVentaService;
import com.christopherbarillas.KinalApp.service.IProductoService;
import com.christopherbarillas.KinalApp.service.IVentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/detalles")
public class DetalleVentaController {

    private final IDetalleVentaService detalleVentaService;
    private final IVentaService ventaService;
    private final IProductoService productoService;

    public DetalleVentaController(IDetalleVentaService detalleVentaService, IVentaService ventaService, IProductoService productoService) {
        this.detalleVentaService = detalleVentaService;
        this.ventaService = ventaService;
        this.productoService = productoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("detalles", detalleVentaService.listarTodos());
        return "detalles/lista";
    }

    @GetMapping("/nuevo")
    public String formulario(Model model) {
        model.addAttribute("detalle", new DetalleVenta());
        model.addAttribute("ventas", ventaService.listarTodos());
        model.addAttribute("productos", productoService.listarTodos());
        return "detalles/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute DetalleVenta detalle) {
        detalleVentaService.guardar(detalle);
        return "redirect:/detalles";
    }
}
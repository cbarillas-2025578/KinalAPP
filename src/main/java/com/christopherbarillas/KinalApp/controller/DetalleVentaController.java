package com.christopherbarillas.KinalApp.controller;

import com.christopherbarillas.KinalApp.entity.DetalleVenta;
import com.christopherbarillas.KinalApp.entity.Producto;
import com.christopherbarillas.KinalApp.entity.Venta;
import com.christopherbarillas.KinalApp.service.IDetalleVentaService;
import com.christopherbarillas.KinalApp.service.IProductoService;
import com.christopherbarillas.KinalApp.service.IVentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/detalles")
public class DetalleVentaController {

    private final IDetalleVentaService detalleVentaService;
    private final IVentaService ventaService;
    private final IProductoService productoService;

    public DetalleVentaController(IDetalleVentaService detalleVentaService,
                                  IVentaService ventaService,
                                  IProductoService productoService) {
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
        model.addAttribute("ventas", ventaService.listarPorActivo());
        model.addAttribute("productos", productoService.listarPorActivo());
        return "detalles/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam("codigoVenta") Integer codigoVenta,
                          @RequestParam("codigoProducto") String codigoProducto,
                          @RequestParam("cantidad") int cantidad) {

        // Buscar la venta
        Venta venta = ventaService.buscarPorCodigo(codigoVenta)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        // Buscar el producto
        Producto producto = productoService.buscarPorCodigoProducto(codigoProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Crear el detalle
        DetalleVenta detalle = new DetalleVenta();
        detalle.setVenta(venta);
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);

        // Calcular precio unitario y subtotal
        BigDecimal precio = new BigDecimal(producto.getPrecio());
        detalle.setPrecioUnitario(precio);
        detalle.setSubtotal(precio.multiply(new BigDecimal(cantidad)));

        detalleVentaService.guardar(detalle);
        return "redirect:/detalles";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        detalleVentaService.eliminar(id);
        return "redirect:/detalles";
    }
}
package com.christopherbarillas.KinalApp.service;

import com.christopherbarillas.KinalApp.entity.DetalleVenta;
import com.christopherbarillas.KinalApp.repository.DetalleVentaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DetalleVentaService implements IDetalleVentaService {

    private final DetalleVentaRepository detalleVentaRepository;

    public DetalleVentaService(DetalleVentaRepository detalleVentaRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
    }

    @Override
    public List<DetalleVenta> listarTodos() {
        return detalleVentaRepository.findAll();
    }

    @Override
    public DetalleVenta guardar(DetalleVenta detalle) {
        return detalleVentaRepository.save(detalle);
    }

    @Override
    public Optional<DetalleVenta> buscarPorId(Long id) { // Usar Long
        return detalleVentaRepository.findById(id);
    }

    @Override
    public void eliminar(Long id) { // Esta es probablemente tu línea 74
        // Aquí es donde el parámetro 'id' debe ser Long para que el Repositorio lo acepte
        detalleVentaRepository.deleteById(id);
    }

    @Override
    public List<DetalleVenta> listarPorVenta(Integer codigoVenta) {
        return detalleVentaRepository.findAll().stream()
                .filter(d -> d.getVenta() != null && d.getVenta().getCodigoVenta().equals(codigoVenta))
                .toList();
    }
}
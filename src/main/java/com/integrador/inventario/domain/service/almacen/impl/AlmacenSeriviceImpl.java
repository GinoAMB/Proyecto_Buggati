package com.integrador.inventario.domain.service.almacen.impl;

import com.integrador.inventario.domain.exception.almacen.AlmacenDuplicadoException;
import com.integrador.inventario.domain.exception.almacen.AlmacenNoEcontradoException;
import com.integrador.inventario.domain.model.Almacen;
import com.integrador.inventario.domain.model.DetalleAlmacen;
import com.integrador.inventario.domain.repository.AlmacenRepositoryPort;
import com.integrador.inventario.domain.repository.DetalleAlmacenRepositoryPort;
import com.integrador.inventario.domain.service.almacen.AlmacenService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlmacenSeriviceImpl implements AlmacenService {

    private final AlmacenRepositoryPort repository;
    private final DetalleAlmacenRepositoryPort detalleAlmacenRepositoryPort;

    public AlmacenSeriviceImpl(AlmacenRepositoryPort repository, DetalleAlmacenRepositoryPort detalleAlmacenRepositoryPort) {
        this.repository = repository;
        this.detalleAlmacenRepositoryPort = detalleAlmacenRepositoryPort;
    }

    @Override
    public List<Almacen> getAll() {
        List<Almacen> almacenes = repository.getAll();
        // Llenar materiales para cada almacén
        almacenes.forEach(a -> a.setMateriales(detalleAlmacenRepositoryPort.findByAlmacenId(a.getIdAlmacen())));
        return almacenes;
    }

    @Override
    public Optional<Almacen> getById(Integer id) {
        Optional<Almacen> almacenOpt = repository.getById(id);
        almacenOpt.ifPresent(a -> {
            List<DetalleAlmacen> materiales = detalleAlmacenRepositoryPort.findByAlmacenId(a.getIdAlmacen());
            a.setMateriales(materiales);
        });
        return almacenOpt;
    }

    @Override
    public Almacen save(Almacen almacen) {
        boolean exists = repository.getAll().stream()
                .anyMatch(a -> a.getNombre().equalsIgnoreCase(almacen.getNombre()));
        if(exists){
            throw new AlmacenDuplicadoException(almacen.getNombre());
        }
        return repository.save(almacen);
    }

    @Override
    public Almacen update(Integer id, Almacen almacen) {
        Almacen existente = repository.getById(id)
                .orElseThrow(() -> new AlmacenNoEcontradoException(id));
        boolean duplicado = repository.getAll().stream()
                .anyMatch(a -> a.getNombre().equalsIgnoreCase(almacen.getNombre())
                && !a.getIdAlmacen().equals(id));
        if(duplicado){
            throw new AlmacenDuplicadoException(almacen.getNombre());
        }
        return repository.update(id, almacen);
    }

    @Override
    public void updateEstado(Integer id, boolean estado) {
        if(repository.getById(id).isEmpty()){
            throw new AlmacenNoEcontradoException(id);
        }
        repository.updateEstado(id, estado);
    }

    @Override
    public Integer obtenerStockTotal() {
        return detalleAlmacenRepositoryPort.obtenerStockTotal();
    }

    @Override
    public Integer contarMaterialesAgotados() {
        return detalleAlmacenRepositoryPort.contarMaterialesAgotados();
    }

}

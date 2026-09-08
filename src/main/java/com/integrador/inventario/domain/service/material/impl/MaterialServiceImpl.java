package com.integrador.inventario.domain.service.material.impl;

import com.integrador.inventario.domain.exception.material.MaterialDuplicadoException;
import com.integrador.inventario.domain.exception.material.MaterialNoEncontradoException;
import com.integrador.inventario.domain.model.DetalleAlmacen;
import com.integrador.inventario.domain.model.Material;
import com.integrador.inventario.domain.repository.DetalleAlmacenRepositoryPort;
import com.integrador.inventario.domain.repository.MaterialRepositoryPort;
import com.integrador.inventario.domain.service.material.MaterialService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepositoryPort materialRepositoryPort;

    private final DetalleAlmacenRepositoryPort detalleAlmacenRepositoryPort;


    public MaterialServiceImpl(MaterialRepositoryPort materialRepositoryPort, DetalleAlmacenRepositoryPort detalleAlmacenRepositoryPort) {
        this.materialRepositoryPort = materialRepositoryPort;
        this.detalleAlmacenRepositoryPort = detalleAlmacenRepositoryPort;
    }

    @Override
    public List<Material> getAll() {
        return materialRepositoryPort.getAll();
    }

    @Override
    public Optional<Material> getById(Integer id) {
        return materialRepositoryPort.getById(id);
    }

    @Override
    public Material save(Material material) {
        boolean exists = materialRepositoryPort.getAll().stream()
                .anyMatch(m -> m.getNombre().equalsIgnoreCase(material.getNombre()));
        if(exists) {
            throw new MaterialDuplicadoException(material.getNombre());
        }
        return materialRepositoryPort.save(material);
    }

    @Override
    public Material update(Integer id, Material material) {
        Material existente = materialRepositoryPort.getById(id)
                .orElseThrow(() -> new MaterialNoEncontradoException(id));

        boolean duplicado = materialRepositoryPort.getAll().stream()
                .anyMatch(m -> m.getNombre().equalsIgnoreCase(material.getNombre())
                        && !m.getId().equals(id));
        if (duplicado){
            throw new MaterialDuplicadoException(material.getNombre());
        }
        return materialRepositoryPort.update(id, material);
    }

    @Override
    public void cambiarEstado(Integer id, boolean estado) {
        materialRepositoryPort.getById(id)
                .orElseThrow(() -> new MaterialNoEncontradoException(id));

        materialRepositoryPort.updateEstado(id, estado);
    }

    @Override
    public List<DetalleAlmacen> getMaterialesPorAlmacen(Integer idAlmacen) {
        return detalleAlmacenRepositoryPort.findByAlmacenId(idAlmacen);
    }

    @Override
    public List<DetalleAlmacen> findAll() {
        return detalleAlmacenRepositoryPort.findAll();
    }
}

package com.integrador.inventario.domain.service.tipoMaterial.impl;

import com.integrador.inventario.domain.model.TipoMaterial;
import com.integrador.inventario.domain.repository.TipoMaterialRepositoryPort;
import com.integrador.inventario.domain.service.tipoMaterial.TipoMaterialService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoMaterialServiceImpl implements TipoMaterialService {

    private final TipoMaterialRepositoryPort adapter;

    public TipoMaterialServiceImpl(TipoMaterialRepositoryPort adapter) {
        this.adapter = adapter;
    }

    @Override
    public List<TipoMaterial> getAll() {
        return adapter.getAll();
    }

    @Override
    public Optional<TipoMaterial> getById(Integer id) {
        return adapter.getById(id);
    }

    @Override
    public TipoMaterial save(TipoMaterial tipoMaterial) {
        return adapter.save(tipoMaterial);
    }

    @Override
    public TipoMaterial update(Integer id, TipoMaterial tipoMaterial) {
        return adapter.update(id, tipoMaterial);
    }

    @Override
    public void delete(Integer id) {
        adapter.delete(id);
    }
}

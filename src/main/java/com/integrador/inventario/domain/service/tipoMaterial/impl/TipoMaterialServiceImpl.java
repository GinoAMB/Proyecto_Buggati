package com.integrador.inventario.domain.service.tipoMaterial.impl;

import com.integrador.inventario.domain.exception.tipoMaterial.TipoMaterialDuplicadoException;
import com.integrador.inventario.domain.exception.tipoMaterial.TipoMaterialNoEncontradoException;
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
        boolean exists = adapter.getAll().stream()
                .anyMatch(t -> t.getName().equalsIgnoreCase(tipoMaterial.getName()));
        if (exists) {
            throw new TipoMaterialDuplicadoException(tipoMaterial.getName());
        }
        return adapter.save(tipoMaterial);
    }

    @Override
    public TipoMaterial update(Integer id, TipoMaterial tipoMaterial) {
        // Validar existencia
        TipoMaterial existente = adapter.getById(id)
                .orElseThrow(() -> new TipoMaterialNoEncontradoException(id));

        // Validar duplicado (si otro tipo material ya usa el mismo nombre)
        boolean duplicado = adapter.getAll().stream()
                .anyMatch(t -> t.getName().equalsIgnoreCase(tipoMaterial.getName()) && !t.getId().equals(id));

        if (duplicado) {
            throw new TipoMaterialDuplicadoException(tipoMaterial.getName());
        }
        return adapter.update(id, tipoMaterial);
    }

    @Override
    public void delete(Integer id) {
        if (adapter.getById(id).isEmpty()) {
            throw new TipoMaterialNoEncontradoException(id);
        }
        adapter.delete(id);
    }
}

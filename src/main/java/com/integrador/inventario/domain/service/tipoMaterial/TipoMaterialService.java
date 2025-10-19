package com.integrador.inventario.domain.service.tipoMaterial;

import com.integrador.inventario.domain.model.TipoMaterial;

import java.util.List;
import java.util.Optional;

public interface TipoMaterialService {
    List<TipoMaterial> getAll();
    Optional<TipoMaterial> getById(Integer id);
    TipoMaterial save(TipoMaterial tipoMaterial);
    TipoMaterial update(Integer id, TipoMaterial tipoMaterial);
    void delete(Integer id);
}

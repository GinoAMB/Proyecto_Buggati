package com.integrador.inventario.web.almacen.controller;

import com.integrador.inventario.domain.model.TipoMaterial;
import com.integrador.inventario.domain.service.tipoMaterial.TipoMaterialService;
import com.integrador.inventario.web.almacen.dto.TipoMaterialInputDTO;
import com.integrador.inventario.web.almacen.dto.TipoMaterialOutputDTO;
import com.integrador.inventario.web.almacen.mapper.TipoMaterialWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipoMaterial")
public class TipoMaterialController {

    private final TipoMaterialService service;
    private final TipoMaterialWebMapper mapper;

    public TipoMaterialController(TipoMaterialService service, TipoMaterialWebMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<TipoMaterialOutputDTO>> getAll(){
        List<TipoMaterialOutputDTO> result = service.getAll()
                .stream()
                .map(mapper::toOutputDTO)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoMaterialOutputDTO> getByID(@PathVariable Integer id){
        return service.getById(id)
                .map(mapper::toOutputDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoMaterialOutputDTO> create(@Valid @RequestBody TipoMaterialInputDTO inputDTO){
        TipoMaterial saved = service.save(mapper.toDomain(inputDTO));
        return ResponseEntity.ok(mapper.toOutputDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoMaterialOutputDTO> update(@PathVariable Integer id, @Valid @RequestBody TipoMaterialInputDTO inputDTO){
        TipoMaterial update = service.update(id, mapper.toDomain(inputDTO));
        return ResponseEntity.ok(mapper.toOutputDTO(update));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delte(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

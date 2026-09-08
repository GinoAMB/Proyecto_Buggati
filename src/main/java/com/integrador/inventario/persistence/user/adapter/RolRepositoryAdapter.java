package com.integrador.inventario.persistence.user.adapter;

import com.integrador.inventario.domain.model.Rol;
import com.integrador.inventario.domain.repository.RolRepositoryPort;
import com.integrador.inventario.persistence.user.mapper.RolMapper;
import com.integrador.inventario.persistence.user.repository.RolJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class RolRepositoryAdapter implements RolRepositoryPort {

    private final RolJpaRepository repository;
    private final RolMapper mapper;

    public RolRepositoryAdapter(RolJpaRepository repository, RolMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Rol> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(mapper::toRol)
                .collect(Collectors.toList());
    }
}

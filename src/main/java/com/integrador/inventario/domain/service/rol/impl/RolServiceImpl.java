package com.integrador.inventario.domain.service.rol.impl;

import com.integrador.inventario.domain.model.Rol;
import com.integrador.inventario.domain.repository.RolRepositoryPort;
import com.integrador.inventario.domain.service.rol.RolServicice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServiceImpl implements RolServicice {

    private final RolRepositoryPort repositoryPort;

    public RolServiceImpl(RolRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public List<Rol> findAll() {
        return repositoryPort.findAll();
    }
}

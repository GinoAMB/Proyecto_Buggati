package com.integrador.inventario.domain.repository;

import com.integrador.inventario.domain.model.TokenRecuperacion;

import java.util.List;
import java.util.Optional;

public interface TokenRecuperacionRepositoryPort {
    TokenRecuperacion save(TokenRecuperacion token);
    List<TokenRecuperacion> findAll();
    Optional<TokenRecuperacion> findById(Integer id);
    Optional<TokenRecuperacion> findByToken(String token);
    void marcarTokenComoUsado(String token);
}

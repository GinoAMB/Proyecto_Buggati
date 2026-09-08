package com.integrador.inventario.domain.repository;

import com.integrador.inventario.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepositoryPort {
    List<Usuario> findAll();
    Optional<Usuario> findById(Integer id);
    Optional<Usuario> findByCorreo(String correo);
    Usuario save(Usuario usuario);
    void updateEstado(Integer id, boolean estado);
    boolean existsByCorreo(String correo);
    List<Usuario> findAlmacenerosSinAlmacen();
}

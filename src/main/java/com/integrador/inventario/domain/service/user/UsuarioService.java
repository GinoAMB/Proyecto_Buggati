package com.integrador.inventario.domain.service.user;

import com.integrador.inventario.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> getAll();
    Optional<Usuario> getById(Integer id);
    Optional<Usuario> getByCorreo(String correo);
    Usuario create(Usuario usuario);
    Usuario update(Integer id, Usuario usuario);
    void cambiarEstado(Integer id, boolean estado);
    Optional<Usuario> getCurrentUser(Long id);
    List<Usuario> findAlmacenerosSinAlmacen();
}

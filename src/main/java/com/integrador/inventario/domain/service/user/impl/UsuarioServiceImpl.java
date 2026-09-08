package com.integrador.inventario.domain.service.user.impl;

import com.integrador.inventario.domain.exception.usuario.UsuarioDuplicadoException;
import com.integrador.inventario.domain.exception.usuario.UsuarioNoEncontradoException;
import com.integrador.inventario.domain.model.Usuario;
import com.integrador.inventario.domain.repository.UsuarioRepositoryPort;
import com.integrador.inventario.domain.service.user.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepositoryPort repository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioServiceImpl(UsuarioRepositoryPort repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Usuario> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Usuario> getById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Usuario> getByCorreo(String correo) {
        return repository.findByCorreo(correo);
    }

    @Override
    public Usuario create(Usuario usuario) {
        if(repository.existsByCorreo(usuario.getCorreo())){
            throw new UsuarioDuplicadoException(usuario.getCorreo());
        }

        // Encriptar la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return repository.save(usuario);
    }

    @Override
    public Usuario update(Integer id, Usuario usuario) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));

        if (!existing.getCorreo().equals(usuario.getCorreo()) &&
                repository.existsByCorreo(usuario.getCorreo())) {
            throw new UsuarioDuplicadoException(usuario.getCorreo());
        }

        usuario.setIdUsuario(id);

        //Manejar la contraseña opcional
        if (usuario.getPassword() != null && !usuario.getPassword().isBlank()) {
            // Si se envió una contraseña nueva, encriptarla
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        } else {
            // Si no se envió, mantener la contraseña actual
            usuario.setPassword(existing.getPassword());
        }

        return repository.save(usuario);
    }

    @Override
    public void cambiarEstado(Integer id, boolean estado) {
        repository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));

        repository.updateEstado(id, estado);
    }

    @Override
    public Optional<Usuario> getCurrentUser(Long id) {
        return repository.findById(id.intValue());
    }

    @Override
    public List<Usuario> findAlmacenerosSinAlmacen() {
        return repository.findAlmacenerosSinAlmacen();
    }

}

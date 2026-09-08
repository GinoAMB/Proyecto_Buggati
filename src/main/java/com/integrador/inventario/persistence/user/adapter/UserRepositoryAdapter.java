package com.integrador.inventario.persistence.user.adapter;

import com.integrador.inventario.domain.model.Usuario;
import com.integrador.inventario.domain.repository.UsuarioRepositoryPort;
import com.integrador.inventario.persistence.user.mapper.UserMapper;
import com.integrador.inventario.persistence.user.repository.UserJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class UserRepositoryAdapter implements UsuarioRepositoryPort {

    private final UserMapper userMapper;
    private final UserJpaRepository repository;

    public UserRepositoryAdapter(UserMapper userMapper, UserJpaRepository repository) {
        this.userMapper = userMapper;
        this.repository = repository;
    }

    @Override
    public List<Usuario> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(userMapper::toUsuario)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Usuario> findById(Integer id) {
        return repository.findById(id)
                .map(userMapper::toUsuario);
    }

    @Override
    public Optional<Usuario> findByCorreo(String correo) {
        return repository.findByCorreo(correo)
                .map(userMapper::toUsuario);
    }

    @Override
    public Usuario save(Usuario usuario) {
        var entity = userMapper.toUserEntity(usuario);
        var saved = repository.save(entity);
        return userMapper.toUsuario(saved);
    }

    @Override
    public void updateEstado(Integer id, boolean estado) {
        repository.findById(id).ifPresent(entity -> {
            entity.setEstado(estado);
            repository.save(entity);
        });
    }

    @Override
    public boolean existsByCorreo(String correo) {
        return repository.existsByCorreo(correo);
    }

    @Override
    public List<Usuario> findAlmacenerosSinAlmacen() {
        return StreamSupport.stream(repository.findAlmacenerosSinAlmacen().spliterator(), false)
                .map(userMapper::toUsuario)
                .collect(Collectors.toList());
    }
}

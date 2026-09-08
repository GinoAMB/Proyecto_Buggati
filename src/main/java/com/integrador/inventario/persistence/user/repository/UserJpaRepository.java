package com.integrador.inventario.persistence.user.repository;

import com.integrador.inventario.persistence.user.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends CrudRepository<UserEntity, Integer> {
    Optional<UserEntity> findByCorreo(String correo);
    boolean existsByCorreo(String correo);

    @Query("""
        SELECT u FROM UserEntity u
        WHERE u.rol.nombreRol = 'ALMACENERO'
          AND u.idUsuario NOT IN (SELECT a.usuario.idUsuario FROM AlmacenEntity a)
    """)
    List<UserEntity> findAlmacenerosSinAlmacen();
}

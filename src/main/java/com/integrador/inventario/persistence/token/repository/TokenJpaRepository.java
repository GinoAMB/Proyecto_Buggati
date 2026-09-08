package com.integrador.inventario.persistence.token.repository;

import com.integrador.inventario.persistence.token.entity.TokenRecuperacionEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface TokenJpaRepository extends CrudRepository<TokenRecuperacionEntity, Integer> {
    Optional<TokenRecuperacionEntity> findByToken(String token);
}

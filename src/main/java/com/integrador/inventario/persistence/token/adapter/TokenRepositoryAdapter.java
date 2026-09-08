package com.integrador.inventario.persistence.token.adapter;

import com.integrador.inventario.domain.model.TokenRecuperacion;
import com.integrador.inventario.domain.repository.TokenRecuperacionRepositoryPort;
import com.integrador.inventario.persistence.token.entity.TokenRecuperacionEntity;
import com.integrador.inventario.persistence.token.mapper.TokenMapper;
import com.integrador.inventario.persistence.token.repository.TokenJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class TokenRepositoryAdapter implements TokenRecuperacionRepositoryPort {

    private final TokenJpaRepository repository;
    private final TokenMapper tokenMapper;

    public TokenRepositoryAdapter(TokenJpaRepository repository, TokenMapper tokenMapper) {
        this.repository = repository;
        this.tokenMapper = tokenMapper;
    }

    @Override
    public TokenRecuperacion save(TokenRecuperacion token) {
        TokenRecuperacionEntity entity = tokenMapper.toTokenEntity(token);
        TokenRecuperacionEntity saved = repository.save(entity);
        return tokenMapper.toToken(saved);
    }

    @Override
    public List<TokenRecuperacion> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(),false)
                .map(tokenMapper::toToken)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TokenRecuperacion> findById(Integer id) {
        return repository.findById(id).map(tokenMapper::toToken);
    }

    @Override
    public Optional<TokenRecuperacion> findByToken(String token) {
        return repository.findByToken(token).map(tokenMapper::toToken);
    }

    @Override
    public void marcarTokenComoUsado(String token) {
        Optional<TokenRecuperacionEntity> optional = repository.findByToken(token);
        if (optional.isPresent()){
            TokenRecuperacionEntity entity = optional.get();
            entity.setToken(null);
            entity.setUsado(true);
            repository.save(entity);
        }
    }
}

package com.integrador.inventario.domain.service.auth.impl;

import com.integrador.inventario.domain.exception.almacen.AlmacenInactivoException;
import com.integrador.inventario.domain.exception.auth.CredencialesInvalidasException;
import com.integrador.inventario.domain.exception.usuario.UsuarioInactivoException;
import com.integrador.inventario.domain.exception.usuario.UsuarioNoEncontradoException;
import com.integrador.inventario.domain.exception.usuario.UsuarioSinAlmacenAsignadoException;
import com.integrador.inventario.domain.model.Usuario;
import com.integrador.inventario.domain.repository.AlmacenRepositoryPort;
import com.integrador.inventario.domain.repository.UsuarioRepositoryPort;
import com.integrador.inventario.domain.service.auth.AuthService;
import com.integrador.inventario.web.auth.dto.AuthResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepositoryPort repositoryPort;
    private final AlmacenRepositoryPort almacenRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final SecretKey secretKey;

    public AuthServiceImpl(
            UsuarioRepositoryPort repositoryPort,
            AlmacenRepositoryPort almacenRepositoryPort,
            PasswordEncoder passwordEncoder,
            @Value("${jwt.secret}") String jwtSecret
    ) {
        this.repositoryPort = repositoryPort;
        this.almacenRepositoryPort = almacenRepositoryPort;
        this.passwordEncoder = passwordEncoder;

        this.secretKey = Keys.hmacShaKeyFor(
                jwtSecret.getBytes(StandardCharsets.UTF_8)
        );
    }


    @Override
    public AuthResponse login(String correo, String password) {
        Usuario usuario = repositoryPort.findByCorreo(correo)
                .orElseThrow(() -> new UsuarioNoEncontradoException(correo));

        if (!usuario.isEstado()){
            throw new UsuarioInactivoException();
        }

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new CredencialesInvalidasException();
        }

        Integer idAlmacen = null;

        if (usuario.getRol().getNombreRol().equalsIgnoreCase("ALMACENERO")) {
            var almacenOpt = almacenRepositoryPort.findByUsuarioId(usuario.getIdUsuario());

            if (almacenOpt.isEmpty()) {
                throw new UsuarioSinAlmacenAsignadoException();
            }

            var almacen = almacenOpt.get();

            if (almacen.getEstado() == null || !almacen.getEstado()) {
                throw new AlmacenInactivoException();
            }

            idAlmacen = almacen.getIdAlmacen();
        }

        var tokenBuilder = Jwts.builder()
                .setSubject(usuario.getCorreo())
                .claim("id", usuario.getIdUsuario())
                .claim("nombre", usuario.getNombre())
                .claim("role", usuario.getRol().getNombreRol())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000));

        if (idAlmacen != null) {
            tokenBuilder.claim("idAlmacen", idAlmacen);
        }

        String token = tokenBuilder
                .signWith(secretKey)
                .compact();

        return new AuthResponse(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getRol().getNombreRol(),
                token,
                idAlmacen // si es ADMIN irá null
        );
    }
}

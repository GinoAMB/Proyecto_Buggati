package com.integrador.inventario.domain.service.recupePassword.impl;

import com.integrador.inventario.domain.exception.recuperacionPassword.CorreoNoRegistradoException;
import com.integrador.inventario.domain.exception.recuperacionPassword.TokenExpiradoException;
import com.integrador.inventario.domain.exception.recuperacionPassword.TokenInvalidoException;
import com.integrador.inventario.domain.model.TokenRecuperacion;
import com.integrador.inventario.domain.model.Usuario;
import com.integrador.inventario.domain.repository.TokenRecuperacionRepositoryPort;
import com.integrador.inventario.domain.repository.UsuarioRepositoryPort;
import com.integrador.inventario.domain.service.email.EmailService;
import com.integrador.inventario.domain.service.recupePassword.RecuperacionContrasenaService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RecuperacionContrasenaServiceImpl implements RecuperacionContrasenaService {

    private final TokenRecuperacionRepositoryPort tokenRepository;
    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public RecuperacionContrasenaServiceImpl(TokenRecuperacionRepositoryPort tokenRepository, UsuarioRepositoryPort usuarioRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.tokenRepository = tokenRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public TokenRecuperacion generarToken(String correo) {

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new CorreoNoRegistradoException("El correo no esta registrado"));

        String tokenValue = UUID.randomUUID().toString();
        TokenRecuperacion token = new TokenRecuperacion();
        token.setUsuario(usuario);
        token.setToken(tokenValue);
        token.setFechaCreacion(LocalDateTime.now());
        token.setFechaExpiracion(LocalDateTime.now().plusHours(1));
        token.setUsado(false);

        tokenRepository.save(token);


        String asunto = "Recuperacion de contraseña - Sistema Inventario BUGATTI SAC";
        String cuerpo = """
<div style="background-color:#f6f8fa; padding:40px 0; font-family:'Segoe UI', Arial, sans-serif;">
    <div style="max-width:600px; margin:0 auto; background-color:#ffffff; border-radius:8px;
                box-shadow:0 2px 8px rgba(0,0,0,0.1); overflow:hidden;">
        
        <div style="background-color:#004aad; padding:25px; text-align:center;">
            <h1 style="color:#ffffff; font-size:24px; margin:0; letter-spacing:1px;">BUGATTI SAC</h1>
            <p style="color:#e0e0e0; margin-top:5px; font-size:14px;">Recuperación de Contraseña</p>
        </div>
        
        <div style="padding:30px 40px; color:#333;">
            <p style="font-size:16px;">Hola <strong>%s</strong>,</p>
            <p style="font-size:15px; line-height:1.6;">
                Hemos recibido una solicitud para restablecer tu contraseña.
                Copia el siguiente token y pégalo en el formulario de recuperación de contraseña:
            </p>

            <div style="text-align:center; margin:30px 0;">
                <div style="display:inline-block; background-color:#f0f0f0; padding:15px 20px; 
                            font-size:16px; border-radius:5px; border:1px solid #ccc; letter-spacing:1px;">
                    <strong>%s</strong>
                </div>
            </div>

            <p style="font-size:14px; color:#555; text-align:center; margin-top:15px;">
                Este token es válido durante <strong>1 hora</strong>.
            </p>

            <p style="margin-top:25px; font-size:14px;">
                Saludos cordiales,<br>
                <strong>Equipo de Soporte - BUGATTI SAC</strong>
            </p>

            <p style="font-size:13px; color:#999; margin-top:20px; text-align:center;">
                Si no solicitaste este cambio, puedes ignorar este mensaje.
            </p>
        </div>
        
        <div style="background-color:#f0f0f0; text-align:center; padding:15px; font-size:12px; color:#777;">
            © 2025 BUGATTI SAC. Todos los derechos reservados.
        </div>
    </div>
</div>
""".formatted(usuario.getNombre(), tokenValue);
        emailService.enviarCorreo(usuario.getCorreo(), asunto, cuerpo);

        return token;
    }

    @Override
    public boolean validarToken(String token) {
        return tokenRepository.findByToken(token)
                .map(t -> !t.isUsado() && t.getFechaExpiracion().isAfter(LocalDateTime.now()))
                .orElse(false);
    }

    @Override
    public void cambiarPassword(String token, String nuevaContrasena) {
        TokenRecuperacion tokenValue = tokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenInvalidoException("Token inválido"));

        if (tokenValue.isUsado() || tokenValue.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            throw new TokenExpiradoException("El token ha expirado o ya fue usado");
        }

        Usuario usuario = tokenValue.getUsuario();
        String passwordEncriptada = passwordEncoder.encode(nuevaContrasena);
        usuario.setPassword(passwordEncriptada); // aquí normalmente encriptas la contraseña
        usuarioRepository.save(usuario);

        // Marcar token como usado y eliminar valor real
        tokenRepository.marcarTokenComoUsado(token);
    }
}

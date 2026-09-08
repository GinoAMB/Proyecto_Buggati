package com.integrador.inventario.domain.service.email;

public interface EmailService {
    void enviarCorreo(String destinatario, String asunto, String cuerpo);
}

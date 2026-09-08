package com.integrador.inventario.domain.service.cloudinary;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryService {
    Map<String, Object> uploadFile(MultipartFile file);
    Map<String, Object> deleteFile(String publicId);
}

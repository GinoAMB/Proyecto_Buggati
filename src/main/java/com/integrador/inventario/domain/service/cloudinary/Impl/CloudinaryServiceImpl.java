package com.integrador.inventario.domain.service.cloudinary.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.integrador.inventario.domain.exception.cloudinary.ErrorAlEliminarImagenException;
import com.integrador.inventario.domain.exception.cloudinary.ErrorAlSubirImagenException;
import com.integrador.inventario.domain.service.cloudinary.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public Map<String, Object> uploadFile(MultipartFile file) {
        try {
            return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new ErrorAlSubirImagenException(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> deleteFile(String publicId) {
        try {
            return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new ErrorAlEliminarImagenException(e.getMessage());
        }
    }
}

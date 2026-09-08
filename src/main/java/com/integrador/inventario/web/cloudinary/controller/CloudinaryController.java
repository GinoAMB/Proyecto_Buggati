package com.integrador.inventario.web.cloudinary.controller;

import com.integrador.inventario.domain.service.cloudinary.CloudinaryService;
import com.integrador.inventario.web.cloudinary.dto.UploadResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/cloudinary")
public class CloudinaryController {
    private final CloudinaryService cloudinaryService;

    public CloudinaryController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @Operation(
            summary = "Subir imagen a Cloudinary",
            description = "Permite al usuario administrador subir una imagen a Cloudinary. Devuelve la URL segura y el publicId de la imagen."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Imagen subida correctamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "400", description = "Archivo inválido")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadResponseDTO> uploadImage(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = cloudinaryService.uploadFile(file);
        String url = (String) result.get("secure_url");
        String publicId = (String) result.get("public_id");
        return ResponseEntity.ok(new UploadResponseDTO(url, publicId));
    }

    @Operation(
            summary = "Eliminar imagen de Cloudinary",
            description = "Permite al usuario administrador eliminar una imagen de Cloudinary mediante su publicId."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Imagen eliminada correctamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "404", description = "Imagen no encontrada")
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/delete/{publicId}")
    public ResponseEntity<Map<String, Object>> deleteImage(@PathVariable String publicId) {
        Map<String, Object> result = cloudinaryService.deleteFile(publicId);
        return ResponseEntity.ok(result);
    }
}

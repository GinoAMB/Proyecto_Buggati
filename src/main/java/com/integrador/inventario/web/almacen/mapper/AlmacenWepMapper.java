package com.integrador.inventario.web.almacen.mapper;

import com.integrador.inventario.domain.model.Almacen;
import com.integrador.inventario.domain.model.DetalleAlmacen;
import com.integrador.inventario.web.almacen.dto.AlmacenRequestDTO;
import com.integrador.inventario.web.almacen.dto.AlmacenResponseDTO;
import com.integrador.inventario.web.almacen.dto.DetalleAlmacenResumenDTO;
import com.integrador.inventario.web.almacen.dto.UsuarioResumenDTO;
import com.integrador.inventario.web.material.dto.MaterialAlmacenResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AlmacenWepMapper {

    // DTO → Dominio
    @Mapping(target = "idAlmacen", ignore = true)
    @Mapping(target = "usuario.idUsuario", source = "idUsuario")
    @Mapping(target = "materiales", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    Almacen toDomain(AlmacenRequestDTO dto);

    // Dominio → DTO
    @Mapping(target = "usuario", source = "usuario")
    @Mapping(target = "materiales", source = "materiales", qualifiedByName = "materialesToResumenDTO")
    AlmacenResponseDTO toResponseDTO(Almacen almacen);

    // Sub-mapeos
    @Mapping(target = "idUsuario", source = "idUsuario")
    @Mapping(target = "nombreCompleto", source = "nombre")
    @Mapping(target = "email", source = "correo")
    UsuarioResumenDTO usuarioToResumenDTO(com.integrador.inventario.domain.model.Usuario usuario);

    @Mapping(target = "idDetalle", source = "material.id")
    @Mapping(target = "nombreMaterial", source = "material.nombre")
    @Mapping(target = "cantidadDisponible", source = "stockActual")
    DetalleAlmacenResumenDTO detalleToResumenDTO(DetalleAlmacen detalle);

    @Named("materialesToResumenDTO")
    List<DetalleAlmacenResumenDTO> materialesToResumenDTO(List<DetalleAlmacen> detalles);

    @Mapping(target = "idAlmacen", source = "almacen.idAlmacen")
    @Mapping(target = "idMaterial", source = "material.id")
    @Mapping(target = "idTipo", source = "material.tipoMaterial.id")
    @Mapping(target = "nombre", source = "material.nombre")
    @Mapping(target = "descripcion", source = "material.descripcion")
    @Mapping(target = "stockMinimo", source = "material.stockMin")
    @Mapping(target = "imagenUrl", source = "material.imagenUrl")
    @Mapping(target = "publicId", source = "material.publicId")
    @Mapping(target = "estado", source = "material.estado")
    @Mapping(target = "unidadMedida", source = "material.unidadMedida")
    @Mapping(target = "stockActual", source = "stockActual")
    @Mapping(target = "nombreAlmacen", source = "almacen.nombre")
    @Mapping(target = "nombreUsuario", source = "almacen.usuario.nombre")
    @Mapping(target = "nombreTipoMaterial", source = "material.tipoMaterial.name")
    MaterialAlmacenResponseDTO detalleToMaterialAlmacenDTO(DetalleAlmacen detalleAlmacen);


}

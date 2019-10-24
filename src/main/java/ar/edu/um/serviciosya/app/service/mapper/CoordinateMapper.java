package ar.edu.um.serviciosya.app.service.mapper;

import ar.edu.um.serviciosya.app.domain.*;
import ar.edu.um.serviciosya.app.service.dto.CoordinateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Coordinate} and its DTO {@link CoordinateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CoordinateMapper extends EntityMapper<CoordinateDTO, Coordinate> {



    default Coordinate fromId(Long id) {
        if (id == null) {
            return null;
        }
        Coordinate coordinate = new Coordinate();
        coordinate.setId(id);
        return coordinate;
    }
}

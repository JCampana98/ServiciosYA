package ar.edu.um.serviciosya.app.service.mapper;

import ar.edu.um.serviciosya.app.domain.*;
import ar.edu.um.serviciosya.app.service.dto.LocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Location} and its DTO {@link LocationDTO}.
 */
@Mapper(componentModel = "spring", uses = {CoordinateMapper.class})
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {

    @Mapping(source = "coordinate.id", target = "coordinateId")
    LocationDTO toDto(Location location);

    @Mapping(source = "coordinateId", target = "coordinate")
    @Mapping(target = "persons", ignore = true)
    @Mapping(target = "removePersons", ignore = true)
    @Mapping(target = "offerers", ignore = true)
    @Mapping(target = "removeOfferers", ignore = true)
    Location toEntity(LocationDTO locationDTO);

    default Location fromId(Long id) {
        if (id == null) {
            return null;
        }
        Location location = new Location();
        location.setId(id);
        return location;
    }
}

package ar.edu.um.serviciosya.app.service.mapper;

import ar.edu.um.serviciosya.app.domain.*;
import ar.edu.um.serviciosya.app.service.dto.OffererDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Offerer} and its DTO {@link OffererDTO}.
 */
@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface OffererMapper extends EntityMapper<OffererDTO, Offerer> {

    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.streetAddress", target = "locationStreetAddress")
    OffererDTO toDto(Offerer offerer);

    @Mapping(target = "professions", ignore = true)
    @Mapping(target = "removeProfession", ignore = true)
    @Mapping(target = "turns", ignore = true)
    @Mapping(target = "removeTurn", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "removeComment", ignore = true)
    @Mapping(source = "locationId", target = "location")
    Offerer toEntity(OffererDTO offererDTO);

    default Offerer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Offerer offerer = new Offerer();
        offerer.setId(id);
        return offerer;
    }
}

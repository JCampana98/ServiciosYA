package ar.edu.um.serviciosya.app.service.mapper;

import ar.edu.um.serviciosya.app.domain.*;
import ar.edu.um.serviciosya.app.service.dto.ProfessionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Profession} and its DTO {@link ProfessionDTO}.
 */
@Mapper(componentModel = "spring", uses = {OffererMapper.class})
public interface ProfessionMapper extends EntityMapper<ProfessionDTO, Profession> {

    @Mapping(source = "offerer.id", target = "offererId")
    ProfessionDTO toDto(Profession profession);

    @Mapping(source = "offererId", target = "offerer")
    Profession toEntity(ProfessionDTO professionDTO);

    default Profession fromId(Long id) {
        if (id == null) {
            return null;
        }
        Profession profession = new Profession();
        profession.setId(id);
        return profession;
    }
}

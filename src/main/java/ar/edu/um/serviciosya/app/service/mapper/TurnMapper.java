package ar.edu.um.serviciosya.app.service.mapper;

import ar.edu.um.serviciosya.app.domain.*;
import ar.edu.um.serviciosya.app.service.dto.TurnDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Turn} and its DTO {@link TurnDTO}.
 */
@Mapper(componentModel = "spring", uses = {TransactionMapper.class, LocationMapper.class, OffererMapper.class, PersonMapper.class})
public interface TurnMapper extends EntityMapper<TurnDTO, Turn> {

    @Mapping(source = "transaction.id", target = "transactionId")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "offerer.id", target = "offererId")
    @Mapping(source = "person.id", target = "personId")
    TurnDTO toDto(Turn turn);

    @Mapping(source = "transactionId", target = "transaction")
    @Mapping(source = "locationId", target = "location")
    @Mapping(source = "offererId", target = "offerer")
    @Mapping(source = "personId", target = "person")
    Turn toEntity(TurnDTO turnDTO);

    default Turn fromId(Long id) {
        if (id == null) {
            return null;
        }
        Turn turn = new Turn();
        turn.setId(id);
        return turn;
    }
}

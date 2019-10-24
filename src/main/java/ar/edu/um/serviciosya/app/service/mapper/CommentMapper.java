package ar.edu.um.serviciosya.app.service.mapper;

import ar.edu.um.serviciosya.app.domain.*;
import ar.edu.um.serviciosya.app.service.dto.CommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring", uses = {OffererMapper.class, PersonMapper.class})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {

    @Mapping(source = "offerer.id", target = "offererId")
    @Mapping(source = "person.id", target = "personId")
    CommentDTO toDto(Comment comment);

    @Mapping(source = "offererId", target = "offerer")
    @Mapping(source = "personId", target = "person")
    Comment toEntity(CommentDTO commentDTO);

    default Comment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }
}

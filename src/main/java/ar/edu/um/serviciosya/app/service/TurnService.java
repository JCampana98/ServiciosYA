package ar.edu.um.serviciosya.app.service;

import ar.edu.um.serviciosya.app.domain.Turn;
import ar.edu.um.serviciosya.app.repository.TurnRepository;
import ar.edu.um.serviciosya.app.service.dto.TurnDTO;
import ar.edu.um.serviciosya.app.service.mapper.TurnMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Turn}.
 */
@Service
@Transactional
public class TurnService {

    private final Logger log = LoggerFactory.getLogger(TurnService.class);

    private final TurnRepository turnRepository;

    private final TurnMapper turnMapper;

    public TurnService(TurnRepository turnRepository, TurnMapper turnMapper) {
        this.turnRepository = turnRepository;
        this.turnMapper = turnMapper;
    }

    /**
     * Save a turn.
     *
     * @param turnDTO the entity to save.
     * @return the persisted entity.
     */
    public TurnDTO save(TurnDTO turnDTO) {
        log.debug("Request to save Turn : {}", turnDTO);
        Turn turn = turnMapper.toEntity(turnDTO);
        turn = turnRepository.save(turn);
        return turnMapper.toDto(turn);
    }

    /**
     * Get all the turns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TurnDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Turns");
        return turnRepository.findAll(pageable)
            .map(turnMapper::toDto);
    }


    /**
     * Get one turn by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TurnDTO> findOne(Long id) {
        log.debug("Request to get Turn : {}", id);
        return turnRepository.findById(id)
            .map(turnMapper::toDto);
    }

    /**
     * Delete the turn by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Turn : {}", id);
        turnRepository.deleteById(id);
    }
}

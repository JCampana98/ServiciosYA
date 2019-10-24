package ar.edu.um.serviciosya.app.service;

import ar.edu.um.serviciosya.app.domain.Coordinate;
import ar.edu.um.serviciosya.app.repository.CoordinateRepository;
import ar.edu.um.serviciosya.app.service.dto.CoordinateDTO;
import ar.edu.um.serviciosya.app.service.mapper.CoordinateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Coordinate}.
 */
@Service
@Transactional
public class CoordinateService {

    private final Logger log = LoggerFactory.getLogger(CoordinateService.class);

    private final CoordinateRepository coordinateRepository;

    private final CoordinateMapper coordinateMapper;

    public CoordinateService(CoordinateRepository coordinateRepository, CoordinateMapper coordinateMapper) {
        this.coordinateRepository = coordinateRepository;
        this.coordinateMapper = coordinateMapper;
    }

    /**
     * Save a coordinate.
     *
     * @param coordinateDTO the entity to save.
     * @return the persisted entity.
     */
    public CoordinateDTO save(CoordinateDTO coordinateDTO) {
        log.debug("Request to save Coordinate : {}", coordinateDTO);
        Coordinate coordinate = coordinateMapper.toEntity(coordinateDTO);
        coordinate = coordinateRepository.save(coordinate);
        return coordinateMapper.toDto(coordinate);
    }

    /**
     * Get all the coordinates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CoordinateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Coordinates");
        return coordinateRepository.findAll(pageable)
            .map(coordinateMapper::toDto);
    }


    /**
     * Get one coordinate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CoordinateDTO> findOne(Long id) {
        log.debug("Request to get Coordinate : {}", id);
        return coordinateRepository.findById(id)
            .map(coordinateMapper::toDto);
    }

    /**
     * Delete the coordinate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Coordinate : {}", id);
        coordinateRepository.deleteById(id);
    }
}

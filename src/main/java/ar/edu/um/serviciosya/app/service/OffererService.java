package ar.edu.um.serviciosya.app.service;

import ar.edu.um.serviciosya.app.domain.Offerer;
import ar.edu.um.serviciosya.app.repository.OffererRepository;
import ar.edu.um.serviciosya.app.service.dto.OffererDTO;
import ar.edu.um.serviciosya.app.service.mapper.OffererMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Offerer}.
 */
@Service
@Transactional
public class OffererService {

    private final Logger log = LoggerFactory.getLogger(OffererService.class);

    private final OffererRepository offererRepository;

    private final OffererMapper offererMapper;

    public OffererService(OffererRepository offererRepository, OffererMapper offererMapper) {
        this.offererRepository = offererRepository;
        this.offererMapper = offererMapper;
    }

    /**
     * Save a offerer.
     *
     * @param offererDTO the entity to save.
     * @return the persisted entity.
     */
    public OffererDTO save(OffererDTO offererDTO) {
        log.debug("Request to save Offerer : {}", offererDTO);
        Offerer offerer = offererMapper.toEntity(offererDTO);
        offerer = offererRepository.save(offerer);
        return offererMapper.toDto(offerer);
    }

    /**
     * Get all the offerers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OffererDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Offerers");
        return offererRepository.findAll(pageable)
            .map(offererMapper::toDto);
    }


    /**
     * Get one offerer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OffererDTO> findOne(Long id) {
        log.debug("Request to get Offerer : {}", id);
        return offererRepository.findById(id)
            .map(offererMapper::toDto);
    }

    /**
     * Delete the offerer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Offerer : {}", id);
        offererRepository.deleteById(id);
    }
}

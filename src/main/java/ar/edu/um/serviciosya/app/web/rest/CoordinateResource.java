package ar.edu.um.serviciosya.app.web.rest;

import ar.edu.um.serviciosya.app.service.CoordinateService;
import ar.edu.um.serviciosya.app.web.rest.errors.BadRequestAlertException;
import ar.edu.um.serviciosya.app.service.dto.CoordinateDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ar.edu.um.serviciosya.app.domain.Coordinate}.
 */
@RestController
@RequestMapping("/api")
public class CoordinateResource {

    private final Logger log = LoggerFactory.getLogger(CoordinateResource.class);

    private static final String ENTITY_NAME = "coordinate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoordinateService coordinateService;

    public CoordinateResource(CoordinateService coordinateService) {
        this.coordinateService = coordinateService;
    }

    /**
     * {@code POST  /coordinates} : Create a new coordinate.
     *
     * @param coordinateDTO the coordinateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coordinateDTO, or with status {@code 400 (Bad Request)} if the coordinate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/coordinates")
    public ResponseEntity<CoordinateDTO> createCoordinate(@RequestBody CoordinateDTO coordinateDTO) throws URISyntaxException {
        log.debug("REST request to save Coordinate : {}", coordinateDTO);
        if (coordinateDTO.getId() != null) {
            throw new BadRequestAlertException("A new coordinate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoordinateDTO result = coordinateService.save(coordinateDTO);
        return ResponseEntity.created(new URI("/api/coordinates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /coordinates} : Updates an existing coordinate.
     *
     * @param coordinateDTO the coordinateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coordinateDTO,
     * or with status {@code 400 (Bad Request)} if the coordinateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coordinateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/coordinates")
    public ResponseEntity<CoordinateDTO> updateCoordinate(@RequestBody CoordinateDTO coordinateDTO) throws URISyntaxException {
        log.debug("REST request to update Coordinate : {}", coordinateDTO);
        if (coordinateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CoordinateDTO result = coordinateService.save(coordinateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coordinateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /coordinates} : get all the coordinates.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coordinates in body.
     */
    @GetMapping("/coordinates")
    public ResponseEntity<List<CoordinateDTO>> getAllCoordinates(Pageable pageable) {
        log.debug("REST request to get a page of Coordinates");
        Page<CoordinateDTO> page = coordinateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /coordinates/:id} : get the "id" coordinate.
     *
     * @param id the id of the coordinateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coordinateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/coordinates/{id}")
    public ResponseEntity<CoordinateDTO> getCoordinate(@PathVariable Long id) {
        log.debug("REST request to get Coordinate : {}", id);
        Optional<CoordinateDTO> coordinateDTO = coordinateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coordinateDTO);
    }

    /**
     * {@code DELETE  /coordinates/:id} : delete the "id" coordinate.
     *
     * @param id the id of the coordinateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/coordinates/{id}")
    public ResponseEntity<Void> deleteCoordinate(@PathVariable Long id) {
        log.debug("REST request to delete Coordinate : {}", id);
        coordinateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

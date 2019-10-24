package ar.edu.um.serviciosya.app.web.rest;

import ar.edu.um.serviciosya.app.ServiciosYaApp;
import ar.edu.um.serviciosya.app.domain.Coordinate;
import ar.edu.um.serviciosya.app.repository.CoordinateRepository;
import ar.edu.um.serviciosya.app.service.CoordinateService;
import ar.edu.um.serviciosya.app.service.dto.CoordinateDTO;
import ar.edu.um.serviciosya.app.service.mapper.CoordinateMapper;
import ar.edu.um.serviciosya.app.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static ar.edu.um.serviciosya.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CoordinateResource} REST controller.
 */
@SpringBootTest(classes = ServiciosYaApp.class)
public class CoordinateResourceIT {

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    @Autowired
    private CoordinateRepository coordinateRepository;

    @Autowired
    private CoordinateMapper coordinateMapper;

    @Autowired
    private CoordinateService coordinateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCoordinateMockMvc;

    private Coordinate coordinate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoordinateResource coordinateResource = new CoordinateResource(coordinateService);
        this.restCoordinateMockMvc = MockMvcBuilders.standaloneSetup(coordinateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coordinate createEntity(EntityManager em) {
        Coordinate coordinate = new Coordinate()
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        return coordinate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coordinate createUpdatedEntity(EntityManager em) {
        Coordinate coordinate = new Coordinate()
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        return coordinate;
    }

    @BeforeEach
    public void initTest() {
        coordinate = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoordinate() throws Exception {
        int databaseSizeBeforeCreate = coordinateRepository.findAll().size();

        // Create the Coordinate
        CoordinateDTO coordinateDTO = coordinateMapper.toDto(coordinate);
        restCoordinateMockMvc.perform(post("/api/coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordinateDTO)))
            .andExpect(status().isCreated());

        // Validate the Coordinate in the database
        List<Coordinate> coordinateList = coordinateRepository.findAll();
        assertThat(coordinateList).hasSize(databaseSizeBeforeCreate + 1);
        Coordinate testCoordinate = coordinateList.get(coordinateList.size() - 1);
        assertThat(testCoordinate.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testCoordinate.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    public void createCoordinateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coordinateRepository.findAll().size();

        // Create the Coordinate with an existing ID
        coordinate.setId(1L);
        CoordinateDTO coordinateDTO = coordinateMapper.toDto(coordinate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoordinateMockMvc.perform(post("/api/coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordinateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Coordinate in the database
        List<Coordinate> coordinateList = coordinateRepository.findAll();
        assertThat(coordinateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCoordinates() throws Exception {
        // Initialize the database
        coordinateRepository.saveAndFlush(coordinate);

        // Get all the coordinateList
        restCoordinateMockMvc.perform(get("/api/coordinates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coordinate.getId().intValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.toString())));
    }
    
    @Test
    @Transactional
    public void getCoordinate() throws Exception {
        // Initialize the database
        coordinateRepository.saveAndFlush(coordinate);

        // Get the coordinate
        restCoordinateMockMvc.perform(get("/api/coordinates/{id}", coordinate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coordinate.getId().intValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCoordinate() throws Exception {
        // Get the coordinate
        restCoordinateMockMvc.perform(get("/api/coordinates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoordinate() throws Exception {
        // Initialize the database
        coordinateRepository.saveAndFlush(coordinate);

        int databaseSizeBeforeUpdate = coordinateRepository.findAll().size();

        // Update the coordinate
        Coordinate updatedCoordinate = coordinateRepository.findById(coordinate.getId()).get();
        // Disconnect from session so that the updates on updatedCoordinate are not directly saved in db
        em.detach(updatedCoordinate);
        updatedCoordinate
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        CoordinateDTO coordinateDTO = coordinateMapper.toDto(updatedCoordinate);

        restCoordinateMockMvc.perform(put("/api/coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordinateDTO)))
            .andExpect(status().isOk());

        // Validate the Coordinate in the database
        List<Coordinate> coordinateList = coordinateRepository.findAll();
        assertThat(coordinateList).hasSize(databaseSizeBeforeUpdate);
        Coordinate testCoordinate = coordinateList.get(coordinateList.size() - 1);
        assertThat(testCoordinate.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testCoordinate.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingCoordinate() throws Exception {
        int databaseSizeBeforeUpdate = coordinateRepository.findAll().size();

        // Create the Coordinate
        CoordinateDTO coordinateDTO = coordinateMapper.toDto(coordinate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoordinateMockMvc.perform(put("/api/coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordinateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Coordinate in the database
        List<Coordinate> coordinateList = coordinateRepository.findAll();
        assertThat(coordinateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCoordinate() throws Exception {
        // Initialize the database
        coordinateRepository.saveAndFlush(coordinate);

        int databaseSizeBeforeDelete = coordinateRepository.findAll().size();

        // Delete the coordinate
        restCoordinateMockMvc.perform(delete("/api/coordinates/{id}", coordinate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Coordinate> coordinateList = coordinateRepository.findAll();
        assertThat(coordinateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coordinate.class);
        Coordinate coordinate1 = new Coordinate();
        coordinate1.setId(1L);
        Coordinate coordinate2 = new Coordinate();
        coordinate2.setId(coordinate1.getId());
        assertThat(coordinate1).isEqualTo(coordinate2);
        coordinate2.setId(2L);
        assertThat(coordinate1).isNotEqualTo(coordinate2);
        coordinate1.setId(null);
        assertThat(coordinate1).isNotEqualTo(coordinate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoordinateDTO.class);
        CoordinateDTO coordinateDTO1 = new CoordinateDTO();
        coordinateDTO1.setId(1L);
        CoordinateDTO coordinateDTO2 = new CoordinateDTO();
        assertThat(coordinateDTO1).isNotEqualTo(coordinateDTO2);
        coordinateDTO2.setId(coordinateDTO1.getId());
        assertThat(coordinateDTO1).isEqualTo(coordinateDTO2);
        coordinateDTO2.setId(2L);
        assertThat(coordinateDTO1).isNotEqualTo(coordinateDTO2);
        coordinateDTO1.setId(null);
        assertThat(coordinateDTO1).isNotEqualTo(coordinateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(coordinateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(coordinateMapper.fromId(null)).isNull();
    }
}

package ar.edu.um.serviciosya.app.web.rest;

import ar.edu.um.serviciosya.app.ServiciosYaApp;
import ar.edu.um.serviciosya.app.domain.Offerer;
import ar.edu.um.serviciosya.app.repository.OffererRepository;
import ar.edu.um.serviciosya.app.service.OffererService;
import ar.edu.um.serviciosya.app.service.dto.OffererDTO;
import ar.edu.um.serviciosya.app.service.mapper.OffererMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static ar.edu.um.serviciosya.app.web.rest.TestUtil.sameInstant;
import static ar.edu.um.serviciosya.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.edu.um.serviciosya.app.domain.enumeration.Gender;
/**
 * Integration tests for the {@link OffererResource} REST controller.
 */
@SpringBootTest(classes = ServiciosYaApp.class)
public class OffererResourceIT {

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final Long DEFAULT_PHONE_NUMBER = 1L;
    private static final Long UPDATED_PHONE_NUMBER = 2L;
    private static final Long SMALLER_PHONE_NUMBER = 1L - 1L;

    private static final ZonedDateTime DEFAULT_BIRTHDAY = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BIRTHDAY = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_BIRTHDAY = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Float DEFAULT_REPUTATION = 1F;
    private static final Float UPDATED_REPUTATION = 2F;
    private static final Float SMALLER_REPUTATION = 1F - 1F;

    @Autowired
    private OffererRepository offererRepository;

    @Autowired
    private OffererMapper offererMapper;

    @Autowired
    private OffererService offererService;

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

    private MockMvc restOffererMockMvc;

    private Offerer offerer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OffererResource offererResource = new OffererResource(offererService);
        this.restOffererMockMvc = MockMvcBuilders.standaloneSetup(offererResource)
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
    public static Offerer createEntity(EntityManager em) {
        Offerer offerer = new Offerer()
            .gender(DEFAULT_GENDER)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .birthday(DEFAULT_BIRTHDAY)
            .reputation(DEFAULT_REPUTATION);
        return offerer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offerer createUpdatedEntity(EntityManager em) {
        Offerer offerer = new Offerer()
            .gender(UPDATED_GENDER)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .birthday(UPDATED_BIRTHDAY)
            .reputation(UPDATED_REPUTATION);
        return offerer;
    }

    @BeforeEach
    public void initTest() {
        offerer = createEntity(em);
    }

    @Test
    @Transactional
    public void createOfferer() throws Exception {
        int databaseSizeBeforeCreate = offererRepository.findAll().size();

        // Create the Offerer
        OffererDTO offererDTO = offererMapper.toDto(offerer);
        restOffererMockMvc.perform(post("/api/offerers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offererDTO)))
            .andExpect(status().isCreated());

        // Validate the Offerer in the database
        List<Offerer> offererList = offererRepository.findAll();
        assertThat(offererList).hasSize(databaseSizeBeforeCreate + 1);
        Offerer testOfferer = offererList.get(offererList.size() - 1);
        assertThat(testOfferer.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testOfferer.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testOfferer.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testOfferer.getReputation()).isEqualTo(DEFAULT_REPUTATION);
    }

    @Test
    @Transactional
    public void createOffererWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offererRepository.findAll().size();

        // Create the Offerer with an existing ID
        offerer.setId(1L);
        OffererDTO offererDTO = offererMapper.toDto(offerer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOffererMockMvc.perform(post("/api/offerers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offererDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Offerer in the database
        List<Offerer> offererList = offererRepository.findAll();
        assertThat(offererList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = offererRepository.findAll().size();
        // set the field null
        offerer.setGender(null);

        // Create the Offerer, which fails.
        OffererDTO offererDTO = offererMapper.toDto(offerer);

        restOffererMockMvc.perform(post("/api/offerers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offererDTO)))
            .andExpect(status().isBadRequest());

        List<Offerer> offererList = offererRepository.findAll();
        assertThat(offererList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = offererRepository.findAll().size();
        // set the field null
        offerer.setPhoneNumber(null);

        // Create the Offerer, which fails.
        OffererDTO offererDTO = offererMapper.toDto(offerer);

        restOffererMockMvc.perform(post("/api/offerers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offererDTO)))
            .andExpect(status().isBadRequest());

        List<Offerer> offererList = offererRepository.findAll();
        assertThat(offererList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthdayIsRequired() throws Exception {
        int databaseSizeBeforeTest = offererRepository.findAll().size();
        // set the field null
        offerer.setBirthday(null);

        // Create the Offerer, which fails.
        OffererDTO offererDTO = offererMapper.toDto(offerer);

        restOffererMockMvc.perform(post("/api/offerers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offererDTO)))
            .andExpect(status().isBadRequest());

        List<Offerer> offererList = offererRepository.findAll();
        assertThat(offererList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOfferers() throws Exception {
        // Initialize the database
        offererRepository.saveAndFlush(offerer);

        // Get all the offererList
        restOffererMockMvc.perform(get("/api/offerers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offerer.getId().intValue())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(sameInstant(DEFAULT_BIRTHDAY))))
            .andExpect(jsonPath("$.[*].reputation").value(hasItem(DEFAULT_REPUTATION.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getOfferer() throws Exception {
        // Initialize the database
        offererRepository.saveAndFlush(offerer);

        // Get the offerer
        restOffererMockMvc.perform(get("/api/offerers/{id}", offerer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(offerer.getId().intValue()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.intValue()))
            .andExpect(jsonPath("$.birthday").value(sameInstant(DEFAULT_BIRTHDAY)))
            .andExpect(jsonPath("$.reputation").value(DEFAULT_REPUTATION.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOfferer() throws Exception {
        // Get the offerer
        restOffererMockMvc.perform(get("/api/offerers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOfferer() throws Exception {
        // Initialize the database
        offererRepository.saveAndFlush(offerer);

        int databaseSizeBeforeUpdate = offererRepository.findAll().size();

        // Update the offerer
        Offerer updatedOfferer = offererRepository.findById(offerer.getId()).get();
        // Disconnect from session so that the updates on updatedOfferer are not directly saved in db
        em.detach(updatedOfferer);
        updatedOfferer
            .gender(UPDATED_GENDER)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .birthday(UPDATED_BIRTHDAY)
            .reputation(UPDATED_REPUTATION);
        OffererDTO offererDTO = offererMapper.toDto(updatedOfferer);

        restOffererMockMvc.perform(put("/api/offerers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offererDTO)))
            .andExpect(status().isOk());

        // Validate the Offerer in the database
        List<Offerer> offererList = offererRepository.findAll();
        assertThat(offererList).hasSize(databaseSizeBeforeUpdate);
        Offerer testOfferer = offererList.get(offererList.size() - 1);
        assertThat(testOfferer.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testOfferer.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testOfferer.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testOfferer.getReputation()).isEqualTo(UPDATED_REPUTATION);
    }

    @Test
    @Transactional
    public void updateNonExistingOfferer() throws Exception {
        int databaseSizeBeforeUpdate = offererRepository.findAll().size();

        // Create the Offerer
        OffererDTO offererDTO = offererMapper.toDto(offerer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOffererMockMvc.perform(put("/api/offerers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offererDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Offerer in the database
        List<Offerer> offererList = offererRepository.findAll();
        assertThat(offererList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOfferer() throws Exception {
        // Initialize the database
        offererRepository.saveAndFlush(offerer);

        int databaseSizeBeforeDelete = offererRepository.findAll().size();

        // Delete the offerer
        restOffererMockMvc.perform(delete("/api/offerers/{id}", offerer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Offerer> offererList = offererRepository.findAll();
        assertThat(offererList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Offerer.class);
        Offerer offerer1 = new Offerer();
        offerer1.setId(1L);
        Offerer offerer2 = new Offerer();
        offerer2.setId(offerer1.getId());
        assertThat(offerer1).isEqualTo(offerer2);
        offerer2.setId(2L);
        assertThat(offerer1).isNotEqualTo(offerer2);
        offerer1.setId(null);
        assertThat(offerer1).isNotEqualTo(offerer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OffererDTO.class);
        OffererDTO offererDTO1 = new OffererDTO();
        offererDTO1.setId(1L);
        OffererDTO offererDTO2 = new OffererDTO();
        assertThat(offererDTO1).isNotEqualTo(offererDTO2);
        offererDTO2.setId(offererDTO1.getId());
        assertThat(offererDTO1).isEqualTo(offererDTO2);
        offererDTO2.setId(2L);
        assertThat(offererDTO1).isNotEqualTo(offererDTO2);
        offererDTO1.setId(null);
        assertThat(offererDTO1).isNotEqualTo(offererDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(offererMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(offererMapper.fromId(null)).isNull();
    }
}

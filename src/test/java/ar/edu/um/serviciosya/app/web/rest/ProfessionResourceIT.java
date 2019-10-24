package ar.edu.um.serviciosya.app.web.rest;

import ar.edu.um.serviciosya.app.ServiciosYaApp;
import ar.edu.um.serviciosya.app.domain.Profession;
import ar.edu.um.serviciosya.app.repository.ProfessionRepository;
import ar.edu.um.serviciosya.app.service.ProfessionService;
import ar.edu.um.serviciosya.app.service.dto.ProfessionDTO;
import ar.edu.um.serviciosya.app.service.mapper.ProfessionMapper;
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
 * Integration tests for the {@link ProfessionResource} REST controller.
 */
@SpringBootTest(classes = ServiciosYaApp.class)
public class ProfessionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROFESSION_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION_DETAIL = "BBBBBBBBBB";

    @Autowired
    private ProfessionRepository professionRepository;

    @Autowired
    private ProfessionMapper professionMapper;

    @Autowired
    private ProfessionService professionService;

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

    private MockMvc restProfessionMockMvc;

    private Profession profession;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfessionResource professionResource = new ProfessionResource(professionService);
        this.restProfessionMockMvc = MockMvcBuilders.standaloneSetup(professionResource)
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
    public static Profession createEntity(EntityManager em) {
        Profession profession = new Profession()
            .name(DEFAULT_NAME)
            .professionDetail(DEFAULT_PROFESSION_DETAIL);
        return profession;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profession createUpdatedEntity(EntityManager em) {
        Profession profession = new Profession()
            .name(UPDATED_NAME)
            .professionDetail(UPDATED_PROFESSION_DETAIL);
        return profession;
    }

    @BeforeEach
    public void initTest() {
        profession = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfession() throws Exception {
        int databaseSizeBeforeCreate = professionRepository.findAll().size();

        // Create the Profession
        ProfessionDTO professionDTO = professionMapper.toDto(profession);
        restProfessionMockMvc.perform(post("/api/professions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionDTO)))
            .andExpect(status().isCreated());

        // Validate the Profession in the database
        List<Profession> professionList = professionRepository.findAll();
        assertThat(professionList).hasSize(databaseSizeBeforeCreate + 1);
        Profession testProfession = professionList.get(professionList.size() - 1);
        assertThat(testProfession.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProfession.getProfessionDetail()).isEqualTo(DEFAULT_PROFESSION_DETAIL);
    }

    @Test
    @Transactional
    public void createProfessionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = professionRepository.findAll().size();

        // Create the Profession with an existing ID
        profession.setId(1L);
        ProfessionDTO professionDTO = professionMapper.toDto(profession);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfessionMockMvc.perform(post("/api/professions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profession in the database
        List<Profession> professionList = professionRepository.findAll();
        assertThat(professionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProfessions() throws Exception {
        // Initialize the database
        professionRepository.saveAndFlush(profession);

        // Get all the professionList
        restProfessionMockMvc.perform(get("/api/professions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profession.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].professionDetail").value(hasItem(DEFAULT_PROFESSION_DETAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getProfession() throws Exception {
        // Initialize the database
        professionRepository.saveAndFlush(profession);

        // Get the profession
        restProfessionMockMvc.perform(get("/api/professions/{id}", profession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profession.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.professionDetail").value(DEFAULT_PROFESSION_DETAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProfession() throws Exception {
        // Get the profession
        restProfessionMockMvc.perform(get("/api/professions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfession() throws Exception {
        // Initialize the database
        professionRepository.saveAndFlush(profession);

        int databaseSizeBeforeUpdate = professionRepository.findAll().size();

        // Update the profession
        Profession updatedProfession = professionRepository.findById(profession.getId()).get();
        // Disconnect from session so that the updates on updatedProfession are not directly saved in db
        em.detach(updatedProfession);
        updatedProfession
            .name(UPDATED_NAME)
            .professionDetail(UPDATED_PROFESSION_DETAIL);
        ProfessionDTO professionDTO = professionMapper.toDto(updatedProfession);

        restProfessionMockMvc.perform(put("/api/professions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionDTO)))
            .andExpect(status().isOk());

        // Validate the Profession in the database
        List<Profession> professionList = professionRepository.findAll();
        assertThat(professionList).hasSize(databaseSizeBeforeUpdate);
        Profession testProfession = professionList.get(professionList.size() - 1);
        assertThat(testProfession.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProfession.getProfessionDetail()).isEqualTo(UPDATED_PROFESSION_DETAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingProfession() throws Exception {
        int databaseSizeBeforeUpdate = professionRepository.findAll().size();

        // Create the Profession
        ProfessionDTO professionDTO = professionMapper.toDto(profession);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfessionMockMvc.perform(put("/api/professions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profession in the database
        List<Profession> professionList = professionRepository.findAll();
        assertThat(professionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfession() throws Exception {
        // Initialize the database
        professionRepository.saveAndFlush(profession);

        int databaseSizeBeforeDelete = professionRepository.findAll().size();

        // Delete the profession
        restProfessionMockMvc.perform(delete("/api/professions/{id}", profession.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profession> professionList = professionRepository.findAll();
        assertThat(professionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profession.class);
        Profession profession1 = new Profession();
        profession1.setId(1L);
        Profession profession2 = new Profession();
        profession2.setId(profession1.getId());
        assertThat(profession1).isEqualTo(profession2);
        profession2.setId(2L);
        assertThat(profession1).isNotEqualTo(profession2);
        profession1.setId(null);
        assertThat(profession1).isNotEqualTo(profession2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfessionDTO.class);
        ProfessionDTO professionDTO1 = new ProfessionDTO();
        professionDTO1.setId(1L);
        ProfessionDTO professionDTO2 = new ProfessionDTO();
        assertThat(professionDTO1).isNotEqualTo(professionDTO2);
        professionDTO2.setId(professionDTO1.getId());
        assertThat(professionDTO1).isEqualTo(professionDTO2);
        professionDTO2.setId(2L);
        assertThat(professionDTO1).isNotEqualTo(professionDTO2);
        professionDTO1.setId(null);
        assertThat(professionDTO1).isNotEqualTo(professionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(professionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(professionMapper.fromId(null)).isNull();
    }
}

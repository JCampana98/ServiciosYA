package ar.edu.um.serviciosya.app.web.rest;

import ar.edu.um.serviciosya.app.ServiciosYaApp;
import ar.edu.um.serviciosya.app.domain.Turn;
import ar.edu.um.serviciosya.app.repository.TurnRepository;
import ar.edu.um.serviciosya.app.service.TurnService;
import ar.edu.um.serviciosya.app.service.dto.TurnDTO;
import ar.edu.um.serviciosya.app.service.mapper.TurnMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
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

import ar.edu.um.serviciosya.app.domain.enumeration.TurnState;
/**
 * Integration tests for the {@link TurnResource} REST controller.
 */
@SpringBootTest(classes = ServiciosYaApp.class)
public class TurnResourceIT {

    private static final ZonedDateTime DEFAULT_ORDER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ORDER_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ORDER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_WORK_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_WORK_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_WORK_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final BigDecimal DEFAULT_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST = new BigDecimal(2);
    private static final BigDecimal SMALLER_COST = new BigDecimal(1 - 1);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AVAILABLE = false;
    private static final Boolean UPDATED_AVAILABLE = true;

    private static final TurnState DEFAULT_TURN_STATE = TurnState.ONWAY;
    private static final TurnState UPDATED_TURN_STATE = TurnState.DELAY;

    @Autowired
    private TurnRepository turnRepository;

    @Autowired
    private TurnMapper turnMapper;

    @Autowired
    private TurnService turnService;

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

    private MockMvc restTurnMockMvc;

    private Turn turn;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TurnResource turnResource = new TurnResource(turnService);
        this.restTurnMockMvc = MockMvcBuilders.standaloneSetup(turnResource)
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
    public static Turn createEntity(EntityManager em) {
        Turn turn = new Turn()
            .orderDate(DEFAULT_ORDER_DATE)
            .workDate(DEFAULT_WORK_DATE)
            .cost(DEFAULT_COST)
            .description(DEFAULT_DESCRIPTION)
            .available(DEFAULT_AVAILABLE)
            .turnState(DEFAULT_TURN_STATE);
        return turn;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Turn createUpdatedEntity(EntityManager em) {
        Turn turn = new Turn()
            .orderDate(UPDATED_ORDER_DATE)
            .workDate(UPDATED_WORK_DATE)
            .cost(UPDATED_COST)
            .description(UPDATED_DESCRIPTION)
            .available(UPDATED_AVAILABLE)
            .turnState(UPDATED_TURN_STATE);
        return turn;
    }

    @BeforeEach
    public void initTest() {
        turn = createEntity(em);
    }

    @Test
    @Transactional
    public void createTurn() throws Exception {
        int databaseSizeBeforeCreate = turnRepository.findAll().size();

        // Create the Turn
        TurnDTO turnDTO = turnMapper.toDto(turn);
        restTurnMockMvc.perform(post("/api/turns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnDTO)))
            .andExpect(status().isCreated());

        // Validate the Turn in the database
        List<Turn> turnList = turnRepository.findAll();
        assertThat(turnList).hasSize(databaseSizeBeforeCreate + 1);
        Turn testTurn = turnList.get(turnList.size() - 1);
        assertThat(testTurn.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testTurn.getWorkDate()).isEqualTo(DEFAULT_WORK_DATE);
        assertThat(testTurn.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testTurn.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTurn.isAvailable()).isEqualTo(DEFAULT_AVAILABLE);
        assertThat(testTurn.getTurnState()).isEqualTo(DEFAULT_TURN_STATE);
    }

    @Test
    @Transactional
    public void createTurnWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = turnRepository.findAll().size();

        // Create the Turn with an existing ID
        turn.setId(1L);
        TurnDTO turnDTO = turnMapper.toDto(turn);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTurnMockMvc.perform(post("/api/turns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Turn in the database
        List<Turn> turnList = turnRepository.findAll();
        assertThat(turnList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTurns() throws Exception {
        // Initialize the database
        turnRepository.saveAndFlush(turn);

        // Get all the turnList
        restTurnMockMvc.perform(get("/api/turns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(turn.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(sameInstant(DEFAULT_ORDER_DATE))))
            .andExpect(jsonPath("$.[*].workDate").value(hasItem(sameInstant(DEFAULT_WORK_DATE))))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].turnState").value(hasItem(DEFAULT_TURN_STATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTurn() throws Exception {
        // Initialize the database
        turnRepository.saveAndFlush(turn);

        // Get the turn
        restTurnMockMvc.perform(get("/api/turns/{id}", turn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(turn.getId().intValue()))
            .andExpect(jsonPath("$.orderDate").value(sameInstant(DEFAULT_ORDER_DATE)))
            .andExpect(jsonPath("$.workDate").value(sameInstant(DEFAULT_WORK_DATE)))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.available").value(DEFAULT_AVAILABLE.booleanValue()))
            .andExpect(jsonPath("$.turnState").value(DEFAULT_TURN_STATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTurn() throws Exception {
        // Get the turn
        restTurnMockMvc.perform(get("/api/turns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTurn() throws Exception {
        // Initialize the database
        turnRepository.saveAndFlush(turn);

        int databaseSizeBeforeUpdate = turnRepository.findAll().size();

        // Update the turn
        Turn updatedTurn = turnRepository.findById(turn.getId()).get();
        // Disconnect from session so that the updates on updatedTurn are not directly saved in db
        em.detach(updatedTurn);
        updatedTurn
            .orderDate(UPDATED_ORDER_DATE)
            .workDate(UPDATED_WORK_DATE)
            .cost(UPDATED_COST)
            .description(UPDATED_DESCRIPTION)
            .available(UPDATED_AVAILABLE)
            .turnState(UPDATED_TURN_STATE);
        TurnDTO turnDTO = turnMapper.toDto(updatedTurn);

        restTurnMockMvc.perform(put("/api/turns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnDTO)))
            .andExpect(status().isOk());

        // Validate the Turn in the database
        List<Turn> turnList = turnRepository.findAll();
        assertThat(turnList).hasSize(databaseSizeBeforeUpdate);
        Turn testTurn = turnList.get(turnList.size() - 1);
        assertThat(testTurn.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testTurn.getWorkDate()).isEqualTo(UPDATED_WORK_DATE);
        assertThat(testTurn.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testTurn.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTurn.isAvailable()).isEqualTo(UPDATED_AVAILABLE);
        assertThat(testTurn.getTurnState()).isEqualTo(UPDATED_TURN_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTurn() throws Exception {
        int databaseSizeBeforeUpdate = turnRepository.findAll().size();

        // Create the Turn
        TurnDTO turnDTO = turnMapper.toDto(turn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTurnMockMvc.perform(put("/api/turns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Turn in the database
        List<Turn> turnList = turnRepository.findAll();
        assertThat(turnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTurn() throws Exception {
        // Initialize the database
        turnRepository.saveAndFlush(turn);

        int databaseSizeBeforeDelete = turnRepository.findAll().size();

        // Delete the turn
        restTurnMockMvc.perform(delete("/api/turns/{id}", turn.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Turn> turnList = turnRepository.findAll();
        assertThat(turnList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Turn.class);
        Turn turn1 = new Turn();
        turn1.setId(1L);
        Turn turn2 = new Turn();
        turn2.setId(turn1.getId());
        assertThat(turn1).isEqualTo(turn2);
        turn2.setId(2L);
        assertThat(turn1).isNotEqualTo(turn2);
        turn1.setId(null);
        assertThat(turn1).isNotEqualTo(turn2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TurnDTO.class);
        TurnDTO turnDTO1 = new TurnDTO();
        turnDTO1.setId(1L);
        TurnDTO turnDTO2 = new TurnDTO();
        assertThat(turnDTO1).isNotEqualTo(turnDTO2);
        turnDTO2.setId(turnDTO1.getId());
        assertThat(turnDTO1).isEqualTo(turnDTO2);
        turnDTO2.setId(2L);
        assertThat(turnDTO1).isNotEqualTo(turnDTO2);
        turnDTO1.setId(null);
        assertThat(turnDTO1).isNotEqualTo(turnDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(turnMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(turnMapper.fromId(null)).isNull();
    }
}

package com.davidfacsko.web.rest;

import com.davidfacsko.JhHotelApp;

import com.davidfacsko.domain.Guest;
import com.davidfacsko.repository.GuestRepository;
import com.davidfacsko.service.GuestService;
import com.davidfacsko.service.dto.GuestDTO;
import com.davidfacsko.service.mapper.GuestMapper;
import com.davidfacsko.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.davidfacsko.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GuestResource REST controller.
 *
 * @see GuestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhHotelApp.class)
public class GuestResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private GuestMapper guestMapper;
    
    @Autowired
    private GuestService guestService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGuestMockMvc;

    private Guest guest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GuestResource guestResource = new GuestResource(guestService);
        this.restGuestMockMvc = MockMvcBuilders.standaloneSetup(guestResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Guest createEntity(EntityManager em) {
        Guest guest = new Guest()
            .name(DEFAULT_NAME);
        return guest;
    }

    @Before
    public void initTest() {
        guest = createEntity(em);
    }

    @Test
    @Transactional
    public void createGuest() throws Exception {
        int databaseSizeBeforeCreate = guestRepository.findAll().size();

        // Create the Guest
        GuestDTO guestDTO = guestMapper.toDto(guest);
        restGuestMockMvc.perform(post("/api/guests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(guestDTO)))
            .andExpect(status().isCreated());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeCreate + 1);
        Guest testGuest = guestList.get(guestList.size() - 1);
        assertThat(testGuest.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createGuestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = guestRepository.findAll().size();

        // Create the Guest with an existing ID
        guest.setId(1L);
        GuestDTO guestDTO = guestMapper.toDto(guest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGuestMockMvc.perform(post("/api/guests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(guestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGuests() throws Exception {
        // Initialize the database
        guestRepository.saveAndFlush(guest);

        // Get all the guestList
        restGuestMockMvc.perform(get("/api/guests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guest.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getGuest() throws Exception {
        // Initialize the database
        guestRepository.saveAndFlush(guest);

        // Get the guest
        restGuestMockMvc.perform(get("/api/guests/{id}", guest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(guest.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGuest() throws Exception {
        // Get the guest
        restGuestMockMvc.perform(get("/api/guests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGuest() throws Exception {
        // Initialize the database
        guestRepository.saveAndFlush(guest);

        int databaseSizeBeforeUpdate = guestRepository.findAll().size();

        // Update the guest
        Guest updatedGuest = guestRepository.findById(guest.getId()).get();
        // Disconnect from session so that the updates on updatedGuest are not directly saved in db
        em.detach(updatedGuest);
        updatedGuest
            .name(UPDATED_NAME);
        GuestDTO guestDTO = guestMapper.toDto(updatedGuest);

        restGuestMockMvc.perform(put("/api/guests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(guestDTO)))
            .andExpect(status().isOk());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeUpdate);
        Guest testGuest = guestList.get(guestList.size() - 1);
        assertThat(testGuest.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingGuest() throws Exception {
        int databaseSizeBeforeUpdate = guestRepository.findAll().size();

        // Create the Guest
        GuestDTO guestDTO = guestMapper.toDto(guest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuestMockMvc.perform(put("/api/guests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(guestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGuest() throws Exception {
        // Initialize the database
        guestRepository.saveAndFlush(guest);

        int databaseSizeBeforeDelete = guestRepository.findAll().size();

        // Get the guest
        restGuestMockMvc.perform(delete("/api/guests/{id}", guest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Guest.class);
        Guest guest1 = new Guest();
        guest1.setId(1L);
        Guest guest2 = new Guest();
        guest2.setId(guest1.getId());
        assertThat(guest1).isEqualTo(guest2);
        guest2.setId(2L);
        assertThat(guest1).isNotEqualTo(guest2);
        guest1.setId(null);
        assertThat(guest1).isNotEqualTo(guest2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuestDTO.class);
        GuestDTO guestDTO1 = new GuestDTO();
        guestDTO1.setId(1L);
        GuestDTO guestDTO2 = new GuestDTO();
        assertThat(guestDTO1).isNotEqualTo(guestDTO2);
        guestDTO2.setId(guestDTO1.getId());
        assertThat(guestDTO1).isEqualTo(guestDTO2);
        guestDTO2.setId(2L);
        assertThat(guestDTO1).isNotEqualTo(guestDTO2);
        guestDTO1.setId(null);
        assertThat(guestDTO1).isNotEqualTo(guestDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(guestMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(guestMapper.fromId(null)).isNull();
    }
}

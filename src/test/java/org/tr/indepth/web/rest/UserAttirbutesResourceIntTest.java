package org.tr.indepth.web.rest;

import org.tr.indepth.InDepth2App;
import org.tr.indepth.domain.UserAttirbutes;
import org.tr.indepth.repository.UserAttirbutesRepository;
import org.tr.indepth.service.UserAttirbutesService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the UserAttirbutesResource REST controller.
 *
 * @see UserAttirbutesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = InDepth2App.class)
@WebAppConfiguration
@IntegrationTest
public class UserAttirbutesResourceIntTest {


    private static final BigDecimal DEFAULT_LAT = new BigDecimal(1);
    private static final BigDecimal UPDATED_LAT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LONGITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LONGITUDE = new BigDecimal(2);

    private static final Integer DEFAULT_ACTIVE = 1;
    private static final Integer UPDATED_ACTIVE = 2;

    @Inject
    private UserAttirbutesRepository userAttirbutesRepository;

    @Inject
    private UserAttirbutesService userAttirbutesService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUserAttirbutesMockMvc;

    private UserAttirbutes userAttirbutes;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserAttirbutesResource userAttirbutesResource = new UserAttirbutesResource();
        ReflectionTestUtils.setField(userAttirbutesResource, "userAttirbutesService", userAttirbutesService);
        this.restUserAttirbutesMockMvc = MockMvcBuilders.standaloneSetup(userAttirbutesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        userAttirbutes = new UserAttirbutes();
        userAttirbutes.setLat(DEFAULT_LAT);
        userAttirbutes.setLongitude(DEFAULT_LONGITUDE);
        userAttirbutes.setActive(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createUserAttirbutes() throws Exception {
        int databaseSizeBeforeCreate = userAttirbutesRepository.findAll().size();

        // Create the UserAttirbutes

        restUserAttirbutesMockMvc.perform(post("/api/user-attirbutes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userAttirbutes)))
                .andExpect(status().isCreated());

        // Validate the UserAttirbutes in the database
        List<UserAttirbutes> userAttirbutes = userAttirbutesRepository.findAll();
        assertThat(userAttirbutes).hasSize(databaseSizeBeforeCreate + 1);
        UserAttirbutes testUserAttirbutes = userAttirbutes.get(userAttirbutes.size() - 1);
        assertThat(testUserAttirbutes.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testUserAttirbutes.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testUserAttirbutes.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllUserAttirbutes() throws Exception {
        // Initialize the database
        userAttirbutesRepository.saveAndFlush(userAttirbutes);

        // Get all the userAttirbutes
        restUserAttirbutesMockMvc.perform(get("/api/user-attirbutes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(userAttirbutes.getId().intValue())))
                .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.intValue())))
                .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.intValue())))
                .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)));
    }

    @Test
    @Transactional
    public void getUserAttirbutes() throws Exception {
        // Initialize the database
        userAttirbutesRepository.saveAndFlush(userAttirbutes);

        // Get the userAttirbutes
        restUserAttirbutesMockMvc.perform(get("/api/user-attirbutes/{id}", userAttirbutes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(userAttirbutes.getId().intValue()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.intValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.intValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE));
    }

    @Test
    @Transactional
    public void getNonExistingUserAttirbutes() throws Exception {
        // Get the userAttirbutes
        restUserAttirbutesMockMvc.perform(get("/api/user-attirbutes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAttirbutes() throws Exception {
        // Initialize the database
        userAttirbutesService.save(userAttirbutes);

        int databaseSizeBeforeUpdate = userAttirbutesRepository.findAll().size();

        // Update the userAttirbutes
        UserAttirbutes updatedUserAttirbutes = new UserAttirbutes();
        updatedUserAttirbutes.setId(userAttirbutes.getId());
        updatedUserAttirbutes.setLat(UPDATED_LAT);
        updatedUserAttirbutes.setLongitude(UPDATED_LONGITUDE);
        updatedUserAttirbutes.setActive(UPDATED_ACTIVE);

        restUserAttirbutesMockMvc.perform(put("/api/user-attirbutes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedUserAttirbutes)))
                .andExpect(status().isOk());

        // Validate the UserAttirbutes in the database
        List<UserAttirbutes> userAttirbutes = userAttirbutesRepository.findAll();
        assertThat(userAttirbutes).hasSize(databaseSizeBeforeUpdate);
        UserAttirbutes testUserAttirbutes = userAttirbutes.get(userAttirbutes.size() - 1);
        assertThat(testUserAttirbutes.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testUserAttirbutes.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testUserAttirbutes.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void deleteUserAttirbutes() throws Exception {
        // Initialize the database
        userAttirbutesService.save(userAttirbutes);

        int databaseSizeBeforeDelete = userAttirbutesRepository.findAll().size();

        // Get the userAttirbutes
        restUserAttirbutesMockMvc.perform(delete("/api/user-attirbutes/{id}", userAttirbutes.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserAttirbutes> userAttirbutes = userAttirbutesRepository.findAll();
        assertThat(userAttirbutes).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package org.tr.indepth.web.rest;

import org.tr.indepth.InDepth2App;
import org.tr.indepth.domain.Pairs;
import org.tr.indepth.repository.PairsRepository;
import org.tr.indepth.service.PairsService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PairsResource REST controller.
 *
 * @see PairsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = InDepth2App.class)
@WebAppConfiguration
@IntegrationTest
public class PairsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";

    @Inject
    private PairsRepository pairsRepository;

    @Inject
    private PairsService pairsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPairsMockMvc;

    private Pairs pairs;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PairsResource pairsResource = new PairsResource();
        ReflectionTestUtils.setField(pairsResource, "pairsService", pairsService);
        this.restPairsMockMvc = MockMvcBuilders.standaloneSetup(pairsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pairs = new Pairs();
        pairs.setName(DEFAULT_NAME);
        pairs.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createPairs() throws Exception {
        int databaseSizeBeforeCreate = pairsRepository.findAll().size();

        // Create the Pairs

        restPairsMockMvc.perform(post("/api/pairs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pairs)))
                .andExpect(status().isCreated());

        // Validate the Pairs in the database
        List<Pairs> pairs = pairsRepository.findAll();
        assertThat(pairs).hasSize(databaseSizeBeforeCreate + 1);
        Pairs testPairs = pairs.get(pairs.size() - 1);
        assertThat(testPairs.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPairs.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllPairs() throws Exception {
        // Initialize the database
        pairsRepository.saveAndFlush(pairs);

        // Get all the pairs
        restPairsMockMvc.perform(get("/api/pairs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pairs.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getPairs() throws Exception {
        // Initialize the database
        pairsRepository.saveAndFlush(pairs);

        // Get the pairs
        restPairsMockMvc.perform(get("/api/pairs/{id}", pairs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pairs.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPairs() throws Exception {
        // Get the pairs
        restPairsMockMvc.perform(get("/api/pairs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePairs() throws Exception {
        // Initialize the database
        pairsService.save(pairs);

        int databaseSizeBeforeUpdate = pairsRepository.findAll().size();

        // Update the pairs
        Pairs updatedPairs = new Pairs();
        updatedPairs.setId(pairs.getId());
        updatedPairs.setName(UPDATED_NAME);
        updatedPairs.setValue(UPDATED_VALUE);

        restPairsMockMvc.perform(put("/api/pairs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPairs)))
                .andExpect(status().isOk());

        // Validate the Pairs in the database
        List<Pairs> pairs = pairsRepository.findAll();
        assertThat(pairs).hasSize(databaseSizeBeforeUpdate);
        Pairs testPairs = pairs.get(pairs.size() - 1);
        assertThat(testPairs.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPairs.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deletePairs() throws Exception {
        // Initialize the database
        pairsService.save(pairs);

        int databaseSizeBeforeDelete = pairsRepository.findAll().size();

        // Get the pairs
        restPairsMockMvc.perform(delete("/api/pairs/{id}", pairs.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Pairs> pairs = pairsRepository.findAll();
        assertThat(pairs).hasSize(databaseSizeBeforeDelete - 1);
    }
}

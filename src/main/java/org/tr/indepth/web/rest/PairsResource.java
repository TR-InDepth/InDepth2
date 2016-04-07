package org.tr.indepth.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.tr.indepth.domain.Pairs;
import org.tr.indepth.service.PairsService;
import org.tr.indepth.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Pairs.
 */
@RestController
@RequestMapping("/api")
public class PairsResource {

    private final Logger log = LoggerFactory.getLogger(PairsResource.class);
        
    @Inject
    private PairsService pairsService;
    
    /**
     * POST  /pairs : Create a new pairs.
     *
     * @param pairs the pairs to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pairs, or with status 400 (Bad Request) if the pairs has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/pairs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pairs> createPairs(@RequestBody Pairs pairs) throws URISyntaxException {
        log.debug("REST request to save Pairs : {}", pairs);
        if (pairs.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pairs", "idexists", "A new pairs cannot already have an ID")).body(null);
        }
        Pairs result = pairsService.save(pairs);
        return ResponseEntity.created(new URI("/api/pairs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pairs", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pairs : Updates an existing pairs.
     *
     * @param pairs the pairs to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pairs,
     * or with status 400 (Bad Request) if the pairs is not valid,
     * or with status 500 (Internal Server Error) if the pairs couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/pairs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pairs> updatePairs(@RequestBody Pairs pairs) throws URISyntaxException {
        log.debug("REST request to update Pairs : {}", pairs);
        if (pairs.getId() == null) {
            return createPairs(pairs);
        }
        Pairs result = pairsService.save(pairs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pairs", pairs.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pairs : get all the pairs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pairs in body
     */
    @RequestMapping(value = "/pairs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Pairs> getAllPairs() {
        log.debug("REST request to get all Pairs");
        return pairsService.findAll();
    }

    /**
     * GET  /pairs/:id : get the "id" pairs.
     *
     * @param id the id of the pairs to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pairs, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/pairs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pairs> getPairs(@PathVariable Long id) {
        log.debug("REST request to get Pairs : {}", id);
        Pairs pairs = pairsService.findOne(id);
        return Optional.ofNullable(pairs)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pairs/:id : delete the "id" pairs.
     *
     * @param id the id of the pairs to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/pairs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePairs(@PathVariable Long id) {
        log.debug("REST request to delete Pairs : {}", id);
        pairsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pairs", id.toString())).build();
    }

}

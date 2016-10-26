package org.tr.indepth.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.tr.indepth.domain.UserAttirbutes;
import org.tr.indepth.service.UserAttirbutesService;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing UserAttirbutes.
 */
@RestController
@RequestMapping("/api")
public class UserAttirbutesResource {

    private final Logger log = LoggerFactory.getLogger(UserAttirbutesResource.class);
        
    @Inject
    private UserAttirbutesService userAttirbutesService;
    
    /**
     * POST  /user-attirbutes : Create a new userAttirbutes.
     *
     * @param userAttirbutes the userAttirbutes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userAttirbutes, or with status 400 (Bad Request) if the userAttirbutes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/user-attirbutes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserAttirbutes> createUserAttirbutes(@RequestBody UserAttirbutes userAttirbutes) throws URISyntaxException {
        log.debug("REST request to save UserAttirbutes : {}", userAttirbutes);
        if (userAttirbutes.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userAttirbutes", "idexists", "A new userAttirbutes cannot already have an ID")).body(null);
        }
        UserAttirbutes result = userAttirbutesService.save(userAttirbutes);
        return ResponseEntity.created(new URI("/api/user-attirbutes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userAttirbutes", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-attirbutes : Updates an existing userAttirbutes.
     *
     * @param userAttirbutes the userAttirbutes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userAttirbutes,
     * or with status 400 (Bad Request) if the userAttirbutes is not valid,
     * or with status 500 (Internal Server Error) if the userAttirbutes couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/user-attirbutes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserAttirbutes> updateUserAttirbutes(@RequestBody UserAttirbutes userAttirbutes) throws URISyntaxException {
        log.debug("REST request to update UserAttirbutes : {}", userAttirbutes);
        if (userAttirbutes.getId() == null) {
            return createUserAttirbutes(userAttirbutes);
        }
        UserAttirbutes result = userAttirbutesService.save(userAttirbutes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userAttirbutes", userAttirbutes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-attirbutes : get all the userAttirbutes.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of userAttirbutes in body
     */
    @RequestMapping(value = "/user-attirbutes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UserAttirbutes> getAllUserAttirbutes(@RequestParam(required = false) String filter) {
        if ("user-is-null".equals(filter)) {
            log.debug("REST request to get all UserAttirbutess where user is null");
            return userAttirbutesService.findAllWhereUserIsNull();
        }
        log.debug("REST request to get all UserAttirbutes");
        return userAttirbutesService.findAll();
    }

    /**
     * GET  /user-attirbutes/:id : get the "id" userAttirbutes.
     *
     * @param id the id of the userAttirbutes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userAttirbutes, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/user-attirbutes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserAttirbutes> getUserAttirbutes(@PathVariable Long id) {
        log.debug("REST request to get UserAttirbutes : {}", id);
        UserAttirbutes userAttirbutes = userAttirbutesService.findOne(id);
        return Optional.ofNullable(userAttirbutes)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /user-attirbutes/:id : delete the "id" userAttirbutes.
     *
     * @param id the id of the userAttirbutes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/user-attirbutes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUserAttirbutes(@PathVariable Long id) {
        log.debug("REST request to delete UserAttirbutes : {}", id);
        userAttirbutesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userAttirbutes", id.toString())).build();
    }

}

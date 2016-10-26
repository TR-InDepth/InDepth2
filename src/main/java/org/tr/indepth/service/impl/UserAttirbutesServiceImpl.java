package org.tr.indepth.service.impl;

import org.tr.indepth.service.UserAttirbutesService;
import org.tr.indepth.domain.UserAttirbutes;
import org.tr.indepth.repository.UserAttirbutesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing UserAttirbutes.
 */
@Service
@Transactional
public class UserAttirbutesServiceImpl implements UserAttirbutesService{

    private final Logger log = LoggerFactory.getLogger(UserAttirbutesServiceImpl.class);
    
    @Inject
    private UserAttirbutesRepository userAttirbutesRepository;
    
    /**
     * Save a userAttirbutes.
     * 
     * @param userAttirbutes the entity to save
     * @return the persisted entity
     */
    public UserAttirbutes save(UserAttirbutes userAttirbutes) {
        log.debug("Request to save UserAttirbutes : {}", userAttirbutes);
        UserAttirbutes result = userAttirbutesRepository.save(userAttirbutes);
        return result;
    }

    /**
     *  Get all the userAttirbutes.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<UserAttirbutes> findAll() {
        log.debug("Request to get all UserAttirbutes");
        List<UserAttirbutes> result = userAttirbutesRepository.findAll();
        return result;
    }


    /**
     *  get all the userAttirbutes where User is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<UserAttirbutes> findAllWhereUserIsNull() {
        log.debug("Request to get all userAttirbutes where User is null");
        return StreamSupport
            .stream(userAttirbutesRepository.findAll().spliterator(), false)
            .filter(userAttirbutes -> userAttirbutes.getUser() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get one userAttirbutes by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public UserAttirbutes findOne(Long id) {
        log.debug("Request to get UserAttirbutes : {}", id);
        UserAttirbutes userAttirbutes = userAttirbutesRepository.findOne(id);
        return userAttirbutes;
    }

    /**
     *  Delete the  userAttirbutes by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserAttirbutes : {}", id);
        userAttirbutesRepository.delete(id);
    }
}

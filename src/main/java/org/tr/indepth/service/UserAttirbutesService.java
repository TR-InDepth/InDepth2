package org.tr.indepth.service;

import org.tr.indepth.domain.UserAttirbutes;

import java.util.List;

/**
 * Service Interface for managing UserAttirbutes.
 */
public interface UserAttirbutesService {

    /**
     * Save a userAttirbutes.
     * 
     * @param userAttirbutes the entity to save
     * @return the persisted entity
     */
    UserAttirbutes save(UserAttirbutes userAttirbutes);

    /**
     *  Get all the userAttirbutes.
     *  
     *  @return the list of entities
     */
    List<UserAttirbutes> findAll();
    /**
     *  Get all the userAttirbutes where User is null.
     *  
     *  @return the list of entities
     */
    List<UserAttirbutes> findAllWhereUserIsNull();

    /**
     *  Get the "id" userAttirbutes.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    UserAttirbutes findOne(Long id);

    /**
     *  Delete the "id" userAttirbutes.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}

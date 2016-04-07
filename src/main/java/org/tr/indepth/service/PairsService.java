package org.tr.indepth.service;

import org.tr.indepth.domain.Pairs;

import java.util.List;

/**
 * Service Interface for managing Pairs.
 */
public interface PairsService {

    /**
     * Save a pairs.
     * 
     * @param pairs the entity to save
     * @return the persisted entity
     */
    Pairs save(Pairs pairs);

    /**
     *  Get all the pairs.
     *  
     *  @return the list of entities
     */
    List<Pairs> findAll();

    /**
     *  Get the "id" pairs.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Pairs findOne(Long id);

    /**
     *  Delete the "id" pairs.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}

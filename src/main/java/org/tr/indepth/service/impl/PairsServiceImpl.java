package org.tr.indepth.service.impl;

import org.tr.indepth.service.PairsService;
import org.tr.indepth.domain.Pairs;
import org.tr.indepth.repository.PairsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Pairs.
 */
@Service
@Transactional
public class PairsServiceImpl implements PairsService{

    private final Logger log = LoggerFactory.getLogger(PairsServiceImpl.class);
    
    @Inject
    private PairsRepository pairsRepository;
    
    /**
     * Save a pairs.
     * 
     * @param pairs the entity to save
     * @return the persisted entity
     */
    public Pairs save(Pairs pairs) {
        log.debug("Request to save Pairs : {}", pairs);
        Pairs result = pairsRepository.save(pairs);
        return result;
    }

    /**
     *  Get all the pairs.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Pairs> findAll() {
        log.debug("Request to get all Pairs");
        List<Pairs> result = pairsRepository.findAll();
        return result;
    }

    /**
     *  Get one pairs by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Pairs findOne(Long id) {
        log.debug("Request to get Pairs : {}", id);
        Pairs pairs = pairsRepository.findOne(id);
        return pairs;
    }

    /**
     *  Delete the  pairs by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Pairs : {}", id);
        pairsRepository.delete(id);
    }
}

package org.tr.indepth.repository;

import org.tr.indepth.domain.Pairs;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pairs entity.
 */
public interface PairsRepository extends JpaRepository<Pairs,Long> {

}

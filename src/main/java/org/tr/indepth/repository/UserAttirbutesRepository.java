package org.tr.indepth.repository;

import org.tr.indepth.domain.UserAttirbutes;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserAttirbutes entity.
 */
public interface UserAttirbutesRepository extends JpaRepository<UserAttirbutes,Long> {

}

/**
 * 
 */
package com.platform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.platform.domain.Application;

/**
 * @author shriman-dev
 *
 */
@Repository
public interface ApplicationRepository extends MongoRepository<Application, String> {

	Optional<Application> findByName(String name);

	List<Application> findAllWithLimit(Pageable page);
}

package com.platform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.platform.domain.Entity;
/**
 * 
 * @author shriman-dev
 *
 */
@Repository
public interface EntityRepository extends MongoRepository<Entity, String> {

}

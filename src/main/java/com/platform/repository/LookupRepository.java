package com.platform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.platform.domain.Lookup;
/**
 * 
 * @author shriman-dev
 *
 */
@Repository
public interface LookupRepository extends MongoRepository<Lookup, String> {

}

package com.platform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.platform.domain.Holiday;
/**
 * 
 * @author shriman-dev
 *
 */
@Repository
public interface HolidayRepository extends MongoRepository<Holiday, String> {

}

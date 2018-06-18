/**
 * 
 */
package com.platform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.platform.domain.SystemConfig;
import org.springframework.stereotype.Repository;

/**
 * @author shriman-dev
 *
 */
@Repository
public interface SystemConfigRepository extends MongoRepository<SystemConfig, String> {

}

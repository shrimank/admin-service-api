/**
 * 
 */
package com.platform.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.platform.domain.Role;

/**
 * @author shriman-dev
 *
 */
@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

	List<Role> findAllWithLimit(Pageable page);
}

package com.platform.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.platform.domain.Menu;

/**
 * 
 * @author shriman-dev
 *
 */
@Repository
public interface MenuRepository extends MongoRepository<Menu, String> {
	List<Menu> findAllWithLimit(Pageable page);
}

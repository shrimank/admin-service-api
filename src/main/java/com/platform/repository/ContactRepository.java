/**
 * 
 */
package com.platform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.platform.domain.Contact;

/**
 * @author shriman-dev
 *
 */
@Repository
public interface ContactRepository extends MongoRepository<Contact, String> {

}

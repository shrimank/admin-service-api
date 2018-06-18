/**
 * 
 */
package com.platform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.platform.domain.User;
import com.platform.enums.ActivationStatus;
import com.platform.enums.ProcessingStatus;

/**
 * @author shriman-dev
 *
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

	/**
	 * 
	 * @param userName
	 * @param userPassword
	 * @param app
	 * @param tenantId
	 * @param enabledFlag
	 * @param activationStatus
	 * @param processingStatus
	 * @return
	 */
	Optional<User> findByUserNameAndUserPasswordAndApplicationNameAndTenantIdAndEnabledFlagAndActivationStatusAndProcessingStatus(
			String userName, String userPassword, String app, String tenantId, Character enabledFlag,
			ActivationStatus activationStatus, ProcessingStatus processingStatus);

	List<User> findAllWithLimit(Pageable page);
}

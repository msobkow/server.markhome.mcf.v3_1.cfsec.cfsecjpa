// Description: Java 25 Spring JPA Service for SecUser

/*
 *	server.markhome.mcf.CFSec
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal 3.1 CFSec - Security Services
 *	
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow mark.sobkow@gmail.com
 *	
 *	These files are part of Mark's Code Fractal CFSec.
 *	
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *	
 *	http://www.apache.org/licenses/LICENSE-2.0
 *	
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 *	
 */

package server.markhome.mcf.v3_1.cfsec.cfsec.jpa;

import java.io.Serializable;
import java.math.*;
import java.time.*;
import java.util.*;
import jakarta.persistence.*;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.xml.CFLibXmlUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;

/**
 *	Service for the CFSecSecUser entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecSecUserRepository to access them.
 */
@Service("cfsec31JpaSecUserService")
public class CFSecJpaSecUserService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaSecUserRepository cfsec31SecUserRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaSecUser, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUser create(CFSecJpaSecUser data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		CFLibDbKeyHash256 originalRequiredSecUserId = data.getRequiredSecUserId();
		boolean generatedRequiredSecUserId = false;
		if(data.getRequiredLoginId() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredLoginId");
		}
		if(data.getRequiredDfltSysGrpName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredDfltSysGrpName");
		}
		if(data.getRequiredDfltClusGrpName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredDfltClusGrpName");
		}
		if(data.getRequiredDfltTentGrpName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredDfltTentGrpName");
		}
		if(data.getRequiredEMailAddress() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredEMailAddress");
		}
		if(data.getRequiredPasswordHash() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredPasswordHash");
		}
		try {
			if (data.getRequiredSecUserId() == null || data.getRequiredSecUserId().isNull()) {
				data.setRequiredSecUserId(new CFLibDbKeyHash256(0));
				generatedRequiredSecUserId = true;
			}
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31SecUserRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFSecJpaSecUser)(cfsec31SecUserRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfsec31SecUserRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredSecUserId) {
					data.setRequiredSecUserId(originalRequiredSecUserId);
				}
			throw new CFLibDbException(getClass(),
				S_ProcName,
				ex);
		}
	}

	/**
	 *	Update an existing entity.
	 *
	 *		@param	data	The entity to be updated.
	 *
	 *		@return The updated entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUser update(CFSecJpaSecUser data) {
		final String S_ProcName = "update";
		if (data == null) {
			return( null );
		}
		if (data.getPKey() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.getPKey()");
		}
		if(data.getRequiredLoginId() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredLoginId");
		}
		if(data.getRequiredDfltSysGrpName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredDfltSysGrpName");
		}
		if(data.getRequiredDfltClusGrpName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredDfltClusGrpName");
		}
		if(data.getRequiredDfltTentGrpName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredDfltTentGrpName");
		}
		if(data.getRequiredEMailAddress() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredEMailAddress");
		}
		if(data.getRequiredPasswordHash() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredPasswordHash");
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaSecUser existing = cfsec31SecUserRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecSecUser to existing object
		// Apply data columns of CFSecSecUser to existing object
		existing.setRequiredLoginId(data.getRequiredLoginId());
		existing.setRequiredDfltSysGrpName(data.getRequiredDfltSysGrpName());
		existing.setRequiredDfltClusGrpName(data.getRequiredDfltClusGrpName());
		existing.setRequiredDfltTentGrpName(data.getRequiredDfltTentGrpName());
		existing.setRequiredEMailAddress(data.getRequiredEMailAddress());
		existing.setOptionalEMailConfirmUuid6(data.getOptionalEMailConfirmUuid6());
		existing.setRequiredPasswordHash(data.getRequiredPasswordHash());
		existing.setOptionalPasswordResetUuid6(data.getOptionalPasswordResetUuid6());
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31SecUserRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUser find(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecUserRepository.get(requiredSecUserId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUser> findAll() {
		return( cfsec31SecUserRepository.findAll() );
	}

	// CFSecSecUser specified index finders

	/**
	 *	Find an entity using the columns of the ICFSecSecUserByULoginIdxKey as arguments.
	 *
	 *		@param requiredLoginId
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUser findByULoginIdx(@Param("loginId") String requiredLoginId) {
		return( cfsec31SecUserRepository.findByULoginIdx(requiredLoginId));
	}

	/**
	 *	ICFSecSecUserByULoginIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecUserByULoginIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUser findByULoginIdx(ICFSecSecUserByULoginIdxKey key) {
		return( cfsec31SecUserRepository.findByULoginIdx(key.getRequiredLoginId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecUserByEMConfIdxKey as arguments.
	 *
	 *		@param optionalEMailConfirmUuid6
	 *
	 *		@return List&lt;CFSecJpaSecUser&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUser> findByEMConfIdx(@Param("eMailConfirmUuid6") CFLibUuid6 optionalEMailConfirmUuid6) {
		return( cfsec31SecUserRepository.findByEMConfIdx(optionalEMailConfirmUuid6));
	}

	/**
	 *	ICFSecSecUserByEMConfIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecUserByEMConfIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUser> findByEMConfIdx(ICFSecSecUserByEMConfIdxKey key) {
		return( cfsec31SecUserRepository.findByEMConfIdx(key.getOptionalEMailConfirmUuid6()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecUserByPwdResetIdxKey as arguments.
	 *
	 *		@param optionalPasswordResetUuid6
	 *
	 *		@return List&lt;CFSecJpaSecUser&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUser> findByPwdResetIdx(@Param("passwordResetUuid6") CFLibUuid6 optionalPasswordResetUuid6) {
		return( cfsec31SecUserRepository.findByPwdResetIdx(optionalPasswordResetUuid6));
	}

	/**
	 *	ICFSecSecUserByPwdResetIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecUserByPwdResetIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUser> findByPwdResetIdx(ICFSecSecUserByPwdResetIdxKey key) {
		return( cfsec31SecUserRepository.findByPwdResetIdx(key.getOptionalPasswordResetUuid6()));
	}

	// CFSecSecUser specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUser lockByIdIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecUserRepository.lockByIdIdx(requiredSecUserId));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredLoginId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUser lockByULoginIdx(@Param("loginId") String requiredLoginId) {
		return( cfsec31SecUserRepository.lockByULoginIdx(requiredLoginId));
	}

	/**
	 *	ICFSecSecUserByULoginIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUser lockByULoginIdx(ICFSecSecUserByULoginIdxKey key) {
		return( cfsec31SecUserRepository.lockByULoginIdx(key.getRequiredLoginId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param optionalEMailConfirmUuid6
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUser> lockByEMConfIdx(@Param("eMailConfirmUuid6") CFLibUuid6 optionalEMailConfirmUuid6) {
		return( cfsec31SecUserRepository.lockByEMConfIdx(optionalEMailConfirmUuid6));
	}

	/**
	 *	ICFSecSecUserByEMConfIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUser> lockByEMConfIdx(ICFSecSecUserByEMConfIdxKey key) {
		return( cfsec31SecUserRepository.lockByEMConfIdx(key.getOptionalEMailConfirmUuid6()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param optionalPasswordResetUuid6
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUser> lockByPwdResetIdx(@Param("passwordResetUuid6") CFLibUuid6 optionalPasswordResetUuid6) {
		return( cfsec31SecUserRepository.lockByPwdResetIdx(optionalPasswordResetUuid6));
	}

	/**
	 *	ICFSecSecUserByPwdResetIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUser> lockByPwdResetIdx(ICFSecSecUserByPwdResetIdxKey key) {
		return( cfsec31SecUserRepository.lockByPwdResetIdx(key.getOptionalPasswordResetUuid6()));
	}

	// CFSecSecUser specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		cfsec31SecUserRepository.deleteByIdIdx(requiredSecUserId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredLoginId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByULoginIdx(@Param("loginId") String requiredLoginId) {
		cfsec31SecUserRepository.deleteByULoginIdx(requiredLoginId);
	}

	/**
	 *	ICFSecSecUserByULoginIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecUserByULoginIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByULoginIdx(ICFSecSecUserByULoginIdxKey key) {
		cfsec31SecUserRepository.deleteByULoginIdx(key.getRequiredLoginId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param optionalEMailConfirmUuid6
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByEMConfIdx(@Param("eMailConfirmUuid6") CFLibUuid6 optionalEMailConfirmUuid6) {
		cfsec31SecUserRepository.deleteByEMConfIdx(optionalEMailConfirmUuid6);
	}

	/**
	 *	ICFSecSecUserByEMConfIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecUserByEMConfIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByEMConfIdx(ICFSecSecUserByEMConfIdxKey key) {
		cfsec31SecUserRepository.deleteByEMConfIdx(key.getOptionalEMailConfirmUuid6());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param optionalPasswordResetUuid6
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByPwdResetIdx(@Param("passwordResetUuid6") CFLibUuid6 optionalPasswordResetUuid6) {
		cfsec31SecUserRepository.deleteByPwdResetIdx(optionalPasswordResetUuid6);
	}

	/**
	 *	ICFSecSecUserByPwdResetIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecUserByPwdResetIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByPwdResetIdx(ICFSecSecUserByPwdResetIdxKey key) {
		cfsec31SecUserRepository.deleteByPwdResetIdx(key.getOptionalPasswordResetUuid6());
	}
}

// Description: Java 25 Spring JPA Service for TSecGroup

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
 *	Service for the CFSecTSecGroup entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecTSecGroupRepository to access them.
 */
@Service("cfsec31JpaTSecGroupService")
public class CFSecJpaTSecGroupService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaTSecGroupRepository cfsec31TSecGroupRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaTSecGroup, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGroup create(CFSecJpaTSecGroup data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		CFLibDbKeyHash256 originalRequiredTSecGroupId = data.getRequiredTSecGroupId();
		boolean generatedRequiredTSecGroupId = false;
		if(data.getRequiredTenantId() == null || data.getRequiredTenantId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredTenantId");
		}
		if(data.getRequiredName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredName");
		}
		try {
			if (data.getRequiredTSecGroupId() == null || data.getRequiredTSecGroupId().isNull()) {
				data.setRequiredTSecGroupId(new CFLibDbKeyHash256(0));
				generatedRequiredTSecGroupId = true;
			}
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31TSecGroupRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFSecJpaTSecGroup)(cfsec31TSecGroupRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfsec31TSecGroupRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredTSecGroupId) {
					data.setRequiredTSecGroupId(originalRequiredTSecGroupId);
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
	public CFSecJpaTSecGroup update(CFSecJpaTSecGroup data) {
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
		if(data.getRequiredTenantId() == null || data.getRequiredTenantId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredTenantId");
		}
		if(data.getRequiredName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredName");
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaTSecGroup existing = cfsec31TSecGroupRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecTSecGroup to existing object
		existing.setRequiredContainerTenant(data.getRequiredContainerTenant());
		// Apply data columns of CFSecTSecGroup to existing object
		existing.setRequiredName(data.getRequiredName());
		existing.setRequiredIsVisible(data.getRequiredIsVisible());
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31TSecGroupRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredTSecGroupId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGroup find(@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId) {
		return( cfsec31TSecGroupRepository.get(requiredTSecGroupId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGroup> findAll() {
		return( cfsec31TSecGroupRepository.findAll() );
	}

	// CFSecTSecGroup specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecTSecGroupByTenantIdxKey as arguments.
	 *
	 *		@param requiredTenantId
	 *
	 *		@return List&lt;CFSecJpaTSecGroup&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGroup> findByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId) {
		return( cfsec31TSecGroupRepository.findByTenantIdx(requiredTenantId));
	}

	/**
	 *	ICFSecTSecGroupByTenantIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecTSecGroupByTenantIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGroup> findByTenantIdx(ICFSecTSecGroupByTenantIdxKey key) {
		return( cfsec31TSecGroupRepository.findByTenantIdx(key.getRequiredTenantId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecTSecGroupByTenantVisIdxKey as arguments.
	 *
	 *		@param requiredTenantId
	 *		@param requiredIsVisible
	 *
	 *		@return List&lt;CFSecJpaTSecGroup&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGroup> findByTenantVisIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("isVisible") boolean requiredIsVisible) {
		return( cfsec31TSecGroupRepository.findByTenantVisIdx(requiredTenantId,
			requiredIsVisible));
	}

	/**
	 *	ICFSecTSecGroupByTenantVisIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecTSecGroupByTenantVisIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGroup> findByTenantVisIdx(ICFSecTSecGroupByTenantVisIdxKey key) {
		return( cfsec31TSecGroupRepository.findByTenantVisIdx(key.getRequiredTenantId(), key.getRequiredIsVisible()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecTSecGroupByUNameIdxKey as arguments.
	 *
	 *		@param requiredTenantId
	 *		@param requiredName
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGroup findByUNameIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("name") String requiredName) {
		return( cfsec31TSecGroupRepository.findByUNameIdx(requiredTenantId,
			requiredName));
	}

	/**
	 *	ICFSecTSecGroupByUNameIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecTSecGroupByUNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGroup findByUNameIdx(ICFSecTSecGroupByUNameIdxKey key) {
		return( cfsec31TSecGroupRepository.findByUNameIdx(key.getRequiredTenantId(), key.getRequiredName()));
	}

	// CFSecTSecGroup specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTSecGroupId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGroup lockByIdIdx(@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId) {
		return( cfsec31TSecGroupRepository.lockByIdIdx(requiredTSecGroupId));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGroup> lockByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId) {
		return( cfsec31TSecGroupRepository.lockByTenantIdx(requiredTenantId));
	}

	/**
	 *	ICFSecTSecGroupByTenantIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGroup> lockByTenantIdx(ICFSecTSecGroupByTenantIdxKey key) {
		return( cfsec31TSecGroupRepository.lockByTenantIdx(key.getRequiredTenantId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *		@param requiredIsVisible
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGroup> lockByTenantVisIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("isVisible") boolean requiredIsVisible) {
		return( cfsec31TSecGroupRepository.lockByTenantVisIdx(requiredTenantId,
			requiredIsVisible));
	}

	/**
	 *	ICFSecTSecGroupByTenantVisIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGroup> lockByTenantVisIdx(ICFSecTSecGroupByTenantVisIdxKey key) {
		return( cfsec31TSecGroupRepository.lockByTenantVisIdx(key.getRequiredTenantId(), key.getRequiredIsVisible()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *		@param requiredName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGroup lockByUNameIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("name") String requiredName) {
		return( cfsec31TSecGroupRepository.lockByUNameIdx(requiredTenantId,
			requiredName));
	}

	/**
	 *	ICFSecTSecGroupByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGroup lockByUNameIdx(ICFSecTSecGroupByUNameIdxKey key) {
		return( cfsec31TSecGroupRepository.lockByUNameIdx(key.getRequiredTenantId(), key.getRequiredName()));
	}

	// CFSecTSecGroup specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTSecGroupId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId) {
		cfsec31TSecGroupRepository.deleteByIdIdx(requiredTSecGroupId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId) {
		cfsec31TSecGroupRepository.deleteByTenantIdx(requiredTenantId);
	}

	/**
	 *	ICFSecTSecGroupByTenantIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecTSecGroupByTenantIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByTenantIdx(ICFSecTSecGroupByTenantIdxKey key) {
		cfsec31TSecGroupRepository.deleteByTenantIdx(key.getRequiredTenantId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *		@param requiredIsVisible
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByTenantVisIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("isVisible") boolean requiredIsVisible) {
		cfsec31TSecGroupRepository.deleteByTenantVisIdx(requiredTenantId,
			requiredIsVisible);
	}

	/**
	 *	ICFSecTSecGroupByTenantVisIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecTSecGroupByTenantVisIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByTenantVisIdx(ICFSecTSecGroupByTenantVisIdxKey key) {
		cfsec31TSecGroupRepository.deleteByTenantVisIdx(key.getRequiredTenantId(), key.getRequiredIsVisible());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *		@param requiredName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUNameIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("name") String requiredName) {
		cfsec31TSecGroupRepository.deleteByUNameIdx(requiredTenantId,
			requiredName);
	}

	/**
	 *	ICFSecTSecGroupByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecTSecGroupByUNameIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUNameIdx(ICFSecTSecGroupByUNameIdxKey key) {
		cfsec31TSecGroupRepository.deleteByUNameIdx(key.getRequiredTenantId(), key.getRequiredName());
	}
}

// Description: Java 25 Spring JPA Service for SecTentGrp

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
 *	Service for the CFSecSecTentGrp entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecSecTentGrpRepository to access them.
 */
@Service("cfsec31JpaSecTentGrpService")
public class CFSecJpaSecTentGrpService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaSecTentGrpRepository cfsec31SecTentGrpRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaSecTentGrp, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecTentGrp create(CFSecJpaSecTentGrp data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		CFLibDbKeyHash256 originalRequiredSecTentGrpId = data.getRequiredSecTentGrpId();
		boolean generatedRequiredSecTentGrpId = false;
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
			if (data.getRequiredSecTentGrpId() == null || data.getRequiredSecTentGrpId().isNull()) {
				data.setRequiredSecTentGrpId(new CFLibDbKeyHash256(0));
				generatedRequiredSecTentGrpId = true;
			}
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31SecTentGrpRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFSecJpaSecTentGrp)(cfsec31SecTentGrpRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfsec31SecTentGrpRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredSecTentGrpId) {
					data.setRequiredSecTentGrpId(originalRequiredSecTentGrpId);
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
	public CFSecJpaSecTentGrp update(CFSecJpaSecTentGrp data) {
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
		CFSecJpaSecTentGrp existing = cfsec31SecTentGrpRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecSecTentGrp to existing object
		existing.setRequiredOwnerTenant(data.getRequiredOwnerTenant());
		// Apply data columns of CFSecSecTentGrp to existing object
		existing.setRequiredName(data.getRequiredName());
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31SecTentGrpRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecTentGrpId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecTentGrp find(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId) {
		return( cfsec31SecTentGrpRepository.get(requiredSecTentGrpId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecTentGrp> findAll() {
		return( cfsec31SecTentGrpRepository.findAll() );
	}

	// CFSecSecTentGrp specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecTentGrpByTenantIdxKey as arguments.
	 *
	 *		@param requiredTenantId
	 *
	 *		@return List&lt;CFSecJpaSecTentGrp&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecTentGrp> findByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId) {
		return( cfsec31SecTentGrpRepository.findByTenantIdx(requiredTenantId));
	}

	/**
	 *	ICFSecSecTentGrpByTenantIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecTentGrpByTenantIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecTentGrp> findByTenantIdx(ICFSecSecTentGrpByTenantIdxKey key) {
		return( cfsec31SecTentGrpRepository.findByTenantIdx(key.getRequiredTenantId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecTentGrpByNameIdxKey as arguments.
	 *
	 *		@param requiredName
	 *
	 *		@return List&lt;CFSecJpaSecTentGrp&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecTentGrp> findByNameIdx(@Param("name") String requiredName) {
		return( cfsec31SecTentGrpRepository.findByNameIdx(requiredName));
	}

	/**
	 *	ICFSecSecTentGrpByNameIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecTentGrpByNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecTentGrp> findByNameIdx(ICFSecSecTentGrpByNameIdxKey key) {
		return( cfsec31SecTentGrpRepository.findByNameIdx(key.getRequiredName()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecSecTentGrpByUNameIdxKey as arguments.
	 *
	 *		@param requiredTenantId
	 *		@param requiredName
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecTentGrp findByUNameIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("name") String requiredName) {
		return( cfsec31SecTentGrpRepository.findByUNameIdx(requiredTenantId,
			requiredName));
	}

	/**
	 *	ICFSecSecTentGrpByUNameIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecTentGrpByUNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecTentGrp findByUNameIdx(ICFSecSecTentGrpByUNameIdxKey key) {
		return( cfsec31SecTentGrpRepository.findByUNameIdx(key.getRequiredTenantId(), key.getRequiredName()));
	}

	// CFSecSecTentGrp specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecTentGrpId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecTentGrp lockByIdIdx(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId) {
		return( cfsec31SecTentGrpRepository.lockByIdIdx(requiredSecTentGrpId));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecTentGrp> lockByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId) {
		return( cfsec31SecTentGrpRepository.lockByTenantIdx(requiredTenantId));
	}

	/**
	 *	ICFSecSecTentGrpByTenantIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecTentGrp> lockByTenantIdx(ICFSecSecTentGrpByTenantIdxKey key) {
		return( cfsec31SecTentGrpRepository.lockByTenantIdx(key.getRequiredTenantId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredName
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecTentGrp> lockByNameIdx(@Param("name") String requiredName) {
		return( cfsec31SecTentGrpRepository.lockByNameIdx(requiredName));
	}

	/**
	 *	ICFSecSecTentGrpByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecTentGrp> lockByNameIdx(ICFSecSecTentGrpByNameIdxKey key) {
		return( cfsec31SecTentGrpRepository.lockByNameIdx(key.getRequiredName()));
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
	public CFSecJpaSecTentGrp lockByUNameIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("name") String requiredName) {
		return( cfsec31SecTentGrpRepository.lockByUNameIdx(requiredTenantId,
			requiredName));
	}

	/**
	 *	ICFSecSecTentGrpByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecTentGrp lockByUNameIdx(ICFSecSecTentGrpByUNameIdxKey key) {
		return( cfsec31SecTentGrpRepository.lockByUNameIdx(key.getRequiredTenantId(), key.getRequiredName()));
	}

	// CFSecSecTentGrp specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecTentGrpId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId) {
		cfsec31SecTentGrpRepository.deleteByIdIdx(requiredSecTentGrpId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId) {
		cfsec31SecTentGrpRepository.deleteByTenantIdx(requiredTenantId);
	}

	/**
	 *	ICFSecSecTentGrpByTenantIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecTentGrpByTenantIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByTenantIdx(ICFSecSecTentGrpByTenantIdxKey key) {
		cfsec31SecTentGrpRepository.deleteByTenantIdx(key.getRequiredTenantId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByNameIdx(@Param("name") String requiredName) {
		cfsec31SecTentGrpRepository.deleteByNameIdx(requiredName);
	}

	/**
	 *	ICFSecSecTentGrpByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecTentGrpByNameIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByNameIdx(ICFSecSecTentGrpByNameIdxKey key) {
		cfsec31SecTentGrpRepository.deleteByNameIdx(key.getRequiredName());
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
		cfsec31SecTentGrpRepository.deleteByUNameIdx(requiredTenantId,
			requiredName);
	}

	/**
	 *	ICFSecSecTentGrpByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecTentGrpByUNameIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUNameIdx(ICFSecSecTentGrpByUNameIdxKey key) {
		cfsec31SecTentGrpRepository.deleteByUNameIdx(key.getRequiredTenantId(), key.getRequiredName());
	}

}

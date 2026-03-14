// Description: Java 25 Spring JPA Service for TSecGrpInc

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
 *	Service for the CFSecTSecGrpInc entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecTSecGrpIncRepository to access them.
 */
@Service("cfsec31JpaTSecGrpIncService")
public class CFSecJpaTSecGrpIncService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaTSecGrpIncRepository cfsec31TSecGrpIncRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaTSecGrpInc, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGrpInc create(CFSecJpaTSecGrpInc data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		CFLibDbKeyHash256 originalRequiredTSecGrpIncId = data.getRequiredTSecGrpIncId();
		boolean generatedRequiredTSecGrpIncId = false;
		if(data.getRequiredTenantId() == null || data.getRequiredTenantId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredTenantId");
		}
		if(data.getRequiredTSecGroupId() == null || data.getRequiredTSecGroupId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredTSecGroupId");
		}
		if(data.getRequiredIncludeGroupId() == null || data.getRequiredIncludeGroupId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredIncludeGroupId");
		}
		try {
			if (data.getRequiredTSecGrpIncId() == null || data.getRequiredTSecGrpIncId().isNull()) {
				data.setRequiredTSecGrpIncId(new CFLibDbKeyHash256(0));
				generatedRequiredTSecGrpIncId = true;
			}
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31TSecGrpIncRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFSecJpaTSecGrpInc)(cfsec31TSecGrpIncRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfsec31TSecGrpIncRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredTSecGrpIncId) {
					data.setRequiredTSecGrpIncId(originalRequiredTSecGrpIncId);
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
	public CFSecJpaTSecGrpInc update(CFSecJpaTSecGrpInc data) {
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
		if(data.getRequiredTSecGroupId() == null || data.getRequiredTSecGroupId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredTSecGroupId");
		}
		if(data.getRequiredIncludeGroupId() == null || data.getRequiredIncludeGroupId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredIncludeGroupId");
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaTSecGrpInc existing = cfsec31TSecGrpIncRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecTSecGrpInc to existing object
		existing.setRequiredOwnerTenant(data.getRequiredOwnerTenant());
		existing.setRequiredContainerGroup(data.getRequiredContainerGroup());
		existing.setRequiredParentSubGroup(data.getRequiredParentSubGroup());
		// Apply data columns of CFSecTSecGrpInc to existing object
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31TSecGrpIncRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredTSecGrpIncId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGrpInc find(@Param("tSecGrpIncId") CFLibDbKeyHash256 requiredTSecGrpIncId) {
		return( cfsec31TSecGrpIncRepository.get(requiredTSecGrpIncId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpInc> findAll() {
		return( cfsec31TSecGrpIncRepository.findAll() );
	}

	// CFSecTSecGrpInc specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecTSecGrpIncByTenantIdxKey as arguments.
	 *
	 *		@param requiredTenantId
	 *
	 *		@return List&lt;CFSecJpaTSecGrpInc&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpInc> findByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId) {
		return( cfsec31TSecGrpIncRepository.findByTenantIdx(requiredTenantId));
	}

	/**
	 *	ICFSecTSecGrpIncByTenantIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecTSecGrpIncByTenantIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpInc> findByTenantIdx(ICFSecTSecGrpIncByTenantIdxKey key) {
		return( cfsec31TSecGrpIncRepository.findByTenantIdx(key.getRequiredTenantId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecTSecGrpIncByGroupIdxKey as arguments.
	 *
	 *		@param requiredTSecGroupId
	 *
	 *		@return List&lt;CFSecJpaTSecGrpInc&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpInc> findByGroupIdx(@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId) {
		return( cfsec31TSecGrpIncRepository.findByGroupIdx(requiredTSecGroupId));
	}

	/**
	 *	ICFSecTSecGrpIncByGroupIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecTSecGrpIncByGroupIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpInc> findByGroupIdx(ICFSecTSecGrpIncByGroupIdxKey key) {
		return( cfsec31TSecGrpIncRepository.findByGroupIdx(key.getRequiredTSecGroupId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecTSecGrpIncByIncludeIdxKey as arguments.
	 *
	 *		@param requiredIncludeGroupId
	 *
	 *		@return List&lt;CFSecJpaTSecGrpInc&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpInc> findByIncludeIdx(@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId) {
		return( cfsec31TSecGrpIncRepository.findByIncludeIdx(requiredIncludeGroupId));
	}

	/**
	 *	ICFSecTSecGrpIncByIncludeIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecTSecGrpIncByIncludeIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpInc> findByIncludeIdx(ICFSecTSecGrpIncByIncludeIdxKey key) {
		return( cfsec31TSecGrpIncRepository.findByIncludeIdx(key.getRequiredIncludeGroupId()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecTSecGrpIncByUIncludeIdxKey as arguments.
	 *
	 *		@param requiredTenantId
	 *		@param requiredTSecGroupId
	 *		@param requiredIncludeGroupId
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGrpInc findByUIncludeIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId,
		@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId) {
		return( cfsec31TSecGrpIncRepository.findByUIncludeIdx(requiredTenantId,
			requiredTSecGroupId,
			requiredIncludeGroupId));
	}

	/**
	 *	ICFSecTSecGrpIncByUIncludeIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecTSecGrpIncByUIncludeIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGrpInc findByUIncludeIdx(ICFSecTSecGrpIncByUIncludeIdxKey key) {
		return( cfsec31TSecGrpIncRepository.findByUIncludeIdx(key.getRequiredTenantId(), key.getRequiredTSecGroupId(), key.getRequiredIncludeGroupId()));
	}

	// CFSecTSecGrpInc specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTSecGrpIncId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGrpInc lockByIdIdx(@Param("tSecGrpIncId") CFLibDbKeyHash256 requiredTSecGrpIncId) {
		return( cfsec31TSecGrpIncRepository.lockByIdIdx(requiredTSecGrpIncId));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpInc> lockByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId) {
		return( cfsec31TSecGrpIncRepository.lockByTenantIdx(requiredTenantId));
	}

	/**
	 *	ICFSecTSecGrpIncByTenantIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpInc> lockByTenantIdx(ICFSecTSecGrpIncByTenantIdxKey key) {
		return( cfsec31TSecGrpIncRepository.lockByTenantIdx(key.getRequiredTenantId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTSecGroupId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpInc> lockByGroupIdx(@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId) {
		return( cfsec31TSecGrpIncRepository.lockByGroupIdx(requiredTSecGroupId));
	}

	/**
	 *	ICFSecTSecGrpIncByGroupIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpInc> lockByGroupIdx(ICFSecTSecGrpIncByGroupIdxKey key) {
		return( cfsec31TSecGrpIncRepository.lockByGroupIdx(key.getRequiredTSecGroupId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredIncludeGroupId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpInc> lockByIncludeIdx(@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId) {
		return( cfsec31TSecGrpIncRepository.lockByIncludeIdx(requiredIncludeGroupId));
	}

	/**
	 *	ICFSecTSecGrpIncByIncludeIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpInc> lockByIncludeIdx(ICFSecTSecGrpIncByIncludeIdxKey key) {
		return( cfsec31TSecGrpIncRepository.lockByIncludeIdx(key.getRequiredIncludeGroupId()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *		@param requiredTSecGroupId
	 *		@param requiredIncludeGroupId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGrpInc lockByUIncludeIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId,
		@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId) {
		return( cfsec31TSecGrpIncRepository.lockByUIncludeIdx(requiredTenantId,
			requiredTSecGroupId,
			requiredIncludeGroupId));
	}

	/**
	 *	ICFSecTSecGrpIncByUIncludeIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGrpInc lockByUIncludeIdx(ICFSecTSecGrpIncByUIncludeIdxKey key) {
		return( cfsec31TSecGrpIncRepository.lockByUIncludeIdx(key.getRequiredTenantId(), key.getRequiredTSecGroupId(), key.getRequiredIncludeGroupId()));
	}

	// CFSecTSecGrpInc specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTSecGrpIncId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("tSecGrpIncId") CFLibDbKeyHash256 requiredTSecGrpIncId) {
		cfsec31TSecGrpIncRepository.deleteByIdIdx(requiredTSecGrpIncId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId) {
		cfsec31TSecGrpIncRepository.deleteByTenantIdx(requiredTenantId);
	}

	/**
	 *	ICFSecTSecGrpIncByTenantIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecTSecGrpIncByTenantIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByTenantIdx(ICFSecTSecGrpIncByTenantIdxKey key) {
		cfsec31TSecGrpIncRepository.deleteByTenantIdx(key.getRequiredTenantId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTSecGroupId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByGroupIdx(@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId) {
		cfsec31TSecGrpIncRepository.deleteByGroupIdx(requiredTSecGroupId);
	}

	/**
	 *	ICFSecTSecGrpIncByGroupIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecTSecGrpIncByGroupIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByGroupIdx(ICFSecTSecGrpIncByGroupIdxKey key) {
		cfsec31TSecGrpIncRepository.deleteByGroupIdx(key.getRequiredTSecGroupId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredIncludeGroupId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIncludeIdx(@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId) {
		cfsec31TSecGrpIncRepository.deleteByIncludeIdx(requiredIncludeGroupId);
	}

	/**
	 *	ICFSecTSecGrpIncByIncludeIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecTSecGrpIncByIncludeIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIncludeIdx(ICFSecTSecGrpIncByIncludeIdxKey key) {
		cfsec31TSecGrpIncRepository.deleteByIncludeIdx(key.getRequiredIncludeGroupId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *		@param requiredTSecGroupId
	 *		@param requiredIncludeGroupId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUIncludeIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId,
		@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId) {
		cfsec31TSecGrpIncRepository.deleteByUIncludeIdx(requiredTenantId,
			requiredTSecGroupId,
			requiredIncludeGroupId);
	}

	/**
	 *	ICFSecTSecGrpIncByUIncludeIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecTSecGrpIncByUIncludeIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUIncludeIdx(ICFSecTSecGrpIncByUIncludeIdxKey key) {
		cfsec31TSecGrpIncRepository.deleteByUIncludeIdx(key.getRequiredTenantId(), key.getRequiredTSecGroupId(), key.getRequiredIncludeGroupId());
	}
}

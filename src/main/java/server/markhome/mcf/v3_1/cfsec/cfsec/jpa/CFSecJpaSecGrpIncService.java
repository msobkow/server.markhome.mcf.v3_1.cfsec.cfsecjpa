// Description: Java 25 Spring JPA Service for SecGrpInc

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
 *	Service for the CFSecSecGrpInc entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecSecGrpIncRepository to access them.
 */
@Service("cfsec31JpaSecGrpIncService")
public class CFSecJpaSecGrpIncService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaSecGrpIncRepository cfsec31SecGrpIncRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaSecGrpInc, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecGrpInc create(CFSecJpaSecGrpInc data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		CFLibDbKeyHash256 originalRequiredSecGrpIncId = data.getRequiredSecGrpIncId();
		boolean generatedRequiredSecGrpIncId = false;
		if(data.getRequiredClusterId() == null || data.getRequiredClusterId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredClusterId");
		}
		if(data.getRequiredSecGroupId() == null || data.getRequiredSecGroupId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredSecGroupId");
		}
		if(data.getRequiredIncludeGroupId() == null || data.getRequiredIncludeGroupId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredIncludeGroupId");
		}
		try {
			if (data.getRequiredSecGrpIncId() == null || data.getRequiredSecGrpIncId().isNull()) {
				data.setRequiredSecGrpIncId(new CFLibDbKeyHash256(0));
				generatedRequiredSecGrpIncId = true;
			}
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31SecGrpIncRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFSecJpaSecGrpInc)(cfsec31SecGrpIncRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfsec31SecGrpIncRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredSecGrpIncId) {
					data.setRequiredSecGrpIncId(originalRequiredSecGrpIncId);
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
	public CFSecJpaSecGrpInc update(CFSecJpaSecGrpInc data) {
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
		if(data.getRequiredClusterId() == null || data.getRequiredClusterId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredClusterId");
		}
		if(data.getRequiredSecGroupId() == null || data.getRequiredSecGroupId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredSecGroupId");
		}
		if(data.getRequiredIncludeGroupId() == null || data.getRequiredIncludeGroupId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredIncludeGroupId");
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaSecGrpInc existing = cfsec31SecGrpIncRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecSecGrpInc to existing object
		existing.setRequiredOwnerCluster(data.getRequiredOwnerCluster());
		existing.setRequiredContainerGroup(data.getRequiredContainerGroup());
		existing.setRequiredParentSubGroup(data.getRequiredParentSubGroup());
		// Apply data columns of CFSecSecGrpInc to existing object
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31SecGrpIncRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecGrpIncId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecGrpInc find(@Param("secGrpIncId") CFLibDbKeyHash256 requiredSecGrpIncId) {
		return( cfsec31SecGrpIncRepository.get(requiredSecGrpIncId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpInc> findAll() {
		return( cfsec31SecGrpIncRepository.findAll() );
	}

	// CFSecSecGrpInc specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecGrpIncByClusterIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return List&lt;CFSecJpaSecGrpInc&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpInc> findByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		return( cfsec31SecGrpIncRepository.findByClusterIdx(requiredClusterId));
	}

	/**
	 *	ICFSecSecGrpIncByClusterIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecGrpIncByClusterIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpInc> findByClusterIdx(ICFSecSecGrpIncByClusterIdxKey key) {
		return( cfsec31SecGrpIncRepository.findByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecGrpIncByGroupIdxKey as arguments.
	 *
	 *		@param requiredSecGroupId
	 *
	 *		@return List&lt;CFSecJpaSecGrpInc&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpInc> findByGroupIdx(@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId) {
		return( cfsec31SecGrpIncRepository.findByGroupIdx(requiredSecGroupId));
	}

	/**
	 *	ICFSecSecGrpIncByGroupIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecGrpIncByGroupIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpInc> findByGroupIdx(ICFSecSecGrpIncByGroupIdxKey key) {
		return( cfsec31SecGrpIncRepository.findByGroupIdx(key.getRequiredSecGroupId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecGrpIncByIncludeIdxKey as arguments.
	 *
	 *		@param requiredIncludeGroupId
	 *
	 *		@return List&lt;CFSecJpaSecGrpInc&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpInc> findByIncludeIdx(@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId) {
		return( cfsec31SecGrpIncRepository.findByIncludeIdx(requiredIncludeGroupId));
	}

	/**
	 *	ICFSecSecGrpIncByIncludeIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecGrpIncByIncludeIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpInc> findByIncludeIdx(ICFSecSecGrpIncByIncludeIdxKey key) {
		return( cfsec31SecGrpIncRepository.findByIncludeIdx(key.getRequiredIncludeGroupId()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecSecGrpIncByUIncludeIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *		@param requiredSecGroupId
	 *		@param requiredIncludeGroupId
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecGrpInc findByUIncludeIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId,
		@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId) {
		return( cfsec31SecGrpIncRepository.findByUIncludeIdx(requiredClusterId,
			requiredSecGroupId,
			requiredIncludeGroupId));
	}

	/**
	 *	ICFSecSecGrpIncByUIncludeIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecGrpIncByUIncludeIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecGrpInc findByUIncludeIdx(ICFSecSecGrpIncByUIncludeIdxKey key) {
		return( cfsec31SecGrpIncRepository.findByUIncludeIdx(key.getRequiredClusterId(), key.getRequiredSecGroupId(), key.getRequiredIncludeGroupId()));
	}

	// CFSecSecGrpInc specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecGrpIncId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecGrpInc lockByIdIdx(@Param("secGrpIncId") CFLibDbKeyHash256 requiredSecGrpIncId) {
		return( cfsec31SecGrpIncRepository.lockByIdIdx(requiredSecGrpIncId));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpInc> lockByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		return( cfsec31SecGrpIncRepository.lockByClusterIdx(requiredClusterId));
	}

	/**
	 *	ICFSecSecGrpIncByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpInc> lockByClusterIdx(ICFSecSecGrpIncByClusterIdxKey key) {
		return( cfsec31SecGrpIncRepository.lockByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecGroupId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpInc> lockByGroupIdx(@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId) {
		return( cfsec31SecGrpIncRepository.lockByGroupIdx(requiredSecGroupId));
	}

	/**
	 *	ICFSecSecGrpIncByGroupIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpInc> lockByGroupIdx(ICFSecSecGrpIncByGroupIdxKey key) {
		return( cfsec31SecGrpIncRepository.lockByGroupIdx(key.getRequiredSecGroupId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredIncludeGroupId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpInc> lockByIncludeIdx(@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId) {
		return( cfsec31SecGrpIncRepository.lockByIncludeIdx(requiredIncludeGroupId));
	}

	/**
	 *	ICFSecSecGrpIncByIncludeIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpInc> lockByIncludeIdx(ICFSecSecGrpIncByIncludeIdxKey key) {
		return( cfsec31SecGrpIncRepository.lockByIncludeIdx(key.getRequiredIncludeGroupId()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredSecGroupId
	 *		@param requiredIncludeGroupId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecGrpInc lockByUIncludeIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId,
		@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId) {
		return( cfsec31SecGrpIncRepository.lockByUIncludeIdx(requiredClusterId,
			requiredSecGroupId,
			requiredIncludeGroupId));
	}

	/**
	 *	ICFSecSecGrpIncByUIncludeIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecGrpInc lockByUIncludeIdx(ICFSecSecGrpIncByUIncludeIdxKey key) {
		return( cfsec31SecGrpIncRepository.lockByUIncludeIdx(key.getRequiredClusterId(), key.getRequiredSecGroupId(), key.getRequiredIncludeGroupId()));
	}

	// CFSecSecGrpInc specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecGrpIncId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("secGrpIncId") CFLibDbKeyHash256 requiredSecGrpIncId) {
		cfsec31SecGrpIncRepository.deleteByIdIdx(requiredSecGrpIncId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		cfsec31SecGrpIncRepository.deleteByClusterIdx(requiredClusterId);
	}

	/**
	 *	ICFSecSecGrpIncByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecGrpIncByClusterIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByClusterIdx(ICFSecSecGrpIncByClusterIdxKey key) {
		cfsec31SecGrpIncRepository.deleteByClusterIdx(key.getRequiredClusterId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecGroupId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByGroupIdx(@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId) {
		cfsec31SecGrpIncRepository.deleteByGroupIdx(requiredSecGroupId);
	}

	/**
	 *	ICFSecSecGrpIncByGroupIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecGrpIncByGroupIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByGroupIdx(ICFSecSecGrpIncByGroupIdxKey key) {
		cfsec31SecGrpIncRepository.deleteByGroupIdx(key.getRequiredSecGroupId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredIncludeGroupId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIncludeIdx(@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId) {
		cfsec31SecGrpIncRepository.deleteByIncludeIdx(requiredIncludeGroupId);
	}

	/**
	 *	ICFSecSecGrpIncByIncludeIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecGrpIncByIncludeIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIncludeIdx(ICFSecSecGrpIncByIncludeIdxKey key) {
		cfsec31SecGrpIncRepository.deleteByIncludeIdx(key.getRequiredIncludeGroupId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredSecGroupId
	 *		@param requiredIncludeGroupId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUIncludeIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId,
		@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId) {
		cfsec31SecGrpIncRepository.deleteByUIncludeIdx(requiredClusterId,
			requiredSecGroupId,
			requiredIncludeGroupId);
	}

	/**
	 *	ICFSecSecGrpIncByUIncludeIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecGrpIncByUIncludeIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUIncludeIdx(ICFSecSecGrpIncByUIncludeIdxKey key) {
		cfsec31SecGrpIncRepository.deleteByUIncludeIdx(key.getRequiredClusterId(), key.getRequiredSecGroupId(), key.getRequiredIncludeGroupId());
	}
}

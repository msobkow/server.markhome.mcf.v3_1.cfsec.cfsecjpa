// Description: Java 25 Spring JPA Service for SecGrpMemb

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
 *	Service for the CFSecSecGrpMemb entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecSecGrpMembRepository to access them.
 */
@Service("cfsec31JpaSecGrpMembService")
public class CFSecJpaSecGrpMembService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaSecGrpMembRepository cfsec31SecGrpMembRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaSecGrpMemb, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecGrpMemb create(CFSecJpaSecGrpMemb data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		CFLibDbKeyHash256 originalRequiredSecGrpMembId = data.getRequiredSecGrpMembId();
		boolean generatedRequiredSecGrpMembId = false;
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
		if(data.getRequiredSecUserId() == null || data.getRequiredSecUserId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredSecUserId");
		}
		try {
			if (data.getRequiredSecGrpMembId() == null || data.getRequiredSecGrpMembId().isNull()) {
				data.setRequiredSecGrpMembId(new CFLibDbKeyHash256(0));
				generatedRequiredSecGrpMembId = true;
			}
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31SecGrpMembRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFSecJpaSecGrpMemb)(cfsec31SecGrpMembRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfsec31SecGrpMembRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredSecGrpMembId) {
					data.setRequiredSecGrpMembId(originalRequiredSecGrpMembId);
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
	public CFSecJpaSecGrpMemb update(CFSecJpaSecGrpMemb data) {
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
		if(data.getRequiredSecUserId() == null || data.getRequiredSecUserId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredSecUserId");
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaSecGrpMemb existing = cfsec31SecGrpMembRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecSecGrpMemb to existing object
		existing.setRequiredOwnerCluster(data.getRequiredOwnerCluster());
		existing.setRequiredContainerGroup(data.getRequiredContainerGroup());
		existing.setRequiredParentUser(data.getRequiredParentUser());
		// Apply data columns of CFSecSecGrpMemb to existing object
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31SecGrpMembRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecGrpMembId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecGrpMemb find(@Param("secGrpMembId") CFLibDbKeyHash256 requiredSecGrpMembId) {
		return( cfsec31SecGrpMembRepository.get(requiredSecGrpMembId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpMemb> findAll() {
		return( cfsec31SecGrpMembRepository.findAll() );
	}

	// CFSecSecGrpMemb specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecGrpMembByClusterIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return List&lt;CFSecJpaSecGrpMemb&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpMemb> findByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		return( cfsec31SecGrpMembRepository.findByClusterIdx(requiredClusterId));
	}

	/**
	 *	ICFSecSecGrpMembByClusterIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecGrpMembByClusterIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpMemb> findByClusterIdx(ICFSecSecGrpMembByClusterIdxKey key) {
		return( cfsec31SecGrpMembRepository.findByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecGrpMembByGroupIdxKey as arguments.
	 *
	 *		@param requiredSecGroupId
	 *
	 *		@return List&lt;CFSecJpaSecGrpMemb&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpMemb> findByGroupIdx(@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId) {
		return( cfsec31SecGrpMembRepository.findByGroupIdx(requiredSecGroupId));
	}

	/**
	 *	ICFSecSecGrpMembByGroupIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecGrpMembByGroupIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpMemb> findByGroupIdx(ICFSecSecGrpMembByGroupIdxKey key) {
		return( cfsec31SecGrpMembRepository.findByGroupIdx(key.getRequiredSecGroupId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecGrpMembByUserIdxKey as arguments.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return List&lt;CFSecJpaSecGrpMemb&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpMemb> findByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecGrpMembRepository.findByUserIdx(requiredSecUserId));
	}

	/**
	 *	ICFSecSecGrpMembByUserIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecGrpMembByUserIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpMemb> findByUserIdx(ICFSecSecGrpMembByUserIdxKey key) {
		return( cfsec31SecGrpMembRepository.findByUserIdx(key.getRequiredSecUserId()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecSecGrpMembByUUserIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *		@param requiredSecGroupId
	 *		@param requiredSecUserId
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecGrpMemb findByUUserIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecGrpMembRepository.findByUUserIdx(requiredClusterId,
			requiredSecGroupId,
			requiredSecUserId));
	}

	/**
	 *	ICFSecSecGrpMembByUUserIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecGrpMembByUUserIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecGrpMemb findByUUserIdx(ICFSecSecGrpMembByUUserIdxKey key) {
		return( cfsec31SecGrpMembRepository.findByUUserIdx(key.getRequiredClusterId(), key.getRequiredSecGroupId(), key.getRequiredSecUserId()));
	}

	// CFSecSecGrpMemb specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecGrpMembId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecGrpMemb lockByIdIdx(@Param("secGrpMembId") CFLibDbKeyHash256 requiredSecGrpMembId) {
		return( cfsec31SecGrpMembRepository.lockByIdIdx(requiredSecGrpMembId));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpMemb> lockByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		return( cfsec31SecGrpMembRepository.lockByClusterIdx(requiredClusterId));
	}

	/**
	 *	ICFSecSecGrpMembByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpMemb> lockByClusterIdx(ICFSecSecGrpMembByClusterIdxKey key) {
		return( cfsec31SecGrpMembRepository.lockByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecGroupId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpMemb> lockByGroupIdx(@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId) {
		return( cfsec31SecGrpMembRepository.lockByGroupIdx(requiredSecGroupId));
	}

	/**
	 *	ICFSecSecGrpMembByGroupIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpMemb> lockByGroupIdx(ICFSecSecGrpMembByGroupIdxKey key) {
		return( cfsec31SecGrpMembRepository.lockByGroupIdx(key.getRequiredSecGroupId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpMemb> lockByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecGrpMembRepository.lockByUserIdx(requiredSecUserId));
	}

	/**
	 *	ICFSecSecGrpMembByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGrpMemb> lockByUserIdx(ICFSecSecGrpMembByUserIdxKey key) {
		return( cfsec31SecGrpMembRepository.lockByUserIdx(key.getRequiredSecUserId()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredSecGroupId
	 *		@param requiredSecUserId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecGrpMemb lockByUUserIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecGrpMembRepository.lockByUUserIdx(requiredClusterId,
			requiredSecGroupId,
			requiredSecUserId));
	}

	/**
	 *	ICFSecSecGrpMembByUUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecGrpMemb lockByUUserIdx(ICFSecSecGrpMembByUUserIdxKey key) {
		return( cfsec31SecGrpMembRepository.lockByUUserIdx(key.getRequiredClusterId(), key.getRequiredSecGroupId(), key.getRequiredSecUserId()));
	}

	// CFSecSecGrpMemb specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecGrpMembId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("secGrpMembId") CFLibDbKeyHash256 requiredSecGrpMembId) {
		cfsec31SecGrpMembRepository.deleteByIdIdx(requiredSecGrpMembId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		cfsec31SecGrpMembRepository.deleteByClusterIdx(requiredClusterId);
	}

	/**
	 *	ICFSecSecGrpMembByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecGrpMembByClusterIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByClusterIdx(ICFSecSecGrpMembByClusterIdxKey key) {
		cfsec31SecGrpMembRepository.deleteByClusterIdx(key.getRequiredClusterId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecGroupId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByGroupIdx(@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId) {
		cfsec31SecGrpMembRepository.deleteByGroupIdx(requiredSecGroupId);
	}

	/**
	 *	ICFSecSecGrpMembByGroupIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecGrpMembByGroupIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByGroupIdx(ICFSecSecGrpMembByGroupIdxKey key) {
		cfsec31SecGrpMembRepository.deleteByGroupIdx(key.getRequiredSecGroupId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		cfsec31SecGrpMembRepository.deleteByUserIdx(requiredSecUserId);
	}

	/**
	 *	ICFSecSecGrpMembByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecGrpMembByUserIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUserIdx(ICFSecSecGrpMembByUserIdxKey key) {
		cfsec31SecGrpMembRepository.deleteByUserIdx(key.getRequiredSecUserId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredSecGroupId
	 *		@param requiredSecUserId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUUserIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		cfsec31SecGrpMembRepository.deleteByUUserIdx(requiredClusterId,
			requiredSecGroupId,
			requiredSecUserId);
	}

	/**
	 *	ICFSecSecGrpMembByUUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecGrpMembByUUserIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUUserIdx(ICFSecSecGrpMembByUUserIdxKey key) {
		cfsec31SecGrpMembRepository.deleteByUUserIdx(key.getRequiredClusterId(), key.getRequiredSecGroupId(), key.getRequiredSecUserId());
	}
}

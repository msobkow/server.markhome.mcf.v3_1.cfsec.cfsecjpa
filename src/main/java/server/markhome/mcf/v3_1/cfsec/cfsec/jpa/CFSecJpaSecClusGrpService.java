// Description: Java 25 Spring JPA Service for SecClusGrp

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
 *	Service for the CFSecSecClusGrp entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecSecClusGrpRepository to access them.
 */
@Service("cfsec31JpaSecClusGrpService")
public class CFSecJpaSecClusGrpService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaSecClusGrpRepository cfsec31SecClusGrpRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaSecClusGrp, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecClusGrp create(CFSecJpaSecClusGrp data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		CFLibDbKeyHash256 originalRequiredSecClusGrpId = data.getRequiredSecClusGrpId();
		boolean generatedRequiredSecClusGrpId = false;
		if(data.getRequiredClusterId() == null || data.getRequiredClusterId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredClusterId");
		}
		if(data.getRequiredName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredName");
		}
		try {
			if (data.getRequiredSecClusGrpId() == null || data.getRequiredSecClusGrpId().isNull()) {
				data.setRequiredSecClusGrpId(new CFLibDbKeyHash256(0));
				generatedRequiredSecClusGrpId = true;
			}
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31SecClusGrpRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFSecJpaSecClusGrp)(cfsec31SecClusGrpRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfsec31SecClusGrpRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredSecClusGrpId) {
					data.setRequiredSecClusGrpId(originalRequiredSecClusGrpId);
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
	public CFSecJpaSecClusGrp update(CFSecJpaSecClusGrp data) {
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
		if(data.getRequiredName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredName");
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaSecClusGrp existing = cfsec31SecClusGrpRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecSecClusGrp to existing object
		existing.setRequiredOwnerCluster(data.getRequiredOwnerCluster());
		// Apply data columns of CFSecSecClusGrp to existing object
		existing.setRequiredName(data.getRequiredName());
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31SecClusGrpRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecClusGrpId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecClusGrp find(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId) {
		return( cfsec31SecClusGrpRepository.get(requiredSecClusGrpId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrp> findAll() {
		return( cfsec31SecClusGrpRepository.findAll() );
	}

	// CFSecSecClusGrp specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecClusGrpByClusterIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return List&lt;CFSecJpaSecClusGrp&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrp> findByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		return( cfsec31SecClusGrpRepository.findByClusterIdx(requiredClusterId));
	}

	/**
	 *	ICFSecSecClusGrpByClusterIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecClusGrpByClusterIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrp> findByClusterIdx(ICFSecSecClusGrpByClusterIdxKey key) {
		return( cfsec31SecClusGrpRepository.findByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecClusGrpByNameIdxKey as arguments.
	 *
	 *		@param requiredName
	 *
	 *		@return List&lt;CFSecJpaSecClusGrp&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrp> findByNameIdx(@Param("name") String requiredName) {
		return( cfsec31SecClusGrpRepository.findByNameIdx(requiredName));
	}

	/**
	 *	ICFSecSecClusGrpByNameIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecClusGrpByNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrp> findByNameIdx(ICFSecSecClusGrpByNameIdxKey key) {
		return( cfsec31SecClusGrpRepository.findByNameIdx(key.getRequiredName()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecSecClusGrpByUNameIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *		@param requiredName
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecClusGrp findByUNameIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("name") String requiredName) {
		return( cfsec31SecClusGrpRepository.findByUNameIdx(requiredClusterId,
			requiredName));
	}

	/**
	 *	ICFSecSecClusGrpByUNameIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecClusGrpByUNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecClusGrp findByUNameIdx(ICFSecSecClusGrpByUNameIdxKey key) {
		return( cfsec31SecClusGrpRepository.findByUNameIdx(key.getRequiredClusterId(), key.getRequiredName()));
	}

	// CFSecSecClusGrp specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecClusGrpId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecClusGrp lockByIdIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId) {
		return( cfsec31SecClusGrpRepository.lockByIdIdx(requiredSecClusGrpId));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrp> lockByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		return( cfsec31SecClusGrpRepository.lockByClusterIdx(requiredClusterId));
	}

	/**
	 *	ICFSecSecClusGrpByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrp> lockByClusterIdx(ICFSecSecClusGrpByClusterIdxKey key) {
		return( cfsec31SecClusGrpRepository.lockByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredName
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrp> lockByNameIdx(@Param("name") String requiredName) {
		return( cfsec31SecClusGrpRepository.lockByNameIdx(requiredName));
	}

	/**
	 *	ICFSecSecClusGrpByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrp> lockByNameIdx(ICFSecSecClusGrpByNameIdxKey key) {
		return( cfsec31SecClusGrpRepository.lockByNameIdx(key.getRequiredName()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecClusGrp lockByUNameIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("name") String requiredName) {
		return( cfsec31SecClusGrpRepository.lockByUNameIdx(requiredClusterId,
			requiredName));
	}

	/**
	 *	ICFSecSecClusGrpByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecClusGrp lockByUNameIdx(ICFSecSecClusGrpByUNameIdxKey key) {
		return( cfsec31SecClusGrpRepository.lockByUNameIdx(key.getRequiredClusterId(), key.getRequiredName()));
	}

	// CFSecSecClusGrp specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecClusGrpId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId) {
		cfsec31SecClusGrpRepository.deleteByIdIdx(requiredSecClusGrpId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		cfsec31SecClusGrpRepository.deleteByClusterIdx(requiredClusterId);
	}

	/**
	 *	ICFSecSecClusGrpByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecClusGrpByClusterIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByClusterIdx(ICFSecSecClusGrpByClusterIdxKey key) {
		cfsec31SecClusGrpRepository.deleteByClusterIdx(key.getRequiredClusterId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByNameIdx(@Param("name") String requiredName) {
		cfsec31SecClusGrpRepository.deleteByNameIdx(requiredName);
	}

	/**
	 *	ICFSecSecClusGrpByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecClusGrpByNameIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByNameIdx(ICFSecSecClusGrpByNameIdxKey key) {
		cfsec31SecClusGrpRepository.deleteByNameIdx(key.getRequiredName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUNameIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("name") String requiredName) {
		cfsec31SecClusGrpRepository.deleteByUNameIdx(requiredClusterId,
			requiredName);
	}

	/**
	 *	ICFSecSecClusGrpByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecClusGrpByUNameIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUNameIdx(ICFSecSecClusGrpByUNameIdxKey key) {
		cfsec31SecClusGrpRepository.deleteByUNameIdx(key.getRequiredClusterId(), key.getRequiredName());
	}
}

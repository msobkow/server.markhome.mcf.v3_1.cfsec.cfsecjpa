// Description: Java 25 Spring JPA Service for SecGroup

/*
 *	server.markhome.mcf.CFSec
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal 3.1 CFSec - Security Services
 *	
 *	This file is part of Mark's Code Fractal CFSec.
 *	
 *	Mark's Code Fractal CFSec is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU Library General Public License,
 *	Version 3 or later.
 *	
 *	Mark's Code Fractal CFSec is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU Library General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	Mark's Code Fractal CFSec is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU Library General Public License
 *	along with Mark's Code Fractal CFSec.  If not, see <https://www.gnu.org/licenses/>.
 *	
 *	If you wish to modify and use this code without publishing your changes in order to
 *	tie it to proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
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
 *	Service for the CFSecSecGroup entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecSecGroupRepository to access them.
 */
@Service("cfsec31JpaSecGroupService")
public class CFSecJpaSecGroupService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaSecGroupRepository cfsec31SecGroupRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaSecGroup, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecGroup create(CFSecJpaSecGroup data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		CFLibDbKeyHash256 originalRequiredSecGroupId = data.getRequiredSecGroupId();
		boolean generatedRequiredSecGroupId = false;
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
			if (data.getRequiredSecGroupId() == null || data.getRequiredSecGroupId().isNull()) {
				data.setRequiredSecGroupId(new CFLibDbKeyHash256(0));
				generatedRequiredSecGroupId = true;
			}
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31SecGroupRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFSecJpaSecGroup)(cfsec31SecGroupRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfsec31SecGroupRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredSecGroupId) {
					data.setRequiredSecGroupId(originalRequiredSecGroupId);
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
	public CFSecJpaSecGroup update(CFSecJpaSecGroup data) {
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
		CFSecJpaSecGroup existing = cfsec31SecGroupRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecSecGroup to existing object
		existing.setRequiredContainerCluster(data.getRequiredContainerCluster());
		// Apply data columns of CFSecSecGroup to existing object
		existing.setRequiredName(data.getRequiredName());
		existing.setRequiredIsVisible(data.getRequiredIsVisible());
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31SecGroupRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecGroupId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecGroup find(@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId) {
		return( cfsec31SecGroupRepository.get(requiredSecGroupId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGroup> findAll() {
		return( cfsec31SecGroupRepository.findAll() );
	}

	// CFSecSecGroup specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecGroupByClusterIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return List&lt;CFSecJpaSecGroup&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGroup> findByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		return( cfsec31SecGroupRepository.findByClusterIdx(requiredClusterId));
	}

	/**
	 *	ICFSecSecGroupByClusterIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecGroupByClusterIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGroup> findByClusterIdx(ICFSecSecGroupByClusterIdxKey key) {
		return( cfsec31SecGroupRepository.findByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecGroupByClusterVisIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *		@param requiredIsVisible
	 *
	 *		@return List&lt;CFSecJpaSecGroup&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGroup> findByClusterVisIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("isVisible") boolean requiredIsVisible) {
		return( cfsec31SecGroupRepository.findByClusterVisIdx(requiredClusterId,
			requiredIsVisible));
	}

	/**
	 *	ICFSecSecGroupByClusterVisIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecGroupByClusterVisIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGroup> findByClusterVisIdx(ICFSecSecGroupByClusterVisIdxKey key) {
		return( cfsec31SecGroupRepository.findByClusterVisIdx(key.getRequiredClusterId(), key.getRequiredIsVisible()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecSecGroupByUNameIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *		@param requiredName
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecGroup findByUNameIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("name") String requiredName) {
		return( cfsec31SecGroupRepository.findByUNameIdx(requiredClusterId,
			requiredName));
	}

	/**
	 *	ICFSecSecGroupByUNameIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecGroupByUNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecGroup findByUNameIdx(ICFSecSecGroupByUNameIdxKey key) {
		return( cfsec31SecGroupRepository.findByUNameIdx(key.getRequiredClusterId(), key.getRequiredName()));
	}

	// CFSecSecGroup specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecGroupId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecGroup lockByIdIdx(@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId) {
		return( cfsec31SecGroupRepository.lockByIdIdx(requiredSecGroupId));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGroup> lockByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		return( cfsec31SecGroupRepository.lockByClusterIdx(requiredClusterId));
	}

	/**
	 *	ICFSecSecGroupByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGroup> lockByClusterIdx(ICFSecSecGroupByClusterIdxKey key) {
		return( cfsec31SecGroupRepository.lockByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredIsVisible
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGroup> lockByClusterVisIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("isVisible") boolean requiredIsVisible) {
		return( cfsec31SecGroupRepository.lockByClusterVisIdx(requiredClusterId,
			requiredIsVisible));
	}

	/**
	 *	ICFSecSecGroupByClusterVisIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecGroup> lockByClusterVisIdx(ICFSecSecGroupByClusterVisIdxKey key) {
		return( cfsec31SecGroupRepository.lockByClusterVisIdx(key.getRequiredClusterId(), key.getRequiredIsVisible()));
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
	public CFSecJpaSecGroup lockByUNameIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("name") String requiredName) {
		return( cfsec31SecGroupRepository.lockByUNameIdx(requiredClusterId,
			requiredName));
	}

	/**
	 *	ICFSecSecGroupByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecGroup lockByUNameIdx(ICFSecSecGroupByUNameIdxKey key) {
		return( cfsec31SecGroupRepository.lockByUNameIdx(key.getRequiredClusterId(), key.getRequiredName()));
	}

	// CFSecSecGroup specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecGroupId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId) {
		cfsec31SecGroupRepository.deleteByIdIdx(requiredSecGroupId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		cfsec31SecGroupRepository.deleteByClusterIdx(requiredClusterId);
	}

	/**
	 *	ICFSecSecGroupByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecGroupByClusterIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByClusterIdx(ICFSecSecGroupByClusterIdxKey key) {
		cfsec31SecGroupRepository.deleteByClusterIdx(key.getRequiredClusterId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredIsVisible
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByClusterVisIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("isVisible") boolean requiredIsVisible) {
		cfsec31SecGroupRepository.deleteByClusterVisIdx(requiredClusterId,
			requiredIsVisible);
	}

	/**
	 *	ICFSecSecGroupByClusterVisIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecGroupByClusterVisIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByClusterVisIdx(ICFSecSecGroupByClusterVisIdxKey key) {
		cfsec31SecGroupRepository.deleteByClusterVisIdx(key.getRequiredClusterId(), key.getRequiredIsVisible());
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
		cfsec31SecGroupRepository.deleteByUNameIdx(requiredClusterId,
			requiredName);
	}

	/**
	 *	ICFSecSecGroupByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecGroupByUNameIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUNameIdx(ICFSecSecGroupByUNameIdxKey key) {
		cfsec31SecGroupRepository.deleteByUNameIdx(key.getRequiredClusterId(), key.getRequiredName());
	}
}

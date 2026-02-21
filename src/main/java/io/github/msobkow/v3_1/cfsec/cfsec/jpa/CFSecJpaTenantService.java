// Description: Java 25 Spring JPA Service for Tenant

/*
 *	io.github.msobkow.CFSec
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

package io.github.msobkow.v3_1.cfsec.cfsec.jpa;

import java.io.Serializable;
import java.math.*;
import java.time.*;
import java.util.*;
import jakarta.persistence.*;
import io.github.msobkow.v3_1.cflib.*;
import io.github.msobkow.v3_1.cflib.dbutil.*;
import io.github.msobkow.v3_1.cflib.xml.CFLibXmlUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import io.github.msobkow.v3_1.cfsec.cfsec.*;

/**
 *	Service for the CFSecTenant entities defined in io.github.msobkow.v3_1.cfsec.cfsec.jpa
 *	using the CFSecTenantRepository to access them.
 */
@Service("cfsec31JpaTenantService")
public class CFSecJpaTenantService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaTenantRepository cfsec31TenantRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaTenant, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTenant create(CFSecJpaTenant data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		CFLibDbKeyHash256 originalRequiredId = data.getRequiredId();
		boolean generatedRequiredId = false;
		if(data.getRequiredClusterId() == null || data.getRequiredClusterId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredClusterId");
		}
		if(data.getRequiredTenantName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredTenantName");
		}
		try {
			if (data.getRequiredId() == null || data.getRequiredId().isNull()) {
				data.setRequiredId(new CFLibDbKeyHash256(0));
				generatedRequiredId = true;
			}
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31TenantRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFSecJpaTenant)(cfsec31TenantRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfsec31TenantRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredId) {
					data.setRequiredId(originalRequiredId);
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
	public CFSecJpaTenant update(CFSecJpaTenant data) {
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
		if(data.getRequiredTenantName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredTenantName");
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaTenant existing = cfsec31TenantRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecTenant to existing object
		existing.setRequiredContainerCluster(data.getRequiredContainerCluster());
		// Apply data columns of CFSecTenant to existing object
		existing.setRequiredTenantName(data.getRequiredTenantName());
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31TenantRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTenant find(@Param("id") CFLibDbKeyHash256 requiredId) {
		return( cfsec31TenantRepository.get(requiredId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTenant> findAll() {
		return( cfsec31TenantRepository.findAll() );
	}

	// CFSecTenant specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecTenantByClusterIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return List&lt;CFSecJpaTenant&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTenant> findByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		return( cfsec31TenantRepository.findByClusterIdx(requiredClusterId));
	}

	/**
	 *	ICFSecTenantByClusterIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecTenantByClusterIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTenant> findByClusterIdx(ICFSecTenantByClusterIdxKey key) {
		return( cfsec31TenantRepository.findByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecTenantByUNameIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *		@param requiredTenantName
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTenant findByUNameIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("tenantName") String requiredTenantName) {
		return( cfsec31TenantRepository.findByUNameIdx(requiredClusterId,
			requiredTenantName));
	}

	/**
	 *	ICFSecTenantByUNameIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecTenantByUNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTenant findByUNameIdx(ICFSecTenantByUNameIdxKey key) {
		return( cfsec31TenantRepository.findByUNameIdx(key.getRequiredClusterId(), key.getRequiredTenantName()));
	}

	// CFSecTenant specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTenant lockByIdIdx(@Param("id") CFLibDbKeyHash256 requiredId) {
		return( cfsec31TenantRepository.lockByIdIdx(requiredId));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTenant> lockByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		return( cfsec31TenantRepository.lockByClusterIdx(requiredClusterId));
	}

	/**
	 *	ICFSecTenantByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTenant> lockByClusterIdx(ICFSecTenantByClusterIdxKey key) {
		return( cfsec31TenantRepository.lockByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredTenantName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTenant lockByUNameIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("tenantName") String requiredTenantName) {
		return( cfsec31TenantRepository.lockByUNameIdx(requiredClusterId,
			requiredTenantName));
	}

	/**
	 *	ICFSecTenantByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTenant lockByUNameIdx(ICFSecTenantByUNameIdxKey key) {
		return( cfsec31TenantRepository.lockByUNameIdx(key.getRequiredClusterId(), key.getRequiredTenantName()));
	}

	// CFSecTenant specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("id") CFLibDbKeyHash256 requiredId) {
		cfsec31TenantRepository.deleteByIdIdx(requiredId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		cfsec31TenantRepository.deleteByClusterIdx(requiredClusterId);
	}

	/**
	 *	ICFSecTenantByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecTenantByClusterIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByClusterIdx(ICFSecTenantByClusterIdxKey key) {
		cfsec31TenantRepository.deleteByClusterIdx(key.getRequiredClusterId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredTenantName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUNameIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("tenantName") String requiredTenantName) {
		cfsec31TenantRepository.deleteByUNameIdx(requiredClusterId,
			requiredTenantName);
	}

	/**
	 *	ICFSecTenantByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecTenantByUNameIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUNameIdx(ICFSecTenantByUNameIdxKey key) {
		cfsec31TenantRepository.deleteByUNameIdx(key.getRequiredClusterId(), key.getRequiredTenantName());
	}
}

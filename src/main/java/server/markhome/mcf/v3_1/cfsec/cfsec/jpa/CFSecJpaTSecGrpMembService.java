// Description: Java 25 Spring JPA Service for TSecGrpMemb

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
 *	Service for the CFSecTSecGrpMemb entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecTSecGrpMembRepository to access them.
 */
@Service("cfsec31JpaTSecGrpMembService")
public class CFSecJpaTSecGrpMembService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaTSecGrpMembRepository cfsec31TSecGrpMembRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaTSecGrpMemb, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGrpMemb create(CFSecJpaTSecGrpMemb data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		CFLibDbKeyHash256 originalRequiredTSecGrpMembId = data.getRequiredTSecGrpMembId();
		boolean generatedRequiredTSecGrpMembId = false;
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
		if(data.getRequiredSecUserId() == null || data.getRequiredSecUserId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredSecUserId");
		}
		try {
			if (data.getRequiredTSecGrpMembId() == null || data.getRequiredTSecGrpMembId().isNull()) {
				data.setRequiredTSecGrpMembId(new CFLibDbKeyHash256(0));
				generatedRequiredTSecGrpMembId = true;
			}
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31TSecGrpMembRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFSecJpaTSecGrpMemb)(cfsec31TSecGrpMembRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfsec31TSecGrpMembRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredTSecGrpMembId) {
					data.setRequiredTSecGrpMembId(originalRequiredTSecGrpMembId);
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
	public CFSecJpaTSecGrpMemb update(CFSecJpaTSecGrpMemb data) {
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
		if(data.getRequiredSecUserId() == null || data.getRequiredSecUserId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredSecUserId");
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaTSecGrpMemb existing = cfsec31TSecGrpMembRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecTSecGrpMemb to existing object
		existing.setRequiredOwnerTenant(data.getRequiredOwnerTenant());
		existing.setRequiredContainerGroup(data.getRequiredContainerGroup());
		existing.setRequiredParentUser(data.getRequiredParentUser());
		// Apply data columns of CFSecTSecGrpMemb to existing object
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31TSecGrpMembRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredTSecGrpMembId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGrpMemb find(@Param("tSecGrpMembId") CFLibDbKeyHash256 requiredTSecGrpMembId) {
		return( cfsec31TSecGrpMembRepository.get(requiredTSecGrpMembId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpMemb> findAll() {
		return( cfsec31TSecGrpMembRepository.findAll() );
	}

	// CFSecTSecGrpMemb specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecTSecGrpMembByTenantIdxKey as arguments.
	 *
	 *		@param requiredTenantId
	 *
	 *		@return List&lt;CFSecJpaTSecGrpMemb&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpMemb> findByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId) {
		return( cfsec31TSecGrpMembRepository.findByTenantIdx(requiredTenantId));
	}

	/**
	 *	ICFSecTSecGrpMembByTenantIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecTSecGrpMembByTenantIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpMemb> findByTenantIdx(ICFSecTSecGrpMembByTenantIdxKey key) {
		return( cfsec31TSecGrpMembRepository.findByTenantIdx(key.getRequiredTenantId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecTSecGrpMembByGroupIdxKey as arguments.
	 *
	 *		@param requiredTSecGroupId
	 *
	 *		@return List&lt;CFSecJpaTSecGrpMemb&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpMemb> findByGroupIdx(@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId) {
		return( cfsec31TSecGrpMembRepository.findByGroupIdx(requiredTSecGroupId));
	}

	/**
	 *	ICFSecTSecGrpMembByGroupIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecTSecGrpMembByGroupIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpMemb> findByGroupIdx(ICFSecTSecGrpMembByGroupIdxKey key) {
		return( cfsec31TSecGrpMembRepository.findByGroupIdx(key.getRequiredTSecGroupId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecTSecGrpMembByUserIdxKey as arguments.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return List&lt;CFSecJpaTSecGrpMemb&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpMemb> findByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31TSecGrpMembRepository.findByUserIdx(requiredSecUserId));
	}

	/**
	 *	ICFSecTSecGrpMembByUserIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecTSecGrpMembByUserIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpMemb> findByUserIdx(ICFSecTSecGrpMembByUserIdxKey key) {
		return( cfsec31TSecGrpMembRepository.findByUserIdx(key.getRequiredSecUserId()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecTSecGrpMembByUUserIdxKey as arguments.
	 *
	 *		@param requiredTenantId
	 *		@param requiredTSecGroupId
	 *		@param requiredSecUserId
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGrpMemb findByUUserIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31TSecGrpMembRepository.findByUUserIdx(requiredTenantId,
			requiredTSecGroupId,
			requiredSecUserId));
	}

	/**
	 *	ICFSecTSecGrpMembByUUserIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecTSecGrpMembByUUserIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGrpMemb findByUUserIdx(ICFSecTSecGrpMembByUUserIdxKey key) {
		return( cfsec31TSecGrpMembRepository.findByUUserIdx(key.getRequiredTenantId(), key.getRequiredTSecGroupId(), key.getRequiredSecUserId()));
	}

	// CFSecTSecGrpMemb specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTSecGrpMembId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGrpMemb lockByIdIdx(@Param("tSecGrpMembId") CFLibDbKeyHash256 requiredTSecGrpMembId) {
		return( cfsec31TSecGrpMembRepository.lockByIdIdx(requiredTSecGrpMembId));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpMemb> lockByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId) {
		return( cfsec31TSecGrpMembRepository.lockByTenantIdx(requiredTenantId));
	}

	/**
	 *	ICFSecTSecGrpMembByTenantIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpMemb> lockByTenantIdx(ICFSecTSecGrpMembByTenantIdxKey key) {
		return( cfsec31TSecGrpMembRepository.lockByTenantIdx(key.getRequiredTenantId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTSecGroupId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpMemb> lockByGroupIdx(@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId) {
		return( cfsec31TSecGrpMembRepository.lockByGroupIdx(requiredTSecGroupId));
	}

	/**
	 *	ICFSecTSecGrpMembByGroupIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpMemb> lockByGroupIdx(ICFSecTSecGrpMembByGroupIdxKey key) {
		return( cfsec31TSecGrpMembRepository.lockByGroupIdx(key.getRequiredTSecGroupId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpMemb> lockByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31TSecGrpMembRepository.lockByUserIdx(requiredSecUserId));
	}

	/**
	 *	ICFSecTSecGrpMembByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaTSecGrpMemb> lockByUserIdx(ICFSecTSecGrpMembByUserIdxKey key) {
		return( cfsec31TSecGrpMembRepository.lockByUserIdx(key.getRequiredSecUserId()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *		@param requiredTSecGroupId
	 *		@param requiredSecUserId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGrpMemb lockByUUserIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31TSecGrpMembRepository.lockByUUserIdx(requiredTenantId,
			requiredTSecGroupId,
			requiredSecUserId));
	}

	/**
	 *	ICFSecTSecGrpMembByUUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaTSecGrpMemb lockByUUserIdx(ICFSecTSecGrpMembByUUserIdxKey key) {
		return( cfsec31TSecGrpMembRepository.lockByUUserIdx(key.getRequiredTenantId(), key.getRequiredTSecGroupId(), key.getRequiredSecUserId()));
	}

	// CFSecTSecGrpMemb specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTSecGrpMembId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("tSecGrpMembId") CFLibDbKeyHash256 requiredTSecGrpMembId) {
		cfsec31TSecGrpMembRepository.deleteByIdIdx(requiredTSecGrpMembId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId) {
		cfsec31TSecGrpMembRepository.deleteByTenantIdx(requiredTenantId);
	}

	/**
	 *	ICFSecTSecGrpMembByTenantIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecTSecGrpMembByTenantIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByTenantIdx(ICFSecTSecGrpMembByTenantIdxKey key) {
		cfsec31TSecGrpMembRepository.deleteByTenantIdx(key.getRequiredTenantId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTSecGroupId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByGroupIdx(@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId) {
		cfsec31TSecGrpMembRepository.deleteByGroupIdx(requiredTSecGroupId);
	}

	/**
	 *	ICFSecTSecGrpMembByGroupIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecTSecGrpMembByGroupIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByGroupIdx(ICFSecTSecGrpMembByGroupIdxKey key) {
		cfsec31TSecGrpMembRepository.deleteByGroupIdx(key.getRequiredTSecGroupId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		cfsec31TSecGrpMembRepository.deleteByUserIdx(requiredSecUserId);
	}

	/**
	 *	ICFSecTSecGrpMembByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecTSecGrpMembByUserIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUserIdx(ICFSecTSecGrpMembByUserIdxKey key) {
		cfsec31TSecGrpMembRepository.deleteByUserIdx(key.getRequiredSecUserId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *		@param requiredTSecGroupId
	 *		@param requiredSecUserId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUUserIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		cfsec31TSecGrpMembRepository.deleteByUUserIdx(requiredTenantId,
			requiredTSecGroupId,
			requiredSecUserId);
	}

	/**
	 *	ICFSecTSecGrpMembByUUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecTSecGrpMembByUUserIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUUserIdx(ICFSecTSecGrpMembByUUserIdxKey key) {
		cfsec31TSecGrpMembRepository.deleteByUUserIdx(key.getRequiredTenantId(), key.getRequiredTSecGroupId(), key.getRequiredSecUserId());
	}
}

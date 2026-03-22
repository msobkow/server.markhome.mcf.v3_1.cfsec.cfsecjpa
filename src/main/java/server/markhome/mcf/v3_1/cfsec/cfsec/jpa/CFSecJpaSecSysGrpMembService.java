// Description: Java 25 Spring JPA Service for SecSysGrpMemb

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
 *	Service for the CFSecSecSysGrpMemb entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecSecSysGrpMembRepository to access them.
 */
@Service("cfsec31JpaSecSysGrpMembService")
public class CFSecJpaSecSysGrpMembService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaSecSysGrpMembRepository cfsec31SecSysGrpMembRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaSecSysGrpMemb, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrpMemb create(CFSecJpaSecSysGrpMemb data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		if (data.getPKey() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.getPKey()");
		}
		try {
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31SecSysGrpMembRepository.existsById((CFSecJpaSecSysGrpMembPKey)data.getPKey())) {
				return( (CFSecJpaSecSysGrpMemb)(cfsec31SecSysGrpMembRepository.findById((CFSecJpaSecSysGrpMembPKey)(data.getPKey())).get()));
			}
			return cfsec31SecSysGrpMembRepository.save(data);
		}
		catch(Exception ex) {
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
	public CFSecJpaSecSysGrpMemb update(CFSecJpaSecSysGrpMemb data) {
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
		// Ensure the entity exists and that the revision matches
		CFSecJpaSecSysGrpMemb existing = cfsec31SecSysGrpMembRepository.findById((CFSecJpaSecSysGrpMembPKey)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecSecSysGrpMemb to existing object
		// Apply data columns of CFSecSecSysGrpMemb to existing object
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31SecSysGrpMembRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecSysGrpId
	 *		@param requiredSecUserId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrpMemb find(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecSysGrpMembRepository.get(requiredSecSysGrpId,
			requiredSecUserId));
	}

	/**
	 *	ICFSecSecSysGrpMembPKey based find method for object-based access.
	 *
	 *		@param key The key of the entity to be find.
	 *
	 *		@return The entity find, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrpMemb find(ICFSecSecSysGrpMembPKey key) {
		return( cfsec31SecSysGrpMembRepository.get(key.getRequiredSecSysGrpId(), key.getRequiredSecUserId()));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSysGrpMemb> findAll() {
		return( cfsec31SecSysGrpMembRepository.findAll() );
	}

	// CFSecSecSysGrpMemb specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecSysGrpMembBySysGrpIdxKey as arguments.
	 *
	 *		@param requiredSecSysGrpId
	 *
	 *		@return List&lt;CFSecJpaSecSysGrpMemb&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSysGrpMemb> findBySysGrpIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId) {
		return( cfsec31SecSysGrpMembRepository.findBySysGrpIdx(requiredSecSysGrpId));
	}

	/**
	 *	ICFSecSecSysGrpMembBySysGrpIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecSysGrpMembBySysGrpIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSysGrpMemb> findBySysGrpIdx(ICFSecSecSysGrpMembBySysGrpIdxKey key) {
		return( cfsec31SecSysGrpMembRepository.findBySysGrpIdx(key.getRequiredSecSysGrpId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecSysGrpMembByUserIdxKey as arguments.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return List&lt;CFSecJpaSecSysGrpMemb&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSysGrpMemb> findByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecSysGrpMembRepository.findByUserIdx(requiredSecUserId));
	}

	/**
	 *	ICFSecSecSysGrpMembByUserIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecSysGrpMembByUserIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSysGrpMemb> findByUserIdx(ICFSecSecSysGrpMembByUserIdxKey key) {
		return( cfsec31SecSysGrpMembRepository.findByUserIdx(key.getRequiredSecUserId()));
	}

	// CFSecSecSysGrpMemb specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 *		@param requiredSecUserId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrpMemb lockByIdIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecSysGrpMembRepository.lockByIdIdx(requiredSecSysGrpId,
			requiredSecUserId));
	}

	/**
	 *	ICFSecSecSysGrpMembPKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrpMemb lockByIdIdx(ICFSecSecSysGrpMembPKey key) {
		return( cfsec31SecSysGrpMembRepository.lockByIdIdx(key.getRequiredSecSysGrpId(), key.getRequiredSecUserId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSysGrpMemb> lockBySysGrpIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId) {
		return( cfsec31SecSysGrpMembRepository.lockBySysGrpIdx(requiredSecSysGrpId));
	}

	/**
	 *	ICFSecSecSysGrpMembBySysGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSysGrpMemb> lockBySysGrpIdx(ICFSecSecSysGrpMembBySysGrpIdxKey key) {
		return( cfsec31SecSysGrpMembRepository.lockBySysGrpIdx(key.getRequiredSecSysGrpId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSysGrpMemb> lockByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecSysGrpMembRepository.lockByUserIdx(requiredSecUserId));
	}

	/**
	 *	ICFSecSecSysGrpMembByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSysGrpMemb> lockByUserIdx(ICFSecSecSysGrpMembByUserIdxKey key) {
		return( cfsec31SecSysGrpMembRepository.lockByUserIdx(key.getRequiredSecUserId()));
	}

	// CFSecSecSysGrpMemb specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 *		@param requiredSecUserId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		cfsec31SecSysGrpMembRepository.deleteByIdIdx(requiredSecSysGrpId,
			requiredSecUserId);
	}

	/**
	 *	ICFSecSecSysGrpMembByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecSysGrpMembByIdIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(ICFSecSecSysGrpMembPKey key) {
		cfsec31SecSysGrpMembRepository.deleteByIdIdx(key.getRequiredSecSysGrpId(), key.getRequiredSecUserId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteBySysGrpIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId) {
		cfsec31SecSysGrpMembRepository.deleteBySysGrpIdx(requiredSecSysGrpId);
	}

	/**
	 *	ICFSecSecSysGrpMembBySysGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecSysGrpMembBySysGrpIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteBySysGrpIdx(ICFSecSecSysGrpMembBySysGrpIdxKey key) {
		cfsec31SecSysGrpMembRepository.deleteBySysGrpIdx(key.getRequiredSecSysGrpId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		cfsec31SecSysGrpMembRepository.deleteByUserIdx(requiredSecUserId);
	}

	/**
	 *	ICFSecSecSysGrpMembByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecSysGrpMembByUserIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUserIdx(ICFSecSecSysGrpMembByUserIdxKey key) {
		cfsec31SecSysGrpMembRepository.deleteByUserIdx(key.getRequiredSecUserId());
	}
}

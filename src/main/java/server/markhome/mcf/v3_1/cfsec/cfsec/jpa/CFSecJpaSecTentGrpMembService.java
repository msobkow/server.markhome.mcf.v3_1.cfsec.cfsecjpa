// Description: Java 25 Spring JPA Service for SecTentGrpMemb

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
 *	Service for the CFSecSecTentGrpMemb entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecSecTentGrpMembRepository to access them.
 */
@Service("cfsec31JpaSecTentGrpMembService")
public class CFSecJpaSecTentGrpMembService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaSecTentGrpMembRepository cfsec31SecTentGrpMembRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaSecTentGrpMemb, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecTentGrpMemb create(CFSecJpaSecTentGrpMemb data) {
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
			if(data.getPKey() != null && cfsec31SecTentGrpMembRepository.existsById((CFSecJpaSecTentGrpMembPKey)data.getPKey())) {
				return( (CFSecJpaSecTentGrpMemb)(cfsec31SecTentGrpMembRepository.findById((CFSecJpaSecTentGrpMembPKey)(data.getPKey())).get()));
			}
			return cfsec31SecTentGrpMembRepository.save(data);
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
	public CFSecJpaSecTentGrpMemb update(CFSecJpaSecTentGrpMemb data) {
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
		CFSecJpaSecTentGrpMemb existing = cfsec31SecTentGrpMembRepository.findById((CFSecJpaSecTentGrpMembPKey)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecSecTentGrpMemb to existing object
		// Apply data columns of CFSecSecTentGrpMemb to existing object
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31SecTentGrpMembRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecTentGrpId
	 *		@param requiredLoginId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecTentGrpMemb find(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId,
		@Param("loginId") String requiredLoginId) {
		return( cfsec31SecTentGrpMembRepository.get(requiredSecTentGrpId,
			requiredLoginId));
	}

	/**
	 *	ICFSecSecTentGrpMembPKey based find method for object-based access.
	 *
	 *		@param key The key of the entity to be find.
	 *
	 *		@return The entity find, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecTentGrpMemb find(ICFSecSecTentGrpMembPKey key) {
		return( cfsec31SecTentGrpMembRepository.get(key.getRequiredSecTentGrpId(), key.getRequiredLoginId()));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecTentGrpMemb> findAll() {
		return( cfsec31SecTentGrpMembRepository.findAll() );
	}

	// CFSecSecTentGrpMemb specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecTentGrpMembByTentGrpIdxKey as arguments.
	 *
	 *		@param requiredSecTentGrpId
	 *
	 *		@return List&lt;CFSecJpaSecTentGrpMemb&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecTentGrpMemb> findByTentGrpIdx(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId) {
		return( cfsec31SecTentGrpMembRepository.findByTentGrpIdx(requiredSecTentGrpId));
	}

	/**
	 *	ICFSecSecTentGrpMembByTentGrpIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecTentGrpMembByTentGrpIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecTentGrpMemb> findByTentGrpIdx(ICFSecSecTentGrpMembByTentGrpIdxKey key) {
		return( cfsec31SecTentGrpMembRepository.findByTentGrpIdx(key.getRequiredSecTentGrpId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecTentGrpMembByUserIdxKey as arguments.
	 *
	 *		@param requiredLoginId
	 *
	 *		@return List&lt;CFSecJpaSecTentGrpMemb&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecTentGrpMemb> findByUserIdx(@Param("loginId") String requiredLoginId) {
		return( cfsec31SecTentGrpMembRepository.findByUserIdx(requiredLoginId));
	}

	/**
	 *	ICFSecSecTentGrpMembByUserIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecTentGrpMembByUserIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecTentGrpMemb> findByUserIdx(ICFSecSecTentGrpMembByUserIdxKey key) {
		return( cfsec31SecTentGrpMembRepository.findByUserIdx(key.getRequiredLoginId()));
	}

	// CFSecSecTentGrpMemb specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecTentGrpId
	 *		@param requiredLoginId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecTentGrpMemb lockByIdIdx(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId,
		@Param("loginId") String requiredLoginId) {
		return( cfsec31SecTentGrpMembRepository.lockByIdIdx(requiredSecTentGrpId,
			requiredLoginId));
	}

	/**
	 *	ICFSecSecTentGrpMembPKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecTentGrpMemb lockByIdIdx(ICFSecSecTentGrpMembPKey key) {
		return( cfsec31SecTentGrpMembRepository.lockByIdIdx(key.getRequiredSecTentGrpId(), key.getRequiredLoginId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecTentGrpId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecTentGrpMemb> lockByTentGrpIdx(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId) {
		return( cfsec31SecTentGrpMembRepository.lockByTentGrpIdx(requiredSecTentGrpId));
	}

	/**
	 *	ICFSecSecTentGrpMembByTentGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecTentGrpMemb> lockByTentGrpIdx(ICFSecSecTentGrpMembByTentGrpIdxKey key) {
		return( cfsec31SecTentGrpMembRepository.lockByTentGrpIdx(key.getRequiredSecTentGrpId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredLoginId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecTentGrpMemb> lockByUserIdx(@Param("loginId") String requiredLoginId) {
		return( cfsec31SecTentGrpMembRepository.lockByUserIdx(requiredLoginId));
	}

	/**
	 *	ICFSecSecTentGrpMembByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecTentGrpMemb> lockByUserIdx(ICFSecSecTentGrpMembByUserIdxKey key) {
		return( cfsec31SecTentGrpMembRepository.lockByUserIdx(key.getRequiredLoginId()));
	}

	// CFSecSecTentGrpMemb specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecTentGrpId
	 *		@param requiredLoginId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId,
		@Param("loginId") String requiredLoginId) {
		cfsec31SecTentGrpMembRepository.deleteByIdIdx(requiredSecTentGrpId,
			requiredLoginId);
	}

	/**
	 *	ICFSecSecTentGrpMembByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecTentGrpMembByIdIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(ICFSecSecTentGrpMembPKey key) {
		cfsec31SecTentGrpMembRepository.deleteByIdIdx(key.getRequiredSecTentGrpId(), key.getRequiredLoginId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecTentGrpId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByTentGrpIdx(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId) {
		cfsec31SecTentGrpMembRepository.deleteByTentGrpIdx(requiredSecTentGrpId);
	}

	/**
	 *	ICFSecSecTentGrpMembByTentGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecTentGrpMembByTentGrpIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByTentGrpIdx(ICFSecSecTentGrpMembByTentGrpIdxKey key) {
		cfsec31SecTentGrpMembRepository.deleteByTentGrpIdx(key.getRequiredSecTentGrpId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredLoginId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUserIdx(@Param("loginId") String requiredLoginId) {
		cfsec31SecTentGrpMembRepository.deleteByUserIdx(requiredLoginId);
	}

	/**
	 *	ICFSecSecTentGrpMembByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecTentGrpMembByUserIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUserIdx(ICFSecSecTentGrpMembByUserIdxKey key) {
		cfsec31SecTentGrpMembRepository.deleteByUserIdx(key.getRequiredLoginId());
	}
}

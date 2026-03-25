// Description: Java 25 Spring JPA Service for SecClusGrpMemb

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
 *	Service for the CFSecSecClusGrpMemb entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecSecClusGrpMembRepository to access them.
 */
@Service("cfsec31JpaSecClusGrpMembService")
public class CFSecJpaSecClusGrpMembService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaSecClusGrpMembRepository cfsec31SecClusGrpMembRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaSecClusGrpMemb, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecClusGrpMemb create(CFSecJpaSecClusGrpMemb data) {
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
			if(data.getPKey() != null && cfsec31SecClusGrpMembRepository.existsById((CFSecJpaSecClusGrpMembPKey)data.getPKey())) {
				return( (CFSecJpaSecClusGrpMemb)(cfsec31SecClusGrpMembRepository.findById((CFSecJpaSecClusGrpMembPKey)(data.getPKey())).get()));
			}
			return cfsec31SecClusGrpMembRepository.save(data);
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
	public CFSecJpaSecClusGrpMemb update(CFSecJpaSecClusGrpMemb data) {
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
		CFSecJpaSecClusGrpMemb existing = cfsec31SecClusGrpMembRepository.findById((CFSecJpaSecClusGrpMembPKey)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecSecClusGrpMemb to existing object
		// Apply data columns of CFSecSecClusGrpMemb to existing object
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31SecClusGrpMembRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecClusGrpId
	 *		@param requiredLoginId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecClusGrpMemb find(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId,
		@Param("loginId") String requiredLoginId) {
		return( cfsec31SecClusGrpMembRepository.get(requiredSecClusGrpId,
			requiredLoginId));
	}

	/**
	 *	ICFSecSecClusGrpMembPKey based find method for object-based access.
	 *
	 *		@param key The key of the entity to be find.
	 *
	 *		@return The entity find, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecClusGrpMemb find(ICFSecSecClusGrpMembPKey key) {
		return( cfsec31SecClusGrpMembRepository.get(key.getRequiredSecClusGrpId(), key.getRequiredLoginId()));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrpMemb> findAll() {
		return( cfsec31SecClusGrpMembRepository.findAll() );
	}

	// CFSecSecClusGrpMemb specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecClusGrpMembByClusGrpIdxKey as arguments.
	 *
	 *		@param requiredSecClusGrpId
	 *
	 *		@return List&lt;CFSecJpaSecClusGrpMemb&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrpMemb> findByClusGrpIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId) {
		return( cfsec31SecClusGrpMembRepository.findByClusGrpIdx(requiredSecClusGrpId));
	}

	/**
	 *	ICFSecSecClusGrpMembByClusGrpIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecClusGrpMembByClusGrpIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrpMemb> findByClusGrpIdx(ICFSecSecClusGrpMembByClusGrpIdxKey key) {
		return( cfsec31SecClusGrpMembRepository.findByClusGrpIdx(key.getRequiredSecClusGrpId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecClusGrpMembByLoginIdxKey as arguments.
	 *
	 *		@param requiredLoginId
	 *
	 *		@return List&lt;CFSecJpaSecClusGrpMemb&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrpMemb> findByLoginIdx(@Param("loginId") String requiredLoginId) {
		return( cfsec31SecClusGrpMembRepository.findByLoginIdx(requiredLoginId));
	}

	/**
	 *	ICFSecSecClusGrpMembByLoginIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecClusGrpMembByLoginIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrpMemb> findByLoginIdx(ICFSecSecClusGrpMembByLoginIdxKey key) {
		return( cfsec31SecClusGrpMembRepository.findByLoginIdx(key.getRequiredLoginId()));
	}

	// CFSecSecClusGrpMemb specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecClusGrpId
	 *		@param requiredLoginId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecClusGrpMemb lockByIdIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId,
		@Param("loginId") String requiredLoginId) {
		return( cfsec31SecClusGrpMembRepository.lockByIdIdx(requiredSecClusGrpId,
			requiredLoginId));
	}

	/**
	 *	ICFSecSecClusGrpMembPKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecClusGrpMemb lockByIdIdx(ICFSecSecClusGrpMembPKey key) {
		return( cfsec31SecClusGrpMembRepository.lockByIdIdx(key.getRequiredSecClusGrpId(), key.getRequiredLoginId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecClusGrpId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrpMemb> lockByClusGrpIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId) {
		return( cfsec31SecClusGrpMembRepository.lockByClusGrpIdx(requiredSecClusGrpId));
	}

	/**
	 *	ICFSecSecClusGrpMembByClusGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrpMemb> lockByClusGrpIdx(ICFSecSecClusGrpMembByClusGrpIdxKey key) {
		return( cfsec31SecClusGrpMembRepository.lockByClusGrpIdx(key.getRequiredSecClusGrpId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredLoginId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrpMemb> lockByLoginIdx(@Param("loginId") String requiredLoginId) {
		return( cfsec31SecClusGrpMembRepository.lockByLoginIdx(requiredLoginId));
	}

	/**
	 *	ICFSecSecClusGrpMembByLoginIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrpMemb> lockByLoginIdx(ICFSecSecClusGrpMembByLoginIdxKey key) {
		return( cfsec31SecClusGrpMembRepository.lockByLoginIdx(key.getRequiredLoginId()));
	}

	// CFSecSecClusGrpMemb specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecClusGrpId
	 *		@param requiredLoginId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId,
		@Param("loginId") String requiredLoginId) {
		cfsec31SecClusGrpMembRepository.deleteByIdIdx(requiredSecClusGrpId,
			requiredLoginId);
	}

	/**
	 *	ICFSecSecClusGrpMembByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecClusGrpMembByIdIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(ICFSecSecClusGrpMembPKey key) {
		cfsec31SecClusGrpMembRepository.deleteByIdIdx(key.getRequiredSecClusGrpId(), key.getRequiredLoginId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecClusGrpId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByClusGrpIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId) {
		cfsec31SecClusGrpMembRepository.deleteByClusGrpIdx(requiredSecClusGrpId);
	}

	/**
	 *	ICFSecSecClusGrpMembByClusGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecClusGrpMembByClusGrpIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByClusGrpIdx(ICFSecSecClusGrpMembByClusGrpIdxKey key) {
		cfsec31SecClusGrpMembRepository.deleteByClusGrpIdx(key.getRequiredSecClusGrpId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredLoginId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByLoginIdx(@Param("loginId") String requiredLoginId) {
		cfsec31SecClusGrpMembRepository.deleteByLoginIdx(requiredLoginId);
	}

	/**
	 *	ICFSecSecClusGrpMembByLoginIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecClusGrpMembByLoginIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByLoginIdx(ICFSecSecClusGrpMembByLoginIdxKey key) {
		cfsec31SecClusGrpMembRepository.deleteByLoginIdx(key.getRequiredLoginId());
	}
}

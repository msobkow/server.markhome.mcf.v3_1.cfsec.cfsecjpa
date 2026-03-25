// Description: Java 25 Spring JPA Service for SecClusGrpInc

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
 *	Service for the CFSecSecClusGrpInc entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecSecClusGrpIncRepository to access them.
 */
@Service("cfsec31JpaSecClusGrpIncService")
public class CFSecJpaSecClusGrpIncService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaSecClusGrpIncRepository cfsec31SecClusGrpIncRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaSecClusGrpInc, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecClusGrpInc create(CFSecJpaSecClusGrpInc data) {
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
			if(data.getPKey() != null && cfsec31SecClusGrpIncRepository.existsById((CFSecJpaSecClusGrpIncPKey)data.getPKey())) {
				return( (CFSecJpaSecClusGrpInc)(cfsec31SecClusGrpIncRepository.findById((CFSecJpaSecClusGrpIncPKey)(data.getPKey())).get()));
			}
			return cfsec31SecClusGrpIncRepository.save(data);
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
	public CFSecJpaSecClusGrpInc update(CFSecJpaSecClusGrpInc data) {
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
		CFSecJpaSecClusGrpInc existing = cfsec31SecClusGrpIncRepository.findById((CFSecJpaSecClusGrpIncPKey)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecSecClusGrpInc to existing object
		// Apply data columns of CFSecSecClusGrpInc to existing object
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31SecClusGrpIncRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecClusGrpId
	 *		@param requiredInclName
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecClusGrpInc find(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId,
		@Param("inclName") String requiredInclName) {
		return( cfsec31SecClusGrpIncRepository.get(requiredSecClusGrpId,
			requiredInclName));
	}

	/**
	 *	ICFSecSecClusGrpIncPKey based find method for object-based access.
	 *
	 *		@param key The key of the entity to be find.
	 *
	 *		@return The entity find, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecClusGrpInc find(ICFSecSecClusGrpIncPKey key) {
		return( cfsec31SecClusGrpIncRepository.get(key.getRequiredSecClusGrpId(), key.getRequiredInclName()));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrpInc> findAll() {
		return( cfsec31SecClusGrpIncRepository.findAll() );
	}

	// CFSecSecClusGrpInc specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecClusGrpIncByClusGrpIdxKey as arguments.
	 *
	 *		@param requiredSecClusGrpId
	 *
	 *		@return List&lt;CFSecJpaSecClusGrpInc&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrpInc> findByClusGrpIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId) {
		return( cfsec31SecClusGrpIncRepository.findByClusGrpIdx(requiredSecClusGrpId));
	}

	/**
	 *	ICFSecSecClusGrpIncByClusGrpIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecClusGrpIncByClusGrpIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrpInc> findByClusGrpIdx(ICFSecSecClusGrpIncByClusGrpIdxKey key) {
		return( cfsec31SecClusGrpIncRepository.findByClusGrpIdx(key.getRequiredSecClusGrpId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecClusGrpIncByNameIdxKey as arguments.
	 *
	 *		@param requiredInclName
	 *
	 *		@return List&lt;CFSecJpaSecClusGrpInc&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrpInc> findByNameIdx(@Param("inclName") String requiredInclName) {
		return( cfsec31SecClusGrpIncRepository.findByNameIdx(requiredInclName));
	}

	/**
	 *	ICFSecSecClusGrpIncByNameIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecClusGrpIncByNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrpInc> findByNameIdx(ICFSecSecClusGrpIncByNameIdxKey key) {
		return( cfsec31SecClusGrpIncRepository.findByNameIdx(key.getRequiredInclName()));
	}

	// CFSecSecClusGrpInc specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecClusGrpId
	 *		@param requiredInclName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecClusGrpInc lockByIdIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId,
		@Param("inclName") String requiredInclName) {
		return( cfsec31SecClusGrpIncRepository.lockByIdIdx(requiredSecClusGrpId,
			requiredInclName));
	}

	/**
	 *	ICFSecSecClusGrpIncPKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecClusGrpInc lockByIdIdx(ICFSecSecClusGrpIncPKey key) {
		return( cfsec31SecClusGrpIncRepository.lockByIdIdx(key.getRequiredSecClusGrpId(), key.getRequiredInclName()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecClusGrpId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrpInc> lockByClusGrpIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId) {
		return( cfsec31SecClusGrpIncRepository.lockByClusGrpIdx(requiredSecClusGrpId));
	}

	/**
	 *	ICFSecSecClusGrpIncByClusGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrpInc> lockByClusGrpIdx(ICFSecSecClusGrpIncByClusGrpIdxKey key) {
		return( cfsec31SecClusGrpIncRepository.lockByClusGrpIdx(key.getRequiredSecClusGrpId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredInclName
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrpInc> lockByNameIdx(@Param("inclName") String requiredInclName) {
		return( cfsec31SecClusGrpIncRepository.lockByNameIdx(requiredInclName));
	}

	/**
	 *	ICFSecSecClusGrpIncByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecClusGrpInc> lockByNameIdx(ICFSecSecClusGrpIncByNameIdxKey key) {
		return( cfsec31SecClusGrpIncRepository.lockByNameIdx(key.getRequiredInclName()));
	}

	// CFSecSecClusGrpInc specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecClusGrpId
	 *		@param requiredInclName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId,
		@Param("inclName") String requiredInclName) {
		cfsec31SecClusGrpIncRepository.deleteByIdIdx(requiredSecClusGrpId,
			requiredInclName);
	}

	/**
	 *	ICFSecSecClusGrpIncByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecClusGrpIncByIdIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(ICFSecSecClusGrpIncPKey key) {
		cfsec31SecClusGrpIncRepository.deleteByIdIdx(key.getRequiredSecClusGrpId(), key.getRequiredInclName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecClusGrpId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByClusGrpIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId) {
		cfsec31SecClusGrpIncRepository.deleteByClusGrpIdx(requiredSecClusGrpId);
	}

	/**
	 *	ICFSecSecClusGrpIncByClusGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecClusGrpIncByClusGrpIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByClusGrpIdx(ICFSecSecClusGrpIncByClusGrpIdxKey key) {
		cfsec31SecClusGrpIncRepository.deleteByClusGrpIdx(key.getRequiredSecClusGrpId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredInclName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByNameIdx(@Param("inclName") String requiredInclName) {
		cfsec31SecClusGrpIncRepository.deleteByNameIdx(requiredInclName);
	}

	/**
	 *	ICFSecSecClusGrpIncByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecClusGrpIncByNameIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByNameIdx(ICFSecSecClusGrpIncByNameIdxKey key) {
		cfsec31SecClusGrpIncRepository.deleteByNameIdx(key.getRequiredInclName());
	}
}

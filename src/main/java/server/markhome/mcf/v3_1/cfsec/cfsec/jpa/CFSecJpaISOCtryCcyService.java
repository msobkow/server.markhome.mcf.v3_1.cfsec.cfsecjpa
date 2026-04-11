// Description: Java 25 Spring JPA Service for ISOCtryCcy

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
 *	Service for the CFSecISOCtryCcy entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecISOCtryCcyRepository to access them.
 */
@Service("cfsec31JpaISOCtryCcyService")
public class CFSecJpaISOCtryCcyService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaISOCtryCcyRepository cfsec31ISOCtryCcyRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaISOCtryCcy, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtryCcy create(CFSecJpaISOCtryCcy data) {
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
			if(data.getPKey() != null && cfsec31ISOCtryCcyRepository.existsById((CFSecJpaISOCtryCcyPKey)data.getPKey())) {
				return( (CFSecJpaISOCtryCcy)(cfsec31ISOCtryCcyRepository.findById((CFSecJpaISOCtryCcyPKey)(data.getPKey())).get()));
			}
			return cfsec31ISOCtryCcyRepository.save(data);
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
	public CFSecJpaISOCtryCcy update(CFSecJpaISOCtryCcy data) {
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
		CFSecJpaISOCtryCcy existing = cfsec31ISOCtryCcyRepository.findById((CFSecJpaISOCtryCcyPKey)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecISOCtryCcy to existing object
		// Apply data columns of CFSecISOCtryCcy to existing object
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31ISOCtryCcyRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredISOCtryId
	 *		@param requiredISOCcyId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtryCcy find(@Param("iSOCtryId") short requiredISOCtryId,
		@Param("iSOCcyId") short requiredISOCcyId) {
		return( cfsec31ISOCtryCcyRepository.get(requiredISOCtryId,
			requiredISOCcyId));
	}

	/**
	 *	ICFSecISOCtryCcyPKey based find method for object-based access.
	 *
	 *		@param key The key of the entity to be find.
	 *
	 *		@return The entity find, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtryCcy find(ICFSecISOCtryCcyPKey key) {
		return( cfsec31ISOCtryCcyRepository.get(key.getRequiredISOCtryId(), key.getRequiredISOCcyId()));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOCtryCcy> findAll() {
		return( cfsec31ISOCtryCcyRepository.findAll() );
	}

	// CFSecISOCtryCcy specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecISOCtryCcyByCtryIdxKey as arguments.
	 *
	 *		@param requiredISOCtryId
	 *
	 *		@return List&lt;CFSecJpaISOCtryCcy&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOCtryCcy> findByCtryIdx(@Param("iSOCtryId") short requiredISOCtryId) {
		return( cfsec31ISOCtryCcyRepository.findByCtryIdx(requiredISOCtryId));
	}

	/**
	 *	ICFSecISOCtryCcyByCtryIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecISOCtryCcyByCtryIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOCtryCcy> findByCtryIdx(ICFSecISOCtryCcyByCtryIdxKey key) {
		return( cfsec31ISOCtryCcyRepository.findByCtryIdx(key.getRequiredISOCtryId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecISOCtryCcyByCcyIdxKey as arguments.
	 *
	 *		@param requiredISOCcyId
	 *
	 *		@return List&lt;CFSecJpaISOCtryCcy&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOCtryCcy> findByCcyIdx(@Param("iSOCcyId") short requiredISOCcyId) {
		return( cfsec31ISOCtryCcyRepository.findByCcyIdx(requiredISOCcyId));
	}

	/**
	 *	ICFSecISOCtryCcyByCcyIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecISOCtryCcyByCcyIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOCtryCcy> findByCcyIdx(ICFSecISOCtryCcyByCcyIdxKey key) {
		return( cfsec31ISOCtryCcyRepository.findByCcyIdx(key.getRequiredISOCcyId()));
	}

	// CFSecISOCtryCcy specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCtryId
	 *		@param requiredISOCcyId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtryCcy lockByIdIdx(@Param("iSOCtryId") short requiredISOCtryId,
		@Param("iSOCcyId") short requiredISOCcyId) {
		return( cfsec31ISOCtryCcyRepository.lockByIdIdx(requiredISOCtryId,
			requiredISOCcyId));
	}

	/**
	 *	ICFSecISOCtryCcyPKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtryCcy lockByIdIdx(ICFSecISOCtryCcyPKey key) {
		return( cfsec31ISOCtryCcyRepository.lockByIdIdx(key.getRequiredISOCtryId(), key.getRequiredISOCcyId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCtryId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOCtryCcy> lockByCtryIdx(@Param("iSOCtryId") short requiredISOCtryId) {
		return( cfsec31ISOCtryCcyRepository.lockByCtryIdx(requiredISOCtryId));
	}

	/**
	 *	ICFSecISOCtryCcyByCtryIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOCtryCcy> lockByCtryIdx(ICFSecISOCtryCcyByCtryIdxKey key) {
		return( cfsec31ISOCtryCcyRepository.lockByCtryIdx(key.getRequiredISOCtryId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCcyId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOCtryCcy> lockByCcyIdx(@Param("iSOCcyId") short requiredISOCcyId) {
		return( cfsec31ISOCtryCcyRepository.lockByCcyIdx(requiredISOCcyId));
	}

	/**
	 *	ICFSecISOCtryCcyByCcyIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOCtryCcy> lockByCcyIdx(ICFSecISOCtryCcyByCcyIdxKey key) {
		return( cfsec31ISOCtryCcyRepository.lockByCcyIdx(key.getRequiredISOCcyId()));
	}

	// CFSecISOCtryCcy specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCtryId
	 *		@param requiredISOCcyId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("iSOCtryId") short requiredISOCtryId,
		@Param("iSOCcyId") short requiredISOCcyId) {
		cfsec31ISOCtryCcyRepository.deleteByIdIdx(requiredISOCtryId,
			requiredISOCcyId);
	}

	/**
	 *	ICFSecISOCtryCcyByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecISOCtryCcyByIdIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(ICFSecISOCtryCcyPKey key) {
		cfsec31ISOCtryCcyRepository.deleteByIdIdx(key.getRequiredISOCtryId(), key.getRequiredISOCcyId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCtryId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByCtryIdx(@Param("iSOCtryId") short requiredISOCtryId) {
		cfsec31ISOCtryCcyRepository.deleteByCtryIdx(requiredISOCtryId);
	}

	/**
	 *	ICFSecISOCtryCcyByCtryIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecISOCtryCcyByCtryIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByCtryIdx(ICFSecISOCtryCcyByCtryIdxKey key) {
		cfsec31ISOCtryCcyRepository.deleteByCtryIdx(key.getRequiredISOCtryId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCcyId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByCcyIdx(@Param("iSOCcyId") short requiredISOCcyId) {
		cfsec31ISOCtryCcyRepository.deleteByCcyIdx(requiredISOCcyId);
	}

	/**
	 *	ICFSecISOCtryCcyByCcyIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecISOCtryCcyByCcyIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByCcyIdx(ICFSecISOCtryCcyByCcyIdxKey key) {
		cfsec31ISOCtryCcyRepository.deleteByCcyIdx(key.getRequiredISOCcyId());
	}

}

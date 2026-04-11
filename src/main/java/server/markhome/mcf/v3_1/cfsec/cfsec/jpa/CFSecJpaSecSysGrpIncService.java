// Description: Java 25 Spring JPA Service for SecSysGrpInc

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
 *	Service for the CFSecSecSysGrpInc entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecSecSysGrpIncRepository to access them.
 */
@Service("cfsec31JpaSecSysGrpIncService")
public class CFSecJpaSecSysGrpIncService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaSecSysGrpIncRepository cfsec31SecSysGrpIncRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaSecSysGrpInc, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrpInc create(CFSecJpaSecSysGrpInc data) {
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
			if(data.getPKey() != null && cfsec31SecSysGrpIncRepository.existsById((CFSecJpaSecSysGrpIncPKey)data.getPKey())) {
				return( (CFSecJpaSecSysGrpInc)(cfsec31SecSysGrpIncRepository.findById((CFSecJpaSecSysGrpIncPKey)(data.getPKey())).get()));
			}
			return cfsec31SecSysGrpIncRepository.save(data);
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
	public CFSecJpaSecSysGrpInc update(CFSecJpaSecSysGrpInc data) {
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
		CFSecJpaSecSysGrpInc existing = cfsec31SecSysGrpIncRepository.findById((CFSecJpaSecSysGrpIncPKey)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecSecSysGrpInc to existing object
		// Apply data columns of CFSecSecSysGrpInc to existing object
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31SecSysGrpIncRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecSysGrpId
	 *		@param requiredInclName
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrpInc find(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId,
		@Param("inclName") String requiredInclName) {
		return( cfsec31SecSysGrpIncRepository.get(requiredSecSysGrpId,
			requiredInclName));
	}

	/**
	 *	ICFSecSecSysGrpIncPKey based find method for object-based access.
	 *
	 *		@param key The key of the entity to be find.
	 *
	 *		@return The entity find, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrpInc find(ICFSecSecSysGrpIncPKey key) {
		return( cfsec31SecSysGrpIncRepository.get(key.getRequiredSecSysGrpId(), key.getRequiredInclName()));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSysGrpInc> findAll() {
		return( cfsec31SecSysGrpIncRepository.findAll() );
	}

	// CFSecSecSysGrpInc specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecSysGrpIncBySysGrpIdxKey as arguments.
	 *
	 *		@param requiredSecSysGrpId
	 *
	 *		@return List&lt;CFSecJpaSecSysGrpInc&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSysGrpInc> findBySysGrpIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId) {
		return( cfsec31SecSysGrpIncRepository.findBySysGrpIdx(requiredSecSysGrpId));
	}

	/**
	 *	ICFSecSecSysGrpIncBySysGrpIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecSysGrpIncBySysGrpIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSysGrpInc> findBySysGrpIdx(ICFSecSecSysGrpIncBySysGrpIdxKey key) {
		return( cfsec31SecSysGrpIncRepository.findBySysGrpIdx(key.getRequiredSecSysGrpId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecSysGrpIncByNameIdxKey as arguments.
	 *
	 *		@param requiredInclName
	 *
	 *		@return List&lt;CFSecJpaSecSysGrpInc&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSysGrpInc> findByNameIdx(@Param("inclName") String requiredInclName) {
		return( cfsec31SecSysGrpIncRepository.findByNameIdx(requiredInclName));
	}

	/**
	 *	ICFSecSecSysGrpIncByNameIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecSysGrpIncByNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSysGrpInc> findByNameIdx(ICFSecSecSysGrpIncByNameIdxKey key) {
		return( cfsec31SecSysGrpIncRepository.findByNameIdx(key.getRequiredInclName()));
	}

	// CFSecSecSysGrpInc specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 *		@param requiredInclName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrpInc lockByIdIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId,
		@Param("inclName") String requiredInclName) {
		return( cfsec31SecSysGrpIncRepository.lockByIdIdx(requiredSecSysGrpId,
			requiredInclName));
	}

	/**
	 *	ICFSecSecSysGrpIncPKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrpInc lockByIdIdx(ICFSecSecSysGrpIncPKey key) {
		return( cfsec31SecSysGrpIncRepository.lockByIdIdx(key.getRequiredSecSysGrpId(), key.getRequiredInclName()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSysGrpInc> lockBySysGrpIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId) {
		return( cfsec31SecSysGrpIncRepository.lockBySysGrpIdx(requiredSecSysGrpId));
	}

	/**
	 *	ICFSecSecSysGrpIncBySysGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSysGrpInc> lockBySysGrpIdx(ICFSecSecSysGrpIncBySysGrpIdxKey key) {
		return( cfsec31SecSysGrpIncRepository.lockBySysGrpIdx(key.getRequiredSecSysGrpId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredInclName
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSysGrpInc> lockByNameIdx(@Param("inclName") String requiredInclName) {
		return( cfsec31SecSysGrpIncRepository.lockByNameIdx(requiredInclName));
	}

	/**
	 *	ICFSecSecSysGrpIncByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSysGrpInc> lockByNameIdx(ICFSecSecSysGrpIncByNameIdxKey key) {
		return( cfsec31SecSysGrpIncRepository.lockByNameIdx(key.getRequiredInclName()));
	}

	// CFSecSecSysGrpInc specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 *		@param requiredInclName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId,
		@Param("inclName") String requiredInclName) {
		cfsec31SecSysGrpIncRepository.deleteByIdIdx(requiredSecSysGrpId,
			requiredInclName);
	}

	/**
	 *	ICFSecSecSysGrpIncByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecSysGrpIncByIdIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(ICFSecSecSysGrpIncPKey key) {
		cfsec31SecSysGrpIncRepository.deleteByIdIdx(key.getRequiredSecSysGrpId(), key.getRequiredInclName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteBySysGrpIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId) {
		cfsec31SecSysGrpIncRepository.deleteBySysGrpIdx(requiredSecSysGrpId);
	}

	/**
	 *	ICFSecSecSysGrpIncBySysGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecSysGrpIncBySysGrpIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteBySysGrpIdx(ICFSecSecSysGrpIncBySysGrpIdxKey key) {
		cfsec31SecSysGrpIncRepository.deleteBySysGrpIdx(key.getRequiredSecSysGrpId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredInclName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByNameIdx(@Param("inclName") String requiredInclName) {
		cfsec31SecSysGrpIncRepository.deleteByNameIdx(requiredInclName);
	}

	/**
	 *	ICFSecSecSysGrpIncByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecSysGrpIncByNameIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByNameIdx(ICFSecSecSysGrpIncByNameIdxKey key) {
		cfsec31SecSysGrpIncRepository.deleteByNameIdx(key.getRequiredInclName());
	}

}

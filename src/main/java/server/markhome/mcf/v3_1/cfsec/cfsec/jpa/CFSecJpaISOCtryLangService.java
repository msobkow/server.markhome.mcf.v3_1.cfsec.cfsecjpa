// Description: Java 25 Spring JPA Service for ISOCtryLang

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
 *	Service for the CFSecISOCtryLang entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecISOCtryLangRepository to access them.
 */
@Service("cfsec31JpaISOCtryLangService")
public class CFSecJpaISOCtryLangService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaISOCtryLangRepository cfsec31ISOCtryLangRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaISOCtryLang, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtryLang create(CFSecJpaISOCtryLang data) {
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
			if(data.getPKey() != null && cfsec31ISOCtryLangRepository.existsById((CFSecJpaISOCtryLangPKey)data.getPKey())) {
				return( (CFSecJpaISOCtryLang)(cfsec31ISOCtryLangRepository.findById((CFSecJpaISOCtryLangPKey)(data.getPKey())).get()));
			}
			return cfsec31ISOCtryLangRepository.save(data);
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
	public CFSecJpaISOCtryLang update(CFSecJpaISOCtryLang data) {
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
		CFSecJpaISOCtryLang existing = cfsec31ISOCtryLangRepository.findById((CFSecJpaISOCtryLangPKey)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecISOCtryLang to existing object
		// Apply data columns of CFSecISOCtryLang to existing object
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31ISOCtryLangRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredISOCtryId
	 *		@param requiredISOLangId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtryLang find(@Param("iSOCtryId") short requiredISOCtryId,
		@Param("iSOLangId") short requiredISOLangId) {
		return( cfsec31ISOCtryLangRepository.get(requiredISOCtryId,
			requiredISOLangId));
	}

	/**
	 *	ICFSecISOCtryLangPKey based find method for object-based access.
	 *
	 *		@param key The key of the entity to be find.
	 *
	 *		@return The entity find, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtryLang find(ICFSecISOCtryLangPKey key) {
		return( cfsec31ISOCtryLangRepository.get(key.getRequiredISOCtryId(), key.getRequiredISOLangId()));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOCtryLang> findAll() {
		return( cfsec31ISOCtryLangRepository.findAll() );
	}

	// CFSecISOCtryLang specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecISOCtryLangByCtryIdxKey as arguments.
	 *
	 *		@param requiredISOCtryId
	 *
	 *		@return List&lt;CFSecJpaISOCtryLang&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOCtryLang> findByCtryIdx(@Param("iSOCtryId") short requiredISOCtryId) {
		return( cfsec31ISOCtryLangRepository.findByCtryIdx(requiredISOCtryId));
	}

	/**
	 *	ICFSecISOCtryLangByCtryIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecISOCtryLangByCtryIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOCtryLang> findByCtryIdx(ICFSecISOCtryLangByCtryIdxKey key) {
		return( cfsec31ISOCtryLangRepository.findByCtryIdx(key.getRequiredISOCtryId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecISOCtryLangByLangIdxKey as arguments.
	 *
	 *		@param requiredISOLangId
	 *
	 *		@return List&lt;CFSecJpaISOCtryLang&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOCtryLang> findByLangIdx(@Param("iSOLangId") short requiredISOLangId) {
		return( cfsec31ISOCtryLangRepository.findByLangIdx(requiredISOLangId));
	}

	/**
	 *	ICFSecISOCtryLangByLangIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecISOCtryLangByLangIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOCtryLang> findByLangIdx(ICFSecISOCtryLangByLangIdxKey key) {
		return( cfsec31ISOCtryLangRepository.findByLangIdx(key.getRequiredISOLangId()));
	}

	// CFSecISOCtryLang specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCtryId
	 *		@param requiredISOLangId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtryLang lockByIdIdx(@Param("iSOCtryId") short requiredISOCtryId,
		@Param("iSOLangId") short requiredISOLangId) {
		return( cfsec31ISOCtryLangRepository.lockByIdIdx(requiredISOCtryId,
			requiredISOLangId));
	}

	/**
	 *	ICFSecISOCtryLangPKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtryLang lockByIdIdx(ICFSecISOCtryLangPKey key) {
		return( cfsec31ISOCtryLangRepository.lockByIdIdx(key.getRequiredISOCtryId(), key.getRequiredISOLangId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCtryId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOCtryLang> lockByCtryIdx(@Param("iSOCtryId") short requiredISOCtryId) {
		return( cfsec31ISOCtryLangRepository.lockByCtryIdx(requiredISOCtryId));
	}

	/**
	 *	ICFSecISOCtryLangByCtryIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOCtryLang> lockByCtryIdx(ICFSecISOCtryLangByCtryIdxKey key) {
		return( cfsec31ISOCtryLangRepository.lockByCtryIdx(key.getRequiredISOCtryId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOLangId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOCtryLang> lockByLangIdx(@Param("iSOLangId") short requiredISOLangId) {
		return( cfsec31ISOCtryLangRepository.lockByLangIdx(requiredISOLangId));
	}

	/**
	 *	ICFSecISOCtryLangByLangIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOCtryLang> lockByLangIdx(ICFSecISOCtryLangByLangIdxKey key) {
		return( cfsec31ISOCtryLangRepository.lockByLangIdx(key.getRequiredISOLangId()));
	}

	// CFSecISOCtryLang specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCtryId
	 *		@param requiredISOLangId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("iSOCtryId") short requiredISOCtryId,
		@Param("iSOLangId") short requiredISOLangId) {
		cfsec31ISOCtryLangRepository.deleteByIdIdx(requiredISOCtryId,
			requiredISOLangId);
	}

	/**
	 *	ICFSecISOCtryLangByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecISOCtryLangByIdIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(ICFSecISOCtryLangPKey key) {
		cfsec31ISOCtryLangRepository.deleteByIdIdx(key.getRequiredISOCtryId(), key.getRequiredISOLangId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCtryId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByCtryIdx(@Param("iSOCtryId") short requiredISOCtryId) {
		cfsec31ISOCtryLangRepository.deleteByCtryIdx(requiredISOCtryId);
	}

	/**
	 *	ICFSecISOCtryLangByCtryIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecISOCtryLangByCtryIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByCtryIdx(ICFSecISOCtryLangByCtryIdxKey key) {
		cfsec31ISOCtryLangRepository.deleteByCtryIdx(key.getRequiredISOCtryId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOLangId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByLangIdx(@Param("iSOLangId") short requiredISOLangId) {
		cfsec31ISOCtryLangRepository.deleteByLangIdx(requiredISOLangId);
	}

	/**
	 *	ICFSecISOCtryLangByLangIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecISOCtryLangByLangIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByLangIdx(ICFSecISOCtryLangByLangIdxKey key) {
		cfsec31ISOCtryLangRepository.deleteByLangIdx(key.getRequiredISOLangId());
	}
}

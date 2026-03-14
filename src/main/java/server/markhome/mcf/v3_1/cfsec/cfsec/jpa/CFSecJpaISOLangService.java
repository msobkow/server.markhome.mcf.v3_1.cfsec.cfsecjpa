// Description: Java 25 Spring JPA Service for ISOLang

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
 *	Service for the CFSecISOLang entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecISOLangRepository to access them.
 */
@Service("cfsec31JpaISOLangService")
public class CFSecJpaISOLangService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaISOLangRepository cfsec31ISOLangRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaISOLang, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOLang create(CFSecJpaISOLang data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		short originalRequiredISOLangId = data.getRequiredISOLangId();
		boolean generatedRequiredISOLangId = false;
		if(data.getRequiredISO6392Code() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredISO6392Code");
		}
		if(data.getRequiredEnglishName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredEnglishName");
		}
		try {
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31ISOLangRepository.existsById((Short)data.getPKey())) {
				return( (CFSecJpaISOLang)(cfsec31ISOLangRepository.findById((Short)(data.getPKey())).get()));
			}
			return cfsec31ISOLangRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredISOLangId) {
					data.setRequiredISOLangId(originalRequiredISOLangId);
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
	public CFSecJpaISOLang update(CFSecJpaISOLang data) {
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
		if(data.getRequiredISO6392Code() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredISO6392Code");
		}
		if(data.getRequiredEnglishName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredEnglishName");
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaISOLang existing = cfsec31ISOLangRepository.findById((Short)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecISOLang to existing object
		// Apply data columns of CFSecISOLang to existing object
		existing.setRequiredISO6392Code(data.getRequiredISO6392Code());
		existing.setOptionalISO6391Code(data.getOptionalISO6391Code());
		existing.setRequiredEnglishName(data.getRequiredEnglishName());
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31ISOLangRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredISOLangId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOLang find(@Param("iSOLangId") short requiredISOLangId) {
		return( cfsec31ISOLangRepository.get(requiredISOLangId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOLang> findAll() {
		return( cfsec31ISOLangRepository.findAll() );
	}

	// CFSecISOLang specified index finders

	/**
	 *	Find an entity using the columns of the ICFSecISOLangByCode3IdxKey as arguments.
	 *
	 *		@param requiredISO6392Code
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOLang findByCode3Idx(@Param("iSO6392Code") String requiredISO6392Code) {
		return( cfsec31ISOLangRepository.findByCode3Idx(requiredISO6392Code));
	}

	/**
	 *	ICFSecISOLangByCode3IdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecISOLangByCode3IdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOLang findByCode3Idx(ICFSecISOLangByCode3IdxKey key) {
		return( cfsec31ISOLangRepository.findByCode3Idx(key.getRequiredISO6392Code()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecISOLangByCode2IdxKey as arguments.
	 *
	 *		@param optionalISO6391Code
	 *
	 *		@return List&lt;CFSecJpaISOLang&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOLang> findByCode2Idx(@Param("iSO6391Code") String optionalISO6391Code) {
		return( cfsec31ISOLangRepository.findByCode2Idx(optionalISO6391Code));
	}

	/**
	 *	ICFSecISOLangByCode2IdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecISOLangByCode2IdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOLang> findByCode2Idx(ICFSecISOLangByCode2IdxKey key) {
		return( cfsec31ISOLangRepository.findByCode2Idx(key.getOptionalISO6391Code()));
	}

	// CFSecISOLang specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOLangId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOLang lockByIdIdx(@Param("iSOLangId") short requiredISOLangId) {
		return( cfsec31ISOLangRepository.lockByIdIdx(requiredISOLangId));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISO6392Code
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOLang lockByCode3Idx(@Param("iSO6392Code") String requiredISO6392Code) {
		return( cfsec31ISOLangRepository.lockByCode3Idx(requiredISO6392Code));
	}

	/**
	 *	ICFSecISOLangByCode3IdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOLang lockByCode3Idx(ICFSecISOLangByCode3IdxKey key) {
		return( cfsec31ISOLangRepository.lockByCode3Idx(key.getRequiredISO6392Code()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param optionalISO6391Code
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOLang> lockByCode2Idx(@Param("iSO6391Code") String optionalISO6391Code) {
		return( cfsec31ISOLangRepository.lockByCode2Idx(optionalISO6391Code));
	}

	/**
	 *	ICFSecISOLangByCode2IdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOLang> lockByCode2Idx(ICFSecISOLangByCode2IdxKey key) {
		return( cfsec31ISOLangRepository.lockByCode2Idx(key.getOptionalISO6391Code()));
	}

	// CFSecISOLang specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOLangId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("iSOLangId") short requiredISOLangId) {
		cfsec31ISOLangRepository.deleteByIdIdx(requiredISOLangId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISO6392Code
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByCode3Idx(@Param("iSO6392Code") String requiredISO6392Code) {
		cfsec31ISOLangRepository.deleteByCode3Idx(requiredISO6392Code);
	}

	/**
	 *	ICFSecISOLangByCode3IdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecISOLangByCode3IdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByCode3Idx(ICFSecISOLangByCode3IdxKey key) {
		cfsec31ISOLangRepository.deleteByCode3Idx(key.getRequiredISO6392Code());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param optionalISO6391Code
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByCode2Idx(@Param("iSO6391Code") String optionalISO6391Code) {
		cfsec31ISOLangRepository.deleteByCode2Idx(optionalISO6391Code);
	}

	/**
	 *	ICFSecISOLangByCode2IdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecISOLangByCode2IdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByCode2Idx(ICFSecISOLangByCode2IdxKey key) {
		cfsec31ISOLangRepository.deleteByCode2Idx(key.getOptionalISO6391Code());
	}
}

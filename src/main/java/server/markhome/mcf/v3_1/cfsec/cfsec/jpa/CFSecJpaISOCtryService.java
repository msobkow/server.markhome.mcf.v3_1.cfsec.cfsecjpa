// Description: Java 25 Spring JPA Service for ISOCtry

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
 *	Service for the CFSecISOCtry entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecISOCtryRepository to access them.
 */
@Service("cfsec31JpaISOCtryService")
public class CFSecJpaISOCtryService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaISOCtryRepository cfsec31ISOCtryRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaISOCtry, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtry create(CFSecJpaISOCtry data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		short originalRequiredISOCtryId = data.getRequiredISOCtryId();
		boolean generatedRequiredISOCtryId = false;
		if(data.getRequiredISOCode() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredISOCode");
		}
		if(data.getRequiredName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredName");
		}
		try {
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31ISOCtryRepository.existsById((Short)data.getPKey())) {
				return( (CFSecJpaISOCtry)(cfsec31ISOCtryRepository.findById((Short)(data.getPKey())).get()));
			}
			return cfsec31ISOCtryRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredISOCtryId) {
					data.setRequiredISOCtryId(originalRequiredISOCtryId);
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
	public CFSecJpaISOCtry update(CFSecJpaISOCtry data) {
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
		if(data.getRequiredISOCode() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredISOCode");
		}
		if(data.getRequiredName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredName");
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaISOCtry existing = cfsec31ISOCtryRepository.findById((Short)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecISOCtry to existing object
		// Apply data columns of CFSecISOCtry to existing object
		existing.setRequiredISOCode(data.getRequiredISOCode());
		existing.setRequiredName(data.getRequiredName());
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31ISOCtryRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredISOCtryId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtry find(@Param("iSOCtryId") short requiredISOCtryId) {
		return( cfsec31ISOCtryRepository.get(requiredISOCtryId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOCtry> findAll() {
		return( cfsec31ISOCtryRepository.findAll() );
	}

	// CFSecISOCtry specified index finders

	/**
	 *	Find an entity using the columns of the ICFSecISOCtryByISOCodeIdxKey as arguments.
	 *
	 *		@param requiredISOCode
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtry findByISOCodeIdx(@Param("iSOCode") String requiredISOCode) {
		return( cfsec31ISOCtryRepository.findByISOCodeIdx(requiredISOCode));
	}

	/**
	 *	ICFSecISOCtryByISOCodeIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecISOCtryByISOCodeIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtry findByISOCodeIdx(ICFSecISOCtryByISOCodeIdxKey key) {
		return( cfsec31ISOCtryRepository.findByISOCodeIdx(key.getRequiredISOCode()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecISOCtryByNameIdxKey as arguments.
	 *
	 *		@param requiredName
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtry findByNameIdx(@Param("name") String requiredName) {
		return( cfsec31ISOCtryRepository.findByNameIdx(requiredName));
	}

	/**
	 *	ICFSecISOCtryByNameIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecISOCtryByNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtry findByNameIdx(ICFSecISOCtryByNameIdxKey key) {
		return( cfsec31ISOCtryRepository.findByNameIdx(key.getRequiredName()));
	}

	// CFSecISOCtry specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCtryId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtry lockByIdIdx(@Param("iSOCtryId") short requiredISOCtryId) {
		return( cfsec31ISOCtryRepository.lockByIdIdx(requiredISOCtryId));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCode
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtry lockByISOCodeIdx(@Param("iSOCode") String requiredISOCode) {
		return( cfsec31ISOCtryRepository.lockByISOCodeIdx(requiredISOCode));
	}

	/**
	 *	ICFSecISOCtryByISOCodeIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtry lockByISOCodeIdx(ICFSecISOCtryByISOCodeIdxKey key) {
		return( cfsec31ISOCtryRepository.lockByISOCodeIdx(key.getRequiredISOCode()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtry lockByNameIdx(@Param("name") String requiredName) {
		return( cfsec31ISOCtryRepository.lockByNameIdx(requiredName));
	}

	/**
	 *	ICFSecISOCtryByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCtry lockByNameIdx(ICFSecISOCtryByNameIdxKey key) {
		return( cfsec31ISOCtryRepository.lockByNameIdx(key.getRequiredName()));
	}

	// CFSecISOCtry specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCtryId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("iSOCtryId") short requiredISOCtryId) {
		cfsec31ISOCtryRepository.deleteByIdIdx(requiredISOCtryId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCode
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByISOCodeIdx(@Param("iSOCode") String requiredISOCode) {
		cfsec31ISOCtryRepository.deleteByISOCodeIdx(requiredISOCode);
	}

	/**
	 *	ICFSecISOCtryByISOCodeIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecISOCtryByISOCodeIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByISOCodeIdx(ICFSecISOCtryByISOCodeIdxKey key) {
		cfsec31ISOCtryRepository.deleteByISOCodeIdx(key.getRequiredISOCode());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByNameIdx(@Param("name") String requiredName) {
		cfsec31ISOCtryRepository.deleteByNameIdx(requiredName);
	}

	/**
	 *	ICFSecISOCtryByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecISOCtryByNameIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByNameIdx(ICFSecISOCtryByNameIdxKey key) {
		cfsec31ISOCtryRepository.deleteByNameIdx(key.getRequiredName());
	}
}

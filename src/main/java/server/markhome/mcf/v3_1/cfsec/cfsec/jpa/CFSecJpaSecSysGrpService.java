// Description: Java 25 Spring JPA Service for SecSysGrp

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
 *	Service for the CFSecSecSysGrp entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecSecSysGrpRepository to access them.
 */
@Service("cfsec31JpaSecSysGrpService")
public class CFSecJpaSecSysGrpService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaSecSysGrpRepository cfsec31SecSysGrpRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaSecSysGrp, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrp create(CFSecJpaSecSysGrp data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		CFLibDbKeyHash256 originalRequiredSecSysGrpId = data.getRequiredSecSysGrpId();
		boolean generatedRequiredSecSysGrpId = false;
		if(data.getRequiredName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredName");
		}
		if(data.getRequiredSecLevel() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredSecLevel");
		}
		try {
			if (data.getRequiredSecSysGrpId() == null || data.getRequiredSecSysGrpId().isNull()) {
				data.setRequiredSecSysGrpId(new CFLibDbKeyHash256(0));
				generatedRequiredSecSysGrpId = true;
			}
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31SecSysGrpRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFSecJpaSecSysGrp)(cfsec31SecSysGrpRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfsec31SecSysGrpRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredSecSysGrpId) {
					data.setRequiredSecSysGrpId(originalRequiredSecSysGrpId);
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
	public CFSecJpaSecSysGrp update(CFSecJpaSecSysGrp data) {
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
		if(data.getRequiredName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredName");
		}
		if(data.getRequiredSecLevel() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredSecLevel");
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaSecSysGrp existing = cfsec31SecSysGrpRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecSecSysGrp to existing object
		// Apply data columns of CFSecSecSysGrp to existing object
		existing.setRequiredName(data.getRequiredName());
		existing.setRequiredSecLevel(data.getRequiredSecLevel());
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31SecSysGrpRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecSysGrpId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrp find(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId) {
		return( cfsec31SecSysGrpRepository.get(requiredSecSysGrpId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSysGrp> findAll() {
		return( cfsec31SecSysGrpRepository.findAll() );
	}

	// CFSecSecSysGrp specified index finders

	/**
	 *	Find an entity using the columns of the ICFSecSecSysGrpByUNameIdxKey as arguments.
	 *
	 *		@param requiredName
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrp findByUNameIdx(@Param("name") String requiredName) {
		return( cfsec31SecSysGrpRepository.findByUNameIdx(requiredName));
	}

	/**
	 *	ICFSecSecSysGrpByUNameIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecSysGrpByUNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrp findByUNameIdx(ICFSecSecSysGrpByUNameIdxKey key) {
		return( cfsec31SecSysGrpRepository.findByUNameIdx(key.getRequiredName()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecSecSysGrpBySecLevelIdxKey as arguments.
	 *
	 *		@param requiredSecLevel
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrp findBySecLevelIdx(@Param("secLevel") ICFSecSchema.SecLevelEnum requiredSecLevel) {
		return( cfsec31SecSysGrpRepository.findBySecLevelIdx(requiredSecLevel));
	}

	/**
	 *	ICFSecSecSysGrpBySecLevelIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecSysGrpBySecLevelIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrp findBySecLevelIdx(ICFSecSecSysGrpBySecLevelIdxKey key) {
		return( cfsec31SecSysGrpRepository.findBySecLevelIdx(key.getRequiredSecLevel()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecSecSysGrpBySecLevelNmIdxKey as arguments.
	 *
	 *		@param requiredSecLevel
	 *		@param requiredName
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrp findBySecLevelNmIdx(@Param("secLevel") ICFSecSchema.SecLevelEnum requiredSecLevel,
		@Param("name") String requiredName) {
		return( cfsec31SecSysGrpRepository.findBySecLevelNmIdx(requiredSecLevel,
			requiredName));
	}

	/**
	 *	ICFSecSecSysGrpBySecLevelNmIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecSysGrpBySecLevelNmIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrp findBySecLevelNmIdx(ICFSecSecSysGrpBySecLevelNmIdxKey key) {
		return( cfsec31SecSysGrpRepository.findBySecLevelNmIdx(key.getRequiredSecLevel(), key.getRequiredName()));
	}

	// CFSecSecSysGrp specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrp lockByIdIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId) {
		return( cfsec31SecSysGrpRepository.lockByIdIdx(requiredSecSysGrpId));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrp lockByUNameIdx(@Param("name") String requiredName) {
		return( cfsec31SecSysGrpRepository.lockByUNameIdx(requiredName));
	}

	/**
	 *	ICFSecSecSysGrpByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrp lockByUNameIdx(ICFSecSecSysGrpByUNameIdxKey key) {
		return( cfsec31SecSysGrpRepository.lockByUNameIdx(key.getRequiredName()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecLevel
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrp lockBySecLevelIdx(@Param("secLevel") ICFSecSchema.SecLevelEnum requiredSecLevel) {
		return( cfsec31SecSysGrpRepository.lockBySecLevelIdx(requiredSecLevel));
	}

	/**
	 *	ICFSecSecSysGrpBySecLevelIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrp lockBySecLevelIdx(ICFSecSecSysGrpBySecLevelIdxKey key) {
		return( cfsec31SecSysGrpRepository.lockBySecLevelIdx(key.getRequiredSecLevel()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecLevel
	 *		@param requiredName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrp lockBySecLevelNmIdx(@Param("secLevel") ICFSecSchema.SecLevelEnum requiredSecLevel,
		@Param("name") String requiredName) {
		return( cfsec31SecSysGrpRepository.lockBySecLevelNmIdx(requiredSecLevel,
			requiredName));
	}

	/**
	 *	ICFSecSecSysGrpBySecLevelNmIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSysGrp lockBySecLevelNmIdx(ICFSecSecSysGrpBySecLevelNmIdxKey key) {
		return( cfsec31SecSysGrpRepository.lockBySecLevelNmIdx(key.getRequiredSecLevel(), key.getRequiredName()));
	}

	// CFSecSecSysGrp specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId) {
		cfsec31SecSysGrpRepository.deleteByIdIdx(requiredSecSysGrpId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUNameIdx(@Param("name") String requiredName) {
		cfsec31SecSysGrpRepository.deleteByUNameIdx(requiredName);
	}

	/**
	 *	ICFSecSecSysGrpByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecSysGrpByUNameIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUNameIdx(ICFSecSecSysGrpByUNameIdxKey key) {
		cfsec31SecSysGrpRepository.deleteByUNameIdx(key.getRequiredName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecLevel
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteBySecLevelIdx(@Param("secLevel") ICFSecSchema.SecLevelEnum requiredSecLevel) {
		cfsec31SecSysGrpRepository.deleteBySecLevelIdx(requiredSecLevel);
	}

	/**
	 *	ICFSecSecSysGrpBySecLevelIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecSysGrpBySecLevelIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteBySecLevelIdx(ICFSecSecSysGrpBySecLevelIdxKey key) {
		cfsec31SecSysGrpRepository.deleteBySecLevelIdx(key.getRequiredSecLevel());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecLevel
	 *		@param requiredName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteBySecLevelNmIdx(@Param("secLevel") ICFSecSchema.SecLevelEnum requiredSecLevel,
		@Param("name") String requiredName) {
		cfsec31SecSysGrpRepository.deleteBySecLevelNmIdx(requiredSecLevel,
			requiredName);
	}

	/**
	 *	ICFSecSecSysGrpBySecLevelNmIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecSysGrpBySecLevelNmIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteBySecLevelNmIdx(ICFSecSecSysGrpBySecLevelNmIdxKey key) {
		cfsec31SecSysGrpRepository.deleteBySecLevelNmIdx(key.getRequiredSecLevel(), key.getRequiredName());
	}

}

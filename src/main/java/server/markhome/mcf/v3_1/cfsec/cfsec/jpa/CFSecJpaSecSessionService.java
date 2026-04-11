// Description: Java 25 Spring JPA Service for SecSession

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
 *	Service for the CFSecSecSession entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecSecSessionRepository to access them.
 */
@Service("cfsec31JpaSecSessionService")
public class CFSecJpaSecSessionService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaSecSessionRepository cfsec31SecSessionRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaSecSession, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSession create(CFSecJpaSecSession data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		CFLibDbKeyHash256 originalRequiredSecSessionId = data.getRequiredSecSessionId();
		boolean generatedRequiredSecSessionId = false;
		if(data.getRequiredSecUserId() == null || data.getRequiredSecUserId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredSecUserId");
		}
		if(data.getRequiredStart() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredStart");
		}
		try {
			if (data.getRequiredSecSessionId() == null || data.getRequiredSecSessionId().isNull()) {
				data.setRequiredSecSessionId(new CFLibDbKeyHash256(0));
				generatedRequiredSecSessionId = true;
			}
			if(data.getPKey() != null && cfsec31SecSessionRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFSecJpaSecSession)(cfsec31SecSessionRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfsec31SecSessionRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredSecSessionId) {
					data.setRequiredSecSessionId(originalRequiredSecSessionId);
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
	public CFSecJpaSecSession update(CFSecJpaSecSession data) {
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
		if(data.getRequiredSecUserId() == null || data.getRequiredSecUserId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredSecUserId");
		}
		if(data.getRequiredStart() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredStart");
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaSecSession existing = cfsec31SecSessionRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecSecSession to existing object
		// Apply data columns of CFSecSecSession to existing object
		existing.setRequiredSecUserId(data.getRequiredSecUserId());
		existing.setRequiredStart(data.getRequiredStart());
		existing.setOptionalFinish(data.getOptionalFinish());
		existing.setOptionalSecProxyId(data.getOptionalSecProxyId());
		// Save the changes we've made
		return cfsec31SecSessionRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecSessionId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSession find(@Param("secSessionId") CFLibDbKeyHash256 requiredSecSessionId) {
		return( cfsec31SecSessionRepository.get(requiredSecSessionId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSession> findAll() {
		return( cfsec31SecSessionRepository.findAll() );
	}

	// CFSecSecSession specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecSessionBySecUserIdxKey as arguments.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return List&lt;CFSecJpaSecSession&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSession> findBySecUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecSessionRepository.findBySecUserIdx(requiredSecUserId));
	}

	/**
	 *	ICFSecSecSessionBySecUserIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecSessionBySecUserIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSession> findBySecUserIdx(ICFSecSecSessionBySecUserIdxKey key) {
		return( cfsec31SecSessionRepository.findBySecUserIdx(key.getRequiredSecUserId()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecSecSessionByStartIdxKey as arguments.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredStart
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSession findByStartIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("start") LocalDateTime requiredStart) {
		return( cfsec31SecSessionRepository.findByStartIdx(requiredSecUserId,
			requiredStart));
	}

	/**
	 *	ICFSecSecSessionByStartIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecSessionByStartIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSession findByStartIdx(ICFSecSecSessionByStartIdxKey key) {
		return( cfsec31SecSessionRepository.findByStartIdx(key.getRequiredSecUserId(), key.getRequiredStart()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecSessionByFinishIdxKey as arguments.
	 *
	 *		@param requiredSecUserId
	 *		@param optionalFinish
	 *
	 *		@return List&lt;CFSecJpaSecSession&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSession> findByFinishIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("finish") LocalDateTime optionalFinish) {
		return( cfsec31SecSessionRepository.findByFinishIdx(requiredSecUserId,
			optionalFinish));
	}

	/**
	 *	ICFSecSecSessionByFinishIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecSessionByFinishIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSession> findByFinishIdx(ICFSecSecSessionByFinishIdxKey key) {
		return( cfsec31SecSessionRepository.findByFinishIdx(key.getRequiredSecUserId(), key.getOptionalFinish()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecSessionBySecProxyIdxKey as arguments.
	 *
	 *		@param optionalSecProxyId
	 *
	 *		@return List&lt;CFSecJpaSecSession&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSession> findBySecProxyIdx(@Param("secProxyId") CFLibDbKeyHash256 optionalSecProxyId) {
		return( cfsec31SecSessionRepository.findBySecProxyIdx(optionalSecProxyId));
	}

	/**
	 *	ICFSecSecSessionBySecProxyIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecSessionBySecProxyIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSession> findBySecProxyIdx(ICFSecSecSessionBySecProxyIdxKey key) {
		return( cfsec31SecSessionRepository.findBySecProxyIdx(key.getOptionalSecProxyId()));
	}

	// CFSecSecSession specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSessionId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSession lockByIdIdx(@Param("secSessionId") CFLibDbKeyHash256 requiredSecSessionId) {
		return( cfsec31SecSessionRepository.lockByIdIdx(requiredSecSessionId));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSession> lockBySecUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecSessionRepository.lockBySecUserIdx(requiredSecUserId));
	}

	/**
	 *	ICFSecSecSessionBySecUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSession> lockBySecUserIdx(ICFSecSecSessionBySecUserIdxKey key) {
		return( cfsec31SecSessionRepository.lockBySecUserIdx(key.getRequiredSecUserId()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredStart
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSession lockByStartIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("start") LocalDateTime requiredStart) {
		return( cfsec31SecSessionRepository.lockByStartIdx(requiredSecUserId,
			requiredStart));
	}

	/**
	 *	ICFSecSecSessionByStartIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecSession lockByStartIdx(ICFSecSecSessionByStartIdxKey key) {
		return( cfsec31SecSessionRepository.lockByStartIdx(key.getRequiredSecUserId(), key.getRequiredStart()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param optionalFinish
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSession> lockByFinishIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("finish") LocalDateTime optionalFinish) {
		return( cfsec31SecSessionRepository.lockByFinishIdx(requiredSecUserId,
			optionalFinish));
	}

	/**
	 *	ICFSecSecSessionByFinishIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSession> lockByFinishIdx(ICFSecSecSessionByFinishIdxKey key) {
		return( cfsec31SecSessionRepository.lockByFinishIdx(key.getRequiredSecUserId(), key.getOptionalFinish()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param optionalSecProxyId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSession> lockBySecProxyIdx(@Param("secProxyId") CFLibDbKeyHash256 optionalSecProxyId) {
		return( cfsec31SecSessionRepository.lockBySecProxyIdx(optionalSecProxyId));
	}

	/**
	 *	ICFSecSecSessionBySecProxyIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecSession> lockBySecProxyIdx(ICFSecSecSessionBySecProxyIdxKey key) {
		return( cfsec31SecSessionRepository.lockBySecProxyIdx(key.getOptionalSecProxyId()));
	}

	// CFSecSecSession specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSessionId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("secSessionId") CFLibDbKeyHash256 requiredSecSessionId) {
		cfsec31SecSessionRepository.deleteByIdIdx(requiredSecSessionId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteBySecUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		cfsec31SecSessionRepository.deleteBySecUserIdx(requiredSecUserId);
	}

	/**
	 *	ICFSecSecSessionBySecUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecSessionBySecUserIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteBySecUserIdx(ICFSecSecSessionBySecUserIdxKey key) {
		cfsec31SecSessionRepository.deleteBySecUserIdx(key.getRequiredSecUserId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredStart
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByStartIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("start") LocalDateTime requiredStart) {
		cfsec31SecSessionRepository.deleteByStartIdx(requiredSecUserId,
			requiredStart);
	}

	/**
	 *	ICFSecSecSessionByStartIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecSessionByStartIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByStartIdx(ICFSecSecSessionByStartIdxKey key) {
		cfsec31SecSessionRepository.deleteByStartIdx(key.getRequiredSecUserId(), key.getRequiredStart());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param optionalFinish
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByFinishIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("finish") LocalDateTime optionalFinish) {
		cfsec31SecSessionRepository.deleteByFinishIdx(requiredSecUserId,
			optionalFinish);
	}

	/**
	 *	ICFSecSecSessionByFinishIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecSessionByFinishIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByFinishIdx(ICFSecSecSessionByFinishIdxKey key) {
		cfsec31SecSessionRepository.deleteByFinishIdx(key.getRequiredSecUserId(), key.getOptionalFinish());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param optionalSecProxyId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteBySecProxyIdx(@Param("secProxyId") CFLibDbKeyHash256 optionalSecProxyId) {
		cfsec31SecSessionRepository.deleteBySecProxyIdx(optionalSecProxyId);
	}

	/**
	 *	ICFSecSecSessionBySecProxyIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecSessionBySecProxyIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteBySecProxyIdx(ICFSecSecSessionBySecProxyIdxKey key) {
		cfsec31SecSessionRepository.deleteBySecProxyIdx(key.getOptionalSecProxyId());
	}

}

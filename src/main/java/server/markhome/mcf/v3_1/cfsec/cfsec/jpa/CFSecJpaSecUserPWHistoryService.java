// Description: Java 25 Spring JPA Service for SecUserPWHistory

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
 *	Service for the CFSecSecUserPWHistory entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecSecUserPWHistoryRepository to access them.
 */
@Service("cfsec31JpaSecUserPWHistoryService")
public class CFSecJpaSecUserPWHistoryService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaSecUserPWHistoryRepository cfsec31SecUserPWHistoryRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaSecUserPWHistory, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWHistory create(CFSecJpaSecUserPWHistory data) {
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
		if(data.getRequiredPWReplacedStamp() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredPWReplacedStamp");
		}
		if(data.getRequiredPasswordHash() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredPasswordHash");
		}
		try {
			if(data.getPKey() != null && cfsec31SecUserPWHistoryRepository.existsById((CFSecJpaSecUserPWHistoryPKey)data.getPKey())) {
				return( (CFSecJpaSecUserPWHistory)(cfsec31SecUserPWHistoryRepository.findById((CFSecJpaSecUserPWHistoryPKey)(data.getPKey())).get()));
			}
			return cfsec31SecUserPWHistoryRepository.save(data);
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
	public CFSecJpaSecUserPWHistory update(CFSecJpaSecUserPWHistory data) {
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
		if(data.getRequiredPWReplacedStamp() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredPWReplacedStamp");
		}
		if(data.getRequiredPasswordHash() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredPasswordHash");
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaSecUserPWHistory existing = cfsec31SecUserPWHistoryRepository.findById((CFSecJpaSecUserPWHistoryPKey)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecSecUserPWHistory to existing object
		// Apply data columns of CFSecSecUserPWHistory to existing object
		existing.setRequiredPWReplacedStamp(data.getRequiredPWReplacedStamp());
		existing.setRequiredPasswordHash(data.getRequiredPasswordHash());
		// Save the changes we've made
		return cfsec31SecUserPWHistoryRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredPWSetStamp
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWHistory find(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("pWSetStamp") LocalDateTime requiredPWSetStamp) {
		return( cfsec31SecUserPWHistoryRepository.get(requiredSecUserId,
			requiredPWSetStamp));
	}

	/**
	 *	ICFSecSecUserPWHistoryPKey based find method for object-based access.
	 *
	 *		@param key The key of the entity to be find.
	 *
	 *		@return The entity find, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWHistory find(ICFSecSecUserPWHistoryPKey key) {
		return( cfsec31SecUserPWHistoryRepository.get(key.getRequiredSecUserId(), key.getRequiredPWSetStamp()));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserPWHistory> findAll() {
		return( cfsec31SecUserPWHistoryRepository.findAll() );
	}

	// CFSecSecUserPWHistory specified index finders

	/**
	 *	Find an entity using the columns of the ICFSecSecUserPWHistoryByUserIdxKey as arguments.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWHistory findByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecUserPWHistoryRepository.findByUserIdx(requiredSecUserId));
	}

	/**
	 *	ICFSecSecUserPWHistoryByUserIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecUserPWHistoryByUserIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWHistory findByUserIdx(ICFSecSecUserPWHistoryByUserIdxKey key) {
		return( cfsec31SecUserPWHistoryRepository.findByUserIdx(key.getRequiredSecUserId()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecSecUserPWHistoryBySetStampIdxKey as arguments.
	 *
	 *		@param requiredPWSetStamp
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWHistory findBySetStampIdx(@Param("pWSetStamp") LocalDateTime requiredPWSetStamp) {
		return( cfsec31SecUserPWHistoryRepository.findBySetStampIdx(requiredPWSetStamp));
	}

	/**
	 *	ICFSecSecUserPWHistoryBySetStampIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecUserPWHistoryBySetStampIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWHistory findBySetStampIdx(ICFSecSecUserPWHistoryBySetStampIdxKey key) {
		return( cfsec31SecUserPWHistoryRepository.findBySetStampIdx(key.getRequiredPWSetStamp()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecSecUserPWHistoryByReplacedStampIdxKey as arguments.
	 *
	 *		@param requiredPWReplacedStamp
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWHistory findByReplacedStampIdx(@Param("pWReplacedStamp") LocalDateTime requiredPWReplacedStamp) {
		return( cfsec31SecUserPWHistoryRepository.findByReplacedStampIdx(requiredPWReplacedStamp));
	}

	/**
	 *	ICFSecSecUserPWHistoryByReplacedStampIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecUserPWHistoryByReplacedStampIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWHistory findByReplacedStampIdx(ICFSecSecUserPWHistoryByReplacedStampIdxKey key) {
		return( cfsec31SecUserPWHistoryRepository.findByReplacedStampIdx(key.getRequiredPWReplacedStamp()));
	}

	// CFSecSecUserPWHistory specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredPWSetStamp
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWHistory lockByIdIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("pWSetStamp") LocalDateTime requiredPWSetStamp) {
		return( cfsec31SecUserPWHistoryRepository.lockByIdIdx(requiredSecUserId,
			requiredPWSetStamp));
	}

	/**
	 *	ICFSecSecUserPWHistoryPKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWHistory lockByIdIdx(ICFSecSecUserPWHistoryPKey key) {
		return( cfsec31SecUserPWHistoryRepository.lockByIdIdx(key.getRequiredSecUserId(), key.getRequiredPWSetStamp()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWHistory lockByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecUserPWHistoryRepository.lockByUserIdx(requiredSecUserId));
	}

	/**
	 *	ICFSecSecUserPWHistoryByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWHistory lockByUserIdx(ICFSecSecUserPWHistoryByUserIdxKey key) {
		return( cfsec31SecUserPWHistoryRepository.lockByUserIdx(key.getRequiredSecUserId()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredPWSetStamp
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWHistory lockBySetStampIdx(@Param("pWSetStamp") LocalDateTime requiredPWSetStamp) {
		return( cfsec31SecUserPWHistoryRepository.lockBySetStampIdx(requiredPWSetStamp));
	}

	/**
	 *	ICFSecSecUserPWHistoryBySetStampIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWHistory lockBySetStampIdx(ICFSecSecUserPWHistoryBySetStampIdxKey key) {
		return( cfsec31SecUserPWHistoryRepository.lockBySetStampIdx(key.getRequiredPWSetStamp()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredPWReplacedStamp
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWHistory lockByReplacedStampIdx(@Param("pWReplacedStamp") LocalDateTime requiredPWReplacedStamp) {
		return( cfsec31SecUserPWHistoryRepository.lockByReplacedStampIdx(requiredPWReplacedStamp));
	}

	/**
	 *	ICFSecSecUserPWHistoryByReplacedStampIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWHistory lockByReplacedStampIdx(ICFSecSecUserPWHistoryByReplacedStampIdxKey key) {
		return( cfsec31SecUserPWHistoryRepository.lockByReplacedStampIdx(key.getRequiredPWReplacedStamp()));
	}

	// CFSecSecUserPWHistory specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredPWSetStamp
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("pWSetStamp") LocalDateTime requiredPWSetStamp) {
		cfsec31SecUserPWHistoryRepository.deleteByIdIdx(requiredSecUserId,
			requiredPWSetStamp);
	}

	/**
	 *	ICFSecSecUserPWHistoryByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecUserPWHistoryByIdIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(ICFSecSecUserPWHistoryPKey key) {
		cfsec31SecUserPWHistoryRepository.deleteByIdIdx(key.getRequiredSecUserId(), key.getRequiredPWSetStamp());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		cfsec31SecUserPWHistoryRepository.deleteByUserIdx(requiredSecUserId);
	}

	/**
	 *	ICFSecSecUserPWHistoryByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecUserPWHistoryByUserIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUserIdx(ICFSecSecUserPWHistoryByUserIdxKey key) {
		cfsec31SecUserPWHistoryRepository.deleteByUserIdx(key.getRequiredSecUserId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredPWSetStamp
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteBySetStampIdx(@Param("pWSetStamp") LocalDateTime requiredPWSetStamp) {
		cfsec31SecUserPWHistoryRepository.deleteBySetStampIdx(requiredPWSetStamp);
	}

	/**
	 *	ICFSecSecUserPWHistoryBySetStampIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecUserPWHistoryBySetStampIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteBySetStampIdx(ICFSecSecUserPWHistoryBySetStampIdxKey key) {
		cfsec31SecUserPWHistoryRepository.deleteBySetStampIdx(key.getRequiredPWSetStamp());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredPWReplacedStamp
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByReplacedStampIdx(@Param("pWReplacedStamp") LocalDateTime requiredPWReplacedStamp) {
		cfsec31SecUserPWHistoryRepository.deleteByReplacedStampIdx(requiredPWReplacedStamp);
	}

	/**
	 *	ICFSecSecUserPWHistoryByReplacedStampIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecUserPWHistoryByReplacedStampIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByReplacedStampIdx(ICFSecSecUserPWHistoryByReplacedStampIdxKey key) {
		cfsec31SecUserPWHistoryRepository.deleteByReplacedStampIdx(key.getRequiredPWReplacedStamp());
	}

}

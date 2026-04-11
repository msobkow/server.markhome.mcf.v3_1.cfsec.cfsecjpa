// Description: Java 25 Spring JPA Service for SecUserPWReset

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
 *	Service for the CFSecSecUserPWReset entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecSecUserPWResetRepository to access them.
 */
@Service("cfsec31JpaSecUserPWResetService")
public class CFSecJpaSecUserPWResetService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaSecUserPWResetRepository cfsec31SecUserPWResetRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaSecUserPWReset, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWReset create(CFSecJpaSecUserPWReset data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		if(data.getRequiredSentToEMailAddr() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredSentToEMailAddr");
		}
		if(data.getRequiredPasswordResetUuid6() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredPasswordResetUuid6");
		}
		try {
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31SecUserPWResetRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFSecJpaSecUserPWReset)(cfsec31SecUserPWResetRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfsec31SecUserPWResetRepository.save(data);
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
	public CFSecJpaSecUserPWReset update(CFSecJpaSecUserPWReset data) {
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
		if(data.getRequiredSentToEMailAddr() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredSentToEMailAddr");
		}
		if(data.getRequiredPasswordResetUuid6() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredPasswordResetUuid6");
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaSecUserPWReset existing = cfsec31SecUserPWResetRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecSecUserPWReset to existing object
		// Apply data columns of CFSecSecUserPWReset to existing object
		existing.setRequiredSentToEMailAddr(data.getRequiredSentToEMailAddr());
		existing.setRequiredPasswordResetUuid6(data.getRequiredPasswordResetUuid6());
		existing.setRequiredNewAccount(data.getRequiredNewAccount());
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31SecUserPWResetRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWReset find(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecUserPWResetRepository.get(requiredSecUserId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserPWReset> findAll() {
		return( cfsec31SecUserPWResetRepository.findAll() );
	}

	// CFSecSecUserPWReset specified index finders

	/**
	 *	Find an entity using the columns of the ICFSecSecUserPWResetByUUuid6IdxKey as arguments.
	 *
	 *		@param requiredPasswordResetUuid6
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWReset findByUUuid6Idx(@Param("passwordResetUuid6") CFLibUuid6 requiredPasswordResetUuid6) {
		return( cfsec31SecUserPWResetRepository.findByUUuid6Idx(requiredPasswordResetUuid6));
	}

	/**
	 *	ICFSecSecUserPWResetByUUuid6IdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecUserPWResetByUUuid6IdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWReset findByUUuid6Idx(ICFSecSecUserPWResetByUUuid6IdxKey key) {
		return( cfsec31SecUserPWResetRepository.findByUUuid6Idx(key.getRequiredPasswordResetUuid6()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecUserPWResetBySentEMAddrIdxKey as arguments.
	 *
	 *		@param requiredSentToEMailAddr
	 *
	 *		@return List&lt;CFSecJpaSecUserPWReset&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserPWReset> findBySentEMAddrIdx(@Param("sentToEMailAddr") String requiredSentToEMailAddr) {
		return( cfsec31SecUserPWResetRepository.findBySentEMAddrIdx(requiredSentToEMailAddr));
	}

	/**
	 *	ICFSecSecUserPWResetBySentEMAddrIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecUserPWResetBySentEMAddrIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserPWReset> findBySentEMAddrIdx(ICFSecSecUserPWResetBySentEMAddrIdxKey key) {
		return( cfsec31SecUserPWResetRepository.findBySentEMAddrIdx(key.getRequiredSentToEMailAddr()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecUserPWResetByNewAcctIdxKey as arguments.
	 *
	 *		@param requiredNewAccount
	 *
	 *		@return List&lt;CFSecJpaSecUserPWReset&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserPWReset> findByNewAcctIdx(@Param("newAccount") boolean requiredNewAccount) {
		return( cfsec31SecUserPWResetRepository.findByNewAcctIdx(requiredNewAccount));
	}

	/**
	 *	ICFSecSecUserPWResetByNewAcctIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecUserPWResetByNewAcctIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserPWReset> findByNewAcctIdx(ICFSecSecUserPWResetByNewAcctIdxKey key) {
		return( cfsec31SecUserPWResetRepository.findByNewAcctIdx(key.getRequiredNewAccount()));
	}

	// CFSecSecUserPWReset specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWReset lockByIdIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecUserPWResetRepository.lockByIdIdx(requiredSecUserId));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredPasswordResetUuid6
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWReset lockByUUuid6Idx(@Param("passwordResetUuid6") CFLibUuid6 requiredPasswordResetUuid6) {
		return( cfsec31SecUserPWResetRepository.lockByUUuid6Idx(requiredPasswordResetUuid6));
	}

	/**
	 *	ICFSecSecUserPWResetByUUuid6IdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPWReset lockByUUuid6Idx(ICFSecSecUserPWResetByUUuid6IdxKey key) {
		return( cfsec31SecUserPWResetRepository.lockByUUuid6Idx(key.getRequiredPasswordResetUuid6()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSentToEMailAddr
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserPWReset> lockBySentEMAddrIdx(@Param("sentToEMailAddr") String requiredSentToEMailAddr) {
		return( cfsec31SecUserPWResetRepository.lockBySentEMAddrIdx(requiredSentToEMailAddr));
	}

	/**
	 *	ICFSecSecUserPWResetBySentEMAddrIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserPWReset> lockBySentEMAddrIdx(ICFSecSecUserPWResetBySentEMAddrIdxKey key) {
		return( cfsec31SecUserPWResetRepository.lockBySentEMAddrIdx(key.getRequiredSentToEMailAddr()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredNewAccount
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserPWReset> lockByNewAcctIdx(@Param("newAccount") boolean requiredNewAccount) {
		return( cfsec31SecUserPWResetRepository.lockByNewAcctIdx(requiredNewAccount));
	}

	/**
	 *	ICFSecSecUserPWResetByNewAcctIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserPWReset> lockByNewAcctIdx(ICFSecSecUserPWResetByNewAcctIdxKey key) {
		return( cfsec31SecUserPWResetRepository.lockByNewAcctIdx(key.getRequiredNewAccount()));
	}

	// CFSecSecUserPWReset specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		cfsec31SecUserPWResetRepository.deleteByIdIdx(requiredSecUserId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredPasswordResetUuid6
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUUuid6Idx(@Param("passwordResetUuid6") CFLibUuid6 requiredPasswordResetUuid6) {
		cfsec31SecUserPWResetRepository.deleteByUUuid6Idx(requiredPasswordResetUuid6);
	}

	/**
	 *	ICFSecSecUserPWResetByUUuid6IdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecUserPWResetByUUuid6IdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUUuid6Idx(ICFSecSecUserPWResetByUUuid6IdxKey key) {
		cfsec31SecUserPWResetRepository.deleteByUUuid6Idx(key.getRequiredPasswordResetUuid6());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSentToEMailAddr
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteBySentEMAddrIdx(@Param("sentToEMailAddr") String requiredSentToEMailAddr) {
		cfsec31SecUserPWResetRepository.deleteBySentEMAddrIdx(requiredSentToEMailAddr);
	}

	/**
	 *	ICFSecSecUserPWResetBySentEMAddrIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecUserPWResetBySentEMAddrIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteBySentEMAddrIdx(ICFSecSecUserPWResetBySentEMAddrIdxKey key) {
		cfsec31SecUserPWResetRepository.deleteBySentEMAddrIdx(key.getRequiredSentToEMailAddr());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredNewAccount
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByNewAcctIdx(@Param("newAccount") boolean requiredNewAccount) {
		cfsec31SecUserPWResetRepository.deleteByNewAcctIdx(requiredNewAccount);
	}

	/**
	 *	ICFSecSecUserPWResetByNewAcctIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecUserPWResetByNewAcctIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByNewAcctIdx(ICFSecSecUserPWResetByNewAcctIdxKey key) {
		cfsec31SecUserPWResetRepository.deleteByNewAcctIdx(key.getRequiredNewAccount());
	}

}

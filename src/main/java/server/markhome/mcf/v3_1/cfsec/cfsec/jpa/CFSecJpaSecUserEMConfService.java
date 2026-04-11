// Description: Java 25 Spring JPA Service for SecUserEMConf

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
 *	Service for the CFSecSecUserEMConf entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecSecUserEMConfRepository to access them.
 */
@Service("cfsec31JpaSecUserEMConfService")
public class CFSecJpaSecUserEMConfService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaSecUserEMConfRepository cfsec31SecUserEMConfRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaSecUserEMConf, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserEMConf create(CFSecJpaSecUserEMConf data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		if(data.getRequiredConfirmEMailAddr() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredConfirmEMailAddr");
		}
		if(data.getRequiredEMailSentStamp() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredEMailSentStamp");
		}
		if(data.getRequiredEMConfirmationUuid6() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredEMConfirmationUuid6");
		}
		try {
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31SecUserEMConfRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFSecJpaSecUserEMConf)(cfsec31SecUserEMConfRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfsec31SecUserEMConfRepository.save(data);
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
	public CFSecJpaSecUserEMConf update(CFSecJpaSecUserEMConf data) {
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
		if(data.getRequiredConfirmEMailAddr() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredConfirmEMailAddr");
		}
		if(data.getRequiredEMailSentStamp() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredEMailSentStamp");
		}
		if(data.getRequiredEMConfirmationUuid6() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredEMConfirmationUuid6");
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaSecUserEMConf existing = cfsec31SecUserEMConfRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecSecUserEMConf to existing object
		// Apply data columns of CFSecSecUserEMConf to existing object
		existing.setRequiredConfirmEMailAddr(data.getRequiredConfirmEMailAddr());
		existing.setRequiredEMailSentStamp(data.getRequiredEMailSentStamp());
		existing.setRequiredEMConfirmationUuid6(data.getRequiredEMConfirmationUuid6());
		existing.setRequiredNewAccount(data.getRequiredNewAccount());
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31SecUserEMConfRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserEMConf find(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecUserEMConfRepository.get(requiredSecUserId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserEMConf> findAll() {
		return( cfsec31SecUserEMConfRepository.findAll() );
	}

	// CFSecSecUserEMConf specified index finders

	/**
	 *	Find an entity using the columns of the ICFSecSecUserEMConfByUUuid6IdxKey as arguments.
	 *
	 *		@param requiredEMConfirmationUuid6
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserEMConf findByUUuid6Idx(@Param("eMConfirmationUuid6") CFLibUuid6 requiredEMConfirmationUuid6) {
		return( cfsec31SecUserEMConfRepository.findByUUuid6Idx(requiredEMConfirmationUuid6));
	}

	/**
	 *	ICFSecSecUserEMConfByUUuid6IdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecUserEMConfByUUuid6IdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserEMConf findByUUuid6Idx(ICFSecSecUserEMConfByUUuid6IdxKey key) {
		return( cfsec31SecUserEMConfRepository.findByUUuid6Idx(key.getRequiredEMConfirmationUuid6()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecUserEMConfByConfEMAddrIdxKey as arguments.
	 *
	 *		@param requiredConfirmEMailAddr
	 *
	 *		@return List&lt;CFSecJpaSecUserEMConf&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserEMConf> findByConfEMAddrIdx(@Param("confirmEMailAddr") String requiredConfirmEMailAddr) {
		return( cfsec31SecUserEMConfRepository.findByConfEMAddrIdx(requiredConfirmEMailAddr));
	}

	/**
	 *	ICFSecSecUserEMConfByConfEMAddrIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecUserEMConfByConfEMAddrIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserEMConf> findByConfEMAddrIdx(ICFSecSecUserEMConfByConfEMAddrIdxKey key) {
		return( cfsec31SecUserEMConfRepository.findByConfEMAddrIdx(key.getRequiredConfirmEMailAddr()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecUserEMConfBySentStampIdxKey as arguments.
	 *
	 *		@param requiredEMailSentStamp
	 *
	 *		@return List&lt;CFSecJpaSecUserEMConf&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserEMConf> findBySentStampIdx(@Param("eMailSentStamp") LocalDateTime requiredEMailSentStamp) {
		return( cfsec31SecUserEMConfRepository.findBySentStampIdx(requiredEMailSentStamp));
	}

	/**
	 *	ICFSecSecUserEMConfBySentStampIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecUserEMConfBySentStampIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserEMConf> findBySentStampIdx(ICFSecSecUserEMConfBySentStampIdxKey key) {
		return( cfsec31SecUserEMConfRepository.findBySentStampIdx(key.getRequiredEMailSentStamp()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecUserEMConfByNewAcctIdxKey as arguments.
	 *
	 *		@param requiredNewAccount
	 *
	 *		@return List&lt;CFSecJpaSecUserEMConf&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserEMConf> findByNewAcctIdx(@Param("newAccount") boolean requiredNewAccount) {
		return( cfsec31SecUserEMConfRepository.findByNewAcctIdx(requiredNewAccount));
	}

	/**
	 *	ICFSecSecUserEMConfByNewAcctIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecUserEMConfByNewAcctIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserEMConf> findByNewAcctIdx(ICFSecSecUserEMConfByNewAcctIdxKey key) {
		return( cfsec31SecUserEMConfRepository.findByNewAcctIdx(key.getRequiredNewAccount()));
	}

	// CFSecSecUserEMConf specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserEMConf lockByIdIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecUserEMConfRepository.lockByIdIdx(requiredSecUserId));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredEMConfirmationUuid6
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserEMConf lockByUUuid6Idx(@Param("eMConfirmationUuid6") CFLibUuid6 requiredEMConfirmationUuid6) {
		return( cfsec31SecUserEMConfRepository.lockByUUuid6Idx(requiredEMConfirmationUuid6));
	}

	/**
	 *	ICFSecSecUserEMConfByUUuid6IdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserEMConf lockByUUuid6Idx(ICFSecSecUserEMConfByUUuid6IdxKey key) {
		return( cfsec31SecUserEMConfRepository.lockByUUuid6Idx(key.getRequiredEMConfirmationUuid6()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredConfirmEMailAddr
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserEMConf> lockByConfEMAddrIdx(@Param("confirmEMailAddr") String requiredConfirmEMailAddr) {
		return( cfsec31SecUserEMConfRepository.lockByConfEMAddrIdx(requiredConfirmEMailAddr));
	}

	/**
	 *	ICFSecSecUserEMConfByConfEMAddrIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserEMConf> lockByConfEMAddrIdx(ICFSecSecUserEMConfByConfEMAddrIdxKey key) {
		return( cfsec31SecUserEMConfRepository.lockByConfEMAddrIdx(key.getRequiredConfirmEMailAddr()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredEMailSentStamp
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserEMConf> lockBySentStampIdx(@Param("eMailSentStamp") LocalDateTime requiredEMailSentStamp) {
		return( cfsec31SecUserEMConfRepository.lockBySentStampIdx(requiredEMailSentStamp));
	}

	/**
	 *	ICFSecSecUserEMConfBySentStampIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserEMConf> lockBySentStampIdx(ICFSecSecUserEMConfBySentStampIdxKey key) {
		return( cfsec31SecUserEMConfRepository.lockBySentStampIdx(key.getRequiredEMailSentStamp()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredNewAccount
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserEMConf> lockByNewAcctIdx(@Param("newAccount") boolean requiredNewAccount) {
		return( cfsec31SecUserEMConfRepository.lockByNewAcctIdx(requiredNewAccount));
	}

	/**
	 *	ICFSecSecUserEMConfByNewAcctIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserEMConf> lockByNewAcctIdx(ICFSecSecUserEMConfByNewAcctIdxKey key) {
		return( cfsec31SecUserEMConfRepository.lockByNewAcctIdx(key.getRequiredNewAccount()));
	}

	// CFSecSecUserEMConf specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		cfsec31SecUserEMConfRepository.deleteByIdIdx(requiredSecUserId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredEMConfirmationUuid6
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUUuid6Idx(@Param("eMConfirmationUuid6") CFLibUuid6 requiredEMConfirmationUuid6) {
		cfsec31SecUserEMConfRepository.deleteByUUuid6Idx(requiredEMConfirmationUuid6);
	}

	/**
	 *	ICFSecSecUserEMConfByUUuid6IdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecUserEMConfByUUuid6IdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUUuid6Idx(ICFSecSecUserEMConfByUUuid6IdxKey key) {
		cfsec31SecUserEMConfRepository.deleteByUUuid6Idx(key.getRequiredEMConfirmationUuid6());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredConfirmEMailAddr
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByConfEMAddrIdx(@Param("confirmEMailAddr") String requiredConfirmEMailAddr) {
		cfsec31SecUserEMConfRepository.deleteByConfEMAddrIdx(requiredConfirmEMailAddr);
	}

	/**
	 *	ICFSecSecUserEMConfByConfEMAddrIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecUserEMConfByConfEMAddrIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByConfEMAddrIdx(ICFSecSecUserEMConfByConfEMAddrIdxKey key) {
		cfsec31SecUserEMConfRepository.deleteByConfEMAddrIdx(key.getRequiredConfirmEMailAddr());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredEMailSentStamp
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteBySentStampIdx(@Param("eMailSentStamp") LocalDateTime requiredEMailSentStamp) {
		cfsec31SecUserEMConfRepository.deleteBySentStampIdx(requiredEMailSentStamp);
	}

	/**
	 *	ICFSecSecUserEMConfBySentStampIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecUserEMConfBySentStampIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteBySentStampIdx(ICFSecSecUserEMConfBySentStampIdxKey key) {
		cfsec31SecUserEMConfRepository.deleteBySentStampIdx(key.getRequiredEMailSentStamp());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredNewAccount
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByNewAcctIdx(@Param("newAccount") boolean requiredNewAccount) {
		cfsec31SecUserEMConfRepository.deleteByNewAcctIdx(requiredNewAccount);
	}

	/**
	 *	ICFSecSecUserEMConfByNewAcctIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecUserEMConfByNewAcctIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByNewAcctIdx(ICFSecSecUserEMConfByNewAcctIdxKey key) {
		cfsec31SecUserEMConfRepository.deleteByNewAcctIdx(key.getRequiredNewAccount());
	}

}

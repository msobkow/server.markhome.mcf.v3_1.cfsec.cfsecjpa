// Description: Java 25 Spring JPA Service for SecUserPassword

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
 *	Service for the CFSecSecUserPassword entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecSecUserPasswordRepository to access them.
 */
@Service("cfsec31JpaSecUserPasswordService")
public class CFSecJpaSecUserPasswordService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaSecUserPasswordRepository cfsec31SecUserPasswordRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaSecUserPassword, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPassword create(CFSecJpaSecUserPassword data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		if(data.getRequiredPWSetStamp() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredPWSetStamp");
		}
		if(data.getRequiredPasswordHash() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredPasswordHash");
		}
		try {
			if(data.getPKey() != null && cfsec31SecUserPasswordRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFSecJpaSecUserPassword)(cfsec31SecUserPasswordRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfsec31SecUserPasswordRepository.save(data);
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
	public CFSecJpaSecUserPassword update(CFSecJpaSecUserPassword data) {
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
		if(data.getRequiredPWSetStamp() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredPWSetStamp");
		}
		if(data.getRequiredPasswordHash() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredPasswordHash");
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaSecUserPassword existing = cfsec31SecUserPasswordRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecSecUserPassword to existing object
		// Apply data columns of CFSecSecUserPassword to existing object
		existing.setRequiredPWSetStamp(data.getRequiredPWSetStamp());
		existing.setRequiredPasswordHash(data.getRequiredPasswordHash());
		// Save the changes we've made
		return cfsec31SecUserPasswordRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPassword find(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecUserPasswordRepository.get(requiredSecUserId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserPassword> findAll() {
		return( cfsec31SecUserPasswordRepository.findAll() );
	}

	// CFSecSecUserPassword specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecUserPasswordBySetStampIdxKey as arguments.
	 *
	 *		@param requiredPWSetStamp
	 *
	 *		@return List&lt;CFSecJpaSecUserPassword&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserPassword> findBySetStampIdx(@Param("pWSetStamp") LocalDateTime requiredPWSetStamp) {
		return( cfsec31SecUserPasswordRepository.findBySetStampIdx(requiredPWSetStamp));
	}

	/**
	 *	ICFSecSecUserPasswordBySetStampIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecUserPasswordBySetStampIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserPassword> findBySetStampIdx(ICFSecSecUserPasswordBySetStampIdxKey key) {
		return( cfsec31SecUserPasswordRepository.findBySetStampIdx(key.getRequiredPWSetStamp()));
	}

	// CFSecSecUserPassword specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecUserPassword lockByIdIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecUserPasswordRepository.lockByIdIdx(requiredSecUserId));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredPWSetStamp
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserPassword> lockBySetStampIdx(@Param("pWSetStamp") LocalDateTime requiredPWSetStamp) {
		return( cfsec31SecUserPasswordRepository.lockBySetStampIdx(requiredPWSetStamp));
	}

	/**
	 *	ICFSecSecUserPasswordBySetStampIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecUserPassword> lockBySetStampIdx(ICFSecSecUserPasswordBySetStampIdxKey key) {
		return( cfsec31SecUserPasswordRepository.lockBySetStampIdx(key.getRequiredPWSetStamp()));
	}

	// CFSecSecUserPassword specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		cfsec31SecUserPasswordRepository.deleteByIdIdx(requiredSecUserId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredPWSetStamp
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteBySetStampIdx(@Param("pWSetStamp") LocalDateTime requiredPWSetStamp) {
		cfsec31SecUserPasswordRepository.deleteBySetStampIdx(requiredPWSetStamp);
	}

	/**
	 *	ICFSecSecUserPasswordBySetStampIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecUserPasswordBySetStampIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteBySetStampIdx(ICFSecSecUserPasswordBySetStampIdxKey key) {
		cfsec31SecUserPasswordRepository.deleteBySetStampIdx(key.getRequiredPWSetStamp());
	}
}

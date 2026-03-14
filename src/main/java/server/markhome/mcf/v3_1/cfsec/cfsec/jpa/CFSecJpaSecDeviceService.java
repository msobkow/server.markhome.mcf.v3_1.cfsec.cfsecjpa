// Description: Java 25 Spring JPA Service for SecDevice

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
 *	Service for the CFSecSecDevice entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecSecDeviceRepository to access them.
 */
@Service("cfsec31JpaSecDeviceService")
public class CFSecJpaSecDeviceService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaSecDeviceRepository cfsec31SecDeviceRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaSecDevice, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecDevice create(CFSecJpaSecDevice data) {
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
			if(data.getPKey() != null && cfsec31SecDeviceRepository.existsById((CFSecJpaSecDevicePKey)data.getPKey())) {
				return( (CFSecJpaSecDevice)(cfsec31SecDeviceRepository.findById((CFSecJpaSecDevicePKey)(data.getPKey())).get()));
			}
			return cfsec31SecDeviceRepository.save(data);
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
	public CFSecJpaSecDevice update(CFSecJpaSecDevice data) {
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
		CFSecJpaSecDevice existing = cfsec31SecDeviceRepository.findById((CFSecJpaSecDevicePKey)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecSecDevice to existing object
		// Apply data columns of CFSecSecDevice to existing object
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31SecDeviceRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredDevName
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecDevice find(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("devName") String requiredDevName) {
		return( cfsec31SecDeviceRepository.get(requiredSecUserId,
			requiredDevName));
	}

	/**
	 *	ICFSecSecDevicePKey based find method for object-based access.
	 *
	 *		@param key The key of the entity to be find.
	 *
	 *		@return The entity find, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecDevice find(ICFSecSecDevicePKey key) {
		return( cfsec31SecDeviceRepository.get(key.getRequiredSecUserId(), key.getRequiredDevName()));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecDevice> findAll() {
		return( cfsec31SecDeviceRepository.findAll() );
	}

	// CFSecSecDevice specified index finders

	/**
	 *	Find an entity using the columns of the ICFSecSecDeviceByNameIdxKey as arguments.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredDevName
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecDevice findByNameIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("devName") String requiredDevName) {
		return( cfsec31SecDeviceRepository.findByNameIdx(requiredSecUserId,
			requiredDevName));
	}

	/**
	 *	ICFSecSecDeviceByNameIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecDeviceByNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecDevice findByNameIdx(ICFSecSecDeviceByNameIdxKey key) {
		return( cfsec31SecDeviceRepository.findByNameIdx(key.getRequiredSecUserId(), key.getRequiredDevName()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecSecDeviceByUserIdxKey as arguments.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return List&lt;CFSecJpaSecDevice&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecDevice> findByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecDeviceRepository.findByUserIdx(requiredSecUserId));
	}

	/**
	 *	ICFSecSecDeviceByUserIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecSecDeviceByUserIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecDevice> findByUserIdx(ICFSecSecDeviceByUserIdxKey key) {
		return( cfsec31SecDeviceRepository.findByUserIdx(key.getRequiredSecUserId()));
	}

	// CFSecSecDevice specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredDevName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecDevice lockByIdIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("devName") String requiredDevName) {
		return( cfsec31SecDeviceRepository.lockByIdIdx(requiredSecUserId,
			requiredDevName));
	}

	/**
	 *	ICFSecSecDevicePKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecDevice lockByIdIdx(ICFSecSecDevicePKey key) {
		return( cfsec31SecDeviceRepository.lockByIdIdx(key.getRequiredSecUserId(), key.getRequiredDevName()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredDevName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecDevice lockByNameIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("devName") String requiredDevName) {
		return( cfsec31SecDeviceRepository.lockByNameIdx(requiredSecUserId,
			requiredDevName));
	}

	/**
	 *	ICFSecSecDeviceByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaSecDevice lockByNameIdx(ICFSecSecDeviceByNameIdxKey key) {
		return( cfsec31SecDeviceRepository.lockByNameIdx(key.getRequiredSecUserId(), key.getRequiredDevName()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecDevice> lockByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		return( cfsec31SecDeviceRepository.lockByUserIdx(requiredSecUserId));
	}

	/**
	 *	ICFSecSecDeviceByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaSecDevice> lockByUserIdx(ICFSecSecDeviceByUserIdxKey key) {
		return( cfsec31SecDeviceRepository.lockByUserIdx(key.getRequiredSecUserId()));
	}

	// CFSecSecDevice specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredDevName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("devName") String requiredDevName) {
		cfsec31SecDeviceRepository.deleteByIdIdx(requiredSecUserId,
			requiredDevName);
	}

	/**
	 *	ICFSecSecDeviceByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecDeviceByIdIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(ICFSecSecDevicePKey key) {
		cfsec31SecDeviceRepository.deleteByIdIdx(key.getRequiredSecUserId(), key.getRequiredDevName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredDevName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByNameIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("devName") String requiredDevName) {
		cfsec31SecDeviceRepository.deleteByNameIdx(requiredSecUserId,
			requiredDevName);
	}

	/**
	 *	ICFSecSecDeviceByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecDeviceByNameIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByNameIdx(ICFSecSecDeviceByNameIdxKey key) {
		cfsec31SecDeviceRepository.deleteByNameIdx(key.getRequiredSecUserId(), key.getRequiredDevName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId) {
		cfsec31SecDeviceRepository.deleteByUserIdx(requiredSecUserId);
	}

	/**
	 *	ICFSecSecDeviceByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecSecDeviceByUserIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUserIdx(ICFSecSecDeviceByUserIdxKey key) {
		cfsec31SecDeviceRepository.deleteByUserIdx(key.getRequiredSecUserId());
	}
}

// Description: Java 25 Spring JPA Service for ServiceType

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
 *	Service for the CFSecServiceType entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecServiceTypeRepository to access them.
 */
@Service("cfsec31JpaServiceTypeService")
public class CFSecJpaServiceTypeService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaServiceTypeRepository cfsec31ServiceTypeRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaServiceType, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaServiceType create(CFSecJpaServiceType data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		CFLibDbKeyHash256 originalRequiredServiceTypeId = data.getRequiredServiceTypeId();
		boolean generatedRequiredServiceTypeId = false;
		if(data.getRequiredDescription() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredDescription");
		}
		try {
			if (data.getRequiredServiceTypeId() == null || data.getRequiredServiceTypeId().isNull()) {
				data.setRequiredServiceTypeId(new CFLibDbKeyHash256(0));
				generatedRequiredServiceTypeId = true;
			}
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31ServiceTypeRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFSecJpaServiceType)(cfsec31ServiceTypeRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfsec31ServiceTypeRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredServiceTypeId) {
					data.setRequiredServiceTypeId(originalRequiredServiceTypeId);
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
	public CFSecJpaServiceType update(CFSecJpaServiceType data) {
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
		if(data.getRequiredDescription() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredDescription");
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaServiceType existing = cfsec31ServiceTypeRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecServiceType to existing object
		// Apply data columns of CFSecServiceType to existing object
		existing.setRequiredDescription(data.getRequiredDescription());
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31ServiceTypeRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredServiceTypeId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaServiceType find(@Param("serviceTypeId") CFLibDbKeyHash256 requiredServiceTypeId) {
		return( cfsec31ServiceTypeRepository.get(requiredServiceTypeId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaServiceType> findAll() {
		return( cfsec31ServiceTypeRepository.findAll() );
	}

	// CFSecServiceType specified index finders

	/**
	 *	Find an entity using the columns of the ICFSecServiceTypeByUDescrIdxKey as arguments.
	 *
	 *		@param requiredDescription
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaServiceType findByUDescrIdx(@Param("description") String requiredDescription) {
		return( cfsec31ServiceTypeRepository.findByUDescrIdx(requiredDescription));
	}

	/**
	 *	ICFSecServiceTypeByUDescrIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecServiceTypeByUDescrIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaServiceType findByUDescrIdx(ICFSecServiceTypeByUDescrIdxKey key) {
		return( cfsec31ServiceTypeRepository.findByUDescrIdx(key.getRequiredDescription()));
	}

	// CFSecServiceType specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredServiceTypeId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaServiceType lockByIdIdx(@Param("serviceTypeId") CFLibDbKeyHash256 requiredServiceTypeId) {
		return( cfsec31ServiceTypeRepository.lockByIdIdx(requiredServiceTypeId));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredDescription
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaServiceType lockByUDescrIdx(@Param("description") String requiredDescription) {
		return( cfsec31ServiceTypeRepository.lockByUDescrIdx(requiredDescription));
	}

	/**
	 *	ICFSecServiceTypeByUDescrIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaServiceType lockByUDescrIdx(ICFSecServiceTypeByUDescrIdxKey key) {
		return( cfsec31ServiceTypeRepository.lockByUDescrIdx(key.getRequiredDescription()));
	}

	// CFSecServiceType specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredServiceTypeId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("serviceTypeId") CFLibDbKeyHash256 requiredServiceTypeId) {
		cfsec31ServiceTypeRepository.deleteByIdIdx(requiredServiceTypeId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredDescription
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUDescrIdx(@Param("description") String requiredDescription) {
		cfsec31ServiceTypeRepository.deleteByUDescrIdx(requiredDescription);
	}

	/**
	 *	ICFSecServiceTypeByUDescrIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecServiceTypeByUDescrIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUDescrIdx(ICFSecServiceTypeByUDescrIdxKey key) {
		cfsec31ServiceTypeRepository.deleteByUDescrIdx(key.getRequiredDescription());
	}
}

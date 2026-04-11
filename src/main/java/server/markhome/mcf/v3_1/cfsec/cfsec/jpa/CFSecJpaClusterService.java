// Description: Java 25 Spring JPA Service for Cluster

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
 *	Service for the CFSecCluster entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecClusterRepository to access them.
 */
@Service("cfsec31JpaClusterService")
public class CFSecJpaClusterService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaClusterRepository cfsec31ClusterRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaCluster, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaCluster create(CFSecJpaCluster data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		CFLibDbKeyHash256 originalRequiredId = data.getRequiredId();
		boolean generatedRequiredId = false;
		if(data.getRequiredFullDomName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredFullDomName");
		}
		if(data.getRequiredDescription() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredDescription");
		}
		try {
			if (data.getRequiredId() == null || data.getRequiredId().isNull()) {
				data.setRequiredId(new CFLibDbKeyHash256(0));
				generatedRequiredId = true;
			}
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31ClusterRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFSecJpaCluster)(cfsec31ClusterRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfsec31ClusterRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredId) {
					data.setRequiredId(originalRequiredId);
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
	public CFSecJpaCluster update(CFSecJpaCluster data) {
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
		if(data.getRequiredFullDomName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredFullDomName");
		}
		if(data.getRequiredDescription() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredDescription");
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaCluster existing = cfsec31ClusterRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecCluster to existing object
		// Apply data columns of CFSecCluster to existing object
		existing.setRequiredFullDomName(data.getRequiredFullDomName());
		existing.setRequiredDescription(data.getRequiredDescription());
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31ClusterRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaCluster find(@Param("id") CFLibDbKeyHash256 requiredId) {
		return( cfsec31ClusterRepository.get(requiredId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaCluster> findAll() {
		return( cfsec31ClusterRepository.findAll() );
	}

	// CFSecCluster specified index finders

	/**
	 *	Find an entity using the columns of the ICFSecClusterByUDomNameIdxKey as arguments.
	 *
	 *		@param requiredFullDomName
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaCluster findByUDomNameIdx(@Param("fullDomName") String requiredFullDomName) {
		return( cfsec31ClusterRepository.findByUDomNameIdx(requiredFullDomName));
	}

	/**
	 *	ICFSecClusterByUDomNameIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecClusterByUDomNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaCluster findByUDomNameIdx(ICFSecClusterByUDomNameIdxKey key) {
		return( cfsec31ClusterRepository.findByUDomNameIdx(key.getRequiredFullDomName()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecClusterByUDescrIdxKey as arguments.
	 *
	 *		@param requiredDescription
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaCluster findByUDescrIdx(@Param("description") String requiredDescription) {
		return( cfsec31ClusterRepository.findByUDescrIdx(requiredDescription));
	}

	/**
	 *	ICFSecClusterByUDescrIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecClusterByUDescrIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaCluster findByUDescrIdx(ICFSecClusterByUDescrIdxKey key) {
		return( cfsec31ClusterRepository.findByUDescrIdx(key.getRequiredDescription()));
	}

	// CFSecCluster specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaCluster lockByIdIdx(@Param("id") CFLibDbKeyHash256 requiredId) {
		return( cfsec31ClusterRepository.lockByIdIdx(requiredId));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredFullDomName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaCluster lockByUDomNameIdx(@Param("fullDomName") String requiredFullDomName) {
		return( cfsec31ClusterRepository.lockByUDomNameIdx(requiredFullDomName));
	}

	/**
	 *	ICFSecClusterByUDomNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaCluster lockByUDomNameIdx(ICFSecClusterByUDomNameIdxKey key) {
		return( cfsec31ClusterRepository.lockByUDomNameIdx(key.getRequiredFullDomName()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredDescription
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaCluster lockByUDescrIdx(@Param("description") String requiredDescription) {
		return( cfsec31ClusterRepository.lockByUDescrIdx(requiredDescription));
	}

	/**
	 *	ICFSecClusterByUDescrIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaCluster lockByUDescrIdx(ICFSecClusterByUDescrIdxKey key) {
		return( cfsec31ClusterRepository.lockByUDescrIdx(key.getRequiredDescription()));
	}

	// CFSecCluster specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("id") CFLibDbKeyHash256 requiredId) {
		cfsec31ClusterRepository.deleteByIdIdx(requiredId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredFullDomName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUDomNameIdx(@Param("fullDomName") String requiredFullDomName) {
		cfsec31ClusterRepository.deleteByUDomNameIdx(requiredFullDomName);
	}

	/**
	 *	ICFSecClusterByUDomNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecClusterByUDomNameIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUDomNameIdx(ICFSecClusterByUDomNameIdxKey key) {
		cfsec31ClusterRepository.deleteByUDomNameIdx(key.getRequiredFullDomName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredDescription
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUDescrIdx(@Param("description") String requiredDescription) {
		cfsec31ClusterRepository.deleteByUDescrIdx(requiredDescription);
	}

	/**
	 *	ICFSecClusterByUDescrIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecClusterByUDescrIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUDescrIdx(ICFSecClusterByUDescrIdxKey key) {
		cfsec31ClusterRepository.deleteByUDescrIdx(key.getRequiredDescription());
	}

		// Customized tabletweak [CFSec::CFSec].[Cluster::Cluster].JpaTableServiceCustomServices
}

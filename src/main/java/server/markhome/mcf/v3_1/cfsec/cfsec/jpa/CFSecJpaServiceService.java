// Description: Java 25 Spring JPA Service for Service

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
 *	Service for the CFSecService entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecServiceRepository to access them.
 */
@Service("cfsec31JpaServiceService")
public class CFSecJpaServiceService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaServiceRepository cfsec31ServiceRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaService, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaService create(CFSecJpaService data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		CFLibDbKeyHash256 originalRequiredServiceId = data.getRequiredServiceId();
		boolean generatedRequiredServiceId = false;
		if(data.getRequiredClusterId() == null || data.getRequiredClusterId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredClusterId");
		}
		if(data.getRequiredHostNodeId() == null || data.getRequiredHostNodeId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredHostNodeId");
		}
		if(data.getRequiredServiceTypeId() == null || data.getRequiredServiceTypeId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredServiceTypeId");
		}
		if( data.getRequiredHostPort() < ICFSecService.HOSTPORT_MIN_VALUE ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				S_ProcName,
				0,
				"getRequiredHostPort()",
				data.getRequiredHostPort(),
				ICFSecService.HOSTPORT_MIN_VALUE );
		}
		try {
			if (data.getRequiredServiceId() == null || data.getRequiredServiceId().isNull()) {
				data.setRequiredServiceId(new CFLibDbKeyHash256(0));
				generatedRequiredServiceId = true;
			}
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31ServiceRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFSecJpaService)(cfsec31ServiceRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfsec31ServiceRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredServiceId) {
					data.setRequiredServiceId(originalRequiredServiceId);
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
	public CFSecJpaService update(CFSecJpaService data) {
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
		if(data.getRequiredClusterId() == null || data.getRequiredClusterId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredClusterId");
		}
		if(data.getRequiredHostNodeId() == null || data.getRequiredHostNodeId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredHostNodeId");
		}
		if(data.getRequiredServiceTypeId() == null || data.getRequiredServiceTypeId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredServiceTypeId");
		}
		if( data.getRequiredHostPort() < ICFSecService.HOSTPORT_MIN_VALUE ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				S_ProcName,
				0,
				"getRequiredHostPort()",
				data.getRequiredHostPort(),
				ICFSecService.HOSTPORT_MIN_VALUE );
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaService existing = cfsec31ServiceRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecService to existing object
		existing.setRequiredOwnerCluster(data.getRequiredOwnerCluster());
		existing.setOptionalContainerHost(data.getOptionalContainerHost());
		existing.setOptionalParentServiceType(data.getOptionalParentServiceType());
		// Apply data columns of CFSecService to existing object
		existing.setRequiredHostPort(data.getRequiredHostPort());
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31ServiceRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredServiceId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaService find(@Param("serviceId") CFLibDbKeyHash256 requiredServiceId) {
		return( cfsec31ServiceRepository.get(requiredServiceId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaService> findAll() {
		return( cfsec31ServiceRepository.findAll() );
	}

	// CFSecService specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecServiceByClusterIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return List&lt;CFSecJpaService&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaService> findByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		return( cfsec31ServiceRepository.findByClusterIdx(requiredClusterId));
	}

	/**
	 *	ICFSecServiceByClusterIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecServiceByClusterIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaService> findByClusterIdx(ICFSecServiceByClusterIdxKey key) {
		return( cfsec31ServiceRepository.findByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecServiceByHostIdxKey as arguments.
	 *
	 *		@param requiredHostNodeId
	 *
	 *		@return List&lt;CFSecJpaService&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaService> findByHostIdx(@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId) {
		return( cfsec31ServiceRepository.findByHostIdx(requiredHostNodeId));
	}

	/**
	 *	ICFSecServiceByHostIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecServiceByHostIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaService> findByHostIdx(ICFSecServiceByHostIdxKey key) {
		return( cfsec31ServiceRepository.findByHostIdx(key.getRequiredHostNodeId()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecServiceByTypeIdxKey as arguments.
	 *
	 *		@param requiredServiceTypeId
	 *
	 *		@return List&lt;CFSecJpaService&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaService> findByTypeIdx(@Param("serviceTypeId") CFLibDbKeyHash256 requiredServiceTypeId) {
		return( cfsec31ServiceRepository.findByTypeIdx(requiredServiceTypeId));
	}

	/**
	 *	ICFSecServiceByTypeIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecServiceByTypeIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaService> findByTypeIdx(ICFSecServiceByTypeIdxKey key) {
		return( cfsec31ServiceRepository.findByTypeIdx(key.getRequiredServiceTypeId()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecServiceByUTypeIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *		@param requiredHostNodeId
	 *		@param requiredServiceTypeId
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaService findByUTypeIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId,
		@Param("serviceTypeId") CFLibDbKeyHash256 requiredServiceTypeId) {
		return( cfsec31ServiceRepository.findByUTypeIdx(requiredClusterId,
			requiredHostNodeId,
			requiredServiceTypeId));
	}

	/**
	 *	ICFSecServiceByUTypeIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecServiceByUTypeIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaService findByUTypeIdx(ICFSecServiceByUTypeIdxKey key) {
		return( cfsec31ServiceRepository.findByUTypeIdx(key.getRequiredClusterId(), key.getRequiredHostNodeId(), key.getRequiredServiceTypeId()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecServiceByUHostPortIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *		@param requiredHostNodeId
	 *		@param requiredHostPort
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaService findByUHostPortIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId,
		@Param("hostPort") short requiredHostPort) {
		return( cfsec31ServiceRepository.findByUHostPortIdx(requiredClusterId,
			requiredHostNodeId,
			requiredHostPort));
	}

	/**
	 *	ICFSecServiceByUHostPortIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecServiceByUHostPortIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaService findByUHostPortIdx(ICFSecServiceByUHostPortIdxKey key) {
		return( cfsec31ServiceRepository.findByUHostPortIdx(key.getRequiredClusterId(), key.getRequiredHostNodeId(), key.getRequiredHostPort()));
	}

	// CFSecService specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredServiceId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaService lockByIdIdx(@Param("serviceId") CFLibDbKeyHash256 requiredServiceId) {
		return( cfsec31ServiceRepository.lockByIdIdx(requiredServiceId));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaService> lockByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		return( cfsec31ServiceRepository.lockByClusterIdx(requiredClusterId));
	}

	/**
	 *	ICFSecServiceByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaService> lockByClusterIdx(ICFSecServiceByClusterIdxKey key) {
		return( cfsec31ServiceRepository.lockByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredHostNodeId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaService> lockByHostIdx(@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId) {
		return( cfsec31ServiceRepository.lockByHostIdx(requiredHostNodeId));
	}

	/**
	 *	ICFSecServiceByHostIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaService> lockByHostIdx(ICFSecServiceByHostIdxKey key) {
		return( cfsec31ServiceRepository.lockByHostIdx(key.getRequiredHostNodeId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredServiceTypeId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaService> lockByTypeIdx(@Param("serviceTypeId") CFLibDbKeyHash256 requiredServiceTypeId) {
		return( cfsec31ServiceRepository.lockByTypeIdx(requiredServiceTypeId));
	}

	/**
	 *	ICFSecServiceByTypeIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaService> lockByTypeIdx(ICFSecServiceByTypeIdxKey key) {
		return( cfsec31ServiceRepository.lockByTypeIdx(key.getRequiredServiceTypeId()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredHostNodeId
	 *		@param requiredServiceTypeId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaService lockByUTypeIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId,
		@Param("serviceTypeId") CFLibDbKeyHash256 requiredServiceTypeId) {
		return( cfsec31ServiceRepository.lockByUTypeIdx(requiredClusterId,
			requiredHostNodeId,
			requiredServiceTypeId));
	}

	/**
	 *	ICFSecServiceByUTypeIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaService lockByUTypeIdx(ICFSecServiceByUTypeIdxKey key) {
		return( cfsec31ServiceRepository.lockByUTypeIdx(key.getRequiredClusterId(), key.getRequiredHostNodeId(), key.getRequiredServiceTypeId()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredHostNodeId
	 *		@param requiredHostPort
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaService lockByUHostPortIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId,
		@Param("hostPort") short requiredHostPort) {
		return( cfsec31ServiceRepository.lockByUHostPortIdx(requiredClusterId,
			requiredHostNodeId,
			requiredHostPort));
	}

	/**
	 *	ICFSecServiceByUHostPortIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaService lockByUHostPortIdx(ICFSecServiceByUHostPortIdxKey key) {
		return( cfsec31ServiceRepository.lockByUHostPortIdx(key.getRequiredClusterId(), key.getRequiredHostNodeId(), key.getRequiredHostPort()));
	}

	// CFSecService specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredServiceId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("serviceId") CFLibDbKeyHash256 requiredServiceId) {
		cfsec31ServiceRepository.deleteByIdIdx(requiredServiceId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		cfsec31ServiceRepository.deleteByClusterIdx(requiredClusterId);
	}

	/**
	 *	ICFSecServiceByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecServiceByClusterIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByClusterIdx(ICFSecServiceByClusterIdxKey key) {
		cfsec31ServiceRepository.deleteByClusterIdx(key.getRequiredClusterId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredHostNodeId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByHostIdx(@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId) {
		cfsec31ServiceRepository.deleteByHostIdx(requiredHostNodeId);
	}

	/**
	 *	ICFSecServiceByHostIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecServiceByHostIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByHostIdx(ICFSecServiceByHostIdxKey key) {
		cfsec31ServiceRepository.deleteByHostIdx(key.getRequiredHostNodeId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredServiceTypeId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByTypeIdx(@Param("serviceTypeId") CFLibDbKeyHash256 requiredServiceTypeId) {
		cfsec31ServiceRepository.deleteByTypeIdx(requiredServiceTypeId);
	}

	/**
	 *	ICFSecServiceByTypeIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecServiceByTypeIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByTypeIdx(ICFSecServiceByTypeIdxKey key) {
		cfsec31ServiceRepository.deleteByTypeIdx(key.getRequiredServiceTypeId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredHostNodeId
	 *		@param requiredServiceTypeId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUTypeIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId,
		@Param("serviceTypeId") CFLibDbKeyHash256 requiredServiceTypeId) {
		cfsec31ServiceRepository.deleteByUTypeIdx(requiredClusterId,
			requiredHostNodeId,
			requiredServiceTypeId);
	}

	/**
	 *	ICFSecServiceByUTypeIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecServiceByUTypeIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUTypeIdx(ICFSecServiceByUTypeIdxKey key) {
		cfsec31ServiceRepository.deleteByUTypeIdx(key.getRequiredClusterId(), key.getRequiredHostNodeId(), key.getRequiredServiceTypeId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredHostNodeId
	 *		@param requiredHostPort
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUHostPortIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId,
		@Param("hostPort") short requiredHostPort) {
		cfsec31ServiceRepository.deleteByUHostPortIdx(requiredClusterId,
			requiredHostNodeId,
			requiredHostPort);
	}

	/**
	 *	ICFSecServiceByUHostPortIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecServiceByUHostPortIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUHostPortIdx(ICFSecServiceByUHostPortIdxKey key) {
		cfsec31ServiceRepository.deleteByUHostPortIdx(key.getRequiredClusterId(), key.getRequiredHostNodeId(), key.getRequiredHostPort());
	}
}

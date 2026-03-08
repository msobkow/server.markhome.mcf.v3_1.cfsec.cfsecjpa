// Description: Java 25 Spring JPA Service for HostNode

/*
 *	server.markhome.mcf.CFSec
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal 3.1 CFSec - Security Services
 *	
 *	This file is part of Mark's Code Fractal CFSec.
 *	
 *	Mark's Code Fractal CFSec is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU Library General Public License,
 *	Version 3 or later.
 *	
 *	Mark's Code Fractal CFSec is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU Library General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	Mark's Code Fractal CFSec is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU Library General Public License
 *	along with Mark's Code Fractal CFSec.  If not, see <https://www.gnu.org/licenses/>.
 *	
 *	If you wish to modify and use this code without publishing your changes in order to
 *	tie it to proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
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
 *	Service for the CFSecHostNode entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecHostNodeRepository to access them.
 */
@Service("cfsec31JpaHostNodeService")
public class CFSecJpaHostNodeService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaHostNodeRepository cfsec31HostNodeRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaHostNode, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaHostNode create(CFSecJpaHostNode data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		CFLibDbKeyHash256 originalRequiredHostNodeId = data.getRequiredHostNodeId();
		boolean generatedRequiredHostNodeId = false;
		if(data.getRequiredClusterId() == null || data.getRequiredClusterId().isNull()) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredClusterId");
		}
		if(data.getRequiredDescription() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredDescription");
		}
		if(data.getRequiredHostName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredHostName");
		}
		try {
			if (data.getRequiredHostNodeId() == null || data.getRequiredHostNodeId().isNull()) {
				data.setRequiredHostNodeId(new CFLibDbKeyHash256(0));
				generatedRequiredHostNodeId = true;
			}
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31HostNodeRepository.existsById((CFLibDbKeyHash256)data.getPKey())) {
				return( (CFSecJpaHostNode)(cfsec31HostNodeRepository.findById((CFLibDbKeyHash256)(data.getPKey())).get()));
			}
			return cfsec31HostNodeRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredHostNodeId) {
					data.setRequiredHostNodeId(originalRequiredHostNodeId);
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
	public CFSecJpaHostNode update(CFSecJpaHostNode data) {
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
		if(data.getRequiredDescription() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredDescription");
		}
		if(data.getRequiredHostName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredHostName");
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaHostNode existing = cfsec31HostNodeRepository.findById((CFLibDbKeyHash256)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecHostNode to existing object
		existing.setRequiredContainerCluster(data.getRequiredContainerCluster());
		// Apply data columns of CFSecHostNode to existing object
		existing.setRequiredDescription(data.getRequiredDescription());
		existing.setRequiredHostName(data.getRequiredHostName());
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31HostNodeRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredHostNodeId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaHostNode find(@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId) {
		return( cfsec31HostNodeRepository.get(requiredHostNodeId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaHostNode> findAll() {
		return( cfsec31HostNodeRepository.findAll() );
	}

	// CFSecHostNode specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecHostNodeByClusterIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return List&lt;CFSecJpaHostNode&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaHostNode> findByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		return( cfsec31HostNodeRepository.findByClusterIdx(requiredClusterId));
	}

	/**
	 *	ICFSecHostNodeByClusterIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecHostNodeByClusterIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaHostNode> findByClusterIdx(ICFSecHostNodeByClusterIdxKey key) {
		return( cfsec31HostNodeRepository.findByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecHostNodeByUDescrIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *		@param requiredDescription
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaHostNode findByUDescrIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("description") String requiredDescription) {
		return( cfsec31HostNodeRepository.findByUDescrIdx(requiredClusterId,
			requiredDescription));
	}

	/**
	 *	ICFSecHostNodeByUDescrIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecHostNodeByUDescrIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaHostNode findByUDescrIdx(ICFSecHostNodeByUDescrIdxKey key) {
		return( cfsec31HostNodeRepository.findByUDescrIdx(key.getRequiredClusterId(), key.getRequiredDescription()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecHostNodeByHostNameIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *		@param requiredHostName
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaHostNode findByHostNameIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("hostName") String requiredHostName) {
		return( cfsec31HostNodeRepository.findByHostNameIdx(requiredClusterId,
			requiredHostName));
	}

	/**
	 *	ICFSecHostNodeByHostNameIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecHostNodeByHostNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaHostNode findByHostNameIdx(ICFSecHostNodeByHostNameIdxKey key) {
		return( cfsec31HostNodeRepository.findByHostNameIdx(key.getRequiredClusterId(), key.getRequiredHostName()));
	}

	// CFSecHostNode specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredHostNodeId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaHostNode lockByIdIdx(@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId) {
		return( cfsec31HostNodeRepository.lockByIdIdx(requiredHostNodeId));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaHostNode> lockByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		return( cfsec31HostNodeRepository.lockByClusterIdx(requiredClusterId));
	}

	/**
	 *	ICFSecHostNodeByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaHostNode> lockByClusterIdx(ICFSecHostNodeByClusterIdxKey key) {
		return( cfsec31HostNodeRepository.lockByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredDescription
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaHostNode lockByUDescrIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("description") String requiredDescription) {
		return( cfsec31HostNodeRepository.lockByUDescrIdx(requiredClusterId,
			requiredDescription));
	}

	/**
	 *	ICFSecHostNodeByUDescrIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaHostNode lockByUDescrIdx(ICFSecHostNodeByUDescrIdxKey key) {
		return( cfsec31HostNodeRepository.lockByUDescrIdx(key.getRequiredClusterId(), key.getRequiredDescription()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredHostName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaHostNode lockByHostNameIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("hostName") String requiredHostName) {
		return( cfsec31HostNodeRepository.lockByHostNameIdx(requiredClusterId,
			requiredHostName));
	}

	/**
	 *	ICFSecHostNodeByHostNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaHostNode lockByHostNameIdx(ICFSecHostNodeByHostNameIdxKey key) {
		return( cfsec31HostNodeRepository.lockByHostNameIdx(key.getRequiredClusterId(), key.getRequiredHostName()));
	}

	// CFSecHostNode specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredHostNodeId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId) {
		cfsec31HostNodeRepository.deleteByIdIdx(requiredHostNodeId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId) {
		cfsec31HostNodeRepository.deleteByClusterIdx(requiredClusterId);
	}

	/**
	 *	ICFSecHostNodeByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecHostNodeByClusterIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByClusterIdx(ICFSecHostNodeByClusterIdxKey key) {
		cfsec31HostNodeRepository.deleteByClusterIdx(key.getRequiredClusterId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredDescription
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUDescrIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("description") String requiredDescription) {
		cfsec31HostNodeRepository.deleteByUDescrIdx(requiredClusterId,
			requiredDescription);
	}

	/**
	 *	ICFSecHostNodeByUDescrIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecHostNodeByUDescrIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUDescrIdx(ICFSecHostNodeByUDescrIdxKey key) {
		cfsec31HostNodeRepository.deleteByUDescrIdx(key.getRequiredClusterId(), key.getRequiredDescription());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredHostName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByHostNameIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("hostName") String requiredHostName) {
		cfsec31HostNodeRepository.deleteByHostNameIdx(requiredClusterId,
			requiredHostName);
	}

	/**
	 *	ICFSecHostNodeByHostNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecHostNodeByHostNameIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByHostNameIdx(ICFSecHostNodeByHostNameIdxKey key) {
		cfsec31HostNodeRepository.deleteByHostNameIdx(key.getRequiredClusterId(), key.getRequiredHostName());
	}
}

// Description: Java 25 Spring JPA Repository for Service

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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;

/**
 *	JpaRepository for the CFSecJpaService entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaServiceRepository extends JpaRepository<CFSecJpaService, CFLibDbKeyHash256> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredServiceId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaService r where r.requiredServiceId = :serviceId")
	CFSecJpaService get(@Param("serviceId") CFLibDbKeyHash256 requiredServiceId);

	// CFSecJpaService specified index readers

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecServiceByClusterIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return List&lt;CFSecJpaService&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaService r where r.requiredClusterId = :clusterId")
	List<CFSecJpaService> findByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId);

	/**
	 *	CFSecServiceByClusterIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecServiceByClusterIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaService> findByClusterIdx(ICFSecServiceByClusterIdxKey key) {
		return( findByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecServiceByHostIdxKey as arguments.
	 *
	 *		@param requiredHostNodeId
	 *
	 *		@return List&lt;CFSecJpaService&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaService r where r.optionalContainerHost.requiredHostNodeId = :hostNodeId")
	List<CFSecJpaService> findByHostIdx(@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId);

	/**
	 *	CFSecServiceByHostIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecServiceByHostIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaService> findByHostIdx(ICFSecServiceByHostIdxKey key) {
		return( findByHostIdx(key.getRequiredHostNodeId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecServiceByTypeIdxKey as arguments.
	 *
	 *		@param requiredServiceTypeId
	 *
	 *		@return List&lt;CFSecJpaService&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaService r where r.optionalParentServiceType.requiredServiceTypeId = :serviceTypeId")
	List<CFSecJpaService> findByTypeIdx(@Param("serviceTypeId") CFLibDbKeyHash256 requiredServiceTypeId);

	/**
	 *	CFSecServiceByTypeIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecServiceByTypeIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaService> findByTypeIdx(ICFSecServiceByTypeIdxKey key) {
		return( findByTypeIdx(key.getRequiredServiceTypeId()));
	}

	/**
	 *	Read an entity using the columns of the CFSecServiceByUTypeIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *		@param requiredHostNodeId
	 *		@param requiredServiceTypeId
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaService r where r.requiredClusterId = :clusterId and r.optionalContainerHost.requiredHostNodeId = :hostNodeId and r.optionalParentServiceType.requiredServiceTypeId = :serviceTypeId")
	CFSecJpaService findByUTypeIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId,
		@Param("serviceTypeId") CFLibDbKeyHash256 requiredServiceTypeId);

	/**
	 *	CFSecServiceByUTypeIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecServiceByUTypeIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaService findByUTypeIdx(ICFSecServiceByUTypeIdxKey key) {
		return( findByUTypeIdx(key.getRequiredClusterId(), key.getRequiredHostNodeId(), key.getRequiredServiceTypeId()));
	}

	/**
	 *	Read an entity using the columns of the CFSecServiceByUHostPortIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *		@param requiredHostNodeId
	 *		@param requiredHostPort
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaService r where r.requiredClusterId = :clusterId and r.optionalContainerHost.requiredHostNodeId = :hostNodeId and r.requiredHostPort = :hostPort")
	CFSecJpaService findByUHostPortIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId,
		@Param("hostPort") short requiredHostPort);

	/**
	 *	CFSecServiceByUHostPortIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecServiceByUHostPortIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaService findByUHostPortIdx(ICFSecServiceByUHostPortIdxKey key) {
		return( findByUHostPortIdx(key.getRequiredClusterId(), key.getRequiredHostNodeId(), key.getRequiredHostPort()));
	}

	// CFSecJpaService specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredServiceId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaService r where r.requiredServiceId = :serviceId")
	CFSecJpaService lockByIdIdx(@Param("serviceId") CFLibDbKeyHash256 requiredServiceId);

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaService r where r.requiredClusterId = :clusterId")
	List<CFSecJpaService> lockByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId);

	/**
	 *	CFSecServiceByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaService> lockByClusterIdx(ICFSecServiceByClusterIdxKey key) {
		return( lockByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredHostNodeId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaService r where r.optionalContainerHost.requiredHostNodeId = :hostNodeId")
	List<CFSecJpaService> lockByHostIdx(@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId);

	/**
	 *	CFSecServiceByHostIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaService> lockByHostIdx(ICFSecServiceByHostIdxKey key) {
		return( lockByHostIdx(key.getRequiredHostNodeId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredServiceTypeId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaService r where r.optionalParentServiceType.requiredServiceTypeId = :serviceTypeId")
	List<CFSecJpaService> lockByTypeIdx(@Param("serviceTypeId") CFLibDbKeyHash256 requiredServiceTypeId);

	/**
	 *	CFSecServiceByTypeIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaService> lockByTypeIdx(ICFSecServiceByTypeIdxKey key) {
		return( lockByTypeIdx(key.getRequiredServiceTypeId()));
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
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaService r where r.requiredClusterId = :clusterId and r.optionalContainerHost.requiredHostNodeId = :hostNodeId and r.optionalParentServiceType.requiredServiceTypeId = :serviceTypeId")
	CFSecJpaService lockByUTypeIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId,
		@Param("serviceTypeId") CFLibDbKeyHash256 requiredServiceTypeId);

	/**
	 *	CFSecServiceByUTypeIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaService lockByUTypeIdx(ICFSecServiceByUTypeIdxKey key) {
		return( lockByUTypeIdx(key.getRequiredClusterId(), key.getRequiredHostNodeId(), key.getRequiredServiceTypeId()));
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
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaService r where r.requiredClusterId = :clusterId and r.optionalContainerHost.requiredHostNodeId = :hostNodeId and r.requiredHostPort = :hostPort")
	CFSecJpaService lockByUHostPortIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId,
		@Param("hostPort") short requiredHostPort);

	/**
	 *	CFSecServiceByUHostPortIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaService lockByUHostPortIdx(ICFSecServiceByUHostPortIdxKey key) {
		return( lockByUHostPortIdx(key.getRequiredClusterId(), key.getRequiredHostNodeId(), key.getRequiredHostPort()));
	}

	// CFSecJpaService specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredServiceId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaService r where r.requiredServiceId = :serviceId")
	void deleteByIdIdx(@Param("serviceId") CFLibDbKeyHash256 requiredServiceId);

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaService r where r.requiredClusterId = :clusterId")
	void deleteByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId);

	/**
	 *	CFSecServiceByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecServiceByClusterIdxKey of the entity to be locked.
	 */
	default void deleteByClusterIdx(ICFSecServiceByClusterIdxKey key) {
		deleteByClusterIdx(key.getRequiredClusterId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredHostNodeId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaService r where r.optionalContainerHost.requiredHostNodeId = :hostNodeId")
	void deleteByHostIdx(@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId);

	/**
	 *	CFSecServiceByHostIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecServiceByHostIdxKey of the entity to be locked.
	 */
	default void deleteByHostIdx(ICFSecServiceByHostIdxKey key) {
		deleteByHostIdx(key.getRequiredHostNodeId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredServiceTypeId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaService r where r.optionalParentServiceType.requiredServiceTypeId = :serviceTypeId")
	void deleteByTypeIdx(@Param("serviceTypeId") CFLibDbKeyHash256 requiredServiceTypeId);

	/**
	 *	CFSecServiceByTypeIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecServiceByTypeIdxKey of the entity to be locked.
	 */
	default void deleteByTypeIdx(ICFSecServiceByTypeIdxKey key) {
		deleteByTypeIdx(key.getRequiredServiceTypeId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredHostNodeId
	 *		@param requiredServiceTypeId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaService r where r.requiredClusterId = :clusterId and r.optionalContainerHost.requiredHostNodeId = :hostNodeId and r.optionalParentServiceType.requiredServiceTypeId = :serviceTypeId")
	void deleteByUTypeIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId,
		@Param("serviceTypeId") CFLibDbKeyHash256 requiredServiceTypeId);

	/**
	 *	CFSecServiceByUTypeIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecServiceByUTypeIdxKey of the entity to be locked.
	 */
	default void deleteByUTypeIdx(ICFSecServiceByUTypeIdxKey key) {
		deleteByUTypeIdx(key.getRequiredClusterId(), key.getRequiredHostNodeId(), key.getRequiredServiceTypeId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredHostNodeId
	 *		@param requiredHostPort
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaService r where r.requiredClusterId = :clusterId and r.optionalContainerHost.requiredHostNodeId = :hostNodeId and r.requiredHostPort = :hostPort")
	void deleteByUHostPortIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId,
		@Param("hostPort") short requiredHostPort);

	/**
	 *	CFSecServiceByUHostPortIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecServiceByUHostPortIdxKey of the entity to be locked.
	 */
	default void deleteByUHostPortIdx(ICFSecServiceByUHostPortIdxKey key) {
		deleteByUHostPortIdx(key.getRequiredClusterId(), key.getRequiredHostNodeId(), key.getRequiredHostPort());
	}

}

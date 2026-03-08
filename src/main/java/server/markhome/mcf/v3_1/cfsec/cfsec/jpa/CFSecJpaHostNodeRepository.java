// Description: Java 25 Spring JPA Repository for HostNode

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
 *	JpaRepository for the CFSecJpaHostNode entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaHostNodeRepository extends JpaRepository<CFSecJpaHostNode, CFLibDbKeyHash256> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredHostNodeId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaHostNode r where r.requiredHostNodeId = :hostNodeId")
	CFSecJpaHostNode get(@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId);

	// CFSecJpaHostNode specified index readers

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecHostNodeByClusterIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return List&lt;CFSecJpaHostNode&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaHostNode r where r.requiredContainerCluster.requiredId = :clusterId")
	List<CFSecJpaHostNode> findByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId);

	/**
	 *	CFSecHostNodeByClusterIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecHostNodeByClusterIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaHostNode> findByClusterIdx(ICFSecHostNodeByClusterIdxKey key) {
		return( findByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Read an entity using the columns of the CFSecHostNodeByUDescrIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *		@param requiredDescription
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaHostNode r where r.requiredContainerCluster.requiredId = :clusterId and r.requiredDescription = :description")
	CFSecJpaHostNode findByUDescrIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("description") String requiredDescription);

	/**
	 *	CFSecHostNodeByUDescrIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecHostNodeByUDescrIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaHostNode findByUDescrIdx(ICFSecHostNodeByUDescrIdxKey key) {
		return( findByUDescrIdx(key.getRequiredClusterId(), key.getRequiredDescription()));
	}

	/**
	 *	Read an entity using the columns of the CFSecHostNodeByHostNameIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *		@param requiredHostName
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaHostNode r where r.requiredContainerCluster.requiredId = :clusterId and r.requiredHostName = :hostName")
	CFSecJpaHostNode findByHostNameIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("hostName") String requiredHostName);

	/**
	 *	CFSecHostNodeByHostNameIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecHostNodeByHostNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaHostNode findByHostNameIdx(ICFSecHostNodeByHostNameIdxKey key) {
		return( findByHostNameIdx(key.getRequiredClusterId(), key.getRequiredHostName()));
	}

	// CFSecJpaHostNode specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredHostNodeId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaHostNode r where r.requiredHostNodeId = :hostNodeId")
	CFSecJpaHostNode lockByIdIdx(@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId);

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaHostNode r where r.requiredContainerCluster.requiredId = :clusterId")
	List<CFSecJpaHostNode> lockByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId);

	/**
	 *	CFSecHostNodeByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaHostNode> lockByClusterIdx(ICFSecHostNodeByClusterIdxKey key) {
		return( lockByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredDescription
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaHostNode r where r.requiredContainerCluster.requiredId = :clusterId and r.requiredDescription = :description")
	CFSecJpaHostNode lockByUDescrIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("description") String requiredDescription);

	/**
	 *	CFSecHostNodeByUDescrIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaHostNode lockByUDescrIdx(ICFSecHostNodeByUDescrIdxKey key) {
		return( lockByUDescrIdx(key.getRequiredClusterId(), key.getRequiredDescription()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredHostName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaHostNode r where r.requiredContainerCluster.requiredId = :clusterId and r.requiredHostName = :hostName")
	CFSecJpaHostNode lockByHostNameIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("hostName") String requiredHostName);

	/**
	 *	CFSecHostNodeByHostNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaHostNode lockByHostNameIdx(ICFSecHostNodeByHostNameIdxKey key) {
		return( lockByHostNameIdx(key.getRequiredClusterId(), key.getRequiredHostName()));
	}

	// CFSecJpaHostNode specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredHostNodeId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaHostNode r where r.requiredHostNodeId = :hostNodeId")
	void deleteByIdIdx(@Param("hostNodeId") CFLibDbKeyHash256 requiredHostNodeId);

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaHostNode r where r.requiredContainerCluster.requiredId = :clusterId")
	void deleteByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId);

	/**
	 *	CFSecHostNodeByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecHostNodeByClusterIdxKey of the entity to be locked.
	 */
	default void deleteByClusterIdx(ICFSecHostNodeByClusterIdxKey key) {
		deleteByClusterIdx(key.getRequiredClusterId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredDescription
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaHostNode r where r.requiredContainerCluster.requiredId = :clusterId and r.requiredDescription = :description")
	void deleteByUDescrIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("description") String requiredDescription);

	/**
	 *	CFSecHostNodeByUDescrIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecHostNodeByUDescrIdxKey of the entity to be locked.
	 */
	default void deleteByUDescrIdx(ICFSecHostNodeByUDescrIdxKey key) {
		deleteByUDescrIdx(key.getRequiredClusterId(), key.getRequiredDescription());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredHostName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaHostNode r where r.requiredContainerCluster.requiredId = :clusterId and r.requiredHostName = :hostName")
	void deleteByHostNameIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("hostName") String requiredHostName);

	/**
	 *	CFSecHostNodeByHostNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecHostNodeByHostNameIdxKey of the entity to be locked.
	 */
	default void deleteByHostNameIdx(ICFSecHostNodeByHostNameIdxKey key) {
		deleteByHostNameIdx(key.getRequiredClusterId(), key.getRequiredHostName());
	}

}

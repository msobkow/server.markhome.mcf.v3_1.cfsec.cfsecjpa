// Description: Java 25 Spring JPA Repository for SecGroup

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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;

/**
 *	JpaRepository for the CFSecJpaSecGroup entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaSecGroupRepository extends JpaRepository<CFSecJpaSecGroup, CFLibDbKeyHash256> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecGroupId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecGroup r where r.requiredSecGroupId = :secGroupId")
	CFSecJpaSecGroup get(@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId);

	// CFSecJpaSecGroup specified index readers

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecGroupByClusterIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return List&lt;CFSecJpaSecGroup&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecGroup r where r.requiredContainerCluster.requiredId = :clusterId")
	List<CFSecJpaSecGroup> findByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId);

	/**
	 *	CFSecSecGroupByClusterIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecGroupByClusterIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecGroup> findByClusterIdx(ICFSecSecGroupByClusterIdxKey key) {
		return( findByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecGroupByClusterVisIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *		@param requiredIsVisible
	 *
	 *		@return List&lt;CFSecJpaSecGroup&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecGroup r where r.requiredContainerCluster.requiredId = :clusterId and r.requiredIsVisible = :isVisible")
	List<CFSecJpaSecGroup> findByClusterVisIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("isVisible") boolean requiredIsVisible);

	/**
	 *	CFSecSecGroupByClusterVisIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecGroupByClusterVisIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecGroup> findByClusterVisIdx(ICFSecSecGroupByClusterVisIdxKey key) {
		return( findByClusterVisIdx(key.getRequiredClusterId(), key.getRequiredIsVisible()));
	}

	/**
	 *	Read an entity using the columns of the CFSecSecGroupByUNameIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *		@param requiredName
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecGroup r where r.requiredContainerCluster.requiredId = :clusterId and r.requiredName = :name")
	CFSecJpaSecGroup findByUNameIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("name") String requiredName);

	/**
	 *	CFSecSecGroupByUNameIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecGroupByUNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecGroup findByUNameIdx(ICFSecSecGroupByUNameIdxKey key) {
		return( findByUNameIdx(key.getRequiredClusterId(), key.getRequiredName()));
	}

	// CFSecJpaSecGroup specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecGroupId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecGroup r where r.requiredSecGroupId = :secGroupId")
	CFSecJpaSecGroup lockByIdIdx(@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId);

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecGroup r where r.requiredContainerCluster.requiredId = :clusterId")
	List<CFSecJpaSecGroup> lockByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId);

	/**
	 *	CFSecSecGroupByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecGroup> lockByClusterIdx(ICFSecSecGroupByClusterIdxKey key) {
		return( lockByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredIsVisible
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecGroup r where r.requiredContainerCluster.requiredId = :clusterId and r.requiredIsVisible = :isVisible")
	List<CFSecJpaSecGroup> lockByClusterVisIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("isVisible") boolean requiredIsVisible);

	/**
	 *	CFSecSecGroupByClusterVisIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecGroup> lockByClusterVisIdx(ICFSecSecGroupByClusterVisIdxKey key) {
		return( lockByClusterVisIdx(key.getRequiredClusterId(), key.getRequiredIsVisible()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecGroup r where r.requiredContainerCluster.requiredId = :clusterId and r.requiredName = :name")
	CFSecJpaSecGroup lockByUNameIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("name") String requiredName);

	/**
	 *	CFSecSecGroupByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecGroup lockByUNameIdx(ICFSecSecGroupByUNameIdxKey key) {
		return( lockByUNameIdx(key.getRequiredClusterId(), key.getRequiredName()));
	}

	// CFSecJpaSecGroup specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecGroupId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecGroup r where r.requiredSecGroupId = :secGroupId")
	void deleteByIdIdx(@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId);

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecGroup r where r.requiredContainerCluster.requiredId = :clusterId")
	void deleteByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId);

	/**
	 *	CFSecSecGroupByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecGroupByClusterIdxKey of the entity to be locked.
	 */
	default void deleteByClusterIdx(ICFSecSecGroupByClusterIdxKey key) {
		deleteByClusterIdx(key.getRequiredClusterId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredIsVisible
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecGroup r where r.requiredContainerCluster.requiredId = :clusterId and r.requiredIsVisible = :isVisible")
	void deleteByClusterVisIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("isVisible") boolean requiredIsVisible);

	/**
	 *	CFSecSecGroupByClusterVisIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecGroupByClusterVisIdxKey of the entity to be locked.
	 */
	default void deleteByClusterVisIdx(ICFSecSecGroupByClusterVisIdxKey key) {
		deleteByClusterVisIdx(key.getRequiredClusterId(), key.getRequiredIsVisible());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecGroup r where r.requiredContainerCluster.requiredId = :clusterId and r.requiredName = :name")
	void deleteByUNameIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("name") String requiredName);

	/**
	 *	CFSecSecGroupByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecGroupByUNameIdxKey of the entity to be locked.
	 */
	default void deleteByUNameIdx(ICFSecSecGroupByUNameIdxKey key) {
		deleteByUNameIdx(key.getRequiredClusterId(), key.getRequiredName());
	}

}

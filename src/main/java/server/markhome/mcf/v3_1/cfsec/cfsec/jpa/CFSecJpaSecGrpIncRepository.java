// Description: Java 25 Spring JPA Repository for SecGrpInc

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
 *	JpaRepository for the CFSecJpaSecGrpInc entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaSecGrpIncRepository extends JpaRepository<CFSecJpaSecGrpInc, CFLibDbKeyHash256> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecGrpIncId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecGrpInc r where r.requiredSecGrpIncId = :secGrpIncId")
	CFSecJpaSecGrpInc get(@Param("secGrpIncId") CFLibDbKeyHash256 requiredSecGrpIncId);

	// CFSecJpaSecGrpInc specified index readers

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecGrpIncByClusterIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return List&lt;CFSecJpaSecGrpInc&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecGrpInc r where r.requiredClusterId = :clusterId")
	List<CFSecJpaSecGrpInc> findByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId);

	/**
	 *	CFSecSecGrpIncByClusterIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecGrpIncByClusterIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecGrpInc> findByClusterIdx(ICFSecSecGrpIncByClusterIdxKey key) {
		return( findByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecGrpIncByGroupIdxKey as arguments.
	 *
	 *		@param requiredSecGroupId
	 *
	 *		@return List&lt;CFSecJpaSecGrpInc&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecGrpInc r where r.requiredContainerGroup.requiredSecGroupId = :secGroupId")
	List<CFSecJpaSecGrpInc> findByGroupIdx(@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId);

	/**
	 *	CFSecSecGrpIncByGroupIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecGrpIncByGroupIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecGrpInc> findByGroupIdx(ICFSecSecGrpIncByGroupIdxKey key) {
		return( findByGroupIdx(key.getRequiredSecGroupId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecGrpIncByIncludeIdxKey as arguments.
	 *
	 *		@param requiredIncludeGroupId
	 *
	 *		@return List&lt;CFSecJpaSecGrpInc&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecGrpInc r where r.requiredParentSubGroup.requiredSecGroupId = :includeGroupId")
	List<CFSecJpaSecGrpInc> findByIncludeIdx(@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId);

	/**
	 *	CFSecSecGrpIncByIncludeIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecGrpIncByIncludeIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecGrpInc> findByIncludeIdx(ICFSecSecGrpIncByIncludeIdxKey key) {
		return( findByIncludeIdx(key.getRequiredIncludeGroupId()));
	}

	/**
	 *	Read an entity using the columns of the CFSecSecGrpIncByUIncludeIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *		@param requiredSecGroupId
	 *		@param requiredIncludeGroupId
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecGrpInc r where r.requiredClusterId = :clusterId and r.requiredContainerGroup.requiredSecGroupId = :secGroupId and r.requiredParentSubGroup.requiredSecGroupId = :includeGroupId")
	CFSecJpaSecGrpInc findByUIncludeIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId,
		@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId);

	/**
	 *	CFSecSecGrpIncByUIncludeIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecGrpIncByUIncludeIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecGrpInc findByUIncludeIdx(ICFSecSecGrpIncByUIncludeIdxKey key) {
		return( findByUIncludeIdx(key.getRequiredClusterId(), key.getRequiredSecGroupId(), key.getRequiredIncludeGroupId()));
	}

	// CFSecJpaSecGrpInc specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecGrpIncId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecGrpInc r where r.requiredSecGrpIncId = :secGrpIncId")
	CFSecJpaSecGrpInc lockByIdIdx(@Param("secGrpIncId") CFLibDbKeyHash256 requiredSecGrpIncId);

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecGrpInc r where r.requiredClusterId = :clusterId")
	List<CFSecJpaSecGrpInc> lockByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId);

	/**
	 *	CFSecSecGrpIncByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecGrpInc> lockByClusterIdx(ICFSecSecGrpIncByClusterIdxKey key) {
		return( lockByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecGroupId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecGrpInc r where r.requiredContainerGroup.requiredSecGroupId = :secGroupId")
	List<CFSecJpaSecGrpInc> lockByGroupIdx(@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId);

	/**
	 *	CFSecSecGrpIncByGroupIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecGrpInc> lockByGroupIdx(ICFSecSecGrpIncByGroupIdxKey key) {
		return( lockByGroupIdx(key.getRequiredSecGroupId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredIncludeGroupId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecGrpInc r where r.requiredParentSubGroup.requiredSecGroupId = :includeGroupId")
	List<CFSecJpaSecGrpInc> lockByIncludeIdx(@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId);

	/**
	 *	CFSecSecGrpIncByIncludeIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecGrpInc> lockByIncludeIdx(ICFSecSecGrpIncByIncludeIdxKey key) {
		return( lockByIncludeIdx(key.getRequiredIncludeGroupId()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredSecGroupId
	 *		@param requiredIncludeGroupId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecGrpInc r where r.requiredClusterId = :clusterId and r.requiredContainerGroup.requiredSecGroupId = :secGroupId and r.requiredParentSubGroup.requiredSecGroupId = :includeGroupId")
	CFSecJpaSecGrpInc lockByUIncludeIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId,
		@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId);

	/**
	 *	CFSecSecGrpIncByUIncludeIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecGrpInc lockByUIncludeIdx(ICFSecSecGrpIncByUIncludeIdxKey key) {
		return( lockByUIncludeIdx(key.getRequiredClusterId(), key.getRequiredSecGroupId(), key.getRequiredIncludeGroupId()));
	}

	// CFSecJpaSecGrpInc specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecGrpIncId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecGrpInc r where r.requiredSecGrpIncId = :secGrpIncId")
	void deleteByIdIdx(@Param("secGrpIncId") CFLibDbKeyHash256 requiredSecGrpIncId);

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecGrpInc r where r.requiredClusterId = :clusterId")
	void deleteByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId);

	/**
	 *	CFSecSecGrpIncByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecGrpIncByClusterIdxKey of the entity to be locked.
	 */
	default void deleteByClusterIdx(ICFSecSecGrpIncByClusterIdxKey key) {
		deleteByClusterIdx(key.getRequiredClusterId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecGroupId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecGrpInc r where r.requiredContainerGroup.requiredSecGroupId = :secGroupId")
	void deleteByGroupIdx(@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId);

	/**
	 *	CFSecSecGrpIncByGroupIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecGrpIncByGroupIdxKey of the entity to be locked.
	 */
	default void deleteByGroupIdx(ICFSecSecGrpIncByGroupIdxKey key) {
		deleteByGroupIdx(key.getRequiredSecGroupId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredIncludeGroupId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecGrpInc r where r.requiredParentSubGroup.requiredSecGroupId = :includeGroupId")
	void deleteByIncludeIdx(@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId);

	/**
	 *	CFSecSecGrpIncByIncludeIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecGrpIncByIncludeIdxKey of the entity to be locked.
	 */
	default void deleteByIncludeIdx(ICFSecSecGrpIncByIncludeIdxKey key) {
		deleteByIncludeIdx(key.getRequiredIncludeGroupId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredSecGroupId
	 *		@param requiredIncludeGroupId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecGrpInc r where r.requiredClusterId = :clusterId and r.requiredContainerGroup.requiredSecGroupId = :secGroupId and r.requiredParentSubGroup.requiredSecGroupId = :includeGroupId")
	void deleteByUIncludeIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId,
		@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId);

	/**
	 *	CFSecSecGrpIncByUIncludeIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecGrpIncByUIncludeIdxKey of the entity to be locked.
	 */
	default void deleteByUIncludeIdx(ICFSecSecGrpIncByUIncludeIdxKey key) {
		deleteByUIncludeIdx(key.getRequiredClusterId(), key.getRequiredSecGroupId(), key.getRequiredIncludeGroupId());
	}

}

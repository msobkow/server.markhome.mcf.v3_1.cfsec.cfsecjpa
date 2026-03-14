// Description: Java 25 Spring JPA Repository for SecSession

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
 *	JpaRepository for the CFSecJpaSecSession entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaSecSessionRepository extends JpaRepository<CFSecJpaSecSession, CFLibDbKeyHash256> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecSessionId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecSession r where r.requiredSecSessionId = :secSessionId")
	CFSecJpaSecSession get(@Param("secSessionId") CFLibDbKeyHash256 requiredSecSessionId);

	// CFSecJpaSecSession specified index readers

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecSessionBySecUserIdxKey as arguments.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return List&lt;CFSecJpaSecSession&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecSession r where r.requiredSecUserId = :secUserId")
	List<CFSecJpaSecSession> findBySecUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecSessionBySecUserIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecSessionBySecUserIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecSession> findBySecUserIdx(ICFSecSecSessionBySecUserIdxKey key) {
		return( findBySecUserIdx(key.getRequiredSecUserId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecSessionBySecDevIdxKey as arguments.
	 *
	 *		@param requiredSecUserId
	 *		@param optionalSecDevName
	 *
	 *		@return List&lt;CFSecJpaSecSession&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecSession r where r.requiredSecUserId = :secUserId and r.optionalSecDevName = :secDevName")
	List<CFSecJpaSecSession> findBySecDevIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("secDevName") String optionalSecDevName);

	/**
	 *	CFSecSecSessionBySecDevIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecSessionBySecDevIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecSession> findBySecDevIdx(ICFSecSecSessionBySecDevIdxKey key) {
		return( findBySecDevIdx(key.getRequiredSecUserId(), key.getOptionalSecDevName()));
	}

	/**
	 *	Read an entity using the columns of the CFSecSecSessionByStartIdxKey as arguments.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredStart
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecSession r where r.requiredSecUserId = :secUserId and r.requiredStart = :start")
	CFSecJpaSecSession findByStartIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("start") LocalDateTime requiredStart);

	/**
	 *	CFSecSecSessionByStartIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecSessionByStartIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecSession findByStartIdx(ICFSecSecSessionByStartIdxKey key) {
		return( findByStartIdx(key.getRequiredSecUserId(), key.getRequiredStart()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecSessionByFinishIdxKey as arguments.
	 *
	 *		@param requiredSecUserId
	 *		@param optionalFinish
	 *
	 *		@return List&lt;CFSecJpaSecSession&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecSession r where r.requiredSecUserId = :secUserId and r.optionalFinish = :finish")
	List<CFSecJpaSecSession> findByFinishIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("finish") LocalDateTime optionalFinish);

	/**
	 *	CFSecSecSessionByFinishIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecSessionByFinishIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecSession> findByFinishIdx(ICFSecSecSessionByFinishIdxKey key) {
		return( findByFinishIdx(key.getRequiredSecUserId(), key.getOptionalFinish()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecSessionBySecProxyIdxKey as arguments.
	 *
	 *		@param optionalSecProxyId
	 *
	 *		@return List&lt;CFSecJpaSecSession&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecSession r where r.optionalSecProxyId = :secProxyId")
	List<CFSecJpaSecSession> findBySecProxyIdx(@Param("secProxyId") CFLibDbKeyHash256 optionalSecProxyId);

	/**
	 *	CFSecSecSessionBySecProxyIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecSessionBySecProxyIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecSession> findBySecProxyIdx(ICFSecSecSessionBySecProxyIdxKey key) {
		return( findBySecProxyIdx(key.getOptionalSecProxyId()));
	}

	// CFSecJpaSecSession specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSessionId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecSession r where r.requiredSecSessionId = :secSessionId")
	CFSecJpaSecSession lockByIdIdx(@Param("secSessionId") CFLibDbKeyHash256 requiredSecSessionId);

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecSession r where r.requiredSecUserId = :secUserId")
	List<CFSecJpaSecSession> lockBySecUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecSessionBySecUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecSession> lockBySecUserIdx(ICFSecSecSessionBySecUserIdxKey key) {
		return( lockBySecUserIdx(key.getRequiredSecUserId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param optionalSecDevName
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecSession r where r.requiredSecUserId = :secUserId and r.optionalSecDevName = :secDevName")
	List<CFSecJpaSecSession> lockBySecDevIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("secDevName") String optionalSecDevName);

	/**
	 *	CFSecSecSessionBySecDevIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecSession> lockBySecDevIdx(ICFSecSecSessionBySecDevIdxKey key) {
		return( lockBySecDevIdx(key.getRequiredSecUserId(), key.getOptionalSecDevName()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredStart
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecSession r where r.requiredSecUserId = :secUserId and r.requiredStart = :start")
	CFSecJpaSecSession lockByStartIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("start") LocalDateTime requiredStart);

	/**
	 *	CFSecSecSessionByStartIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecSession lockByStartIdx(ICFSecSecSessionByStartIdxKey key) {
		return( lockByStartIdx(key.getRequiredSecUserId(), key.getRequiredStart()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param optionalFinish
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecSession r where r.requiredSecUserId = :secUserId and r.optionalFinish = :finish")
	List<CFSecJpaSecSession> lockByFinishIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("finish") LocalDateTime optionalFinish);

	/**
	 *	CFSecSecSessionByFinishIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecSession> lockByFinishIdx(ICFSecSecSessionByFinishIdxKey key) {
		return( lockByFinishIdx(key.getRequiredSecUserId(), key.getOptionalFinish()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param optionalSecProxyId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecSession r where r.optionalSecProxyId = :secProxyId")
	List<CFSecJpaSecSession> lockBySecProxyIdx(@Param("secProxyId") CFLibDbKeyHash256 optionalSecProxyId);

	/**
	 *	CFSecSecSessionBySecProxyIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecSession> lockBySecProxyIdx(ICFSecSecSessionBySecProxyIdxKey key) {
		return( lockBySecProxyIdx(key.getOptionalSecProxyId()));
	}

	// CFSecJpaSecSession specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSessionId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecSession r where r.requiredSecSessionId = :secSessionId")
	void deleteByIdIdx(@Param("secSessionId") CFLibDbKeyHash256 requiredSecSessionId);

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecSession r where r.requiredSecUserId = :secUserId")
	void deleteBySecUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecSessionBySecUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecSessionBySecUserIdxKey of the entity to be locked.
	 */
	default void deleteBySecUserIdx(ICFSecSecSessionBySecUserIdxKey key) {
		deleteBySecUserIdx(key.getRequiredSecUserId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param optionalSecDevName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecSession r where r.requiredSecUserId = :secUserId and r.optionalSecDevName = :secDevName")
	void deleteBySecDevIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("secDevName") String optionalSecDevName);

	/**
	 *	CFSecSecSessionBySecDevIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecSessionBySecDevIdxKey of the entity to be locked.
	 */
	default void deleteBySecDevIdx(ICFSecSecSessionBySecDevIdxKey key) {
		deleteBySecDevIdx(key.getRequiredSecUserId(), key.getOptionalSecDevName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredStart
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecSession r where r.requiredSecUserId = :secUserId and r.requiredStart = :start")
	void deleteByStartIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("start") LocalDateTime requiredStart);

	/**
	 *	CFSecSecSessionByStartIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecSessionByStartIdxKey of the entity to be locked.
	 */
	default void deleteByStartIdx(ICFSecSecSessionByStartIdxKey key) {
		deleteByStartIdx(key.getRequiredSecUserId(), key.getRequiredStart());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param optionalFinish
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecSession r where r.requiredSecUserId = :secUserId and r.optionalFinish = :finish")
	void deleteByFinishIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("finish") LocalDateTime optionalFinish);

	/**
	 *	CFSecSecSessionByFinishIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecSessionByFinishIdxKey of the entity to be locked.
	 */
	default void deleteByFinishIdx(ICFSecSecSessionByFinishIdxKey key) {
		deleteByFinishIdx(key.getRequiredSecUserId(), key.getOptionalFinish());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param optionalSecProxyId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecSession r where r.optionalSecProxyId = :secProxyId")
	void deleteBySecProxyIdx(@Param("secProxyId") CFLibDbKeyHash256 optionalSecProxyId);

	/**
	 *	CFSecSecSessionBySecProxyIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecSessionBySecProxyIdxKey of the entity to be locked.
	 */
	default void deleteBySecProxyIdx(ICFSecSecSessionBySecProxyIdxKey key) {
		deleteBySecProxyIdx(key.getOptionalSecProxyId());
	}

}

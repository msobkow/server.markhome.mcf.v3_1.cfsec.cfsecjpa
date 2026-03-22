// Description: Java 25 Spring JPA Repository for SecTentGrpMemb

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
 *	JpaRepository for the CFSecJpaSecTentGrpMemb entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaSecTentGrpMembRepository extends JpaRepository<CFSecJpaSecTentGrpMemb, CFSecJpaSecTentGrpMembPKey> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecTentGrpId
	 *		@param requiredSecUserId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecTentGrpMemb r where r.pkey.requiredSecTentGrpId = :secTentGrpId and r.pkey.requiredSecUserId = :secUserId")
	CFSecJpaSecTentGrpMemb get(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecTentGrpMembPKey based read method for object-based access.
	 *
	 *		@param key The key of the entity to be read.
	 *
	 *		@return The entity read, usually from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecTentGrpMemb get(ICFSecSecTentGrpMembPKey key) {
		return( get(key.getRequiredSecTentGrpId(), key.getRequiredSecUserId()));
	}

	// CFSecJpaSecTentGrpMemb specified index readers

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecTentGrpMembByTentGrpIdxKey as arguments.
	 *
	 *		@param requiredSecTentGrpId
	 *
	 *		@return List&lt;CFSecJpaSecTentGrpMemb&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecTentGrpMemb r where r.pkey.requiredSecTentGrpId = :secTentGrpId")
	List<CFSecJpaSecTentGrpMemb> findByTentGrpIdx(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId);

	/**
	 *	CFSecSecTentGrpMembByTentGrpIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecTentGrpMembByTentGrpIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecTentGrpMemb> findByTentGrpIdx(ICFSecSecTentGrpMembByTentGrpIdxKey key) {
		return( findByTentGrpIdx(key.getRequiredSecTentGrpId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecTentGrpMembByUserIdxKey as arguments.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return List&lt;CFSecJpaSecTentGrpMemb&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecTentGrpMemb r where r.pkey.requiredSecUserId = :secUserId")
	List<CFSecJpaSecTentGrpMemb> findByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecTentGrpMembByUserIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecTentGrpMembByUserIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecTentGrpMemb> findByUserIdx(ICFSecSecTentGrpMembByUserIdxKey key) {
		return( findByUserIdx(key.getRequiredSecUserId()));
	}

	// CFSecJpaSecTentGrpMemb specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecTentGrpId
	 *		@param requiredSecUserId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecTentGrpMemb r where r.pkey.requiredSecTentGrpId = :secTentGrpId and r.pkey.requiredSecUserId = :secUserId")
	CFSecJpaSecTentGrpMemb lockByIdIdx(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecTentGrpMembByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecTentGrpMemb lockByIdIdx(ICFSecSecTentGrpMembPKey key) {
		return( lockByIdIdx(key.getRequiredSecTentGrpId(), key.getRequiredSecUserId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecTentGrpId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecTentGrpMemb r where r.pkey.requiredSecTentGrpId = :secTentGrpId")
	List<CFSecJpaSecTentGrpMemb> lockByTentGrpIdx(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId);

	/**
	 *	CFSecSecTentGrpMembByTentGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecTentGrpMemb> lockByTentGrpIdx(ICFSecSecTentGrpMembByTentGrpIdxKey key) {
		return( lockByTentGrpIdx(key.getRequiredSecTentGrpId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecTentGrpMemb r where r.pkey.requiredSecUserId = :secUserId")
	List<CFSecJpaSecTentGrpMemb> lockByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecTentGrpMembByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecTentGrpMemb> lockByUserIdx(ICFSecSecTentGrpMembByUserIdxKey key) {
		return( lockByUserIdx(key.getRequiredSecUserId()));
	}

	// CFSecJpaSecTentGrpMemb specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecTentGrpId
	 *		@param requiredSecUserId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecTentGrpMemb r where r.pkey.requiredSecTentGrpId = :secTentGrpId and r.pkey.requiredSecUserId = :secUserId")
	void deleteByIdIdx(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecTentGrpMembByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecTentGrpMembByIdIdxKey of the entity to be locked.
	 */
	default void deleteByIdIdx(ICFSecSecTentGrpMembPKey key) {
		deleteByIdIdx(key.getRequiredSecTentGrpId(), key.getRequiredSecUserId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecTentGrpId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecTentGrpMemb r where r.pkey.requiredSecTentGrpId = :secTentGrpId")
	void deleteByTentGrpIdx(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId);

	/**
	 *	CFSecSecTentGrpMembByTentGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecTentGrpMembByTentGrpIdxKey of the entity to be locked.
	 */
	default void deleteByTentGrpIdx(ICFSecSecTentGrpMembByTentGrpIdxKey key) {
		deleteByTentGrpIdx(key.getRequiredSecTentGrpId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecTentGrpMemb r where r.pkey.requiredSecUserId = :secUserId")
	void deleteByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecTentGrpMembByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecTentGrpMembByUserIdxKey of the entity to be locked.
	 */
	default void deleteByUserIdx(ICFSecSecTentGrpMembByUserIdxKey key) {
		deleteByUserIdx(key.getRequiredSecUserId());
	}

}

// Description: Java 25 Spring JPA Repository for SecClusGrpMemb

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
 *	JpaRepository for the CFSecJpaSecClusGrpMemb entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaSecClusGrpMembRepository extends JpaRepository<CFSecJpaSecClusGrpMemb, CFSecJpaSecClusGrpMembPKey> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecClusGrpId
	 *		@param requiredLoginId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecClusGrpMemb r where r.pkey.requiredSecClusGrpId = :secClusGrpId and r.pkey.requiredLoginId = :loginId")
	CFSecJpaSecClusGrpMemb get(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId,
		@Param("loginId") String requiredLoginId);

	/**
	 *	CFSecSecClusGrpMembPKey based read method for object-based access.
	 *
	 *		@param key The key of the entity to be read.
	 *
	 *		@return The entity read, usually from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecClusGrpMemb get(ICFSecSecClusGrpMembPKey key) {
		return( get(key.getRequiredSecClusGrpId(), key.getRequiredLoginId()));
	}

	// CFSecJpaSecClusGrpMemb specified index readers

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecClusGrpMembByClusGrpIdxKey as arguments.
	 *
	 *		@param requiredSecClusGrpId
	 *
	 *		@return List&lt;CFSecJpaSecClusGrpMemb&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecClusGrpMemb r where r.pkey.requiredSecClusGrpId = :secClusGrpId")
	List<CFSecJpaSecClusGrpMemb> findByClusGrpIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId);

	/**
	 *	CFSecSecClusGrpMembByClusGrpIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecClusGrpMembByClusGrpIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecClusGrpMemb> findByClusGrpIdx(ICFSecSecClusGrpMembByClusGrpIdxKey key) {
		return( findByClusGrpIdx(key.getRequiredSecClusGrpId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecClusGrpMembByLoginIdxKey as arguments.
	 *
	 *		@param requiredLoginId
	 *
	 *		@return List&lt;CFSecJpaSecClusGrpMemb&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecClusGrpMemb r where r.pkey.requiredLoginId = :loginId")
	List<CFSecJpaSecClusGrpMemb> findByLoginIdx(@Param("loginId") String requiredLoginId);

	/**
	 *	CFSecSecClusGrpMembByLoginIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecClusGrpMembByLoginIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecClusGrpMemb> findByLoginIdx(ICFSecSecClusGrpMembByLoginIdxKey key) {
		return( findByLoginIdx(key.getRequiredLoginId()));
	}

	// CFSecJpaSecClusGrpMemb specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecClusGrpId
	 *		@param requiredLoginId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecClusGrpMemb r where r.pkey.requiredSecClusGrpId = :secClusGrpId and r.pkey.requiredLoginId = :loginId")
	CFSecJpaSecClusGrpMemb lockByIdIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId,
		@Param("loginId") String requiredLoginId);

	/**
	 *	CFSecSecClusGrpMembByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecClusGrpMemb lockByIdIdx(ICFSecSecClusGrpMembPKey key) {
		return( lockByIdIdx(key.getRequiredSecClusGrpId(), key.getRequiredLoginId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecClusGrpId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecClusGrpMemb r where r.pkey.requiredSecClusGrpId = :secClusGrpId")
	List<CFSecJpaSecClusGrpMemb> lockByClusGrpIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId);

	/**
	 *	CFSecSecClusGrpMembByClusGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecClusGrpMemb> lockByClusGrpIdx(ICFSecSecClusGrpMembByClusGrpIdxKey key) {
		return( lockByClusGrpIdx(key.getRequiredSecClusGrpId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredLoginId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecClusGrpMemb r where r.pkey.requiredLoginId = :loginId")
	List<CFSecJpaSecClusGrpMemb> lockByLoginIdx(@Param("loginId") String requiredLoginId);

	/**
	 *	CFSecSecClusGrpMembByLoginIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecClusGrpMemb> lockByLoginIdx(ICFSecSecClusGrpMembByLoginIdxKey key) {
		return( lockByLoginIdx(key.getRequiredLoginId()));
	}

	// CFSecJpaSecClusGrpMemb specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecClusGrpId
	 *		@param requiredLoginId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecClusGrpMemb r where r.pkey.requiredSecClusGrpId = :secClusGrpId and r.pkey.requiredLoginId = :loginId")
	void deleteByIdIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId,
		@Param("loginId") String requiredLoginId);

	/**
	 *	CFSecSecClusGrpMembByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecClusGrpMembByIdIdxKey of the entity to be locked.
	 */
	default void deleteByIdIdx(ICFSecSecClusGrpMembPKey key) {
		deleteByIdIdx(key.getRequiredSecClusGrpId(), key.getRequiredLoginId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecClusGrpId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecClusGrpMemb r where r.pkey.requiredSecClusGrpId = :secClusGrpId")
	void deleteByClusGrpIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId);

	/**
	 *	CFSecSecClusGrpMembByClusGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecClusGrpMembByClusGrpIdxKey of the entity to be locked.
	 */
	default void deleteByClusGrpIdx(ICFSecSecClusGrpMembByClusGrpIdxKey key) {
		deleteByClusGrpIdx(key.getRequiredSecClusGrpId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredLoginId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecClusGrpMemb r where r.pkey.requiredLoginId = :loginId")
	void deleteByLoginIdx(@Param("loginId") String requiredLoginId);

	/**
	 *	CFSecSecClusGrpMembByLoginIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecClusGrpMembByLoginIdxKey of the entity to be locked.
	 */
	default void deleteByLoginIdx(ICFSecSecClusGrpMembByLoginIdxKey key) {
		deleteByLoginIdx(key.getRequiredLoginId());
	}

}

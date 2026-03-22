// Description: Java 25 Spring JPA Repository for SecSysGrpMemb

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
 *	JpaRepository for the CFSecJpaSecSysGrpMemb entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaSecSysGrpMembRepository extends JpaRepository<CFSecJpaSecSysGrpMemb, CFSecJpaSecSysGrpMembPKey> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecSysGrpId
	 *		@param requiredSecUserId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecSysGrpMemb r where r.pkey.requiredContainerGroup.requiredSecSysGrpId = :secSysGrpId and r.pkey.requiredParentUser.requiredSecUserId = :secUserId")
	CFSecJpaSecSysGrpMemb get(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecSysGrpMembPKey based read method for object-based access.
	 *
	 *		@param key The key of the entity to be read.
	 *
	 *		@return The entity read, usually from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecSysGrpMemb get(ICFSecSecSysGrpMembPKey key) {
		return( get(key.getRequiredSecSysGrpId(), key.getRequiredSecUserId()));
	}

	// CFSecJpaSecSysGrpMemb specified index readers

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecSysGrpMembBySysGrpIdxKey as arguments.
	 *
	 *		@param requiredSecSysGrpId
	 *
	 *		@return List&lt;CFSecJpaSecSysGrpMemb&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecSysGrpMemb r where r.pkey.requiredContainerGroup.requiredSecSysGrpId = :secSysGrpId")
	List<CFSecJpaSecSysGrpMemb> findBySysGrpIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId);

	/**
	 *	CFSecSecSysGrpMembBySysGrpIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecSysGrpMembBySysGrpIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecSysGrpMemb> findBySysGrpIdx(ICFSecSecSysGrpMembBySysGrpIdxKey key) {
		return( findBySysGrpIdx(key.getRequiredSecSysGrpId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecSysGrpMembByUserIdxKey as arguments.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return List&lt;CFSecJpaSecSysGrpMemb&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecSysGrpMemb r where r.pkey.requiredParentUser.requiredSecUserId = :secUserId")
	List<CFSecJpaSecSysGrpMemb> findByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecSysGrpMembByUserIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecSysGrpMembByUserIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecSysGrpMemb> findByUserIdx(ICFSecSecSysGrpMembByUserIdxKey key) {
		return( findByUserIdx(key.getRequiredSecUserId()));
	}

	// CFSecJpaSecSysGrpMemb specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 *		@param requiredSecUserId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecSysGrpMemb r where r.pkey.requiredContainerGroup.requiredSecSysGrpId = :secSysGrpId and r.pkey.requiredParentUser.requiredSecUserId = :secUserId")
	CFSecJpaSecSysGrpMemb lockByIdIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecSysGrpMembByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecSysGrpMemb lockByIdIdx(ICFSecSecSysGrpMembPKey key) {
		return( lockByIdIdx(key.getRequiredSecSysGrpId(), key.getRequiredSecUserId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecSysGrpMemb r where r.pkey.requiredContainerGroup.requiredSecSysGrpId = :secSysGrpId")
	List<CFSecJpaSecSysGrpMemb> lockBySysGrpIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId);

	/**
	 *	CFSecSecSysGrpMembBySysGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecSysGrpMemb> lockBySysGrpIdx(ICFSecSecSysGrpMembBySysGrpIdxKey key) {
		return( lockBySysGrpIdx(key.getRequiredSecSysGrpId()));
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
	@Query("select r from CFSecJpaSecSysGrpMemb r where r.pkey.requiredParentUser.requiredSecUserId = :secUserId")
	List<CFSecJpaSecSysGrpMemb> lockByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecSysGrpMembByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecSysGrpMemb> lockByUserIdx(ICFSecSecSysGrpMembByUserIdxKey key) {
		return( lockByUserIdx(key.getRequiredSecUserId()));
	}

	// CFSecJpaSecSysGrpMemb specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 *		@param requiredSecUserId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecSysGrpMemb r where r.pkey.requiredContainerGroup.requiredSecSysGrpId = :secSysGrpId and r.pkey.requiredParentUser.requiredSecUserId = :secUserId")
	void deleteByIdIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecSysGrpMembByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecSysGrpMembByIdIdxKey of the entity to be locked.
	 */
	default void deleteByIdIdx(ICFSecSecSysGrpMembPKey key) {
		deleteByIdIdx(key.getRequiredSecSysGrpId(), key.getRequiredSecUserId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecSysGrpMemb r where r.pkey.requiredContainerGroup.requiredSecSysGrpId = :secSysGrpId")
	void deleteBySysGrpIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId);

	/**
	 *	CFSecSecSysGrpMembBySysGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecSysGrpMembBySysGrpIdxKey of the entity to be locked.
	 */
	default void deleteBySysGrpIdx(ICFSecSecSysGrpMembBySysGrpIdxKey key) {
		deleteBySysGrpIdx(key.getRequiredSecSysGrpId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecSysGrpMemb r where r.pkey.requiredParentUser.requiredSecUserId = :secUserId")
	void deleteByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecSysGrpMembByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecSysGrpMembByUserIdxKey of the entity to be locked.
	 */
	default void deleteByUserIdx(ICFSecSecSysGrpMembByUserIdxKey key) {
		deleteByUserIdx(key.getRequiredSecUserId());
	}

}

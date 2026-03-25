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
	 *		@param requiredLoginId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecSysGrpMemb r where r.pkey.requiredContainerGroup.requiredSecSysGrpId = :secSysGrpId and r.pkey.requiredParentUser.requiredLoginId = :loginId")
	CFSecJpaSecSysGrpMemb get(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId,
		@Param("loginId") String requiredLoginId);

	/**
	 *	CFSecSecSysGrpMembPKey based read method for object-based access.
	 *
	 *		@param key The key of the entity to be read.
	 *
	 *		@return The entity read, usually from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecSysGrpMemb get(ICFSecSecSysGrpMembPKey key) {
		return( get(key.getRequiredSecSysGrpId(), key.getRequiredLoginId()));
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
	 *	Read zero or more entities into a List using the columns of the CFSecSecSysGrpMembByLoginIdxKey as arguments.
	 *
	 *		@param requiredLoginId
	 *
	 *		@return List&lt;CFSecJpaSecSysGrpMemb&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecSysGrpMemb r where r.pkey.requiredParentUser.requiredLoginId = :loginId")
	List<CFSecJpaSecSysGrpMemb> findByLoginIdx(@Param("loginId") String requiredLoginId);

	/**
	 *	CFSecSecSysGrpMembByLoginIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecSysGrpMembByLoginIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecSysGrpMemb> findByLoginIdx(ICFSecSecSysGrpMembByLoginIdxKey key) {
		return( findByLoginIdx(key.getRequiredLoginId()));
	}

	// CFSecJpaSecSysGrpMemb specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 *		@param requiredLoginId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecSysGrpMemb r where r.pkey.requiredContainerGroup.requiredSecSysGrpId = :secSysGrpId and r.pkey.requiredParentUser.requiredLoginId = :loginId")
	CFSecJpaSecSysGrpMemb lockByIdIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId,
		@Param("loginId") String requiredLoginId);

	/**
	 *	CFSecSecSysGrpMembByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecSysGrpMemb lockByIdIdx(ICFSecSecSysGrpMembPKey key) {
		return( lockByIdIdx(key.getRequiredSecSysGrpId(), key.getRequiredLoginId()));
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
	 *		@param requiredLoginId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecSysGrpMemb r where r.pkey.requiredParentUser.requiredLoginId = :loginId")
	List<CFSecJpaSecSysGrpMemb> lockByLoginIdx(@Param("loginId") String requiredLoginId);

	/**
	 *	CFSecSecSysGrpMembByLoginIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecSysGrpMemb> lockByLoginIdx(ICFSecSecSysGrpMembByLoginIdxKey key) {
		return( lockByLoginIdx(key.getRequiredLoginId()));
	}

	// CFSecJpaSecSysGrpMemb specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 *		@param requiredLoginId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecSysGrpMemb r where r.pkey.requiredContainerGroup.requiredSecSysGrpId = :secSysGrpId and r.pkey.requiredParentUser.requiredLoginId = :loginId")
	void deleteByIdIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId,
		@Param("loginId") String requiredLoginId);

	/**
	 *	CFSecSecSysGrpMembByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecSysGrpMembByIdIdxKey of the entity to be locked.
	 */
	default void deleteByIdIdx(ICFSecSecSysGrpMembPKey key) {
		deleteByIdIdx(key.getRequiredSecSysGrpId(), key.getRequiredLoginId());
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
	 *		@param requiredLoginId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecSysGrpMemb r where r.pkey.requiredParentUser.requiredLoginId = :loginId")
	void deleteByLoginIdx(@Param("loginId") String requiredLoginId);

	/**
	 *	CFSecSecSysGrpMembByLoginIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecSysGrpMembByLoginIdxKey of the entity to be locked.
	 */
	default void deleteByLoginIdx(ICFSecSecSysGrpMembByLoginIdxKey key) {
		deleteByLoginIdx(key.getRequiredLoginId());
	}

}

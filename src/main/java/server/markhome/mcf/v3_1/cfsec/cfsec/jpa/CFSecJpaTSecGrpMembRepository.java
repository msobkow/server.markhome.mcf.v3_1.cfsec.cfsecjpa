// Description: Java 25 Spring JPA Repository for TSecGrpMemb

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
 *	JpaRepository for the CFSecJpaTSecGrpMemb entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaTSecGrpMembRepository extends JpaRepository<CFSecJpaTSecGrpMemb, CFLibDbKeyHash256> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredTSecGrpMembId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaTSecGrpMemb r where r.requiredTSecGrpMembId = :tSecGrpMembId")
	CFSecJpaTSecGrpMemb get(@Param("tSecGrpMembId") CFLibDbKeyHash256 requiredTSecGrpMembId);

	// CFSecJpaTSecGrpMemb specified index readers

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecTSecGrpMembByTenantIdxKey as arguments.
	 *
	 *		@param requiredTenantId
	 *
	 *		@return List&lt;CFSecJpaTSecGrpMemb&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaTSecGrpMemb r where r.requiredTenantId = :tenantId")
	List<CFSecJpaTSecGrpMemb> findByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId);

	/**
	 *	CFSecTSecGrpMembByTenantIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecTSecGrpMembByTenantIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaTSecGrpMemb> findByTenantIdx(ICFSecTSecGrpMembByTenantIdxKey key) {
		return( findByTenantIdx(key.getRequiredTenantId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecTSecGrpMembByGroupIdxKey as arguments.
	 *
	 *		@param requiredTSecGroupId
	 *
	 *		@return List&lt;CFSecJpaTSecGrpMemb&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaTSecGrpMemb r where r.requiredContainerGroup.requiredTSecGroupId = :tSecGroupId")
	List<CFSecJpaTSecGrpMemb> findByGroupIdx(@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId);

	/**
	 *	CFSecTSecGrpMembByGroupIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecTSecGrpMembByGroupIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaTSecGrpMemb> findByGroupIdx(ICFSecTSecGrpMembByGroupIdxKey key) {
		return( findByGroupIdx(key.getRequiredTSecGroupId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecTSecGrpMembByUserIdxKey as arguments.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return List&lt;CFSecJpaTSecGrpMemb&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaTSecGrpMemb r where r.requiredParentUser.requiredSecUserId = :secUserId")
	List<CFSecJpaTSecGrpMemb> findByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecTSecGrpMembByUserIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecTSecGrpMembByUserIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaTSecGrpMemb> findByUserIdx(ICFSecTSecGrpMembByUserIdxKey key) {
		return( findByUserIdx(key.getRequiredSecUserId()));
	}

	/**
	 *	Read an entity using the columns of the CFSecTSecGrpMembByUUserIdxKey as arguments.
	 *
	 *		@param requiredTenantId
	 *		@param requiredTSecGroupId
	 *		@param requiredSecUserId
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaTSecGrpMemb r where r.requiredTenantId = :tenantId and r.requiredContainerGroup.requiredTSecGroupId = :tSecGroupId and r.requiredParentUser.requiredSecUserId = :secUserId")
	CFSecJpaTSecGrpMemb findByUUserIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecTSecGrpMembByUUserIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecTSecGrpMembByUUserIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaTSecGrpMemb findByUUserIdx(ICFSecTSecGrpMembByUUserIdxKey key) {
		return( findByUUserIdx(key.getRequiredTenantId(), key.getRequiredTSecGroupId(), key.getRequiredSecUserId()));
	}

	// CFSecJpaTSecGrpMemb specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTSecGrpMembId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaTSecGrpMemb r where r.requiredTSecGrpMembId = :tSecGrpMembId")
	CFSecJpaTSecGrpMemb lockByIdIdx(@Param("tSecGrpMembId") CFLibDbKeyHash256 requiredTSecGrpMembId);

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaTSecGrpMemb r where r.requiredTenantId = :tenantId")
	List<CFSecJpaTSecGrpMemb> lockByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId);

	/**
	 *	CFSecTSecGrpMembByTenantIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaTSecGrpMemb> lockByTenantIdx(ICFSecTSecGrpMembByTenantIdxKey key) {
		return( lockByTenantIdx(key.getRequiredTenantId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTSecGroupId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaTSecGrpMemb r where r.requiredContainerGroup.requiredTSecGroupId = :tSecGroupId")
	List<CFSecJpaTSecGrpMemb> lockByGroupIdx(@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId);

	/**
	 *	CFSecTSecGrpMembByGroupIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaTSecGrpMemb> lockByGroupIdx(ICFSecTSecGrpMembByGroupIdxKey key) {
		return( lockByGroupIdx(key.getRequiredTSecGroupId()));
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
	@Query("select r from CFSecJpaTSecGrpMemb r where r.requiredParentUser.requiredSecUserId = :secUserId")
	List<CFSecJpaTSecGrpMemb> lockByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecTSecGrpMembByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaTSecGrpMemb> lockByUserIdx(ICFSecTSecGrpMembByUserIdxKey key) {
		return( lockByUserIdx(key.getRequiredSecUserId()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *		@param requiredTSecGroupId
	 *		@param requiredSecUserId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaTSecGrpMemb r where r.requiredTenantId = :tenantId and r.requiredContainerGroup.requiredTSecGroupId = :tSecGroupId and r.requiredParentUser.requiredSecUserId = :secUserId")
	CFSecJpaTSecGrpMemb lockByUUserIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecTSecGrpMembByUUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaTSecGrpMemb lockByUUserIdx(ICFSecTSecGrpMembByUUserIdxKey key) {
		return( lockByUUserIdx(key.getRequiredTenantId(), key.getRequiredTSecGroupId(), key.getRequiredSecUserId()));
	}

	// CFSecJpaTSecGrpMemb specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTSecGrpMembId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaTSecGrpMemb r where r.requiredTSecGrpMembId = :tSecGrpMembId")
	void deleteByIdIdx(@Param("tSecGrpMembId") CFLibDbKeyHash256 requiredTSecGrpMembId);

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaTSecGrpMemb r where r.requiredTenantId = :tenantId")
	void deleteByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId);

	/**
	 *	CFSecTSecGrpMembByTenantIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecTSecGrpMembByTenantIdxKey of the entity to be locked.
	 */
	default void deleteByTenantIdx(ICFSecTSecGrpMembByTenantIdxKey key) {
		deleteByTenantIdx(key.getRequiredTenantId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTSecGroupId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaTSecGrpMemb r where r.requiredContainerGroup.requiredTSecGroupId = :tSecGroupId")
	void deleteByGroupIdx(@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId);

	/**
	 *	CFSecTSecGrpMembByGroupIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecTSecGrpMembByGroupIdxKey of the entity to be locked.
	 */
	default void deleteByGroupIdx(ICFSecTSecGrpMembByGroupIdxKey key) {
		deleteByGroupIdx(key.getRequiredTSecGroupId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaTSecGrpMemb r where r.requiredParentUser.requiredSecUserId = :secUserId")
	void deleteByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecTSecGrpMembByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecTSecGrpMembByUserIdxKey of the entity to be locked.
	 */
	default void deleteByUserIdx(ICFSecTSecGrpMembByUserIdxKey key) {
		deleteByUserIdx(key.getRequiredSecUserId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *		@param requiredTSecGroupId
	 *		@param requiredSecUserId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaTSecGrpMemb r where r.requiredTenantId = :tenantId and r.requiredContainerGroup.requiredTSecGroupId = :tSecGroupId and r.requiredParentUser.requiredSecUserId = :secUserId")
	void deleteByUUserIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecTSecGrpMembByUUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecTSecGrpMembByUUserIdxKey of the entity to be locked.
	 */
	default void deleteByUUserIdx(ICFSecTSecGrpMembByUUserIdxKey key) {
		deleteByUUserIdx(key.getRequiredTenantId(), key.getRequiredTSecGroupId(), key.getRequiredSecUserId());
	}

}

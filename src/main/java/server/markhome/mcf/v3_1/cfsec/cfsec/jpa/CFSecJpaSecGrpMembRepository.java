// Description: Java 25 Spring JPA Repository for SecGrpMemb

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
 *	JpaRepository for the CFSecJpaSecGrpMemb entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaSecGrpMembRepository extends JpaRepository<CFSecJpaSecGrpMemb, CFLibDbKeyHash256> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecGrpMembId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecGrpMemb r where r.requiredSecGrpMembId = :secGrpMembId")
	CFSecJpaSecGrpMemb get(@Param("secGrpMembId") CFLibDbKeyHash256 requiredSecGrpMembId);

	// CFSecJpaSecGrpMemb specified index readers

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecGrpMembByClusterIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return List&lt;CFSecJpaSecGrpMemb&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecGrpMemb r where r.requiredClusterId = :clusterId")
	List<CFSecJpaSecGrpMemb> findByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId);

	/**
	 *	CFSecSecGrpMembByClusterIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecGrpMembByClusterIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecGrpMemb> findByClusterIdx(ICFSecSecGrpMembByClusterIdxKey key) {
		return( findByClusterIdx(key.getRequiredClusterId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecGrpMembByGroupIdxKey as arguments.
	 *
	 *		@param requiredSecGroupId
	 *
	 *		@return List&lt;CFSecJpaSecGrpMemb&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecGrpMemb r where r.requiredContainerGroup.requiredSecGroupId = :secGroupId")
	List<CFSecJpaSecGrpMemb> findByGroupIdx(@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId);

	/**
	 *	CFSecSecGrpMembByGroupIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecGrpMembByGroupIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecGrpMemb> findByGroupIdx(ICFSecSecGrpMembByGroupIdxKey key) {
		return( findByGroupIdx(key.getRequiredSecGroupId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecGrpMembByUserIdxKey as arguments.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return List&lt;CFSecJpaSecGrpMemb&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecGrpMemb r where r.requiredParentUser.requiredSecUserId = :secUserId")
	List<CFSecJpaSecGrpMemb> findByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecGrpMembByUserIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecGrpMembByUserIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecGrpMemb> findByUserIdx(ICFSecSecGrpMembByUserIdxKey key) {
		return( findByUserIdx(key.getRequiredSecUserId()));
	}

	/**
	 *	Read an entity using the columns of the CFSecSecGrpMembByUUserIdxKey as arguments.
	 *
	 *		@param requiredClusterId
	 *		@param requiredSecGroupId
	 *		@param requiredSecUserId
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecGrpMemb r where r.requiredClusterId = :clusterId and r.requiredContainerGroup.requiredSecGroupId = :secGroupId and r.requiredParentUser.requiredSecUserId = :secUserId")
	CFSecJpaSecGrpMemb findByUUserIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecGrpMembByUUserIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecGrpMembByUUserIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecGrpMemb findByUUserIdx(ICFSecSecGrpMembByUUserIdxKey key) {
		return( findByUUserIdx(key.getRequiredClusterId(), key.getRequiredSecGroupId(), key.getRequiredSecUserId()));
	}

	// CFSecJpaSecGrpMemb specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecGrpMembId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecGrpMemb r where r.requiredSecGrpMembId = :secGrpMembId")
	CFSecJpaSecGrpMemb lockByIdIdx(@Param("secGrpMembId") CFLibDbKeyHash256 requiredSecGrpMembId);

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecGrpMemb r where r.requiredClusterId = :clusterId")
	List<CFSecJpaSecGrpMemb> lockByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId);

	/**
	 *	CFSecSecGrpMembByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecGrpMemb> lockByClusterIdx(ICFSecSecGrpMembByClusterIdxKey key) {
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
	@Query("select r from CFSecJpaSecGrpMemb r where r.requiredContainerGroup.requiredSecGroupId = :secGroupId")
	List<CFSecJpaSecGrpMemb> lockByGroupIdx(@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId);

	/**
	 *	CFSecSecGrpMembByGroupIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecGrpMemb> lockByGroupIdx(ICFSecSecGrpMembByGroupIdxKey key) {
		return( lockByGroupIdx(key.getRequiredSecGroupId()));
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
	@Query("select r from CFSecJpaSecGrpMemb r where r.requiredParentUser.requiredSecUserId = :secUserId")
	List<CFSecJpaSecGrpMemb> lockByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecGrpMembByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecGrpMemb> lockByUserIdx(ICFSecSecGrpMembByUserIdxKey key) {
		return( lockByUserIdx(key.getRequiredSecUserId()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredSecGroupId
	 *		@param requiredSecUserId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecGrpMemb r where r.requiredClusterId = :clusterId and r.requiredContainerGroup.requiredSecGroupId = :secGroupId and r.requiredParentUser.requiredSecUserId = :secUserId")
	CFSecJpaSecGrpMemb lockByUUserIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecGrpMembByUUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecGrpMemb lockByUUserIdx(ICFSecSecGrpMembByUUserIdxKey key) {
		return( lockByUUserIdx(key.getRequiredClusterId(), key.getRequiredSecGroupId(), key.getRequiredSecUserId()));
	}

	// CFSecJpaSecGrpMemb specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecGrpMembId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecGrpMemb r where r.requiredSecGrpMembId = :secGrpMembId")
	void deleteByIdIdx(@Param("secGrpMembId") CFLibDbKeyHash256 requiredSecGrpMembId);

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecGrpMemb r where r.requiredClusterId = :clusterId")
	void deleteByClusterIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId);

	/**
	 *	CFSecSecGrpMembByClusterIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecGrpMembByClusterIdxKey of the entity to be locked.
	 */
	default void deleteByClusterIdx(ICFSecSecGrpMembByClusterIdxKey key) {
		deleteByClusterIdx(key.getRequiredClusterId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecGroupId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecGrpMemb r where r.requiredContainerGroup.requiredSecGroupId = :secGroupId")
	void deleteByGroupIdx(@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId);

	/**
	 *	CFSecSecGrpMembByGroupIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecGrpMembByGroupIdxKey of the entity to be locked.
	 */
	default void deleteByGroupIdx(ICFSecSecGrpMembByGroupIdxKey key) {
		deleteByGroupIdx(key.getRequiredSecGroupId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecGrpMemb r where r.requiredParentUser.requiredSecUserId = :secUserId")
	void deleteByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecGrpMembByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecGrpMembByUserIdxKey of the entity to be locked.
	 */
	default void deleteByUserIdx(ICFSecSecGrpMembByUserIdxKey key) {
		deleteByUserIdx(key.getRequiredSecUserId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredClusterId
	 *		@param requiredSecGroupId
	 *		@param requiredSecUserId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecGrpMemb r where r.requiredClusterId = :clusterId and r.requiredContainerGroup.requiredSecGroupId = :secGroupId and r.requiredParentUser.requiredSecUserId = :secUserId")
	void deleteByUUserIdx(@Param("clusterId") CFLibDbKeyHash256 requiredClusterId,
		@Param("secGroupId") CFLibDbKeyHash256 requiredSecGroupId,
		@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecGrpMembByUUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecGrpMembByUUserIdxKey of the entity to be locked.
	 */
	default void deleteByUUserIdx(ICFSecSecGrpMembByUUserIdxKey key) {
		deleteByUUserIdx(key.getRequiredClusterId(), key.getRequiredSecGroupId(), key.getRequiredSecUserId());
	}

}

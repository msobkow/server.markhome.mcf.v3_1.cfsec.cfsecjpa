// Description: Java 25 Spring JPA Repository for TSecGrpInc

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
 *	JpaRepository for the CFSecJpaTSecGrpInc entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaTSecGrpIncRepository extends JpaRepository<CFSecJpaTSecGrpInc, CFLibDbKeyHash256> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredTSecGrpIncId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaTSecGrpInc r where r.requiredTSecGrpIncId = :tSecGrpIncId")
	CFSecJpaTSecGrpInc get(@Param("tSecGrpIncId") CFLibDbKeyHash256 requiredTSecGrpIncId);

	// CFSecJpaTSecGrpInc specified index readers

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecTSecGrpIncByTenantIdxKey as arguments.
	 *
	 *		@param requiredTenantId
	 *
	 *		@return List&lt;CFSecJpaTSecGrpInc&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaTSecGrpInc r where r.requiredTenantId = :tenantId")
	List<CFSecJpaTSecGrpInc> findByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId);

	/**
	 *	CFSecTSecGrpIncByTenantIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecTSecGrpIncByTenantIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaTSecGrpInc> findByTenantIdx(ICFSecTSecGrpIncByTenantIdxKey key) {
		return( findByTenantIdx(key.getRequiredTenantId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecTSecGrpIncByGroupIdxKey as arguments.
	 *
	 *		@param requiredTSecGroupId
	 *
	 *		@return List&lt;CFSecJpaTSecGrpInc&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaTSecGrpInc r where r.requiredContainerGroup.requiredTSecGroupId = :tSecGroupId")
	List<CFSecJpaTSecGrpInc> findByGroupIdx(@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId);

	/**
	 *	CFSecTSecGrpIncByGroupIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecTSecGrpIncByGroupIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaTSecGrpInc> findByGroupIdx(ICFSecTSecGrpIncByGroupIdxKey key) {
		return( findByGroupIdx(key.getRequiredTSecGroupId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecTSecGrpIncByIncludeIdxKey as arguments.
	 *
	 *		@param requiredIncludeGroupId
	 *
	 *		@return List&lt;CFSecJpaTSecGrpInc&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaTSecGrpInc r where r.requiredParentSubGroup.requiredTSecGroupId = :includeGroupId")
	List<CFSecJpaTSecGrpInc> findByIncludeIdx(@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId);

	/**
	 *	CFSecTSecGrpIncByIncludeIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecTSecGrpIncByIncludeIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaTSecGrpInc> findByIncludeIdx(ICFSecTSecGrpIncByIncludeIdxKey key) {
		return( findByIncludeIdx(key.getRequiredIncludeGroupId()));
	}

	/**
	 *	Read an entity using the columns of the CFSecTSecGrpIncByUIncludeIdxKey as arguments.
	 *
	 *		@param requiredTenantId
	 *		@param requiredTSecGroupId
	 *		@param requiredIncludeGroupId
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaTSecGrpInc r where r.requiredTenantId = :tenantId and r.requiredContainerGroup.requiredTSecGroupId = :tSecGroupId and r.requiredParentSubGroup.requiredTSecGroupId = :includeGroupId")
	CFSecJpaTSecGrpInc findByUIncludeIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId,
		@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId);

	/**
	 *	CFSecTSecGrpIncByUIncludeIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecTSecGrpIncByUIncludeIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaTSecGrpInc findByUIncludeIdx(ICFSecTSecGrpIncByUIncludeIdxKey key) {
		return( findByUIncludeIdx(key.getRequiredTenantId(), key.getRequiredTSecGroupId(), key.getRequiredIncludeGroupId()));
	}

	// CFSecJpaTSecGrpInc specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTSecGrpIncId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaTSecGrpInc r where r.requiredTSecGrpIncId = :tSecGrpIncId")
	CFSecJpaTSecGrpInc lockByIdIdx(@Param("tSecGrpIncId") CFLibDbKeyHash256 requiredTSecGrpIncId);

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaTSecGrpInc r where r.requiredTenantId = :tenantId")
	List<CFSecJpaTSecGrpInc> lockByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId);

	/**
	 *	CFSecTSecGrpIncByTenantIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaTSecGrpInc> lockByTenantIdx(ICFSecTSecGrpIncByTenantIdxKey key) {
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
	@Query("select r from CFSecJpaTSecGrpInc r where r.requiredContainerGroup.requiredTSecGroupId = :tSecGroupId")
	List<CFSecJpaTSecGrpInc> lockByGroupIdx(@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId);

	/**
	 *	CFSecTSecGrpIncByGroupIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaTSecGrpInc> lockByGroupIdx(ICFSecTSecGrpIncByGroupIdxKey key) {
		return( lockByGroupIdx(key.getRequiredTSecGroupId()));
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
	@Query("select r from CFSecJpaTSecGrpInc r where r.requiredParentSubGroup.requiredTSecGroupId = :includeGroupId")
	List<CFSecJpaTSecGrpInc> lockByIncludeIdx(@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId);

	/**
	 *	CFSecTSecGrpIncByIncludeIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaTSecGrpInc> lockByIncludeIdx(ICFSecTSecGrpIncByIncludeIdxKey key) {
		return( lockByIncludeIdx(key.getRequiredIncludeGroupId()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *		@param requiredTSecGroupId
	 *		@param requiredIncludeGroupId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaTSecGrpInc r where r.requiredTenantId = :tenantId and r.requiredContainerGroup.requiredTSecGroupId = :tSecGroupId and r.requiredParentSubGroup.requiredTSecGroupId = :includeGroupId")
	CFSecJpaTSecGrpInc lockByUIncludeIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId,
		@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId);

	/**
	 *	CFSecTSecGrpIncByUIncludeIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaTSecGrpInc lockByUIncludeIdx(ICFSecTSecGrpIncByUIncludeIdxKey key) {
		return( lockByUIncludeIdx(key.getRequiredTenantId(), key.getRequiredTSecGroupId(), key.getRequiredIncludeGroupId()));
	}

	// CFSecJpaTSecGrpInc specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTSecGrpIncId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaTSecGrpInc r where r.requiredTSecGrpIncId = :tSecGrpIncId")
	void deleteByIdIdx(@Param("tSecGrpIncId") CFLibDbKeyHash256 requiredTSecGrpIncId);

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaTSecGrpInc r where r.requiredTenantId = :tenantId")
	void deleteByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId);

	/**
	 *	CFSecTSecGrpIncByTenantIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecTSecGrpIncByTenantIdxKey of the entity to be locked.
	 */
	default void deleteByTenantIdx(ICFSecTSecGrpIncByTenantIdxKey key) {
		deleteByTenantIdx(key.getRequiredTenantId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTSecGroupId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaTSecGrpInc r where r.requiredContainerGroup.requiredTSecGroupId = :tSecGroupId")
	void deleteByGroupIdx(@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId);

	/**
	 *	CFSecTSecGrpIncByGroupIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecTSecGrpIncByGroupIdxKey of the entity to be locked.
	 */
	default void deleteByGroupIdx(ICFSecTSecGrpIncByGroupIdxKey key) {
		deleteByGroupIdx(key.getRequiredTSecGroupId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredIncludeGroupId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaTSecGrpInc r where r.requiredParentSubGroup.requiredTSecGroupId = :includeGroupId")
	void deleteByIncludeIdx(@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId);

	/**
	 *	CFSecTSecGrpIncByIncludeIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecTSecGrpIncByIncludeIdxKey of the entity to be locked.
	 */
	default void deleteByIncludeIdx(ICFSecTSecGrpIncByIncludeIdxKey key) {
		deleteByIncludeIdx(key.getRequiredIncludeGroupId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *		@param requiredTSecGroupId
	 *		@param requiredIncludeGroupId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaTSecGrpInc r where r.requiredTenantId = :tenantId and r.requiredContainerGroup.requiredTSecGroupId = :tSecGroupId and r.requiredParentSubGroup.requiredTSecGroupId = :includeGroupId")
	void deleteByUIncludeIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId,
		@Param("includeGroupId") CFLibDbKeyHash256 requiredIncludeGroupId);

	/**
	 *	CFSecTSecGrpIncByUIncludeIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecTSecGrpIncByUIncludeIdxKey of the entity to be locked.
	 */
	default void deleteByUIncludeIdx(ICFSecTSecGrpIncByUIncludeIdxKey key) {
		deleteByUIncludeIdx(key.getRequiredTenantId(), key.getRequiredTSecGroupId(), key.getRequiredIncludeGroupId());
	}

}

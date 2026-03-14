// Description: Java 25 Spring JPA Repository for TSecGroup

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
 *	JpaRepository for the CFSecJpaTSecGroup entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaTSecGroupRepository extends JpaRepository<CFSecJpaTSecGroup, CFLibDbKeyHash256> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredTSecGroupId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaTSecGroup r where r.requiredTSecGroupId = :tSecGroupId")
	CFSecJpaTSecGroup get(@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId);

	// CFSecJpaTSecGroup specified index readers

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecTSecGroupByTenantIdxKey as arguments.
	 *
	 *		@param requiredTenantId
	 *
	 *		@return List&lt;CFSecJpaTSecGroup&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaTSecGroup r where r.requiredContainerTenant.requiredId = :tenantId")
	List<CFSecJpaTSecGroup> findByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId);

	/**
	 *	CFSecTSecGroupByTenantIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecTSecGroupByTenantIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaTSecGroup> findByTenantIdx(ICFSecTSecGroupByTenantIdxKey key) {
		return( findByTenantIdx(key.getRequiredTenantId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecTSecGroupByTenantVisIdxKey as arguments.
	 *
	 *		@param requiredTenantId
	 *		@param requiredIsVisible
	 *
	 *		@return List&lt;CFSecJpaTSecGroup&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaTSecGroup r where r.requiredContainerTenant.requiredId = :tenantId and r.requiredIsVisible = :isVisible")
	List<CFSecJpaTSecGroup> findByTenantVisIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("isVisible") boolean requiredIsVisible);

	/**
	 *	CFSecTSecGroupByTenantVisIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecTSecGroupByTenantVisIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaTSecGroup> findByTenantVisIdx(ICFSecTSecGroupByTenantVisIdxKey key) {
		return( findByTenantVisIdx(key.getRequiredTenantId(), key.getRequiredIsVisible()));
	}

	/**
	 *	Read an entity using the columns of the CFSecTSecGroupByUNameIdxKey as arguments.
	 *
	 *		@param requiredTenantId
	 *		@param requiredName
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaTSecGroup r where r.requiredContainerTenant.requiredId = :tenantId and r.requiredName = :name")
	CFSecJpaTSecGroup findByUNameIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("name") String requiredName);

	/**
	 *	CFSecTSecGroupByUNameIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecTSecGroupByUNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaTSecGroup findByUNameIdx(ICFSecTSecGroupByUNameIdxKey key) {
		return( findByUNameIdx(key.getRequiredTenantId(), key.getRequiredName()));
	}

	// CFSecJpaTSecGroup specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTSecGroupId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaTSecGroup r where r.requiredTSecGroupId = :tSecGroupId")
	CFSecJpaTSecGroup lockByIdIdx(@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId);

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaTSecGroup r where r.requiredContainerTenant.requiredId = :tenantId")
	List<CFSecJpaTSecGroup> lockByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId);

	/**
	 *	CFSecTSecGroupByTenantIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaTSecGroup> lockByTenantIdx(ICFSecTSecGroupByTenantIdxKey key) {
		return( lockByTenantIdx(key.getRequiredTenantId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *		@param requiredIsVisible
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaTSecGroup r where r.requiredContainerTenant.requiredId = :tenantId and r.requiredIsVisible = :isVisible")
	List<CFSecJpaTSecGroup> lockByTenantVisIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("isVisible") boolean requiredIsVisible);

	/**
	 *	CFSecTSecGroupByTenantVisIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaTSecGroup> lockByTenantVisIdx(ICFSecTSecGroupByTenantVisIdxKey key) {
		return( lockByTenantVisIdx(key.getRequiredTenantId(), key.getRequiredIsVisible()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *		@param requiredName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaTSecGroup r where r.requiredContainerTenant.requiredId = :tenantId and r.requiredName = :name")
	CFSecJpaTSecGroup lockByUNameIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("name") String requiredName);

	/**
	 *	CFSecTSecGroupByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaTSecGroup lockByUNameIdx(ICFSecTSecGroupByUNameIdxKey key) {
		return( lockByUNameIdx(key.getRequiredTenantId(), key.getRequiredName()));
	}

	// CFSecJpaTSecGroup specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTSecGroupId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaTSecGroup r where r.requiredTSecGroupId = :tSecGroupId")
	void deleteByIdIdx(@Param("tSecGroupId") CFLibDbKeyHash256 requiredTSecGroupId);

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaTSecGroup r where r.requiredContainerTenant.requiredId = :tenantId")
	void deleteByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId);

	/**
	 *	CFSecTSecGroupByTenantIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecTSecGroupByTenantIdxKey of the entity to be locked.
	 */
	default void deleteByTenantIdx(ICFSecTSecGroupByTenantIdxKey key) {
		deleteByTenantIdx(key.getRequiredTenantId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *		@param requiredIsVisible
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaTSecGroup r where r.requiredContainerTenant.requiredId = :tenantId and r.requiredIsVisible = :isVisible")
	void deleteByTenantVisIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("isVisible") boolean requiredIsVisible);

	/**
	 *	CFSecTSecGroupByTenantVisIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecTSecGroupByTenantVisIdxKey of the entity to be locked.
	 */
	default void deleteByTenantVisIdx(ICFSecTSecGroupByTenantVisIdxKey key) {
		deleteByTenantVisIdx(key.getRequiredTenantId(), key.getRequiredIsVisible());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *		@param requiredName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaTSecGroup r where r.requiredContainerTenant.requiredId = :tenantId and r.requiredName = :name")
	void deleteByUNameIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("name") String requiredName);

	/**
	 *	CFSecTSecGroupByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecTSecGroupByUNameIdxKey of the entity to be locked.
	 */
	default void deleteByUNameIdx(ICFSecTSecGroupByUNameIdxKey key) {
		deleteByUNameIdx(key.getRequiredTenantId(), key.getRequiredName());
	}

}

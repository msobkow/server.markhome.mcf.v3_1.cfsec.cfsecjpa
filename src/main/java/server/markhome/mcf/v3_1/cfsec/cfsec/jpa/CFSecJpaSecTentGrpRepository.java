// Description: Java 25 Spring JPA Repository for SecTentGrp

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
 *	JpaRepository for the CFSecJpaSecTentGrp entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaSecTentGrpRepository extends JpaRepository<CFSecJpaSecTentGrp, CFLibDbKeyHash256> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecTentGrpId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecTentGrp r where r.requiredSecTentGrpId = :secTentGrpId")
	CFSecJpaSecTentGrp get(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId);

	// CFSecJpaSecTentGrp specified index readers

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecTentGrpByTenantIdxKey as arguments.
	 *
	 *		@param requiredTenantId
	 *
	 *		@return List&lt;CFSecJpaSecTentGrp&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecTentGrp r where r.requiredOwnerTenant.requiredId = :tenantId")
	List<CFSecJpaSecTentGrp> findByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId);

	/**
	 *	CFSecSecTentGrpByTenantIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecTentGrpByTenantIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecTentGrp> findByTenantIdx(ICFSecSecTentGrpByTenantIdxKey key) {
		return( findByTenantIdx(key.getRequiredTenantId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecTentGrpByNameIdxKey as arguments.
	 *
	 *		@param requiredName
	 *
	 *		@return List&lt;CFSecJpaSecTentGrp&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecTentGrp r where r.requiredName = :name")
	List<CFSecJpaSecTentGrp> findByNameIdx(@Param("name") String requiredName);

	/**
	 *	CFSecSecTentGrpByNameIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecTentGrpByNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecTentGrp> findByNameIdx(ICFSecSecTentGrpByNameIdxKey key) {
		return( findByNameIdx(key.getRequiredName()));
	}

	/**
	 *	Read an entity using the columns of the CFSecSecTentGrpByUNameIdxKey as arguments.
	 *
	 *		@param requiredTenantId
	 *		@param requiredName
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecTentGrp r where r.requiredOwnerTenant.requiredId = :tenantId and r.requiredName = :name")
	CFSecJpaSecTentGrp findByUNameIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("name") String requiredName);

	/**
	 *	CFSecSecTentGrpByUNameIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecTentGrpByUNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecTentGrp findByUNameIdx(ICFSecSecTentGrpByUNameIdxKey key) {
		return( findByUNameIdx(key.getRequiredTenantId(), key.getRequiredName()));
	}

	// CFSecJpaSecTentGrp specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecTentGrpId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecTentGrp r where r.requiredSecTentGrpId = :secTentGrpId")
	CFSecJpaSecTentGrp lockByIdIdx(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId);

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecTentGrp r where r.requiredOwnerTenant.requiredId = :tenantId")
	List<CFSecJpaSecTentGrp> lockByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId);

	/**
	 *	CFSecSecTentGrpByTenantIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecTentGrp> lockByTenantIdx(ICFSecSecTentGrpByTenantIdxKey key) {
		return( lockByTenantIdx(key.getRequiredTenantId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredName
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecTentGrp r where r.requiredName = :name")
	List<CFSecJpaSecTentGrp> lockByNameIdx(@Param("name") String requiredName);

	/**
	 *	CFSecSecTentGrpByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecTentGrp> lockByNameIdx(ICFSecSecTentGrpByNameIdxKey key) {
		return( lockByNameIdx(key.getRequiredName()));
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
	@Query("select r from CFSecJpaSecTentGrp r where r.requiredOwnerTenant.requiredId = :tenantId and r.requiredName = :name")
	CFSecJpaSecTentGrp lockByUNameIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("name") String requiredName);

	/**
	 *	CFSecSecTentGrpByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecTentGrp lockByUNameIdx(ICFSecSecTentGrpByUNameIdxKey key) {
		return( lockByUNameIdx(key.getRequiredTenantId(), key.getRequiredName()));
	}

	// CFSecJpaSecTentGrp specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecTentGrpId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecTentGrp r where r.requiredSecTentGrpId = :secTentGrpId")
	void deleteByIdIdx(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId);

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecTentGrp r where r.requiredOwnerTenant.requiredId = :tenantId")
	void deleteByTenantIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId);

	/**
	 *	CFSecSecTentGrpByTenantIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecTentGrpByTenantIdxKey of the entity to be locked.
	 */
	default void deleteByTenantIdx(ICFSecSecTentGrpByTenantIdxKey key) {
		deleteByTenantIdx(key.getRequiredTenantId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecTentGrp r where r.requiredName = :name")
	void deleteByNameIdx(@Param("name") String requiredName);

	/**
	 *	CFSecSecTentGrpByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecTentGrpByNameIdxKey of the entity to be locked.
	 */
	default void deleteByNameIdx(ICFSecSecTentGrpByNameIdxKey key) {
		deleteByNameIdx(key.getRequiredName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTenantId
	 *		@param requiredName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecTentGrp r where r.requiredOwnerTenant.requiredId = :tenantId and r.requiredName = :name")
	void deleteByUNameIdx(@Param("tenantId") CFLibDbKeyHash256 requiredTenantId,
		@Param("name") String requiredName);

	/**
	 *	CFSecSecTentGrpByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecTentGrpByUNameIdxKey of the entity to be locked.
	 */
	default void deleteByUNameIdx(ICFSecSecTentGrpByUNameIdxKey key) {
		deleteByUNameIdx(key.getRequiredTenantId(), key.getRequiredName());
	}

}

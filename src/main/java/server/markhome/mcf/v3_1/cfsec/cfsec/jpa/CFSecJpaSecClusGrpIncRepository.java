// Description: Java 25 Spring JPA Repository for SecClusGrpInc

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
 *	JpaRepository for the CFSecJpaSecClusGrpInc entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaSecClusGrpIncRepository extends JpaRepository<CFSecJpaSecClusGrpInc, CFSecJpaSecClusGrpIncPKey> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecClusGrpId
	 *		@param requiredInclName
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecClusGrpInc r where r.pkey.requiredContainerGroup.requiredSecClusGrpId = :secClusGrpId and r.pkey.requiredParentSubGroup.requiredName = :inclName")
	CFSecJpaSecClusGrpInc get(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId,
		@Param("inclName") String requiredInclName);

	/**
	 *	CFSecSecClusGrpIncPKey based read method for object-based access.
	 *
	 *		@param key The key of the entity to be read.
	 *
	 *		@return The entity read, usually from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecClusGrpInc get(ICFSecSecClusGrpIncPKey key) {
		return( get(key.getRequiredSecClusGrpId(), key.getRequiredInclName()));
	}

	// CFSecJpaSecClusGrpInc specified index readers

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecClusGrpIncByClusGrpIdxKey as arguments.
	 *
	 *		@param requiredSecClusGrpId
	 *
	 *		@return List&lt;CFSecJpaSecClusGrpInc&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecClusGrpInc r where r.pkey.requiredContainerGroup.requiredSecClusGrpId = :secClusGrpId")
	List<CFSecJpaSecClusGrpInc> findByClusGrpIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId);

	/**
	 *	CFSecSecClusGrpIncByClusGrpIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecClusGrpIncByClusGrpIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecClusGrpInc> findByClusGrpIdx(ICFSecSecClusGrpIncByClusGrpIdxKey key) {
		return( findByClusGrpIdx(key.getRequiredSecClusGrpId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecClusGrpIncByNameIdxKey as arguments.
	 *
	 *		@param requiredInclName
	 *
	 *		@return List&lt;CFSecJpaSecClusGrpInc&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecClusGrpInc r where r.pkey.requiredParentSubGroup.requiredName = :inclName")
	List<CFSecJpaSecClusGrpInc> findByNameIdx(@Param("inclName") String requiredInclName);

	/**
	 *	CFSecSecClusGrpIncByNameIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecClusGrpIncByNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecClusGrpInc> findByNameIdx(ICFSecSecClusGrpIncByNameIdxKey key) {
		return( findByNameIdx(key.getRequiredInclName()));
	}

	// CFSecJpaSecClusGrpInc specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecClusGrpId
	 *		@param requiredInclName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecClusGrpInc r where r.pkey.requiredContainerGroup.requiredSecClusGrpId = :secClusGrpId and r.pkey.requiredParentSubGroup.requiredName = :inclName")
	CFSecJpaSecClusGrpInc lockByIdIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId,
		@Param("inclName") String requiredInclName);

	/**
	 *	CFSecSecClusGrpIncByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecClusGrpInc lockByIdIdx(ICFSecSecClusGrpIncPKey key) {
		return( lockByIdIdx(key.getRequiredSecClusGrpId(), key.getRequiredInclName()));
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
	@Query("select r from CFSecJpaSecClusGrpInc r where r.pkey.requiredContainerGroup.requiredSecClusGrpId = :secClusGrpId")
	List<CFSecJpaSecClusGrpInc> lockByClusGrpIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId);

	/**
	 *	CFSecSecClusGrpIncByClusGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecClusGrpInc> lockByClusGrpIdx(ICFSecSecClusGrpIncByClusGrpIdxKey key) {
		return( lockByClusGrpIdx(key.getRequiredSecClusGrpId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredInclName
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecClusGrpInc r where r.pkey.requiredParentSubGroup.requiredName = :inclName")
	List<CFSecJpaSecClusGrpInc> lockByNameIdx(@Param("inclName") String requiredInclName);

	/**
	 *	CFSecSecClusGrpIncByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecClusGrpInc> lockByNameIdx(ICFSecSecClusGrpIncByNameIdxKey key) {
		return( lockByNameIdx(key.getRequiredInclName()));
	}

	// CFSecJpaSecClusGrpInc specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecClusGrpId
	 *		@param requiredInclName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecClusGrpInc r where r.pkey.requiredContainerGroup.requiredSecClusGrpId = :secClusGrpId and r.pkey.requiredParentSubGroup.requiredName = :inclName")
	void deleteByIdIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId,
		@Param("inclName") String requiredInclName);

	/**
	 *	CFSecSecClusGrpIncByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecClusGrpIncByIdIdxKey of the entity to be locked.
	 */
	default void deleteByIdIdx(ICFSecSecClusGrpIncPKey key) {
		deleteByIdIdx(key.getRequiredSecClusGrpId(), key.getRequiredInclName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecClusGrpId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecClusGrpInc r where r.pkey.requiredContainerGroup.requiredSecClusGrpId = :secClusGrpId")
	void deleteByClusGrpIdx(@Param("secClusGrpId") CFLibDbKeyHash256 requiredSecClusGrpId);

	/**
	 *	CFSecSecClusGrpIncByClusGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecClusGrpIncByClusGrpIdxKey of the entity to be locked.
	 */
	default void deleteByClusGrpIdx(ICFSecSecClusGrpIncByClusGrpIdxKey key) {
		deleteByClusGrpIdx(key.getRequiredSecClusGrpId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredInclName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecClusGrpInc r where r.pkey.requiredParentSubGroup.requiredName = :inclName")
	void deleteByNameIdx(@Param("inclName") String requiredInclName);

	/**
	 *	CFSecSecClusGrpIncByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecClusGrpIncByNameIdxKey of the entity to be locked.
	 */
	default void deleteByNameIdx(ICFSecSecClusGrpIncByNameIdxKey key) {
		deleteByNameIdx(key.getRequiredInclName());
	}

}

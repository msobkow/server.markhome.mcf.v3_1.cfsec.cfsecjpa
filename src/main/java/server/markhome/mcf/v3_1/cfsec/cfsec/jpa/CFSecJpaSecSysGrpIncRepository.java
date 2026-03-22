// Description: Java 25 Spring JPA Repository for SecSysGrpInc

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
 *	JpaRepository for the CFSecJpaSecSysGrpInc entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaSecSysGrpIncRepository extends JpaRepository<CFSecJpaSecSysGrpInc, CFSecJpaSecSysGrpIncPKey> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecSysGrpId
	 *		@param requiredIncName
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecSysGrpInc r where r.pkey.requiredContainerGroup.requiredSecSysGrpId = :secSysGrpId and r.pkey.requiredIncName = :incName")
	CFSecJpaSecSysGrpInc get(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId,
		@Param("incName") String requiredIncName);

	/**
	 *	CFSecSecSysGrpIncPKey based read method for object-based access.
	 *
	 *		@param key The key of the entity to be read.
	 *
	 *		@return The entity read, usually from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecSysGrpInc get(ICFSecSecSysGrpIncPKey key) {
		return( get(key.getRequiredSecSysGrpId(), key.getRequiredIncName()));
	}

	// CFSecJpaSecSysGrpInc specified index readers

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecSysGrpIncBySysGrpIdxKey as arguments.
	 *
	 *		@param requiredSecSysGrpId
	 *
	 *		@return List&lt;CFSecJpaSecSysGrpInc&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecSysGrpInc r where r.pkey.requiredContainerGroup.requiredSecSysGrpId = :secSysGrpId")
	List<CFSecJpaSecSysGrpInc> findBySysGrpIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId);

	/**
	 *	CFSecSecSysGrpIncBySysGrpIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecSysGrpIncBySysGrpIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecSysGrpInc> findBySysGrpIdx(ICFSecSecSysGrpIncBySysGrpIdxKey key) {
		return( findBySysGrpIdx(key.getRequiredSecSysGrpId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecSysGrpIncByNameIdxKey as arguments.
	 *
	 *		@param requiredIncName
	 *
	 *		@return List&lt;CFSecJpaSecSysGrpInc&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecSysGrpInc r where r.pkey.requiredIncName = :incName")
	List<CFSecJpaSecSysGrpInc> findByNameIdx(@Param("incName") String requiredIncName);

	/**
	 *	CFSecSecSysGrpIncByNameIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecSysGrpIncByNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecSysGrpInc> findByNameIdx(ICFSecSecSysGrpIncByNameIdxKey key) {
		return( findByNameIdx(key.getRequiredIncName()));
	}

	// CFSecJpaSecSysGrpInc specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 *		@param requiredIncName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecSysGrpInc r where r.pkey.requiredContainerGroup.requiredSecSysGrpId = :secSysGrpId and r.pkey.requiredIncName = :incName")
	CFSecJpaSecSysGrpInc lockByIdIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId,
		@Param("incName") String requiredIncName);

	/**
	 *	CFSecSecSysGrpIncByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecSysGrpInc lockByIdIdx(ICFSecSecSysGrpIncPKey key) {
		return( lockByIdIdx(key.getRequiredSecSysGrpId(), key.getRequiredIncName()));
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
	@Query("select r from CFSecJpaSecSysGrpInc r where r.pkey.requiredContainerGroup.requiredSecSysGrpId = :secSysGrpId")
	List<CFSecJpaSecSysGrpInc> lockBySysGrpIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId);

	/**
	 *	CFSecSecSysGrpIncBySysGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecSysGrpInc> lockBySysGrpIdx(ICFSecSecSysGrpIncBySysGrpIdxKey key) {
		return( lockBySysGrpIdx(key.getRequiredSecSysGrpId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredIncName
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecSysGrpInc r where r.pkey.requiredIncName = :incName")
	List<CFSecJpaSecSysGrpInc> lockByNameIdx(@Param("incName") String requiredIncName);

	/**
	 *	CFSecSecSysGrpIncByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecSysGrpInc> lockByNameIdx(ICFSecSecSysGrpIncByNameIdxKey key) {
		return( lockByNameIdx(key.getRequiredIncName()));
	}

	// CFSecJpaSecSysGrpInc specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 *		@param requiredIncName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecSysGrpInc r where r.pkey.requiredContainerGroup.requiredSecSysGrpId = :secSysGrpId and r.pkey.requiredIncName = :incName")
	void deleteByIdIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId,
		@Param("incName") String requiredIncName);

	/**
	 *	CFSecSecSysGrpIncByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecSysGrpIncByIdIdxKey of the entity to be locked.
	 */
	default void deleteByIdIdx(ICFSecSecSysGrpIncPKey key) {
		deleteByIdIdx(key.getRequiredSecSysGrpId(), key.getRequiredIncName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecSysGrpInc r where r.pkey.requiredContainerGroup.requiredSecSysGrpId = :secSysGrpId")
	void deleteBySysGrpIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId);

	/**
	 *	CFSecSecSysGrpIncBySysGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecSysGrpIncBySysGrpIdxKey of the entity to be locked.
	 */
	default void deleteBySysGrpIdx(ICFSecSecSysGrpIncBySysGrpIdxKey key) {
		deleteBySysGrpIdx(key.getRequiredSecSysGrpId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredIncName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecSysGrpInc r where r.pkey.requiredIncName = :incName")
	void deleteByNameIdx(@Param("incName") String requiredIncName);

	/**
	 *	CFSecSecSysGrpIncByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecSysGrpIncByNameIdxKey of the entity to be locked.
	 */
	default void deleteByNameIdx(ICFSecSecSysGrpIncByNameIdxKey key) {
		deleteByNameIdx(key.getRequiredIncName());
	}

}

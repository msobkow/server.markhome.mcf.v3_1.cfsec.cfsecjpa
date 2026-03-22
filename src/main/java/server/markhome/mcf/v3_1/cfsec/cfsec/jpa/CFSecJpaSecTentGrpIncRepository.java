// Description: Java 25 Spring JPA Repository for SecTentGrpInc

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
 *	JpaRepository for the CFSecJpaSecTentGrpInc entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaSecTentGrpIncRepository extends JpaRepository<CFSecJpaSecTentGrpInc, CFSecJpaSecTentGrpIncPKey> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecTentGrpId
	 *		@param requiredIncName
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecTentGrpInc r where r.pkey.requiredSecTentGrpId = :secTentGrpId and r.pkey.requiredIncName = :incName")
	CFSecJpaSecTentGrpInc get(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId,
		@Param("incName") String requiredIncName);

	/**
	 *	CFSecSecTentGrpIncPKey based read method for object-based access.
	 *
	 *		@param key The key of the entity to be read.
	 *
	 *		@return The entity read, usually from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecTentGrpInc get(ICFSecSecTentGrpIncPKey key) {
		return( get(key.getRequiredSecTentGrpId(), key.getRequiredIncName()));
	}

	// CFSecJpaSecTentGrpInc specified index readers

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecTentGrpIncByTentGrpIdxKey as arguments.
	 *
	 *		@param requiredSecTentGrpId
	 *
	 *		@return List&lt;CFSecJpaSecTentGrpInc&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecTentGrpInc r where r.pkey.requiredSecTentGrpId = :secTentGrpId")
	List<CFSecJpaSecTentGrpInc> findByTentGrpIdx(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId);

	/**
	 *	CFSecSecTentGrpIncByTentGrpIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecTentGrpIncByTentGrpIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecTentGrpInc> findByTentGrpIdx(ICFSecSecTentGrpIncByTentGrpIdxKey key) {
		return( findByTentGrpIdx(key.getRequiredSecTentGrpId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecTentGrpIncByNameIdxKey as arguments.
	 *
	 *		@param requiredIncName
	 *
	 *		@return List&lt;CFSecJpaSecTentGrpInc&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecTentGrpInc r where r.pkey.requiredIncName = :incName")
	List<CFSecJpaSecTentGrpInc> findByNameIdx(@Param("incName") String requiredIncName);

	/**
	 *	CFSecSecTentGrpIncByNameIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecTentGrpIncByNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecTentGrpInc> findByNameIdx(ICFSecSecTentGrpIncByNameIdxKey key) {
		return( findByNameIdx(key.getRequiredIncName()));
	}

	// CFSecJpaSecTentGrpInc specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecTentGrpId
	 *		@param requiredIncName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecTentGrpInc r where r.pkey.requiredSecTentGrpId = :secTentGrpId and r.pkey.requiredIncName = :incName")
	CFSecJpaSecTentGrpInc lockByIdIdx(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId,
		@Param("incName") String requiredIncName);

	/**
	 *	CFSecSecTentGrpIncByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecTentGrpInc lockByIdIdx(ICFSecSecTentGrpIncPKey key) {
		return( lockByIdIdx(key.getRequiredSecTentGrpId(), key.getRequiredIncName()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecTentGrpId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecTentGrpInc r where r.pkey.requiredSecTentGrpId = :secTentGrpId")
	List<CFSecJpaSecTentGrpInc> lockByTentGrpIdx(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId);

	/**
	 *	CFSecSecTentGrpIncByTentGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecTentGrpInc> lockByTentGrpIdx(ICFSecSecTentGrpIncByTentGrpIdxKey key) {
		return( lockByTentGrpIdx(key.getRequiredSecTentGrpId()));
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
	@Query("select r from CFSecJpaSecTentGrpInc r where r.pkey.requiredIncName = :incName")
	List<CFSecJpaSecTentGrpInc> lockByNameIdx(@Param("incName") String requiredIncName);

	/**
	 *	CFSecSecTentGrpIncByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecTentGrpInc> lockByNameIdx(ICFSecSecTentGrpIncByNameIdxKey key) {
		return( lockByNameIdx(key.getRequiredIncName()));
	}

	// CFSecJpaSecTentGrpInc specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecTentGrpId
	 *		@param requiredIncName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecTentGrpInc r where r.pkey.requiredSecTentGrpId = :secTentGrpId and r.pkey.requiredIncName = :incName")
	void deleteByIdIdx(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId,
		@Param("incName") String requiredIncName);

	/**
	 *	CFSecSecTentGrpIncByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecTentGrpIncByIdIdxKey of the entity to be locked.
	 */
	default void deleteByIdIdx(ICFSecSecTentGrpIncPKey key) {
		deleteByIdIdx(key.getRequiredSecTentGrpId(), key.getRequiredIncName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecTentGrpId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecTentGrpInc r where r.pkey.requiredSecTentGrpId = :secTentGrpId")
	void deleteByTentGrpIdx(@Param("secTentGrpId") CFLibDbKeyHash256 requiredSecTentGrpId);

	/**
	 *	CFSecSecTentGrpIncByTentGrpIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecTentGrpIncByTentGrpIdxKey of the entity to be locked.
	 */
	default void deleteByTentGrpIdx(ICFSecSecTentGrpIncByTentGrpIdxKey key) {
		deleteByTentGrpIdx(key.getRequiredSecTentGrpId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredIncName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecTentGrpInc r where r.pkey.requiredIncName = :incName")
	void deleteByNameIdx(@Param("incName") String requiredIncName);

	/**
	 *	CFSecSecTentGrpIncByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecTentGrpIncByNameIdxKey of the entity to be locked.
	 */
	default void deleteByNameIdx(ICFSecSecTentGrpIncByNameIdxKey key) {
		deleteByNameIdx(key.getRequiredIncName());
	}

}

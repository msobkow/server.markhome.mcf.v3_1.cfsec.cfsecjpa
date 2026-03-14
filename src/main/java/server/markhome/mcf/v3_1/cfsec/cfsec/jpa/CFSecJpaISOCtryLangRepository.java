// Description: Java 25 Spring JPA Repository for ISOCtryLang

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
 *	JpaRepository for the CFSecJpaISOCtryLang entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaISOCtryLangRepository extends JpaRepository<CFSecJpaISOCtryLang, CFSecJpaISOCtryLangPKey> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredISOCtryId
	 *		@param requiredISOLangId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaISOCtryLang r where r.pkey.requiredContainerCtry.requiredISOCtryId = :iSOCtryId and r.pkey.requiredParentLang.requiredISOLangId = :iSOLangId")
	CFSecJpaISOCtryLang get(@Param("iSOCtryId") short requiredISOCtryId,
		@Param("iSOLangId") short requiredISOLangId);

	/**
	 *	CFSecISOCtryLangPKey based read method for object-based access.
	 *
	 *		@param key The key of the entity to be read.
	 *
	 *		@return The entity read, usually from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaISOCtryLang get(ICFSecISOCtryLangPKey key) {
		return( get(key.getRequiredISOCtryId(), key.getRequiredISOLangId()));
	}

	// CFSecJpaISOCtryLang specified index readers

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecISOCtryLangByCtryIdxKey as arguments.
	 *
	 *		@param requiredISOCtryId
	 *
	 *		@return List&lt;CFSecJpaISOCtryLang&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaISOCtryLang r where r.pkey.requiredContainerCtry.requiredISOCtryId = :iSOCtryId")
	List<CFSecJpaISOCtryLang> findByCtryIdx(@Param("iSOCtryId") short requiredISOCtryId);

	/**
	 *	CFSecISOCtryLangByCtryIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecISOCtryLangByCtryIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaISOCtryLang> findByCtryIdx(ICFSecISOCtryLangByCtryIdxKey key) {
		return( findByCtryIdx(key.getRequiredISOCtryId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecISOCtryLangByLangIdxKey as arguments.
	 *
	 *		@param requiredISOLangId
	 *
	 *		@return List&lt;CFSecJpaISOCtryLang&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaISOCtryLang r where r.pkey.requiredParentLang.requiredISOLangId = :iSOLangId")
	List<CFSecJpaISOCtryLang> findByLangIdx(@Param("iSOLangId") short requiredISOLangId);

	/**
	 *	CFSecISOCtryLangByLangIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecISOCtryLangByLangIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaISOCtryLang> findByLangIdx(ICFSecISOCtryLangByLangIdxKey key) {
		return( findByLangIdx(key.getRequiredISOLangId()));
	}

	// CFSecJpaISOCtryLang specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCtryId
	 *		@param requiredISOLangId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaISOCtryLang r where r.pkey.requiredContainerCtry.requiredISOCtryId = :iSOCtryId and r.pkey.requiredParentLang.requiredISOLangId = :iSOLangId")
	CFSecJpaISOCtryLang lockByIdIdx(@Param("iSOCtryId") short requiredISOCtryId,
		@Param("iSOLangId") short requiredISOLangId);

	/**
	 *	CFSecISOCtryLangByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaISOCtryLang lockByIdIdx(ICFSecISOCtryLangPKey key) {
		return( lockByIdIdx(key.getRequiredISOCtryId(), key.getRequiredISOLangId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCtryId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaISOCtryLang r where r.pkey.requiredContainerCtry.requiredISOCtryId = :iSOCtryId")
	List<CFSecJpaISOCtryLang> lockByCtryIdx(@Param("iSOCtryId") short requiredISOCtryId);

	/**
	 *	CFSecISOCtryLangByCtryIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaISOCtryLang> lockByCtryIdx(ICFSecISOCtryLangByCtryIdxKey key) {
		return( lockByCtryIdx(key.getRequiredISOCtryId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOLangId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaISOCtryLang r where r.pkey.requiredParentLang.requiredISOLangId = :iSOLangId")
	List<CFSecJpaISOCtryLang> lockByLangIdx(@Param("iSOLangId") short requiredISOLangId);

	/**
	 *	CFSecISOCtryLangByLangIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaISOCtryLang> lockByLangIdx(ICFSecISOCtryLangByLangIdxKey key) {
		return( lockByLangIdx(key.getRequiredISOLangId()));
	}

	// CFSecJpaISOCtryLang specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCtryId
	 *		@param requiredISOLangId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaISOCtryLang r where r.pkey.requiredContainerCtry.requiredISOCtryId = :iSOCtryId and r.pkey.requiredParentLang.requiredISOLangId = :iSOLangId")
	void deleteByIdIdx(@Param("iSOCtryId") short requiredISOCtryId,
		@Param("iSOLangId") short requiredISOLangId);

	/**
	 *	CFSecISOCtryLangByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecISOCtryLangByIdIdxKey of the entity to be locked.
	 */
	default void deleteByIdIdx(ICFSecISOCtryLangPKey key) {
		deleteByIdIdx(key.getRequiredISOCtryId(), key.getRequiredISOLangId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCtryId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaISOCtryLang r where r.pkey.requiredContainerCtry.requiredISOCtryId = :iSOCtryId")
	void deleteByCtryIdx(@Param("iSOCtryId") short requiredISOCtryId);

	/**
	 *	CFSecISOCtryLangByCtryIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecISOCtryLangByCtryIdxKey of the entity to be locked.
	 */
	default void deleteByCtryIdx(ICFSecISOCtryLangByCtryIdxKey key) {
		deleteByCtryIdx(key.getRequiredISOCtryId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOLangId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaISOCtryLang r where r.pkey.requiredParentLang.requiredISOLangId = :iSOLangId")
	void deleteByLangIdx(@Param("iSOLangId") short requiredISOLangId);

	/**
	 *	CFSecISOCtryLangByLangIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecISOCtryLangByLangIdxKey of the entity to be locked.
	 */
	default void deleteByLangIdx(ICFSecISOCtryLangByLangIdxKey key) {
		deleteByLangIdx(key.getRequiredISOLangId());
	}

}

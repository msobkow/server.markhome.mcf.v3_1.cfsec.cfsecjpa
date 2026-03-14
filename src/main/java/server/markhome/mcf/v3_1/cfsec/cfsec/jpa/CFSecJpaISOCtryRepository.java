// Description: Java 25 Spring JPA Repository for ISOCtry

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
 *	JpaRepository for the CFSecJpaISOCtry entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaISOCtryRepository extends JpaRepository<CFSecJpaISOCtry, Short> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredISOCtryId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaISOCtry r where r.requiredISOCtryId = :iSOCtryId")
	CFSecJpaISOCtry get(@Param("iSOCtryId") short requiredISOCtryId);

	// CFSecJpaISOCtry specified index readers

	/**
	 *	Read an entity using the columns of the CFSecISOCtryByISOCodeIdxKey as arguments.
	 *
	 *		@param requiredISOCode
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaISOCtry r where r.requiredISOCode = :iSOCode")
	CFSecJpaISOCtry findByISOCodeIdx(@Param("iSOCode") String requiredISOCode);

	/**
	 *	CFSecISOCtryByISOCodeIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecISOCtryByISOCodeIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaISOCtry findByISOCodeIdx(ICFSecISOCtryByISOCodeIdxKey key) {
		return( findByISOCodeIdx(key.getRequiredISOCode()));
	}

	/**
	 *	Read an entity using the columns of the CFSecISOCtryByNameIdxKey as arguments.
	 *
	 *		@param requiredName
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaISOCtry r where r.requiredName = :name")
	CFSecJpaISOCtry findByNameIdx(@Param("name") String requiredName);

	/**
	 *	CFSecISOCtryByNameIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecISOCtryByNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaISOCtry findByNameIdx(ICFSecISOCtryByNameIdxKey key) {
		return( findByNameIdx(key.getRequiredName()));
	}

	// CFSecJpaISOCtry specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCtryId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaISOCtry r where r.requiredISOCtryId = :iSOCtryId")
	CFSecJpaISOCtry lockByIdIdx(@Param("iSOCtryId") short requiredISOCtryId);

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCode
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaISOCtry r where r.requiredISOCode = :iSOCode")
	CFSecJpaISOCtry lockByISOCodeIdx(@Param("iSOCode") String requiredISOCode);

	/**
	 *	CFSecISOCtryByISOCodeIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaISOCtry lockByISOCodeIdx(ICFSecISOCtryByISOCodeIdxKey key) {
		return( lockByISOCodeIdx(key.getRequiredISOCode()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaISOCtry r where r.requiredName = :name")
	CFSecJpaISOCtry lockByNameIdx(@Param("name") String requiredName);

	/**
	 *	CFSecISOCtryByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaISOCtry lockByNameIdx(ICFSecISOCtryByNameIdxKey key) {
		return( lockByNameIdx(key.getRequiredName()));
	}

	// CFSecJpaISOCtry specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCtryId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaISOCtry r where r.requiredISOCtryId = :iSOCtryId")
	void deleteByIdIdx(@Param("iSOCtryId") short requiredISOCtryId);

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCode
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaISOCtry r where r.requiredISOCode = :iSOCode")
	void deleteByISOCodeIdx(@Param("iSOCode") String requiredISOCode);

	/**
	 *	CFSecISOCtryByISOCodeIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecISOCtryByISOCodeIdxKey of the entity to be locked.
	 */
	default void deleteByISOCodeIdx(ICFSecISOCtryByISOCodeIdxKey key) {
		deleteByISOCodeIdx(key.getRequiredISOCode());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaISOCtry r where r.requiredName = :name")
	void deleteByNameIdx(@Param("name") String requiredName);

	/**
	 *	CFSecISOCtryByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecISOCtryByNameIdxKey of the entity to be locked.
	 */
	default void deleteByNameIdx(ICFSecISOCtryByNameIdxKey key) {
		deleteByNameIdx(key.getRequiredName());
	}

}

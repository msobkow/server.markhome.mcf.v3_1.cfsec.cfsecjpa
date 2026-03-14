// Description: Java 25 Spring JPA Repository for ISOTZone

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
 *	JpaRepository for the CFSecJpaISOTZone entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaISOTZoneRepository extends JpaRepository<CFSecJpaISOTZone, Short> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredISOTZoneId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaISOTZone r where r.requiredISOTZoneId = :iSOTZoneId")
	CFSecJpaISOTZone get(@Param("iSOTZoneId") short requiredISOTZoneId);

	// CFSecJpaISOTZone specified index readers

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecISOTZoneByOffsetIdxKey as arguments.
	 *
	 *		@param requiredTZHourOffset
	 *		@param requiredTZMinOffset
	 *
	 *		@return List&lt;CFSecJpaISOTZone&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaISOTZone r where r.requiredTZHourOffset = :tZHourOffset and r.requiredTZMinOffset = :tZMinOffset")
	List<CFSecJpaISOTZone> findByOffsetIdx(@Param("tZHourOffset") short requiredTZHourOffset,
		@Param("tZMinOffset") short requiredTZMinOffset);

	/**
	 *	CFSecISOTZoneByOffsetIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecISOTZoneByOffsetIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaISOTZone> findByOffsetIdx(ICFSecISOTZoneByOffsetIdxKey key) {
		return( findByOffsetIdx(key.getRequiredTZHourOffset(), key.getRequiredTZMinOffset()));
	}

	/**
	 *	Read an entity using the columns of the CFSecISOTZoneByUTZNameIdxKey as arguments.
	 *
	 *		@param requiredTZName
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaISOTZone r where r.requiredTZName = :tZName")
	CFSecJpaISOTZone findByUTZNameIdx(@Param("tZName") String requiredTZName);

	/**
	 *	CFSecISOTZoneByUTZNameIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecISOTZoneByUTZNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaISOTZone findByUTZNameIdx(ICFSecISOTZoneByUTZNameIdxKey key) {
		return( findByUTZNameIdx(key.getRequiredTZName()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecISOTZoneByIso8601IdxKey as arguments.
	 *
	 *		@param requiredIso8601
	 *
	 *		@return List&lt;CFSecJpaISOTZone&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaISOTZone r where r.requiredIso8601 = :iso8601")
	List<CFSecJpaISOTZone> findByIso8601Idx(@Param("iso8601") String requiredIso8601);

	/**
	 *	CFSecISOTZoneByIso8601IdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecISOTZoneByIso8601IdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaISOTZone> findByIso8601Idx(ICFSecISOTZoneByIso8601IdxKey key) {
		return( findByIso8601Idx(key.getRequiredIso8601()));
	}

	// CFSecJpaISOTZone specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOTZoneId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaISOTZone r where r.requiredISOTZoneId = :iSOTZoneId")
	CFSecJpaISOTZone lockByIdIdx(@Param("iSOTZoneId") short requiredISOTZoneId);

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTZHourOffset
	 *		@param requiredTZMinOffset
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaISOTZone r where r.requiredTZHourOffset = :tZHourOffset and r.requiredTZMinOffset = :tZMinOffset")
	List<CFSecJpaISOTZone> lockByOffsetIdx(@Param("tZHourOffset") short requiredTZHourOffset,
		@Param("tZMinOffset") short requiredTZMinOffset);

	/**
	 *	CFSecISOTZoneByOffsetIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaISOTZone> lockByOffsetIdx(ICFSecISOTZoneByOffsetIdxKey key) {
		return( lockByOffsetIdx(key.getRequiredTZHourOffset(), key.getRequiredTZMinOffset()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTZName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaISOTZone r where r.requiredTZName = :tZName")
	CFSecJpaISOTZone lockByUTZNameIdx(@Param("tZName") String requiredTZName);

	/**
	 *	CFSecISOTZoneByUTZNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaISOTZone lockByUTZNameIdx(ICFSecISOTZoneByUTZNameIdxKey key) {
		return( lockByUTZNameIdx(key.getRequiredTZName()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredIso8601
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaISOTZone r where r.requiredIso8601 = :iso8601")
	List<CFSecJpaISOTZone> lockByIso8601Idx(@Param("iso8601") String requiredIso8601);

	/**
	 *	CFSecISOTZoneByIso8601IdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaISOTZone> lockByIso8601Idx(ICFSecISOTZoneByIso8601IdxKey key) {
		return( lockByIso8601Idx(key.getRequiredIso8601()));
	}

	// CFSecJpaISOTZone specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOTZoneId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaISOTZone r where r.requiredISOTZoneId = :iSOTZoneId")
	void deleteByIdIdx(@Param("iSOTZoneId") short requiredISOTZoneId);

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTZHourOffset
	 *		@param requiredTZMinOffset
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaISOTZone r where r.requiredTZHourOffset = :tZHourOffset and r.requiredTZMinOffset = :tZMinOffset")
	void deleteByOffsetIdx(@Param("tZHourOffset") short requiredTZHourOffset,
		@Param("tZMinOffset") short requiredTZMinOffset);

	/**
	 *	CFSecISOTZoneByOffsetIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecISOTZoneByOffsetIdxKey of the entity to be locked.
	 */
	default void deleteByOffsetIdx(ICFSecISOTZoneByOffsetIdxKey key) {
		deleteByOffsetIdx(key.getRequiredTZHourOffset(), key.getRequiredTZMinOffset());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTZName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaISOTZone r where r.requiredTZName = :tZName")
	void deleteByUTZNameIdx(@Param("tZName") String requiredTZName);

	/**
	 *	CFSecISOTZoneByUTZNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecISOTZoneByUTZNameIdxKey of the entity to be locked.
	 */
	default void deleteByUTZNameIdx(ICFSecISOTZoneByUTZNameIdxKey key) {
		deleteByUTZNameIdx(key.getRequiredTZName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredIso8601
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaISOTZone r where r.requiredIso8601 = :iso8601")
	void deleteByIso8601Idx(@Param("iso8601") String requiredIso8601);

	/**
	 *	CFSecISOTZoneByIso8601IdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecISOTZoneByIso8601IdxKey of the entity to be locked.
	 */
	default void deleteByIso8601Idx(ICFSecISOTZoneByIso8601IdxKey key) {
		deleteByIso8601Idx(key.getRequiredIso8601());
	}

}

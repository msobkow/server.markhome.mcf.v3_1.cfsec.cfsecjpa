// Description: Java 25 Spring JPA Repository for ISOCtryCcy

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
 *	JpaRepository for the CFSecJpaISOCtryCcy entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaISOCtryCcyRepository extends JpaRepository<CFSecJpaISOCtryCcy, CFSecJpaISOCtryCcyPKey> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredISOCtryId
	 *		@param requiredISOCcyId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaISOCtryCcy r where r.pkey.requiredContainerCtry.requiredISOCtryId = :iSOCtryId and r.pkey.requiredParentCcy.requiredISOCcyId = :iSOCcyId")
	CFSecJpaISOCtryCcy get(@Param("iSOCtryId") short requiredISOCtryId,
		@Param("iSOCcyId") short requiredISOCcyId);

	/**
	 *	CFSecISOCtryCcyPKey based read method for object-based access.
	 *
	 *		@param key The key of the entity to be read.
	 *
	 *		@return The entity read, usually from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaISOCtryCcy get(ICFSecISOCtryCcyPKey key) {
		return( get(key.getRequiredISOCtryId(), key.getRequiredISOCcyId()));
	}

	// CFSecJpaISOCtryCcy specified index readers

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecISOCtryCcyByCtryIdxKey as arguments.
	 *
	 *		@param requiredISOCtryId
	 *
	 *		@return List&lt;CFSecJpaISOCtryCcy&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaISOCtryCcy r where r.pkey.requiredContainerCtry.requiredISOCtryId = :iSOCtryId")
	List<CFSecJpaISOCtryCcy> findByCtryIdx(@Param("iSOCtryId") short requiredISOCtryId);

	/**
	 *	CFSecISOCtryCcyByCtryIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecISOCtryCcyByCtryIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaISOCtryCcy> findByCtryIdx(ICFSecISOCtryCcyByCtryIdxKey key) {
		return( findByCtryIdx(key.getRequiredISOCtryId()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecISOCtryCcyByCcyIdxKey as arguments.
	 *
	 *		@param requiredISOCcyId
	 *
	 *		@return List&lt;CFSecJpaISOCtryCcy&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaISOCtryCcy r where r.pkey.requiredParentCcy.requiredISOCcyId = :iSOCcyId")
	List<CFSecJpaISOCtryCcy> findByCcyIdx(@Param("iSOCcyId") short requiredISOCcyId);

	/**
	 *	CFSecISOCtryCcyByCcyIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecISOCtryCcyByCcyIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaISOCtryCcy> findByCcyIdx(ICFSecISOCtryCcyByCcyIdxKey key) {
		return( findByCcyIdx(key.getRequiredISOCcyId()));
	}

	// CFSecJpaISOCtryCcy specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCtryId
	 *		@param requiredISOCcyId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaISOCtryCcy r where r.pkey.requiredContainerCtry.requiredISOCtryId = :iSOCtryId and r.pkey.requiredParentCcy.requiredISOCcyId = :iSOCcyId")
	CFSecJpaISOCtryCcy lockByIdIdx(@Param("iSOCtryId") short requiredISOCtryId,
		@Param("iSOCcyId") short requiredISOCcyId);

	/**
	 *	CFSecISOCtryCcyByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaISOCtryCcy lockByIdIdx(ICFSecISOCtryCcyPKey key) {
		return( lockByIdIdx(key.getRequiredISOCtryId(), key.getRequiredISOCcyId()));
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
	@Query("select r from CFSecJpaISOCtryCcy r where r.pkey.requiredContainerCtry.requiredISOCtryId = :iSOCtryId")
	List<CFSecJpaISOCtryCcy> lockByCtryIdx(@Param("iSOCtryId") short requiredISOCtryId);

	/**
	 *	CFSecISOCtryCcyByCtryIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaISOCtryCcy> lockByCtryIdx(ICFSecISOCtryCcyByCtryIdxKey key) {
		return( lockByCtryIdx(key.getRequiredISOCtryId()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCcyId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaISOCtryCcy r where r.pkey.requiredParentCcy.requiredISOCcyId = :iSOCcyId")
	List<CFSecJpaISOCtryCcy> lockByCcyIdx(@Param("iSOCcyId") short requiredISOCcyId);

	/**
	 *	CFSecISOCtryCcyByCcyIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaISOCtryCcy> lockByCcyIdx(ICFSecISOCtryCcyByCcyIdxKey key) {
		return( lockByCcyIdx(key.getRequiredISOCcyId()));
	}

	// CFSecJpaISOCtryCcy specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCtryId
	 *		@param requiredISOCcyId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaISOCtryCcy r where r.pkey.requiredContainerCtry.requiredISOCtryId = :iSOCtryId and r.pkey.requiredParentCcy.requiredISOCcyId = :iSOCcyId")
	void deleteByIdIdx(@Param("iSOCtryId") short requiredISOCtryId,
		@Param("iSOCcyId") short requiredISOCcyId);

	/**
	 *	CFSecISOCtryCcyByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecISOCtryCcyByIdIdxKey of the entity to be locked.
	 */
	default void deleteByIdIdx(ICFSecISOCtryCcyPKey key) {
		deleteByIdIdx(key.getRequiredISOCtryId(), key.getRequiredISOCcyId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCtryId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaISOCtryCcy r where r.pkey.requiredContainerCtry.requiredISOCtryId = :iSOCtryId")
	void deleteByCtryIdx(@Param("iSOCtryId") short requiredISOCtryId);

	/**
	 *	CFSecISOCtryCcyByCtryIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecISOCtryCcyByCtryIdxKey of the entity to be locked.
	 */
	default void deleteByCtryIdx(ICFSecISOCtryCcyByCtryIdxKey key) {
		deleteByCtryIdx(key.getRequiredISOCtryId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCcyId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaISOCtryCcy r where r.pkey.requiredParentCcy.requiredISOCcyId = :iSOCcyId")
	void deleteByCcyIdx(@Param("iSOCcyId") short requiredISOCcyId);

	/**
	 *	CFSecISOCtryCcyByCcyIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecISOCtryCcyByCcyIdxKey of the entity to be locked.
	 */
	default void deleteByCcyIdx(ICFSecISOCtryCcyByCcyIdxKey key) {
		deleteByCcyIdx(key.getRequiredISOCcyId());
	}

}

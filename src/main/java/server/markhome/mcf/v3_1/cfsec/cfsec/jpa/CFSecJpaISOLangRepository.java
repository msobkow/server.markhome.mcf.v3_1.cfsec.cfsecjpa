// Description: Java 25 Spring JPA Repository for ISOLang

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
 *	JpaRepository for the CFSecJpaISOLang entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaISOLangRepository extends JpaRepository<CFSecJpaISOLang, Short> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredISOLangId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaISOLang r where r.requiredISOLangId = :iSOLangId")
	CFSecJpaISOLang get(@Param("iSOLangId") short requiredISOLangId);

	// CFSecJpaISOLang specified index readers

	/**
	 *	Read an entity using the columns of the CFSecISOLangByCode3IdxKey as arguments.
	 *
	 *		@param requiredISO6392Code
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaISOLang r where r.requiredISO6392Code = :iSO6392Code")
	CFSecJpaISOLang findByCode3Idx(@Param("iSO6392Code") String requiredISO6392Code);

	/**
	 *	CFSecISOLangByCode3IdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecISOLangByCode3IdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaISOLang findByCode3Idx(ICFSecISOLangByCode3IdxKey key) {
		return( findByCode3Idx(key.getRequiredISO6392Code()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecISOLangByCode2IdxKey as arguments.
	 *
	 *		@param optionalISO6391Code
	 *
	 *		@return List&lt;CFSecJpaISOLang&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaISOLang r where r.optionalISO6391Code = :iSO6391Code")
	List<CFSecJpaISOLang> findByCode2Idx(@Param("iSO6391Code") String optionalISO6391Code);

	/**
	 *	CFSecISOLangByCode2IdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecISOLangByCode2IdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaISOLang> findByCode2Idx(ICFSecISOLangByCode2IdxKey key) {
		return( findByCode2Idx(key.getOptionalISO6391Code()));
	}

	// CFSecJpaISOLang specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOLangId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaISOLang r where r.requiredISOLangId = :iSOLangId")
	CFSecJpaISOLang lockByIdIdx(@Param("iSOLangId") short requiredISOLangId);

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISO6392Code
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaISOLang r where r.requiredISO6392Code = :iSO6392Code")
	CFSecJpaISOLang lockByCode3Idx(@Param("iSO6392Code") String requiredISO6392Code);

	/**
	 *	CFSecISOLangByCode3IdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaISOLang lockByCode3Idx(ICFSecISOLangByCode3IdxKey key) {
		return( lockByCode3Idx(key.getRequiredISO6392Code()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param optionalISO6391Code
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaISOLang r where r.optionalISO6391Code = :iSO6391Code")
	List<CFSecJpaISOLang> lockByCode2Idx(@Param("iSO6391Code") String optionalISO6391Code);

	/**
	 *	CFSecISOLangByCode2IdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaISOLang> lockByCode2Idx(ICFSecISOLangByCode2IdxKey key) {
		return( lockByCode2Idx(key.getOptionalISO6391Code()));
	}

	// CFSecJpaISOLang specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOLangId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaISOLang r where r.requiredISOLangId = :iSOLangId")
	void deleteByIdIdx(@Param("iSOLangId") short requiredISOLangId);

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISO6392Code
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaISOLang r where r.requiredISO6392Code = :iSO6392Code")
	void deleteByCode3Idx(@Param("iSO6392Code") String requiredISO6392Code);

	/**
	 *	CFSecISOLangByCode3IdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecISOLangByCode3IdxKey of the entity to be locked.
	 */
	default void deleteByCode3Idx(ICFSecISOLangByCode3IdxKey key) {
		deleteByCode3Idx(key.getRequiredISO6392Code());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param optionalISO6391Code
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaISOLang r where r.optionalISO6391Code = :iSO6391Code")
	void deleteByCode2Idx(@Param("iSO6391Code") String optionalISO6391Code);

	/**
	 *	CFSecISOLangByCode2IdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecISOLangByCode2IdxKey of the entity to be locked.
	 */
	default void deleteByCode2Idx(ICFSecISOLangByCode2IdxKey key) {
		deleteByCode2Idx(key.getOptionalISO6391Code());
	}

}

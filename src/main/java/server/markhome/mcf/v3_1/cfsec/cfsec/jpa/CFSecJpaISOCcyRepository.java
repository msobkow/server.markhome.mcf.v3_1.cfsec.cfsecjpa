// Description: Java 25 Spring JPA Repository for ISOCcy

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
 *	JpaRepository for the CFSecJpaISOCcy entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaISOCcyRepository extends JpaRepository<CFSecJpaISOCcy, Short> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredISOCcyId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaISOCcy r where r.requiredISOCcyId = :iSOCcyId")
	CFSecJpaISOCcy get(@Param("iSOCcyId") short requiredISOCcyId);

	// CFSecJpaISOCcy specified index readers

	/**
	 *	Read an entity using the columns of the CFSecISOCcyByCcyCdIdxKey as arguments.
	 *
	 *		@param requiredISOCode
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaISOCcy r where r.requiredISOCode = :iSOCode")
	CFSecJpaISOCcy findByCcyCdIdx(@Param("iSOCode") String requiredISOCode);

	/**
	 *	CFSecISOCcyByCcyCdIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecISOCcyByCcyCdIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaISOCcy findByCcyCdIdx(ICFSecISOCcyByCcyCdIdxKey key) {
		return( findByCcyCdIdx(key.getRequiredISOCode()));
	}

	/**
	 *	Read an entity using the columns of the CFSecISOCcyByCcyNmIdxKey as arguments.
	 *
	 *		@param requiredName
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaISOCcy r where r.requiredName = :name")
	CFSecJpaISOCcy findByCcyNmIdx(@Param("name") String requiredName);

	/**
	 *	CFSecISOCcyByCcyNmIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecISOCcyByCcyNmIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaISOCcy findByCcyNmIdx(ICFSecISOCcyByCcyNmIdxKey key) {
		return( findByCcyNmIdx(key.getRequiredName()));
	}

	// CFSecJpaISOCcy specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCcyId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaISOCcy r where r.requiredISOCcyId = :iSOCcyId")
	CFSecJpaISOCcy lockByIdIdx(@Param("iSOCcyId") short requiredISOCcyId);

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCode
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaISOCcy r where r.requiredISOCode = :iSOCode")
	CFSecJpaISOCcy lockByCcyCdIdx(@Param("iSOCode") String requiredISOCode);

	/**
	 *	CFSecISOCcyByCcyCdIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaISOCcy lockByCcyCdIdx(ICFSecISOCcyByCcyCdIdxKey key) {
		return( lockByCcyCdIdx(key.getRequiredISOCode()));
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
	@Query("select r from CFSecJpaISOCcy r where r.requiredName = :name")
	CFSecJpaISOCcy lockByCcyNmIdx(@Param("name") String requiredName);

	/**
	 *	CFSecISOCcyByCcyNmIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaISOCcy lockByCcyNmIdx(ICFSecISOCcyByCcyNmIdxKey key) {
		return( lockByCcyNmIdx(key.getRequiredName()));
	}

	// CFSecJpaISOCcy specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCcyId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaISOCcy r where r.requiredISOCcyId = :iSOCcyId")
	void deleteByIdIdx(@Param("iSOCcyId") short requiredISOCcyId);

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCode
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaISOCcy r where r.requiredISOCode = :iSOCode")
	void deleteByCcyCdIdx(@Param("iSOCode") String requiredISOCode);

	/**
	 *	CFSecISOCcyByCcyCdIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecISOCcyByCcyCdIdxKey of the entity to be locked.
	 */
	default void deleteByCcyCdIdx(ICFSecISOCcyByCcyCdIdxKey key) {
		deleteByCcyCdIdx(key.getRequiredISOCode());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaISOCcy r where r.requiredName = :name")
	void deleteByCcyNmIdx(@Param("name") String requiredName);

	/**
	 *	CFSecISOCcyByCcyNmIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecISOCcyByCcyNmIdxKey of the entity to be locked.
	 */
	default void deleteByCcyNmIdx(ICFSecISOCcyByCcyNmIdxKey key) {
		deleteByCcyNmIdx(key.getRequiredName());
	}

}

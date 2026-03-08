// Description: Java 25 Spring JPA Repository for ServiceType

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
 *	JpaRepository for the CFSecJpaServiceType entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaServiceTypeRepository extends JpaRepository<CFSecJpaServiceType, CFLibDbKeyHash256> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredServiceTypeId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaServiceType r where r.requiredServiceTypeId = :serviceTypeId")
	CFSecJpaServiceType get(@Param("serviceTypeId") CFLibDbKeyHash256 requiredServiceTypeId);

	// CFSecJpaServiceType specified index readers

	/**
	 *	Read an entity using the columns of the CFSecServiceTypeByUDescrIdxKey as arguments.
	 *
	 *		@param requiredDescription
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaServiceType r where r.requiredDescription = :description")
	CFSecJpaServiceType findByUDescrIdx(@Param("description") String requiredDescription);

	/**
	 *	CFSecServiceTypeByUDescrIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecServiceTypeByUDescrIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaServiceType findByUDescrIdx(ICFSecServiceTypeByUDescrIdxKey key) {
		return( findByUDescrIdx(key.getRequiredDescription()));
	}

	// CFSecJpaServiceType specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredServiceTypeId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaServiceType r where r.requiredServiceTypeId = :serviceTypeId")
	CFSecJpaServiceType lockByIdIdx(@Param("serviceTypeId") CFLibDbKeyHash256 requiredServiceTypeId);

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredDescription
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaServiceType r where r.requiredDescription = :description")
	CFSecJpaServiceType lockByUDescrIdx(@Param("description") String requiredDescription);

	/**
	 *	CFSecServiceTypeByUDescrIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaServiceType lockByUDescrIdx(ICFSecServiceTypeByUDescrIdxKey key) {
		return( lockByUDescrIdx(key.getRequiredDescription()));
	}

	// CFSecJpaServiceType specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredServiceTypeId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaServiceType r where r.requiredServiceTypeId = :serviceTypeId")
	void deleteByIdIdx(@Param("serviceTypeId") CFLibDbKeyHash256 requiredServiceTypeId);

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredDescription
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaServiceType r where r.requiredDescription = :description")
	void deleteByUDescrIdx(@Param("description") String requiredDescription);

	/**
	 *	CFSecServiceTypeByUDescrIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecServiceTypeByUDescrIdxKey of the entity to be locked.
	 */
	default void deleteByUDescrIdx(ICFSecServiceTypeByUDescrIdxKey key) {
		deleteByUDescrIdx(key.getRequiredDescription());
	}

}

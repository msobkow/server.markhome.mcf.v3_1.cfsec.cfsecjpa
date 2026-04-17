// Description: Java 25 Spring JPA Repository for SecSysGrp

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
 *	JpaRepository for the CFSecJpaSecSysGrp entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaSecSysGrpRepository extends JpaRepository<CFSecJpaSecSysGrp, CFLibDbKeyHash256> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecSysGrpId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecSysGrp r where r.requiredSecSysGrpId = :secSysGrpId")
	CFSecJpaSecSysGrp get(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId);

	// CFSecJpaSecSysGrp specified index readers

	/**
	 *	Read an entity using the columns of the CFSecSecSysGrpByUNameIdxKey as arguments.
	 *
	 *		@param requiredName
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecSysGrp r where r.requiredName = :name")
	CFSecJpaSecSysGrp findByUNameIdx(@Param("name") String requiredName);

	/**
	 *	CFSecSecSysGrpByUNameIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecSysGrpByUNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecSysGrp findByUNameIdx(ICFSecSecSysGrpByUNameIdxKey key) {
		return( findByUNameIdx(key.getRequiredName()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecSysGrpBySecLevelIdxKey as arguments.
	 *
	 *		@param requiredSecLevel
	 *
	 *		@return List&lt;CFSecJpaSecSysGrp&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecSysGrp r where r.requiredSecLevel = :secLevel")
	List<CFSecJpaSecSysGrp> findBySecLevelIdx(@Param("secLevel") ICFSecSchema.SecLevelEnum requiredSecLevel);

	/**
	 *	CFSecSecSysGrpBySecLevelIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecSysGrpBySecLevelIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecSysGrp> findBySecLevelIdx(ICFSecSecSysGrpBySecLevelIdxKey key) {
		return( findBySecLevelIdx(key.getRequiredSecLevel()));
	}

	// CFSecJpaSecSysGrp specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecSysGrp r where r.requiredSecSysGrpId = :secSysGrpId")
	CFSecJpaSecSysGrp lockByIdIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId);

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecSysGrp r where r.requiredName = :name")
	CFSecJpaSecSysGrp lockByUNameIdx(@Param("name") String requiredName);

	/**
	 *	CFSecSecSysGrpByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecSysGrp lockByUNameIdx(ICFSecSecSysGrpByUNameIdxKey key) {
		return( lockByUNameIdx(key.getRequiredName()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecLevel
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecSysGrp r where r.requiredSecLevel = :secLevel")
	List<CFSecJpaSecSysGrp> lockBySecLevelIdx(@Param("secLevel") ICFSecSchema.SecLevelEnum requiredSecLevel);

	/**
	 *	CFSecSecSysGrpBySecLevelIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecSysGrp> lockBySecLevelIdx(ICFSecSecSysGrpBySecLevelIdxKey key) {
		return( lockBySecLevelIdx(key.getRequiredSecLevel()));
	}

	// CFSecJpaSecSysGrp specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecSysGrpId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecSysGrp r where r.requiredSecSysGrpId = :secSysGrpId")
	void deleteByIdIdx(@Param("secSysGrpId") CFLibDbKeyHash256 requiredSecSysGrpId);

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecSysGrp r where r.requiredName = :name")
	void deleteByUNameIdx(@Param("name") String requiredName);

	/**
	 *	CFSecSecSysGrpByUNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecSysGrpByUNameIdxKey of the entity to be locked.
	 */
	default void deleteByUNameIdx(ICFSecSecSysGrpByUNameIdxKey key) {
		deleteByUNameIdx(key.getRequiredName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecLevel
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecSysGrp r where r.requiredSecLevel = :secLevel")
	void deleteBySecLevelIdx(@Param("secLevel") ICFSecSchema.SecLevelEnum requiredSecLevel);

	/**
	 *	CFSecSecSysGrpBySecLevelIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecSysGrpBySecLevelIdxKey of the entity to be locked.
	 */
	default void deleteBySecLevelIdx(ICFSecSecSysGrpBySecLevelIdxKey key) {
		deleteBySecLevelIdx(key.getRequiredSecLevel());
	}

}

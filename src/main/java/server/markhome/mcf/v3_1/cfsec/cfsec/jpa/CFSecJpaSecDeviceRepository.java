// Description: Java 25 Spring JPA Repository for SecDevice

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
 *	JpaRepository for the CFSecJpaSecDevice entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaSecDeviceRepository extends JpaRepository<CFSecJpaSecDevice, CFSecJpaSecDevicePKey> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredDevName
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecDevice r where r.pkey.requiredContainerSecUser.requiredSecUserId = :secUserId and r.pkey.requiredDevName = :devName")
	CFSecJpaSecDevice get(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("devName") String requiredDevName);

	/**
	 *	CFSecSecDevicePKey based read method for object-based access.
	 *
	 *		@param key The key of the entity to be read.
	 *
	 *		@return The entity read, usually from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecDevice get(ICFSecSecDevicePKey key) {
		return( get(key.getRequiredSecUserId(), key.getRequiredDevName()));
	}

	// CFSecJpaSecDevice specified index readers

	/**
	 *	Read an entity using the columns of the CFSecSecDeviceByNameIdxKey as arguments.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredDevName
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecDevice r where r.pkey.requiredContainerSecUser.requiredSecUserId = :secUserId and r.pkey.requiredDevName = :devName")
	CFSecJpaSecDevice findByNameIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("devName") String requiredDevName);

	/**
	 *	CFSecSecDeviceByNameIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecDeviceByNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecDevice findByNameIdx(ICFSecSecDeviceByNameIdxKey key) {
		return( findByNameIdx(key.getRequiredSecUserId(), key.getRequiredDevName()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecDeviceByUserIdxKey as arguments.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return List&lt;CFSecJpaSecDevice&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecDevice r where r.pkey.requiredContainerSecUser.requiredSecUserId = :secUserId")
	List<CFSecJpaSecDevice> findByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecDeviceByUserIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecDeviceByUserIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecDevice> findByUserIdx(ICFSecSecDeviceByUserIdxKey key) {
		return( findByUserIdx(key.getRequiredSecUserId()));
	}

	// CFSecJpaSecDevice specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredDevName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecDevice r where r.pkey.requiredContainerSecUser.requiredSecUserId = :secUserId and r.pkey.requiredDevName = :devName")
	CFSecJpaSecDevice lockByIdIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("devName") String requiredDevName);

	/**
	 *	CFSecSecDeviceByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecDevice lockByIdIdx(ICFSecSecDevicePKey key) {
		return( lockByIdIdx(key.getRequiredSecUserId(), key.getRequiredDevName()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredDevName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecDevice r where r.pkey.requiredContainerSecUser.requiredSecUserId = :secUserId and r.pkey.requiredDevName = :devName")
	CFSecJpaSecDevice lockByNameIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("devName") String requiredDevName);

	/**
	 *	CFSecSecDeviceByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecDevice lockByNameIdx(ICFSecSecDeviceByNameIdxKey key) {
		return( lockByNameIdx(key.getRequiredSecUserId(), key.getRequiredDevName()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecDevice r where r.pkey.requiredContainerSecUser.requiredSecUserId = :secUserId")
	List<CFSecJpaSecDevice> lockByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecDeviceByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecDevice> lockByUserIdx(ICFSecSecDeviceByUserIdxKey key) {
		return( lockByUserIdx(key.getRequiredSecUserId()));
	}

	// CFSecJpaSecDevice specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredDevName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecDevice r where r.pkey.requiredContainerSecUser.requiredSecUserId = :secUserId and r.pkey.requiredDevName = :devName")
	void deleteByIdIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("devName") String requiredDevName);

	/**
	 *	CFSecSecDeviceByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecDeviceByIdIdxKey of the entity to be locked.
	 */
	default void deleteByIdIdx(ICFSecSecDevicePKey key) {
		deleteByIdIdx(key.getRequiredSecUserId(), key.getRequiredDevName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredDevName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecDevice r where r.pkey.requiredContainerSecUser.requiredSecUserId = :secUserId and r.pkey.requiredDevName = :devName")
	void deleteByNameIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("devName") String requiredDevName);

	/**
	 *	CFSecSecDeviceByNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecDeviceByNameIdxKey of the entity to be locked.
	 */
	default void deleteByNameIdx(ICFSecSecDeviceByNameIdxKey key) {
		deleteByNameIdx(key.getRequiredSecUserId(), key.getRequiredDevName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecDevice r where r.pkey.requiredContainerSecUser.requiredSecUserId = :secUserId")
	void deleteByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecDeviceByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecDeviceByUserIdxKey of the entity to be locked.
	 */
	default void deleteByUserIdx(ICFSecSecDeviceByUserIdxKey key) {
		deleteByUserIdx(key.getRequiredSecUserId());
	}

}

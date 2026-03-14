// Description: Java 25 Spring JPA Repository for Cluster

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
 *	JpaRepository for the CFSecJpaCluster entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaClusterRepository extends JpaRepository<CFSecJpaCluster, CFLibDbKeyHash256> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaCluster r where r.requiredId = :id")
	CFSecJpaCluster get(@Param("id") CFLibDbKeyHash256 requiredId);

	// CFSecJpaCluster specified index readers

	/**
	 *	Read an entity using the columns of the CFSecClusterByUDomNameIdxKey as arguments.
	 *
	 *		@param requiredFullDomName
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaCluster r where r.requiredFullDomName = :fullDomName")
	CFSecJpaCluster findByUDomNameIdx(@Param("fullDomName") String requiredFullDomName);

	/**
	 *	CFSecClusterByUDomNameIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecClusterByUDomNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaCluster findByUDomNameIdx(ICFSecClusterByUDomNameIdxKey key) {
		return( findByUDomNameIdx(key.getRequiredFullDomName()));
	}

	/**
	 *	Read an entity using the columns of the CFSecClusterByUDescrIdxKey as arguments.
	 *
	 *		@param requiredDescription
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaCluster r where r.requiredDescription = :description")
	CFSecJpaCluster findByUDescrIdx(@Param("description") String requiredDescription);

	/**
	 *	CFSecClusterByUDescrIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecClusterByUDescrIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaCluster findByUDescrIdx(ICFSecClusterByUDescrIdxKey key) {
		return( findByUDescrIdx(key.getRequiredDescription()));
	}

	// CFSecJpaCluster specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaCluster r where r.requiredId = :id")
	CFSecJpaCluster lockByIdIdx(@Param("id") CFLibDbKeyHash256 requiredId);

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredFullDomName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaCluster r where r.requiredFullDomName = :fullDomName")
	CFSecJpaCluster lockByUDomNameIdx(@Param("fullDomName") String requiredFullDomName);

	/**
	 *	CFSecClusterByUDomNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaCluster lockByUDomNameIdx(ICFSecClusterByUDomNameIdxKey key) {
		return( lockByUDomNameIdx(key.getRequiredFullDomName()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredDescription
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaCluster r where r.requiredDescription = :description")
	CFSecJpaCluster lockByUDescrIdx(@Param("description") String requiredDescription);

	/**
	 *	CFSecClusterByUDescrIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaCluster lockByUDescrIdx(ICFSecClusterByUDescrIdxKey key) {
		return( lockByUDescrIdx(key.getRequiredDescription()));
	}

	// CFSecJpaCluster specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaCluster r where r.requiredId = :id")
	void deleteByIdIdx(@Param("id") CFLibDbKeyHash256 requiredId);

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredFullDomName
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaCluster r where r.requiredFullDomName = :fullDomName")
	void deleteByUDomNameIdx(@Param("fullDomName") String requiredFullDomName);

	/**
	 *	CFSecClusterByUDomNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecClusterByUDomNameIdxKey of the entity to be locked.
	 */
	default void deleteByUDomNameIdx(ICFSecClusterByUDomNameIdxKey key) {
		deleteByUDomNameIdx(key.getRequiredFullDomName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredDescription
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaCluster r where r.requiredDescription = :description")
	void deleteByUDescrIdx(@Param("description") String requiredDescription);

	/**
	 *	CFSecClusterByUDescrIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecClusterByUDescrIdxKey of the entity to be locked.
	 */
	default void deleteByUDescrIdx(ICFSecClusterByUDescrIdxKey key) {
		deleteByUDescrIdx(key.getRequiredDescription());
	}

}

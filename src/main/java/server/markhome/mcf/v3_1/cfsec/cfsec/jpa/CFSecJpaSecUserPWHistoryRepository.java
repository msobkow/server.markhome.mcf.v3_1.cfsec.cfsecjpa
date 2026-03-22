// Description: Java 25 Spring JPA Repository for SecUserPWHistory

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
 *	JpaRepository for the CFSecJpaSecUserPWHistory entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaSecUserPWHistoryRepository extends JpaRepository<CFSecJpaSecUserPWHistory, CFSecJpaSecUserPWHistoryPKey> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredPWSetStamp
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecUserPWHistory r where r.pkey.requiredSecUserId = :secUserId and r.pkey.requiredPWSetStamp = :pWSetStamp")
	CFSecJpaSecUserPWHistory get(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("pWSetStamp") LocalDateTime requiredPWSetStamp);

	/**
	 *	CFSecSecUserPWHistoryPKey based read method for object-based access.
	 *
	 *		@param key The key of the entity to be read.
	 *
	 *		@return The entity read, usually from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecUserPWHistory get(ICFSecSecUserPWHistoryPKey key) {
		return( get(key.getRequiredSecUserId(), key.getRequiredPWSetStamp()));
	}

	// CFSecJpaSecUserPWHistory specified index readers

	/**
	 *	Read an entity using the columns of the CFSecSecUserPWHistoryByUserIdxKey as arguments.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecUserPWHistory r where r.pkey.requiredSecUserId = :secUserId")
	CFSecJpaSecUserPWHistory findByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecUserPWHistoryByUserIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecUserPWHistoryByUserIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecUserPWHistory findByUserIdx(ICFSecSecUserPWHistoryByUserIdxKey key) {
		return( findByUserIdx(key.getRequiredSecUserId()));
	}

	/**
	 *	Read an entity using the columns of the CFSecSecUserPWHistoryBySetStampIdxKey as arguments.
	 *
	 *		@param requiredPWSetStamp
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecUserPWHistory r where r.pkey.requiredPWSetStamp = :pWSetStamp")
	CFSecJpaSecUserPWHistory findBySetStampIdx(@Param("pWSetStamp") LocalDateTime requiredPWSetStamp);

	/**
	 *	CFSecSecUserPWHistoryBySetStampIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecUserPWHistoryBySetStampIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecUserPWHistory findBySetStampIdx(ICFSecSecUserPWHistoryBySetStampIdxKey key) {
		return( findBySetStampIdx(key.getRequiredPWSetStamp()));
	}

	/**
	 *	Read an entity using the columns of the CFSecSecUserPWHistoryByReplacedStampIdxKey as arguments.
	 *
	 *		@param requiredPWReplacedStamp
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecUserPWHistory r where r.requiredPWReplacedStamp = :pWReplacedStamp")
	CFSecJpaSecUserPWHistory findByReplacedStampIdx(@Param("pWReplacedStamp") LocalDateTime requiredPWReplacedStamp);

	/**
	 *	CFSecSecUserPWHistoryByReplacedStampIdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecUserPWHistoryByReplacedStampIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecUserPWHistory findByReplacedStampIdx(ICFSecSecUserPWHistoryByReplacedStampIdxKey key) {
		return( findByReplacedStampIdx(key.getRequiredPWReplacedStamp()));
	}

	// CFSecJpaSecUserPWHistory specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredPWSetStamp
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecUserPWHistory r where r.pkey.requiredSecUserId = :secUserId and r.pkey.requiredPWSetStamp = :pWSetStamp")
	CFSecJpaSecUserPWHistory lockByIdIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("pWSetStamp") LocalDateTime requiredPWSetStamp);

	/**
	 *	CFSecSecUserPWHistoryByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecUserPWHistory lockByIdIdx(ICFSecSecUserPWHistoryPKey key) {
		return( lockByIdIdx(key.getRequiredSecUserId(), key.getRequiredPWSetStamp()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecUserPWHistory r where r.pkey.requiredSecUserId = :secUserId")
	CFSecJpaSecUserPWHistory lockByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecUserPWHistoryByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecUserPWHistory lockByUserIdx(ICFSecSecUserPWHistoryByUserIdxKey key) {
		return( lockByUserIdx(key.getRequiredSecUserId()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredPWSetStamp
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecUserPWHistory r where r.pkey.requiredPWSetStamp = :pWSetStamp")
	CFSecJpaSecUserPWHistory lockBySetStampIdx(@Param("pWSetStamp") LocalDateTime requiredPWSetStamp);

	/**
	 *	CFSecSecUserPWHistoryBySetStampIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecUserPWHistory lockBySetStampIdx(ICFSecSecUserPWHistoryBySetStampIdxKey key) {
		return( lockBySetStampIdx(key.getRequiredPWSetStamp()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredPWReplacedStamp
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecUserPWHistory r where r.requiredPWReplacedStamp = :pWReplacedStamp")
	CFSecJpaSecUserPWHistory lockByReplacedStampIdx(@Param("pWReplacedStamp") LocalDateTime requiredPWReplacedStamp);

	/**
	 *	CFSecSecUserPWHistoryByReplacedStampIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecUserPWHistory lockByReplacedStampIdx(ICFSecSecUserPWHistoryByReplacedStampIdxKey key) {
		return( lockByReplacedStampIdx(key.getRequiredPWReplacedStamp()));
	}

	// CFSecJpaSecUserPWHistory specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *		@param requiredPWSetStamp
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecUserPWHistory r where r.pkey.requiredSecUserId = :secUserId and r.pkey.requiredPWSetStamp = :pWSetStamp")
	void deleteByIdIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId,
		@Param("pWSetStamp") LocalDateTime requiredPWSetStamp);

	/**
	 *	CFSecSecUserPWHistoryByIdIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecUserPWHistoryByIdIdxKey of the entity to be locked.
	 */
	default void deleteByIdIdx(ICFSecSecUserPWHistoryPKey key) {
		deleteByIdIdx(key.getRequiredSecUserId(), key.getRequiredPWSetStamp());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecUserPWHistory r where r.pkey.requiredSecUserId = :secUserId")
	void deleteByUserIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	CFSecSecUserPWHistoryByUserIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecUserPWHistoryByUserIdxKey of the entity to be locked.
	 */
	default void deleteByUserIdx(ICFSecSecUserPWHistoryByUserIdxKey key) {
		deleteByUserIdx(key.getRequiredSecUserId());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredPWSetStamp
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecUserPWHistory r where r.pkey.requiredPWSetStamp = :pWSetStamp")
	void deleteBySetStampIdx(@Param("pWSetStamp") LocalDateTime requiredPWSetStamp);

	/**
	 *	CFSecSecUserPWHistoryBySetStampIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecUserPWHistoryBySetStampIdxKey of the entity to be locked.
	 */
	default void deleteBySetStampIdx(ICFSecSecUserPWHistoryBySetStampIdxKey key) {
		deleteBySetStampIdx(key.getRequiredPWSetStamp());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredPWReplacedStamp
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecUserPWHistory r where r.requiredPWReplacedStamp = :pWReplacedStamp")
	void deleteByReplacedStampIdx(@Param("pWReplacedStamp") LocalDateTime requiredPWReplacedStamp);

	/**
	 *	CFSecSecUserPWHistoryByReplacedStampIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecUserPWHistoryByReplacedStampIdxKey of the entity to be locked.
	 */
	default void deleteByReplacedStampIdx(ICFSecSecUserPWHistoryByReplacedStampIdxKey key) {
		deleteByReplacedStampIdx(key.getRequiredPWReplacedStamp());
	}

}

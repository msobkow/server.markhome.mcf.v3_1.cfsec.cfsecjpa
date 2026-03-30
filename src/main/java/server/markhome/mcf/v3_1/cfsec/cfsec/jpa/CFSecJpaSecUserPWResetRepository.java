// Description: Java 25 Spring JPA Repository for SecUserPWReset

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
 *	JpaRepository for the CFSecJpaSecUserPWReset entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaSecUserPWResetRepository extends JpaRepository<CFSecJpaSecUserPWReset, CFLibDbKeyHash256> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecUserPWReset r where r.requiredContainerUser.requiredSecUserId = :secUserId")
	CFSecJpaSecUserPWReset get(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	// CFSecJpaSecUserPWReset specified index readers

	/**
	 *	Read an entity using the columns of the CFSecSecUserPWResetByUUuid6IdxKey as arguments.
	 *
	 *		@param requiredPasswordResetUuid6
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecUserPWReset r where r.requiredPasswordResetUuid6 = :passwordResetUuid6")
	CFSecJpaSecUserPWReset findByUUuid6Idx(@Param("passwordResetUuid6") CFLibUuid6 requiredPasswordResetUuid6);

	/**
	 *	CFSecSecUserPWResetByUUuid6IdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecUserPWResetByUUuid6IdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecUserPWReset findByUUuid6Idx(ICFSecSecUserPWResetByUUuid6IdxKey key) {
		return( findByUUuid6Idx(key.getRequiredPasswordResetUuid6()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecUserPWResetBySentEMAddrIdxKey as arguments.
	 *
	 *		@param requiredSentToEMailAddr
	 *
	 *		@return List&lt;CFSecJpaSecUserPWReset&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecUserPWReset r where r.requiredSentToEMailAddr = :sentToEMailAddr")
	List<CFSecJpaSecUserPWReset> findBySentEMAddrIdx(@Param("sentToEMailAddr") String requiredSentToEMailAddr);

	/**
	 *	CFSecSecUserPWResetBySentEMAddrIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecUserPWResetBySentEMAddrIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecUserPWReset> findBySentEMAddrIdx(ICFSecSecUserPWResetBySentEMAddrIdxKey key) {
		return( findBySentEMAddrIdx(key.getRequiredSentToEMailAddr()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecUserPWResetByNewAcctIdxKey as arguments.
	 *
	 *		@param requiredNewAccount
	 *
	 *		@return List&lt;CFSecJpaSecUserPWReset&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecUserPWReset r where r.requiredNewAccount = :newAccount")
	List<CFSecJpaSecUserPWReset> findByNewAcctIdx(@Param("newAccount") boolean requiredNewAccount);

	/**
	 *	CFSecSecUserPWResetByNewAcctIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecUserPWResetByNewAcctIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecUserPWReset> findByNewAcctIdx(ICFSecSecUserPWResetByNewAcctIdxKey key) {
		return( findByNewAcctIdx(key.getRequiredNewAccount()));
	}

	// CFSecJpaSecUserPWReset specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecUserPWReset r where r.requiredContainerUser.requiredSecUserId = :secUserId")
	CFSecJpaSecUserPWReset lockByIdIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredPasswordResetUuid6
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecUserPWReset r where r.requiredPasswordResetUuid6 = :passwordResetUuid6")
	CFSecJpaSecUserPWReset lockByUUuid6Idx(@Param("passwordResetUuid6") CFLibUuid6 requiredPasswordResetUuid6);

	/**
	 *	CFSecSecUserPWResetByUUuid6IdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecUserPWReset lockByUUuid6Idx(ICFSecSecUserPWResetByUUuid6IdxKey key) {
		return( lockByUUuid6Idx(key.getRequiredPasswordResetUuid6()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSentToEMailAddr
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecUserPWReset r where r.requiredSentToEMailAddr = :sentToEMailAddr")
	List<CFSecJpaSecUserPWReset> lockBySentEMAddrIdx(@Param("sentToEMailAddr") String requiredSentToEMailAddr);

	/**
	 *	CFSecSecUserPWResetBySentEMAddrIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecUserPWReset> lockBySentEMAddrIdx(ICFSecSecUserPWResetBySentEMAddrIdxKey key) {
		return( lockBySentEMAddrIdx(key.getRequiredSentToEMailAddr()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredNewAccount
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecUserPWReset r where r.requiredNewAccount = :newAccount")
	List<CFSecJpaSecUserPWReset> lockByNewAcctIdx(@Param("newAccount") boolean requiredNewAccount);

	/**
	 *	CFSecSecUserPWResetByNewAcctIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecUserPWReset> lockByNewAcctIdx(ICFSecSecUserPWResetByNewAcctIdxKey key) {
		return( lockByNewAcctIdx(key.getRequiredNewAccount()));
	}

	// CFSecJpaSecUserPWReset specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecUserPWReset r where r.requiredContainerUser.requiredSecUserId = :secUserId")
	void deleteByIdIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredPasswordResetUuid6
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecUserPWReset r where r.requiredPasswordResetUuid6 = :passwordResetUuid6")
	void deleteByUUuid6Idx(@Param("passwordResetUuid6") CFLibUuid6 requiredPasswordResetUuid6);

	/**
	 *	CFSecSecUserPWResetByUUuid6IdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecUserPWResetByUUuid6IdxKey of the entity to be locked.
	 */
	default void deleteByUUuid6Idx(ICFSecSecUserPWResetByUUuid6IdxKey key) {
		deleteByUUuid6Idx(key.getRequiredPasswordResetUuid6());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSentToEMailAddr
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecUserPWReset r where r.requiredSentToEMailAddr = :sentToEMailAddr")
	void deleteBySentEMAddrIdx(@Param("sentToEMailAddr") String requiredSentToEMailAddr);

	/**
	 *	CFSecSecUserPWResetBySentEMAddrIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecUserPWResetBySentEMAddrIdxKey of the entity to be locked.
	 */
	default void deleteBySentEMAddrIdx(ICFSecSecUserPWResetBySentEMAddrIdxKey key) {
		deleteBySentEMAddrIdx(key.getRequiredSentToEMailAddr());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredNewAccount
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecUserPWReset r where r.requiredNewAccount = :newAccount")
	void deleteByNewAcctIdx(@Param("newAccount") boolean requiredNewAccount);

	/**
	 *	CFSecSecUserPWResetByNewAcctIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecUserPWResetByNewAcctIdxKey of the entity to be locked.
	 */
	default void deleteByNewAcctIdx(ICFSecSecUserPWResetByNewAcctIdxKey key) {
		deleteByNewAcctIdx(key.getRequiredNewAccount());
	}

}

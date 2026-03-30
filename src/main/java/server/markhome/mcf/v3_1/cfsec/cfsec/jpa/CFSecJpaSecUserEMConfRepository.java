// Description: Java 25 Spring JPA Repository for SecUserEMConf

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
 *	JpaRepository for the CFSecJpaSecUserEMConf entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa.
 *	The manufactured repositories try to provide a rich, do-it-all interface to the JPA data store, with both object and argument-based implementations of the interface defined.
 */
@Transactional(readOnly = true)
public interface CFSecJpaSecUserEMConfRepository extends JpaRepository<CFSecJpaSecUserEMConf, CFLibDbKeyHash256> {

	/**
	 *	Argument-based get database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return The retrieved entity, usually from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecUserEMConf r where r.requiredContainerUser.requiredSecUserId = :secUserId")
	CFSecJpaSecUserEMConf get(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	// CFSecJpaSecUserEMConf specified index readers

	/**
	 *	Read an entity using the columns of the CFSecSecUserEMConfByUUuid6IdxKey as arguments.
	 *
	 *		@param requiredEMConfirmationUuid6
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	@Query("select r from CFSecJpaSecUserEMConf r where r.requiredEMConfirmationUuid6 = :eMConfirmationUuid6")
	CFSecJpaSecUserEMConf findByUUuid6Idx(@Param("eMConfirmationUuid6") CFLibUuid6 requiredEMConfirmationUuid6);

	/**
	 *	CFSecSecUserEMConfByUUuid6IdxKey entity reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecUserEMConfByUUuid6IdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, typically from the JPA cache, or null if no such entity exists.
	 */
	default CFSecJpaSecUserEMConf findByUUuid6Idx(ICFSecSecUserEMConfByUUuid6IdxKey key) {
		return( findByUUuid6Idx(key.getRequiredEMConfirmationUuid6()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecUserEMConfByConfEMAddrIdxKey as arguments.
	 *
	 *		@param requiredConfirmEMailAddr
	 *
	 *		@return List&lt;CFSecJpaSecUserEMConf&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecUserEMConf r where r.requiredConfirmEMailAddr = :confirmEMailAddr")
	List<CFSecJpaSecUserEMConf> findByConfEMAddrIdx(@Param("confirmEMailAddr") String requiredConfirmEMailAddr);

	/**
	 *	CFSecSecUserEMConfByConfEMAddrIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecUserEMConfByConfEMAddrIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecUserEMConf> findByConfEMAddrIdx(ICFSecSecUserEMConfByConfEMAddrIdxKey key) {
		return( findByConfEMAddrIdx(key.getRequiredConfirmEMailAddr()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecUserEMConfBySentStampIdxKey as arguments.
	 *
	 *		@param requiredEMailSentStamp
	 *
	 *		@return List&lt;CFSecJpaSecUserEMConf&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecUserEMConf r where r.requiredEMailSentStamp = :eMailSentStamp")
	List<CFSecJpaSecUserEMConf> findBySentStampIdx(@Param("eMailSentStamp") LocalDateTime requiredEMailSentStamp);

	/**
	 *	CFSecSecUserEMConfBySentStampIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecUserEMConfBySentStampIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecUserEMConf> findBySentStampIdx(ICFSecSecUserEMConfBySentStampIdxKey key) {
		return( findBySentStampIdx(key.getRequiredEMailSentStamp()));
	}

	/**
	 *	Read zero or more entities into a List using the columns of the CFSecSecUserEMConfByNewAcctIdxKey as arguments.
	 *
	 *		@param requiredNewAccount
	 *
	 *		@return List&lt;CFSecJpaSecUserEMConf&gt; of the found entities, typically from the JPA cache, or an empty list if no such entities exist.
	 */
	@Query("select r from CFSecJpaSecUserEMConf r where r.requiredNewAccount = :newAccount")
	List<CFSecJpaSecUserEMConf> findByNewAcctIdx(@Param("newAccount") boolean requiredNewAccount);

	/**
	 *	CFSecSecUserEMConfByNewAcctIdxKey entity list reader convenience method for object-based access.
	 *
	 *		@param key The CFSecSecUserEMConfByNewAcctIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty, typically populated from the JPA cache.
	 */
	default List<CFSecJpaSecUserEMConf> findByNewAcctIdx(ICFSecSecUserEMConfByNewAcctIdxKey key) {
		return( findByNewAcctIdx(key.getRequiredNewAccount()));
	}

	// CFSecJpaSecUserEMConf specified delete-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecUserEMConf r where r.requiredContainerUser.requiredSecUserId = :secUserId")
	CFSecJpaSecUserEMConf lockByIdIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredEMConfirmationUuid6
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecUserEMConf r where r.requiredEMConfirmationUuid6 = :eMConfirmationUuid6")
	CFSecJpaSecUserEMConf lockByUUuid6Idx(@Param("eMConfirmationUuid6") CFLibUuid6 requiredEMConfirmationUuid6);

	/**
	 *	CFSecSecUserEMConfByUUuid6IdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	default CFSecJpaSecUserEMConf lockByUUuid6Idx(ICFSecSecUserEMConfByUUuid6IdxKey key) {
		return( lockByUUuid6Idx(key.getRequiredEMConfirmationUuid6()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredConfirmEMailAddr
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecUserEMConf r where r.requiredConfirmEMailAddr = :confirmEMailAddr")
	List<CFSecJpaSecUserEMConf> lockByConfEMAddrIdx(@Param("confirmEMailAddr") String requiredConfirmEMailAddr);

	/**
	 *	CFSecSecUserEMConfByConfEMAddrIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecUserEMConf> lockByConfEMAddrIdx(ICFSecSecUserEMConfByConfEMAddrIdxKey key) {
		return( lockByConfEMAddrIdx(key.getRequiredConfirmEMailAddr()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredEMailSentStamp
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional
	@Lock(LockModeType.WRITE)
	@Query("select r from CFSecJpaSecUserEMConf r where r.requiredEMailSentStamp = :eMailSentStamp")
	List<CFSecJpaSecUserEMConf> lockBySentStampIdx(@Param("eMailSentStamp") LocalDateTime requiredEMailSentStamp);

	/**
	 *	CFSecSecUserEMConfBySentStampIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecUserEMConf> lockBySentStampIdx(ICFSecSecUserEMConfBySentStampIdxKey key) {
		return( lockBySentStampIdx(key.getRequiredEMailSentStamp()));
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
	@Query("select r from CFSecJpaSecUserEMConf r where r.requiredNewAccount = :newAccount")
	List<CFSecJpaSecUserEMConf> lockByNewAcctIdx(@Param("newAccount") boolean requiredNewAccount);

	/**
	 *	CFSecSecUserEMConfByNewAcctIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	default List<CFSecJpaSecUserEMConf> lockByNewAcctIdx(ICFSecSecUserEMConfByNewAcctIdxKey key) {
		return( lockByNewAcctIdx(key.getRequiredNewAccount()));
	}

	// CFSecJpaSecUserEMConf specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredSecUserId
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecUserEMConf r where r.requiredContainerUser.requiredSecUserId = :secUserId")
	void deleteByIdIdx(@Param("secUserId") CFLibDbKeyHash256 requiredSecUserId);

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredEMConfirmationUuid6
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecUserEMConf r where r.requiredEMConfirmationUuid6 = :eMConfirmationUuid6")
	void deleteByUUuid6Idx(@Param("eMConfirmationUuid6") CFLibUuid6 requiredEMConfirmationUuid6);

	/**
	 *	CFSecSecUserEMConfByUUuid6IdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecUserEMConfByUUuid6IdxKey of the entity to be locked.
	 */
	default void deleteByUUuid6Idx(ICFSecSecUserEMConfByUUuid6IdxKey key) {
		deleteByUUuid6Idx(key.getRequiredEMConfirmationUuid6());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredConfirmEMailAddr
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecUserEMConf r where r.requiredConfirmEMailAddr = :confirmEMailAddr")
	void deleteByConfEMAddrIdx(@Param("confirmEMailAddr") String requiredConfirmEMailAddr);

	/**
	 *	CFSecSecUserEMConfByConfEMAddrIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecUserEMConfByConfEMAddrIdxKey of the entity to be locked.
	 */
	default void deleteByConfEMAddrIdx(ICFSecSecUserEMConfByConfEMAddrIdxKey key) {
		deleteByConfEMAddrIdx(key.getRequiredConfirmEMailAddr());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredEMailSentStamp
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecUserEMConf r where r.requiredEMailSentStamp = :eMailSentStamp")
	void deleteBySentStampIdx(@Param("eMailSentStamp") LocalDateTime requiredEMailSentStamp);

	/**
	 *	CFSecSecUserEMConfBySentStampIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecUserEMConfBySentStampIdxKey of the entity to be locked.
	 */
	default void deleteBySentStampIdx(ICFSecSecUserEMConfBySentStampIdxKey key) {
		deleteBySentStampIdx(key.getRequiredEMailSentStamp());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredNewAccount
	 */
	@Transactional
	@Modifying
	@Query("delete from CFSecJpaSecUserEMConf r where r.requiredNewAccount = :newAccount")
	void deleteByNewAcctIdx(@Param("newAccount") boolean requiredNewAccount);

	/**
	 *	CFSecSecUserEMConfByNewAcctIdxKey based lock method for object-based access.
	 *
	 *		@param key The CFSecSecUserEMConfByNewAcctIdxKey of the entity to be locked.
	 */
	default void deleteByNewAcctIdx(ICFSecSecUserEMConfByNewAcctIdxKey key) {
		deleteByNewAcctIdx(key.getRequiredNewAccount());
	}

}

// Description: Java 25 Spring JPA Service for ISOTZone

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;

/**
 *	Service for the CFSecISOTZone entities defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSecISOTZoneRepository to access them.
 */
@Service("cfsec31JpaISOTZoneService")
public class CFSecJpaISOTZoneService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaISOTZoneRepository cfsec31ISOTZoneRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaISOTZone, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOTZone create(CFSecJpaISOTZone data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		short originalRequiredISOTZoneId = data.getRequiredISOTZoneId();
		boolean generatedRequiredISOTZoneId = false;
		if(data.getRequiredIso8601() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredIso8601");
		}
		if(data.getRequiredTZName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredTZName");
		}
		if( data.getRequiredTZHourOffset() < ICFSecISOTZone.TZHOUROFFSET_MIN_VALUE ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				S_ProcName,
				0,
				"getRequiredTZHourOffset()",
				data.getRequiredTZHourOffset(),
				ICFSecISOTZone.TZHOUROFFSET_MIN_VALUE );
		}
		if( data.getRequiredTZHourOffset() > ICFSecISOTZone.TZHOUROFFSET_MAX_VALUE ) {
			throw new CFLibArgumentOverflowException( getClass(),
				S_ProcName,
				0,
				"getRequiredTZHourOffset()",
				data.getRequiredTZHourOffset(),
				ICFSecISOTZone.TZHOUROFFSET_MAX_VALUE );
		}
		if( data.getRequiredTZMinOffset() < ICFSecISOTZone.TZMINOFFSET_MIN_VALUE ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				S_ProcName,
				0,
				"getRequiredTZMinOffset()",
				data.getRequiredTZMinOffset(),
				ICFSecISOTZone.TZMINOFFSET_MIN_VALUE );
		}
		if( data.getRequiredTZMinOffset() > ICFSecISOTZone.TZMINOFFSET_MAX_VALUE ) {
			throw new CFLibArgumentOverflowException( getClass(),
				S_ProcName,
				0,
				"getRequiredTZMinOffset()",
				data.getRequiredTZMinOffset(),
				ICFSecISOTZone.TZMINOFFSET_MAX_VALUE );
		}
		if(data.getRequiredDescription() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredDescription");
		}
		try {
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31ISOTZoneRepository.existsById((Short)data.getPKey())) {
				return( (CFSecJpaISOTZone)(cfsec31ISOTZoneRepository.findById((Short)(data.getPKey())).get()));
			}
			return cfsec31ISOTZoneRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredISOTZoneId) {
					data.setRequiredISOTZoneId(originalRequiredISOTZoneId);
				}
			throw new CFLibDbException(getClass(),
				S_ProcName,
				ex);
		}
	}

	/**
	 *	Update an existing entity.
	 *
	 *		@param	data	The entity to be updated.
	 *
	 *		@return The updated entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOTZone update(CFSecJpaISOTZone data) {
		final String S_ProcName = "update";
		if (data == null) {
			return( null );
		}
		if (data.getPKey() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.getPKey()");
		}
		if(data.getRequiredIso8601() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredIso8601");
		}
		if(data.getRequiredTZName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredTZName");
		}
		if( data.getRequiredTZHourOffset() < ICFSecISOTZone.TZHOUROFFSET_MIN_VALUE ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				S_ProcName,
				0,
				"getRequiredTZHourOffset()",
				data.getRequiredTZHourOffset(),
				ICFSecISOTZone.TZHOUROFFSET_MIN_VALUE );
		}
		if( data.getRequiredTZHourOffset() > ICFSecISOTZone.TZHOUROFFSET_MAX_VALUE ) {
			throw new CFLibArgumentOverflowException( getClass(),
				S_ProcName,
				0,
				"getRequiredTZHourOffset()",
				data.getRequiredTZHourOffset(),
				ICFSecISOTZone.TZHOUROFFSET_MAX_VALUE );
		}
		if( data.getRequiredTZMinOffset() < ICFSecISOTZone.TZMINOFFSET_MIN_VALUE ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				S_ProcName,
				0,
				"getRequiredTZMinOffset()",
				data.getRequiredTZMinOffset(),
				ICFSecISOTZone.TZMINOFFSET_MIN_VALUE );
		}
		if( data.getRequiredTZMinOffset() > ICFSecISOTZone.TZMINOFFSET_MAX_VALUE ) {
			throw new CFLibArgumentOverflowException( getClass(),
				S_ProcName,
				0,
				"getRequiredTZMinOffset()",
				data.getRequiredTZMinOffset(),
				ICFSecISOTZone.TZMINOFFSET_MAX_VALUE );
		}
		if(data.getRequiredDescription() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredDescription");
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaISOTZone existing = cfsec31ISOTZoneRepository.findById((Short)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecISOTZone to existing object
		// Apply data columns of CFSecISOTZone to existing object
		existing.setRequiredIso8601(data.getRequiredIso8601());
		existing.setRequiredTZName(data.getRequiredTZName());
		existing.setRequiredTZHourOffset(data.getRequiredTZHourOffset());
		existing.setRequiredTZMinOffset(data.getRequiredTZMinOffset());
		existing.setRequiredDescription(data.getRequiredDescription());
		existing.setRequiredVisible(data.getRequiredVisible());
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31ISOTZoneRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredISOTZoneId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOTZone find(@Param("iSOTZoneId") short requiredISOTZoneId) {
		return( cfsec31ISOTZoneRepository.get(requiredISOTZoneId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOTZone> findAll() {
		return( cfsec31ISOTZoneRepository.findAll() );
	}

	// CFSecISOTZone specified index finders

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecISOTZoneByOffsetIdxKey as arguments.
	 *
	 *		@param requiredTZHourOffset
	 *		@param requiredTZMinOffset
	 *
	 *		@return List&lt;CFSecJpaISOTZone&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOTZone> findByOffsetIdx(@Param("tZHourOffset") short requiredTZHourOffset,
		@Param("tZMinOffset") short requiredTZMinOffset) {
		return( cfsec31ISOTZoneRepository.findByOffsetIdx(requiredTZHourOffset,
			requiredTZMinOffset));
	}

	/**
	 *	ICFSecISOTZoneByOffsetIdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecISOTZoneByOffsetIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOTZone> findByOffsetIdx(ICFSecISOTZoneByOffsetIdxKey key) {
		return( cfsec31ISOTZoneRepository.findByOffsetIdx(key.getRequiredTZHourOffset(), key.getRequiredTZMinOffset()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecISOTZoneByUTZNameIdxKey as arguments.
	 *
	 *		@param requiredTZName
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOTZone findByUTZNameIdx(@Param("tZName") String requiredTZName) {
		return( cfsec31ISOTZoneRepository.findByUTZNameIdx(requiredTZName));
	}

	/**
	 *	ICFSecISOTZoneByUTZNameIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecISOTZoneByUTZNameIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOTZone findByUTZNameIdx(ICFSecISOTZoneByUTZNameIdxKey key) {
		return( cfsec31ISOTZoneRepository.findByUTZNameIdx(key.getRequiredTZName()));
	}

	/**
	 *	Find zero or more entities into a List using the columns of the ICFSecISOTZoneByIso8601IdxKey as arguments.
	 *
	 *		@param requiredIso8601
	 *
	 *		@return List&lt;CFSecJpaISOTZone&gt; of the found entities, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOTZone> findByIso8601Idx(@Param("iso8601") String requiredIso8601) {
		return( cfsec31ISOTZoneRepository.findByIso8601Idx(requiredIso8601));
	}

	/**
	 *	ICFSecISOTZoneByIso8601IdxKey entity list finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecISOTZoneByIso8601IdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity list, which may be empty.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOTZone> findByIso8601Idx(ICFSecISOTZoneByIso8601IdxKey key) {
		return( cfsec31ISOTZoneRepository.findByIso8601Idx(key.getRequiredIso8601()));
	}

	// CFSecISOTZone specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOTZoneId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOTZone lockByIdIdx(@Param("iSOTZoneId") short requiredISOTZoneId) {
		return( cfsec31ISOTZoneRepository.lockByIdIdx(requiredISOTZoneId));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTZHourOffset
	 *		@param requiredTZMinOffset
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOTZone> lockByOffsetIdx(@Param("tZHourOffset") short requiredTZHourOffset,
		@Param("tZMinOffset") short requiredTZMinOffset) {
		return( cfsec31ISOTZoneRepository.lockByOffsetIdx(requiredTZHourOffset,
			requiredTZMinOffset));
	}

	/**
	 *	ICFSecISOTZoneByOffsetIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOTZone> lockByOffsetIdx(ICFSecISOTZoneByOffsetIdxKey key) {
		return( cfsec31ISOTZoneRepository.lockByOffsetIdx(key.getRequiredTZHourOffset(), key.getRequiredTZMinOffset()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTZName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOTZone lockByUTZNameIdx(@Param("tZName") String requiredTZName) {
		return( cfsec31ISOTZoneRepository.lockByUTZNameIdx(requiredTZName));
	}

	/**
	 *	ICFSecISOTZoneByUTZNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOTZone lockByUTZNameIdx(ICFSecISOTZoneByUTZNameIdxKey key) {
		return( cfsec31ISOTZoneRepository.lockByUTZNameIdx(key.getRequiredTZName()));
	}

	/**
	 *	Argument-based lock database instance for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredIso8601
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOTZone> lockByIso8601Idx(@Param("iso8601") String requiredIso8601) {
		return( cfsec31ISOTZoneRepository.lockByIso8601Idx(requiredIso8601));
	}

	/**
	 *	ICFSecISOTZoneByIso8601IdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return A list of locked entities, refreshed from the data store, or an empty list if no such entities exist.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOTZone> lockByIso8601Idx(ICFSecISOTZoneByIso8601IdxKey key) {
		return( cfsec31ISOTZoneRepository.lockByIso8601Idx(key.getRequiredIso8601()));
	}

	// CFSecISOTZone specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOTZoneId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("iSOTZoneId") short requiredISOTZoneId) {
		cfsec31ISOTZoneRepository.deleteByIdIdx(requiredISOTZoneId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTZHourOffset
	 *		@param requiredTZMinOffset
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByOffsetIdx(@Param("tZHourOffset") short requiredTZHourOffset,
		@Param("tZMinOffset") short requiredTZMinOffset) {
		cfsec31ISOTZoneRepository.deleteByOffsetIdx(requiredTZHourOffset,
			requiredTZMinOffset);
	}

	/**
	 *	ICFSecISOTZoneByOffsetIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecISOTZoneByOffsetIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByOffsetIdx(ICFSecISOTZoneByOffsetIdxKey key) {
		cfsec31ISOTZoneRepository.deleteByOffsetIdx(key.getRequiredTZHourOffset(), key.getRequiredTZMinOffset());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredTZName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUTZNameIdx(@Param("tZName") String requiredTZName) {
		cfsec31ISOTZoneRepository.deleteByUTZNameIdx(requiredTZName);
	}

	/**
	 *	ICFSecISOTZoneByUTZNameIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecISOTZoneByUTZNameIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByUTZNameIdx(ICFSecISOTZoneByUTZNameIdxKey key) {
		cfsec31ISOTZoneRepository.deleteByUTZNameIdx(key.getRequiredTZName());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredIso8601
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIso8601Idx(@Param("iso8601") String requiredIso8601) {
		cfsec31ISOTZoneRepository.deleteByIso8601Idx(requiredIso8601);
	}

	/**
	 *	ICFSecISOTZoneByIso8601IdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecISOTZoneByIso8601IdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIso8601Idx(ICFSecISOTZoneByIso8601IdxKey key) {
		cfsec31ISOTZoneRepository.deleteByIso8601Idx(key.getRequiredIso8601());
	}

}

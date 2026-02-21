// Description: Java 25 Spring JPA Service for ISOCcy

/*
 *	io.github.msobkow.CFSec
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

package io.github.msobkow.v3_1.cfsec.cfsec.jpa;

import java.io.Serializable;
import java.math.*;
import java.time.*;
import java.util.*;
import jakarta.persistence.*;
import io.github.msobkow.v3_1.cflib.*;
import io.github.msobkow.v3_1.cflib.dbutil.*;
import io.github.msobkow.v3_1.cflib.xml.CFLibXmlUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import io.github.msobkow.v3_1.cfsec.cfsec.*;

/**
 *	Service for the CFSecISOCcy entities defined in io.github.msobkow.v3_1.cfsec.cfsec.jpa
 *	using the CFSecISOCcyRepository to access them.
 */
@Service("cfsec31JpaISOCcyService")
public class CFSecJpaISOCcyService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaISOCcyRepository cfsec31ISOCcyRepository;

	/**
	 *	Create an entity, generating any database keys required along the way.
	 *
	 *		@param	data	The entity to be instantiated; must be a specific instance of CFSecJpaISOCcy, not a subclass.
	 *
	 *		@return The updated/created entity.
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCcy create(CFSecJpaISOCcy data) {
		final String S_ProcName = "create";
		if (data == null) {
			return( null );
		}
		short originalRequiredISOCcyId = data.getRequiredISOCcyId();
		boolean generatedRequiredISOCcyId = false;
		if(data.getRequiredISOCode() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredISOCode");
		}
		if(data.getRequiredName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredName");
		}
		if( data.getRequiredPrecis() < ICFSecISOCcy.PRECIS_MIN_VALUE ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				S_ProcName,
				0,
				"getRequiredPrecis()",
				data.getRequiredPrecis(),
				ICFSecISOCcy.PRECIS_MIN_VALUE );
		}
		if( data.getRequiredPrecis() > ICFSecISOCcy.PRECIS_MAX_VALUE ) {
			throw new CFLibArgumentOverflowException( getClass(),
				S_ProcName,
				0,
				"getRequiredPrecis()",
				data.getRequiredPrecis(),
				ICFSecISOCcy.PRECIS_MAX_VALUE );
		}
		try {
			LocalDateTime now = LocalDateTime.now();
			data.setCreatedAt(now);
			data.setUpdatedAt(now);
			if(data.getPKey() != null && cfsec31ISOCcyRepository.existsById((Short)data.getPKey())) {
				return( (CFSecJpaISOCcy)(cfsec31ISOCcyRepository.findById((Short)(data.getPKey())).get()));
			}
			return cfsec31ISOCcyRepository.save(data);
		}
		catch(Exception ex) {
				if(generatedRequiredISOCcyId) {
					data.setRequiredISOCcyId(originalRequiredISOCcyId);
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
	public CFSecJpaISOCcy update(CFSecJpaISOCcy data) {
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
		if(data.getRequiredISOCode() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredISOCode");
		}
		if(data.getRequiredName() == null) {
			throw new CFLibNullArgumentException(getClass(),
				S_ProcName,
				0,
				"data.requiredName");
		}
		if( data.getRequiredPrecis() < ICFSecISOCcy.PRECIS_MIN_VALUE ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				S_ProcName,
				0,
				"getRequiredPrecis()",
				data.getRequiredPrecis(),
				ICFSecISOCcy.PRECIS_MIN_VALUE );
		}
		if( data.getRequiredPrecis() > ICFSecISOCcy.PRECIS_MAX_VALUE ) {
			throw new CFLibArgumentOverflowException( getClass(),
				S_ProcName,
				0,
				"getRequiredPrecis()",
				data.getRequiredPrecis(),
				ICFSecISOCcy.PRECIS_MAX_VALUE );
		}
		// Ensure the entity exists and that the revision matches
		CFSecJpaISOCcy existing = cfsec31ISOCcyRepository.findById((Short)(data.getPKey()))
			.orElseThrow(() -> new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey()));
		if (existing.getRequiredRevision() != data.getRequiredRevision()) {
			throw new CFLibCollisionDetectedException(getClass(), S_ProcName, data.getPKey());
		}
		// Apply superior data relationships of CFSecISOCcy to existing object
		// Apply data columns of CFSecISOCcy to existing object
		existing.setRequiredISOCode(data.getRequiredISOCode());
		existing.setRequiredName(data.getRequiredName());
		existing.setOptionalUnitSymbol(data.getOptionalUnitSymbol());
		existing.setRequiredPrecis(data.getRequiredPrecis());
		// Update the audit columns
		data.setUpdatedAt(LocalDateTime.now());
		// Save the changes we've made
		return cfsec31ISOCcyRepository.save(existing);
	}

	/**
	 *	Argument-based find database instance for compatibility with the current MSS code factory code base.
	 *
	 *		@param requiredISOCcyId
	 *
	 *		@return The retrieved entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCcy find(@Param("iSOCcyId") short requiredISOCcyId) {
		return( cfsec31ISOCcyRepository.get(requiredISOCcyId));
	}

	/**
	 *	Retrieve all entities from the repository
	 *
	 *		@return The list of retrieved entities, which may be empty
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public List<CFSecJpaISOCcy> findAll() {
		return( cfsec31ISOCcyRepository.findAll() );
	}

	// CFSecISOCcy specified index finders

	/**
	 *	Find an entity using the columns of the ICFSecISOCcyByCcyCdIdxKey as arguments.
	 *
	 *		@param requiredISOCode
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCcy findByCcyCdIdx(@Param("iSOCode") String requiredISOCode) {
		return( cfsec31ISOCcyRepository.findByCcyCdIdx(requiredISOCode));
	}

	/**
	 *	ICFSecISOCcyByCcyCdIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecISOCcyByCcyCdIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCcy findByCcyCdIdx(ICFSecISOCcyByCcyCdIdxKey key) {
		return( cfsec31ISOCcyRepository.findByCcyCdIdx(key.getRequiredISOCode()));
	}

	/**
	 *	Find an entity using the columns of the ICFSecISOCcyByCcyNmIdxKey as arguments.
	 *
	 *		@param requiredName
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCcy findByCcyNmIdx(@Param("name") String requiredName) {
		return( cfsec31ISOCcyRepository.findByCcyNmIdx(requiredName));
	}

	/**
	 *	ICFSecISOCcyByCcyNmIdxKey entity finder convenience method for object-based access.
	 *
	 *		@param key The ICFSecISOCcyByCcyNmIdxKey instance to use for the query arguments.
	 *
	 *		@return The found entity, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCcy findByCcyNmIdx(ICFSecISOCcyByCcyNmIdxKey key) {
		return( cfsec31ISOCcyRepository.findByCcyNmIdx(key.getRequiredName()));
	}

	// CFSecISOCcy specified lock-by-index methods

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCcyId
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCcy lockByIdIdx(@Param("iSOCcyId") short requiredISOCcyId) {
		return( cfsec31ISOCcyRepository.lockByIdIdx(requiredISOCcyId));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCode
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCcy lockByCcyCdIdx(@Param("iSOCode") String requiredISOCode) {
		return( cfsec31ISOCcyRepository.lockByCcyCdIdx(requiredISOCode));
	}

	/**
	 *	ICFSecISOCcyByCcyCdIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCcy lockByCcyCdIdx(ICFSecISOCcyByCcyCdIdxKey key) {
		return( cfsec31ISOCcyRepository.lockByCcyCdIdx(key.getRequiredISOCode()));
	}

	/**
	 *	Argument-based lock database entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity locks, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredName
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCcy lockByCcyNmIdx(@Param("name") String requiredName) {
		return( cfsec31ISOCcyRepository.lockByCcyNmIdx(requiredName));
	}

	/**
	 *	ICFSecISOCcyByCcyNmIdxKey based lock method for object-based access.
	 *
	 *		@param key The key of the entity to be locked.
	 *
	 *		@return The locked entity, refreshed from the data store, or null if no such entity exists.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public CFSecJpaISOCcy lockByCcyNmIdx(ICFSecISOCcyByCcyNmIdxKey key) {
		return( cfsec31ISOCcyRepository.lockByCcyNmIdx(key.getRequiredName()));
	}

	// CFSecISOCcy specified delete-by-index methods

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCcyId
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByIdIdx(@Param("iSOCcyId") short requiredISOCcyId) {
		cfsec31ISOCcyRepository.deleteByIdIdx(requiredISOCcyId);
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredISOCode
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByCcyCdIdx(@Param("iSOCode") String requiredISOCode) {
		cfsec31ISOCcyRepository.deleteByCcyCdIdx(requiredISOCode);
	}

	/**
	 *	ICFSecISOCcyByCcyCdIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecISOCcyByCcyCdIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByCcyCdIdx(ICFSecISOCcyByCcyCdIdxKey key) {
		cfsec31ISOCcyRepository.deleteByCcyCdIdx(key.getRequiredISOCode());
	}

	/**
	 *	Argument-based delete entity for compatibility with the current MSS code factory code base, uses @Transactional to acquire a JPA entity lock, which may or may not imply an actual database lock during the transaction.
	 *
	 *		@param requiredName
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByCcyNmIdx(@Param("name") String requiredName) {
		cfsec31ISOCcyRepository.deleteByCcyNmIdx(requiredName);
	}

	/**
	 *	ICFSecISOCcyByCcyNmIdxKey based lock method for object-based access.
	 *
	 *		@param key The ICFSecISOCcyByCcyNmIdxKey of the entity to be locked.
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void deleteByCcyNmIdx(ICFSecISOCcyByCcyNmIdxKey key) {
		cfsec31ISOCcyRepository.deleteByCcyNmIdx(key.getRequiredName());
	}
}


// Description: Java 25 DbIO implementation for SecUserPWHistory.

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

import java.lang.reflect.*;
import java.net.*;
import java.rmi.*;
import java.sql.*;
import java.text.*;
import java.time.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfsec.cfsecobj.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.jpa.CFSecJpaHooksSchema;

/*
 *	CFSecJpaSecUserPWHistoryTable database implementation for SecUserPWHistory
 */
public class CFSecJpaSecUserPWHistoryTable implements ICFSecSecUserPWHistoryTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaSecUserPWHistoryTable(ICFSecSchema schema) {
		if( schema == null ) {
			throw new CFLibNullArgumentException(getClass(), "constructor", 1, "schema" );
		}
		if (schema instanceof CFSecJpaSchema) {
			this.schema = (CFSecJpaSchema)schema;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "constructor", "schema", schema, "CFSecJpaSchema");
		}
	}

	/**
	 *	Create the instance in the database, and update the specified record
	 *	with the assigned primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	rec	The instance interface to be created.
	 */
	@Override
	public ICFSecSecUserPWHistory createSecUserPWHistory( ICFSecAuthorization Authorization,
		ICFSecSecUserPWHistory rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createSecUserPWHistory", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecUserPWHistory) {
			CFSecJpaSecUserPWHistory jparec = (CFSecJpaSecUserPWHistory)rec;
			CFSecJpaSecUserPWHistory created = schema.getJpaHooksSchema().getSecUserPWHistoryService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createSecUserPWHistory", "rec", rec, "CFSecJpaSecUserPWHistory");
		}
	}

	/**
	 *	Update the instance in the database, and update the specified record
	 *	with any calculated changes imposed by the associated stored procedure.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	rec	The instance interface to be updated
	 */
	@Override
	public ICFSecSecUserPWHistory updateSecUserPWHistory( ICFSecAuthorization Authorization,
		ICFSecSecUserPWHistory rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateSecUserPWHistory", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecUserPWHistory) {
			CFSecJpaSecUserPWHistory jparec = (CFSecJpaSecUserPWHistory)rec;
			CFSecJpaSecUserPWHistory updated = schema.getJpaHooksSchema().getSecUserPWHistoryService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateSecUserPWHistory", "rec", rec, "CFSecJpaSecUserPWHistory");
		}
	}

	/**
	 *	Delete the instance from the database.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	rec	The instance interface to be deleted.
	 */
	@Override
	public void deleteSecUserPWHistory( ICFSecAuthorization Authorization,
		ICFSecSecUserPWHistory rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaSecUserPWHistory) {
			CFSecJpaSecUserPWHistory jparec = (CFSecJpaSecUserPWHistory)rec;
			schema.getJpaHooksSchema().getSecUserPWHistoryService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteSecUserPWHistory", "rec", rec, "CFSecJpaSecUserPWHistory");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteSecUserPWHistory");
	}

	/**
	 *	Delete the SecUserPWHistory instance identified by the primary key attributes.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecUserPWHistory key attribute of the instance generating the id.
	 *
	 *	@param	PWSetStamp	The SecUserPWHistory key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecUserPWHistoryByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		LocalDateTime argPWSetStamp )
	{
		schema.getJpaHooksSchema().getSecUserPWHistoryService().deleteByIdIdx(argSecUserId,
		argPWSetStamp);
	}

	/**
	 *	Delete the SecUserPWHistory instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteSecUserPWHistoryByIdIdx( ICFSecAuthorization Authorization,
		ICFSecSecUserPWHistoryPKey argKey )
	{
		schema.getJpaHooksSchema().getSecUserPWHistoryService().deleteByIdIdx(argKey.getRequiredSecUserId(),
			argKey.getRequiredPWSetStamp());
	}

	/**
	 *	Delete the SecUserPWHistory instances identified by the key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecUserPWHistory key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecUserPWHistoryByUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId )
	{
		schema.getJpaHooksSchema().getSecUserPWHistoryService().deleteByUserIdx(argSecUserId);
	}


	/**
	 *	Delete the SecUserPWHistory instances identified by the key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecUserPWHistoryByUserIdx( ICFSecAuthorization Authorization,
		ICFSecSecUserPWHistoryByUserIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecUserPWHistoryService().deleteByUserIdx(argKey.getRequiredSecUserId());
	}

	/**
	 *	Delete the SecUserPWHistory instances identified by the key SetStampIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PWSetStamp	The SecUserPWHistory key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecUserPWHistoryBySetStampIdx( ICFSecAuthorization Authorization,
		LocalDateTime argPWSetStamp )
	{
		schema.getJpaHooksSchema().getSecUserPWHistoryService().deleteBySetStampIdx(argPWSetStamp);
	}


	/**
	 *	Delete the SecUserPWHistory instances identified by the key SetStampIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecUserPWHistoryBySetStampIdx( ICFSecAuthorization Authorization,
		ICFSecSecUserPWHistoryBySetStampIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecUserPWHistoryService().deleteBySetStampIdx(argKey.getRequiredPWSetStamp());
	}

	/**
	 *	Delete the SecUserPWHistory instances identified by the key ReplacedStampIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PWReplacedStamp	The SecUserPWHistory key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecUserPWHistoryByReplacedStampIdx( ICFSecAuthorization Authorization,
		LocalDateTime argPWReplacedStamp )
	{
		schema.getJpaHooksSchema().getSecUserPWHistoryService().deleteByReplacedStampIdx(argPWReplacedStamp);
	}


	/**
	 *	Delete the SecUserPWHistory instances identified by the key ReplacedStampIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecUserPWHistoryByReplacedStampIdx( ICFSecAuthorization Authorization,
		ICFSecSecUserPWHistoryByReplacedStampIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecUserPWHistoryService().deleteByReplacedStampIdx(argKey.getRequiredPWReplacedStamp());
	}


	/**
	 *	Read the derived SecUserPWHistory record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecUserPWHistory instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecUserPWHistory readDerived( ICFSecAuthorization Authorization,
		ICFSecSecUserPWHistoryPKey PKey )
	{
		return( schema.getJpaHooksSchema().getSecUserPWHistoryService().find(PKey) );
	}

	/**
	 *	Read the derived SecUserPWHistory record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecUserPWHistory readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		LocalDateTime argPWSetStamp )
	{
		return( schema.getJpaHooksSchema().getSecUserPWHistoryService().find(argSecUserId,
		argPWSetStamp) );
	}

	/**
	 *	Lock the derived SecUserPWHistory record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecUserPWHistory instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecUserPWHistory lockDerived( ICFSecAuthorization Authorization,
		ICFSecSecUserPWHistoryPKey PKey )
	{
		return( schema.getJpaHooksSchema().getSecUserPWHistoryService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all SecUserPWHistory instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecUserPWHistory[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaSecUserPWHistory> results = schema.getJpaHooksSchema().getSecUserPWHistoryService().findAll();
		ICFSecSecUserPWHistory[] retset = new ICFSecSecUserPWHistory[results.size()];
		int idx = 0;
		for (CFSecJpaSecUserPWHistory cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SecUserPWHistory record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecUserPWHistory key attribute of the instance generating the id.
	 *
	 *	@param	PWSetStamp	The SecUserPWHistory key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecUserPWHistory readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		LocalDateTime argPWSetStamp )
	{
		return( schema.getJpaHooksSchema().getSecUserPWHistoryService().find(argSecUserId,
		argPWSetStamp) );
	}

	/**
	 *	Read the derived SecUserPWHistory record instance identified by the unique key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecUserPWHistory key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecUserPWHistory readDerivedByUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId )
	{
		return( schema.getJpaHooksSchema().getSecUserPWHistoryService().findByUserIdx(argSecUserId) );
	}

	/**
	 *	Read the derived SecUserPWHistory record instance identified by the unique key SetStampIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PWSetStamp	The SecUserPWHistory key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecUserPWHistory readDerivedBySetStampIdx( ICFSecAuthorization Authorization,
		LocalDateTime argPWSetStamp )
	{
		return( schema.getJpaHooksSchema().getSecUserPWHistoryService().findBySetStampIdx(argPWSetStamp) );
	}

	/**
	 *	Read the derived SecUserPWHistory record instance identified by the unique key ReplacedStampIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PWReplacedStamp	The SecUserPWHistory key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecUserPWHistory readDerivedByReplacedStampIdx( ICFSecAuthorization Authorization,
		LocalDateTime argPWReplacedStamp )
	{
		return( schema.getJpaHooksSchema().getSecUserPWHistoryService().findByReplacedStampIdx(argPWReplacedStamp) );
	}

	/**
	 *	Read the specific SecUserPWHistory record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecUserPWHistory instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecUserPWHistory readRec( ICFSecAuthorization Authorization,
		ICFSecSecUserPWHistoryPKey PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Read the specific SecUserPWHistory record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecUserPWHistory instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecUserPWHistory readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		LocalDateTime argPWSetStamp )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec-args");
	}

	/**
	 *	Lock the specific SecUserPWHistory record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecUserPWHistory instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecUserPWHistory lockRec( ICFSecAuthorization Authorization,
		ICFSecSecUserPWHistoryPKey PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific SecUserPWHistory record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecUserPWHistory instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecUserPWHistory[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific SecUserPWHistory record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecUserPWHistory key attribute of the instance generating the id.
	 *
	 *	@param	PWSetStamp	The SecUserPWHistory key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecUserPWHistory readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		LocalDateTime argPWSetStamp )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read the specific SecUserPWHistory record instance identified by the unique key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecUserPWHistory key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecUserPWHistory readRecByUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUserIdx");
	}

	/**
	 *	Read the specific SecUserPWHistory record instance identified by the unique key SetStampIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PWSetStamp	The SecUserPWHistory key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecUserPWHistory readRecBySetStampIdx( ICFSecAuthorization Authorization,
		LocalDateTime argPWSetStamp )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecBySetStampIdx");
	}

	/**
	 *	Read the specific SecUserPWHistory record instance identified by the unique key ReplacedStampIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PWReplacedStamp	The SecUserPWHistory key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecUserPWHistory readRecByReplacedStampIdx( ICFSecAuthorization Authorization,
		LocalDateTime argPWReplacedStamp )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByReplacedStampIdx");
	}
}

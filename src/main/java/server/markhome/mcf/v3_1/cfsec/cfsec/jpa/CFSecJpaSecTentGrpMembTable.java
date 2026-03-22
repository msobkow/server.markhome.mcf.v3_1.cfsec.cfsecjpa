
// Description: Java 25 DbIO implementation for SecTentGrpMemb.

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
 *	CFSecJpaSecTentGrpMembTable database implementation for SecTentGrpMemb
 */
public class CFSecJpaSecTentGrpMembTable implements ICFSecSecTentGrpMembTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaSecTentGrpMembTable(ICFSecSchema schema) {
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
	public ICFSecSecTentGrpMemb createSecTentGrpMemb( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpMemb rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createSecTentGrpMemb", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecTentGrpMemb) {
			CFSecJpaSecTentGrpMemb jparec = (CFSecJpaSecTentGrpMemb)rec;
			CFSecJpaSecTentGrpMemb created = schema.getJpaHooksSchema().getSecTentGrpMembService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createSecTentGrpMemb", "rec", rec, "CFSecJpaSecTentGrpMemb");
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
	public ICFSecSecTentGrpMemb updateSecTentGrpMemb( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpMemb rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateSecTentGrpMemb", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecTentGrpMemb) {
			CFSecJpaSecTentGrpMemb jparec = (CFSecJpaSecTentGrpMemb)rec;
			CFSecJpaSecTentGrpMemb updated = schema.getJpaHooksSchema().getSecTentGrpMembService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateSecTentGrpMemb", "rec", rec, "CFSecJpaSecTentGrpMemb");
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
	public void deleteSecTentGrpMemb( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpMemb rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaSecTentGrpMemb) {
			CFSecJpaSecTentGrpMemb jparec = (CFSecJpaSecTentGrpMemb)rec;
			schema.getJpaHooksSchema().getSecTentGrpMembService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteSecTentGrpMemb", "rec", rec, "CFSecJpaSecTentGrpMemb");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteSecTentGrpMemb");
	}

	/**
	 *	Delete the SecTentGrpMemb instance identified by the primary key attributes.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecTentGrpId	The SecTentGrpMemb key attribute of the instance generating the id.
	 *
	 *	@param	SecUserId	The SecTentGrpMemb key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecTentGrpMembByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecTentGrpId,
		CFLibDbKeyHash256 argSecUserId )
	{
		schema.getJpaHooksSchema().getSecTentGrpMembService().deleteByIdIdx(argSecTentGrpId,
		argSecUserId);
	}

	/**
	 *	Delete the SecTentGrpMemb instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteSecTentGrpMembByIdIdx( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpMembPKey argKey )
	{
		schema.getJpaHooksSchema().getSecTentGrpMembService().deleteByIdIdx(argKey.getRequiredSecTentGrpId(),
			argKey.getRequiredSecUserId());
	}

	/**
	 *	Delete the SecTentGrpMemb instances identified by the key TentGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecTentGrpId	The SecTentGrpMemb key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecTentGrpMembByTentGrpIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecTentGrpId )
	{
		schema.getJpaHooksSchema().getSecTentGrpMembService().deleteByTentGrpIdx(argSecTentGrpId);
	}


	/**
	 *	Delete the SecTentGrpMemb instances identified by the key TentGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecTentGrpMembByTentGrpIdx( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpMembByTentGrpIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecTentGrpMembService().deleteByTentGrpIdx(argKey.getRequiredSecTentGrpId());
	}

	/**
	 *	Delete the SecTentGrpMemb instances identified by the key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecTentGrpMemb key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecTentGrpMembByUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId )
	{
		schema.getJpaHooksSchema().getSecTentGrpMembService().deleteByUserIdx(argSecUserId);
	}


	/**
	 *	Delete the SecTentGrpMemb instances identified by the key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecTentGrpMembByUserIdx( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpMembByUserIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecTentGrpMembService().deleteByUserIdx(argKey.getRequiredSecUserId());
	}


	/**
	 *	Read the derived SecTentGrpMemb record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecTentGrpMemb instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecTentGrpMemb readDerived( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpMembPKey PKey )
	{
		return( schema.getJpaHooksSchema().getSecTentGrpMembService().find(PKey) );
	}

	/**
	 *	Read the derived SecTentGrpMemb record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecTentGrpMemb readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecTentGrpId,
		CFLibDbKeyHash256 argSecUserId )
	{
		return( schema.getJpaHooksSchema().getSecTentGrpMembService().find(argSecTentGrpId,
		argSecUserId) );
	}

	/**
	 *	Lock the derived SecTentGrpMemb record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecTentGrpMemb instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecTentGrpMemb lockDerived( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpMembPKey PKey )
	{
		return( schema.getJpaHooksSchema().getSecTentGrpMembService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all SecTentGrpMemb instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecTentGrpMemb[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaSecTentGrpMemb> results = schema.getJpaHooksSchema().getSecTentGrpMembService().findAll();
		ICFSecSecTentGrpMemb[] retset = new ICFSecSecTentGrpMemb[results.size()];
		int idx = 0;
		for (CFSecJpaSecTentGrpMemb cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SecTentGrpMemb record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecTentGrpId	The SecTentGrpMemb key attribute of the instance generating the id.
	 *
	 *	@param	SecUserId	The SecTentGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecTentGrpMemb readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecTentGrpId,
		CFLibDbKeyHash256 argSecUserId )
	{
		return( schema.getJpaHooksSchema().getSecTentGrpMembService().find(argSecTentGrpId,
		argSecUserId) );
	}

	/**
	 *	Read an array of the derived SecTentGrpMemb record instances identified by the duplicate key TentGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecTentGrpId	The SecTentGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecTentGrpMemb[] readDerivedByTentGrpIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecTentGrpId )
	{
		List<CFSecJpaSecTentGrpMemb> results = schema.getJpaHooksSchema().getSecTentGrpMembService().findByTentGrpIdx(argSecTentGrpId);
		ICFSecSecTentGrpMemb[] retset = new ICFSecSecTentGrpMemb[results.size()];
		int idx = 0;
		for (CFSecJpaSecTentGrpMemb cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived SecTentGrpMemb record instances identified by the duplicate key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecTentGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecTentGrpMemb[] readDerivedByUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId )
	{
		List<CFSecJpaSecTentGrpMemb> results = schema.getJpaHooksSchema().getSecTentGrpMembService().findByUserIdx(argSecUserId);
		ICFSecSecTentGrpMemb[] retset = new ICFSecSecTentGrpMemb[results.size()];
		int idx = 0;
		for (CFSecJpaSecTentGrpMemb cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific SecTentGrpMemb record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecTentGrpMemb instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrpMemb readRec( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpMembPKey PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Read the specific SecTentGrpMemb record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecTentGrpMemb instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrpMemb readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecTentGrpId,
		CFLibDbKeyHash256 argSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec-args");
	}

	/**
	 *	Lock the specific SecTentGrpMemb record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecTentGrpMemb instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrpMemb lockRec( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpMembPKey PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific SecTentGrpMemb record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecTentGrpMemb instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecTentGrpMemb[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read a page of all the specific SecTentGrpMemb record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecTentGrpMemb instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecTentGrpMemb[] pageAllRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 priorSecTentGrpId,
		CFLibDbKeyHash256 priorSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageAllRec");
	}

	/**
	 *	Read the specific SecTentGrpMemb record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecTentGrpId	The SecTentGrpMemb key attribute of the instance generating the id.
	 *
	 *	@param	SecUserId	The SecTentGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrpMemb readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecTentGrpId,
		CFLibDbKeyHash256 argSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific SecTentGrpMemb record instances identified by the duplicate key TentGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecTentGrpId	The SecTentGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrpMemb[] readRecByTentGrpIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecTentGrpId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByTentGrpIdx");
	}

	/**
	 *	Read an array of the specific SecTentGrpMemb record instances identified by the duplicate key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecTentGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrpMemb[] readRecByUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUserIdx");
	}

	/**
	 *	Read a page array of the specific SecTentGrpMemb record instances identified by the duplicate key TentGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecTentGrpId	The SecTentGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrpMemb[] pageRecByTentGrpIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecTentGrpId,
		CFLibDbKeyHash256 priorSecTentGrpId,
		CFLibDbKeyHash256 priorSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByTentGrpIdx");
	}

	/**
	 *	Read a page array of the specific SecTentGrpMemb record instances identified by the duplicate key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecTentGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrpMemb[] pageRecByUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		CFLibDbKeyHash256 priorSecTentGrpId,
		CFLibDbKeyHash256 priorSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByUserIdx");
	}
}

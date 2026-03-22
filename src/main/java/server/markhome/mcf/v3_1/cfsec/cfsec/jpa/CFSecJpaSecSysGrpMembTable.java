
// Description: Java 25 DbIO implementation for SecSysGrpMemb.

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
 *	CFSecJpaSecSysGrpMembTable database implementation for SecSysGrpMemb
 */
public class CFSecJpaSecSysGrpMembTable implements ICFSecSecSysGrpMembTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaSecSysGrpMembTable(ICFSecSchema schema) {
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
	public ICFSecSecSysGrpMemb createSecSysGrpMemb( ICFSecAuthorization Authorization,
		ICFSecSecSysGrpMemb rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createSecSysGrpMemb", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecSysGrpMemb) {
			CFSecJpaSecSysGrpMemb jparec = (CFSecJpaSecSysGrpMemb)rec;
			CFSecJpaSecSysGrpMemb created = schema.getJpaHooksSchema().getSecSysGrpMembService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createSecSysGrpMemb", "rec", rec, "CFSecJpaSecSysGrpMemb");
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
	public ICFSecSecSysGrpMemb updateSecSysGrpMemb( ICFSecAuthorization Authorization,
		ICFSecSecSysGrpMemb rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateSecSysGrpMemb", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecSysGrpMemb) {
			CFSecJpaSecSysGrpMemb jparec = (CFSecJpaSecSysGrpMemb)rec;
			CFSecJpaSecSysGrpMemb updated = schema.getJpaHooksSchema().getSecSysGrpMembService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateSecSysGrpMemb", "rec", rec, "CFSecJpaSecSysGrpMemb");
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
	public void deleteSecSysGrpMemb( ICFSecAuthorization Authorization,
		ICFSecSecSysGrpMemb rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaSecSysGrpMemb) {
			CFSecJpaSecSysGrpMemb jparec = (CFSecJpaSecSysGrpMemb)rec;
			schema.getJpaHooksSchema().getSecSysGrpMembService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteSecSysGrpMemb", "rec", rec, "CFSecJpaSecSysGrpMemb");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteSecSysGrpMemb");
	}

	/**
	 *	Delete the SecSysGrpMemb instance identified by the primary key attributes.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecSysGrpId	The SecSysGrpMemb key attribute of the instance generating the id.
	 *
	 *	@param	SecUserId	The SecSysGrpMemb key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecSysGrpMembByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecSysGrpId,
		CFLibDbKeyHash256 argSecUserId )
	{
		schema.getJpaHooksSchema().getSecSysGrpMembService().deleteByIdIdx(argSecSysGrpId,
		argSecUserId);
	}

	/**
	 *	Delete the SecSysGrpMemb instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteSecSysGrpMembByIdIdx( ICFSecAuthorization Authorization,
		ICFSecSecSysGrpMembPKey argKey )
	{
		schema.getJpaHooksSchema().getSecSysGrpMembService().deleteByIdIdx(argKey.getRequiredSecSysGrpId(),
			argKey.getRequiredSecUserId());
	}

	/**
	 *	Delete the SecSysGrpMemb instances identified by the key SysGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecSysGrpId	The SecSysGrpMemb key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecSysGrpMembBySysGrpIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecSysGrpId )
	{
		schema.getJpaHooksSchema().getSecSysGrpMembService().deleteBySysGrpIdx(argSecSysGrpId);
	}


	/**
	 *	Delete the SecSysGrpMemb instances identified by the key SysGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecSysGrpMembBySysGrpIdx( ICFSecAuthorization Authorization,
		ICFSecSecSysGrpMembBySysGrpIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecSysGrpMembService().deleteBySysGrpIdx(argKey.getRequiredSecSysGrpId());
	}

	/**
	 *	Delete the SecSysGrpMemb instances identified by the key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecSysGrpMemb key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecSysGrpMembByUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId )
	{
		schema.getJpaHooksSchema().getSecSysGrpMembService().deleteByUserIdx(argSecUserId);
	}


	/**
	 *	Delete the SecSysGrpMemb instances identified by the key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecSysGrpMembByUserIdx( ICFSecAuthorization Authorization,
		ICFSecSecSysGrpMembByUserIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecSysGrpMembService().deleteByUserIdx(argKey.getRequiredSecUserId());
	}


	/**
	 *	Read the derived SecSysGrpMemb record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecSysGrpMemb instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecSysGrpMemb readDerived( ICFSecAuthorization Authorization,
		ICFSecSecSysGrpMembPKey PKey )
	{
		return( schema.getJpaHooksSchema().getSecSysGrpMembService().find(PKey) );
	}

	/**
	 *	Read the derived SecSysGrpMemb record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecSysGrpMemb readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecSysGrpId,
		CFLibDbKeyHash256 argSecUserId )
	{
		return( schema.getJpaHooksSchema().getSecSysGrpMembService().find(argSecSysGrpId,
		argSecUserId) );
	}

	/**
	 *	Lock the derived SecSysGrpMemb record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecSysGrpMemb instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecSysGrpMemb lockDerived( ICFSecAuthorization Authorization,
		ICFSecSecSysGrpMembPKey PKey )
	{
		return( schema.getJpaHooksSchema().getSecSysGrpMembService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all SecSysGrpMemb instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecSysGrpMemb[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaSecSysGrpMemb> results = schema.getJpaHooksSchema().getSecSysGrpMembService().findAll();
		ICFSecSecSysGrpMemb[] retset = new ICFSecSecSysGrpMemb[results.size()];
		int idx = 0;
		for (CFSecJpaSecSysGrpMemb cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SecSysGrpMemb record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecSysGrpId	The SecSysGrpMemb key attribute of the instance generating the id.
	 *
	 *	@param	SecUserId	The SecSysGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecSysGrpMemb readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecSysGrpId,
		CFLibDbKeyHash256 argSecUserId )
	{
		return( schema.getJpaHooksSchema().getSecSysGrpMembService().find(argSecSysGrpId,
		argSecUserId) );
	}

	/**
	 *	Read an array of the derived SecSysGrpMemb record instances identified by the duplicate key SysGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecSysGrpId	The SecSysGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecSysGrpMemb[] readDerivedBySysGrpIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecSysGrpId )
	{
		List<CFSecJpaSecSysGrpMemb> results = schema.getJpaHooksSchema().getSecSysGrpMembService().findBySysGrpIdx(argSecSysGrpId);
		ICFSecSecSysGrpMemb[] retset = new ICFSecSecSysGrpMemb[results.size()];
		int idx = 0;
		for (CFSecJpaSecSysGrpMemb cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived SecSysGrpMemb record instances identified by the duplicate key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecSysGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecSysGrpMemb[] readDerivedByUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId )
	{
		List<CFSecJpaSecSysGrpMemb> results = schema.getJpaHooksSchema().getSecSysGrpMembService().findByUserIdx(argSecUserId);
		ICFSecSecSysGrpMemb[] retset = new ICFSecSecSysGrpMemb[results.size()];
		int idx = 0;
		for (CFSecJpaSecSysGrpMemb cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific SecSysGrpMemb record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecSysGrpMemb instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSysGrpMemb readRec( ICFSecAuthorization Authorization,
		ICFSecSecSysGrpMembPKey PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Read the specific SecSysGrpMemb record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecSysGrpMemb instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSysGrpMemb readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecSysGrpId,
		CFLibDbKeyHash256 argSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec-args");
	}

	/**
	 *	Lock the specific SecSysGrpMemb record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecSysGrpMemb instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSysGrpMemb lockRec( ICFSecAuthorization Authorization,
		ICFSecSecSysGrpMembPKey PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific SecSysGrpMemb record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecSysGrpMemb instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecSysGrpMemb[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read a page of all the specific SecSysGrpMemb record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecSysGrpMemb instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecSysGrpMemb[] pageAllRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 priorSecSysGrpId,
		CFLibDbKeyHash256 priorSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageAllRec");
	}

	/**
	 *	Read the specific SecSysGrpMemb record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecSysGrpId	The SecSysGrpMemb key attribute of the instance generating the id.
	 *
	 *	@param	SecUserId	The SecSysGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSysGrpMemb readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecSysGrpId,
		CFLibDbKeyHash256 argSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific SecSysGrpMemb record instances identified by the duplicate key SysGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecSysGrpId	The SecSysGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSysGrpMemb[] readRecBySysGrpIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecSysGrpId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecBySysGrpIdx");
	}

	/**
	 *	Read an array of the specific SecSysGrpMemb record instances identified by the duplicate key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecSysGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSysGrpMemb[] readRecByUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUserIdx");
	}

	/**
	 *	Read a page array of the specific SecSysGrpMemb record instances identified by the duplicate key SysGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecSysGrpId	The SecSysGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSysGrpMemb[] pageRecBySysGrpIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecSysGrpId,
		CFLibDbKeyHash256 priorSecSysGrpId,
		CFLibDbKeyHash256 priorSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecBySysGrpIdx");
	}

	/**
	 *	Read a page array of the specific SecSysGrpMemb record instances identified by the duplicate key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecSysGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSysGrpMemb[] pageRecByUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		CFLibDbKeyHash256 priorSecSysGrpId,
		CFLibDbKeyHash256 priorSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByUserIdx");
	}
}

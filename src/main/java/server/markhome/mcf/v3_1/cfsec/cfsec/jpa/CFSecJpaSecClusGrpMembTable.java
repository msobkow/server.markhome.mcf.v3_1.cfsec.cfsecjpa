
// Description: Java 25 DbIO implementation for SecClusGrpMemb.

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
 *	CFSecJpaSecClusGrpMembTable database implementation for SecClusGrpMemb
 */
public class CFSecJpaSecClusGrpMembTable implements ICFSecSecClusGrpMembTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaSecClusGrpMembTable(ICFSecSchema schema) {
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
	public ICFSecSecClusGrpMemb createSecClusGrpMemb( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpMemb rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createSecClusGrpMemb", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecClusGrpMemb) {
			CFSecJpaSecClusGrpMemb jparec = (CFSecJpaSecClusGrpMemb)rec;
			CFSecJpaSecClusGrpMemb created = schema.getJpaHooksSchema().getSecClusGrpMembService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createSecClusGrpMemb", "rec", rec, "CFSecJpaSecClusGrpMemb");
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
	public ICFSecSecClusGrpMemb updateSecClusGrpMemb( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpMemb rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateSecClusGrpMemb", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecClusGrpMemb) {
			CFSecJpaSecClusGrpMemb jparec = (CFSecJpaSecClusGrpMemb)rec;
			CFSecJpaSecClusGrpMemb updated = schema.getJpaHooksSchema().getSecClusGrpMembService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateSecClusGrpMemb", "rec", rec, "CFSecJpaSecClusGrpMemb");
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
	public void deleteSecClusGrpMemb( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpMemb rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaSecClusGrpMemb) {
			CFSecJpaSecClusGrpMemb jparec = (CFSecJpaSecClusGrpMemb)rec;
			schema.getJpaHooksSchema().getSecClusGrpMembService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteSecClusGrpMemb", "rec", rec, "CFSecJpaSecClusGrpMemb");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteSecClusGrpMemb");
	}

	/**
	 *	Delete the SecClusGrpMemb instance identified by the primary key attributes.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecClusGrpId	The SecClusGrpMemb key attribute of the instance generating the id.
	 *
	 *	@param	SecUserId	The SecClusGrpMemb key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecClusGrpMembByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecClusGrpId,
		CFLibDbKeyHash256 argSecUserId )
	{
		schema.getJpaHooksSchema().getSecClusGrpMembService().deleteByIdIdx(argSecClusGrpId,
		argSecUserId);
	}

	/**
	 *	Delete the SecClusGrpMemb instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteSecClusGrpMembByIdIdx( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpMembPKey argKey )
	{
		schema.getJpaHooksSchema().getSecClusGrpMembService().deleteByIdIdx(argKey.getRequiredSecClusGrpId(),
			argKey.getRequiredSecUserId());
	}

	/**
	 *	Delete the SecClusGrpMemb instances identified by the key ClusGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecClusGrpId	The SecClusGrpMemb key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecClusGrpMembByClusGrpIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecClusGrpId )
	{
		schema.getJpaHooksSchema().getSecClusGrpMembService().deleteByClusGrpIdx(argSecClusGrpId);
	}


	/**
	 *	Delete the SecClusGrpMemb instances identified by the key ClusGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecClusGrpMembByClusGrpIdx( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpMembByClusGrpIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecClusGrpMembService().deleteByClusGrpIdx(argKey.getRequiredSecClusGrpId());
	}

	/**
	 *	Delete the SecClusGrpMemb instances identified by the key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecClusGrpMemb key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecClusGrpMembByUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId )
	{
		schema.getJpaHooksSchema().getSecClusGrpMembService().deleteByUserIdx(argSecUserId);
	}


	/**
	 *	Delete the SecClusGrpMemb instances identified by the key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecClusGrpMembByUserIdx( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpMembByUserIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecClusGrpMembService().deleteByUserIdx(argKey.getRequiredSecUserId());
	}


	/**
	 *	Read the derived SecClusGrpMemb record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecClusGrpMemb instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecClusGrpMemb readDerived( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpMembPKey PKey )
	{
		return( schema.getJpaHooksSchema().getSecClusGrpMembService().find(PKey) );
	}

	/**
	 *	Read the derived SecClusGrpMemb record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecClusGrpMemb readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecClusGrpId,
		CFLibDbKeyHash256 argSecUserId )
	{
		return( schema.getJpaHooksSchema().getSecClusGrpMembService().find(argSecClusGrpId,
		argSecUserId) );
	}

	/**
	 *	Lock the derived SecClusGrpMemb record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecClusGrpMemb instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecClusGrpMemb lockDerived( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpMembPKey PKey )
	{
		return( schema.getJpaHooksSchema().getSecClusGrpMembService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all SecClusGrpMemb instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecClusGrpMemb[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaSecClusGrpMemb> results = schema.getJpaHooksSchema().getSecClusGrpMembService().findAll();
		ICFSecSecClusGrpMemb[] retset = new ICFSecSecClusGrpMemb[results.size()];
		int idx = 0;
		for (CFSecJpaSecClusGrpMemb cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SecClusGrpMemb record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecClusGrpId	The SecClusGrpMemb key attribute of the instance generating the id.
	 *
	 *	@param	SecUserId	The SecClusGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecClusGrpMemb readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecClusGrpId,
		CFLibDbKeyHash256 argSecUserId )
	{
		return( schema.getJpaHooksSchema().getSecClusGrpMembService().find(argSecClusGrpId,
		argSecUserId) );
	}

	/**
	 *	Read an array of the derived SecClusGrpMemb record instances identified by the duplicate key ClusGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecClusGrpId	The SecClusGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecClusGrpMemb[] readDerivedByClusGrpIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecClusGrpId )
	{
		List<CFSecJpaSecClusGrpMemb> results = schema.getJpaHooksSchema().getSecClusGrpMembService().findByClusGrpIdx(argSecClusGrpId);
		ICFSecSecClusGrpMemb[] retset = new ICFSecSecClusGrpMemb[results.size()];
		int idx = 0;
		for (CFSecJpaSecClusGrpMemb cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived SecClusGrpMemb record instances identified by the duplicate key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecClusGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecClusGrpMemb[] readDerivedByUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId )
	{
		List<CFSecJpaSecClusGrpMemb> results = schema.getJpaHooksSchema().getSecClusGrpMembService().findByUserIdx(argSecUserId);
		ICFSecSecClusGrpMemb[] retset = new ICFSecSecClusGrpMemb[results.size()];
		int idx = 0;
		for (CFSecJpaSecClusGrpMemb cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific SecClusGrpMemb record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecClusGrpMemb instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrpMemb readRec( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpMembPKey PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Read the specific SecClusGrpMemb record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecClusGrpMemb instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrpMemb readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecClusGrpId,
		CFLibDbKeyHash256 argSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec-args");
	}

	/**
	 *	Lock the specific SecClusGrpMemb record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecClusGrpMemb instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrpMemb lockRec( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpMembPKey PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific SecClusGrpMemb record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecClusGrpMemb instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecClusGrpMemb[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read a page of all the specific SecClusGrpMemb record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecClusGrpMemb instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecClusGrpMemb[] pageAllRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 priorSecClusGrpId,
		CFLibDbKeyHash256 priorSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageAllRec");
	}

	/**
	 *	Read the specific SecClusGrpMemb record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecClusGrpId	The SecClusGrpMemb key attribute of the instance generating the id.
	 *
	 *	@param	SecUserId	The SecClusGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrpMemb readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecClusGrpId,
		CFLibDbKeyHash256 argSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific SecClusGrpMemb record instances identified by the duplicate key ClusGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecClusGrpId	The SecClusGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrpMemb[] readRecByClusGrpIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecClusGrpId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByClusGrpIdx");
	}

	/**
	 *	Read an array of the specific SecClusGrpMemb record instances identified by the duplicate key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecClusGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrpMemb[] readRecByUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUserIdx");
	}

	/**
	 *	Read a page array of the specific SecClusGrpMemb record instances identified by the duplicate key ClusGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecClusGrpId	The SecClusGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrpMemb[] pageRecByClusGrpIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecClusGrpId,
		CFLibDbKeyHash256 priorSecClusGrpId,
		CFLibDbKeyHash256 priorSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByClusGrpIdx");
	}

	/**
	 *	Read a page array of the specific SecClusGrpMemb record instances identified by the duplicate key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecClusGrpMemb key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrpMemb[] pageRecByUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		CFLibDbKeyHash256 priorSecClusGrpId,
		CFLibDbKeyHash256 priorSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByUserIdx");
	}
}

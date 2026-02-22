
// Description: Java 25 DbIO implementation for SecSession.

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

import io.github.msobkow.v3_1.cflib.*;
import io.github.msobkow.v3_1.cflib.dbutil.*;
import io.github.msobkow.v3_1.cfsec.cfsec.*;
import io.github.msobkow.v3_1.cfsec.cfsecobj.*;
import io.github.msobkow.v3_1.cfsec.cfsecjpahooks.CFSecJpaHooksSchema;

/*
 *	CFSecJpaSecSessionTable database implementation for SecSession
 */
public class CFSecJpaSecSessionTable implements ICFSecSecSessionTable
{
	protected CFSecJpaSchema schema;
	protected CFSecJpaHooksSchema jpaHooksSchema;


	public CFSecJpaSecSessionTable(ICFSecSchema schema) {
		if( schema == null ) {
			throw new CFLibNullArgumentException(getClass(), "constructor", 1, "schema" );
		}
		if (schema instanceof CFSecJpaSchema) {
			this.schema = (CFSecJpaSchema)schema;
			this.jpaHooksSchema = this.schema.getJpaHooksSchema();
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
	public ICFSecSecSession createSecSession( ICFSecAuthorization Authorization,
		ICFSecSecSession rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createSecSession", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecSession) {
			CFSecJpaSecSession jparec = (CFSecJpaSecSession)rec;
			CFSecJpaSecSession created = jpaHooksSchema.getSecSessionService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createSecSession", "rec", rec, "CFSecJpaSecSession");
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
	public ICFSecSecSession updateSecSession( ICFSecAuthorization Authorization,
		ICFSecSecSession rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateSecSession", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecSession) {
			CFSecJpaSecSession jparec = (CFSecJpaSecSession)rec;
			CFSecJpaSecSession updated = jpaHooksSchema.getSecSessionService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateSecSession", "rec", rec, "CFSecJpaSecSession");
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
	public void deleteSecSession( ICFSecAuthorization Authorization,
		ICFSecSecSession rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaSecSession) {
			CFSecJpaSecSession jparec = (CFSecJpaSecSession)rec;
			jpaHooksSchema.getSecSessionService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteSecSession", "rec", rec, "CFSecJpaSecSession");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteSecSession");
	}

	/**
	 *	Delete the SecSession instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteSecSessionByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argKey )
	{
		jpaHooksSchema.getSecSessionService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the SecSession instances identified by the key SecUserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecSession key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecSessionBySecUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId )
	{
		jpaHooksSchema.getSecSessionService().deleteBySecUserIdx(argSecUserId);
	}


	/**
	 *	Delete the SecSession instances identified by the key SecUserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecSessionBySecUserIdx( ICFSecAuthorization Authorization,
		ICFSecSecSessionBySecUserIdxKey argKey )
	{
		jpaHooksSchema.getSecSessionService().deleteBySecUserIdx(argKey.getRequiredSecUserId());
	}

	/**
	 *	Delete the SecSession instances identified by the key SecDevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@param	SecDevName	The SecSession key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecSessionBySecDevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		String argSecDevName )
	{
		jpaHooksSchema.getSecSessionService().deleteBySecDevIdx(argSecUserId,
		argSecDevName);
	}


	/**
	 *	Delete the SecSession instances identified by the key SecDevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecSessionBySecDevIdx( ICFSecAuthorization Authorization,
		ICFSecSecSessionBySecDevIdxKey argKey )
	{
		jpaHooksSchema.getSecSessionService().deleteBySecDevIdx(argKey.getRequiredSecUserId(),
			argKey.getOptionalSecDevName());
	}

	/**
	 *	Delete the SecSession instances identified by the key StartIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@param	Start	The SecSession key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecSessionByStartIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		LocalDateTime argStart )
	{
		jpaHooksSchema.getSecSessionService().deleteByStartIdx(argSecUserId,
		argStart);
	}


	/**
	 *	Delete the SecSession instances identified by the key StartIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecSessionByStartIdx( ICFSecAuthorization Authorization,
		ICFSecSecSessionByStartIdxKey argKey )
	{
		jpaHooksSchema.getSecSessionService().deleteByStartIdx(argKey.getRequiredSecUserId(),
			argKey.getRequiredStart());
	}

	/**
	 *	Delete the SecSession instances identified by the key FinishIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@param	Finish	The SecSession key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecSessionByFinishIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		LocalDateTime argFinish )
	{
		jpaHooksSchema.getSecSessionService().deleteByFinishIdx(argSecUserId,
		argFinish);
	}


	/**
	 *	Delete the SecSession instances identified by the key FinishIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecSessionByFinishIdx( ICFSecAuthorization Authorization,
		ICFSecSecSessionByFinishIdxKey argKey )
	{
		jpaHooksSchema.getSecSessionService().deleteByFinishIdx(argKey.getRequiredSecUserId(),
			argKey.getOptionalFinish());
	}

	/**
	 *	Delete the SecSession instances identified by the key SecProxyIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecProxyId	The SecSession key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecSessionBySecProxyIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecProxyId )
	{
		jpaHooksSchema.getSecSessionService().deleteBySecProxyIdx(argSecProxyId);
	}


	/**
	 *	Delete the SecSession instances identified by the key SecProxyIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecSessionBySecProxyIdx( ICFSecAuthorization Authorization,
		ICFSecSecSessionBySecProxyIdxKey argKey )
	{
		jpaHooksSchema.getSecSessionService().deleteBySecProxyIdx(argKey.getOptionalSecProxyId());
	}


	/**
	 *	Read the derived SecSession record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecSession instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecSession readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		return( jpaHooksSchema.getSecSessionService().find(PKey) );
	}

	/**
	 *	Lock the derived SecSession record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecSession instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecSession lockDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		return( jpaHooksSchema.getSecSessionService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all SecSession instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecSession[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaSecSession> results = jpaHooksSchema.getSecSessionService().findAll();
		ICFSecSecSession[] retset = new ICFSecSecSession[results.size()];
		int idx = 0;
		for (CFSecJpaSecSession cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SecSession record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecSessionId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecSession readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecSessionId )
	{
		return( jpaHooksSchema.getSecSessionService().find(argSecSessionId) );
	}

	/**
	 *	Read an array of the derived SecSession record instances identified by the duplicate key SecUserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecSession[] readDerivedBySecUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId )
	{
		List<CFSecJpaSecSession> results = jpaHooksSchema.getSecSessionService().findBySecUserIdx(argSecUserId);
		ICFSecSecSession[] retset = new ICFSecSecSession[results.size()];
		int idx = 0;
		for (CFSecJpaSecSession cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived SecSession record instances identified by the duplicate key SecDevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@param	SecDevName	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecSession[] readDerivedBySecDevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		String argSecDevName )
	{
		List<CFSecJpaSecSession> results = jpaHooksSchema.getSecSessionService().findBySecDevIdx(argSecUserId,
		argSecDevName);
		ICFSecSecSession[] retset = new ICFSecSecSession[results.size()];
		int idx = 0;
		for (CFSecJpaSecSession cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SecSession record instance identified by the unique key StartIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@param	Start	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecSession readDerivedByStartIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		LocalDateTime argStart )
	{
		return( jpaHooksSchema.getSecSessionService().findByStartIdx(argSecUserId,
		argStart) );
	}

	/**
	 *	Read an array of the derived SecSession record instances identified by the duplicate key FinishIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@param	Finish	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecSession[] readDerivedByFinishIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		LocalDateTime argFinish )
	{
		List<CFSecJpaSecSession> results = jpaHooksSchema.getSecSessionService().findByFinishIdx(argSecUserId,
		argFinish);
		ICFSecSecSession[] retset = new ICFSecSecSession[results.size()];
		int idx = 0;
		for (CFSecJpaSecSession cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived SecSession record instances identified by the duplicate key SecProxyIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecProxyId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecSession[] readDerivedBySecProxyIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecProxyId )
	{
		List<CFSecJpaSecSession> results = jpaHooksSchema.getSecSessionService().findBySecProxyIdx(argSecProxyId);
		ICFSecSecSession[] retset = new ICFSecSecSession[results.size()];
		int idx = 0;
		for (CFSecJpaSecSession cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific SecSession record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecSession instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSession readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific SecSession record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecSession instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSession lockRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific SecSession record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecSession instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecSession[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read a page of all the specific SecSession record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecSession instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecSession[] pageAllRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 priorSecSessionId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageAllRec");
	}

	/**
	 *	Read the specific SecSession record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecSessionId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSession readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecSessionId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific SecSession record instances identified by the duplicate key SecUserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSession[] readRecBySecUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecBySecUserIdx");
	}

	/**
	 *	Read an array of the specific SecSession record instances identified by the duplicate key SecDevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@param	SecDevName	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSession[] readRecBySecDevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		String argSecDevName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecBySecDevIdx");
	}

	/**
	 *	Read the specific SecSession record instance identified by the unique key StartIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@param	Start	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSession readRecByStartIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		LocalDateTime argStart )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByStartIdx");
	}

	/**
	 *	Read an array of the specific SecSession record instances identified by the duplicate key FinishIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@param	Finish	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSession[] readRecByFinishIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		LocalDateTime argFinish )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByFinishIdx");
	}

	/**
	 *	Read an array of the specific SecSession record instances identified by the duplicate key SecProxyIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecProxyId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSession[] readRecBySecProxyIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecProxyId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecBySecProxyIdx");
	}

	/**
	 *	Read a page array of the specific SecSession record instances identified by the duplicate key SecUserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSession[] pageRecBySecUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		CFLibDbKeyHash256 priorSecSessionId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecBySecUserIdx");
	}

	/**
	 *	Read a page array of the specific SecSession record instances identified by the duplicate key SecDevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@param	SecDevName	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSession[] pageRecBySecDevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		String argSecDevName,
		CFLibDbKeyHash256 priorSecSessionId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecBySecDevIdx");
	}

	/**
	 *	Read a page array of the specific SecSession record instances identified by the duplicate key FinishIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@param	Finish	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSession[] pageRecByFinishIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		LocalDateTime argFinish,
		CFLibDbKeyHash256 priorSecSessionId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByFinishIdx");
	}

	/**
	 *	Read a page array of the specific SecSession record instances identified by the duplicate key SecProxyIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecProxyId	The SecSession key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSession[] pageRecBySecProxyIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecProxyId,
		CFLibDbKeyHash256 priorSecSessionId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecBySecProxyIdx");
	}
}


// Description: Java 25 DbIO implementation for SecGrpInc.

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
 *	CFSecJpaSecGrpIncTable database implementation for SecGrpInc
 */
public class CFSecJpaSecGrpIncTable implements ICFSecSecGrpIncTable
{
	protected CFSecJpaSchema schema;
	protected CFSecJpaHooksSchema jpaHooksSchema;


	public CFSecJpaSecGrpIncTable(ICFSecSchema schema) {
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
	public ICFSecSecGrpInc createSecGrpInc( ICFSecAuthorization Authorization,
		ICFSecSecGrpInc rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createSecGrpInc", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecGrpInc) {
			CFSecJpaSecGrpInc jparec = (CFSecJpaSecGrpInc)rec;
			CFSecJpaSecGrpInc created = jpaHooksSchema.getSecGrpIncService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createSecGrpInc", "rec", rec, "CFSecJpaSecGrpInc");
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
	public ICFSecSecGrpInc updateSecGrpInc( ICFSecAuthorization Authorization,
		ICFSecSecGrpInc rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateSecGrpInc", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecGrpInc) {
			CFSecJpaSecGrpInc jparec = (CFSecJpaSecGrpInc)rec;
			CFSecJpaSecGrpInc updated = jpaHooksSchema.getSecGrpIncService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateSecGrpInc", "rec", rec, "CFSecJpaSecGrpInc");
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
	public void deleteSecGrpInc( ICFSecAuthorization Authorization,
		ICFSecSecGrpInc rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaSecGrpInc) {
			CFSecJpaSecGrpInc jparec = (CFSecJpaSecGrpInc)rec;
			jpaHooksSchema.getSecGrpIncService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteSecGrpInc", "rec", rec, "CFSecJpaSecGrpInc");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteSecGrpInc");
	}

	/**
	 *	Delete the SecGrpInc instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteSecGrpIncByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argKey )
	{
		jpaHooksSchema.getSecGrpIncService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the SecGrpInc instances identified by the key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecGrpInc key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecGrpIncByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		jpaHooksSchema.getSecGrpIncService().deleteByClusterIdx(argClusterId);
	}


	/**
	 *	Delete the SecGrpInc instances identified by the key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecGrpIncByClusterIdx( ICFSecAuthorization Authorization,
		ICFSecSecGrpIncByClusterIdxKey argKey )
	{
		jpaHooksSchema.getSecGrpIncService().deleteByClusterIdx(argKey.getRequiredClusterId());
	}

	/**
	 *	Delete the SecGrpInc instances identified by the key GroupIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecGroupId	The SecGrpInc key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecGrpIncByGroupIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecGroupId )
	{
		jpaHooksSchema.getSecGrpIncService().deleteByGroupIdx(argSecGroupId);
	}


	/**
	 *	Delete the SecGrpInc instances identified by the key GroupIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecGrpIncByGroupIdx( ICFSecAuthorization Authorization,
		ICFSecSecGrpIncByGroupIdxKey argKey )
	{
		jpaHooksSchema.getSecGrpIncService().deleteByGroupIdx(argKey.getRequiredSecGroupId());
	}

	/**
	 *	Delete the SecGrpInc instances identified by the key IncludeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	IncludeGroupId	The SecGrpInc key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecGrpIncByIncludeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argIncludeGroupId )
	{
		jpaHooksSchema.getSecGrpIncService().deleteByIncludeIdx(argIncludeGroupId);
	}


	/**
	 *	Delete the SecGrpInc instances identified by the key IncludeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecGrpIncByIncludeIdx( ICFSecAuthorization Authorization,
		ICFSecSecGrpIncByIncludeIdxKey argKey )
	{
		jpaHooksSchema.getSecGrpIncService().deleteByIncludeIdx(argKey.getRequiredIncludeGroupId());
	}

	/**
	 *	Delete the SecGrpInc instances identified by the key UIncludeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	SecGroupId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	IncludeGroupId	The SecGrpInc key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecGrpIncByUIncludeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		CFLibDbKeyHash256 argSecGroupId,
		CFLibDbKeyHash256 argIncludeGroupId )
	{
		jpaHooksSchema.getSecGrpIncService().deleteByUIncludeIdx(argClusterId,
		argSecGroupId,
		argIncludeGroupId);
	}


	/**
	 *	Delete the SecGrpInc instances identified by the key UIncludeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecGrpIncByUIncludeIdx( ICFSecAuthorization Authorization,
		ICFSecSecGrpIncByUIncludeIdxKey argKey )
	{
		jpaHooksSchema.getSecGrpIncService().deleteByUIncludeIdx(argKey.getRequiredClusterId(),
			argKey.getRequiredSecGroupId(),
			argKey.getRequiredIncludeGroupId());
	}


	/**
	 *	Read the derived SecGrpInc record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecGrpInc instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecGrpInc readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		return( jpaHooksSchema.getSecGrpIncService().find(PKey) );
	}

	/**
	 *	Lock the derived SecGrpInc record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecGrpInc instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecGrpInc lockDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		return( jpaHooksSchema.getSecGrpIncService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all SecGrpInc instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecGrpInc[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaSecGrpInc> results = jpaHooksSchema.getSecGrpIncService().findAll();
		ICFSecSecGrpInc[] retset = new ICFSecSecGrpInc[results.size()];
		int idx = 0;
		for (CFSecJpaSecGrpInc cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SecGrpInc record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecGrpIncId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecGrpInc readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecGrpIncId )
	{
		return( jpaHooksSchema.getSecGrpIncService().find(argSecGrpIncId) );
	}

	/**
	 *	Read an array of the derived SecGrpInc record instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecGrpInc[] readDerivedByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		List<CFSecJpaSecGrpInc> results = jpaHooksSchema.getSecGrpIncService().findByClusterIdx(argClusterId);
		ICFSecSecGrpInc[] retset = new ICFSecSecGrpInc[results.size()];
		int idx = 0;
		for (CFSecJpaSecGrpInc cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived SecGrpInc record instances identified by the duplicate key GroupIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecGroupId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecGrpInc[] readDerivedByGroupIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecGroupId )
	{
		List<CFSecJpaSecGrpInc> results = jpaHooksSchema.getSecGrpIncService().findByGroupIdx(argSecGroupId);
		ICFSecSecGrpInc[] retset = new ICFSecSecGrpInc[results.size()];
		int idx = 0;
		for (CFSecJpaSecGrpInc cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived SecGrpInc record instances identified by the duplicate key IncludeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	IncludeGroupId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecGrpInc[] readDerivedByIncludeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argIncludeGroupId )
	{
		List<CFSecJpaSecGrpInc> results = jpaHooksSchema.getSecGrpIncService().findByIncludeIdx(argIncludeGroupId);
		ICFSecSecGrpInc[] retset = new ICFSecSecGrpInc[results.size()];
		int idx = 0;
		for (CFSecJpaSecGrpInc cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SecGrpInc record instance identified by the unique key UIncludeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	SecGroupId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	IncludeGroupId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecGrpInc readDerivedByUIncludeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		CFLibDbKeyHash256 argSecGroupId,
		CFLibDbKeyHash256 argIncludeGroupId )
	{
		return( jpaHooksSchema.getSecGrpIncService().findByUIncludeIdx(argClusterId,
		argSecGroupId,
		argIncludeGroupId) );
	}

	/**
	 *	Read the specific SecGrpInc record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecGrpInc instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecGrpInc readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific SecGrpInc record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecGrpInc instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecGrpInc lockRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific SecGrpInc record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecGrpInc instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecGrpInc[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read a page of all the specific SecGrpInc record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecGrpInc instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecGrpInc[] pageAllRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 priorSecGrpIncId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageAllRec");
	}

	/**
	 *	Read the specific SecGrpInc record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecGrpIncId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecGrpInc readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecGrpIncId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific SecGrpInc record instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecGrpInc[] readRecByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByClusterIdx");
	}

	/**
	 *	Read an array of the specific SecGrpInc record instances identified by the duplicate key GroupIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecGroupId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecGrpInc[] readRecByGroupIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecGroupId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByGroupIdx");
	}

	/**
	 *	Read an array of the specific SecGrpInc record instances identified by the duplicate key IncludeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	IncludeGroupId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecGrpInc[] readRecByIncludeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argIncludeGroupId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIncludeIdx");
	}

	/**
	 *	Read the specific SecGrpInc record instance identified by the unique key UIncludeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	SecGroupId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	IncludeGroupId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecGrpInc readRecByUIncludeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		CFLibDbKeyHash256 argSecGroupId,
		CFLibDbKeyHash256 argIncludeGroupId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUIncludeIdx");
	}

	/**
	 *	Read a page array of the specific SecGrpInc record instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecGrpInc[] pageRecByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		CFLibDbKeyHash256 priorSecGrpIncId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByClusterIdx");
	}

	/**
	 *	Read a page array of the specific SecGrpInc record instances identified by the duplicate key GroupIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecGroupId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecGrpInc[] pageRecByGroupIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecGroupId,
		CFLibDbKeyHash256 priorSecGrpIncId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByGroupIdx");
	}

	/**
	 *	Read a page array of the specific SecGrpInc record instances identified by the duplicate key IncludeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	IncludeGroupId	The SecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecGrpInc[] pageRecByIncludeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argIncludeGroupId,
		CFLibDbKeyHash256 priorSecGrpIncId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByIncludeIdx");
	}
}

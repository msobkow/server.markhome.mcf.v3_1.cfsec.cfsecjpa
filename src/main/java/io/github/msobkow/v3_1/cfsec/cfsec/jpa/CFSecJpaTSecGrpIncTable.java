
// Description: Java 25 DbIO implementation for TSecGrpInc.

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
 *	CFSecJpaTSecGrpIncTable database implementation for TSecGrpInc
 */
public class CFSecJpaTSecGrpIncTable implements ICFSecTSecGrpIncTable
{
	protected CFSecJpaSchema schema;
	protected CFSecJpaHooksSchema jpaHooksSchema;


	public CFSecJpaTSecGrpIncTable(ICFSecSchema schema) {
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
	public ICFSecTSecGrpInc createTSecGrpInc( ICFSecAuthorization Authorization,
		ICFSecTSecGrpInc rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createTSecGrpInc", 1, "rec");
		}
		else if (rec instanceof CFSecJpaTSecGrpInc) {
			CFSecJpaTSecGrpInc jparec = (CFSecJpaTSecGrpInc)rec;
			CFSecJpaTSecGrpInc created = jpaHooksSchema.getTSecGrpIncService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createTSecGrpInc", "rec", rec, "CFSecJpaTSecGrpInc");
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
	public ICFSecTSecGrpInc updateTSecGrpInc( ICFSecAuthorization Authorization,
		ICFSecTSecGrpInc rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateTSecGrpInc", 1, "rec");
		}
		else if (rec instanceof CFSecJpaTSecGrpInc) {
			CFSecJpaTSecGrpInc jparec = (CFSecJpaTSecGrpInc)rec;
			CFSecJpaTSecGrpInc updated = jpaHooksSchema.getTSecGrpIncService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateTSecGrpInc", "rec", rec, "CFSecJpaTSecGrpInc");
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
	public void deleteTSecGrpInc( ICFSecAuthorization Authorization,
		ICFSecTSecGrpInc rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaTSecGrpInc) {
			CFSecJpaTSecGrpInc jparec = (CFSecJpaTSecGrpInc)rec;
			jpaHooksSchema.getTSecGrpIncService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteTSecGrpInc", "rec", rec, "CFSecJpaTSecGrpInc");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteTSecGrpInc");
	}

	/**
	 *	Delete the TSecGrpInc instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteTSecGrpIncByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argKey )
	{
		jpaHooksSchema.getTSecGrpIncService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the TSecGrpInc instances identified by the key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The TSecGrpInc key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTSecGrpIncByTenantIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId )
	{
		jpaHooksSchema.getTSecGrpIncService().deleteByTenantIdx(argTenantId);
	}


	/**
	 *	Delete the TSecGrpInc instances identified by the key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTSecGrpIncByTenantIdx( ICFSecAuthorization Authorization,
		ICFSecTSecGrpIncByTenantIdxKey argKey )
	{
		jpaHooksSchema.getTSecGrpIncService().deleteByTenantIdx(argKey.getRequiredTenantId());
	}

	/**
	 *	Delete the TSecGrpInc instances identified by the key GroupIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TSecGroupId	The TSecGrpInc key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTSecGrpIncByGroupIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTSecGroupId )
	{
		jpaHooksSchema.getTSecGrpIncService().deleteByGroupIdx(argTSecGroupId);
	}


	/**
	 *	Delete the TSecGrpInc instances identified by the key GroupIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTSecGrpIncByGroupIdx( ICFSecAuthorization Authorization,
		ICFSecTSecGrpIncByGroupIdxKey argKey )
	{
		jpaHooksSchema.getTSecGrpIncService().deleteByGroupIdx(argKey.getRequiredTSecGroupId());
	}

	/**
	 *	Delete the TSecGrpInc instances identified by the key IncludeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	IncludeGroupId	The TSecGrpInc key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTSecGrpIncByIncludeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argIncludeGroupId )
	{
		jpaHooksSchema.getTSecGrpIncService().deleteByIncludeIdx(argIncludeGroupId);
	}


	/**
	 *	Delete the TSecGrpInc instances identified by the key IncludeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTSecGrpIncByIncludeIdx( ICFSecAuthorization Authorization,
		ICFSecTSecGrpIncByIncludeIdxKey argKey )
	{
		jpaHooksSchema.getTSecGrpIncService().deleteByIncludeIdx(argKey.getRequiredIncludeGroupId());
	}

	/**
	 *	Delete the TSecGrpInc instances identified by the key UIncludeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	TSecGroupId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	IncludeGroupId	The TSecGrpInc key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTSecGrpIncByUIncludeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId,
		CFLibDbKeyHash256 argTSecGroupId,
		CFLibDbKeyHash256 argIncludeGroupId )
	{
		jpaHooksSchema.getTSecGrpIncService().deleteByUIncludeIdx(argTenantId,
		argTSecGroupId,
		argIncludeGroupId);
	}


	/**
	 *	Delete the TSecGrpInc instances identified by the key UIncludeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTSecGrpIncByUIncludeIdx( ICFSecAuthorization Authorization,
		ICFSecTSecGrpIncByUIncludeIdxKey argKey )
	{
		jpaHooksSchema.getTSecGrpIncService().deleteByUIncludeIdx(argKey.getRequiredTenantId(),
			argKey.getRequiredTSecGroupId(),
			argKey.getRequiredIncludeGroupId());
	}


	/**
	 *	Read the derived TSecGrpInc record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the TSecGrpInc instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecTSecGrpInc readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		return( jpaHooksSchema.getTSecGrpIncService().find(PKey) );
	}

	/**
	 *	Lock the derived TSecGrpInc record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the TSecGrpInc instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecTSecGrpInc lockDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		return( jpaHooksSchema.getTSecGrpIncService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all TSecGrpInc instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecTSecGrpInc[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaTSecGrpInc> results = jpaHooksSchema.getTSecGrpIncService().findAll();
		ICFSecTSecGrpInc[] retset = new ICFSecTSecGrpInc[results.size()];
		int idx = 0;
		for (CFSecJpaTSecGrpInc cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived TSecGrpInc record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TSecGrpIncId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecTSecGrpInc readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTSecGrpIncId )
	{
		return( jpaHooksSchema.getTSecGrpIncService().find(argTSecGrpIncId) );
	}

	/**
	 *	Read an array of the derived TSecGrpInc record instances identified by the duplicate key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecTSecGrpInc[] readDerivedByTenantIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId )
	{
		List<CFSecJpaTSecGrpInc> results = jpaHooksSchema.getTSecGrpIncService().findByTenantIdx(argTenantId);
		ICFSecTSecGrpInc[] retset = new ICFSecTSecGrpInc[results.size()];
		int idx = 0;
		for (CFSecJpaTSecGrpInc cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived TSecGrpInc record instances identified by the duplicate key GroupIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TSecGroupId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecTSecGrpInc[] readDerivedByGroupIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTSecGroupId )
	{
		List<CFSecJpaTSecGrpInc> results = jpaHooksSchema.getTSecGrpIncService().findByGroupIdx(argTSecGroupId);
		ICFSecTSecGrpInc[] retset = new ICFSecTSecGrpInc[results.size()];
		int idx = 0;
		for (CFSecJpaTSecGrpInc cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived TSecGrpInc record instances identified by the duplicate key IncludeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	IncludeGroupId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecTSecGrpInc[] readDerivedByIncludeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argIncludeGroupId )
	{
		List<CFSecJpaTSecGrpInc> results = jpaHooksSchema.getTSecGrpIncService().findByIncludeIdx(argIncludeGroupId);
		ICFSecTSecGrpInc[] retset = new ICFSecTSecGrpInc[results.size()];
		int idx = 0;
		for (CFSecJpaTSecGrpInc cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived TSecGrpInc record instance identified by the unique key UIncludeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	TSecGroupId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	IncludeGroupId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecTSecGrpInc readDerivedByUIncludeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId,
		CFLibDbKeyHash256 argTSecGroupId,
		CFLibDbKeyHash256 argIncludeGroupId )
	{
		return( jpaHooksSchema.getTSecGrpIncService().findByUIncludeIdx(argTenantId,
		argTSecGroupId,
		argIncludeGroupId) );
	}

	/**
	 *	Read the specific TSecGrpInc record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the TSecGrpInc instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTSecGrpInc readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific TSecGrpInc record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the TSecGrpInc instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTSecGrpInc lockRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific TSecGrpInc record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific TSecGrpInc instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecTSecGrpInc[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read a page of all the specific TSecGrpInc record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific TSecGrpInc instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecTSecGrpInc[] pageAllRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 priorTSecGrpIncId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageAllRec");
	}

	/**
	 *	Read the specific TSecGrpInc record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TSecGrpIncId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTSecGrpInc readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTSecGrpIncId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific TSecGrpInc record instances identified by the duplicate key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTSecGrpInc[] readRecByTenantIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByTenantIdx");
	}

	/**
	 *	Read an array of the specific TSecGrpInc record instances identified by the duplicate key GroupIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TSecGroupId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTSecGrpInc[] readRecByGroupIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTSecGroupId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByGroupIdx");
	}

	/**
	 *	Read an array of the specific TSecGrpInc record instances identified by the duplicate key IncludeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	IncludeGroupId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTSecGrpInc[] readRecByIncludeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argIncludeGroupId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIncludeIdx");
	}

	/**
	 *	Read the specific TSecGrpInc record instance identified by the unique key UIncludeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	TSecGroupId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	IncludeGroupId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTSecGrpInc readRecByUIncludeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId,
		CFLibDbKeyHash256 argTSecGroupId,
		CFLibDbKeyHash256 argIncludeGroupId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUIncludeIdx");
	}

	/**
	 *	Read a page array of the specific TSecGrpInc record instances identified by the duplicate key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTSecGrpInc[] pageRecByTenantIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId,
		CFLibDbKeyHash256 priorTSecGrpIncId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByTenantIdx");
	}

	/**
	 *	Read a page array of the specific TSecGrpInc record instances identified by the duplicate key GroupIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TSecGroupId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTSecGrpInc[] pageRecByGroupIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTSecGroupId,
		CFLibDbKeyHash256 priorTSecGrpIncId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByGroupIdx");
	}

	/**
	 *	Read a page array of the specific TSecGrpInc record instances identified by the duplicate key IncludeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	IncludeGroupId	The TSecGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTSecGrpInc[] pageRecByIncludeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argIncludeGroupId,
		CFLibDbKeyHash256 priorTSecGrpIncId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByIncludeIdx");
	}
}

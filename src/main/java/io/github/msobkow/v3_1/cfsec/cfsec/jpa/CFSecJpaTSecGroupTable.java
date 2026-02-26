
// Description: Java 25 DbIO implementation for TSecGroup.

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
import io.github.msobkow.v3_1.cfsec.cfsec.jpa.CFSecJpaHooksSchema;

/*
 *	CFSecJpaTSecGroupTable database implementation for TSecGroup
 */
public class CFSecJpaTSecGroupTable implements ICFSecTSecGroupTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaTSecGroupTable(ICFSecSchema schema) {
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
	public ICFSecTSecGroup createTSecGroup( ICFSecAuthorization Authorization,
		ICFSecTSecGroup rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createTSecGroup", 1, "rec");
		}
		else if (rec instanceof CFSecJpaTSecGroup) {
			CFSecJpaTSecGroup jparec = (CFSecJpaTSecGroup)rec;
			CFSecJpaTSecGroup created = schema.getJpaHooksSchema().getTSecGroupService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createTSecGroup", "rec", rec, "CFSecJpaTSecGroup");
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
	public ICFSecTSecGroup updateTSecGroup( ICFSecAuthorization Authorization,
		ICFSecTSecGroup rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateTSecGroup", 1, "rec");
		}
		else if (rec instanceof CFSecJpaTSecGroup) {
			CFSecJpaTSecGroup jparec = (CFSecJpaTSecGroup)rec;
			CFSecJpaTSecGroup updated = schema.getJpaHooksSchema().getTSecGroupService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateTSecGroup", "rec", rec, "CFSecJpaTSecGroup");
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
	public void deleteTSecGroup( ICFSecAuthorization Authorization,
		ICFSecTSecGroup rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaTSecGroup) {
			CFSecJpaTSecGroup jparec = (CFSecJpaTSecGroup)rec;
			schema.getJpaHooksSchema().getTSecGroupService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteTSecGroup", "rec", rec, "CFSecJpaTSecGroup");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteTSecGroup");
	}

	/**
	 *	Delete the TSecGroup instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteTSecGroupByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argKey )
	{
		schema.getJpaHooksSchema().getTSecGroupService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the TSecGroup instances identified by the key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The TSecGroup key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTSecGroupByTenantIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId )
	{
		schema.getJpaHooksSchema().getTSecGroupService().deleteByTenantIdx(argTenantId);
	}


	/**
	 *	Delete the TSecGroup instances identified by the key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTSecGroupByTenantIdx( ICFSecAuthorization Authorization,
		ICFSecTSecGroupByTenantIdxKey argKey )
	{
		schema.getJpaHooksSchema().getTSecGroupService().deleteByTenantIdx(argKey.getRequiredTenantId());
	}

	/**
	 *	Delete the TSecGroup instances identified by the key TenantVisIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The TSecGroup key attribute of the instance generating the id.
	 *
	 *	@param	IsVisible	The TSecGroup key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTSecGroupByTenantVisIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId,
		boolean argIsVisible )
	{
		schema.getJpaHooksSchema().getTSecGroupService().deleteByTenantVisIdx(argTenantId,
		argIsVisible);
	}


	/**
	 *	Delete the TSecGroup instances identified by the key TenantVisIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTSecGroupByTenantVisIdx( ICFSecAuthorization Authorization,
		ICFSecTSecGroupByTenantVisIdxKey argKey )
	{
		schema.getJpaHooksSchema().getTSecGroupService().deleteByTenantVisIdx(argKey.getRequiredTenantId(),
			argKey.getRequiredIsVisible());
	}

	/**
	 *	Delete the TSecGroup instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The TSecGroup key attribute of the instance generating the id.
	 *
	 *	@param	Name	The TSecGroup key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTSecGroupByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId,
		String argName )
	{
		schema.getJpaHooksSchema().getTSecGroupService().deleteByUNameIdx(argTenantId,
		argName);
	}


	/**
	 *	Delete the TSecGroup instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTSecGroupByUNameIdx( ICFSecAuthorization Authorization,
		ICFSecTSecGroupByUNameIdxKey argKey )
	{
		schema.getJpaHooksSchema().getTSecGroupService().deleteByUNameIdx(argKey.getRequiredTenantId(),
			argKey.getRequiredName());
	}


	/**
	 *	Read the derived TSecGroup record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the TSecGroup instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecTSecGroup readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		return( schema.getJpaHooksSchema().getTSecGroupService().find(PKey) );
	}

	/**
	 *	Lock the derived TSecGroup record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the TSecGroup instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecTSecGroup lockDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		return( schema.getJpaHooksSchema().getTSecGroupService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all TSecGroup instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecTSecGroup[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaTSecGroup> results = schema.getJpaHooksSchema().getTSecGroupService().findAll();
		ICFSecTSecGroup[] retset = new ICFSecTSecGroup[results.size()];
		int idx = 0;
		for (CFSecJpaTSecGroup cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived TSecGroup record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TSecGroupId	The TSecGroup key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecTSecGroup readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTSecGroupId )
	{
		return( schema.getJpaHooksSchema().getTSecGroupService().find(argTSecGroupId) );
	}

	/**
	 *	Read an array of the derived TSecGroup record instances identified by the duplicate key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The TSecGroup key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecTSecGroup[] readDerivedByTenantIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId )
	{
		List<CFSecJpaTSecGroup> results = schema.getJpaHooksSchema().getTSecGroupService().findByTenantIdx(argTenantId);
		ICFSecTSecGroup[] retset = new ICFSecTSecGroup[results.size()];
		int idx = 0;
		for (CFSecJpaTSecGroup cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived TSecGroup record instances identified by the duplicate key TenantVisIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The TSecGroup key attribute of the instance generating the id.
	 *
	 *	@param	IsVisible	The TSecGroup key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecTSecGroup[] readDerivedByTenantVisIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId,
		boolean argIsVisible )
	{
		List<CFSecJpaTSecGroup> results = schema.getJpaHooksSchema().getTSecGroupService().findByTenantVisIdx(argTenantId,
		argIsVisible);
		ICFSecTSecGroup[] retset = new ICFSecTSecGroup[results.size()];
		int idx = 0;
		for (CFSecJpaTSecGroup cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived TSecGroup record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The TSecGroup key attribute of the instance generating the id.
	 *
	 *	@param	Name	The TSecGroup key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecTSecGroup readDerivedByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId,
		String argName )
	{
		return( schema.getJpaHooksSchema().getTSecGroupService().findByUNameIdx(argTenantId,
		argName) );
	}

	/**
	 *	Read the specific TSecGroup record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the TSecGroup instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTSecGroup readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific TSecGroup record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the TSecGroup instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTSecGroup lockRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific TSecGroup record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific TSecGroup instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecTSecGroup[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific TSecGroup record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TSecGroupId	The TSecGroup key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTSecGroup readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTSecGroupId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific TSecGroup record instances identified by the duplicate key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The TSecGroup key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTSecGroup[] readRecByTenantIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByTenantIdx");
	}

	/**
	 *	Read an array of the specific TSecGroup record instances identified by the duplicate key TenantVisIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The TSecGroup key attribute of the instance generating the id.
	 *
	 *	@param	IsVisible	The TSecGroup key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTSecGroup[] readRecByTenantVisIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId,
		boolean argIsVisible )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByTenantVisIdx");
	}

	/**
	 *	Read the specific TSecGroup record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The TSecGroup key attribute of the instance generating the id.
	 *
	 *	@param	Name	The TSecGroup key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTSecGroup readRecByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId,
		String argName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUNameIdx");
	}
}


// Description: Java 25 DbIO implementation for Tenant.

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
 *	CFSecJpaTenantTable database implementation for Tenant
 */
public class CFSecJpaTenantTable implements ICFSecTenantTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaTenantTable(ICFSecSchema schema) {
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
	public ICFSecTenant createTenant( ICFSecAuthorization Authorization,
		ICFSecTenant rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createTenant", 1, "rec");
		}
		else if (rec instanceof CFSecJpaTenant) {
			CFSecJpaTenant jparec = (CFSecJpaTenant)rec;
			CFSecJpaTenant created = schema.getJpaHooksSchema().getTenantService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createTenant", "rec", rec, "CFSecJpaTenant");
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
	public ICFSecTenant updateTenant( ICFSecAuthorization Authorization,
		ICFSecTenant rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateTenant", 1, "rec");
		}
		else if (rec instanceof CFSecJpaTenant) {
			CFSecJpaTenant jparec = (CFSecJpaTenant)rec;
			CFSecJpaTenant updated = schema.getJpaHooksSchema().getTenantService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateTenant", "rec", rec, "CFSecJpaTenant");
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
	public void deleteTenant( ICFSecAuthorization Authorization,
		ICFSecTenant rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaTenant) {
			CFSecJpaTenant jparec = (CFSecJpaTenant)rec;
			schema.getJpaHooksSchema().getTenantService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteTenant", "rec", rec, "CFSecJpaTenant");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteTenant");
	}

	/**
	 *	Delete the Tenant instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteTenantByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argKey )
	{
		schema.getJpaHooksSchema().getTenantService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the Tenant instances identified by the key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The Tenant key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTenantByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		schema.getJpaHooksSchema().getTenantService().deleteByClusterIdx(argClusterId);
	}


	/**
	 *	Delete the Tenant instances identified by the key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTenantByClusterIdx( ICFSecAuthorization Authorization,
		ICFSecTenantByClusterIdxKey argKey )
	{
		schema.getJpaHooksSchema().getTenantService().deleteByClusterIdx(argKey.getRequiredClusterId());
	}

	/**
	 *	Delete the Tenant instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The Tenant key attribute of the instance generating the id.
	 *
	 *	@param	TenantName	The Tenant key attribute of the instance generating the id.
	 */
	@Override
	public void deleteTenantByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		String argTenantName )
	{
		schema.getJpaHooksSchema().getTenantService().deleteByUNameIdx(argClusterId,
		argTenantName);
	}


	/**
	 *	Delete the Tenant instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteTenantByUNameIdx( ICFSecAuthorization Authorization,
		ICFSecTenantByUNameIdxKey argKey )
	{
		schema.getJpaHooksSchema().getTenantService().deleteByUNameIdx(argKey.getRequiredClusterId(),
			argKey.getRequiredTenantName());
	}


	/**
	 *	Read the derived Tenant record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the Tenant instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecTenant readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		return( schema.getJpaHooksSchema().getTenantService().find(PKey) );
	}

	/**
	 *	Lock the derived Tenant record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the Tenant instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecTenant lockDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		return( schema.getJpaHooksSchema().getTenantService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all Tenant instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecTenant[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaTenant> results = schema.getJpaHooksSchema().getTenantService().findAll();
		ICFSecTenant[] retset = new ICFSecTenant[results.size()];
		int idx = 0;
		for (CFSecJpaTenant cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived Tenant record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The Tenant key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecTenant readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argId )
	{
		return( schema.getJpaHooksSchema().getTenantService().find(argId) );
	}

	/**
	 *	Read an array of the derived Tenant record instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The Tenant key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecTenant[] readDerivedByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		List<CFSecJpaTenant> results = schema.getJpaHooksSchema().getTenantService().findByClusterIdx(argClusterId);
		ICFSecTenant[] retset = new ICFSecTenant[results.size()];
		int idx = 0;
		for (CFSecJpaTenant cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived Tenant record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The Tenant key attribute of the instance generating the id.
	 *
	 *	@param	TenantName	The Tenant key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecTenant readDerivedByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		String argTenantName )
	{
		return( schema.getJpaHooksSchema().getTenantService().findByUNameIdx(argClusterId,
		argTenantName) );
	}

	/**
	 *	Read the specific Tenant record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the Tenant instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTenant readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific Tenant record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the Tenant instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTenant lockRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific Tenant record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific Tenant instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecTenant[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read a page of all the specific Tenant record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific Tenant instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecTenant[] pageAllRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 priorId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageAllRec");
	}

	/**
	 *	Read the specific Tenant record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The Tenant key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTenant readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific Tenant record instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The Tenant key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTenant[] readRecByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByClusterIdx");
	}

	/**
	 *	Read the specific Tenant record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The Tenant key attribute of the instance generating the id.
	 *
	 *	@param	TenantName	The Tenant key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTenant readRecByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		String argTenantName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUNameIdx");
	}

	/**
	 *	Read a page array of the specific Tenant record instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The Tenant key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecTenant[] pageRecByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		CFLibDbKeyHash256 priorId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByClusterIdx");
	}
}

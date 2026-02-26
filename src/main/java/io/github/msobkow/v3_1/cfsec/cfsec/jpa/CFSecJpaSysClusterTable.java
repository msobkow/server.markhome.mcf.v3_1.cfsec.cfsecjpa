
// Description: Java 25 DbIO implementation for SysCluster.

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
 *	CFSecJpaSysClusterTable database implementation for SysCluster
 */
public class CFSecJpaSysClusterTable implements ICFSecSysClusterTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaSysClusterTable(ICFSecSchema schema) {
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
	public ICFSecSysCluster createSysCluster( ICFSecAuthorization Authorization,
		ICFSecSysCluster rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createSysCluster", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSysCluster) {
			CFSecJpaSysCluster jparec = (CFSecJpaSysCluster)rec;
			CFSecJpaSysCluster created = schema.getJpaHooksSchema().getSysClusterService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createSysCluster", "rec", rec, "CFSecJpaSysCluster");
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
	public ICFSecSysCluster updateSysCluster( ICFSecAuthorization Authorization,
		ICFSecSysCluster rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateSysCluster", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSysCluster) {
			CFSecJpaSysCluster jparec = (CFSecJpaSysCluster)rec;
			CFSecJpaSysCluster updated = schema.getJpaHooksSchema().getSysClusterService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateSysCluster", "rec", rec, "CFSecJpaSysCluster");
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
	public void deleteSysCluster( ICFSecAuthorization Authorization,
		ICFSecSysCluster rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaSysCluster) {
			CFSecJpaSysCluster jparec = (CFSecJpaSysCluster)rec;
			schema.getJpaHooksSchema().getSysClusterService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteSysCluster", "rec", rec, "CFSecJpaSysCluster");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteSysCluster");
	}

	/**
	 *	Delete the SysCluster instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteSysClusterByIdIdx( ICFSecAuthorization Authorization,
		Integer argKey )
	{
		schema.getJpaHooksSchema().getSysClusterService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the SysCluster instances identified by the key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SysCluster key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSysClusterByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		schema.getJpaHooksSchema().getSysClusterService().deleteByClusterIdx(argClusterId);
	}


	/**
	 *	Delete the SysCluster instances identified by the key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSysClusterByClusterIdx( ICFSecAuthorization Authorization,
		ICFSecSysClusterByClusterIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSysClusterService().deleteByClusterIdx(argKey.getRequiredClusterId());
	}


	/**
	 *	Read the derived SysCluster record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SysCluster instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSysCluster readDerived( ICFSecAuthorization Authorization,
		Integer PKey )
	{
		return( schema.getJpaHooksSchema().getSysClusterService().find(PKey) );
	}

	/**
	 *	Lock the derived SysCluster record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SysCluster instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSysCluster lockDerived( ICFSecAuthorization Authorization,
		Integer PKey )
	{
		return( schema.getJpaHooksSchema().getSysClusterService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all SysCluster instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSysCluster[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaSysCluster> results = schema.getJpaHooksSchema().getSysClusterService().findAll();
		ICFSecSysCluster[] retset = new ICFSecSysCluster[results.size()];
		int idx = 0;
		for (CFSecJpaSysCluster cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SysCluster record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SingletonId	The SysCluster key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSysCluster readDerivedByIdIdx( ICFSecAuthorization Authorization,
		int argSingletonId )
	{
		return( schema.getJpaHooksSchema().getSysClusterService().find(argSingletonId) );
	}

	/**
	 *	Read an array of the derived SysCluster record instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SysCluster key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSysCluster[] readDerivedByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		List<CFSecJpaSysCluster> results = schema.getJpaHooksSchema().getSysClusterService().findByClusterIdx(argClusterId);
		ICFSecSysCluster[] retset = new ICFSecSysCluster[results.size()];
		int idx = 0;
		for (CFSecJpaSysCluster cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific SysCluster record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SysCluster instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSysCluster readRec( ICFSecAuthorization Authorization,
		Integer PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific SysCluster record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SysCluster instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSysCluster lockRec( ICFSecAuthorization Authorization,
		Integer PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific SysCluster record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SysCluster instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSysCluster[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific SysCluster record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SingletonId	The SysCluster key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSysCluster readRecByIdIdx( ICFSecAuthorization Authorization,
		int argSingletonId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific SysCluster record instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SysCluster key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSysCluster[] readRecByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByClusterIdx");
	}
}

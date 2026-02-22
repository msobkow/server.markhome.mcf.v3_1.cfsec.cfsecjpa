
// Description: Java 25 DbIO implementation for HostNode.

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
 *	CFSecJpaHostNodeTable database implementation for HostNode
 */
public class CFSecJpaHostNodeTable implements ICFSecHostNodeTable
{
	protected CFSecJpaSchema schema;
	protected CFSecJpaHooksSchema jpaHooksSchema;


	public CFSecJpaHostNodeTable(ICFSecSchema schema) {
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
	public ICFSecHostNode createHostNode( ICFSecAuthorization Authorization,
		ICFSecHostNode rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createHostNode", 1, "rec");
		}
		else if (rec instanceof CFSecJpaHostNode) {
			CFSecJpaHostNode jparec = (CFSecJpaHostNode)rec;
			CFSecJpaHostNode created = jpaHooksSchema.getHostNodeService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createHostNode", "rec", rec, "CFSecJpaHostNode");
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
	public ICFSecHostNode updateHostNode( ICFSecAuthorization Authorization,
		ICFSecHostNode rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateHostNode", 1, "rec");
		}
		else if (rec instanceof CFSecJpaHostNode) {
			CFSecJpaHostNode jparec = (CFSecJpaHostNode)rec;
			CFSecJpaHostNode updated = jpaHooksSchema.getHostNodeService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateHostNode", "rec", rec, "CFSecJpaHostNode");
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
	public void deleteHostNode( ICFSecAuthorization Authorization,
		ICFSecHostNode rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaHostNode) {
			CFSecJpaHostNode jparec = (CFSecJpaHostNode)rec;
			jpaHooksSchema.getHostNodeService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteHostNode", "rec", rec, "CFSecJpaHostNode");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteHostNode");
	}

	/**
	 *	Delete the HostNode instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteHostNodeByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argKey )
	{
		jpaHooksSchema.getHostNodeService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the HostNode instances identified by the key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The HostNode key attribute of the instance generating the id.
	 */
	@Override
	public void deleteHostNodeByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		jpaHooksSchema.getHostNodeService().deleteByClusterIdx(argClusterId);
	}


	/**
	 *	Delete the HostNode instances identified by the key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteHostNodeByClusterIdx( ICFSecAuthorization Authorization,
		ICFSecHostNodeByClusterIdxKey argKey )
	{
		jpaHooksSchema.getHostNodeService().deleteByClusterIdx(argKey.getRequiredClusterId());
	}

	/**
	 *	Delete the HostNode instances identified by the key UDescrIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The HostNode key attribute of the instance generating the id.
	 *
	 *	@param	Description	The HostNode key attribute of the instance generating the id.
	 */
	@Override
	public void deleteHostNodeByUDescrIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		String argDescription )
	{
		jpaHooksSchema.getHostNodeService().deleteByUDescrIdx(argClusterId,
		argDescription);
	}


	/**
	 *	Delete the HostNode instances identified by the key UDescrIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteHostNodeByUDescrIdx( ICFSecAuthorization Authorization,
		ICFSecHostNodeByUDescrIdxKey argKey )
	{
		jpaHooksSchema.getHostNodeService().deleteByUDescrIdx(argKey.getRequiredClusterId(),
			argKey.getRequiredDescription());
	}

	/**
	 *	Delete the HostNode instances identified by the key HostNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The HostNode key attribute of the instance generating the id.
	 *
	 *	@param	HostName	The HostNode key attribute of the instance generating the id.
	 */
	@Override
	public void deleteHostNodeByHostNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		String argHostName )
	{
		jpaHooksSchema.getHostNodeService().deleteByHostNameIdx(argClusterId,
		argHostName);
	}


	/**
	 *	Delete the HostNode instances identified by the key HostNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteHostNodeByHostNameIdx( ICFSecAuthorization Authorization,
		ICFSecHostNodeByHostNameIdxKey argKey )
	{
		jpaHooksSchema.getHostNodeService().deleteByHostNameIdx(argKey.getRequiredClusterId(),
			argKey.getRequiredHostName());
	}


	/**
	 *	Read the derived HostNode record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the HostNode instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecHostNode readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		return( jpaHooksSchema.getHostNodeService().find(PKey) );
	}

	/**
	 *	Lock the derived HostNode record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the HostNode instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecHostNode lockDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		return( jpaHooksSchema.getHostNodeService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all HostNode instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecHostNode[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaHostNode> results = jpaHooksSchema.getHostNodeService().findAll();
		ICFSecHostNode[] retset = new ICFSecHostNode[results.size()];
		int idx = 0;
		for (CFSecJpaHostNode cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived HostNode record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	HostNodeId	The HostNode key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecHostNode readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argHostNodeId )
	{
		return( jpaHooksSchema.getHostNodeService().find(argHostNodeId) );
	}

	/**
	 *	Read an array of the derived HostNode record instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The HostNode key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecHostNode[] readDerivedByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		List<CFSecJpaHostNode> results = jpaHooksSchema.getHostNodeService().findByClusterIdx(argClusterId);
		ICFSecHostNode[] retset = new ICFSecHostNode[results.size()];
		int idx = 0;
		for (CFSecJpaHostNode cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived HostNode record instance identified by the unique key UDescrIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The HostNode key attribute of the instance generating the id.
	 *
	 *	@param	Description	The HostNode key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecHostNode readDerivedByUDescrIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		String argDescription )
	{
		return( jpaHooksSchema.getHostNodeService().findByUDescrIdx(argClusterId,
		argDescription) );
	}

	/**
	 *	Read the derived HostNode record instance identified by the unique key HostNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The HostNode key attribute of the instance generating the id.
	 *
	 *	@param	HostName	The HostNode key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecHostNode readDerivedByHostNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		String argHostName )
	{
		return( jpaHooksSchema.getHostNodeService().findByHostNameIdx(argClusterId,
		argHostName) );
	}

	/**
	 *	Read the specific HostNode record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the HostNode instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecHostNode readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific HostNode record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the HostNode instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecHostNode lockRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific HostNode record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific HostNode instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecHostNode[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read a page of all the specific HostNode record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific HostNode instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecHostNode[] pageAllRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 priorHostNodeId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageAllRec");
	}

	/**
	 *	Read the specific HostNode record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	HostNodeId	The HostNode key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecHostNode readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argHostNodeId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific HostNode record instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The HostNode key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecHostNode[] readRecByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByClusterIdx");
	}

	/**
	 *	Read the specific HostNode record instance identified by the unique key UDescrIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The HostNode key attribute of the instance generating the id.
	 *
	 *	@param	Description	The HostNode key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecHostNode readRecByUDescrIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		String argDescription )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUDescrIdx");
	}

	/**
	 *	Read the specific HostNode record instance identified by the unique key HostNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The HostNode key attribute of the instance generating the id.
	 *
	 *	@param	HostName	The HostNode key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecHostNode readRecByHostNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		String argHostName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByHostNameIdx");
	}

	/**
	 *	Read a page array of the specific HostNode record instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The HostNode key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecHostNode[] pageRecByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		CFLibDbKeyHash256 priorHostNodeId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByClusterIdx");
	}
}


// Description: Java 25 DbIO implementation for HostNode.

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
 *	CFSecJpaHostNodeTable database implementation for HostNode
 */
public class CFSecJpaHostNodeTable implements ICFSecHostNodeTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaHostNodeTable(ICFSecSchema schema) {
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
	public ICFSecHostNode createHostNode( ICFSecAuthorization Authorization,
		ICFSecHostNode rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createHostNode", 1, "rec");
		}
		else if (rec instanceof CFSecJpaHostNode) {
			CFSecJpaHostNode jparec = (CFSecJpaHostNode)rec;
			CFSecJpaHostNode created = schema.getJpaHooksSchema().getHostNodeService().create(jparec);
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
			CFSecJpaHostNode updated = schema.getJpaHooksSchema().getHostNodeService().update(jparec);
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
			schema.getJpaHooksSchema().getHostNodeService().deleteByIdIdx(jparec.getPKey());
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
		schema.getJpaHooksSchema().getHostNodeService().deleteByIdIdx(argKey);
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
		schema.getJpaHooksSchema().getHostNodeService().deleteByClusterIdx(argClusterId);
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
		schema.getJpaHooksSchema().getHostNodeService().deleteByClusterIdx(argKey.getRequiredClusterId());
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
		schema.getJpaHooksSchema().getHostNodeService().deleteByUDescrIdx(argClusterId,
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
		schema.getJpaHooksSchema().getHostNodeService().deleteByUDescrIdx(argKey.getRequiredClusterId(),
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
		schema.getJpaHooksSchema().getHostNodeService().deleteByHostNameIdx(argClusterId,
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
		schema.getJpaHooksSchema().getHostNodeService().deleteByHostNameIdx(argKey.getRequiredClusterId(),
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
		return( schema.getJpaHooksSchema().getHostNodeService().find(PKey) );
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
		return( schema.getJpaHooksSchema().getHostNodeService().lockByIdIdx(PKey) );
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
		List<CFSecJpaHostNode> results = schema.getJpaHooksSchema().getHostNodeService().findAll();
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
		return( schema.getJpaHooksSchema().getHostNodeService().find(argHostNodeId) );
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
		List<CFSecJpaHostNode> results = schema.getJpaHooksSchema().getHostNodeService().findByClusterIdx(argClusterId);
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
		return( schema.getJpaHooksSchema().getHostNodeService().findByUDescrIdx(argClusterId,
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
		return( schema.getJpaHooksSchema().getHostNodeService().findByHostNameIdx(argClusterId,
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

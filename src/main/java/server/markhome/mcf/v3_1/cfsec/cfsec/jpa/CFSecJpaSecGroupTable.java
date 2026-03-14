
// Description: Java 25 DbIO implementation for SecGroup.

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
 *	CFSecJpaSecGroupTable database implementation for SecGroup
 */
public class CFSecJpaSecGroupTable implements ICFSecSecGroupTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaSecGroupTable(ICFSecSchema schema) {
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
	public ICFSecSecGroup createSecGroup( ICFSecAuthorization Authorization,
		ICFSecSecGroup rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createSecGroup", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecGroup) {
			CFSecJpaSecGroup jparec = (CFSecJpaSecGroup)rec;
			CFSecJpaSecGroup created = schema.getJpaHooksSchema().getSecGroupService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createSecGroup", "rec", rec, "CFSecJpaSecGroup");
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
	public ICFSecSecGroup updateSecGroup( ICFSecAuthorization Authorization,
		ICFSecSecGroup rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateSecGroup", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecGroup) {
			CFSecJpaSecGroup jparec = (CFSecJpaSecGroup)rec;
			CFSecJpaSecGroup updated = schema.getJpaHooksSchema().getSecGroupService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateSecGroup", "rec", rec, "CFSecJpaSecGroup");
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
	public void deleteSecGroup( ICFSecAuthorization Authorization,
		ICFSecSecGroup rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaSecGroup) {
			CFSecJpaSecGroup jparec = (CFSecJpaSecGroup)rec;
			schema.getJpaHooksSchema().getSecGroupService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteSecGroup", "rec", rec, "CFSecJpaSecGroup");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteSecGroup");
	}

	/**
	 *	Delete the SecGroup instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteSecGroupByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argKey )
	{
		schema.getJpaHooksSchema().getSecGroupService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the SecGroup instances identified by the key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecGroup key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecGroupByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		schema.getJpaHooksSchema().getSecGroupService().deleteByClusterIdx(argClusterId);
	}


	/**
	 *	Delete the SecGroup instances identified by the key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecGroupByClusterIdx( ICFSecAuthorization Authorization,
		ICFSecSecGroupByClusterIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecGroupService().deleteByClusterIdx(argKey.getRequiredClusterId());
	}

	/**
	 *	Delete the SecGroup instances identified by the key ClusterVisIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecGroup key attribute of the instance generating the id.
	 *
	 *	@param	IsVisible	The SecGroup key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecGroupByClusterVisIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		boolean argIsVisible )
	{
		schema.getJpaHooksSchema().getSecGroupService().deleteByClusterVisIdx(argClusterId,
		argIsVisible);
	}


	/**
	 *	Delete the SecGroup instances identified by the key ClusterVisIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecGroupByClusterVisIdx( ICFSecAuthorization Authorization,
		ICFSecSecGroupByClusterVisIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecGroupService().deleteByClusterVisIdx(argKey.getRequiredClusterId(),
			argKey.getRequiredIsVisible());
	}

	/**
	 *	Delete the SecGroup instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecGroup key attribute of the instance generating the id.
	 *
	 *	@param	Name	The SecGroup key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecGroupByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		String argName )
	{
		schema.getJpaHooksSchema().getSecGroupService().deleteByUNameIdx(argClusterId,
		argName);
	}


	/**
	 *	Delete the SecGroup instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecGroupByUNameIdx( ICFSecAuthorization Authorization,
		ICFSecSecGroupByUNameIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecGroupService().deleteByUNameIdx(argKey.getRequiredClusterId(),
			argKey.getRequiredName());
	}


	/**
	 *	Read the derived SecGroup record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecGroup instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecGroup readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		return( schema.getJpaHooksSchema().getSecGroupService().find(PKey) );
	}

	/**
	 *	Lock the derived SecGroup record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecGroup instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecGroup lockDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		return( schema.getJpaHooksSchema().getSecGroupService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all SecGroup instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecGroup[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaSecGroup> results = schema.getJpaHooksSchema().getSecGroupService().findAll();
		ICFSecSecGroup[] retset = new ICFSecSecGroup[results.size()];
		int idx = 0;
		for (CFSecJpaSecGroup cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SecGroup record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecGroupId	The SecGroup key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecGroup readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecGroupId )
	{
		return( schema.getJpaHooksSchema().getSecGroupService().find(argSecGroupId) );
	}

	/**
	 *	Read an array of the derived SecGroup record instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecGroup key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecGroup[] readDerivedByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		List<CFSecJpaSecGroup> results = schema.getJpaHooksSchema().getSecGroupService().findByClusterIdx(argClusterId);
		ICFSecSecGroup[] retset = new ICFSecSecGroup[results.size()];
		int idx = 0;
		for (CFSecJpaSecGroup cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived SecGroup record instances identified by the duplicate key ClusterVisIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecGroup key attribute of the instance generating the id.
	 *
	 *	@param	IsVisible	The SecGroup key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecGroup[] readDerivedByClusterVisIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		boolean argIsVisible )
	{
		List<CFSecJpaSecGroup> results = schema.getJpaHooksSchema().getSecGroupService().findByClusterVisIdx(argClusterId,
		argIsVisible);
		ICFSecSecGroup[] retset = new ICFSecSecGroup[results.size()];
		int idx = 0;
		for (CFSecJpaSecGroup cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SecGroup record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecGroup key attribute of the instance generating the id.
	 *
	 *	@param	Name	The SecGroup key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecGroup readDerivedByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		String argName )
	{
		return( schema.getJpaHooksSchema().getSecGroupService().findByUNameIdx(argClusterId,
		argName) );
	}

	/**
	 *	Read the specific SecGroup record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecGroup instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecGroup readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific SecGroup record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecGroup instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecGroup lockRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific SecGroup record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecGroup instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecGroup[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific SecGroup record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecGroupId	The SecGroup key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecGroup readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecGroupId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific SecGroup record instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecGroup key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecGroup[] readRecByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByClusterIdx");
	}

	/**
	 *	Read an array of the specific SecGroup record instances identified by the duplicate key ClusterVisIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecGroup key attribute of the instance generating the id.
	 *
	 *	@param	IsVisible	The SecGroup key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecGroup[] readRecByClusterVisIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		boolean argIsVisible )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByClusterVisIdx");
	}

	/**
	 *	Read the specific SecGroup record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecGroup key attribute of the instance generating the id.
	 *
	 *	@param	Name	The SecGroup key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecGroup readRecByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		String argName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUNameIdx");
	}
}

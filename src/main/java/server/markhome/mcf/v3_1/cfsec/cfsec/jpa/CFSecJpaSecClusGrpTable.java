
// Description: Java 25 DbIO implementation for SecClusGrp.

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
 *	CFSecJpaSecClusGrpTable database implementation for SecClusGrp
 */
public class CFSecJpaSecClusGrpTable implements ICFSecSecClusGrpTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaSecClusGrpTable(ICFSecSchema schema) {
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
	public ICFSecSecClusGrp createSecClusGrp( ICFSecAuthorization Authorization,
		ICFSecSecClusGrp rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createSecClusGrp", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecClusGrp) {
			CFSecJpaSecClusGrp jparec = (CFSecJpaSecClusGrp)rec;
			CFSecJpaSecClusGrp created = schema.getJpaHooksSchema().getSecClusGrpService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createSecClusGrp", "rec", rec, "CFSecJpaSecClusGrp");
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
	public ICFSecSecClusGrp updateSecClusGrp( ICFSecAuthorization Authorization,
		ICFSecSecClusGrp rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateSecClusGrp", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecClusGrp) {
			CFSecJpaSecClusGrp jparec = (CFSecJpaSecClusGrp)rec;
			CFSecJpaSecClusGrp updated = schema.getJpaHooksSchema().getSecClusGrpService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateSecClusGrp", "rec", rec, "CFSecJpaSecClusGrp");
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
	public void deleteSecClusGrp( ICFSecAuthorization Authorization,
		ICFSecSecClusGrp rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaSecClusGrp) {
			CFSecJpaSecClusGrp jparec = (CFSecJpaSecClusGrp)rec;
			schema.getJpaHooksSchema().getSecClusGrpService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteSecClusGrp", "rec", rec, "CFSecJpaSecClusGrp");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteSecClusGrp");
	}

	/**
	 *	Delete the SecClusGrp instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteSecClusGrpByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argKey )
	{
		schema.getJpaHooksSchema().getSecClusGrpService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the SecClusGrp instances identified by the key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecClusGrp key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecClusGrpByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		schema.getJpaHooksSchema().getSecClusGrpService().deleteByClusterIdx(argClusterId);
	}


	/**
	 *	Delete the SecClusGrp instances identified by the key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecClusGrpByClusterIdx( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpByClusterIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecClusGrpService().deleteByClusterIdx(argKey.getRequiredClusterId());
	}

	/**
	 *	Delete the SecClusGrp instances identified by the key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Name	The SecClusGrp key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecClusGrpByNameIdx( ICFSecAuthorization Authorization,
		String argName )
	{
		schema.getJpaHooksSchema().getSecClusGrpService().deleteByNameIdx(argName);
	}


	/**
	 *	Delete the SecClusGrp instances identified by the key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecClusGrpByNameIdx( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpByNameIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecClusGrpService().deleteByNameIdx(argKey.getRequiredName());
	}

	/**
	 *	Delete the SecClusGrp instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecClusGrp key attribute of the instance generating the id.
	 *
	 *	@param	Name	The SecClusGrp key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecClusGrpByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		String argName )
	{
		schema.getJpaHooksSchema().getSecClusGrpService().deleteByUNameIdx(argClusterId,
		argName);
	}


	/**
	 *	Delete the SecClusGrp instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecClusGrpByUNameIdx( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpByUNameIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecClusGrpService().deleteByUNameIdx(argKey.getRequiredClusterId(),
			argKey.getRequiredName());
	}


	/**
	 *	Read the derived SecClusGrp record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecClusGrp instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecClusGrp readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		return( schema.getJpaHooksSchema().getSecClusGrpService().find(PKey) );
	}

	/**
	 *	Lock the derived SecClusGrp record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecClusGrp instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecClusGrp lockDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		return( schema.getJpaHooksSchema().getSecClusGrpService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all SecClusGrp instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecClusGrp[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaSecClusGrp> results = schema.getJpaHooksSchema().getSecClusGrpService().findAll();
		ICFSecSecClusGrp[] retset = new ICFSecSecClusGrp[results.size()];
		int idx = 0;
		for (CFSecJpaSecClusGrp cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SecClusGrp record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecClusGrpId	The SecClusGrp key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecClusGrp readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecClusGrpId )
	{
		return( schema.getJpaHooksSchema().getSecClusGrpService().find(argSecClusGrpId) );
	}

	/**
	 *	Read an array of the derived SecClusGrp record instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecClusGrp key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecClusGrp[] readDerivedByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		List<CFSecJpaSecClusGrp> results = schema.getJpaHooksSchema().getSecClusGrpService().findByClusterIdx(argClusterId);
		ICFSecSecClusGrp[] retset = new ICFSecSecClusGrp[results.size()];
		int idx = 0;
		for (CFSecJpaSecClusGrp cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived SecClusGrp record instances identified by the duplicate key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Name	The SecClusGrp key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecClusGrp[] readDerivedByNameIdx( ICFSecAuthorization Authorization,
		String argName )
	{
		List<CFSecJpaSecClusGrp> results = schema.getJpaHooksSchema().getSecClusGrpService().findByNameIdx(argName);
		ICFSecSecClusGrp[] retset = new ICFSecSecClusGrp[results.size()];
		int idx = 0;
		for (CFSecJpaSecClusGrp cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SecClusGrp record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecClusGrp key attribute of the instance generating the id.
	 *
	 *	@param	Name	The SecClusGrp key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecClusGrp readDerivedByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		String argName )
	{
		return( schema.getJpaHooksSchema().getSecClusGrpService().findByUNameIdx(argClusterId,
		argName) );
	}

	/**
	 *	Read the specific SecClusGrp record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecClusGrp instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrp readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific SecClusGrp record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecClusGrp instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrp lockRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific SecClusGrp record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecClusGrp instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecClusGrp[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific SecClusGrp record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecClusGrpId	The SecClusGrp key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrp readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecClusGrpId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific SecClusGrp record instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecClusGrp key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrp[] readRecByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByClusterIdx");
	}

	/**
	 *	Read an array of the specific SecClusGrp record instances identified by the duplicate key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Name	The SecClusGrp key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrp[] readRecByNameIdx( ICFSecAuthorization Authorization,
		String argName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByNameIdx");
	}

	/**
	 *	Read the specific SecClusGrp record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The SecClusGrp key attribute of the instance generating the id.
	 *
	 *	@param	Name	The SecClusGrp key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrp readRecByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		String argName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUNameIdx");
	}
}

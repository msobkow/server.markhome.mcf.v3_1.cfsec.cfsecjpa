
// Description: Java 25 DbIO implementation for Cluster.

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
 *	CFSecJpaClusterTable database implementation for Cluster
 */
public class CFSecJpaClusterTable implements ICFSecClusterTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaClusterTable(ICFSecSchema schema) {
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
	public ICFSecCluster createCluster( ICFSecAuthorization Authorization,
		ICFSecCluster rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createCluster", 1, "rec");
		}
		else if (rec instanceof CFSecJpaCluster) {
			CFSecJpaCluster jparec = (CFSecJpaCluster)rec;
			CFSecJpaCluster created = schema.getJpaHooksSchema().getClusterService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createCluster", "rec", rec, "CFSecJpaCluster");
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
	public ICFSecCluster updateCluster( ICFSecAuthorization Authorization,
		ICFSecCluster rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateCluster", 1, "rec");
		}
		else if (rec instanceof CFSecJpaCluster) {
			CFSecJpaCluster jparec = (CFSecJpaCluster)rec;
			CFSecJpaCluster updated = schema.getJpaHooksSchema().getClusterService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateCluster", "rec", rec, "CFSecJpaCluster");
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
	public void deleteCluster( ICFSecAuthorization Authorization,
		ICFSecCluster rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaCluster) {
			CFSecJpaCluster jparec = (CFSecJpaCluster)rec;
			schema.getJpaHooksSchema().getClusterService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteCluster", "rec", rec, "CFSecJpaCluster");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteCluster");
	}

	/**
	 *	Delete the Cluster instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteClusterByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argKey )
	{
		schema.getJpaHooksSchema().getClusterService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the Cluster instances identified by the key UDomNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	FullDomName	The Cluster key attribute of the instance generating the id.
	 */
	@Override
	public void deleteClusterByUDomNameIdx( ICFSecAuthorization Authorization,
		String argFullDomName )
	{
		schema.getJpaHooksSchema().getClusterService().deleteByUDomNameIdx(argFullDomName);
	}


	/**
	 *	Delete the Cluster instances identified by the key UDomNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteClusterByUDomNameIdx( ICFSecAuthorization Authorization,
		ICFSecClusterByUDomNameIdxKey argKey )
	{
		schema.getJpaHooksSchema().getClusterService().deleteByUDomNameIdx(argKey.getRequiredFullDomName());
	}

	/**
	 *	Delete the Cluster instances identified by the key UDescrIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Description	The Cluster key attribute of the instance generating the id.
	 */
	@Override
	public void deleteClusterByUDescrIdx( ICFSecAuthorization Authorization,
		String argDescription )
	{
		schema.getJpaHooksSchema().getClusterService().deleteByUDescrIdx(argDescription);
	}


	/**
	 *	Delete the Cluster instances identified by the key UDescrIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteClusterByUDescrIdx( ICFSecAuthorization Authorization,
		ICFSecClusterByUDescrIdxKey argKey )
	{
		schema.getJpaHooksSchema().getClusterService().deleteByUDescrIdx(argKey.getRequiredDescription());
	}


	/**
	 *	Read the derived Cluster record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the Cluster instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecCluster readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		return( schema.getJpaHooksSchema().getClusterService().find(PKey) );
	}

	/**
	 *	Lock the derived Cluster record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the Cluster instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecCluster lockDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		return( schema.getJpaHooksSchema().getClusterService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all Cluster instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecCluster[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaCluster> results = schema.getJpaHooksSchema().getClusterService().findAll();
		ICFSecCluster[] retset = new ICFSecCluster[results.size()];
		int idx = 0;
		for (CFSecJpaCluster cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived Cluster record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The Cluster key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecCluster readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argId )
	{
		return( schema.getJpaHooksSchema().getClusterService().find(argId) );
	}

	/**
	 *	Read the derived Cluster record instance identified by the unique key UDomNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	FullDomName	The Cluster key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecCluster readDerivedByUDomNameIdx( ICFSecAuthorization Authorization,
		String argFullDomName )
	{
		return( schema.getJpaHooksSchema().getClusterService().findByUDomNameIdx(argFullDomName) );
	}

	/**
	 *	Read the derived Cluster record instance identified by the unique key UDescrIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Description	The Cluster key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecCluster readDerivedByUDescrIdx( ICFSecAuthorization Authorization,
		String argDescription )
	{
		return( schema.getJpaHooksSchema().getClusterService().findByUDescrIdx(argDescription) );
	}

	/**
	 *	Read the specific Cluster record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the Cluster instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecCluster readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific Cluster record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the Cluster instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecCluster lockRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific Cluster record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific Cluster instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecCluster[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read a page of all the specific Cluster record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific Cluster instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecCluster[] pageAllRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 priorId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageAllRec");
	}

	/**
	 *	Read the specific Cluster record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Id	The Cluster key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecCluster readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read the specific Cluster record instance identified by the unique key UDomNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	FullDomName	The Cluster key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecCluster readRecByUDomNameIdx( ICFSecAuthorization Authorization,
		String argFullDomName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUDomNameIdx");
	}

	/**
	 *	Read the specific Cluster record instance identified by the unique key UDescrIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Description	The Cluster key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecCluster readRecByUDescrIdx( ICFSecAuthorization Authorization,
		String argDescription )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUDescrIdx");
	}
}

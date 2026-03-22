
// Description: Java 25 DbIO implementation for SecClusGrpInc.

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
 *	CFSecJpaSecClusGrpIncTable database implementation for SecClusGrpInc
 */
public class CFSecJpaSecClusGrpIncTable implements ICFSecSecClusGrpIncTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaSecClusGrpIncTable(ICFSecSchema schema) {
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
	public ICFSecSecClusGrpInc createSecClusGrpInc( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpInc rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createSecClusGrpInc", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecClusGrpInc) {
			CFSecJpaSecClusGrpInc jparec = (CFSecJpaSecClusGrpInc)rec;
			CFSecJpaSecClusGrpInc created = schema.getJpaHooksSchema().getSecClusGrpIncService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createSecClusGrpInc", "rec", rec, "CFSecJpaSecClusGrpInc");
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
	public ICFSecSecClusGrpInc updateSecClusGrpInc( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpInc rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateSecClusGrpInc", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecClusGrpInc) {
			CFSecJpaSecClusGrpInc jparec = (CFSecJpaSecClusGrpInc)rec;
			CFSecJpaSecClusGrpInc updated = schema.getJpaHooksSchema().getSecClusGrpIncService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateSecClusGrpInc", "rec", rec, "CFSecJpaSecClusGrpInc");
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
	public void deleteSecClusGrpInc( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpInc rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaSecClusGrpInc) {
			CFSecJpaSecClusGrpInc jparec = (CFSecJpaSecClusGrpInc)rec;
			schema.getJpaHooksSchema().getSecClusGrpIncService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteSecClusGrpInc", "rec", rec, "CFSecJpaSecClusGrpInc");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteSecClusGrpInc");
	}

	/**
	 *	Delete the SecClusGrpInc instance identified by the primary key attributes.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecClusGrpId	The SecClusGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	IncName	The SecClusGrpInc key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecClusGrpIncByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecClusGrpId,
		String argIncName )
	{
		schema.getJpaHooksSchema().getSecClusGrpIncService().deleteByIdIdx(argSecClusGrpId,
		argIncName);
	}

	/**
	 *	Delete the SecClusGrpInc instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteSecClusGrpIncByIdIdx( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpIncPKey argKey )
	{
		schema.getJpaHooksSchema().getSecClusGrpIncService().deleteByIdIdx(argKey.getRequiredSecClusGrpId(),
			argKey.getRequiredIncName());
	}

	/**
	 *	Delete the SecClusGrpInc instances identified by the key ClusGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecClusGrpId	The SecClusGrpInc key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecClusGrpIncByClusGrpIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecClusGrpId )
	{
		schema.getJpaHooksSchema().getSecClusGrpIncService().deleteByClusGrpIdx(argSecClusGrpId);
	}


	/**
	 *	Delete the SecClusGrpInc instances identified by the key ClusGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecClusGrpIncByClusGrpIdx( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpIncByClusGrpIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecClusGrpIncService().deleteByClusGrpIdx(argKey.getRequiredSecClusGrpId());
	}

	/**
	 *	Delete the SecClusGrpInc instances identified by the key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	IncName	The SecClusGrpInc key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecClusGrpIncByNameIdx( ICFSecAuthorization Authorization,
		String argIncName )
	{
		schema.getJpaHooksSchema().getSecClusGrpIncService().deleteByNameIdx(argIncName);
	}


	/**
	 *	Delete the SecClusGrpInc instances identified by the key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecClusGrpIncByNameIdx( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpIncByNameIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecClusGrpIncService().deleteByNameIdx(argKey.getRequiredIncName());
	}


	/**
	 *	Read the derived SecClusGrpInc record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecClusGrpInc instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecClusGrpInc readDerived( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpIncPKey PKey )
	{
		return( schema.getJpaHooksSchema().getSecClusGrpIncService().find(PKey) );
	}

	/**
	 *	Read the derived SecClusGrpInc record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecClusGrpInc readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecClusGrpId,
		String argIncName )
	{
		return( schema.getJpaHooksSchema().getSecClusGrpIncService().find(argSecClusGrpId,
		argIncName) );
	}

	/**
	 *	Lock the derived SecClusGrpInc record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecClusGrpInc instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecClusGrpInc lockDerived( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpIncPKey PKey )
	{
		return( schema.getJpaHooksSchema().getSecClusGrpIncService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all SecClusGrpInc instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecClusGrpInc[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaSecClusGrpInc> results = schema.getJpaHooksSchema().getSecClusGrpIncService().findAll();
		ICFSecSecClusGrpInc[] retset = new ICFSecSecClusGrpInc[results.size()];
		int idx = 0;
		for (CFSecJpaSecClusGrpInc cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SecClusGrpInc record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecClusGrpId	The SecClusGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	IncName	The SecClusGrpInc key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecClusGrpInc readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecClusGrpId,
		String argIncName )
	{
		return( schema.getJpaHooksSchema().getSecClusGrpIncService().find(argSecClusGrpId,
		argIncName) );
	}

	/**
	 *	Read an array of the derived SecClusGrpInc record instances identified by the duplicate key ClusGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecClusGrpId	The SecClusGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecClusGrpInc[] readDerivedByClusGrpIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecClusGrpId )
	{
		List<CFSecJpaSecClusGrpInc> results = schema.getJpaHooksSchema().getSecClusGrpIncService().findByClusGrpIdx(argSecClusGrpId);
		ICFSecSecClusGrpInc[] retset = new ICFSecSecClusGrpInc[results.size()];
		int idx = 0;
		for (CFSecJpaSecClusGrpInc cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived SecClusGrpInc record instances identified by the duplicate key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	IncName	The SecClusGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecClusGrpInc[] readDerivedByNameIdx( ICFSecAuthorization Authorization,
		String argIncName )
	{
		List<CFSecJpaSecClusGrpInc> results = schema.getJpaHooksSchema().getSecClusGrpIncService().findByNameIdx(argIncName);
		ICFSecSecClusGrpInc[] retset = new ICFSecSecClusGrpInc[results.size()];
		int idx = 0;
		for (CFSecJpaSecClusGrpInc cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific SecClusGrpInc record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecClusGrpInc instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrpInc readRec( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpIncPKey PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Read the specific SecClusGrpInc record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecClusGrpInc instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrpInc readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecClusGrpId,
		String argIncName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec-args");
	}

	/**
	 *	Lock the specific SecClusGrpInc record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecClusGrpInc instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrpInc lockRec( ICFSecAuthorization Authorization,
		ICFSecSecClusGrpIncPKey PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific SecClusGrpInc record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecClusGrpInc instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecClusGrpInc[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read a page of all the specific SecClusGrpInc record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecClusGrpInc instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecClusGrpInc[] pageAllRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 priorSecClusGrpId,
		String priorIncName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageAllRec");
	}

	/**
	 *	Read the specific SecClusGrpInc record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecClusGrpId	The SecClusGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	IncName	The SecClusGrpInc key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrpInc readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecClusGrpId,
		String argIncName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific SecClusGrpInc record instances identified by the duplicate key ClusGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecClusGrpId	The SecClusGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrpInc[] readRecByClusGrpIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecClusGrpId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByClusGrpIdx");
	}

	/**
	 *	Read an array of the specific SecClusGrpInc record instances identified by the duplicate key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	IncName	The SecClusGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrpInc[] readRecByNameIdx( ICFSecAuthorization Authorization,
		String argIncName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByNameIdx");
	}

	/**
	 *	Read a page array of the specific SecClusGrpInc record instances identified by the duplicate key ClusGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecClusGrpId	The SecClusGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrpInc[] pageRecByClusGrpIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecClusGrpId,
		CFLibDbKeyHash256 priorSecClusGrpId,
		String priorIncName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByClusGrpIdx");
	}

	/**
	 *	Read a page array of the specific SecClusGrpInc record instances identified by the duplicate key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	IncName	The SecClusGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecClusGrpInc[] pageRecByNameIdx( ICFSecAuthorization Authorization,
		String argIncName,
		CFLibDbKeyHash256 priorSecClusGrpId,
		String priorIncName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByNameIdx");
	}
}

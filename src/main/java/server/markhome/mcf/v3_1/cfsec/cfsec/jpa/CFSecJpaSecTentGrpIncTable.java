
// Description: Java 25 DbIO implementation for SecTentGrpInc.

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
 *	CFSecJpaSecTentGrpIncTable database implementation for SecTentGrpInc
 */
public class CFSecJpaSecTentGrpIncTable implements ICFSecSecTentGrpIncTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaSecTentGrpIncTable(ICFSecSchema schema) {
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
	public ICFSecSecTentGrpInc createSecTentGrpInc( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpInc rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createSecTentGrpInc", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecTentGrpInc) {
			CFSecJpaSecTentGrpInc jparec = (CFSecJpaSecTentGrpInc)rec;
			CFSecJpaSecTentGrpInc created = schema.getJpaHooksSchema().getSecTentGrpIncService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createSecTentGrpInc", "rec", rec, "CFSecJpaSecTentGrpInc");
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
	public ICFSecSecTentGrpInc updateSecTentGrpInc( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpInc rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateSecTentGrpInc", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecTentGrpInc) {
			CFSecJpaSecTentGrpInc jparec = (CFSecJpaSecTentGrpInc)rec;
			CFSecJpaSecTentGrpInc updated = schema.getJpaHooksSchema().getSecTentGrpIncService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateSecTentGrpInc", "rec", rec, "CFSecJpaSecTentGrpInc");
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
	public void deleteSecTentGrpInc( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpInc rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaSecTentGrpInc) {
			CFSecJpaSecTentGrpInc jparec = (CFSecJpaSecTentGrpInc)rec;
			schema.getJpaHooksSchema().getSecTentGrpIncService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteSecTentGrpInc", "rec", rec, "CFSecJpaSecTentGrpInc");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteSecTentGrpInc");
	}

	/**
	 *	Delete the SecTentGrpInc instance identified by the primary key attributes.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecTentGrpId	The SecTentGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	IncName	The SecTentGrpInc key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecTentGrpIncByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecTentGrpId,
		String argIncName )
	{
		schema.getJpaHooksSchema().getSecTentGrpIncService().deleteByIdIdx(argSecTentGrpId,
		argIncName);
	}

	/**
	 *	Delete the SecTentGrpInc instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteSecTentGrpIncByIdIdx( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpIncPKey argKey )
	{
		schema.getJpaHooksSchema().getSecTentGrpIncService().deleteByIdIdx(argKey.getRequiredSecTentGrpId(),
			argKey.getRequiredIncName());
	}

	/**
	 *	Delete the SecTentGrpInc instances identified by the key TentGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecTentGrpId	The SecTentGrpInc key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecTentGrpIncByTentGrpIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecTentGrpId )
	{
		schema.getJpaHooksSchema().getSecTentGrpIncService().deleteByTentGrpIdx(argSecTentGrpId);
	}


	/**
	 *	Delete the SecTentGrpInc instances identified by the key TentGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecTentGrpIncByTentGrpIdx( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpIncByTentGrpIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecTentGrpIncService().deleteByTentGrpIdx(argKey.getRequiredSecTentGrpId());
	}

	/**
	 *	Delete the SecTentGrpInc instances identified by the key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	IncName	The SecTentGrpInc key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecTentGrpIncByNameIdx( ICFSecAuthorization Authorization,
		String argIncName )
	{
		schema.getJpaHooksSchema().getSecTentGrpIncService().deleteByNameIdx(argIncName);
	}


	/**
	 *	Delete the SecTentGrpInc instances identified by the key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecTentGrpIncByNameIdx( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpIncByNameIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecTentGrpIncService().deleteByNameIdx(argKey.getRequiredIncName());
	}


	/**
	 *	Read the derived SecTentGrpInc record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecTentGrpInc instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecTentGrpInc readDerived( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpIncPKey PKey )
	{
		return( schema.getJpaHooksSchema().getSecTentGrpIncService().find(PKey) );
	}

	/**
	 *	Read the derived SecTentGrpInc record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecTentGrpInc readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecTentGrpId,
		String argIncName )
	{
		return( schema.getJpaHooksSchema().getSecTentGrpIncService().find(argSecTentGrpId,
		argIncName) );
	}

	/**
	 *	Lock the derived SecTentGrpInc record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecTentGrpInc instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecTentGrpInc lockDerived( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpIncPKey PKey )
	{
		return( schema.getJpaHooksSchema().getSecTentGrpIncService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all SecTentGrpInc instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecTentGrpInc[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaSecTentGrpInc> results = schema.getJpaHooksSchema().getSecTentGrpIncService().findAll();
		ICFSecSecTentGrpInc[] retset = new ICFSecSecTentGrpInc[results.size()];
		int idx = 0;
		for (CFSecJpaSecTentGrpInc cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SecTentGrpInc record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecTentGrpId	The SecTentGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	IncName	The SecTentGrpInc key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecTentGrpInc readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecTentGrpId,
		String argIncName )
	{
		return( schema.getJpaHooksSchema().getSecTentGrpIncService().find(argSecTentGrpId,
		argIncName) );
	}

	/**
	 *	Read an array of the derived SecTentGrpInc record instances identified by the duplicate key TentGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecTentGrpId	The SecTentGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecTentGrpInc[] readDerivedByTentGrpIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecTentGrpId )
	{
		List<CFSecJpaSecTentGrpInc> results = schema.getJpaHooksSchema().getSecTentGrpIncService().findByTentGrpIdx(argSecTentGrpId);
		ICFSecSecTentGrpInc[] retset = new ICFSecSecTentGrpInc[results.size()];
		int idx = 0;
		for (CFSecJpaSecTentGrpInc cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived SecTentGrpInc record instances identified by the duplicate key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	IncName	The SecTentGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecTentGrpInc[] readDerivedByNameIdx( ICFSecAuthorization Authorization,
		String argIncName )
	{
		List<CFSecJpaSecTentGrpInc> results = schema.getJpaHooksSchema().getSecTentGrpIncService().findByNameIdx(argIncName);
		ICFSecSecTentGrpInc[] retset = new ICFSecSecTentGrpInc[results.size()];
		int idx = 0;
		for (CFSecJpaSecTentGrpInc cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific SecTentGrpInc record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecTentGrpInc instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrpInc readRec( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpIncPKey PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Read the specific SecTentGrpInc record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecTentGrpInc instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrpInc readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecTentGrpId,
		String argIncName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec-args");
	}

	/**
	 *	Lock the specific SecTentGrpInc record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecTentGrpInc instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrpInc lockRec( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpIncPKey PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific SecTentGrpInc record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecTentGrpInc instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecTentGrpInc[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read a page of all the specific SecTentGrpInc record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecTentGrpInc instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecTentGrpInc[] pageAllRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 priorSecTentGrpId,
		String priorIncName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageAllRec");
	}

	/**
	 *	Read the specific SecTentGrpInc record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecTentGrpId	The SecTentGrpInc key attribute of the instance generating the id.
	 *
	 *	@param	IncName	The SecTentGrpInc key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrpInc readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecTentGrpId,
		String argIncName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific SecTentGrpInc record instances identified by the duplicate key TentGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecTentGrpId	The SecTentGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrpInc[] readRecByTentGrpIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecTentGrpId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByTentGrpIdx");
	}

	/**
	 *	Read an array of the specific SecTentGrpInc record instances identified by the duplicate key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	IncName	The SecTentGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrpInc[] readRecByNameIdx( ICFSecAuthorization Authorization,
		String argIncName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByNameIdx");
	}

	/**
	 *	Read a page array of the specific SecTentGrpInc record instances identified by the duplicate key TentGrpIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecTentGrpId	The SecTentGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrpInc[] pageRecByTentGrpIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecTentGrpId,
		CFLibDbKeyHash256 priorSecTentGrpId,
		String priorIncName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByTentGrpIdx");
	}

	/**
	 *	Read a page array of the specific SecTentGrpInc record instances identified by the duplicate key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	IncName	The SecTentGrpInc key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrpInc[] pageRecByNameIdx( ICFSecAuthorization Authorization,
		String argIncName,
		CFLibDbKeyHash256 priorSecTentGrpId,
		String priorIncName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByNameIdx");
	}
}


// Description: Java 25 DbIO implementation for ISOCtryLang.

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
 *	CFSecJpaISOCtryLangTable database implementation for ISOCtryLang
 */
public class CFSecJpaISOCtryLangTable implements ICFSecISOCtryLangTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaISOCtryLangTable(ICFSecSchema schema) {
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
	public ICFSecISOCtryLang createISOCtryLang( ICFSecAuthorization Authorization,
		ICFSecISOCtryLang rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createISOCtryLang", 1, "rec");
		}
		else if (rec instanceof CFSecJpaISOCtryLang) {
			CFSecJpaISOCtryLang jparec = (CFSecJpaISOCtryLang)rec;
			CFSecJpaISOCtryLang created = schema.getJpaHooksSchema().getISOCtryLangService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createISOCtryLang", "rec", rec, "CFSecJpaISOCtryLang");
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
	public ICFSecISOCtryLang updateISOCtryLang( ICFSecAuthorization Authorization,
		ICFSecISOCtryLang rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateISOCtryLang", 1, "rec");
		}
		else if (rec instanceof CFSecJpaISOCtryLang) {
			CFSecJpaISOCtryLang jparec = (CFSecJpaISOCtryLang)rec;
			CFSecJpaISOCtryLang updated = schema.getJpaHooksSchema().getISOCtryLangService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateISOCtryLang", "rec", rec, "CFSecJpaISOCtryLang");
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
	public void deleteISOCtryLang( ICFSecAuthorization Authorization,
		ICFSecISOCtryLang rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaISOCtryLang) {
			CFSecJpaISOCtryLang jparec = (CFSecJpaISOCtryLang)rec;
			schema.getJpaHooksSchema().getISOCtryLangService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteISOCtryLang", "rec", rec, "CFSecJpaISOCtryLang");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteISOCtryLang");
	}

	/**
	 *	Delete the ISOCtryLang instance identified by the primary key attributes.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOCtryId	The ISOCtryLang key attribute of the instance generating the id.
	 *
	 *	@param	ISOLangId	The ISOCtryLang key attribute of the instance generating the id.
	 */
	@Override
	public void deleteISOCtryLangByIdIdx( ICFSecAuthorization Authorization,
		short argISOCtryId,
		short argISOLangId )
	{
		schema.getJpaHooksSchema().getISOCtryLangService().deleteByIdIdx(argISOCtryId,
		argISOLangId);
	}

	/**
	 *	Delete the ISOCtryLang instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteISOCtryLangByIdIdx( ICFSecAuthorization Authorization,
		ICFSecISOCtryLangPKey argKey )
	{
		schema.getJpaHooksSchema().getISOCtryLangService().deleteByIdIdx(argKey.getRequiredISOCtryId(),
			argKey.getRequiredISOLangId());
	}

	/**
	 *	Delete the ISOCtryLang instances identified by the key CtryIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOCtryId	The ISOCtryLang key attribute of the instance generating the id.
	 */
	@Override
	public void deleteISOCtryLangByCtryIdx( ICFSecAuthorization Authorization,
		short argISOCtryId )
	{
		schema.getJpaHooksSchema().getISOCtryLangService().deleteByCtryIdx(argISOCtryId);
	}


	/**
	 *	Delete the ISOCtryLang instances identified by the key CtryIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteISOCtryLangByCtryIdx( ICFSecAuthorization Authorization,
		ICFSecISOCtryLangByCtryIdxKey argKey )
	{
		schema.getJpaHooksSchema().getISOCtryLangService().deleteByCtryIdx(argKey.getRequiredISOCtryId());
	}

	/**
	 *	Delete the ISOCtryLang instances identified by the key LangIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOLangId	The ISOCtryLang key attribute of the instance generating the id.
	 */
	@Override
	public void deleteISOCtryLangByLangIdx( ICFSecAuthorization Authorization,
		short argISOLangId )
	{
		schema.getJpaHooksSchema().getISOCtryLangService().deleteByLangIdx(argISOLangId);
	}


	/**
	 *	Delete the ISOCtryLang instances identified by the key LangIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteISOCtryLangByLangIdx( ICFSecAuthorization Authorization,
		ICFSecISOCtryLangByLangIdxKey argKey )
	{
		schema.getJpaHooksSchema().getISOCtryLangService().deleteByLangIdx(argKey.getRequiredISOLangId());
	}


	/**
	 *	Read the derived ISOCtryLang record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOCtryLang instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOCtryLang readDerived( ICFSecAuthorization Authorization,
		ICFSecISOCtryLangPKey PKey )
	{
		return( schema.getJpaHooksSchema().getISOCtryLangService().find(PKey) );
	}

	/**
	 *	Read the derived ISOCtryLang record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOCtryLang readDerived( ICFSecAuthorization Authorization,
		short argISOCtryId,
		short argISOLangId )
	{
		return( schema.getJpaHooksSchema().getISOCtryLangService().find(argISOCtryId,
		argISOLangId) );
	}

	/**
	 *	Lock the derived ISOCtryLang record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOCtryLang instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOCtryLang lockDerived( ICFSecAuthorization Authorization,
		ICFSecISOCtryLangPKey PKey )
	{
		return( schema.getJpaHooksSchema().getISOCtryLangService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all ISOCtryLang instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecISOCtryLang[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaISOCtryLang> results = schema.getJpaHooksSchema().getISOCtryLangService().findAll();
		ICFSecISOCtryLang[] retset = new ICFSecISOCtryLang[results.size()];
		int idx = 0;
		for (CFSecJpaISOCtryLang cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived ISOCtryLang record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOCtryId	The ISOCtryLang key attribute of the instance generating the id.
	 *
	 *	@param	ISOLangId	The ISOCtryLang key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOCtryLang readDerivedByIdIdx( ICFSecAuthorization Authorization,
		short argISOCtryId,
		short argISOLangId )
	{
		return( schema.getJpaHooksSchema().getISOCtryLangService().find(argISOCtryId,
		argISOLangId) );
	}

	/**
	 *	Read an array of the derived ISOCtryLang record instances identified by the duplicate key CtryIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOCtryId	The ISOCtryLang key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecISOCtryLang[] readDerivedByCtryIdx( ICFSecAuthorization Authorization,
		short argISOCtryId )
	{
		List<CFSecJpaISOCtryLang> results = schema.getJpaHooksSchema().getISOCtryLangService().findByCtryIdx(argISOCtryId);
		ICFSecISOCtryLang[] retset = new ICFSecISOCtryLang[results.size()];
		int idx = 0;
		for (CFSecJpaISOCtryLang cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived ISOCtryLang record instances identified by the duplicate key LangIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOLangId	The ISOCtryLang key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecISOCtryLang[] readDerivedByLangIdx( ICFSecAuthorization Authorization,
		short argISOLangId )
	{
		List<CFSecJpaISOCtryLang> results = schema.getJpaHooksSchema().getISOCtryLangService().findByLangIdx(argISOLangId);
		ICFSecISOCtryLang[] retset = new ICFSecISOCtryLang[results.size()];
		int idx = 0;
		for (CFSecJpaISOCtryLang cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific ISOCtryLang record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOCtryLang instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOCtryLang readRec( ICFSecAuthorization Authorization,
		ICFSecISOCtryLangPKey PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Read the specific ISOCtryLang record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOCtryLang instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOCtryLang readRec( ICFSecAuthorization Authorization,
		short argISOCtryId,
		short argISOLangId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec-args");
	}

	/**
	 *	Lock the specific ISOCtryLang record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOCtryLang instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOCtryLang lockRec( ICFSecAuthorization Authorization,
		ICFSecISOCtryLangPKey PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific ISOCtryLang record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific ISOCtryLang instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecISOCtryLang[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific ISOCtryLang record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOCtryId	The ISOCtryLang key attribute of the instance generating the id.
	 *
	 *	@param	ISOLangId	The ISOCtryLang key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOCtryLang readRecByIdIdx( ICFSecAuthorization Authorization,
		short argISOCtryId,
		short argISOLangId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific ISOCtryLang record instances identified by the duplicate key CtryIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOCtryId	The ISOCtryLang key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOCtryLang[] readRecByCtryIdx( ICFSecAuthorization Authorization,
		short argISOCtryId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByCtryIdx");
	}

	/**
	 *	Read an array of the specific ISOCtryLang record instances identified by the duplicate key LangIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOLangId	The ISOCtryLang key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOCtryLang[] readRecByLangIdx( ICFSecAuthorization Authorization,
		short argISOLangId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByLangIdx");
	}
}

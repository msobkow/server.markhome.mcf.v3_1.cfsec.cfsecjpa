
// Description: Java 25 DbIO implementation for ISOLang.

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
 *	CFSecJpaISOLangTable database implementation for ISOLang
 */
public class CFSecJpaISOLangTable implements ICFSecISOLangTable
{
	protected CFSecJpaSchema schema;
	protected CFSecJpaHooksSchema jpaHooksSchema;


	public CFSecJpaISOLangTable(ICFSecSchema schema) {
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
	public ICFSecISOLang createISOLang( ICFSecAuthorization Authorization,
		ICFSecISOLang rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createISOLang", 1, "rec");
		}
		else if (rec instanceof CFSecJpaISOLang) {
			CFSecJpaISOLang jparec = (CFSecJpaISOLang)rec;
			CFSecJpaISOLang created = jpaHooksSchema.getISOLangService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createISOLang", "rec", rec, "CFSecJpaISOLang");
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
	public ICFSecISOLang updateISOLang( ICFSecAuthorization Authorization,
		ICFSecISOLang rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateISOLang", 1, "rec");
		}
		else if (rec instanceof CFSecJpaISOLang) {
			CFSecJpaISOLang jparec = (CFSecJpaISOLang)rec;
			CFSecJpaISOLang updated = jpaHooksSchema.getISOLangService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateISOLang", "rec", rec, "CFSecJpaISOLang");
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
	public void deleteISOLang( ICFSecAuthorization Authorization,
		ICFSecISOLang rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaISOLang) {
			CFSecJpaISOLang jparec = (CFSecJpaISOLang)rec;
			jpaHooksSchema.getISOLangService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteISOLang", "rec", rec, "CFSecJpaISOLang");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteISOLang");
	}

	/**
	 *	Delete the ISOLang instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteISOLangByIdIdx( ICFSecAuthorization Authorization,
		Short argKey )
	{
		jpaHooksSchema.getISOLangService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the ISOLang instances identified by the key Code3Idx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISO6392Code	The ISOLang key attribute of the instance generating the id.
	 */
	@Override
	public void deleteISOLangByCode3Idx( ICFSecAuthorization Authorization,
		String argISO6392Code )
	{
		jpaHooksSchema.getISOLangService().deleteByCode3Idx(argISO6392Code);
	}


	/**
	 *	Delete the ISOLang instances identified by the key Code3Idx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteISOLangByCode3Idx( ICFSecAuthorization Authorization,
		ICFSecISOLangByCode3IdxKey argKey )
	{
		jpaHooksSchema.getISOLangService().deleteByCode3Idx(argKey.getRequiredISO6392Code());
	}

	/**
	 *	Delete the ISOLang instances identified by the key Code2Idx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISO6391Code	The ISOLang key attribute of the instance generating the id.
	 */
	@Override
	public void deleteISOLangByCode2Idx( ICFSecAuthorization Authorization,
		String argISO6391Code )
	{
		jpaHooksSchema.getISOLangService().deleteByCode2Idx(argISO6391Code);
	}


	/**
	 *	Delete the ISOLang instances identified by the key Code2Idx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteISOLangByCode2Idx( ICFSecAuthorization Authorization,
		ICFSecISOLangByCode2IdxKey argKey )
	{
		jpaHooksSchema.getISOLangService().deleteByCode2Idx(argKey.getOptionalISO6391Code());
	}


	/**
	 *	Read the derived ISOLang record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOLang instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOLang readDerived( ICFSecAuthorization Authorization,
		Short PKey )
	{
		return( jpaHooksSchema.getISOLangService().find(PKey) );
	}

	/**
	 *	Lock the derived ISOLang record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOLang instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOLang lockDerived( ICFSecAuthorization Authorization,
		Short PKey )
	{
		return( jpaHooksSchema.getISOLangService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all ISOLang instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecISOLang[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaISOLang> results = jpaHooksSchema.getISOLangService().findAll();
		ICFSecISOLang[] retset = new ICFSecISOLang[results.size()];
		int idx = 0;
		for (CFSecJpaISOLang cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived ISOLang record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOLangId	The ISOLang key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOLang readDerivedByIdIdx( ICFSecAuthorization Authorization,
		short argISOLangId )
	{
		return( jpaHooksSchema.getISOLangService().find(argISOLangId) );
	}

	/**
	 *	Read the derived ISOLang record instance identified by the unique key Code3Idx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISO6392Code	The ISOLang key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOLang readDerivedByCode3Idx( ICFSecAuthorization Authorization,
		String argISO6392Code )
	{
		return( jpaHooksSchema.getISOLangService().findByCode3Idx(argISO6392Code) );
	}

	/**
	 *	Read an array of the derived ISOLang record instances identified by the duplicate key Code2Idx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISO6391Code	The ISOLang key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecISOLang[] readDerivedByCode2Idx( ICFSecAuthorization Authorization,
		String argISO6391Code )
	{
		List<CFSecJpaISOLang> results = jpaHooksSchema.getISOLangService().findByCode2Idx(argISO6391Code);
		ICFSecISOLang[] retset = new ICFSecISOLang[results.size()];
		int idx = 0;
		for (CFSecJpaISOLang cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific ISOLang record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOLang instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOLang readRec( ICFSecAuthorization Authorization,
		Short PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific ISOLang record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOLang instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOLang lockRec( ICFSecAuthorization Authorization,
		Short PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific ISOLang record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific ISOLang instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecISOLang[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific ISOLang record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOLangId	The ISOLang key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOLang readRecByIdIdx( ICFSecAuthorization Authorization,
		short argISOLangId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read the specific ISOLang record instance identified by the unique key Code3Idx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISO6392Code	The ISOLang key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOLang readRecByCode3Idx( ICFSecAuthorization Authorization,
		String argISO6392Code )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByCode3Idx");
	}

	/**
	 *	Read an array of the specific ISOLang record instances identified by the duplicate key Code2Idx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISO6391Code	The ISOLang key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOLang[] readRecByCode2Idx( ICFSecAuthorization Authorization,
		String argISO6391Code )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByCode2Idx");
	}
}

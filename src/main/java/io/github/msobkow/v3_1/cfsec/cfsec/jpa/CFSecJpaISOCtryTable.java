
// Description: Java 25 DbIO implementation for ISOCtry.

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
 *	CFSecJpaISOCtryTable database implementation for ISOCtry
 */
public class CFSecJpaISOCtryTable implements ICFSecISOCtryTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaISOCtryTable(ICFSecSchema schema) {
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
	public ICFSecISOCtry createISOCtry( ICFSecAuthorization Authorization,
		ICFSecISOCtry rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createISOCtry", 1, "rec");
		}
		else if (rec instanceof CFSecJpaISOCtry) {
			CFSecJpaISOCtry jparec = (CFSecJpaISOCtry)rec;
			CFSecJpaISOCtry created = schema.getJpaHooksSchema().getISOCtryService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createISOCtry", "rec", rec, "CFSecJpaISOCtry");
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
	public ICFSecISOCtry updateISOCtry( ICFSecAuthorization Authorization,
		ICFSecISOCtry rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateISOCtry", 1, "rec");
		}
		else if (rec instanceof CFSecJpaISOCtry) {
			CFSecJpaISOCtry jparec = (CFSecJpaISOCtry)rec;
			CFSecJpaISOCtry updated = schema.getJpaHooksSchema().getISOCtryService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateISOCtry", "rec", rec, "CFSecJpaISOCtry");
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
	public void deleteISOCtry( ICFSecAuthorization Authorization,
		ICFSecISOCtry rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaISOCtry) {
			CFSecJpaISOCtry jparec = (CFSecJpaISOCtry)rec;
			schema.getJpaHooksSchema().getISOCtryService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteISOCtry", "rec", rec, "CFSecJpaISOCtry");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteISOCtry");
	}

	/**
	 *	Delete the ISOCtry instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteISOCtryByIdIdx( ICFSecAuthorization Authorization,
		Short argKey )
	{
		schema.getJpaHooksSchema().getISOCtryService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the ISOCtry instances identified by the key ISOCodeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOCode	The ISOCtry key attribute of the instance generating the id.
	 */
	@Override
	public void deleteISOCtryByISOCodeIdx( ICFSecAuthorization Authorization,
		String argISOCode )
	{
		schema.getJpaHooksSchema().getISOCtryService().deleteByISOCodeIdx(argISOCode);
	}


	/**
	 *	Delete the ISOCtry instances identified by the key ISOCodeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteISOCtryByISOCodeIdx( ICFSecAuthorization Authorization,
		ICFSecISOCtryByISOCodeIdxKey argKey )
	{
		schema.getJpaHooksSchema().getISOCtryService().deleteByISOCodeIdx(argKey.getRequiredISOCode());
	}

	/**
	 *	Delete the ISOCtry instances identified by the key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Name	The ISOCtry key attribute of the instance generating the id.
	 */
	@Override
	public void deleteISOCtryByNameIdx( ICFSecAuthorization Authorization,
		String argName )
	{
		schema.getJpaHooksSchema().getISOCtryService().deleteByNameIdx(argName);
	}


	/**
	 *	Delete the ISOCtry instances identified by the key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteISOCtryByNameIdx( ICFSecAuthorization Authorization,
		ICFSecISOCtryByNameIdxKey argKey )
	{
		schema.getJpaHooksSchema().getISOCtryService().deleteByNameIdx(argKey.getRequiredName());
	}


	/**
	 *	Read the derived ISOCtry record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOCtry instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOCtry readDerived( ICFSecAuthorization Authorization,
		Short PKey )
	{
		return( schema.getJpaHooksSchema().getISOCtryService().find(PKey) );
	}

	/**
	 *	Lock the derived ISOCtry record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOCtry instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOCtry lockDerived( ICFSecAuthorization Authorization,
		Short PKey )
	{
		return( schema.getJpaHooksSchema().getISOCtryService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all ISOCtry instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecISOCtry[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaISOCtry> results = schema.getJpaHooksSchema().getISOCtryService().findAll();
		ICFSecISOCtry[] retset = new ICFSecISOCtry[results.size()];
		int idx = 0;
		for (CFSecJpaISOCtry cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived ISOCtry record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOCtryId	The ISOCtry key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOCtry readDerivedByIdIdx( ICFSecAuthorization Authorization,
		short argISOCtryId )
	{
		return( schema.getJpaHooksSchema().getISOCtryService().find(argISOCtryId) );
	}

	/**
	 *	Read the derived ISOCtry record instance identified by the unique key ISOCodeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOCode	The ISOCtry key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOCtry readDerivedByISOCodeIdx( ICFSecAuthorization Authorization,
		String argISOCode )
	{
		return( schema.getJpaHooksSchema().getISOCtryService().findByISOCodeIdx(argISOCode) );
	}

	/**
	 *	Read the derived ISOCtry record instance identified by the unique key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Name	The ISOCtry key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOCtry readDerivedByNameIdx( ICFSecAuthorization Authorization,
		String argName )
	{
		return( schema.getJpaHooksSchema().getISOCtryService().findByNameIdx(argName) );
	}

	/**
	 *	Read the specific ISOCtry record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOCtry instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOCtry readRec( ICFSecAuthorization Authorization,
		Short PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific ISOCtry record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOCtry instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOCtry lockRec( ICFSecAuthorization Authorization,
		Short PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific ISOCtry record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific ISOCtry instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecISOCtry[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific ISOCtry record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOCtryId	The ISOCtry key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOCtry readRecByIdIdx( ICFSecAuthorization Authorization,
		short argISOCtryId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read the specific ISOCtry record instance identified by the unique key ISOCodeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOCode	The ISOCtry key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOCtry readRecByISOCodeIdx( ICFSecAuthorization Authorization,
		String argISOCode )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByISOCodeIdx");
	}

	/**
	 *	Read the specific ISOCtry record instance identified by the unique key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Name	The ISOCtry key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOCtry readRecByNameIdx( ICFSecAuthorization Authorization,
		String argName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByNameIdx");
	}
}

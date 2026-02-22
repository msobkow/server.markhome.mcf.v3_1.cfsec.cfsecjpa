
// Description: Java 25 DbIO implementation for ISOCtryCcy.

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
 *	CFSecJpaISOCtryCcyTable database implementation for ISOCtryCcy
 */
public class CFSecJpaISOCtryCcyTable implements ICFSecISOCtryCcyTable
{
	protected CFSecJpaSchema schema;
	protected CFSecJpaHooksSchema jpaHooksSchema;


	public CFSecJpaISOCtryCcyTable(ICFSecSchema schema) {
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
	public ICFSecISOCtryCcy createISOCtryCcy( ICFSecAuthorization Authorization,
		ICFSecISOCtryCcy rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createISOCtryCcy", 1, "rec");
		}
		else if (rec instanceof CFSecJpaISOCtryCcy) {
			CFSecJpaISOCtryCcy jparec = (CFSecJpaISOCtryCcy)rec;
			CFSecJpaISOCtryCcy created = jpaHooksSchema.getISOCtryCcyService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createISOCtryCcy", "rec", rec, "CFSecJpaISOCtryCcy");
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
	public ICFSecISOCtryCcy updateISOCtryCcy( ICFSecAuthorization Authorization,
		ICFSecISOCtryCcy rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateISOCtryCcy", 1, "rec");
		}
		else if (rec instanceof CFSecJpaISOCtryCcy) {
			CFSecJpaISOCtryCcy jparec = (CFSecJpaISOCtryCcy)rec;
			CFSecJpaISOCtryCcy updated = jpaHooksSchema.getISOCtryCcyService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateISOCtryCcy", "rec", rec, "CFSecJpaISOCtryCcy");
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
	public void deleteISOCtryCcy( ICFSecAuthorization Authorization,
		ICFSecISOCtryCcy rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaISOCtryCcy) {
			CFSecJpaISOCtryCcy jparec = (CFSecJpaISOCtryCcy)rec;
			jpaHooksSchema.getISOCtryCcyService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteISOCtryCcy", "rec", rec, "CFSecJpaISOCtryCcy");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteISOCtryCcy");
	}

	/**
	 *	Delete the ISOCtryCcy instance identified by the primary key attributes.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOCtryId	The ISOCtryCcy key attribute of the instance generating the id.
	 *
	 *	@param	ISOCcyId	The ISOCtryCcy key attribute of the instance generating the id.
	 */
	@Override
	public void deleteISOCtryCcyByIdIdx( ICFSecAuthorization Authorization,
		short argISOCtryId,
		short argISOCcyId )
	{
		jpaHooksSchema.getISOCtryCcyService().deleteByIdIdx(argISOCtryId,
		argISOCcyId);
	}

	/**
	 *	Delete the ISOCtryCcy instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteISOCtryCcyByIdIdx( ICFSecAuthorization Authorization,
		ICFSecISOCtryCcyPKey argKey )
	{
		jpaHooksSchema.getISOCtryCcyService().deleteByIdIdx(argKey.getRequiredISOCtryId(),
			argKey.getRequiredISOCcyId());
	}

	/**
	 *	Delete the ISOCtryCcy instances identified by the key CtryIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOCtryId	The ISOCtryCcy key attribute of the instance generating the id.
	 */
	@Override
	public void deleteISOCtryCcyByCtryIdx( ICFSecAuthorization Authorization,
		short argISOCtryId )
	{
		jpaHooksSchema.getISOCtryCcyService().deleteByCtryIdx(argISOCtryId);
	}


	/**
	 *	Delete the ISOCtryCcy instances identified by the key CtryIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteISOCtryCcyByCtryIdx( ICFSecAuthorization Authorization,
		ICFSecISOCtryCcyByCtryIdxKey argKey )
	{
		jpaHooksSchema.getISOCtryCcyService().deleteByCtryIdx(argKey.getRequiredISOCtryId());
	}

	/**
	 *	Delete the ISOCtryCcy instances identified by the key CcyIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOCcyId	The ISOCtryCcy key attribute of the instance generating the id.
	 */
	@Override
	public void deleteISOCtryCcyByCcyIdx( ICFSecAuthorization Authorization,
		short argISOCcyId )
	{
		jpaHooksSchema.getISOCtryCcyService().deleteByCcyIdx(argISOCcyId);
	}


	/**
	 *	Delete the ISOCtryCcy instances identified by the key CcyIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteISOCtryCcyByCcyIdx( ICFSecAuthorization Authorization,
		ICFSecISOCtryCcyByCcyIdxKey argKey )
	{
		jpaHooksSchema.getISOCtryCcyService().deleteByCcyIdx(argKey.getRequiredISOCcyId());
	}


	/**
	 *	Read the derived ISOCtryCcy record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOCtryCcy instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOCtryCcy readDerived( ICFSecAuthorization Authorization,
		ICFSecISOCtryCcyPKey PKey )
	{
		return( jpaHooksSchema.getISOCtryCcyService().find(PKey) );
	}

	/**
	 *	Read the derived ISOCtryCcy record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOCtryCcy readDerived( ICFSecAuthorization Authorization,
		short argISOCtryId,
		short argISOCcyId )
	{
		return( jpaHooksSchema.getISOCtryCcyService().find(argISOCtryId,
		argISOCcyId) );
	}

	/**
	 *	Lock the derived ISOCtryCcy record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOCtryCcy instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOCtryCcy lockDerived( ICFSecAuthorization Authorization,
		ICFSecISOCtryCcyPKey PKey )
	{
		return( jpaHooksSchema.getISOCtryCcyService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all ISOCtryCcy instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecISOCtryCcy[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaISOCtryCcy> results = jpaHooksSchema.getISOCtryCcyService().findAll();
		ICFSecISOCtryCcy[] retset = new ICFSecISOCtryCcy[results.size()];
		int idx = 0;
		for (CFSecJpaISOCtryCcy cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived ISOCtryCcy record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOCtryId	The ISOCtryCcy key attribute of the instance generating the id.
	 *
	 *	@param	ISOCcyId	The ISOCtryCcy key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOCtryCcy readDerivedByIdIdx( ICFSecAuthorization Authorization,
		short argISOCtryId,
		short argISOCcyId )
	{
		return( jpaHooksSchema.getISOCtryCcyService().find(argISOCtryId,
		argISOCcyId) );
	}

	/**
	 *	Read an array of the derived ISOCtryCcy record instances identified by the duplicate key CtryIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOCtryId	The ISOCtryCcy key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecISOCtryCcy[] readDerivedByCtryIdx( ICFSecAuthorization Authorization,
		short argISOCtryId )
	{
		List<CFSecJpaISOCtryCcy> results = jpaHooksSchema.getISOCtryCcyService().findByCtryIdx(argISOCtryId);
		ICFSecISOCtryCcy[] retset = new ICFSecISOCtryCcy[results.size()];
		int idx = 0;
		for (CFSecJpaISOCtryCcy cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived ISOCtryCcy record instances identified by the duplicate key CcyIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOCcyId	The ISOCtryCcy key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecISOCtryCcy[] readDerivedByCcyIdx( ICFSecAuthorization Authorization,
		short argISOCcyId )
	{
		List<CFSecJpaISOCtryCcy> results = jpaHooksSchema.getISOCtryCcyService().findByCcyIdx(argISOCcyId);
		ICFSecISOCtryCcy[] retset = new ICFSecISOCtryCcy[results.size()];
		int idx = 0;
		for (CFSecJpaISOCtryCcy cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific ISOCtryCcy record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOCtryCcy instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOCtryCcy readRec( ICFSecAuthorization Authorization,
		ICFSecISOCtryCcyPKey PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Read the specific ISOCtryCcy record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOCtryCcy instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOCtryCcy readRec( ICFSecAuthorization Authorization,
		short argISOCtryId,
		short argISOCcyId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec-args");
	}

	/**
	 *	Lock the specific ISOCtryCcy record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOCtryCcy instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOCtryCcy lockRec( ICFSecAuthorization Authorization,
		ICFSecISOCtryCcyPKey PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific ISOCtryCcy record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific ISOCtryCcy instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecISOCtryCcy[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific ISOCtryCcy record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOCtryId	The ISOCtryCcy key attribute of the instance generating the id.
	 *
	 *	@param	ISOCcyId	The ISOCtryCcy key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOCtryCcy readRecByIdIdx( ICFSecAuthorization Authorization,
		short argISOCtryId,
		short argISOCcyId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific ISOCtryCcy record instances identified by the duplicate key CtryIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOCtryId	The ISOCtryCcy key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOCtryCcy[] readRecByCtryIdx( ICFSecAuthorization Authorization,
		short argISOCtryId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByCtryIdx");
	}

	/**
	 *	Read an array of the specific ISOCtryCcy record instances identified by the duplicate key CcyIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOCcyId	The ISOCtryCcy key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOCtryCcy[] readRecByCcyIdx( ICFSecAuthorization Authorization,
		short argISOCcyId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByCcyIdx");
	}
}

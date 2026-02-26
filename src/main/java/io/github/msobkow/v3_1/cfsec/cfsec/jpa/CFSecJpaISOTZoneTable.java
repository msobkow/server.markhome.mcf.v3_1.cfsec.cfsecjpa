
// Description: Java 25 DbIO implementation for ISOTZone.

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
 *	CFSecJpaISOTZoneTable database implementation for ISOTZone
 */
public class CFSecJpaISOTZoneTable implements ICFSecISOTZoneTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaISOTZoneTable(ICFSecSchema schema) {
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
	public ICFSecISOTZone createISOTZone( ICFSecAuthorization Authorization,
		ICFSecISOTZone rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createISOTZone", 1, "rec");
		}
		else if (rec instanceof CFSecJpaISOTZone) {
			CFSecJpaISOTZone jparec = (CFSecJpaISOTZone)rec;
			CFSecJpaISOTZone created = schema.getJpaHooksSchema().getISOTZoneService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createISOTZone", "rec", rec, "CFSecJpaISOTZone");
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
	public ICFSecISOTZone updateISOTZone( ICFSecAuthorization Authorization,
		ICFSecISOTZone rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateISOTZone", 1, "rec");
		}
		else if (rec instanceof CFSecJpaISOTZone) {
			CFSecJpaISOTZone jparec = (CFSecJpaISOTZone)rec;
			CFSecJpaISOTZone updated = schema.getJpaHooksSchema().getISOTZoneService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateISOTZone", "rec", rec, "CFSecJpaISOTZone");
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
	public void deleteISOTZone( ICFSecAuthorization Authorization,
		ICFSecISOTZone rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaISOTZone) {
			CFSecJpaISOTZone jparec = (CFSecJpaISOTZone)rec;
			schema.getJpaHooksSchema().getISOTZoneService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteISOTZone", "rec", rec, "CFSecJpaISOTZone");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteISOTZone");
	}

	/**
	 *	Delete the ISOTZone instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteISOTZoneByIdIdx( ICFSecAuthorization Authorization,
		Short argKey )
	{
		schema.getJpaHooksSchema().getISOTZoneService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the ISOTZone instances identified by the key OffsetIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TZHourOffset	The ISOTZone key attribute of the instance generating the id.
	 *
	 *	@param	TZMinOffset	The ISOTZone key attribute of the instance generating the id.
	 */
	@Override
	public void deleteISOTZoneByOffsetIdx( ICFSecAuthorization Authorization,
		short argTZHourOffset,
		short argTZMinOffset )
	{
		schema.getJpaHooksSchema().getISOTZoneService().deleteByOffsetIdx(argTZHourOffset,
		argTZMinOffset);
	}


	/**
	 *	Delete the ISOTZone instances identified by the key OffsetIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteISOTZoneByOffsetIdx( ICFSecAuthorization Authorization,
		ICFSecISOTZoneByOffsetIdxKey argKey )
	{
		schema.getJpaHooksSchema().getISOTZoneService().deleteByOffsetIdx(argKey.getRequiredTZHourOffset(),
			argKey.getRequiredTZMinOffset());
	}

	/**
	 *	Delete the ISOTZone instances identified by the key UTZNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TZName	The ISOTZone key attribute of the instance generating the id.
	 */
	@Override
	public void deleteISOTZoneByUTZNameIdx( ICFSecAuthorization Authorization,
		String argTZName )
	{
		schema.getJpaHooksSchema().getISOTZoneService().deleteByUTZNameIdx(argTZName);
	}


	/**
	 *	Delete the ISOTZone instances identified by the key UTZNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteISOTZoneByUTZNameIdx( ICFSecAuthorization Authorization,
		ICFSecISOTZoneByUTZNameIdxKey argKey )
	{
		schema.getJpaHooksSchema().getISOTZoneService().deleteByUTZNameIdx(argKey.getRequiredTZName());
	}

	/**
	 *	Delete the ISOTZone instances identified by the key Iso8601Idx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Iso8601	The ISOTZone key attribute of the instance generating the id.
	 */
	@Override
	public void deleteISOTZoneByIso8601Idx( ICFSecAuthorization Authorization,
		String argIso8601 )
	{
		schema.getJpaHooksSchema().getISOTZoneService().deleteByIso8601Idx(argIso8601);
	}


	/**
	 *	Delete the ISOTZone instances identified by the key Iso8601Idx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteISOTZoneByIso8601Idx( ICFSecAuthorization Authorization,
		ICFSecISOTZoneByIso8601IdxKey argKey )
	{
		schema.getJpaHooksSchema().getISOTZoneService().deleteByIso8601Idx(argKey.getRequiredIso8601());
	}


	/**
	 *	Read the derived ISOTZone record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOTZone instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOTZone readDerived( ICFSecAuthorization Authorization,
		Short PKey )
	{
		return( schema.getJpaHooksSchema().getISOTZoneService().find(PKey) );
	}

	/**
	 *	Lock the derived ISOTZone record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOTZone instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOTZone lockDerived( ICFSecAuthorization Authorization,
		Short PKey )
	{
		return( schema.getJpaHooksSchema().getISOTZoneService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all ISOTZone instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecISOTZone[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaISOTZone> results = schema.getJpaHooksSchema().getISOTZoneService().findAll();
		ICFSecISOTZone[] retset = new ICFSecISOTZone[results.size()];
		int idx = 0;
		for (CFSecJpaISOTZone cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived ISOTZone record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOTZoneId	The ISOTZone key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOTZone readDerivedByIdIdx( ICFSecAuthorization Authorization,
		short argISOTZoneId )
	{
		return( schema.getJpaHooksSchema().getISOTZoneService().find(argISOTZoneId) );
	}

	/**
	 *	Read an array of the derived ISOTZone record instances identified by the duplicate key OffsetIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TZHourOffset	The ISOTZone key attribute of the instance generating the id.
	 *
	 *	@param	TZMinOffset	The ISOTZone key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecISOTZone[] readDerivedByOffsetIdx( ICFSecAuthorization Authorization,
		short argTZHourOffset,
		short argTZMinOffset )
	{
		List<CFSecJpaISOTZone> results = schema.getJpaHooksSchema().getISOTZoneService().findByOffsetIdx(argTZHourOffset,
		argTZMinOffset);
		ICFSecISOTZone[] retset = new ICFSecISOTZone[results.size()];
		int idx = 0;
		for (CFSecJpaISOTZone cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived ISOTZone record instance identified by the unique key UTZNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TZName	The ISOTZone key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecISOTZone readDerivedByUTZNameIdx( ICFSecAuthorization Authorization,
		String argTZName )
	{
		return( schema.getJpaHooksSchema().getISOTZoneService().findByUTZNameIdx(argTZName) );
	}

	/**
	 *	Read an array of the derived ISOTZone record instances identified by the duplicate key Iso8601Idx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Iso8601	The ISOTZone key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecISOTZone[] readDerivedByIso8601Idx( ICFSecAuthorization Authorization,
		String argIso8601 )
	{
		List<CFSecJpaISOTZone> results = schema.getJpaHooksSchema().getISOTZoneService().findByIso8601Idx(argIso8601);
		ICFSecISOTZone[] retset = new ICFSecISOTZone[results.size()];
		int idx = 0;
		for (CFSecJpaISOTZone cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific ISOTZone record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOTZone instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOTZone readRec( ICFSecAuthorization Authorization,
		Short PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific ISOTZone record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the ISOTZone instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOTZone lockRec( ICFSecAuthorization Authorization,
		Short PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific ISOTZone record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific ISOTZone instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecISOTZone[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific ISOTZone record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ISOTZoneId	The ISOTZone key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOTZone readRecByIdIdx( ICFSecAuthorization Authorization,
		short argISOTZoneId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific ISOTZone record instances identified by the duplicate key OffsetIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TZHourOffset	The ISOTZone key attribute of the instance generating the id.
	 *
	 *	@param	TZMinOffset	The ISOTZone key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOTZone[] readRecByOffsetIdx( ICFSecAuthorization Authorization,
		short argTZHourOffset,
		short argTZMinOffset )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByOffsetIdx");
	}

	/**
	 *	Read the specific ISOTZone record instance identified by the unique key UTZNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TZName	The ISOTZone key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOTZone readRecByUTZNameIdx( ICFSecAuthorization Authorization,
		String argTZName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUTZNameIdx");
	}

	/**
	 *	Read an array of the specific ISOTZone record instances identified by the duplicate key Iso8601Idx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Iso8601	The ISOTZone key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecISOTZone[] readRecByIso8601Idx( ICFSecAuthorization Authorization,
		String argIso8601 )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIso8601Idx");
	}
}

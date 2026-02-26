
// Description: Java 25 DbIO implementation for SecDevice.

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
 *	CFSecJpaSecDeviceTable database implementation for SecDevice
 */
public class CFSecJpaSecDeviceTable implements ICFSecSecDeviceTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaSecDeviceTable(ICFSecSchema schema) {
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
	public ICFSecSecDevice createSecDevice( ICFSecAuthorization Authorization,
		ICFSecSecDevice rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createSecDevice", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecDevice) {
			CFSecJpaSecDevice jparec = (CFSecJpaSecDevice)rec;
			CFSecJpaSecDevice created = schema.getJpaHooksSchema().getSecDeviceService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createSecDevice", "rec", rec, "CFSecJpaSecDevice");
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
	public ICFSecSecDevice updateSecDevice( ICFSecAuthorization Authorization,
		ICFSecSecDevice rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateSecDevice", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecDevice) {
			CFSecJpaSecDevice jparec = (CFSecJpaSecDevice)rec;
			CFSecJpaSecDevice updated = schema.getJpaHooksSchema().getSecDeviceService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateSecDevice", "rec", rec, "CFSecJpaSecDevice");
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
	public void deleteSecDevice( ICFSecAuthorization Authorization,
		ICFSecSecDevice rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaSecDevice) {
			CFSecJpaSecDevice jparec = (CFSecJpaSecDevice)rec;
			schema.getJpaHooksSchema().getSecDeviceService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteSecDevice", "rec", rec, "CFSecJpaSecDevice");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteSecDevice");
	}

	/**
	 *	Delete the SecDevice instance identified by the primary key attributes.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecDevice key attribute of the instance generating the id.
	 *
	 *	@param	DevName	The SecDevice key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecDeviceByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		String argDevName )
	{
		schema.getJpaHooksSchema().getSecDeviceService().deleteByIdIdx(argSecUserId,
		argDevName);
	}

	/**
	 *	Delete the SecDevice instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteSecDeviceByIdIdx( ICFSecAuthorization Authorization,
		ICFSecSecDevicePKey argKey )
	{
		schema.getJpaHooksSchema().getSecDeviceService().deleteByIdIdx(argKey.getRequiredSecUserId(),
			argKey.getRequiredDevName());
	}

	/**
	 *	Delete the SecDevice instances identified by the key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecDevice key attribute of the instance generating the id.
	 *
	 *	@param	DevName	The SecDevice key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecDeviceByNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		String argDevName )
	{
		schema.getJpaHooksSchema().getSecDeviceService().deleteByNameIdx(argSecUserId,
		argDevName);
	}


	/**
	 *	Delete the SecDevice instances identified by the key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecDeviceByNameIdx( ICFSecAuthorization Authorization,
		ICFSecSecDeviceByNameIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecDeviceService().deleteByNameIdx(argKey.getRequiredSecUserId(),
			argKey.getRequiredDevName());
	}

	/**
	 *	Delete the SecDevice instances identified by the key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecDevice key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecDeviceByUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId )
	{
		schema.getJpaHooksSchema().getSecDeviceService().deleteByUserIdx(argSecUserId);
	}


	/**
	 *	Delete the SecDevice instances identified by the key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecDeviceByUserIdx( ICFSecAuthorization Authorization,
		ICFSecSecDeviceByUserIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecDeviceService().deleteByUserIdx(argKey.getRequiredSecUserId());
	}


	/**
	 *	Read the derived SecDevice record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecDevice instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecDevice readDerived( ICFSecAuthorization Authorization,
		ICFSecSecDevicePKey PKey )
	{
		return( schema.getJpaHooksSchema().getSecDeviceService().find(PKey) );
	}

	/**
	 *	Read the derived SecDevice record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecDevice readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		String argDevName )
	{
		return( schema.getJpaHooksSchema().getSecDeviceService().find(argSecUserId,
		argDevName) );
	}

	/**
	 *	Lock the derived SecDevice record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecDevice instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecDevice lockDerived( ICFSecAuthorization Authorization,
		ICFSecSecDevicePKey PKey )
	{
		return( schema.getJpaHooksSchema().getSecDeviceService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all SecDevice instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecDevice[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaSecDevice> results = schema.getJpaHooksSchema().getSecDeviceService().findAll();
		ICFSecSecDevice[] retset = new ICFSecSecDevice[results.size()];
		int idx = 0;
		for (CFSecJpaSecDevice cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SecDevice record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecDevice key attribute of the instance generating the id.
	 *
	 *	@param	DevName	The SecDevice key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecDevice readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		String argDevName )
	{
		return( schema.getJpaHooksSchema().getSecDeviceService().find(argSecUserId,
		argDevName) );
	}

	/**
	 *	Read the derived SecDevice record instance identified by the unique key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecDevice key attribute of the instance generating the id.
	 *
	 *	@param	DevName	The SecDevice key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecDevice readDerivedByNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		String argDevName )
	{
		return( schema.getJpaHooksSchema().getSecDeviceService().findByNameIdx(argSecUserId,
		argDevName) );
	}

	/**
	 *	Read an array of the derived SecDevice record instances identified by the duplicate key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecDevice key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecDevice[] readDerivedByUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId )
	{
		List<CFSecJpaSecDevice> results = schema.getJpaHooksSchema().getSecDeviceService().findByUserIdx(argSecUserId);
		ICFSecSecDevice[] retset = new ICFSecSecDevice[results.size()];
		int idx = 0;
		for (CFSecJpaSecDevice cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific SecDevice record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecDevice instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecDevice readRec( ICFSecAuthorization Authorization,
		ICFSecSecDevicePKey PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Read the specific SecDevice record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecDevice instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecDevice readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		String argDevName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec-args");
	}

	/**
	 *	Lock the specific SecDevice record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecDevice instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecDevice lockRec( ICFSecAuthorization Authorization,
		ICFSecSecDevicePKey PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific SecDevice record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecDevice instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecDevice[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read a page of all the specific SecDevice record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecDevice instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecDevice[] pageAllRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 priorSecUserId,
		String priorDevName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageAllRec");
	}

	/**
	 *	Read the specific SecDevice record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecDevice key attribute of the instance generating the id.
	 *
	 *	@param	DevName	The SecDevice key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecDevice readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		String argDevName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read the specific SecDevice record instance identified by the unique key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecDevice key attribute of the instance generating the id.
	 *
	 *	@param	DevName	The SecDevice key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecDevice readRecByNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		String argDevName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByNameIdx");
	}

	/**
	 *	Read an array of the specific SecDevice record instances identified by the duplicate key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecDevice key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecDevice[] readRecByUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUserIdx");
	}

	/**
	 *	Read a page array of the specific SecDevice record instances identified by the duplicate key UserIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecDevice key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecDevice[] pageRecByUserIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId,
		CFLibDbKeyHash256 priorSecUserId,
		String priorDevName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByUserIdx");
	}
}

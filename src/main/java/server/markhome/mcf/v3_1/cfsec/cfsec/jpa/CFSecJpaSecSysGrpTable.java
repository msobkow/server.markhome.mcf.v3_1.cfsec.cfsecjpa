
// Description: Java 25 DbIO implementation for SecSysGrp.

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
 *	CFSecJpaSecSysGrpTable database implementation for SecSysGrp
 */
public class CFSecJpaSecSysGrpTable implements ICFSecSecSysGrpTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaSecSysGrpTable(ICFSecSchema schema) {
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
	public ICFSecSecSysGrp createSecSysGrp( ICFSecAuthorization Authorization,
		ICFSecSecSysGrp rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createSecSysGrp", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecSysGrp) {
			CFSecJpaSecSysGrp jparec = (CFSecJpaSecSysGrp)rec;
			CFSecJpaSecSysGrp created = schema.getJpaHooksSchema().getSecSysGrpService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createSecSysGrp", "rec", rec, "CFSecJpaSecSysGrp");
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
	public ICFSecSecSysGrp updateSecSysGrp( ICFSecAuthorization Authorization,
		ICFSecSecSysGrp rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateSecSysGrp", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecSysGrp) {
			CFSecJpaSecSysGrp jparec = (CFSecJpaSecSysGrp)rec;
			CFSecJpaSecSysGrp updated = schema.getJpaHooksSchema().getSecSysGrpService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateSecSysGrp", "rec", rec, "CFSecJpaSecSysGrp");
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
	public void deleteSecSysGrp( ICFSecAuthorization Authorization,
		ICFSecSecSysGrp rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaSecSysGrp) {
			CFSecJpaSecSysGrp jparec = (CFSecJpaSecSysGrp)rec;
			schema.getJpaHooksSchema().getSecSysGrpService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteSecSysGrp", "rec", rec, "CFSecJpaSecSysGrp");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteSecSysGrp");
	}

	/**
	 *	Delete the SecSysGrp instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteSecSysGrpByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argKey )
	{
		schema.getJpaHooksSchema().getSecSysGrpService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the SecSysGrp instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Name	The SecSysGrp key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecSysGrpByUNameIdx( ICFSecAuthorization Authorization,
		String argName )
	{
		schema.getJpaHooksSchema().getSecSysGrpService().deleteByUNameIdx(argName);
	}


	/**
	 *	Delete the SecSysGrp instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecSysGrpByUNameIdx( ICFSecAuthorization Authorization,
		ICFSecSecSysGrpByUNameIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecSysGrpService().deleteByUNameIdx(argKey.getRequiredName());
	}

	/**
	 *	Delete the SecSysGrp instances identified by the key SecLevelIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecLevel	The SecSysGrp key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecSysGrpBySecLevelIdx( ICFSecAuthorization Authorization,
		ICFSecSchema.SecLevelEnum argSecLevel )
	{
		schema.getJpaHooksSchema().getSecSysGrpService().deleteBySecLevelIdx(argSecLevel);
	}


	/**
	 *	Delete the SecSysGrp instances identified by the key SecLevelIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecSysGrpBySecLevelIdx( ICFSecAuthorization Authorization,
		ICFSecSecSysGrpBySecLevelIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecSysGrpService().deleteBySecLevelIdx(argKey.getRequiredSecLevel());
	}


	/**
	 *	Read the derived SecSysGrp record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecSysGrp instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecSysGrp readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "readDerived";
		if (Authorization == null) {
			throw new CFLibNullArgumentException(getClass(), S_ProcName, 0, "Authorization");
		}
		boolean permissionGranted = false;
		CFLibDbKeyHash256 authUserId = Authorization.getSecUserId();
		if ((!permissionGranted) && (authUserId == null || authUserId.isNull())) {
			throw new CFLibNullArgumentException(getClass(), S_ProcName, 0, "Authorization.getSecUserId()");
		}
		// Check for "system" user
		CFLibDbKeyHash256 sysAdminId = ICFSecSchema.getSysAdminId();
		if ((!permissionGranted) && (sysAdminId != null && !sysAdminId.isNull() && sysAdminId.equals(authUserId))) {
			permissionGranted = true;
		}
		else if ((!permissionGranted) && (sysAdminId == null || sysAdminId.isNull())) {
			throw new CFLibNullArgumentException(getClass(), S_ProcName, 0, "ICFSecSchema.getSysAdminId()");
		}
		if(!permissionGranted) {
			permissionGranted = ICFSecSchema.getBackingCFSec().isMemberOfSystemGroup(Authorization.getSecUserId(), "readsecsysgrp");
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFSecSchema.SCHEMA_NAME, ICFSecSecSysGrpTable.TABLE_NAME, "readsecsysgrp", authUserId.toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFSecSecSysGrp retval = schema.getJpaHooksSchema().getSecSysGrpService().find(PKey);
		return( retval );
	}

	/**
	 *	Lock the derived SecSysGrp record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecSysGrp instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecSysGrp lockDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		final String S_ProcName = "lockDerived";
		if (Authorization == null) {
			throw new CFLibNullArgumentException(getClass(), S_ProcName, 0, "Authorization");
		}
		boolean permissionGranted = false;
		CFLibDbKeyHash256 authUserId = Authorization.getSecUserId();
		if ((!permissionGranted) && (authUserId == null || authUserId.isNull())) {
			throw new CFLibNullArgumentException(getClass(), S_ProcName, 0, "Authorization.getSecUserId()");
		}
		// Check for "system" user
		CFLibDbKeyHash256 sysAdminId = ICFSecSchema.getSysAdminId();
		if ((!permissionGranted) && (sysAdminId != null && !sysAdminId.isNull() && sysAdminId.equals(authUserId))) {
			permissionGranted = true;
		}
		else if ((!permissionGranted) && (sysAdminId == null || sysAdminId.isNull())) {
			throw new CFLibNullArgumentException(getClass(), S_ProcName, 0, "ICFSecSchema.getSysAdminId()");
		}
		if(!permissionGranted) {
			permissionGranted = ICFSecSchema.getBackingCFSec().isMemberOfSystemGroup(Authorization.getSecUserId(), "updatesecsysgrp");
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFSecSchema.SCHEMA_NAME, ICFSecSecSysGrpTable.TABLE_NAME, "updatesecsysgrp", authUserId.toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFSecSecSysGrp retval = schema.getJpaHooksSchema().getSecSysGrpService().lockByIdIdx(PKey);
		return( retval );
	}

	/**
	 *	Read all SecSysGrp instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecSysGrp[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaSecSysGrp> results = schema.getJpaHooksSchema().getSecSysGrpService().findAll();
		ICFSecSecSysGrp[] retset = new ICFSecSecSysGrp[results.size()];
		int idx = 0;
		for (CFSecJpaSecSysGrp cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SecSysGrp record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecSysGrpId	The SecSysGrp key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecSysGrp readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecSysGrpId )
	{
		return( schema.getJpaHooksSchema().getSecSysGrpService().find(argSecSysGrpId) );
	}

	/**
	 *	Read the derived SecSysGrp record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Name	The SecSysGrp key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecSysGrp readDerivedByUNameIdx( ICFSecAuthorization Authorization,
		String argName )
	{
		return( schema.getJpaHooksSchema().getSecSysGrpService().findByUNameIdx(argName) );
	}

	/**
	 *	Read an array of the derived SecSysGrp record instances identified by the duplicate key SecLevelIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecLevel	The SecSysGrp key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecSysGrp[] readDerivedBySecLevelIdx( ICFSecAuthorization Authorization,
		ICFSecSchema.SecLevelEnum argSecLevel )
	{
		List<CFSecJpaSecSysGrp> results = schema.getJpaHooksSchema().getSecSysGrpService().findBySecLevelIdx(argSecLevel);
		ICFSecSecSysGrp[] retset = new ICFSecSecSysGrp[results.size()];
		int idx = 0;
		for (CFSecJpaSecSysGrp cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific SecSysGrp record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecSysGrp instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSysGrp readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific SecSysGrp record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecSysGrp instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSysGrp lockRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific SecSysGrp record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecSysGrp instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecSysGrp[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific SecSysGrp record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecSysGrpId	The SecSysGrp key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSysGrp readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecSysGrpId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read the specific SecSysGrp record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Name	The SecSysGrp key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSysGrp readRecByUNameIdx( ICFSecAuthorization Authorization,
		String argName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUNameIdx");
	}

	/**
	 *	Read an array of the specific SecSysGrp record instances identified by the duplicate key SecLevelIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecLevel	The SecSysGrp key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecSysGrp[] readRecBySecLevelIdx( ICFSecAuthorization Authorization,
		ICFSecSchema.SecLevelEnum argSecLevel )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecBySecLevelIdx");
	}
}

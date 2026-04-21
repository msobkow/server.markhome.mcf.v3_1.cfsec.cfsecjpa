
// Description: Java 25 DbIO implementation for SecTentGrp.

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
 *	CFSecJpaSecTentGrpTable database implementation for SecTentGrp
 */
public class CFSecJpaSecTentGrpTable implements ICFSecSecTentGrpTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaSecTentGrpTable(ICFSecSchema schema) {
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
	public ICFSecSecTentGrp createSecTentGrp( ICFSecAuthorization Authorization,
		ICFSecSecTentGrp rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createSecTentGrp", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecTentGrp) {
			CFSecJpaSecTentGrp jparec = (CFSecJpaSecTentGrp)rec;
			CFSecJpaSecTentGrp created = schema.getJpaHooksSchema().getSecTentGrpService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createSecTentGrp", "rec", rec, "CFSecJpaSecTentGrp");
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
	public ICFSecSecTentGrp updateSecTentGrp( ICFSecAuthorization Authorization,
		ICFSecSecTentGrp rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateSecTentGrp", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecTentGrp) {
			CFSecJpaSecTentGrp jparec = (CFSecJpaSecTentGrp)rec;
			CFSecJpaSecTentGrp updated = schema.getJpaHooksSchema().getSecTentGrpService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateSecTentGrp", "rec", rec, "CFSecJpaSecTentGrp");
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
	public void deleteSecTentGrp( ICFSecAuthorization Authorization,
		ICFSecSecTentGrp rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaSecTentGrp) {
			CFSecJpaSecTentGrp jparec = (CFSecJpaSecTentGrp)rec;
			schema.getJpaHooksSchema().getSecTentGrpService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteSecTentGrp", "rec", rec, "CFSecJpaSecTentGrp");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteSecTentGrp");
	}

	/**
	 *	Delete the SecTentGrp instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteSecTentGrpByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argKey )
	{
		schema.getJpaHooksSchema().getSecTentGrpService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the SecTentGrp instances identified by the key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The SecTentGrp key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecTentGrpByTenantIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId )
	{
		schema.getJpaHooksSchema().getSecTentGrpService().deleteByTenantIdx(argTenantId);
	}


	/**
	 *	Delete the SecTentGrp instances identified by the key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecTentGrpByTenantIdx( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpByTenantIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecTentGrpService().deleteByTenantIdx(argKey.getRequiredTenantId());
	}

	/**
	 *	Delete the SecTentGrp instances identified by the key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Name	The SecTentGrp key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecTentGrpByNameIdx( ICFSecAuthorization Authorization,
		String argName )
	{
		schema.getJpaHooksSchema().getSecTentGrpService().deleteByNameIdx(argName);
	}


	/**
	 *	Delete the SecTentGrp instances identified by the key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecTentGrpByNameIdx( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpByNameIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecTentGrpService().deleteByNameIdx(argKey.getRequiredName());
	}

	/**
	 *	Delete the SecTentGrp instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The SecTentGrp key attribute of the instance generating the id.
	 *
	 *	@param	Name	The SecTentGrp key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecTentGrpByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId,
		String argName )
	{
		schema.getJpaHooksSchema().getSecTentGrpService().deleteByUNameIdx(argTenantId,
		argName);
	}


	/**
	 *	Delete the SecTentGrp instances identified by the key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecTentGrpByUNameIdx( ICFSecAuthorization Authorization,
		ICFSecSecTentGrpByUNameIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecTentGrpService().deleteByUNameIdx(argKey.getRequiredTenantId(),
			argKey.getRequiredName());
	}


	/**
	 *	Read the derived SecTentGrp record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecTentGrp instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecTentGrp readDerived( ICFSecAuthorization Authorization,
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
			permissionGranted = ICFSecSchema.getBackingCFSec().isMemberOfTenantGroup(Authorization.getSecUserId(), Authorization.getSecTenantId(), "readsectentgrp");
		}
		if(!permissionGranted) {
			permissionGranted = ICFSecSchema.getBackingCFSec().isMemberOfClusterGroup(Authorization.getSecUserId(), Authorization.getSecClusterId(), "readsectentgrp");
		}
		if(!permissionGranted) {
			permissionGranted = ICFSecSchema.getBackingCFSec().isMemberOfSystemGroup(Authorization.getSecUserId(), "readsectentgrp");
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFSecSchema.SCHEMA_NAME, ICFSecSecTentGrpTable.TABLE_NAME, "readsectentgrp", authUserId.toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFSecSecTentGrp retval = schema.getJpaHooksSchema().getSecTentGrpService().find(PKey);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,TenantId,'readsectentgrp'), clear retval to null if not a member
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getBackingCFSec().isMemberOfTenantGroup(Authorization.getSecUserId(), effTenantId, "readsectentgrp")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Lock the derived SecTentGrp record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecTentGrp instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecTentGrp lockDerived( ICFSecAuthorization Authorization,
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
			permissionGranted = ICFSecSchema.getBackingCFSec().isMemberOfTenantGroup(Authorization.getSecUserId(), ICFSecSchema.getSysTenantId(), "updatesectentgrp");
		}
		if(!permissionGranted) {
			permissionGranted = ICFSecSchema.getBackingCFSec().isMemberOfClusterGroup(Authorization.getSecUserId(), ICFSecSchema.getSysClusterId(), "updatesectentgrp");
		}
		if(!permissionGranted) {
			permissionGranted = ICFSecSchema.getBackingCFSec().isMemberOfSystemGroup(Authorization.getSecUserId(), "updatesectentgrp");
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFSecSchema.SCHEMA_NAME, ICFSecSecTentGrpTable.TABLE_NAME, "updatesectentgrp", authUserId.toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFSecSecTentGrp retval = schema.getJpaHooksSchema().getSecTentGrpService().lockByIdIdx(PKey);
		if(retval != null) {
			// Retrieve the TenantId from retval and check ICFSec.backingSchema().isMemberOfTenantGroup(auth,TenantId,'readsectentgrp'), clear retval to null if not a member
			CFLibDbKeyHash256 effTenantId = CFLibDbKeyHash256.nullGet();
			if (!ICFSecSchema.getBackingCFSec().isMemberOfTenantGroup(Authorization.getSecUserId(), effTenantId, "readsectentgrp")) {
				retval = null;
			}
		}
		return( retval );
	}

	/**
	 *	Read all SecTentGrp instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecTentGrp[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaSecTentGrp> results = schema.getJpaHooksSchema().getSecTentGrpService().findAll();
		ICFSecSecTentGrp[] retset = new ICFSecSecTentGrp[results.size()];
		int idx = 0;
		for (CFSecJpaSecTentGrp cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SecTentGrp record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecTentGrpId	The SecTentGrp key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecTentGrp readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecTentGrpId )
	{
		return( schema.getJpaHooksSchema().getSecTentGrpService().find(argSecTentGrpId) );
	}

	/**
	 *	Read an array of the derived SecTentGrp record instances identified by the duplicate key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The SecTentGrp key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecTentGrp[] readDerivedByTenantIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId )
	{
		List<CFSecJpaSecTentGrp> results = schema.getJpaHooksSchema().getSecTentGrpService().findByTenantIdx(argTenantId);
		ICFSecSecTentGrp[] retset = new ICFSecSecTentGrp[results.size()];
		int idx = 0;
		for (CFSecJpaSecTentGrp cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived SecTentGrp record instances identified by the duplicate key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Name	The SecTentGrp key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecTentGrp[] readDerivedByNameIdx( ICFSecAuthorization Authorization,
		String argName )
	{
		List<CFSecJpaSecTentGrp> results = schema.getJpaHooksSchema().getSecTentGrpService().findByNameIdx(argName);
		ICFSecSecTentGrp[] retset = new ICFSecSecTentGrp[results.size()];
		int idx = 0;
		for (CFSecJpaSecTentGrp cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SecTentGrp record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The SecTentGrp key attribute of the instance generating the id.
	 *
	 *	@param	Name	The SecTentGrp key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecTentGrp readDerivedByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId,
		String argName )
	{
		return( schema.getJpaHooksSchema().getSecTentGrpService().findByUNameIdx(argTenantId,
		argName) );
	}

	/**
	 *	Read the specific SecTentGrp record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecTentGrp instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrp readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific SecTentGrp record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecTentGrp instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrp lockRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific SecTentGrp record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecTentGrp instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecTentGrp[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read the specific SecTentGrp record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecTentGrpId	The SecTentGrp key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrp readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecTentGrpId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific SecTentGrp record instances identified by the duplicate key TenantIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The SecTentGrp key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrp[] readRecByTenantIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByTenantIdx");
	}

	/**
	 *	Read an array of the specific SecTentGrp record instances identified by the duplicate key NameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	Name	The SecTentGrp key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrp[] readRecByNameIdx( ICFSecAuthorization Authorization,
		String argName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByNameIdx");
	}

	/**
	 *	Read the specific SecTentGrp record instance identified by the unique key UNameIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	TenantId	The SecTentGrp key attribute of the instance generating the id.
	 *
	 *	@param	Name	The SecTentGrp key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecTentGrp readRecByUNameIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argTenantId,
		String argName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUNameIdx");
	}
}

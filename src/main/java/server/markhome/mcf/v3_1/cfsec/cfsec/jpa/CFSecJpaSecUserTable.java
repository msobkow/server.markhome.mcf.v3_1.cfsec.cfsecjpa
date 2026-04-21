
// Description: Java 25 DbIO implementation for SecUser.

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
 *	CFSecJpaSecUserTable database implementation for SecUser
 */
public class CFSecJpaSecUserTable implements ICFSecSecUserTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaSecUserTable(ICFSecSchema schema) {
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
	public ICFSecSecUser createSecUser( ICFSecAuthorization Authorization,
		ICFSecSecUser rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createSecUser", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecUser) {
			CFSecJpaSecUser jparec = (CFSecJpaSecUser)rec;
			CFSecJpaSecUser created = schema.getJpaHooksSchema().getSecUserService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createSecUser", "rec", rec, "CFSecJpaSecUser");
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
	public ICFSecSecUser updateSecUser( ICFSecAuthorization Authorization,
		ICFSecSecUser rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateSecUser", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecUser) {
			CFSecJpaSecUser jparec = (CFSecJpaSecUser)rec;
			CFSecJpaSecUser updated = schema.getJpaHooksSchema().getSecUserService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateSecUser", "rec", rec, "CFSecJpaSecUser");
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
	public void deleteSecUser( ICFSecAuthorization Authorization,
		ICFSecSecUser rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaSecUser) {
			CFSecJpaSecUser jparec = (CFSecJpaSecUser)rec;
			schema.getJpaHooksSchema().getSecUserService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteSecUser", "rec", rec, "CFSecJpaSecUser");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteSecUser");
	}

	/**
	 *	Delete the SecUser instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteSecUserByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argKey )
	{
		schema.getJpaHooksSchema().getSecUserService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the SecUser instances identified by the key ULoginIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	LoginId	The SecUser key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecUserByULoginIdx( ICFSecAuthorization Authorization,
		String argLoginId )
	{
		schema.getJpaHooksSchema().getSecUserService().deleteByULoginIdx(argLoginId);
	}


	/**
	 *	Delete the SecUser instances identified by the key ULoginIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecUserByULoginIdx( ICFSecAuthorization Authorization,
		ICFSecSecUserByULoginIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecUserService().deleteByULoginIdx(argKey.getRequiredLoginId());
	}

	/**
	 *	Delete the SecUser instances identified by the key EMAddrIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	EMailAddress	The SecUser key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecUserByEMAddrIdx( ICFSecAuthorization Authorization,
		String argEMailAddress )
	{
		schema.getJpaHooksSchema().getSecUserService().deleteByEMAddrIdx(argEMailAddress);
	}


	/**
	 *	Delete the SecUser instances identified by the key EMAddrIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecUserByEMAddrIdx( ICFSecAuthorization Authorization,
		ICFSecSecUserByEMAddrIdxKey argKey )
	{
		schema.getJpaHooksSchema().getSecUserService().deleteByEMAddrIdx(argKey.getRequiredEMailAddress());
	}


	/**
	 *	Read the derived SecUser record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecUser instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecUser readDerived( ICFSecAuthorization Authorization,
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
			permissionGranted = ICFSecSchema.getBackingCFSec().isMemberOfSystemGroup(Authorization.getSecUserId(), "readsecuser");
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFSecSchema.SCHEMA_NAME, ICFSecSecUserTable.TABLE_NAME, "readsecuser", authUserId.toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFSecSecUser retval = schema.getJpaHooksSchema().getSecUserService().find(PKey);
		return( retval );
	}

	/**
	 *	Lock the derived SecUser record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecUser instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecUser lockDerived( ICFSecAuthorization Authorization,
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
			permissionGranted = ICFSecSchema.getBackingCFSec().isMemberOfSystemGroup(Authorization.getSecUserId(), "updatesecuser");
		}
		if (!permissionGranted) {
			throw new CFLibPermissionDeniedException(getClass(), S_ProcName, ICFSecSchema.SCHEMA_NAME, ICFSecSecUserTable.TABLE_NAME, "updatesecuser", authUserId.toString());//"Permission '%4$s' denied attempting to access %1$s.%2$s for user id %3$s"
		}

		ICFSecSecUser retval = schema.getJpaHooksSchema().getSecUserService().lockByIdIdx(PKey);
		return( retval );
	}

	/**
	 *	Read all SecUser instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecUser[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaSecUser> results = schema.getJpaHooksSchema().getSecUserService().findAll();
		ICFSecSecUser[] retset = new ICFSecSecUser[results.size()];
		int idx = 0;
		for (CFSecJpaSecUser cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived SecUser record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecUser readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId )
	{
		return( schema.getJpaHooksSchema().getSecUserService().find(argSecUserId) );
	}

	/**
	 *	Read the derived SecUser record instance identified by the unique key ULoginIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	LoginId	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecSecUser readDerivedByULoginIdx( ICFSecAuthorization Authorization,
		String argLoginId )
	{
		return( schema.getJpaHooksSchema().getSecUserService().findByULoginIdx(argLoginId) );
	}

	/**
	 *	Read an array of the derived SecUser record instances identified by the duplicate key EMAddrIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	EMailAddress	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecUser[] readDerivedByEMAddrIdx( ICFSecAuthorization Authorization,
		String argEMailAddress )
	{
		List<CFSecJpaSecUser> results = schema.getJpaHooksSchema().getSecUserService().findByEMAddrIdx(argEMailAddress);
		ICFSecSecUser[] retset = new ICFSecSecUser[results.size()];
		int idx = 0;
		for (CFSecJpaSecUser cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the specific SecUser record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecUser instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecUser readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific SecUser record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the SecUser instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecUser lockRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific SecUser record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecUser instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecUser[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read a page of all the specific SecUser record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific SecUser instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecSecUser[] pageAllRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 priorSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageAllRec");
	}

	/**
	 *	Read the specific SecUser record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	SecUserId	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecUser readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read the specific SecUser record instance identified by the unique key ULoginIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	LoginId	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecUser readRecByULoginIdx( ICFSecAuthorization Authorization,
		String argLoginId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByULoginIdx");
	}

	/**
	 *	Read an array of the specific SecUser record instances identified by the duplicate key EMAddrIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	EMailAddress	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecUser[] readRecByEMAddrIdx( ICFSecAuthorization Authorization,
		String argEMailAddress )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByEMAddrIdx");
	}

	/**
	 *	Read a page array of the specific SecUser record instances identified by the duplicate key EMAddrIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	EMailAddress	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecUser[] pageRecByEMAddrIdx( ICFSecAuthorization Authorization,
		String argEMailAddress,
		CFLibDbKeyHash256 priorSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByEMAddrIdx");
	}
}

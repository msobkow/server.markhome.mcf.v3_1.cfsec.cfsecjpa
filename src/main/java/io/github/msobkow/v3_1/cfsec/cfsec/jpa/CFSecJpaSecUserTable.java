
// Description: Java 25 DbIO implementation for SecUser.

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
 *	CFSecJpaSecUserTable database implementation for SecUser
 */
public class CFSecJpaSecUserTable implements ICFSecSecUserTable
{
	protected CFSecJpaSchema schema;
	protected CFSecJpaHooksSchema jpaHooksSchema;


	public CFSecJpaSecUserTable(ICFSecSchema schema) {
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
	public ICFSecSecUser createSecUser( ICFSecAuthorization Authorization,
		ICFSecSecUser rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createSecUser", 1, "rec");
		}
		else if (rec instanceof CFSecJpaSecUser) {
			CFSecJpaSecUser jparec = (CFSecJpaSecUser)rec;
			CFSecJpaSecUser created = jpaHooksSchema.getSecUserService().create(jparec);
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
			CFSecJpaSecUser updated = jpaHooksSchema.getSecUserService().update(jparec);
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
			jpaHooksSchema.getSecUserService().deleteByIdIdx(jparec.getPKey());
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
		jpaHooksSchema.getSecUserService().deleteByIdIdx(argKey);
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
		jpaHooksSchema.getSecUserService().deleteByULoginIdx(argLoginId);
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
		jpaHooksSchema.getSecUserService().deleteByULoginIdx(argKey.getRequiredLoginId());
	}

	/**
	 *	Delete the SecUser instances identified by the key EMConfIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	EMailConfirmUuid6	The SecUser key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecUserByEMConfIdx( ICFSecAuthorization Authorization,
		CFLibUuid6 argEMailConfirmUuid6 )
	{
		jpaHooksSchema.getSecUserService().deleteByEMConfIdx(argEMailConfirmUuid6);
	}


	/**
	 *	Delete the SecUser instances identified by the key EMConfIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecUserByEMConfIdx( ICFSecAuthorization Authorization,
		ICFSecSecUserByEMConfIdxKey argKey )
	{
		jpaHooksSchema.getSecUserService().deleteByEMConfIdx(argKey.getOptionalEMailConfirmUuid6());
	}

	/**
	 *	Delete the SecUser instances identified by the key PwdResetIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PasswordResetUuid6	The SecUser key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecUserByPwdResetIdx( ICFSecAuthorization Authorization,
		CFLibUuid6 argPasswordResetUuid6 )
	{
		jpaHooksSchema.getSecUserService().deleteByPwdResetIdx(argPasswordResetUuid6);
	}


	/**
	 *	Delete the SecUser instances identified by the key PwdResetIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecUserByPwdResetIdx( ICFSecAuthorization Authorization,
		ICFSecSecUserByPwdResetIdxKey argKey )
	{
		jpaHooksSchema.getSecUserService().deleteByPwdResetIdx(argKey.getOptionalPasswordResetUuid6());
	}

	/**
	 *	Delete the SecUser instances identified by the key DefDevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DfltDevUserId	The SecUser key attribute of the instance generating the id.
	 *
	 *	@param	DfltDevName	The SecUser key attribute of the instance generating the id.
	 */
	@Override
	public void deleteSecUserByDefDevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argDfltDevUserId,
		String argDfltDevName )
	{
		jpaHooksSchema.getSecUserService().deleteByDefDevIdx(argDfltDevUserId,
		argDfltDevName);
	}


	/**
	 *	Delete the SecUser instances identified by the key DefDevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteSecUserByDefDevIdx( ICFSecAuthorization Authorization,
		ICFSecSecUserByDefDevIdxKey argKey )
	{
		jpaHooksSchema.getSecUserService().deleteByDefDevIdx(argKey.getOptionalDfltDevUserId(),
			argKey.getOptionalDfltDevName());
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
		return( jpaHooksSchema.getSecUserService().find(PKey) );
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
		return( jpaHooksSchema.getSecUserService().lockByIdIdx(PKey) );
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
		List<CFSecJpaSecUser> results = jpaHooksSchema.getSecUserService().findAll();
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
		return( jpaHooksSchema.getSecUserService().find(argSecUserId) );
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
		return( jpaHooksSchema.getSecUserService().findByULoginIdx(argLoginId) );
	}

	/**
	 *	Read an array of the derived SecUser record instances identified by the duplicate key EMConfIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	EMailConfirmUuid6	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecUser[] readDerivedByEMConfIdx( ICFSecAuthorization Authorization,
		CFLibUuid6 argEMailConfirmUuid6 )
	{
		List<CFSecJpaSecUser> results = jpaHooksSchema.getSecUserService().findByEMConfIdx(argEMailConfirmUuid6);
		ICFSecSecUser[] retset = new ICFSecSecUser[results.size()];
		int idx = 0;
		for (CFSecJpaSecUser cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived SecUser record instances identified by the duplicate key PwdResetIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PasswordResetUuid6	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecUser[] readDerivedByPwdResetIdx( ICFSecAuthorization Authorization,
		CFLibUuid6 argPasswordResetUuid6 )
	{
		List<CFSecJpaSecUser> results = jpaHooksSchema.getSecUserService().findByPwdResetIdx(argPasswordResetUuid6);
		ICFSecSecUser[] retset = new ICFSecSecUser[results.size()];
		int idx = 0;
		for (CFSecJpaSecUser cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived SecUser record instances identified by the duplicate key DefDevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DfltDevUserId	The SecUser key attribute of the instance generating the id.
	 *
	 *	@param	DfltDevName	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecSecUser[] readDerivedByDefDevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argDfltDevUserId,
		String argDfltDevName )
	{
		List<CFSecJpaSecUser> results = jpaHooksSchema.getSecUserService().findByDefDevIdx(argDfltDevUserId,
		argDfltDevName);
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
	 *	Read an array of the specific SecUser record instances identified by the duplicate key EMConfIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	EMailConfirmUuid6	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecUser[] readRecByEMConfIdx( ICFSecAuthorization Authorization,
		CFLibUuid6 argEMailConfirmUuid6 )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByEMConfIdx");
	}

	/**
	 *	Read an array of the specific SecUser record instances identified by the duplicate key PwdResetIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PasswordResetUuid6	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecUser[] readRecByPwdResetIdx( ICFSecAuthorization Authorization,
		CFLibUuid6 argPasswordResetUuid6 )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByPwdResetIdx");
	}

	/**
	 *	Read an array of the specific SecUser record instances identified by the duplicate key DefDevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DfltDevUserId	The SecUser key attribute of the instance generating the id.
	 *
	 *	@param	DfltDevName	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecUser[] readRecByDefDevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argDfltDevUserId,
		String argDfltDevName )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByDefDevIdx");
	}

	/**
	 *	Read a page array of the specific SecUser record instances identified by the duplicate key EMConfIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	EMailConfirmUuid6	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecUser[] pageRecByEMConfIdx( ICFSecAuthorization Authorization,
		CFLibUuid6 argEMailConfirmUuid6,
		CFLibDbKeyHash256 priorSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByEMConfIdx");
	}

	/**
	 *	Read a page array of the specific SecUser record instances identified by the duplicate key PwdResetIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PasswordResetUuid6	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecUser[] pageRecByPwdResetIdx( ICFSecAuthorization Authorization,
		CFLibUuid6 argPasswordResetUuid6,
		CFLibDbKeyHash256 priorSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByPwdResetIdx");
	}

	/**
	 *	Read a page array of the specific SecUser record instances identified by the duplicate key DefDevIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	DfltDevUserId	The SecUser key attribute of the instance generating the id.
	 *
	 *	@param	DfltDevName	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecSecUser[] pageRecByDefDevIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argDfltDevUserId,
		String argDfltDevName,
		CFLibDbKeyHash256 priorSecUserId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByDefDevIdx");
	}
}

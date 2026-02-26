
// Description: Java 25 DbIO implementation for Service.

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
 *	CFSecJpaServiceTable database implementation for Service
 */
public class CFSecJpaServiceTable implements ICFSecServiceTable
{
	protected CFSecJpaSchema schema;


	public CFSecJpaServiceTable(ICFSecSchema schema) {
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
	public ICFSecService createService( ICFSecAuthorization Authorization,
		ICFSecService rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "createService", 1, "rec");
		}
		else if (rec instanceof CFSecJpaService) {
			CFSecJpaService jparec = (CFSecJpaService)rec;
			CFSecJpaService created = schema.getJpaHooksSchema().getServiceService().create(jparec);
			return( created );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "createService", "rec", rec, "CFSecJpaService");
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
	public ICFSecService updateService( ICFSecAuthorization Authorization,
		ICFSecService rec )
	{
		if (rec == null) {
			throw new CFLibNullArgumentException(getClass(), "updateService", 1, "rec");
		}
		else if (rec instanceof CFSecJpaService) {
			CFSecJpaService jparec = (CFSecJpaService)rec;
			CFSecJpaService updated = schema.getJpaHooksSchema().getServiceService().update(jparec);
			return( updated );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "updateService", "rec", rec, "CFSecJpaService");
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
	public void deleteService( ICFSecAuthorization Authorization,
		ICFSecService rec )
	{
		if (rec == null) {
			return;
		}
		if (rec instanceof CFSecJpaService) {
			CFSecJpaService jparec = (CFSecJpaService)rec;
			schema.getJpaHooksSchema().getServiceService().deleteByIdIdx(jparec.getPKey());
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "deleteService", "rec", rec, "CFSecJpaService");
		}

		throw new CFLibNotImplementedYetException(getClass(), "deleteService");
	}

	/**
	 *	Delete the Service instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The primary key identifying the instance to be deleted.
	 */
	@Override
	public void deleteServiceByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argKey )
	{
		schema.getJpaHooksSchema().getServiceService().deleteByIdIdx(argKey);
	}

	/**
	 *	Delete the Service instances identified by the key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The Service key attribute of the instance generating the id.
	 */
	@Override
	public void deleteServiceByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		schema.getJpaHooksSchema().getServiceService().deleteByClusterIdx(argClusterId);
	}


	/**
	 *	Delete the Service instances identified by the key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteServiceByClusterIdx( ICFSecAuthorization Authorization,
		ICFSecServiceByClusterIdxKey argKey )
	{
		schema.getJpaHooksSchema().getServiceService().deleteByClusterIdx(argKey.getRequiredClusterId());
	}

	/**
	 *	Delete the Service instances identified by the key HostIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	HostNodeId	The Service key attribute of the instance generating the id.
	 */
	@Override
	public void deleteServiceByHostIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argHostNodeId )
	{
		schema.getJpaHooksSchema().getServiceService().deleteByHostIdx(argHostNodeId);
	}


	/**
	 *	Delete the Service instances identified by the key HostIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteServiceByHostIdx( ICFSecAuthorization Authorization,
		ICFSecServiceByHostIdxKey argKey )
	{
		schema.getJpaHooksSchema().getServiceService().deleteByHostIdx(argKey.getRequiredHostNodeId());
	}

	/**
	 *	Delete the Service instances identified by the key TypeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ServiceTypeId	The Service key attribute of the instance generating the id.
	 */
	@Override
	public void deleteServiceByTypeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argServiceTypeId )
	{
		schema.getJpaHooksSchema().getServiceService().deleteByTypeIdx(argServiceTypeId);
	}


	/**
	 *	Delete the Service instances identified by the key TypeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteServiceByTypeIdx( ICFSecAuthorization Authorization,
		ICFSecServiceByTypeIdxKey argKey )
	{
		schema.getJpaHooksSchema().getServiceService().deleteByTypeIdx(argKey.getRequiredServiceTypeId());
	}

	/**
	 *	Delete the Service instances identified by the key UTypeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The Service key attribute of the instance generating the id.
	 *
	 *	@param	HostNodeId	The Service key attribute of the instance generating the id.
	 *
	 *	@param	ServiceTypeId	The Service key attribute of the instance generating the id.
	 */
	@Override
	public void deleteServiceByUTypeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		CFLibDbKeyHash256 argHostNodeId,
		CFLibDbKeyHash256 argServiceTypeId )
	{
		schema.getJpaHooksSchema().getServiceService().deleteByUTypeIdx(argClusterId,
		argHostNodeId,
		argServiceTypeId);
	}


	/**
	 *	Delete the Service instances identified by the key UTypeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteServiceByUTypeIdx( ICFSecAuthorization Authorization,
		ICFSecServiceByUTypeIdxKey argKey )
	{
		schema.getJpaHooksSchema().getServiceService().deleteByUTypeIdx(argKey.getRequiredClusterId(),
			argKey.getRequiredHostNodeId(),
			argKey.getRequiredServiceTypeId());
	}

	/**
	 *	Delete the Service instances identified by the key UHostPortIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The Service key attribute of the instance generating the id.
	 *
	 *	@param	HostNodeId	The Service key attribute of the instance generating the id.
	 *
	 *	@param	HostPort	The Service key attribute of the instance generating the id.
	 */
	@Override
	public void deleteServiceByUHostPortIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		CFLibDbKeyHash256 argHostNodeId,
		short argHostPort )
	{
		schema.getJpaHooksSchema().getServiceService().deleteByUHostPortIdx(argClusterId,
		argHostNodeId,
		argHostPort);
	}


	/**
	 *	Delete the Service instances identified by the key UHostPortIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argKey	The key identifying the instances to be deleted.
	 */
	@Override
	public void deleteServiceByUHostPortIdx( ICFSecAuthorization Authorization,
		ICFSecServiceByUHostPortIdxKey argKey )
	{
		schema.getJpaHooksSchema().getServiceService().deleteByUHostPortIdx(argKey.getRequiredClusterId(),
			argKey.getRequiredHostNodeId(),
			argKey.getRequiredHostPort());
	}


	/**
	 *	Read the derived Service record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the Service instance to be read.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecService readDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		return( schema.getJpaHooksSchema().getServiceService().find(PKey) );
	}

	/**
	 *	Lock the derived Service record instance by primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the Service instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecService lockDerived( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		return( schema.getJpaHooksSchema().getServiceService().lockByIdIdx(PKey) );
	}

	/**
	 *	Read all Service instances.
	 *
	 *	@param	Authorization	The session authorization information.	
	 *
	 *	@return An array of derived record instances, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecService[] readAllDerived( ICFSecAuthorization Authorization ) {
		List<CFSecJpaService> results = schema.getJpaHooksSchema().getServiceService().findAll();
		ICFSecService[] retset = new ICFSecService[results.size()];
		int idx = 0;
		for (CFSecJpaService cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived Service record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ServiceId	The Service key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecService readDerivedByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argServiceId )
	{
		return( schema.getJpaHooksSchema().getServiceService().find(argServiceId) );
	}

	/**
	 *	Read an array of the derived Service record instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The Service key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecService[] readDerivedByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		List<CFSecJpaService> results = schema.getJpaHooksSchema().getServiceService().findByClusterIdx(argClusterId);
		ICFSecService[] retset = new ICFSecService[results.size()];
		int idx = 0;
		for (CFSecJpaService cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived Service record instances identified by the duplicate key HostIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	HostNodeId	The Service key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecService[] readDerivedByHostIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argHostNodeId )
	{
		List<CFSecJpaService> results = schema.getJpaHooksSchema().getServiceService().findByHostIdx(argHostNodeId);
		ICFSecService[] retset = new ICFSecService[results.size()];
		int idx = 0;
		for (CFSecJpaService cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read an array of the derived Service record instances identified by the duplicate key TypeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ServiceTypeId	The Service key attribute of the instance generating the id.
	 *
	 *	@return An array of derived instances for the specified key, potentially with 0 elements in the set.
	 */
	@Override
	public ICFSecService[] readDerivedByTypeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argServiceTypeId )
	{
		List<CFSecJpaService> results = schema.getJpaHooksSchema().getServiceService().findByTypeIdx(argServiceTypeId);
		ICFSecService[] retset = new ICFSecService[results.size()];
		int idx = 0;
		for (CFSecJpaService cur: results) {
			retset[idx++] = cur;
		}
		return( retset );
	}

	/**
	 *	Read the derived Service record instance identified by the unique key UTypeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The Service key attribute of the instance generating the id.
	 *
	 *	@param	HostNodeId	The Service key attribute of the instance generating the id.
	 *
	 *	@param	ServiceTypeId	The Service key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecService readDerivedByUTypeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		CFLibDbKeyHash256 argHostNodeId,
		CFLibDbKeyHash256 argServiceTypeId )
	{
		return( schema.getJpaHooksSchema().getServiceService().findByUTypeIdx(argClusterId,
		argHostNodeId,
		argServiceTypeId) );
	}

	/**
	 *	Read the derived Service record instance identified by the unique key UHostPortIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The Service key attribute of the instance generating the id.
	 *
	 *	@param	HostNodeId	The Service key attribute of the instance generating the id.
	 *
	 *	@param	HostPort	The Service key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 */
	@Override
	public ICFSecService readDerivedByUHostPortIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		CFLibDbKeyHash256 argHostNodeId,
		short argHostPort )
	{
		return( schema.getJpaHooksSchema().getServiceService().findByUHostPortIdx(argClusterId,
		argHostNodeId,
		argHostPort) );
	}

	/**
	 *	Read the specific Service record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the Service instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecService readRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRec");
	}

	/**
	 *	Lock the specific Service record instance identified by the primary key.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	PKey	The primary key of the Service instance to be locked.
	 *
	 *	@return The record instance for the specified primary key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecService lockRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 PKey )
	{
		throw new CFLibNotImplementedYetException(getClass(), "lockRec");
	}

	/**
	 *	Read all the specific Service record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific Service instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecService[] readAllRec( ICFSecAuthorization Authorization ) {
		throw new CFLibNotImplementedYetException(getClass(), "readAllRec");
	}


	/**
	 *	Read a page of all the specific Service record instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific Service instances in the database accessible for the Authorization.
	 */
	@Override
	public ICFSecService[] pageAllRec( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 priorServiceId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageAllRec");
	}

	/**
	 *	Read the specific Service record instance identified by the unique key IdIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ServiceId	The Service key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecService readRecByIdIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argServiceId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByIdIdx");
	}

	/**
	 *	Read an array of the specific Service record instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The Service key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecService[] readRecByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByClusterIdx");
	}

	/**
	 *	Read an array of the specific Service record instances identified by the duplicate key HostIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	HostNodeId	The Service key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecService[] readRecByHostIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argHostNodeId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByHostIdx");
	}

	/**
	 *	Read an array of the specific Service record instances identified by the duplicate key TypeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ServiceTypeId	The Service key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecService[] readRecByTypeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argServiceTypeId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByTypeIdx");
	}

	/**
	 *	Read the specific Service record instance identified by the unique key UTypeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The Service key attribute of the instance generating the id.
	 *
	 *	@param	HostNodeId	The Service key attribute of the instance generating the id.
	 *
	 *	@param	ServiceTypeId	The Service key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecService readRecByUTypeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		CFLibDbKeyHash256 argHostNodeId,
		CFLibDbKeyHash256 argServiceTypeId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUTypeIdx");
	}

	/**
	 *	Read the specific Service record instance identified by the unique key UHostPortIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The Service key attribute of the instance generating the id.
	 *
	 *	@param	HostNodeId	The Service key attribute of the instance generating the id.
	 *
	 *	@param	HostPort	The Service key attribute of the instance generating the id.
	 *
	 *	@return The record instance for the specified key, or null if there is
	 *		no such existing key value.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecService readRecByUHostPortIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		CFLibDbKeyHash256 argHostNodeId,
		short argHostPort )
	{
		throw new CFLibNotImplementedYetException(getClass(), "readRecByUHostPortIdx");
	}

	/**
	 *	Read a page array of the specific Service record instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ClusterId	The Service key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecService[] pageRecByClusterIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argClusterId,
		CFLibDbKeyHash256 priorServiceId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByClusterIdx");
	}

	/**
	 *	Read a page array of the specific Service record instances identified by the duplicate key HostIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	HostNodeId	The Service key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecService[] pageRecByHostIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argHostNodeId,
		CFLibDbKeyHash256 priorServiceId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByHostIdx");
	}

	/**
	 *	Read a page array of the specific Service record instances identified by the duplicate key TypeIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	ServiceTypeId	The Service key attribute of the instance generating the id.
	 *
	 *	@return An array of derived record instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	@Override
	public ICFSecService[] pageRecByTypeIdx( ICFSecAuthorization Authorization,
		CFLibDbKeyHash256 argServiceTypeId,
		CFLibDbKeyHash256 priorServiceId )
	{
		throw new CFLibNotImplementedYetException(getClass(), "pageRecByTypeIdx");
	}
}

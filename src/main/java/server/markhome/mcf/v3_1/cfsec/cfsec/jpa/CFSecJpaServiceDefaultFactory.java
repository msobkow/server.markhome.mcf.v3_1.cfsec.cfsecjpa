
// Description: Java 25 JPA Default Factory implementation for Service.

/*
 *	server.markhome.mcf.CFSec
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

package server.markhome.mcf.v3_1.cfsec.cfsec.jpa;

import java.lang.reflect.*;
import java.net.*;
import java.rmi.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;

/*
 *	CFSecServiceFactory JPA implementation for Service
 */
public class CFSecJpaServiceDefaultFactory
    implements ICFSecServiceFactory
{
    public CFSecJpaServiceDefaultFactory() {
    }

    @Override
    public ICFSecServiceHPKey newHPKey() {
        ICFSecServiceHPKey hpkey =
            new CFSecJpaServiceHPKey();
        return( hpkey );
    }

	public CFSecJpaServiceHPKey ensureHPKey(ICFSecServiceHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaServiceHPKey) {
			return( (CFSecJpaServiceHPKey)key );
		}
		else {
			CFSecJpaServiceHPKey mapped = new CFSecJpaServiceHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredServiceId( key.getRequiredServiceId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecServiceByClusterIdxKey newByClusterIdxKey() {
	ICFSecServiceByClusterIdxKey key =
            new CFSecJpaServiceByClusterIdxKey();
	return( key );
    }

	public CFSecJpaServiceByClusterIdxKey ensureByClusterIdxKey(ICFSecServiceByClusterIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaServiceByClusterIdxKey) {
			return( (CFSecJpaServiceByClusterIdxKey)key );
		}
		else {
			CFSecJpaServiceByClusterIdxKey mapped = new CFSecJpaServiceByClusterIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecServiceByHostIdxKey newByHostIdxKey() {
	ICFSecServiceByHostIdxKey key =
            new CFSecJpaServiceByHostIdxKey();
	return( key );
    }

	public CFSecJpaServiceByHostIdxKey ensureByHostIdxKey(ICFSecServiceByHostIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaServiceByHostIdxKey) {
			return( (CFSecJpaServiceByHostIdxKey)key );
		}
		else {
			CFSecJpaServiceByHostIdxKey mapped = new CFSecJpaServiceByHostIdxKey();
			mapped.setRequiredHostNodeId( key.getRequiredHostNodeId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecServiceByTypeIdxKey newByTypeIdxKey() {
	ICFSecServiceByTypeIdxKey key =
            new CFSecJpaServiceByTypeIdxKey();
	return( key );
    }

	public CFSecJpaServiceByTypeIdxKey ensureByTypeIdxKey(ICFSecServiceByTypeIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaServiceByTypeIdxKey) {
			return( (CFSecJpaServiceByTypeIdxKey)key );
		}
		else {
			CFSecJpaServiceByTypeIdxKey mapped = new CFSecJpaServiceByTypeIdxKey();
			mapped.setRequiredServiceTypeId( key.getRequiredServiceTypeId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecServiceByUTypeIdxKey newByUTypeIdxKey() {
	ICFSecServiceByUTypeIdxKey key =
            new CFSecJpaServiceByUTypeIdxKey();
	return( key );
    }

	public CFSecJpaServiceByUTypeIdxKey ensureByUTypeIdxKey(ICFSecServiceByUTypeIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaServiceByUTypeIdxKey) {
			return( (CFSecJpaServiceByUTypeIdxKey)key );
		}
		else {
			CFSecJpaServiceByUTypeIdxKey mapped = new CFSecJpaServiceByUTypeIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			mapped.setRequiredHostNodeId( key.getRequiredHostNodeId() );
			mapped.setRequiredServiceTypeId( key.getRequiredServiceTypeId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecServiceByUHostPortIdxKey newByUHostPortIdxKey() {
	ICFSecServiceByUHostPortIdxKey key =
            new CFSecJpaServiceByUHostPortIdxKey();
	return( key );
    }

	public CFSecJpaServiceByUHostPortIdxKey ensureByUHostPortIdxKey(ICFSecServiceByUHostPortIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaServiceByUHostPortIdxKey) {
			return( (CFSecJpaServiceByUHostPortIdxKey)key );
		}
		else {
			CFSecJpaServiceByUHostPortIdxKey mapped = new CFSecJpaServiceByUHostPortIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			mapped.setRequiredHostNodeId( key.getRequiredHostNodeId() );
			mapped.setRequiredHostPort( key.getRequiredHostPort() );
			return( mapped );
		}
	}

    @Override
    public ICFSecService newRec() {
        ICFSecService rec =
            new CFSecJpaService();
        return( rec );
    }

	public CFSecJpaService ensureRec(ICFSecService rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaService) {
			return( (CFSecJpaService)rec );
		}
		else {
			CFSecJpaService mapped = new CFSecJpaService();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecServiceH newHRec() {
        ICFSecServiceH hrec =
            new CFSecJpaServiceH();
        return( hrec );
    }

	public CFSecJpaServiceH ensureHRec(ICFSecServiceH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaServiceH) {
			return( (CFSecJpaServiceH)hrec );
		}
		else {
			CFSecJpaServiceH mapped = new CFSecJpaServiceH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

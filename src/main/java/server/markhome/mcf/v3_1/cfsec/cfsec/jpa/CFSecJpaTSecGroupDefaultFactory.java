
// Description: Java 25 JPA Default Factory implementation for TSecGroup.

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
 *	CFSecTSecGroupFactory JPA implementation for TSecGroup
 */
public class CFSecJpaTSecGroupDefaultFactory
    implements ICFSecTSecGroupFactory
{
    public CFSecJpaTSecGroupDefaultFactory() {
    }

    @Override
    public ICFSecTSecGroupHPKey newHPKey() {
        ICFSecTSecGroupHPKey hpkey =
            new CFSecJpaTSecGroupHPKey();
        return( hpkey );
    }

	public CFSecJpaTSecGroupHPKey ensureHPKey(ICFSecTSecGroupHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaTSecGroupHPKey) {
			return( (CFSecJpaTSecGroupHPKey)key );
		}
		else {
			CFSecJpaTSecGroupHPKey mapped = new CFSecJpaTSecGroupHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredTSecGroupId( key.getRequiredTSecGroupId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGroupByTenantIdxKey newByTenantIdxKey() {
	ICFSecTSecGroupByTenantIdxKey key =
            new CFSecJpaTSecGroupByTenantIdxKey();
	return( key );
    }

	public CFSecJpaTSecGroupByTenantIdxKey ensureByTenantIdxKey(ICFSecTSecGroupByTenantIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaTSecGroupByTenantIdxKey) {
			return( (CFSecJpaTSecGroupByTenantIdxKey)key );
		}
		else {
			CFSecJpaTSecGroupByTenantIdxKey mapped = new CFSecJpaTSecGroupByTenantIdxKey();
			mapped.setRequiredTenantId( key.getRequiredTenantId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGroupByTenantVisIdxKey newByTenantVisIdxKey() {
	ICFSecTSecGroupByTenantVisIdxKey key =
            new CFSecJpaTSecGroupByTenantVisIdxKey();
	return( key );
    }

	public CFSecJpaTSecGroupByTenantVisIdxKey ensureByTenantVisIdxKey(ICFSecTSecGroupByTenantVisIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaTSecGroupByTenantVisIdxKey) {
			return( (CFSecJpaTSecGroupByTenantVisIdxKey)key );
		}
		else {
			CFSecJpaTSecGroupByTenantVisIdxKey mapped = new CFSecJpaTSecGroupByTenantVisIdxKey();
			mapped.setRequiredTenantId( key.getRequiredTenantId() );
			mapped.setRequiredIsVisible( key.getRequiredIsVisible() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGroupByUNameIdxKey newByUNameIdxKey() {
	ICFSecTSecGroupByUNameIdxKey key =
            new CFSecJpaTSecGroupByUNameIdxKey();
	return( key );
    }

	public CFSecJpaTSecGroupByUNameIdxKey ensureByUNameIdxKey(ICFSecTSecGroupByUNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaTSecGroupByUNameIdxKey) {
			return( (CFSecJpaTSecGroupByUNameIdxKey)key );
		}
		else {
			CFSecJpaTSecGroupByUNameIdxKey mapped = new CFSecJpaTSecGroupByUNameIdxKey();
			mapped.setRequiredTenantId( key.getRequiredTenantId() );
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGroup newRec() {
        ICFSecTSecGroup rec =
            new CFSecJpaTSecGroup();
        return( rec );
    }

	public CFSecJpaTSecGroup ensureRec(ICFSecTSecGroup rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaTSecGroup) {
			return( (CFSecJpaTSecGroup)rec );
		}
		else {
			CFSecJpaTSecGroup mapped = new CFSecJpaTSecGroup();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGroupH newHRec() {
        ICFSecTSecGroupH hrec =
            new CFSecJpaTSecGroupH();
        return( hrec );
    }

	public CFSecJpaTSecGroupH ensureHRec(ICFSecTSecGroupH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaTSecGroupH) {
			return( (CFSecJpaTSecGroupH)hrec );
		}
		else {
			CFSecJpaTSecGroupH mapped = new CFSecJpaTSecGroupH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

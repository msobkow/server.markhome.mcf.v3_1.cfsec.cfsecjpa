
// Description: Java 25 JPA Default Factory implementation for TSecGrpInc.

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
 *	CFSecTSecGrpIncFactory JPA implementation for TSecGrpInc
 */
public class CFSecJpaTSecGrpIncDefaultFactory
    implements ICFSecTSecGrpIncFactory
{
    public CFSecJpaTSecGrpIncDefaultFactory() {
    }

    @Override
    public ICFSecTSecGrpIncHPKey newHPKey() {
        ICFSecTSecGrpIncHPKey hpkey =
            new CFSecJpaTSecGrpIncHPKey();
        return( hpkey );
    }

	public CFSecJpaTSecGrpIncHPKey ensureHPKey(ICFSecTSecGrpIncHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaTSecGrpIncHPKey) {
			return( (CFSecJpaTSecGrpIncHPKey)key );
		}
		else {
			CFSecJpaTSecGrpIncHPKey mapped = new CFSecJpaTSecGrpIncHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredTSecGrpIncId( key.getRequiredTSecGrpIncId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGrpIncByTenantIdxKey newByTenantIdxKey() {
	ICFSecTSecGrpIncByTenantIdxKey key =
            new CFSecJpaTSecGrpIncByTenantIdxKey();
	return( key );
    }

	public CFSecJpaTSecGrpIncByTenantIdxKey ensureByTenantIdxKey(ICFSecTSecGrpIncByTenantIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaTSecGrpIncByTenantIdxKey) {
			return( (CFSecJpaTSecGrpIncByTenantIdxKey)key );
		}
		else {
			CFSecJpaTSecGrpIncByTenantIdxKey mapped = new CFSecJpaTSecGrpIncByTenantIdxKey();
			mapped.setRequiredTenantId( key.getRequiredTenantId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGrpIncByGroupIdxKey newByGroupIdxKey() {
	ICFSecTSecGrpIncByGroupIdxKey key =
            new CFSecJpaTSecGrpIncByGroupIdxKey();
	return( key );
    }

	public CFSecJpaTSecGrpIncByGroupIdxKey ensureByGroupIdxKey(ICFSecTSecGrpIncByGroupIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaTSecGrpIncByGroupIdxKey) {
			return( (CFSecJpaTSecGrpIncByGroupIdxKey)key );
		}
		else {
			CFSecJpaTSecGrpIncByGroupIdxKey mapped = new CFSecJpaTSecGrpIncByGroupIdxKey();
			mapped.setRequiredTSecGroupId( key.getRequiredTSecGroupId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGrpIncByIncludeIdxKey newByIncludeIdxKey() {
	ICFSecTSecGrpIncByIncludeIdxKey key =
            new CFSecJpaTSecGrpIncByIncludeIdxKey();
	return( key );
    }

	public CFSecJpaTSecGrpIncByIncludeIdxKey ensureByIncludeIdxKey(ICFSecTSecGrpIncByIncludeIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaTSecGrpIncByIncludeIdxKey) {
			return( (CFSecJpaTSecGrpIncByIncludeIdxKey)key );
		}
		else {
			CFSecJpaTSecGrpIncByIncludeIdxKey mapped = new CFSecJpaTSecGrpIncByIncludeIdxKey();
			mapped.setRequiredIncludeGroupId( key.getRequiredIncludeGroupId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGrpIncByUIncludeIdxKey newByUIncludeIdxKey() {
	ICFSecTSecGrpIncByUIncludeIdxKey key =
            new CFSecJpaTSecGrpIncByUIncludeIdxKey();
	return( key );
    }

	public CFSecJpaTSecGrpIncByUIncludeIdxKey ensureByUIncludeIdxKey(ICFSecTSecGrpIncByUIncludeIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaTSecGrpIncByUIncludeIdxKey) {
			return( (CFSecJpaTSecGrpIncByUIncludeIdxKey)key );
		}
		else {
			CFSecJpaTSecGrpIncByUIncludeIdxKey mapped = new CFSecJpaTSecGrpIncByUIncludeIdxKey();
			mapped.setRequiredTenantId( key.getRequiredTenantId() );
			mapped.setRequiredTSecGroupId( key.getRequiredTSecGroupId() );
			mapped.setRequiredIncludeGroupId( key.getRequiredIncludeGroupId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGrpInc newRec() {
        ICFSecTSecGrpInc rec =
            new CFSecJpaTSecGrpInc();
        return( rec );
    }

	public CFSecJpaTSecGrpInc ensureRec(ICFSecTSecGrpInc rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaTSecGrpInc) {
			return( (CFSecJpaTSecGrpInc)rec );
		}
		else {
			CFSecJpaTSecGrpInc mapped = new CFSecJpaTSecGrpInc();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGrpIncH newHRec() {
        ICFSecTSecGrpIncH hrec =
            new CFSecJpaTSecGrpIncH();
        return( hrec );
    }

	public CFSecJpaTSecGrpIncH ensureHRec(ICFSecTSecGrpIncH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaTSecGrpIncH) {
			return( (CFSecJpaTSecGrpIncH)hrec );
		}
		else {
			CFSecJpaTSecGrpIncH mapped = new CFSecJpaTSecGrpIncH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

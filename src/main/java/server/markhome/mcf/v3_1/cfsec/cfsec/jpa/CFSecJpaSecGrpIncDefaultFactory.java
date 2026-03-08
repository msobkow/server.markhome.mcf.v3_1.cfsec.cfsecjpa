
// Description: Java 25 JPA Default Factory implementation for SecGrpInc.

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
 *	CFSecSecGrpIncFactory JPA implementation for SecGrpInc
 */
public class CFSecJpaSecGrpIncDefaultFactory
    implements ICFSecSecGrpIncFactory
{
    public CFSecJpaSecGrpIncDefaultFactory() {
    }

    @Override
    public ICFSecSecGrpIncHPKey newHPKey() {
        ICFSecSecGrpIncHPKey hpkey =
            new CFSecJpaSecGrpIncHPKey();
        return( hpkey );
    }

	public CFSecJpaSecGrpIncHPKey ensureHPKey(ICFSecSecGrpIncHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaSecGrpIncHPKey) {
			return( (CFSecJpaSecGrpIncHPKey)key );
		}
		else {
			CFSecJpaSecGrpIncHPKey mapped = new CFSecJpaSecGrpIncHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredSecGrpIncId( key.getRequiredSecGrpIncId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGrpIncByClusterIdxKey newByClusterIdxKey() {
	ICFSecSecGrpIncByClusterIdxKey key =
            new CFSecJpaSecGrpIncByClusterIdxKey();
	return( key );
    }

	public CFSecJpaSecGrpIncByClusterIdxKey ensureByClusterIdxKey(ICFSecSecGrpIncByClusterIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecGrpIncByClusterIdxKey) {
			return( (CFSecJpaSecGrpIncByClusterIdxKey)key );
		}
		else {
			CFSecJpaSecGrpIncByClusterIdxKey mapped = new CFSecJpaSecGrpIncByClusterIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGrpIncByGroupIdxKey newByGroupIdxKey() {
	ICFSecSecGrpIncByGroupIdxKey key =
            new CFSecJpaSecGrpIncByGroupIdxKey();
	return( key );
    }

	public CFSecJpaSecGrpIncByGroupIdxKey ensureByGroupIdxKey(ICFSecSecGrpIncByGroupIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecGrpIncByGroupIdxKey) {
			return( (CFSecJpaSecGrpIncByGroupIdxKey)key );
		}
		else {
			CFSecJpaSecGrpIncByGroupIdxKey mapped = new CFSecJpaSecGrpIncByGroupIdxKey();
			mapped.setRequiredSecGroupId( key.getRequiredSecGroupId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGrpIncByIncludeIdxKey newByIncludeIdxKey() {
	ICFSecSecGrpIncByIncludeIdxKey key =
            new CFSecJpaSecGrpIncByIncludeIdxKey();
	return( key );
    }

	public CFSecJpaSecGrpIncByIncludeIdxKey ensureByIncludeIdxKey(ICFSecSecGrpIncByIncludeIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecGrpIncByIncludeIdxKey) {
			return( (CFSecJpaSecGrpIncByIncludeIdxKey)key );
		}
		else {
			CFSecJpaSecGrpIncByIncludeIdxKey mapped = new CFSecJpaSecGrpIncByIncludeIdxKey();
			mapped.setRequiredIncludeGroupId( key.getRequiredIncludeGroupId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGrpIncByUIncludeIdxKey newByUIncludeIdxKey() {
	ICFSecSecGrpIncByUIncludeIdxKey key =
            new CFSecJpaSecGrpIncByUIncludeIdxKey();
	return( key );
    }

	public CFSecJpaSecGrpIncByUIncludeIdxKey ensureByUIncludeIdxKey(ICFSecSecGrpIncByUIncludeIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecGrpIncByUIncludeIdxKey) {
			return( (CFSecJpaSecGrpIncByUIncludeIdxKey)key );
		}
		else {
			CFSecJpaSecGrpIncByUIncludeIdxKey mapped = new CFSecJpaSecGrpIncByUIncludeIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			mapped.setRequiredSecGroupId( key.getRequiredSecGroupId() );
			mapped.setRequiredIncludeGroupId( key.getRequiredIncludeGroupId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGrpInc newRec() {
        ICFSecSecGrpInc rec =
            new CFSecJpaSecGrpInc();
        return( rec );
    }

	public CFSecJpaSecGrpInc ensureRec(ICFSecSecGrpInc rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaSecGrpInc) {
			return( (CFSecJpaSecGrpInc)rec );
		}
		else {
			CFSecJpaSecGrpInc mapped = new CFSecJpaSecGrpInc();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGrpIncH newHRec() {
        ICFSecSecGrpIncH hrec =
            new CFSecJpaSecGrpIncH();
        return( hrec );
    }

	public CFSecJpaSecGrpIncH ensureHRec(ICFSecSecGrpIncH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaSecGrpIncH) {
			return( (CFSecJpaSecGrpIncH)hrec );
		}
		else {
			CFSecJpaSecGrpIncH mapped = new CFSecJpaSecGrpIncH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

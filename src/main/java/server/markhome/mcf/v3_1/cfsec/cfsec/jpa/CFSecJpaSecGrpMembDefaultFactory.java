
// Description: Java 25 JPA Default Factory implementation for SecGrpMemb.

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
 *	CFSecSecGrpMembFactory JPA implementation for SecGrpMemb
 */
public class CFSecJpaSecGrpMembDefaultFactory
    implements ICFSecSecGrpMembFactory
{
    public CFSecJpaSecGrpMembDefaultFactory() {
    }

    @Override
    public ICFSecSecGrpMembHPKey newHPKey() {
        ICFSecSecGrpMembHPKey hpkey =
            new CFSecJpaSecGrpMembHPKey();
        return( hpkey );
    }

	public CFSecJpaSecGrpMembHPKey ensureHPKey(ICFSecSecGrpMembHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaSecGrpMembHPKey) {
			return( (CFSecJpaSecGrpMembHPKey)key );
		}
		else {
			CFSecJpaSecGrpMembHPKey mapped = new CFSecJpaSecGrpMembHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredSecGrpMembId( key.getRequiredSecGrpMembId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGrpMembByClusterIdxKey newByClusterIdxKey() {
	ICFSecSecGrpMembByClusterIdxKey key =
            new CFSecJpaSecGrpMembByClusterIdxKey();
	return( key );
    }

	public CFSecJpaSecGrpMembByClusterIdxKey ensureByClusterIdxKey(ICFSecSecGrpMembByClusterIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecGrpMembByClusterIdxKey) {
			return( (CFSecJpaSecGrpMembByClusterIdxKey)key );
		}
		else {
			CFSecJpaSecGrpMembByClusterIdxKey mapped = new CFSecJpaSecGrpMembByClusterIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGrpMembByGroupIdxKey newByGroupIdxKey() {
	ICFSecSecGrpMembByGroupIdxKey key =
            new CFSecJpaSecGrpMembByGroupIdxKey();
	return( key );
    }

	public CFSecJpaSecGrpMembByGroupIdxKey ensureByGroupIdxKey(ICFSecSecGrpMembByGroupIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecGrpMembByGroupIdxKey) {
			return( (CFSecJpaSecGrpMembByGroupIdxKey)key );
		}
		else {
			CFSecJpaSecGrpMembByGroupIdxKey mapped = new CFSecJpaSecGrpMembByGroupIdxKey();
			mapped.setRequiredSecGroupId( key.getRequiredSecGroupId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGrpMembByUserIdxKey newByUserIdxKey() {
	ICFSecSecGrpMembByUserIdxKey key =
            new CFSecJpaSecGrpMembByUserIdxKey();
	return( key );
    }

	public CFSecJpaSecGrpMembByUserIdxKey ensureByUserIdxKey(ICFSecSecGrpMembByUserIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecGrpMembByUserIdxKey) {
			return( (CFSecJpaSecGrpMembByUserIdxKey)key );
		}
		else {
			CFSecJpaSecGrpMembByUserIdxKey mapped = new CFSecJpaSecGrpMembByUserIdxKey();
			mapped.setRequiredSecUserId( key.getRequiredSecUserId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGrpMembByUUserIdxKey newByUUserIdxKey() {
	ICFSecSecGrpMembByUUserIdxKey key =
            new CFSecJpaSecGrpMembByUUserIdxKey();
	return( key );
    }

	public CFSecJpaSecGrpMembByUUserIdxKey ensureByUUserIdxKey(ICFSecSecGrpMembByUUserIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecGrpMembByUUserIdxKey) {
			return( (CFSecJpaSecGrpMembByUUserIdxKey)key );
		}
		else {
			CFSecJpaSecGrpMembByUUserIdxKey mapped = new CFSecJpaSecGrpMembByUUserIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			mapped.setRequiredSecGroupId( key.getRequiredSecGroupId() );
			mapped.setRequiredSecUserId( key.getRequiredSecUserId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGrpMemb newRec() {
        ICFSecSecGrpMemb rec =
            new CFSecJpaSecGrpMemb();
        return( rec );
    }

	public CFSecJpaSecGrpMemb ensureRec(ICFSecSecGrpMemb rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaSecGrpMemb) {
			return( (CFSecJpaSecGrpMemb)rec );
		}
		else {
			CFSecJpaSecGrpMemb mapped = new CFSecJpaSecGrpMemb();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGrpMembH newHRec() {
        ICFSecSecGrpMembH hrec =
            new CFSecJpaSecGrpMembH();
        return( hrec );
    }

	public CFSecJpaSecGrpMembH ensureHRec(ICFSecSecGrpMembH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaSecGrpMembH) {
			return( (CFSecJpaSecGrpMembH)hrec );
		}
		else {
			CFSecJpaSecGrpMembH mapped = new CFSecJpaSecGrpMembH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

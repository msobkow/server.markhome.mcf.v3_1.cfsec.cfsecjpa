
// Description: Java 25 JPA Default Factory implementation for SecDevice.

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
 *	CFSecSecDeviceFactory JPA implementation for SecDevice
 */
public class CFSecJpaSecDeviceDefaultFactory
    implements ICFSecSecDeviceFactory
{
    public CFSecJpaSecDeviceDefaultFactory() {
    }

    @Override
    public ICFSecSecDevicePKey newPKey() {
        ICFSecSecDevicePKey pkey =
            new CFSecJpaSecDevicePKey();
        return( pkey );
    }

	public CFSecJpaSecDevicePKey ensurePKey(ICFSecSecDevicePKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecDevicePKey) {
			return( (CFSecJpaSecDevicePKey)key );
		}
		else {
			CFSecJpaSecDevicePKey mapped = new CFSecJpaSecDevicePKey();
			mapped.setRequiredContainerSecUser( key.getRequiredSecUserId() );
			mapped.setRequiredDevName( key.getRequiredDevName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecDeviceHPKey newHPKey() {
        ICFSecSecDeviceHPKey hpkey =
            new CFSecJpaSecDeviceHPKey();
        return( hpkey );
    }

	public CFSecJpaSecDeviceHPKey ensureHPKey(ICFSecSecDeviceHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaSecDeviceHPKey) {
			return( (CFSecJpaSecDeviceHPKey)key );
		}
		else {
			CFSecJpaSecDeviceHPKey mapped = new CFSecJpaSecDeviceHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredSecUserId( key.getRequiredSecUserId() );
			mapped.setRequiredDevName( key.getRequiredDevName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecDeviceByNameIdxKey newByNameIdxKey() {
	ICFSecSecDeviceByNameIdxKey key =
            new CFSecJpaSecDeviceByNameIdxKey();
	return( key );
    }

	public CFSecJpaSecDeviceByNameIdxKey ensureByNameIdxKey(ICFSecSecDeviceByNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecDeviceByNameIdxKey) {
			return( (CFSecJpaSecDeviceByNameIdxKey)key );
		}
		else {
			CFSecJpaSecDeviceByNameIdxKey mapped = new CFSecJpaSecDeviceByNameIdxKey();
			mapped.setRequiredSecUserId( key.getRequiredSecUserId() );
			mapped.setRequiredDevName( key.getRequiredDevName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecDeviceByUserIdxKey newByUserIdxKey() {
	ICFSecSecDeviceByUserIdxKey key =
            new CFSecJpaSecDeviceByUserIdxKey();
	return( key );
    }

	public CFSecJpaSecDeviceByUserIdxKey ensureByUserIdxKey(ICFSecSecDeviceByUserIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecDeviceByUserIdxKey) {
			return( (CFSecJpaSecDeviceByUserIdxKey)key );
		}
		else {
			CFSecJpaSecDeviceByUserIdxKey mapped = new CFSecJpaSecDeviceByUserIdxKey();
			mapped.setRequiredSecUserId( key.getRequiredSecUserId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecDevice newRec() {
        ICFSecSecDevice rec =
            new CFSecJpaSecDevice();
        return( rec );
    }

	public CFSecJpaSecDevice ensureRec(ICFSecSecDevice rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaSecDevice) {
			return( (CFSecJpaSecDevice)rec );
		}
		else {
			CFSecJpaSecDevice mapped = new CFSecJpaSecDevice();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecSecDeviceH newHRec() {
        ICFSecSecDeviceH hrec =
            new CFSecJpaSecDeviceH();
        return( hrec );
    }

	public CFSecJpaSecDeviceH ensureHRec(ICFSecSecDeviceH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaSecDeviceH) {
			return( (CFSecJpaSecDeviceH)hrec );
		}
		else {
			CFSecJpaSecDeviceH mapped = new CFSecJpaSecDeviceH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

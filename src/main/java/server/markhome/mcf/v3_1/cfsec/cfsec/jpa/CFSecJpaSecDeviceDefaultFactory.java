
// Description: Java 25 JPA Default Factory implementation for SecDevice.

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

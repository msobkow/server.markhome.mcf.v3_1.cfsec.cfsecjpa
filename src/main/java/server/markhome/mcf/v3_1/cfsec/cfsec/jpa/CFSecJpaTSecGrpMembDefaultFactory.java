
// Description: Java 25 JPA Default Factory implementation for TSecGrpMemb.

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
 *	CFSecTSecGrpMembFactory JPA implementation for TSecGrpMemb
 */
public class CFSecJpaTSecGrpMembDefaultFactory
    implements ICFSecTSecGrpMembFactory
{
    public CFSecJpaTSecGrpMembDefaultFactory() {
    }

    @Override
    public ICFSecTSecGrpMembHPKey newHPKey() {
        ICFSecTSecGrpMembHPKey hpkey =
            new CFSecJpaTSecGrpMembHPKey();
        return( hpkey );
    }

	public CFSecJpaTSecGrpMembHPKey ensureHPKey(ICFSecTSecGrpMembHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaTSecGrpMembHPKey) {
			return( (CFSecJpaTSecGrpMembHPKey)key );
		}
		else {
			CFSecJpaTSecGrpMembHPKey mapped = new CFSecJpaTSecGrpMembHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredTSecGrpMembId( key.getRequiredTSecGrpMembId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGrpMembByTenantIdxKey newByTenantIdxKey() {
	ICFSecTSecGrpMembByTenantIdxKey key =
            new CFSecJpaTSecGrpMembByTenantIdxKey();
	return( key );
    }

	public CFSecJpaTSecGrpMembByTenantIdxKey ensureByTenantIdxKey(ICFSecTSecGrpMembByTenantIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaTSecGrpMembByTenantIdxKey) {
			return( (CFSecJpaTSecGrpMembByTenantIdxKey)key );
		}
		else {
			CFSecJpaTSecGrpMembByTenantIdxKey mapped = new CFSecJpaTSecGrpMembByTenantIdxKey();
			mapped.setRequiredTenantId( key.getRequiredTenantId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGrpMembByGroupIdxKey newByGroupIdxKey() {
	ICFSecTSecGrpMembByGroupIdxKey key =
            new CFSecJpaTSecGrpMembByGroupIdxKey();
	return( key );
    }

	public CFSecJpaTSecGrpMembByGroupIdxKey ensureByGroupIdxKey(ICFSecTSecGrpMembByGroupIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaTSecGrpMembByGroupIdxKey) {
			return( (CFSecJpaTSecGrpMembByGroupIdxKey)key );
		}
		else {
			CFSecJpaTSecGrpMembByGroupIdxKey mapped = new CFSecJpaTSecGrpMembByGroupIdxKey();
			mapped.setRequiredTSecGroupId( key.getRequiredTSecGroupId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGrpMembByUserIdxKey newByUserIdxKey() {
	ICFSecTSecGrpMembByUserIdxKey key =
            new CFSecJpaTSecGrpMembByUserIdxKey();
	return( key );
    }

	public CFSecJpaTSecGrpMembByUserIdxKey ensureByUserIdxKey(ICFSecTSecGrpMembByUserIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaTSecGrpMembByUserIdxKey) {
			return( (CFSecJpaTSecGrpMembByUserIdxKey)key );
		}
		else {
			CFSecJpaTSecGrpMembByUserIdxKey mapped = new CFSecJpaTSecGrpMembByUserIdxKey();
			mapped.setRequiredSecUserId( key.getRequiredSecUserId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGrpMembByUUserIdxKey newByUUserIdxKey() {
	ICFSecTSecGrpMembByUUserIdxKey key =
            new CFSecJpaTSecGrpMembByUUserIdxKey();
	return( key );
    }

	public CFSecJpaTSecGrpMembByUUserIdxKey ensureByUUserIdxKey(ICFSecTSecGrpMembByUUserIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaTSecGrpMembByUUserIdxKey) {
			return( (CFSecJpaTSecGrpMembByUUserIdxKey)key );
		}
		else {
			CFSecJpaTSecGrpMembByUUserIdxKey mapped = new CFSecJpaTSecGrpMembByUUserIdxKey();
			mapped.setRequiredTenantId( key.getRequiredTenantId() );
			mapped.setRequiredTSecGroupId( key.getRequiredTSecGroupId() );
			mapped.setRequiredSecUserId( key.getRequiredSecUserId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGrpMemb newRec() {
        ICFSecTSecGrpMemb rec =
            new CFSecJpaTSecGrpMemb();
        return( rec );
    }

	public CFSecJpaTSecGrpMemb ensureRec(ICFSecTSecGrpMemb rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaTSecGrpMemb) {
			return( (CFSecJpaTSecGrpMemb)rec );
		}
		else {
			CFSecJpaTSecGrpMemb mapped = new CFSecJpaTSecGrpMemb();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGrpMembH newHRec() {
        ICFSecTSecGrpMembH hrec =
            new CFSecJpaTSecGrpMembH();
        return( hrec );
    }

	public CFSecJpaTSecGrpMembH ensureHRec(ICFSecTSecGrpMembH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaTSecGrpMembH) {
			return( (CFSecJpaTSecGrpMembH)hrec );
		}
		else {
			CFSecJpaTSecGrpMembH mapped = new CFSecJpaTSecGrpMembH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}


// Description: Java 25 JPA Default Factory implementation for SecGrpMemb.

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


// Description: Java 25 JPA Default Factory implementation for SecTentGrpMemb.

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
 *	CFSecSecTentGrpMembFactory JPA implementation for SecTentGrpMemb
 */
public class CFSecJpaSecTentGrpMembDefaultFactory
    implements ICFSecSecTentGrpMembFactory
{
    public CFSecJpaSecTentGrpMembDefaultFactory() {
    }

    @Override
    public ICFSecSecTentGrpMembPKey newPKey() {
        ICFSecSecTentGrpMembPKey pkey =
            new CFSecJpaSecTentGrpMembPKey();
        return( pkey );
    }

	public CFSecJpaSecTentGrpMembPKey ensurePKey(ICFSecSecTentGrpMembPKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecTentGrpMembPKey) {
			return( (CFSecJpaSecTentGrpMembPKey)key );
		}
		else {
			CFSecJpaSecTentGrpMembPKey mapped = new CFSecJpaSecTentGrpMembPKey();
			mapped.setRequiredSecTentGrpId( key.getRequiredSecTentGrpId() );
			mapped.setRequiredLoginId( key.getRequiredLoginId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecTentGrpMembHPKey newHPKey() {
        ICFSecSecTentGrpMembHPKey hpkey =
            new CFSecJpaSecTentGrpMembHPKey();
        return( hpkey );
    }

	public CFSecJpaSecTentGrpMembHPKey ensureHPKey(ICFSecSecTentGrpMembHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaSecTentGrpMembHPKey) {
			return( (CFSecJpaSecTentGrpMembHPKey)key );
		}
		else {
			CFSecJpaSecTentGrpMembHPKey mapped = new CFSecJpaSecTentGrpMembHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredSecTentGrpId( key.getRequiredSecTentGrpId() );
			mapped.setRequiredLoginId( key.getRequiredLoginId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecTentGrpMembByTentGrpIdxKey newByTentGrpIdxKey() {
	ICFSecSecTentGrpMembByTentGrpIdxKey key =
            new CFSecJpaSecTentGrpMembByTentGrpIdxKey();
	return( key );
    }

	public CFSecJpaSecTentGrpMembByTentGrpIdxKey ensureByTentGrpIdxKey(ICFSecSecTentGrpMembByTentGrpIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecTentGrpMembByTentGrpIdxKey) {
			return( (CFSecJpaSecTentGrpMembByTentGrpIdxKey)key );
		}
		else {
			CFSecJpaSecTentGrpMembByTentGrpIdxKey mapped = new CFSecJpaSecTentGrpMembByTentGrpIdxKey();
			mapped.setRequiredSecTentGrpId( key.getRequiredSecTentGrpId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecTentGrpMembByUserIdxKey newByUserIdxKey() {
	ICFSecSecTentGrpMembByUserIdxKey key =
            new CFSecJpaSecTentGrpMembByUserIdxKey();
	return( key );
    }

	public CFSecJpaSecTentGrpMembByUserIdxKey ensureByUserIdxKey(ICFSecSecTentGrpMembByUserIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecTentGrpMembByUserIdxKey) {
			return( (CFSecJpaSecTentGrpMembByUserIdxKey)key );
		}
		else {
			CFSecJpaSecTentGrpMembByUserIdxKey mapped = new CFSecJpaSecTentGrpMembByUserIdxKey();
			mapped.setRequiredLoginId( key.getRequiredLoginId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecTentGrpMemb newRec() {
        ICFSecSecTentGrpMemb rec =
            new CFSecJpaSecTentGrpMemb();
        return( rec );
    }

	public CFSecJpaSecTentGrpMemb ensureRec(ICFSecSecTentGrpMemb rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaSecTentGrpMemb) {
			return( (CFSecJpaSecTentGrpMemb)rec );
		}
		else {
			CFSecJpaSecTentGrpMemb mapped = new CFSecJpaSecTentGrpMemb();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecSecTentGrpMembH newHRec() {
        ICFSecSecTentGrpMembH hrec =
            new CFSecJpaSecTentGrpMembH();
        return( hrec );
    }

	public CFSecJpaSecTentGrpMembH ensureHRec(ICFSecSecTentGrpMembH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaSecTentGrpMembH) {
			return( (CFSecJpaSecTentGrpMembH)hrec );
		}
		else {
			CFSecJpaSecTentGrpMembH mapped = new CFSecJpaSecTentGrpMembH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

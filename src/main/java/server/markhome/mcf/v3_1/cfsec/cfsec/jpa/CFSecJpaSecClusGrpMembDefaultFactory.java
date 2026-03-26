
// Description: Java 25 JPA Default Factory implementation for SecClusGrpMemb.

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
 *	CFSecSecClusGrpMembFactory JPA implementation for SecClusGrpMemb
 */
public class CFSecJpaSecClusGrpMembDefaultFactory
    implements ICFSecSecClusGrpMembFactory
{
    public CFSecJpaSecClusGrpMembDefaultFactory() {
    }

    @Override
    public ICFSecSecClusGrpMembPKey newPKey() {
        ICFSecSecClusGrpMembPKey pkey =
            new CFSecJpaSecClusGrpMembPKey();
        return( pkey );
    }

	public CFSecJpaSecClusGrpMembPKey ensurePKey(ICFSecSecClusGrpMembPKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecClusGrpMembPKey) {
			return( (CFSecJpaSecClusGrpMembPKey)key );
		}
		else {
			CFSecJpaSecClusGrpMembPKey mapped = new CFSecJpaSecClusGrpMembPKey();
			mapped.setRequiredContainerGroup( key.getRequiredSecClusGrpId() );
			mapped.setRequiredParentUser( key.getRequiredLoginId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecClusGrpMembHPKey newHPKey() {
        ICFSecSecClusGrpMembHPKey hpkey =
            new CFSecJpaSecClusGrpMembHPKey();
        return( hpkey );
    }

	public CFSecJpaSecClusGrpMembHPKey ensureHPKey(ICFSecSecClusGrpMembHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaSecClusGrpMembHPKey) {
			return( (CFSecJpaSecClusGrpMembHPKey)key );
		}
		else {
			CFSecJpaSecClusGrpMembHPKey mapped = new CFSecJpaSecClusGrpMembHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredSecClusGrpId( key.getRequiredSecClusGrpId() );
			mapped.setRequiredLoginId( key.getRequiredLoginId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecClusGrpMembByClusGrpIdxKey newByClusGrpIdxKey() {
	ICFSecSecClusGrpMembByClusGrpIdxKey key =
            new CFSecJpaSecClusGrpMembByClusGrpIdxKey();
	return( key );
    }

	public CFSecJpaSecClusGrpMembByClusGrpIdxKey ensureByClusGrpIdxKey(ICFSecSecClusGrpMembByClusGrpIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecClusGrpMembByClusGrpIdxKey) {
			return( (CFSecJpaSecClusGrpMembByClusGrpIdxKey)key );
		}
		else {
			CFSecJpaSecClusGrpMembByClusGrpIdxKey mapped = new CFSecJpaSecClusGrpMembByClusGrpIdxKey();
			mapped.setRequiredSecClusGrpId( key.getRequiredSecClusGrpId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecClusGrpMembByLoginIdxKey newByLoginIdxKey() {
	ICFSecSecClusGrpMembByLoginIdxKey key =
            new CFSecJpaSecClusGrpMembByLoginIdxKey();
	return( key );
    }

	public CFSecJpaSecClusGrpMembByLoginIdxKey ensureByLoginIdxKey(ICFSecSecClusGrpMembByLoginIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecClusGrpMembByLoginIdxKey) {
			return( (CFSecJpaSecClusGrpMembByLoginIdxKey)key );
		}
		else {
			CFSecJpaSecClusGrpMembByLoginIdxKey mapped = new CFSecJpaSecClusGrpMembByLoginIdxKey();
			mapped.setRequiredLoginId( key.getRequiredLoginId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecClusGrpMemb newRec() {
        ICFSecSecClusGrpMemb rec =
            new CFSecJpaSecClusGrpMemb();
        return( rec );
    }

	public CFSecJpaSecClusGrpMemb ensureRec(ICFSecSecClusGrpMemb rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaSecClusGrpMemb) {
			return( (CFSecJpaSecClusGrpMemb)rec );
		}
		else {
			CFSecJpaSecClusGrpMemb mapped = new CFSecJpaSecClusGrpMemb();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecSecClusGrpMembH newHRec() {
        ICFSecSecClusGrpMembH hrec =
            new CFSecJpaSecClusGrpMembH();
        return( hrec );
    }

	public CFSecJpaSecClusGrpMembH ensureHRec(ICFSecSecClusGrpMembH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaSecClusGrpMembH) {
			return( (CFSecJpaSecClusGrpMembH)hrec );
		}
		else {
			CFSecJpaSecClusGrpMembH mapped = new CFSecJpaSecClusGrpMembH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

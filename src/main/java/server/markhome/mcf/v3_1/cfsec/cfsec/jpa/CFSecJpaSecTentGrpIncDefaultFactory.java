
// Description: Java 25 JPA Default Factory implementation for SecTentGrpInc.

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
 *	CFSecSecTentGrpIncFactory JPA implementation for SecTentGrpInc
 */
public class CFSecJpaSecTentGrpIncDefaultFactory
    implements ICFSecSecTentGrpIncFactory
{
    public CFSecJpaSecTentGrpIncDefaultFactory() {
    }

    @Override
    public ICFSecSecTentGrpIncPKey newPKey() {
        ICFSecSecTentGrpIncPKey pkey =
            new CFSecJpaSecTentGrpIncPKey();
        return( pkey );
    }

	public CFSecJpaSecTentGrpIncPKey ensurePKey(ICFSecSecTentGrpIncPKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecTentGrpIncPKey) {
			return( (CFSecJpaSecTentGrpIncPKey)key );
		}
		else {
			CFSecJpaSecTentGrpIncPKey mapped = new CFSecJpaSecTentGrpIncPKey();
			mapped.setRequiredSecTentGrpId( key.getRequiredSecTentGrpId() );
			mapped.setRequiredIncName( key.getRequiredIncName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecTentGrpIncHPKey newHPKey() {
        ICFSecSecTentGrpIncHPKey hpkey =
            new CFSecJpaSecTentGrpIncHPKey();
        return( hpkey );
    }

	public CFSecJpaSecTentGrpIncHPKey ensureHPKey(ICFSecSecTentGrpIncHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaSecTentGrpIncHPKey) {
			return( (CFSecJpaSecTentGrpIncHPKey)key );
		}
		else {
			CFSecJpaSecTentGrpIncHPKey mapped = new CFSecJpaSecTentGrpIncHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredSecTentGrpId( key.getRequiredSecTentGrpId() );
			mapped.setRequiredIncName( key.getRequiredIncName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecTentGrpIncByTentGrpIdxKey newByTentGrpIdxKey() {
	ICFSecSecTentGrpIncByTentGrpIdxKey key =
            new CFSecJpaSecTentGrpIncByTentGrpIdxKey();
	return( key );
    }

	public CFSecJpaSecTentGrpIncByTentGrpIdxKey ensureByTentGrpIdxKey(ICFSecSecTentGrpIncByTentGrpIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecTentGrpIncByTentGrpIdxKey) {
			return( (CFSecJpaSecTentGrpIncByTentGrpIdxKey)key );
		}
		else {
			CFSecJpaSecTentGrpIncByTentGrpIdxKey mapped = new CFSecJpaSecTentGrpIncByTentGrpIdxKey();
			mapped.setRequiredSecTentGrpId( key.getRequiredSecTentGrpId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecTentGrpIncByNameIdxKey newByNameIdxKey() {
	ICFSecSecTentGrpIncByNameIdxKey key =
            new CFSecJpaSecTentGrpIncByNameIdxKey();
	return( key );
    }

	public CFSecJpaSecTentGrpIncByNameIdxKey ensureByNameIdxKey(ICFSecSecTentGrpIncByNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecTentGrpIncByNameIdxKey) {
			return( (CFSecJpaSecTentGrpIncByNameIdxKey)key );
		}
		else {
			CFSecJpaSecTentGrpIncByNameIdxKey mapped = new CFSecJpaSecTentGrpIncByNameIdxKey();
			mapped.setRequiredIncName( key.getRequiredIncName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecTentGrpInc newRec() {
        ICFSecSecTentGrpInc rec =
            new CFSecJpaSecTentGrpInc();
        return( rec );
    }

	public CFSecJpaSecTentGrpInc ensureRec(ICFSecSecTentGrpInc rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaSecTentGrpInc) {
			return( (CFSecJpaSecTentGrpInc)rec );
		}
		else {
			CFSecJpaSecTentGrpInc mapped = new CFSecJpaSecTentGrpInc();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecSecTentGrpIncH newHRec() {
        ICFSecSecTentGrpIncH hrec =
            new CFSecJpaSecTentGrpIncH();
        return( hrec );
    }

	public CFSecJpaSecTentGrpIncH ensureHRec(ICFSecSecTentGrpIncH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaSecTentGrpIncH) {
			return( (CFSecJpaSecTentGrpIncH)hrec );
		}
		else {
			CFSecJpaSecTentGrpIncH mapped = new CFSecJpaSecTentGrpIncH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

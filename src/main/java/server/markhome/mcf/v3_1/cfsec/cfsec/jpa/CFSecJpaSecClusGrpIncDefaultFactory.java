
// Description: Java 25 JPA Default Factory implementation for SecClusGrpInc.

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
 *	CFSecSecClusGrpIncFactory JPA implementation for SecClusGrpInc
 */
public class CFSecJpaSecClusGrpIncDefaultFactory
    implements ICFSecSecClusGrpIncFactory
{
    public CFSecJpaSecClusGrpIncDefaultFactory() {
    }

    @Override
    public ICFSecSecClusGrpIncPKey newPKey() {
        ICFSecSecClusGrpIncPKey pkey =
            new CFSecJpaSecClusGrpIncPKey();
        return( pkey );
    }

	public CFSecJpaSecClusGrpIncPKey ensurePKey(ICFSecSecClusGrpIncPKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecClusGrpIncPKey) {
			return( (CFSecJpaSecClusGrpIncPKey)key );
		}
		else {
			CFSecJpaSecClusGrpIncPKey mapped = new CFSecJpaSecClusGrpIncPKey();
			mapped.setRequiredContainerGroup( key.getRequiredSecClusGrpId() );
			mapped.setRequiredParentSubGroup( key.getRequiredInclName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecClusGrpIncHPKey newHPKey() {
        ICFSecSecClusGrpIncHPKey hpkey =
            new CFSecJpaSecClusGrpIncHPKey();
        return( hpkey );
    }

	public CFSecJpaSecClusGrpIncHPKey ensureHPKey(ICFSecSecClusGrpIncHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaSecClusGrpIncHPKey) {
			return( (CFSecJpaSecClusGrpIncHPKey)key );
		}
		else {
			CFSecJpaSecClusGrpIncHPKey mapped = new CFSecJpaSecClusGrpIncHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredSecClusGrpId( key.getRequiredSecClusGrpId() );
			mapped.setRequiredInclName( key.getRequiredInclName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecClusGrpIncByClusGrpIdxKey newByClusGrpIdxKey() {
	ICFSecSecClusGrpIncByClusGrpIdxKey key =
            new CFSecJpaSecClusGrpIncByClusGrpIdxKey();
	return( key );
    }

	public CFSecJpaSecClusGrpIncByClusGrpIdxKey ensureByClusGrpIdxKey(ICFSecSecClusGrpIncByClusGrpIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecClusGrpIncByClusGrpIdxKey) {
			return( (CFSecJpaSecClusGrpIncByClusGrpIdxKey)key );
		}
		else {
			CFSecJpaSecClusGrpIncByClusGrpIdxKey mapped = new CFSecJpaSecClusGrpIncByClusGrpIdxKey();
			mapped.setRequiredSecClusGrpId( key.getRequiredSecClusGrpId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecClusGrpIncByNameIdxKey newByNameIdxKey() {
	ICFSecSecClusGrpIncByNameIdxKey key =
            new CFSecJpaSecClusGrpIncByNameIdxKey();
	return( key );
    }

	public CFSecJpaSecClusGrpIncByNameIdxKey ensureByNameIdxKey(ICFSecSecClusGrpIncByNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecClusGrpIncByNameIdxKey) {
			return( (CFSecJpaSecClusGrpIncByNameIdxKey)key );
		}
		else {
			CFSecJpaSecClusGrpIncByNameIdxKey mapped = new CFSecJpaSecClusGrpIncByNameIdxKey();
			mapped.setRequiredInclName( key.getRequiredInclName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecClusGrpInc newRec() {
        ICFSecSecClusGrpInc rec =
            new CFSecJpaSecClusGrpInc();
        return( rec );
    }

	public CFSecJpaSecClusGrpInc ensureRec(ICFSecSecClusGrpInc rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaSecClusGrpInc) {
			return( (CFSecJpaSecClusGrpInc)rec );
		}
		else {
			CFSecJpaSecClusGrpInc mapped = new CFSecJpaSecClusGrpInc();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecSecClusGrpIncH newHRec() {
        ICFSecSecClusGrpIncH hrec =
            new CFSecJpaSecClusGrpIncH();
        return( hrec );
    }

	public CFSecJpaSecClusGrpIncH ensureHRec(ICFSecSecClusGrpIncH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaSecClusGrpIncH) {
			return( (CFSecJpaSecClusGrpIncH)hrec );
		}
		else {
			CFSecJpaSecClusGrpIncH mapped = new CFSecJpaSecClusGrpIncH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

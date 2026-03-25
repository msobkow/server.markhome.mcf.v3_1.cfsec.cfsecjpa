
// Description: Java 25 JPA Default Factory implementation for SecSysGrpInc.

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
 *	CFSecSecSysGrpIncFactory JPA implementation for SecSysGrpInc
 */
public class CFSecJpaSecSysGrpIncDefaultFactory
    implements ICFSecSecSysGrpIncFactory
{
    public CFSecJpaSecSysGrpIncDefaultFactory() {
    }

    @Override
    public ICFSecSecSysGrpIncPKey newPKey() {
        ICFSecSecSysGrpIncPKey pkey =
            new CFSecJpaSecSysGrpIncPKey();
        return( pkey );
    }

	public CFSecJpaSecSysGrpIncPKey ensurePKey(ICFSecSecSysGrpIncPKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecSysGrpIncPKey) {
			return( (CFSecJpaSecSysGrpIncPKey)key );
		}
		else {
			CFSecJpaSecSysGrpIncPKey mapped = new CFSecJpaSecSysGrpIncPKey();
			mapped.setRequiredContainerGroup( key.getRequiredSecSysGrpId() );
			mapped.setRequiredParentSubGroup( key.getRequiredInclName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecSysGrpIncHPKey newHPKey() {
        ICFSecSecSysGrpIncHPKey hpkey =
            new CFSecJpaSecSysGrpIncHPKey();
        return( hpkey );
    }

	public CFSecJpaSecSysGrpIncHPKey ensureHPKey(ICFSecSecSysGrpIncHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaSecSysGrpIncHPKey) {
			return( (CFSecJpaSecSysGrpIncHPKey)key );
		}
		else {
			CFSecJpaSecSysGrpIncHPKey mapped = new CFSecJpaSecSysGrpIncHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredSecSysGrpId( key.getRequiredSecSysGrpId() );
			mapped.setRequiredInclName( key.getRequiredInclName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecSysGrpIncBySysGrpIdxKey newBySysGrpIdxKey() {
	ICFSecSecSysGrpIncBySysGrpIdxKey key =
            new CFSecJpaSecSysGrpIncBySysGrpIdxKey();
	return( key );
    }

	public CFSecJpaSecSysGrpIncBySysGrpIdxKey ensureBySysGrpIdxKey(ICFSecSecSysGrpIncBySysGrpIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecSysGrpIncBySysGrpIdxKey) {
			return( (CFSecJpaSecSysGrpIncBySysGrpIdxKey)key );
		}
		else {
			CFSecJpaSecSysGrpIncBySysGrpIdxKey mapped = new CFSecJpaSecSysGrpIncBySysGrpIdxKey();
			mapped.setRequiredSecSysGrpId( key.getRequiredSecSysGrpId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecSysGrpIncByNameIdxKey newByNameIdxKey() {
	ICFSecSecSysGrpIncByNameIdxKey key =
            new CFSecJpaSecSysGrpIncByNameIdxKey();
	return( key );
    }

	public CFSecJpaSecSysGrpIncByNameIdxKey ensureByNameIdxKey(ICFSecSecSysGrpIncByNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecSysGrpIncByNameIdxKey) {
			return( (CFSecJpaSecSysGrpIncByNameIdxKey)key );
		}
		else {
			CFSecJpaSecSysGrpIncByNameIdxKey mapped = new CFSecJpaSecSysGrpIncByNameIdxKey();
			mapped.setRequiredInclName( key.getRequiredInclName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecSysGrpInc newRec() {
        ICFSecSecSysGrpInc rec =
            new CFSecJpaSecSysGrpInc();
        return( rec );
    }

	public CFSecJpaSecSysGrpInc ensureRec(ICFSecSecSysGrpInc rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaSecSysGrpInc) {
			return( (CFSecJpaSecSysGrpInc)rec );
		}
		else {
			CFSecJpaSecSysGrpInc mapped = new CFSecJpaSecSysGrpInc();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecSecSysGrpIncH newHRec() {
        ICFSecSecSysGrpIncH hrec =
            new CFSecJpaSecSysGrpIncH();
        return( hrec );
    }

	public CFSecJpaSecSysGrpIncH ensureHRec(ICFSecSecSysGrpIncH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaSecSysGrpIncH) {
			return( (CFSecJpaSecSysGrpIncH)hrec );
		}
		else {
			CFSecJpaSecSysGrpIncH mapped = new CFSecJpaSecSysGrpIncH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

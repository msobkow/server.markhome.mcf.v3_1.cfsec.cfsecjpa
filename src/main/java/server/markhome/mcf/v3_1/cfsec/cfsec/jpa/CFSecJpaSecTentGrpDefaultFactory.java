
// Description: Java 25 JPA Default Factory implementation for SecTentGrp.

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
 *	CFSecSecTentGrpFactory JPA implementation for SecTentGrp
 */
public class CFSecJpaSecTentGrpDefaultFactory
    implements ICFSecSecTentGrpFactory
{
    public CFSecJpaSecTentGrpDefaultFactory() {
    }

    @Override
    public ICFSecSecTentGrpHPKey newHPKey() {
        ICFSecSecTentGrpHPKey hpkey =
            new CFSecJpaSecTentGrpHPKey();
        return( hpkey );
    }

	public CFSecJpaSecTentGrpHPKey ensureHPKey(ICFSecSecTentGrpHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaSecTentGrpHPKey) {
			return( (CFSecJpaSecTentGrpHPKey)key );
		}
		else {
			CFSecJpaSecTentGrpHPKey mapped = new CFSecJpaSecTentGrpHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredSecTentGrpId( key.getRequiredSecTentGrpId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecTentGrpByTenantIdxKey newByTenantIdxKey() {
	ICFSecSecTentGrpByTenantIdxKey key =
            new CFSecJpaSecTentGrpByTenantIdxKey();
	return( key );
    }

	public CFSecJpaSecTentGrpByTenantIdxKey ensureByTenantIdxKey(ICFSecSecTentGrpByTenantIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecTentGrpByTenantIdxKey) {
			return( (CFSecJpaSecTentGrpByTenantIdxKey)key );
		}
		else {
			CFSecJpaSecTentGrpByTenantIdxKey mapped = new CFSecJpaSecTentGrpByTenantIdxKey();
			mapped.setRequiredTenantId( key.getRequiredTenantId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecTentGrpByNameIdxKey newByNameIdxKey() {
	ICFSecSecTentGrpByNameIdxKey key =
            new CFSecJpaSecTentGrpByNameIdxKey();
	return( key );
    }

	public CFSecJpaSecTentGrpByNameIdxKey ensureByNameIdxKey(ICFSecSecTentGrpByNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecTentGrpByNameIdxKey) {
			return( (CFSecJpaSecTentGrpByNameIdxKey)key );
		}
		else {
			CFSecJpaSecTentGrpByNameIdxKey mapped = new CFSecJpaSecTentGrpByNameIdxKey();
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecTentGrpByUNameIdxKey newByUNameIdxKey() {
	ICFSecSecTentGrpByUNameIdxKey key =
            new CFSecJpaSecTentGrpByUNameIdxKey();
	return( key );
    }

	public CFSecJpaSecTentGrpByUNameIdxKey ensureByUNameIdxKey(ICFSecSecTentGrpByUNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecTentGrpByUNameIdxKey) {
			return( (CFSecJpaSecTentGrpByUNameIdxKey)key );
		}
		else {
			CFSecJpaSecTentGrpByUNameIdxKey mapped = new CFSecJpaSecTentGrpByUNameIdxKey();
			mapped.setRequiredTenantId( key.getRequiredTenantId() );
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecTentGrp newRec() {
        ICFSecSecTentGrp rec =
            new CFSecJpaSecTentGrp();
        return( rec );
    }

	public CFSecJpaSecTentGrp ensureRec(ICFSecSecTentGrp rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaSecTentGrp) {
			return( (CFSecJpaSecTentGrp)rec );
		}
		else {
			CFSecJpaSecTentGrp mapped = new CFSecJpaSecTentGrp();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecSecTentGrpH newHRec() {
        ICFSecSecTentGrpH hrec =
            new CFSecJpaSecTentGrpH();
        return( hrec );
    }

	public CFSecJpaSecTentGrpH ensureHRec(ICFSecSecTentGrpH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaSecTentGrpH) {
			return( (CFSecJpaSecTentGrpH)hrec );
		}
		else {
			CFSecJpaSecTentGrpH mapped = new CFSecJpaSecTentGrpH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}


// Description: Java 25 JPA Default Factory implementation for Tenant.

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
 *	CFSecTenantFactory JPA implementation for Tenant
 */
public class CFSecJpaTenantDefaultFactory
    implements ICFSecTenantFactory
{
    public CFSecJpaTenantDefaultFactory() {
    }

    @Override
    public ICFSecTenantHPKey newHPKey() {
        ICFSecTenantHPKey hpkey =
            new CFSecJpaTenantHPKey();
        return( hpkey );
    }

	public CFSecJpaTenantHPKey ensureHPKey(ICFSecTenantHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaTenantHPKey) {
			return( (CFSecJpaTenantHPKey)key );
		}
		else {
			CFSecJpaTenantHPKey mapped = new CFSecJpaTenantHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredId( key.getRequiredId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTenantByClusterIdxKey newByClusterIdxKey() {
	ICFSecTenantByClusterIdxKey key =
            new CFSecJpaTenantByClusterIdxKey();
	return( key );
    }

	public CFSecJpaTenantByClusterIdxKey ensureByClusterIdxKey(ICFSecTenantByClusterIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaTenantByClusterIdxKey) {
			return( (CFSecJpaTenantByClusterIdxKey)key );
		}
		else {
			CFSecJpaTenantByClusterIdxKey mapped = new CFSecJpaTenantByClusterIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTenantByUNameIdxKey newByUNameIdxKey() {
	ICFSecTenantByUNameIdxKey key =
            new CFSecJpaTenantByUNameIdxKey();
	return( key );
    }

	public CFSecJpaTenantByUNameIdxKey ensureByUNameIdxKey(ICFSecTenantByUNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaTenantByUNameIdxKey) {
			return( (CFSecJpaTenantByUNameIdxKey)key );
		}
		else {
			CFSecJpaTenantByUNameIdxKey mapped = new CFSecJpaTenantByUNameIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			mapped.setRequiredTenantName( key.getRequiredTenantName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTenant newRec() {
        ICFSecTenant rec =
            new CFSecJpaTenant();
        return( rec );
    }

	public CFSecJpaTenant ensureRec(ICFSecTenant rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaTenant) {
			return( (CFSecJpaTenant)rec );
		}
		else {
			CFSecJpaTenant mapped = new CFSecJpaTenant();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecTenantH newHRec() {
        ICFSecTenantH hrec =
            new CFSecJpaTenantH();
        return( hrec );
    }

	public CFSecJpaTenantH ensureHRec(ICFSecTenantH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaTenantH) {
			return( (CFSecJpaTenantH)hrec );
		}
		else {
			CFSecJpaTenantH mapped = new CFSecJpaTenantH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

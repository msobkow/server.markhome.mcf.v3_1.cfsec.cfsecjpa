
// Description: Java 25 JPA Default Factory implementation for TSecGroup.

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
 *	CFSecTSecGroupFactory JPA implementation for TSecGroup
 */
public class CFSecJpaTSecGroupDefaultFactory
    implements ICFSecTSecGroupFactory
{
    public CFSecJpaTSecGroupDefaultFactory() {
    }

    @Override
    public ICFSecTSecGroupHPKey newHPKey() {
        ICFSecTSecGroupHPKey hpkey =
            new CFSecJpaTSecGroupHPKey();
        return( hpkey );
    }

	public CFSecJpaTSecGroupHPKey ensureHPKey(ICFSecTSecGroupHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaTSecGroupHPKey) {
			return( (CFSecJpaTSecGroupHPKey)key );
		}
		else {
			CFSecJpaTSecGroupHPKey mapped = new CFSecJpaTSecGroupHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredTSecGroupId( key.getRequiredTSecGroupId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGroupByTenantIdxKey newByTenantIdxKey() {
	ICFSecTSecGroupByTenantIdxKey key =
            new CFSecJpaTSecGroupByTenantIdxKey();
	return( key );
    }

	public CFSecJpaTSecGroupByTenantIdxKey ensureByTenantIdxKey(ICFSecTSecGroupByTenantIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaTSecGroupByTenantIdxKey) {
			return( (CFSecJpaTSecGroupByTenantIdxKey)key );
		}
		else {
			CFSecJpaTSecGroupByTenantIdxKey mapped = new CFSecJpaTSecGroupByTenantIdxKey();
			mapped.setRequiredTenantId( key.getRequiredTenantId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGroupByTenantVisIdxKey newByTenantVisIdxKey() {
	ICFSecTSecGroupByTenantVisIdxKey key =
            new CFSecJpaTSecGroupByTenantVisIdxKey();
	return( key );
    }

	public CFSecJpaTSecGroupByTenantVisIdxKey ensureByTenantVisIdxKey(ICFSecTSecGroupByTenantVisIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaTSecGroupByTenantVisIdxKey) {
			return( (CFSecJpaTSecGroupByTenantVisIdxKey)key );
		}
		else {
			CFSecJpaTSecGroupByTenantVisIdxKey mapped = new CFSecJpaTSecGroupByTenantVisIdxKey();
			mapped.setRequiredTenantId( key.getRequiredTenantId() );
			mapped.setRequiredIsVisible( key.getRequiredIsVisible() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGroupByUNameIdxKey newByUNameIdxKey() {
	ICFSecTSecGroupByUNameIdxKey key =
            new CFSecJpaTSecGroupByUNameIdxKey();
	return( key );
    }

	public CFSecJpaTSecGroupByUNameIdxKey ensureByUNameIdxKey(ICFSecTSecGroupByUNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaTSecGroupByUNameIdxKey) {
			return( (CFSecJpaTSecGroupByUNameIdxKey)key );
		}
		else {
			CFSecJpaTSecGroupByUNameIdxKey mapped = new CFSecJpaTSecGroupByUNameIdxKey();
			mapped.setRequiredTenantId( key.getRequiredTenantId() );
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGroup newRec() {
        ICFSecTSecGroup rec =
            new CFSecJpaTSecGroup();
        return( rec );
    }

	public CFSecJpaTSecGroup ensureRec(ICFSecTSecGroup rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaTSecGroup) {
			return( (CFSecJpaTSecGroup)rec );
		}
		else {
			CFSecJpaTSecGroup mapped = new CFSecJpaTSecGroup();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecTSecGroupH newHRec() {
        ICFSecTSecGroupH hrec =
            new CFSecJpaTSecGroupH();
        return( hrec );
    }

	public CFSecJpaTSecGroupH ensureHRec(ICFSecTSecGroupH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaTSecGroupH) {
			return( (CFSecJpaTSecGroupH)hrec );
		}
		else {
			CFSecJpaTSecGroupH mapped = new CFSecJpaTSecGroupH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

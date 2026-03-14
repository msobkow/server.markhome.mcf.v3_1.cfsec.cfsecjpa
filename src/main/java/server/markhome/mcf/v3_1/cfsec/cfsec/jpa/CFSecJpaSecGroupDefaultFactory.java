
// Description: Java 25 JPA Default Factory implementation for SecGroup.

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
 *	CFSecSecGroupFactory JPA implementation for SecGroup
 */
public class CFSecJpaSecGroupDefaultFactory
    implements ICFSecSecGroupFactory
{
    public CFSecJpaSecGroupDefaultFactory() {
    }

    @Override
    public ICFSecSecGroupHPKey newHPKey() {
        ICFSecSecGroupHPKey hpkey =
            new CFSecJpaSecGroupHPKey();
        return( hpkey );
    }

	public CFSecJpaSecGroupHPKey ensureHPKey(ICFSecSecGroupHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaSecGroupHPKey) {
			return( (CFSecJpaSecGroupHPKey)key );
		}
		else {
			CFSecJpaSecGroupHPKey mapped = new CFSecJpaSecGroupHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredSecGroupId( key.getRequiredSecGroupId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGroupByClusterIdxKey newByClusterIdxKey() {
	ICFSecSecGroupByClusterIdxKey key =
            new CFSecJpaSecGroupByClusterIdxKey();
	return( key );
    }

	public CFSecJpaSecGroupByClusterIdxKey ensureByClusterIdxKey(ICFSecSecGroupByClusterIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecGroupByClusterIdxKey) {
			return( (CFSecJpaSecGroupByClusterIdxKey)key );
		}
		else {
			CFSecJpaSecGroupByClusterIdxKey mapped = new CFSecJpaSecGroupByClusterIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGroupByClusterVisIdxKey newByClusterVisIdxKey() {
	ICFSecSecGroupByClusterVisIdxKey key =
            new CFSecJpaSecGroupByClusterVisIdxKey();
	return( key );
    }

	public CFSecJpaSecGroupByClusterVisIdxKey ensureByClusterVisIdxKey(ICFSecSecGroupByClusterVisIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecGroupByClusterVisIdxKey) {
			return( (CFSecJpaSecGroupByClusterVisIdxKey)key );
		}
		else {
			CFSecJpaSecGroupByClusterVisIdxKey mapped = new CFSecJpaSecGroupByClusterVisIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			mapped.setRequiredIsVisible( key.getRequiredIsVisible() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGroupByUNameIdxKey newByUNameIdxKey() {
	ICFSecSecGroupByUNameIdxKey key =
            new CFSecJpaSecGroupByUNameIdxKey();
	return( key );
    }

	public CFSecJpaSecGroupByUNameIdxKey ensureByUNameIdxKey(ICFSecSecGroupByUNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecGroupByUNameIdxKey) {
			return( (CFSecJpaSecGroupByUNameIdxKey)key );
		}
		else {
			CFSecJpaSecGroupByUNameIdxKey mapped = new CFSecJpaSecGroupByUNameIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGroup newRec() {
        ICFSecSecGroup rec =
            new CFSecJpaSecGroup();
        return( rec );
    }

	public CFSecJpaSecGroup ensureRec(ICFSecSecGroup rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaSecGroup) {
			return( (CFSecJpaSecGroup)rec );
		}
		else {
			CFSecJpaSecGroup mapped = new CFSecJpaSecGroup();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGroupH newHRec() {
        ICFSecSecGroupH hrec =
            new CFSecJpaSecGroupH();
        return( hrec );
    }

	public CFSecJpaSecGroupH ensureHRec(ICFSecSecGroupH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaSecGroupH) {
			return( (CFSecJpaSecGroupH)hrec );
		}
		else {
			CFSecJpaSecGroupH mapped = new CFSecJpaSecGroupH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

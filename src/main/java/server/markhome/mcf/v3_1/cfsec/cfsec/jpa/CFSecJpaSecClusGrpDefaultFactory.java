
// Description: Java 25 JPA Default Factory implementation for SecClusGrp.

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
 *	CFSecSecClusGrpFactory JPA implementation for SecClusGrp
 */
public class CFSecJpaSecClusGrpDefaultFactory
    implements ICFSecSecClusGrpFactory
{
    public CFSecJpaSecClusGrpDefaultFactory() {
    }

    @Override
    public ICFSecSecClusGrpHPKey newHPKey() {
        ICFSecSecClusGrpHPKey hpkey =
            new CFSecJpaSecClusGrpHPKey();
        return( hpkey );
    }

	public CFSecJpaSecClusGrpHPKey ensureHPKey(ICFSecSecClusGrpHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaSecClusGrpHPKey) {
			return( (CFSecJpaSecClusGrpHPKey)key );
		}
		else {
			CFSecJpaSecClusGrpHPKey mapped = new CFSecJpaSecClusGrpHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredSecClusGrpId( key.getRequiredSecClusGrpId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecClusGrpByClusterIdxKey newByClusterIdxKey() {
	ICFSecSecClusGrpByClusterIdxKey key =
            new CFSecJpaSecClusGrpByClusterIdxKey();
	return( key );
    }

	public CFSecJpaSecClusGrpByClusterIdxKey ensureByClusterIdxKey(ICFSecSecClusGrpByClusterIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecClusGrpByClusterIdxKey) {
			return( (CFSecJpaSecClusGrpByClusterIdxKey)key );
		}
		else {
			CFSecJpaSecClusGrpByClusterIdxKey mapped = new CFSecJpaSecClusGrpByClusterIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecClusGrpByNameIdxKey newByNameIdxKey() {
	ICFSecSecClusGrpByNameIdxKey key =
            new CFSecJpaSecClusGrpByNameIdxKey();
	return( key );
    }

	public CFSecJpaSecClusGrpByNameIdxKey ensureByNameIdxKey(ICFSecSecClusGrpByNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecClusGrpByNameIdxKey) {
			return( (CFSecJpaSecClusGrpByNameIdxKey)key );
		}
		else {
			CFSecJpaSecClusGrpByNameIdxKey mapped = new CFSecJpaSecClusGrpByNameIdxKey();
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecClusGrpByUNameIdxKey newByUNameIdxKey() {
	ICFSecSecClusGrpByUNameIdxKey key =
            new CFSecJpaSecClusGrpByUNameIdxKey();
	return( key );
    }

	public CFSecJpaSecClusGrpByUNameIdxKey ensureByUNameIdxKey(ICFSecSecClusGrpByUNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecClusGrpByUNameIdxKey) {
			return( (CFSecJpaSecClusGrpByUNameIdxKey)key );
		}
		else {
			CFSecJpaSecClusGrpByUNameIdxKey mapped = new CFSecJpaSecClusGrpByUNameIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecClusGrp newRec() {
        ICFSecSecClusGrp rec =
            new CFSecJpaSecClusGrp();
        return( rec );
    }

	public CFSecJpaSecClusGrp ensureRec(ICFSecSecClusGrp rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaSecClusGrp) {
			return( (CFSecJpaSecClusGrp)rec );
		}
		else {
			CFSecJpaSecClusGrp mapped = new CFSecJpaSecClusGrp();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecSecClusGrpH newHRec() {
        ICFSecSecClusGrpH hrec =
            new CFSecJpaSecClusGrpH();
        return( hrec );
    }

	public CFSecJpaSecClusGrpH ensureHRec(ICFSecSecClusGrpH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaSecClusGrpH) {
			return( (CFSecJpaSecClusGrpH)hrec );
		}
		else {
			CFSecJpaSecClusGrpH mapped = new CFSecJpaSecClusGrpH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

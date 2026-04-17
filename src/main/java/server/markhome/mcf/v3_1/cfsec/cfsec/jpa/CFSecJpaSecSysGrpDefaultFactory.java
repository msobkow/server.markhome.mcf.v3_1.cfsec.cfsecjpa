
// Description: Java 25 JPA Default Factory implementation for SecSysGrp.

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
 *	CFSecSecSysGrpFactory JPA implementation for SecSysGrp
 */
public class CFSecJpaSecSysGrpDefaultFactory
    implements ICFSecSecSysGrpFactory
{
    public CFSecJpaSecSysGrpDefaultFactory() {
    }

    @Override
    public ICFSecSecSysGrpHPKey newHPKey() {
        ICFSecSecSysGrpHPKey hpkey =
            new CFSecJpaSecSysGrpHPKey();
        return( hpkey );
    }

	public CFSecJpaSecSysGrpHPKey ensureHPKey(ICFSecSecSysGrpHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaSecSysGrpHPKey) {
			return( (CFSecJpaSecSysGrpHPKey)key );
		}
		else {
			CFSecJpaSecSysGrpHPKey mapped = new CFSecJpaSecSysGrpHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredSecSysGrpId( key.getRequiredSecSysGrpId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecSysGrpByUNameIdxKey newByUNameIdxKey() {
	ICFSecSecSysGrpByUNameIdxKey key =
            new CFSecJpaSecSysGrpByUNameIdxKey();
	return( key );
    }

	public CFSecJpaSecSysGrpByUNameIdxKey ensureByUNameIdxKey(ICFSecSecSysGrpByUNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecSysGrpByUNameIdxKey) {
			return( (CFSecJpaSecSysGrpByUNameIdxKey)key );
		}
		else {
			CFSecJpaSecSysGrpByUNameIdxKey mapped = new CFSecJpaSecSysGrpByUNameIdxKey();
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecSysGrpBySecLevelIdxKey newBySecLevelIdxKey() {
	ICFSecSecSysGrpBySecLevelIdxKey key =
            new CFSecJpaSecSysGrpBySecLevelIdxKey();
	return( key );
    }

	public CFSecJpaSecSysGrpBySecLevelIdxKey ensureBySecLevelIdxKey(ICFSecSecSysGrpBySecLevelIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecSysGrpBySecLevelIdxKey) {
			return( (CFSecJpaSecSysGrpBySecLevelIdxKey)key );
		}
		else {
			CFSecJpaSecSysGrpBySecLevelIdxKey mapped = new CFSecJpaSecSysGrpBySecLevelIdxKey();
			mapped.setRequiredSecLevel( key.getRequiredSecLevel() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecSysGrp newRec() {
        ICFSecSecSysGrp rec =
            new CFSecJpaSecSysGrp();
        return( rec );
    }

	public CFSecJpaSecSysGrp ensureRec(ICFSecSecSysGrp rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaSecSysGrp) {
			return( (CFSecJpaSecSysGrp)rec );
		}
		else {
			CFSecJpaSecSysGrp mapped = new CFSecJpaSecSysGrp();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecSecSysGrpH newHRec() {
        ICFSecSecSysGrpH hrec =
            new CFSecJpaSecSysGrpH();
        return( hrec );
    }

	public CFSecJpaSecSysGrpH ensureHRec(ICFSecSecSysGrpH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaSecSysGrpH) {
			return( (CFSecJpaSecSysGrpH)hrec );
		}
		else {
			CFSecJpaSecSysGrpH mapped = new CFSecJpaSecSysGrpH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

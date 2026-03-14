
// Description: Java 25 JPA Default Factory implementation for ISOTZone.

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
 *	CFSecISOTZoneFactory JPA implementation for ISOTZone
 */
public class CFSecJpaISOTZoneDefaultFactory
    implements ICFSecISOTZoneFactory
{
    public CFSecJpaISOTZoneDefaultFactory() {
    }

    @Override
    public ICFSecISOTZoneHPKey newHPKey() {
        ICFSecISOTZoneHPKey hpkey =
            new CFSecJpaISOTZoneHPKey();
        return( hpkey );
    }

	public CFSecJpaISOTZoneHPKey ensureHPKey(ICFSecISOTZoneHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaISOTZoneHPKey) {
			return( (CFSecJpaISOTZoneHPKey)key );
		}
		else {
			CFSecJpaISOTZoneHPKey mapped = new CFSecJpaISOTZoneHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredISOTZoneId( key.getRequiredISOTZoneId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOTZoneByOffsetIdxKey newByOffsetIdxKey() {
	ICFSecISOTZoneByOffsetIdxKey key =
            new CFSecJpaISOTZoneByOffsetIdxKey();
	return( key );
    }

	public CFSecJpaISOTZoneByOffsetIdxKey ensureByOffsetIdxKey(ICFSecISOTZoneByOffsetIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaISOTZoneByOffsetIdxKey) {
			return( (CFSecJpaISOTZoneByOffsetIdxKey)key );
		}
		else {
			CFSecJpaISOTZoneByOffsetIdxKey mapped = new CFSecJpaISOTZoneByOffsetIdxKey();
			mapped.setRequiredTZHourOffset( key.getRequiredTZHourOffset() );
			mapped.setRequiredTZMinOffset( key.getRequiredTZMinOffset() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOTZoneByUTZNameIdxKey newByUTZNameIdxKey() {
	ICFSecISOTZoneByUTZNameIdxKey key =
            new CFSecJpaISOTZoneByUTZNameIdxKey();
	return( key );
    }

	public CFSecJpaISOTZoneByUTZNameIdxKey ensureByUTZNameIdxKey(ICFSecISOTZoneByUTZNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaISOTZoneByUTZNameIdxKey) {
			return( (CFSecJpaISOTZoneByUTZNameIdxKey)key );
		}
		else {
			CFSecJpaISOTZoneByUTZNameIdxKey mapped = new CFSecJpaISOTZoneByUTZNameIdxKey();
			mapped.setRequiredTZName( key.getRequiredTZName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOTZoneByIso8601IdxKey newByIso8601IdxKey() {
	ICFSecISOTZoneByIso8601IdxKey key =
            new CFSecJpaISOTZoneByIso8601IdxKey();
	return( key );
    }

	public CFSecJpaISOTZoneByIso8601IdxKey ensureByIso8601IdxKey(ICFSecISOTZoneByIso8601IdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaISOTZoneByIso8601IdxKey) {
			return( (CFSecJpaISOTZoneByIso8601IdxKey)key );
		}
		else {
			CFSecJpaISOTZoneByIso8601IdxKey mapped = new CFSecJpaISOTZoneByIso8601IdxKey();
			mapped.setRequiredIso8601( key.getRequiredIso8601() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOTZone newRec() {
        ICFSecISOTZone rec =
            new CFSecJpaISOTZone();
        return( rec );
    }

	public CFSecJpaISOTZone ensureRec(ICFSecISOTZone rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaISOTZone) {
			return( (CFSecJpaISOTZone)rec );
		}
		else {
			CFSecJpaISOTZone mapped = new CFSecJpaISOTZone();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecISOTZoneH newHRec() {
        ICFSecISOTZoneH hrec =
            new CFSecJpaISOTZoneH();
        return( hrec );
    }

	public CFSecJpaISOTZoneH ensureHRec(ICFSecISOTZoneH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaISOTZoneH) {
			return( (CFSecJpaISOTZoneH)hrec );
		}
		else {
			CFSecJpaISOTZoneH mapped = new CFSecJpaISOTZoneH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

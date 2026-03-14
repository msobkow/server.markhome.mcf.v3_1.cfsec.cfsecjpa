
// Description: Java 25 JPA Default Factory implementation for ISOCtry.

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
 *	CFSecISOCtryFactory JPA implementation for ISOCtry
 */
public class CFSecJpaISOCtryDefaultFactory
    implements ICFSecISOCtryFactory
{
    public CFSecJpaISOCtryDefaultFactory() {
    }

    @Override
    public ICFSecISOCtryHPKey newHPKey() {
        ICFSecISOCtryHPKey hpkey =
            new CFSecJpaISOCtryHPKey();
        return( hpkey );
    }

	public CFSecJpaISOCtryHPKey ensureHPKey(ICFSecISOCtryHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaISOCtryHPKey) {
			return( (CFSecJpaISOCtryHPKey)key );
		}
		else {
			CFSecJpaISOCtryHPKey mapped = new CFSecJpaISOCtryHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredISOCtryId( key.getRequiredISOCtryId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOCtryByISOCodeIdxKey newByISOCodeIdxKey() {
	ICFSecISOCtryByISOCodeIdxKey key =
            new CFSecJpaISOCtryByISOCodeIdxKey();
	return( key );
    }

	public CFSecJpaISOCtryByISOCodeIdxKey ensureByISOCodeIdxKey(ICFSecISOCtryByISOCodeIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaISOCtryByISOCodeIdxKey) {
			return( (CFSecJpaISOCtryByISOCodeIdxKey)key );
		}
		else {
			CFSecJpaISOCtryByISOCodeIdxKey mapped = new CFSecJpaISOCtryByISOCodeIdxKey();
			mapped.setRequiredISOCode( key.getRequiredISOCode() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOCtryByNameIdxKey newByNameIdxKey() {
	ICFSecISOCtryByNameIdxKey key =
            new CFSecJpaISOCtryByNameIdxKey();
	return( key );
    }

	public CFSecJpaISOCtryByNameIdxKey ensureByNameIdxKey(ICFSecISOCtryByNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaISOCtryByNameIdxKey) {
			return( (CFSecJpaISOCtryByNameIdxKey)key );
		}
		else {
			CFSecJpaISOCtryByNameIdxKey mapped = new CFSecJpaISOCtryByNameIdxKey();
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOCtry newRec() {
        ICFSecISOCtry rec =
            new CFSecJpaISOCtry();
        return( rec );
    }

	public CFSecJpaISOCtry ensureRec(ICFSecISOCtry rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaISOCtry) {
			return( (CFSecJpaISOCtry)rec );
		}
		else {
			CFSecJpaISOCtry mapped = new CFSecJpaISOCtry();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecISOCtryH newHRec() {
        ICFSecISOCtryH hrec =
            new CFSecJpaISOCtryH();
        return( hrec );
    }

	public CFSecJpaISOCtryH ensureHRec(ICFSecISOCtryH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaISOCtryH) {
			return( (CFSecJpaISOCtryH)hrec );
		}
		else {
			CFSecJpaISOCtryH mapped = new CFSecJpaISOCtryH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

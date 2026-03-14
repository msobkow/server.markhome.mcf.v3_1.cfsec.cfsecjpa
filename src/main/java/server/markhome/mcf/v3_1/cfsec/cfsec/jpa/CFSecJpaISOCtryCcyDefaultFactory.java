
// Description: Java 25 JPA Default Factory implementation for ISOCtryCcy.

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
 *	CFSecISOCtryCcyFactory JPA implementation for ISOCtryCcy
 */
public class CFSecJpaISOCtryCcyDefaultFactory
    implements ICFSecISOCtryCcyFactory
{
    public CFSecJpaISOCtryCcyDefaultFactory() {
    }

    @Override
    public ICFSecISOCtryCcyPKey newPKey() {
        ICFSecISOCtryCcyPKey pkey =
            new CFSecJpaISOCtryCcyPKey();
        return( pkey );
    }

	public CFSecJpaISOCtryCcyPKey ensurePKey(ICFSecISOCtryCcyPKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaISOCtryCcyPKey) {
			return( (CFSecJpaISOCtryCcyPKey)key );
		}
		else {
			CFSecJpaISOCtryCcyPKey mapped = new CFSecJpaISOCtryCcyPKey();
			mapped.setRequiredContainerCtry( key.getRequiredISOCtryId() );
			mapped.setRequiredParentCcy( key.getRequiredISOCcyId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOCtryCcyHPKey newHPKey() {
        ICFSecISOCtryCcyHPKey hpkey =
            new CFSecJpaISOCtryCcyHPKey();
        return( hpkey );
    }

	public CFSecJpaISOCtryCcyHPKey ensureHPKey(ICFSecISOCtryCcyHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaISOCtryCcyHPKey) {
			return( (CFSecJpaISOCtryCcyHPKey)key );
		}
		else {
			CFSecJpaISOCtryCcyHPKey mapped = new CFSecJpaISOCtryCcyHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredISOCtryId( key.getRequiredISOCtryId() );
			mapped.setRequiredISOCcyId( key.getRequiredISOCcyId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOCtryCcyByCtryIdxKey newByCtryIdxKey() {
	ICFSecISOCtryCcyByCtryIdxKey key =
            new CFSecJpaISOCtryCcyByCtryIdxKey();
	return( key );
    }

	public CFSecJpaISOCtryCcyByCtryIdxKey ensureByCtryIdxKey(ICFSecISOCtryCcyByCtryIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaISOCtryCcyByCtryIdxKey) {
			return( (CFSecJpaISOCtryCcyByCtryIdxKey)key );
		}
		else {
			CFSecJpaISOCtryCcyByCtryIdxKey mapped = new CFSecJpaISOCtryCcyByCtryIdxKey();
			mapped.setRequiredISOCtryId( key.getRequiredISOCtryId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOCtryCcyByCcyIdxKey newByCcyIdxKey() {
	ICFSecISOCtryCcyByCcyIdxKey key =
            new CFSecJpaISOCtryCcyByCcyIdxKey();
	return( key );
    }

	public CFSecJpaISOCtryCcyByCcyIdxKey ensureByCcyIdxKey(ICFSecISOCtryCcyByCcyIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaISOCtryCcyByCcyIdxKey) {
			return( (CFSecJpaISOCtryCcyByCcyIdxKey)key );
		}
		else {
			CFSecJpaISOCtryCcyByCcyIdxKey mapped = new CFSecJpaISOCtryCcyByCcyIdxKey();
			mapped.setRequiredISOCcyId( key.getRequiredISOCcyId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOCtryCcy newRec() {
        ICFSecISOCtryCcy rec =
            new CFSecJpaISOCtryCcy();
        return( rec );
    }

	public CFSecJpaISOCtryCcy ensureRec(ICFSecISOCtryCcy rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaISOCtryCcy) {
			return( (CFSecJpaISOCtryCcy)rec );
		}
		else {
			CFSecJpaISOCtryCcy mapped = new CFSecJpaISOCtryCcy();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecISOCtryCcyH newHRec() {
        ICFSecISOCtryCcyH hrec =
            new CFSecJpaISOCtryCcyH();
        return( hrec );
    }

	public CFSecJpaISOCtryCcyH ensureHRec(ICFSecISOCtryCcyH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaISOCtryCcyH) {
			return( (CFSecJpaISOCtryCcyH)hrec );
		}
		else {
			CFSecJpaISOCtryCcyH mapped = new CFSecJpaISOCtryCcyH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

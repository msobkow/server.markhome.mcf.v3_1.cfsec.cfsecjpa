
// Description: Java 25 JPA Default Factory implementation for ISOCcy.

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
 *	CFSecISOCcyFactory JPA implementation for ISOCcy
 */
public class CFSecJpaISOCcyDefaultFactory
    implements ICFSecISOCcyFactory
{
    public CFSecJpaISOCcyDefaultFactory() {
    }

    @Override
    public ICFSecISOCcyHPKey newHPKey() {
        ICFSecISOCcyHPKey hpkey =
            new CFSecJpaISOCcyHPKey();
        return( hpkey );
    }

	public CFSecJpaISOCcyHPKey ensureHPKey(ICFSecISOCcyHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaISOCcyHPKey) {
			return( (CFSecJpaISOCcyHPKey)key );
		}
		else {
			CFSecJpaISOCcyHPKey mapped = new CFSecJpaISOCcyHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredISOCcyId( key.getRequiredISOCcyId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOCcyByCcyCdIdxKey newByCcyCdIdxKey() {
	ICFSecISOCcyByCcyCdIdxKey key =
            new CFSecJpaISOCcyByCcyCdIdxKey();
	return( key );
    }

	public CFSecJpaISOCcyByCcyCdIdxKey ensureByCcyCdIdxKey(ICFSecISOCcyByCcyCdIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaISOCcyByCcyCdIdxKey) {
			return( (CFSecJpaISOCcyByCcyCdIdxKey)key );
		}
		else {
			CFSecJpaISOCcyByCcyCdIdxKey mapped = new CFSecJpaISOCcyByCcyCdIdxKey();
			mapped.setRequiredISOCode( key.getRequiredISOCode() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOCcyByCcyNmIdxKey newByCcyNmIdxKey() {
	ICFSecISOCcyByCcyNmIdxKey key =
            new CFSecJpaISOCcyByCcyNmIdxKey();
	return( key );
    }

	public CFSecJpaISOCcyByCcyNmIdxKey ensureByCcyNmIdxKey(ICFSecISOCcyByCcyNmIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaISOCcyByCcyNmIdxKey) {
			return( (CFSecJpaISOCcyByCcyNmIdxKey)key );
		}
		else {
			CFSecJpaISOCcyByCcyNmIdxKey mapped = new CFSecJpaISOCcyByCcyNmIdxKey();
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOCcy newRec() {
        ICFSecISOCcy rec =
            new CFSecJpaISOCcy();
        return( rec );
    }

	public CFSecJpaISOCcy ensureRec(ICFSecISOCcy rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaISOCcy) {
			return( (CFSecJpaISOCcy)rec );
		}
		else {
			CFSecJpaISOCcy mapped = new CFSecJpaISOCcy();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecISOCcyH newHRec() {
        ICFSecISOCcyH hrec =
            new CFSecJpaISOCcyH();
        return( hrec );
    }

	public CFSecJpaISOCcyH ensureHRec(ICFSecISOCcyH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaISOCcyH) {
			return( (CFSecJpaISOCcyH)hrec );
		}
		else {
			CFSecJpaISOCcyH mapped = new CFSecJpaISOCcyH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

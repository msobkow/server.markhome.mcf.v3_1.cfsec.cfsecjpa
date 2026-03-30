
// Description: Java 25 JPA Default Factory implementation for SecUserPWReset.

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
 *	CFSecSecUserPWResetFactory JPA implementation for SecUserPWReset
 */
public class CFSecJpaSecUserPWResetDefaultFactory
    implements ICFSecSecUserPWResetFactory
{
    public CFSecJpaSecUserPWResetDefaultFactory() {
    }

    @Override
    public ICFSecSecUserPWResetHPKey newHPKey() {
        ICFSecSecUserPWResetHPKey hpkey =
            new CFSecJpaSecUserPWResetHPKey();
        return( hpkey );
    }

	public CFSecJpaSecUserPWResetHPKey ensureHPKey(ICFSecSecUserPWResetHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaSecUserPWResetHPKey) {
			return( (CFSecJpaSecUserPWResetHPKey)key );
		}
		else {
			CFSecJpaSecUserPWResetHPKey mapped = new CFSecJpaSecUserPWResetHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredSecUserId( key.getRequiredSecUserId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecUserPWResetByUUuid6IdxKey newByUUuid6IdxKey() {
	ICFSecSecUserPWResetByUUuid6IdxKey key =
            new CFSecJpaSecUserPWResetByUUuid6IdxKey();
	return( key );
    }

	public CFSecJpaSecUserPWResetByUUuid6IdxKey ensureByUUuid6IdxKey(ICFSecSecUserPWResetByUUuid6IdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecUserPWResetByUUuid6IdxKey) {
			return( (CFSecJpaSecUserPWResetByUUuid6IdxKey)key );
		}
		else {
			CFSecJpaSecUserPWResetByUUuid6IdxKey mapped = new CFSecJpaSecUserPWResetByUUuid6IdxKey();
			mapped.setRequiredPasswordResetUuid6( key.getRequiredPasswordResetUuid6() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecUserPWResetBySentEMAddrIdxKey newBySentEMAddrIdxKey() {
	ICFSecSecUserPWResetBySentEMAddrIdxKey key =
            new CFSecJpaSecUserPWResetBySentEMAddrIdxKey();
	return( key );
    }

	public CFSecJpaSecUserPWResetBySentEMAddrIdxKey ensureBySentEMAddrIdxKey(ICFSecSecUserPWResetBySentEMAddrIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecUserPWResetBySentEMAddrIdxKey) {
			return( (CFSecJpaSecUserPWResetBySentEMAddrIdxKey)key );
		}
		else {
			CFSecJpaSecUserPWResetBySentEMAddrIdxKey mapped = new CFSecJpaSecUserPWResetBySentEMAddrIdxKey();
			mapped.setRequiredSentToEMailAddr( key.getRequiredSentToEMailAddr() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecUserPWResetByNewAcctIdxKey newByNewAcctIdxKey() {
	ICFSecSecUserPWResetByNewAcctIdxKey key =
            new CFSecJpaSecUserPWResetByNewAcctIdxKey();
	return( key );
    }

	public CFSecJpaSecUserPWResetByNewAcctIdxKey ensureByNewAcctIdxKey(ICFSecSecUserPWResetByNewAcctIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecUserPWResetByNewAcctIdxKey) {
			return( (CFSecJpaSecUserPWResetByNewAcctIdxKey)key );
		}
		else {
			CFSecJpaSecUserPWResetByNewAcctIdxKey mapped = new CFSecJpaSecUserPWResetByNewAcctIdxKey();
			mapped.setRequiredNewAccount( key.getRequiredNewAccount() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecUserPWReset newRec() {
        ICFSecSecUserPWReset rec =
            new CFSecJpaSecUserPWReset();
        return( rec );
    }

	public CFSecJpaSecUserPWReset ensureRec(ICFSecSecUserPWReset rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaSecUserPWReset) {
			return( (CFSecJpaSecUserPWReset)rec );
		}
		else {
			CFSecJpaSecUserPWReset mapped = new CFSecJpaSecUserPWReset();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecSecUserPWResetH newHRec() {
        ICFSecSecUserPWResetH hrec =
            new CFSecJpaSecUserPWResetH();
        return( hrec );
    }

	public CFSecJpaSecUserPWResetH ensureHRec(ICFSecSecUserPWResetH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaSecUserPWResetH) {
			return( (CFSecJpaSecUserPWResetH)hrec );
		}
		else {
			CFSecJpaSecUserPWResetH mapped = new CFSecJpaSecUserPWResetH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

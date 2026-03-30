
// Description: Java 25 JPA Default Factory implementation for SecUser.

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
 *	CFSecSecUserFactory JPA implementation for SecUser
 */
public class CFSecJpaSecUserDefaultFactory
    implements ICFSecSecUserFactory
{
    public CFSecJpaSecUserDefaultFactory() {
    }

    @Override
    public ICFSecSecUserHPKey newHPKey() {
        ICFSecSecUserHPKey hpkey =
            new CFSecJpaSecUserHPKey();
        return( hpkey );
    }

	public CFSecJpaSecUserHPKey ensureHPKey(ICFSecSecUserHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaSecUserHPKey) {
			return( (CFSecJpaSecUserHPKey)key );
		}
		else {
			CFSecJpaSecUserHPKey mapped = new CFSecJpaSecUserHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredSecUserId( key.getRequiredSecUserId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecUserByULoginIdxKey newByULoginIdxKey() {
	ICFSecSecUserByULoginIdxKey key =
            new CFSecJpaSecUserByULoginIdxKey();
	return( key );
    }

	public CFSecJpaSecUserByULoginIdxKey ensureByULoginIdxKey(ICFSecSecUserByULoginIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecUserByULoginIdxKey) {
			return( (CFSecJpaSecUserByULoginIdxKey)key );
		}
		else {
			CFSecJpaSecUserByULoginIdxKey mapped = new CFSecJpaSecUserByULoginIdxKey();
			mapped.setRequiredLoginId( key.getRequiredLoginId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecUserByEMAddrIdxKey newByEMAddrIdxKey() {
	ICFSecSecUserByEMAddrIdxKey key =
            new CFSecJpaSecUserByEMAddrIdxKey();
	return( key );
    }

	public CFSecJpaSecUserByEMAddrIdxKey ensureByEMAddrIdxKey(ICFSecSecUserByEMAddrIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecUserByEMAddrIdxKey) {
			return( (CFSecJpaSecUserByEMAddrIdxKey)key );
		}
		else {
			CFSecJpaSecUserByEMAddrIdxKey mapped = new CFSecJpaSecUserByEMAddrIdxKey();
			mapped.setRequiredEMailAddress( key.getRequiredEMailAddress() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecUser newRec() {
        ICFSecSecUser rec =
            new CFSecJpaSecUser();
        return( rec );
    }

	public CFSecJpaSecUser ensureRec(ICFSecSecUser rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaSecUser) {
			return( (CFSecJpaSecUser)rec );
		}
		else {
			CFSecJpaSecUser mapped = new CFSecJpaSecUser();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecSecUserH newHRec() {
        ICFSecSecUserH hrec =
            new CFSecJpaSecUserH();
        return( hrec );
    }

	public CFSecJpaSecUserH ensureHRec(ICFSecSecUserH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaSecUserH) {
			return( (CFSecJpaSecUserH)hrec );
		}
		else {
			CFSecJpaSecUserH mapped = new CFSecJpaSecUserH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

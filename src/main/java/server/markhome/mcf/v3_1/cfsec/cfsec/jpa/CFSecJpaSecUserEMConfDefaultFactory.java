
// Description: Java 25 JPA Default Factory implementation for SecUserEMConf.

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
 *	CFSecSecUserEMConfFactory JPA implementation for SecUserEMConf
 */
public class CFSecJpaSecUserEMConfDefaultFactory
    implements ICFSecSecUserEMConfFactory
{
    public CFSecJpaSecUserEMConfDefaultFactory() {
    }

    @Override
    public ICFSecSecUserEMConfHPKey newHPKey() {
        ICFSecSecUserEMConfHPKey hpkey =
            new CFSecJpaSecUserEMConfHPKey();
        return( hpkey );
    }

	public CFSecJpaSecUserEMConfHPKey ensureHPKey(ICFSecSecUserEMConfHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaSecUserEMConfHPKey) {
			return( (CFSecJpaSecUserEMConfHPKey)key );
		}
		else {
			CFSecJpaSecUserEMConfHPKey mapped = new CFSecJpaSecUserEMConfHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredSecUserId( key.getRequiredSecUserId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecUserEMConfByUUuid6IdxKey newByUUuid6IdxKey() {
	ICFSecSecUserEMConfByUUuid6IdxKey key =
            new CFSecJpaSecUserEMConfByUUuid6IdxKey();
	return( key );
    }

	public CFSecJpaSecUserEMConfByUUuid6IdxKey ensureByUUuid6IdxKey(ICFSecSecUserEMConfByUUuid6IdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecUserEMConfByUUuid6IdxKey) {
			return( (CFSecJpaSecUserEMConfByUUuid6IdxKey)key );
		}
		else {
			CFSecJpaSecUserEMConfByUUuid6IdxKey mapped = new CFSecJpaSecUserEMConfByUUuid6IdxKey();
			mapped.setRequiredEMConfirmationUuid6( key.getRequiredEMConfirmationUuid6() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecUserEMConfByConfEMAddrIdxKey newByConfEMAddrIdxKey() {
	ICFSecSecUserEMConfByConfEMAddrIdxKey key =
            new CFSecJpaSecUserEMConfByConfEMAddrIdxKey();
	return( key );
    }

	public CFSecJpaSecUserEMConfByConfEMAddrIdxKey ensureByConfEMAddrIdxKey(ICFSecSecUserEMConfByConfEMAddrIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecUserEMConfByConfEMAddrIdxKey) {
			return( (CFSecJpaSecUserEMConfByConfEMAddrIdxKey)key );
		}
		else {
			CFSecJpaSecUserEMConfByConfEMAddrIdxKey mapped = new CFSecJpaSecUserEMConfByConfEMAddrIdxKey();
			mapped.setRequiredConfirmEMailAddr( key.getRequiredConfirmEMailAddr() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecUserEMConfBySentStampIdxKey newBySentStampIdxKey() {
	ICFSecSecUserEMConfBySentStampIdxKey key =
            new CFSecJpaSecUserEMConfBySentStampIdxKey();
	return( key );
    }

	public CFSecJpaSecUserEMConfBySentStampIdxKey ensureBySentStampIdxKey(ICFSecSecUserEMConfBySentStampIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecUserEMConfBySentStampIdxKey) {
			return( (CFSecJpaSecUserEMConfBySentStampIdxKey)key );
		}
		else {
			CFSecJpaSecUserEMConfBySentStampIdxKey mapped = new CFSecJpaSecUserEMConfBySentStampIdxKey();
			mapped.setRequiredEMailSentStamp( key.getRequiredEMailSentStamp() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecUserEMConfByNewAcctIdxKey newByNewAcctIdxKey() {
	ICFSecSecUserEMConfByNewAcctIdxKey key =
            new CFSecJpaSecUserEMConfByNewAcctIdxKey();
	return( key );
    }

	public CFSecJpaSecUserEMConfByNewAcctIdxKey ensureByNewAcctIdxKey(ICFSecSecUserEMConfByNewAcctIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecUserEMConfByNewAcctIdxKey) {
			return( (CFSecJpaSecUserEMConfByNewAcctIdxKey)key );
		}
		else {
			CFSecJpaSecUserEMConfByNewAcctIdxKey mapped = new CFSecJpaSecUserEMConfByNewAcctIdxKey();
			mapped.setRequiredNewAccount( key.getRequiredNewAccount() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecUserEMConf newRec() {
        ICFSecSecUserEMConf rec =
            new CFSecJpaSecUserEMConf();
        return( rec );
    }

	public CFSecJpaSecUserEMConf ensureRec(ICFSecSecUserEMConf rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaSecUserEMConf) {
			return( (CFSecJpaSecUserEMConf)rec );
		}
		else {
			CFSecJpaSecUserEMConf mapped = new CFSecJpaSecUserEMConf();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecSecUserEMConfH newHRec() {
        ICFSecSecUserEMConfH hrec =
            new CFSecJpaSecUserEMConfH();
        return( hrec );
    }

	public CFSecJpaSecUserEMConfH ensureHRec(ICFSecSecUserEMConfH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaSecUserEMConfH) {
			return( (CFSecJpaSecUserEMConfH)hrec );
		}
		else {
			CFSecJpaSecUserEMConfH mapped = new CFSecJpaSecUserEMConfH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

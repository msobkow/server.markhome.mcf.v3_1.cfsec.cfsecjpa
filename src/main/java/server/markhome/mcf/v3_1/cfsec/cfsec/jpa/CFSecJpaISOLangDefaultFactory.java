
// Description: Java 25 JPA Default Factory implementation for ISOLang.

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
 *	CFSecISOLangFactory JPA implementation for ISOLang
 */
public class CFSecJpaISOLangDefaultFactory
    implements ICFSecISOLangFactory
{
    public CFSecJpaISOLangDefaultFactory() {
    }

    @Override
    public ICFSecISOLangHPKey newHPKey() {
        ICFSecISOLangHPKey hpkey =
            new CFSecJpaISOLangHPKey();
        return( hpkey );
    }

	public CFSecJpaISOLangHPKey ensureHPKey(ICFSecISOLangHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaISOLangHPKey) {
			return( (CFSecJpaISOLangHPKey)key );
		}
		else {
			CFSecJpaISOLangHPKey mapped = new CFSecJpaISOLangHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredISOLangId( key.getRequiredISOLangId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOLangByCode3IdxKey newByCode3IdxKey() {
	ICFSecISOLangByCode3IdxKey key =
            new CFSecJpaISOLangByCode3IdxKey();
	return( key );
    }

	public CFSecJpaISOLangByCode3IdxKey ensureByCode3IdxKey(ICFSecISOLangByCode3IdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaISOLangByCode3IdxKey) {
			return( (CFSecJpaISOLangByCode3IdxKey)key );
		}
		else {
			CFSecJpaISOLangByCode3IdxKey mapped = new CFSecJpaISOLangByCode3IdxKey();
			mapped.setRequiredISO6392Code( key.getRequiredISO6392Code() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOLangByCode2IdxKey newByCode2IdxKey() {
	ICFSecISOLangByCode2IdxKey key =
            new CFSecJpaISOLangByCode2IdxKey();
	return( key );
    }

	public CFSecJpaISOLangByCode2IdxKey ensureByCode2IdxKey(ICFSecISOLangByCode2IdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaISOLangByCode2IdxKey) {
			return( (CFSecJpaISOLangByCode2IdxKey)key );
		}
		else {
			CFSecJpaISOLangByCode2IdxKey mapped = new CFSecJpaISOLangByCode2IdxKey();
			mapped.setOptionalISO6391Code( key.getOptionalISO6391Code() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOLang newRec() {
        ICFSecISOLang rec =
            new CFSecJpaISOLang();
        return( rec );
    }

	public CFSecJpaISOLang ensureRec(ICFSecISOLang rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaISOLang) {
			return( (CFSecJpaISOLang)rec );
		}
		else {
			CFSecJpaISOLang mapped = new CFSecJpaISOLang();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecISOLangH newHRec() {
        ICFSecISOLangH hrec =
            new CFSecJpaISOLangH();
        return( hrec );
    }

	public CFSecJpaISOLangH ensureHRec(ICFSecISOLangH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaISOLangH) {
			return( (CFSecJpaISOLangH)hrec );
		}
		else {
			CFSecJpaISOLangH mapped = new CFSecJpaISOLangH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

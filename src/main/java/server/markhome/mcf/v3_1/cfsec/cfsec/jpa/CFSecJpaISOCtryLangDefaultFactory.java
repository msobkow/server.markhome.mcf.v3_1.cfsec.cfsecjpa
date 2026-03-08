
// Description: Java 25 JPA Default Factory implementation for ISOCtryLang.

/*
 *	server.markhome.mcf.CFSec
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal 3.1 CFSec - Security Services
 *	
 *	This file is part of Mark's Code Fractal CFSec.
 *	
 *	Mark's Code Fractal CFSec is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU Library General Public License,
 *	Version 3 or later.
 *	
 *	Mark's Code Fractal CFSec is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU Library General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	Mark's Code Fractal CFSec is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU Library General Public License
 *	along with Mark's Code Fractal CFSec.  If not, see <https://www.gnu.org/licenses/>.
 *	
 *	If you wish to modify and use this code without publishing your changes in order to
 *	tie it to proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
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
 *	CFSecISOCtryLangFactory JPA implementation for ISOCtryLang
 */
public class CFSecJpaISOCtryLangDefaultFactory
    implements ICFSecISOCtryLangFactory
{
    public CFSecJpaISOCtryLangDefaultFactory() {
    }

    @Override
    public ICFSecISOCtryLangPKey newPKey() {
        ICFSecISOCtryLangPKey pkey =
            new CFSecJpaISOCtryLangPKey();
        return( pkey );
    }

	public CFSecJpaISOCtryLangPKey ensurePKey(ICFSecISOCtryLangPKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaISOCtryLangPKey) {
			return( (CFSecJpaISOCtryLangPKey)key );
		}
		else {
			CFSecJpaISOCtryLangPKey mapped = new CFSecJpaISOCtryLangPKey();
			mapped.setRequiredContainerCtry( key.getRequiredISOCtryId() );
			mapped.setRequiredParentLang( key.getRequiredISOLangId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOCtryLangHPKey newHPKey() {
        ICFSecISOCtryLangHPKey hpkey =
            new CFSecJpaISOCtryLangHPKey();
        return( hpkey );
    }

	public CFSecJpaISOCtryLangHPKey ensureHPKey(ICFSecISOCtryLangHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaISOCtryLangHPKey) {
			return( (CFSecJpaISOCtryLangHPKey)key );
		}
		else {
			CFSecJpaISOCtryLangHPKey mapped = new CFSecJpaISOCtryLangHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredISOCtryId( key.getRequiredISOCtryId() );
			mapped.setRequiredISOLangId( key.getRequiredISOLangId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOCtryLangByCtryIdxKey newByCtryIdxKey() {
	ICFSecISOCtryLangByCtryIdxKey key =
            new CFSecJpaISOCtryLangByCtryIdxKey();
	return( key );
    }

	public CFSecJpaISOCtryLangByCtryIdxKey ensureByCtryIdxKey(ICFSecISOCtryLangByCtryIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaISOCtryLangByCtryIdxKey) {
			return( (CFSecJpaISOCtryLangByCtryIdxKey)key );
		}
		else {
			CFSecJpaISOCtryLangByCtryIdxKey mapped = new CFSecJpaISOCtryLangByCtryIdxKey();
			mapped.setRequiredISOCtryId( key.getRequiredISOCtryId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOCtryLangByLangIdxKey newByLangIdxKey() {
	ICFSecISOCtryLangByLangIdxKey key =
            new CFSecJpaISOCtryLangByLangIdxKey();
	return( key );
    }

	public CFSecJpaISOCtryLangByLangIdxKey ensureByLangIdxKey(ICFSecISOCtryLangByLangIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaISOCtryLangByLangIdxKey) {
			return( (CFSecJpaISOCtryLangByLangIdxKey)key );
		}
		else {
			CFSecJpaISOCtryLangByLangIdxKey mapped = new CFSecJpaISOCtryLangByLangIdxKey();
			mapped.setRequiredISOLangId( key.getRequiredISOLangId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecISOCtryLang newRec() {
        ICFSecISOCtryLang rec =
            new CFSecJpaISOCtryLang();
        return( rec );
    }

	public CFSecJpaISOCtryLang ensureRec(ICFSecISOCtryLang rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaISOCtryLang) {
			return( (CFSecJpaISOCtryLang)rec );
		}
		else {
			CFSecJpaISOCtryLang mapped = new CFSecJpaISOCtryLang();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecISOCtryLangH newHRec() {
        ICFSecISOCtryLangH hrec =
            new CFSecJpaISOCtryLangH();
        return( hrec );
    }

	public CFSecJpaISOCtryLangH ensureHRec(ICFSecISOCtryLangH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaISOCtryLangH) {
			return( (CFSecJpaISOCtryLangH)hrec );
		}
		else {
			CFSecJpaISOCtryLangH mapped = new CFSecJpaISOCtryLangH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}


// Description: Java 25 JPA Default Factory implementation for SecUser.

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
    public ICFSecSecUserByEMConfIdxKey newByEMConfIdxKey() {
	ICFSecSecUserByEMConfIdxKey key =
            new CFSecJpaSecUserByEMConfIdxKey();
	return( key );
    }

	public CFSecJpaSecUserByEMConfIdxKey ensureByEMConfIdxKey(ICFSecSecUserByEMConfIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecUserByEMConfIdxKey) {
			return( (CFSecJpaSecUserByEMConfIdxKey)key );
		}
		else {
			CFSecJpaSecUserByEMConfIdxKey mapped = new CFSecJpaSecUserByEMConfIdxKey();
			mapped.setOptionalEMailConfirmUuid6( key.getOptionalEMailConfirmUuid6() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecUserByPwdResetIdxKey newByPwdResetIdxKey() {
	ICFSecSecUserByPwdResetIdxKey key =
            new CFSecJpaSecUserByPwdResetIdxKey();
	return( key );
    }

	public CFSecJpaSecUserByPwdResetIdxKey ensureByPwdResetIdxKey(ICFSecSecUserByPwdResetIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecUserByPwdResetIdxKey) {
			return( (CFSecJpaSecUserByPwdResetIdxKey)key );
		}
		else {
			CFSecJpaSecUserByPwdResetIdxKey mapped = new CFSecJpaSecUserByPwdResetIdxKey();
			mapped.setOptionalPasswordResetUuid6( key.getOptionalPasswordResetUuid6() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecUserByDefDevIdxKey newByDefDevIdxKey() {
	ICFSecSecUserByDefDevIdxKey key =
            new CFSecJpaSecUserByDefDevIdxKey();
	return( key );
    }

	public CFSecJpaSecUserByDefDevIdxKey ensureByDefDevIdxKey(ICFSecSecUserByDefDevIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecUserByDefDevIdxKey) {
			return( (CFSecJpaSecUserByDefDevIdxKey)key );
		}
		else {
			CFSecJpaSecUserByDefDevIdxKey mapped = new CFSecJpaSecUserByDefDevIdxKey();
			mapped.setOptionalDfltDevUserId( key.getOptionalDfltDevUserId() );
			mapped.setOptionalDfltDevName( key.getOptionalDfltDevName() );
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


// Description: Java 25 JPA Default Factory implementation for SecSession.

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
 *	CFSecSecSessionFactory JPA implementation for SecSession
 */
public class CFSecJpaSecSessionDefaultFactory
    implements ICFSecSecSessionFactory
{
    public CFSecJpaSecSessionDefaultFactory() {
    }

    @Override
    public ICFSecSecSessionBySecUserIdxKey newBySecUserIdxKey() {
	ICFSecSecSessionBySecUserIdxKey key =
            new CFSecJpaSecSessionBySecUserIdxKey();
	return( key );
    }

	public CFSecJpaSecSessionBySecUserIdxKey ensureBySecUserIdxKey(ICFSecSecSessionBySecUserIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecSessionBySecUserIdxKey) {
			return( (CFSecJpaSecSessionBySecUserIdxKey)key );
		}
		else {
			CFSecJpaSecSessionBySecUserIdxKey mapped = new CFSecJpaSecSessionBySecUserIdxKey();
			mapped.setRequiredSecUserId( key.getRequiredSecUserId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecSessionBySecDevIdxKey newBySecDevIdxKey() {
	ICFSecSecSessionBySecDevIdxKey key =
            new CFSecJpaSecSessionBySecDevIdxKey();
	return( key );
    }

	public CFSecJpaSecSessionBySecDevIdxKey ensureBySecDevIdxKey(ICFSecSecSessionBySecDevIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecSessionBySecDevIdxKey) {
			return( (CFSecJpaSecSessionBySecDevIdxKey)key );
		}
		else {
			CFSecJpaSecSessionBySecDevIdxKey mapped = new CFSecJpaSecSessionBySecDevIdxKey();
			mapped.setRequiredSecUserId( key.getRequiredSecUserId() );
			mapped.setOptionalSecDevName( key.getOptionalSecDevName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecSessionByStartIdxKey newByStartIdxKey() {
	ICFSecSecSessionByStartIdxKey key =
            new CFSecJpaSecSessionByStartIdxKey();
	return( key );
    }

	public CFSecJpaSecSessionByStartIdxKey ensureByStartIdxKey(ICFSecSecSessionByStartIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecSessionByStartIdxKey) {
			return( (CFSecJpaSecSessionByStartIdxKey)key );
		}
		else {
			CFSecJpaSecSessionByStartIdxKey mapped = new CFSecJpaSecSessionByStartIdxKey();
			mapped.setRequiredSecUserId( key.getRequiredSecUserId() );
			mapped.setRequiredStart( key.getRequiredStart() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecSessionByFinishIdxKey newByFinishIdxKey() {
	ICFSecSecSessionByFinishIdxKey key =
            new CFSecJpaSecSessionByFinishIdxKey();
	return( key );
    }

	public CFSecJpaSecSessionByFinishIdxKey ensureByFinishIdxKey(ICFSecSecSessionByFinishIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecSessionByFinishIdxKey) {
			return( (CFSecJpaSecSessionByFinishIdxKey)key );
		}
		else {
			CFSecJpaSecSessionByFinishIdxKey mapped = new CFSecJpaSecSessionByFinishIdxKey();
			mapped.setRequiredSecUserId( key.getRequiredSecUserId() );
			mapped.setOptionalFinish( key.getOptionalFinish() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecSessionBySecProxyIdxKey newBySecProxyIdxKey() {
	ICFSecSecSessionBySecProxyIdxKey key =
            new CFSecJpaSecSessionBySecProxyIdxKey();
	return( key );
    }

	public CFSecJpaSecSessionBySecProxyIdxKey ensureBySecProxyIdxKey(ICFSecSecSessionBySecProxyIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecSessionBySecProxyIdxKey) {
			return( (CFSecJpaSecSessionBySecProxyIdxKey)key );
		}
		else {
			CFSecJpaSecSessionBySecProxyIdxKey mapped = new CFSecJpaSecSessionBySecProxyIdxKey();
			mapped.setOptionalSecProxyId( key.getOptionalSecProxyId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecSession newRec() {
        ICFSecSecSession rec =
            new CFSecJpaSecSession();
        return( rec );
    }

	public CFSecJpaSecSession ensureRec(ICFSecSecSession rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaSecSession) {
			return( (CFSecJpaSecSession)rec );
		}
		else {
			CFSecJpaSecSession mapped = new CFSecJpaSecSession();
			mapped.set(rec);
			return( mapped );
		}
	}
}

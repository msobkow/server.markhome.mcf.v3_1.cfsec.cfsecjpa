
// Description: Java 25 JPA Default Factory implementation for SecSession.

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


// Description: Java 25 JPA Default Factory implementation for HostNode.

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
 *	CFSecHostNodeFactory JPA implementation for HostNode
 */
public class CFSecJpaHostNodeDefaultFactory
    implements ICFSecHostNodeFactory
{
    public CFSecJpaHostNodeDefaultFactory() {
    }

    @Override
    public ICFSecHostNodeHPKey newHPKey() {
        ICFSecHostNodeHPKey hpkey =
            new CFSecJpaHostNodeHPKey();
        return( hpkey );
    }

	public CFSecJpaHostNodeHPKey ensureHPKey(ICFSecHostNodeHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaHostNodeHPKey) {
			return( (CFSecJpaHostNodeHPKey)key );
		}
		else {
			CFSecJpaHostNodeHPKey mapped = new CFSecJpaHostNodeHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredHostNodeId( key.getRequiredHostNodeId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecHostNodeByClusterIdxKey newByClusterIdxKey() {
	ICFSecHostNodeByClusterIdxKey key =
            new CFSecJpaHostNodeByClusterIdxKey();
	return( key );
    }

	public CFSecJpaHostNodeByClusterIdxKey ensureByClusterIdxKey(ICFSecHostNodeByClusterIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaHostNodeByClusterIdxKey) {
			return( (CFSecJpaHostNodeByClusterIdxKey)key );
		}
		else {
			CFSecJpaHostNodeByClusterIdxKey mapped = new CFSecJpaHostNodeByClusterIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecHostNodeByUDescrIdxKey newByUDescrIdxKey() {
	ICFSecHostNodeByUDescrIdxKey key =
            new CFSecJpaHostNodeByUDescrIdxKey();
	return( key );
    }

	public CFSecJpaHostNodeByUDescrIdxKey ensureByUDescrIdxKey(ICFSecHostNodeByUDescrIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaHostNodeByUDescrIdxKey) {
			return( (CFSecJpaHostNodeByUDescrIdxKey)key );
		}
		else {
			CFSecJpaHostNodeByUDescrIdxKey mapped = new CFSecJpaHostNodeByUDescrIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			mapped.setRequiredDescription( key.getRequiredDescription() );
			return( mapped );
		}
	}

    @Override
    public ICFSecHostNodeByHostNameIdxKey newByHostNameIdxKey() {
	ICFSecHostNodeByHostNameIdxKey key =
            new CFSecJpaHostNodeByHostNameIdxKey();
	return( key );
    }

	public CFSecJpaHostNodeByHostNameIdxKey ensureByHostNameIdxKey(ICFSecHostNodeByHostNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaHostNodeByHostNameIdxKey) {
			return( (CFSecJpaHostNodeByHostNameIdxKey)key );
		}
		else {
			CFSecJpaHostNodeByHostNameIdxKey mapped = new CFSecJpaHostNodeByHostNameIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			mapped.setRequiredHostName( key.getRequiredHostName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecHostNode newRec() {
        ICFSecHostNode rec =
            new CFSecJpaHostNode();
        return( rec );
    }

	public CFSecJpaHostNode ensureRec(ICFSecHostNode rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaHostNode) {
			return( (CFSecJpaHostNode)rec );
		}
		else {
			CFSecJpaHostNode mapped = new CFSecJpaHostNode();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecHostNodeH newHRec() {
        ICFSecHostNodeH hrec =
            new CFSecJpaHostNodeH();
        return( hrec );
    }

	public CFSecJpaHostNodeH ensureHRec(ICFSecHostNodeH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaHostNodeH) {
			return( (CFSecJpaHostNodeH)hrec );
		}
		else {
			CFSecJpaHostNodeH mapped = new CFSecJpaHostNodeH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

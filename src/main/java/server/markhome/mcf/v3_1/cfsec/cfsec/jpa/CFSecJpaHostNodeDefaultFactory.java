
// Description: Java 25 JPA Default Factory implementation for HostNode.

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

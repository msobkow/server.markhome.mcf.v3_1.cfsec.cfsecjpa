
// Description: Java 25 JPA Default Factory implementation for Cluster.

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
 *	CFSecClusterFactory JPA implementation for Cluster
 */
public class CFSecJpaClusterDefaultFactory
    implements ICFSecClusterFactory
{
    public CFSecJpaClusterDefaultFactory() {
    }

    @Override
    public ICFSecClusterHPKey newHPKey() {
        ICFSecClusterHPKey hpkey =
            new CFSecJpaClusterHPKey();
        return( hpkey );
    }

	public CFSecJpaClusterHPKey ensureHPKey(ICFSecClusterHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaClusterHPKey) {
			return( (CFSecJpaClusterHPKey)key );
		}
		else {
			CFSecJpaClusterHPKey mapped = new CFSecJpaClusterHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredId( key.getRequiredId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecClusterByUDomNameIdxKey newByUDomNameIdxKey() {
	ICFSecClusterByUDomNameIdxKey key =
            new CFSecJpaClusterByUDomNameIdxKey();
	return( key );
    }

	public CFSecJpaClusterByUDomNameIdxKey ensureByUDomNameIdxKey(ICFSecClusterByUDomNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaClusterByUDomNameIdxKey) {
			return( (CFSecJpaClusterByUDomNameIdxKey)key );
		}
		else {
			CFSecJpaClusterByUDomNameIdxKey mapped = new CFSecJpaClusterByUDomNameIdxKey();
			mapped.setRequiredFullDomName( key.getRequiredFullDomName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecClusterByUDescrIdxKey newByUDescrIdxKey() {
	ICFSecClusterByUDescrIdxKey key =
            new CFSecJpaClusterByUDescrIdxKey();
	return( key );
    }

	public CFSecJpaClusterByUDescrIdxKey ensureByUDescrIdxKey(ICFSecClusterByUDescrIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaClusterByUDescrIdxKey) {
			return( (CFSecJpaClusterByUDescrIdxKey)key );
		}
		else {
			CFSecJpaClusterByUDescrIdxKey mapped = new CFSecJpaClusterByUDescrIdxKey();
			mapped.setRequiredDescription( key.getRequiredDescription() );
			return( mapped );
		}
	}

    @Override
    public ICFSecCluster newRec() {
        ICFSecCluster rec =
            new CFSecJpaCluster();
        return( rec );
    }

	public CFSecJpaCluster ensureRec(ICFSecCluster rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaCluster) {
			return( (CFSecJpaCluster)rec );
		}
		else {
			CFSecJpaCluster mapped = new CFSecJpaCluster();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecClusterH newHRec() {
        ICFSecClusterH hrec =
            new CFSecJpaClusterH();
        return( hrec );
    }

	public CFSecJpaClusterH ensureHRec(ICFSecClusterH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaClusterH) {
			return( (CFSecJpaClusterH)hrec );
		}
		else {
			CFSecJpaClusterH mapped = new CFSecJpaClusterH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}

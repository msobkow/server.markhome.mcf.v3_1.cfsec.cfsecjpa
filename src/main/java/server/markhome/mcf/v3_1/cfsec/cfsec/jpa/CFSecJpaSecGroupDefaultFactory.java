
// Description: Java 25 JPA Default Factory implementation for SecGroup.

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
 *	CFSecSecGroupFactory JPA implementation for SecGroup
 */
public class CFSecJpaSecGroupDefaultFactory
    implements ICFSecSecGroupFactory
{
    public CFSecJpaSecGroupDefaultFactory() {
    }

    @Override
    public ICFSecSecGroupHPKey newHPKey() {
        ICFSecSecGroupHPKey hpkey =
            new CFSecJpaSecGroupHPKey();
        return( hpkey );
    }

	public CFSecJpaSecGroupHPKey ensureHPKey(ICFSecSecGroupHPKey key) {
		if (key == null) {
			return( null );
		}
		else if( key instanceof CFSecJpaSecGroupHPKey) {
			return( (CFSecJpaSecGroupHPKey)key );
		}
		else {
			CFSecJpaSecGroupHPKey mapped = new CFSecJpaSecGroupHPKey();
			mapped.setAuditClusterId(key.getAuditClusterId());
			mapped.setAuditActionId(key.getAuditActionId());
			mapped.setAuditSessionId(key.getAuditSessionId());
			mapped.setAuditStamp(key.getAuditStamp());
			mapped.setRequiredSecGroupId( key.getRequiredSecGroupId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGroupByClusterIdxKey newByClusterIdxKey() {
	ICFSecSecGroupByClusterIdxKey key =
            new CFSecJpaSecGroupByClusterIdxKey();
	return( key );
    }

	public CFSecJpaSecGroupByClusterIdxKey ensureByClusterIdxKey(ICFSecSecGroupByClusterIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecGroupByClusterIdxKey) {
			return( (CFSecJpaSecGroupByClusterIdxKey)key );
		}
		else {
			CFSecJpaSecGroupByClusterIdxKey mapped = new CFSecJpaSecGroupByClusterIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGroupByClusterVisIdxKey newByClusterVisIdxKey() {
	ICFSecSecGroupByClusterVisIdxKey key =
            new CFSecJpaSecGroupByClusterVisIdxKey();
	return( key );
    }

	public CFSecJpaSecGroupByClusterVisIdxKey ensureByClusterVisIdxKey(ICFSecSecGroupByClusterVisIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecGroupByClusterVisIdxKey) {
			return( (CFSecJpaSecGroupByClusterVisIdxKey)key );
		}
		else {
			CFSecJpaSecGroupByClusterVisIdxKey mapped = new CFSecJpaSecGroupByClusterVisIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			mapped.setRequiredIsVisible( key.getRequiredIsVisible() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGroupByUNameIdxKey newByUNameIdxKey() {
	ICFSecSecGroupByUNameIdxKey key =
            new CFSecJpaSecGroupByUNameIdxKey();
	return( key );
    }

	public CFSecJpaSecGroupByUNameIdxKey ensureByUNameIdxKey(ICFSecSecGroupByUNameIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecGroupByUNameIdxKey) {
			return( (CFSecJpaSecGroupByUNameIdxKey)key );
		}
		else {
			CFSecJpaSecGroupByUNameIdxKey mapped = new CFSecJpaSecGroupByUNameIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			mapped.setRequiredName( key.getRequiredName() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGroup newRec() {
        ICFSecSecGroup rec =
            new CFSecJpaSecGroup();
        return( rec );
    }

	public CFSecJpaSecGroup ensureRec(ICFSecSecGroup rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaSecGroup) {
			return( (CFSecJpaSecGroup)rec );
		}
		else {
			CFSecJpaSecGroup mapped = new CFSecJpaSecGroup();
			mapped.set(rec);
			return( mapped );
		}
	}

    @Override
    public ICFSecSecGroupH newHRec() {
        ICFSecSecGroupH hrec =
            new CFSecJpaSecGroupH();
        return( hrec );
    }

	public CFSecJpaSecGroupH ensureHRec(ICFSecSecGroupH hrec) {
		if (hrec == null) {
			return( null );
		}
		else if( hrec instanceof CFSecJpaSecGroupH) {
			return( (CFSecJpaSecGroupH)hrec );
		}
		else {
			CFSecJpaSecGroupH mapped = new CFSecJpaSecGroupH();
			mapped.set(hrec);
			return( mapped );
		}
	}
}


// Description: Java 25 JPA Default Factory implementation for SysCluster.

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
 *	CFSecSysClusterFactory JPA implementation for SysCluster
 */
public class CFSecJpaSysClusterDefaultFactory
    implements ICFSecSysClusterFactory
{
    public CFSecJpaSysClusterDefaultFactory() {
    }

    @Override
    public ICFSecSysClusterByClusterIdxKey newByClusterIdxKey() {
	ICFSecSysClusterByClusterIdxKey key =
            new CFSecJpaSysClusterByClusterIdxKey();
	return( key );
    }

	public CFSecJpaSysClusterByClusterIdxKey ensureByClusterIdxKey(ICFSecSysClusterByClusterIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSysClusterByClusterIdxKey) {
			return( (CFSecJpaSysClusterByClusterIdxKey)key );
		}
		else {
			CFSecJpaSysClusterByClusterIdxKey mapped = new CFSecJpaSysClusterByClusterIdxKey();
			mapped.setRequiredClusterId( key.getRequiredClusterId() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSysCluster newRec() {
        ICFSecSysCluster rec =
            new CFSecJpaSysCluster();
        return( rec );
    }

	public CFSecJpaSysCluster ensureRec(ICFSecSysCluster rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaSysCluster) {
			return( (CFSecJpaSysCluster)rec );
		}
		else {
			CFSecJpaSysCluster mapped = new CFSecJpaSysCluster();
			mapped.set(rec);
			return( mapped );
		}
	}
}

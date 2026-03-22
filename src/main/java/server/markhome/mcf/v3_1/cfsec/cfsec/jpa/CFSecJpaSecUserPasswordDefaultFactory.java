
// Description: Java 25 JPA Default Factory implementation for SecUserPassword.

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
 *	CFSecSecUserPasswordFactory JPA implementation for SecUserPassword
 */
public class CFSecJpaSecUserPasswordDefaultFactory
    implements ICFSecSecUserPasswordFactory
{
    public CFSecJpaSecUserPasswordDefaultFactory() {
    }

    @Override
    public ICFSecSecUserPasswordBySetStampIdxKey newBySetStampIdxKey() {
	ICFSecSecUserPasswordBySetStampIdxKey key =
            new CFSecJpaSecUserPasswordBySetStampIdxKey();
	return( key );
    }

	public CFSecJpaSecUserPasswordBySetStampIdxKey ensureBySetStampIdxKey(ICFSecSecUserPasswordBySetStampIdxKey key) {
		if (key == null) {
			return( null );
		}
		else if (key instanceof CFSecJpaSecUserPasswordBySetStampIdxKey) {
			return( (CFSecJpaSecUserPasswordBySetStampIdxKey)key );
		}
		else {
			CFSecJpaSecUserPasswordBySetStampIdxKey mapped = new CFSecJpaSecUserPasswordBySetStampIdxKey();
			mapped.setRequiredPWSetStamp( key.getRequiredPWSetStamp() );
			return( mapped );
		}
	}

    @Override
    public ICFSecSecUserPassword newRec() {
        ICFSecSecUserPassword rec =
            new CFSecJpaSecUserPassword();
        return( rec );
    }

	public CFSecJpaSecUserPassword ensureRec(ICFSecSecUserPassword rec) {
		if( rec == null ) {
			return( null );
		}
		else if (rec instanceof CFSecJpaSecUserPassword) {
			return( (CFSecJpaSecUserPassword)rec );
		}
		else {
			CFSecJpaSecUserPassword mapped = new CFSecJpaSecUserPassword();
			mapped.set(rec);
			return( mapped );
		}
	}
}

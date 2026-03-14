// Description: Java 25 JPA implementation of a SecSession by StartIdx index key object.

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

import java.io.Serializable;
import java.math.*;
import java.time.*;
import java.util.*;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.xml.CFLibXmlUtil;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;

public class CFSecJpaSecSessionByStartIdxKey
	implements ICFSecSecSessionByStartIdxKey, Comparable<Object>, Serializable
{
	protected CFLibDbKeyHash256 requiredSecUserId;
	protected LocalDateTime requiredStart;
	public CFSecJpaSecSessionByStartIdxKey() {
		requiredSecUserId = CFLibDbKeyHash256.fromHex( ICFSecSecSession.SECUSERID_INIT_VALUE.toString() );
		requiredStart = CFLibXmlUtil.parseTimestamp("2020-01-01T00:00:00");
	}

	@Override
	public CFLibDbKeyHash256 getRequiredSecUserId() {
		return( requiredSecUserId );
	}

	@Override
	public void setRequiredSecUserId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredSecUserId",
				1,
				"value" );
		}
		requiredSecUserId = value;
	}

	@Override
	public LocalDateTime getRequiredStart() {
		return( requiredStart );
	}

	@Override
	public void setRequiredStart( LocalDateTime value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredStart",
				1,
				"value" );
		}
		requiredStart = value;
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecSecSessionByStartIdxKey) {
			ICFSecSecSessionByStartIdxKey rhs = (ICFSecSecSessionByStartIdxKey)obj;
			if( getRequiredSecUserId() != null ) {
				if( rhs.getRequiredSecUserId() != null ) {
					if( ! getRequiredSecUserId().equals( rhs.getRequiredSecUserId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecUserId() != null ) {
					return( false );
				}
			}
			if( getRequiredStart() != null ) {
				if( rhs.getRequiredStart() != null ) {
					if( ! getRequiredStart().equals( rhs.getRequiredStart() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredStart() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecSession) {
			ICFSecSecSession rhs = (ICFSecSecSession)obj;
			if( getRequiredSecUserId() != null ) {
				if( rhs.getRequiredSecUserId() != null ) {
					if( ! getRequiredSecUserId().equals( rhs.getRequiredSecUserId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecUserId() != null ) {
					return( false );
				}
			}
			if( getRequiredStart() != null ) {
				if( rhs.getRequiredStart() != null ) {
					if( ! getRequiredStart().equals( rhs.getRequiredStart() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredStart() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else {
			return( false );
		}
	}

	@Override
	public int hashCode() {
		int hashCode = 0;
		hashCode = hashCode + getRequiredSecUserId().hashCode();
		if( getRequiredStart() != null ) {
			hashCode = hashCode + getRequiredStart().hashCode();
		}
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFSecSecSessionByStartIdxKey) {
			ICFSecSecSessionByStartIdxKey rhs = (ICFSecSecSessionByStartIdxKey)obj;
			if (getRequiredSecUserId() != null) {
				if (rhs.getRequiredSecUserId() != null) {
					cmp = getRequiredSecUserId().compareTo( rhs.getRequiredSecUserId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecUserId() != null) {
				return( -1 );
			}
			if (getRequiredStart() != null) {
				if (rhs.getRequiredStart() != null) {
					cmp = getRequiredStart().compareTo( rhs.getRequiredStart() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredStart() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecSecSession) {
			ICFSecSecSession rhs = (ICFSecSecSession)obj;
			if (getRequiredSecUserId() != null) {
				if (rhs.getRequiredSecUserId() != null) {
					cmp = getRequiredSecUserId().compareTo( rhs.getRequiredSecUserId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecUserId() != null) {
				return( -1 );
			}
			if (getRequiredStart() != null) {
				if (rhs.getRequiredStart() != null) {
					cmp = getRequiredStart().compareTo( rhs.getRequiredStart() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredStart() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(),
				"compareTo",
				"obj",
				obj,
				"ICFSecSecSessionByStartIdxKey, ICFSecSecSession");
		}
	}

	public String getXmlAttrFragment() {
		String ret = "" 
			+ " RequiredSecUserId=" + "\"" + getRequiredSecUserId().toString() + "\""
			+ " RequiredStart=" + "\"" + getRequiredStart().toString() + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecSecSessionByStartIdxKey" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

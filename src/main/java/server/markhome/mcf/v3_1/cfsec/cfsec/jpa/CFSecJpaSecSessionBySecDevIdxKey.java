// Description: Java 25 JPA implementation of a SecSession by SecDevIdx index key object.

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

public class CFSecJpaSecSessionBySecDevIdxKey
	implements ICFSecSecSessionBySecDevIdxKey, Comparable<Object>, Serializable
{
	protected CFLibDbKeyHash256 requiredSecUserId;
	protected String optionalSecDevName;
	public CFSecJpaSecSessionBySecDevIdxKey() {
		requiredSecUserId = CFLibDbKeyHash256.fromHex( ICFSecSecSession.SECUSERID_INIT_VALUE.toString() );
		optionalSecDevName = null;
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
	public String getOptionalSecDevName() {
		return( optionalSecDevName );
	}

	@Override
	public void setOptionalSecDevName( String value ) {
		if( value != null && value.length() > 127 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setOptionalSecDevName",
				1,
				"value.length()",
				value.length(),
				127 );
		}
		optionalSecDevName = value;
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecSecSessionBySecDevIdxKey) {
			ICFSecSecSessionBySecDevIdxKey rhs = (ICFSecSecSessionBySecDevIdxKey)obj;
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
			if( getOptionalSecDevName() != null ) {
				if( rhs.getOptionalSecDevName() != null ) {
					if( ! getOptionalSecDevName().equals( rhs.getOptionalSecDevName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalSecDevName() != null ) {
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
			if( getOptionalSecDevName() != null ) {
				if( rhs.getOptionalSecDevName() != null ) {
					if( ! getOptionalSecDevName().equals( rhs.getOptionalSecDevName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalSecDevName() != null ) {
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
		if( getOptionalSecDevName() != null ) {
			hashCode = hashCode + getOptionalSecDevName().hashCode();
		}
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFSecSecSessionBySecDevIdxKey) {
			ICFSecSecSessionBySecDevIdxKey rhs = (ICFSecSecSessionBySecDevIdxKey)obj;
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
			if( getOptionalSecDevName() != null ) {
				if( rhs.getOptionalSecDevName() != null ) {
					cmp = getOptionalSecDevName().compareTo( rhs.getOptionalSecDevName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalSecDevName() != null ) {
					return( -1 );
				}
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
			if( getOptionalSecDevName() != null ) {
				if( rhs.getOptionalSecDevName() != null ) {
					cmp = getOptionalSecDevName().compareTo( rhs.getOptionalSecDevName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalSecDevName() != null ) {
					return( -1 );
				}
			}
			return( 0 );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(),
				"compareTo",
				"obj",
				obj,
				"ICFSecSecSessionBySecDevIdxKey, ICFSecSecSession");
		}
	}

	public String getXmlAttrFragment() {
		String ret = "" 
			+ " RequiredSecUserId=" + "\"" + getRequiredSecUserId().toString() + "\""
			+ " OptionalSecDevName=" + ( ( getOptionalSecDevName() == null ) ? "null" : "\"" + StringEscapeUtils.escapeXml11( getOptionalSecDevName() ) + "\"" );
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecSecSessionBySecDevIdxKey" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

// Description: Java 25 JPA implementation of a ISOTZone by Iso8601Idx index key object.

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

public class CFSecJpaISOTZoneByIso8601IdxKey
	implements ICFSecISOTZoneByIso8601IdxKey, Comparable<Object>, Serializable
{
	protected String requiredIso8601;
	public CFSecJpaISOTZoneByIso8601IdxKey() {
		requiredIso8601 = ICFSecISOTZone.ISO8601_INIT_VALUE;
	}

	@Override
	public String getRequiredIso8601() {
		return( requiredIso8601 );
	}

	@Override
	public void setRequiredIso8601( String value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredIso8601",
				1,
				"value" );
		}
		else if( value.length() > 6 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredIso8601",
				1,
				"value.length()",
				value.length(),
				6 );
		}
		requiredIso8601 = value;
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecISOTZoneByIso8601IdxKey) {
			ICFSecISOTZoneByIso8601IdxKey rhs = (ICFSecISOTZoneByIso8601IdxKey)obj;
			if( getRequiredIso8601() != null ) {
				if( rhs.getRequiredIso8601() != null ) {
					if( ! getRequiredIso8601().equals( rhs.getRequiredIso8601() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredIso8601() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecISOTZone) {
			ICFSecISOTZone rhs = (ICFSecISOTZone)obj;
			if( getRequiredIso8601() != null ) {
				if( rhs.getRequiredIso8601() != null ) {
					if( ! getRequiredIso8601().equals( rhs.getRequiredIso8601() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredIso8601() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecISOTZoneH) {
			ICFSecISOTZoneH rhs = (ICFSecISOTZoneH)obj;
			if( getRequiredIso8601() != null ) {
				if( rhs.getRequiredIso8601() != null ) {
					if( ! getRequiredIso8601().equals( rhs.getRequiredIso8601() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredIso8601() != null ) {
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
		if( getRequiredIso8601() != null ) {
			hashCode = hashCode + getRequiredIso8601().hashCode();
		}
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFSecISOTZoneByIso8601IdxKey) {
			ICFSecISOTZoneByIso8601IdxKey rhs = (ICFSecISOTZoneByIso8601IdxKey)obj;
			if (getRequiredIso8601() != null) {
				if (rhs.getRequiredIso8601() != null) {
					cmp = getRequiredIso8601().compareTo( rhs.getRequiredIso8601() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredIso8601() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecISOTZone) {
			ICFSecISOTZone rhs = (ICFSecISOTZone)obj;
			if (getRequiredIso8601() != null) {
				if (rhs.getRequiredIso8601() != null) {
					cmp = getRequiredIso8601().compareTo( rhs.getRequiredIso8601() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredIso8601() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecISOTZoneH) {
			ICFSecISOTZoneH rhs = (ICFSecISOTZoneH)obj;
			if (getRequiredIso8601() != null) {
				if (rhs.getRequiredIso8601() != null) {
					cmp = getRequiredIso8601().compareTo( rhs.getRequiredIso8601() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredIso8601() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(),
				"compareTo",
				"obj",
				obj,
				"ICFSecISOTZoneByIso8601IdxKey, ICFSecISOTZone, ICFSecISOTZoneH");
		}
	}

	public String getXmlAttrFragment() {
		String ret = "" 
			+ " RequiredIso8601=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredIso8601() ) + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecISOTZoneByIso8601IdxKey" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

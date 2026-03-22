// Description: Java 25 JPA implementation of a SecTentGrpInc by NameIdx index key object.

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

public class CFSecJpaSecTentGrpIncByNameIdxKey
	implements ICFSecSecTentGrpIncByNameIdxKey, Comparable<Object>, Serializable
{
	protected String requiredIncName;
	public CFSecJpaSecTentGrpIncByNameIdxKey() {
		requiredIncName = ICFSecSecTentGrpInc.INCNAME_INIT_VALUE;
	}

	@Override
	public String getRequiredIncName() {
		return( requiredIncName );
	}

	@Override
	public void setRequiredIncName( String value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredIncName",
				1,
				"value" );
		}
		else if( value.length() > 64 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredIncName",
				1,
				"value.length()",
				value.length(),
				64 );
		}
		requiredIncName = value;
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecSecTentGrpIncByNameIdxKey) {
			ICFSecSecTentGrpIncByNameIdxKey rhs = (ICFSecSecTentGrpIncByNameIdxKey)obj;
			if( getRequiredIncName() != null ) {
				if( rhs.getRequiredIncName() != null ) {
					if( ! getRequiredIncName().equals( rhs.getRequiredIncName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredIncName() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecTentGrpInc) {
			ICFSecSecTentGrpInc rhs = (ICFSecSecTentGrpInc)obj;
			if( getRequiredIncName() != null ) {
				if( rhs.getRequiredIncName() != null ) {
					if( ! getRequiredIncName().equals( rhs.getRequiredIncName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredIncName() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecTentGrpIncH) {
			ICFSecSecTentGrpIncH rhs = (ICFSecSecTentGrpIncH)obj;
			if( getRequiredIncName() != null ) {
				if( rhs.getRequiredIncName() != null ) {
					if( ! getRequiredIncName().equals( rhs.getRequiredIncName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredIncName() != null ) {
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
		if( getRequiredIncName() != null ) {
			hashCode = hashCode + getRequiredIncName().hashCode();
		}
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFSecSecTentGrpIncByNameIdxKey) {
			ICFSecSecTentGrpIncByNameIdxKey rhs = (ICFSecSecTentGrpIncByNameIdxKey)obj;
			if (getRequiredIncName() != null) {
				if (rhs.getRequiredIncName() != null) {
					cmp = getRequiredIncName().compareTo( rhs.getRequiredIncName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredIncName() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecSecTentGrpInc) {
			ICFSecSecTentGrpInc rhs = (ICFSecSecTentGrpInc)obj;
			if (getRequiredIncName() != null) {
				if (rhs.getRequiredIncName() != null) {
					cmp = getRequiredIncName().compareTo( rhs.getRequiredIncName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredIncName() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecSecTentGrpIncH) {
			ICFSecSecTentGrpIncH rhs = (ICFSecSecTentGrpIncH)obj;
			if (getRequiredIncName() != null) {
				if (rhs.getRequiredIncName() != null) {
					cmp = getRequiredIncName().compareTo( rhs.getRequiredIncName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredIncName() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(),
				"compareTo",
				"obj",
				obj,
				"ICFSecSecTentGrpIncByNameIdxKey, ICFSecSecTentGrpInc, ICFSecSecTentGrpIncH");
		}
	}

	public String getXmlAttrFragment() {
		String ret = "" 
			+ " RequiredIncName=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredIncName() ) + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecSecTentGrpIncByNameIdxKey" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

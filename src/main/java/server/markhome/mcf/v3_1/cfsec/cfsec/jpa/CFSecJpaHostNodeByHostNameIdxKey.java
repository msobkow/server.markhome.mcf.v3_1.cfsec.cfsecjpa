// Description: Java 25 JPA implementation of a HostNode by HostNameIdx index key object.

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

public class CFSecJpaHostNodeByHostNameIdxKey
	implements ICFSecHostNodeByHostNameIdxKey, Comparable<Object>, Serializable
{
	protected CFLibDbKeyHash256 requiredClusterId;
	protected String requiredHostName;
	public CFSecJpaHostNodeByHostNameIdxKey() {
		requiredClusterId = CFLibDbKeyHash256.fromHex( ICFSecHostNode.CLUSTERID_INIT_VALUE.toString() );
		requiredHostName = ICFSecHostNode.HOSTNAME_INIT_VALUE;
	}

	@Override
	public CFLibDbKeyHash256 getRequiredClusterId() {
		return( requiredClusterId );
	}

	@Override
	public void setRequiredClusterId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredClusterId",
				1,
				"value" );
		}
		requiredClusterId = value;
	}

	@Override
	public String getRequiredHostName() {
		return( requiredHostName );
	}

	@Override
	public void setRequiredHostName( String value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredHostName",
				1,
				"value" );
		}
		else if( value.length() > 192 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredHostName",
				1,
				"value.length()",
				value.length(),
				192 );
		}
		requiredHostName = value;
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecHostNodeByHostNameIdxKey) {
			ICFSecHostNodeByHostNameIdxKey rhs = (ICFSecHostNodeByHostNameIdxKey)obj;
			if( getRequiredClusterId() != null ) {
				if( rhs.getRequiredClusterId() != null ) {
					if( ! getRequiredClusterId().equals( rhs.getRequiredClusterId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredClusterId() != null ) {
					return( false );
				}
			}
			if( getRequiredHostName() != null ) {
				if( rhs.getRequiredHostName() != null ) {
					if( ! getRequiredHostName().equals( rhs.getRequiredHostName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredHostName() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecHostNode) {
			ICFSecHostNode rhs = (ICFSecHostNode)obj;
			if( getRequiredClusterId() != null ) {
				if( rhs.getRequiredClusterId() != null ) {
					if( ! getRequiredClusterId().equals( rhs.getRequiredClusterId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredClusterId() != null ) {
					return( false );
				}
			}
			if( getRequiredHostName() != null ) {
				if( rhs.getRequiredHostName() != null ) {
					if( ! getRequiredHostName().equals( rhs.getRequiredHostName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredHostName() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecHostNodeH) {
			ICFSecHostNodeH rhs = (ICFSecHostNodeH)obj;
			if( getRequiredClusterId() != null ) {
				if( rhs.getRequiredClusterId() != null ) {
					if( ! getRequiredClusterId().equals( rhs.getRequiredClusterId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredClusterId() != null ) {
					return( false );
				}
			}
			if( getRequiredHostName() != null ) {
				if( rhs.getRequiredHostName() != null ) {
					if( ! getRequiredHostName().equals( rhs.getRequiredHostName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredHostName() != null ) {
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
		hashCode = hashCode + getRequiredClusterId().hashCode();
		if( getRequiredHostName() != null ) {
			hashCode = hashCode + getRequiredHostName().hashCode();
		}
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFSecHostNodeByHostNameIdxKey) {
			ICFSecHostNodeByHostNameIdxKey rhs = (ICFSecHostNodeByHostNameIdxKey)obj;
			if (getRequiredClusterId() != null) {
				if (rhs.getRequiredClusterId() != null) {
					cmp = getRequiredClusterId().compareTo( rhs.getRequiredClusterId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredClusterId() != null) {
				return( -1 );
			}
			if (getRequiredHostName() != null) {
				if (rhs.getRequiredHostName() != null) {
					cmp = getRequiredHostName().compareTo( rhs.getRequiredHostName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredHostName() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecHostNode) {
			ICFSecHostNode rhs = (ICFSecHostNode)obj;
			if (getRequiredClusterId() != null) {
				if (rhs.getRequiredClusterId() != null) {
					cmp = getRequiredClusterId().compareTo( rhs.getRequiredClusterId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredClusterId() != null) {
				return( -1 );
			}
			if (getRequiredHostName() != null) {
				if (rhs.getRequiredHostName() != null) {
					cmp = getRequiredHostName().compareTo( rhs.getRequiredHostName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredHostName() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecHostNodeH) {
			ICFSecHostNodeH rhs = (ICFSecHostNodeH)obj;
			if (getRequiredClusterId() != null) {
				if (rhs.getRequiredClusterId() != null) {
					cmp = getRequiredClusterId().compareTo( rhs.getRequiredClusterId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredClusterId() != null) {
				return( -1 );
			}
			if (getRequiredHostName() != null) {
				if (rhs.getRequiredHostName() != null) {
					cmp = getRequiredHostName().compareTo( rhs.getRequiredHostName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredHostName() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(),
				"compareTo",
				"obj",
				obj,
				"ICFSecHostNodeByHostNameIdxKey, ICFSecHostNode, ICFSecHostNodeH");
		}
	}

	public String getXmlAttrFragment() {
		String ret = "" 
			+ " RequiredClusterId=" + "\"" + getRequiredClusterId().toString() + "\""
			+ " RequiredHostName=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredHostName() ) + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecHostNodeByHostNameIdxKey" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

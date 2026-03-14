// Description: Java 25 JPA implementation of a Service by UHostPortIdx index key object.

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

public class CFSecJpaServiceByUHostPortIdxKey
	implements ICFSecServiceByUHostPortIdxKey, Comparable<Object>, Serializable
{
	protected CFLibDbKeyHash256 requiredClusterId;
	protected CFLibDbKeyHash256 requiredHostNodeId;
	protected short requiredHostPort;
	public CFSecJpaServiceByUHostPortIdxKey() {
		requiredClusterId = CFLibDbKeyHash256.fromHex( ICFSecService.CLUSTERID_INIT_VALUE.toString() );
		requiredHostNodeId = CFLibDbKeyHash256.fromHex( ICFSecService.HOSTNODEID_INIT_VALUE.toString() );
		requiredHostPort = ICFSecService.HOSTPORT_INIT_VALUE;
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
	public CFLibDbKeyHash256 getRequiredHostNodeId() {
		return( requiredHostNodeId );
	}

	@Override
	public void setRequiredHostNodeId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredHostNodeId",
				1,
				"value" );
		}
		requiredHostNodeId = value;
	}

	@Override
	public short getRequiredHostPort() {
		return( requiredHostPort );
	}

	@Override
	public void setRequiredHostPort( short value ) {
		if( value < ICFSecService.HOSTPORT_MIN_VALUE ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				"setRequiredHostPort",
				1,
				"value",
				value,
				ICFSecService.HOSTPORT_MIN_VALUE );
		}
		requiredHostPort = value;
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecServiceByUHostPortIdxKey) {
			ICFSecServiceByUHostPortIdxKey rhs = (ICFSecServiceByUHostPortIdxKey)obj;
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
			if( getRequiredHostNodeId() != null ) {
				if( rhs.getRequiredHostNodeId() != null ) {
					if( ! getRequiredHostNodeId().equals( rhs.getRequiredHostNodeId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredHostNodeId() != null ) {
					return( false );
				}
			}
			if( getRequiredHostPort() != rhs.getRequiredHostPort() ) {
				return( false );
			}
			return( true );
		}
		else if (obj instanceof ICFSecService) {
			ICFSecService rhs = (ICFSecService)obj;
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
			if( getRequiredHostNodeId() != null ) {
				if( rhs.getRequiredHostNodeId() != null ) {
					if( ! getRequiredHostNodeId().equals( rhs.getRequiredHostNodeId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredHostNodeId() != null ) {
					return( false );
				}
			}
			if( getRequiredHostPort() != rhs.getRequiredHostPort() ) {
				return( false );
			}
			return( true );
		}
		else if (obj instanceof ICFSecServiceH) {
			ICFSecServiceH rhs = (ICFSecServiceH)obj;
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
			if( getRequiredHostNodeId() != null ) {
				if( rhs.getRequiredHostNodeId() != null ) {
					if( ! getRequiredHostNodeId().equals( rhs.getRequiredHostNodeId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredHostNodeId() != null ) {
					return( false );
				}
			}
			if( getRequiredHostPort() != rhs.getRequiredHostPort() ) {
				return( false );
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
		hashCode = hashCode + getRequiredHostNodeId().hashCode();
		hashCode = ( hashCode * 0x10000 ) + getRequiredHostPort();
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFSecServiceByUHostPortIdxKey) {
			ICFSecServiceByUHostPortIdxKey rhs = (ICFSecServiceByUHostPortIdxKey)obj;
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
			if (getRequiredHostNodeId() != null) {
				if (rhs.getRequiredHostNodeId() != null) {
					cmp = getRequiredHostNodeId().compareTo( rhs.getRequiredHostNodeId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredHostNodeId() != null) {
				return( -1 );
			}
			if( getRequiredHostPort() < rhs.getRequiredHostPort() ) {
				return( -1 );
			}
			else if( getRequiredHostPort() > rhs.getRequiredHostPort() ) {
				return( 1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecService) {
			ICFSecService rhs = (ICFSecService)obj;
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
			if (getRequiredHostNodeId() != null) {
				if (rhs.getRequiredHostNodeId() != null) {
					cmp = getRequiredHostNodeId().compareTo( rhs.getRequiredHostNodeId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredHostNodeId() != null) {
				return( -1 );
			}
			if( getRequiredHostPort() < rhs.getRequiredHostPort() ) {
				return( -1 );
			}
			else if( getRequiredHostPort() > rhs.getRequiredHostPort() ) {
				return( 1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecServiceH) {
			ICFSecServiceH rhs = (ICFSecServiceH)obj;
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
			if (getRequiredHostNodeId() != null) {
				if (rhs.getRequiredHostNodeId() != null) {
					cmp = getRequiredHostNodeId().compareTo( rhs.getRequiredHostNodeId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredHostNodeId() != null) {
				return( -1 );
			}
			if( getRequiredHostPort() < rhs.getRequiredHostPort() ) {
				return( -1 );
			}
			else if( getRequiredHostPort() > rhs.getRequiredHostPort() ) {
				return( 1 );
			}
			return( 0 );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(),
				"compareTo",
				"obj",
				obj,
				"ICFSecServiceByUHostPortIdxKey, ICFSecService, ICFSecServiceH");
		}
	}

	public String getXmlAttrFragment() {
		String ret = "" 
			+ " RequiredClusterId=" + "\"" + getRequiredClusterId().toString() + "\""
			+ " RequiredHostNodeId=" + "\"" + getRequiredHostNodeId().toString() + "\""
			+ " RequiredHostPort=" + "\"" + Short.toString( getRequiredHostPort() ) + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecServiceByUHostPortIdxKey" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

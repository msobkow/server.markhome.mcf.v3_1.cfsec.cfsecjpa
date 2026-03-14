// Description: Java JPA implementation of a SecDevice primary key object.

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

/**
 *	CFSecJpaSecDevicePKey Primary Key for SecDevice
 *		requiredSecUserId	Required object attribute SecUserId.
 *		requiredDevName	Required object attribute DevName.
 */
@Embeddable
public class CFSecJpaSecDevicePKey
	implements ICFSecSecDevicePKey, Comparable<ICFSecSecDevicePKey>, Serializable
{
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn( name="SecUserId" )
	protected CFSecJpaSecUser requiredContainerSecUser;
	@Column( name="DevName", nullable=false, length=127 )
	protected String requiredDevName;

	public CFSecJpaSecDevicePKey() {
		requiredContainerSecUser = null;
		requiredDevName = ICFSecSecDevice.DEVNAME_INIT_VALUE;
	}

	@Override
	public ICFSecSecUser getRequiredContainerSecUser() {
		return( requiredContainerSecUser );
	}
	@Override
	public void setRequiredContainerSecUser(ICFSecSecUser argObj) {
		if(argObj == null) {
			throw new CFLibNullArgumentException(getClass(), "setContainerSecUser", 1, "argObj");
		}
		else if (argObj instanceof CFSecJpaSecUser) {
			requiredContainerSecUser = (CFSecJpaSecUser)argObj;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "setContainerSecUser", "argObj", argObj, "CFSecJpaSecUser");
		}
	
	}

	@Override
	public void setRequiredContainerSecUser(CFLibDbKeyHash256 argSecUserId) {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredContainerSecUser", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecSecUserTable targetTable = targetBackingSchema.getTableSecUser();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredContainerSecUser", 0, "ICFSecSchema.getBackingCFSec().getTableSecUser()");
		}
		ICFSecSecUser targetRec = targetTable.readDerivedByIdIdx(null, argSecUserId);
		setRequiredContainerSecUser(targetRec);
	}
	@Override
	public CFLibDbKeyHash256 getRequiredSecUserId() {
		ICFSecSecUser result = getRequiredContainerSecUser();
		if (result != null) {
			return result.getRequiredSecUserId();
		}
		else {
			throw new CFLibNullArgumentException(getClass(), "getRequiredSecUserId", 0, "getRequiredContainerSecUser()");
		}
	}

	@Override
	public String getRequiredDevName() {
		return( requiredDevName );
	}

	@Override
	public void setRequiredDevName( String value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredDevName",
				1,
				"value" );
		}
		else if( value.length() > 127 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredDevName",
				1,
				"value.length()",
				value.length(),
				127 );
		}
		requiredDevName = value;
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecSecDevicePKey) {
			ICFSecSecDevicePKey rhs = (ICFSecSecDevicePKey)obj;
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
			if( getRequiredDevName() != null ) {
				if( rhs.getRequiredDevName() != null ) {
					if( ! getRequiredDevName().equals( rhs.getRequiredDevName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredDevName() != null ) {
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
		if( getRequiredDevName() != null ) {
			hashCode = hashCode + getRequiredDevName().hashCode();
		}
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( ICFSecSecDevicePKey rhs ) {
		int cmp;
		if (rhs == null) {
			return( 1 );
		}
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
			if (getRequiredDevName() != null) {
				if (rhs.getRequiredDevName() != null) {
					cmp = getRequiredDevName().compareTo( rhs.getRequiredDevName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredDevName() != null) {
				return( -1 );
			}
		return( 0 );
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = "" 
			+ " RequiredSecUserId=" + "\"" + getRequiredSecUserId().toString() + "\""
			+ " RequiredDevName=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredDevName() ) + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecSecDevicePKey" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

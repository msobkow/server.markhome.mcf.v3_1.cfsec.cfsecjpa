// Description: Java 25 JPA implementation of a SecSysGrpInc history primary key object.

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
 *	CFSecJpaSecSysGrpIncHPKey History Primary Key for SecSysGrpInc
 *		requiredSecSysGrpId	Required object attribute SecSysGrpId.
 *		requiredIncName	Required object attribute IncName.
 */
public class CFSecJpaSecSysGrpIncHPKey
	implements ICFSecSecSysGrpIncHPKey, Comparable<Object>, Serializable
{
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column=@Column(name="auditClusterId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH) )
	})
	protected CFLibDbKeyHash256 auditClusterId;

	@Column(name="auditStamp", nullable=false)
	protected LocalDateTime auditStamp;

	@Column(name="auditAction", nullable=false)
	protected short auditActionId;

	@Column(name="requiredRevision", nullable=false)
	protected int requiredRevision;

	@AttributeOverrides({
		@AttributeOverride(name="bytes", column=@Column(name="auditSessionId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH) )
	})
	protected CFLibDbKeyHash256 auditSessionId;

	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="SecSysGrpId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredSecSysGrpId;
	@Column( name="inc_name", nullable=false, length=64 )
	protected String requiredIncName;

	public CFSecJpaSecSysGrpIncHPKey() {
		auditClusterId = ICFSecCluster.ID_INIT_VALUE;
		auditStamp = LocalDateTime.now();
		auditActionId = 0;
		requiredRevision = 1;
		auditSessionId = CFLibDbKeyHash256.nullGet();
		requiredSecSysGrpId = CFLibDbKeyHash256.fromHex( ICFSecSecSysGrpInc.SECSYSGRPID_INIT_VALUE.toString() );
		requiredIncName = ICFSecSecSysGrpInc.INCNAME_INIT_VALUE;
	}

	@Override
	public CFLibDbKeyHash256 getAuditClusterId() {
		return( auditClusterId );
	}

	@Override
	public void setAuditClusterId( CFLibDbKeyHash256 value ) {
		auditClusterId = value;
	}

	@Override
	public LocalDateTime getAuditStamp() {
		return( auditStamp );
	}

	@Override
	public void setAuditStamp( LocalDateTime value ) {
		auditStamp = value;
	}

	@Override
	public short getAuditActionId() {
		return( auditActionId );
	}

	@Override
	public void setAuditActionId( short value ) {
		auditActionId = value;
	}

	@Override
	public int getRequiredRevision() {
		return( requiredRevision );
	}

	@Override
	public void setRequiredRevision( int value ) {
		requiredRevision = value;
	}

	@Override
	public CFLibDbKeyHash256 getAuditSessionId() {
		return( auditSessionId );
	}

	@Override
	public void setAuditSessionId( CFLibDbKeyHash256 value ) {
		auditSessionId = value;
	}

	@Override
	public CFLibDbKeyHash256 getRequiredSecSysGrpId() {
		return( requiredSecSysGrpId );
	}

	@Override
	public void setRequiredSecSysGrpId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredSecSysGrpId",
				1,
				"value" );
		}
		requiredSecSysGrpId = value;
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
		else if (obj instanceof ICFSecSecSysGrpInc) {
			ICFSecSecSysGrpInc rhs = (ICFSecSecSysGrpInc)obj;
			if( getRequiredSecSysGrpId() != null ) {
				if( rhs.getRequiredSecSysGrpId() != null ) {
					if( ! getRequiredSecSysGrpId().equals( rhs.getRequiredSecSysGrpId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecSysGrpId() != null ) {
					return( false );
				}
			}
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
		else if (obj instanceof ICFSecSecSysGrpIncHPKey) {
			ICFSecSecSysGrpIncHPKey rhs = (ICFSecSecSysGrpIncHPKey)obj;
			if (getAuditClusterId() != null) {
				if (rhs.getAuditClusterId() != null) {
					if ( ! getAuditClusterId().equals(rhs.getAuditClusterId())) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else if (rhs.getAuditClusterId() != null) {
				return( false );
			}
			if (getAuditStamp() != null) {
				if (rhs.getAuditStamp() != null) {
					if ( ! getAuditStamp().equals(rhs.getAuditStamp())) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else if (rhs.getAuditStamp() != null) {
				return( false );
			}
			if( getAuditActionId() != rhs.getAuditActionId() ) {
				return( false );
			}
			if( getRequiredRevision() != rhs.getRequiredRevision() ) {
				return( false );
			}
			if (getAuditSessionId() != null && !getAuditSessionId().isNull() ) {
				if (rhs.getAuditSessionId() != null && !rhs.getAuditSessionId().isNull() ) {
					if ( ! getAuditSessionId().equals(rhs.getAuditSessionId())) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else if (rhs.getAuditSessionId() != null && !rhs.getAuditSessionId().isNull() ) {
				return( false );
			}
			if( getRequiredSecSysGrpId() != null ) {
				if( rhs.getRequiredSecSysGrpId() != null ) {
					if( ! getRequiredSecSysGrpId().equals( rhs.getRequiredSecSysGrpId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecSysGrpId() != null ) {
					return( false );
				}
			}
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
		else if (obj instanceof ICFSecSecSysGrpIncH) {
			ICFSecSecSysGrpIncH rhs = (ICFSecSecSysGrpIncH)obj;
			if (getAuditClusterId() != null) {
				if (rhs.getAuditClusterId() != null) {
					if ( ! getAuditClusterId().equals(rhs.getAuditClusterId())) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else if (rhs.getAuditClusterId() != null) {
				return( false );
			}
			if (getAuditStamp() != null) {
				if (rhs.getAuditStamp() != null) {
					if ( ! getAuditStamp().equals(rhs.getAuditStamp())) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else if (rhs.getAuditStamp() != null) {
				return( false );
			}
			if( getAuditActionId() != rhs.getAuditActionId() ) {
				return( false );
			}
			if( getRequiredRevision() != rhs.getRequiredRevision() ) {
				return( false );
			}
			if (getAuditSessionId() != null && !getAuditSessionId().isNull() ) {
				if (rhs.getAuditSessionId() != null && !rhs.getAuditSessionId().isNull() ) {
					if ( ! getAuditSessionId().equals(rhs.getAuditSessionId())) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else if (rhs.getAuditSessionId() != null && !rhs.getAuditSessionId().isNull() ) {
				return( false );
			}
			if( getRequiredSecSysGrpId() != null ) {
				if( rhs.getRequiredSecSysGrpId() != null ) {
					if( ! getRequiredSecSysGrpId().equals( rhs.getRequiredSecSysGrpId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecSysGrpId() != null ) {
					return( false );
				}
			}
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
		if( auditClusterId != null ) {
			hashCode = hashCode + auditClusterId.hashCode();
		}
		if( auditStamp != null ) {
			hashCode = hashCode + auditStamp.hashCode();
		}
		hashCode = hashCode + auditActionId;
		hashCode = hashCode + requiredRevision;
		if( auditSessionId != null ) {
			hashCode = hashCode + auditSessionId.hashCode();
		}
		hashCode = hashCode + getRequiredSecSysGrpId().hashCode();
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
		else if (obj instanceof ICFSecSecSysGrpInc) {
			ICFSecSecSysGrpInc rhs = (ICFSecSecSysGrpInc)obj;
			if (getRequiredSecSysGrpId() != null) {
				if (rhs.getRequiredSecSysGrpId() != null) {
					cmp = getRequiredSecSysGrpId().compareTo( rhs.getRequiredSecSysGrpId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecSysGrpId() != null) {
				return( -1 );
			}
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
		else if (obj instanceof ICFSecSecSysGrpIncHPKey) {
			ICFSecSecSysGrpIncHPKey rhs = (ICFSecSecSysGrpIncHPKey)obj;
			if( getAuditClusterId() == null ) {
				if( rhs.getAuditClusterId() != null ) {
					return( -1 );
				}
			}
			else if( rhs.getAuditClusterId() == null ) {
				return( 1 );
			}
			else {
				cmp = getAuditClusterId().compareTo( rhs.getAuditClusterId() );
				if( cmp != 0 ) {
					return( cmp );
				}
			}
			if( getAuditStamp() == null ) {
				if( rhs.getAuditStamp() != null ) {
					return( -1 );
				}
			}
			else if( rhs.getAuditStamp() == null ) {
				return( 1 );
			}
			else {
				cmp = getAuditStamp().compareTo( rhs.getAuditStamp() );
				if( cmp != 0 ) {
					return( cmp );
				}
			}
			if( getAuditActionId() < rhs.getAuditActionId() ) {
				return( -1 );
			}
			else if( getAuditActionId() > rhs.getAuditActionId() ) {
				return( 1 );
			}
			if( getRequiredRevision() < rhs.getRequiredRevision() ) {
				return( -1 );
			}
			else if( getRequiredRevision() > rhs.getRequiredRevision() ) {
				return( 1 );
			}
			if( getAuditSessionId() == null ) {
				if( rhs.getAuditSessionId() != null ) {
					return( -1 );
				}
			}
			else if( rhs.getAuditSessionId() == null ) {
				return( 1 );
			}
			else {
				cmp = getAuditSessionId().compareTo( rhs.getAuditSessionId() );
				if( cmp != 0 ) {
					return( cmp );
				}
			}
			if (getRequiredSecSysGrpId() != null) {
				if (rhs.getRequiredSecSysGrpId() != null) {
					cmp = getRequiredSecSysGrpId().compareTo( rhs.getRequiredSecSysGrpId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecSysGrpId() != null) {
				return( -1 );
			}
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
		else if (obj instanceof ICFSecSecSysGrpIncH) {
			ICFSecSecSysGrpIncH rhs = (ICFSecSecSysGrpIncH)obj;
			if( getAuditClusterId() == null ) {
				if( rhs.getAuditClusterId() != null ) {
					return( -1 );
				}
			}
			else if( rhs.getAuditClusterId() == null ) {
				return( 1 );
			}
			else {
				cmp = getAuditClusterId().compareTo( rhs.getAuditClusterId() );
				if( cmp != 0 ) {
					return( cmp );
				}
			}
			if( getAuditStamp() == null ) {
				if( rhs.getAuditStamp() != null ) {
					return( -1 );
				}
			}
			else if( rhs.getAuditStamp() == null ) {
				return( 1 );
			}
			else {
				cmp = getAuditStamp().compareTo( rhs.getAuditStamp() );
				if( cmp != 0 ) {
					return( cmp );
				}
			}
			if( getAuditActionId() < rhs.getAuditActionId() ) {
				return( -1 );
			}
			else if( getAuditActionId() > rhs.getAuditActionId() ) {
				return( 1 );
			}
			if( getRequiredRevision() < rhs.getRequiredRevision() ) {
				return( -1 );
			}
			else if( getRequiredRevision() > rhs.getRequiredRevision() ) {
				return( 1 );
			}
			if( getAuditSessionId() == null ) {
				if( rhs.getAuditSessionId() != null ) {
					return( -1 );
				}
			}
			else if( rhs.getAuditSessionId() == null ) {
				return( 1 );
			}
			else {
				cmp = getAuditSessionId().compareTo( rhs.getAuditSessionId() );
				if( cmp != 0 ) {
					return( cmp );
				}
			}
			if (getRequiredSecSysGrpId() != null) {
				if (rhs.getRequiredSecSysGrpId() != null) {
					cmp = getRequiredSecSysGrpId().compareTo( rhs.getRequiredSecSysGrpId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecSysGrpId() != null) {
				return( -1 );
			}
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
			throw new CFLibUnsupportedClassException( getClass(),
				"compareTo",
				"obj",
				obj,
				"ICFSecSecSysGrpIncPKey, ICFSecSecSysGrpInc, ICFSecSecSysGrpIncHPKey, ICFSecSecSysGrpIncH" );
		}
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = " auditClusterId=\"" + (auditClusterId != null ? auditClusterId.toString() : "null" ) + "\""
			+ " auditStamp=\"" + (auditStamp != null ? CFLibXmlUtil.formatTimestamp(auditStamp) : "null") + "\""
			+ " auditAction=\"" + auditActionId + "\""
			+ " requiredRevision=\"" + requiredRevision + "\""
			+ " auditSessionId=\"" + (getAuditSessionId() != null ? getAuditSessionId().toString() : "null") + "\""
			+ " RequiredSecSysGrpId=" + "\"" + getRequiredSecSysGrpId().toString() + "\""
			+ " RequiredIncName=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredIncName() ) + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecJpaSecSysGrpIncHPKey" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

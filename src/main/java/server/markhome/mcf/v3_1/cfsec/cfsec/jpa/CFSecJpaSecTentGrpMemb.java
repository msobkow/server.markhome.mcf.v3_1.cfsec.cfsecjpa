// Description: Java 25 JPA implementation of a SecTentGrpMemb entity definition object.

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

@Entity
@Table(
	name = "SecTentGrpMemb", schema = "CFSec31",
	indexes = {
		@Index(name = "SecTentGrpMembIdIdx", columnList = "SecTentGrpId, login_id", unique = true),
		@Index(name = "SecTentGrpMembTentGrpIdx", columnList = "SecTentGrpId", unique = false),
		@Index(name = "SecTentGrpMembLoginIdx", columnList = "login_id", unique = false)
	}
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaSecTentGrpMemb
	implements Comparable<Object>,
		ICFSecSecTentGrpMemb,
		Serializable
{
	// Embedded id's are package-accessible so that the Repository can dereference the attributes of the primary key
	@AttributeOverrides({
		@AttributeOverride(name="SecTentGrpId", column = @Column( name="SecTentGrpId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) ),
		@AttributeOverride(name="login_id", column = @Column( name="login_id", nullable=false, length=32 ) )
	})
	@EmbeddedId
	CFSecJpaSecTentGrpMembPKey pkey = new CFSecJpaSecTentGrpMembPKey();
	protected int requiredRevision;


	@AttributeOverrides({
		@AttributeOverride( name="bytes", column = @Column( name="CreatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 createdByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecTentGrpMemb.S_INIT_CREATED_BY);

	@Column(name="CreatedAt", nullable=false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column= @Column( name="UpdatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 updatedByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecTentGrpMemb.S_INIT_UPDATED_BY);

	@Column(name="UpdatedAt", nullable=false)
	protected LocalDateTime updatedAt = LocalDateTime.now();

	public CFSecJpaSecTentGrpMemb() {
		pkey = new CFSecJpaSecTentGrpMembPKey();
	}

	@Override
	public int getClassCode() {
		return( ICFSecSecTentGrpMemb.CLASS_CODE );
	}

	@Override
	public ICFSecSecTentGrp getRequiredContainerGroup() {
		return( pkey.getRequiredContainerGroup() );
	}
	@Override
	public void setRequiredContainerGroup(ICFSecSecTentGrp argObj) {
		pkey.setRequiredContainerGroup(argObj);
	}

	@Override
	public void setRequiredContainerGroup(CFLibDbKeyHash256 argSecTentGrpId) {
		pkey.setRequiredContainerGroup(argSecTentGrpId);
	}
	@Override
	public ICFSecSecUser getRequiredParentUser() {
		return( pkey.getRequiredParentUser() );
	}
	@Override
	public void setRequiredParentUser(ICFSecSecUser argObj) {
		pkey.setRequiredParentUser(argObj);
	}

	@Override
	public void setRequiredParentUser(String argLoginId) {
		pkey.setRequiredParentUser(argLoginId);
	}
	@Override
	public CFLibDbKeyHash256 getCreatedByUserId() {
		return( createdByUserId );
	}

	@Override
	public void setCreatedByUserId( CFLibDbKeyHash256 value ) {
		if (value == null || value.isNull()) {
			throw new CFLibNullArgumentException(getClass(), "setCreatedByUserId", 1, "value");
		}
		createdByUserId = value;
	}

	@Override
	public LocalDateTime getCreatedAt() {
		return( createdAt );
	}

	@Override
	public void setCreatedAt( LocalDateTime value ) {
		if (value == null) {
			throw new CFLibNullArgumentException(getClass(), "setCreatedAt", 1, "value");
		}
		createdAt = value;
	}

	@Override
	public CFLibDbKeyHash256 getUpdatedByUserId() {
		return( updatedByUserId );
	}

	@Override
	public void setUpdatedByUserId( CFLibDbKeyHash256 value ) {
		if (value == null || value.isNull()) {
			throw new CFLibNullArgumentException(getClass(), "setUpdatedByUserId", 1, "value");
		}
		updatedByUserId = value;
	}

	@Override
	public LocalDateTime getUpdatedAt() {
		return( updatedAt );
	}

	@Override
	public void setUpdatedAt( LocalDateTime value ) {
		if (value == null) {
			throw new CFLibNullArgumentException(getClass(), "setUpdatedAt", 1, "value");
		}
		updatedAt = value;
	}

	@Override
	public ICFSecSecTentGrpMembPKey getPKey() {
		return pkey;
	}

	@Override
	public void setPKey(ICFSecSecTentGrpMembPKey pkey ) {
		if (pkey == null) {
			throw new CFLibNullArgumentException(getClass(), "setPKey", 1, "pkey");
		}
		else if (!(pkey instanceof CFSecJpaSecTentGrpMembPKey)) {
			throw new CFLibUnsupportedClassException(getClass(), "setPKey", "pkey", pkey, "CFSecJpaSecTentGrpMembPKey");
		}
		this.pkey = (CFSecJpaSecTentGrpMembPKey)pkey;
	}

	@Override
	public CFLibDbKeyHash256 getRequiredSecTentGrpId() {
		return( pkey.getRequiredSecTentGrpId() );
	}

	@Override
	public String getRequiredLoginId() {
		return( pkey.getRequiredLoginId() );
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
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecSecTentGrpMemb) {
			ICFSecSecTentGrpMemb rhs = (ICFSecSecTentGrpMemb)obj;
			if( ! getCreatedByUserId().equals( rhs.getCreatedByUserId() ) ) {
				return( false );
			}
			if( ! getCreatedAt().equals( rhs.getCreatedAt() ) ) {
				return( false );
			}
			if( ! getUpdatedByUserId().equals( rhs.getUpdatedByUserId() ) ) {
				return( false );
			}
			if( ! getUpdatedAt().equals( rhs.getUpdatedAt() ) ) {
				return( false );
			}
			if( getRequiredSecTentGrpId() != null ) {
				if( rhs.getRequiredSecTentGrpId() != null ) {
					if( ! getRequiredSecTentGrpId().equals( rhs.getRequiredSecTentGrpId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecTentGrpId() != null ) {
					return( false );
				}
			}
			if( getRequiredLoginId() != null ) {
				if( rhs.getRequiredLoginId() != null ) {
					if( ! getRequiredLoginId().equals( rhs.getRequiredLoginId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredLoginId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecTentGrpMembH) {
			ICFSecSecTentGrpMembH rhs = (ICFSecSecTentGrpMembH)obj;
			if( ! getCreatedByUserId().equals( rhs.getCreatedByUserId() ) ) {
				return( false );
			}
			if( ! getCreatedAt().equals( rhs.getCreatedAt() ) ) {
				return( false );
			}
			if( ! getUpdatedByUserId().equals( rhs.getUpdatedByUserId() ) ) {
				return( false );
			}
			if( ! getUpdatedAt().equals( rhs.getUpdatedAt() ) ) {
				return( false );
			}
			if( getRequiredSecTentGrpId() != null ) {
				if( rhs.getRequiredSecTentGrpId() != null ) {
					if( ! getRequiredSecTentGrpId().equals( rhs.getRequiredSecTentGrpId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecTentGrpId() != null ) {
					return( false );
				}
			}
			if( getRequiredLoginId() != null ) {
				if( rhs.getRequiredLoginId() != null ) {
					if( ! getRequiredLoginId().equals( rhs.getRequiredLoginId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredLoginId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecTentGrpMembHPKey) {
			ICFSecSecTentGrpMembHPKey rhs = (ICFSecSecTentGrpMembHPKey)obj;
			if( getRequiredSecTentGrpId() != null ) {
				if( rhs.getRequiredSecTentGrpId() != null ) {
					if( ! getRequiredSecTentGrpId().equals( rhs.getRequiredSecTentGrpId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecTentGrpId() != null ) {
					return( false );
				}
			}
			if( getRequiredLoginId() != null ) {
				if( rhs.getRequiredLoginId() != null ) {
					if( ! getRequiredLoginId().equals( rhs.getRequiredLoginId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredLoginId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecTentGrpMembByTentGrpIdxKey) {
			ICFSecSecTentGrpMembByTentGrpIdxKey rhs = (ICFSecSecTentGrpMembByTentGrpIdxKey)obj;
			if( getRequiredSecTentGrpId() != null ) {
				if( rhs.getRequiredSecTentGrpId() != null ) {
					if( ! getRequiredSecTentGrpId().equals( rhs.getRequiredSecTentGrpId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecTentGrpId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecTentGrpMembByUserIdxKey) {
			ICFSecSecTentGrpMembByUserIdxKey rhs = (ICFSecSecTentGrpMembByUserIdxKey)obj;
			if( getRequiredLoginId() != null ) {
				if( rhs.getRequiredLoginId() != null ) {
					if( ! getRequiredLoginId().equals( rhs.getRequiredLoginId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredLoginId() != null ) {
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
		int hashCode = getPKey().hashCode();
		hashCode = hashCode + getCreatedByUserId().hashCode();
		hashCode = hashCode + getCreatedAt().hashCode();
		hashCode = hashCode + getUpdatedByUserId().hashCode();
		hashCode = hashCode + getUpdatedAt().hashCode();
		hashCode = hashCode + getRequiredSecTentGrpId().hashCode();
		if( getRequiredLoginId() != null ) {
			hashCode = hashCode + getRequiredLoginId().hashCode();
		}
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFSecSecTentGrpMemb) {
			ICFSecSecTentGrpMemb rhs = (ICFSecSecTentGrpMemb)obj;
			if (getPKey() == null) {
				if (rhs.getPKey() != null) {
					return( -1 );
				}
			}
			else {
				if (rhs.getPKey() == null) {
					return( 1 );
				}
				else {
					cmp = getPKey().compareTo(rhs.getPKey());
					if (cmp != 0) {
						return( cmp );
					}
				}
			}
			cmp = getCreatedByUserId().compareTo( rhs.getCreatedByUserId() );
			if( cmp != 0 ) {
				return( cmp );
			}
			cmp = getCreatedAt().compareTo( rhs.getCreatedAt() );
			if( cmp != 0 ) {
				return( cmp );
			}
			cmp = getUpdatedByUserId().compareTo( rhs.getUpdatedByUserId() );
			if( cmp != 0 ) {
				return( cmp );
			}
			cmp = getUpdatedAt().compareTo( rhs.getUpdatedAt() );
			if( cmp != 0 ) {
				return( cmp );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecSecTentGrpMembHPKey) {
			ICFSecSecTentGrpMembHPKey rhs = (ICFSecSecTentGrpMembHPKey)obj;
			if (getRequiredSecTentGrpId() != null) {
				if (rhs.getRequiredSecTentGrpId() != null) {
					cmp = getRequiredSecTentGrpId().compareTo( rhs.getRequiredSecTentGrpId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecTentGrpId() != null) {
				return( -1 );
			}
			if (getRequiredLoginId() != null) {
				if (rhs.getRequiredLoginId() != null) {
					cmp = getRequiredLoginId().compareTo( rhs.getRequiredLoginId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredLoginId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if( obj instanceof ICFSecSecTentGrpMembH ) {
			ICFSecSecTentGrpMembH rhs = (ICFSecSecTentGrpMembH)obj;
			if (getRequiredSecTentGrpId() != null) {
				if (rhs.getRequiredSecTentGrpId() != null) {
					cmp = getRequiredSecTentGrpId().compareTo( rhs.getRequiredSecTentGrpId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecTentGrpId() != null) {
				return( -1 );
			}
			if (getRequiredLoginId() != null) {
				if (rhs.getRequiredLoginId() != null) {
					cmp = getRequiredLoginId().compareTo( rhs.getRequiredLoginId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredLoginId() != null) {
				return( -1 );
			}
			cmp = getCreatedByUserId().compareTo( rhs.getCreatedByUserId() );
			if( cmp != 0 ) {
				return( cmp );
			}
			cmp = getCreatedAt().compareTo( rhs.getCreatedAt() );
			if( cmp != 0 ) {
				return( cmp );
			}
			cmp = getUpdatedByUserId().compareTo( rhs.getUpdatedByUserId() );
			if( cmp != 0 ) {
				return( cmp );
			}
			cmp = getUpdatedAt().compareTo( rhs.getUpdatedAt() );
			if( cmp != 0 ) {
				return( cmp );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecSecTentGrpMembByTentGrpIdxKey) {
			ICFSecSecTentGrpMembByTentGrpIdxKey rhs = (ICFSecSecTentGrpMembByTentGrpIdxKey)obj;
			if (getRequiredSecTentGrpId() != null) {
				if (rhs.getRequiredSecTentGrpId() != null) {
					cmp = getRequiredSecTentGrpId().compareTo( rhs.getRequiredSecTentGrpId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecTentGrpId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecSecTentGrpMembByUserIdxKey) {
			ICFSecSecTentGrpMembByUserIdxKey rhs = (ICFSecSecTentGrpMembByUserIdxKey)obj;
			if (getRequiredLoginId() != null) {
				if (rhs.getRequiredLoginId() != null) {
					cmp = getRequiredLoginId().compareTo( rhs.getRequiredLoginId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredLoginId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				"compareTo",
				"obj",
				obj,
				null );
		}
	}

	@Override
	public void set( ICFSecSecTentGrpMemb src ) {
		setSecTentGrpMemb( src );
	}

	@Override
	public void setSecTentGrpMemb( ICFSecSecTentGrpMemb src ) {
		setRequiredContainerGroup(src.getRequiredContainerGroup());
		setRequiredParentUser(src.getRequiredParentUser());
		setRequiredRevision( src.getRequiredRevision() );
		setCreatedByUserId( src.getCreatedByUserId() );
		setCreatedAt( src.getCreatedAt() );
		setUpdatedByUserId( src.getUpdatedByUserId() );
		setUpdatedAt( src.getUpdatedAt() );
	}

	@Override
	public void set( ICFSecSecTentGrpMembH src ) {
		setSecTentGrpMemb( src );
	}

	@Override
	public void setSecTentGrpMemb( ICFSecSecTentGrpMembH src ) {
		setRequiredContainerGroup(src.getRequiredSecTentGrpId());
		setRequiredParentUser(src.getRequiredLoginId());
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = pkey.getXmlAttrFragment() 
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredSecTentGrpId=" + "\"" + getRequiredSecTentGrpId().toString() + "\""
			+ " RequiredLoginId=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredLoginId() ) + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecJpaSecTentGrpMemb" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

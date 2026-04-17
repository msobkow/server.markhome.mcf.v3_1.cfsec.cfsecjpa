// Description: Java 25 JPA implementation of a SecSysGrp entity definition object.

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
	name = "SecSysGrp", schema = "CFSec31",
	indexes = {
		@Index(name = "SecSysGrpIdIdx", columnList = "SecSysGrpId", unique = true),
		@Index(name = "SecSysGrpUNameIdx", columnList = "safe_name", unique = true),
		@Index(name = "SecSysGrpLevelIdx", columnList = "sec_level", unique = false)
	}
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaSecSysGrp
	implements Comparable<Object>,
		ICFSecSecSysGrp,
		Serializable
{
	@Id
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="SecSysGrpId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredSecSysGrpId;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="pkey.requiredContainerGroup")
	protected Set<CFSecJpaSecSysGrpInc> optionalChildrenIncByGrp;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="pkey.requiredContainerGroup")
	protected Set<CFSecJpaSecSysGrpMemb> optionalChildrenMembByGrp;
	protected int requiredRevision;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="pkey.requiredParentSubGroup")
	protected Set<CFSecJpaSecSysGrpInc> optionalChildrenSysGrpByName;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="pkey.requiredParentSubGroup")
	protected Set<CFSecJpaSecClusGrpInc> optionalChildrenClusGrpByName;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="pkey.requiredParentSubGroup")
	protected Set<CFSecJpaSecTentGrpInc> optionalChildrenTentGrpByName;

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column = @Column( name="CreatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 createdByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecSysGrp.S_INIT_CREATED_BY);

	@Column(name="CreatedAt", nullable=false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column= @Column( name="UpdatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 updatedByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecSysGrp.S_INIT_UPDATED_BY);

	@Column(name="UpdatedAt", nullable=false)
	protected LocalDateTime updatedAt = LocalDateTime.now();
	@Column( name="safe_name", nullable=false, length=64 )
	protected String requiredName;
	@Column( name="sec_level", nullable=false )
	protected ICFSecSchema.SecLevelEnum requiredSecLevel;

	public CFSecJpaSecSysGrp() {
		requiredSecSysGrpId = CFLibDbKeyHash256.fromHex( ICFSecSecSysGrp.SECSYSGRPID_INIT_VALUE.toString() );
		requiredName = ICFSecSecSysGrp.NAME_INIT_VALUE;
		requiredSecLevel = ICFSecSecSysGrp.SECLEVEL_INIT_VALUE;
	}

	@Override
	public int getClassCode() {
		return( ICFSecSecSysGrp.CLASS_CODE );
	}

	@Override
	public List<ICFSecSecSysGrpInc> getOptionalChildrenIncByGrp() {
		List<ICFSecSecSysGrpInc> retlist = new ArrayList<>(optionalChildrenIncByGrp.size());
		for (CFSecJpaSecSysGrpInc cur: optionalChildrenIncByGrp) {
			retlist.add(cur);
		}
		return( retlist );
	}
	@Override
	public List<ICFSecSecSysGrpMemb> getOptionalChildrenMembByGrp() {
		List<ICFSecSecSysGrpMemb> retlist = new ArrayList<>(optionalChildrenMembByGrp.size());
		for (CFSecJpaSecSysGrpMemb cur: optionalChildrenMembByGrp) {
			retlist.add(cur);
		}
		return( retlist );
	}
	@Override
	public List<ICFSecSecSysGrpInc> getOptionalChildrenSysGrpByName() {
		List<ICFSecSecSysGrpInc> retlist = new ArrayList<>(optionalChildrenSysGrpByName.size());
		for (CFSecJpaSecSysGrpInc cur: optionalChildrenSysGrpByName) {
			retlist.add(cur);
		}
		return( retlist );
	}
	@Override
	public List<ICFSecSecClusGrpInc> getOptionalChildrenClusGrpByName() {
		List<ICFSecSecClusGrpInc> retlist = new ArrayList<>(optionalChildrenClusGrpByName.size());
		for (CFSecJpaSecClusGrpInc cur: optionalChildrenClusGrpByName) {
			retlist.add(cur);
		}
		return( retlist );
	}
	@Override
	public List<ICFSecSecTentGrpInc> getOptionalChildrenTentGrpByName() {
		List<ICFSecSecTentGrpInc> retlist = new ArrayList<>(optionalChildrenTentGrpByName.size());
		for (CFSecJpaSecTentGrpInc cur: optionalChildrenTentGrpByName) {
			retlist.add(cur);
		}
		return( retlist );
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
	public CFLibDbKeyHash256 getPKey() {
		return getRequiredSecSysGrpId();
	}

	@Override
	public void setPKey(CFLibDbKeyHash256 requiredSecSysGrpId) {
		this.requiredSecSysGrpId = requiredSecSysGrpId;
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
	public int getRequiredRevision() {
		return( requiredRevision );
	}

	@Override
	public void setRequiredRevision( int value ) {
		requiredRevision = value;
	}

	@Override
	public String getRequiredName() {
		return( requiredName );
	}

	@Override
	public void setRequiredName( String value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredName",
				1,
				"value" );
		}
		else if( value.length() > 64 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredName",
				1,
				"value.length()",
				value.length(),
				64 );
		}
		requiredName = value;
	}

	@Override
	public ICFSecSchema.SecLevelEnum getRequiredSecLevel() {
		return( requiredSecLevel );
	}

	@Override
	public void setRequiredSecLevel( ICFSecSchema.SecLevelEnum value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredSecLevel",
				1,
				"value" );
		}
		requiredSecLevel = value;
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecSecSysGrp) {
			ICFSecSecSysGrp rhs = (ICFSecSecSysGrp)obj;
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
			if( getRequiredName() != null ) {
				if( rhs.getRequiredName() != null ) {
					if( ! getRequiredName().equals( rhs.getRequiredName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredName() != null ) {
					return( false );
				}
			}
			if( getRequiredSecLevel() != null ) {
				if( rhs.getRequiredSecLevel() != null ) {
					if( ! getRequiredSecLevel().equals( rhs.getRequiredSecLevel() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecLevel() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecSysGrpH) {
			ICFSecSecSysGrpH rhs = (ICFSecSecSysGrpH)obj;
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
			if( getRequiredName() != null ) {
				if( rhs.getRequiredName() != null ) {
					if( ! getRequiredName().equals( rhs.getRequiredName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredName() != null ) {
					return( false );
				}
			}
			if( getRequiredSecLevel() != null ) {
				if( rhs.getRequiredSecLevel() != null ) {
					if( ! getRequiredSecLevel().equals( rhs.getRequiredSecLevel() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecLevel() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecSysGrpHPKey) {
			ICFSecSecSysGrpHPKey rhs = (ICFSecSecSysGrpHPKey)obj;
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
			return( true );
		}
		else if (obj instanceof ICFSecSecSysGrpByUNameIdxKey) {
			ICFSecSecSysGrpByUNameIdxKey rhs = (ICFSecSecSysGrpByUNameIdxKey)obj;
			if( getRequiredName() != null ) {
				if( rhs.getRequiredName() != null ) {
					if( ! getRequiredName().equals( rhs.getRequiredName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredName() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecSysGrpBySecLevelIdxKey) {
			ICFSecSecSysGrpBySecLevelIdxKey rhs = (ICFSecSecSysGrpBySecLevelIdxKey)obj;
			if( getRequiredSecLevel() != null ) {
				if( rhs.getRequiredSecLevel() != null ) {
					if( ! getRequiredSecLevel().equals( rhs.getRequiredSecLevel() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecLevel() != null ) {
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
		hashCode = hashCode + getRequiredSecSysGrpId().hashCode();
		if( getRequiredName() != null ) {
			hashCode = hashCode + getRequiredName().hashCode();
		}
		hashCode = ( hashCode * 0x10000 ) + getRequiredSecLevel().ordinal();
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFSecSecSysGrp) {
			ICFSecSecSysGrp rhs = (ICFSecSecSysGrp)obj;
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
			if (getRequiredName() != null) {
				if (rhs.getRequiredName() != null) {
					cmp = getRequiredName().compareTo( rhs.getRequiredName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredName() != null) {
				return( -1 );
			}
			if (getRequiredSecLevel() != null) {
				if (rhs.getRequiredSecLevel() != null) {
					cmp = getRequiredSecLevel().compareTo( rhs.getRequiredSecLevel() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecLevel() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecSecSysGrpHPKey) {
			ICFSecSecSysGrpHPKey rhs = (ICFSecSecSysGrpHPKey)obj;
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
			return( 0 );
		}
		else if( obj instanceof ICFSecSecSysGrpH ) {
			ICFSecSecSysGrpH rhs = (ICFSecSecSysGrpH)obj;
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
			if (getRequiredName() != null) {
				if (rhs.getRequiredName() != null) {
					cmp = getRequiredName().compareTo( rhs.getRequiredName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredName() != null) {
				return( -1 );
			}
			if (getRequiredSecLevel() != null) {
				if (rhs.getRequiredSecLevel() != null) {
					cmp = getRequiredSecLevel().compareTo( rhs.getRequiredSecLevel() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecLevel() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecSecSysGrpByUNameIdxKey) {
			ICFSecSecSysGrpByUNameIdxKey rhs = (ICFSecSecSysGrpByUNameIdxKey)obj;
			if (getRequiredName() != null) {
				if (rhs.getRequiredName() != null) {
					cmp = getRequiredName().compareTo( rhs.getRequiredName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredName() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecSecSysGrpBySecLevelIdxKey) {
			ICFSecSecSysGrpBySecLevelIdxKey rhs = (ICFSecSecSysGrpBySecLevelIdxKey)obj;
			if (getRequiredSecLevel() != null) {
				if (rhs.getRequiredSecLevel() != null) {
					cmp = getRequiredSecLevel().compareTo( rhs.getRequiredSecLevel() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecLevel() != null) {
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
	public void set( ICFSecSecSysGrp src ) {
		setSecSysGrp( src );
	}

	@Override
	public void setSecSysGrp( ICFSecSecSysGrp src ) {
		setRequiredSecSysGrpId(src.getRequiredSecSysGrpId());
		setRequiredRevision( src.getRequiredRevision() );
		setCreatedByUserId( src.getCreatedByUserId() );
		setCreatedAt( src.getCreatedAt() );
		setUpdatedByUserId( src.getUpdatedByUserId() );
		setUpdatedAt( src.getUpdatedAt() );
		setRequiredName(src.getRequiredName());
		setRequiredSecLevel(src.getRequiredSecLevel());
	}

	@Override
	public void set( ICFSecSecSysGrpH src ) {
		setSecSysGrp( src );
	}

	@Override
	public void setSecSysGrp( ICFSecSecSysGrpH src ) {
		setRequiredSecSysGrpId(src.getRequiredSecSysGrpId());
		setRequiredName(src.getRequiredName());
		setRequiredSecLevel(src.getRequiredSecLevel());
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = ""
			+ " RequiredSecSysGrpId=" + "\"" + getRequiredSecSysGrpId().toString() + "\""
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredSecSysGrpId=" + "\"" + getRequiredSecSysGrpId().toString() + "\""
			+ " RequiredName=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredName() ) + "\""
			+ " RequiredSecLevel=" + "\"" + getRequiredSecLevel().toString() + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecJpaSecSysGrp" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

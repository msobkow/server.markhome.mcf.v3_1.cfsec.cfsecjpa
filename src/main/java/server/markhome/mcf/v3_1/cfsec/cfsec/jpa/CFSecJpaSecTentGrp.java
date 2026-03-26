// Description: Java 25 JPA implementation of a SecTentGrp entity definition object.

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
	name = "SecTentGrp", schema = "CFSec31",
	indexes = {
		@Index(name = "SecTentGrpIdIdx", columnList = "SecTentGrpId", unique = true),
		@Index(name = "SecTentGrpTenantIdx", columnList = "TenantId", unique = false),
		@Index(name = "SecTentGrpNameIdx", columnList = "safe_name", unique = false),
		@Index(name = "SecTentGrpUNameIdx", columnList = "TenantId, safe_name", unique = true)
	}
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaSecTentGrp
	implements Comparable<Object>,
		ICFSecSecTentGrp,
		Serializable
{
	@Id
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="SecTentGrpId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredSecTentGrpId;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="pkey.requiredContainerGroup")
	protected Set<CFSecJpaSecTentGrpInc> optionalChildrenIncByGrp;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="pkey.requiredContainerGroup")
	protected Set<CFSecJpaSecTentGrpMemb> optionalChildrenMembByGrp;
	protected int requiredRevision;

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn( name="TenantId" )
	protected CFSecJpaTenant requiredOwnerTenant;

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column = @Column( name="CreatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 createdByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecTentGrp.S_INIT_CREATED_BY);

	@Column(name="CreatedAt", nullable=false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column= @Column( name="UpdatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 updatedByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecTentGrp.S_INIT_UPDATED_BY);

	@Column(name="UpdatedAt", nullable=false)
	protected LocalDateTime updatedAt = LocalDateTime.now();
	@Column( name="safe_name", nullable=false, length=64 )
	protected String requiredName;

	public CFSecJpaSecTentGrp() {
		requiredSecTentGrpId = CFLibDbKeyHash256.fromHex( ICFSecSecTentGrp.SECTENTGRPID_INIT_VALUE.toString() );
		requiredName = ICFSecSecTentGrp.NAME_INIT_VALUE;
	}

	@Override
	public int getClassCode() {
		return( ICFSecSecTentGrp.CLASS_CODE );
	}

	@Override
	public List<ICFSecSecTentGrpInc> getOptionalChildrenIncByGrp() {
		List<ICFSecSecTentGrpInc> retlist = new ArrayList<>(optionalChildrenIncByGrp.size());
		for (CFSecJpaSecTentGrpInc cur: optionalChildrenIncByGrp) {
			retlist.add(cur);
		}
		return( retlist );
	}
	@Override
	public List<ICFSecSecTentGrpMemb> getOptionalChildrenMembByGrp() {
		List<ICFSecSecTentGrpMemb> retlist = new ArrayList<>(optionalChildrenMembByGrp.size());
		for (CFSecJpaSecTentGrpMemb cur: optionalChildrenMembByGrp) {
			retlist.add(cur);
		}
		return( retlist );
	}
	@Override
	public ICFSecTenant getRequiredOwnerTenant() {
		return( requiredOwnerTenant );
	}
	@Override
	public void setRequiredOwnerTenant(ICFSecTenant argObj) {
		if(argObj == null) {
			throw new CFLibNullArgumentException(getClass(), "setOwnerTenant", 1, "argObj");
		}
		else if (argObj instanceof CFSecJpaTenant) {
			requiredOwnerTenant = (CFSecJpaTenant)argObj;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "setOwnerTenant", "argObj", argObj, "CFSecJpaTenant");
		}
	}

	@Override
	public void setRequiredOwnerTenant(CFLibDbKeyHash256 argTenantId) {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredOwnerTenant", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecTenantTable targetTable = targetBackingSchema.getTableTenant();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredOwnerTenant", 0, "ICFSecSchema.getBackingCFSec().getTableTenant()");
		}
		ICFSecTenant targetRec = targetTable.readDerived(null, argTenantId);
		setRequiredOwnerTenant(targetRec);
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
		return getRequiredSecTentGrpId();
	}

	@Override
	public void setPKey(CFLibDbKeyHash256 requiredSecTentGrpId) {
		if (requiredSecTentGrpId != null) {
			setRequiredSecTentGrpId(requiredSecTentGrpId);
		}
	}
	
	@Override
	public CFLibDbKeyHash256 getRequiredSecTentGrpId() {
		return( requiredSecTentGrpId );
	}

	@Override
	public void setRequiredSecTentGrpId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredSecTentGrpId",
				1,
				"value" );
		}
		requiredSecTentGrpId = value;
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
	public CFLibDbKeyHash256 getRequiredTenantId() {
		ICFSecTenant result = getRequiredOwnerTenant();
		if (result != null) {
			return result.getRequiredId();
		}
		else {
			return( ICFSecTenant.ID_INIT_VALUE );
		}
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
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecSecTentGrp) {
			ICFSecSecTentGrp rhs = (ICFSecSecTentGrp)obj;
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
			if( getRequiredTenantId() != null ) {
				if( rhs.getRequiredTenantId() != null ) {
					if( ! getRequiredTenantId().equals( rhs.getRequiredTenantId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTenantId() != null ) {
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
			return( true );
		}
		else if (obj instanceof ICFSecSecTentGrpH) {
			ICFSecSecTentGrpH rhs = (ICFSecSecTentGrpH)obj;
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
			if( getRequiredTenantId() != null ) {
				if( rhs.getRequiredTenantId() != null ) {
					if( ! getRequiredTenantId().equals( rhs.getRequiredTenantId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTenantId() != null ) {
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
			return( true );
		}
		else if (obj instanceof ICFSecSecTentGrpHPKey) {
			ICFSecSecTentGrpHPKey rhs = (ICFSecSecTentGrpHPKey)obj;
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
		else if (obj instanceof ICFSecSecTentGrpByTenantIdxKey) {
			ICFSecSecTentGrpByTenantIdxKey rhs = (ICFSecSecTentGrpByTenantIdxKey)obj;
			if( getRequiredTenantId() != null ) {
				if( rhs.getRequiredTenantId() != null ) {
					if( ! getRequiredTenantId().equals( rhs.getRequiredTenantId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTenantId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecTentGrpByNameIdxKey) {
			ICFSecSecTentGrpByNameIdxKey rhs = (ICFSecSecTentGrpByNameIdxKey)obj;
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
		else if (obj instanceof ICFSecSecTentGrpByUNameIdxKey) {
			ICFSecSecTentGrpByUNameIdxKey rhs = (ICFSecSecTentGrpByUNameIdxKey)obj;
			if( getRequiredTenantId() != null ) {
				if( rhs.getRequiredTenantId() != null ) {
					if( ! getRequiredTenantId().equals( rhs.getRequiredTenantId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTenantId() != null ) {
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
		hashCode = hashCode + getRequiredTenantId().hashCode();
		if( getRequiredName() != null ) {
			hashCode = hashCode + getRequiredName().hashCode();
		}
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFSecSecTentGrp) {
			ICFSecSecTentGrp rhs = (ICFSecSecTentGrp)obj;
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
			if (getRequiredTenantId() != null) {
				if (rhs.getRequiredTenantId() != null) {
					cmp = getRequiredTenantId().compareTo( rhs.getRequiredTenantId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTenantId() != null) {
				return( -1 );
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
			return( 0 );
		}
		else if (obj instanceof ICFSecSecTentGrpHPKey) {
			ICFSecSecTentGrpHPKey rhs = (ICFSecSecTentGrpHPKey)obj;
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
		else if( obj instanceof ICFSecSecTentGrpH ) {
			ICFSecSecTentGrpH rhs = (ICFSecSecTentGrpH)obj;
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
			if (getRequiredTenantId() != null) {
				if (rhs.getRequiredTenantId() != null) {
					cmp = getRequiredTenantId().compareTo( rhs.getRequiredTenantId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTenantId() != null) {
				return( -1 );
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
			return( 0 );
		}
		else if (obj instanceof ICFSecSecTentGrpByTenantIdxKey) {
			ICFSecSecTentGrpByTenantIdxKey rhs = (ICFSecSecTentGrpByTenantIdxKey)obj;
			if (getRequiredTenantId() != null) {
				if (rhs.getRequiredTenantId() != null) {
					cmp = getRequiredTenantId().compareTo( rhs.getRequiredTenantId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTenantId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecSecTentGrpByNameIdxKey) {
			ICFSecSecTentGrpByNameIdxKey rhs = (ICFSecSecTentGrpByNameIdxKey)obj;
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
		else if (obj instanceof ICFSecSecTentGrpByUNameIdxKey) {
			ICFSecSecTentGrpByUNameIdxKey rhs = (ICFSecSecTentGrpByUNameIdxKey)obj;
			if (getRequiredTenantId() != null) {
				if (rhs.getRequiredTenantId() != null) {
					cmp = getRequiredTenantId().compareTo( rhs.getRequiredTenantId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTenantId() != null) {
				return( -1 );
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
	public void set( ICFSecSecTentGrp src ) {
		setSecTentGrp( src );
	}

	@Override
	public void setSecTentGrp( ICFSecSecTentGrp src ) {
		setRequiredSecTentGrpId(src.getRequiredSecTentGrpId());
		setRequiredRevision( src.getRequiredRevision() );
		setCreatedByUserId( src.getCreatedByUserId() );
		setCreatedAt( src.getCreatedAt() );
		setUpdatedByUserId( src.getUpdatedByUserId() );
		setUpdatedAt( src.getUpdatedAt() );
		setRequiredOwnerTenant(src.getRequiredOwnerTenant());
		setRequiredName(src.getRequiredName());
	}

	@Override
	public void set( ICFSecSecTentGrpH src ) {
		setSecTentGrp( src );
	}

	@Override
	public void setSecTentGrp( ICFSecSecTentGrpH src ) {
		setRequiredSecTentGrpId(src.getRequiredSecTentGrpId());
		setRequiredOwnerTenant(src.getRequiredTenantId());
		setRequiredName(src.getRequiredName());
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = ""
			+ " RequiredSecTentGrpId=" + "\"" + getRequiredSecTentGrpId().toString() + "\""
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredSecTentGrpId=" + "\"" + getRequiredSecTentGrpId().toString() + "\""
			+ " RequiredTenantId=" + "\"" + getRequiredTenantId().toString() + "\""
			+ " RequiredName=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredName() ) + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecJpaSecTentGrp" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

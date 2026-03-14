// Description: Java 25 JPA implementation of a TSecGrpMemb entity definition object.

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
	name = "TSecMemb", schema = "CFSec31",
	indexes = {
		@Index(name = "TSecGrpMembIdIdx", columnList = "TSecGrpMembId", unique = true),
		@Index(name = "TSecGrpMembTenantIdx", columnList = "TenantId", unique = false),
		@Index(name = "TSecGrpMembGroupIdx", columnList = "TSecGroupId", unique = false),
		@Index(name = "TSecGrpMembUserIdx", columnList = "SecUserId", unique = false),
		@Index(name = "TSecGrpMembUUserIdx", columnList = "TenantId, TSecGroupId, SecUserId", unique = true)
	}
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaTSecGrpMemb
	implements Comparable<Object>,
		ICFSecTSecGrpMemb,
		Serializable
{
	@Id
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="TSecGrpMembId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredTSecGrpMembId;
	protected int requiredRevision;

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn( name="TSecGroupId" )
	protected CFSecJpaTSecGroup requiredContainerGroup;
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn( name="SecUserId" )
	protected CFSecJpaSecUser requiredParentUser;

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column = @Column( name="CreatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 createdByUserId = CFLibDbKeyHash256.fromHex(ICFSecTSecGrpMemb.S_INIT_CREATED_BY);

	@Column(name="CreatedAt", nullable=false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column= @Column( name="UpdatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 updatedByUserId = CFLibDbKeyHash256.fromHex(ICFSecTSecGrpMemb.S_INIT_UPDATED_BY);

	@Column(name="UpdatedAt", nullable=false)
	protected LocalDateTime updatedAt = LocalDateTime.now();
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="TenantId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredTenantId;

	public CFSecJpaTSecGrpMemb() {
		requiredTSecGrpMembId = CFLibDbKeyHash256.fromHex( ICFSecTSecGrpMemb.TSECGRPMEMBID_INIT_VALUE.toString() );
		requiredTenantId = CFLibDbKeyHash256.fromHex( ICFSecTSecGrpMemb.TENANTID_INIT_VALUE.toString() );
	}

	@Override
	public int getClassCode() {
		return( ICFSecTSecGrpMemb.CLASS_CODE );
	}

	@Override
	public ICFSecTenant getRequiredOwnerTenant() {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredOwnerTenant", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecTenantTable targetTable = targetBackingSchema.getTableTenant();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredOwnerTenant", 0, "ICFSecSchema.getBackingCFSec().getTableTenant()");
		}
		ICFSecTenant targetRec = targetTable.readDerivedByIdIdx(null, getRequiredTenantId());
		return(targetRec);
	}
	@Override
	public void setRequiredOwnerTenant(ICFSecTenant argObj) {
		if(argObj == null) {
			throw new CFLibNullArgumentException(getClass(), "setOwnerTenant", 1, "argObj");
		}
		else {
			requiredTenantId = argObj.getRequiredId();
		}
	}

	@Override
	public void setRequiredOwnerTenant(CFLibDbKeyHash256 argTenantId) {
		requiredTenantId = argTenantId;
	}

	@Override
	public ICFSecTSecGroup getRequiredContainerGroup() {
		return( requiredContainerGroup );
	}
	@Override
	public void setRequiredContainerGroup(ICFSecTSecGroup argObj) {
		if(argObj == null) {
			throw new CFLibNullArgumentException(getClass(), "setContainerGroup", 1, "argObj");
		}
		else if (argObj instanceof CFSecJpaTSecGroup) {
			requiredContainerGroup = (CFSecJpaTSecGroup)argObj;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "setContainerGroup", "argObj", argObj, "CFSecJpaTSecGroup");
		}
	}

	@Override
	public void setRequiredContainerGroup(CFLibDbKeyHash256 argTSecGroupId) {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredContainerGroup", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecTSecGroupTable targetTable = targetBackingSchema.getTableTSecGroup();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredContainerGroup", 0, "ICFSecSchema.getBackingCFSec().getTableTSecGroup()");
		}
		ICFSecTSecGroup targetRec = targetTable.readDerived(null, argTSecGroupId);
		setRequiredContainerGroup(targetRec);
	}

	@Override
	public ICFSecSecUser getRequiredParentUser() {
		return( requiredParentUser );
	}
	@Override
	public void setRequiredParentUser(ICFSecSecUser argObj) {
		if(argObj == null) {
			throw new CFLibNullArgumentException(getClass(), "setParentUser", 1, "argObj");
		}
		else if (argObj instanceof CFSecJpaSecUser) {
			requiredParentUser = (CFSecJpaSecUser)argObj;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "setParentUser", "argObj", argObj, "CFSecJpaSecUser");
		}
	}

	@Override
	public void setRequiredParentUser(CFLibDbKeyHash256 argSecUserId) {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredParentUser", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecSecUserTable targetTable = targetBackingSchema.getTableSecUser();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredParentUser", 0, "ICFSecSchema.getBackingCFSec().getTableSecUser()");
		}
		ICFSecSecUser targetRec = targetTable.readDerived(null, argSecUserId);
		setRequiredParentUser(targetRec);
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
		return getRequiredTSecGrpMembId();
	}

	@Override
	public void setPKey(CFLibDbKeyHash256 requiredTSecGrpMembId) {
		if (requiredTSecGrpMembId != null) {
			setRequiredTSecGrpMembId(requiredTSecGrpMembId);
		}
	}
	
	@Override
	public CFLibDbKeyHash256 getRequiredTSecGrpMembId() {
		return( requiredTSecGrpMembId );
	}

	@Override
	public void setRequiredTSecGrpMembId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredTSecGrpMembId",
				1,
				"value" );
		}
		requiredTSecGrpMembId = value;
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
		return( requiredTenantId );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredTSecGroupId() {
		ICFSecTSecGroup result = getRequiredContainerGroup();
		if (result != null) {
			return result.getRequiredTSecGroupId();
		}
		else {
			return( ICFSecTSecGroup.TSECGROUPID_INIT_VALUE );
		}
	}

	@Override
	public CFLibDbKeyHash256 getRequiredSecUserId() {
		ICFSecSecUser result = getRequiredParentUser();
		if (result != null) {
			return result.getRequiredSecUserId();
		}
		else {
			return( ICFSecSecUser.SECUSERID_INIT_VALUE );
		}
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecTSecGrpMemb) {
			ICFSecTSecGrpMemb rhs = (ICFSecTSecGrpMemb)obj;
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
			if( getRequiredTSecGrpMembId() != null ) {
				if( rhs.getRequiredTSecGrpMembId() != null ) {
					if( ! getRequiredTSecGrpMembId().equals( rhs.getRequiredTSecGrpMembId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTSecGrpMembId() != null ) {
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
			if( getRequiredTSecGroupId() != null ) {
				if( rhs.getRequiredTSecGroupId() != null ) {
					if( ! getRequiredTSecGroupId().equals( rhs.getRequiredTSecGroupId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTSecGroupId() != null ) {
					return( false );
				}
			}
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
			return( true );
		}
		else if (obj instanceof ICFSecTSecGrpMembH) {
			ICFSecTSecGrpMembH rhs = (ICFSecTSecGrpMembH)obj;
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
			if( getRequiredTSecGrpMembId() != null ) {
				if( rhs.getRequiredTSecGrpMembId() != null ) {
					if( ! getRequiredTSecGrpMembId().equals( rhs.getRequiredTSecGrpMembId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTSecGrpMembId() != null ) {
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
			if( getRequiredTSecGroupId() != null ) {
				if( rhs.getRequiredTSecGroupId() != null ) {
					if( ! getRequiredTSecGroupId().equals( rhs.getRequiredTSecGroupId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTSecGroupId() != null ) {
					return( false );
				}
			}
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
			return( true );
		}
		else if (obj instanceof ICFSecTSecGrpMembHPKey) {
			ICFSecTSecGrpMembHPKey rhs = (ICFSecTSecGrpMembHPKey)obj;
			if( getRequiredTSecGrpMembId() != null ) {
				if( rhs.getRequiredTSecGrpMembId() != null ) {
					if( ! getRequiredTSecGrpMembId().equals( rhs.getRequiredTSecGrpMembId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTSecGrpMembId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecTSecGrpMembByTenantIdxKey) {
			ICFSecTSecGrpMembByTenantIdxKey rhs = (ICFSecTSecGrpMembByTenantIdxKey)obj;
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
		else if (obj instanceof ICFSecTSecGrpMembByGroupIdxKey) {
			ICFSecTSecGrpMembByGroupIdxKey rhs = (ICFSecTSecGrpMembByGroupIdxKey)obj;
			if( getRequiredTSecGroupId() != null ) {
				if( rhs.getRequiredTSecGroupId() != null ) {
					if( ! getRequiredTSecGroupId().equals( rhs.getRequiredTSecGroupId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTSecGroupId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecTSecGrpMembByUserIdxKey) {
			ICFSecTSecGrpMembByUserIdxKey rhs = (ICFSecTSecGrpMembByUserIdxKey)obj;
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
			return( true );
		}
		else if (obj instanceof ICFSecTSecGrpMembByUUserIdxKey) {
			ICFSecTSecGrpMembByUUserIdxKey rhs = (ICFSecTSecGrpMembByUUserIdxKey)obj;
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
			if( getRequiredTSecGroupId() != null ) {
				if( rhs.getRequiredTSecGroupId() != null ) {
					if( ! getRequiredTSecGroupId().equals( rhs.getRequiredTSecGroupId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTSecGroupId() != null ) {
					return( false );
				}
			}
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
		hashCode = hashCode + getRequiredTSecGrpMembId().hashCode();
		hashCode = hashCode + getRequiredTenantId().hashCode();
		hashCode = hashCode + getRequiredTSecGroupId().hashCode();
		hashCode = hashCode + getRequiredSecUserId().hashCode();
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFSecTSecGrpMemb) {
			ICFSecTSecGrpMemb rhs = (ICFSecTSecGrpMemb)obj;
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
			if (getRequiredTSecGroupId() != null) {
				if (rhs.getRequiredTSecGroupId() != null) {
					cmp = getRequiredTSecGroupId().compareTo( rhs.getRequiredTSecGroupId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTSecGroupId() != null) {
				return( -1 );
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
			return( 0 );
		}
		else if (obj instanceof ICFSecTSecGrpMembHPKey) {
			ICFSecTSecGrpMembHPKey rhs = (ICFSecTSecGrpMembHPKey)obj;
			if (getRequiredTSecGrpMembId() != null) {
				if (rhs.getRequiredTSecGrpMembId() != null) {
					cmp = getRequiredTSecGrpMembId().compareTo( rhs.getRequiredTSecGrpMembId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTSecGrpMembId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if( obj instanceof ICFSecTSecGrpMembH ) {
			ICFSecTSecGrpMembH rhs = (ICFSecTSecGrpMembH)obj;
			if (getRequiredTSecGrpMembId() != null) {
				if (rhs.getRequiredTSecGrpMembId() != null) {
					cmp = getRequiredTSecGrpMembId().compareTo( rhs.getRequiredTSecGrpMembId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTSecGrpMembId() != null) {
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
			if (getRequiredTSecGroupId() != null) {
				if (rhs.getRequiredTSecGroupId() != null) {
					cmp = getRequiredTSecGroupId().compareTo( rhs.getRequiredTSecGroupId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTSecGroupId() != null) {
				return( -1 );
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
			return( 0 );
		}
		else if (obj instanceof ICFSecTSecGrpMembByTenantIdxKey) {
			ICFSecTSecGrpMembByTenantIdxKey rhs = (ICFSecTSecGrpMembByTenantIdxKey)obj;
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
		else if (obj instanceof ICFSecTSecGrpMembByGroupIdxKey) {
			ICFSecTSecGrpMembByGroupIdxKey rhs = (ICFSecTSecGrpMembByGroupIdxKey)obj;
			if (getRequiredTSecGroupId() != null) {
				if (rhs.getRequiredTSecGroupId() != null) {
					cmp = getRequiredTSecGroupId().compareTo( rhs.getRequiredTSecGroupId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTSecGroupId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecTSecGrpMembByUserIdxKey) {
			ICFSecTSecGrpMembByUserIdxKey rhs = (ICFSecTSecGrpMembByUserIdxKey)obj;
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
			return( 0 );
		}
		else if (obj instanceof ICFSecTSecGrpMembByUUserIdxKey) {
			ICFSecTSecGrpMembByUUserIdxKey rhs = (ICFSecTSecGrpMembByUUserIdxKey)obj;
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
			if (getRequiredTSecGroupId() != null) {
				if (rhs.getRequiredTSecGroupId() != null) {
					cmp = getRequiredTSecGroupId().compareTo( rhs.getRequiredTSecGroupId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTSecGroupId() != null) {
				return( -1 );
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
	public void set( ICFSecTSecGrpMemb src ) {
		setTSecGrpMemb( src );
	}

	@Override
	public void setTSecGrpMemb( ICFSecTSecGrpMemb src ) {
		setRequiredTSecGrpMembId(src.getRequiredTSecGrpMembId());
		setRequiredRevision( src.getRequiredRevision() );
		setCreatedByUserId( src.getCreatedByUserId() );
		setCreatedAt( src.getCreatedAt() );
		setUpdatedByUserId( src.getUpdatedByUserId() );
		setUpdatedAt( src.getUpdatedAt() );
		setRequiredOwnerTenant(src.getRequiredOwnerTenant());
		setRequiredContainerGroup(src.getRequiredContainerGroup());
		setRequiredParentUser(src.getRequiredParentUser());
	}

	@Override
	public void set( ICFSecTSecGrpMembH src ) {
		setTSecGrpMemb( src );
	}

	@Override
	public void setTSecGrpMemb( ICFSecTSecGrpMembH src ) {
		setRequiredTSecGrpMembId(src.getRequiredTSecGrpMembId());
		setRequiredOwnerTenant(src.getRequiredTenantId());
		setRequiredContainerGroup(src.getRequiredTSecGroupId());
		setRequiredParentUser(src.getRequiredSecUserId());
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = ""
			+ " RequiredTSecGrpMembId=" + "\"" + getRequiredTSecGrpMembId().toString() + "\""
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredTSecGrpMembId=" + "\"" + getRequiredTSecGrpMembId().toString() + "\""
			+ " RequiredTenantId=" + "\"" + getRequiredTenantId().toString() + "\""
			+ " RequiredTSecGroupId=" + "\"" + getRequiredTSecGroupId().toString() + "\""
			+ " RequiredSecUserId=" + "\"" + getRequiredSecUserId().toString() + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecJpaTSecGrpMemb" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

// Description: Java 25 JPA implementation of a SecGrpMemb entity definition object.

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
	name = "SecMemb", schema = "CFSec31",
	indexes = {
		@Index(name = "SecGrpMembIdIdx", columnList = "SecGrpMembId", unique = true),
		@Index(name = "SecGrpMembClusterIdx", columnList = "ClusterId", unique = false),
		@Index(name = "SecGrpMembGroupIdx", columnList = "SecGroupId", unique = false),
		@Index(name = "SecGrpMembUserIdx", columnList = "SecUserId", unique = false),
		@Index(name = "SecGrpMembUUserIdx", columnList = "ClusterId, SecGroupId, SecUserId", unique = true)
	}
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaSecGrpMemb
	implements Comparable<Object>,
		ICFSecSecGrpMemb,
		Serializable
{
	@Id
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="SecGrpMembId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredSecGrpMembId;
	protected int requiredRevision;

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn( name="SecGroupId" )
	protected CFSecJpaSecGroup requiredContainerGroup;
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn( name="SecUserId" )
	protected CFSecJpaSecUser requiredParentUser;

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column = @Column( name="CreatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 createdByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecGrpMemb.S_INIT_CREATED_BY);

	@Column(name="CreatedAt", nullable=false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column= @Column( name="UpdatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 updatedByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecGrpMemb.S_INIT_UPDATED_BY);

	@Column(name="UpdatedAt", nullable=false)
	protected LocalDateTime updatedAt = LocalDateTime.now();
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="ClusterId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredClusterId;

	public CFSecJpaSecGrpMemb() {
		requiredSecGrpMembId = CFLibDbKeyHash256.fromHex( ICFSecSecGrpMemb.SECGRPMEMBID_INIT_VALUE.toString() );
		requiredClusterId = CFLibDbKeyHash256.fromHex( ICFSecSecGrpMemb.CLUSTERID_INIT_VALUE.toString() );
	}

	@Override
	public int getClassCode() {
		return( ICFSecSecGrpMemb.CLASS_CODE );
	}

	@Override
	public ICFSecCluster getRequiredOwnerCluster() {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredOwnerCluster", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecClusterTable targetTable = targetBackingSchema.getTableCluster();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredOwnerCluster", 0, "ICFSecSchema.getBackingCFSec().getTableCluster()");
		}
		ICFSecCluster targetRec = targetTable.readDerivedByIdIdx(null, getRequiredClusterId());
		return(targetRec);
	}
	@Override
	public void setRequiredOwnerCluster(ICFSecCluster argObj) {
		if(argObj == null) {
			throw new CFLibNullArgumentException(getClass(), "setOwnerCluster", 1, "argObj");
		}
		else {
			requiredClusterId = argObj.getRequiredId();
		}
	}

	@Override
	public void setRequiredOwnerCluster(CFLibDbKeyHash256 argClusterId) {
		requiredClusterId = argClusterId;
	}

	@Override
	public ICFSecSecGroup getRequiredContainerGroup() {
		return( requiredContainerGroup );
	}
	@Override
	public void setRequiredContainerGroup(ICFSecSecGroup argObj) {
		if(argObj == null) {
			throw new CFLibNullArgumentException(getClass(), "setContainerGroup", 1, "argObj");
		}
		else if (argObj instanceof CFSecJpaSecGroup) {
			requiredContainerGroup = (CFSecJpaSecGroup)argObj;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "setContainerGroup", "argObj", argObj, "CFSecJpaSecGroup");
		}
	}

	@Override
	public void setRequiredContainerGroup(CFLibDbKeyHash256 argSecGroupId) {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredContainerGroup", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecSecGroupTable targetTable = targetBackingSchema.getTableSecGroup();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredContainerGroup", 0, "ICFSecSchema.getBackingCFSec().getTableSecGroup()");
		}
		ICFSecSecGroup targetRec = targetTable.readDerived(null, argSecGroupId);
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
		return getRequiredSecGrpMembId();
	}

	@Override
	public void setPKey(CFLibDbKeyHash256 requiredSecGrpMembId) {
		if (requiredSecGrpMembId != null) {
			setRequiredSecGrpMembId(requiredSecGrpMembId);
		}
	}
	
	@Override
	public CFLibDbKeyHash256 getRequiredSecGrpMembId() {
		return( requiredSecGrpMembId );
	}

	@Override
	public void setRequiredSecGrpMembId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredSecGrpMembId",
				1,
				"value" );
		}
		requiredSecGrpMembId = value;
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
	public CFLibDbKeyHash256 getRequiredClusterId() {
		return( requiredClusterId );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredSecGroupId() {
		ICFSecSecGroup result = getRequiredContainerGroup();
		if (result != null) {
			return result.getRequiredSecGroupId();
		}
		else {
			return( ICFSecSecGroup.SECGROUPID_INIT_VALUE );
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
		else if (obj instanceof ICFSecSecGrpMemb) {
			ICFSecSecGrpMemb rhs = (ICFSecSecGrpMemb)obj;
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
			if( getRequiredSecGrpMembId() != null ) {
				if( rhs.getRequiredSecGrpMembId() != null ) {
					if( ! getRequiredSecGrpMembId().equals( rhs.getRequiredSecGrpMembId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecGrpMembId() != null ) {
					return( false );
				}
			}
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
			if( getRequiredSecGroupId() != null ) {
				if( rhs.getRequiredSecGroupId() != null ) {
					if( ! getRequiredSecGroupId().equals( rhs.getRequiredSecGroupId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecGroupId() != null ) {
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
		else if (obj instanceof ICFSecSecGrpMembH) {
			ICFSecSecGrpMembH rhs = (ICFSecSecGrpMembH)obj;
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
			if( getRequiredSecGrpMembId() != null ) {
				if( rhs.getRequiredSecGrpMembId() != null ) {
					if( ! getRequiredSecGrpMembId().equals( rhs.getRequiredSecGrpMembId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecGrpMembId() != null ) {
					return( false );
				}
			}
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
			if( getRequiredSecGroupId() != null ) {
				if( rhs.getRequiredSecGroupId() != null ) {
					if( ! getRequiredSecGroupId().equals( rhs.getRequiredSecGroupId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecGroupId() != null ) {
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
		else if (obj instanceof ICFSecSecGrpMembHPKey) {
			ICFSecSecGrpMembHPKey rhs = (ICFSecSecGrpMembHPKey)obj;
			if( getRequiredSecGrpMembId() != null ) {
				if( rhs.getRequiredSecGrpMembId() != null ) {
					if( ! getRequiredSecGrpMembId().equals( rhs.getRequiredSecGrpMembId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecGrpMembId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecGrpMembByClusterIdxKey) {
			ICFSecSecGrpMembByClusterIdxKey rhs = (ICFSecSecGrpMembByClusterIdxKey)obj;
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
			return( true );
		}
		else if (obj instanceof ICFSecSecGrpMembByGroupIdxKey) {
			ICFSecSecGrpMembByGroupIdxKey rhs = (ICFSecSecGrpMembByGroupIdxKey)obj;
			if( getRequiredSecGroupId() != null ) {
				if( rhs.getRequiredSecGroupId() != null ) {
					if( ! getRequiredSecGroupId().equals( rhs.getRequiredSecGroupId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecGroupId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecGrpMembByUserIdxKey) {
			ICFSecSecGrpMembByUserIdxKey rhs = (ICFSecSecGrpMembByUserIdxKey)obj;
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
		else if (obj instanceof ICFSecSecGrpMembByUUserIdxKey) {
			ICFSecSecGrpMembByUUserIdxKey rhs = (ICFSecSecGrpMembByUUserIdxKey)obj;
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
			if( getRequiredSecGroupId() != null ) {
				if( rhs.getRequiredSecGroupId() != null ) {
					if( ! getRequiredSecGroupId().equals( rhs.getRequiredSecGroupId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecGroupId() != null ) {
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
		hashCode = hashCode + getRequiredSecGrpMembId().hashCode();
		hashCode = hashCode + getRequiredClusterId().hashCode();
		hashCode = hashCode + getRequiredSecGroupId().hashCode();
		hashCode = hashCode + getRequiredSecUserId().hashCode();
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFSecSecGrpMemb) {
			ICFSecSecGrpMemb rhs = (ICFSecSecGrpMemb)obj;
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
			if (getRequiredSecGroupId() != null) {
				if (rhs.getRequiredSecGroupId() != null) {
					cmp = getRequiredSecGroupId().compareTo( rhs.getRequiredSecGroupId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecGroupId() != null) {
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
		else if (obj instanceof ICFSecSecGrpMembHPKey) {
			ICFSecSecGrpMembHPKey rhs = (ICFSecSecGrpMembHPKey)obj;
			if (getRequiredSecGrpMembId() != null) {
				if (rhs.getRequiredSecGrpMembId() != null) {
					cmp = getRequiredSecGrpMembId().compareTo( rhs.getRequiredSecGrpMembId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecGrpMembId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if( obj instanceof ICFSecSecGrpMembH ) {
			ICFSecSecGrpMembH rhs = (ICFSecSecGrpMembH)obj;
			if (getRequiredSecGrpMembId() != null) {
				if (rhs.getRequiredSecGrpMembId() != null) {
					cmp = getRequiredSecGrpMembId().compareTo( rhs.getRequiredSecGrpMembId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecGrpMembId() != null) {
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
			if (getRequiredSecGroupId() != null) {
				if (rhs.getRequiredSecGroupId() != null) {
					cmp = getRequiredSecGroupId().compareTo( rhs.getRequiredSecGroupId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecGroupId() != null) {
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
		else if (obj instanceof ICFSecSecGrpMembByClusterIdxKey) {
			ICFSecSecGrpMembByClusterIdxKey rhs = (ICFSecSecGrpMembByClusterIdxKey)obj;
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
			return( 0 );
		}
		else if (obj instanceof ICFSecSecGrpMembByGroupIdxKey) {
			ICFSecSecGrpMembByGroupIdxKey rhs = (ICFSecSecGrpMembByGroupIdxKey)obj;
			if (getRequiredSecGroupId() != null) {
				if (rhs.getRequiredSecGroupId() != null) {
					cmp = getRequiredSecGroupId().compareTo( rhs.getRequiredSecGroupId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecGroupId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecSecGrpMembByUserIdxKey) {
			ICFSecSecGrpMembByUserIdxKey rhs = (ICFSecSecGrpMembByUserIdxKey)obj;
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
		else if (obj instanceof ICFSecSecGrpMembByUUserIdxKey) {
			ICFSecSecGrpMembByUUserIdxKey rhs = (ICFSecSecGrpMembByUUserIdxKey)obj;
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
			if (getRequiredSecGroupId() != null) {
				if (rhs.getRequiredSecGroupId() != null) {
					cmp = getRequiredSecGroupId().compareTo( rhs.getRequiredSecGroupId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecGroupId() != null) {
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
	public void set( ICFSecSecGrpMemb src ) {
		setSecGrpMemb( src );
	}

	@Override
	public void setSecGrpMemb( ICFSecSecGrpMemb src ) {
		setRequiredSecGrpMembId(src.getRequiredSecGrpMembId());
		setRequiredRevision( src.getRequiredRevision() );
		setCreatedByUserId( src.getCreatedByUserId() );
		setCreatedAt( src.getCreatedAt() );
		setUpdatedByUserId( src.getUpdatedByUserId() );
		setUpdatedAt( src.getUpdatedAt() );
		setRequiredOwnerCluster(src.getRequiredOwnerCluster());
		setRequiredContainerGroup(src.getRequiredContainerGroup());
		setRequiredParentUser(src.getRequiredParentUser());
	}

	@Override
	public void set( ICFSecSecGrpMembH src ) {
		setSecGrpMemb( src );
	}

	@Override
	public void setSecGrpMemb( ICFSecSecGrpMembH src ) {
		setRequiredSecGrpMembId(src.getRequiredSecGrpMembId());
		setRequiredOwnerCluster(src.getRequiredClusterId());
		setRequiredContainerGroup(src.getRequiredSecGroupId());
		setRequiredParentUser(src.getRequiredSecUserId());
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = ""
			+ " RequiredSecGrpMembId=" + "\"" + getRequiredSecGrpMembId().toString() + "\""
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredSecGrpMembId=" + "\"" + getRequiredSecGrpMembId().toString() + "\""
			+ " RequiredClusterId=" + "\"" + getRequiredClusterId().toString() + "\""
			+ " RequiredSecGroupId=" + "\"" + getRequiredSecGroupId().toString() + "\""
			+ " RequiredSecUserId=" + "\"" + getRequiredSecUserId().toString() + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecJpaSecGrpMemb" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

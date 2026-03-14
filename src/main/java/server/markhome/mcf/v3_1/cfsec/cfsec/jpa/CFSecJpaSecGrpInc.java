// Description: Java 25 JPA implementation of a SecGrpInc entity definition object.

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
	name = "SecInc", schema = "CFSec31",
	indexes = {
		@Index(name = "SecGrpIncIdIdx", columnList = "SecGrpIncId", unique = true),
		@Index(name = "SecGrpIncClusterIdx", columnList = "ClusId", unique = false),
		@Index(name = "SecGrpIncGroupIdx", columnList = "SecGrpId", unique = false),
		@Index(name = "SecGrpIncIncludeIdx", columnList = "IncGrpId", unique = false),
		@Index(name = "SecGrpIncUIncludeIdx", columnList = "ClusId, SecGrpId, IncGrpId", unique = true)
	}
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaSecGrpInc
	implements Comparable<Object>,
		ICFSecSecGrpInc,
		Serializable
{
	@Id
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="SecGrpIncId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredSecGrpIncId;
	protected int requiredRevision;

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn( name="SecGrpId" )
	protected CFSecJpaSecGroup requiredContainerGroup;
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn( name="IncGrpId" )
	protected CFSecJpaSecGroup requiredParentSubGroup;

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column = @Column( name="CreatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 createdByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecGrpInc.S_INIT_CREATED_BY);

	@Column(name="CreatedAt", nullable=false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column= @Column( name="UpdatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 updatedByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecGrpInc.S_INIT_UPDATED_BY);

	@Column(name="UpdatedAt", nullable=false)
	protected LocalDateTime updatedAt = LocalDateTime.now();
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="ClusId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredClusterId;

	public CFSecJpaSecGrpInc() {
		requiredSecGrpIncId = CFLibDbKeyHash256.fromHex( ICFSecSecGrpInc.SECGRPINCID_INIT_VALUE.toString() );
		requiredClusterId = CFLibDbKeyHash256.fromHex( ICFSecSecGrpInc.CLUSTERID_INIT_VALUE.toString() );
	}

	@Override
	public int getClassCode() {
		return( ICFSecSecGrpInc.CLASS_CODE );
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
	public ICFSecSecGroup getRequiredParentSubGroup() {
		return( requiredParentSubGroup );
	}
	@Override
	public void setRequiredParentSubGroup(ICFSecSecGroup argObj) {
		if(argObj == null) {
			throw new CFLibNullArgumentException(getClass(), "setParentSubGroup", 1, "argObj");
		}
		else if (argObj instanceof CFSecJpaSecGroup) {
			requiredParentSubGroup = (CFSecJpaSecGroup)argObj;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "setParentSubGroup", "argObj", argObj, "CFSecJpaSecGroup");
		}
	}

	@Override
	public void setRequiredParentSubGroup(CFLibDbKeyHash256 argIncludeGroupId) {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredParentSubGroup", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecSecGroupTable targetTable = targetBackingSchema.getTableSecGroup();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredParentSubGroup", 0, "ICFSecSchema.getBackingCFSec().getTableSecGroup()");
		}
		ICFSecSecGroup targetRec = targetTable.readDerived(null, argIncludeGroupId);
		setRequiredParentSubGroup(targetRec);
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
		return getRequiredSecGrpIncId();
	}

	@Override
	public void setPKey(CFLibDbKeyHash256 requiredSecGrpIncId) {
		if (requiredSecGrpIncId != null) {
			setRequiredSecGrpIncId(requiredSecGrpIncId);
		}
	}
	
	@Override
	public CFLibDbKeyHash256 getRequiredSecGrpIncId() {
		return( requiredSecGrpIncId );
	}

	@Override
	public void setRequiredSecGrpIncId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredSecGrpIncId",
				1,
				"value" );
		}
		requiredSecGrpIncId = value;
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
	public CFLibDbKeyHash256 getRequiredIncludeGroupId() {
		ICFSecSecGroup result = getRequiredParentSubGroup();
		if (result != null) {
			return result.getRequiredSecGroupId();
		}
		else {
			return( ICFSecSecGroup.SECGROUPID_INIT_VALUE );
		}
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecSecGrpInc) {
			ICFSecSecGrpInc rhs = (ICFSecSecGrpInc)obj;
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
			if( getRequiredSecGrpIncId() != null ) {
				if( rhs.getRequiredSecGrpIncId() != null ) {
					if( ! getRequiredSecGrpIncId().equals( rhs.getRequiredSecGrpIncId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecGrpIncId() != null ) {
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
			if( getRequiredIncludeGroupId() != null ) {
				if( rhs.getRequiredIncludeGroupId() != null ) {
					if( ! getRequiredIncludeGroupId().equals( rhs.getRequiredIncludeGroupId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredIncludeGroupId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecGrpIncH) {
			ICFSecSecGrpIncH rhs = (ICFSecSecGrpIncH)obj;
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
			if( getRequiredSecGrpIncId() != null ) {
				if( rhs.getRequiredSecGrpIncId() != null ) {
					if( ! getRequiredSecGrpIncId().equals( rhs.getRequiredSecGrpIncId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecGrpIncId() != null ) {
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
			if( getRequiredIncludeGroupId() != null ) {
				if( rhs.getRequiredIncludeGroupId() != null ) {
					if( ! getRequiredIncludeGroupId().equals( rhs.getRequiredIncludeGroupId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredIncludeGroupId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecGrpIncHPKey) {
			ICFSecSecGrpIncHPKey rhs = (ICFSecSecGrpIncHPKey)obj;
			if( getRequiredSecGrpIncId() != null ) {
				if( rhs.getRequiredSecGrpIncId() != null ) {
					if( ! getRequiredSecGrpIncId().equals( rhs.getRequiredSecGrpIncId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecGrpIncId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecGrpIncByClusterIdxKey) {
			ICFSecSecGrpIncByClusterIdxKey rhs = (ICFSecSecGrpIncByClusterIdxKey)obj;
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
		else if (obj instanceof ICFSecSecGrpIncByGroupIdxKey) {
			ICFSecSecGrpIncByGroupIdxKey rhs = (ICFSecSecGrpIncByGroupIdxKey)obj;
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
		else if (obj instanceof ICFSecSecGrpIncByIncludeIdxKey) {
			ICFSecSecGrpIncByIncludeIdxKey rhs = (ICFSecSecGrpIncByIncludeIdxKey)obj;
			if( getRequiredIncludeGroupId() != null ) {
				if( rhs.getRequiredIncludeGroupId() != null ) {
					if( ! getRequiredIncludeGroupId().equals( rhs.getRequiredIncludeGroupId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredIncludeGroupId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecGrpIncByUIncludeIdxKey) {
			ICFSecSecGrpIncByUIncludeIdxKey rhs = (ICFSecSecGrpIncByUIncludeIdxKey)obj;
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
			if( getRequiredIncludeGroupId() != null ) {
				if( rhs.getRequiredIncludeGroupId() != null ) {
					if( ! getRequiredIncludeGroupId().equals( rhs.getRequiredIncludeGroupId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredIncludeGroupId() != null ) {
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
		hashCode = hashCode + getRequiredSecGrpIncId().hashCode();
		hashCode = hashCode + getRequiredClusterId().hashCode();
		hashCode = hashCode + getRequiredSecGroupId().hashCode();
		hashCode = hashCode + getRequiredIncludeGroupId().hashCode();
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFSecSecGrpInc) {
			ICFSecSecGrpInc rhs = (ICFSecSecGrpInc)obj;
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
			if (getRequiredIncludeGroupId() != null) {
				if (rhs.getRequiredIncludeGroupId() != null) {
					cmp = getRequiredIncludeGroupId().compareTo( rhs.getRequiredIncludeGroupId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredIncludeGroupId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecSecGrpIncHPKey) {
			ICFSecSecGrpIncHPKey rhs = (ICFSecSecGrpIncHPKey)obj;
			if (getRequiredSecGrpIncId() != null) {
				if (rhs.getRequiredSecGrpIncId() != null) {
					cmp = getRequiredSecGrpIncId().compareTo( rhs.getRequiredSecGrpIncId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecGrpIncId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if( obj instanceof ICFSecSecGrpIncH ) {
			ICFSecSecGrpIncH rhs = (ICFSecSecGrpIncH)obj;
			if (getRequiredSecGrpIncId() != null) {
				if (rhs.getRequiredSecGrpIncId() != null) {
					cmp = getRequiredSecGrpIncId().compareTo( rhs.getRequiredSecGrpIncId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecGrpIncId() != null) {
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
			if (getRequiredIncludeGroupId() != null) {
				if (rhs.getRequiredIncludeGroupId() != null) {
					cmp = getRequiredIncludeGroupId().compareTo( rhs.getRequiredIncludeGroupId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredIncludeGroupId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecSecGrpIncByClusterIdxKey) {
			ICFSecSecGrpIncByClusterIdxKey rhs = (ICFSecSecGrpIncByClusterIdxKey)obj;
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
		else if (obj instanceof ICFSecSecGrpIncByGroupIdxKey) {
			ICFSecSecGrpIncByGroupIdxKey rhs = (ICFSecSecGrpIncByGroupIdxKey)obj;
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
		else if (obj instanceof ICFSecSecGrpIncByIncludeIdxKey) {
			ICFSecSecGrpIncByIncludeIdxKey rhs = (ICFSecSecGrpIncByIncludeIdxKey)obj;
			if (getRequiredIncludeGroupId() != null) {
				if (rhs.getRequiredIncludeGroupId() != null) {
					cmp = getRequiredIncludeGroupId().compareTo( rhs.getRequiredIncludeGroupId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredIncludeGroupId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecSecGrpIncByUIncludeIdxKey) {
			ICFSecSecGrpIncByUIncludeIdxKey rhs = (ICFSecSecGrpIncByUIncludeIdxKey)obj;
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
			if (getRequiredIncludeGroupId() != null) {
				if (rhs.getRequiredIncludeGroupId() != null) {
					cmp = getRequiredIncludeGroupId().compareTo( rhs.getRequiredIncludeGroupId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredIncludeGroupId() != null) {
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
	public void set( ICFSecSecGrpInc src ) {
		setSecGrpInc( src );
	}

	@Override
	public void setSecGrpInc( ICFSecSecGrpInc src ) {
		setRequiredSecGrpIncId(src.getRequiredSecGrpIncId());
		setRequiredRevision( src.getRequiredRevision() );
		setCreatedByUserId( src.getCreatedByUserId() );
		setCreatedAt( src.getCreatedAt() );
		setUpdatedByUserId( src.getUpdatedByUserId() );
		setUpdatedAt( src.getUpdatedAt() );
		setRequiredOwnerCluster(src.getRequiredOwnerCluster());
		setRequiredContainerGroup(src.getRequiredContainerGroup());
		setRequiredParentSubGroup(src.getRequiredParentSubGroup());
	}

	@Override
	public void set( ICFSecSecGrpIncH src ) {
		setSecGrpInc( src );
	}

	@Override
	public void setSecGrpInc( ICFSecSecGrpIncH src ) {
		setRequiredSecGrpIncId(src.getRequiredSecGrpIncId());
		setRequiredOwnerCluster(src.getRequiredClusterId());
		setRequiredContainerGroup(src.getRequiredSecGroupId());
		setRequiredParentSubGroup(src.getRequiredIncludeGroupId());
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = ""
			+ " RequiredSecGrpIncId=" + "\"" + getRequiredSecGrpIncId().toString() + "\""
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredSecGrpIncId=" + "\"" + getRequiredSecGrpIncId().toString() + "\""
			+ " RequiredClusterId=" + "\"" + getRequiredClusterId().toString() + "\""
			+ " RequiredSecGroupId=" + "\"" + getRequiredSecGroupId().toString() + "\""
			+ " RequiredIncludeGroupId=" + "\"" + getRequiredIncludeGroupId().toString() + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecJpaSecGrpInc" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

// Description: Java 25 JPA implementation of a Tenant entity definition object.

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
	name = "tenant", schema = "CFSec31",
	indexes = {
		@Index(name = "TenantIdIdx", columnList = "Id", unique = true),
		@Index(name = "TenantClusterIdx", columnList = "ClusterId", unique = false),
		@Index(name = "TenantUNameIdx", columnList = "ClusterId, TenantName", unique = true)
	}
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaTenant
	implements Comparable<Object>,
		ICFSecTenant,
		Serializable
{
	@Id
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="Id", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredId;
	protected int requiredRevision;

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn( name="ClusterId" )
	protected CFSecJpaCluster requiredContainerCluster;

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column = @Column( name="CreatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 createdByUserId = CFLibDbKeyHash256.fromHex(ICFSecTenant.S_INIT_CREATED_BY);

	@Column(name="CreatedAt", nullable=false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column= @Column( name="UpdatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 updatedByUserId = CFLibDbKeyHash256.fromHex(ICFSecTenant.S_INIT_UPDATED_BY);

	@Column(name="UpdatedAt", nullable=false)
	protected LocalDateTime updatedAt = LocalDateTime.now();
	@Column( name="TenantName", nullable=false, length=192 )
	protected String requiredTenantName;

	public CFSecJpaTenant() {
		requiredId = CFLibDbKeyHash256.fromHex( ICFSecTenant.ID_INIT_VALUE.toString() );
		requiredTenantName = ICFSecTenant.TENANTNAME_INIT_VALUE;
	}

	@Override
	public int getClassCode() {
		return( ICFSecTenant.CLASS_CODE );
	}

	@Override
	public List<ICFSecSecTentGrp> getOptionalComponentsSecGroup() {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setOptionalComponentsSecGroup", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecSecTentGrpTable targetTable = targetBackingSchema.getTableSecTentGrp();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setOptionalComponentsSecGroup", 0, "ICFSecSchema.getBackingCFSec().getTableSecTentGrp()");
		}
		ICFSecSecTentGrp[] targetArr = targetTable.readDerivedByTenantIdx(null, getRequiredId());
		if( targetArr != null ) {
			List<ICFSecSecTentGrp> results = new ArrayList<>(targetArr.length);
			for (int idx = 0; idx < targetArr.length; idx++) {
				results.add(targetArr[idx]);
			}
			return( results );
		}
		else {
			List<ICFSecSecTentGrp> results = new ArrayList<>();
			return( results );
		}
	}
	@Override
	public ICFSecCluster getRequiredContainerCluster() {
		return( requiredContainerCluster );
	}
	@Override
	public void setRequiredContainerCluster(ICFSecCluster argObj) {
		if(argObj == null) {
			throw new CFLibNullArgumentException(getClass(), "setContainerCluster", 1, "argObj");
		}
		else if (argObj instanceof CFSecJpaCluster) {
			requiredContainerCluster = (CFSecJpaCluster)argObj;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "setContainerCluster", "argObj", argObj, "CFSecJpaCluster");
		}
	}

	@Override
	public void setRequiredContainerCluster(CFLibDbKeyHash256 argClusterId) {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredContainerCluster", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecClusterTable targetTable = targetBackingSchema.getTableCluster();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredContainerCluster", 0, "ICFSecSchema.getBackingCFSec().getTableCluster()");
		}
		ICFSecCluster targetRec = targetTable.readDerived(null, argClusterId);
		setRequiredContainerCluster(targetRec);
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
		return getRequiredId();
	}

	@Override
	public void setPKey(CFLibDbKeyHash256 requiredId) {
		if (requiredId != null) {
			setRequiredId(requiredId);
		}
	}
	
	@Override
	public CFLibDbKeyHash256 getRequiredId() {
		return( requiredId );
	}

	@Override
	public void setRequiredId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredId",
				1,
				"value" );
		}
		requiredId = value;
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
		ICFSecCluster result = getRequiredContainerCluster();
		if (result != null) {
			return result.getRequiredId();
		}
		else {
			return( ICFSecCluster.ID_INIT_VALUE );
		}
	}

	@Override
	public String getRequiredTenantName() {
		return( requiredTenantName );
	}

	@Override
	public void setRequiredTenantName( String value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredTenantName",
				1,
				"value" );
		}
		else if( value.length() > 192 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredTenantName",
				1,
				"value.length()",
				value.length(),
				192 );
		}
		requiredTenantName = value;
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecTenant) {
			ICFSecTenant rhs = (ICFSecTenant)obj;
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
			if( getRequiredId() != null ) {
				if( rhs.getRequiredId() != null ) {
					if( ! getRequiredId().equals( rhs.getRequiredId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredId() != null ) {
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
			if( getRequiredTenantName() != null ) {
				if( rhs.getRequiredTenantName() != null ) {
					if( ! getRequiredTenantName().equals( rhs.getRequiredTenantName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTenantName() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecTenantH) {
			ICFSecTenantH rhs = (ICFSecTenantH)obj;
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
			if( getRequiredId() != null ) {
				if( rhs.getRequiredId() != null ) {
					if( ! getRequiredId().equals( rhs.getRequiredId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredId() != null ) {
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
			if( getRequiredTenantName() != null ) {
				if( rhs.getRequiredTenantName() != null ) {
					if( ! getRequiredTenantName().equals( rhs.getRequiredTenantName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTenantName() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecTenantHPKey) {
			ICFSecTenantHPKey rhs = (ICFSecTenantHPKey)obj;
			if( getRequiredId() != null ) {
				if( rhs.getRequiredId() != null ) {
					if( ! getRequiredId().equals( rhs.getRequiredId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecTenantByClusterIdxKey) {
			ICFSecTenantByClusterIdxKey rhs = (ICFSecTenantByClusterIdxKey)obj;
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
		else if (obj instanceof ICFSecTenantByUNameIdxKey) {
			ICFSecTenantByUNameIdxKey rhs = (ICFSecTenantByUNameIdxKey)obj;
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
			if( getRequiredTenantName() != null ) {
				if( rhs.getRequiredTenantName() != null ) {
					if( ! getRequiredTenantName().equals( rhs.getRequiredTenantName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTenantName() != null ) {
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
		hashCode = hashCode + getRequiredId().hashCode();
		hashCode = hashCode + getRequiredClusterId().hashCode();
		if( getRequiredTenantName() != null ) {
			hashCode = hashCode + getRequiredTenantName().hashCode();
		}
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFSecTenant) {
			ICFSecTenant rhs = (ICFSecTenant)obj;
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
			if (getRequiredTenantName() != null) {
				if (rhs.getRequiredTenantName() != null) {
					cmp = getRequiredTenantName().compareTo( rhs.getRequiredTenantName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTenantName() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecTenantHPKey) {
			ICFSecTenantHPKey rhs = (ICFSecTenantHPKey)obj;
			if (getRequiredId() != null) {
				if (rhs.getRequiredId() != null) {
					cmp = getRequiredId().compareTo( rhs.getRequiredId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if( obj instanceof ICFSecTenantH ) {
			ICFSecTenantH rhs = (ICFSecTenantH)obj;
			if (getRequiredId() != null) {
				if (rhs.getRequiredId() != null) {
					cmp = getRequiredId().compareTo( rhs.getRequiredId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredId() != null) {
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
			if (getRequiredTenantName() != null) {
				if (rhs.getRequiredTenantName() != null) {
					cmp = getRequiredTenantName().compareTo( rhs.getRequiredTenantName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTenantName() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecTenantByClusterIdxKey) {
			ICFSecTenantByClusterIdxKey rhs = (ICFSecTenantByClusterIdxKey)obj;
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
		else if (obj instanceof ICFSecTenantByUNameIdxKey) {
			ICFSecTenantByUNameIdxKey rhs = (ICFSecTenantByUNameIdxKey)obj;
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
			if (getRequiredTenantName() != null) {
				if (rhs.getRequiredTenantName() != null) {
					cmp = getRequiredTenantName().compareTo( rhs.getRequiredTenantName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTenantName() != null) {
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
	public void set( ICFSecTenant src ) {
		setTenant( src );
	}

	@Override
	public void setTenant( ICFSecTenant src ) {
		setRequiredId(src.getRequiredId());
		setRequiredRevision( src.getRequiredRevision() );
		setCreatedByUserId( src.getCreatedByUserId() );
		setCreatedAt( src.getCreatedAt() );
		setUpdatedByUserId( src.getUpdatedByUserId() );
		setUpdatedAt( src.getUpdatedAt() );
		setRequiredContainerCluster(src.getRequiredContainerCluster());
		setRequiredTenantName(src.getRequiredTenantName());
	}

	@Override
	public void set( ICFSecTenantH src ) {
		setTenant( src );
	}

	@Override
	public void setTenant( ICFSecTenantH src ) {
		setRequiredId(src.getRequiredId());
		setRequiredContainerCluster(src.getRequiredClusterId());
		setRequiredTenantName(src.getRequiredTenantName());
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = ""
			+ " RequiredId=" + "\"" + getRequiredId().toString() + "\""
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredId=" + "\"" + getRequiredId().toString() + "\""
			+ " RequiredClusterId=" + "\"" + getRequiredClusterId().toString() + "\""
			+ " RequiredTenantName=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredTenantName() ) + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecJpaTenant" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

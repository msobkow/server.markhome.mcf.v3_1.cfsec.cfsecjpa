// Description: Java 25 JPA implementation of a SecClusGrp entity definition object.

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
	name = "SecClusGrp", schema = "CFSec31",
	indexes = {
		@Index(name = "SecClusGrpIdIdx", columnList = "SecClusGrpId", unique = true),
		@Index(name = "SecClusGrpClusterIdx", columnList = "ClusterId", unique = false),
		@Index(name = "SecClusGrpNameIdx", columnList = "safe_name", unique = false),
		@Index(name = "SecClusGrpUNameIdx", columnList = "ClusterId, safe_name", unique = true)
	}
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaSecClusGrp
	implements Comparable<Object>,
		ICFSecSecClusGrp,
		Serializable
{
	@Id
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="SecClusGrpId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredSecClusGrpId;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="pkey.requiredContainerGroup")
	protected Set<CFSecJpaSecClusGrpInc> optionalChildrenIncByGrp;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="pkey.requiredContainerGroup")
	protected Set<CFSecJpaSecClusGrpMemb> optionalChildrenMembByGrp;
	protected int requiredRevision;

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn( name="ClusterId" )
	protected CFSecJpaCluster requiredOwnerCluster;

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column = @Column( name="CreatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 createdByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecClusGrp.S_INIT_CREATED_BY);

	@Column(name="CreatedAt", nullable=false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column= @Column( name="UpdatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 updatedByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecClusGrp.S_INIT_UPDATED_BY);

	@Column(name="UpdatedAt", nullable=false)
	protected LocalDateTime updatedAt = LocalDateTime.now();
	@Column( name="safe_name", nullable=false, length=64 )
	protected String requiredName;

	public CFSecJpaSecClusGrp() {
		requiredSecClusGrpId = CFLibDbKeyHash256.fromHex( ICFSecSecClusGrp.SECCLUSGRPID_INIT_VALUE.toString() );
		requiredName = ICFSecSecClusGrp.NAME_INIT_VALUE;
	}

	@Override
	public int getClassCode() {
		return( ICFSecSecClusGrp.CLASS_CODE );
	}

	@Override
	public List<ICFSecSecClusGrpInc> getOptionalChildrenIncByGrp() {
		List<ICFSecSecClusGrpInc> retlist = new ArrayList<>(optionalChildrenIncByGrp.size());
		for (CFSecJpaSecClusGrpInc cur: optionalChildrenIncByGrp) {
			retlist.add(cur);
		}
		return( retlist );
	}
	@Override
	public List<ICFSecSecClusGrpMemb> getOptionalChildrenMembByGrp() {
		List<ICFSecSecClusGrpMemb> retlist = new ArrayList<>(optionalChildrenMembByGrp.size());
		for (CFSecJpaSecClusGrpMemb cur: optionalChildrenMembByGrp) {
			retlist.add(cur);
		}
		return( retlist );
	}
	@Override
	public ICFSecCluster getRequiredOwnerCluster() {
		return( requiredOwnerCluster );
	}
	@Override
	public void setRequiredOwnerCluster(ICFSecCluster argObj) {
		if(argObj == null) {
			throw new CFLibNullArgumentException(getClass(), "setOwnerCluster", 1, "argObj");
		}
		else if (argObj instanceof CFSecJpaCluster) {
			requiredOwnerCluster = (CFSecJpaCluster)argObj;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "setOwnerCluster", "argObj", argObj, "CFSecJpaCluster");
		}
	}

	@Override
	public void setRequiredOwnerCluster(CFLibDbKeyHash256 argClusterId) {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredOwnerCluster", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecClusterTable targetTable = targetBackingSchema.getTableCluster();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredOwnerCluster", 0, "ICFSecSchema.getBackingCFSec().getTableCluster()");
		}
		ICFSecCluster targetRec = targetTable.readDerived(null, argClusterId);
		setRequiredOwnerCluster(targetRec);
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
		return getRequiredSecClusGrpId();
	}

	@Override
	public void setPKey(CFLibDbKeyHash256 requiredSecClusGrpId) {
		if (requiredSecClusGrpId != null) {
			setRequiredSecClusGrpId(requiredSecClusGrpId);
		}
	}
	
	@Override
	public CFLibDbKeyHash256 getRequiredSecClusGrpId() {
		return( requiredSecClusGrpId );
	}

	@Override
	public void setRequiredSecClusGrpId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredSecClusGrpId",
				1,
				"value" );
		}
		requiredSecClusGrpId = value;
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
		ICFSecCluster result = getRequiredOwnerCluster();
		if (result != null) {
			return result.getRequiredId();
		}
		else {
			return( ICFSecCluster.ID_INIT_VALUE );
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
		else if (obj instanceof ICFSecSecClusGrp) {
			ICFSecSecClusGrp rhs = (ICFSecSecClusGrp)obj;
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
			if( getRequiredSecClusGrpId() != null ) {
				if( rhs.getRequiredSecClusGrpId() != null ) {
					if( ! getRequiredSecClusGrpId().equals( rhs.getRequiredSecClusGrpId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecClusGrpId() != null ) {
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
		else if (obj instanceof ICFSecSecClusGrpH) {
			ICFSecSecClusGrpH rhs = (ICFSecSecClusGrpH)obj;
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
			if( getRequiredSecClusGrpId() != null ) {
				if( rhs.getRequiredSecClusGrpId() != null ) {
					if( ! getRequiredSecClusGrpId().equals( rhs.getRequiredSecClusGrpId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecClusGrpId() != null ) {
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
		else if (obj instanceof ICFSecSecClusGrpHPKey) {
			ICFSecSecClusGrpHPKey rhs = (ICFSecSecClusGrpHPKey)obj;
			if( getRequiredSecClusGrpId() != null ) {
				if( rhs.getRequiredSecClusGrpId() != null ) {
					if( ! getRequiredSecClusGrpId().equals( rhs.getRequiredSecClusGrpId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecClusGrpId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecClusGrpByClusterIdxKey) {
			ICFSecSecClusGrpByClusterIdxKey rhs = (ICFSecSecClusGrpByClusterIdxKey)obj;
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
		else if (obj instanceof ICFSecSecClusGrpByNameIdxKey) {
			ICFSecSecClusGrpByNameIdxKey rhs = (ICFSecSecClusGrpByNameIdxKey)obj;
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
		else if (obj instanceof ICFSecSecClusGrpByUNameIdxKey) {
			ICFSecSecClusGrpByUNameIdxKey rhs = (ICFSecSecClusGrpByUNameIdxKey)obj;
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
		hashCode = hashCode + getRequiredSecClusGrpId().hashCode();
		hashCode = hashCode + getRequiredClusterId().hashCode();
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
		else if (obj instanceof ICFSecSecClusGrp) {
			ICFSecSecClusGrp rhs = (ICFSecSecClusGrp)obj;
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
		else if (obj instanceof ICFSecSecClusGrpHPKey) {
			ICFSecSecClusGrpHPKey rhs = (ICFSecSecClusGrpHPKey)obj;
			if (getRequiredSecClusGrpId() != null) {
				if (rhs.getRequiredSecClusGrpId() != null) {
					cmp = getRequiredSecClusGrpId().compareTo( rhs.getRequiredSecClusGrpId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecClusGrpId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if( obj instanceof ICFSecSecClusGrpH ) {
			ICFSecSecClusGrpH rhs = (ICFSecSecClusGrpH)obj;
			if (getRequiredSecClusGrpId() != null) {
				if (rhs.getRequiredSecClusGrpId() != null) {
					cmp = getRequiredSecClusGrpId().compareTo( rhs.getRequiredSecClusGrpId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecClusGrpId() != null) {
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
		else if (obj instanceof ICFSecSecClusGrpByClusterIdxKey) {
			ICFSecSecClusGrpByClusterIdxKey rhs = (ICFSecSecClusGrpByClusterIdxKey)obj;
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
		else if (obj instanceof ICFSecSecClusGrpByNameIdxKey) {
			ICFSecSecClusGrpByNameIdxKey rhs = (ICFSecSecClusGrpByNameIdxKey)obj;
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
		else if (obj instanceof ICFSecSecClusGrpByUNameIdxKey) {
			ICFSecSecClusGrpByUNameIdxKey rhs = (ICFSecSecClusGrpByUNameIdxKey)obj;
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
	public void set( ICFSecSecClusGrp src ) {
		setSecClusGrp( src );
	}

	@Override
	public void setSecClusGrp( ICFSecSecClusGrp src ) {
		setRequiredSecClusGrpId(src.getRequiredSecClusGrpId());
		setRequiredRevision( src.getRequiredRevision() );
		setCreatedByUserId( src.getCreatedByUserId() );
		setCreatedAt( src.getCreatedAt() );
		setUpdatedByUserId( src.getUpdatedByUserId() );
		setUpdatedAt( src.getUpdatedAt() );
		setRequiredOwnerCluster(src.getRequiredOwnerCluster());
		setRequiredName(src.getRequiredName());
	}

	@Override
	public void set( ICFSecSecClusGrpH src ) {
		setSecClusGrp( src );
	}

	@Override
	public void setSecClusGrp( ICFSecSecClusGrpH src ) {
		setRequiredSecClusGrpId(src.getRequiredSecClusGrpId());
		setRequiredOwnerCluster(src.getRequiredClusterId());
		setRequiredName(src.getRequiredName());
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = ""
			+ " RequiredSecClusGrpId=" + "\"" + getRequiredSecClusGrpId().toString() + "\""
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredSecClusGrpId=" + "\"" + getRequiredSecClusGrpId().toString() + "\""
			+ " RequiredClusterId=" + "\"" + getRequiredClusterId().toString() + "\""
			+ " RequiredName=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredName() ) + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecJpaSecClusGrp" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

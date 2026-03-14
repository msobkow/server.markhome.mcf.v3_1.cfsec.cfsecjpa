// Description: Java 25 JPA implementation of a ServiceType entity definition object.

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
	name = "SvcType", schema = "CFSec31",
	indexes = {
		@Index(name = "ServiceTypeIdIdx", columnList = "ServiceTypeId", unique = true),
		@Index(name = "ServiceTypeUDescrIdx", columnList = "Description", unique = true)
	}
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaServiceType
	implements Comparable<Object>,
		ICFSecServiceType,
		Serializable
{
	@Id
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="ServiceTypeId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredServiceTypeId;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="optionalParentServiceType")
	protected Set<CFSecJpaService> optionalChildrenDeployed;
	protected int requiredRevision;


	@AttributeOverrides({
		@AttributeOverride( name="bytes", column = @Column( name="CreatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 createdByUserId = CFLibDbKeyHash256.fromHex(ICFSecServiceType.S_INIT_CREATED_BY);

	@Column(name="CreatedAt", nullable=false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column= @Column( name="UpdatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 updatedByUserId = CFLibDbKeyHash256.fromHex(ICFSecServiceType.S_INIT_UPDATED_BY);

	@Column(name="UpdatedAt", nullable=false)
	protected LocalDateTime updatedAt = LocalDateTime.now();
	@Column( name="Description", nullable=false, length=50 )
	protected String requiredDescription;

	public CFSecJpaServiceType() {
		requiredServiceTypeId = CFLibDbKeyHash256.fromHex( ICFSecServiceType.SERVICETYPEID_INIT_VALUE.toString() );
		requiredDescription = ICFSecServiceType.DESCRIPTION_INIT_VALUE;
	}

	@Override
	public int getClassCode() {
		return( ICFSecServiceType.CLASS_CODE );
	}

	@Override
	public List<ICFSecService> getOptionalChildrenDeployed() {
		List<ICFSecService> retlist = new ArrayList<>(optionalChildrenDeployed.size());
		for (CFSecJpaService cur: optionalChildrenDeployed) {
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
		return getRequiredServiceTypeId();
	}

	@Override
	public void setPKey(CFLibDbKeyHash256 requiredServiceTypeId) {
		if (requiredServiceTypeId != null) {
			setRequiredServiceTypeId(requiredServiceTypeId);
		}
	}
	
	@Override
	public CFLibDbKeyHash256 getRequiredServiceTypeId() {
		return( requiredServiceTypeId );
	}

	@Override
	public void setRequiredServiceTypeId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredServiceTypeId",
				1,
				"value" );
		}
		requiredServiceTypeId = value;
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
	public String getRequiredDescription() {
		return( requiredDescription );
	}

	@Override
	public void setRequiredDescription( String value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredDescription",
				1,
				"value" );
		}
		else if( value.length() > 50 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredDescription",
				1,
				"value.length()",
				value.length(),
				50 );
		}
		requiredDescription = value;
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecServiceType) {
			ICFSecServiceType rhs = (ICFSecServiceType)obj;
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
			if( getRequiredServiceTypeId() != null ) {
				if( rhs.getRequiredServiceTypeId() != null ) {
					if( ! getRequiredServiceTypeId().equals( rhs.getRequiredServiceTypeId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredServiceTypeId() != null ) {
					return( false );
				}
			}
			if( getRequiredDescription() != null ) {
				if( rhs.getRequiredDescription() != null ) {
					if( ! getRequiredDescription().equals( rhs.getRequiredDescription() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredDescription() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecServiceTypeH) {
			ICFSecServiceTypeH rhs = (ICFSecServiceTypeH)obj;
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
			if( getRequiredServiceTypeId() != null ) {
				if( rhs.getRequiredServiceTypeId() != null ) {
					if( ! getRequiredServiceTypeId().equals( rhs.getRequiredServiceTypeId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredServiceTypeId() != null ) {
					return( false );
				}
			}
			if( getRequiredDescription() != null ) {
				if( rhs.getRequiredDescription() != null ) {
					if( ! getRequiredDescription().equals( rhs.getRequiredDescription() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredDescription() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecServiceTypeHPKey) {
			ICFSecServiceTypeHPKey rhs = (ICFSecServiceTypeHPKey)obj;
			if( getRequiredServiceTypeId() != null ) {
				if( rhs.getRequiredServiceTypeId() != null ) {
					if( ! getRequiredServiceTypeId().equals( rhs.getRequiredServiceTypeId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredServiceTypeId() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecServiceTypeByUDescrIdxKey) {
			ICFSecServiceTypeByUDescrIdxKey rhs = (ICFSecServiceTypeByUDescrIdxKey)obj;
			if( getRequiredDescription() != null ) {
				if( rhs.getRequiredDescription() != null ) {
					if( ! getRequiredDescription().equals( rhs.getRequiredDescription() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredDescription() != null ) {
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
		hashCode = hashCode + getRequiredServiceTypeId().hashCode();
		if( getRequiredDescription() != null ) {
			hashCode = hashCode + getRequiredDescription().hashCode();
		}
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFSecServiceType) {
			ICFSecServiceType rhs = (ICFSecServiceType)obj;
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
			if (getRequiredDescription() != null) {
				if (rhs.getRequiredDescription() != null) {
					cmp = getRequiredDescription().compareTo( rhs.getRequiredDescription() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredDescription() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecServiceTypeHPKey) {
			ICFSecServiceTypeHPKey rhs = (ICFSecServiceTypeHPKey)obj;
			if (getRequiredServiceTypeId() != null) {
				if (rhs.getRequiredServiceTypeId() != null) {
					cmp = getRequiredServiceTypeId().compareTo( rhs.getRequiredServiceTypeId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredServiceTypeId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if( obj instanceof ICFSecServiceTypeH ) {
			ICFSecServiceTypeH rhs = (ICFSecServiceTypeH)obj;
			if (getRequiredServiceTypeId() != null) {
				if (rhs.getRequiredServiceTypeId() != null) {
					cmp = getRequiredServiceTypeId().compareTo( rhs.getRequiredServiceTypeId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredServiceTypeId() != null) {
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
			if (getRequiredDescription() != null) {
				if (rhs.getRequiredDescription() != null) {
					cmp = getRequiredDescription().compareTo( rhs.getRequiredDescription() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredDescription() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecServiceTypeByUDescrIdxKey) {
			ICFSecServiceTypeByUDescrIdxKey rhs = (ICFSecServiceTypeByUDescrIdxKey)obj;
			if (getRequiredDescription() != null) {
				if (rhs.getRequiredDescription() != null) {
					cmp = getRequiredDescription().compareTo( rhs.getRequiredDescription() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredDescription() != null) {
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
	public void set( ICFSecServiceType src ) {
		setServiceType( src );
	}

	@Override
	public void setServiceType( ICFSecServiceType src ) {
		setRequiredServiceTypeId(src.getRequiredServiceTypeId());
		setRequiredRevision( src.getRequiredRevision() );
		setCreatedByUserId( src.getCreatedByUserId() );
		setCreatedAt( src.getCreatedAt() );
		setUpdatedByUserId( src.getUpdatedByUserId() );
		setUpdatedAt( src.getUpdatedAt() );
		setRequiredDescription(src.getRequiredDescription());
	}

	@Override
	public void set( ICFSecServiceTypeH src ) {
		setServiceType( src );
	}

	@Override
	public void setServiceType( ICFSecServiceTypeH src ) {
		setRequiredServiceTypeId(src.getRequiredServiceTypeId());
		setRequiredDescription(src.getRequiredDescription());
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = ""
			+ " RequiredServiceTypeId=" + "\"" + getRequiredServiceTypeId().toString() + "\""
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredServiceTypeId=" + "\"" + getRequiredServiceTypeId().toString() + "\""
			+ " RequiredDescription=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredDescription() ) + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecJpaServiceType" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

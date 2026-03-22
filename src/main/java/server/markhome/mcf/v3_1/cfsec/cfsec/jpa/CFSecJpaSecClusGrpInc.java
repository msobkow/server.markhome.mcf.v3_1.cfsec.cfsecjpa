// Description: Java 25 JPA implementation of a SecClusGrpInc entity definition object.

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
	name = "SecClusGrpInc", schema = "CFSec31",
	indexes = {
		@Index(name = "SecClusGrpIncIdIdx", columnList = "SecClusGrpId, inc_name", unique = true),
		@Index(name = "SecClusGrpIncClusGrpIdx", columnList = "SecClusGrpId", unique = false),
		@Index(name = "SecClusGrpIncNameIdx", columnList = "inc_name", unique = false)
	}
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaSecClusGrpInc
	implements Comparable<Object>,
		ICFSecSecClusGrpInc,
		Serializable
{
	// Embedded id's are package-accessible so that the Repository can dereference the attributes of the primary key
	@AttributeOverrides({
		@AttributeOverride(name="SecClusGrpId", column = @Column( name="SecClusGrpId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) ),
		@AttributeOverride(name="inc_name", column = @Column( name="inc_name", nullable=false, length=64 ) )
	})
	@EmbeddedId
	CFSecJpaSecClusGrpIncPKey pkey = new CFSecJpaSecClusGrpIncPKey();
	protected int requiredRevision;


	@AttributeOverrides({
		@AttributeOverride( name="bytes", column = @Column( name="CreatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 createdByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecClusGrpInc.S_INIT_CREATED_BY);

	@Column(name="CreatedAt", nullable=false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column= @Column( name="UpdatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 updatedByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecClusGrpInc.S_INIT_UPDATED_BY);

	@Column(name="UpdatedAt", nullable=false)
	protected LocalDateTime updatedAt = LocalDateTime.now();

	public CFSecJpaSecClusGrpInc() {
		pkey = new CFSecJpaSecClusGrpIncPKey();
	}

	@Override
	public int getClassCode() {
		return( ICFSecSecClusGrpInc.CLASS_CODE );
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
	public ICFSecSecClusGrpIncPKey getPKey() {
		return pkey;
	}

	@Override
	public void setPKey(ICFSecSecClusGrpIncPKey pkey ) {
		if (pkey == null) {
			throw new CFLibNullArgumentException(getClass(), "setPKey", 1, "pkey");
		}
		else if (!(pkey instanceof CFSecJpaSecClusGrpIncPKey)) {
			throw new CFLibUnsupportedClassException(getClass(), "setPKey", "pkey", pkey, "CFSecJpaSecClusGrpIncPKey");
		}
		this.pkey = (CFSecJpaSecClusGrpIncPKey)pkey;
	}

	@Override
	public CFLibDbKeyHash256 getRequiredSecClusGrpId() {
		return( pkey.getRequiredSecClusGrpId() );
	}

	@Override
	public void setRequiredSecClusGrpId( CFLibDbKeyHash256 requiredSecClusGrpId ) {
		pkey.setRequiredSecClusGrpId( requiredSecClusGrpId );
	}

	@Override
	public String getRequiredIncName() {
		return( pkey.getRequiredIncName() );
	}

	@Override
	public void setRequiredIncName( String requiredIncName ) {
		pkey.setRequiredIncName( requiredIncName );
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
		else if (obj instanceof ICFSecSecClusGrpInc) {
			ICFSecSecClusGrpInc rhs = (ICFSecSecClusGrpInc)obj;
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
		else if (obj instanceof ICFSecSecClusGrpIncH) {
			ICFSecSecClusGrpIncH rhs = (ICFSecSecClusGrpIncH)obj;
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
		else if (obj instanceof ICFSecSecClusGrpIncHPKey) {
			ICFSecSecClusGrpIncHPKey rhs = (ICFSecSecClusGrpIncHPKey)obj;
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
		else if (obj instanceof ICFSecSecClusGrpIncByClusGrpIdxKey) {
			ICFSecSecClusGrpIncByClusGrpIdxKey rhs = (ICFSecSecClusGrpIncByClusGrpIdxKey)obj;
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
		else if (obj instanceof ICFSecSecClusGrpIncByNameIdxKey) {
			ICFSecSecClusGrpIncByNameIdxKey rhs = (ICFSecSecClusGrpIncByNameIdxKey)obj;
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
		int hashCode = getPKey().hashCode();
		hashCode = hashCode + getCreatedByUserId().hashCode();
		hashCode = hashCode + getCreatedAt().hashCode();
		hashCode = hashCode + getUpdatedByUserId().hashCode();
		hashCode = hashCode + getUpdatedAt().hashCode();
		hashCode = hashCode + getRequiredSecClusGrpId().hashCode();
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
		else if (obj instanceof ICFSecSecClusGrpInc) {
			ICFSecSecClusGrpInc rhs = (ICFSecSecClusGrpInc)obj;
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
		else if (obj instanceof ICFSecSecClusGrpIncHPKey) {
			ICFSecSecClusGrpIncHPKey rhs = (ICFSecSecClusGrpIncHPKey)obj;
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
		else if( obj instanceof ICFSecSecClusGrpIncH ) {
			ICFSecSecClusGrpIncH rhs = (ICFSecSecClusGrpIncH)obj;
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
		else if (obj instanceof ICFSecSecClusGrpIncByClusGrpIdxKey) {
			ICFSecSecClusGrpIncByClusGrpIdxKey rhs = (ICFSecSecClusGrpIncByClusGrpIdxKey)obj;
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
		else if (obj instanceof ICFSecSecClusGrpIncByNameIdxKey) {
			ICFSecSecClusGrpIncByNameIdxKey rhs = (ICFSecSecClusGrpIncByNameIdxKey)obj;
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
				null );
		}
	}

	@Override
	public void set( ICFSecSecClusGrpInc src ) {
		setSecClusGrpInc( src );
	}

	@Override
	public void setSecClusGrpInc( ICFSecSecClusGrpInc src ) {
		setRequiredSecClusGrpId(src.getRequiredSecClusGrpId());
		setRequiredIncName(src.getRequiredIncName());
		setRequiredRevision( src.getRequiredRevision() );
		setCreatedByUserId( src.getCreatedByUserId() );
		setCreatedAt( src.getCreatedAt() );
		setUpdatedByUserId( src.getUpdatedByUserId() );
		setUpdatedAt( src.getUpdatedAt() );
	}

	@Override
	public void set( ICFSecSecClusGrpIncH src ) {
		setSecClusGrpInc( src );
	}

	@Override
	public void setSecClusGrpInc( ICFSecSecClusGrpIncH src ) {
		setRequiredSecClusGrpId(src.getRequiredSecClusGrpId());
		setRequiredIncName(src.getRequiredIncName());
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = pkey.getXmlAttrFragment() 
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredSecClusGrpId=" + "\"" + getRequiredSecClusGrpId().toString() + "\""
			+ " RequiredIncName=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredIncName() ) + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecJpaSecClusGrpInc" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

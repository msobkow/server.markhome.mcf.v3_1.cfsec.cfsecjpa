// Description: Java 25 JPA implementation of a ISOTZone entity definition object.

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
	name = "ISOTz", schema = "CFSec31",
	indexes = {
		@Index(name = "ISOTZoneIdIdx", columnList = "ISOTZoneId", unique = true),
		@Index(name = "ISOTZoneOffsetIdx", columnList = "TZHourOffset, TZMinOffset", unique = false),
		@Index(name = "ISOTZoneUTZNameIdx", columnList = "TZName", unique = true),
		@Index(name = "ISOTZoneIso8601Idx", columnList = "Iso8601", unique = false)
	}
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaISOTZone
	implements Comparable<Object>,
		ICFSecISOTZone,
		Serializable
{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ISOTZoneIdGenSeq")
	@SequenceGenerator(name = "ISOTZoneIdGenSeq", allocationSize = 1, initialValue = 0, schema = "CFSec31")
	@Column( name="ISOTZoneId", nullable=false )
	protected short requiredISOTZoneId;
	protected int requiredRevision;


	@AttributeOverrides({
		@AttributeOverride( name="bytes", column = @Column( name="CreatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 createdByUserId = CFLibDbKeyHash256.fromHex(ICFSecISOTZone.S_INIT_CREATED_BY);

	@Column(name="CreatedAt", nullable=false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column= @Column( name="UpdatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 updatedByUserId = CFLibDbKeyHash256.fromHex(ICFSecISOTZone.S_INIT_UPDATED_BY);

	@Column(name="UpdatedAt", nullable=false)
	protected LocalDateTime updatedAt = LocalDateTime.now();
	@Column( name="Iso8601", nullable=false, length=6 )
	protected String requiredIso8601;
	@Column( name="TZName", nullable=false, length=64 )
	protected String requiredTZName;
	@Column( name="TZHourOffset", nullable=false )
	protected short requiredTZHourOffset;
	@Column( name="TZMinOffset", nullable=false )
	protected short requiredTZMinOffset;
	@Column( name="Description", nullable=false, length=128 )
	protected String requiredDescription;
	@Column( name="Visible", nullable=false )
	protected boolean requiredVisible;

	public CFSecJpaISOTZone() {
		requiredISOTZoneId = ICFSecISOTZone.ISOTZONEID_INIT_VALUE;
		requiredIso8601 = ICFSecISOTZone.ISO8601_INIT_VALUE;
		requiredTZName = ICFSecISOTZone.TZNAME_INIT_VALUE;
		requiredTZHourOffset = ICFSecISOTZone.TZHOUROFFSET_INIT_VALUE;
		requiredTZMinOffset = ICFSecISOTZone.TZMINOFFSET_INIT_VALUE;
		requiredDescription = ICFSecISOTZone.DESCRIPTION_INIT_VALUE;
		requiredVisible = ICFSecISOTZone.VISIBLE_INIT_VALUE;
	}

	@Override
	public int getClassCode() {
		return( ICFSecISOTZone.CLASS_CODE );
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
	public Short getPKey() {
		return getRequiredISOTZoneId();
	}

	@Override
	public void setPKey(Short requiredISOTZoneId) {
		this.requiredISOTZoneId = requiredISOTZoneId;
	}
	
	@Override
	public short getRequiredISOTZoneId() {
		return( requiredISOTZoneId );
	}

	@Override
	public void setRequiredISOTZoneId( short value ) {
		if( value < ICFSecISOTZone.ISOTZONEID_MIN_VALUE ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				"setRequiredISOTZoneId",
				1,
				"value",
				value,
				ICFSecISOTZone.ISOTZONEID_MIN_VALUE );
		}
		requiredISOTZoneId = value;
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
	public String getRequiredIso8601() {
		return( requiredIso8601 );
	}

	@Override
	public void setRequiredIso8601( String value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredIso8601",
				1,
				"value" );
		}
		else if( value.length() > 6 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredIso8601",
				1,
				"value.length()",
				value.length(),
				6 );
		}
		requiredIso8601 = value;
	}

	@Override
	public String getRequiredTZName() {
		return( requiredTZName );
	}

	@Override
	public void setRequiredTZName( String value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredTZName",
				1,
				"value" );
		}
		else if( value.length() > 64 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredTZName",
				1,
				"value.length()",
				value.length(),
				64 );
		}
		requiredTZName = value;
	}

	@Override
	public short getRequiredTZHourOffset() {
		return( requiredTZHourOffset );
	}

	@Override
	public void setRequiredTZHourOffset( short value ) {
		if( value < ICFSecISOTZone.TZHOUROFFSET_MIN_VALUE ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				"setRequiredTZHourOffset",
				1,
				"value",
				value,
				ICFSecISOTZone.TZHOUROFFSET_MIN_VALUE );
		}
		if( value > ICFSecISOTZone.TZHOUROFFSET_MAX_VALUE ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredTZHourOffset",
				1,
				"value",
				value,
				ICFSecISOTZone.TZHOUROFFSET_MAX_VALUE );
		}
		requiredTZHourOffset = value;
	}

	@Override
	public short getRequiredTZMinOffset() {
		return( requiredTZMinOffset );
	}

	@Override
	public void setRequiredTZMinOffset( short value ) {
		if( value < ICFSecISOTZone.TZMINOFFSET_MIN_VALUE ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				"setRequiredTZMinOffset",
				1,
				"value",
				value,
				ICFSecISOTZone.TZMINOFFSET_MIN_VALUE );
		}
		if( value > ICFSecISOTZone.TZMINOFFSET_MAX_VALUE ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredTZMinOffset",
				1,
				"value",
				value,
				ICFSecISOTZone.TZMINOFFSET_MAX_VALUE );
		}
		requiredTZMinOffset = value;
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
		else if( value.length() > 128 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredDescription",
				1,
				"value.length()",
				value.length(),
				128 );
		}
		requiredDescription = value;
	}

	@Override
	public boolean getRequiredVisible() {
		return( requiredVisible );
	}

	@Override
	public void setRequiredVisible( boolean value ) {
		requiredVisible = value;
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecISOTZone) {
			ICFSecISOTZone rhs = (ICFSecISOTZone)obj;
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
			if( getRequiredISOTZoneId() != rhs.getRequiredISOTZoneId() ) {
				return( false );
			}
			if( getRequiredIso8601() != null ) {
				if( rhs.getRequiredIso8601() != null ) {
					if( ! getRequiredIso8601().equals( rhs.getRequiredIso8601() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredIso8601() != null ) {
					return( false );
				}
			}
			if( getRequiredTZName() != null ) {
				if( rhs.getRequiredTZName() != null ) {
					if( ! getRequiredTZName().equals( rhs.getRequiredTZName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTZName() != null ) {
					return( false );
				}
			}
			if( getRequiredTZHourOffset() != rhs.getRequiredTZHourOffset() ) {
				return( false );
			}
			if( getRequiredTZMinOffset() != rhs.getRequiredTZMinOffset() ) {
				return( false );
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
			if( getRequiredVisible() != rhs.getRequiredVisible() ) {
				return( false );
			}
			return( true );
		}
		else if (obj instanceof ICFSecISOTZoneH) {
			ICFSecISOTZoneH rhs = (ICFSecISOTZoneH)obj;
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
			if( getRequiredISOTZoneId() != rhs.getRequiredISOTZoneId() ) {
				return( false );
			}
			if( getRequiredIso8601() != null ) {
				if( rhs.getRequiredIso8601() != null ) {
					if( ! getRequiredIso8601().equals( rhs.getRequiredIso8601() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredIso8601() != null ) {
					return( false );
				}
			}
			if( getRequiredTZName() != null ) {
				if( rhs.getRequiredTZName() != null ) {
					if( ! getRequiredTZName().equals( rhs.getRequiredTZName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTZName() != null ) {
					return( false );
				}
			}
			if( getRequiredTZHourOffset() != rhs.getRequiredTZHourOffset() ) {
				return( false );
			}
			if( getRequiredTZMinOffset() != rhs.getRequiredTZMinOffset() ) {
				return( false );
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
			if( getRequiredVisible() != rhs.getRequiredVisible() ) {
				return( false );
			}
			return( true );
		}
		else if (obj instanceof ICFSecISOTZoneHPKey) {
			ICFSecISOTZoneHPKey rhs = (ICFSecISOTZoneHPKey)obj;
			if( getRequiredISOTZoneId() != rhs.getRequiredISOTZoneId() ) {
				return( false );
			}
			return( true );
		}
		else if (obj instanceof ICFSecISOTZoneByOffsetIdxKey) {
			ICFSecISOTZoneByOffsetIdxKey rhs = (ICFSecISOTZoneByOffsetIdxKey)obj;
			if( getRequiredTZHourOffset() != rhs.getRequiredTZHourOffset() ) {
				return( false );
			}
			if( getRequiredTZMinOffset() != rhs.getRequiredTZMinOffset() ) {
				return( false );
			}
			return( true );
		}
		else if (obj instanceof ICFSecISOTZoneByUTZNameIdxKey) {
			ICFSecISOTZoneByUTZNameIdxKey rhs = (ICFSecISOTZoneByUTZNameIdxKey)obj;
			if( getRequiredTZName() != null ) {
				if( rhs.getRequiredTZName() != null ) {
					if( ! getRequiredTZName().equals( rhs.getRequiredTZName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTZName() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecISOTZoneByIso8601IdxKey) {
			ICFSecISOTZoneByIso8601IdxKey rhs = (ICFSecISOTZoneByIso8601IdxKey)obj;
			if( getRequiredIso8601() != null ) {
				if( rhs.getRequiredIso8601() != null ) {
					if( ! getRequiredIso8601().equals( rhs.getRequiredIso8601() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredIso8601() != null ) {
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
		hashCode = ( hashCode * 0x10000 ) + getRequiredISOTZoneId();
		if( getRequiredIso8601() != null ) {
			hashCode = hashCode + getRequiredIso8601().hashCode();
		}
		if( getRequiredTZName() != null ) {
			hashCode = hashCode + getRequiredTZName().hashCode();
		}
		hashCode = ( hashCode * 0x10000 ) + getRequiredTZHourOffset();
		hashCode = ( hashCode * 0x10000 ) + getRequiredTZMinOffset();
		if( getRequiredDescription() != null ) {
			hashCode = hashCode + getRequiredDescription().hashCode();
		}
		if( getRequiredVisible() ) {
			hashCode = ( hashCode * 2 ) + 1;
		}
		else {
			hashCode = hashCode * 2;
		}
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFSecISOTZone) {
			ICFSecISOTZone rhs = (ICFSecISOTZone)obj;
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
			if (getRequiredIso8601() != null) {
				if (rhs.getRequiredIso8601() != null) {
					cmp = getRequiredIso8601().compareTo( rhs.getRequiredIso8601() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredIso8601() != null) {
				return( -1 );
			}
			if (getRequiredTZName() != null) {
				if (rhs.getRequiredTZName() != null) {
					cmp = getRequiredTZName().compareTo( rhs.getRequiredTZName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTZName() != null) {
				return( -1 );
			}
			if( getRequiredTZHourOffset() < rhs.getRequiredTZHourOffset() ) {
				return( -1 );
			}
			else if( getRequiredTZHourOffset() > rhs.getRequiredTZHourOffset() ) {
				return( 1 );
			}
			if( getRequiredTZMinOffset() < rhs.getRequiredTZMinOffset() ) {
				return( -1 );
			}
			else if( getRequiredTZMinOffset() > rhs.getRequiredTZMinOffset() ) {
				return( 1 );
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
			if( getRequiredVisible() ) {
				if( ! rhs.getRequiredVisible() ) {
					return( 1 );
				}
			}
			else {
				if( rhs.getRequiredVisible() ) {
					return( -1 );
				}
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecISOTZoneHPKey) {
			ICFSecISOTZoneHPKey rhs = (ICFSecISOTZoneHPKey)obj;
			if( getRequiredISOTZoneId() < rhs.getRequiredISOTZoneId() ) {
				return( -1 );
			}
			else if( getRequiredISOTZoneId() > rhs.getRequiredISOTZoneId() ) {
				return( 1 );
			}
			return( 0 );
		}
		else if( obj instanceof ICFSecISOTZoneH ) {
			ICFSecISOTZoneH rhs = (ICFSecISOTZoneH)obj;
			if( getRequiredISOTZoneId() < rhs.getRequiredISOTZoneId() ) {
				return( -1 );
			}
			else if( getRequiredISOTZoneId() > rhs.getRequiredISOTZoneId() ) {
				return( 1 );
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
			if (getRequiredIso8601() != null) {
				if (rhs.getRequiredIso8601() != null) {
					cmp = getRequiredIso8601().compareTo( rhs.getRequiredIso8601() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredIso8601() != null) {
				return( -1 );
			}
			if (getRequiredTZName() != null) {
				if (rhs.getRequiredTZName() != null) {
					cmp = getRequiredTZName().compareTo( rhs.getRequiredTZName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTZName() != null) {
				return( -1 );
			}
			if( getRequiredTZHourOffset() < rhs.getRequiredTZHourOffset() ) {
				return( -1 );
			}
			else if( getRequiredTZHourOffset() > rhs.getRequiredTZHourOffset() ) {
				return( 1 );
			}
			if( getRequiredTZMinOffset() < rhs.getRequiredTZMinOffset() ) {
				return( -1 );
			}
			else if( getRequiredTZMinOffset() > rhs.getRequiredTZMinOffset() ) {
				return( 1 );
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
			if( getRequiredVisible() ) {
				if( ! rhs.getRequiredVisible() ) {
					return( 1 );
				}
			}
			else {
				if( rhs.getRequiredVisible() ) {
					return( -1 );
				}
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecISOTZoneByOffsetIdxKey) {
			ICFSecISOTZoneByOffsetIdxKey rhs = (ICFSecISOTZoneByOffsetIdxKey)obj;
			if( getRequiredTZHourOffset() < rhs.getRequiredTZHourOffset() ) {
				return( -1 );
			}
			else if( getRequiredTZHourOffset() > rhs.getRequiredTZHourOffset() ) {
				return( 1 );
			}
			if( getRequiredTZMinOffset() < rhs.getRequiredTZMinOffset() ) {
				return( -1 );
			}
			else if( getRequiredTZMinOffset() > rhs.getRequiredTZMinOffset() ) {
				return( 1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecISOTZoneByUTZNameIdxKey) {
			ICFSecISOTZoneByUTZNameIdxKey rhs = (ICFSecISOTZoneByUTZNameIdxKey)obj;
			if (getRequiredTZName() != null) {
				if (rhs.getRequiredTZName() != null) {
					cmp = getRequiredTZName().compareTo( rhs.getRequiredTZName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTZName() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecISOTZoneByIso8601IdxKey) {
			ICFSecISOTZoneByIso8601IdxKey rhs = (ICFSecISOTZoneByIso8601IdxKey)obj;
			if (getRequiredIso8601() != null) {
				if (rhs.getRequiredIso8601() != null) {
					cmp = getRequiredIso8601().compareTo( rhs.getRequiredIso8601() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredIso8601() != null) {
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
	public void set( ICFSecISOTZone src ) {
		setISOTZone( src );
	}

	@Override
	public void setISOTZone( ICFSecISOTZone src ) {
		setRequiredISOTZoneId(src.getRequiredISOTZoneId());
		setRequiredRevision( src.getRequiredRevision() );
		setCreatedByUserId( src.getCreatedByUserId() );
		setCreatedAt( src.getCreatedAt() );
		setUpdatedByUserId( src.getUpdatedByUserId() );
		setUpdatedAt( src.getUpdatedAt() );
		setRequiredIso8601(src.getRequiredIso8601());
		setRequiredTZName(src.getRequiredTZName());
		setRequiredTZHourOffset(src.getRequiredTZHourOffset());
		setRequiredTZMinOffset(src.getRequiredTZMinOffset());
		setRequiredDescription(src.getRequiredDescription());
		setRequiredVisible(src.getRequiredVisible());
	}

	@Override
	public void set( ICFSecISOTZoneH src ) {
		setISOTZone( src );
	}

	@Override
	public void setISOTZone( ICFSecISOTZoneH src ) {
		setRequiredISOTZoneId(src.getRequiredISOTZoneId());
		setRequiredIso8601(src.getRequiredIso8601());
		setRequiredTZName(src.getRequiredTZName());
		setRequiredTZHourOffset(src.getRequiredTZHourOffset());
		setRequiredTZMinOffset(src.getRequiredTZMinOffset());
		setRequiredDescription(src.getRequiredDescription());
		setRequiredVisible(src.getRequiredVisible());
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = ""
			+ " RequiredISOTZoneId=" + "\"" + Short.toString( getRequiredISOTZoneId() ) + "\""
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredISOTZoneId=" + "\"" + Short.toString( getRequiredISOTZoneId() ) + "\""
			+ " RequiredIso8601=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredIso8601() ) + "\""
			+ " RequiredTZName=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredTZName() ) + "\""
			+ " RequiredTZHourOffset=" + "\"" + Short.toString( getRequiredTZHourOffset() ) + "\""
			+ " RequiredTZMinOffset=" + "\"" + Short.toString( getRequiredTZMinOffset() ) + "\""
			+ " RequiredDescription=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredDescription() ) + "\""
			+ " RequiredVisible=" + (( getRequiredVisible() ) ? "\"true\"" : "\"false\"" );
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecJpaISOTZone" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

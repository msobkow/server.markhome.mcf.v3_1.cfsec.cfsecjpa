// Description: Java 25 JPA implementation of a SecUserPWHistory entity definition object.

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
	name = "SecUserPWHistory", schema = "CFSec31",
	indexes = {
		@Index(name = "SecUserPWHistIdIdx", columnList = "SecUserId, PWSetStamp", unique = true),
		@Index(name = "SecUserPWHistUserIdx", columnList = "SecUserId", unique = true),
		@Index(name = "SecUserPWHistSetStampIdx", columnList = "PWSetStamp", unique = true),
		@Index(name = "SecUserPWHistReplacedStampIdx", columnList = "PWReplacedStamp", unique = true)
	}
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaSecUserPWHistory
	implements Comparable<Object>,
		ICFSecSecUserPWHistory,
		Serializable
{
	// Embedded id's are package-accessible so that the Repository can dereference the attributes of the primary key
	@AttributeOverrides({
		@AttributeOverride(name="SecUserId", column = @Column( name="SecUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) ),
		@AttributeOverride(name="PWSetStamp", column = @Column( name="PWSetStamp", nullable=false ) )
	})
	@EmbeddedId
	CFSecJpaSecUserPWHistoryPKey pkey = new CFSecJpaSecUserPWHistoryPKey();
	protected int requiredRevision;


	@Column( name="PWReplacedStamp", nullable=false )
	protected LocalDateTime requiredPWReplacedStamp;
	@Column( name="pwd_hash", nullable=false, length=256 )
	protected String requiredPasswordHash;

	public CFSecJpaSecUserPWHistory() {
		pkey = new CFSecJpaSecUserPWHistoryPKey();
		requiredPWReplacedStamp = CFLibXmlUtil.parseTimestamp("2020-01-01T00:00:00");
	}

	@Override
	public int getClassCode() {
		return( ICFSecSecUserPWHistory.CLASS_CODE );
	}

	@Override
	public ICFSecSecUserPWHistoryPKey getPKey() {
		return pkey;
	}

	@Override
	public void setPKey(ICFSecSecUserPWHistoryPKey pkey ) {
		if (pkey == null) {
			throw new CFLibNullArgumentException(getClass(), "setPKey", 1, "pkey");
		}
		else if (!(pkey instanceof CFSecJpaSecUserPWHistoryPKey)) {
			throw new CFLibUnsupportedClassException(getClass(), "setPKey", "pkey", pkey, "CFSecJpaSecUserPWHistoryPKey");
		}
		this.pkey = (CFSecJpaSecUserPWHistoryPKey)pkey;
	}

	@Override
	public CFLibDbKeyHash256 getRequiredSecUserId() {
		return( pkey.getRequiredSecUserId() );
	}

	@Override
	public void setRequiredSecUserId( CFLibDbKeyHash256 requiredSecUserId ) {
		pkey.setRequiredSecUserId( requiredSecUserId );
	}

	@Override
	public LocalDateTime getRequiredPWSetStamp() {
		return( pkey.getRequiredPWSetStamp() );
	}

	@Override
	public void setRequiredPWSetStamp( LocalDateTime requiredPWSetStamp ) {
		pkey.setRequiredPWSetStamp( requiredPWSetStamp );
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
	public LocalDateTime getRequiredPWReplacedStamp() {
		return( requiredPWReplacedStamp );
	}

	@Override
	public void setRequiredPWReplacedStamp( LocalDateTime value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredPWReplacedStamp",
				1,
				"value" );
		}
		requiredPWReplacedStamp = value;
	}

	@Override
	public String getRequiredPasswordHash() {
		return( requiredPasswordHash );
	}

	@Override
	public void setRequiredPasswordHash( String value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredPasswordHash",
				1,
				"value" );
		}
		else if( value.length() > 256 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredPasswordHash",
				1,
				"value.length()",
				value.length(),
				256 );
		}
		requiredPasswordHash = value;
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecSecUserPWHistory) {
			ICFSecSecUserPWHistory rhs = (ICFSecSecUserPWHistory)obj;
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
			if( getRequiredPWSetStamp() != null ) {
				if( rhs.getRequiredPWSetStamp() != null ) {
					if( ! getRequiredPWSetStamp().equals( rhs.getRequiredPWSetStamp() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredPWSetStamp() != null ) {
					return( false );
				}
			}
			if( getRequiredPWReplacedStamp() != null ) {
				if( rhs.getRequiredPWReplacedStamp() != null ) {
					if( ! getRequiredPWReplacedStamp().equals( rhs.getRequiredPWReplacedStamp() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredPWReplacedStamp() != null ) {
					return( false );
				}
			}
			if( getRequiredPasswordHash() != null ) {
				if( rhs.getRequiredPasswordHash() != null ) {
					if( ! getRequiredPasswordHash().equals( rhs.getRequiredPasswordHash() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredPasswordHash() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecUserPWHistoryByUserIdxKey) {
			ICFSecSecUserPWHistoryByUserIdxKey rhs = (ICFSecSecUserPWHistoryByUserIdxKey)obj;
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
		else if (obj instanceof ICFSecSecUserPWHistoryBySetStampIdxKey) {
			ICFSecSecUserPWHistoryBySetStampIdxKey rhs = (ICFSecSecUserPWHistoryBySetStampIdxKey)obj;
			if( getRequiredPWSetStamp() != null ) {
				if( rhs.getRequiredPWSetStamp() != null ) {
					if( ! getRequiredPWSetStamp().equals( rhs.getRequiredPWSetStamp() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredPWSetStamp() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecUserPWHistoryByReplacedStampIdxKey) {
			ICFSecSecUserPWHistoryByReplacedStampIdxKey rhs = (ICFSecSecUserPWHistoryByReplacedStampIdxKey)obj;
			if( getRequiredPWReplacedStamp() != null ) {
				if( rhs.getRequiredPWReplacedStamp() != null ) {
					if( ! getRequiredPWReplacedStamp().equals( rhs.getRequiredPWReplacedStamp() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredPWReplacedStamp() != null ) {
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
		hashCode = hashCode + getRequiredSecUserId().hashCode();
		if( getRequiredPWSetStamp() != null ) {
			hashCode = hashCode + getRequiredPWSetStamp().hashCode();
		}
		if( getRequiredPWReplacedStamp() != null ) {
			hashCode = hashCode + getRequiredPWReplacedStamp().hashCode();
		}
		if( getRequiredPasswordHash() != null ) {
			hashCode = hashCode + getRequiredPasswordHash().hashCode();
		}
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFSecSecUserPWHistory) {
			ICFSecSecUserPWHistory rhs = (ICFSecSecUserPWHistory)obj;
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
			if (getRequiredPWReplacedStamp() != null) {
				if (rhs.getRequiredPWReplacedStamp() != null) {
					cmp = getRequiredPWReplacedStamp().compareTo( rhs.getRequiredPWReplacedStamp() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredPWReplacedStamp() != null) {
				return( -1 );
			}
			if (getRequiredPasswordHash() != null) {
				if (rhs.getRequiredPasswordHash() != null) {
					cmp = getRequiredPasswordHash().compareTo( rhs.getRequiredPasswordHash() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredPasswordHash() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecSecUserPWHistoryByUserIdxKey) {
			ICFSecSecUserPWHistoryByUserIdxKey rhs = (ICFSecSecUserPWHistoryByUserIdxKey)obj;
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
		else if (obj instanceof ICFSecSecUserPWHistoryBySetStampIdxKey) {
			ICFSecSecUserPWHistoryBySetStampIdxKey rhs = (ICFSecSecUserPWHistoryBySetStampIdxKey)obj;
			if (getRequiredPWSetStamp() != null) {
				if (rhs.getRequiredPWSetStamp() != null) {
					cmp = getRequiredPWSetStamp().compareTo( rhs.getRequiredPWSetStamp() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredPWSetStamp() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecSecUserPWHistoryByReplacedStampIdxKey) {
			ICFSecSecUserPWHistoryByReplacedStampIdxKey rhs = (ICFSecSecUserPWHistoryByReplacedStampIdxKey)obj;
			if (getRequiredPWReplacedStamp() != null) {
				if (rhs.getRequiredPWReplacedStamp() != null) {
					cmp = getRequiredPWReplacedStamp().compareTo( rhs.getRequiredPWReplacedStamp() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredPWReplacedStamp() != null) {
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
	public void set( ICFSecSecUserPWHistory src ) {
		setSecUserPWHistory( src );
	}

	@Override
	public void setSecUserPWHistory( ICFSecSecUserPWHistory src ) {
		setRequiredSecUserId(src.getRequiredSecUserId());
		setRequiredPWSetStamp(src.getRequiredPWSetStamp());
		setRequiredRevision( src.getRequiredRevision() );
		setRequiredPWReplacedStamp(src.getRequiredPWReplacedStamp());
		setRequiredPasswordHash(src.getRequiredPasswordHash());
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = pkey.getXmlAttrFragment() 
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredSecUserId=" + "\"" + getRequiredSecUserId().toString() + "\""
			+ " RequiredPWSetStamp=" + "\"" + getRequiredPWSetStamp().toString() + "\""
			+ " RequiredPWReplacedStamp=" + "\"" + getRequiredPWReplacedStamp().toString() + "\""
			+ " RequiredPasswordHash=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredPasswordHash() ) + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecJpaSecUserPWHistory" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

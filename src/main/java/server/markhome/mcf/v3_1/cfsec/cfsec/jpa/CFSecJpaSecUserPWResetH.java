// Description: Java 25 JPA implementation of SecUserPWReset history objects

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

/**
 *  CFSecJpaSecUserPWResetH provides history objects matching the CFSecSecUserPWReset change history.
 */
@Entity
@Table(
    name = "secusrpwrst_h", schema = "CFSec31",
    indexes = {
        @Index(name = "SecUserPWResetIdIdx_h", columnList = "auditClusterId, auditStamp, auditAction, requiredRevision, auditSessionId, SecUserId", unique = true),
        @Index(name = "SecUserPWResetUuid6Idx_h", columnList = "pwdrstuuid6", unique = true),
        @Index(name = "SecUserPWResetSentToEMailAddrIdx_h", columnList = "sent_emailaddr", unique = false),
        @Index(name = "SecUserPWResetNewAcctIdx_h", columnList = "newacct", unique = false)
    }
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaSecUserPWResetH
    implements ICFSecSecUserPWResetH, Comparable<Object>, Serializable
{
	@AttributeOverrides({
		@AttributeOverride(name="auditClusterId", column = @Column( name="auditClusterId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) ),
		@AttributeOverride(name="auditStamp", column = @Column( name="auditStamp", nullable=false ) ),
		@AttributeOverride(name="auditAction", column = @Column( name="auditAction", nullable=false ) ),
		@AttributeOverride(name="requiredRevision", column = @Column( name="requiredRevision", nullable=false ) ),
		@AttributeOverride(name="auditSessionId", column = @Column( name="auditSessionId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) ),
		@AttributeOverride(name="SecUserId", column = @Column( name="SecUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
    @EmbeddedId
    protected CFSecJpaSecUserPWResetHPKey pkey;
	@AttributeOverrides({
		@AttributeOverride( name="bytes", column = @Column( name="CreatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 createdByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecUserPWReset.S_INIT_CREATED_BY);

	@Column(name="CreatedAt", nullable=false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column= @Column( name="UpdatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 updatedByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecUserPWReset.S_INIT_UPDATED_BY);

	@Column(name="UpdatedAt", nullable=false)
	protected LocalDateTime updatedAt = LocalDateTime.now();
	@Column( name="sent_emailaddr", nullable=false, length=512 )
	protected String requiredSentToEMailAddr;
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="pwdrstuuid6", nullable=false, length=CFLibUuid6.TOTAL_BYTES ) )
	})
	protected CFLibUuid6 requiredPasswordResetUuid6;
	@Column( name="newacct", nullable=false )
	protected boolean requiredNewAccount;

    public CFSecJpaSecUserPWResetH() {
            // The primary key member attributes are initialized on construction
            pkey = new CFSecJpaSecUserPWResetHPKey();
		requiredSentToEMailAddr = ICFSecSecUserPWReset.SENTTOEMAILADDR_INIT_VALUE;
		requiredNewAccount = ICFSecSecUserPWReset.NEWACCOUNT_INIT_VALUE;
    }

    @Override
    public int getClassCode() {
            return( ICFSecSecUserPWReset.CLASS_CODE );
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
    public ICFSecSecUserPWResetHPKey getPKey() {
        return( pkey );
    }

    @Override
    public void setPKey( ICFSecSecUserPWResetHPKey pkey ) {
        if (pkey != null) {
            if (pkey instanceof CFSecJpaSecUserPWResetHPKey) {
                this.pkey = (CFSecJpaSecUserPWResetHPKey)pkey;
            }
            else {
                throw new CFLibUnsupportedClassException(getClass(), "setPKey", "pkey", pkey, "CFSecJpaSecUserPWResetHPKey");
            }
        }
    }

    @Override
    public CFLibDbKeyHash256 getAuditClusterId() {
        return pkey.getAuditClusterId();
    }

    @Override
    public void setAuditClusterId(CFLibDbKeyHash256 auditClusterId) {
        pkey.setAuditClusterId(auditClusterId);
    }

    @Override
    public LocalDateTime getAuditStamp() {
        return pkey.getAuditStamp();
    }

    @Override
    public void setAuditStamp(LocalDateTime auditStamp) {
        pkey.setAuditStamp(auditStamp);
    }

    @Override
    public short getAuditActionId() {
        return pkey.getAuditActionId();
    }

    @Override
    public void setAuditActionId(short auditActionId) {
        pkey.setAuditActionId(auditActionId);
    }

    @Override
    public int getRequiredRevision() {
        return pkey.getRequiredRevision();
    }

    @Override
    public void setRequiredRevision(int revision) {
        pkey.setRequiredRevision(revision);
    }

    @Override
    public CFLibDbKeyHash256 getAuditSessionId() {
        return pkey.getAuditSessionId();
    }

    @Override
    public void setAuditSessionId(CFLibDbKeyHash256 auditSessionId) {
        pkey.setAuditSessionId(auditSessionId);
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
	public String getRequiredSentToEMailAddr() {
		return( requiredSentToEMailAddr );
	}

	@Override
	public void setRequiredSentToEMailAddr( String value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredSentToEMailAddr",
				1,
				"value" );
		}
		else if( value.length() > 512 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredSentToEMailAddr",
				1,
				"value.length()",
				value.length(),
				512 );
		}
		requiredSentToEMailAddr = value;
	}

	@Override
	public CFLibUuid6 getRequiredPasswordResetUuid6() {
		return( requiredPasswordResetUuid6 );
	}

	@Override
	public void setRequiredPasswordResetUuid6( CFLibUuid6 value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredPasswordResetUuid6",
				1,
				"value" );
		}
		requiredPasswordResetUuid6 = value;
	}

	@Override
	public boolean getRequiredNewAccount() {
		return( requiredNewAccount );
	}

	@Override
	public void setRequiredNewAccount( boolean value ) {
		requiredNewAccount = value;
	}

    @Override
    public boolean equals( Object obj ) {
        if (obj == null) {
            return( false );
        }
        else if (obj instanceof ICFSecSecUserPWReset) {
            ICFSecSecUserPWReset rhs = (ICFSecSecUserPWReset)obj;
		if (getPKey() != null) {
			if (rhs.getPKey() != null) {
				if (!getPKey().equals(rhs.getPKey())) {
					return( false );
				}
			}
			else {
				return( false );
			}
		}
		else if (rhs.getPKey() != null) {
			return( false );
		}

			if( getRequiredSentToEMailAddr() != null ) {
				if( rhs.getRequiredSentToEMailAddr() != null ) {
					if( ! getRequiredSentToEMailAddr().equals( rhs.getRequiredSentToEMailAddr() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSentToEMailAddr() != null ) {
					return( false );
				}
			}
			if( getRequiredPasswordResetUuid6() != null ) {
				if( rhs.getRequiredPasswordResetUuid6() != null ) {
					if( ! getRequiredPasswordResetUuid6().equals( rhs.getRequiredPasswordResetUuid6() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredPasswordResetUuid6() != null ) {
					return( false );
				}
			}
			if( getRequiredNewAccount() != rhs.getRequiredNewAccount() ) {
				return( false );
			}
            return( true );
        }
        else if (obj instanceof ICFSecSecUserPWResetH) {
            ICFSecSecUserPWResetH rhs = (ICFSecSecUserPWResetH)obj;
		if (getPKey() != null) {
			if (rhs.getPKey() != null) {
				if (!getPKey().equals(rhs.getPKey())) {
					return( false );
				}
			}
			else {
				return( false );
			}
		}
		else if (rhs.getPKey() != null) {
			return( false );
		}

			if( getRequiredSentToEMailAddr() != null ) {
				if( rhs.getRequiredSentToEMailAddr() != null ) {
					if( ! getRequiredSentToEMailAddr().equals( rhs.getRequiredSentToEMailAddr() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSentToEMailAddr() != null ) {
					return( false );
				}
			}
			if( getRequiredPasswordResetUuid6() != null ) {
				if( rhs.getRequiredPasswordResetUuid6() != null ) {
					if( ! getRequiredPasswordResetUuid6().equals( rhs.getRequiredPasswordResetUuid6() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredPasswordResetUuid6() != null ) {
					return( false );
				}
			}
			if( getRequiredNewAccount() != rhs.getRequiredNewAccount() ) {
				return( false );
			}
            return( true );
        }
        else if (obj instanceof ICFSecSecUserPWResetHPKey) {
		ICFSecSecUserPWResetHPKey rhs = (ICFSecSecUserPWResetHPKey)obj;
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
        else if (obj instanceof ICFSecSecUserPWResetByUUuid6IdxKey) {
            ICFSecSecUserPWResetByUUuid6IdxKey rhs = (ICFSecSecUserPWResetByUUuid6IdxKey)obj;
			if( getRequiredPasswordResetUuid6() != null ) {
				if( rhs.getRequiredPasswordResetUuid6() != null ) {
					if( ! getRequiredPasswordResetUuid6().equals( rhs.getRequiredPasswordResetUuid6() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredPasswordResetUuid6() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFSecSecUserPWResetBySentEMAddrIdxKey) {
            ICFSecSecUserPWResetBySentEMAddrIdxKey rhs = (ICFSecSecUserPWResetBySentEMAddrIdxKey)obj;
			if( getRequiredSentToEMailAddr() != null ) {
				if( rhs.getRequiredSentToEMailAddr() != null ) {
					if( ! getRequiredSentToEMailAddr().equals( rhs.getRequiredSentToEMailAddr() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSentToEMailAddr() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFSecSecUserPWResetByNewAcctIdxKey) {
            ICFSecSecUserPWResetByNewAcctIdxKey rhs = (ICFSecSecUserPWResetByNewAcctIdxKey)obj;
			if( getRequiredNewAccount() != rhs.getRequiredNewAccount() ) {
				return( false );
			}
            return( true );
        }
        else {
			return( false );
        }
    }
    
    @Override
    public int hashCode() {
        int hashCode = pkey.hashCode();
		if( getRequiredSentToEMailAddr() != null ) {
			hashCode = hashCode + getRequiredSentToEMailAddr().hashCode();
		}
		hashCode = hashCode + getRequiredPasswordResetUuid6().hashCode();
		if( getRequiredNewAccount() ) {
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
        else if (obj instanceof ICFSecSecUserPWReset) {
		ICFSecSecUserPWReset rhs = (ICFSecSecUserPWReset)obj;
		if (getPKey() != null) {
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
		else {
			if (rhs.getPKey() != null) {
				return( -1 );
			}
		}
			if (getRequiredSentToEMailAddr() != null) {
				if (rhs.getRequiredSentToEMailAddr() != null) {
					cmp = getRequiredSentToEMailAddr().compareTo( rhs.getRequiredSentToEMailAddr() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSentToEMailAddr() != null) {
				return( -1 );
			}
			if (getRequiredPasswordResetUuid6() != null) {
				if (rhs.getRequiredPasswordResetUuid6() != null) {
					cmp = getRequiredPasswordResetUuid6().compareTo( rhs.getRequiredPasswordResetUuid6() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredPasswordResetUuid6() != null) {
				return( -1 );
			}
			if( getRequiredNewAccount() ) {
				if( ! rhs.getRequiredNewAccount() ) {
					return( 1 );
				}
			}
			else {
				if( rhs.getRequiredNewAccount() ) {
					return( -1 );
				}
			}
            return( 0 );
        }
        else if (obj instanceof ICFSecSecUserPWResetHPKey) {
        if (getPKey() != null) {
            return( getPKey().compareTo( obj ));
        }
        else {
            return( -1 );
        }
        }
        else if (obj instanceof ICFSecSecUserPWResetH) {
		ICFSecSecUserPWResetH rhs = (ICFSecSecUserPWResetH)obj;
		if (getPKey() != null) {
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
		else {
			if (rhs.getPKey() != null) {
				return( -1 );
			}
		}
			if (getRequiredSentToEMailAddr() != null) {
				if (rhs.getRequiredSentToEMailAddr() != null) {
					cmp = getRequiredSentToEMailAddr().compareTo( rhs.getRequiredSentToEMailAddr() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSentToEMailAddr() != null) {
				return( -1 );
			}
			if (getRequiredPasswordResetUuid6() != null) {
				if (rhs.getRequiredPasswordResetUuid6() != null) {
					cmp = getRequiredPasswordResetUuid6().compareTo( rhs.getRequiredPasswordResetUuid6() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredPasswordResetUuid6() != null) {
				return( -1 );
			}
			if( getRequiredNewAccount() ) {
				if( ! rhs.getRequiredNewAccount() ) {
					return( 1 );
				}
			}
			else {
				if( rhs.getRequiredNewAccount() ) {
					return( -1 );
				}
			}
            return( 0 );
        }
        else if (obj instanceof ICFSecSecUserPWResetByUUuid6IdxKey ) {
            ICFSecSecUserPWResetByUUuid6IdxKey rhs = (ICFSecSecUserPWResetByUUuid6IdxKey)obj;
			if (getRequiredPasswordResetUuid6() != null) {
				if (rhs.getRequiredPasswordResetUuid6() != null) {
					cmp = getRequiredPasswordResetUuid6().compareTo( rhs.getRequiredPasswordResetUuid6() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredPasswordResetUuid6() != null) {
				return( -1 );
			}
            return( 0 );
        }
        else if (obj instanceof ICFSecSecUserPWResetBySentEMAddrIdxKey ) {
            ICFSecSecUserPWResetBySentEMAddrIdxKey rhs = (ICFSecSecUserPWResetBySentEMAddrIdxKey)obj;
			if (getRequiredSentToEMailAddr() != null) {
				if (rhs.getRequiredSentToEMailAddr() != null) {
					cmp = getRequiredSentToEMailAddr().compareTo( rhs.getRequiredSentToEMailAddr() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSentToEMailAddr() != null) {
				return( -1 );
			}
            return( 0 );
        }
        else if (obj instanceof ICFSecSecUserPWResetByNewAcctIdxKey ) {
            ICFSecSecUserPWResetByNewAcctIdxKey rhs = (ICFSecSecUserPWResetByNewAcctIdxKey)obj;
			if( getRequiredNewAccount() ) {
				if( ! rhs.getRequiredNewAccount() ) {
					return( 1 );
				}
			}
			else {
				if( rhs.getRequiredNewAccount() ) {
					return( -1 );
				}
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
    public void set( ICFSecSecUserPWReset src ) {
		setSecUserPWReset( src );
    }

	@Override
    public void setSecUserPWReset( ICFSecSecUserPWReset src ) {
		setRequiredSecUserId( src.getRequiredSecUserId() );
		setRequiredSentToEMailAddr( src.getRequiredSentToEMailAddr() );
		setRequiredPasswordResetUuid6( src.getRequiredPasswordResetUuid6() );
		setRequiredNewAccount( src.getRequiredNewAccount() );
		setRequiredRevision( src.getRequiredRevision() );
    }

	@Override
    public void set( ICFSecSecUserPWResetH src ) {
		setSecUserPWReset( src );
    }

	@Override
    public void setSecUserPWReset( ICFSecSecUserPWResetH src ) {
		setRequiredSecUserId( src.getRequiredSecUserId() );
		setRequiredSentToEMailAddr( src.getRequiredSentToEMailAddr() );
		setRequiredPasswordResetUuid6( src.getRequiredPasswordResetUuid6() );
		setRequiredNewAccount( src.getRequiredNewAccount() );
		setRequiredRevision( src.getRequiredRevision() );
    }

    public String getXmlAttrFragment() {
        String ret = pkey.getXmlAttrFragment() 
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredSentToEMailAddr=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredSentToEMailAddr() ) + "\""
			+ " RequiredPasswordResetUuid6=" + "\"" + getRequiredPasswordResetUuid6().toString() + "\""
			+ " RequiredNewAccount=" + (( getRequiredNewAccount() ) ? "\"true\"" : "\"false\"" );
        return( ret );
    }

    public String toString() {
        String ret = "<CFSecJpaSecUserPWResetH" + getXmlAttrFragment() + "/>";
        return( ret );
    }
}

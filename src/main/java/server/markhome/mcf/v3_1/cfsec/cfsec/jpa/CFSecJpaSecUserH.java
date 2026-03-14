// Description: Java 25 JPA implementation of SecUser history objects

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
 *  CFSecJpaSecUserH provides history objects matching the CFSecSecUser change history.
 */
@Entity
@Table(
    name = "SecUser_h", schema = "CFSec31",
    indexes = {
        @Index(name = "SecUserIdIdx_h", columnList = "auditClusterId, auditStamp, auditAction, requiredRevision, auditSessionId, SecUserId", unique = true),
        @Index(name = "SecUserLoginIdx_h", columnList = "login_id", unique = true),
        @Index(name = "SecUserUEMailConfirmIdx_h", columnList = "em_confuuid6", unique = false),
        @Index(name = "SecUserPasswordResetIdx_h", columnList = "pwdrstuuid6", unique = false),
        @Index(name = "SecUserDefDevIdx_h", columnList = "DefDevUserId, DefDevName", unique = false)
    }
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaSecUserH
    implements ICFSecSecUserH, Comparable<Object>, Serializable
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
    protected CFSecJpaSecUserHPKey pkey;
	@AttributeOverrides({
		@AttributeOverride( name="bytes", column = @Column( name="CreatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 createdByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecUser.S_INIT_CREATED_BY);

	@Column(name="CreatedAt", nullable=false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column= @Column( name="UpdatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 updatedByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecUser.S_INIT_UPDATED_BY);

	@Column(name="UpdatedAt", nullable=false)
	protected LocalDateTime updatedAt = LocalDateTime.now();
	@Column( name="login_id", nullable=false, length=32 )
	protected String requiredLoginId;
	@Column( name="email_addr", nullable=false, length=512 )
	protected String requiredEMailAddress;
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="em_confuuid6", nullable=true, length=CFLibUuid6.TOTAL_BYTES ) )
	})
	protected CFLibUuid6 optionalEMailConfirmUuid6;
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="DefDevUserId", nullable=true, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 optionalDfltDevUserId;
	@Column( name="DefDevName", nullable=true, length=127 )
	protected String optionalDfltDevName;
	@Column( name="pwd_hash", nullable=false, length=256 )
	protected String requiredPasswordHash;
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="pwdrstuuid6", nullable=true, length=CFLibUuid6.TOTAL_BYTES ) )
	})
	protected CFLibUuid6 optionalPasswordResetUuid6;

    public CFSecJpaSecUserH() {
            // The primary key member attributes are initialized on construction
            pkey = new CFSecJpaSecUserHPKey();
		requiredLoginId = ICFSecSecUser.LOGINID_INIT_VALUE;
		requiredEMailAddress = ICFSecSecUser.EMAILADDRESS_INIT_VALUE;
		optionalEMailConfirmUuid6 = null;
		optionalDfltDevUserId = CFLibDbKeyHash256.nullGet();
		optionalDfltDevName = null;
		optionalPasswordResetUuid6 = null;
    }

    @Override
    public int getClassCode() {
            return( ICFSecSecUser.CLASS_CODE );
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
    public ICFSecSecUserHPKey getPKey() {
        return( pkey );
    }

    @Override
    public void setPKey( ICFSecSecUserHPKey pkey ) {
        if (pkey != null) {
            if (pkey instanceof CFSecJpaSecUserHPKey) {
                this.pkey = (CFSecJpaSecUserHPKey)pkey;
            }
            else {
                throw new CFLibUnsupportedClassException(getClass(), "setPKey", "pkey", pkey, "CFSecJpaSecUserHPKey");
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
	public String getRequiredLoginId() {
		return( requiredLoginId );
	}

	@Override
	public void setRequiredLoginId( String value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredLoginId",
				1,
				"value" );
		}
		else if( value.length() > 32 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredLoginId",
				1,
				"value.length()",
				value.length(),
				32 );
		}
		requiredLoginId = value;
	}

	@Override
	public String getRequiredEMailAddress() {
		return( requiredEMailAddress );
	}

	@Override
	public void setRequiredEMailAddress( String value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredEMailAddress",
				1,
				"value" );
		}
		else if( value.length() > 512 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredEMailAddress",
				1,
				"value.length()",
				value.length(),
				512 );
		}
		requiredEMailAddress = value;
	}

	@Override
	public CFLibUuid6 getOptionalEMailConfirmUuid6() {
		return( optionalEMailConfirmUuid6 );
	}

	@Override
	public void setOptionalEMailConfirmUuid6( CFLibUuid6 value ) {
		optionalEMailConfirmUuid6 = value;
	}

	@Override
	public CFLibDbKeyHash256 getOptionalDfltDevUserId() {
		return( optionalDfltDevUserId );
	}

	@Override
	public void setOptionalDfltDevUserId( CFLibDbKeyHash256 value ) {
		optionalDfltDevUserId = value;
	}

	@Override
	public String getOptionalDfltDevName() {
		return( optionalDfltDevName );
	}

	@Override
	public void setOptionalDfltDevName( String value ) {
		if( value != null && value.length() > 127 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setOptionalDfltDevName",
				1,
				"value.length()",
				value.length(),
				127 );
		}
		optionalDfltDevName = value;
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
	public CFLibUuid6 getOptionalPasswordResetUuid6() {
		return( optionalPasswordResetUuid6 );
	}

	@Override
	public void setOptionalPasswordResetUuid6( CFLibUuid6 value ) {
		optionalPasswordResetUuid6 = value;
	}

    @Override
    public boolean equals( Object obj ) {
        if (obj == null) {
            return( false );
        }
        else if (obj instanceof ICFSecSecUser) {
            ICFSecSecUser rhs = (ICFSecSecUser)obj;
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

			if( getRequiredLoginId() != null ) {
				if( rhs.getRequiredLoginId() != null ) {
					if( ! getRequiredLoginId().equals( rhs.getRequiredLoginId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredLoginId() != null ) {
					return( false );
				}
			}
			if( getRequiredEMailAddress() != null ) {
				if( rhs.getRequiredEMailAddress() != null ) {
					if( ! getRequiredEMailAddress().equals( rhs.getRequiredEMailAddress() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredEMailAddress() != null ) {
					return( false );
				}
			}
			if( getOptionalEMailConfirmUuid6() != null ) {
				if( rhs.getOptionalEMailConfirmUuid6() != null ) {
					if( ! getOptionalEMailConfirmUuid6().equals( rhs.getOptionalEMailConfirmUuid6() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalEMailConfirmUuid6() != null ) {
					return( false );
				}
			}
			if( getOptionalDfltDevUserId() != null ) {
				if( rhs.getOptionalDfltDevUserId() != null ) {
					if( ! getOptionalDfltDevUserId().equals( rhs.getOptionalDfltDevUserId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalDfltDevUserId() != null ) {
					return( false );
				}
			}
			if( getOptionalDfltDevName() != null ) {
				if( rhs.getOptionalDfltDevName() != null ) {
					if( ! getOptionalDfltDevName().equals( rhs.getOptionalDfltDevName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalDfltDevName() != null ) {
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
			if( getOptionalPasswordResetUuid6() != null ) {
				if( rhs.getOptionalPasswordResetUuid6() != null ) {
					if( ! getOptionalPasswordResetUuid6().equals( rhs.getOptionalPasswordResetUuid6() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalPasswordResetUuid6() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFSecSecUserH) {
            ICFSecSecUserH rhs = (ICFSecSecUserH)obj;
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

			if( getRequiredLoginId() != null ) {
				if( rhs.getRequiredLoginId() != null ) {
					if( ! getRequiredLoginId().equals( rhs.getRequiredLoginId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredLoginId() != null ) {
					return( false );
				}
			}
			if( getRequiredEMailAddress() != null ) {
				if( rhs.getRequiredEMailAddress() != null ) {
					if( ! getRequiredEMailAddress().equals( rhs.getRequiredEMailAddress() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredEMailAddress() != null ) {
					return( false );
				}
			}
			if( getOptionalEMailConfirmUuid6() != null ) {
				if( rhs.getOptionalEMailConfirmUuid6() != null ) {
					if( ! getOptionalEMailConfirmUuid6().equals( rhs.getOptionalEMailConfirmUuid6() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalEMailConfirmUuid6() != null ) {
					return( false );
				}
			}
			if( getOptionalDfltDevUserId() != null ) {
				if( rhs.getOptionalDfltDevUserId() != null ) {
					if( ! getOptionalDfltDevUserId().equals( rhs.getOptionalDfltDevUserId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalDfltDevUserId() != null ) {
					return( false );
				}
			}
			if( getOptionalDfltDevName() != null ) {
				if( rhs.getOptionalDfltDevName() != null ) {
					if( ! getOptionalDfltDevName().equals( rhs.getOptionalDfltDevName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalDfltDevName() != null ) {
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
			if( getOptionalPasswordResetUuid6() != null ) {
				if( rhs.getOptionalPasswordResetUuid6() != null ) {
					if( ! getOptionalPasswordResetUuid6().equals( rhs.getOptionalPasswordResetUuid6() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalPasswordResetUuid6() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFSecSecUserHPKey) {
		ICFSecSecUserHPKey rhs = (ICFSecSecUserHPKey)obj;
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
        else if (obj instanceof ICFSecSecUserByULoginIdxKey) {
            ICFSecSecUserByULoginIdxKey rhs = (ICFSecSecUserByULoginIdxKey)obj;
			if( getRequiredLoginId() != null ) {
				if( rhs.getRequiredLoginId() != null ) {
					if( ! getRequiredLoginId().equals( rhs.getRequiredLoginId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredLoginId() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFSecSecUserByEMConfIdxKey) {
            ICFSecSecUserByEMConfIdxKey rhs = (ICFSecSecUserByEMConfIdxKey)obj;
			if( getOptionalEMailConfirmUuid6() != null ) {
				if( rhs.getOptionalEMailConfirmUuid6() != null ) {
					if( ! getOptionalEMailConfirmUuid6().equals( rhs.getOptionalEMailConfirmUuid6() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalEMailConfirmUuid6() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFSecSecUserByPwdResetIdxKey) {
            ICFSecSecUserByPwdResetIdxKey rhs = (ICFSecSecUserByPwdResetIdxKey)obj;
			if( getOptionalPasswordResetUuid6() != null ) {
				if( rhs.getOptionalPasswordResetUuid6() != null ) {
					if( ! getOptionalPasswordResetUuid6().equals( rhs.getOptionalPasswordResetUuid6() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalPasswordResetUuid6() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFSecSecUserByDefDevIdxKey) {
            ICFSecSecUserByDefDevIdxKey rhs = (ICFSecSecUserByDefDevIdxKey)obj;
			if( getOptionalDfltDevUserId() != null ) {
				if( rhs.getOptionalDfltDevUserId() != null ) {
					if( ! getOptionalDfltDevUserId().equals( rhs.getOptionalDfltDevUserId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalDfltDevUserId() != null ) {
					return( false );
				}
			}
			if( getOptionalDfltDevName() != null ) {
				if( rhs.getOptionalDfltDevName() != null ) {
					if( ! getOptionalDfltDevName().equals( rhs.getOptionalDfltDevName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getOptionalDfltDevName() != null ) {
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
        int hashCode = pkey.hashCode();
		if( getRequiredLoginId() != null ) {
			hashCode = hashCode + getRequiredLoginId().hashCode();
		}
		if( getRequiredEMailAddress() != null ) {
			hashCode = hashCode + getRequiredEMailAddress().hashCode();
		}
		if( getOptionalEMailConfirmUuid6() != null ) {
			hashCode = hashCode + getOptionalEMailConfirmUuid6().hashCode();
		}
		if( getOptionalDfltDevUserId() != null ) {
			hashCode = hashCode + getOptionalDfltDevUserId().hashCode();
		}
		if( getOptionalDfltDevName() != null ) {
			hashCode = hashCode + getOptionalDfltDevName().hashCode();
		}
		if( getRequiredPasswordHash() != null ) {
			hashCode = hashCode + getRequiredPasswordHash().hashCode();
		}
		if( getOptionalPasswordResetUuid6() != null ) {
			hashCode = hashCode + getOptionalPasswordResetUuid6().hashCode();
		}
        return( hashCode & 0x7fffffff );
    }

    @Override
    public int compareTo( Object obj ) {
        int cmp;
        if (obj == null) {
            return( 1 );
        }
        else if (obj instanceof ICFSecSecUser) {
		ICFSecSecUser rhs = (ICFSecSecUser)obj;
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
			if (getRequiredLoginId() != null) {
				if (rhs.getRequiredLoginId() != null) {
					cmp = getRequiredLoginId().compareTo( rhs.getRequiredLoginId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredLoginId() != null) {
				return( -1 );
			}
			if (getRequiredEMailAddress() != null) {
				if (rhs.getRequiredEMailAddress() != null) {
					cmp = getRequiredEMailAddress().compareTo( rhs.getRequiredEMailAddress() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredEMailAddress() != null) {
				return( -1 );
			}
			if( getOptionalEMailConfirmUuid6() != null ) {
				if( rhs.getOptionalEMailConfirmUuid6() != null ) {
					cmp = getOptionalEMailConfirmUuid6().compareTo( rhs.getOptionalEMailConfirmUuid6() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalEMailConfirmUuid6() != null ) {
					return( -1 );
				}
			}
			if( getOptionalDfltDevUserId() != null ) {
				if( rhs.getOptionalDfltDevUserId() != null ) {
					cmp = getOptionalDfltDevUserId().compareTo( rhs.getOptionalDfltDevUserId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalDfltDevUserId() != null ) {
					return( -1 );
				}
			}
			if( getOptionalDfltDevName() != null ) {
				if( rhs.getOptionalDfltDevName() != null ) {
					cmp = getOptionalDfltDevName().compareTo( rhs.getOptionalDfltDevName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalDfltDevName() != null ) {
					return( -1 );
				}
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
			if( getOptionalPasswordResetUuid6() != null ) {
				if( rhs.getOptionalPasswordResetUuid6() != null ) {
					cmp = getOptionalPasswordResetUuid6().compareTo( rhs.getOptionalPasswordResetUuid6() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalPasswordResetUuid6() != null ) {
					return( -1 );
				}
			}
            return( 0 );
        }
        else if (obj instanceof ICFSecSecUserHPKey) {
        if (getPKey() != null) {
            return( getPKey().compareTo( obj ));
        }
        else {
            return( -1 );
        }
        }
        else if (obj instanceof ICFSecSecUserH) {
		ICFSecSecUserH rhs = (ICFSecSecUserH)obj;
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
			if (getRequiredLoginId() != null) {
				if (rhs.getRequiredLoginId() != null) {
					cmp = getRequiredLoginId().compareTo( rhs.getRequiredLoginId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredLoginId() != null) {
				return( -1 );
			}
			if (getRequiredEMailAddress() != null) {
				if (rhs.getRequiredEMailAddress() != null) {
					cmp = getRequiredEMailAddress().compareTo( rhs.getRequiredEMailAddress() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredEMailAddress() != null) {
				return( -1 );
			}
			if( getOptionalEMailConfirmUuid6() != null ) {
				if( rhs.getOptionalEMailConfirmUuid6() != null ) {
					cmp = getOptionalEMailConfirmUuid6().compareTo( rhs.getOptionalEMailConfirmUuid6() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalEMailConfirmUuid6() != null ) {
					return( -1 );
				}
			}
			if( getOptionalDfltDevUserId() != null ) {
				if( rhs.getOptionalDfltDevUserId() != null ) {
					cmp = getOptionalDfltDevUserId().compareTo( rhs.getOptionalDfltDevUserId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalDfltDevUserId() != null ) {
					return( -1 );
				}
			}
			if( getOptionalDfltDevName() != null ) {
				if( rhs.getOptionalDfltDevName() != null ) {
					cmp = getOptionalDfltDevName().compareTo( rhs.getOptionalDfltDevName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalDfltDevName() != null ) {
					return( -1 );
				}
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
			if( getOptionalPasswordResetUuid6() != null ) {
				if( rhs.getOptionalPasswordResetUuid6() != null ) {
					cmp = getOptionalPasswordResetUuid6().compareTo( rhs.getOptionalPasswordResetUuid6() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalPasswordResetUuid6() != null ) {
					return( -1 );
				}
			}
            return( 0 );
        }
        else if (obj instanceof ICFSecSecUserByULoginIdxKey ) {
            ICFSecSecUserByULoginIdxKey rhs = (ICFSecSecUserByULoginIdxKey)obj;
			if (getRequiredLoginId() != null) {
				if (rhs.getRequiredLoginId() != null) {
					cmp = getRequiredLoginId().compareTo( rhs.getRequiredLoginId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredLoginId() != null) {
				return( -1 );
			}
            return( 0 );
        }
        else if (obj instanceof ICFSecSecUserByEMConfIdxKey ) {
            ICFSecSecUserByEMConfIdxKey rhs = (ICFSecSecUserByEMConfIdxKey)obj;
			if( getOptionalEMailConfirmUuid6() != null ) {
				if( rhs.getOptionalEMailConfirmUuid6() != null ) {
					cmp = getOptionalEMailConfirmUuid6().compareTo( rhs.getOptionalEMailConfirmUuid6() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalEMailConfirmUuid6() != null ) {
					return( -1 );
				}
			}
            return( 0 );
        }
        else if (obj instanceof ICFSecSecUserByPwdResetIdxKey ) {
            ICFSecSecUserByPwdResetIdxKey rhs = (ICFSecSecUserByPwdResetIdxKey)obj;
			if( getOptionalPasswordResetUuid6() != null ) {
				if( rhs.getOptionalPasswordResetUuid6() != null ) {
					cmp = getOptionalPasswordResetUuid6().compareTo( rhs.getOptionalPasswordResetUuid6() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalPasswordResetUuid6() != null ) {
					return( -1 );
				}
			}
            return( 0 );
        }
        else if (obj instanceof ICFSecSecUserByDefDevIdxKey ) {
            ICFSecSecUserByDefDevIdxKey rhs = (ICFSecSecUserByDefDevIdxKey)obj;
			if( getOptionalDfltDevUserId() != null ) {
				if( rhs.getOptionalDfltDevUserId() != null ) {
					cmp = getOptionalDfltDevUserId().compareTo( rhs.getOptionalDfltDevUserId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalDfltDevUserId() != null ) {
					return( -1 );
				}
			}
			if( getOptionalDfltDevName() != null ) {
				if( rhs.getOptionalDfltDevName() != null ) {
					cmp = getOptionalDfltDevName().compareTo( rhs.getOptionalDfltDevName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else {
				if( rhs.getOptionalDfltDevName() != null ) {
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
    public void set( ICFSecSecUser src ) {
		setSecUser( src );
    }

	@Override
    public void setSecUser( ICFSecSecUser src ) {
		setRequiredSecUserId( src.getRequiredSecUserId() );
		setRequiredLoginId( src.getRequiredLoginId() );
		setRequiredEMailAddress( src.getRequiredEMailAddress() );
		setOptionalEMailConfirmUuid6( src.getOptionalEMailConfirmUuid6() );
		setOptionalDfltDevUserId( src.getOptionalDfltDevUserId() );
		setOptionalDfltDevName( src.getOptionalDfltDevName() );
		setRequiredPasswordHash( src.getRequiredPasswordHash() );
		setOptionalPasswordResetUuid6( src.getOptionalPasswordResetUuid6() );
		setRequiredRevision( src.getRequiredRevision() );
    }

	@Override
    public void set( ICFSecSecUserH src ) {
		setSecUser( src );
    }

	@Override
    public void setSecUser( ICFSecSecUserH src ) {
		setRequiredSecUserId( src.getRequiredSecUserId() );
		setRequiredLoginId( src.getRequiredLoginId() );
		setRequiredEMailAddress( src.getRequiredEMailAddress() );
		setOptionalEMailConfirmUuid6( src.getOptionalEMailConfirmUuid6() );
		setOptionalDfltDevUserId( src.getOptionalDfltDevUserId() );
		setOptionalDfltDevName( src.getOptionalDfltDevName() );
		setRequiredPasswordHash( src.getRequiredPasswordHash() );
		setOptionalPasswordResetUuid6( src.getOptionalPasswordResetUuid6() );
		setRequiredRevision( src.getRequiredRevision() );
    }

    public String getXmlAttrFragment() {
        String ret = pkey.getXmlAttrFragment() 
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredLoginId=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredLoginId() ) + "\""
			+ " RequiredEMailAddress=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredEMailAddress() ) + "\""
			+ " OptionalEMailConfirmUuid6=" + ( ( getOptionalEMailConfirmUuid6() == null ) ? "null" : "\"" + getOptionalEMailConfirmUuid6().toString() + "\"" )
			+ " OptionalDfltDevUserId=" + ( ( getOptionalDfltDevUserId() == null ) ? "null" : "\"" + getOptionalDfltDevUserId().toString() + "\"" )
			+ " OptionalDfltDevName=" + ( ( getOptionalDfltDevName() == null ) ? "null" : "\"" + StringEscapeUtils.escapeXml11( getOptionalDfltDevName() ) + "\"" )
			+ " RequiredPasswordHash=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredPasswordHash() ) + "\""
			+ " OptionalPasswordResetUuid6=" + ( ( getOptionalPasswordResetUuid6() == null ) ? "null" : "\"" + getOptionalPasswordResetUuid6().toString() + "\"" );
        return( ret );
    }

    public String toString() {
        String ret = "<CFSecJpaSecUserH" + getXmlAttrFragment() + "/>";
        return( ret );
    }
}

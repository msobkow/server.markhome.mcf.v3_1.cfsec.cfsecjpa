// Description: Java 25 JPA implementation of SecUserEMConf history objects

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
 *  CFSecJpaSecUserEMConfH provides history objects matching the CFSecSecUserEMConf change history.
 */
@Entity
@Table(
    name = "secusremcnf_h", schema = "CFSec31",
    indexes = {
        @Index(name = "SecUserEMConfIdIdx_h", columnList = "auditClusterId, auditStamp, auditAction, requiredRevision, auditSessionId, SecUserId", unique = true),
        @Index(name = "SecUserEMConfUuid6Idx_h", columnList = "conf_uuid6", unique = true),
        @Index(name = "SecUserEMConfConfirmingAddrIdx_h", columnList = "conf_emailaddr", unique = false),
        @Index(name = "SecUserEMConfSentStampIdx_h", columnList = "conf_sent", unique = false),
        @Index(name = "SecUserEMConfNewAcctIdx_h", columnList = "conf_newacct", unique = false)
    }
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaSecUserEMConfH
    implements ICFSecSecUserEMConfH, Comparable<Object>, Serializable
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
    protected CFSecJpaSecUserEMConfHPKey pkey;
	@AttributeOverrides({
		@AttributeOverride( name="bytes", column = @Column( name="CreatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 createdByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecUserEMConf.S_INIT_CREATED_BY);

	@Column(name="CreatedAt", nullable=false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column= @Column( name="UpdatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 updatedByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecUserEMConf.S_INIT_UPDATED_BY);

	@Column(name="UpdatedAt", nullable=false)
	protected LocalDateTime updatedAt = LocalDateTime.now();
	@Column( name="conf_emailaddr", nullable=false, length=512 )
	protected String requiredConfirmEMailAddr;
	@Column( name="conf_sent", nullable=false )
	protected LocalDateTime requiredEMailSentStamp;
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="conf_uuid6", nullable=false, length=CFLibUuid6.TOTAL_BYTES ) )
	})
	protected CFLibUuid6 requiredEMConfirmationUuid6;
	@Column( name="conf_newacct", nullable=false )
	protected boolean requiredNewAccount;

    public CFSecJpaSecUserEMConfH() {
            // The primary key member attributes are initialized on construction
            pkey = new CFSecJpaSecUserEMConfHPKey();
		requiredConfirmEMailAddr = ICFSecSecUserEMConf.CONFIRMEMAILADDR_INIT_VALUE;
		requiredEMailSentStamp = CFLibXmlUtil.parseTimestamp("2020-01-01T00:00:00");
		requiredNewAccount = ICFSecSecUserEMConf.NEWACCOUNT_INIT_VALUE;
    }

    @Override
    public int getClassCode() {
            return( ICFSecSecUserEMConf.CLASS_CODE );
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
    public ICFSecSecUserEMConfHPKey getPKey() {
        return( pkey );
    }

    @Override
    public void setPKey( ICFSecSecUserEMConfHPKey pkey ) {
        if (pkey != null) {
            if (pkey instanceof CFSecJpaSecUserEMConfHPKey) {
                this.pkey = (CFSecJpaSecUserEMConfHPKey)pkey;
            }
            else {
                throw new CFLibUnsupportedClassException(getClass(), "setPKey", "pkey", pkey, "CFSecJpaSecUserEMConfHPKey");
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
	public String getRequiredConfirmEMailAddr() {
		return( requiredConfirmEMailAddr );
	}

	@Override
	public void setRequiredConfirmEMailAddr( String value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredConfirmEMailAddr",
				1,
				"value" );
		}
		else if( value.length() > 512 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredConfirmEMailAddr",
				1,
				"value.length()",
				value.length(),
				512 );
		}
		requiredConfirmEMailAddr = value;
	}

	@Override
	public LocalDateTime getRequiredEMailSentStamp() {
		return( requiredEMailSentStamp );
	}

	@Override
	public void setRequiredEMailSentStamp( LocalDateTime value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredEMailSentStamp",
				1,
				"value" );
		}
		requiredEMailSentStamp = value;
	}

	@Override
	public CFLibUuid6 getRequiredEMConfirmationUuid6() {
		return( requiredEMConfirmationUuid6 );
	}

	@Override
	public void setRequiredEMConfirmationUuid6( CFLibUuid6 value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredEMConfirmationUuid6",
				1,
				"value" );
		}
		requiredEMConfirmationUuid6 = value;
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
        else if (obj instanceof ICFSecSecUserEMConf) {
            ICFSecSecUserEMConf rhs = (ICFSecSecUserEMConf)obj;
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

			if( getRequiredConfirmEMailAddr() != null ) {
				if( rhs.getRequiredConfirmEMailAddr() != null ) {
					if( ! getRequiredConfirmEMailAddr().equals( rhs.getRequiredConfirmEMailAddr() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredConfirmEMailAddr() != null ) {
					return( false );
				}
			}
			if( getRequiredEMailSentStamp() != null ) {
				if( rhs.getRequiredEMailSentStamp() != null ) {
					if( ! getRequiredEMailSentStamp().equals( rhs.getRequiredEMailSentStamp() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredEMailSentStamp() != null ) {
					return( false );
				}
			}
			if( getRequiredEMConfirmationUuid6() != null ) {
				if( rhs.getRequiredEMConfirmationUuid6() != null ) {
					if( ! getRequiredEMConfirmationUuid6().equals( rhs.getRequiredEMConfirmationUuid6() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredEMConfirmationUuid6() != null ) {
					return( false );
				}
			}
			if( getRequiredNewAccount() != rhs.getRequiredNewAccount() ) {
				return( false );
			}
            return( true );
        }
        else if (obj instanceof ICFSecSecUserEMConfH) {
            ICFSecSecUserEMConfH rhs = (ICFSecSecUserEMConfH)obj;
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

			if( getRequiredConfirmEMailAddr() != null ) {
				if( rhs.getRequiredConfirmEMailAddr() != null ) {
					if( ! getRequiredConfirmEMailAddr().equals( rhs.getRequiredConfirmEMailAddr() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredConfirmEMailAddr() != null ) {
					return( false );
				}
			}
			if( getRequiredEMailSentStamp() != null ) {
				if( rhs.getRequiredEMailSentStamp() != null ) {
					if( ! getRequiredEMailSentStamp().equals( rhs.getRequiredEMailSentStamp() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredEMailSentStamp() != null ) {
					return( false );
				}
			}
			if( getRequiredEMConfirmationUuid6() != null ) {
				if( rhs.getRequiredEMConfirmationUuid6() != null ) {
					if( ! getRequiredEMConfirmationUuid6().equals( rhs.getRequiredEMConfirmationUuid6() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredEMConfirmationUuid6() != null ) {
					return( false );
				}
			}
			if( getRequiredNewAccount() != rhs.getRequiredNewAccount() ) {
				return( false );
			}
            return( true );
        }
        else if (obj instanceof ICFSecSecUserEMConfHPKey) {
		ICFSecSecUserEMConfHPKey rhs = (ICFSecSecUserEMConfHPKey)obj;
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
        else if (obj instanceof ICFSecSecUserEMConfByUUuid6IdxKey) {
            ICFSecSecUserEMConfByUUuid6IdxKey rhs = (ICFSecSecUserEMConfByUUuid6IdxKey)obj;
			if( getRequiredEMConfirmationUuid6() != null ) {
				if( rhs.getRequiredEMConfirmationUuid6() != null ) {
					if( ! getRequiredEMConfirmationUuid6().equals( rhs.getRequiredEMConfirmationUuid6() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredEMConfirmationUuid6() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFSecSecUserEMConfByConfEMAddrIdxKey) {
            ICFSecSecUserEMConfByConfEMAddrIdxKey rhs = (ICFSecSecUserEMConfByConfEMAddrIdxKey)obj;
			if( getRequiredConfirmEMailAddr() != null ) {
				if( rhs.getRequiredConfirmEMailAddr() != null ) {
					if( ! getRequiredConfirmEMailAddr().equals( rhs.getRequiredConfirmEMailAddr() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredConfirmEMailAddr() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFSecSecUserEMConfBySentStampIdxKey) {
            ICFSecSecUserEMConfBySentStampIdxKey rhs = (ICFSecSecUserEMConfBySentStampIdxKey)obj;
			if( getRequiredEMailSentStamp() != null ) {
				if( rhs.getRequiredEMailSentStamp() != null ) {
					if( ! getRequiredEMailSentStamp().equals( rhs.getRequiredEMailSentStamp() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredEMailSentStamp() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFSecSecUserEMConfByNewAcctIdxKey) {
            ICFSecSecUserEMConfByNewAcctIdxKey rhs = (ICFSecSecUserEMConfByNewAcctIdxKey)obj;
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
		if( getRequiredConfirmEMailAddr() != null ) {
			hashCode = hashCode + getRequiredConfirmEMailAddr().hashCode();
		}
		if( getRequiredEMailSentStamp() != null ) {
			hashCode = hashCode + getRequiredEMailSentStamp().hashCode();
		}
		hashCode = hashCode + getRequiredEMConfirmationUuid6().hashCode();
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
        else if (obj instanceof ICFSecSecUserEMConf) {
		ICFSecSecUserEMConf rhs = (ICFSecSecUserEMConf)obj;
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
			if (getRequiredConfirmEMailAddr() != null) {
				if (rhs.getRequiredConfirmEMailAddr() != null) {
					cmp = getRequiredConfirmEMailAddr().compareTo( rhs.getRequiredConfirmEMailAddr() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredConfirmEMailAddr() != null) {
				return( -1 );
			}
			if (getRequiredEMailSentStamp() != null) {
				if (rhs.getRequiredEMailSentStamp() != null) {
					cmp = getRequiredEMailSentStamp().compareTo( rhs.getRequiredEMailSentStamp() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredEMailSentStamp() != null) {
				return( -1 );
			}
			if (getRequiredEMConfirmationUuid6() != null) {
				if (rhs.getRequiredEMConfirmationUuid6() != null) {
					cmp = getRequiredEMConfirmationUuid6().compareTo( rhs.getRequiredEMConfirmationUuid6() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredEMConfirmationUuid6() != null) {
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
        else if (obj instanceof ICFSecSecUserEMConfHPKey) {
        if (getPKey() != null) {
            return( getPKey().compareTo( obj ));
        }
        else {
            return( -1 );
        }
        }
        else if (obj instanceof ICFSecSecUserEMConfH) {
		ICFSecSecUserEMConfH rhs = (ICFSecSecUserEMConfH)obj;
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
			if (getRequiredConfirmEMailAddr() != null) {
				if (rhs.getRequiredConfirmEMailAddr() != null) {
					cmp = getRequiredConfirmEMailAddr().compareTo( rhs.getRequiredConfirmEMailAddr() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredConfirmEMailAddr() != null) {
				return( -1 );
			}
			if (getRequiredEMailSentStamp() != null) {
				if (rhs.getRequiredEMailSentStamp() != null) {
					cmp = getRequiredEMailSentStamp().compareTo( rhs.getRequiredEMailSentStamp() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredEMailSentStamp() != null) {
				return( -1 );
			}
			if (getRequiredEMConfirmationUuid6() != null) {
				if (rhs.getRequiredEMConfirmationUuid6() != null) {
					cmp = getRequiredEMConfirmationUuid6().compareTo( rhs.getRequiredEMConfirmationUuid6() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredEMConfirmationUuid6() != null) {
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
        else if (obj instanceof ICFSecSecUserEMConfByUUuid6IdxKey ) {
            ICFSecSecUserEMConfByUUuid6IdxKey rhs = (ICFSecSecUserEMConfByUUuid6IdxKey)obj;
			if (getRequiredEMConfirmationUuid6() != null) {
				if (rhs.getRequiredEMConfirmationUuid6() != null) {
					cmp = getRequiredEMConfirmationUuid6().compareTo( rhs.getRequiredEMConfirmationUuid6() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredEMConfirmationUuid6() != null) {
				return( -1 );
			}
            return( 0 );
        }
        else if (obj instanceof ICFSecSecUserEMConfByConfEMAddrIdxKey ) {
            ICFSecSecUserEMConfByConfEMAddrIdxKey rhs = (ICFSecSecUserEMConfByConfEMAddrIdxKey)obj;
			if (getRequiredConfirmEMailAddr() != null) {
				if (rhs.getRequiredConfirmEMailAddr() != null) {
					cmp = getRequiredConfirmEMailAddr().compareTo( rhs.getRequiredConfirmEMailAddr() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredConfirmEMailAddr() != null) {
				return( -1 );
			}
            return( 0 );
        }
        else if (obj instanceof ICFSecSecUserEMConfBySentStampIdxKey ) {
            ICFSecSecUserEMConfBySentStampIdxKey rhs = (ICFSecSecUserEMConfBySentStampIdxKey)obj;
			if (getRequiredEMailSentStamp() != null) {
				if (rhs.getRequiredEMailSentStamp() != null) {
					cmp = getRequiredEMailSentStamp().compareTo( rhs.getRequiredEMailSentStamp() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredEMailSentStamp() != null) {
				return( -1 );
			}
            return( 0 );
        }
        else if (obj instanceof ICFSecSecUserEMConfByNewAcctIdxKey ) {
            ICFSecSecUserEMConfByNewAcctIdxKey rhs = (ICFSecSecUserEMConfByNewAcctIdxKey)obj;
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
    public void set( ICFSecSecUserEMConf src ) {
		setSecUserEMConf( src );
    }

	@Override
    public void setSecUserEMConf( ICFSecSecUserEMConf src ) {
		setRequiredSecUserId( src.getRequiredSecUserId() );
		setRequiredConfirmEMailAddr( src.getRequiredConfirmEMailAddr() );
		setRequiredEMailSentStamp( src.getRequiredEMailSentStamp() );
		setRequiredEMConfirmationUuid6( src.getRequiredEMConfirmationUuid6() );
		setRequiredNewAccount( src.getRequiredNewAccount() );
		setRequiredRevision( src.getRequiredRevision() );
    }

	@Override
    public void set( ICFSecSecUserEMConfH src ) {
		setSecUserEMConf( src );
    }

	@Override
    public void setSecUserEMConf( ICFSecSecUserEMConfH src ) {
		setRequiredSecUserId( src.getRequiredSecUserId() );
		setRequiredConfirmEMailAddr( src.getRequiredConfirmEMailAddr() );
		setRequiredEMailSentStamp( src.getRequiredEMailSentStamp() );
		setRequiredEMConfirmationUuid6( src.getRequiredEMConfirmationUuid6() );
		setRequiredNewAccount( src.getRequiredNewAccount() );
		setRequiredRevision( src.getRequiredRevision() );
    }

    public String getXmlAttrFragment() {
        String ret = pkey.getXmlAttrFragment() 
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredConfirmEMailAddr=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredConfirmEMailAddr() ) + "\""
			+ " RequiredEMailSentStamp=" + "\"" + getRequiredEMailSentStamp().toString() + "\""
			+ " RequiredEMConfirmationUuid6=" + "\"" + getRequiredEMConfirmationUuid6().toString() + "\""
			+ " RequiredNewAccount=" + (( getRequiredNewAccount() ) ? "\"true\"" : "\"false\"" );
        return( ret );
    }

    public String toString() {
        String ret = "<CFSecJpaSecUserEMConfH" + getXmlAttrFragment() + "/>";
        return( ret );
    }
}

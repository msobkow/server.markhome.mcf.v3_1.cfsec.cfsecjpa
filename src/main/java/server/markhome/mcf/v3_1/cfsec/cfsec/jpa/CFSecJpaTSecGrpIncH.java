// Description: Java 25 JPA implementation of TSecGrpInc history objects

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
 *  CFSecJpaTSecGrpIncH provides history objects matching the CFSecTSecGrpInc change history.
 */
@Entity
@Table(
    name = "TSecInc_h", schema = "CFSec31",
    indexes = {
        @Index(name = "TSecGrpIncIdIdx_h", columnList = "auditClusterId, auditStamp, auditAction, requiredRevision, auditSessionId, TSecGrpIncId", unique = true),
        @Index(name = "TSecGrpIncTenantIdx_h", columnList = "TenantId", unique = false),
        @Index(name = "TSecGrpIncGroupIdx_h", columnList = "TSecGrpId", unique = false),
        @Index(name = "TSecGrpIncIncludeIdx_h", columnList = "IncGrpId", unique = false),
        @Index(name = "TSecGrpIncUIncludeIdx_h", columnList = "TenantId, TSecGrpId, IncGrpId", unique = true)
    }
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaTSecGrpIncH
    implements ICFSecTSecGrpIncH, Comparable<Object>, Serializable
{
	@AttributeOverrides({
		@AttributeOverride(name="auditClusterId", column = @Column( name="auditClusterId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) ),
		@AttributeOverride(name="auditStamp", column = @Column( name="auditStamp", nullable=false ) ),
		@AttributeOverride(name="auditAction", column = @Column( name="auditAction", nullable=false ) ),
		@AttributeOverride(name="requiredRevision", column = @Column( name="requiredRevision", nullable=false ) ),
		@AttributeOverride(name="auditSessionId", column = @Column( name="auditSessionId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) ),
		@AttributeOverride(name="TSecGrpIncId", column = @Column( name="TSecGrpIncId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
    @EmbeddedId
    protected CFSecJpaTSecGrpIncHPKey pkey;
	@AttributeOverrides({
		@AttributeOverride( name="bytes", column = @Column( name="CreatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 createdByUserId = CFLibDbKeyHash256.fromHex(ICFSecTSecGrpInc.S_INIT_CREATED_BY);

	@Column(name="CreatedAt", nullable=false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column= @Column( name="UpdatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 updatedByUserId = CFLibDbKeyHash256.fromHex(ICFSecTSecGrpInc.S_INIT_UPDATED_BY);

	@Column(name="UpdatedAt", nullable=false)
	protected LocalDateTime updatedAt = LocalDateTime.now();
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="TenantId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredTenantId;
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="TSecGrpId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredTSecGroupId;
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="IncGrpId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredIncludeGroupId;

    public CFSecJpaTSecGrpIncH() {
            // The primary key member attributes are initialized on construction
            pkey = new CFSecJpaTSecGrpIncHPKey();
		requiredTenantId = CFLibDbKeyHash256.fromHex( ICFSecTSecGrpInc.TENANTID_INIT_VALUE.toString() );
		requiredTSecGroupId = CFLibDbKeyHash256.fromHex( ICFSecTSecGrpInc.TSECGROUPID_INIT_VALUE.toString() );
		requiredIncludeGroupId = CFLibDbKeyHash256.fromHex( ICFSecTSecGrpInc.INCLUDEGROUPID_INIT_VALUE.toString() );
    }

    @Override
    public int getClassCode() {
            return( ICFSecTSecGrpInc.CLASS_CODE );
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
    public ICFSecTSecGrpIncHPKey getPKey() {
        return( pkey );
    }

    @Override
    public void setPKey( ICFSecTSecGrpIncHPKey pkey ) {
        if (pkey != null) {
            if (pkey instanceof CFSecJpaTSecGrpIncHPKey) {
                this.pkey = (CFSecJpaTSecGrpIncHPKey)pkey;
            }
            else {
                throw new CFLibUnsupportedClassException(getClass(), "setPKey", "pkey", pkey, "CFSecJpaTSecGrpIncHPKey");
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
    public CFLibDbKeyHash256 getRequiredTSecGrpIncId() {
        return( pkey.getRequiredTSecGrpIncId() );
    }

    @Override
    public void setRequiredTSecGrpIncId( CFLibDbKeyHash256 requiredTSecGrpIncId ) {
        pkey.setRequiredTSecGrpIncId( requiredTSecGrpIncId );
    }

	@Override
	public CFLibDbKeyHash256 getRequiredTenantId() {
		return( requiredTenantId );
	}

	@Override
	public void setRequiredTenantId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredTenantId",
				1,
				"value" );
		}
		requiredTenantId = value;
	}

	@Override
	public CFLibDbKeyHash256 getRequiredTSecGroupId() {
		return( requiredTSecGroupId );
	}

	@Override
	public void setRequiredTSecGroupId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredTSecGroupId",
				1,
				"value" );
		}
		requiredTSecGroupId = value;
	}

	@Override
	public CFLibDbKeyHash256 getRequiredIncludeGroupId() {
		return( requiredIncludeGroupId );
	}

	@Override
	public void setRequiredIncludeGroupId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredIncludeGroupId",
				1,
				"value" );
		}
		requiredIncludeGroupId = value;
	}

    @Override
    public boolean equals( Object obj ) {
        if (obj == null) {
            return( false );
        }
        else if (obj instanceof ICFSecTSecGrpInc) {
            ICFSecTSecGrpInc rhs = (ICFSecTSecGrpInc)obj;
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

			if( getRequiredTenantId() != null ) {
				if( rhs.getRequiredTenantId() != null ) {
					if( ! getRequiredTenantId().equals( rhs.getRequiredTenantId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTenantId() != null ) {
					return( false );
				}
			}
			if( getRequiredTSecGroupId() != null ) {
				if( rhs.getRequiredTSecGroupId() != null ) {
					if( ! getRequiredTSecGroupId().equals( rhs.getRequiredTSecGroupId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTSecGroupId() != null ) {
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
        else if (obj instanceof ICFSecTSecGrpIncH) {
            ICFSecTSecGrpIncH rhs = (ICFSecTSecGrpIncH)obj;
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

			if( getRequiredTenantId() != null ) {
				if( rhs.getRequiredTenantId() != null ) {
					if( ! getRequiredTenantId().equals( rhs.getRequiredTenantId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTenantId() != null ) {
					return( false );
				}
			}
			if( getRequiredTSecGroupId() != null ) {
				if( rhs.getRequiredTSecGroupId() != null ) {
					if( ! getRequiredTSecGroupId().equals( rhs.getRequiredTSecGroupId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTSecGroupId() != null ) {
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
        else if (obj instanceof ICFSecTSecGrpIncHPKey) {
		ICFSecTSecGrpIncHPKey rhs = (ICFSecTSecGrpIncHPKey)obj;
			if( getRequiredTSecGrpIncId() != null ) {
				if( rhs.getRequiredTSecGrpIncId() != null ) {
					if( ! getRequiredTSecGrpIncId().equals( rhs.getRequiredTSecGrpIncId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTSecGrpIncId() != null ) {
					return( false );
				}
			}
		return( true );
        }
        else if (obj instanceof ICFSecTSecGrpIncByTenantIdxKey) {
            ICFSecTSecGrpIncByTenantIdxKey rhs = (ICFSecTSecGrpIncByTenantIdxKey)obj;
			if( getRequiredTenantId() != null ) {
				if( rhs.getRequiredTenantId() != null ) {
					if( ! getRequiredTenantId().equals( rhs.getRequiredTenantId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTenantId() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFSecTSecGrpIncByGroupIdxKey) {
            ICFSecTSecGrpIncByGroupIdxKey rhs = (ICFSecTSecGrpIncByGroupIdxKey)obj;
			if( getRequiredTSecGroupId() != null ) {
				if( rhs.getRequiredTSecGroupId() != null ) {
					if( ! getRequiredTSecGroupId().equals( rhs.getRequiredTSecGroupId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTSecGroupId() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFSecTSecGrpIncByIncludeIdxKey) {
            ICFSecTSecGrpIncByIncludeIdxKey rhs = (ICFSecTSecGrpIncByIncludeIdxKey)obj;
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
        else if (obj instanceof ICFSecTSecGrpIncByUIncludeIdxKey) {
            ICFSecTSecGrpIncByUIncludeIdxKey rhs = (ICFSecTSecGrpIncByUIncludeIdxKey)obj;
			if( getRequiredTenantId() != null ) {
				if( rhs.getRequiredTenantId() != null ) {
					if( ! getRequiredTenantId().equals( rhs.getRequiredTenantId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTenantId() != null ) {
					return( false );
				}
			}
			if( getRequiredTSecGroupId() != null ) {
				if( rhs.getRequiredTSecGroupId() != null ) {
					if( ! getRequiredTSecGroupId().equals( rhs.getRequiredTSecGroupId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredTSecGroupId() != null ) {
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
        int hashCode = pkey.hashCode();
		hashCode = hashCode + getRequiredTenantId().hashCode();
		hashCode = hashCode + getRequiredTSecGroupId().hashCode();
		hashCode = hashCode + getRequiredIncludeGroupId().hashCode();
        return( hashCode & 0x7fffffff );
    }

    @Override
    public int compareTo( Object obj ) {
        int cmp;
        if (obj == null) {
            return( 1 );
        }
        else if (obj instanceof ICFSecTSecGrpInc) {
		ICFSecTSecGrpInc rhs = (ICFSecTSecGrpInc)obj;
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
			if (getRequiredTenantId() != null) {
				if (rhs.getRequiredTenantId() != null) {
					cmp = getRequiredTenantId().compareTo( rhs.getRequiredTenantId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTenantId() != null) {
				return( -1 );
			}
			if (getRequiredTSecGroupId() != null) {
				if (rhs.getRequiredTSecGroupId() != null) {
					cmp = getRequiredTSecGroupId().compareTo( rhs.getRequiredTSecGroupId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTSecGroupId() != null) {
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
        else if (obj instanceof ICFSecTSecGrpIncHPKey) {
        if (getPKey() != null) {
            return( getPKey().compareTo( obj ));
        }
        else {
            return( -1 );
        }
        }
        else if (obj instanceof ICFSecTSecGrpIncH) {
		ICFSecTSecGrpIncH rhs = (ICFSecTSecGrpIncH)obj;
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
			if (getRequiredTenantId() != null) {
				if (rhs.getRequiredTenantId() != null) {
					cmp = getRequiredTenantId().compareTo( rhs.getRequiredTenantId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTenantId() != null) {
				return( -1 );
			}
			if (getRequiredTSecGroupId() != null) {
				if (rhs.getRequiredTSecGroupId() != null) {
					cmp = getRequiredTSecGroupId().compareTo( rhs.getRequiredTSecGroupId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTSecGroupId() != null) {
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
        else if (obj instanceof ICFSecTSecGrpIncByTenantIdxKey ) {
            ICFSecTSecGrpIncByTenantIdxKey rhs = (ICFSecTSecGrpIncByTenantIdxKey)obj;
			if (getRequiredTenantId() != null) {
				if (rhs.getRequiredTenantId() != null) {
					cmp = getRequiredTenantId().compareTo( rhs.getRequiredTenantId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTenantId() != null) {
				return( -1 );
			}
            return( 0 );
        }
        else if (obj instanceof ICFSecTSecGrpIncByGroupIdxKey ) {
            ICFSecTSecGrpIncByGroupIdxKey rhs = (ICFSecTSecGrpIncByGroupIdxKey)obj;
			if (getRequiredTSecGroupId() != null) {
				if (rhs.getRequiredTSecGroupId() != null) {
					cmp = getRequiredTSecGroupId().compareTo( rhs.getRequiredTSecGroupId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTSecGroupId() != null) {
				return( -1 );
			}
            return( 0 );
        }
        else if (obj instanceof ICFSecTSecGrpIncByIncludeIdxKey ) {
            ICFSecTSecGrpIncByIncludeIdxKey rhs = (ICFSecTSecGrpIncByIncludeIdxKey)obj;
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
        else if (obj instanceof ICFSecTSecGrpIncByUIncludeIdxKey ) {
            ICFSecTSecGrpIncByUIncludeIdxKey rhs = (ICFSecTSecGrpIncByUIncludeIdxKey)obj;
			if (getRequiredTenantId() != null) {
				if (rhs.getRequiredTenantId() != null) {
					cmp = getRequiredTenantId().compareTo( rhs.getRequiredTenantId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTenantId() != null) {
				return( -1 );
			}
			if (getRequiredTSecGroupId() != null) {
				if (rhs.getRequiredTSecGroupId() != null) {
					cmp = getRequiredTSecGroupId().compareTo( rhs.getRequiredTSecGroupId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredTSecGroupId() != null) {
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
    public void set( ICFSecTSecGrpInc src ) {
		setTSecGrpInc( src );
    }

	@Override
    public void setTSecGrpInc( ICFSecTSecGrpInc src ) {
		setRequiredTSecGrpIncId( src.getRequiredTSecGrpIncId() );
		setRequiredTenantId( src.getRequiredTenantId() );
		setRequiredTSecGroupId( src.getRequiredTSecGroupId() );
		setRequiredIncludeGroupId( src.getRequiredIncludeGroupId() );
		setRequiredRevision( src.getRequiredRevision() );
    }

	@Override
    public void set( ICFSecTSecGrpIncH src ) {
		setTSecGrpInc( src );
    }

	@Override
    public void setTSecGrpInc( ICFSecTSecGrpIncH src ) {
		setRequiredTSecGrpIncId( src.getRequiredTSecGrpIncId() );
		setRequiredTenantId( src.getRequiredTenantId() );
		setRequiredTSecGroupId( src.getRequiredTSecGroupId() );
		setRequiredIncludeGroupId( src.getRequiredIncludeGroupId() );
		setRequiredRevision( src.getRequiredRevision() );
    }

    public String getXmlAttrFragment() {
        String ret = pkey.getXmlAttrFragment() 
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredTenantId=" + "\"" + getRequiredTenantId().toString() + "\""
			+ " RequiredTSecGroupId=" + "\"" + getRequiredTSecGroupId().toString() + "\""
			+ " RequiredIncludeGroupId=" + "\"" + getRequiredIncludeGroupId().toString() + "\"";
        return( ret );
    }

    public String toString() {
        String ret = "<CFSecJpaTSecGrpIncH" + getXmlAttrFragment() + "/>";
        return( ret );
    }
}

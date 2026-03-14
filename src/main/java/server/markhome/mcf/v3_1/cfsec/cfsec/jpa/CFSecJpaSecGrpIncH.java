// Description: Java 25 JPA implementation of SecGrpInc history objects

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
 *  CFSecJpaSecGrpIncH provides history objects matching the CFSecSecGrpInc change history.
 */
@Entity
@Table(
    name = "SecInc_h", schema = "CFSec31",
    indexes = {
        @Index(name = "SecGrpIncIdIdx_h", columnList = "auditClusterId, auditStamp, auditAction, requiredRevision, auditSessionId, SecGrpIncId", unique = true),
        @Index(name = "SecGrpIncClusterIdx_h", columnList = "ClusId", unique = false),
        @Index(name = "SecGrpIncGroupIdx_h", columnList = "SecGrpId", unique = false),
        @Index(name = "SecGrpIncIncludeIdx_h", columnList = "IncGrpId", unique = false),
        @Index(name = "SecGrpIncUIncludeIdx_h", columnList = "ClusId, SecGrpId, IncGrpId", unique = true)
    }
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaSecGrpIncH
    implements ICFSecSecGrpIncH, Comparable<Object>, Serializable
{
	@AttributeOverrides({
		@AttributeOverride(name="auditClusterId", column = @Column( name="auditClusterId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) ),
		@AttributeOverride(name="auditStamp", column = @Column( name="auditStamp", nullable=false ) ),
		@AttributeOverride(name="auditAction", column = @Column( name="auditAction", nullable=false ) ),
		@AttributeOverride(name="requiredRevision", column = @Column( name="requiredRevision", nullable=false ) ),
		@AttributeOverride(name="auditSessionId", column = @Column( name="auditSessionId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) ),
		@AttributeOverride(name="SecGrpIncId", column = @Column( name="SecGrpIncId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
    @EmbeddedId
    protected CFSecJpaSecGrpIncHPKey pkey;
	@AttributeOverrides({
		@AttributeOverride( name="bytes", column = @Column( name="CreatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 createdByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecGrpInc.S_INIT_CREATED_BY);

	@Column(name="CreatedAt", nullable=false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column= @Column( name="UpdatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 updatedByUserId = CFLibDbKeyHash256.fromHex(ICFSecSecGrpInc.S_INIT_UPDATED_BY);

	@Column(name="UpdatedAt", nullable=false)
	protected LocalDateTime updatedAt = LocalDateTime.now();
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="ClusId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredClusterId;
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="SecGrpId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredSecGroupId;
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="IncGrpId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredIncludeGroupId;

    public CFSecJpaSecGrpIncH() {
            // The primary key member attributes are initialized on construction
            pkey = new CFSecJpaSecGrpIncHPKey();
		requiredClusterId = CFLibDbKeyHash256.fromHex( ICFSecSecGrpInc.CLUSTERID_INIT_VALUE.toString() );
		requiredSecGroupId = CFLibDbKeyHash256.fromHex( ICFSecSecGrpInc.SECGROUPID_INIT_VALUE.toString() );
		requiredIncludeGroupId = CFLibDbKeyHash256.fromHex( ICFSecSecGrpInc.INCLUDEGROUPID_INIT_VALUE.toString() );
    }

    @Override
    public int getClassCode() {
            return( ICFSecSecGrpInc.CLASS_CODE );
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
    public ICFSecSecGrpIncHPKey getPKey() {
        return( pkey );
    }

    @Override
    public void setPKey( ICFSecSecGrpIncHPKey pkey ) {
        if (pkey != null) {
            if (pkey instanceof CFSecJpaSecGrpIncHPKey) {
                this.pkey = (CFSecJpaSecGrpIncHPKey)pkey;
            }
            else {
                throw new CFLibUnsupportedClassException(getClass(), "setPKey", "pkey", pkey, "CFSecJpaSecGrpIncHPKey");
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
    public CFLibDbKeyHash256 getRequiredSecGrpIncId() {
        return( pkey.getRequiredSecGrpIncId() );
    }

    @Override
    public void setRequiredSecGrpIncId( CFLibDbKeyHash256 requiredSecGrpIncId ) {
        pkey.setRequiredSecGrpIncId( requiredSecGrpIncId );
    }

	@Override
	public CFLibDbKeyHash256 getRequiredClusterId() {
		return( requiredClusterId );
	}

	@Override
	public void setRequiredClusterId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredClusterId",
				1,
				"value" );
		}
		requiredClusterId = value;
	}

	@Override
	public CFLibDbKeyHash256 getRequiredSecGroupId() {
		return( requiredSecGroupId );
	}

	@Override
	public void setRequiredSecGroupId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredSecGroupId",
				1,
				"value" );
		}
		requiredSecGroupId = value;
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
        else if (obj instanceof ICFSecSecGrpInc) {
            ICFSecSecGrpInc rhs = (ICFSecSecGrpInc)obj;
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
			if( getRequiredSecGroupId() != null ) {
				if( rhs.getRequiredSecGroupId() != null ) {
					if( ! getRequiredSecGroupId().equals( rhs.getRequiredSecGroupId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecGroupId() != null ) {
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
        else if (obj instanceof ICFSecSecGrpIncH) {
            ICFSecSecGrpIncH rhs = (ICFSecSecGrpIncH)obj;
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
			if( getRequiredSecGroupId() != null ) {
				if( rhs.getRequiredSecGroupId() != null ) {
					if( ! getRequiredSecGroupId().equals( rhs.getRequiredSecGroupId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecGroupId() != null ) {
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
        else if (obj instanceof ICFSecSecGrpIncHPKey) {
		ICFSecSecGrpIncHPKey rhs = (ICFSecSecGrpIncHPKey)obj;
			if( getRequiredSecGrpIncId() != null ) {
				if( rhs.getRequiredSecGrpIncId() != null ) {
					if( ! getRequiredSecGrpIncId().equals( rhs.getRequiredSecGrpIncId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecGrpIncId() != null ) {
					return( false );
				}
			}
		return( true );
        }
        else if (obj instanceof ICFSecSecGrpIncByClusterIdxKey) {
            ICFSecSecGrpIncByClusterIdxKey rhs = (ICFSecSecGrpIncByClusterIdxKey)obj;
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
        else if (obj instanceof ICFSecSecGrpIncByGroupIdxKey) {
            ICFSecSecGrpIncByGroupIdxKey rhs = (ICFSecSecGrpIncByGroupIdxKey)obj;
			if( getRequiredSecGroupId() != null ) {
				if( rhs.getRequiredSecGroupId() != null ) {
					if( ! getRequiredSecGroupId().equals( rhs.getRequiredSecGroupId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecGroupId() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFSecSecGrpIncByIncludeIdxKey) {
            ICFSecSecGrpIncByIncludeIdxKey rhs = (ICFSecSecGrpIncByIncludeIdxKey)obj;
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
        else if (obj instanceof ICFSecSecGrpIncByUIncludeIdxKey) {
            ICFSecSecGrpIncByUIncludeIdxKey rhs = (ICFSecSecGrpIncByUIncludeIdxKey)obj;
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
			if( getRequiredSecGroupId() != null ) {
				if( rhs.getRequiredSecGroupId() != null ) {
					if( ! getRequiredSecGroupId().equals( rhs.getRequiredSecGroupId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecGroupId() != null ) {
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
		hashCode = hashCode + getRequiredClusterId().hashCode();
		hashCode = hashCode + getRequiredSecGroupId().hashCode();
		hashCode = hashCode + getRequiredIncludeGroupId().hashCode();
        return( hashCode & 0x7fffffff );
    }

    @Override
    public int compareTo( Object obj ) {
        int cmp;
        if (obj == null) {
            return( 1 );
        }
        else if (obj instanceof ICFSecSecGrpInc) {
		ICFSecSecGrpInc rhs = (ICFSecSecGrpInc)obj;
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
			if (getRequiredSecGroupId() != null) {
				if (rhs.getRequiredSecGroupId() != null) {
					cmp = getRequiredSecGroupId().compareTo( rhs.getRequiredSecGroupId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecGroupId() != null) {
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
        else if (obj instanceof ICFSecSecGrpIncHPKey) {
        if (getPKey() != null) {
            return( getPKey().compareTo( obj ));
        }
        else {
            return( -1 );
        }
        }
        else if (obj instanceof ICFSecSecGrpIncH) {
		ICFSecSecGrpIncH rhs = (ICFSecSecGrpIncH)obj;
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
			if (getRequiredSecGroupId() != null) {
				if (rhs.getRequiredSecGroupId() != null) {
					cmp = getRequiredSecGroupId().compareTo( rhs.getRequiredSecGroupId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecGroupId() != null) {
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
        else if (obj instanceof ICFSecSecGrpIncByClusterIdxKey ) {
            ICFSecSecGrpIncByClusterIdxKey rhs = (ICFSecSecGrpIncByClusterIdxKey)obj;
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
        else if (obj instanceof ICFSecSecGrpIncByGroupIdxKey ) {
            ICFSecSecGrpIncByGroupIdxKey rhs = (ICFSecSecGrpIncByGroupIdxKey)obj;
			if (getRequiredSecGroupId() != null) {
				if (rhs.getRequiredSecGroupId() != null) {
					cmp = getRequiredSecGroupId().compareTo( rhs.getRequiredSecGroupId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecGroupId() != null) {
				return( -1 );
			}
            return( 0 );
        }
        else if (obj instanceof ICFSecSecGrpIncByIncludeIdxKey ) {
            ICFSecSecGrpIncByIncludeIdxKey rhs = (ICFSecSecGrpIncByIncludeIdxKey)obj;
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
        else if (obj instanceof ICFSecSecGrpIncByUIncludeIdxKey ) {
            ICFSecSecGrpIncByUIncludeIdxKey rhs = (ICFSecSecGrpIncByUIncludeIdxKey)obj;
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
			if (getRequiredSecGroupId() != null) {
				if (rhs.getRequiredSecGroupId() != null) {
					cmp = getRequiredSecGroupId().compareTo( rhs.getRequiredSecGroupId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecGroupId() != null) {
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
    public void set( ICFSecSecGrpInc src ) {
		setSecGrpInc( src );
    }

	@Override
    public void setSecGrpInc( ICFSecSecGrpInc src ) {
		setRequiredSecGrpIncId( src.getRequiredSecGrpIncId() );
		setRequiredClusterId( src.getRequiredClusterId() );
		setRequiredSecGroupId( src.getRequiredSecGroupId() );
		setRequiredIncludeGroupId( src.getRequiredIncludeGroupId() );
		setRequiredRevision( src.getRequiredRevision() );
    }

	@Override
    public void set( ICFSecSecGrpIncH src ) {
		setSecGrpInc( src );
    }

	@Override
    public void setSecGrpInc( ICFSecSecGrpIncH src ) {
		setRequiredSecGrpIncId( src.getRequiredSecGrpIncId() );
		setRequiredClusterId( src.getRequiredClusterId() );
		setRequiredSecGroupId( src.getRequiredSecGroupId() );
		setRequiredIncludeGroupId( src.getRequiredIncludeGroupId() );
		setRequiredRevision( src.getRequiredRevision() );
    }

    public String getXmlAttrFragment() {
        String ret = pkey.getXmlAttrFragment() 
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredClusterId=" + "\"" + getRequiredClusterId().toString() + "\""
			+ " RequiredSecGroupId=" + "\"" + getRequiredSecGroupId().toString() + "\""
			+ " RequiredIncludeGroupId=" + "\"" + getRequiredIncludeGroupId().toString() + "\"";
        return( ret );
    }

    public String toString() {
        String ret = "<CFSecJpaSecGrpIncH" + getXmlAttrFragment() + "/>";
        return( ret );
    }
}

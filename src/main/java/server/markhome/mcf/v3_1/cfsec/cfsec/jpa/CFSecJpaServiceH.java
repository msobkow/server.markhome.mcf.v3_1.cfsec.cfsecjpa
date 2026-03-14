// Description: Java 25 JPA implementation of Service history objects

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
 *  CFSecJpaServiceH provides history objects matching the CFSecService change history.
 */
@Entity
@Table(
    name = "HostSvc_h", schema = "CFSec31",
    indexes = {
        @Index(name = "ServiceIdIdx_h", columnList = "auditClusterId, auditStamp, auditAction, requiredRevision, auditSessionId, ServiceId", unique = true),
        @Index(name = "ServiceClusterIdx_h", columnList = "ClusterId", unique = false),
        @Index(name = "ServiceHostNodeIdx_h", columnList = "HostNodeId", unique = false),
        @Index(name = "ServiceTypeIdx_h", columnList = "ServiceTypeId", unique = false),
        @Index(name = "ServiceUTypeIdx_h", columnList = "ClusterId, HostNodeId, ServiceTypeId", unique = true),
        @Index(name = "ServiceUHostPort_h", columnList = "ClusterId, HostNodeId, HostPort", unique = true)
    }
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaServiceH
    implements ICFSecServiceH, Comparable<Object>, Serializable
{
	@AttributeOverrides({
		@AttributeOverride(name="auditClusterId", column = @Column( name="auditClusterId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) ),
		@AttributeOverride(name="auditStamp", column = @Column( name="auditStamp", nullable=false ) ),
		@AttributeOverride(name="auditAction", column = @Column( name="auditAction", nullable=false ) ),
		@AttributeOverride(name="requiredRevision", column = @Column( name="requiredRevision", nullable=false ) ),
		@AttributeOverride(name="auditSessionId", column = @Column( name="auditSessionId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) ),
		@AttributeOverride(name="ServiceId", column = @Column( name="ServiceId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
    @EmbeddedId
    protected CFSecJpaServiceHPKey pkey;
	@AttributeOverrides({
		@AttributeOverride( name="bytes", column = @Column( name="CreatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 createdByUserId = CFLibDbKeyHash256.fromHex(ICFSecService.S_INIT_CREATED_BY);

	@Column(name="CreatedAt", nullable=false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column= @Column( name="UpdatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 updatedByUserId = CFLibDbKeyHash256.fromHex(ICFSecService.S_INIT_UPDATED_BY);

	@Column(name="UpdatedAt", nullable=false)
	protected LocalDateTime updatedAt = LocalDateTime.now();
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="ClusterId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredClusterId;
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="HostNodeId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredHostNodeId;
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="ServiceTypeId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredServiceTypeId;
	@Column( name="HostPort", nullable=false )
	protected short requiredHostPort;

    public CFSecJpaServiceH() {
            // The primary key member attributes are initialized on construction
            pkey = new CFSecJpaServiceHPKey();
		requiredClusterId = CFLibDbKeyHash256.fromHex( ICFSecService.CLUSTERID_INIT_VALUE.toString() );
		requiredHostNodeId = CFLibDbKeyHash256.fromHex( ICFSecService.HOSTNODEID_INIT_VALUE.toString() );
		requiredServiceTypeId = CFLibDbKeyHash256.fromHex( ICFSecService.SERVICETYPEID_INIT_VALUE.toString() );
		requiredHostPort = ICFSecService.HOSTPORT_INIT_VALUE;
    }

    @Override
    public int getClassCode() {
            return( ICFSecService.CLASS_CODE );
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
    public ICFSecServiceHPKey getPKey() {
        return( pkey );
    }

    @Override
    public void setPKey( ICFSecServiceHPKey pkey ) {
        if (pkey != null) {
            if (pkey instanceof CFSecJpaServiceHPKey) {
                this.pkey = (CFSecJpaServiceHPKey)pkey;
            }
            else {
                throw new CFLibUnsupportedClassException(getClass(), "setPKey", "pkey", pkey, "CFSecJpaServiceHPKey");
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
    public CFLibDbKeyHash256 getRequiredServiceId() {
        return( pkey.getRequiredServiceId() );
    }

    @Override
    public void setRequiredServiceId( CFLibDbKeyHash256 requiredServiceId ) {
        pkey.setRequiredServiceId( requiredServiceId );
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
	public CFLibDbKeyHash256 getRequiredHostNodeId() {
		return( requiredHostNodeId );
	}

	@Override
	public void setRequiredHostNodeId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredHostNodeId",
				1,
				"value" );
		}
		requiredHostNodeId = value;
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
	public short getRequiredHostPort() {
		return( requiredHostPort );
	}

	@Override
	public void setRequiredHostPort( short value ) {
		if( value < ICFSecService.HOSTPORT_MIN_VALUE ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				"setRequiredHostPort",
				1,
				"value",
				value,
				ICFSecService.HOSTPORT_MIN_VALUE );
		}
		requiredHostPort = value;
	}

    @Override
    public boolean equals( Object obj ) {
        if (obj == null) {
            return( false );
        }
        else if (obj instanceof ICFSecService) {
            ICFSecService rhs = (ICFSecService)obj;
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
			if( getRequiredHostNodeId() != null ) {
				if( rhs.getRequiredHostNodeId() != null ) {
					if( ! getRequiredHostNodeId().equals( rhs.getRequiredHostNodeId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredHostNodeId() != null ) {
					return( false );
				}
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
			if( getRequiredHostPort() != rhs.getRequiredHostPort() ) {
				return( false );
			}
            return( true );
        }
        else if (obj instanceof ICFSecServiceH) {
            ICFSecServiceH rhs = (ICFSecServiceH)obj;
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
			if( getRequiredHostNodeId() != null ) {
				if( rhs.getRequiredHostNodeId() != null ) {
					if( ! getRequiredHostNodeId().equals( rhs.getRequiredHostNodeId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredHostNodeId() != null ) {
					return( false );
				}
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
			if( getRequiredHostPort() != rhs.getRequiredHostPort() ) {
				return( false );
			}
            return( true );
        }
        else if (obj instanceof ICFSecServiceHPKey) {
		ICFSecServiceHPKey rhs = (ICFSecServiceHPKey)obj;
			if( getRequiredServiceId() != null ) {
				if( rhs.getRequiredServiceId() != null ) {
					if( ! getRequiredServiceId().equals( rhs.getRequiredServiceId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredServiceId() != null ) {
					return( false );
				}
			}
		return( true );
        }
        else if (obj instanceof ICFSecServiceByClusterIdxKey) {
            ICFSecServiceByClusterIdxKey rhs = (ICFSecServiceByClusterIdxKey)obj;
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
        else if (obj instanceof ICFSecServiceByHostIdxKey) {
            ICFSecServiceByHostIdxKey rhs = (ICFSecServiceByHostIdxKey)obj;
			if( getRequiredHostNodeId() != null ) {
				if( rhs.getRequiredHostNodeId() != null ) {
					if( ! getRequiredHostNodeId().equals( rhs.getRequiredHostNodeId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredHostNodeId() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFSecServiceByTypeIdxKey) {
            ICFSecServiceByTypeIdxKey rhs = (ICFSecServiceByTypeIdxKey)obj;
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
        else if (obj instanceof ICFSecServiceByUTypeIdxKey) {
            ICFSecServiceByUTypeIdxKey rhs = (ICFSecServiceByUTypeIdxKey)obj;
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
			if( getRequiredHostNodeId() != null ) {
				if( rhs.getRequiredHostNodeId() != null ) {
					if( ! getRequiredHostNodeId().equals( rhs.getRequiredHostNodeId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredHostNodeId() != null ) {
					return( false );
				}
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
            return( true );
        }
        else if (obj instanceof ICFSecServiceByUHostPortIdxKey) {
            ICFSecServiceByUHostPortIdxKey rhs = (ICFSecServiceByUHostPortIdxKey)obj;
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
			if( getRequiredHostNodeId() != null ) {
				if( rhs.getRequiredHostNodeId() != null ) {
					if( ! getRequiredHostNodeId().equals( rhs.getRequiredHostNodeId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredHostNodeId() != null ) {
					return( false );
				}
			}
			if( getRequiredHostPort() != rhs.getRequiredHostPort() ) {
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
		hashCode = hashCode + getRequiredClusterId().hashCode();
		hashCode = hashCode + getRequiredHostNodeId().hashCode();
		hashCode = hashCode + getRequiredServiceTypeId().hashCode();
		hashCode = ( hashCode * 0x10000 ) + getRequiredHostPort();
        return( hashCode & 0x7fffffff );
    }

    @Override
    public int compareTo( Object obj ) {
        int cmp;
        if (obj == null) {
            return( 1 );
        }
        else if (obj instanceof ICFSecService) {
		ICFSecService rhs = (ICFSecService)obj;
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
			if (getRequiredHostNodeId() != null) {
				if (rhs.getRequiredHostNodeId() != null) {
					cmp = getRequiredHostNodeId().compareTo( rhs.getRequiredHostNodeId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredHostNodeId() != null) {
				return( -1 );
			}
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
			if( getRequiredHostPort() < rhs.getRequiredHostPort() ) {
				return( -1 );
			}
			else if( getRequiredHostPort() > rhs.getRequiredHostPort() ) {
				return( 1 );
			}
            return( 0 );
        }
        else if (obj instanceof ICFSecServiceHPKey) {
        if (getPKey() != null) {
            return( getPKey().compareTo( obj ));
        }
        else {
            return( -1 );
        }
        }
        else if (obj instanceof ICFSecServiceH) {
		ICFSecServiceH rhs = (ICFSecServiceH)obj;
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
			if (getRequiredHostNodeId() != null) {
				if (rhs.getRequiredHostNodeId() != null) {
					cmp = getRequiredHostNodeId().compareTo( rhs.getRequiredHostNodeId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredHostNodeId() != null) {
				return( -1 );
			}
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
			if( getRequiredHostPort() < rhs.getRequiredHostPort() ) {
				return( -1 );
			}
			else if( getRequiredHostPort() > rhs.getRequiredHostPort() ) {
				return( 1 );
			}
            return( 0 );
        }
        else if (obj instanceof ICFSecServiceByClusterIdxKey ) {
            ICFSecServiceByClusterIdxKey rhs = (ICFSecServiceByClusterIdxKey)obj;
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
        else if (obj instanceof ICFSecServiceByHostIdxKey ) {
            ICFSecServiceByHostIdxKey rhs = (ICFSecServiceByHostIdxKey)obj;
			if (getRequiredHostNodeId() != null) {
				if (rhs.getRequiredHostNodeId() != null) {
					cmp = getRequiredHostNodeId().compareTo( rhs.getRequiredHostNodeId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredHostNodeId() != null) {
				return( -1 );
			}
            return( 0 );
        }
        else if (obj instanceof ICFSecServiceByTypeIdxKey ) {
            ICFSecServiceByTypeIdxKey rhs = (ICFSecServiceByTypeIdxKey)obj;
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
        else if (obj instanceof ICFSecServiceByUTypeIdxKey ) {
            ICFSecServiceByUTypeIdxKey rhs = (ICFSecServiceByUTypeIdxKey)obj;
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
			if (getRequiredHostNodeId() != null) {
				if (rhs.getRequiredHostNodeId() != null) {
					cmp = getRequiredHostNodeId().compareTo( rhs.getRequiredHostNodeId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredHostNodeId() != null) {
				return( -1 );
			}
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
        else if (obj instanceof ICFSecServiceByUHostPortIdxKey ) {
            ICFSecServiceByUHostPortIdxKey rhs = (ICFSecServiceByUHostPortIdxKey)obj;
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
			if (getRequiredHostNodeId() != null) {
				if (rhs.getRequiredHostNodeId() != null) {
					cmp = getRequiredHostNodeId().compareTo( rhs.getRequiredHostNodeId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredHostNodeId() != null) {
				return( -1 );
			}
			if( getRequiredHostPort() < rhs.getRequiredHostPort() ) {
				return( -1 );
			}
			else if( getRequiredHostPort() > rhs.getRequiredHostPort() ) {
				return( 1 );
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
    public void set( ICFSecService src ) {
		setService( src );
    }

	@Override
    public void setService( ICFSecService src ) {
		setRequiredServiceId( src.getRequiredServiceId() );
		setRequiredClusterId( src.getRequiredClusterId() );
		setRequiredHostNodeId( src.getRequiredHostNodeId() );
		setRequiredServiceTypeId( src.getRequiredServiceTypeId() );
		setRequiredHostPort( src.getRequiredHostPort() );
		setRequiredRevision( src.getRequiredRevision() );
    }

	@Override
    public void set( ICFSecServiceH src ) {
		setService( src );
    }

	@Override
    public void setService( ICFSecServiceH src ) {
		setRequiredServiceId( src.getRequiredServiceId() );
		setRequiredClusterId( src.getRequiredClusterId() );
		setRequiredHostNodeId( src.getRequiredHostNodeId() );
		setRequiredServiceTypeId( src.getRequiredServiceTypeId() );
		setRequiredHostPort( src.getRequiredHostPort() );
		setRequiredRevision( src.getRequiredRevision() );
    }

    public String getXmlAttrFragment() {
        String ret = pkey.getXmlAttrFragment() 
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredClusterId=" + "\"" + getRequiredClusterId().toString() + "\""
			+ " RequiredHostNodeId=" + "\"" + getRequiredHostNodeId().toString() + "\""
			+ " RequiredServiceTypeId=" + "\"" + getRequiredServiceTypeId().toString() + "\""
			+ " RequiredHostPort=" + "\"" + Short.toString( getRequiredHostPort() ) + "\"";
        return( ret );
    }

    public String toString() {
        String ret = "<CFSecJpaServiceH" + getXmlAttrFragment() + "/>";
        return( ret );
    }
}

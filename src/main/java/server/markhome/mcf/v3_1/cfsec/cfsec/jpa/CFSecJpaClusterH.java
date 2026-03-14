// Description: Java 25 JPA implementation of Cluster history objects

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
 *  CFSecJpaClusterH provides history objects matching the CFSecCluster change history.
 */
@Entity
@Table(
    name = "clus_h", schema = "CFSec31",
    indexes = {
        @Index(name = "ClusterIdIdx_h", columnList = "auditClusterId, auditStamp, auditAction, requiredRevision, auditSessionId, Id", unique = true),
        @Index(name = "ClusterUDomNameIdx_h", columnList = "FullDomName", unique = true),
        @Index(name = "ClusterUDescrNameIdx_h", columnList = "Description", unique = true)
    }
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaClusterH
    implements ICFSecClusterH, Comparable<Object>, Serializable
{
	@AttributeOverrides({
		@AttributeOverride(name="auditClusterId", column = @Column( name="auditClusterId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) ),
		@AttributeOverride(name="auditStamp", column = @Column( name="auditStamp", nullable=false ) ),
		@AttributeOverride(name="auditAction", column = @Column( name="auditAction", nullable=false ) ),
		@AttributeOverride(name="requiredRevision", column = @Column( name="requiredRevision", nullable=false ) ),
		@AttributeOverride(name="auditSessionId", column = @Column( name="auditSessionId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) ),
		@AttributeOverride(name="Id", column = @Column( name="Id", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
    @EmbeddedId
    protected CFSecJpaClusterHPKey pkey;
	@AttributeOverrides({
		@AttributeOverride( name="bytes", column = @Column( name="CreatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 createdByUserId = CFLibDbKeyHash256.fromHex(ICFSecCluster.S_INIT_CREATED_BY);

	@Column(name="CreatedAt", nullable=false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column= @Column( name="UpdatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 updatedByUserId = CFLibDbKeyHash256.fromHex(ICFSecCluster.S_INIT_UPDATED_BY);

	@Column(name="UpdatedAt", nullable=false)
	protected LocalDateTime updatedAt = LocalDateTime.now();
	@Column( name="FullDomName", nullable=false, length=192 )
	protected String requiredFullDomName;
	@Column( name="Description", nullable=false, length=128 )
	protected String requiredDescription;

    public CFSecJpaClusterH() {
            // The primary key member attributes are initialized on construction
            pkey = new CFSecJpaClusterHPKey();
		requiredFullDomName = ICFSecCluster.FULLDOMNAME_INIT_VALUE;
		requiredDescription = ICFSecCluster.DESCRIPTION_INIT_VALUE;
    }

    @Override
    public int getClassCode() {
            return( ICFSecCluster.CLASS_CODE );
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
    public ICFSecClusterHPKey getPKey() {
        return( pkey );
    }

    @Override
    public void setPKey( ICFSecClusterHPKey pkey ) {
        if (pkey != null) {
            if (pkey instanceof CFSecJpaClusterHPKey) {
                this.pkey = (CFSecJpaClusterHPKey)pkey;
            }
            else {
                throw new CFLibUnsupportedClassException(getClass(), "setPKey", "pkey", pkey, "CFSecJpaClusterHPKey");
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
    public CFLibDbKeyHash256 getRequiredId() {
        return( pkey.getRequiredId() );
    }

    @Override
    public void setRequiredId( CFLibDbKeyHash256 requiredId ) {
        pkey.setRequiredId( requiredId );
    }

	@Override
	public String getRequiredFullDomName() {
		return( requiredFullDomName );
	}

	@Override
	public void setRequiredFullDomName( String value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredFullDomName",
				1,
				"value" );
		}
		else if( value.length() > 192 ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredFullDomName",
				1,
				"value.length()",
				value.length(),
				192 );
		}
		requiredFullDomName = value;
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
    public boolean equals( Object obj ) {
        if (obj == null) {
            return( false );
        }
        else if (obj instanceof ICFSecCluster) {
            ICFSecCluster rhs = (ICFSecCluster)obj;
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

			if( getRequiredFullDomName() != null ) {
				if( rhs.getRequiredFullDomName() != null ) {
					if( ! getRequiredFullDomName().equals( rhs.getRequiredFullDomName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredFullDomName() != null ) {
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
        else if (obj instanceof ICFSecClusterH) {
            ICFSecClusterH rhs = (ICFSecClusterH)obj;
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

			if( getRequiredFullDomName() != null ) {
				if( rhs.getRequiredFullDomName() != null ) {
					if( ! getRequiredFullDomName().equals( rhs.getRequiredFullDomName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredFullDomName() != null ) {
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
        else if (obj instanceof ICFSecClusterHPKey) {
		ICFSecClusterHPKey rhs = (ICFSecClusterHPKey)obj;
			if( getRequiredId() != null ) {
				if( rhs.getRequiredId() != null ) {
					if( ! getRequiredId().equals( rhs.getRequiredId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredId() != null ) {
					return( false );
				}
			}
		return( true );
        }
        else if (obj instanceof ICFSecClusterByUDomNameIdxKey) {
            ICFSecClusterByUDomNameIdxKey rhs = (ICFSecClusterByUDomNameIdxKey)obj;
			if( getRequiredFullDomName() != null ) {
				if( rhs.getRequiredFullDomName() != null ) {
					if( ! getRequiredFullDomName().equals( rhs.getRequiredFullDomName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredFullDomName() != null ) {
					return( false );
				}
			}
            return( true );
        }
        else if (obj instanceof ICFSecClusterByUDescrIdxKey) {
            ICFSecClusterByUDescrIdxKey rhs = (ICFSecClusterByUDescrIdxKey)obj;
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
        int hashCode = pkey.hashCode();
		if( getRequiredFullDomName() != null ) {
			hashCode = hashCode + getRequiredFullDomName().hashCode();
		}
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
        else if (obj instanceof ICFSecCluster) {
		ICFSecCluster rhs = (ICFSecCluster)obj;
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
			if (getRequiredFullDomName() != null) {
				if (rhs.getRequiredFullDomName() != null) {
					cmp = getRequiredFullDomName().compareTo( rhs.getRequiredFullDomName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredFullDomName() != null) {
				return( -1 );
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
        else if (obj instanceof ICFSecClusterHPKey) {
        if (getPKey() != null) {
            return( getPKey().compareTo( obj ));
        }
        else {
            return( -1 );
        }
        }
        else if (obj instanceof ICFSecClusterH) {
		ICFSecClusterH rhs = (ICFSecClusterH)obj;
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
			if (getRequiredFullDomName() != null) {
				if (rhs.getRequiredFullDomName() != null) {
					cmp = getRequiredFullDomName().compareTo( rhs.getRequiredFullDomName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredFullDomName() != null) {
				return( -1 );
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
        else if (obj instanceof ICFSecClusterByUDomNameIdxKey ) {
            ICFSecClusterByUDomNameIdxKey rhs = (ICFSecClusterByUDomNameIdxKey)obj;
			if (getRequiredFullDomName() != null) {
				if (rhs.getRequiredFullDomName() != null) {
					cmp = getRequiredFullDomName().compareTo( rhs.getRequiredFullDomName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredFullDomName() != null) {
				return( -1 );
			}
            return( 0 );
        }
        else if (obj instanceof ICFSecClusterByUDescrIdxKey ) {
            ICFSecClusterByUDescrIdxKey rhs = (ICFSecClusterByUDescrIdxKey)obj;
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
    public void set( ICFSecCluster src ) {
		setCluster( src );
    }

	@Override
    public void setCluster( ICFSecCluster src ) {
		setRequiredId( src.getRequiredId() );
		setRequiredFullDomName( src.getRequiredFullDomName() );
		setRequiredDescription( src.getRequiredDescription() );
		setRequiredRevision( src.getRequiredRevision() );
    }

	@Override
    public void set( ICFSecClusterH src ) {
		setCluster( src );
    }

	@Override
    public void setCluster( ICFSecClusterH src ) {
		setRequiredId( src.getRequiredId() );
		setRequiredFullDomName( src.getRequiredFullDomName() );
		setRequiredDescription( src.getRequiredDescription() );
		setRequiredRevision( src.getRequiredRevision() );
    }

    public String getXmlAttrFragment() {
        String ret = pkey.getXmlAttrFragment() 
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredFullDomName=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredFullDomName() ) + "\""
			+ " RequiredDescription=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredDescription() ) + "\"";
        return( ret );
    }

    public String toString() {
        String ret = "<CFSecJpaClusterH" + getXmlAttrFragment() + "/>";
        return( ret );
    }
}

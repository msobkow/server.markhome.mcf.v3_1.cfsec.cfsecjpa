// Description: Java 25 JPA implementation of ISOCtryCcy history objects

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
 *  CFSecJpaISOCtryCcyH provides history objects matching the CFSecISOCtryCcy change history.
 */
@Entity
@Table(
    name = "iso_cntryccy_h", schema = "CFSec31",
    indexes = {
        @Index(name = "ISOCtryCcyIdIdx_h", columnList = "auditClusterId, auditStamp, auditAction, requiredRevision, auditSessionId, ISOCtryId, ISOCcyId", unique = true),
        @Index(name = "ISOCtryCcyCtryIdx_h", columnList = "ISOCtryId", unique = false),
        @Index(name = "ISOCtryCcyCcyIdx_h", columnList = "ISOCcyId", unique = false)
    }
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaISOCtryCcyH
    implements ICFSecISOCtryCcyH, Comparable<Object>, Serializable
{
	@AttributeOverrides({
		@AttributeOverride(name="auditClusterId", column = @Column( name="auditClusterId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) ),
		@AttributeOverride(name="auditStamp", column = @Column( name="auditStamp", nullable=false ) ),
		@AttributeOverride(name="auditAction", column = @Column( name="auditAction", nullable=false ) ),
		@AttributeOverride(name="requiredRevision", column = @Column( name="requiredRevision", nullable=false ) ),
		@AttributeOverride(name="auditSessionId", column = @Column( name="auditSessionId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) ),
		@AttributeOverride(name="ISOCtryId", column = @Column( name="ISOCtryId", nullable=false ) ),
		@AttributeOverride(name="ISOCcyId", column = @Column( name="ISOCcyId", nullable=false ) )
	})
    @EmbeddedId
    protected CFSecJpaISOCtryCcyHPKey pkey;
	@AttributeOverrides({
		@AttributeOverride( name="bytes", column = @Column( name="CreatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 createdByUserId = CFLibDbKeyHash256.fromHex(ICFSecISOCtryCcy.S_INIT_CREATED_BY);

	@Column(name="CreatedAt", nullable=false)
	protected LocalDateTime createdAt = LocalDateTime.now();

	@AttributeOverrides({
		@AttributeOverride( name="bytes", column= @Column( name="UpdatedByUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 updatedByUserId = CFLibDbKeyHash256.fromHex(ICFSecISOCtryCcy.S_INIT_UPDATED_BY);

	@Column(name="UpdatedAt", nullable=false)
	protected LocalDateTime updatedAt = LocalDateTime.now();

    public CFSecJpaISOCtryCcyH() {
            // The primary key member attributes are initialized on construction
            pkey = new CFSecJpaISOCtryCcyHPKey();
    }

    @Override
    public int getClassCode() {
            return( ICFSecISOCtryCcy.CLASS_CODE );
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
    public ICFSecISOCtryCcyHPKey getPKey() {
        return( pkey );
    }

    @Override
    public void setPKey( ICFSecISOCtryCcyHPKey pkey ) {
        if (pkey != null) {
            if (pkey instanceof CFSecJpaISOCtryCcyHPKey) {
                this.pkey = (CFSecJpaISOCtryCcyHPKey)pkey;
            }
            else {
                throw new CFLibUnsupportedClassException(getClass(), "setPKey", "pkey", pkey, "CFSecJpaISOCtryCcyHPKey");
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
    public short getRequiredISOCtryId() {
        return( pkey.getRequiredISOCtryId() );
    }

    @Override
    public void setRequiredISOCtryId( short requiredISOCtryId ) {
        pkey.setRequiredISOCtryId( requiredISOCtryId );
    }

    @Override
    public short getRequiredISOCcyId() {
        return( pkey.getRequiredISOCcyId() );
    }

    @Override
    public void setRequiredISOCcyId( short requiredISOCcyId ) {
        pkey.setRequiredISOCcyId( requiredISOCcyId );
    }

    @Override
    public boolean equals( Object obj ) {
        if (obj == null) {
            return( false );
        }
        else if (obj instanceof ICFSecISOCtryCcy) {
            ICFSecISOCtryCcy rhs = (ICFSecISOCtryCcy)obj;
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

            return( true );
        }
        else if (obj instanceof ICFSecISOCtryCcyH) {
            ICFSecISOCtryCcyH rhs = (ICFSecISOCtryCcyH)obj;
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

            return( true );
        }
        else if (obj instanceof ICFSecISOCtryCcyHPKey) {
		ICFSecISOCtryCcyHPKey rhs = (ICFSecISOCtryCcyHPKey)obj;
			if( getRequiredISOCtryId() != rhs.getRequiredISOCtryId() ) {
				return( false );
			}
			if( getRequiredISOCcyId() != rhs.getRequiredISOCcyId() ) {
				return( false );
			}
		return( true );
        }
        else if (obj instanceof ICFSecISOCtryCcyByCtryIdxKey) {
            ICFSecISOCtryCcyByCtryIdxKey rhs = (ICFSecISOCtryCcyByCtryIdxKey)obj;
			if( getRequiredISOCtryId() != rhs.getRequiredISOCtryId() ) {
				return( false );
			}
            return( true );
        }
        else if (obj instanceof ICFSecISOCtryCcyByCcyIdxKey) {
            ICFSecISOCtryCcyByCcyIdxKey rhs = (ICFSecISOCtryCcyByCcyIdxKey)obj;
			if( getRequiredISOCcyId() != rhs.getRequiredISOCcyId() ) {
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
        return( hashCode & 0x7fffffff );
    }

    @Override
    public int compareTo( Object obj ) {
        int cmp;
        if (obj == null) {
            return( 1 );
        }
        else if (obj instanceof ICFSecISOCtryCcy) {
		ICFSecISOCtryCcy rhs = (ICFSecISOCtryCcy)obj;
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
            return( 0 );
        }
        else if (obj instanceof ICFSecISOCtryCcyHPKey) {
        if (getPKey() != null) {
            return( getPKey().compareTo( obj ));
        }
        else {
            return( -1 );
        }
        }
        else if (obj instanceof ICFSecISOCtryCcyH) {
		ICFSecISOCtryCcyH rhs = (ICFSecISOCtryCcyH)obj;
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
            return( 0 );
        }
        else if (obj instanceof ICFSecISOCtryCcyByCtryIdxKey ) {
            ICFSecISOCtryCcyByCtryIdxKey rhs = (ICFSecISOCtryCcyByCtryIdxKey)obj;
			if( getRequiredISOCtryId() < rhs.getRequiredISOCtryId() ) {
				return( -1 );
			}
			else if( getRequiredISOCtryId() > rhs.getRequiredISOCtryId() ) {
				return( 1 );
			}
            return( 0 );
        }
        else if (obj instanceof ICFSecISOCtryCcyByCcyIdxKey ) {
            ICFSecISOCtryCcyByCcyIdxKey rhs = (ICFSecISOCtryCcyByCcyIdxKey)obj;
			if( getRequiredISOCcyId() < rhs.getRequiredISOCcyId() ) {
				return( -1 );
			}
			else if( getRequiredISOCcyId() > rhs.getRequiredISOCcyId() ) {
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
    public void set( ICFSecISOCtryCcy src ) {
		setISOCtryCcy( src );
    }

	@Override
    public void setISOCtryCcy( ICFSecISOCtryCcy src ) {
		setRequiredISOCtryId( src.getRequiredISOCtryId() );
		setRequiredISOCcyId( src.getRequiredISOCcyId() );
		setRequiredRevision( src.getRequiredRevision() );
    }

	@Override
    public void set( ICFSecISOCtryCcyH src ) {
		setISOCtryCcy( src );
    }

	@Override
    public void setISOCtryCcy( ICFSecISOCtryCcyH src ) {
		setRequiredISOCtryId( src.getRequiredISOCtryId() );
		setRequiredISOCcyId( src.getRequiredISOCcyId() );
		setRequiredRevision( src.getRequiredRevision() );
    }

    public String getXmlAttrFragment() {
        String ret = pkey.getXmlAttrFragment() 
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\"";
        return( ret );
    }

    public String toString() {
        String ret = "<CFSecJpaISOCtryCcyH" + getXmlAttrFragment() + "/>";
        return( ret );
    }
}

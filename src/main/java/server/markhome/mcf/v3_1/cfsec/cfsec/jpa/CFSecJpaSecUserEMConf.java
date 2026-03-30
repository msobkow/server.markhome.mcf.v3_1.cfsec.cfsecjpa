// Description: Java 25 JPA implementation of a SecUserEMConf entity definition object.

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
	name = "secusremcnf", schema = "CFSec31",
	indexes = {
		@Index(name = "SecUserEMConfIdIdx", columnList = "SecUserId", unique = true),
		@Index(name = "SecUserEMConfUuid6Idx", columnList = "conf_uuid6", unique = true),
		@Index(name = "SecUserEMConfConfirmingAddrIdx", columnList = "conf_emailaddr", unique = false),
		@Index(name = "SecUserEMConfSentStampIdx", columnList = "conf_sent", unique = false),
		@Index(name = "SecUserEMConfNewAcctIdx", columnList = "conf_newacct", unique = false)
	}
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaSecUserEMConf
	implements Comparable<Object>,
		ICFSecSecUserEMConf,
		Serializable
{
	@Id
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="SecUserId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredSecUserId;
		
	@OneToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn( name="SecUserId" )
	protected CFSecJpaSecUser requiredContainerUser;
	protected int requiredRevision;


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

	public CFSecJpaSecUserEMConf() {
		requiredSecUserId = CFLibDbKeyHash256.fromHex( ICFSecSecUserEMConf.SECUSERID_INIT_VALUE.toString() );
		requiredConfirmEMailAddr = ICFSecSecUserEMConf.CONFIRMEMAILADDR_INIT_VALUE;
		requiredEMailSentStamp = CFLibXmlUtil.parseTimestamp("2020-01-01T00:00:00");
		requiredNewAccount = ICFSecSecUserEMConf.NEWACCOUNT_INIT_VALUE;
	}

	@Override
	public int getClassCode() {
		return( ICFSecSecUserEMConf.CLASS_CODE );
	}

	@Override
	public ICFSecSecUser getRequiredContainerUser() {
		return( requiredContainerUser );
	}
	@Override
	public void setRequiredContainerUser(ICFSecSecUser argObj) {
		if(argObj == null) {
			throw new CFLibNullArgumentException(getClass(), "setContainerUser", 1, "argObj");
		}
		else if (argObj instanceof CFSecJpaSecUser) {
			requiredContainerUser = (CFSecJpaSecUser)argObj;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "setContainerUser", "argObj", argObj, "CFSecJpaSecUser");
		}
	
	}

	@Override
	public void setRequiredContainerUser(CFLibDbKeyHash256 argSecUserId) {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredContainerUser", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecSecUserTable targetTable = targetBackingSchema.getTableSecUser();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredContainerUser", 0, "ICFSecSchema.getBackingCFSec().getTableSecUser()");
		}
		ICFSecSecUser targetRec = targetTable.readDerivedByIdIdx(null, argSecUserId);
		setRequiredContainerUser(targetRec);
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
	public CFLibDbKeyHash256 getPKey() {
		return getRequiredSecUserId();
	}

	@Override
	public void setPKey(CFLibDbKeyHash256 requiredSecUserId) {
		this.requiredSecUserId = requiredSecUserId;
	}
	
	@Override
	public CFLibDbKeyHash256 getRequiredSecUserId() {
		return( requiredSecUserId );
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
		int hashCode = getPKey().hashCode();
		hashCode = hashCode + getCreatedByUserId().hashCode();
		hashCode = hashCode + getCreatedAt().hashCode();
		hashCode = hashCode + getUpdatedByUserId().hashCode();
		hashCode = hashCode + getUpdatedAt().hashCode();
		hashCode = hashCode + getRequiredSecUserId().hashCode();
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
			ICFSecSecUserEMConfHPKey rhs = (ICFSecSecUserEMConfHPKey)obj;
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
		else if( obj instanceof ICFSecSecUserEMConfH ) {
			ICFSecSecUserEMConfH rhs = (ICFSecSecUserEMConfH)obj;
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
		else if (obj instanceof ICFSecSecUserEMConfByUUuid6IdxKey) {
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
		else if (obj instanceof ICFSecSecUserEMConfByConfEMAddrIdxKey) {
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
		else if (obj instanceof ICFSecSecUserEMConfBySentStampIdxKey) {
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
		else if (obj instanceof ICFSecSecUserEMConfByNewAcctIdxKey) {
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
		setRequiredContainerUser(src.getRequiredContainerUser());
		setRequiredRevision( src.getRequiredRevision() );
		setCreatedByUserId( src.getCreatedByUserId() );
		setCreatedAt( src.getCreatedAt() );
		setUpdatedByUserId( src.getUpdatedByUserId() );
		setUpdatedAt( src.getUpdatedAt() );
		setRequiredConfirmEMailAddr(src.getRequiredConfirmEMailAddr());
		setRequiredEMailSentStamp(src.getRequiredEMailSentStamp());
		setRequiredEMConfirmationUuid6(src.getRequiredEMConfirmationUuid6());
		setRequiredNewAccount(src.getRequiredNewAccount());
	}

	@Override
	public void set( ICFSecSecUserEMConfH src ) {
		setSecUserEMConf( src );
	}

	@Override
	public void setSecUserEMConf( ICFSecSecUserEMConfH src ) {
		setRequiredContainerUser(src.getRequiredSecUserId());
		setRequiredConfirmEMailAddr(src.getRequiredConfirmEMailAddr());
		setRequiredEMailSentStamp(src.getRequiredEMailSentStamp());
		setRequiredEMConfirmationUuid6(src.getRequiredEMConfirmationUuid6());
		setRequiredNewAccount(src.getRequiredNewAccount());
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = ""
			+ " RequiredSecUserId=" + "\"" + getRequiredSecUserId().toString() + "\""
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredSecUserId=" + "\"" + getRequiredSecUserId().toString() + "\""
			+ " RequiredConfirmEMailAddr=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredConfirmEMailAddr() ) + "\""
			+ " RequiredEMailSentStamp=" + "\"" + getRequiredEMailSentStamp().toString() + "\""
			+ " RequiredEMConfirmationUuid6=" + "\"" + getRequiredEMConfirmationUuid6().toString() + "\""
			+ " RequiredNewAccount=" + (( getRequiredNewAccount() ) ? "\"true\"" : "\"false\"" );
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecJpaSecUserEMConf" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

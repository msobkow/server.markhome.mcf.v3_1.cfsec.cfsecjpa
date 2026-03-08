// Description: Java 25 JPA implementation of a Service entity definition object.

/*
 *	server.markhome.mcf.CFSec
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal 3.1 CFSec - Security Services
 *	
 *	This file is part of Mark's Code Fractal CFSec.
 *	
 *	Mark's Code Fractal CFSec is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU Library General Public License,
 *	Version 3 or later.
 *	
 *	Mark's Code Fractal CFSec is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU Library General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	Mark's Code Fractal CFSec is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU Library General Public License
 *	along with Mark's Code Fractal CFSec.  If not, see <https://www.gnu.org/licenses/>.
 *	
 *	If you wish to modify and use this code without publishing your changes in order to
 *	tie it to proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
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
	name = "HostSvc", schema = "CFSec31",
	indexes = {
		@Index(name = "ServiceIdIdx", columnList = "ServiceId", unique = true),
		@Index(name = "ServiceClusterIdx", columnList = "ClusterId", unique = false),
		@Index(name = "ServiceHostNodeIdx", columnList = "HostNodeId", unique = false),
		@Index(name = "ServiceTypeIdx", columnList = "ServiceTypeId", unique = false),
		@Index(name = "ServiceUTypeIdx", columnList = "ClusterId, HostNodeId, ServiceTypeId", unique = true),
		@Index(name = "ServiceUHostPort", columnList = "ClusterId, HostNodeId, HostPort", unique = true)
	}
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaService
	implements Comparable<Object>,
		ICFSecService,
		Serializable
{
	@Id
	@AttributeOverrides({
		@AttributeOverride(name="bytes", column = @Column( name="ServiceId", nullable=false, length=CFLibDbKeyHash256.HASH_LENGTH ) )
	})
	protected CFLibDbKeyHash256 requiredServiceId;
	protected int requiredRevision;

	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@JoinColumn( name="HostNodeId" )
	protected CFSecJpaHostNode optionalContainerHost;
	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@JoinColumn( name="ServiceTypeId" )
	protected CFSecJpaServiceType optionalParentServiceType;

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
	@Column( name="HostPort", nullable=false )
	protected short requiredHostPort;

	public CFSecJpaService() {
		requiredServiceId = CFLibDbKeyHash256.fromHex( ICFSecService.SERVICEID_INIT_VALUE.toString() );
		requiredClusterId = CFLibDbKeyHash256.fromHex( ICFSecService.CLUSTERID_INIT_VALUE.toString() );
		requiredHostPort = ICFSecService.HOSTPORT_INIT_VALUE;
	}

	@Override
	public int getClassCode() {
		return( ICFSecService.CLASS_CODE );
	}

	@Override
	public ICFSecCluster getRequiredOwnerCluster() {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredOwnerCluster", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecClusterTable targetTable = targetBackingSchema.getTableCluster();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredOwnerCluster", 0, "ICFSecSchema.getBackingCFSec().getTableCluster()");
		}
		ICFSecCluster targetRec = targetTable.readDerivedByIdIdx(null, getRequiredClusterId());
		return(targetRec);
	}
	@Override
	public void setRequiredOwnerCluster(ICFSecCluster argObj) {
		if(argObj == null) {
			throw new CFLibNullArgumentException(getClass(), "setOwnerCluster", 1, "argObj");
		}
		else {
			requiredClusterId = argObj.getRequiredId();
		}
	}

	@Override
	public void setRequiredOwnerCluster(CFLibDbKeyHash256 argClusterId) {
		requiredClusterId = argClusterId;
	}

	@Override
	public ICFSecHostNode getOptionalContainerHost() {
		return( optionalContainerHost );
	}
	@Override
	public void setOptionalContainerHost(ICFSecHostNode argObj) {
		if(argObj == null) {
			optionalContainerHost = null;
		}
		else if (argObj instanceof CFSecJpaHostNode) {
			optionalContainerHost = (CFSecJpaHostNode)argObj;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "setContainerHost", "argObj", argObj, "CFSecJpaHostNode");
		}
	}

	@Override
	public void setOptionalContainerHost(CFLibDbKeyHash256 argHostNodeId) {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setOptionalContainerHost", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecHostNodeTable targetTable = targetBackingSchema.getTableHostNode();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setOptionalContainerHost", 0, "ICFSecSchema.getBackingCFSec().getTableHostNode()");
		}
		ICFSecHostNode targetRec = targetTable.readDerived(null, argHostNodeId);
		setOptionalContainerHost(targetRec);
	}

	@Override
	public ICFSecServiceType getOptionalParentServiceType() {
		return( optionalParentServiceType );
	}
	@Override
	public void setOptionalParentServiceType(ICFSecServiceType argObj) {
		if(argObj == null) {
			optionalParentServiceType = null;
		}
		else if (argObj instanceof CFSecJpaServiceType) {
			optionalParentServiceType = (CFSecJpaServiceType)argObj;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "setParentServiceType", "argObj", argObj, "CFSecJpaServiceType");
		}
	}

	@Override
	public void setOptionalParentServiceType(CFLibDbKeyHash256 argServiceTypeId) {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setOptionalParentServiceType", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecServiceTypeTable targetTable = targetBackingSchema.getTableServiceType();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setOptionalParentServiceType", 0, "ICFSecSchema.getBackingCFSec().getTableServiceType()");
		}
		ICFSecServiceType targetRec = targetTable.readDerived(null, argServiceTypeId);
		setOptionalParentServiceType(targetRec);
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
		return getRequiredServiceId();
	}

	@Override
	public void setPKey(CFLibDbKeyHash256 requiredServiceId) {
		if (requiredServiceId != null) {
			setRequiredServiceId(requiredServiceId);
		}
	}
	
	@Override
	public CFLibDbKeyHash256 getRequiredServiceId() {
		return( requiredServiceId );
	}

	@Override
	public void setRequiredServiceId( CFLibDbKeyHash256 value ) {
		if( value == null || value.isNull() ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredServiceId",
				1,
				"value" );
		}
		requiredServiceId = value;
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
	public CFLibDbKeyHash256 getRequiredClusterId() {
		return( requiredClusterId );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredHostNodeId() {
		ICFSecHostNode result = getOptionalContainerHost();
		if (result != null) {
			return result.getRequiredHostNodeId();
		}
		else {
			return( ICFSecHostNode.HOSTNODEID_INIT_VALUE );
		}
	}

	@Override
	public CFLibDbKeyHash256 getRequiredServiceTypeId() {
		ICFSecServiceType result = getOptionalParentServiceType();
		if (result != null) {
			return result.getRequiredServiceTypeId();
		}
		else {
			return( ICFSecServiceType.SERVICETYPEID_INIT_VALUE );
		}
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
		int hashCode = getPKey().hashCode();
		hashCode = hashCode + getCreatedByUserId().hashCode();
		hashCode = hashCode + getCreatedAt().hashCode();
		hashCode = hashCode + getUpdatedByUserId().hashCode();
		hashCode = hashCode + getUpdatedAt().hashCode();
		hashCode = hashCode + getRequiredServiceId().hashCode();
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
			ICFSecServiceHPKey rhs = (ICFSecServiceHPKey)obj;
			if (getRequiredServiceId() != null) {
				if (rhs.getRequiredServiceId() != null) {
					cmp = getRequiredServiceId().compareTo( rhs.getRequiredServiceId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredServiceId() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if( obj instanceof ICFSecServiceH ) {
			ICFSecServiceH rhs = (ICFSecServiceH)obj;
			if (getRequiredServiceId() != null) {
				if (rhs.getRequiredServiceId() != null) {
					cmp = getRequiredServiceId().compareTo( rhs.getRequiredServiceId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredServiceId() != null) {
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
		else if (obj instanceof ICFSecServiceByClusterIdxKey) {
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
		else if (obj instanceof ICFSecServiceByHostIdxKey) {
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
		else if (obj instanceof ICFSecServiceByTypeIdxKey) {
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
		else if (obj instanceof ICFSecServiceByUTypeIdxKey) {
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
		else if (obj instanceof ICFSecServiceByUHostPortIdxKey) {
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
		setRequiredServiceId(src.getRequiredServiceId());
		setRequiredRevision( src.getRequiredRevision() );
		setCreatedByUserId( src.getCreatedByUserId() );
		setCreatedAt( src.getCreatedAt() );
		setUpdatedByUserId( src.getUpdatedByUserId() );
		setUpdatedAt( src.getUpdatedAt() );
		setRequiredOwnerCluster(src.getRequiredOwnerCluster());
		setOptionalContainerHost(src.getOptionalContainerHost());
		setOptionalParentServiceType(src.getOptionalParentServiceType());
		setRequiredHostPort(src.getRequiredHostPort());
	}

	@Override
	public void set( ICFSecServiceH src ) {
		setService( src );
	}

	@Override
	public void setService( ICFSecServiceH src ) {
		setRequiredServiceId(src.getRequiredServiceId());
		setRequiredOwnerCluster(src.getRequiredClusterId());
		setOptionalContainerHost(src.getRequiredHostNodeId());
		setOptionalParentServiceType(src.getRequiredServiceTypeId());
		setRequiredHostPort(src.getRequiredHostPort());
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = ""
			+ " RequiredServiceId=" + "\"" + getRequiredServiceId().toString() + "\""
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredServiceId=" + "\"" + getRequiredServiceId().toString() + "\""
			+ " RequiredClusterId=" + "\"" + getRequiredClusterId().toString() + "\""
			+ " RequiredHostNodeId=" + "\"" + getRequiredHostNodeId().toString() + "\""
			+ " RequiredServiceTypeId=" + "\"" + getRequiredServiceTypeId().toString() + "\""
			+ " RequiredHostPort=" + "\"" + Short.toString( getRequiredHostPort() ) + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecJpaService" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

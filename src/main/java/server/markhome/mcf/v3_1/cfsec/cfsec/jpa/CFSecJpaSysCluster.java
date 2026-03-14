// Description: Java 25 JPA implementation of a SysCluster entity definition object.

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
	name = "sysclus", schema = "CFSec31",
	indexes = {
		@Index(name = "SysClusterIdIdx", columnList = "sgltn_id", unique = true),
		@Index(name = "SysClusterClusterIdx", columnList = "sys_clus_id", unique = false)
	}
)
@Transactional(Transactional.TxType.SUPPORTS)
@PersistenceContext(unitName = "CFSecPU")
public class CFSecJpaSysCluster
	implements Comparable<Object>,
		ICFSecSysCluster,
		Serializable
{
	@Id
	@Column( name="sgltn_id", nullable=false )
	protected int requiredSingletonId;
	protected int requiredRevision;

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn( name="sys_clus_id" )
	protected CFSecJpaCluster requiredContainerCluster;


	public CFSecJpaSysCluster() {
		requiredSingletonId = ICFSecSysCluster.SINGLETONID_INIT_VALUE;
	}

	@Override
	public int getClassCode() {
		return( ICFSecSysCluster.CLASS_CODE );
	}

	@Override
	public ICFSecCluster getRequiredContainerCluster() {
		return( requiredContainerCluster );
	}
	@Override
	public void setRequiredContainerCluster(ICFSecCluster argObj) {
		if(argObj == null) {
			throw new CFLibNullArgumentException(getClass(), "setContainerCluster", 1, "argObj");
		}
		else if (argObj instanceof CFSecJpaCluster) {
			requiredContainerCluster = (CFSecJpaCluster)argObj;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "setContainerCluster", "argObj", argObj, "CFSecJpaCluster");
		}
	}

	@Override
	public void setRequiredContainerCluster(CFLibDbKeyHash256 argClusterId) {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredContainerCluster", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecClusterTable targetTable = targetBackingSchema.getTableCluster();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredContainerCluster", 0, "ICFSecSchema.getBackingCFSec().getTableCluster()");
		}
		ICFSecCluster targetRec = targetTable.readDerived(null, argClusterId);
		setRequiredContainerCluster(targetRec);
	}

	@Override
	public Integer getPKey() {
		return getRequiredSingletonId();
	}

	@Override
	public void setPKey(Integer requiredSingletonId) {
		if (requiredSingletonId != null) {
			setRequiredSingletonId(requiredSingletonId);
		}
	}
	
	@Override
	public int getRequiredSingletonId() {
		return( requiredSingletonId );
	}

	@Override
	public void setRequiredSingletonId( int value ) {
		if( value < ICFSecSysCluster.SINGLETONID_MIN_VALUE ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				"setRequiredSingletonId",
				1,
				"value",
				value,
				ICFSecSysCluster.SINGLETONID_MIN_VALUE );
		}
		if( value > ICFSecSysCluster.SINGLETONID_MAX_VALUE ) {
			throw new CFLibArgumentOverflowException( getClass(),
				"setRequiredSingletonId",
				1,
				"value",
				value,
				ICFSecSysCluster.SINGLETONID_MAX_VALUE );
		}
		requiredSingletonId = value;
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
		ICFSecCluster result = getRequiredContainerCluster();
		if (result != null) {
			return result.getRequiredId();
		}
		else {
			return( ICFSecCluster.ID_INIT_VALUE );
		}
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecSysCluster) {
			ICFSecSysCluster rhs = (ICFSecSysCluster)obj;
			if( getRequiredSingletonId() != rhs.getRequiredSingletonId() ) {
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
			return( true );
		}
		else if (obj instanceof ICFSecSysClusterByClusterIdxKey) {
			ICFSecSysClusterByClusterIdxKey rhs = (ICFSecSysClusterByClusterIdxKey)obj;
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
		else {
			return( false );
		}
	}
	
	@Override
	public int hashCode() {
		int hashCode = getPKey().hashCode();
		hashCode = hashCode + getRequiredSingletonId();
		hashCode = hashCode + getRequiredClusterId().hashCode();
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFSecSysCluster) {
			ICFSecSysCluster rhs = (ICFSecSysCluster)obj;
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
		else if (obj instanceof ICFSecSysClusterByClusterIdxKey) {
			ICFSecSysClusterByClusterIdxKey rhs = (ICFSecSysClusterByClusterIdxKey)obj;
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
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				"compareTo",
				"obj",
				obj,
				null );
		}
	}

	@Override
	public void set( ICFSecSysCluster src ) {
		setSysCluster( src );
	}

	@Override
	public void setSysCluster( ICFSecSysCluster src ) {
		setRequiredSingletonId(src.getRequiredSingletonId());
		setRequiredRevision( src.getRequiredRevision() );
		setRequiredContainerCluster(src.getRequiredContainerCluster());
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = ""
			+ " RequiredSingletonId=" + "\"" + Integer.toString( getRequiredSingletonId() ) + "\""
			+ " RequiredRevision=\"" + Integer.toString( getRequiredRevision() ) + "\""
			+ " RequiredSingletonId=" + "\"" + Integer.toString( getRequiredSingletonId() ) + "\""
			+ " RequiredClusterId=" + "\"" + getRequiredClusterId().toString() + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecJpaSysCluster" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

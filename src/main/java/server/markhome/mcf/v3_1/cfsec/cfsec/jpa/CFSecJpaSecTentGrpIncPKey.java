// Description: Java JPA implementation of a SecTentGrpInc primary key object.

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
 *	CFSecJpaSecTentGrpIncPKey Primary Key for SecTentGrpInc
 *		requiredSecTentGrpId	Required object attribute SecTentGrpId.
 *		requiredInclName	Required object attribute InclName.
 */
@Embeddable
public class CFSecJpaSecTentGrpIncPKey
	implements ICFSecSecTentGrpIncPKey, Comparable<ICFSecSecTentGrpIncPKey>, Serializable
{
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn( name="SecTentGrpId" )
	protected CFSecJpaSecTentGrp requiredContainerGroup;
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn( name="inc_name", referencedColumnName="safe_name" )
	protected CFSecJpaSecSysGrp requiredParentSubGroup;

	public CFSecJpaSecTentGrpIncPKey() {
		requiredContainerGroup = null;
		requiredParentSubGroup = null;
	}

	@Override
	public ICFSecSecTentGrp getRequiredContainerGroup() {
		return( requiredContainerGroup );
	}
	@Override
	public void setRequiredContainerGroup(ICFSecSecTentGrp argObj) {
		if(argObj == null) {
			throw new CFLibNullArgumentException(getClass(), "setContainerGroup", 1, "argObj");
		}
		else if (argObj instanceof CFSecJpaSecTentGrp) {
			requiredContainerGroup = (CFSecJpaSecTentGrp)argObj;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "setContainerGroup", "argObj", argObj, "CFSecJpaSecTentGrp");
		}
	
	}

	@Override
	public void setRequiredContainerGroup(CFLibDbKeyHash256 argSecTentGrpId) {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredContainerGroup", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecSecTentGrpTable targetTable = targetBackingSchema.getTableSecTentGrp();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredContainerGroup", 0, "ICFSecSchema.getBackingCFSec().getTableSecTentGrp()");
		}
		ICFSecSecTentGrp targetRec = targetTable.readDerivedByIdIdx(null, argSecTentGrpId);
		setRequiredContainerGroup(targetRec);
	}
	@Override
	public ICFSecSecSysGrp getRequiredParentSubGroup() {
		return( requiredParentSubGroup );
	}
	@Override
	public void setRequiredParentSubGroup(ICFSecSecSysGrp argObj) {
		if(argObj == null) {
			throw new CFLibNullArgumentException(getClass(), "setParentSubGroup", 1, "argObj");
		}
		else if (argObj instanceof CFSecJpaSecSysGrp) {
			requiredParentSubGroup = (CFSecJpaSecSysGrp)argObj;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "setParentSubGroup", "argObj", argObj, "CFSecJpaSecSysGrp");
		}
	
	}

	@Override
	public void setRequiredParentSubGroup(String argInclName) {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredParentSubGroup", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecSecSysGrpTable targetTable = targetBackingSchema.getTableSecSysGrp();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredParentSubGroup", 0, "ICFSecSchema.getBackingCFSec().getTableSecSysGrp()");
		}
		ICFSecSecSysGrp targetRec = targetTable.readDerivedByUNameIdx(null, argInclName);
		setRequiredParentSubGroup(targetRec);
	}
	@Override
	public CFLibDbKeyHash256 getRequiredSecTentGrpId() {
		ICFSecSecTentGrp result = getRequiredContainerGroup();
		if (result != null) {
			return result.getRequiredSecTentGrpId();
		}
		else {
			throw new CFLibNullArgumentException(getClass(), "getRequiredSecTentGrpId", 0, "getRequiredContainerGroup()");
		}
	}

	@Override
	public String getRequiredInclName() {
		ICFSecSecSysGrp result = getRequiredParentSubGroup();
		if (result != null) {
			return result.getRequiredName();
		}
		else {
			throw new CFLibNullArgumentException(getClass(), "getRequiredInclName", 0, "getRequiredParentSubGroup()");
		}
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecSecTentGrpIncPKey) {
			ICFSecSecTentGrpIncPKey rhs = (ICFSecSecTentGrpIncPKey)obj;
			if( getRequiredSecTentGrpId() != null ) {
				if( rhs.getRequiredSecTentGrpId() != null ) {
					if( ! getRequiredSecTentGrpId().equals( rhs.getRequiredSecTentGrpId() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredSecTentGrpId() != null ) {
					return( false );
				}
			}
			if( getRequiredInclName() != null ) {
				if( rhs.getRequiredInclName() != null ) {
					if( ! getRequiredInclName().equals( rhs.getRequiredInclName() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredInclName() != null ) {
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
		int hashCode = 0;
		hashCode = hashCode + getRequiredSecTentGrpId().hashCode();
		if( getRequiredInclName() != null ) {
			hashCode = hashCode + getRequiredInclName().hashCode();
		}
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( ICFSecSecTentGrpIncPKey rhs ) {
		int cmp;
		if (rhs == null) {
			return( 1 );
		}
			if (getRequiredSecTentGrpId() != null) {
				if (rhs.getRequiredSecTentGrpId() != null) {
					cmp = getRequiredSecTentGrpId().compareTo( rhs.getRequiredSecTentGrpId() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredSecTentGrpId() != null) {
				return( -1 );
			}
			if (getRequiredInclName() != null) {
				if (rhs.getRequiredInclName() != null) {
					cmp = getRequiredInclName().compareTo( rhs.getRequiredInclName() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredInclName() != null) {
				return( -1 );
			}
		return( 0 );
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = "" 
			+ " RequiredSecTentGrpId=" + "\"" + getRequiredSecTentGrpId().toString() + "\""
			+ " RequiredInclName=" + "\"" + StringEscapeUtils.escapeXml11( getRequiredInclName() ) + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecSecTentGrpIncPKey" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

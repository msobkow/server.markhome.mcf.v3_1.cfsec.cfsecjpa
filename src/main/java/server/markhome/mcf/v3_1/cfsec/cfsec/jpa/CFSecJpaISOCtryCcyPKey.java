// Description: Java JPA implementation of a ISOCtryCcy primary key object.

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

/**
 *	CFSecJpaISOCtryCcyPKey Primary Key for ISOCtryCcy
 *		requiredISOCtryId	Required object attribute ISOCtryId.
 *		requiredISOCcyId	Required object attribute ISOCcyId.
 */
@Embeddable
public class CFSecJpaISOCtryCcyPKey
	implements ICFSecISOCtryCcyPKey, Comparable<ICFSecISOCtryCcyPKey>, Serializable
{
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn( name="ISOCtryId" )
	protected CFSecJpaISOCtry requiredContainerCtry;
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn( name="ISOCcyId" )
	protected CFSecJpaISOCcy requiredParentCcy;

	public CFSecJpaISOCtryCcyPKey() {
		requiredContainerCtry = null;
		requiredParentCcy = null;
	}

	@Override
	public ICFSecISOCtry getRequiredContainerCtry() {
		return( requiredContainerCtry );
	}
	@Override
	public void setRequiredContainerCtry(ICFSecISOCtry argObj) {
		if(argObj == null) {
			throw new CFLibNullArgumentException(getClass(), "setContainerCtry", 1, "argObj");
		}
		else if (argObj instanceof CFSecJpaISOCtry) {
			requiredContainerCtry = (CFSecJpaISOCtry)argObj;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "setContainerCtry", "argObj", argObj, "CFSecJpaISOCtry");
		}
	
	}

	@Override
	public void setRequiredContainerCtry(short argISOCtryId) {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredContainerCtry", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecISOCtryTable targetTable = targetBackingSchema.getTableISOCtry();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredContainerCtry", 0, "ICFSecSchema.getBackingCFSec().getTableISOCtry()");
		}
		ICFSecISOCtry targetRec = targetTable.readDerivedByIdIdx(null, argISOCtryId);
		setRequiredContainerCtry(targetRec);
	}
	@Override
	public ICFSecISOCcy getRequiredParentCcy() {
		return( requiredParentCcy );
	}
	@Override
	public void setRequiredParentCcy(ICFSecISOCcy argObj) {
		if(argObj == null) {
			throw new CFLibNullArgumentException(getClass(), "setParentCcy", 1, "argObj");
		}
		else if (argObj instanceof CFSecJpaISOCcy) {
			requiredParentCcy = (CFSecJpaISOCcy)argObj;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "setParentCcy", "argObj", argObj, "CFSecJpaISOCcy");
		}
	
	}

	@Override
	public void setRequiredParentCcy(short argISOCcyId) {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredParentCcy", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecISOCcyTable targetTable = targetBackingSchema.getTableISOCcy();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredParentCcy", 0, "ICFSecSchema.getBackingCFSec().getTableISOCcy()");
		}
		ICFSecISOCcy targetRec = targetTable.readDerivedByIdIdx(null, argISOCcyId);
		setRequiredParentCcy(targetRec);
	}
	@Override
	public short getRequiredISOCtryId() {
		ICFSecISOCtry result = getRequiredContainerCtry();
		if (result != null) {
			return result.getRequiredISOCtryId();
		}
		else {
			throw new CFLibNullArgumentException(getClass(), "getRequiredISOCtryId", 0, "getRequiredContainerCtry()");
		}
	}

	@Override
	public short getRequiredISOCcyId() {
		ICFSecISOCcy result = getRequiredParentCcy();
		if (result != null) {
			return result.getRequiredISOCcyId();
		}
		else {
			throw new CFLibNullArgumentException(getClass(), "getRequiredISOCcyId", 0, "getRequiredParentCcy()");
		}
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecISOCtryCcyPKey) {
			ICFSecISOCtryCcyPKey rhs = (ICFSecISOCtryCcyPKey)obj;
			if( getRequiredISOCtryId() != rhs.getRequiredISOCtryId() ) {
				return( false );
			}
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
		int hashCode = 0;
		hashCode = ( hashCode * 0x10000 ) + getRequiredISOCtryId();
		hashCode = ( hashCode * 0x10000 ) + getRequiredISOCcyId();
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( ICFSecISOCtryCcyPKey rhs ) {
		int cmp;
		if (rhs == null) {
			return( 1 );
		}
			if( getRequiredISOCtryId() < rhs.getRequiredISOCtryId() ) {
				return( -1 );
			}
			else if( getRequiredISOCtryId() > rhs.getRequiredISOCtryId() ) {
				return( 1 );
			}
			if( getRequiredISOCcyId() < rhs.getRequiredISOCcyId() ) {
				return( -1 );
			}
			else if( getRequiredISOCcyId() > rhs.getRequiredISOCcyId() ) {
				return( 1 );
			}
		return( 0 );
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = "" 
			+ " RequiredISOCtryId=" + "\"" + Short.toString( getRequiredISOCtryId() ) + "\""
			+ " RequiredISOCcyId=" + "\"" + Short.toString( getRequiredISOCcyId() ) + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecISOCtryCcyPKey" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

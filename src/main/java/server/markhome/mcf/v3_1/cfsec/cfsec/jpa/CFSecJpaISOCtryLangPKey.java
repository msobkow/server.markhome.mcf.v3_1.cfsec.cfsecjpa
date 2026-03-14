// Description: Java JPA implementation of a ISOCtryLang primary key object.

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
 *	CFSecJpaISOCtryLangPKey Primary Key for ISOCtryLang
 *		requiredISOCtryId	Required object attribute ISOCtryId.
 *		requiredISOLangId	Required object attribute ISOLangId.
 */
@Embeddable
public class CFSecJpaISOCtryLangPKey
	implements ICFSecISOCtryLangPKey, Comparable<ICFSecISOCtryLangPKey>, Serializable
{
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn( name="ISOCtryId" )
	protected CFSecJpaISOCtry requiredContainerCtry;
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn( name="ISOLangId" )
	protected CFSecJpaISOLang requiredParentLang;

	public CFSecJpaISOCtryLangPKey() {
		requiredContainerCtry = null;
		requiredParentLang = null;
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
	public ICFSecISOLang getRequiredParentLang() {
		return( requiredParentLang );
	}
	@Override
	public void setRequiredParentLang(ICFSecISOLang argObj) {
		if(argObj == null) {
			throw new CFLibNullArgumentException(getClass(), "setParentLang", 1, "argObj");
		}
		else if (argObj instanceof CFSecJpaISOLang) {
			requiredParentLang = (CFSecJpaISOLang)argObj;
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(), "setParentLang", "argObj", argObj, "CFSecJpaISOLang");
		}
	
	}

	@Override
	public void setRequiredParentLang(short argISOLangId) {
		ICFSecSchema targetBackingSchema = ICFSecSchema.getBackingCFSec();
		if (targetBackingSchema == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredParentLang", 0, "ICFSecSchema.getBackingCFSec()");
		}
		ICFSecISOLangTable targetTable = targetBackingSchema.getTableISOLang();
		if (targetTable == null) {
			throw new CFLibNullArgumentException(getClass(), "setRequiredParentLang", 0, "ICFSecSchema.getBackingCFSec().getTableISOLang()");
		}
		ICFSecISOLang targetRec = targetTable.readDerivedByIdIdx(null, argISOLangId);
		setRequiredParentLang(targetRec);
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
	public short getRequiredISOLangId() {
		ICFSecISOLang result = getRequiredParentLang();
		if (result != null) {
			return result.getRequiredISOLangId();
		}
		else {
			throw new CFLibNullArgumentException(getClass(), "getRequiredISOLangId", 0, "getRequiredParentLang()");
		}
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecISOCtryLangPKey) {
			ICFSecISOCtryLangPKey rhs = (ICFSecISOCtryLangPKey)obj;
			if( getRequiredISOCtryId() != rhs.getRequiredISOCtryId() ) {
				return( false );
			}
			if( getRequiredISOLangId() != rhs.getRequiredISOLangId() ) {
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
		hashCode = ( hashCode * 0x10000 ) + getRequiredISOLangId();
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( ICFSecISOCtryLangPKey rhs ) {
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
			if( getRequiredISOLangId() < rhs.getRequiredISOLangId() ) {
				return( -1 );
			}
			else if( getRequiredISOLangId() > rhs.getRequiredISOLangId() ) {
				return( 1 );
			}
		return( 0 );
	}

	@Override
	public String getXmlAttrFragment() {
		String ret = "" 
			+ " RequiredISOCtryId=" + "\"" + Short.toString( getRequiredISOCtryId() ) + "\""
			+ " RequiredISOLangId=" + "\"" + Short.toString( getRequiredISOLangId() ) + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecISOCtryLangPKey" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

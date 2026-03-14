// Description: Java 25 JPA implementation of a ISOCtryLang by LangIdx index key object.

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

public class CFSecJpaISOCtryLangByLangIdxKey
	implements ICFSecISOCtryLangByLangIdxKey, Comparable<Object>, Serializable
{
	protected short requiredISOLangId;
	public CFSecJpaISOCtryLangByLangIdxKey() {
		requiredISOLangId = ICFSecISOCtryLang.ISOLANGID_INIT_VALUE;
	}

	@Override
	public short getRequiredISOLangId() {
		return( requiredISOLangId );
	}

	@Override
	public void setRequiredISOLangId( short value ) {
		if( value < ICFSecISOCtryLang.ISOLANGID_MIN_VALUE ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				"setRequiredISOLangId",
				1,
				"value",
				value,
				ICFSecISOCtryLang.ISOLANGID_MIN_VALUE );
		}
		requiredISOLangId = value;
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecISOCtryLangByLangIdxKey) {
			ICFSecISOCtryLangByLangIdxKey rhs = (ICFSecISOCtryLangByLangIdxKey)obj;
			if( getRequiredISOLangId() != rhs.getRequiredISOLangId() ) {
				return( false );
			}
			return( true );
		}
		else if (obj instanceof ICFSecISOCtryLang) {
			ICFSecISOCtryLang rhs = (ICFSecISOCtryLang)obj;
			if( getRequiredISOLangId() != rhs.getRequiredISOLangId() ) {
				return( false );
			}
			return( true );
		}
		else if (obj instanceof ICFSecISOCtryLangH) {
			ICFSecISOCtryLangH rhs = (ICFSecISOCtryLangH)obj;
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
		hashCode = ( hashCode * 0x10000 ) + getRequiredISOLangId();
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFSecISOCtryLangByLangIdxKey) {
			ICFSecISOCtryLangByLangIdxKey rhs = (ICFSecISOCtryLangByLangIdxKey)obj;
			if( getRequiredISOLangId() < rhs.getRequiredISOLangId() ) {
				return( -1 );
			}
			else if( getRequiredISOLangId() > rhs.getRequiredISOLangId() ) {
				return( 1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecISOCtryLang) {
			ICFSecISOCtryLang rhs = (ICFSecISOCtryLang)obj;
			if( getRequiredISOLangId() < rhs.getRequiredISOLangId() ) {
				return( -1 );
			}
			else if( getRequiredISOLangId() > rhs.getRequiredISOLangId() ) {
				return( 1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecISOCtryLangH) {
			ICFSecISOCtryLangH rhs = (ICFSecISOCtryLangH)obj;
			if( getRequiredISOLangId() < rhs.getRequiredISOLangId() ) {
				return( -1 );
			}
			else if( getRequiredISOLangId() > rhs.getRequiredISOLangId() ) {
				return( 1 );
			}
			return( 0 );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(),
				"compareTo",
				"obj",
				obj,
				"ICFSecISOCtryLangByLangIdxKey, ICFSecISOCtryLang, ICFSecISOCtryLangH");
		}
	}

	public String getXmlAttrFragment() {
		String ret = "" 
			+ " RequiredISOLangId=" + "\"" + Short.toString( getRequiredISOLangId() ) + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecISOCtryLangByLangIdxKey" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

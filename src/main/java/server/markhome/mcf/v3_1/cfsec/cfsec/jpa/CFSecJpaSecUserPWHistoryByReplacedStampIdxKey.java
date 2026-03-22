// Description: Java 25 JPA implementation of a SecUserPWHistory by ReplacedStampIdx index key object.

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

public class CFSecJpaSecUserPWHistoryByReplacedStampIdxKey
	implements ICFSecSecUserPWHistoryByReplacedStampIdxKey, Comparable<Object>, Serializable
{
	protected LocalDateTime requiredPWReplacedStamp;
	public CFSecJpaSecUserPWHistoryByReplacedStampIdxKey() {
		requiredPWReplacedStamp = CFLibXmlUtil.parseTimestamp("2020-01-01T00:00:00");
	}

	@Override
	public LocalDateTime getRequiredPWReplacedStamp() {
		return( requiredPWReplacedStamp );
	}

	@Override
	public void setRequiredPWReplacedStamp( LocalDateTime value ) {
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				"setRequiredPWReplacedStamp",
				1,
				"value" );
		}
		requiredPWReplacedStamp = value;
	}

	@Override
	public boolean equals( Object obj ) {
		if (obj == null) {
			return( false );
		}
		else if (obj instanceof ICFSecSecUserPWHistoryByReplacedStampIdxKey) {
			ICFSecSecUserPWHistoryByReplacedStampIdxKey rhs = (ICFSecSecUserPWHistoryByReplacedStampIdxKey)obj;
			if( getRequiredPWReplacedStamp() != null ) {
				if( rhs.getRequiredPWReplacedStamp() != null ) {
					if( ! getRequiredPWReplacedStamp().equals( rhs.getRequiredPWReplacedStamp() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredPWReplacedStamp() != null ) {
					return( false );
				}
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecUserPWHistory) {
			ICFSecSecUserPWHistory rhs = (ICFSecSecUserPWHistory)obj;
			if( getRequiredPWReplacedStamp() != null ) {
				if( rhs.getRequiredPWReplacedStamp() != null ) {
					if( ! getRequiredPWReplacedStamp().equals( rhs.getRequiredPWReplacedStamp() ) ) {
						return( false );
					}
				}
				else {
					return( false );
				}
			}
			else {
				if( rhs.getRequiredPWReplacedStamp() != null ) {
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
		if( getRequiredPWReplacedStamp() != null ) {
			hashCode = hashCode + getRequiredPWReplacedStamp().hashCode();
		}
		return( hashCode & 0x7fffffff );
	}

	@Override
	public int compareTo( Object obj ) {
		int cmp;
		if (obj == null) {
			return( 1 );
		}
		else if (obj instanceof ICFSecSecUserPWHistoryByReplacedStampIdxKey) {
			ICFSecSecUserPWHistoryByReplacedStampIdxKey rhs = (ICFSecSecUserPWHistoryByReplacedStampIdxKey)obj;
			if (getRequiredPWReplacedStamp() != null) {
				if (rhs.getRequiredPWReplacedStamp() != null) {
					cmp = getRequiredPWReplacedStamp().compareTo( rhs.getRequiredPWReplacedStamp() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredPWReplacedStamp() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else if (obj instanceof ICFSecSecUserPWHistory) {
			ICFSecSecUserPWHistory rhs = (ICFSecSecUserPWHistory)obj;
			if (getRequiredPWReplacedStamp() != null) {
				if (rhs.getRequiredPWReplacedStamp() != null) {
					cmp = getRequiredPWReplacedStamp().compareTo( rhs.getRequiredPWReplacedStamp() );
					if( cmp != 0 ) {
						return( cmp );
					}
				}
				else {
					return( 1 );
				}
			}
			else if (rhs.getRequiredPWReplacedStamp() != null) {
				return( -1 );
			}
			return( 0 );
		}
		else {
			throw new CFLibUnsupportedClassException(getClass(),
				"compareTo",
				"obj",
				obj,
				"ICFSecSecUserPWHistoryByReplacedStampIdxKey, ICFSecSecUserPWHistory");
		}
	}

	public String getXmlAttrFragment() {
		String ret = "" 
			+ " RequiredPWReplacedStamp=" + "\"" + getRequiredPWReplacedStamp().toString() + "\"";
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecSecUserPWHistoryByReplacedStampIdxKey" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

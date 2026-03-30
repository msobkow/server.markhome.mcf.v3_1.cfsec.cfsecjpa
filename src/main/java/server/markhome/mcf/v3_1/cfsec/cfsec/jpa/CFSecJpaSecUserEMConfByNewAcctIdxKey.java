// Description: Java 25 JPA implementation of a SecUserEMConf by NewAcctIdx index key object.

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

public class CFSecJpaSecUserEMConfByNewAcctIdxKey
	implements ICFSecSecUserEMConfByNewAcctIdxKey, Comparable<Object>, Serializable
{
	protected boolean requiredNewAccount;
	public CFSecJpaSecUserEMConfByNewAcctIdxKey() {
		requiredNewAccount = ICFSecSecUserEMConf.NEWACCOUNT_INIT_VALUE;
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
		else if (obj instanceof ICFSecSecUserEMConfByNewAcctIdxKey) {
			ICFSecSecUserEMConfByNewAcctIdxKey rhs = (ICFSecSecUserEMConfByNewAcctIdxKey)obj;
			if( getRequiredNewAccount() != rhs.getRequiredNewAccount() ) {
				return( false );
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecUserEMConf) {
			ICFSecSecUserEMConf rhs = (ICFSecSecUserEMConf)obj;
			if( getRequiredNewAccount() != rhs.getRequiredNewAccount() ) {
				return( false );
			}
			return( true );
		}
		else if (obj instanceof ICFSecSecUserEMConfH) {
			ICFSecSecUserEMConfH rhs = (ICFSecSecUserEMConfH)obj;
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
		int hashCode = 0;
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
		else if (obj instanceof ICFSecSecUserEMConf) {
			ICFSecSecUserEMConf rhs = (ICFSecSecUserEMConf)obj;
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
		else if (obj instanceof ICFSecSecUserEMConfH) {
			ICFSecSecUserEMConfH rhs = (ICFSecSecUserEMConfH)obj;
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
			throw new CFLibUnsupportedClassException(getClass(),
				"compareTo",
				"obj",
				obj,
				"ICFSecSecUserEMConfByNewAcctIdxKey, ICFSecSecUserEMConf, ICFSecSecUserEMConfH");
		}
	}

	public String getXmlAttrFragment() {
		String ret = "" 
			+ " RequiredNewAccount=" + (( getRequiredNewAccount() ) ? "\"true\"" : "\"false\"" );
		return( ret );
	}

	@Override
	public String toString() {
		String ret = "<CFSecSecUserEMConfByNewAcctIdxKey" + getXmlAttrFragment() + "/>";
		return( ret );
	}
}

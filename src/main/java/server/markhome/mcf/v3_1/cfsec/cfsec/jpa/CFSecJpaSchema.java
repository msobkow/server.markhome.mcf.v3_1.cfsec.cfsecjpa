// Description: Java 25 JPA implementation of a CFSec schema.

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
//package server.markhome.mcf.v3_1.cfsec.cfsec.jpa;

import java.io.Serializable;
import java.math.*;
import java.time.*;
import java.net.InetAddress;
import java.util.*;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.atomic.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.inz.Inz;
import server.markhome.mcf.v3_1.cflib.xml.CFLibXmlUtil;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.jpa.CFSecJpaHooksSchema;

public class CFSecJpaSchema
	implements ICFSecSchema
{
	private CFSecJpaHooksSchema cfsecJpaHooksSchema = null;

	protected static ICFSecTablePerms tablePerms;
	protected ICFSecClusterTable tableCluster;
	protected ICFSecISOCcyTable tableISOCcy;
	protected ICFSecISOCtryTable tableISOCtry;
	protected ICFSecISOCtryCcyTable tableISOCtryCcy;
	protected ICFSecISOCtryLangTable tableISOCtryLang;
	protected ICFSecISOLangTable tableISOLang;
	protected ICFSecISOTZoneTable tableISOTZone;
	protected ICFSecSecClusGrpTable tableSecClusGrp;
	protected ICFSecSecClusGrpIncTable tableSecClusGrpInc;
	protected ICFSecSecClusGrpMembTable tableSecClusGrpMemb;
	protected ICFSecSecSessionTable tableSecSession;
	protected ICFSecSecSysGrpTable tableSecSysGrp;
	protected ICFSecSecSysGrpIncTable tableSecSysGrpInc;
	protected ICFSecSecSysGrpMembTable tableSecSysGrpMemb;
	protected ICFSecSecTentGrpTable tableSecTentGrp;
	protected ICFSecSecTentGrpIncTable tableSecTentGrpInc;
	protected ICFSecSecTentGrpMembTable tableSecTentGrpMemb;
	protected ICFSecSecUserTable tableSecUser;
	protected ICFSecSecUserPWHistoryTable tableSecUserPWHistory;
	protected ICFSecSecUserPasswordTable tableSecUserPassword;
	protected ICFSecSysClusterTable tableSysCluster;
	protected ICFSecTenantTable tableTenant;

	protected ICFSecClusterFactory factoryCluster;
	protected ICFSecISOCcyFactory factoryISOCcy;
	protected ICFSecISOCtryFactory factoryISOCtry;
	protected ICFSecISOCtryCcyFactory factoryISOCtryCcy;
	protected ICFSecISOCtryLangFactory factoryISOCtryLang;
	protected ICFSecISOLangFactory factoryISOLang;
	protected ICFSecISOTZoneFactory factoryISOTZone;
	protected ICFSecSecClusGrpFactory factorySecClusGrp;
	protected ICFSecSecClusGrpIncFactory factorySecClusGrpInc;
	protected ICFSecSecClusGrpMembFactory factorySecClusGrpMemb;
	protected ICFSecSecSessionFactory factorySecSession;
	protected ICFSecSecSysGrpFactory factorySecSysGrp;
	protected ICFSecSecSysGrpIncFactory factorySecSysGrpInc;
	protected ICFSecSecSysGrpMembFactory factorySecSysGrpMemb;
	protected ICFSecSecTentGrpFactory factorySecTentGrp;
	protected ICFSecSecTentGrpIncFactory factorySecTentGrpInc;
	protected ICFSecSecTentGrpMembFactory factorySecTentGrpMemb;
	protected ICFSecSecUserFactory factorySecUser;
	protected ICFSecSecUserPWHistoryFactory factorySecUserPWHistory;
	protected ICFSecSecUserPasswordFactory factorySecUserPassword;
	protected ICFSecSysClusterFactory factorySysCluster;
	protected ICFSecTenantFactory factoryTenant;


	@Override
	public int initClassMapEntries(int value) {
		return( ICFSecSchema.doInitClassMapEntries(value) );
	}

	@Override
	public void wireRecConstructors() {
		ICFSecSchema.ClassMapEntry entry;
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecCluster.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecCluster ret = new CFSecJpaCluster();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecCluster.CLASS_CODE)[" + ICFSecCluster.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecISOCcy.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecISOCcy ret = new CFSecJpaISOCcy();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecISOCcy.CLASS_CODE)[" + ICFSecISOCcy.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecISOCtry.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecISOCtry ret = new CFSecJpaISOCtry();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecISOCtry.CLASS_CODE)[" + ICFSecISOCtry.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecISOCtryCcy.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecISOCtryCcy ret = new CFSecJpaISOCtryCcy();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecISOCtryCcy.CLASS_CODE)[" + ICFSecISOCtryCcy.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecISOCtryLang.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecISOCtryLang ret = new CFSecJpaISOCtryLang();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecISOCtryLang.CLASS_CODE)[" + ICFSecISOCtryLang.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecISOLang.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecISOLang ret = new CFSecJpaISOLang();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecISOLang.CLASS_CODE)[" + ICFSecISOLang.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecISOTZone.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecISOTZone ret = new CFSecJpaISOTZone();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecISOTZone.CLASS_CODE)[" + ICFSecISOTZone.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecUser.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecSecUser ret = new CFSecJpaSecUser();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecUser.CLASS_CODE)[" + ICFSecSecUser.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecUserPassword.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecSecUserPassword ret = new CFSecJpaSecUserPassword();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecUserPassword.CLASS_CODE)[" + ICFSecSecUserPassword.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecUserPWHistory.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecSecUserPWHistory ret = new CFSecJpaSecUserPWHistory();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecUserPWHistory.CLASS_CODE)[" + ICFSecSecUserPWHistory.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecSysGrp.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecSecSysGrp ret = new CFSecJpaSecSysGrp();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecSysGrp.CLASS_CODE)[" + ICFSecSecSysGrp.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecSysGrpInc.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecSecSysGrpInc ret = new CFSecJpaSecSysGrpInc();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecSysGrpInc.CLASS_CODE)[" + ICFSecSecSysGrpInc.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecSysGrpMemb.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecSecSysGrpMemb ret = new CFSecJpaSecSysGrpMemb();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecSysGrpMemb.CLASS_CODE)[" + ICFSecSecSysGrpMemb.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecClusGrp.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecSecClusGrp ret = new CFSecJpaSecClusGrp();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecClusGrp.CLASS_CODE)[" + ICFSecSecClusGrp.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecClusGrpInc.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecSecClusGrpInc ret = new CFSecJpaSecClusGrpInc();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecClusGrpInc.CLASS_CODE)[" + ICFSecSecClusGrpInc.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecClusGrpMemb.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecSecClusGrpMemb ret = new CFSecJpaSecClusGrpMemb();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecClusGrpMemb.CLASS_CODE)[" + ICFSecSecClusGrpMemb.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecTentGrp.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecSecTentGrp ret = new CFSecJpaSecTentGrp();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecTentGrp.CLASS_CODE)[" + ICFSecSecTentGrp.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecTentGrpInc.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecSecTentGrpInc ret = new CFSecJpaSecTentGrpInc();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecTentGrpInc.CLASS_CODE)[" + ICFSecSecTentGrpInc.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecTentGrpMemb.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecSecTentGrpMemb ret = new CFSecJpaSecTentGrpMemb();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecTentGrpMemb.CLASS_CODE)[" + ICFSecSecTentGrpMemb.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecSession.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecSecSession ret = new CFSecJpaSecSession();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecSecSession.CLASS_CODE)[" + ICFSecSecSession.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecSysCluster.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecSysCluster ret = new CFSecJpaSysCluster();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecSysCluster.CLASS_CODE)[" + ICFSecSysCluster.CLASS_CODE + "]");
		}
	
		entry = ICFSecSchema.getClassMapByBackingClassCode(ICFSecTenant.CLASS_CODE);
		if (entry != null) {
			entry.setBackingRecConstructor( new BackingRecConstructor() {
				@Override
				public Object instantiate() {
					ICFSecTenant ret = new CFSecJpaTenant();
					return(ret);
				}
			});
		}
		else {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "wireRecConstructors", 0, "ICFSecSchema.getClassMapByBackingClassCode(ICFSecTenant.CLASS_CODE)[" + ICFSecTenant.CLASS_CODE + "]");
		}
	
	}

	@Override
	public void wireTableTableInstances() {
		if (tableCluster == null || !(tableCluster instanceof CFSecJpaClusterTable)) {
			tableCluster = new CFSecJpaClusterTable(this);
		}
		if (tableISOCcy == null || !(tableISOCcy instanceof CFSecJpaISOCcyTable)) {
			tableISOCcy = new CFSecJpaISOCcyTable(this);
		}
		if (tableISOCtry == null || !(tableISOCtry instanceof CFSecJpaISOCtryTable)) {
			tableISOCtry = new CFSecJpaISOCtryTable(this);
		}
		if (tableISOCtryCcy == null || !(tableISOCtryCcy instanceof CFSecJpaISOCtryCcyTable)) {
			tableISOCtryCcy = new CFSecJpaISOCtryCcyTable(this);
		}
		if (tableISOCtryLang == null || !(tableISOCtryLang instanceof CFSecJpaISOCtryLangTable)) {
			tableISOCtryLang = new CFSecJpaISOCtryLangTable(this);
		}
		if (tableISOLang == null || !(tableISOLang instanceof CFSecJpaISOLangTable)) {
			tableISOLang = new CFSecJpaISOLangTable(this);
		}
		if (tableISOTZone == null || !(tableISOTZone instanceof CFSecJpaISOTZoneTable)) {
			tableISOTZone = new CFSecJpaISOTZoneTable(this);
		}
		if (tableSecUser == null || !(tableSecUser instanceof CFSecJpaSecUserTable)) {
			tableSecUser = new CFSecJpaSecUserTable(this);
		}
		if (tableSecUserPassword == null || !(tableSecUserPassword instanceof CFSecJpaSecUserPasswordTable)) {
			tableSecUserPassword = new CFSecJpaSecUserPasswordTable(this);
		}
		if (tableSecUserPWHistory == null || !(tableSecUserPWHistory instanceof CFSecJpaSecUserPWHistoryTable)) {
			tableSecUserPWHistory = new CFSecJpaSecUserPWHistoryTable(this);
		}
		if (tableSecSysGrp == null || !(tableSecSysGrp instanceof CFSecJpaSecSysGrpTable)) {
			tableSecSysGrp = new CFSecJpaSecSysGrpTable(this);
		}
		if (tableSecSysGrpInc == null || !(tableSecSysGrpInc instanceof CFSecJpaSecSysGrpIncTable)) {
			tableSecSysGrpInc = new CFSecJpaSecSysGrpIncTable(this);
		}
		if (tableSecSysGrpMemb == null || !(tableSecSysGrpMemb instanceof CFSecJpaSecSysGrpMembTable)) {
			tableSecSysGrpMemb = new CFSecJpaSecSysGrpMembTable(this);
		}
		if (tableSecClusGrp == null || !(tableSecClusGrp instanceof CFSecJpaSecClusGrpTable)) {
			tableSecClusGrp = new CFSecJpaSecClusGrpTable(this);
		}
		if (tableSecClusGrpInc == null || !(tableSecClusGrpInc instanceof CFSecJpaSecClusGrpIncTable)) {
			tableSecClusGrpInc = new CFSecJpaSecClusGrpIncTable(this);
		}
		if (tableSecClusGrpMemb == null || !(tableSecClusGrpMemb instanceof CFSecJpaSecClusGrpMembTable)) {
			tableSecClusGrpMemb = new CFSecJpaSecClusGrpMembTable(this);
		}
		if (tableSecTentGrp == null || !(tableSecTentGrp instanceof CFSecJpaSecTentGrpTable)) {
			tableSecTentGrp = new CFSecJpaSecTentGrpTable(this);
		}
		if (tableSecTentGrpInc == null || !(tableSecTentGrpInc instanceof CFSecJpaSecTentGrpIncTable)) {
			tableSecTentGrpInc = new CFSecJpaSecTentGrpIncTable(this);
		}
		if (tableSecTentGrpMemb == null || !(tableSecTentGrpMemb instanceof CFSecJpaSecTentGrpMembTable)) {
			tableSecTentGrpMemb = new CFSecJpaSecTentGrpMembTable(this);
		}
		if (tableSecSession == null || !(tableSecSession instanceof CFSecJpaSecSessionTable)) {
			tableSecSession = new CFSecJpaSecSessionTable(this);
		}
		if (tableSysCluster == null || !(tableSysCluster instanceof CFSecJpaSysClusterTable)) {
			tableSysCluster = new CFSecJpaSysClusterTable(this);
		}
		if (tableTenant == null || !(tableTenant instanceof CFSecJpaTenantTable)) {
			tableTenant = new CFSecJpaTenantTable(this);
		}
	}

	@Override		
	public ICFSecSchema getCFSecSchema() {
		return( ICFSecSchema.getBackingCFSec() );
	}

	@Override
	public void setCFSecSchema(ICFSecSchema schema) {
		ICFSecSchema.setBackingCFSec(schema);
		schema.wireRecConstructors();
	}

	public CFSecJpaHooksSchema getJpaHooksSchema() {
		return( cfsecJpaHooksSchema );
	}

	public void setJpaHooksSchema(CFSecJpaHooksSchema jpaHooksSchema) {
		cfsecJpaHooksSchema = jpaHooksSchema;
	}

	public CFSecJpaSchemaService getSchemaService() {
		return( getJpaHooksSchema().getSchemaService() );
	}

	public CFSecJpaSchema() {

		tableCluster = null;
		tableISOCcy = null;
		tableISOCtry = null;
		tableISOCtryCcy = null;
		tableISOCtryLang = null;
		tableISOLang = null;
		tableISOTZone = null;
		tableSecClusGrp = null;
		tableSecClusGrpInc = null;
		tableSecClusGrpMemb = null;
		tableSecSession = null;
		tableSecSysGrp = null;
		tableSecSysGrpInc = null;
		tableSecSysGrpMemb = null;
		tableSecTentGrp = null;
		tableSecTentGrpInc = null;
		tableSecTentGrpMemb = null;
		tableSecUser = null;
		tableSecUserPWHistory = null;
		tableSecUserPassword = null;
		tableSysCluster = null;
		tableTenant = null;

		factoryCluster = new CFSecJpaClusterDefaultFactory();
		factoryISOCcy = new CFSecJpaISOCcyDefaultFactory();
		factoryISOCtry = new CFSecJpaISOCtryDefaultFactory();
		factoryISOCtryCcy = new CFSecJpaISOCtryCcyDefaultFactory();
		factoryISOCtryLang = new CFSecJpaISOCtryLangDefaultFactory();
		factoryISOLang = new CFSecJpaISOLangDefaultFactory();
		factoryISOTZone = new CFSecJpaISOTZoneDefaultFactory();
		factorySecClusGrp = new CFSecJpaSecClusGrpDefaultFactory();
		factorySecClusGrpInc = new CFSecJpaSecClusGrpIncDefaultFactory();
		factorySecClusGrpMemb = new CFSecJpaSecClusGrpMembDefaultFactory();
		factorySecSession = new CFSecJpaSecSessionDefaultFactory();
		factorySecSysGrp = new CFSecJpaSecSysGrpDefaultFactory();
		factorySecSysGrpInc = new CFSecJpaSecSysGrpIncDefaultFactory();
		factorySecSysGrpMemb = new CFSecJpaSecSysGrpMembDefaultFactory();
		factorySecTentGrp = new CFSecJpaSecTentGrpDefaultFactory();
		factorySecTentGrpInc = new CFSecJpaSecTentGrpIncDefaultFactory();
		factorySecTentGrpMemb = new CFSecJpaSecTentGrpMembDefaultFactory();
		factorySecUser = new CFSecJpaSecUserDefaultFactory();
		factorySecUserPWHistory = new CFSecJpaSecUserPWHistoryDefaultFactory();
		factorySecUserPassword = new CFSecJpaSecUserPasswordDefaultFactory();
		factorySysCluster = new CFSecJpaSysClusterDefaultFactory();
		factoryTenant = new CFSecJpaTenantDefaultFactory();	}

	@Override
	public ICFSecSchema newSchema() {
		throw new CFLibMustOverrideException( getClass(), "newSchema" );
	}

	@Override
	public short nextISOCcyIdGen() {
		throw new CFLibNotImplementedYetException( getClass(), "nextISOCcyIdGen" );
	}

	@Override
	public short nextISOCtryIdGen() {
		throw new CFLibNotImplementedYetException( getClass(), "nextISOCtryIdGen" );
	}

	@Override
	public short nextISOLangIdGen() {
		throw new CFLibNotImplementedYetException( getClass(), "nextISOLangIdGen" );
	}

	@Override
	public short nextISOTZoneIdGen() {
		throw new CFLibNotImplementedYetException( getClass(), "nextISOTZoneIdGen" );
	}

	@Override
	public CFLibDbKeyHash256 nextClusterIdGen() {
		CFLibDbKeyHash256 retval = new CFLibDbKeyHash256(0);
		return( retval );
	}

	@Override
	public CFLibDbKeyHash256 nextSecSessionIdGen() {
		CFLibDbKeyHash256 retval = new CFLibDbKeyHash256(0);
		return( retval );
	}

	@Override
	public CFLibDbKeyHash256 nextSecUserIdGen() {
		CFLibDbKeyHash256 retval = new CFLibDbKeyHash256(0);
		return( retval );
	}

	@Override
	public CFLibDbKeyHash256 nextTenantIdGen() {
		CFLibDbKeyHash256 retval = new CFLibDbKeyHash256(0);
		return( retval );
	}

	@Override
	public CFLibDbKeyHash256 nextSecSysGrpIdGen() {
		CFLibDbKeyHash256 retval = new CFLibDbKeyHash256(0);
		return( retval );
	}

	@Override
	public CFLibDbKeyHash256 nextSecClusGrpIdGen() {
		CFLibDbKeyHash256 retval = new CFLibDbKeyHash256(0);
		return( retval );
	}

	@Override
	public CFLibDbKeyHash256 nextSecTentGrpIdGen() {
		CFLibDbKeyHash256 retval = new CFLibDbKeyHash256(0);
		return( retval );
	}

	public ICFSecClusterTable getTableCluster() {
		return( tableCluster );
	}

	public void setTableCluster( ICFSecClusterTable value ) {
		tableCluster = value;
	}

	public ICFSecClusterFactory getFactoryCluster() {
		return( factoryCluster );
	}

	public void setFactoryCluster( ICFSecClusterFactory value ) {
		factoryCluster = value;
	}

	public ICFSecISOCcyTable getTableISOCcy() {
		return( tableISOCcy );
	}

	public void setTableISOCcy( ICFSecISOCcyTable value ) {
		tableISOCcy = value;
	}

	public ICFSecISOCcyFactory getFactoryISOCcy() {
		return( factoryISOCcy );
	}

	public void setFactoryISOCcy( ICFSecISOCcyFactory value ) {
		factoryISOCcy = value;
	}

	public ICFSecISOCtryTable getTableISOCtry() {
		return( tableISOCtry );
	}

	public void setTableISOCtry( ICFSecISOCtryTable value ) {
		tableISOCtry = value;
	}

	public ICFSecISOCtryFactory getFactoryISOCtry() {
		return( factoryISOCtry );
	}

	public void setFactoryISOCtry( ICFSecISOCtryFactory value ) {
		factoryISOCtry = value;
	}

	public ICFSecISOCtryCcyTable getTableISOCtryCcy() {
		return( tableISOCtryCcy );
	}

	public void setTableISOCtryCcy( ICFSecISOCtryCcyTable value ) {
		tableISOCtryCcy = value;
	}

	public ICFSecISOCtryCcyFactory getFactoryISOCtryCcy() {
		return( factoryISOCtryCcy );
	}

	public void setFactoryISOCtryCcy( ICFSecISOCtryCcyFactory value ) {
		factoryISOCtryCcy = value;
	}

	public ICFSecISOCtryLangTable getTableISOCtryLang() {
		return( tableISOCtryLang );
	}

	public void setTableISOCtryLang( ICFSecISOCtryLangTable value ) {
		tableISOCtryLang = value;
	}

	public ICFSecISOCtryLangFactory getFactoryISOCtryLang() {
		return( factoryISOCtryLang );
	}

	public void setFactoryISOCtryLang( ICFSecISOCtryLangFactory value ) {
		factoryISOCtryLang = value;
	}

	public ICFSecISOLangTable getTableISOLang() {
		return( tableISOLang );
	}

	public void setTableISOLang( ICFSecISOLangTable value ) {
		tableISOLang = value;
	}

	public ICFSecISOLangFactory getFactoryISOLang() {
		return( factoryISOLang );
	}

	public void setFactoryISOLang( ICFSecISOLangFactory value ) {
		factoryISOLang = value;
	}

	public ICFSecISOTZoneTable getTableISOTZone() {
		return( tableISOTZone );
	}

	public void setTableISOTZone( ICFSecISOTZoneTable value ) {
		tableISOTZone = value;
	}

	public ICFSecISOTZoneFactory getFactoryISOTZone() {
		return( factoryISOTZone );
	}

	public void setFactoryISOTZone( ICFSecISOTZoneFactory value ) {
		factoryISOTZone = value;
	}

	public ICFSecSecClusGrpTable getTableSecClusGrp() {
		return( tableSecClusGrp );
	}

	public void setTableSecClusGrp( ICFSecSecClusGrpTable value ) {
		tableSecClusGrp = value;
	}

	public ICFSecSecClusGrpFactory getFactorySecClusGrp() {
		return( factorySecClusGrp );
	}

	public void setFactorySecClusGrp( ICFSecSecClusGrpFactory value ) {
		factorySecClusGrp = value;
	}

	public ICFSecSecClusGrpIncTable getTableSecClusGrpInc() {
		return( tableSecClusGrpInc );
	}

	public void setTableSecClusGrpInc( ICFSecSecClusGrpIncTable value ) {
		tableSecClusGrpInc = value;
	}

	public ICFSecSecClusGrpIncFactory getFactorySecClusGrpInc() {
		return( factorySecClusGrpInc );
	}

	public void setFactorySecClusGrpInc( ICFSecSecClusGrpIncFactory value ) {
		factorySecClusGrpInc = value;
	}

	public ICFSecSecClusGrpMembTable getTableSecClusGrpMemb() {
		return( tableSecClusGrpMemb );
	}

	public void setTableSecClusGrpMemb( ICFSecSecClusGrpMembTable value ) {
		tableSecClusGrpMemb = value;
	}

	public ICFSecSecClusGrpMembFactory getFactorySecClusGrpMemb() {
		return( factorySecClusGrpMemb );
	}

	public void setFactorySecClusGrpMemb( ICFSecSecClusGrpMembFactory value ) {
		factorySecClusGrpMemb = value;
	}

	public ICFSecSecSessionTable getTableSecSession() {
		return( tableSecSession );
	}

	public void setTableSecSession( ICFSecSecSessionTable value ) {
		tableSecSession = value;
	}

	public ICFSecSecSessionFactory getFactorySecSession() {
		return( factorySecSession );
	}

	public void setFactorySecSession( ICFSecSecSessionFactory value ) {
		factorySecSession = value;
	}

	public ICFSecSecSysGrpTable getTableSecSysGrp() {
		return( tableSecSysGrp );
	}

	public void setTableSecSysGrp( ICFSecSecSysGrpTable value ) {
		tableSecSysGrp = value;
	}

	public ICFSecSecSysGrpFactory getFactorySecSysGrp() {
		return( factorySecSysGrp );
	}

	public void setFactorySecSysGrp( ICFSecSecSysGrpFactory value ) {
		factorySecSysGrp = value;
	}

	public ICFSecSecSysGrpIncTable getTableSecSysGrpInc() {
		return( tableSecSysGrpInc );
	}

	public void setTableSecSysGrpInc( ICFSecSecSysGrpIncTable value ) {
		tableSecSysGrpInc = value;
	}

	public ICFSecSecSysGrpIncFactory getFactorySecSysGrpInc() {
		return( factorySecSysGrpInc );
	}

	public void setFactorySecSysGrpInc( ICFSecSecSysGrpIncFactory value ) {
		factorySecSysGrpInc = value;
	}

	public ICFSecSecSysGrpMembTable getTableSecSysGrpMemb() {
		return( tableSecSysGrpMemb );
	}

	public void setTableSecSysGrpMemb( ICFSecSecSysGrpMembTable value ) {
		tableSecSysGrpMemb = value;
	}

	public ICFSecSecSysGrpMembFactory getFactorySecSysGrpMemb() {
		return( factorySecSysGrpMemb );
	}

	public void setFactorySecSysGrpMemb( ICFSecSecSysGrpMembFactory value ) {
		factorySecSysGrpMemb = value;
	}

	public ICFSecSecTentGrpTable getTableSecTentGrp() {
		return( tableSecTentGrp );
	}

	public void setTableSecTentGrp( ICFSecSecTentGrpTable value ) {
		tableSecTentGrp = value;
	}

	public ICFSecSecTentGrpFactory getFactorySecTentGrp() {
		return( factorySecTentGrp );
	}

	public void setFactorySecTentGrp( ICFSecSecTentGrpFactory value ) {
		factorySecTentGrp = value;
	}

	public ICFSecSecTentGrpIncTable getTableSecTentGrpInc() {
		return( tableSecTentGrpInc );
	}

	public void setTableSecTentGrpInc( ICFSecSecTentGrpIncTable value ) {
		tableSecTentGrpInc = value;
	}

	public ICFSecSecTentGrpIncFactory getFactorySecTentGrpInc() {
		return( factorySecTentGrpInc );
	}

	public void setFactorySecTentGrpInc( ICFSecSecTentGrpIncFactory value ) {
		factorySecTentGrpInc = value;
	}

	public ICFSecSecTentGrpMembTable getTableSecTentGrpMemb() {
		return( tableSecTentGrpMemb );
	}

	public void setTableSecTentGrpMemb( ICFSecSecTentGrpMembTable value ) {
		tableSecTentGrpMemb = value;
	}

	public ICFSecSecTentGrpMembFactory getFactorySecTentGrpMemb() {
		return( factorySecTentGrpMemb );
	}

	public void setFactorySecTentGrpMemb( ICFSecSecTentGrpMembFactory value ) {
		factorySecTentGrpMemb = value;
	}

	public ICFSecSecUserTable getTableSecUser() {
		return( tableSecUser );
	}

	public void setTableSecUser( ICFSecSecUserTable value ) {
		tableSecUser = value;
	}

	public ICFSecSecUserFactory getFactorySecUser() {
		return( factorySecUser );
	}

	public void setFactorySecUser( ICFSecSecUserFactory value ) {
		factorySecUser = value;
	}

	public ICFSecSecUserPWHistoryTable getTableSecUserPWHistory() {
		return( tableSecUserPWHistory );
	}

	public void setTableSecUserPWHistory( ICFSecSecUserPWHistoryTable value ) {
		tableSecUserPWHistory = value;
	}

	public ICFSecSecUserPWHistoryFactory getFactorySecUserPWHistory() {
		return( factorySecUserPWHistory );
	}

	public void setFactorySecUserPWHistory( ICFSecSecUserPWHistoryFactory value ) {
		factorySecUserPWHistory = value;
	}

	public ICFSecSecUserPasswordTable getTableSecUserPassword() {
		return( tableSecUserPassword );
	}

	public void setTableSecUserPassword( ICFSecSecUserPasswordTable value ) {
		tableSecUserPassword = value;
	}

	public ICFSecSecUserPasswordFactory getFactorySecUserPassword() {
		return( factorySecUserPassword );
	}

	public void setFactorySecUserPassword( ICFSecSecUserPasswordFactory value ) {
		factorySecUserPassword = value;
	}

	public ICFSecSysClusterTable getTableSysCluster() {
		return( tableSysCluster );
	}

	public void setTableSysCluster( ICFSecSysClusterTable value ) {
		tableSysCluster = value;
	}

	public ICFSecSysClusterFactory getFactorySysCluster() {
		return( factorySysCluster );
	}

	public void setFactorySysCluster( ICFSecSysClusterFactory value ) {
		factorySysCluster = value;
	}

	public ICFSecTenantTable getTableTenant() {
		return( tableTenant );
	}

	public void setTableTenant( ICFSecTenantTable value ) {
		tableTenant = value;
	}

	public ICFSecTenantFactory getFactoryTenant() {
		return( factoryTenant );
	}

	public void setFactoryTenant( ICFSecTenantFactory value ) {
		factoryTenant = value;
	}

	/**
	 *	Get the Table Permissions interface for the schema.
	 *
	 *	@return	The Table Permissions interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public static ICFSecTablePerms getTablePerms() {
		return(tablePerms);
	}

	/**
	 *	Get the Table Permissions interface cast to this schema's implementation.
	 *
	 *	@return The Table Permissions interface for this schema.
	 */
	public static ICFSecTablePerms getCFSecTablePerms() {
		return (ICFSecTablePerms)getTablePerms();
	}

	/**
	 *	Set the Table Permissions interface for the schema.  All fractal subclasses of
	 *	the ICFSecTablePerms implement at least that interface plus their own
	 *	accessors.
	 *
	 *	@param	value	The Table Permissions interface to be used by the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public static void setTablePerms( ICFSecTablePerms value ) {
		if(value == null) {
			throw new CFLibNullArgumentException(CFSecJpaSchema.class, "setTablePerms", 1, "value");
		}
		tablePerms = value;
	}

	public void bootstrapSchema() {
		getSchemaService().bootstrapSchema();
	}
}

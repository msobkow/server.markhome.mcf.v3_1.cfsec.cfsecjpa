// Description: Java 25 Spring JPA Service for CFSec

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
import java.net.InetAddress;
import java.time.*;
import java.util.*;
import jakarta.persistence.*;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.xml.CFLibXmlUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;

/**
 *	Services for schema CFSec defined in server.markhome.mcf.v3_1.cfsec.cfsec.jpa
 *	using the CFSec*Repository objects to access the data directly, bypassing normal application security for the bootstrap and login processing.
 */
@Service("cfsec31JpaSchemaService")
public class CFSecJpaSchemaService {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;
	@Autowired
	private CFSecJpaClusterService clusterService;

	@Autowired
	private CFSecJpaTenantService tenantService;

	@Autowired
	private CFSecJpaISOCcyService isoccyService;

	@Autowired
	private CFSecJpaISOCtryService isoctryService;

	@Autowired
	private CFSecJpaISOCtryCcyService isoctryccyService;

	@Autowired
	private CFSecJpaISOCtryLangService isoctrylangService;

	@Autowired
	private CFSecJpaISOLangService isolangService;

	@Autowired
	private CFSecJpaISOTZoneService isotzoneService;

	@Autowired
	private CFSecJpaSecUserService secuserService;

	@Autowired
	private CFSecJpaSecUserPasswordService secuserpasswordService;

	@Autowired
	private CFSecJpaSecUserEMConfService secuseremconfService;

	@Autowired
	private CFSecJpaSecUserPWResetService secuserpwresetService;

	@Autowired
	private CFSecJpaSecUserPWHistoryService secuserpwhistoryService;

	@Autowired
	private CFSecJpaSecSysGrpService secsysgrpService;

	@Autowired
	private CFSecJpaSecSysGrpIncService secsysgrpincService;

	@Autowired
	private CFSecJpaSecSysGrpMembService secsysgrpmembService;

	@Autowired
	private CFSecJpaSecClusGrpService secclusgrpService;

	@Autowired
	private CFSecJpaSecClusGrpIncService secclusgrpincService;

	@Autowired
	private CFSecJpaSecClusGrpMembService secclusgrpmembService;

	@Autowired
	private CFSecJpaSecTentGrpService sectentgrpService;

	@Autowired
	private CFSecJpaSecTentGrpIncService sectentgrpincService;

	@Autowired
	private CFSecJpaSecTentGrpMembService sectentgrpmembService;

	@Autowired
	private CFSecJpaSecSessionService secsessionService;

	@Autowired
	private CFSecJpaSysClusterService sysclusterService;


	public void bootstrapSchema() {
		bootstrapSecurity();
		bootstrapAllTablesSecurity();
	}

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "$secdbschemaname$TransactionManager")
	public void bootstrapSecurity() {
		CFSecJpaSysCluster sysCluster;
		CFLibDbKeyHash256 systemClusterID;
		CFSecJpaCluster systemCluster;
		CFSecJpaSecUser sysAdminUser;
		CFLibDbKeyHash256 sysAdminUID;
		CFSecJpaSecSession bootstrapSession;
		CFLibDbKeyHash256 bootstrapSessionID;
		CFSecJpaTenant systemTenant;
		CFLibDbKeyHash256 systemTenantID;
		CFSecJpaSecSysGrp secSysGroupSysAdmin;
		CFLibDbKeyHash256 secSysGroupSysAdminID;
		CFSecJpaSecSysGrpMemb secSysGroupSysAdminMembSysadmin;
		CFSecJpaSecSysGrp secSysGroupSysClusAdmin;
		CFLibDbKeyHash256 secSysGroupSysClusAdminID;
		CFSecJpaSecClusGrpInc secSysGroupSysClusAdminIncSysclusadmin;
		CFSecJpaSecSysGrp secSysGroupSysTentAdmin;
		CFLibDbKeyHash256 secSysGroupSysTentAdminID;
		CFSecJpaSecTentGrpInc secSysGroupSysTentAdminIncSystentadmin;
		CFSecJpaSecClusGrp secSysClusGroupSysAdmin;
		CFLibDbKeyHash256 secSysClusGroupSysAdminID;
		CFSecJpaSecClusGrpInc secSysClusGroupSysAdminIncSysadmin;
		CFSecJpaSecTentGrp secSysTentGroupSysAdmin;
		CFLibDbKeyHash256 secSysTentGroupSysAdminID;
		CFSecJpaSecTentGrpInc secSysTentGroupSysAdminIncSysadmin;
		List<CFSecJpaSysCluster> sysClusters = sysclusterService.findAll();
		if (sysClusters != null && sysClusters.size() == 1) {
			sysCluster = sysClusters.get(0);
			systemClusterID = sysCluster.getRequiredClusterId();
			if(systemClusterID == null || systemClusterID.isNull()) {
				throw new CFLibNullArgumentException(getClass(), "bootstrapSchema", 0, "systemClusterID");
			}
			systemCluster = clusterService.find(systemClusterID);
			if (systemCluster == null) {
				throw new CFLibNullArgumentException(getClass(), "bootstrapSchema", 0, "systemCluster");
			}
			sysAdminUID = systemCluster.getCreatedByUserId();
			if (sysAdminUID == null || sysAdminUID.isNull()) {
				throw new CFLibNullArgumentException(getClass(), "bootstrapSchema", 0, "sysAdminUID");
			}
			sysAdminUser = secuserService.find(sysAdminUID);
			if( sysAdminUser == null) {
				throw new CFLibNullArgumentException(getClass(), "bootstrapSchema", 0, "sysAdminUser");
			}
			systemTenant = tenantService.findByUNameIdx(systemClusterID, "system");
			if( systemTenant == null) {
				systemTenantID = null;
				throw new CFLibNullArgumentException(getClass(), "bootstrapSchema", 0, "systemTenant");
			}
			else {
				systemTenantID = systemTenant.getPKey();
			}
			bootstrapSession = secsessionService.findByStartIdx(sysAdminUID, systemCluster.getCreatedAt());
			if (bootstrapSession == null) {
				List<CFSecJpaSecSession> sessions = secsessionService.findBySecUserIdx(sysAdminUID);
				if (sessions != null) {
					for (CFSecJpaSecSession cursess: sessions) {
						if (bootstrapSession == null || (bootstrapSession != null && (cursess.getRequiredStart().compareTo(bootstrapSession.getRequiredStart()) < 0))) {
							bootstrapSession = cursess;
						}
					}
				}
				if (bootstrapSession == null) {
					throw new CFLibNullArgumentException(getClass(), "bootstrapSchema", 0, "bootstrapSession");
				}
			}
			bootstrapSessionID = bootstrapSession.getPKey();

			secSysGroupSysAdmin = (CFSecJpaSecSysGrp)(secsysgrpService.findByUNameIdx( "sysadmin"));
			if (secSysGroupSysAdmin == null) {
				throw new CFLibNullArgumentException(getClass(), "bootstrapSchema", 0, "secSysGroupSysAdmin");
			}
			secSysGroupSysAdminID = secSysGroupSysAdmin.getRequiredSecSysGrpId();

			secSysGroupSysClusAdmin = (CFSecJpaSecSysGrp)(secsysgrpService.findByUNameIdx( "sysclusteradmin"));
			if (secSysGroupSysClusAdmin == null) {
				throw new CFLibNullArgumentException(getClass(), "bootstrapSchema", 0, "secSysGroupSysClusAdmin");
			}
			secSysGroupSysClusAdminID = secSysGroupSysClusAdmin.getRequiredSecSysGrpId();

			secSysGroupSysTentAdmin = (CFSecJpaSecSysGrp)(secsysgrpService.findByUNameIdx( "systenantadmin"));
			if (secSysGroupSysTentAdmin == null) {
				throw new CFLibNullArgumentException(getClass(), "bootstrapSchema", 0, "secSysGroupSysTentAdmin");
			}
			secSysGroupSysTentAdminID = secSysGroupSysTentAdmin.getRequiredSecSysGrpId();

			secSysGroupSysAdminMembSysadmin = (CFSecJpaSecSysGrpMemb)(secsysgrpmembService.find(secSysGroupSysAdminID, sysAdminUser.getRequiredLoginId()));
			if (secSysGroupSysAdminMembSysadmin == null) {
				throw new CFLibNullArgumentException(getClass(), "bootstrapSchema", 0, "secSysGroupSysAdminMembSysadmin");
			}

			secSysClusGroupSysAdmin = (CFSecJpaSecClusGrp)(secclusgrpService.findByUNameIdx(systemClusterID, "sysclusteradmin"));
			if (secSysClusGroupSysAdmin == null) {
				throw new CFLibNullArgumentException(getClass(), "bootstrapSchema", 0, "secSysClusGroupSysAdmin");
			}
			secSysClusGroupSysAdminID = secSysClusGroupSysAdmin.getRequiredSecClusGrpId();

			secSysClusGroupSysAdminIncSysadmin = (CFSecJpaSecClusGrpInc)(secclusgrpincService.find(secSysClusGroupSysAdminID, secSysGroupSysAdmin.getRequiredName()));
			if (secSysClusGroupSysAdminIncSysadmin == null) {
				throw new CFLibNullArgumentException(getClass(), "bootstrapSchema", 0, "secSysClusGroupSysAdminIncSysadmin");
			}

			secSysTentGroupSysAdmin = (CFSecJpaSecTentGrp)(sectentgrpService.findByUNameIdx(systemTenantID, "systenantadmin"));
			if (secSysTentGroupSysAdmin == null) {
				throw new CFLibNullArgumentException(getClass(), "bootstrapSchema", 0, "secSysTentGroupSysAdmin");
			}
			secSysTentGroupSysAdminID = secSysTentGroupSysAdmin.getRequiredSecTentGrpId();

			secSysTentGroupSysAdminIncSysadmin = (CFSecJpaSecTentGrpInc)(sectentgrpincService.find(secSysTentGroupSysAdminID, secSysGroupSysAdmin.getRequiredName()));
			if (secSysTentGroupSysAdminIncSysadmin == null) {
				throw new CFLibNullArgumentException(getClass(), "bootstrapSchema", 0, "secSysTentGroupSysAdminIncSysadmin");
			}
		}
		else {
			sysCluster = null;
			systemCluster = null;
			systemClusterID = null;
			sysAdminUID = null;
			sysAdminUser = null;
			bootstrapSession = null;
			bootstrapSessionID = null;
			systemTenant = null;
			systemTenantID = null;
			secSysGroupSysAdmin = null;
			secSysGroupSysAdminID = null;
			secSysGroupSysAdminMembSysadmin = null;
			secSysGroupSysClusAdmin = null;
			secSysGroupSysClusAdminID = null;
			secSysGroupSysTentAdmin = null;
			secSysGroupSysTentAdminID = null;
			secSysClusGroupSysAdmin = null;
			secSysClusGroupSysAdminID = null;
			secSysClusGroupSysAdminIncSysadmin = null;
			secSysTentGroupSysAdmin = null;
			secSysTentGroupSysAdminID = null;
			secSysTentGroupSysAdminIncSysadmin = null;
			secSysGroupSysClusAdmin = null;
			secSysGroupSysClusAdminID = null;
			secSysGroupSysClusAdminIncSysclusadmin = null;
			secSysGroupSysTentAdmin = null;
			secSysGroupSysTentAdminID = null;
			secSysGroupSysTentAdminIncSystentadmin = null;
		}
		LocalDateTime now = LocalDateTime.now();
		if (sysAdminUID == null || sysAdminUID.isNull()) {
			sysAdminUID = new CFLibDbKeyHash256(0);
		}
		if (bootstrapSessionID == null || bootstrapSessionID.isNull()) {
			bootstrapSessionID = new CFLibDbKeyHash256(0);
		}
		if (systemClusterID == null || systemClusterID.isNull()) {
			systemClusterID = new CFLibDbKeyHash256(0);
		}
		if (systemTenantID == null || systemTenantID.isNull()) {
			systemTenantID = new CFLibDbKeyHash256(0);
		}
		if (secSysGroupSysAdminID == null || secSysGroupSysAdminID.isNull()) {
			secSysGroupSysAdminID = new CFLibDbKeyHash256(0);
		}
		if (secSysGroupSysClusAdminID == null || secSysGroupSysClusAdminID.isNull()) {
			secSysGroupSysClusAdminID = new CFLibDbKeyHash256(0);
		}
		if (secSysGroupSysTentAdminID == null || secSysGroupSysTentAdminID.isNull()) {
			secSysGroupSysTentAdminID = new CFLibDbKeyHash256(0);
		}
		if (ICFSecSchema.getSysClusterId() == null || ICFSecSchema.getSysClusterId().isNull()) {
			ICFSecSchema.setSysClusterId(systemClusterID);
		}
		else if ( ! ICFSecSchema.getSysClusterId().equals( systemClusterID )) {
			throw new CFLibInvalidArgumentException(getClass(), "bootstrapSchema", "Previously set system cluster id disagrees with new system cluster id", "Previously set system cluster id disagrees with new system cluster id");
		}
		if (ICFSecSchema.getSysTenantId() == null || ICFSecSchema.getSysTenantId().isNull()) {
			ICFSecSchema.setSysTenantId(systemTenantID);
		}
		else if ( ! ICFSecSchema.getSysTenantId().equals( systemTenantID )) {
			throw new CFLibInvalidArgumentException(getClass(), "bootstrapSchema", "Previously set system tenant id disagrees with new system tenant id", "Previously set system tenant id disagrees with new system tenant id");
		}
		if (ICFSecSchema.getSysAdminId() == null || ICFSecSchema.getSysAdminId().isNull()) {
			ICFSecSchema.setSysAdminId(sysAdminUID);
		}
		else if ( ! ICFSecSchema.getSysAdminId().equals( sysAdminUID )) {
			throw new CFLibInvalidArgumentException(getClass(), "bootstrapSchema", "Previously set system admin id disagrees with new system admin id", "Previously set system admin id disagrees with new system admin id");
		}

		String fqdn;
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			fqdn = localHost.getCanonicalHostName();
		} catch (java.net.UnknownHostException e) {
			fqdn = "localhost";
		}

		if (systemCluster == null) {
			systemCluster = new CFSecJpaCluster();
			systemCluster.setRequiredRevision(1);
			systemCluster.setRequiredId(systemClusterID);
			systemCluster.setCreatedByUserId(sysAdminUID);
			systemCluster.setUpdatedByUserId(sysAdminUID);
			systemCluster.setCreatedAt(now);
			systemCluster.setUpdatedAt(now);
			systemCluster.setRequiredFullDomName(fqdn);
			systemCluster.setRequiredDescription("System cluster for " + fqdn);
			systemCluster = clusterService.create(systemCluster);
			systemClusterID = systemCluster.getPKey();
		}

		if (sysAdminUser == null) {
			sysAdminUser = new CFSecJpaSecUser();
			sysAdminUser.setRequiredRevision(1);
			sysAdminUser.setCreatedByUserId(sysAdminUID);
			sysAdminUser.setUpdatedByUserId(sysAdminUID);
			sysAdminUser.setCreatedAt(now);
			sysAdminUser.setUpdatedAt(now);
			sysAdminUser.setRequiredSecUserId(sysAdminUID);
			sysAdminUser.setRequiredLoginId("sysadmin");
			sysAdminUser.setRequiredEMailAddress("sysadmin@" + fqdn);
			sysAdminUser = secuserService.create(sysAdminUser);
			sysAdminUID = sysAdminUser.getPKey();
		}

		CFSecJpaSecUserPassword sysAdminUserPassword = secuserpasswordService.find(sysAdminUID);
		if (sysAdminUserPassword == null) {
			sysAdminUserPassword = new CFSecJpaSecUserPassword();
			sysAdminUserPassword.setRequiredRevision(1);
			sysAdminUserPassword.setPKey(sysAdminUID);
			sysAdminUserPassword.setRequiredContainerUser(sysAdminUID);
			sysAdminUserPassword.setRequiredPWSetStamp(now);
			sysAdminUserPassword.setRequiredPasswordHash(ICFSecSchema.getPasswordHash("ChangeOnInstall"));
		}

		if (systemTenant == null) {
			systemTenant = new CFSecJpaTenant();
			systemTenant.setRequiredRevision(1);
			systemTenant.setRequiredId(systemTenantID);
			systemTenant.setCreatedByUserId(sysAdminUID);
			systemTenant.setUpdatedByUserId(sysAdminUID);
			systemTenant.setCreatedAt(now);
			systemTenant.setUpdatedAt(now);
			systemTenant.setRequiredContainerCluster(systemClusterID);
			systemTenant.setRequiredTenantName("system");
			systemTenant = tenantService.create(systemTenant);
			systemTenantID = systemTenant.getPKey();
		}

		if (bootstrapSession == null) {
			bootstrapSession = new CFSecJpaSecSession();
			bootstrapSession.setRequiredRevision(1);
			bootstrapSession.setRequiredSecSessionId(bootstrapSessionID);
			bootstrapSession.setRequiredSecUserId(sysAdminUID);
			bootstrapSession.setOptionalSecProxyId(sysAdminUID);
			bootstrapSession.setRequiredStart(now);
			bootstrapSession.setOptionalFinish(null);
			bootstrapSession = secsessionService.create(bootstrapSession);
		}
			
		if (sysCluster == null) {
			sysCluster = new CFSecJpaSysCluster();
			sysCluster.setRequiredContainerCluster(systemClusterID);
			sysCluster = sysclusterService.create(sysCluster);
		}

		if (secSysGroupSysAdmin == null) {
			secSysGroupSysAdmin = new CFSecJpaSecSysGrp();
			secSysGroupSysAdmin.setRequiredRevision(1);
			secSysGroupSysAdmin.setCreatedAt(now);
			secSysGroupSysAdmin.setCreatedByUserId(sysAdminUID);
			secSysGroupSysAdmin.setUpdatedAt(now);
			secSysGroupSysAdmin.setUpdatedByUserId(sysAdminUID);
			secSysGroupSysAdmin.setRequiredName("sysadmin");
			secSysGroupSysAdmin.setRequiredSecLevel(ICFSecSchema.SecLevelEnum.System);
			secSysGroupSysAdmin.setRequiredSecSysGrpId(secSysGroupSysAdminID);
			secSysGroupSysAdmin = (CFSecJpaSecSysGrp)(secsysgrpService.create(secSysGroupSysAdmin));
			secSysGroupSysAdminID = secSysGroupSysAdmin.getRequiredSecSysGrpId();
		}

		if (secSysGroupSysAdminMembSysadmin == null) {
			secSysGroupSysAdminMembSysadmin = new CFSecJpaSecSysGrpMemb();
			secSysGroupSysAdminMembSysadmin.setRequiredRevision(1);
			secSysGroupSysAdminMembSysadmin.setRequiredContainerGroup(secSysGroupSysAdminID);
			secSysGroupSysAdminMembSysadmin.setRequiredParentUser(sysAdminUser.getRequiredLoginId());
			secSysGroupSysAdminMembSysadmin = (CFSecJpaSecSysGrpMemb)(secsysgrpmembService.create(secSysGroupSysAdminMembSysadmin));
		}

		if (secSysGroupSysClusAdmin == null) {
			secSysGroupSysClusAdmin = new CFSecJpaSecSysGrp();
			secSysGroupSysClusAdmin.setRequiredRevision(1);
			secSysGroupSysClusAdmin.setCreatedAt(now);
			secSysGroupSysClusAdmin.setCreatedByUserId(sysAdminUID);
			secSysGroupSysClusAdmin.setUpdatedAt(now);
			secSysGroupSysClusAdmin.setUpdatedByUserId(sysAdminUID);
			secSysGroupSysClusAdmin.setRequiredName("sysclusteradmin");
			secSysGroupSysClusAdmin.setRequiredSecLevel(ICFSecSchema.SecLevelEnum.Cluster);
			secSysGroupSysClusAdmin.setRequiredSecSysGrpId(secSysGroupSysClusAdminID);
			secSysGroupSysClusAdmin = (CFSecJpaSecSysGrp)(secsysgrpService.create(secSysGroupSysClusAdmin));
			secSysGroupSysClusAdminID = secSysGroupSysClusAdmin.getRequiredSecSysGrpId();
		}

		if (secSysGroupSysTentAdmin == null) {
			secSysGroupSysTentAdmin = new CFSecJpaSecSysGrp();
			secSysGroupSysTentAdmin.setRequiredRevision(1);
			secSysGroupSysTentAdmin.setCreatedAt(now);
			secSysGroupSysTentAdmin.setCreatedByUserId(sysAdminUID);
			secSysGroupSysTentAdmin.setUpdatedAt(now);
			secSysGroupSysTentAdmin.setUpdatedByUserId(sysAdminUID);
			secSysGroupSysTentAdmin.setRequiredName("systenantadmin");
			secSysGroupSysTentAdmin.setRequiredSecLevel(ICFSecSchema.SecLevelEnum.Tenant);
			secSysGroupSysTentAdmin.setRequiredSecSysGrpId(secSysGroupSysTentAdminID);
			secSysGroupSysTentAdmin = (CFSecJpaSecSysGrp)(secsysgrpService.create(secSysGroupSysTentAdmin));
			secSysGroupSysTentAdminID = secSysGroupSysTentAdmin.getRequiredSecSysGrpId();
		}

		if (secSysClusGroupSysAdmin == null) {
			secSysClusGroupSysAdmin = new CFSecJpaSecClusGrp();
			secSysClusGroupSysAdmin.setCreatedAt(now);
			secSysClusGroupSysAdmin.setCreatedByUserId(sysAdminUID);
			secSysClusGroupSysAdmin.setUpdatedAt(now);
			secSysClusGroupSysAdmin.setUpdatedByUserId(sysAdminUID);
			secSysClusGroupSysAdmin.setRequiredName("sysclusteradmin");
			secSysClusGroupSysAdmin.setRequiredOwnerCluster(systemClusterID);
			secSysClusGroupSysAdmin.setRequiredRevision(1);
			secSysClusGroupSysAdmin = (CFSecJpaSecClusGrp)(secclusgrpService.create(secSysClusGroupSysAdmin));
			secSysClusGroupSysAdminID = secSysClusGroupSysAdmin.getRequiredSecClusGrpId();
		}

		if (secSysClusGroupSysAdminIncSysadmin == null) {
			secSysClusGroupSysAdminIncSysadmin = new CFSecJpaSecClusGrpInc();
			secSysClusGroupSysAdminIncSysadmin.setRequiredContainerGroup(secSysClusGroupSysAdminID);
			secSysClusGroupSysAdminIncSysadmin.setCreatedAt(now);
			secSysClusGroupSysAdminIncSysadmin.setCreatedByUserId(sysAdminUID);
			secSysClusGroupSysAdminIncSysadmin.setUpdatedAt(now);
			secSysClusGroupSysAdminIncSysadmin.setUpdatedByUserId(sysAdminUID);
			secSysClusGroupSysAdminIncSysadmin.setRequiredParentSubGroup(secSysGroupSysAdmin.getRequiredName());
			secSysClusGroupSysAdminIncSysadmin.setRequiredRevision(1);
			secSysClusGroupSysAdminIncSysadmin = (CFSecJpaSecClusGrpInc)(secclusgrpincService.create(secSysClusGroupSysAdminIncSysadmin));
		}

		if (secSysTentGroupSysAdmin == null) {
			secSysTentGroupSysAdmin = new CFSecJpaSecTentGrp();
			secSysTentGroupSysAdmin.setCreatedAt(now);
			secSysTentGroupSysAdmin.setCreatedByUserId(sysAdminUID);
			secSysTentGroupSysAdmin.setUpdatedAt(now);
			secSysTentGroupSysAdmin.setUpdatedByUserId(sysAdminUID);
			secSysTentGroupSysAdmin.setRequiredName("sysclusteradmin");
			secSysTentGroupSysAdmin.setRequiredOwnerTenant(systemTenantID);
			secSysTentGroupSysAdmin.setRequiredRevision(1);
			secSysTentGroupSysAdmin = (CFSecJpaSecTentGrp)(sectentgrpService.create(secSysTentGroupSysAdmin));
			secSysTentGroupSysAdminID = secSysTentGroupSysAdmin.getRequiredSecTentGrpId();
		}

		if (secSysTentGroupSysAdminIncSysadmin == null) {
			secSysTentGroupSysAdminIncSysadmin = new CFSecJpaSecTentGrpInc();
			secSysTentGroupSysAdminIncSysadmin.setRequiredContainerGroup(secSysTentGroupSysAdminID);
			secSysTentGroupSysAdminIncSysadmin.setCreatedAt(now);
			secSysTentGroupSysAdminIncSysadmin.setCreatedByUserId(sysAdminUID);
			secSysTentGroupSysAdminIncSysadmin.setUpdatedAt(now);
			secSysTentGroupSysAdminIncSysadmin.setUpdatedByUserId(sysAdminUID);
			secSysTentGroupSysAdminIncSysadmin.setRequiredParentSubGroup(secSysGroupSysAdmin.getRequiredName());
			secSysTentGroupSysAdminIncSysadmin.setRequiredRevision(1);
			secSysTentGroupSysAdminIncSysadmin = (CFSecJpaSecTentGrpInc)(sectentgrpincService.create(secSysTentGroupSysAdminIncSysadmin));
		}

		if (bootstrapSession != null && bootstrapSessionID != null && !bootstrapSessionID.isNull() && bootstrapSession.getOptionalFinish() == null) {
			bootstrapSession.setOptionalFinish(LocalDateTime.now());
			bootstrapSession = secsessionService.update(bootstrapSession);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "$secdbschemaname$TransactionManager")
	public void bootstrapAllTablesSecurity() {
		LocalDateTime now = LocalDateTime.now();
		ICFSecSecSession bootstrapSession;
		CFLibDbKeyHash256 bootstrapSessionID = new CFLibDbKeyHash256(0);

		ICFSecAuthorization auth = new CFSecAuthorization();
		auth.setAuthUuid6(CFLibUuid6.generateUuid6());
		auth.setSecClusterId(ICFSecSchema.getSysClusterId());
		auth.setSecTenantId(ICFSecSchema.getSysTenantId());
		auth.setSecSessionId(bootstrapSessionID);

//ICFSecSchema.getSysTenantId(), ICFSecSchema.getSysAdminId()
		bootstrapSession = ICFSecSchema.getBackingCFSec().getFactorySecSession().newRec();
		bootstrapSession.setRequiredRevision(1);
		bootstrapSession.setRequiredSecSessionId(bootstrapSessionID);
		bootstrapSession.setRequiredSecUserId(ICFSecSchema.getSysAdminId());
		bootstrapSession.setOptionalSecProxyId(ICFSecSchema.getSysAdminId());
		bootstrapSession.setRequiredStart(now);
		bootstrapSession.setOptionalFinish(null);
		bootstrapSession = ICFSecSchema.getBackingCFSec().getTableSecSession().createSecSession(auth, bootstrapSession);
		bootstrapSessionID = bootstrapSession.getRequiredSecSessionId();

		ICFSecSecSysGrp secSysGroupSysAdmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx( auth, "sysadmin");
		if (secSysGroupSysAdmin == null) {
			throw new CFLibNullArgumentException(getClass(), "bootstrapAllTablesSecurity", 0, "secSysGroupSysAdmin");
		}

		ICFSecSecClusGrp secSysClusGroupSysAdmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "sysclusteradmin");
		if (secSysClusGroupSysAdmin == null) {
			throw new CFLibNullArgumentException(getClass(), "bootstrapAllTablesSecurity", 0, "secSysClusGroupSysAdmin");
		}

		ICFSecSecTentGrp secSysTentGroupSysAdmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysTenantId(), "systenantadmin");
		if (secSysTentGroupSysAdmin == null) {
			throw new CFLibNullArgumentException(getClass(), "bootstrapAllTablesSecurity", 0, "secSysTentGroupSysAdmin");
		}

		bootstrapTableSecurity(auth, now, "Cluster", true, false, "Global", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "Tenant", true, false, "System", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ISOCcy", true, false, "Global", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ISOCtry", true, false, "Global", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ISOCtryCcy", true, false, "Global", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ISOCtryLang", true, false, "Global", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ISOLang", true, false, "Global", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "ISOTZone", true, false, "Global", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SecUser", true, false, "System", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SecUserPassword", false, false, "System", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SecUserEMConf", true, false, "System", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SecUserPWReset", true, false, "System", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SecUserPWHistory", false, false, "System", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SecSysGrp", true, false, "System", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SecSysGrpInc", true, false, "System", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SecSysGrpMemb", true, false, "Cluster", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SecClusGrp", true, false, "Cluster", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SecClusGrpInc", true, false, "Cluster", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SecClusGrpMemb", true, false, "Cluster", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SecTentGrp", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SecTentGrpInc", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SecTentGrpMemb", true, false, "Tenant", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SecSession", false, false, "System", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		bootstrapTableSecurity(auth, now, "SysCluster", false, false, "System", secSysGroupSysAdmin, secSysClusGroupSysAdmin, secSysTentGroupSysAdmin);
		if (bootstrapSession != null && bootstrapSessionID != null && !bootstrapSessionID.isNull() && bootstrapSession.getOptionalFinish() == null) {
			bootstrapSession.setOptionalFinish(LocalDateTime.now());
			bootstrapSession = ICFSecSchema.getBackingCFSec().getTableSecSession().updateSecSession(auth, bootstrapSession);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "$secdbschemaname$TransactionManager")
	public void bootstrapTableSecurity(ICFSecAuthorization auth,
		LocalDateTime now,
		String tableName,
		boolean hasHistory,
		boolean isMutable,
		String secScope,
		ICFSecSecSysGrp secSysGroupSysAdmin,
		ICFSecSecClusGrp secSysClusGroupSysAdmin,
		ICFSecSecTentGrp secSysTentGroupSysAdmin )
	{
		ICFSecSchema.SecLevelEnum level;
		if (secScope.equalsIgnoreCase("global")) {
			level = ICFSecSchema.SecLevelEnum.Global;
		}
		else if (secScope.toLowerCase().startsWith("cluster")) {
			level = ICFSecSchema.SecLevelEnum.Cluster;
		}
		else if (secScope.toLowerCase().startsWith("tenant")) {
			level = ICFSecSchema.SecLevelEnum.Tenant;
		}
		else {
			level = ICFSecSchema.SecLevelEnum.System;
		}
			
		String lowerTableName = tableName.toLowerCase();
		String createPermName = "create" + lowerTableName;
		String readPermName = "read" + lowerTableName;
		String updatePermName = "update" + lowerTableName;
		String deletePermName = "delete" + lowerTableName;
		String restorePermName = "restore" + lowerTableName;
		String mutatePermName = "mutate" + lowerTableName;
		String sysadminGroup = secSysGroupSysAdmin.getRequiredName();
		String sysclusadminGroup = secSysClusGroupSysAdmin.getRequiredName();
		String systentadminGroup = secSysTentGroupSysAdmin.getRequiredName();

		ICFSecSecSysGrp secGroupCreate;
		CFLibDbKeyHash256 secGroupCreateID;
		ICFSecSecSysGrpInc secGroupCreateIncSysadmin;
		ICFSecSecSysGrp secGroupRead;
		CFLibDbKeyHash256 secGroupReadID;
		ICFSecSecSysGrpInc secGroupReadIncSysadmin;
		ICFSecSecSysGrp secGroupUpdate;
		CFLibDbKeyHash256 secGroupUpdateID;
		ICFSecSecSysGrpInc secGroupUpdateIncSysadmin;
		ICFSecSecSysGrp secGroupDelete;
		CFLibDbKeyHash256 secGroupDeleteID;
		ICFSecSecSysGrpInc secGroupDeleteIncSysadmin;
		ICFSecSecSysGrp secGroupRestore;
		CFLibDbKeyHash256 secGroupRestoreID;
		ICFSecSecSysGrpInc secGroupRestoreIncSysadmin;
		ICFSecSecSysGrp secGroupMutate;
		CFLibDbKeyHash256 secGroupMutateID;
		ICFSecSecSysGrpInc secGroupMutateIncSysadmin;

		ICFSecSecClusGrp csecGroupCreate;
		CFLibDbKeyHash256 csecGroupCreateID;
		ICFSecSecClusGrpInc csecGroupCreateIncSystentadmin;
		ICFSecSecClusGrp csecGroupRead;
		CFLibDbKeyHash256 csecGroupReadID;
		ICFSecSecClusGrpInc csecGroupReadIncSystentadmin;
		ICFSecSecClusGrp csecGroupUpdate;
		CFLibDbKeyHash256 csecGroupUpdateID;
		ICFSecSecClusGrpInc csecGroupUpdateIncSystentadmin;
		ICFSecSecClusGrp csecGroupDelete;
		CFLibDbKeyHash256 csecGroupDeleteID;
		ICFSecSecClusGrpInc csecGroupDeleteIncSystentadmin;
		ICFSecSecClusGrp csecGroupRestore;
		CFLibDbKeyHash256 csecGroupRestoreID;
		ICFSecSecClusGrpInc csecGroupRestoreIncSystentadmin;
		ICFSecSecClusGrp csecGroupMutate;
		CFLibDbKeyHash256 csecGroupMutateID;
		ICFSecSecClusGrpInc csecGroupMutateIncSystentadmin;
		
		ICFSecSecTentGrp tsecGroupCreate;
		CFLibDbKeyHash256 tsecGroupCreateID;
		ICFSecSecTentGrpInc tsecGroupCreateIncSystentadmin;
		ICFSecSecTentGrp tsecGroupRead;
		CFLibDbKeyHash256 tsecGroupReadID;
		ICFSecSecTentGrpInc tsecGroupReadIncSystentadmin;
		ICFSecSecTentGrp tsecGroupUpdate;
		CFLibDbKeyHash256 tsecGroupUpdateID;
		ICFSecSecTentGrpInc tsecGroupUpdateIncSystentadmin;
		ICFSecSecTentGrp tsecGroupDelete;
		CFLibDbKeyHash256 tsecGroupDeleteID;
		ICFSecSecTentGrpInc tsecGroupDeleteIncSystentadmin;
		ICFSecSecTentGrp tsecGroupRestore;
		CFLibDbKeyHash256 tsecGroupRestoreID;
		ICFSecSecTentGrpInc tsecGroupRestoreIncSystentadmin;
		ICFSecSecTentGrp tsecGroupMutate;
		CFLibDbKeyHash256 tsecGroupMutateID;
		ICFSecSecTentGrpInc tsecGroupMutateIncSystentadmin;

		secGroupCreate = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx(auth, createPermName);
		if (secGroupCreate != null) {
			secGroupCreateID = secGroupCreate.getRequiredSecSysGrpId();
			secGroupCreateIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secGroupCreateID, sysadminGroup);
		}
		else {
			secGroupCreateID = null;
			secGroupCreateIncSysadmin = null;
		}

		secGroupRead = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx(auth, readPermName);
		if (secGroupRead != null) {
			secGroupReadID = secGroupRead.getRequiredSecSysGrpId();
			secGroupReadIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secGroupReadID, sysadminGroup);
		}
		else {
			secGroupReadID = null;
			secGroupReadIncSysadmin = null;
		}

		secGroupUpdate = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx(auth, updatePermName);
		if (secGroupUpdate != null) {
			secGroupUpdateID = secGroupUpdate.getRequiredSecSysGrpId();
			secGroupUpdateIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secGroupUpdateID, sysadminGroup);
		}
		else {
			secGroupUpdateID = null;
			secGroupUpdateIncSysadmin = null;
		}

		secGroupDelete = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx(auth, deletePermName);
		if (secGroupDelete != null) {
			secGroupDeleteID = secGroupDelete.getRequiredSecSysGrpId();
			secGroupDeleteIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secGroupDeleteID, sysadminGroup);
		}
		else {
			secGroupDeleteID = null;
			secGroupDeleteIncSysadmin = null;
		}
		
		if (hasHistory) {
			secGroupRestore = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx(auth, restorePermName);
			if (secGroupRestore != null) {
				secGroupRestoreID = secGroupRestore.getRequiredSecSysGrpId();
				secGroupRestoreIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secGroupRestoreID, sysadminGroup);
			}
			else {
				secGroupRestoreID = null;
				secGroupRestoreIncSysadmin = null;
			}
		}
		else {
			secGroupRestore = null;
			secGroupRestoreID = null;
			secGroupRestoreIncSysadmin = null;
		}
		
		if (isMutable) {
			secGroupMutate = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx(auth, mutatePermName);
			if (secGroupMutate != null) {
				secGroupMutateID = secGroupMutate.getRequiredSecSysGrpId();
				secGroupMutateIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secGroupMutateID, sysadminGroup);
			}
			else {
				secGroupMutateID = null;
				secGroupMutateIncSysadmin = null;
			}
		}
		else {
			secGroupMutate = null;
			secGroupMutateID = null;
			secGroupMutateIncSysadmin = null;
		}

		if (secGroupCreateID == null || secGroupCreateID.isNull()) {
			secGroupCreateID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadID == null || secGroupReadID.isNull()) {
			secGroupReadID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateID == null || secGroupUpdateID.isNull()) {
			secGroupUpdateID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteID == null || secGroupDeleteID.isNull()) {
			secGroupDeleteID = new CFLibDbKeyHash256(0);
		}
		if (hasHistory) {
			if (secGroupRestoreID == null || secGroupRestoreID.isNull()) {
				secGroupRestoreID = new CFLibDbKeyHash256(0);
			}
		}
		if (isMutable) {
			if (secGroupMutateID == null || secGroupMutateID.isNull()) {
				secGroupMutateID = new CFLibDbKeyHash256(0);
			}
		}

		if (secGroupCreate == null) {
			secGroupCreate = ICFSecSchema.getBackingCFSec().getFactorySecSysGrp().newRec();
			secGroupCreate.setRequiredRevision(1);
			secGroupCreate.setCreatedAt(now);
			secGroupCreate.setCreatedByUserId(auth.getSecUserId());
			secGroupCreate.setUpdatedAt(now);
			secGroupCreate.setUpdatedByUserId(auth.getSecUserId());
			secGroupCreate.setRequiredName(createPermName);
			secGroupCreate.setRequiredSecLevel(level);
			secGroupCreate.setRequiredSecSysGrpId(secGroupCreateID);
			secGroupCreate = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().createSecSysGrp(auth, secGroupCreate);
			secGroupCreateID = secGroupCreate.getRequiredSecSysGrpId();
		}

		if (secGroupCreateIncSysadmin == null) {
			secGroupCreateIncSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
			secGroupCreateIncSysadmin.setRequiredRevision(1);
			secGroupCreateIncSysadmin.setCreatedAt(now);
			secGroupCreateIncSysadmin.setCreatedByUserId(auth.getSecUserId());
			secGroupCreateIncSysadmin.setUpdatedAt(now);
			secGroupCreateIncSysadmin.setUpdatedByUserId(auth.getSecUserId());
			secGroupCreateIncSysadmin.setRequiredContainerGroup(secGroupCreateID);
			secGroupCreateIncSysadmin.setRequiredParentSubGroup(sysadminGroup);
			secGroupCreateIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupCreateIncSysadmin);
		}

		if (secGroupRead == null) {
			secGroupRead = ICFSecSchema.getBackingCFSec().getFactorySecSysGrp().newRec();
			secGroupRead.setRequiredRevision(1);
			secGroupRead.setCreatedAt(now);
			secGroupRead.setCreatedByUserId(auth.getSecUserId());
			secGroupRead.setUpdatedAt(now);
			secGroupRead.setUpdatedByUserId(auth.getSecUserId());
			secGroupRead.setRequiredName(readPermName);
			secGroupRead.setRequiredSecLevel(level);
			secGroupRead.setRequiredSecSysGrpId(secGroupReadID);
			secGroupRead = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().createSecSysGrp(auth, secGroupRead);
			secGroupReadID = secGroupRead.getRequiredSecSysGrpId();
		}

		if (secGroupReadIncSysadmin == null) {
			secGroupReadIncSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
			secGroupReadIncSysadmin.setRequiredRevision(1);
			secGroupReadIncSysadmin.setCreatedAt(now);
			secGroupReadIncSysadmin.setCreatedByUserId(auth.getSecUserId());
			secGroupReadIncSysadmin.setUpdatedAt(now);
			secGroupReadIncSysadmin.setUpdatedByUserId(auth.getSecUserId());
			secGroupReadIncSysadmin.setRequiredContainerGroup(secGroupReadID);
			secGroupReadIncSysadmin.setRequiredParentSubGroup(sysadminGroup);
			secGroupReadIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupReadIncSysadmin);
		}

		if (secGroupUpdate == null) {
			secGroupUpdate = ICFSecSchema.getBackingCFSec().getFactorySecSysGrp().newRec();
			secGroupUpdate.setRequiredRevision(1);
			secGroupUpdate.setCreatedAt(now);
			secGroupUpdate.setCreatedByUserId(auth.getSecUserId());
			secGroupUpdate.setUpdatedAt(now);
			secGroupUpdate.setUpdatedByUserId(auth.getSecUserId());
			secGroupUpdate.setRequiredName(updatePermName);
			secGroupUpdate.setRequiredSecLevel(level);
			secGroupUpdate.setRequiredSecSysGrpId(secGroupUpdateID);
			secGroupUpdate = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().createSecSysGrp(auth, secGroupUpdate);
			secGroupUpdateID = secGroupUpdate.getRequiredSecSysGrpId();
		}

		if (secGroupUpdateIncSysadmin == null) {
			secGroupUpdateIncSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
			secGroupUpdateIncSysadmin.setRequiredRevision(1);
			secGroupUpdateIncSysadmin.setCreatedAt(now);
			secGroupUpdateIncSysadmin.setCreatedByUserId(auth.getSecUserId());
			secGroupUpdateIncSysadmin.setUpdatedAt(now);
			secGroupUpdateIncSysadmin.setUpdatedByUserId(auth.getSecUserId());
			secGroupUpdateIncSysadmin.setRequiredContainerGroup(secGroupUpdateID);
			secGroupUpdateIncSysadmin.setRequiredParentSubGroup(sysadminGroup);
			secGroupUpdateIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupUpdateIncSysadmin);
		}

		if (secGroupDelete == null) {
			secGroupDelete = ICFSecSchema.getBackingCFSec().getFactorySecSysGrp().newRec();
			secGroupDelete.setRequiredRevision(1);
			secGroupDelete.setCreatedAt(now);
			secGroupDelete.setCreatedByUserId(auth.getSecUserId());
			secGroupDelete.setUpdatedAt(now);
			secGroupDelete.setUpdatedByUserId(auth.getSecUserId());
			secGroupDelete.setRequiredName(deletePermName);
			secGroupDelete.setRequiredSecLevel(level);
			secGroupDelete.setRequiredSecSysGrpId(secGroupDeleteID);
			secGroupDelete = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().createSecSysGrp(auth, secGroupDelete);
			secGroupDeleteID = secGroupDelete.getRequiredSecSysGrpId();
		}

		if (secGroupDeleteIncSysadmin == null) {
			secGroupDeleteIncSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
			secGroupDeleteIncSysadmin.setRequiredRevision(1);
			secGroupDeleteIncSysadmin.setCreatedAt(now);
			secGroupDeleteIncSysadmin.setCreatedByUserId(auth.getSecUserId());
			secGroupDeleteIncSysadmin.setUpdatedAt(now);
			secGroupDeleteIncSysadmin.setUpdatedByUserId(auth.getSecUserId());
			secGroupDeleteIncSysadmin.setRequiredContainerGroup(secGroupDeleteID);
			secGroupDeleteIncSysadmin.setRequiredParentSubGroup(sysadminGroup);
			secGroupDeleteIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupDeleteIncSysadmin);
		}
		
		if (hasHistory) {
			if (secGroupRestore == null) {
				secGroupRestore = ICFSecSchema.getBackingCFSec().getFactorySecSysGrp().newRec();
				secGroupRestore.setRequiredRevision(1);
				secGroupRestore.setCreatedAt(now);
				secGroupRestore.setCreatedByUserId(auth.getSecUserId());
				secGroupRestore.setUpdatedAt(now);
				secGroupRestore.setUpdatedByUserId(auth.getSecUserId());
				secGroupRestore.setRequiredName(restorePermName);
				secGroupRestore.setRequiredSecLevel(level);
				secGroupRestore.setRequiredSecSysGrpId(secGroupRestoreID);
				secGroupRestore = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().createSecSysGrp(auth, secGroupRestore);
				secGroupRestoreID = secGroupRestore.getRequiredSecSysGrpId();
			}

			if (secGroupRestoreIncSysadmin == null) {
				secGroupRestoreIncSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
				secGroupRestoreIncSysadmin.setRequiredRevision(1);
				secGroupRestoreIncSysadmin.setCreatedAt(now);
				secGroupRestoreIncSysadmin.setCreatedByUserId(auth.getSecUserId());
				secGroupRestoreIncSysadmin.setUpdatedAt(now);
				secGroupRestoreIncSysadmin.setUpdatedByUserId(auth.getSecUserId());
				secGroupRestoreIncSysadmin.setRequiredContainerGroup(secGroupRestoreID);
				secGroupRestoreIncSysadmin.setRequiredParentSubGroup(sysadminGroup);
				secGroupRestoreIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupRestoreIncSysadmin);
			}
		}
		
		if (isMutable) {
			if (secGroupMutate == null) {
				secGroupMutate = ICFSecSchema.getBackingCFSec().getFactorySecSysGrp().newRec();
				secGroupMutate.setRequiredRevision(1);
				secGroupMutate.setCreatedAt(now);
				secGroupMutate.setCreatedByUserId(auth.getSecUserId());
				secGroupMutate.setUpdatedAt(now);
				secGroupMutate.setUpdatedByUserId(auth.getSecUserId());
				secGroupMutate.setRequiredName(mutatePermName);
				secGroupMutate.setRequiredSecLevel(level);
				secGroupMutate.setRequiredSecSysGrpId(secGroupMutateID);
				secGroupMutate = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().createSecSysGrp(auth, secGroupMutate);
				secGroupMutateID = secGroupMutate.getRequiredSecSysGrpId();
			}

			if (secGroupMutateIncSysadmin == null) {
				secGroupMutateIncSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
				secGroupMutateIncSysadmin.setRequiredRevision(1);
				secGroupMutateIncSysadmin.setCreatedAt(now);
				secGroupMutateIncSysadmin.setCreatedByUserId(auth.getSecUserId());
				secGroupMutateIncSysadmin.setUpdatedAt(now);
				secGroupMutateIncSysadmin.setUpdatedByUserId(auth.getSecUserId());
				secGroupMutateIncSysadmin.setRequiredContainerGroup(secGroupMutateID);
				secGroupMutateIncSysadmin.setRequiredParentSubGroup(sysadminGroup);
				secGroupMutateIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupMutateIncSysadmin);
			}
		}
		
		if (level == ICFSecSchema.SecLevelEnum.Cluster ) {
		}
		else if (level == ICFSecSchema.SecLevelEnum.Tenant ) {
		}
	}		


}

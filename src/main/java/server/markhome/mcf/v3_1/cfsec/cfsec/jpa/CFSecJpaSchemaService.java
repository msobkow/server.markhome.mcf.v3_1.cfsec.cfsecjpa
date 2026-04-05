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
		ICFSecSecClusGrpInc csecGroupCreateIncSysclusadmin;
		ICFSecSecClusGrp csecGroupRead;
		CFLibDbKeyHash256 csecGroupReadID;
		ICFSecSecClusGrpInc csecGroupReadIncSysclusadmin;
		ICFSecSecClusGrp csecGroupUpdate;
		CFLibDbKeyHash256 csecGroupUpdateID;
		ICFSecSecClusGrpInc csecGroupUpdateIncSysclusadmin;
		ICFSecSecClusGrp csecGroupDelete;
		CFLibDbKeyHash256 csecGroupDeleteID;
		ICFSecSecClusGrpInc csecGroupDeleteIncSysclusadmin;
		ICFSecSecClusGrp csecGroupRestore;
		CFLibDbKeyHash256 csecGroupRestoreID;
		ICFSecSecClusGrpInc csecGroupRestoreIncSysclusadmin;
		ICFSecSecClusGrp csecGroupMutate;
		CFLibDbKeyHash256 csecGroupMutateID;
		ICFSecSecClusGrpInc csecGroupMutateIncSysclusadmin;
		
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
				secGroupMutateIncSysadmin.setRequiredParentSubGroup(sysclusadminGroup);
				secGroupMutateIncSysadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupMutateIncSysadmin);
			}
		}
		
		if (level == ICFSecSchema.SecLevelEnum.Cluster ) {
			csecGroupCreate = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), createPermName);
			if (csecGroupCreate != null) {
				csecGroupCreateID = csecGroupCreate.getRequiredSecClusGrpId();
				csecGroupCreateIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().readDerived(auth, csecGroupCreateID, sysclusadminGroup);
			}
			else {
				csecGroupCreateID = null;
				csecGroupCreateIncSysclusadmin = null;
			}

			csecGroupRead = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), readPermName);
			if (csecGroupRead != null) {
				csecGroupReadID = csecGroupRead.getRequiredSecClusGrpId();
				csecGroupReadIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().readDerived(auth, csecGroupReadID, sysclusadminGroup);
			}
			else {
				csecGroupReadID = null;
				csecGroupReadIncSysclusadmin = null;
			}

			csecGroupUpdate = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), updatePermName);
			if (csecGroupUpdate != null) {
				csecGroupUpdateID = csecGroupUpdate.getRequiredSecClusGrpId();
				csecGroupUpdateIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().readDerived(auth, csecGroupUpdateID, sysclusadminGroup);
			}
			else {
				csecGroupUpdateID = null;
				csecGroupUpdateIncSysclusadmin = null;
			}

			csecGroupDelete = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), deletePermName);
			if (csecGroupDelete != null) {
				csecGroupDeleteID = csecGroupDelete.getRequiredSecClusGrpId();
				csecGroupDeleteIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().readDerived(auth, csecGroupDeleteID, sysclusadminGroup);
			}
			else {
				csecGroupDeleteID = null;
				csecGroupDeleteIncSysclusadmin = null;
			}

			if (hasHistory) {
				csecGroupRestore = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), restorePermName);
				if (csecGroupRestore != null) {
					csecGroupRestoreID = csecGroupRestore.getRequiredSecClusGrpId();
					csecGroupRestoreIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().readDerived(auth, csecGroupRestoreID, sysclusadminGroup);
				}
				else {
					csecGroupRestoreID = null;
					csecGroupRestoreIncSysclusadmin = null;
				}
			}
			else {
				csecGroupRestore = null;
				csecGroupRestoreID = null;
				csecGroupRestoreIncSysclusadmin = null;
			}

			if (isMutable) {
				csecGroupMutate = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), mutatePermName);
				if (csecGroupMutate != null) {
					csecGroupMutateID = csecGroupMutate.getRequiredSecClusGrpId();
					csecGroupMutateIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().readDerived(auth, csecGroupMutateID, sysclusadminGroup);
				}
				else {
					csecGroupMutateID = null;
					csecGroupMutateIncSysclusadmin = null;
				}
			}
			else {
				csecGroupMutate = null;
				csecGroupMutateID = null;
				csecGroupMutateIncSysclusadmin = null;
			}

			if (csecGroupCreateID == null || csecGroupCreateID.isNull()) {
				csecGroupCreateID = new CFLibDbKeyHash256(0);
			}
			if (csecGroupReadID == null || csecGroupReadID.isNull()) {
				csecGroupReadID = new CFLibDbKeyHash256(0);
			}
			if (csecGroupUpdateID == null || csecGroupUpdateID.isNull()) {
				csecGroupUpdateID = new CFLibDbKeyHash256(0);
			}
			if (csecGroupDeleteID == null || csecGroupDeleteID.isNull()) {
				csecGroupDeleteID = new CFLibDbKeyHash256(0);
			}
			if (hasHistory) {
				if (csecGroupRestoreID == null || csecGroupRestoreID.isNull()) {
					csecGroupRestoreID = new CFLibDbKeyHash256(0);
				}
			}
			if (isMutable) {
				if (csecGroupMutateID == null || csecGroupMutateID.isNull()) {
					csecGroupMutateID = new CFLibDbKeyHash256(0);
				}
			}

			if (csecGroupCreate == null) {
				csecGroupCreate = ICFSecSchema.getBackingCFSec().getFactorySecClusGrp().newRec();
				csecGroupCreate.setRequiredRevision(1);
				csecGroupCreate.setCreatedAt(now);
				csecGroupCreate.setCreatedByUserId(auth.getSecUserId());
				csecGroupCreate.setUpdatedAt(now);
				csecGroupCreate.setUpdatedByUserId(auth.getSecUserId());
				csecGroupCreate.setRequiredName(createPermName);
				csecGroupCreate.setRequiredSecClusGrpId(csecGroupCreateID);
				csecGroupCreate = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().createSecClusGrp(auth, csecGroupCreate);
				csecGroupCreateID = csecGroupCreate.getRequiredSecClusGrpId();
			}

			if (csecGroupCreateIncSysclusadmin == null) {
				csecGroupCreateIncSysclusadmin = ICFSecSchema.getBackingCFSec().getFactorySecClusGrpInc().newRec();
				csecGroupCreateIncSysclusadmin.setRequiredRevision(1);
				csecGroupCreateIncSysclusadmin.setCreatedAt(now);
				csecGroupCreateIncSysclusadmin.setCreatedByUserId(auth.getSecUserId());
				csecGroupCreateIncSysclusadmin.setUpdatedAt(now);
				csecGroupCreateIncSysclusadmin.setUpdatedByUserId(auth.getSecUserId());
				csecGroupCreateIncSysclusadmin.setRequiredContainerGroup(csecGroupCreateID);
				csecGroupCreateIncSysclusadmin.setRequiredParentSubGroup(sysclusadminGroup);
				csecGroupCreateIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().createSecClusGrpInc(auth, csecGroupCreateIncSysclusadmin);
			}

			if (csecGroupRead == null) {
				csecGroupRead = ICFSecSchema.getBackingCFSec().getFactorySecClusGrp().newRec();
				csecGroupRead.setRequiredRevision(1);
				csecGroupRead.setCreatedAt(now);
				csecGroupRead.setCreatedByUserId(auth.getSecUserId());
				csecGroupRead.setUpdatedAt(now);
				csecGroupRead.setUpdatedByUserId(auth.getSecUserId());
				csecGroupRead.setRequiredName(readPermName);
				csecGroupRead.setRequiredSecClusGrpId(csecGroupReadID);
				csecGroupRead = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().createSecClusGrp(auth, csecGroupRead);
				csecGroupReadID = csecGroupRead.getRequiredSecClusGrpId();
			}

			if (csecGroupReadIncSysclusadmin == null) {
				csecGroupReadIncSysclusadmin = ICFSecSchema.getBackingCFSec().getFactorySecClusGrpInc().newRec();
				csecGroupReadIncSysclusadmin.setRequiredRevision(1);
				csecGroupReadIncSysclusadmin.setCreatedAt(now);
				csecGroupReadIncSysclusadmin.setCreatedByUserId(auth.getSecUserId());
				csecGroupReadIncSysclusadmin.setUpdatedAt(now);
				csecGroupReadIncSysclusadmin.setUpdatedByUserId(auth.getSecUserId());
				csecGroupReadIncSysclusadmin.setRequiredContainerGroup(csecGroupReadID);
				csecGroupReadIncSysclusadmin.setRequiredParentSubGroup(sysclusadminGroup);
				csecGroupReadIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().createSecClusGrpInc(auth, csecGroupReadIncSysclusadmin);
			}

			if (csecGroupUpdate == null) {
				csecGroupUpdate = ICFSecSchema.getBackingCFSec().getFactorySecClusGrp().newRec();
				csecGroupUpdate.setRequiredRevision(1);
				csecGroupUpdate.setCreatedAt(now);
				csecGroupUpdate.setCreatedByUserId(auth.getSecUserId());
				csecGroupUpdate.setUpdatedAt(now);
				csecGroupUpdate.setUpdatedByUserId(auth.getSecUserId());
				csecGroupUpdate.setRequiredName(updatePermName);
				csecGroupUpdate.setRequiredSecClusGrpId(csecGroupUpdateID);
				csecGroupUpdate = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().createSecClusGrp(auth, csecGroupUpdate);
				csecGroupUpdateID = csecGroupUpdate.getRequiredSecClusGrpId();
			}

			if (csecGroupUpdateIncSysclusadmin == null) {
				csecGroupUpdateIncSysclusadmin = ICFSecSchema.getBackingCFSec().getFactorySecClusGrpInc().newRec();
				csecGroupUpdateIncSysclusadmin.setRequiredRevision(1);
				csecGroupUpdateIncSysclusadmin.setCreatedAt(now);
				csecGroupUpdateIncSysclusadmin.setCreatedByUserId(auth.getSecUserId());
				csecGroupUpdateIncSysclusadmin.setUpdatedAt(now);
				csecGroupUpdateIncSysclusadmin.setUpdatedByUserId(auth.getSecUserId());
				csecGroupUpdateIncSysclusadmin.setRequiredContainerGroup(csecGroupUpdateID);
				csecGroupUpdateIncSysclusadmin.setRequiredParentSubGroup(sysclusadminGroup);
				csecGroupUpdateIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().createSecClusGrpInc(auth, csecGroupUpdateIncSysclusadmin);
			}

			if (csecGroupDelete == null) {
				csecGroupDelete = ICFSecSchema.getBackingCFSec().getFactorySecClusGrp().newRec();
				csecGroupDelete.setRequiredRevision(1);
				csecGroupDelete.setCreatedAt(now);
				csecGroupDelete.setCreatedByUserId(auth.getSecUserId());
				csecGroupDelete.setUpdatedAt(now);
				csecGroupDelete.setUpdatedByUserId(auth.getSecUserId());
				csecGroupDelete.setRequiredName(deletePermName);
				csecGroupDelete.setRequiredSecClusGrpId(csecGroupDeleteID);
				csecGroupDelete = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().createSecClusGrp(auth, csecGroupDelete);
				csecGroupDeleteID = csecGroupDelete.getRequiredSecClusGrpId();
			}

			if (csecGroupDeleteIncSysclusadmin == null) {
				csecGroupDeleteIncSysclusadmin = ICFSecSchema.getBackingCFSec().getFactorySecClusGrpInc().newRec();
				csecGroupDeleteIncSysclusadmin.setRequiredRevision(1);
				csecGroupDeleteIncSysclusadmin.setCreatedAt(now);
				csecGroupDeleteIncSysclusadmin.setCreatedByUserId(auth.getSecUserId());
				csecGroupDeleteIncSysclusadmin.setUpdatedAt(now);
				csecGroupDeleteIncSysclusadmin.setUpdatedByUserId(auth.getSecUserId());
				csecGroupDeleteIncSysclusadmin.setRequiredContainerGroup(csecGroupDeleteID);
				csecGroupDeleteIncSysclusadmin.setRequiredParentSubGroup(sysclusadminGroup);
				csecGroupDeleteIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().createSecClusGrpInc(auth, csecGroupDeleteIncSysclusadmin);
			}

			if (hasHistory) {
				if (csecGroupRestore == null) {
					csecGroupRestore = ICFSecSchema.getBackingCFSec().getFactorySecClusGrp().newRec();
					csecGroupRestore.setRequiredRevision(1);
					csecGroupRestore.setCreatedAt(now);
					csecGroupRestore.setCreatedByUserId(auth.getSecUserId());
					csecGroupRestore.setUpdatedAt(now);
					csecGroupRestore.setUpdatedByUserId(auth.getSecUserId());
					csecGroupRestore.setRequiredName(restorePermName);
					csecGroupRestore.setRequiredSecClusGrpId(csecGroupRestoreID);
					csecGroupRestore = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().createSecClusGrp(auth, csecGroupRestore);
					csecGroupRestoreID = csecGroupRestore.getRequiredSecClusGrpId();
				}

				if (csecGroupRestoreIncSysclusadmin == null) {
					csecGroupRestoreIncSysclusadmin = ICFSecSchema.getBackingCFSec().getFactorySecClusGrpInc().newRec();
					csecGroupRestoreIncSysclusadmin.setRequiredRevision(1);
					csecGroupRestoreIncSysclusadmin.setCreatedAt(now);
					csecGroupRestoreIncSysclusadmin.setCreatedByUserId(auth.getSecUserId());
					csecGroupRestoreIncSysclusadmin.setUpdatedAt(now);
					csecGroupRestoreIncSysclusadmin.setUpdatedByUserId(auth.getSecUserId());
					csecGroupRestoreIncSysclusadmin.setRequiredContainerGroup(csecGroupRestoreID);
					csecGroupRestoreIncSysclusadmin.setRequiredParentSubGroup(sysclusadminGroup);
					csecGroupRestoreIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().createSecClusGrpInc(auth, csecGroupRestoreIncSysclusadmin);
				}
			}

			if (isMutable) {
				if (csecGroupMutate == null) {
					csecGroupMutate = ICFSecSchema.getBackingCFSec().getFactorySecClusGrp().newRec();
					csecGroupMutate.setRequiredRevision(1);
					csecGroupMutate.setCreatedAt(now);
					csecGroupMutate.setCreatedByUserId(auth.getSecUserId());
					csecGroupMutate.setUpdatedAt(now);
					csecGroupMutate.setUpdatedByUserId(auth.getSecUserId());
					csecGroupMutate.setRequiredName(mutatePermName);
					csecGroupMutate.setRequiredSecClusGrpId(csecGroupMutateID);
					csecGroupMutate = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().createSecClusGrp(auth, csecGroupMutate);
					csecGroupMutateID = csecGroupMutate.getRequiredSecClusGrpId();
				}

				if (csecGroupMutateIncSysclusadmin == null) {
					csecGroupMutateIncSysclusadmin = ICFSecSchema.getBackingCFSec().getFactorySecClusGrpInc().newRec();
					csecGroupMutateIncSysclusadmin.setRequiredRevision(1);
					csecGroupMutateIncSysclusadmin.setCreatedAt(now);
					csecGroupMutateIncSysclusadmin.setCreatedByUserId(auth.getSecUserId());
					csecGroupMutateIncSysclusadmin.setUpdatedAt(now);
					csecGroupMutateIncSysclusadmin.setUpdatedByUserId(auth.getSecUserId());
					csecGroupMutateIncSysclusadmin.setRequiredContainerGroup(csecGroupMutateID);
					csecGroupMutateIncSysclusadmin.setRequiredParentSubGroup(sysclusadminGroup);
					csecGroupMutateIncSysclusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().createSecClusGrpInc(auth, csecGroupMutateIncSysclusadmin);
				}
			}
		}
		else if (level == ICFSecSchema.SecLevelEnum.Tenant ) {
			tsecGroupCreate = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysTenantId(), createPermName);
			if (tsecGroupCreate != null) {
				tsecGroupCreateID = tsecGroupCreate.getRequiredSecTentGrpId();
				tsecGroupCreateIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().readDerived(auth, tsecGroupCreateID, systentadminGroup);
			}
			else {
				tsecGroupCreateID = null;
				tsecGroupCreateIncSystentadmin = null;
			}

			tsecGroupRead = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysTenantId(), readPermName);
			if (tsecGroupRead != null) {
				tsecGroupReadID = tsecGroupRead.getRequiredSecTentGrpId();
				tsecGroupReadIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().readDerived(auth, tsecGroupReadID, systentadminGroup);
			}
			else {
				tsecGroupReadID = null;
				tsecGroupReadIncSystentadmin = null;
			}

			tsecGroupUpdate = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysTenantId(), updatePermName);
			if (tsecGroupUpdate != null) {
				tsecGroupUpdateID = tsecGroupUpdate.getRequiredSecTentGrpId();
				tsecGroupUpdateIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().readDerived(auth, tsecGroupUpdateID, systentadminGroup);
			}
			else {
				tsecGroupUpdateID = null;
				tsecGroupUpdateIncSystentadmin = null;
			}

			tsecGroupDelete = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysTenantId(), deletePermName);
			if (tsecGroupDelete != null) {
				tsecGroupDeleteID = tsecGroupDelete.getRequiredSecTentGrpId();
				tsecGroupDeleteIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().readDerived(auth, tsecGroupDeleteID, systentadminGroup);
			}
			else {
				tsecGroupDeleteID = null;
				tsecGroupDeleteIncSystentadmin = null;
			}

			if (hasHistory) {
				tsecGroupRestore = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysTenantId(), restorePermName);
				if (tsecGroupRestore != null) {
					tsecGroupRestoreID = tsecGroupRestore.getRequiredSecTentGrpId();
					tsecGroupRestoreIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().readDerived(auth, tsecGroupRestoreID, systentadminGroup);
				}
				else {
					tsecGroupRestoreID = null;
					tsecGroupRestoreIncSystentadmin = null;
				}
			}
			else {
				tsecGroupRestore = null;
				tsecGroupRestoreID = null;
				tsecGroupRestoreIncSystentadmin = null;
			}

			if (isMutable) {
				tsecGroupMutate = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().readDerivedByUNameIdx(auth, ICFSecSchema.getSysTenantId(), mutatePermName);
				if (tsecGroupMutate != null) {
					tsecGroupMutateID = tsecGroupMutate.getRequiredSecTentGrpId();
					tsecGroupMutateIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().readDerived(auth, tsecGroupMutateID, systentadminGroup);
				}
				else {
					tsecGroupMutateID = null;
					tsecGroupMutateIncSystentadmin = null;
				}
			}
			else {
				tsecGroupMutate = null;
				tsecGroupMutateID = null;
				tsecGroupMutateIncSystentadmin = null;
			}

			if (tsecGroupCreateID == null || tsecGroupCreateID.isNull()) {
				tsecGroupCreateID = new CFLibDbKeyHash256(0);
			}
			if (tsecGroupReadID == null || tsecGroupReadID.isNull()) {
				tsecGroupReadID = new CFLibDbKeyHash256(0);
			}
			if (tsecGroupUpdateID == null || tsecGroupUpdateID.isNull()) {
				tsecGroupUpdateID = new CFLibDbKeyHash256(0);
			}
			if (tsecGroupDeleteID == null || tsecGroupDeleteID.isNull()) {
				tsecGroupDeleteID = new CFLibDbKeyHash256(0);
			}
			if (hasHistory) {
				if (tsecGroupRestoreID == null || tsecGroupRestoreID.isNull()) {
					tsecGroupRestoreID = new CFLibDbKeyHash256(0);
				}
			}
			if (isMutable) {
				if (tsecGroupMutateID == null || tsecGroupMutateID.isNull()) {
					tsecGroupMutateID = new CFLibDbKeyHash256(0);
				}
			}

			if (tsecGroupCreate == null) {
				tsecGroupCreate = ICFSecSchema.getBackingCFSec().getFactorySecTentGrp().newRec();
				tsecGroupCreate.setRequiredRevision(1);
				tsecGroupCreate.setCreatedAt(now);
				tsecGroupCreate.setCreatedByUserId(auth.getSecUserId());
				tsecGroupCreate.setUpdatedAt(now);
				tsecGroupCreate.setUpdatedByUserId(auth.getSecUserId());
				tsecGroupCreate.setRequiredName(createPermName);
				tsecGroupCreate.setRequiredSecTentGrpId(tsecGroupCreateID);
				tsecGroupCreate = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().createSecTentGrp(auth, tsecGroupCreate);
				tsecGroupCreateID = tsecGroupCreate.getRequiredSecTentGrpId();
			}

			if (tsecGroupCreateIncSystentadmin == null) {
				tsecGroupCreateIncSystentadmin = ICFSecSchema.getBackingCFSec().getFactorySecTentGrpInc().newRec();
				tsecGroupCreateIncSystentadmin.setRequiredRevision(1);
				tsecGroupCreateIncSystentadmin.setCreatedAt(now);
				tsecGroupCreateIncSystentadmin.setCreatedByUserId(auth.getSecUserId());
				tsecGroupCreateIncSystentadmin.setUpdatedAt(now);
				tsecGroupCreateIncSystentadmin.setUpdatedByUserId(auth.getSecUserId());
				tsecGroupCreateIncSystentadmin.setRequiredContainerGroup(tsecGroupCreateID);
				tsecGroupCreateIncSystentadmin.setRequiredParentSubGroup(systentadminGroup);
				tsecGroupCreateIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().createSecTentGrpInc(auth, tsecGroupCreateIncSystentadmin);
			}

			if (tsecGroupRead == null) {
				tsecGroupRead = ICFSecSchema.getBackingCFSec().getFactorySecTentGrp().newRec();
				tsecGroupRead.setRequiredRevision(1);
				tsecGroupRead.setCreatedAt(now);
				tsecGroupRead.setCreatedByUserId(auth.getSecUserId());
				tsecGroupRead.setUpdatedAt(now);
				tsecGroupRead.setUpdatedByUserId(auth.getSecUserId());
				tsecGroupRead.setRequiredName(readPermName);
				tsecGroupRead.setRequiredSecTentGrpId(tsecGroupReadID);
				tsecGroupRead = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().createSecTentGrp(auth, tsecGroupRead);
				tsecGroupReadID = tsecGroupRead.getRequiredSecTentGrpId();
			}

			if (tsecGroupReadIncSystentadmin == null) {
				tsecGroupReadIncSystentadmin = ICFSecSchema.getBackingCFSec().getFactorySecTentGrpInc().newRec();
				tsecGroupReadIncSystentadmin.setRequiredRevision(1);
				tsecGroupReadIncSystentadmin.setCreatedAt(now);
				tsecGroupReadIncSystentadmin.setCreatedByUserId(auth.getSecUserId());
				tsecGroupReadIncSystentadmin.setUpdatedAt(now);
				tsecGroupReadIncSystentadmin.setUpdatedByUserId(auth.getSecUserId());
				tsecGroupReadIncSystentadmin.setRequiredContainerGroup(tsecGroupReadID);
				tsecGroupReadIncSystentadmin.setRequiredParentSubGroup(systentadminGroup);
				tsecGroupReadIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().createSecTentGrpInc(auth, tsecGroupReadIncSystentadmin);
			}

			if (tsecGroupUpdate == null) {
				tsecGroupUpdate = ICFSecSchema.getBackingCFSec().getFactorySecTentGrp().newRec();
				tsecGroupUpdate.setRequiredRevision(1);
				tsecGroupUpdate.setCreatedAt(now);
				tsecGroupUpdate.setCreatedByUserId(auth.getSecUserId());
				tsecGroupUpdate.setUpdatedAt(now);
				tsecGroupUpdate.setUpdatedByUserId(auth.getSecUserId());
				tsecGroupUpdate.setRequiredName(updatePermName);
				tsecGroupUpdate.setRequiredSecTentGrpId(tsecGroupUpdateID);
				tsecGroupUpdate = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().createSecTentGrp(auth, tsecGroupUpdate);
				tsecGroupUpdateID = tsecGroupUpdate.getRequiredSecTentGrpId();
			}

			if (tsecGroupUpdateIncSystentadmin == null) {
				tsecGroupUpdateIncSystentadmin = ICFSecSchema.getBackingCFSec().getFactorySecTentGrpInc().newRec();
				tsecGroupUpdateIncSystentadmin.setRequiredRevision(1);
				tsecGroupUpdateIncSystentadmin.setCreatedAt(now);
				tsecGroupUpdateIncSystentadmin.setCreatedByUserId(auth.getSecUserId());
				tsecGroupUpdateIncSystentadmin.setUpdatedAt(now);
				tsecGroupUpdateIncSystentadmin.setUpdatedByUserId(auth.getSecUserId());
				tsecGroupUpdateIncSystentadmin.setRequiredContainerGroup(tsecGroupUpdateID);
				tsecGroupUpdateIncSystentadmin.setRequiredParentSubGroup(systentadminGroup);
				tsecGroupUpdateIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().createSecTentGrpInc(auth, tsecGroupUpdateIncSystentadmin);
			}

			if (tsecGroupDelete == null) {
				tsecGroupDelete = ICFSecSchema.getBackingCFSec().getFactorySecTentGrp().newRec();
				tsecGroupDelete.setRequiredRevision(1);
				tsecGroupDelete.setCreatedAt(now);
				tsecGroupDelete.setCreatedByUserId(auth.getSecUserId());
				tsecGroupDelete.setUpdatedAt(now);
				tsecGroupDelete.setUpdatedByUserId(auth.getSecUserId());
				tsecGroupDelete.setRequiredName(deletePermName);
				tsecGroupDelete.setRequiredSecTentGrpId(tsecGroupDeleteID);
				tsecGroupDelete = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().createSecTentGrp(auth, tsecGroupDelete);
				tsecGroupDeleteID = tsecGroupDelete.getRequiredSecTentGrpId();
			}

			if (tsecGroupDeleteIncSystentadmin == null) {
				tsecGroupDeleteIncSystentadmin = ICFSecSchema.getBackingCFSec().getFactorySecTentGrpInc().newRec();
				tsecGroupDeleteIncSystentadmin.setRequiredRevision(1);
				tsecGroupDeleteIncSystentadmin.setCreatedAt(now);
				tsecGroupDeleteIncSystentadmin.setCreatedByUserId(auth.getSecUserId());
				tsecGroupDeleteIncSystentadmin.setUpdatedAt(now);
				tsecGroupDeleteIncSystentadmin.setUpdatedByUserId(auth.getSecUserId());
				tsecGroupDeleteIncSystentadmin.setRequiredContainerGroup(tsecGroupDeleteID);
				tsecGroupDeleteIncSystentadmin.setRequiredParentSubGroup(systentadminGroup);
				tsecGroupDeleteIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().createSecTentGrpInc(auth, tsecGroupDeleteIncSystentadmin);
			}

			if (hasHistory) {
				if (tsecGroupRestore == null) {
					tsecGroupRestore = ICFSecSchema.getBackingCFSec().getFactorySecTentGrp().newRec();
					tsecGroupRestore.setRequiredRevision(1);
					tsecGroupRestore.setCreatedAt(now);
					tsecGroupRestore.setCreatedByUserId(auth.getSecUserId());
					tsecGroupRestore.setUpdatedAt(now);
					tsecGroupRestore.setUpdatedByUserId(auth.getSecUserId());
					tsecGroupRestore.setRequiredName(restorePermName);
					tsecGroupRestore.setRequiredSecTentGrpId(tsecGroupRestoreID);
					tsecGroupRestore = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().createSecTentGrp(auth, tsecGroupRestore);
					tsecGroupRestoreID = tsecGroupRestore.getRequiredSecTentGrpId();
				}

				if (tsecGroupRestoreIncSystentadmin == null) {
					tsecGroupRestoreIncSystentadmin = ICFSecSchema.getBackingCFSec().getFactorySecTentGrpInc().newRec();
					tsecGroupRestoreIncSystentadmin.setRequiredRevision(1);
					tsecGroupRestoreIncSystentadmin.setCreatedAt(now);
					tsecGroupRestoreIncSystentadmin.setCreatedByUserId(auth.getSecUserId());
					tsecGroupRestoreIncSystentadmin.setUpdatedAt(now);
					tsecGroupRestoreIncSystentadmin.setUpdatedByUserId(auth.getSecUserId());
					tsecGroupRestoreIncSystentadmin.setRequiredContainerGroup(tsecGroupRestoreID);
					tsecGroupRestoreIncSystentadmin.setRequiredParentSubGroup(systentadminGroup);
					tsecGroupRestoreIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().createSecTentGrpInc(auth, tsecGroupRestoreIncSystentadmin);
				}
			}

			if (isMutable) {
				if (tsecGroupMutate == null) {
					tsecGroupMutate = ICFSecSchema.getBackingCFSec().getFactorySecTentGrp().newRec();
					tsecGroupMutate.setRequiredRevision(1);
					tsecGroupMutate.setCreatedAt(now);
					tsecGroupMutate.setCreatedByUserId(auth.getSecUserId());
					tsecGroupMutate.setUpdatedAt(now);
					tsecGroupMutate.setUpdatedByUserId(auth.getSecUserId());
					tsecGroupMutate.setRequiredName(mutatePermName);
					tsecGroupMutate.setRequiredSecTentGrpId(tsecGroupMutateID);
					tsecGroupMutate = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().createSecTentGrp(auth, tsecGroupMutate);
					tsecGroupMutateID = tsecGroupMutate.getRequiredSecTentGrpId();
				}

				if (tsecGroupMutateIncSystentadmin == null) {
					tsecGroupMutateIncSystentadmin = ICFSecSchema.getBackingCFSec().getFactorySecTentGrpInc().newRec();
					tsecGroupMutateIncSystentadmin.setRequiredRevision(1);
					tsecGroupMutateIncSystentadmin.setCreatedAt(now);
					tsecGroupMutateIncSystentadmin.setCreatedByUserId(auth.getSecUserId());
					tsecGroupMutateIncSystentadmin.setUpdatedAt(now);
					tsecGroupMutateIncSystentadmin.setUpdatedByUserId(auth.getSecUserId());
					tsecGroupMutateIncSystentadmin.setRequiredContainerGroup(tsecGroupMutateID);
					tsecGroupMutateIncSystentadmin.setRequiredParentSubGroup(systentadminGroup);
					tsecGroupMutateIncSystentadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().createSecTentGrpInc(auth, tsecGroupMutateIncSystentadmin);
				}
			}
		}
	}		


}

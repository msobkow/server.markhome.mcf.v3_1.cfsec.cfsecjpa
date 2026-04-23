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


	public void bootstrapSchema(CFSecTableInfo tableInfo[]) {
		bootstrapSecurity();
		bootstrapAllTablesSecurity(tableInfo);
	}

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "$secschemadbname$TransactionManager")
	public void bootstrapSecurity() {
		CFSecJpaSysCluster sysCluster;
		CFLibDbKeyHash256 systemClusterID;
		CFSecJpaCluster systemCluster;
		CFSecJpaSecUser systemUser;
		CFLibDbKeyHash256 systemUID;
		CFSecJpaSecUser systemAdminUser;
		CFLibDbKeyHash256 systemAdminUID;
		CFSecJpaSecSession bootstrapSession;
		CFLibDbKeyHash256 bootstrapSessionID;
		CFSecJpaTenant systemTenant;
		CFLibDbKeyHash256 systemTenantID;
		CFSecJpaSecSysGrp secSystemAdminGroup;
		CFLibDbKeyHash256 secSystemAdminGroupID;
		CFSecJpaSecSysGrpMemb secSystemAdminGroupMembSystemAdmin;
		CFSecJpaSecSysGrp secSysGroupPublic;
		CFLibDbKeyHash256 secSysGroupPublicID;
		CFSecJpaSecSysGrpInc secSysGroupPublicIncSystemAdmin;
		CFSecJpaSecSysGrp secSysGroupSysClusAdmin;
		CFLibDbKeyHash256 secSysGroupSysClusAdminID;
		CFSecJpaSecClusGrpInc secSysGroupSysClusAdminIncSysclusadmin;
		CFSecJpaSecSysGrp secSysGroupSysTentAdmin;
		CFLibDbKeyHash256 secSysGroupSysTentAdminID;
		CFSecJpaSecTentGrpInc secSysGroupSysTentAdminIncSystentadmin;
		CFSecJpaSecClusGrp secSysClusGroupSysAdmin;
		CFLibDbKeyHash256 secSysClusGroupSysAdminID;
		CFSecJpaSecClusGrpInc secSysClusGroupSysAdminIncSystemAdmin;
		CFSecJpaSecTentGrp secSysTentGroupSysAdmin;
		CFLibDbKeyHash256 secSysTentGroupSysAdminID;
		CFSecJpaSecTentGrpInc secSysTentGroupSysAdminIncSystemAdmin;
		systemUser = secuserService.findByULoginIdx("system");
		if (systemUser != null) {
			systemUID = systemUser.getRequiredSecUserId();
			ICFSecSchema.setSystemId(systemUID);
		
			systemAdminUser = secuserService.findByULoginIdx("systemadmin");
			if (systemAdminUser == null) {
				throw new CFLibNullArgumentException(getClass(), "bootstrapSecurity", 0, "findByULoginIdx('systemadmin')");
			}
			systemAdminUID = systemAdminUser.getRequiredSecUserId();

			List<CFSecJpaSysCluster> sysClusters = sysclusterService.findAll();
			if (sysClusters != null && sysClusters.size() == 1) {
				sysCluster = sysClusters.get(0);
				systemClusterID = sysCluster.getRequiredClusterId();
				if(systemClusterID == null || systemClusterID.isNull()) {
					throw new CFLibNullArgumentException(getClass(), "bootstrapSecurity", 0, "systemClusterID");
				}
				systemCluster = clusterService.find(systemClusterID);
				if (systemCluster == null) {
					throw new CFLibNullArgumentException(getClass(), "bootstrapSecurity", 0, "systemCluster");
				}
				systemTenant = tenantService.findByUNameIdx(systemClusterID, "system");
				if( systemTenant == null) {
					systemTenantID = null;
					throw new CFLibNullArgumentException(getClass(), "bootstrapSecurity", 0, "systemTenant");
				}
				else {
					systemTenantID = systemTenant.getPKey();
				}
				bootstrapSession = secsessionService.findByStartIdx(systemUID, systemCluster.getCreatedAt());
				if (bootstrapSession == null) {
					List<CFSecJpaSecSession> sessions = secsessionService.findBySecUserIdx(systemUID);
					if (sessions != null) {
						for (CFSecJpaSecSession cursess: sessions) {
							if (bootstrapSession == null || (bootstrapSession != null && (cursess.getRequiredStart().compareTo(bootstrapSession.getRequiredStart()) < 0))) {
								bootstrapSession = cursess;
							}
						}
					}
					if (bootstrapSession == null) {
						throw new CFLibNullArgumentException(getClass(), "bootstrapSecurity", 0, "bootstrapSession");
					}
				}
				bootstrapSessionID = bootstrapSession.getPKey();

				secSystemAdminGroup = (CFSecJpaSecSysGrp)(secsysgrpService.findByUNameIdx( "systemadmin"));
				if (secSystemAdminGroup == null) {
					throw new CFLibNullArgumentException(getClass(), "bootstrapSecurity", 0, "secSystemAdminGroup");
				}
				secSystemAdminGroupID = secSystemAdminGroup.getRequiredSecSysGrpId();

				secSysGroupPublic = (CFSecJpaSecSysGrp)(secsysgrpService.findByUNameIdx( "public"));
				if (secSysGroupPublic == null) {
					throw new CFLibNullArgumentException(getClass(), "bootstrapSecurity", 0, "secSysGroupPublic");
				}
				secSysGroupPublicID = secSysGroupPublic.getRequiredSecSysGrpId();

				secSysGroupSysClusAdmin = (CFSecJpaSecSysGrp)(secsysgrpService.findByUNameIdx( "systemclusteradmin"));
				if (secSysGroupSysClusAdmin == null) {
					throw new CFLibNullArgumentException(getClass(), "bootstrapSecurity", 0, "secSysGroupSysClusAdmin");
				}
				secSysGroupSysClusAdminID = secSysGroupSysClusAdmin.getRequiredSecSysGrpId();

				secSysGroupSysTentAdmin = (CFSecJpaSecSysGrp)(secsysgrpService.findByUNameIdx( "systemtenantadmin"));
				if (secSysGroupSysTentAdmin == null) {
					throw new CFLibNullArgumentException(getClass(), "bootstrapSecurity", 0, "secSysGroupSysTentAdmin");
				}
				secSysGroupSysTentAdminID = secSysGroupSysTentAdmin.getRequiredSecSysGrpId();

				secSystemAdminGroupMembSystemAdmin = (CFSecJpaSecSysGrpMemb)(secsysgrpmembService.find(secSystemAdminGroupID, systemUser.getRequiredLoginId()));
				if (secSystemAdminGroupMembSystemAdmin == null) {
					throw new CFLibNullArgumentException(getClass(), "bootstrapSecurity", 0, "secSystemAdminGroupMembSystemAdmin");
				}

				secSysGroupPublicIncSystemAdmin = (CFSecJpaSecSysGrpInc)(secsysgrpincService.find(secSysGroupPublicID, secSystemAdminGroup.getRequiredName()));
				if (secSysGroupPublicIncSystemAdmin == null) {
					throw new CFLibNullArgumentException(getClass(), "bootstrapSecurity", 0, "secSysGroupPublicIncSystemAdmin");
				}

				secSysClusGroupSysAdmin = (CFSecJpaSecClusGrp)(secclusgrpService.findByUNameIdx(systemClusterID, "systemclusteradmin"));
				if (secSysClusGroupSysAdmin == null) {
					throw new CFLibNullArgumentException(getClass(), "bootstrapSecurity", 0, "secSysClusGroupSysAdmin");
				}
				secSysClusGroupSysAdminID = secSysClusGroupSysAdmin.getRequiredSecClusGrpId();

				secSysClusGroupSysAdminIncSystemAdmin = (CFSecJpaSecClusGrpInc)(secclusgrpincService.find(secSysClusGroupSysAdminID, secSystemAdminGroup.getRequiredName()));
				if (secSysClusGroupSysAdminIncSystemAdmin == null) {
					throw new CFLibNullArgumentException(getClass(), "bootstrapSecurity", 0, "secSysClusGroupSysAdminIncSystem");
				}

				secSysTentGroupSysAdmin = (CFSecJpaSecTentGrp)(sectentgrpService.findByUNameIdx(systemTenantID, "systemtenantadmin"));
				if (secSysTentGroupSysAdmin == null) {
					throw new CFLibNullArgumentException(getClass(), "bootstrapSecurity", 0, "secSysTentGroupSysAdmin");
				}
				secSysTentGroupSysAdminID = secSysTentGroupSysAdmin.getRequiredSecTentGrpId();

				secSysTentGroupSysAdminIncSystemAdmin = (CFSecJpaSecTentGrpInc)(sectentgrpincService.find(secSysTentGroupSysAdminID, secSystemAdminGroup.getRequiredName()));
				if (secSysTentGroupSysAdminIncSystemAdmin == null) {
					throw new CFLibNullArgumentException(getClass(), "bootstrapSecurity", 0, "secSysTentGroupSysAdminIncSystemAdmin");
				}
			}
			else {
				throw new CFLibNullArgumentException(getClass(), "bootstrapSecurity", 0, "SysClusterSingleton");
			}
		}
		else {
			systemUID = null;
			systemAdminUID = null;
			systemAdminUser = null;
			sysCluster = null;
			systemCluster = null;
			systemClusterID = null;
			bootstrapSession = null;
			bootstrapSessionID = null;
			systemTenant = null;
			systemTenantID = null;
			secSystemAdminGroup = null;
			secSystemAdminGroupID = null;
			secSystemAdminGroupMembSystemAdmin = null;
			secSysGroupPublic = null;
			secSysGroupPublicID = null;
			secSysGroupPublicIncSystemAdmin = null;
			secSysGroupSysClusAdmin = null;
			secSysGroupSysClusAdminID = null;
			secSysGroupSysTentAdmin = null;
			secSysGroupSysTentAdminID = null;
			secSysClusGroupSysAdmin = null;
			secSysClusGroupSysAdminID = null;
			secSysClusGroupSysAdminIncSystemAdmin = null;
			secSysTentGroupSysAdmin = null;
			secSysTentGroupSysAdminID = null;
			secSysTentGroupSysAdminIncSystemAdmin = null;
			secSysGroupSysClusAdmin = null;
			secSysGroupSysClusAdminID = null;
			secSysGroupSysClusAdminIncSysclusadmin = null;
			secSysGroupSysTentAdmin = null;
			secSysGroupSysTentAdminID = null;
			secSysGroupSysTentAdminIncSystentadmin = null;
		}
		LocalDateTime now = LocalDateTime.now();
		if (systemUID == null || systemUID.isNull()) {
			systemUID = new CFLibDbKeyHash256(0);
		}
		if (systemAdminUID == null || systemAdminUID.isNull()) {
			systemAdminUID = new CFLibDbKeyHash256(0);
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
		if (secSystemAdminGroupID == null || secSystemAdminGroupID.isNull()) {
			secSystemAdminGroupID = new CFLibDbKeyHash256(0);
		}
		if (secSysGroupPublicID == null || secSysGroupPublicID.isNull()) {
			secSysGroupPublicID = new CFLibDbKeyHash256(0);
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
			throw new CFLibInvalidArgumentException(getClass(), "bootstrapSecurity", "Previously set system cluster id disagrees with new system cluster id", "Previously set system cluster id disagrees with new system cluster id");
		}
		if (ICFSecSchema.getSysTenantId() == null || ICFSecSchema.getSysTenantId().isNull()) {
			ICFSecSchema.setSysTenantId(systemTenantID);
		}
		else if ( ! ICFSecSchema.getSysTenantId().equals( systemTenantID )) {
			throw new CFLibInvalidArgumentException(getClass(), "bootstrapSecurity", "Previously set system tenant id disagrees with new system tenant id", "Previously set system tenant id disagrees with new system tenant id");
		}
		if (ICFSecSchema.getSystemId() == null || ICFSecSchema.getSystemId().isNull()) {
			ICFSecSchema.setSystemId(systemUID);
		}
		else if ( ! ICFSecSchema.getSystemId().equals( systemUID )) {
			throw new CFLibInvalidArgumentException(getClass(), "bootstrapSecurity", "Previously set system admin id disagrees with new system admin id", "Previously set system admin id disagrees with new system admin id");
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
			systemCluster.setCreatedByUserId(systemUID);
			systemCluster.setUpdatedByUserId(systemUID);
			systemCluster.setCreatedAt(now);
			systemCluster.setUpdatedAt(now);
			systemCluster.setRequiredFullDomName(fqdn);
			systemCluster.setRequiredDescription("System cluster for " + fqdn);
			systemCluster = clusterService.create(systemCluster);
			systemClusterID = systemCluster.getPKey();
		}

		if (systemUser == null) {
			systemUser = new CFSecJpaSecUser();
			systemUser.setRequiredRevision(1);
			systemUser.setCreatedByUserId(systemUID);
			systemUser.setUpdatedByUserId(systemUID);
			systemUser.setCreatedAt(now);
			systemUser.setUpdatedAt(now);
			systemUser.setRequiredSecUserId(systemUID);
			systemUser.setRequiredLoginId("system");
			systemUser.setRequiredEMailAddress("system@" + fqdn);
			systemUser.setRequiredAccountStatus(ICFSecSchema.SecAccountStatusEnum.System);
			systemUser = secuserService.create(systemUser);
			systemUID = systemUser.getPKey();
		}

		if (systemAdminUser == null) {
			systemAdminUser = new CFSecJpaSecUser();
			systemAdminUser.setRequiredRevision(1);
			systemAdminUser.setCreatedByUserId(systemUID);
			systemAdminUser.setUpdatedByUserId(systemUID);
			systemAdminUser.setCreatedAt(now);
			systemAdminUser.setUpdatedAt(now);
			systemAdminUser.setRequiredSecUserId(systemAdminUID);
			systemAdminUser.setRequiredLoginId("systemadmin");
			systemAdminUser.setRequiredEMailAddress("systemadmin@" + fqdn);
			systemAdminUser.setRequiredAccountStatus(ICFSecSchema.SecAccountStatusEnum.ResettingPassword);
			systemAdminUser = secuserService.create(systemAdminUser);
			systemAdminUID = systemAdminUser.getPKey();
			
			CFSecJpaSecUserPassword systemAdminUserPassword = secuserpasswordService.find(systemAdminUID);
			if (systemAdminUserPassword == null) {
				systemAdminUserPassword = new CFSecJpaSecUserPassword();
				systemAdminUserPassword.setRequiredRevision(1);
				systemAdminUserPassword.setPKey(systemAdminUID);
				systemAdminUserPassword.setRequiredContainerUser(systemAdminUID);
				systemAdminUserPassword.setRequiredPWSetStamp(now);
				systemAdminUserPassword.setRequiredPasswordHash(ICFSecSchema.getPasswordHash("ChangeOnInstall"));
				systemAdminUserPassword = secuserpasswordService.create(systemAdminUserPassword);
			}
		}

		if (systemTenant == null) {
			systemTenant = new CFSecJpaTenant();
			systemTenant.setRequiredRevision(1);
			systemTenant.setRequiredId(systemTenantID);
			systemTenant.setCreatedByUserId(systemUID);
			systemTenant.setUpdatedByUserId(systemUID);
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
			bootstrapSession.setRequiredSecUserId(systemUID);
			bootstrapSession.setOptionalSecProxyId(systemUID);
			bootstrapSession.setRequiredStart(now);
			bootstrapSession.setOptionalFinish(null);
			bootstrapSession = secsessionService.create(bootstrapSession);
		}
			
		if (sysCluster == null) {
			sysCluster = new CFSecJpaSysCluster();
			sysCluster.setRequiredContainerCluster(systemClusterID);
			sysCluster = sysclusterService.create(sysCluster);
		}

		if (secSystemAdminGroup == null) {
			secSystemAdminGroup = new CFSecJpaSecSysGrp();
			secSystemAdminGroup.setRequiredRevision(1);
			secSystemAdminGroup.setCreatedAt(now);
			secSystemAdminGroup.setCreatedByUserId(systemUID);
			secSystemAdminGroup.setUpdatedAt(now);
			secSystemAdminGroup.setUpdatedByUserId(systemUID);
			secSystemAdminGroup.setRequiredName("systemadmin");
			secSystemAdminGroup.setRequiredSecLevel(ICFSecSchema.SecLevelEnum.System);
			secSystemAdminGroup.setRequiredSecSysGrpId(secSystemAdminGroupID);
			secSystemAdminGroup = (CFSecJpaSecSysGrp)(secsysgrpService.create(secSystemAdminGroup));
			secSystemAdminGroupID = secSystemAdminGroup.getRequiredSecSysGrpId();
		}

		if (secSystemAdminGroupMembSystemAdmin == null) {
			secSystemAdminGroupMembSystemAdmin = new CFSecJpaSecSysGrpMemb();
			secSystemAdminGroupMembSystemAdmin.setRequiredRevision(1);
			secSystemAdminGroupMembSystemAdmin.setRequiredContainerGroup(secSystemAdminGroupID);
			secSystemAdminGroupMembSystemAdmin.setRequiredParentUser(systemAdminUser.getRequiredLoginId());
			secSystemAdminGroupMembSystemAdmin = (CFSecJpaSecSysGrpMemb)(secsysgrpmembService.create(secSystemAdminGroupMembSystemAdmin));
		}

		if (secSysGroupPublic == null) {
			secSysGroupPublic = new CFSecJpaSecSysGrp();
			secSysGroupPublic.setRequiredRevision(1);
			secSysGroupPublic.setCreatedAt(now);
			secSysGroupPublic.setCreatedByUserId(systemUID);
			secSysGroupPublic.setUpdatedAt(now);
			secSysGroupPublic.setUpdatedByUserId(systemUID);
			secSysGroupPublic.setRequiredName("public");
			secSysGroupPublic.setRequiredSecLevel(ICFSecSchema.SecLevelEnum.System);
			secSysGroupPublic.setRequiredSecSysGrpId(secSysGroupPublicID);
			secSysGroupPublic = (CFSecJpaSecSysGrp)(secsysgrpService.create(secSysGroupPublic));
			secSysGroupPublicID = secSysGroupPublic.getRequiredSecSysGrpId();
		}

		if (secSysGroupPublicIncSystemAdmin == null) {
			secSysGroupPublicIncSystemAdmin = new CFSecJpaSecSysGrpInc();
			secSysGroupPublicIncSystemAdmin.setRequiredContainerGroup(secSysGroupPublicID);
			secSysGroupPublicIncSystemAdmin.setCreatedAt(now);
			secSysGroupPublicIncSystemAdmin.setCreatedByUserId(systemUID);
			secSysGroupPublicIncSystemAdmin.setUpdatedAt(now);
			secSysGroupPublicIncSystemAdmin.setUpdatedByUserId(systemUID);
			secSysGroupPublicIncSystemAdmin.setRequiredParentSubGroup(secSystemAdminGroup.getRequiredName());
			secSysGroupPublicIncSystemAdmin.setRequiredRevision(1);
			secSysGroupPublicIncSystemAdmin = (CFSecJpaSecSysGrpInc)(secsysgrpincService.create(secSysGroupPublicIncSystemAdmin));
		}
		
		if (secSysGroupSysClusAdmin == null) {
			secSysGroupSysClusAdmin = new CFSecJpaSecSysGrp();
			secSysGroupSysClusAdmin.setRequiredRevision(1);
			secSysGroupSysClusAdmin.setCreatedAt(now);
			secSysGroupSysClusAdmin.setCreatedByUserId(systemUID);
			secSysGroupSysClusAdmin.setUpdatedAt(now);
			secSysGroupSysClusAdmin.setUpdatedByUserId(systemUID);
			secSysGroupSysClusAdmin.setRequiredName("systemclusteradmin");
			secSysGroupSysClusAdmin.setRequiredSecLevel(ICFSecSchema.SecLevelEnum.Cluster);
			secSysGroupSysClusAdmin.setRequiredSecSysGrpId(secSysGroupSysClusAdminID);
			secSysGroupSysClusAdmin = (CFSecJpaSecSysGrp)(secsysgrpService.create(secSysGroupSysClusAdmin));
			secSysGroupSysClusAdminID = secSysGroupSysClusAdmin.getRequiredSecSysGrpId();
		}

		if (secSysGroupSysTentAdmin == null) {
			secSysGroupSysTentAdmin = new CFSecJpaSecSysGrp();
			secSysGroupSysTentAdmin.setRequiredRevision(1);
			secSysGroupSysTentAdmin.setCreatedAt(now);
			secSysGroupSysTentAdmin.setCreatedByUserId(systemUID);
			secSysGroupSysTentAdmin.setUpdatedAt(now);
			secSysGroupSysTentAdmin.setUpdatedByUserId(systemUID);
			secSysGroupSysTentAdmin.setRequiredName("systemtenantadmin");
			secSysGroupSysTentAdmin.setRequiredSecLevel(ICFSecSchema.SecLevelEnum.Tenant);
			secSysGroupSysTentAdmin.setRequiredSecSysGrpId(secSysGroupSysTentAdminID);
			secSysGroupSysTentAdmin = (CFSecJpaSecSysGrp)(secsysgrpService.create(secSysGroupSysTentAdmin));
			secSysGroupSysTentAdminID = secSysGroupSysTentAdmin.getRequiredSecSysGrpId();
		}

		if (secSysClusGroupSysAdmin == null) {
			secSysClusGroupSysAdmin = new CFSecJpaSecClusGrp();
			secSysClusGroupSysAdmin.setCreatedAt(now);
			secSysClusGroupSysAdmin.setCreatedByUserId(systemUID);
			secSysClusGroupSysAdmin.setUpdatedAt(now);
			secSysClusGroupSysAdmin.setUpdatedByUserId(systemUID);
			secSysClusGroupSysAdmin.setRequiredName("systemclusteradmin");
			secSysClusGroupSysAdmin.setRequiredOwnerCluster(systemClusterID);
			secSysClusGroupSysAdmin.setRequiredRevision(1);
			secSysClusGroupSysAdmin = (CFSecJpaSecClusGrp)(secclusgrpService.create(secSysClusGroupSysAdmin));
			secSysClusGroupSysAdminID = secSysClusGroupSysAdmin.getRequiredSecClusGrpId();
		}

		if (secSysClusGroupSysAdminIncSystemAdmin == null) {
			secSysClusGroupSysAdminIncSystemAdmin = new CFSecJpaSecClusGrpInc();
			secSysClusGroupSysAdminIncSystemAdmin.setRequiredContainerGroup(secSysClusGroupSysAdminID);
			secSysClusGroupSysAdminIncSystemAdmin.setCreatedAt(now);
			secSysClusGroupSysAdminIncSystemAdmin.setCreatedByUserId(systemUID);
			secSysClusGroupSysAdminIncSystemAdmin.setUpdatedAt(now);
			secSysClusGroupSysAdminIncSystemAdmin.setUpdatedByUserId(systemUID);
			secSysClusGroupSysAdminIncSystemAdmin.setRequiredParentSubGroup(secSystemAdminGroup.getRequiredName());
			secSysClusGroupSysAdminIncSystemAdmin.setRequiredRevision(1);
			secSysClusGroupSysAdminIncSystemAdmin = (CFSecJpaSecClusGrpInc)(secclusgrpincService.create(secSysClusGroupSysAdminIncSystemAdmin));
		}

		if (secSysTentGroupSysAdmin == null) {
			secSysTentGroupSysAdmin = new CFSecJpaSecTentGrp();
			secSysTentGroupSysAdmin.setCreatedAt(now);
			secSysTentGroupSysAdmin.setCreatedByUserId(systemUID);
			secSysTentGroupSysAdmin.setUpdatedAt(now);
			secSysTentGroupSysAdmin.setUpdatedByUserId(systemUID);
			secSysTentGroupSysAdmin.setRequiredName("systemtenantadmin");
			secSysTentGroupSysAdmin.setRequiredOwnerTenant(systemTenantID);
			secSysTentGroupSysAdmin.setRequiredRevision(1);
			secSysTentGroupSysAdmin = (CFSecJpaSecTentGrp)(sectentgrpService.create(secSysTentGroupSysAdmin));
			secSysTentGroupSysAdminID = secSysTentGroupSysAdmin.getRequiredSecTentGrpId();
		}

		if (secSysTentGroupSysAdminIncSystemAdmin == null) {
			secSysTentGroupSysAdminIncSystemAdmin = new CFSecJpaSecTentGrpInc();
			secSysTentGroupSysAdminIncSystemAdmin.setRequiredContainerGroup(secSysTentGroupSysAdminID);
			secSysTentGroupSysAdminIncSystemAdmin.setCreatedAt(now);
			secSysTentGroupSysAdminIncSystemAdmin.setCreatedByUserId(systemUID);
			secSysTentGroupSysAdminIncSystemAdmin.setUpdatedAt(now);
			secSysTentGroupSysAdminIncSystemAdmin.setUpdatedByUserId(systemUID);
			secSysTentGroupSysAdminIncSystemAdmin.setRequiredParentSubGroup(secSystemAdminGroup.getRequiredName());
			secSysTentGroupSysAdminIncSystemAdmin.setRequiredRevision(1);
			secSysTentGroupSysAdminIncSystemAdmin = (CFSecJpaSecTentGrpInc)(sectentgrpincService.create(secSysTentGroupSysAdminIncSystemAdmin));
		}

		if (bootstrapSession != null && bootstrapSessionID != null && !bootstrapSessionID.isNull() && bootstrapSession.getOptionalFinish() == null) {
			bootstrapSession.setOptionalFinish(LocalDateTime.now());
			bootstrapSession = secsessionService.update(bootstrapSession);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "$secschemadbname$TransactionManager")
	public void bootstrapTableSecurity(ICFSecAuthorization auth,
		LocalDateTime now,
		String tableName,
		boolean hasHistory,
		boolean isMutable,
		String secScope,
		ICFSecSecSysGrp secSysGroupPublic,
		ICFSecSecSysGrp secSystemAdminGroup,
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
		String systemGroup = secSystemAdminGroup.getRequiredName();
		String sysclusadminGroup = secSysClusGroupSysAdmin.getRequiredName();
		String systentadminGroup = secSysTentGroupSysAdmin.getRequiredName();
		String publicGroup = secSysGroupPublic.getRequiredName();
		
		ICFSecSecSysGrp secGroupCreate;
		CFLibDbKeyHash256 secGroupCreateID;
		ICFSecSecSysGrpInc secGroupCreateIncSystemAdmin;
		ICFSecSecSysGrp secGroupRead;
		CFLibDbKeyHash256 secGroupReadID;
		ICFSecSecSysGrpInc secGroupReadIncSystemAdmin;
		ICFSecSecSysGrpInc secGroupReadIncPublic;
		ICFSecSecSysGrp secGroupUpdate;
		CFLibDbKeyHash256 secGroupUpdateID;
		ICFSecSecSysGrpInc secGroupUpdateIncSystemAdmin;
		ICFSecSecSysGrp secGroupDelete;
		CFLibDbKeyHash256 secGroupDeleteID;
		ICFSecSecSysGrpInc secGroupDeleteIncSystemAdmin;
		ICFSecSecSysGrp secGroupRestore;
		CFLibDbKeyHash256 secGroupRestoreID;
		ICFSecSecSysGrpInc secGroupRestoreIncSystemAdmin;
		ICFSecSecSysGrp secGroupMutate;
		CFLibDbKeyHash256 secGroupMutateID;
		ICFSecSecSysGrpInc secGroupMutateIncSystemAdmin;

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
			secGroupCreateIncSystemAdmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secGroupCreateID, systemGroup);
		}
		else {
			secGroupCreateID = null;
			secGroupCreateIncSystemAdmin = null;
		}

		secGroupRead = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx(auth, readPermName);
		if (secGroupRead != null) {
			secGroupReadID = secGroupRead.getRequiredSecSysGrpId();
			secGroupReadIncSystemAdmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secGroupReadID, systemGroup);
		}
		else {
			secGroupReadID = null;
			secGroupReadIncSystemAdmin = null;
		}

		if (secGroupRead != null && level == ICFSecSchema.SecLevelEnum.Global) {
			secGroupReadIncPublic = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secGroupReadID, publicGroup);
		}
		else {
			secGroupReadIncPublic = null;
		}
		
		secGroupUpdate = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx(auth, updatePermName);
		if (secGroupUpdate != null) {
			secGroupUpdateID = secGroupUpdate.getRequiredSecSysGrpId();
			secGroupUpdateIncSystemAdmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secGroupUpdateID, systemGroup);
		}
		else {
			secGroupUpdateID = null;
			secGroupUpdateIncSystemAdmin = null;
		}

		secGroupDelete = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx(auth, deletePermName);
		if (secGroupDelete != null) {
			secGroupDeleteID = secGroupDelete.getRequiredSecSysGrpId();
			secGroupDeleteIncSystemAdmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secGroupDeleteID, systemGroup);
		}
		else {
			secGroupDeleteID = null;
			secGroupDeleteIncSystemAdmin = null;
		}
		
		if (hasHistory) {
			secGroupRestore = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx(auth, restorePermName);
			if (secGroupRestore != null) {
				secGroupRestoreID = secGroupRestore.getRequiredSecSysGrpId();
				secGroupRestoreIncSystemAdmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secGroupRestoreID, systemGroup);
			}
			else {
				secGroupRestoreID = null;
				secGroupRestoreIncSystemAdmin = null;
			}
		}
		else {
			secGroupRestore = null;
			secGroupRestoreID = null;
			secGroupRestoreIncSystemAdmin = null;
		}
		
		if (isMutable) {
			secGroupMutate = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx(auth, mutatePermName);
			if (secGroupMutate != null) {
				secGroupMutateID = secGroupMutate.getRequiredSecSysGrpId();
				secGroupMutateIncSystemAdmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secGroupMutateID, systemGroup);
			}
			else {
				secGroupMutateID = null;
				secGroupMutateIncSystemAdmin = null;
			}
		}
		else {
			secGroupMutate = null;
			secGroupMutateID = null;
			secGroupMutateIncSystemAdmin = null;
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

		if (secGroupCreateIncSystemAdmin == null) {
			secGroupCreateIncSystemAdmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
			secGroupCreateIncSystemAdmin.setRequiredRevision(1);
			secGroupCreateIncSystemAdmin.setCreatedAt(now);
			secGroupCreateIncSystemAdmin.setCreatedByUserId(auth.getSecUserId());
			secGroupCreateIncSystemAdmin.setUpdatedAt(now);
			secGroupCreateIncSystemAdmin.setUpdatedByUserId(auth.getSecUserId());
			secGroupCreateIncSystemAdmin.setRequiredContainerGroup(secGroupCreateID);
			secGroupCreateIncSystemAdmin.setRequiredParentSubGroup(systemGroup);
			secGroupCreateIncSystemAdmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupCreateIncSystemAdmin);
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

		if (secGroupReadIncSystemAdmin == null) {
			secGroupReadIncSystemAdmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
			secGroupReadIncSystemAdmin.setRequiredRevision(1);
			secGroupReadIncSystemAdmin.setCreatedAt(now);
			secGroupReadIncSystemAdmin.setCreatedByUserId(auth.getSecUserId());
			secGroupReadIncSystemAdmin.setUpdatedAt(now);
			secGroupReadIncSystemAdmin.setUpdatedByUserId(auth.getSecUserId());
			secGroupReadIncSystemAdmin.setRequiredContainerGroup(secGroupReadID);
			secGroupReadIncSystemAdmin.setRequiredParentSubGroup(systemGroup);
			secGroupReadIncSystemAdmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupReadIncSystemAdmin);
		}

		if (secGroupRead != null && level == ICFSecSchema.SecLevelEnum.Global && secGroupReadIncPublic == null) {
			secGroupReadIncPublic = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
			secGroupReadIncPublic.setRequiredRevision(1);
			secGroupReadIncPublic.setCreatedAt(now);
			secGroupReadIncPublic.setCreatedByUserId(auth.getSecUserId());
			secGroupReadIncPublic.setUpdatedAt(now);
			secGroupReadIncPublic.setUpdatedByUserId(auth.getSecUserId());
			secGroupReadIncPublic.setRequiredContainerGroup(secGroupReadID);
			secGroupReadIncPublic.setRequiredParentSubGroup(publicGroup);
			secGroupReadIncPublic = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupReadIncPublic);
		}
		else {
			secGroupReadIncPublic = null;
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

		if (secGroupUpdateIncSystemAdmin == null) {
			secGroupUpdateIncSystemAdmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
			secGroupUpdateIncSystemAdmin.setRequiredRevision(1);
			secGroupUpdateIncSystemAdmin.setCreatedAt(now);
			secGroupUpdateIncSystemAdmin.setCreatedByUserId(auth.getSecUserId());
			secGroupUpdateIncSystemAdmin.setUpdatedAt(now);
			secGroupUpdateIncSystemAdmin.setUpdatedByUserId(auth.getSecUserId());
			secGroupUpdateIncSystemAdmin.setRequiredContainerGroup(secGroupUpdateID);
			secGroupUpdateIncSystemAdmin.setRequiredParentSubGroup(systemGroup);
			secGroupUpdateIncSystemAdmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupUpdateIncSystemAdmin);
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

		if (secGroupDeleteIncSystemAdmin == null) {
			secGroupDeleteIncSystemAdmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
			secGroupDeleteIncSystemAdmin.setRequiredRevision(1);
			secGroupDeleteIncSystemAdmin.setCreatedAt(now);
			secGroupDeleteIncSystemAdmin.setCreatedByUserId(auth.getSecUserId());
			secGroupDeleteIncSystemAdmin.setUpdatedAt(now);
			secGroupDeleteIncSystemAdmin.setUpdatedByUserId(auth.getSecUserId());
			secGroupDeleteIncSystemAdmin.setRequiredContainerGroup(secGroupDeleteID);
			secGroupDeleteIncSystemAdmin.setRequiredParentSubGroup(systemGroup);
			secGroupDeleteIncSystemAdmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupDeleteIncSystemAdmin);
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

			if (secGroupRestoreIncSystemAdmin == null) {
				secGroupRestoreIncSystemAdmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
				secGroupRestoreIncSystemAdmin.setRequiredRevision(1);
				secGroupRestoreIncSystemAdmin.setCreatedAt(now);
				secGroupRestoreIncSystemAdmin.setCreatedByUserId(auth.getSecUserId());
				secGroupRestoreIncSystemAdmin.setUpdatedAt(now);
				secGroupRestoreIncSystemAdmin.setUpdatedByUserId(auth.getSecUserId());
				secGroupRestoreIncSystemAdmin.setRequiredContainerGroup(secGroupRestoreID);
				secGroupRestoreIncSystemAdmin.setRequiredParentSubGroup(systemGroup);
				secGroupRestoreIncSystemAdmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupRestoreIncSystemAdmin);
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

			if (secGroupMutateIncSystemAdmin == null) {
				secGroupMutateIncSystemAdmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
				secGroupMutateIncSystemAdmin.setRequiredRevision(1);
				secGroupMutateIncSystemAdmin.setCreatedAt(now);
				secGroupMutateIncSystemAdmin.setCreatedByUserId(auth.getSecUserId());
				secGroupMutateIncSystemAdmin.setUpdatedAt(now);
				secGroupMutateIncSystemAdmin.setUpdatedByUserId(auth.getSecUserId());
				secGroupMutateIncSystemAdmin.setRequiredContainerGroup(secGroupMutateID);
				secGroupMutateIncSystemAdmin.setRequiredParentSubGroup(systemGroup);
				secGroupMutateIncSystemAdmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secGroupMutateIncSystemAdmin);
			}
		}
		
		if (level == ICFSecSchema.SecLevelEnum.Cluster || level == ICFSecSchema.SecLevelEnum.Tenant) {
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
				csecGroupCreate.setRequiredOwnerCluster(secSysClusGroupSysAdmin.getRequiredOwnerCluster());
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
				csecGroupRead.setRequiredOwnerCluster(secSysClusGroupSysAdmin.getRequiredOwnerCluster());
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
				csecGroupUpdate.setRequiredOwnerCluster(secSysClusGroupSysAdmin.getRequiredOwnerCluster());
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
				csecGroupDelete.setRequiredOwnerCluster(secSysClusGroupSysAdmin.getRequiredOwnerCluster());
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
					csecGroupRestore.setRequiredOwnerCluster(secSysClusGroupSysAdmin.getRequiredOwnerCluster());
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
					csecGroupMutate.setRequiredOwnerCluster(secSysClusGroupSysAdmin.getRequiredOwnerCluster());
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

		if (level == ICFSecSchema.SecLevelEnum.Tenant ) {
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
				tsecGroupCreate.setRequiredOwnerTenant(secSysTentGroupSysAdmin.getRequiredOwnerTenant());
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
				tsecGroupRead.setRequiredOwnerTenant(secSysTentGroupSysAdmin.getRequiredOwnerTenant());
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
				tsecGroupUpdate.setRequiredOwnerTenant(secSysTentGroupSysAdmin.getRequiredOwnerTenant());
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
				tsecGroupDelete.setRequiredOwnerTenant(secSysTentGroupSysAdmin.getRequiredOwnerTenant());
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
					tsecGroupRestore.setRequiredOwnerTenant(secSysTentGroupSysAdmin.getRequiredOwnerTenant());
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
					tsecGroupMutate.setRequiredOwnerTenant(secSysTentGroupSysAdmin.getRequiredOwnerTenant());
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

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "$secschemadbname$TransactionManager")
	public void bootstrapAllTablesSecurity(CFSecTableInfo tableInfo[]) {
		bootstrapAllTablesSecurity(ICFSecSchema.getSysClusterId(), ICFSecSchema.getSysTenantId(), tableInfo);
	}

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "$secschemadbname$TransactionManager")
	public void bootstrapAllTablesSecurity(CFLibDbKeyHash256 clusterId, CFLibDbKeyHash256 tenantId, CFSecTableInfo tableInfo[]) {
		LocalDateTime now = LocalDateTime.now();
		ICFSecSecSession bootstrapSession;
		CFLibDbKeyHash256 bootstrapSessionID = new CFLibDbKeyHash256(0);

		ICFSecAuthorization auth = new CFSecAuthorization();
		auth.setSecUserId(ICFSecSchema.getSystemId());
		auth.setAuthUuid6(CFLibUuid6.generateUuid6());
		auth.setSecClusterId(clusterId);
		auth.setSecTenantId(tenantId);
		auth.setSecSessionId(bootstrapSessionID);

		CFLibDbKeyHash256 systemUID = ICFSecSchema.getSystemId();

//ICFSecSchema.getSysTenantId(), ICFSecSchema.getSystemId()
		bootstrapSession = ICFSecSchema.getBackingCFSec().getFactorySecSession().newRec();
		bootstrapSession.setRequiredRevision(1);
		bootstrapSession.setRequiredSecSessionId(bootstrapSessionID);
		bootstrapSession.setRequiredSecUserId(systemUID);
		bootstrapSession.setOptionalSecProxyId(systemUID);
		bootstrapSession.setRequiredStart(now);
		bootstrapSession.setOptionalFinish(null);
		bootstrapSession = ICFSecSchema.getBackingCFSec().getTableSecSession().createSecSession(auth, bootstrapSession);
		bootstrapSessionID = bootstrapSession.getRequiredSecSessionId();

		ICFSecSecSysGrp secSystemAdminGroup = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx( auth, "systemadmin");
		if (secSystemAdminGroup == null) {
			throw new CFLibNullArgumentException(getClass(), "bootstrapAllTablesSecurity", 0, "secSystemAdminGroup");
		}

		ICFSecSecSysGrp secSysGroupPublic = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx( auth, "public");
		if (secSysGroupPublic == null) {
			throw new CFLibNullArgumentException(getClass(), "bootstrapAllTablesSecurity", 0, "secSysGroupPublic");
		}
		
		ICFSecCluster secCluster = ICFSecSchema.getBackingCFSec().getTableCluster().readDerived(auth, clusterId);
		if (secCluster == null) {
			throw new CFLibNullArgumentException(getClass(), "bootstrapAllTablesSecurity", 0, "secCluster<" + clusterId.toString() + ">");
		}
		
		ICFSecTenant secTenant = ICFSecSchema.getBackingCFSec().getTableTenant().readDerived(auth, tenantId);
		if (secTenant == null) {
			throw new CFLibNullArgumentException(getClass(), "bootstrapAllTablesSecurity", 0, "secTenant<" + tenantId.toString() + ">");
		}
	
		bootstrapAllTablesSecurity(auth, systemUID, bootstrapSession, secSystemAdminGroup, secSysGroupPublic, secCluster, secTenant, tableInfo);

		if (bootstrapSession != null && bootstrapSessionID != null && !bootstrapSessionID.isNull() && bootstrapSession.getOptionalFinish() == null) {
			bootstrapSession.setOptionalFinish(LocalDateTime.now());
			bootstrapSession = ICFSecSchema.getBackingCFSec().getTableSecSession().updateSecSession(auth, bootstrapSession);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "$secschemadbname$TransactionManager")
	public void bootstrapAllTablesSecurity(ICFSecAuthorization auth,
		CFLibDbKeyHash256 systemUID,
		ICFSecSecSession bootstrapSession,
		ICFSecSecSysGrp secSystemAdminGroup,
		ICFSecSecSysGrp secSysGroupPublic,
		ICFSecCluster secCluster,
		ICFSecTenant secTenant,
		CFSecTableInfo tableInfo[])
	{
		LocalDateTime now = LocalDateTime.now();

		CFLibDbKeyHash256 bootstrapSessionID = bootstrapSession.getRequiredSecSessionId();
		if (secSystemAdminGroup == null) {
			throw new CFLibNullArgumentException(getClass(), "bootstrapAllTablesSecurity", 0, "secSystemAdminGroup");
		}
		if (secSysGroupPublic == null) {
			throw new CFLibNullArgumentException(getClass(), "bootstrapAllTablesSecurity", 0, "secSysGroupPublic");
		}
		if (secCluster == null) {
			throw new CFLibNullArgumentException(getClass(), "bootstrapAllTablesSecurity", 0, "secCluster");
		}
		if (secTenant == null) {
			throw new CFLibNullArgumentException(getClass(), "bootstrapAllTablesSecurity", 0, "secTenant");
		}

		ICFSecSecSysGrp secSysClusGroupSysAdmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx(auth, secCluster.getRequiredFullDomName().toLowerCase() + "clusteradmin");
		if (secSysClusGroupSysAdmin == null) {
			secSysClusGroupSysAdmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrp().newRec();
			secSysClusGroupSysAdmin.setCreatedAt(now);
			secSysClusGroupSysAdmin.setCreatedByUserId(systemUID);
			secSysClusGroupSysAdmin.setUpdatedAt(now);
			secSysClusGroupSysAdmin.setUpdatedByUserId(systemUID);
			secSysClusGroupSysAdmin.setRequiredName(secCluster.getRequiredFullDomName().toLowerCase() + "clusteradmin");
			secSysClusGroupSysAdmin.setRequiredRevision(1);
			secSysClusGroupSysAdmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().createSecSysGrp(auth, secSysClusGroupSysAdmin);
		}
		CFLibDbKeyHash256 secSysClusGroupSysAdminID = secSysClusGroupSysAdmin.getRequiredSecSysGrpId();

		ICFSecSecSysGrpInc secSysClusGroupSysAdminIncSystemAdmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secSysClusGroupSysAdminID, secSystemAdminGroup.getRequiredName());
		if (secSysClusGroupSysAdminIncSystemAdmin == null) {
			secSysClusGroupSysAdminIncSystemAdmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
			secSysClusGroupSysAdminIncSystemAdmin.setRequiredContainerGroup(secSysClusGroupSysAdminID);
			secSysClusGroupSysAdminIncSystemAdmin.setCreatedAt(now);
			secSysClusGroupSysAdminIncSystemAdmin.setCreatedByUserId(systemUID);
			secSysClusGroupSysAdminIncSystemAdmin.setUpdatedAt(now);
			secSysClusGroupSysAdminIncSystemAdmin.setUpdatedByUserId(systemUID);
			secSysClusGroupSysAdminIncSystemAdmin.setRequiredParentSubGroup(secSystemAdminGroup.getRequiredName());
			secSysClusGroupSysAdminIncSystemAdmin.setRequiredRevision(1);
			secSysClusGroupSysAdminIncSystemAdmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secSysClusGroupSysAdminIncSystemAdmin);
		}

		ICFSecSecSysGrp secSysTentGroupSysAdmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().readDerivedByUNameIdx(auth, secTenant.getRequiredTenantName().toLowerCase() + "tenantadmin");
		if (secSysTentGroupSysAdmin == null) {
			secSysTentGroupSysAdmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrp().newRec();
			secSysTentGroupSysAdmin.setCreatedAt(now);
			secSysTentGroupSysAdmin.setCreatedByUserId(systemUID);
			secSysTentGroupSysAdmin.setUpdatedAt(now);
			secSysTentGroupSysAdmin.setUpdatedByUserId(systemUID);
			secSysTentGroupSysAdmin.setRequiredName(secTenant.getRequiredTenantName().toLowerCase() + "tenantadmin");
			secSysTentGroupSysAdmin.setRequiredRevision(1);
			secSysTentGroupSysAdmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrp().createSecSysGrp(auth, secSysTentGroupSysAdmin);
		}
		CFLibDbKeyHash256 secSysTentGroupSysAdminID = secSysTentGroupSysAdmin.getRequiredSecSysGrpId();

		ICFSecSecSysGrpInc secSysTentGroupSysAdminIncClusadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().readDerived(auth, secSysTentGroupSysAdminID, secSysClusGroupSysAdmin.getRequiredName());
		if (secSysTentGroupSysAdminIncClusadmin == null) {
			secSysTentGroupSysAdminIncClusadmin = ICFSecSchema.getBackingCFSec().getFactorySecSysGrpInc().newRec();
			secSysTentGroupSysAdminIncClusadmin.setRequiredContainerGroup(secSysTentGroupSysAdminID);
			secSysTentGroupSysAdminIncClusadmin.setCreatedAt(now);
			secSysTentGroupSysAdminIncClusadmin.setCreatedByUserId(systemUID);
			secSysTentGroupSysAdminIncClusadmin.setUpdatedAt(now);
			secSysTentGroupSysAdminIncClusadmin.setUpdatedByUserId(systemUID);
			secSysTentGroupSysAdminIncClusadmin.setRequiredParentSubGroup(secSysClusGroupSysAdmin.getRequiredName());
			secSysTentGroupSysAdminIncClusadmin.setRequiredRevision(1);
			secSysTentGroupSysAdminIncClusadmin = ICFSecSchema.getBackingCFSec().getTableSecSysGrpInc().createSecSysGrpInc(auth, secSysTentGroupSysAdminIncClusadmin);
		}

		ICFSecSecClusGrp secClusGroupSysAdmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().readDerivedByUNameIdx(auth, secCluster.getRequiredId(), secCluster.getRequiredFullDomName().toLowerCase() + "clusteradmin");
		if (secClusGroupSysAdmin == null) {
			secClusGroupSysAdmin = ICFSecSchema.getBackingCFSec().getFactorySecClusGrp().newRec();
			secClusGroupSysAdmin.setCreatedAt(now);
			secClusGroupSysAdmin.setCreatedByUserId(systemUID);
			secClusGroupSysAdmin.setUpdatedAt(now);
			secClusGroupSysAdmin.setUpdatedByUserId(systemUID);
			secClusGroupSysAdmin.setRequiredName(secCluster.getRequiredFullDomName().toLowerCase() + "clusteradmin");
			secClusGroupSysAdmin.setRequiredOwnerCluster(secCluster.getRequiredId());
			secClusGroupSysAdmin.setRequiredRevision(1);
			secClusGroupSysAdmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().createSecClusGrp(auth, secClusGroupSysAdmin);
		}
		CFLibDbKeyHash256 secClusGroupSysAdminID = secClusGroupSysAdmin.getRequiredSecClusGrpId();

		ICFSecSecClusGrpInc secClusGroupSysAdminIncSystemAdmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().readDerived(auth, secSysClusGroupSysAdminID, secSystemAdminGroup.getRequiredName());
		if (secClusGroupSysAdminIncSystemAdmin == null) {
			secClusGroupSysAdminIncSystemAdmin = ICFSecSchema.getBackingCFSec().getFactorySecClusGrpInc().newRec();
			secClusGroupSysAdminIncSystemAdmin.setRequiredContainerGroup(secClusGroupSysAdminID);
			secClusGroupSysAdminIncSystemAdmin.setCreatedAt(now);
			secClusGroupSysAdminIncSystemAdmin.setCreatedByUserId(systemUID);
			secClusGroupSysAdminIncSystemAdmin.setUpdatedAt(now);
			secClusGroupSysAdminIncSystemAdmin.setUpdatedByUserId(systemUID);
			secClusGroupSysAdminIncSystemAdmin.setRequiredParentSubGroup(secSystemAdminGroup.getRequiredName());
			secClusGroupSysAdminIncSystemAdmin.setRequiredRevision(1);
			secClusGroupSysAdminIncSystemAdmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().createSecClusGrpInc(auth, secClusGroupSysAdminIncSystemAdmin);
		}

		ICFSecSecClusGrp secClusGroupTentAdmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().readDerivedByUNameIdx(auth, secCluster.getRequiredId(), secTenant.getRequiredTenantName().toLowerCase() + "tenantadmin");
		if (secClusGroupTentAdmin == null) {
			secClusGroupTentAdmin = ICFSecSchema.getBackingCFSec().getFactorySecClusGrp().newRec();
			secClusGroupTentAdmin.setCreatedAt(now);
			secClusGroupTentAdmin.setCreatedByUserId(systemUID);
			secClusGroupTentAdmin.setUpdatedAt(now);
			secClusGroupTentAdmin.setUpdatedByUserId(systemUID);
			secClusGroupTentAdmin.setRequiredName(secTenant.getRequiredTenantName().toLowerCase() + "tenantadmin");
			secClusGroupTentAdmin.setRequiredOwnerCluster(secCluster.getRequiredId());
			secClusGroupTentAdmin.setRequiredRevision(1);
			secClusGroupTentAdmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrp().createSecClusGrp(auth, secClusGroupTentAdmin);
		}
		CFLibDbKeyHash256 secClusGroupTentAdminID = secClusGroupTentAdmin.getRequiredSecClusGrpId();

		ICFSecSecClusGrpInc secClusGroupTentAdminIncClusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().readDerived(auth, secClusGroupTentAdminID, secSysClusGroupSysAdmin.getRequiredName());
		if( secClusGroupTentAdminIncClusadmin == null) {
			secClusGroupTentAdminIncClusadmin = ICFSecSchema.getBackingCFSec().getFactorySecClusGrpInc().newRec();
			secClusGroupTentAdminIncClusadmin.setRequiredContainerGroup(secClusGroupTentAdminID);
			secClusGroupTentAdminIncClusadmin.setCreatedAt(now);
			secClusGroupTentAdminIncClusadmin.setCreatedByUserId(systemUID);
			secClusGroupTentAdminIncClusadmin.setUpdatedAt(now);
			secClusGroupTentAdminIncClusadmin.setUpdatedByUserId(systemUID);
			secClusGroupTentAdminIncClusadmin.setRequiredParentSubGroup(secClusGroupSysAdmin.getRequiredName());
			secClusGroupTentAdminIncClusadmin.setRequiredRevision(1);
			secClusGroupTentAdminIncClusadmin = ICFSecSchema.getBackingCFSec().getTableSecClusGrpInc().createSecClusGrpInc(auth, secClusGroupTentAdminIncClusadmin);
		}

		ICFSecSecTentGrp secTentGroupSysAdmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().readDerivedByUNameIdx(auth, secTenant.getRequiredId(), secTenant.getRequiredTenantName().toLowerCase() + "tenantadmin");
		if (secTentGroupSysAdmin == null) {
			secTentGroupSysAdmin = ICFSecSchema.getBackingCFSec().getFactorySecTentGrp().newRec();
			secTentGroupSysAdmin.setCreatedAt(now);
			secTentGroupSysAdmin.setCreatedByUserId(systemUID);
			secTentGroupSysAdmin.setUpdatedAt(now);
			secTentGroupSysAdmin.setUpdatedByUserId(systemUID);
			secTentGroupSysAdmin.setRequiredName(secTenant.getRequiredTenantName().toLowerCase() + "tenantadmin");
			secTentGroupSysAdmin.setRequiredOwnerTenant(secTenant);
			secTentGroupSysAdmin.setRequiredRevision(1);
			secTentGroupSysAdmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrp().createSecTentGrp(auth, secTentGroupSysAdmin);
		}
		CFLibDbKeyHash256 secTentGroupSysAdminID = secTentGroupSysAdmin.getRequiredSecTentGrpId();

		ICFSecSecTentGrpInc secTentGroupSysAdminIncClusadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().readDerived(auth, secTentGroupSysAdminID, secSysClusGroupSysAdmin.getRequiredName());
		if (secTentGroupSysAdminIncClusadmin == null) {
			secTentGroupSysAdminIncClusadmin = ICFSecSchema.getBackingCFSec().getFactorySecTentGrpInc().newRec();
			secTentGroupSysAdminIncClusadmin.setRequiredContainerGroup(secTentGroupSysAdminID);
			secTentGroupSysAdminIncClusadmin.setCreatedAt(now);
			secTentGroupSysAdminIncClusadmin.setCreatedByUserId(systemUID);
			secTentGroupSysAdminIncClusadmin.setUpdatedAt(now);
			secTentGroupSysAdminIncClusadmin.setUpdatedByUserId(systemUID);
			secTentGroupSysAdminIncClusadmin.setRequiredParentSubGroup(secClusGroupSysAdmin.getRequiredName());
			secTentGroupSysAdminIncClusadmin.setRequiredRevision(1);
			secTentGroupSysAdminIncClusadmin = ICFSecSchema.getBackingCFSec().getTableSecTentGrpInc().createSecTentGrpInc(auth, secTentGroupSysAdminIncClusadmin);
		}
	
		for( CFSecTableInfo info: tableInfo) {
			bootstrapTableSecurity(auth, LocalDateTime.now(), info.getTableName(), info.hasHistory(), info.isMutable(), info.getScope(), secSysGroupPublic, secSystemAdminGroup, secClusGroupSysAdmin, secTentGroupSysAdmin);
		}

		if (bootstrapSession != null && bootstrapSessionID != null && !bootstrapSessionID.isNull() && bootstrapSession.getOptionalFinish() == null) {
			bootstrapSession.setOptionalFinish(LocalDateTime.now());
			bootstrapSession = ICFSecSchema.getBackingCFSec().getTableSecSession().updateSecSession(auth, bootstrapSession);
		}
	}


		// Customized schematweak [CFSec::CFSec].JpaSchemaServiceCustomServices
}

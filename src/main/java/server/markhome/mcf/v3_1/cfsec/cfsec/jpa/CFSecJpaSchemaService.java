// Description: Java 25 Spring JPA Service for CFSec

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
	private CFSecJpaHostNodeService hostnodeService;

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
	private CFSecJpaSecDeviceService secdeviceService;

	@Autowired
	private CFSecJpaSecGroupService secgroupService;

	@Autowired
	private CFSecJpaSecGrpIncService secgrpincService;

	@Autowired
	private CFSecJpaSecGrpMembService secgrpmembService;

	@Autowired
	private CFSecJpaSecSessionService secsessionService;

	@Autowired
	private CFSecJpaSecUserService secuserService;

	@Autowired
	private CFSecJpaServiceService serviceService;

	@Autowired
	private CFSecJpaServiceTypeService servicetypeService;

	@Autowired
	private CFSecJpaSysClusterService sysclusterService;

	@Autowired
	private CFSecJpaTenantService tenantService;

	@Autowired
	private CFSecJpaTSecGroupService tsecgroupService;

	@Autowired
	private CFSecJpaTSecGrpIncService tsecgrpincService;

	@Autowired
	private CFSecJpaTSecGrpMembService tsecgrpmembService;


	public void bootstrapSchema() {
		bootstrapSecurity();
		bootstrapAllTablesSecurity();
	}

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "$secdbschemaname$TransactionManager")
	public void bootstrapSecurity() {
		CFSecJpaSysCluster sysCluster;
		CFLibDbKeyHash256 systemClusterID;
		CFSecJpaCluster systemCluster;
		CFSecJpaSecUser adminUser;
		CFLibDbKeyHash256 adminUID;
		CFSecJpaSecSession bootstrapSession;
		CFLibDbKeyHash256 bootstrapSessionID;
		CFSecJpaTenant systemTenant;
		CFLibDbKeyHash256 systemTenantID;
		CFSecJpaSecGroup secGroupSysadmin;
		CFLibDbKeyHash256 secGroupSysadminID;
		CFSecJpaSecGrpMemb secGroupSysadminMembSysadmin;
		CFLibDbKeyHash256 secGroupSysadminMembSysadminID;
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
			adminUID = systemCluster.getCreatedByUserId();
			if (adminUID == null || adminUID.isNull()) {
				throw new CFLibNullArgumentException(getClass(), "bootstrapSchema", 0, "adminUID");
			}
			adminUser = secuserService.find(adminUID);
			if( adminUser == null) {
				throw new CFLibNullArgumentException(getClass(), "bootstrapSchema", 0, "adminUser");
			}
			systemTenant = tenantService.findByUNameIdx(systemClusterID, "system");
			if( systemTenant == null) {
				systemTenantID = null;
				throw new CFLibNullArgumentException(getClass(), "bootstrapSchema", 0, "systemTenant");
			}
			else {
				systemTenantID = systemTenant.getPKey();
			}
			bootstrapSession = secsessionService.findByStartIdx(adminUID, systemCluster.getCreatedAt());
			if (bootstrapSession == null) {
				List<CFSecJpaSecSession> sessions = secsessionService.findBySecUserIdx(adminUID);
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

			secGroupSysadmin = (CFSecJpaSecGroup)(secgroupService.findByUNameIdx(systemClusterID, "sysadmin"));
			if (secGroupSysadmin == null) {
				throw new CFLibNullArgumentException(getClass(), "bootstrapSchema", 0, "secGroupSysadmin");
			}
			secGroupSysadminID = secGroupSysadmin.getRequiredSecGroupId();

			secGroupSysadminMembSysadmin = (CFSecJpaSecGrpMemb)(secgrpmembService.findByUUserIdx(systemClusterID, secGroupSysadminID, adminUID));
			if (secGroupSysadminMembSysadmin == null) {
				throw new CFLibNullArgumentException(getClass(), "bootstrapSchema", 0, "secGroupSysadminMembSysadmin");
			}
			secGroupSysadminMembSysadminID = secGroupSysadminMembSysadmin.getRequiredSecGrpMembId();
		}
		else {
			sysCluster = null;
			systemCluster = null;
			systemClusterID = null;
			adminUID = null;
			adminUser = null;
			bootstrapSession = null;
			bootstrapSessionID = null;
			systemTenant = null;
			systemTenantID = null;
			secGroupSysadmin = null;
			secGroupSysadminID = null;
			secGroupSysadminMembSysadmin = null;
			secGroupSysadminMembSysadminID = null;
		}
		LocalDateTime now = LocalDateTime.now();
		if (adminUID == null || adminUID.isNull()) {
			adminUID = new CFLibDbKeyHash256(0);
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
		if (secGroupSysadminID == null || secGroupSysadminID.isNull()) {
			secGroupSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupSysadminMembSysadminID == null || secGroupSysadminMembSysadminID.isNull()) {
			secGroupSysadminMembSysadminID = new CFLibDbKeyHash256(0);
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
			ICFSecSchema.setSysAdminId(adminUID);
		}
		else if ( ! ICFSecSchema.getSysAdminId().equals( adminUID )) {
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
			systemCluster.setCreatedByUserId(adminUID);
			systemCluster.setUpdatedByUserId(adminUID);
			systemCluster.setCreatedAt(now);
			systemCluster.setUpdatedAt(now);
			systemCluster.setRequiredFullDomName(fqdn);
			systemCluster.setRequiredDescription("System cluster for " + fqdn);
			systemCluster = clusterService.create(systemCluster);
			systemClusterID = systemCluster.getPKey();
		}
		if (adminUser == null) {
			adminUser = new CFSecJpaSecUser();
			adminUser.setRequiredRevision(1);
			adminUser.setCreatedByUserId(adminUID);
			adminUser.setUpdatedByUserId(adminUID);
			adminUser.setCreatedAt(now);
			adminUser.setUpdatedAt(now);
			adminUser.setRequiredSecUserId(adminUID);
			adminUser.setRequiredLoginId("sysadmin");
			adminUser.setRequiredEMailAddress("sysadmin@" + fqdn);
			adminUser.setRequiredPasswordHash(ICFSecSchema.getPasswordHash("ChangeOnInstall"));
			adminUser = secuserService.create(adminUser);
			adminUID = adminUser.getPKey();
		}
		if (systemTenant == null) {
			systemTenant = new CFSecJpaTenant();
			systemTenant.setRequiredRevision(1);
			systemTenant.setRequiredId(systemTenantID);
			systemTenant.setCreatedByUserId(adminUID);
			systemTenant.setUpdatedByUserId(adminUID);
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
			bootstrapSession.setRequiredSecUserId(adminUID);
			bootstrapSession.setOptionalSecProxyId(adminUID);
			bootstrapSession.setOptionalSecDevName(null);
			bootstrapSession.setRequiredStart(now);
			bootstrapSession.setOptionalFinish(null);
			bootstrapSession = secsessionService.create(bootstrapSession);
		}
//		bootstrapSessionID = bootstrapSession.getPKey();
			
		if (sysCluster == null) {
			sysCluster = new CFSecJpaSysCluster();
			sysCluster.setRequiredContainerCluster(systemClusterID);
			sysCluster = sysclusterService.create(sysCluster);
		}

		if (secGroupSysadmin == null) {
			secGroupSysadmin = new CFSecJpaSecGroup();
			secGroupSysadmin.setRequiredRevision(1);
			secGroupSysadmin.setRequiredContainerCluster(systemClusterID);
			secGroupSysadmin.setRequiredName("sysadmin");
			secGroupSysadmin.setRequiredIsVisible(true);
			secGroupSysadmin.setRequiredSecGroupId(secGroupSysadminID);
			secGroupSysadmin = (CFSecJpaSecGroup)(secgroupService.create(secGroupSysadmin));
			secGroupSysadminID = secGroupSysadmin.getRequiredSecGroupId();
		}

		if (secGroupSysadminMembSysadmin == null) {
			secGroupSysadminMembSysadmin = new CFSecJpaSecGrpMemb();
			secGroupSysadminMembSysadmin.setRequiredRevision(1);
			secGroupSysadminMembSysadmin.setRequiredOwnerCluster(systemClusterID);
			secGroupSysadminMembSysadmin.setRequiredContainerGroup(secGroupSysadminID);
			secGroupSysadminMembSysadmin.setRequiredParentUser(adminUID);
			secGroupSysadminMembSysadmin.setRequiredSecGrpMembId(secGroupSysadminMembSysadminID);
			secGroupSysadminMembSysadmin = (CFSecJpaSecGrpMemb)(secgrpmembService.create(secGroupSysadminMembSysadmin));
			secGroupSysadminMembSysadminID = secGroupSysadminMembSysadmin.getRequiredSecGrpMembId();
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
		bootstrapSession.setOptionalSecDevName(null);
		bootstrapSession.setRequiredStart(now);
		bootstrapSession.setOptionalFinish(null);
		bootstrapSession = ICFSecSchema.getBackingCFSec().getTableSecSession().createSecSession(auth, bootstrapSession);
		bootstrapSessionID = bootstrapSession.getRequiredSecSessionId();

		bootstrapTableSecurity(auth, "Cluster", true, false);
		bootstrapTableSecurity(auth, "HostNode", true, false);
		bootstrapTableSecurity(auth, "ISOCcy", true, false);
		bootstrapTableSecurity(auth, "ISOCtry", true, false);
		bootstrapTableSecurity(auth, "ISOCtryCcy", true, false);
		bootstrapTableSecurity(auth, "ISOCtryLang", true, false);
		bootstrapTableSecurity(auth, "ISOLang", true, false);
		bootstrapTableSecurity(auth, "ISOTZone", true, false);
		bootstrapTableSecurity(auth, "SecDevice", true, false);
		bootstrapTableSecurity(auth, "SecGroup", true, false);
		bootstrapTableSecurity(auth, "SecGrpInc", true, false);
		bootstrapTableSecurity(auth, "SecGrpMemb", true, false);
		bootstrapTableSecurity(auth, "SecSession", false, false);
		bootstrapTableSecurity(auth, "SecUser", true, false);
		bootstrapTableSecurity(auth, "Service", true, false);
		bootstrapTableSecurity(auth, "ServiceType", true, false);
		bootstrapTableSecurity(auth, "SysCluster", false, false);
		bootstrapTableSecurity(auth, "Tenant", true, false);
		bootstrapTableSecurity(auth, "TSecGroup", true, false);
		bootstrapTableSecurity(auth, "TSecGrpInc", true, false);
		bootstrapTableSecurity(auth, "TSecGrpMemb", true, false);

		if (bootstrapSession != null && bootstrapSessionID != null && !bootstrapSessionID.isNull() && bootstrapSession.getOptionalFinish() == null) {
			bootstrapSession.setOptionalFinish(LocalDateTime.now());
			bootstrapSession = ICFSecSchema.getBackingCFSec().getTableSecSession().updateSecSession(auth, bootstrapSession);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "$secdbschemaname$TransactionManager")
	public void bootstrapTableSecurity(ICFSecAuthorization auth, String tableName, boolean hasHistory, boolean isMutable) {
		LocalDateTime now = LocalDateTime.now();
		String lowerTableName = tableName.toLowerCase();
		String createPermName = "create" + lowerTableName;
		String readPermName = "read" + lowerTableName;
		String updatePermName = "update" + lowerTableName;
		String deletePermName = "delete" + lowerTableName;
		String restorePermName = "restore" + lowerTableName;
		String mutatePermName = "mutate" + lowerTableName;
		ICFSecSecGroup secGroupCreate;
		CFLibDbKeyHash256 secGroupCreateID;
		ICFSecSecGrpMemb secGroupCreateMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateMembSysadminID;
		ICFSecSecGroup secGroupRead;
		CFLibDbKeyHash256 secGroupReadID;
		ICFSecSecGrpMemb secGroupReadMembSysadmin;
		CFLibDbKeyHash256 secGroupReadMembSysadminID;
		ICFSecSecGroup secGroupUpdate;
		CFLibDbKeyHash256 secGroupUpdateID;
		ICFSecSecGrpMemb secGroupUpdateMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateMembSysadminID;
		ICFSecSecGroup secGroupDelete;
		CFLibDbKeyHash256 secGroupDeleteID;
		ICFSecSecGrpMemb secGroupDeleteMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteMembSysadminID;
		ICFSecSecGroup secGroupRestore;
		CFLibDbKeyHash256 secGroupRestoreID;
		ICFSecSecGrpMemb secGroupRestoreMembSysadmin;
		CFLibDbKeyHash256 secGroupRestoreMembSysadminID;
		ICFSecSecGroup secGroupMutate;
		CFLibDbKeyHash256 secGroupMutateID;
		ICFSecSecGrpMemb secGroupMutateMembSysadmin;
		CFLibDbKeyHash256 secGroupMutateMembSysadminID;

		secGroupCreate = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), createPermName);
		if (secGroupCreate != null) {
			secGroupCreateID = secGroupCreate.getRequiredSecGroupId();
			secGroupCreateMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateMembSysadmin != null) {
				secGroupCreateMembSysadminID = secGroupCreateMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateMembSysadminID = null;
			}
		}
		else {
			secGroupCreateID = null;
			secGroupCreateMembSysadmin = null;
			secGroupCreateMembSysadminID = null;
		}

		secGroupRead = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), readPermName);
		if (secGroupRead != null) {
			secGroupReadID = secGroupRead.getRequiredSecGroupId();
			secGroupReadMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadID, ICFSecSchema.getSysAdminId());
			if (secGroupReadMembSysadmin != null) {
				secGroupReadMembSysadminID = secGroupReadMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadMembSysadminID = null;
			}
		}
		else {
			secGroupReadID = null;
			secGroupReadMembSysadmin = null;
			secGroupReadMembSysadminID = null;
		}

		secGroupUpdate = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), updatePermName);
		if (secGroupUpdate != null) {
			secGroupUpdateID = secGroupUpdate.getRequiredSecGroupId();
			secGroupUpdateMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateMembSysadmin != null) {
				secGroupUpdateMembSysadminID = secGroupUpdateMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateID = null;
			secGroupUpdateMembSysadmin = null;
			secGroupUpdateMembSysadminID = null;
		}

		secGroupDelete = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), deletePermName);
		if (secGroupDelete != null) {
			secGroupDeleteID = secGroupDelete.getRequiredSecGroupId();
			secGroupDeleteMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteMembSysadmin != null) {
				secGroupDeleteMembSysadminID = secGroupDeleteMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteID = null;
			secGroupDeleteMembSysadmin = null;
			secGroupDeleteMembSysadminID = null;
		}

		if (hasHistory) {
			secGroupRestore = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), restorePermName);
			if (secGroupRestore != null) {
				secGroupRestoreID = secGroupRestore.getRequiredSecGroupId();
				secGroupRestoreMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupRestoreID, ICFSecSchema.getSysAdminId());
				if (secGroupRestoreMembSysadmin != null) {
					secGroupRestoreMembSysadminID = secGroupRestoreMembSysadmin.getRequiredSecGrpMembId();
				}
				else {
					secGroupRestoreMembSysadminID = null;
				}
			}
			else {
				secGroupRestoreID = null;
				secGroupRestoreMembSysadmin = null;
				secGroupRestoreMembSysadminID = null;
			}
		}
		else {
			secGroupRestore = null;
			secGroupRestoreID = null;
			secGroupRestoreMembSysadmin = null;
			secGroupRestoreMembSysadminID = null;
		}

		if (isMutable) {
			secGroupMutate = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), mutatePermName);
			if (secGroupMutate != null) {
				secGroupMutateID = secGroupMutate.getRequiredSecGroupId();
				secGroupMutateMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupMutateID, ICFSecSchema.getSysAdminId());
				if (secGroupMutateMembSysadmin != null) {
					secGroupMutateMembSysadminID = secGroupMutateMembSysadmin.getRequiredSecGrpMembId();
				}
				else {
					secGroupMutateMembSysadminID = null;
				}
			}
			else {
				secGroupMutateID = null;
				secGroupMutateMembSysadmin = null;
				secGroupMutateMembSysadminID = null;
			}
		}
		else {
			secGroupMutate = null;
			secGroupMutateID = null;
			secGroupMutateMembSysadmin = null;
			secGroupMutateMembSysadminID = null;
		}
		
		if (secGroupCreateID == null || secGroupCreateID.isNull()) {
			secGroupCreateID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateMembSysadminID == null || secGroupCreateMembSysadminID.isNull()) {
			secGroupCreateMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadID == null || secGroupReadID.isNull()) {
			secGroupReadID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadMembSysadminID == null || secGroupReadMembSysadminID.isNull()) {
			secGroupReadMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateID == null || secGroupUpdateID.isNull()) {
			secGroupUpdateID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateMembSysadminID == null || secGroupUpdateMembSysadminID.isNull()) {
			secGroupUpdateMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteID == null || secGroupDeleteID.isNull()) {
			secGroupDeleteID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteMembSysadminID == null || secGroupDeleteMembSysadminID.isNull()) {
			secGroupDeleteMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (hasHistory) {
			if (secGroupRestoreID == null || secGroupRestoreID.isNull()) {
				secGroupRestoreID = new CFLibDbKeyHash256(0);
			}
			if (secGroupRestoreMembSysadminID == null || secGroupRestoreMembSysadminID.isNull()) {
				secGroupRestoreMembSysadminID = new CFLibDbKeyHash256(0);
			}
		}
		if (isMutable) {
			if (secGroupMutateID == null || secGroupMutateID.isNull()) {
				secGroupMutateID = new CFLibDbKeyHash256(0);
			}
			if (secGroupMutateMembSysadminID == null || secGroupMutateMembSysadminID.isNull()) {
				secGroupMutateMembSysadminID = new CFLibDbKeyHash256(0);
			}
		}

		if (secGroupCreate == null) {
			secGroupCreate = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreate.setRequiredRevision(1);
			secGroupCreate.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreate.setRequiredName(createPermName);
			secGroupCreate.setRequiredIsVisible(true);
			secGroupCreate.setRequiredSecGroupId(secGroupCreateID);
			secGroupCreate = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreate);
			secGroupCreateID = secGroupCreate.getRequiredSecGroupId();
		}

		if (secGroupCreateMembSysadmin == null) {
			secGroupCreateMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateMembSysadmin.setRequiredRevision(1);
			secGroupCreateMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateMembSysadmin.setRequiredContainerGroup(secGroupCreateID);
			secGroupCreateMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateMembSysadmin.setRequiredSecGrpMembId(secGroupCreateMembSysadminID);
			secGroupCreateMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateMembSysadmin);
			secGroupCreateMembSysadminID = secGroupCreateMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupRead == null) {
			secGroupRead = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupRead.setRequiredRevision(1);
			secGroupRead.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupRead.setRequiredName(readPermName);
			secGroupRead.setRequiredIsVisible(true);
			secGroupRead.setRequiredSecGroupId(secGroupReadID);
			secGroupRead = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupRead);
			secGroupReadID = secGroupRead.getRequiredSecGroupId();
		}

		if (secGroupReadMembSysadmin == null) {
			secGroupReadMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadMembSysadmin.setRequiredRevision(1);
			secGroupReadMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadMembSysadmin.setRequiredContainerGroup(secGroupReadID);
			secGroupReadMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadMembSysadmin.setRequiredSecGrpMembId(secGroupReadMembSysadminID);
			secGroupReadMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadMembSysadmin);
			secGroupReadMembSysadminID = secGroupReadMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdate == null) {
			secGroupUpdate = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdate.setRequiredRevision(1);
			secGroupUpdate.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdate.setRequiredName(updatePermName);
			secGroupUpdate.setRequiredIsVisible(true);
			secGroupUpdate.setRequiredSecGroupId(secGroupUpdateID);
			secGroupUpdate = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdate);
			secGroupUpdateID = secGroupUpdate.getRequiredSecGroupId();
		}

		if (secGroupUpdateMembSysadmin == null) {
			secGroupUpdateMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateMembSysadmin.setRequiredRevision(1);
			secGroupUpdateMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateMembSysadmin.setRequiredContainerGroup(secGroupUpdateID);
			secGroupUpdateMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateMembSysadminID);
			secGroupUpdateMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateMembSysadmin);
			secGroupUpdateMembSysadminID = secGroupUpdateMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDelete == null) {
			secGroupDelete = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDelete.setRequiredRevision(1);
			secGroupDelete.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDelete.setRequiredName(deletePermName);
			secGroupDelete.setRequiredIsVisible(true);
			secGroupDelete.setRequiredSecGroupId(secGroupDeleteID);
			secGroupDelete = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDelete);
			secGroupDeleteID = secGroupDelete.getRequiredSecGroupId();
		}

		if (secGroupDeleteMembSysadmin == null) {
			secGroupDeleteMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteMembSysadmin.setRequiredRevision(1);
			secGroupDeleteMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteMembSysadmin.setRequiredContainerGroup(secGroupDeleteID);
			secGroupDeleteMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteMembSysadminID);
			secGroupDeleteMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteMembSysadmin);
			secGroupDeleteMembSysadminID = secGroupDeleteMembSysadmin.getRequiredSecGrpMembId();
		}
		
		if (hasHistory) {
			if (secGroupRestore == null) {
				secGroupRestore = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
				secGroupRestore.setRequiredRevision(1);
				secGroupRestore.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
				secGroupRestore.setRequiredName(restorePermName);
				secGroupRestore.setRequiredIsVisible(true);
				secGroupRestore.setRequiredSecGroupId(secGroupRestoreID);
				secGroupRestore = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupRestore);
				secGroupRestoreID = secGroupRestore.getRequiredSecGroupId();
			}

			if (secGroupRestoreMembSysadmin == null) {
				secGroupRestoreMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
				secGroupRestoreMembSysadmin.setRequiredRevision(1);
				secGroupRestoreMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
				secGroupRestoreMembSysadmin.setRequiredContainerGroup(secGroupRestoreID);
				secGroupRestoreMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
				secGroupRestoreMembSysadmin.setRequiredSecGrpMembId(secGroupRestoreMembSysadminID);
				secGroupRestoreMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupRestoreMembSysadmin);
				secGroupRestoreMembSysadminID = secGroupRestoreMembSysadmin.getRequiredSecGrpMembId();
			}
		}
		
		if (isMutable) {
			if (secGroupMutate == null) {
				secGroupMutate = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
				secGroupMutate.setRequiredRevision(1);
				secGroupMutate.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
				secGroupMutate.setRequiredName(mutatePermName);
				secGroupMutate.setRequiredIsVisible(true);
				secGroupMutate.setRequiredSecGroupId(secGroupMutateID);
				secGroupMutate = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupMutate);
				secGroupMutateID = secGroupMutate.getRequiredSecGroupId();
			}

			if (secGroupMutateMembSysadmin == null) {
				secGroupMutateMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
				secGroupMutateMembSysadmin.setRequiredRevision(1);
				secGroupMutateMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
				secGroupMutateMembSysadmin.setRequiredContainerGroup(secGroupMutateID);
				secGroupMutateMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
				secGroupMutateMembSysadmin.setRequiredSecGrpMembId(secGroupMutateMembSysadminID);
				secGroupMutateMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupMutateMembSysadmin);
				secGroupMutateMembSysadminID = secGroupMutateMembSysadmin.getRequiredSecGrpMembId();
			}
		}
	}		


}

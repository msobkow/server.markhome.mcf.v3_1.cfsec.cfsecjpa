// Description: Java 25 Spring JPA Service for CFSec

/*
 *	io.github.msobkow.CFSec
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

package io.github.msobkow.v3_1.cfsec.cfsec.jpa;

import java.io.Serializable;
import java.math.*;
import java.net.InetAddress;
import java.time.*;
import java.util.*;
import jakarta.persistence.*;
import io.github.msobkow.v3_1.cflib.*;
import io.github.msobkow.v3_1.cflib.dbutil.*;
import io.github.msobkow.v3_1.cflib.xml.CFLibXmlUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import io.github.msobkow.v3_1.cfsec.cfsec.*;

/**
 *	Services for schema CFSec defined in io.github.msobkow.v3_1.cfsec.cfsec.jpa
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

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
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

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
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

		bootstrapTableClusterSecurity(auth);
		bootstrapTableHostNodeSecurity(auth);
		bootstrapTableISOCcySecurity(auth);
		bootstrapTableISOCtrySecurity(auth);
		bootstrapTableISOCtryCcySecurity(auth);
		bootstrapTableISOCtryLangSecurity(auth);
		bootstrapTableISOLangSecurity(auth);
		bootstrapTableISOTZoneSecurity(auth);
		bootstrapTableSecDeviceSecurity(auth);
		bootstrapTableSecGroupSecurity(auth);
		bootstrapTableSecGrpIncSecurity(auth);
		bootstrapTableSecGrpMembSecurity(auth);
		bootstrapTableSecSessionSecurity(auth);
		bootstrapTableSecUserSecurity(auth);
		bootstrapTableServiceSecurity(auth);
		bootstrapTableServiceTypeSecurity(auth);
		bootstrapTableSysClusterSecurity(auth);
		bootstrapTableTenantSecurity(auth);
		bootstrapTableTSecGroupSecurity(auth);
		bootstrapTableTSecGrpIncSecurity(auth);
		bootstrapTableTSecGrpMembSecurity(auth);

		if (bootstrapSession != null && bootstrapSessionID != null && !bootstrapSessionID.isNull() && bootstrapSession.getOptionalFinish() == null) {
			bootstrapSession.setOptionalFinish(LocalDateTime.now());
			bootstrapSession = ICFSecSchema.getBackingCFSec().getTableSecSession().updateSecSession(auth, bootstrapSession);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableClusterSecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateCluster;
		CFLibDbKeyHash256 secGroupCreateClusterID;
		ICFSecSecGrpMemb secGroupCreateClusterMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateClusterMembSysadminID;
		ICFSecSecGroup secGroupReadCluster;
		CFLibDbKeyHash256 secGroupReadClusterID;
		ICFSecSecGrpMemb secGroupReadClusterMembSysadmin;
		CFLibDbKeyHash256 secGroupReadClusterMembSysadminID;
		ICFSecSecGroup secGroupUpdateCluster;
		CFLibDbKeyHash256 secGroupUpdateClusterID;
		ICFSecSecGrpMemb secGroupUpdateClusterMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateClusterMembSysadminID;
		ICFSecSecGroup secGroupDeleteCluster;
		CFLibDbKeyHash256 secGroupDeleteClusterID;
		ICFSecSecGrpMemb secGroupDeleteClusterMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteClusterMembSysadminID;

		secGroupCreateCluster = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateCluster");
		if (secGroupCreateCluster != null) {
			secGroupCreateClusterID = secGroupCreateCluster.getRequiredSecGroupId();
			secGroupCreateClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateClusterID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateClusterMembSysadmin != null) {
				secGroupCreateClusterMembSysadminID = secGroupCreateClusterMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateClusterMembSysadminID = null;
			}
		}
		else {
			secGroupCreateClusterID = null;
			secGroupCreateClusterMembSysadmin = null;
			secGroupCreateClusterMembSysadminID = null;
		}

		secGroupReadCluster = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadCluster");
		if (secGroupReadCluster != null) {
			secGroupReadClusterID = secGroupReadCluster.getRequiredSecGroupId();
			secGroupReadClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadClusterID, ICFSecSchema.getSysAdminId());
			if (secGroupReadClusterMembSysadmin != null) {
				secGroupReadClusterMembSysadminID = secGroupReadClusterMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadClusterMembSysadminID = null;
			}
		}
		else {
			secGroupReadClusterID = null;
			secGroupReadClusterMembSysadmin = null;
			secGroupReadClusterMembSysadminID = null;
		}

		secGroupUpdateCluster = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateCluster");
		if (secGroupUpdateCluster != null) {
			secGroupUpdateClusterID = secGroupUpdateCluster.getRequiredSecGroupId();
			secGroupUpdateClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateClusterID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateClusterMembSysadmin != null) {
				secGroupUpdateClusterMembSysadminID = secGroupUpdateClusterMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateClusterMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateClusterID = null;
			secGroupUpdateClusterMembSysadmin = null;
			secGroupUpdateClusterMembSysadminID = null;
		}

		secGroupDeleteCluster = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteCluster");
		if (secGroupDeleteCluster != null) {
			secGroupDeleteClusterID = secGroupDeleteCluster.getRequiredSecGroupId();
			secGroupDeleteClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteClusterID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteClusterMembSysadmin != null) {
				secGroupDeleteClusterMembSysadminID = secGroupDeleteClusterMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteClusterMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteClusterID = null;
			secGroupDeleteClusterMembSysadmin = null;
			secGroupDeleteClusterMembSysadminID = null;
		}


		if (secGroupCreateClusterID == null || secGroupCreateClusterID.isNull()) {
			secGroupCreateClusterID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateClusterMembSysadminID == null || secGroupCreateClusterMembSysadminID.isNull()) {
			secGroupCreateClusterMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadClusterID == null || secGroupReadClusterID.isNull()) {
			secGroupReadClusterID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadClusterMembSysadminID == null || secGroupReadClusterMembSysadminID.isNull()) {
			secGroupReadClusterMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateClusterID == null || secGroupUpdateClusterID.isNull()) {
			secGroupUpdateClusterID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateClusterMembSysadminID == null || secGroupUpdateClusterMembSysadminID.isNull()) {
			secGroupUpdateClusterMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteClusterID == null || secGroupDeleteClusterID.isNull()) {
			secGroupDeleteClusterID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteClusterMembSysadminID == null || secGroupDeleteClusterMembSysadminID.isNull()) {
			secGroupDeleteClusterMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateCluster == null) {
			secGroupCreateCluster = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateCluster.setRequiredRevision(1);
			secGroupCreateCluster.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateCluster.setRequiredName("CreateCluster");
			secGroupCreateCluster.setRequiredIsVisible(true);
			secGroupCreateCluster.setRequiredSecGroupId(secGroupCreateClusterID);
			secGroupCreateCluster = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateCluster);
			secGroupCreateClusterID = secGroupCreateCluster.getRequiredSecGroupId();
		}

		if (secGroupCreateClusterMembSysadmin == null) {
			secGroupCreateClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateClusterMembSysadmin.setRequiredRevision(1);
			secGroupCreateClusterMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateClusterMembSysadmin.setRequiredContainerGroup(secGroupCreateClusterID);
			secGroupCreateClusterMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateClusterMembSysadmin.setRequiredSecGrpMembId(secGroupCreateClusterMembSysadminID);
			secGroupCreateClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateClusterMembSysadmin);
			secGroupCreateClusterMembSysadminID = secGroupCreateClusterMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadCluster == null) {
			secGroupReadCluster = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadCluster.setRequiredRevision(1);
			secGroupReadCluster.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadCluster.setRequiredName("ReadCluster");
			secGroupReadCluster.setRequiredIsVisible(true);
			secGroupReadCluster.setRequiredSecGroupId(secGroupReadClusterID);
			secGroupReadCluster = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadCluster);
			secGroupReadClusterID = secGroupReadCluster.getRequiredSecGroupId();
		}

		if (secGroupReadClusterMembSysadmin == null) {
			secGroupReadClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadClusterMembSysadmin.setRequiredRevision(1);
			secGroupReadClusterMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadClusterMembSysadmin.setRequiredContainerGroup(secGroupReadClusterID);
			secGroupReadClusterMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadClusterMembSysadmin.setRequiredSecGrpMembId(secGroupReadClusterMembSysadminID);
			secGroupReadClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadClusterMembSysadmin);
			secGroupReadClusterMembSysadminID = secGroupReadClusterMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateCluster == null) {
			secGroupUpdateCluster = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateCluster.setRequiredRevision(1);
			secGroupUpdateCluster.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateCluster.setRequiredName("UpdateCluster");
			secGroupUpdateCluster.setRequiredIsVisible(true);
			secGroupUpdateCluster.setRequiredSecGroupId(secGroupUpdateClusterID);
			secGroupUpdateCluster = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateCluster);
			secGroupUpdateClusterID = secGroupUpdateCluster.getRequiredSecGroupId();
		}

		if (secGroupUpdateClusterMembSysadmin == null) {
			secGroupUpdateClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateClusterMembSysadmin.setRequiredRevision(1);
			secGroupUpdateClusterMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateClusterMembSysadmin.setRequiredContainerGroup(secGroupUpdateClusterID);
			secGroupUpdateClusterMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateClusterMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateClusterMembSysadminID);
			secGroupUpdateClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateClusterMembSysadmin);
			secGroupUpdateClusterMembSysadminID = secGroupUpdateClusterMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteCluster == null) {
			secGroupDeleteCluster = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteCluster.setRequiredRevision(1);
			secGroupDeleteCluster.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteCluster.setRequiredName("DeleteCluster");
			secGroupDeleteCluster.setRequiredIsVisible(true);
			secGroupDeleteCluster.setRequiredSecGroupId(secGroupDeleteClusterID);
			secGroupDeleteCluster = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteCluster);
			secGroupDeleteClusterID = secGroupDeleteCluster.getRequiredSecGroupId();
		}

		if (secGroupDeleteClusterMembSysadmin == null) {
			secGroupDeleteClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteClusterMembSysadmin.setRequiredRevision(1);
			secGroupDeleteClusterMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteClusterMembSysadmin.setRequiredContainerGroup(secGroupDeleteClusterID);
			secGroupDeleteClusterMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteClusterMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteClusterMembSysadminID);
			secGroupDeleteClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteClusterMembSysadmin);
			secGroupDeleteClusterMembSysadminID = secGroupDeleteClusterMembSysadmin.getRequiredSecGrpMembId();
		}

	}		

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableHostNodeSecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateHostNode;
		CFLibDbKeyHash256 secGroupCreateHostNodeID;
		ICFSecSecGrpMemb secGroupCreateHostNodeMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateHostNodeMembSysadminID;
		ICFSecSecGroup secGroupReadHostNode;
		CFLibDbKeyHash256 secGroupReadHostNodeID;
		ICFSecSecGrpMemb secGroupReadHostNodeMembSysadmin;
		CFLibDbKeyHash256 secGroupReadHostNodeMembSysadminID;
		ICFSecSecGroup secGroupUpdateHostNode;
		CFLibDbKeyHash256 secGroupUpdateHostNodeID;
		ICFSecSecGrpMemb secGroupUpdateHostNodeMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateHostNodeMembSysadminID;
		ICFSecSecGroup secGroupDeleteHostNode;
		CFLibDbKeyHash256 secGroupDeleteHostNodeID;
		ICFSecSecGrpMemb secGroupDeleteHostNodeMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteHostNodeMembSysadminID;

		secGroupCreateHostNode = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateHostNode");
		if (secGroupCreateHostNode != null) {
			secGroupCreateHostNodeID = secGroupCreateHostNode.getRequiredSecGroupId();
			secGroupCreateHostNodeMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateHostNodeID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateHostNodeMembSysadmin != null) {
				secGroupCreateHostNodeMembSysadminID = secGroupCreateHostNodeMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateHostNodeMembSysadminID = null;
			}
		}
		else {
			secGroupCreateHostNodeID = null;
			secGroupCreateHostNodeMembSysadmin = null;
			secGroupCreateHostNodeMembSysadminID = null;
		}

		secGroupReadHostNode = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadHostNode");
		if (secGroupReadHostNode != null) {
			secGroupReadHostNodeID = secGroupReadHostNode.getRequiredSecGroupId();
			secGroupReadHostNodeMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadHostNodeID, ICFSecSchema.getSysAdminId());
			if (secGroupReadHostNodeMembSysadmin != null) {
				secGroupReadHostNodeMembSysadminID = secGroupReadHostNodeMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadHostNodeMembSysadminID = null;
			}
		}
		else {
			secGroupReadHostNodeID = null;
			secGroupReadHostNodeMembSysadmin = null;
			secGroupReadHostNodeMembSysadminID = null;
		}

		secGroupUpdateHostNode = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateHostNode");
		if (secGroupUpdateHostNode != null) {
			secGroupUpdateHostNodeID = secGroupUpdateHostNode.getRequiredSecGroupId();
			secGroupUpdateHostNodeMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateHostNodeID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateHostNodeMembSysadmin != null) {
				secGroupUpdateHostNodeMembSysadminID = secGroupUpdateHostNodeMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateHostNodeMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateHostNodeID = null;
			secGroupUpdateHostNodeMembSysadmin = null;
			secGroupUpdateHostNodeMembSysadminID = null;
		}

		secGroupDeleteHostNode = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteHostNode");
		if (secGroupDeleteHostNode != null) {
			secGroupDeleteHostNodeID = secGroupDeleteHostNode.getRequiredSecGroupId();
			secGroupDeleteHostNodeMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteHostNodeID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteHostNodeMembSysadmin != null) {
				secGroupDeleteHostNodeMembSysadminID = secGroupDeleteHostNodeMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteHostNodeMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteHostNodeID = null;
			secGroupDeleteHostNodeMembSysadmin = null;
			secGroupDeleteHostNodeMembSysadminID = null;
		}


		if (secGroupCreateHostNodeID == null || secGroupCreateHostNodeID.isNull()) {
			secGroupCreateHostNodeID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateHostNodeMembSysadminID == null || secGroupCreateHostNodeMembSysadminID.isNull()) {
			secGroupCreateHostNodeMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadHostNodeID == null || secGroupReadHostNodeID.isNull()) {
			secGroupReadHostNodeID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadHostNodeMembSysadminID == null || secGroupReadHostNodeMembSysadminID.isNull()) {
			secGroupReadHostNodeMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateHostNodeID == null || secGroupUpdateHostNodeID.isNull()) {
			secGroupUpdateHostNodeID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateHostNodeMembSysadminID == null || secGroupUpdateHostNodeMembSysadminID.isNull()) {
			secGroupUpdateHostNodeMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteHostNodeID == null || secGroupDeleteHostNodeID.isNull()) {
			secGroupDeleteHostNodeID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteHostNodeMembSysadminID == null || secGroupDeleteHostNodeMembSysadminID.isNull()) {
			secGroupDeleteHostNodeMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateHostNode == null) {
			secGroupCreateHostNode = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateHostNode.setRequiredRevision(1);
			secGroupCreateHostNode.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateHostNode.setRequiredName("CreateHostNode");
			secGroupCreateHostNode.setRequiredIsVisible(true);
			secGroupCreateHostNode.setRequiredSecGroupId(secGroupCreateHostNodeID);
			secGroupCreateHostNode = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateHostNode);
			secGroupCreateHostNodeID = secGroupCreateHostNode.getRequiredSecGroupId();
		}

		if (secGroupCreateHostNodeMembSysadmin == null) {
			secGroupCreateHostNodeMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateHostNodeMembSysadmin.setRequiredRevision(1);
			secGroupCreateHostNodeMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateHostNodeMembSysadmin.setRequiredContainerGroup(secGroupCreateHostNodeID);
			secGroupCreateHostNodeMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateHostNodeMembSysadmin.setRequiredSecGrpMembId(secGroupCreateHostNodeMembSysadminID);
			secGroupCreateHostNodeMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateHostNodeMembSysadmin);
			secGroupCreateHostNodeMembSysadminID = secGroupCreateHostNodeMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadHostNode == null) {
			secGroupReadHostNode = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadHostNode.setRequiredRevision(1);
			secGroupReadHostNode.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadHostNode.setRequiredName("ReadHostNode");
			secGroupReadHostNode.setRequiredIsVisible(true);
			secGroupReadHostNode.setRequiredSecGroupId(secGroupReadHostNodeID);
			secGroupReadHostNode = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadHostNode);
			secGroupReadHostNodeID = secGroupReadHostNode.getRequiredSecGroupId();
		}

		if (secGroupReadHostNodeMembSysadmin == null) {
			secGroupReadHostNodeMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadHostNodeMembSysadmin.setRequiredRevision(1);
			secGroupReadHostNodeMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadHostNodeMembSysadmin.setRequiredContainerGroup(secGroupReadHostNodeID);
			secGroupReadHostNodeMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadHostNodeMembSysadmin.setRequiredSecGrpMembId(secGroupReadHostNodeMembSysadminID);
			secGroupReadHostNodeMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadHostNodeMembSysadmin);
			secGroupReadHostNodeMembSysadminID = secGroupReadHostNodeMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateHostNode == null) {
			secGroupUpdateHostNode = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateHostNode.setRequiredRevision(1);
			secGroupUpdateHostNode.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateHostNode.setRequiredName("UpdateHostNode");
			secGroupUpdateHostNode.setRequiredIsVisible(true);
			secGroupUpdateHostNode.setRequiredSecGroupId(secGroupUpdateHostNodeID);
			secGroupUpdateHostNode = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateHostNode);
			secGroupUpdateHostNodeID = secGroupUpdateHostNode.getRequiredSecGroupId();
		}

		if (secGroupUpdateHostNodeMembSysadmin == null) {
			secGroupUpdateHostNodeMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateHostNodeMembSysadmin.setRequiredRevision(1);
			secGroupUpdateHostNodeMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateHostNodeMembSysadmin.setRequiredContainerGroup(secGroupUpdateHostNodeID);
			secGroupUpdateHostNodeMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateHostNodeMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateHostNodeMembSysadminID);
			secGroupUpdateHostNodeMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateHostNodeMembSysadmin);
			secGroupUpdateHostNodeMembSysadminID = secGroupUpdateHostNodeMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteHostNode == null) {
			secGroupDeleteHostNode = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteHostNode.setRequiredRevision(1);
			secGroupDeleteHostNode.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteHostNode.setRequiredName("DeleteHostNode");
			secGroupDeleteHostNode.setRequiredIsVisible(true);
			secGroupDeleteHostNode.setRequiredSecGroupId(secGroupDeleteHostNodeID);
			secGroupDeleteHostNode = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteHostNode);
			secGroupDeleteHostNodeID = secGroupDeleteHostNode.getRequiredSecGroupId();
		}

		if (secGroupDeleteHostNodeMembSysadmin == null) {
			secGroupDeleteHostNodeMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteHostNodeMembSysadmin.setRequiredRevision(1);
			secGroupDeleteHostNodeMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteHostNodeMembSysadmin.setRequiredContainerGroup(secGroupDeleteHostNodeID);
			secGroupDeleteHostNodeMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteHostNodeMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteHostNodeMembSysadminID);
			secGroupDeleteHostNodeMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteHostNodeMembSysadmin);
			secGroupDeleteHostNodeMembSysadminID = secGroupDeleteHostNodeMembSysadmin.getRequiredSecGrpMembId();
		}

	}		

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableISOCcySecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateISOCcy;
		CFLibDbKeyHash256 secGroupCreateISOCcyID;
		ICFSecSecGrpMemb secGroupCreateISOCcyMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateISOCcyMembSysadminID;
		ICFSecSecGroup secGroupReadISOCcy;
		CFLibDbKeyHash256 secGroupReadISOCcyID;
		ICFSecSecGrpMemb secGroupReadISOCcyMembSysadmin;
		CFLibDbKeyHash256 secGroupReadISOCcyMembSysadminID;
		ICFSecSecGroup secGroupUpdateISOCcy;
		CFLibDbKeyHash256 secGroupUpdateISOCcyID;
		ICFSecSecGrpMemb secGroupUpdateISOCcyMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateISOCcyMembSysadminID;
		ICFSecSecGroup secGroupDeleteISOCcy;
		CFLibDbKeyHash256 secGroupDeleteISOCcyID;
		ICFSecSecGrpMemb secGroupDeleteISOCcyMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteISOCcyMembSysadminID;

		secGroupCreateISOCcy = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateISOCcy");
		if (secGroupCreateISOCcy != null) {
			secGroupCreateISOCcyID = secGroupCreateISOCcy.getRequiredSecGroupId();
			secGroupCreateISOCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateISOCcyID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateISOCcyMembSysadmin != null) {
				secGroupCreateISOCcyMembSysadminID = secGroupCreateISOCcyMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateISOCcyMembSysadminID = null;
			}
		}
		else {
			secGroupCreateISOCcyID = null;
			secGroupCreateISOCcyMembSysadmin = null;
			secGroupCreateISOCcyMembSysadminID = null;
		}

		secGroupReadISOCcy = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadISOCcy");
		if (secGroupReadISOCcy != null) {
			secGroupReadISOCcyID = secGroupReadISOCcy.getRequiredSecGroupId();
			secGroupReadISOCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadISOCcyID, ICFSecSchema.getSysAdminId());
			if (secGroupReadISOCcyMembSysadmin != null) {
				secGroupReadISOCcyMembSysadminID = secGroupReadISOCcyMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadISOCcyMembSysadminID = null;
			}
		}
		else {
			secGroupReadISOCcyID = null;
			secGroupReadISOCcyMembSysadmin = null;
			secGroupReadISOCcyMembSysadminID = null;
		}

		secGroupUpdateISOCcy = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateISOCcy");
		if (secGroupUpdateISOCcy != null) {
			secGroupUpdateISOCcyID = secGroupUpdateISOCcy.getRequiredSecGroupId();
			secGroupUpdateISOCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateISOCcyID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateISOCcyMembSysadmin != null) {
				secGroupUpdateISOCcyMembSysadminID = secGroupUpdateISOCcyMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateISOCcyMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateISOCcyID = null;
			secGroupUpdateISOCcyMembSysadmin = null;
			secGroupUpdateISOCcyMembSysadminID = null;
		}

		secGroupDeleteISOCcy = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteISOCcy");
		if (secGroupDeleteISOCcy != null) {
			secGroupDeleteISOCcyID = secGroupDeleteISOCcy.getRequiredSecGroupId();
			secGroupDeleteISOCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteISOCcyID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteISOCcyMembSysadmin != null) {
				secGroupDeleteISOCcyMembSysadminID = secGroupDeleteISOCcyMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteISOCcyMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteISOCcyID = null;
			secGroupDeleteISOCcyMembSysadmin = null;
			secGroupDeleteISOCcyMembSysadminID = null;
		}


		if (secGroupCreateISOCcyID == null || secGroupCreateISOCcyID.isNull()) {
			secGroupCreateISOCcyID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateISOCcyMembSysadminID == null || secGroupCreateISOCcyMembSysadminID.isNull()) {
			secGroupCreateISOCcyMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadISOCcyID == null || secGroupReadISOCcyID.isNull()) {
			secGroupReadISOCcyID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadISOCcyMembSysadminID == null || secGroupReadISOCcyMembSysadminID.isNull()) {
			secGroupReadISOCcyMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateISOCcyID == null || secGroupUpdateISOCcyID.isNull()) {
			secGroupUpdateISOCcyID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateISOCcyMembSysadminID == null || secGroupUpdateISOCcyMembSysadminID.isNull()) {
			secGroupUpdateISOCcyMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteISOCcyID == null || secGroupDeleteISOCcyID.isNull()) {
			secGroupDeleteISOCcyID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteISOCcyMembSysadminID == null || secGroupDeleteISOCcyMembSysadminID.isNull()) {
			secGroupDeleteISOCcyMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateISOCcy == null) {
			secGroupCreateISOCcy = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateISOCcy.setRequiredRevision(1);
			secGroupCreateISOCcy.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateISOCcy.setRequiredName("CreateISOCcy");
			secGroupCreateISOCcy.setRequiredIsVisible(true);
			secGroupCreateISOCcy.setRequiredSecGroupId(secGroupCreateISOCcyID);
			secGroupCreateISOCcy = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateISOCcy);
			secGroupCreateISOCcyID = secGroupCreateISOCcy.getRequiredSecGroupId();
		}

		if (secGroupCreateISOCcyMembSysadmin == null) {
			secGroupCreateISOCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateISOCcyMembSysadmin.setRequiredRevision(1);
			secGroupCreateISOCcyMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateISOCcyMembSysadmin.setRequiredContainerGroup(secGroupCreateISOCcyID);
			secGroupCreateISOCcyMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateISOCcyMembSysadmin.setRequiredSecGrpMembId(secGroupCreateISOCcyMembSysadminID);
			secGroupCreateISOCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateISOCcyMembSysadmin);
			secGroupCreateISOCcyMembSysadminID = secGroupCreateISOCcyMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadISOCcy == null) {
			secGroupReadISOCcy = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadISOCcy.setRequiredRevision(1);
			secGroupReadISOCcy.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadISOCcy.setRequiredName("ReadISOCcy");
			secGroupReadISOCcy.setRequiredIsVisible(true);
			secGroupReadISOCcy.setRequiredSecGroupId(secGroupReadISOCcyID);
			secGroupReadISOCcy = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadISOCcy);
			secGroupReadISOCcyID = secGroupReadISOCcy.getRequiredSecGroupId();
		}

		if (secGroupReadISOCcyMembSysadmin == null) {
			secGroupReadISOCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadISOCcyMembSysadmin.setRequiredRevision(1);
			secGroupReadISOCcyMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadISOCcyMembSysadmin.setRequiredContainerGroup(secGroupReadISOCcyID);
			secGroupReadISOCcyMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadISOCcyMembSysadmin.setRequiredSecGrpMembId(secGroupReadISOCcyMembSysadminID);
			secGroupReadISOCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadISOCcyMembSysadmin);
			secGroupReadISOCcyMembSysadminID = secGroupReadISOCcyMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateISOCcy == null) {
			secGroupUpdateISOCcy = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateISOCcy.setRequiredRevision(1);
			secGroupUpdateISOCcy.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateISOCcy.setRequiredName("UpdateISOCcy");
			secGroupUpdateISOCcy.setRequiredIsVisible(true);
			secGroupUpdateISOCcy.setRequiredSecGroupId(secGroupUpdateISOCcyID);
			secGroupUpdateISOCcy = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateISOCcy);
			secGroupUpdateISOCcyID = secGroupUpdateISOCcy.getRequiredSecGroupId();
		}

		if (secGroupUpdateISOCcyMembSysadmin == null) {
			secGroupUpdateISOCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateISOCcyMembSysadmin.setRequiredRevision(1);
			secGroupUpdateISOCcyMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateISOCcyMembSysadmin.setRequiredContainerGroup(secGroupUpdateISOCcyID);
			secGroupUpdateISOCcyMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateISOCcyMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateISOCcyMembSysadminID);
			secGroupUpdateISOCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateISOCcyMembSysadmin);
			secGroupUpdateISOCcyMembSysadminID = secGroupUpdateISOCcyMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteISOCcy == null) {
			secGroupDeleteISOCcy = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteISOCcy.setRequiredRevision(1);
			secGroupDeleteISOCcy.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteISOCcy.setRequiredName("DeleteISOCcy");
			secGroupDeleteISOCcy.setRequiredIsVisible(true);
			secGroupDeleteISOCcy.setRequiredSecGroupId(secGroupDeleteISOCcyID);
			secGroupDeleteISOCcy = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteISOCcy);
			secGroupDeleteISOCcyID = secGroupDeleteISOCcy.getRequiredSecGroupId();
		}

		if (secGroupDeleteISOCcyMembSysadmin == null) {
			secGroupDeleteISOCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteISOCcyMembSysadmin.setRequiredRevision(1);
			secGroupDeleteISOCcyMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteISOCcyMembSysadmin.setRequiredContainerGroup(secGroupDeleteISOCcyID);
			secGroupDeleteISOCcyMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteISOCcyMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteISOCcyMembSysadminID);
			secGroupDeleteISOCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteISOCcyMembSysadmin);
			secGroupDeleteISOCcyMembSysadminID = secGroupDeleteISOCcyMembSysadmin.getRequiredSecGrpMembId();
		}

	}		

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableISOCtrySecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateISOCtry;
		CFLibDbKeyHash256 secGroupCreateISOCtryID;
		ICFSecSecGrpMemb secGroupCreateISOCtryMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateISOCtryMembSysadminID;
		ICFSecSecGroup secGroupReadISOCtry;
		CFLibDbKeyHash256 secGroupReadISOCtryID;
		ICFSecSecGrpMemb secGroupReadISOCtryMembSysadmin;
		CFLibDbKeyHash256 secGroupReadISOCtryMembSysadminID;
		ICFSecSecGroup secGroupUpdateISOCtry;
		CFLibDbKeyHash256 secGroupUpdateISOCtryID;
		ICFSecSecGrpMemb secGroupUpdateISOCtryMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateISOCtryMembSysadminID;
		ICFSecSecGroup secGroupDeleteISOCtry;
		CFLibDbKeyHash256 secGroupDeleteISOCtryID;
		ICFSecSecGrpMemb secGroupDeleteISOCtryMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteISOCtryMembSysadminID;

		secGroupCreateISOCtry = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateISOCtry");
		if (secGroupCreateISOCtry != null) {
			secGroupCreateISOCtryID = secGroupCreateISOCtry.getRequiredSecGroupId();
			secGroupCreateISOCtryMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateISOCtryID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateISOCtryMembSysadmin != null) {
				secGroupCreateISOCtryMembSysadminID = secGroupCreateISOCtryMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateISOCtryMembSysadminID = null;
			}
		}
		else {
			secGroupCreateISOCtryID = null;
			secGroupCreateISOCtryMembSysadmin = null;
			secGroupCreateISOCtryMembSysadminID = null;
		}

		secGroupReadISOCtry = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadISOCtry");
		if (secGroupReadISOCtry != null) {
			secGroupReadISOCtryID = secGroupReadISOCtry.getRequiredSecGroupId();
			secGroupReadISOCtryMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadISOCtryID, ICFSecSchema.getSysAdminId());
			if (secGroupReadISOCtryMembSysadmin != null) {
				secGroupReadISOCtryMembSysadminID = secGroupReadISOCtryMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadISOCtryMembSysadminID = null;
			}
		}
		else {
			secGroupReadISOCtryID = null;
			secGroupReadISOCtryMembSysadmin = null;
			secGroupReadISOCtryMembSysadminID = null;
		}

		secGroupUpdateISOCtry = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateISOCtry");
		if (secGroupUpdateISOCtry != null) {
			secGroupUpdateISOCtryID = secGroupUpdateISOCtry.getRequiredSecGroupId();
			secGroupUpdateISOCtryMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateISOCtryID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateISOCtryMembSysadmin != null) {
				secGroupUpdateISOCtryMembSysadminID = secGroupUpdateISOCtryMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateISOCtryMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateISOCtryID = null;
			secGroupUpdateISOCtryMembSysadmin = null;
			secGroupUpdateISOCtryMembSysadminID = null;
		}

		secGroupDeleteISOCtry = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteISOCtry");
		if (secGroupDeleteISOCtry != null) {
			secGroupDeleteISOCtryID = secGroupDeleteISOCtry.getRequiredSecGroupId();
			secGroupDeleteISOCtryMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteISOCtryID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteISOCtryMembSysadmin != null) {
				secGroupDeleteISOCtryMembSysadminID = secGroupDeleteISOCtryMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteISOCtryMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteISOCtryID = null;
			secGroupDeleteISOCtryMembSysadmin = null;
			secGroupDeleteISOCtryMembSysadminID = null;
		}


		if (secGroupCreateISOCtryID == null || secGroupCreateISOCtryID.isNull()) {
			secGroupCreateISOCtryID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateISOCtryMembSysadminID == null || secGroupCreateISOCtryMembSysadminID.isNull()) {
			secGroupCreateISOCtryMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadISOCtryID == null || secGroupReadISOCtryID.isNull()) {
			secGroupReadISOCtryID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadISOCtryMembSysadminID == null || secGroupReadISOCtryMembSysadminID.isNull()) {
			secGroupReadISOCtryMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateISOCtryID == null || secGroupUpdateISOCtryID.isNull()) {
			secGroupUpdateISOCtryID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateISOCtryMembSysadminID == null || secGroupUpdateISOCtryMembSysadminID.isNull()) {
			secGroupUpdateISOCtryMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteISOCtryID == null || secGroupDeleteISOCtryID.isNull()) {
			secGroupDeleteISOCtryID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteISOCtryMembSysadminID == null || secGroupDeleteISOCtryMembSysadminID.isNull()) {
			secGroupDeleteISOCtryMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateISOCtry == null) {
			secGroupCreateISOCtry = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateISOCtry.setRequiredRevision(1);
			secGroupCreateISOCtry.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateISOCtry.setRequiredName("CreateISOCtry");
			secGroupCreateISOCtry.setRequiredIsVisible(true);
			secGroupCreateISOCtry.setRequiredSecGroupId(secGroupCreateISOCtryID);
			secGroupCreateISOCtry = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateISOCtry);
			secGroupCreateISOCtryID = secGroupCreateISOCtry.getRequiredSecGroupId();
		}

		if (secGroupCreateISOCtryMembSysadmin == null) {
			secGroupCreateISOCtryMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateISOCtryMembSysadmin.setRequiredRevision(1);
			secGroupCreateISOCtryMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateISOCtryMembSysadmin.setRequiredContainerGroup(secGroupCreateISOCtryID);
			secGroupCreateISOCtryMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateISOCtryMembSysadmin.setRequiredSecGrpMembId(secGroupCreateISOCtryMembSysadminID);
			secGroupCreateISOCtryMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateISOCtryMembSysadmin);
			secGroupCreateISOCtryMembSysadminID = secGroupCreateISOCtryMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadISOCtry == null) {
			secGroupReadISOCtry = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadISOCtry.setRequiredRevision(1);
			secGroupReadISOCtry.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadISOCtry.setRequiredName("ReadISOCtry");
			secGroupReadISOCtry.setRequiredIsVisible(true);
			secGroupReadISOCtry.setRequiredSecGroupId(secGroupReadISOCtryID);
			secGroupReadISOCtry = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadISOCtry);
			secGroupReadISOCtryID = secGroupReadISOCtry.getRequiredSecGroupId();
		}

		if (secGroupReadISOCtryMembSysadmin == null) {
			secGroupReadISOCtryMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadISOCtryMembSysadmin.setRequiredRevision(1);
			secGroupReadISOCtryMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadISOCtryMembSysadmin.setRequiredContainerGroup(secGroupReadISOCtryID);
			secGroupReadISOCtryMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadISOCtryMembSysadmin.setRequiredSecGrpMembId(secGroupReadISOCtryMembSysadminID);
			secGroupReadISOCtryMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadISOCtryMembSysadmin);
			secGroupReadISOCtryMembSysadminID = secGroupReadISOCtryMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateISOCtry == null) {
			secGroupUpdateISOCtry = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateISOCtry.setRequiredRevision(1);
			secGroupUpdateISOCtry.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateISOCtry.setRequiredName("UpdateISOCtry");
			secGroupUpdateISOCtry.setRequiredIsVisible(true);
			secGroupUpdateISOCtry.setRequiredSecGroupId(secGroupUpdateISOCtryID);
			secGroupUpdateISOCtry = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateISOCtry);
			secGroupUpdateISOCtryID = secGroupUpdateISOCtry.getRequiredSecGroupId();
		}

		if (secGroupUpdateISOCtryMembSysadmin == null) {
			secGroupUpdateISOCtryMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateISOCtryMembSysadmin.setRequiredRevision(1);
			secGroupUpdateISOCtryMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateISOCtryMembSysadmin.setRequiredContainerGroup(secGroupUpdateISOCtryID);
			secGroupUpdateISOCtryMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateISOCtryMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateISOCtryMembSysadminID);
			secGroupUpdateISOCtryMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateISOCtryMembSysadmin);
			secGroupUpdateISOCtryMembSysadminID = secGroupUpdateISOCtryMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteISOCtry == null) {
			secGroupDeleteISOCtry = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteISOCtry.setRequiredRevision(1);
			secGroupDeleteISOCtry.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteISOCtry.setRequiredName("DeleteISOCtry");
			secGroupDeleteISOCtry.setRequiredIsVisible(true);
			secGroupDeleteISOCtry.setRequiredSecGroupId(secGroupDeleteISOCtryID);
			secGroupDeleteISOCtry = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteISOCtry);
			secGroupDeleteISOCtryID = secGroupDeleteISOCtry.getRequiredSecGroupId();
		}

		if (secGroupDeleteISOCtryMembSysadmin == null) {
			secGroupDeleteISOCtryMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteISOCtryMembSysadmin.setRequiredRevision(1);
			secGroupDeleteISOCtryMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteISOCtryMembSysadmin.setRequiredContainerGroup(secGroupDeleteISOCtryID);
			secGroupDeleteISOCtryMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteISOCtryMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteISOCtryMembSysadminID);
			secGroupDeleteISOCtryMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteISOCtryMembSysadmin);
			secGroupDeleteISOCtryMembSysadminID = secGroupDeleteISOCtryMembSysadmin.getRequiredSecGrpMembId();
		}

	}		

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableISOCtryCcySecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateISOCtryCcy;
		CFLibDbKeyHash256 secGroupCreateISOCtryCcyID;
		ICFSecSecGrpMemb secGroupCreateISOCtryCcyMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateISOCtryCcyMembSysadminID;
		ICFSecSecGroup secGroupReadISOCtryCcy;
		CFLibDbKeyHash256 secGroupReadISOCtryCcyID;
		ICFSecSecGrpMemb secGroupReadISOCtryCcyMembSysadmin;
		CFLibDbKeyHash256 secGroupReadISOCtryCcyMembSysadminID;
		ICFSecSecGroup secGroupUpdateISOCtryCcy;
		CFLibDbKeyHash256 secGroupUpdateISOCtryCcyID;
		ICFSecSecGrpMemb secGroupUpdateISOCtryCcyMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateISOCtryCcyMembSysadminID;
		ICFSecSecGroup secGroupDeleteISOCtryCcy;
		CFLibDbKeyHash256 secGroupDeleteISOCtryCcyID;
		ICFSecSecGrpMemb secGroupDeleteISOCtryCcyMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteISOCtryCcyMembSysadminID;

		secGroupCreateISOCtryCcy = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateISOCtryCcy");
		if (secGroupCreateISOCtryCcy != null) {
			secGroupCreateISOCtryCcyID = secGroupCreateISOCtryCcy.getRequiredSecGroupId();
			secGroupCreateISOCtryCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateISOCtryCcyID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateISOCtryCcyMembSysadmin != null) {
				secGroupCreateISOCtryCcyMembSysadminID = secGroupCreateISOCtryCcyMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateISOCtryCcyMembSysadminID = null;
			}
		}
		else {
			secGroupCreateISOCtryCcyID = null;
			secGroupCreateISOCtryCcyMembSysadmin = null;
			secGroupCreateISOCtryCcyMembSysadminID = null;
		}

		secGroupReadISOCtryCcy = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadISOCtryCcy");
		if (secGroupReadISOCtryCcy != null) {
			secGroupReadISOCtryCcyID = secGroupReadISOCtryCcy.getRequiredSecGroupId();
			secGroupReadISOCtryCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadISOCtryCcyID, ICFSecSchema.getSysAdminId());
			if (secGroupReadISOCtryCcyMembSysadmin != null) {
				secGroupReadISOCtryCcyMembSysadminID = secGroupReadISOCtryCcyMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadISOCtryCcyMembSysadminID = null;
			}
		}
		else {
			secGroupReadISOCtryCcyID = null;
			secGroupReadISOCtryCcyMembSysadmin = null;
			secGroupReadISOCtryCcyMembSysadminID = null;
		}

		secGroupUpdateISOCtryCcy = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateISOCtryCcy");
		if (secGroupUpdateISOCtryCcy != null) {
			secGroupUpdateISOCtryCcyID = secGroupUpdateISOCtryCcy.getRequiredSecGroupId();
			secGroupUpdateISOCtryCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateISOCtryCcyID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateISOCtryCcyMembSysadmin != null) {
				secGroupUpdateISOCtryCcyMembSysadminID = secGroupUpdateISOCtryCcyMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateISOCtryCcyMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateISOCtryCcyID = null;
			secGroupUpdateISOCtryCcyMembSysadmin = null;
			secGroupUpdateISOCtryCcyMembSysadminID = null;
		}

		secGroupDeleteISOCtryCcy = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteISOCtryCcy");
		if (secGroupDeleteISOCtryCcy != null) {
			secGroupDeleteISOCtryCcyID = secGroupDeleteISOCtryCcy.getRequiredSecGroupId();
			secGroupDeleteISOCtryCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteISOCtryCcyID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteISOCtryCcyMembSysadmin != null) {
				secGroupDeleteISOCtryCcyMembSysadminID = secGroupDeleteISOCtryCcyMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteISOCtryCcyMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteISOCtryCcyID = null;
			secGroupDeleteISOCtryCcyMembSysadmin = null;
			secGroupDeleteISOCtryCcyMembSysadminID = null;
		}


		if (secGroupCreateISOCtryCcyID == null || secGroupCreateISOCtryCcyID.isNull()) {
			secGroupCreateISOCtryCcyID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateISOCtryCcyMembSysadminID == null || secGroupCreateISOCtryCcyMembSysadminID.isNull()) {
			secGroupCreateISOCtryCcyMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadISOCtryCcyID == null || secGroupReadISOCtryCcyID.isNull()) {
			secGroupReadISOCtryCcyID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadISOCtryCcyMembSysadminID == null || secGroupReadISOCtryCcyMembSysadminID.isNull()) {
			secGroupReadISOCtryCcyMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateISOCtryCcyID == null || secGroupUpdateISOCtryCcyID.isNull()) {
			secGroupUpdateISOCtryCcyID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateISOCtryCcyMembSysadminID == null || secGroupUpdateISOCtryCcyMembSysadminID.isNull()) {
			secGroupUpdateISOCtryCcyMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteISOCtryCcyID == null || secGroupDeleteISOCtryCcyID.isNull()) {
			secGroupDeleteISOCtryCcyID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteISOCtryCcyMembSysadminID == null || secGroupDeleteISOCtryCcyMembSysadminID.isNull()) {
			secGroupDeleteISOCtryCcyMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateISOCtryCcy == null) {
			secGroupCreateISOCtryCcy = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateISOCtryCcy.setRequiredRevision(1);
			secGroupCreateISOCtryCcy.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateISOCtryCcy.setRequiredName("CreateISOCtryCcy");
			secGroupCreateISOCtryCcy.setRequiredIsVisible(true);
			secGroupCreateISOCtryCcy.setRequiredSecGroupId(secGroupCreateISOCtryCcyID);
			secGroupCreateISOCtryCcy = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateISOCtryCcy);
			secGroupCreateISOCtryCcyID = secGroupCreateISOCtryCcy.getRequiredSecGroupId();
		}

		if (secGroupCreateISOCtryCcyMembSysadmin == null) {
			secGroupCreateISOCtryCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateISOCtryCcyMembSysadmin.setRequiredRevision(1);
			secGroupCreateISOCtryCcyMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateISOCtryCcyMembSysadmin.setRequiredContainerGroup(secGroupCreateISOCtryCcyID);
			secGroupCreateISOCtryCcyMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateISOCtryCcyMembSysadmin.setRequiredSecGrpMembId(secGroupCreateISOCtryCcyMembSysadminID);
			secGroupCreateISOCtryCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateISOCtryCcyMembSysadmin);
			secGroupCreateISOCtryCcyMembSysadminID = secGroupCreateISOCtryCcyMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadISOCtryCcy == null) {
			secGroupReadISOCtryCcy = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadISOCtryCcy.setRequiredRevision(1);
			secGroupReadISOCtryCcy.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadISOCtryCcy.setRequiredName("ReadISOCtryCcy");
			secGroupReadISOCtryCcy.setRequiredIsVisible(true);
			secGroupReadISOCtryCcy.setRequiredSecGroupId(secGroupReadISOCtryCcyID);
			secGroupReadISOCtryCcy = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadISOCtryCcy);
			secGroupReadISOCtryCcyID = secGroupReadISOCtryCcy.getRequiredSecGroupId();
		}

		if (secGroupReadISOCtryCcyMembSysadmin == null) {
			secGroupReadISOCtryCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadISOCtryCcyMembSysadmin.setRequiredRevision(1);
			secGroupReadISOCtryCcyMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadISOCtryCcyMembSysadmin.setRequiredContainerGroup(secGroupReadISOCtryCcyID);
			secGroupReadISOCtryCcyMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadISOCtryCcyMembSysadmin.setRequiredSecGrpMembId(secGroupReadISOCtryCcyMembSysadminID);
			secGroupReadISOCtryCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadISOCtryCcyMembSysadmin);
			secGroupReadISOCtryCcyMembSysadminID = secGroupReadISOCtryCcyMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateISOCtryCcy == null) {
			secGroupUpdateISOCtryCcy = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateISOCtryCcy.setRequiredRevision(1);
			secGroupUpdateISOCtryCcy.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateISOCtryCcy.setRequiredName("UpdateISOCtryCcy");
			secGroupUpdateISOCtryCcy.setRequiredIsVisible(true);
			secGroupUpdateISOCtryCcy.setRequiredSecGroupId(secGroupUpdateISOCtryCcyID);
			secGroupUpdateISOCtryCcy = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateISOCtryCcy);
			secGroupUpdateISOCtryCcyID = secGroupUpdateISOCtryCcy.getRequiredSecGroupId();
		}

		if (secGroupUpdateISOCtryCcyMembSysadmin == null) {
			secGroupUpdateISOCtryCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateISOCtryCcyMembSysadmin.setRequiredRevision(1);
			secGroupUpdateISOCtryCcyMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateISOCtryCcyMembSysadmin.setRequiredContainerGroup(secGroupUpdateISOCtryCcyID);
			secGroupUpdateISOCtryCcyMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateISOCtryCcyMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateISOCtryCcyMembSysadminID);
			secGroupUpdateISOCtryCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateISOCtryCcyMembSysadmin);
			secGroupUpdateISOCtryCcyMembSysadminID = secGroupUpdateISOCtryCcyMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteISOCtryCcy == null) {
			secGroupDeleteISOCtryCcy = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteISOCtryCcy.setRequiredRevision(1);
			secGroupDeleteISOCtryCcy.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteISOCtryCcy.setRequiredName("DeleteISOCtryCcy");
			secGroupDeleteISOCtryCcy.setRequiredIsVisible(true);
			secGroupDeleteISOCtryCcy.setRequiredSecGroupId(secGroupDeleteISOCtryCcyID);
			secGroupDeleteISOCtryCcy = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteISOCtryCcy);
			secGroupDeleteISOCtryCcyID = secGroupDeleteISOCtryCcy.getRequiredSecGroupId();
		}

		if (secGroupDeleteISOCtryCcyMembSysadmin == null) {
			secGroupDeleteISOCtryCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteISOCtryCcyMembSysadmin.setRequiredRevision(1);
			secGroupDeleteISOCtryCcyMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteISOCtryCcyMembSysadmin.setRequiredContainerGroup(secGroupDeleteISOCtryCcyID);
			secGroupDeleteISOCtryCcyMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteISOCtryCcyMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteISOCtryCcyMembSysadminID);
			secGroupDeleteISOCtryCcyMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteISOCtryCcyMembSysadmin);
			secGroupDeleteISOCtryCcyMembSysadminID = secGroupDeleteISOCtryCcyMembSysadmin.getRequiredSecGrpMembId();
		}

	}		

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableISOCtryLangSecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateISOCtryLang;
		CFLibDbKeyHash256 secGroupCreateISOCtryLangID;
		ICFSecSecGrpMemb secGroupCreateISOCtryLangMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateISOCtryLangMembSysadminID;
		ICFSecSecGroup secGroupReadISOCtryLang;
		CFLibDbKeyHash256 secGroupReadISOCtryLangID;
		ICFSecSecGrpMemb secGroupReadISOCtryLangMembSysadmin;
		CFLibDbKeyHash256 secGroupReadISOCtryLangMembSysadminID;
		ICFSecSecGroup secGroupUpdateISOCtryLang;
		CFLibDbKeyHash256 secGroupUpdateISOCtryLangID;
		ICFSecSecGrpMemb secGroupUpdateISOCtryLangMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateISOCtryLangMembSysadminID;
		ICFSecSecGroup secGroupDeleteISOCtryLang;
		CFLibDbKeyHash256 secGroupDeleteISOCtryLangID;
		ICFSecSecGrpMemb secGroupDeleteISOCtryLangMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteISOCtryLangMembSysadminID;

		secGroupCreateISOCtryLang = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateISOCtryLang");
		if (secGroupCreateISOCtryLang != null) {
			secGroupCreateISOCtryLangID = secGroupCreateISOCtryLang.getRequiredSecGroupId();
			secGroupCreateISOCtryLangMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateISOCtryLangID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateISOCtryLangMembSysadmin != null) {
				secGroupCreateISOCtryLangMembSysadminID = secGroupCreateISOCtryLangMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateISOCtryLangMembSysadminID = null;
			}
		}
		else {
			secGroupCreateISOCtryLangID = null;
			secGroupCreateISOCtryLangMembSysadmin = null;
			secGroupCreateISOCtryLangMembSysadminID = null;
		}

		secGroupReadISOCtryLang = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadISOCtryLang");
		if (secGroupReadISOCtryLang != null) {
			secGroupReadISOCtryLangID = secGroupReadISOCtryLang.getRequiredSecGroupId();
			secGroupReadISOCtryLangMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadISOCtryLangID, ICFSecSchema.getSysAdminId());
			if (secGroupReadISOCtryLangMembSysadmin != null) {
				secGroupReadISOCtryLangMembSysadminID = secGroupReadISOCtryLangMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadISOCtryLangMembSysadminID = null;
			}
		}
		else {
			secGroupReadISOCtryLangID = null;
			secGroupReadISOCtryLangMembSysadmin = null;
			secGroupReadISOCtryLangMembSysadminID = null;
		}

		secGroupUpdateISOCtryLang = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateISOCtryLang");
		if (secGroupUpdateISOCtryLang != null) {
			secGroupUpdateISOCtryLangID = secGroupUpdateISOCtryLang.getRequiredSecGroupId();
			secGroupUpdateISOCtryLangMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateISOCtryLangID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateISOCtryLangMembSysadmin != null) {
				secGroupUpdateISOCtryLangMembSysadminID = secGroupUpdateISOCtryLangMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateISOCtryLangMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateISOCtryLangID = null;
			secGroupUpdateISOCtryLangMembSysadmin = null;
			secGroupUpdateISOCtryLangMembSysadminID = null;
		}

		secGroupDeleteISOCtryLang = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteISOCtryLang");
		if (secGroupDeleteISOCtryLang != null) {
			secGroupDeleteISOCtryLangID = secGroupDeleteISOCtryLang.getRequiredSecGroupId();
			secGroupDeleteISOCtryLangMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteISOCtryLangID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteISOCtryLangMembSysadmin != null) {
				secGroupDeleteISOCtryLangMembSysadminID = secGroupDeleteISOCtryLangMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteISOCtryLangMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteISOCtryLangID = null;
			secGroupDeleteISOCtryLangMembSysadmin = null;
			secGroupDeleteISOCtryLangMembSysadminID = null;
		}


		if (secGroupCreateISOCtryLangID == null || secGroupCreateISOCtryLangID.isNull()) {
			secGroupCreateISOCtryLangID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateISOCtryLangMembSysadminID == null || secGroupCreateISOCtryLangMembSysadminID.isNull()) {
			secGroupCreateISOCtryLangMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadISOCtryLangID == null || secGroupReadISOCtryLangID.isNull()) {
			secGroupReadISOCtryLangID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadISOCtryLangMembSysadminID == null || secGroupReadISOCtryLangMembSysadminID.isNull()) {
			secGroupReadISOCtryLangMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateISOCtryLangID == null || secGroupUpdateISOCtryLangID.isNull()) {
			secGroupUpdateISOCtryLangID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateISOCtryLangMembSysadminID == null || secGroupUpdateISOCtryLangMembSysadminID.isNull()) {
			secGroupUpdateISOCtryLangMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteISOCtryLangID == null || secGroupDeleteISOCtryLangID.isNull()) {
			secGroupDeleteISOCtryLangID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteISOCtryLangMembSysadminID == null || secGroupDeleteISOCtryLangMembSysadminID.isNull()) {
			secGroupDeleteISOCtryLangMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateISOCtryLang == null) {
			secGroupCreateISOCtryLang = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateISOCtryLang.setRequiredRevision(1);
			secGroupCreateISOCtryLang.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateISOCtryLang.setRequiredName("CreateISOCtryLang");
			secGroupCreateISOCtryLang.setRequiredIsVisible(true);
			secGroupCreateISOCtryLang.setRequiredSecGroupId(secGroupCreateISOCtryLangID);
			secGroupCreateISOCtryLang = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateISOCtryLang);
			secGroupCreateISOCtryLangID = secGroupCreateISOCtryLang.getRequiredSecGroupId();
		}

		if (secGroupCreateISOCtryLangMembSysadmin == null) {
			secGroupCreateISOCtryLangMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateISOCtryLangMembSysadmin.setRequiredRevision(1);
			secGroupCreateISOCtryLangMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateISOCtryLangMembSysadmin.setRequiredContainerGroup(secGroupCreateISOCtryLangID);
			secGroupCreateISOCtryLangMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateISOCtryLangMembSysadmin.setRequiredSecGrpMembId(secGroupCreateISOCtryLangMembSysadminID);
			secGroupCreateISOCtryLangMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateISOCtryLangMembSysadmin);
			secGroupCreateISOCtryLangMembSysadminID = secGroupCreateISOCtryLangMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadISOCtryLang == null) {
			secGroupReadISOCtryLang = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadISOCtryLang.setRequiredRevision(1);
			secGroupReadISOCtryLang.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadISOCtryLang.setRequiredName("ReadISOCtryLang");
			secGroupReadISOCtryLang.setRequiredIsVisible(true);
			secGroupReadISOCtryLang.setRequiredSecGroupId(secGroupReadISOCtryLangID);
			secGroupReadISOCtryLang = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadISOCtryLang);
			secGroupReadISOCtryLangID = secGroupReadISOCtryLang.getRequiredSecGroupId();
		}

		if (secGroupReadISOCtryLangMembSysadmin == null) {
			secGroupReadISOCtryLangMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadISOCtryLangMembSysadmin.setRequiredRevision(1);
			secGroupReadISOCtryLangMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadISOCtryLangMembSysadmin.setRequiredContainerGroup(secGroupReadISOCtryLangID);
			secGroupReadISOCtryLangMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadISOCtryLangMembSysadmin.setRequiredSecGrpMembId(secGroupReadISOCtryLangMembSysadminID);
			secGroupReadISOCtryLangMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadISOCtryLangMembSysadmin);
			secGroupReadISOCtryLangMembSysadminID = secGroupReadISOCtryLangMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateISOCtryLang == null) {
			secGroupUpdateISOCtryLang = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateISOCtryLang.setRequiredRevision(1);
			secGroupUpdateISOCtryLang.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateISOCtryLang.setRequiredName("UpdateISOCtryLang");
			secGroupUpdateISOCtryLang.setRequiredIsVisible(true);
			secGroupUpdateISOCtryLang.setRequiredSecGroupId(secGroupUpdateISOCtryLangID);
			secGroupUpdateISOCtryLang = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateISOCtryLang);
			secGroupUpdateISOCtryLangID = secGroupUpdateISOCtryLang.getRequiredSecGroupId();
		}

		if (secGroupUpdateISOCtryLangMembSysadmin == null) {
			secGroupUpdateISOCtryLangMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateISOCtryLangMembSysadmin.setRequiredRevision(1);
			secGroupUpdateISOCtryLangMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateISOCtryLangMembSysadmin.setRequiredContainerGroup(secGroupUpdateISOCtryLangID);
			secGroupUpdateISOCtryLangMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateISOCtryLangMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateISOCtryLangMembSysadminID);
			secGroupUpdateISOCtryLangMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateISOCtryLangMembSysadmin);
			secGroupUpdateISOCtryLangMembSysadminID = secGroupUpdateISOCtryLangMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteISOCtryLang == null) {
			secGroupDeleteISOCtryLang = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteISOCtryLang.setRequiredRevision(1);
			secGroupDeleteISOCtryLang.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteISOCtryLang.setRequiredName("DeleteISOCtryLang");
			secGroupDeleteISOCtryLang.setRequiredIsVisible(true);
			secGroupDeleteISOCtryLang.setRequiredSecGroupId(secGroupDeleteISOCtryLangID);
			secGroupDeleteISOCtryLang = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteISOCtryLang);
			secGroupDeleteISOCtryLangID = secGroupDeleteISOCtryLang.getRequiredSecGroupId();
		}

		if (secGroupDeleteISOCtryLangMembSysadmin == null) {
			secGroupDeleteISOCtryLangMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteISOCtryLangMembSysadmin.setRequiredRevision(1);
			secGroupDeleteISOCtryLangMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteISOCtryLangMembSysadmin.setRequiredContainerGroup(secGroupDeleteISOCtryLangID);
			secGroupDeleteISOCtryLangMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteISOCtryLangMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteISOCtryLangMembSysadminID);
			secGroupDeleteISOCtryLangMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteISOCtryLangMembSysadmin);
			secGroupDeleteISOCtryLangMembSysadminID = secGroupDeleteISOCtryLangMembSysadmin.getRequiredSecGrpMembId();
		}

	}		

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableISOLangSecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateISOLang;
		CFLibDbKeyHash256 secGroupCreateISOLangID;
		ICFSecSecGrpMemb secGroupCreateISOLangMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateISOLangMembSysadminID;
		ICFSecSecGroup secGroupReadISOLang;
		CFLibDbKeyHash256 secGroupReadISOLangID;
		ICFSecSecGrpMemb secGroupReadISOLangMembSysadmin;
		CFLibDbKeyHash256 secGroupReadISOLangMembSysadminID;
		ICFSecSecGroup secGroupUpdateISOLang;
		CFLibDbKeyHash256 secGroupUpdateISOLangID;
		ICFSecSecGrpMemb secGroupUpdateISOLangMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateISOLangMembSysadminID;
		ICFSecSecGroup secGroupDeleteISOLang;
		CFLibDbKeyHash256 secGroupDeleteISOLangID;
		ICFSecSecGrpMemb secGroupDeleteISOLangMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteISOLangMembSysadminID;

		secGroupCreateISOLang = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateISOLang");
		if (secGroupCreateISOLang != null) {
			secGroupCreateISOLangID = secGroupCreateISOLang.getRequiredSecGroupId();
			secGroupCreateISOLangMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateISOLangID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateISOLangMembSysadmin != null) {
				secGroupCreateISOLangMembSysadminID = secGroupCreateISOLangMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateISOLangMembSysadminID = null;
			}
		}
		else {
			secGroupCreateISOLangID = null;
			secGroupCreateISOLangMembSysadmin = null;
			secGroupCreateISOLangMembSysadminID = null;
		}

		secGroupReadISOLang = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadISOLang");
		if (secGroupReadISOLang != null) {
			secGroupReadISOLangID = secGroupReadISOLang.getRequiredSecGroupId();
			secGroupReadISOLangMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadISOLangID, ICFSecSchema.getSysAdminId());
			if (secGroupReadISOLangMembSysadmin != null) {
				secGroupReadISOLangMembSysadminID = secGroupReadISOLangMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadISOLangMembSysadminID = null;
			}
		}
		else {
			secGroupReadISOLangID = null;
			secGroupReadISOLangMembSysadmin = null;
			secGroupReadISOLangMembSysadminID = null;
		}

		secGroupUpdateISOLang = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateISOLang");
		if (secGroupUpdateISOLang != null) {
			secGroupUpdateISOLangID = secGroupUpdateISOLang.getRequiredSecGroupId();
			secGroupUpdateISOLangMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateISOLangID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateISOLangMembSysadmin != null) {
				secGroupUpdateISOLangMembSysadminID = secGroupUpdateISOLangMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateISOLangMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateISOLangID = null;
			secGroupUpdateISOLangMembSysadmin = null;
			secGroupUpdateISOLangMembSysadminID = null;
		}

		secGroupDeleteISOLang = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteISOLang");
		if (secGroupDeleteISOLang != null) {
			secGroupDeleteISOLangID = secGroupDeleteISOLang.getRequiredSecGroupId();
			secGroupDeleteISOLangMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteISOLangID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteISOLangMembSysadmin != null) {
				secGroupDeleteISOLangMembSysadminID = secGroupDeleteISOLangMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteISOLangMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteISOLangID = null;
			secGroupDeleteISOLangMembSysadmin = null;
			secGroupDeleteISOLangMembSysadminID = null;
		}


		if (secGroupCreateISOLangID == null || secGroupCreateISOLangID.isNull()) {
			secGroupCreateISOLangID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateISOLangMembSysadminID == null || secGroupCreateISOLangMembSysadminID.isNull()) {
			secGroupCreateISOLangMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadISOLangID == null || secGroupReadISOLangID.isNull()) {
			secGroupReadISOLangID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadISOLangMembSysadminID == null || secGroupReadISOLangMembSysadminID.isNull()) {
			secGroupReadISOLangMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateISOLangID == null || secGroupUpdateISOLangID.isNull()) {
			secGroupUpdateISOLangID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateISOLangMembSysadminID == null || secGroupUpdateISOLangMembSysadminID.isNull()) {
			secGroupUpdateISOLangMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteISOLangID == null || secGroupDeleteISOLangID.isNull()) {
			secGroupDeleteISOLangID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteISOLangMembSysadminID == null || secGroupDeleteISOLangMembSysadminID.isNull()) {
			secGroupDeleteISOLangMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateISOLang == null) {
			secGroupCreateISOLang = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateISOLang.setRequiredRevision(1);
			secGroupCreateISOLang.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateISOLang.setRequiredName("CreateISOLang");
			secGroupCreateISOLang.setRequiredIsVisible(true);
			secGroupCreateISOLang.setRequiredSecGroupId(secGroupCreateISOLangID);
			secGroupCreateISOLang = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateISOLang);
			secGroupCreateISOLangID = secGroupCreateISOLang.getRequiredSecGroupId();
		}

		if (secGroupCreateISOLangMembSysadmin == null) {
			secGroupCreateISOLangMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateISOLangMembSysadmin.setRequiredRevision(1);
			secGroupCreateISOLangMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateISOLangMembSysadmin.setRequiredContainerGroup(secGroupCreateISOLangID);
			secGroupCreateISOLangMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateISOLangMembSysadmin.setRequiredSecGrpMembId(secGroupCreateISOLangMembSysadminID);
			secGroupCreateISOLangMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateISOLangMembSysadmin);
			secGroupCreateISOLangMembSysadminID = secGroupCreateISOLangMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadISOLang == null) {
			secGroupReadISOLang = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadISOLang.setRequiredRevision(1);
			secGroupReadISOLang.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadISOLang.setRequiredName("ReadISOLang");
			secGroupReadISOLang.setRequiredIsVisible(true);
			secGroupReadISOLang.setRequiredSecGroupId(secGroupReadISOLangID);
			secGroupReadISOLang = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadISOLang);
			secGroupReadISOLangID = secGroupReadISOLang.getRequiredSecGroupId();
		}

		if (secGroupReadISOLangMembSysadmin == null) {
			secGroupReadISOLangMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadISOLangMembSysadmin.setRequiredRevision(1);
			secGroupReadISOLangMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadISOLangMembSysadmin.setRequiredContainerGroup(secGroupReadISOLangID);
			secGroupReadISOLangMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadISOLangMembSysadmin.setRequiredSecGrpMembId(secGroupReadISOLangMembSysadminID);
			secGroupReadISOLangMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadISOLangMembSysadmin);
			secGroupReadISOLangMembSysadminID = secGroupReadISOLangMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateISOLang == null) {
			secGroupUpdateISOLang = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateISOLang.setRequiredRevision(1);
			secGroupUpdateISOLang.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateISOLang.setRequiredName("UpdateISOLang");
			secGroupUpdateISOLang.setRequiredIsVisible(true);
			secGroupUpdateISOLang.setRequiredSecGroupId(secGroupUpdateISOLangID);
			secGroupUpdateISOLang = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateISOLang);
			secGroupUpdateISOLangID = secGroupUpdateISOLang.getRequiredSecGroupId();
		}

		if (secGroupUpdateISOLangMembSysadmin == null) {
			secGroupUpdateISOLangMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateISOLangMembSysadmin.setRequiredRevision(1);
			secGroupUpdateISOLangMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateISOLangMembSysadmin.setRequiredContainerGroup(secGroupUpdateISOLangID);
			secGroupUpdateISOLangMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateISOLangMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateISOLangMembSysadminID);
			secGroupUpdateISOLangMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateISOLangMembSysadmin);
			secGroupUpdateISOLangMembSysadminID = secGroupUpdateISOLangMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteISOLang == null) {
			secGroupDeleteISOLang = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteISOLang.setRequiredRevision(1);
			secGroupDeleteISOLang.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteISOLang.setRequiredName("DeleteISOLang");
			secGroupDeleteISOLang.setRequiredIsVisible(true);
			secGroupDeleteISOLang.setRequiredSecGroupId(secGroupDeleteISOLangID);
			secGroupDeleteISOLang = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteISOLang);
			secGroupDeleteISOLangID = secGroupDeleteISOLang.getRequiredSecGroupId();
		}

		if (secGroupDeleteISOLangMembSysadmin == null) {
			secGroupDeleteISOLangMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteISOLangMembSysadmin.setRequiredRevision(1);
			secGroupDeleteISOLangMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteISOLangMembSysadmin.setRequiredContainerGroup(secGroupDeleteISOLangID);
			secGroupDeleteISOLangMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteISOLangMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteISOLangMembSysadminID);
			secGroupDeleteISOLangMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteISOLangMembSysadmin);
			secGroupDeleteISOLangMembSysadminID = secGroupDeleteISOLangMembSysadmin.getRequiredSecGrpMembId();
		}

	}		

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableISOTZoneSecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateISOTZone;
		CFLibDbKeyHash256 secGroupCreateISOTZoneID;
		ICFSecSecGrpMemb secGroupCreateISOTZoneMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateISOTZoneMembSysadminID;
		ICFSecSecGroup secGroupReadISOTZone;
		CFLibDbKeyHash256 secGroupReadISOTZoneID;
		ICFSecSecGrpMemb secGroupReadISOTZoneMembSysadmin;
		CFLibDbKeyHash256 secGroupReadISOTZoneMembSysadminID;
		ICFSecSecGroup secGroupUpdateISOTZone;
		CFLibDbKeyHash256 secGroupUpdateISOTZoneID;
		ICFSecSecGrpMemb secGroupUpdateISOTZoneMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateISOTZoneMembSysadminID;
		ICFSecSecGroup secGroupDeleteISOTZone;
		CFLibDbKeyHash256 secGroupDeleteISOTZoneID;
		ICFSecSecGrpMemb secGroupDeleteISOTZoneMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteISOTZoneMembSysadminID;

		secGroupCreateISOTZone = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateISOTZone");
		if (secGroupCreateISOTZone != null) {
			secGroupCreateISOTZoneID = secGroupCreateISOTZone.getRequiredSecGroupId();
			secGroupCreateISOTZoneMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateISOTZoneID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateISOTZoneMembSysadmin != null) {
				secGroupCreateISOTZoneMembSysadminID = secGroupCreateISOTZoneMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateISOTZoneMembSysadminID = null;
			}
		}
		else {
			secGroupCreateISOTZoneID = null;
			secGroupCreateISOTZoneMembSysadmin = null;
			secGroupCreateISOTZoneMembSysadminID = null;
		}

		secGroupReadISOTZone = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadISOTZone");
		if (secGroupReadISOTZone != null) {
			secGroupReadISOTZoneID = secGroupReadISOTZone.getRequiredSecGroupId();
			secGroupReadISOTZoneMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadISOTZoneID, ICFSecSchema.getSysAdminId());
			if (secGroupReadISOTZoneMembSysadmin != null) {
				secGroupReadISOTZoneMembSysadminID = secGroupReadISOTZoneMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadISOTZoneMembSysadminID = null;
			}
		}
		else {
			secGroupReadISOTZoneID = null;
			secGroupReadISOTZoneMembSysadmin = null;
			secGroupReadISOTZoneMembSysadminID = null;
		}

		secGroupUpdateISOTZone = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateISOTZone");
		if (secGroupUpdateISOTZone != null) {
			secGroupUpdateISOTZoneID = secGroupUpdateISOTZone.getRequiredSecGroupId();
			secGroupUpdateISOTZoneMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateISOTZoneID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateISOTZoneMembSysadmin != null) {
				secGroupUpdateISOTZoneMembSysadminID = secGroupUpdateISOTZoneMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateISOTZoneMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateISOTZoneID = null;
			secGroupUpdateISOTZoneMembSysadmin = null;
			secGroupUpdateISOTZoneMembSysadminID = null;
		}

		secGroupDeleteISOTZone = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteISOTZone");
		if (secGroupDeleteISOTZone != null) {
			secGroupDeleteISOTZoneID = secGroupDeleteISOTZone.getRequiredSecGroupId();
			secGroupDeleteISOTZoneMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteISOTZoneID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteISOTZoneMembSysadmin != null) {
				secGroupDeleteISOTZoneMembSysadminID = secGroupDeleteISOTZoneMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteISOTZoneMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteISOTZoneID = null;
			secGroupDeleteISOTZoneMembSysadmin = null;
			secGroupDeleteISOTZoneMembSysadminID = null;
		}


		if (secGroupCreateISOTZoneID == null || secGroupCreateISOTZoneID.isNull()) {
			secGroupCreateISOTZoneID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateISOTZoneMembSysadminID == null || secGroupCreateISOTZoneMembSysadminID.isNull()) {
			secGroupCreateISOTZoneMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadISOTZoneID == null || secGroupReadISOTZoneID.isNull()) {
			secGroupReadISOTZoneID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadISOTZoneMembSysadminID == null || secGroupReadISOTZoneMembSysadminID.isNull()) {
			secGroupReadISOTZoneMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateISOTZoneID == null || secGroupUpdateISOTZoneID.isNull()) {
			secGroupUpdateISOTZoneID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateISOTZoneMembSysadminID == null || secGroupUpdateISOTZoneMembSysadminID.isNull()) {
			secGroupUpdateISOTZoneMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteISOTZoneID == null || secGroupDeleteISOTZoneID.isNull()) {
			secGroupDeleteISOTZoneID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteISOTZoneMembSysadminID == null || secGroupDeleteISOTZoneMembSysadminID.isNull()) {
			secGroupDeleteISOTZoneMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateISOTZone == null) {
			secGroupCreateISOTZone = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateISOTZone.setRequiredRevision(1);
			secGroupCreateISOTZone.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateISOTZone.setRequiredName("CreateISOTZone");
			secGroupCreateISOTZone.setRequiredIsVisible(true);
			secGroupCreateISOTZone.setRequiredSecGroupId(secGroupCreateISOTZoneID);
			secGroupCreateISOTZone = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateISOTZone);
			secGroupCreateISOTZoneID = secGroupCreateISOTZone.getRequiredSecGroupId();
		}

		if (secGroupCreateISOTZoneMembSysadmin == null) {
			secGroupCreateISOTZoneMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateISOTZoneMembSysadmin.setRequiredRevision(1);
			secGroupCreateISOTZoneMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateISOTZoneMembSysadmin.setRequiredContainerGroup(secGroupCreateISOTZoneID);
			secGroupCreateISOTZoneMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateISOTZoneMembSysadmin.setRequiredSecGrpMembId(secGroupCreateISOTZoneMembSysadminID);
			secGroupCreateISOTZoneMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateISOTZoneMembSysadmin);
			secGroupCreateISOTZoneMembSysadminID = secGroupCreateISOTZoneMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadISOTZone == null) {
			secGroupReadISOTZone = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadISOTZone.setRequiredRevision(1);
			secGroupReadISOTZone.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadISOTZone.setRequiredName("ReadISOTZone");
			secGroupReadISOTZone.setRequiredIsVisible(true);
			secGroupReadISOTZone.setRequiredSecGroupId(secGroupReadISOTZoneID);
			secGroupReadISOTZone = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadISOTZone);
			secGroupReadISOTZoneID = secGroupReadISOTZone.getRequiredSecGroupId();
		}

		if (secGroupReadISOTZoneMembSysadmin == null) {
			secGroupReadISOTZoneMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadISOTZoneMembSysadmin.setRequiredRevision(1);
			secGroupReadISOTZoneMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadISOTZoneMembSysadmin.setRequiredContainerGroup(secGroupReadISOTZoneID);
			secGroupReadISOTZoneMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadISOTZoneMembSysadmin.setRequiredSecGrpMembId(secGroupReadISOTZoneMembSysadminID);
			secGroupReadISOTZoneMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadISOTZoneMembSysadmin);
			secGroupReadISOTZoneMembSysadminID = secGroupReadISOTZoneMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateISOTZone == null) {
			secGroupUpdateISOTZone = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateISOTZone.setRequiredRevision(1);
			secGroupUpdateISOTZone.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateISOTZone.setRequiredName("UpdateISOTZone");
			secGroupUpdateISOTZone.setRequiredIsVisible(true);
			secGroupUpdateISOTZone.setRequiredSecGroupId(secGroupUpdateISOTZoneID);
			secGroupUpdateISOTZone = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateISOTZone);
			secGroupUpdateISOTZoneID = secGroupUpdateISOTZone.getRequiredSecGroupId();
		}

		if (secGroupUpdateISOTZoneMembSysadmin == null) {
			secGroupUpdateISOTZoneMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateISOTZoneMembSysadmin.setRequiredRevision(1);
			secGroupUpdateISOTZoneMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateISOTZoneMembSysadmin.setRequiredContainerGroup(secGroupUpdateISOTZoneID);
			secGroupUpdateISOTZoneMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateISOTZoneMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateISOTZoneMembSysadminID);
			secGroupUpdateISOTZoneMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateISOTZoneMembSysadmin);
			secGroupUpdateISOTZoneMembSysadminID = secGroupUpdateISOTZoneMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteISOTZone == null) {
			secGroupDeleteISOTZone = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteISOTZone.setRequiredRevision(1);
			secGroupDeleteISOTZone.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteISOTZone.setRequiredName("DeleteISOTZone");
			secGroupDeleteISOTZone.setRequiredIsVisible(true);
			secGroupDeleteISOTZone.setRequiredSecGroupId(secGroupDeleteISOTZoneID);
			secGroupDeleteISOTZone = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteISOTZone);
			secGroupDeleteISOTZoneID = secGroupDeleteISOTZone.getRequiredSecGroupId();
		}

		if (secGroupDeleteISOTZoneMembSysadmin == null) {
			secGroupDeleteISOTZoneMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteISOTZoneMembSysadmin.setRequiredRevision(1);
			secGroupDeleteISOTZoneMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteISOTZoneMembSysadmin.setRequiredContainerGroup(secGroupDeleteISOTZoneID);
			secGroupDeleteISOTZoneMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteISOTZoneMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteISOTZoneMembSysadminID);
			secGroupDeleteISOTZoneMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteISOTZoneMembSysadmin);
			secGroupDeleteISOTZoneMembSysadminID = secGroupDeleteISOTZoneMembSysadmin.getRequiredSecGrpMembId();
		}

	}		

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableSecDeviceSecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateSecDevice;
		CFLibDbKeyHash256 secGroupCreateSecDeviceID;
		ICFSecSecGrpMemb secGroupCreateSecDeviceMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateSecDeviceMembSysadminID;
		ICFSecSecGroup secGroupReadSecDevice;
		CFLibDbKeyHash256 secGroupReadSecDeviceID;
		ICFSecSecGrpMemb secGroupReadSecDeviceMembSysadmin;
		CFLibDbKeyHash256 secGroupReadSecDeviceMembSysadminID;
		ICFSecSecGroup secGroupUpdateSecDevice;
		CFLibDbKeyHash256 secGroupUpdateSecDeviceID;
		ICFSecSecGrpMemb secGroupUpdateSecDeviceMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateSecDeviceMembSysadminID;
		ICFSecSecGroup secGroupDeleteSecDevice;
		CFLibDbKeyHash256 secGroupDeleteSecDeviceID;
		ICFSecSecGrpMemb secGroupDeleteSecDeviceMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteSecDeviceMembSysadminID;

		secGroupCreateSecDevice = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateSecDevice");
		if (secGroupCreateSecDevice != null) {
			secGroupCreateSecDeviceID = secGroupCreateSecDevice.getRequiredSecGroupId();
			secGroupCreateSecDeviceMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateSecDeviceID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateSecDeviceMembSysadmin != null) {
				secGroupCreateSecDeviceMembSysadminID = secGroupCreateSecDeviceMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateSecDeviceMembSysadminID = null;
			}
		}
		else {
			secGroupCreateSecDeviceID = null;
			secGroupCreateSecDeviceMembSysadmin = null;
			secGroupCreateSecDeviceMembSysadminID = null;
		}

		secGroupReadSecDevice = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadSecDevice");
		if (secGroupReadSecDevice != null) {
			secGroupReadSecDeviceID = secGroupReadSecDevice.getRequiredSecGroupId();
			secGroupReadSecDeviceMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadSecDeviceID, ICFSecSchema.getSysAdminId());
			if (secGroupReadSecDeviceMembSysadmin != null) {
				secGroupReadSecDeviceMembSysadminID = secGroupReadSecDeviceMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadSecDeviceMembSysadminID = null;
			}
		}
		else {
			secGroupReadSecDeviceID = null;
			secGroupReadSecDeviceMembSysadmin = null;
			secGroupReadSecDeviceMembSysadminID = null;
		}

		secGroupUpdateSecDevice = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateSecDevice");
		if (secGroupUpdateSecDevice != null) {
			secGroupUpdateSecDeviceID = secGroupUpdateSecDevice.getRequiredSecGroupId();
			secGroupUpdateSecDeviceMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateSecDeviceID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateSecDeviceMembSysadmin != null) {
				secGroupUpdateSecDeviceMembSysadminID = secGroupUpdateSecDeviceMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateSecDeviceMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateSecDeviceID = null;
			secGroupUpdateSecDeviceMembSysadmin = null;
			secGroupUpdateSecDeviceMembSysadminID = null;
		}

		secGroupDeleteSecDevice = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteSecDevice");
		if (secGroupDeleteSecDevice != null) {
			secGroupDeleteSecDeviceID = secGroupDeleteSecDevice.getRequiredSecGroupId();
			secGroupDeleteSecDeviceMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteSecDeviceID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteSecDeviceMembSysadmin != null) {
				secGroupDeleteSecDeviceMembSysadminID = secGroupDeleteSecDeviceMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteSecDeviceMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteSecDeviceID = null;
			secGroupDeleteSecDeviceMembSysadmin = null;
			secGroupDeleteSecDeviceMembSysadminID = null;
		}


		if (secGroupCreateSecDeviceID == null || secGroupCreateSecDeviceID.isNull()) {
			secGroupCreateSecDeviceID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateSecDeviceMembSysadminID == null || secGroupCreateSecDeviceMembSysadminID.isNull()) {
			secGroupCreateSecDeviceMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadSecDeviceID == null || secGroupReadSecDeviceID.isNull()) {
			secGroupReadSecDeviceID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadSecDeviceMembSysadminID == null || secGroupReadSecDeviceMembSysadminID.isNull()) {
			secGroupReadSecDeviceMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateSecDeviceID == null || secGroupUpdateSecDeviceID.isNull()) {
			secGroupUpdateSecDeviceID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateSecDeviceMembSysadminID == null || secGroupUpdateSecDeviceMembSysadminID.isNull()) {
			secGroupUpdateSecDeviceMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteSecDeviceID == null || secGroupDeleteSecDeviceID.isNull()) {
			secGroupDeleteSecDeviceID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteSecDeviceMembSysadminID == null || secGroupDeleteSecDeviceMembSysadminID.isNull()) {
			secGroupDeleteSecDeviceMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateSecDevice == null) {
			secGroupCreateSecDevice = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateSecDevice.setRequiredRevision(1);
			secGroupCreateSecDevice.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateSecDevice.setRequiredName("CreateSecDevice");
			secGroupCreateSecDevice.setRequiredIsVisible(true);
			secGroupCreateSecDevice.setRequiredSecGroupId(secGroupCreateSecDeviceID);
			secGroupCreateSecDevice = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateSecDevice);
			secGroupCreateSecDeviceID = secGroupCreateSecDevice.getRequiredSecGroupId();
		}

		if (secGroupCreateSecDeviceMembSysadmin == null) {
			secGroupCreateSecDeviceMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateSecDeviceMembSysadmin.setRequiredRevision(1);
			secGroupCreateSecDeviceMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateSecDeviceMembSysadmin.setRequiredContainerGroup(secGroupCreateSecDeviceID);
			secGroupCreateSecDeviceMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateSecDeviceMembSysadmin.setRequiredSecGrpMembId(secGroupCreateSecDeviceMembSysadminID);
			secGroupCreateSecDeviceMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateSecDeviceMembSysadmin);
			secGroupCreateSecDeviceMembSysadminID = secGroupCreateSecDeviceMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadSecDevice == null) {
			secGroupReadSecDevice = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadSecDevice.setRequiredRevision(1);
			secGroupReadSecDevice.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadSecDevice.setRequiredName("ReadSecDevice");
			secGroupReadSecDevice.setRequiredIsVisible(true);
			secGroupReadSecDevice.setRequiredSecGroupId(secGroupReadSecDeviceID);
			secGroupReadSecDevice = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadSecDevice);
			secGroupReadSecDeviceID = secGroupReadSecDevice.getRequiredSecGroupId();
		}

		if (secGroupReadSecDeviceMembSysadmin == null) {
			secGroupReadSecDeviceMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadSecDeviceMembSysadmin.setRequiredRevision(1);
			secGroupReadSecDeviceMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadSecDeviceMembSysadmin.setRequiredContainerGroup(secGroupReadSecDeviceID);
			secGroupReadSecDeviceMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadSecDeviceMembSysadmin.setRequiredSecGrpMembId(secGroupReadSecDeviceMembSysadminID);
			secGroupReadSecDeviceMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadSecDeviceMembSysadmin);
			secGroupReadSecDeviceMembSysadminID = secGroupReadSecDeviceMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateSecDevice == null) {
			secGroupUpdateSecDevice = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateSecDevice.setRequiredRevision(1);
			secGroupUpdateSecDevice.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateSecDevice.setRequiredName("UpdateSecDevice");
			secGroupUpdateSecDevice.setRequiredIsVisible(true);
			secGroupUpdateSecDevice.setRequiredSecGroupId(secGroupUpdateSecDeviceID);
			secGroupUpdateSecDevice = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateSecDevice);
			secGroupUpdateSecDeviceID = secGroupUpdateSecDevice.getRequiredSecGroupId();
		}

		if (secGroupUpdateSecDeviceMembSysadmin == null) {
			secGroupUpdateSecDeviceMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateSecDeviceMembSysadmin.setRequiredRevision(1);
			secGroupUpdateSecDeviceMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateSecDeviceMembSysadmin.setRequiredContainerGroup(secGroupUpdateSecDeviceID);
			secGroupUpdateSecDeviceMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateSecDeviceMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateSecDeviceMembSysadminID);
			secGroupUpdateSecDeviceMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateSecDeviceMembSysadmin);
			secGroupUpdateSecDeviceMembSysadminID = secGroupUpdateSecDeviceMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteSecDevice == null) {
			secGroupDeleteSecDevice = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteSecDevice.setRequiredRevision(1);
			secGroupDeleteSecDevice.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteSecDevice.setRequiredName("DeleteSecDevice");
			secGroupDeleteSecDevice.setRequiredIsVisible(true);
			secGroupDeleteSecDevice.setRequiredSecGroupId(secGroupDeleteSecDeviceID);
			secGroupDeleteSecDevice = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteSecDevice);
			secGroupDeleteSecDeviceID = secGroupDeleteSecDevice.getRequiredSecGroupId();
		}

		if (secGroupDeleteSecDeviceMembSysadmin == null) {
			secGroupDeleteSecDeviceMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteSecDeviceMembSysadmin.setRequiredRevision(1);
			secGroupDeleteSecDeviceMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteSecDeviceMembSysadmin.setRequiredContainerGroup(secGroupDeleteSecDeviceID);
			secGroupDeleteSecDeviceMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteSecDeviceMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteSecDeviceMembSysadminID);
			secGroupDeleteSecDeviceMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteSecDeviceMembSysadmin);
			secGroupDeleteSecDeviceMembSysadminID = secGroupDeleteSecDeviceMembSysadmin.getRequiredSecGrpMembId();
		}

	}		

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableSecGroupSecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateSecGroup;
		CFLibDbKeyHash256 secGroupCreateSecGroupID;
		ICFSecSecGrpMemb secGroupCreateSecGroupMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateSecGroupMembSysadminID;
		ICFSecSecGroup secGroupReadSecGroup;
		CFLibDbKeyHash256 secGroupReadSecGroupID;
		ICFSecSecGrpMemb secGroupReadSecGroupMembSysadmin;
		CFLibDbKeyHash256 secGroupReadSecGroupMembSysadminID;
		ICFSecSecGroup secGroupUpdateSecGroup;
		CFLibDbKeyHash256 secGroupUpdateSecGroupID;
		ICFSecSecGrpMemb secGroupUpdateSecGroupMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateSecGroupMembSysadminID;
		ICFSecSecGroup secGroupDeleteSecGroup;
		CFLibDbKeyHash256 secGroupDeleteSecGroupID;
		ICFSecSecGrpMemb secGroupDeleteSecGroupMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteSecGroupMembSysadminID;

		secGroupCreateSecGroup = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateSecGroup");
		if (secGroupCreateSecGroup != null) {
			secGroupCreateSecGroupID = secGroupCreateSecGroup.getRequiredSecGroupId();
			secGroupCreateSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateSecGroupID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateSecGroupMembSysadmin != null) {
				secGroupCreateSecGroupMembSysadminID = secGroupCreateSecGroupMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateSecGroupMembSysadminID = null;
			}
		}
		else {
			secGroupCreateSecGroupID = null;
			secGroupCreateSecGroupMembSysadmin = null;
			secGroupCreateSecGroupMembSysadminID = null;
		}

		secGroupReadSecGroup = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadSecGroup");
		if (secGroupReadSecGroup != null) {
			secGroupReadSecGroupID = secGroupReadSecGroup.getRequiredSecGroupId();
			secGroupReadSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadSecGroupID, ICFSecSchema.getSysAdminId());
			if (secGroupReadSecGroupMembSysadmin != null) {
				secGroupReadSecGroupMembSysadminID = secGroupReadSecGroupMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadSecGroupMembSysadminID = null;
			}
		}
		else {
			secGroupReadSecGroupID = null;
			secGroupReadSecGroupMembSysadmin = null;
			secGroupReadSecGroupMembSysadminID = null;
		}

		secGroupUpdateSecGroup = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateSecGroup");
		if (secGroupUpdateSecGroup != null) {
			secGroupUpdateSecGroupID = secGroupUpdateSecGroup.getRequiredSecGroupId();
			secGroupUpdateSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateSecGroupID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateSecGroupMembSysadmin != null) {
				secGroupUpdateSecGroupMembSysadminID = secGroupUpdateSecGroupMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateSecGroupMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateSecGroupID = null;
			secGroupUpdateSecGroupMembSysadmin = null;
			secGroupUpdateSecGroupMembSysadminID = null;
		}

		secGroupDeleteSecGroup = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteSecGroup");
		if (secGroupDeleteSecGroup != null) {
			secGroupDeleteSecGroupID = secGroupDeleteSecGroup.getRequiredSecGroupId();
			secGroupDeleteSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteSecGroupID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteSecGroupMembSysadmin != null) {
				secGroupDeleteSecGroupMembSysadminID = secGroupDeleteSecGroupMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteSecGroupMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteSecGroupID = null;
			secGroupDeleteSecGroupMembSysadmin = null;
			secGroupDeleteSecGroupMembSysadminID = null;
		}


		if (secGroupCreateSecGroupID == null || secGroupCreateSecGroupID.isNull()) {
			secGroupCreateSecGroupID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateSecGroupMembSysadminID == null || secGroupCreateSecGroupMembSysadminID.isNull()) {
			secGroupCreateSecGroupMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadSecGroupID == null || secGroupReadSecGroupID.isNull()) {
			secGroupReadSecGroupID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadSecGroupMembSysadminID == null || secGroupReadSecGroupMembSysadminID.isNull()) {
			secGroupReadSecGroupMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateSecGroupID == null || secGroupUpdateSecGroupID.isNull()) {
			secGroupUpdateSecGroupID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateSecGroupMembSysadminID == null || secGroupUpdateSecGroupMembSysadminID.isNull()) {
			secGroupUpdateSecGroupMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteSecGroupID == null || secGroupDeleteSecGroupID.isNull()) {
			secGroupDeleteSecGroupID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteSecGroupMembSysadminID == null || secGroupDeleteSecGroupMembSysadminID.isNull()) {
			secGroupDeleteSecGroupMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateSecGroup == null) {
			secGroupCreateSecGroup = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateSecGroup.setRequiredRevision(1);
			secGroupCreateSecGroup.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateSecGroup.setRequiredName("CreateSecGroup");
			secGroupCreateSecGroup.setRequiredIsVisible(true);
			secGroupCreateSecGroup.setRequiredSecGroupId(secGroupCreateSecGroupID);
			secGroupCreateSecGroup = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateSecGroup);
			secGroupCreateSecGroupID = secGroupCreateSecGroup.getRequiredSecGroupId();
		}

		if (secGroupCreateSecGroupMembSysadmin == null) {
			secGroupCreateSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateSecGroupMembSysadmin.setRequiredRevision(1);
			secGroupCreateSecGroupMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateSecGroupMembSysadmin.setRequiredContainerGroup(secGroupCreateSecGroupID);
			secGroupCreateSecGroupMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateSecGroupMembSysadmin.setRequiredSecGrpMembId(secGroupCreateSecGroupMembSysadminID);
			secGroupCreateSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateSecGroupMembSysadmin);
			secGroupCreateSecGroupMembSysadminID = secGroupCreateSecGroupMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadSecGroup == null) {
			secGroupReadSecGroup = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadSecGroup.setRequiredRevision(1);
			secGroupReadSecGroup.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadSecGroup.setRequiredName("ReadSecGroup");
			secGroupReadSecGroup.setRequiredIsVisible(true);
			secGroupReadSecGroup.setRequiredSecGroupId(secGroupReadSecGroupID);
			secGroupReadSecGroup = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadSecGroup);
			secGroupReadSecGroupID = secGroupReadSecGroup.getRequiredSecGroupId();
		}

		if (secGroupReadSecGroupMembSysadmin == null) {
			secGroupReadSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadSecGroupMembSysadmin.setRequiredRevision(1);
			secGroupReadSecGroupMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadSecGroupMembSysadmin.setRequiredContainerGroup(secGroupReadSecGroupID);
			secGroupReadSecGroupMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadSecGroupMembSysadmin.setRequiredSecGrpMembId(secGroupReadSecGroupMembSysadminID);
			secGroupReadSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadSecGroupMembSysadmin);
			secGroupReadSecGroupMembSysadminID = secGroupReadSecGroupMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateSecGroup == null) {
			secGroupUpdateSecGroup = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateSecGroup.setRequiredRevision(1);
			secGroupUpdateSecGroup.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateSecGroup.setRequiredName("UpdateSecGroup");
			secGroupUpdateSecGroup.setRequiredIsVisible(true);
			secGroupUpdateSecGroup.setRequiredSecGroupId(secGroupUpdateSecGroupID);
			secGroupUpdateSecGroup = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateSecGroup);
			secGroupUpdateSecGroupID = secGroupUpdateSecGroup.getRequiredSecGroupId();
		}

		if (secGroupUpdateSecGroupMembSysadmin == null) {
			secGroupUpdateSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateSecGroupMembSysadmin.setRequiredRevision(1);
			secGroupUpdateSecGroupMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateSecGroupMembSysadmin.setRequiredContainerGroup(secGroupUpdateSecGroupID);
			secGroupUpdateSecGroupMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateSecGroupMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateSecGroupMembSysadminID);
			secGroupUpdateSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateSecGroupMembSysadmin);
			secGroupUpdateSecGroupMembSysadminID = secGroupUpdateSecGroupMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteSecGroup == null) {
			secGroupDeleteSecGroup = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteSecGroup.setRequiredRevision(1);
			secGroupDeleteSecGroup.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteSecGroup.setRequiredName("DeleteSecGroup");
			secGroupDeleteSecGroup.setRequiredIsVisible(true);
			secGroupDeleteSecGroup.setRequiredSecGroupId(secGroupDeleteSecGroupID);
			secGroupDeleteSecGroup = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteSecGroup);
			secGroupDeleteSecGroupID = secGroupDeleteSecGroup.getRequiredSecGroupId();
		}

		if (secGroupDeleteSecGroupMembSysadmin == null) {
			secGroupDeleteSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteSecGroupMembSysadmin.setRequiredRevision(1);
			secGroupDeleteSecGroupMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteSecGroupMembSysadmin.setRequiredContainerGroup(secGroupDeleteSecGroupID);
			secGroupDeleteSecGroupMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteSecGroupMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteSecGroupMembSysadminID);
			secGroupDeleteSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteSecGroupMembSysadmin);
			secGroupDeleteSecGroupMembSysadminID = secGroupDeleteSecGroupMembSysadmin.getRequiredSecGrpMembId();
		}

	}		

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableSecGrpIncSecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateSecGrpInc;
		CFLibDbKeyHash256 secGroupCreateSecGrpIncID;
		ICFSecSecGrpMemb secGroupCreateSecGrpIncMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateSecGrpIncMembSysadminID;
		ICFSecSecGroup secGroupReadSecGrpInc;
		CFLibDbKeyHash256 secGroupReadSecGrpIncID;
		ICFSecSecGrpMemb secGroupReadSecGrpIncMembSysadmin;
		CFLibDbKeyHash256 secGroupReadSecGrpIncMembSysadminID;
		ICFSecSecGroup secGroupUpdateSecGrpInc;
		CFLibDbKeyHash256 secGroupUpdateSecGrpIncID;
		ICFSecSecGrpMemb secGroupUpdateSecGrpIncMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateSecGrpIncMembSysadminID;
		ICFSecSecGroup secGroupDeleteSecGrpInc;
		CFLibDbKeyHash256 secGroupDeleteSecGrpIncID;
		ICFSecSecGrpMemb secGroupDeleteSecGrpIncMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteSecGrpIncMembSysadminID;

		secGroupCreateSecGrpInc = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateSecGrpInc");
		if (secGroupCreateSecGrpInc != null) {
			secGroupCreateSecGrpIncID = secGroupCreateSecGrpInc.getRequiredSecGroupId();
			secGroupCreateSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateSecGrpIncID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateSecGrpIncMembSysadmin != null) {
				secGroupCreateSecGrpIncMembSysadminID = secGroupCreateSecGrpIncMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateSecGrpIncMembSysadminID = null;
			}
		}
		else {
			secGroupCreateSecGrpIncID = null;
			secGroupCreateSecGrpIncMembSysadmin = null;
			secGroupCreateSecGrpIncMembSysadminID = null;
		}

		secGroupReadSecGrpInc = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadSecGrpInc");
		if (secGroupReadSecGrpInc != null) {
			secGroupReadSecGrpIncID = secGroupReadSecGrpInc.getRequiredSecGroupId();
			secGroupReadSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadSecGrpIncID, ICFSecSchema.getSysAdminId());
			if (secGroupReadSecGrpIncMembSysadmin != null) {
				secGroupReadSecGrpIncMembSysadminID = secGroupReadSecGrpIncMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadSecGrpIncMembSysadminID = null;
			}
		}
		else {
			secGroupReadSecGrpIncID = null;
			secGroupReadSecGrpIncMembSysadmin = null;
			secGroupReadSecGrpIncMembSysadminID = null;
		}

		secGroupUpdateSecGrpInc = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateSecGrpInc");
		if (secGroupUpdateSecGrpInc != null) {
			secGroupUpdateSecGrpIncID = secGroupUpdateSecGrpInc.getRequiredSecGroupId();
			secGroupUpdateSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateSecGrpIncID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateSecGrpIncMembSysadmin != null) {
				secGroupUpdateSecGrpIncMembSysadminID = secGroupUpdateSecGrpIncMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateSecGrpIncMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateSecGrpIncID = null;
			secGroupUpdateSecGrpIncMembSysadmin = null;
			secGroupUpdateSecGrpIncMembSysadminID = null;
		}

		secGroupDeleteSecGrpInc = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteSecGrpInc");
		if (secGroupDeleteSecGrpInc != null) {
			secGroupDeleteSecGrpIncID = secGroupDeleteSecGrpInc.getRequiredSecGroupId();
			secGroupDeleteSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteSecGrpIncID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteSecGrpIncMembSysadmin != null) {
				secGroupDeleteSecGrpIncMembSysadminID = secGroupDeleteSecGrpIncMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteSecGrpIncMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteSecGrpIncID = null;
			secGroupDeleteSecGrpIncMembSysadmin = null;
			secGroupDeleteSecGrpIncMembSysadminID = null;
		}


		if (secGroupCreateSecGrpIncID == null || secGroupCreateSecGrpIncID.isNull()) {
			secGroupCreateSecGrpIncID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateSecGrpIncMembSysadminID == null || secGroupCreateSecGrpIncMembSysadminID.isNull()) {
			secGroupCreateSecGrpIncMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadSecGrpIncID == null || secGroupReadSecGrpIncID.isNull()) {
			secGroupReadSecGrpIncID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadSecGrpIncMembSysadminID == null || secGroupReadSecGrpIncMembSysadminID.isNull()) {
			secGroupReadSecGrpIncMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateSecGrpIncID == null || secGroupUpdateSecGrpIncID.isNull()) {
			secGroupUpdateSecGrpIncID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateSecGrpIncMembSysadminID == null || secGroupUpdateSecGrpIncMembSysadminID.isNull()) {
			secGroupUpdateSecGrpIncMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteSecGrpIncID == null || secGroupDeleteSecGrpIncID.isNull()) {
			secGroupDeleteSecGrpIncID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteSecGrpIncMembSysadminID == null || secGroupDeleteSecGrpIncMembSysadminID.isNull()) {
			secGroupDeleteSecGrpIncMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateSecGrpInc == null) {
			secGroupCreateSecGrpInc = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateSecGrpInc.setRequiredRevision(1);
			secGroupCreateSecGrpInc.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateSecGrpInc.setRequiredName("CreateSecGrpInc");
			secGroupCreateSecGrpInc.setRequiredIsVisible(true);
			secGroupCreateSecGrpInc.setRequiredSecGroupId(secGroupCreateSecGrpIncID);
			secGroupCreateSecGrpInc = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateSecGrpInc);
			secGroupCreateSecGrpIncID = secGroupCreateSecGrpInc.getRequiredSecGroupId();
		}

		if (secGroupCreateSecGrpIncMembSysadmin == null) {
			secGroupCreateSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateSecGrpIncMembSysadmin.setRequiredRevision(1);
			secGroupCreateSecGrpIncMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateSecGrpIncMembSysadmin.setRequiredContainerGroup(secGroupCreateSecGrpIncID);
			secGroupCreateSecGrpIncMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateSecGrpIncMembSysadmin.setRequiredSecGrpMembId(secGroupCreateSecGrpIncMembSysadminID);
			secGroupCreateSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateSecGrpIncMembSysadmin);
			secGroupCreateSecGrpIncMembSysadminID = secGroupCreateSecGrpIncMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadSecGrpInc == null) {
			secGroupReadSecGrpInc = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadSecGrpInc.setRequiredRevision(1);
			secGroupReadSecGrpInc.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadSecGrpInc.setRequiredName("ReadSecGrpInc");
			secGroupReadSecGrpInc.setRequiredIsVisible(true);
			secGroupReadSecGrpInc.setRequiredSecGroupId(secGroupReadSecGrpIncID);
			secGroupReadSecGrpInc = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadSecGrpInc);
			secGroupReadSecGrpIncID = secGroupReadSecGrpInc.getRequiredSecGroupId();
		}

		if (secGroupReadSecGrpIncMembSysadmin == null) {
			secGroupReadSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadSecGrpIncMembSysadmin.setRequiredRevision(1);
			secGroupReadSecGrpIncMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadSecGrpIncMembSysadmin.setRequiredContainerGroup(secGroupReadSecGrpIncID);
			secGroupReadSecGrpIncMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadSecGrpIncMembSysadmin.setRequiredSecGrpMembId(secGroupReadSecGrpIncMembSysadminID);
			secGroupReadSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadSecGrpIncMembSysadmin);
			secGroupReadSecGrpIncMembSysadminID = secGroupReadSecGrpIncMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateSecGrpInc == null) {
			secGroupUpdateSecGrpInc = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateSecGrpInc.setRequiredRevision(1);
			secGroupUpdateSecGrpInc.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateSecGrpInc.setRequiredName("UpdateSecGrpInc");
			secGroupUpdateSecGrpInc.setRequiredIsVisible(true);
			secGroupUpdateSecGrpInc.setRequiredSecGroupId(secGroupUpdateSecGrpIncID);
			secGroupUpdateSecGrpInc = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateSecGrpInc);
			secGroupUpdateSecGrpIncID = secGroupUpdateSecGrpInc.getRequiredSecGroupId();
		}

		if (secGroupUpdateSecGrpIncMembSysadmin == null) {
			secGroupUpdateSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateSecGrpIncMembSysadmin.setRequiredRevision(1);
			secGroupUpdateSecGrpIncMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateSecGrpIncMembSysadmin.setRequiredContainerGroup(secGroupUpdateSecGrpIncID);
			secGroupUpdateSecGrpIncMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateSecGrpIncMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateSecGrpIncMembSysadminID);
			secGroupUpdateSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateSecGrpIncMembSysadmin);
			secGroupUpdateSecGrpIncMembSysadminID = secGroupUpdateSecGrpIncMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteSecGrpInc == null) {
			secGroupDeleteSecGrpInc = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteSecGrpInc.setRequiredRevision(1);
			secGroupDeleteSecGrpInc.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteSecGrpInc.setRequiredName("DeleteSecGrpInc");
			secGroupDeleteSecGrpInc.setRequiredIsVisible(true);
			secGroupDeleteSecGrpInc.setRequiredSecGroupId(secGroupDeleteSecGrpIncID);
			secGroupDeleteSecGrpInc = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteSecGrpInc);
			secGroupDeleteSecGrpIncID = secGroupDeleteSecGrpInc.getRequiredSecGroupId();
		}

		if (secGroupDeleteSecGrpIncMembSysadmin == null) {
			secGroupDeleteSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteSecGrpIncMembSysadmin.setRequiredRevision(1);
			secGroupDeleteSecGrpIncMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteSecGrpIncMembSysadmin.setRequiredContainerGroup(secGroupDeleteSecGrpIncID);
			secGroupDeleteSecGrpIncMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteSecGrpIncMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteSecGrpIncMembSysadminID);
			secGroupDeleteSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteSecGrpIncMembSysadmin);
			secGroupDeleteSecGrpIncMembSysadminID = secGroupDeleteSecGrpIncMembSysadmin.getRequiredSecGrpMembId();
		}

	}		

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableSecGrpMembSecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateSecGrpMemb;
		CFLibDbKeyHash256 secGroupCreateSecGrpMembID;
		ICFSecSecGrpMemb secGroupCreateSecGrpMembMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateSecGrpMembMembSysadminID;
		ICFSecSecGroup secGroupReadSecGrpMemb;
		CFLibDbKeyHash256 secGroupReadSecGrpMembID;
		ICFSecSecGrpMemb secGroupReadSecGrpMembMembSysadmin;
		CFLibDbKeyHash256 secGroupReadSecGrpMembMembSysadminID;
		ICFSecSecGroup secGroupUpdateSecGrpMemb;
		CFLibDbKeyHash256 secGroupUpdateSecGrpMembID;
		ICFSecSecGrpMemb secGroupUpdateSecGrpMembMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateSecGrpMembMembSysadminID;
		ICFSecSecGroup secGroupDeleteSecGrpMemb;
		CFLibDbKeyHash256 secGroupDeleteSecGrpMembID;
		ICFSecSecGrpMemb secGroupDeleteSecGrpMembMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteSecGrpMembMembSysadminID;

		secGroupCreateSecGrpMemb = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateSecGrpMemb");
		if (secGroupCreateSecGrpMemb != null) {
			secGroupCreateSecGrpMembID = secGroupCreateSecGrpMemb.getRequiredSecGroupId();
			secGroupCreateSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateSecGrpMembID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateSecGrpMembMembSysadmin != null) {
				secGroupCreateSecGrpMembMembSysadminID = secGroupCreateSecGrpMembMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateSecGrpMembMembSysadminID = null;
			}
		}
		else {
			secGroupCreateSecGrpMembID = null;
			secGroupCreateSecGrpMembMembSysadmin = null;
			secGroupCreateSecGrpMembMembSysadminID = null;
		}

		secGroupReadSecGrpMemb = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadSecGrpMemb");
		if (secGroupReadSecGrpMemb != null) {
			secGroupReadSecGrpMembID = secGroupReadSecGrpMemb.getRequiredSecGroupId();
			secGroupReadSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadSecGrpMembID, ICFSecSchema.getSysAdminId());
			if (secGroupReadSecGrpMembMembSysadmin != null) {
				secGroupReadSecGrpMembMembSysadminID = secGroupReadSecGrpMembMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadSecGrpMembMembSysadminID = null;
			}
		}
		else {
			secGroupReadSecGrpMembID = null;
			secGroupReadSecGrpMembMembSysadmin = null;
			secGroupReadSecGrpMembMembSysadminID = null;
		}

		secGroupUpdateSecGrpMemb = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateSecGrpMemb");
		if (secGroupUpdateSecGrpMemb != null) {
			secGroupUpdateSecGrpMembID = secGroupUpdateSecGrpMemb.getRequiredSecGroupId();
			secGroupUpdateSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateSecGrpMembID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateSecGrpMembMembSysadmin != null) {
				secGroupUpdateSecGrpMembMembSysadminID = secGroupUpdateSecGrpMembMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateSecGrpMembMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateSecGrpMembID = null;
			secGroupUpdateSecGrpMembMembSysadmin = null;
			secGroupUpdateSecGrpMembMembSysadminID = null;
		}

		secGroupDeleteSecGrpMemb = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteSecGrpMemb");
		if (secGroupDeleteSecGrpMemb != null) {
			secGroupDeleteSecGrpMembID = secGroupDeleteSecGrpMemb.getRequiredSecGroupId();
			secGroupDeleteSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteSecGrpMembID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteSecGrpMembMembSysadmin != null) {
				secGroupDeleteSecGrpMembMembSysadminID = secGroupDeleteSecGrpMembMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteSecGrpMembMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteSecGrpMembID = null;
			secGroupDeleteSecGrpMembMembSysadmin = null;
			secGroupDeleteSecGrpMembMembSysadminID = null;
		}


		if (secGroupCreateSecGrpMembID == null || secGroupCreateSecGrpMembID.isNull()) {
			secGroupCreateSecGrpMembID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateSecGrpMembMembSysadminID == null || secGroupCreateSecGrpMembMembSysadminID.isNull()) {
			secGroupCreateSecGrpMembMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadSecGrpMembID == null || secGroupReadSecGrpMembID.isNull()) {
			secGroupReadSecGrpMembID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadSecGrpMembMembSysadminID == null || secGroupReadSecGrpMembMembSysadminID.isNull()) {
			secGroupReadSecGrpMembMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateSecGrpMembID == null || secGroupUpdateSecGrpMembID.isNull()) {
			secGroupUpdateSecGrpMembID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateSecGrpMembMembSysadminID == null || secGroupUpdateSecGrpMembMembSysadminID.isNull()) {
			secGroupUpdateSecGrpMembMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteSecGrpMembID == null || secGroupDeleteSecGrpMembID.isNull()) {
			secGroupDeleteSecGrpMembID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteSecGrpMembMembSysadminID == null || secGroupDeleteSecGrpMembMembSysadminID.isNull()) {
			secGroupDeleteSecGrpMembMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateSecGrpMemb == null) {
			secGroupCreateSecGrpMemb = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateSecGrpMemb.setRequiredRevision(1);
			secGroupCreateSecGrpMemb.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateSecGrpMemb.setRequiredName("CreateSecGrpMemb");
			secGroupCreateSecGrpMemb.setRequiredIsVisible(true);
			secGroupCreateSecGrpMemb.setRequiredSecGroupId(secGroupCreateSecGrpMembID);
			secGroupCreateSecGrpMemb = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateSecGrpMemb);
			secGroupCreateSecGrpMembID = secGroupCreateSecGrpMemb.getRequiredSecGroupId();
		}

		if (secGroupCreateSecGrpMembMembSysadmin == null) {
			secGroupCreateSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateSecGrpMembMembSysadmin.setRequiredRevision(1);
			secGroupCreateSecGrpMembMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateSecGrpMembMembSysadmin.setRequiredContainerGroup(secGroupCreateSecGrpMembID);
			secGroupCreateSecGrpMembMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateSecGrpMembMembSysadmin.setRequiredSecGrpMembId(secGroupCreateSecGrpMembMembSysadminID);
			secGroupCreateSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateSecGrpMembMembSysadmin);
			secGroupCreateSecGrpMembMembSysadminID = secGroupCreateSecGrpMembMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadSecGrpMemb == null) {
			secGroupReadSecGrpMemb = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadSecGrpMemb.setRequiredRevision(1);
			secGroupReadSecGrpMemb.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadSecGrpMemb.setRequiredName("ReadSecGrpMemb");
			secGroupReadSecGrpMemb.setRequiredIsVisible(true);
			secGroupReadSecGrpMemb.setRequiredSecGroupId(secGroupReadSecGrpMembID);
			secGroupReadSecGrpMemb = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadSecGrpMemb);
			secGroupReadSecGrpMembID = secGroupReadSecGrpMemb.getRequiredSecGroupId();
		}

		if (secGroupReadSecGrpMembMembSysadmin == null) {
			secGroupReadSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadSecGrpMembMembSysadmin.setRequiredRevision(1);
			secGroupReadSecGrpMembMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadSecGrpMembMembSysadmin.setRequiredContainerGroup(secGroupReadSecGrpMembID);
			secGroupReadSecGrpMembMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadSecGrpMembMembSysadmin.setRequiredSecGrpMembId(secGroupReadSecGrpMembMembSysadminID);
			secGroupReadSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadSecGrpMembMembSysadmin);
			secGroupReadSecGrpMembMembSysadminID = secGroupReadSecGrpMembMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateSecGrpMemb == null) {
			secGroupUpdateSecGrpMemb = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateSecGrpMemb.setRequiredRevision(1);
			secGroupUpdateSecGrpMemb.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateSecGrpMemb.setRequiredName("UpdateSecGrpMemb");
			secGroupUpdateSecGrpMemb.setRequiredIsVisible(true);
			secGroupUpdateSecGrpMemb.setRequiredSecGroupId(secGroupUpdateSecGrpMembID);
			secGroupUpdateSecGrpMemb = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateSecGrpMemb);
			secGroupUpdateSecGrpMembID = secGroupUpdateSecGrpMemb.getRequiredSecGroupId();
		}

		if (secGroupUpdateSecGrpMembMembSysadmin == null) {
			secGroupUpdateSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateSecGrpMembMembSysadmin.setRequiredRevision(1);
			secGroupUpdateSecGrpMembMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateSecGrpMembMembSysadmin.setRequiredContainerGroup(secGroupUpdateSecGrpMembID);
			secGroupUpdateSecGrpMembMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateSecGrpMembMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateSecGrpMembMembSysadminID);
			secGroupUpdateSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateSecGrpMembMembSysadmin);
			secGroupUpdateSecGrpMembMembSysadminID = secGroupUpdateSecGrpMembMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteSecGrpMemb == null) {
			secGroupDeleteSecGrpMemb = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteSecGrpMemb.setRequiredRevision(1);
			secGroupDeleteSecGrpMemb.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteSecGrpMemb.setRequiredName("DeleteSecGrpMemb");
			secGroupDeleteSecGrpMemb.setRequiredIsVisible(true);
			secGroupDeleteSecGrpMemb.setRequiredSecGroupId(secGroupDeleteSecGrpMembID);
			secGroupDeleteSecGrpMemb = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteSecGrpMemb);
			secGroupDeleteSecGrpMembID = secGroupDeleteSecGrpMemb.getRequiredSecGroupId();
		}

		if (secGroupDeleteSecGrpMembMembSysadmin == null) {
			secGroupDeleteSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteSecGrpMembMembSysadmin.setRequiredRevision(1);
			secGroupDeleteSecGrpMembMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteSecGrpMembMembSysadmin.setRequiredContainerGroup(secGroupDeleteSecGrpMembID);
			secGroupDeleteSecGrpMembMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteSecGrpMembMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteSecGrpMembMembSysadminID);
			secGroupDeleteSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteSecGrpMembMembSysadmin);
			secGroupDeleteSecGrpMembMembSysadminID = secGroupDeleteSecGrpMembMembSysadmin.getRequiredSecGrpMembId();
		}

	}		

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableSecSessionSecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateSecSession;
		CFLibDbKeyHash256 secGroupCreateSecSessionID;
		ICFSecSecGrpMemb secGroupCreateSecSessionMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateSecSessionMembSysadminID;
		ICFSecSecGroup secGroupReadSecSession;
		CFLibDbKeyHash256 secGroupReadSecSessionID;
		ICFSecSecGrpMemb secGroupReadSecSessionMembSysadmin;
		CFLibDbKeyHash256 secGroupReadSecSessionMembSysadminID;
		ICFSecSecGroup secGroupUpdateSecSession;
		CFLibDbKeyHash256 secGroupUpdateSecSessionID;
		ICFSecSecGrpMemb secGroupUpdateSecSessionMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateSecSessionMembSysadminID;
		ICFSecSecGroup secGroupDeleteSecSession;
		CFLibDbKeyHash256 secGroupDeleteSecSessionID;
		ICFSecSecGrpMemb secGroupDeleteSecSessionMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteSecSessionMembSysadminID;

		secGroupCreateSecSession = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateSecSession");
		if (secGroupCreateSecSession != null) {
			secGroupCreateSecSessionID = secGroupCreateSecSession.getRequiredSecGroupId();
			secGroupCreateSecSessionMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateSecSessionID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateSecSessionMembSysadmin != null) {
				secGroupCreateSecSessionMembSysadminID = secGroupCreateSecSessionMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateSecSessionMembSysadminID = null;
			}
		}
		else {
			secGroupCreateSecSessionID = null;
			secGroupCreateSecSessionMembSysadmin = null;
			secGroupCreateSecSessionMembSysadminID = null;
		}

		secGroupReadSecSession = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadSecSession");
		if (secGroupReadSecSession != null) {
			secGroupReadSecSessionID = secGroupReadSecSession.getRequiredSecGroupId();
			secGroupReadSecSessionMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadSecSessionID, ICFSecSchema.getSysAdminId());
			if (secGroupReadSecSessionMembSysadmin != null) {
				secGroupReadSecSessionMembSysadminID = secGroupReadSecSessionMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadSecSessionMembSysadminID = null;
			}
		}
		else {
			secGroupReadSecSessionID = null;
			secGroupReadSecSessionMembSysadmin = null;
			secGroupReadSecSessionMembSysadminID = null;
		}

		secGroupUpdateSecSession = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateSecSession");
		if (secGroupUpdateSecSession != null) {
			secGroupUpdateSecSessionID = secGroupUpdateSecSession.getRequiredSecGroupId();
			secGroupUpdateSecSessionMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateSecSessionID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateSecSessionMembSysadmin != null) {
				secGroupUpdateSecSessionMembSysadminID = secGroupUpdateSecSessionMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateSecSessionMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateSecSessionID = null;
			secGroupUpdateSecSessionMembSysadmin = null;
			secGroupUpdateSecSessionMembSysadminID = null;
		}

		secGroupDeleteSecSession = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteSecSession");
		if (secGroupDeleteSecSession != null) {
			secGroupDeleteSecSessionID = secGroupDeleteSecSession.getRequiredSecGroupId();
			secGroupDeleteSecSessionMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteSecSessionID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteSecSessionMembSysadmin != null) {
				secGroupDeleteSecSessionMembSysadminID = secGroupDeleteSecSessionMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteSecSessionMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteSecSessionID = null;
			secGroupDeleteSecSessionMembSysadmin = null;
			secGroupDeleteSecSessionMembSysadminID = null;
		}


		if (secGroupCreateSecSessionID == null || secGroupCreateSecSessionID.isNull()) {
			secGroupCreateSecSessionID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateSecSessionMembSysadminID == null || secGroupCreateSecSessionMembSysadminID.isNull()) {
			secGroupCreateSecSessionMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadSecSessionID == null || secGroupReadSecSessionID.isNull()) {
			secGroupReadSecSessionID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadSecSessionMembSysadminID == null || secGroupReadSecSessionMembSysadminID.isNull()) {
			secGroupReadSecSessionMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateSecSessionID == null || secGroupUpdateSecSessionID.isNull()) {
			secGroupUpdateSecSessionID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateSecSessionMembSysadminID == null || secGroupUpdateSecSessionMembSysadminID.isNull()) {
			secGroupUpdateSecSessionMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteSecSessionID == null || secGroupDeleteSecSessionID.isNull()) {
			secGroupDeleteSecSessionID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteSecSessionMembSysadminID == null || secGroupDeleteSecSessionMembSysadminID.isNull()) {
			secGroupDeleteSecSessionMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateSecSession == null) {
			secGroupCreateSecSession = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateSecSession.setRequiredRevision(1);
			secGroupCreateSecSession.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateSecSession.setRequiredName("CreateSecSession");
			secGroupCreateSecSession.setRequiredIsVisible(true);
			secGroupCreateSecSession.setRequiredSecGroupId(secGroupCreateSecSessionID);
			secGroupCreateSecSession = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateSecSession);
			secGroupCreateSecSessionID = secGroupCreateSecSession.getRequiredSecGroupId();
		}

		if (secGroupCreateSecSessionMembSysadmin == null) {
			secGroupCreateSecSessionMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateSecSessionMembSysadmin.setRequiredRevision(1);
			secGroupCreateSecSessionMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateSecSessionMembSysadmin.setRequiredContainerGroup(secGroupCreateSecSessionID);
			secGroupCreateSecSessionMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateSecSessionMembSysadmin.setRequiredSecGrpMembId(secGroupCreateSecSessionMembSysadminID);
			secGroupCreateSecSessionMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateSecSessionMembSysadmin);
			secGroupCreateSecSessionMembSysadminID = secGroupCreateSecSessionMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadSecSession == null) {
			secGroupReadSecSession = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadSecSession.setRequiredRevision(1);
			secGroupReadSecSession.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadSecSession.setRequiredName("ReadSecSession");
			secGroupReadSecSession.setRequiredIsVisible(true);
			secGroupReadSecSession.setRequiredSecGroupId(secGroupReadSecSessionID);
			secGroupReadSecSession = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadSecSession);
			secGroupReadSecSessionID = secGroupReadSecSession.getRequiredSecGroupId();
		}

		if (secGroupReadSecSessionMembSysadmin == null) {
			secGroupReadSecSessionMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadSecSessionMembSysadmin.setRequiredRevision(1);
			secGroupReadSecSessionMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadSecSessionMembSysadmin.setRequiredContainerGroup(secGroupReadSecSessionID);
			secGroupReadSecSessionMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadSecSessionMembSysadmin.setRequiredSecGrpMembId(secGroupReadSecSessionMembSysadminID);
			secGroupReadSecSessionMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadSecSessionMembSysadmin);
			secGroupReadSecSessionMembSysadminID = secGroupReadSecSessionMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateSecSession == null) {
			secGroupUpdateSecSession = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateSecSession.setRequiredRevision(1);
			secGroupUpdateSecSession.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateSecSession.setRequiredName("UpdateSecSession");
			secGroupUpdateSecSession.setRequiredIsVisible(true);
			secGroupUpdateSecSession.setRequiredSecGroupId(secGroupUpdateSecSessionID);
			secGroupUpdateSecSession = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateSecSession);
			secGroupUpdateSecSessionID = secGroupUpdateSecSession.getRequiredSecGroupId();
		}

		if (secGroupUpdateSecSessionMembSysadmin == null) {
			secGroupUpdateSecSessionMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateSecSessionMembSysadmin.setRequiredRevision(1);
			secGroupUpdateSecSessionMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateSecSessionMembSysadmin.setRequiredContainerGroup(secGroupUpdateSecSessionID);
			secGroupUpdateSecSessionMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateSecSessionMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateSecSessionMembSysadminID);
			secGroupUpdateSecSessionMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateSecSessionMembSysadmin);
			secGroupUpdateSecSessionMembSysadminID = secGroupUpdateSecSessionMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteSecSession == null) {
			secGroupDeleteSecSession = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteSecSession.setRequiredRevision(1);
			secGroupDeleteSecSession.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteSecSession.setRequiredName("DeleteSecSession");
			secGroupDeleteSecSession.setRequiredIsVisible(true);
			secGroupDeleteSecSession.setRequiredSecGroupId(secGroupDeleteSecSessionID);
			secGroupDeleteSecSession = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteSecSession);
			secGroupDeleteSecSessionID = secGroupDeleteSecSession.getRequiredSecGroupId();
		}

		if (secGroupDeleteSecSessionMembSysadmin == null) {
			secGroupDeleteSecSessionMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteSecSessionMembSysadmin.setRequiredRevision(1);
			secGroupDeleteSecSessionMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteSecSessionMembSysadmin.setRequiredContainerGroup(secGroupDeleteSecSessionID);
			secGroupDeleteSecSessionMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteSecSessionMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteSecSessionMembSysadminID);
			secGroupDeleteSecSessionMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteSecSessionMembSysadmin);
			secGroupDeleteSecSessionMembSysadminID = secGroupDeleteSecSessionMembSysadmin.getRequiredSecGrpMembId();
		}

	}		

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableSecUserSecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateSecUser;
		CFLibDbKeyHash256 secGroupCreateSecUserID;
		ICFSecSecGrpMemb secGroupCreateSecUserMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateSecUserMembSysadminID;
		ICFSecSecGroup secGroupReadSecUser;
		CFLibDbKeyHash256 secGroupReadSecUserID;
		ICFSecSecGrpMemb secGroupReadSecUserMembSysadmin;
		CFLibDbKeyHash256 secGroupReadSecUserMembSysadminID;
		ICFSecSecGroup secGroupUpdateSecUser;
		CFLibDbKeyHash256 secGroupUpdateSecUserID;
		ICFSecSecGrpMemb secGroupUpdateSecUserMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateSecUserMembSysadminID;
		ICFSecSecGroup secGroupDeleteSecUser;
		CFLibDbKeyHash256 secGroupDeleteSecUserID;
		ICFSecSecGrpMemb secGroupDeleteSecUserMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteSecUserMembSysadminID;

		secGroupCreateSecUser = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateSecUser");
		if (secGroupCreateSecUser != null) {
			secGroupCreateSecUserID = secGroupCreateSecUser.getRequiredSecGroupId();
			secGroupCreateSecUserMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateSecUserID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateSecUserMembSysadmin != null) {
				secGroupCreateSecUserMembSysadminID = secGroupCreateSecUserMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateSecUserMembSysadminID = null;
			}
		}
		else {
			secGroupCreateSecUserID = null;
			secGroupCreateSecUserMembSysadmin = null;
			secGroupCreateSecUserMembSysadminID = null;
		}

		secGroupReadSecUser = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadSecUser");
		if (secGroupReadSecUser != null) {
			secGroupReadSecUserID = secGroupReadSecUser.getRequiredSecGroupId();
			secGroupReadSecUserMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadSecUserID, ICFSecSchema.getSysAdminId());
			if (secGroupReadSecUserMembSysadmin != null) {
				secGroupReadSecUserMembSysadminID = secGroupReadSecUserMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadSecUserMembSysadminID = null;
			}
		}
		else {
			secGroupReadSecUserID = null;
			secGroupReadSecUserMembSysadmin = null;
			secGroupReadSecUserMembSysadminID = null;
		}

		secGroupUpdateSecUser = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateSecUser");
		if (secGroupUpdateSecUser != null) {
			secGroupUpdateSecUserID = secGroupUpdateSecUser.getRequiredSecGroupId();
			secGroupUpdateSecUserMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateSecUserID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateSecUserMembSysadmin != null) {
				secGroupUpdateSecUserMembSysadminID = secGroupUpdateSecUserMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateSecUserMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateSecUserID = null;
			secGroupUpdateSecUserMembSysadmin = null;
			secGroupUpdateSecUserMembSysadminID = null;
		}

		secGroupDeleteSecUser = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteSecUser");
		if (secGroupDeleteSecUser != null) {
			secGroupDeleteSecUserID = secGroupDeleteSecUser.getRequiredSecGroupId();
			secGroupDeleteSecUserMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteSecUserID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteSecUserMembSysadmin != null) {
				secGroupDeleteSecUserMembSysadminID = secGroupDeleteSecUserMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteSecUserMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteSecUserID = null;
			secGroupDeleteSecUserMembSysadmin = null;
			secGroupDeleteSecUserMembSysadminID = null;
		}


		if (secGroupCreateSecUserID == null || secGroupCreateSecUserID.isNull()) {
			secGroupCreateSecUserID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateSecUserMembSysadminID == null || secGroupCreateSecUserMembSysadminID.isNull()) {
			secGroupCreateSecUserMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadSecUserID == null || secGroupReadSecUserID.isNull()) {
			secGroupReadSecUserID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadSecUserMembSysadminID == null || secGroupReadSecUserMembSysadminID.isNull()) {
			secGroupReadSecUserMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateSecUserID == null || secGroupUpdateSecUserID.isNull()) {
			secGroupUpdateSecUserID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateSecUserMembSysadminID == null || secGroupUpdateSecUserMembSysadminID.isNull()) {
			secGroupUpdateSecUserMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteSecUserID == null || secGroupDeleteSecUserID.isNull()) {
			secGroupDeleteSecUserID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteSecUserMembSysadminID == null || secGroupDeleteSecUserMembSysadminID.isNull()) {
			secGroupDeleteSecUserMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateSecUser == null) {
			secGroupCreateSecUser = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateSecUser.setRequiredRevision(1);
			secGroupCreateSecUser.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateSecUser.setRequiredName("CreateSecUser");
			secGroupCreateSecUser.setRequiredIsVisible(true);
			secGroupCreateSecUser.setRequiredSecGroupId(secGroupCreateSecUserID);
			secGroupCreateSecUser = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateSecUser);
			secGroupCreateSecUserID = secGroupCreateSecUser.getRequiredSecGroupId();
		}

		if (secGroupCreateSecUserMembSysadmin == null) {
			secGroupCreateSecUserMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateSecUserMembSysadmin.setRequiredRevision(1);
			secGroupCreateSecUserMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateSecUserMembSysadmin.setRequiredContainerGroup(secGroupCreateSecUserID);
			secGroupCreateSecUserMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateSecUserMembSysadmin.setRequiredSecGrpMembId(secGroupCreateSecUserMembSysadminID);
			secGroupCreateSecUserMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateSecUserMembSysadmin);
			secGroupCreateSecUserMembSysadminID = secGroupCreateSecUserMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadSecUser == null) {
			secGroupReadSecUser = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadSecUser.setRequiredRevision(1);
			secGroupReadSecUser.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadSecUser.setRequiredName("ReadSecUser");
			secGroupReadSecUser.setRequiredIsVisible(true);
			secGroupReadSecUser.setRequiredSecGroupId(secGroupReadSecUserID);
			secGroupReadSecUser = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadSecUser);
			secGroupReadSecUserID = secGroupReadSecUser.getRequiredSecGroupId();
		}

		if (secGroupReadSecUserMembSysadmin == null) {
			secGroupReadSecUserMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadSecUserMembSysadmin.setRequiredRevision(1);
			secGroupReadSecUserMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadSecUserMembSysadmin.setRequiredContainerGroup(secGroupReadSecUserID);
			secGroupReadSecUserMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadSecUserMembSysadmin.setRequiredSecGrpMembId(secGroupReadSecUserMembSysadminID);
			secGroupReadSecUserMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadSecUserMembSysadmin);
			secGroupReadSecUserMembSysadminID = secGroupReadSecUserMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateSecUser == null) {
			secGroupUpdateSecUser = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateSecUser.setRequiredRevision(1);
			secGroupUpdateSecUser.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateSecUser.setRequiredName("UpdateSecUser");
			secGroupUpdateSecUser.setRequiredIsVisible(true);
			secGroupUpdateSecUser.setRequiredSecGroupId(secGroupUpdateSecUserID);
			secGroupUpdateSecUser = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateSecUser);
			secGroupUpdateSecUserID = secGroupUpdateSecUser.getRequiredSecGroupId();
		}

		if (secGroupUpdateSecUserMembSysadmin == null) {
			secGroupUpdateSecUserMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateSecUserMembSysadmin.setRequiredRevision(1);
			secGroupUpdateSecUserMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateSecUserMembSysadmin.setRequiredContainerGroup(secGroupUpdateSecUserID);
			secGroupUpdateSecUserMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateSecUserMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateSecUserMembSysadminID);
			secGroupUpdateSecUserMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateSecUserMembSysadmin);
			secGroupUpdateSecUserMembSysadminID = secGroupUpdateSecUserMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteSecUser == null) {
			secGroupDeleteSecUser = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteSecUser.setRequiredRevision(1);
			secGroupDeleteSecUser.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteSecUser.setRequiredName("DeleteSecUser");
			secGroupDeleteSecUser.setRequiredIsVisible(true);
			secGroupDeleteSecUser.setRequiredSecGroupId(secGroupDeleteSecUserID);
			secGroupDeleteSecUser = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteSecUser);
			secGroupDeleteSecUserID = secGroupDeleteSecUser.getRequiredSecGroupId();
		}

		if (secGroupDeleteSecUserMembSysadmin == null) {
			secGroupDeleteSecUserMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteSecUserMembSysadmin.setRequiredRevision(1);
			secGroupDeleteSecUserMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteSecUserMembSysadmin.setRequiredContainerGroup(secGroupDeleteSecUserID);
			secGroupDeleteSecUserMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteSecUserMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteSecUserMembSysadminID);
			secGroupDeleteSecUserMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteSecUserMembSysadmin);
			secGroupDeleteSecUserMembSysadminID = secGroupDeleteSecUserMembSysadmin.getRequiredSecGrpMembId();
		}

	}		

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableServiceSecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateService;
		CFLibDbKeyHash256 secGroupCreateServiceID;
		ICFSecSecGrpMemb secGroupCreateServiceMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateServiceMembSysadminID;
		ICFSecSecGroup secGroupReadService;
		CFLibDbKeyHash256 secGroupReadServiceID;
		ICFSecSecGrpMemb secGroupReadServiceMembSysadmin;
		CFLibDbKeyHash256 secGroupReadServiceMembSysadminID;
		ICFSecSecGroup secGroupUpdateService;
		CFLibDbKeyHash256 secGroupUpdateServiceID;
		ICFSecSecGrpMemb secGroupUpdateServiceMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateServiceMembSysadminID;
		ICFSecSecGroup secGroupDeleteService;
		CFLibDbKeyHash256 secGroupDeleteServiceID;
		ICFSecSecGrpMemb secGroupDeleteServiceMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteServiceMembSysadminID;

		secGroupCreateService = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateService");
		if (secGroupCreateService != null) {
			secGroupCreateServiceID = secGroupCreateService.getRequiredSecGroupId();
			secGroupCreateServiceMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateServiceID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateServiceMembSysadmin != null) {
				secGroupCreateServiceMembSysadminID = secGroupCreateServiceMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateServiceMembSysadminID = null;
			}
		}
		else {
			secGroupCreateServiceID = null;
			secGroupCreateServiceMembSysadmin = null;
			secGroupCreateServiceMembSysadminID = null;
		}

		secGroupReadService = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadService");
		if (secGroupReadService != null) {
			secGroupReadServiceID = secGroupReadService.getRequiredSecGroupId();
			secGroupReadServiceMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadServiceID, ICFSecSchema.getSysAdminId());
			if (secGroupReadServiceMembSysadmin != null) {
				secGroupReadServiceMembSysadminID = secGroupReadServiceMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadServiceMembSysadminID = null;
			}
		}
		else {
			secGroupReadServiceID = null;
			secGroupReadServiceMembSysadmin = null;
			secGroupReadServiceMembSysadminID = null;
		}

		secGroupUpdateService = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateService");
		if (secGroupUpdateService != null) {
			secGroupUpdateServiceID = secGroupUpdateService.getRequiredSecGroupId();
			secGroupUpdateServiceMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateServiceID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateServiceMembSysadmin != null) {
				secGroupUpdateServiceMembSysadminID = secGroupUpdateServiceMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateServiceMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateServiceID = null;
			secGroupUpdateServiceMembSysadmin = null;
			secGroupUpdateServiceMembSysadminID = null;
		}

		secGroupDeleteService = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteService");
		if (secGroupDeleteService != null) {
			secGroupDeleteServiceID = secGroupDeleteService.getRequiredSecGroupId();
			secGroupDeleteServiceMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteServiceID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteServiceMembSysadmin != null) {
				secGroupDeleteServiceMembSysadminID = secGroupDeleteServiceMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteServiceMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteServiceID = null;
			secGroupDeleteServiceMembSysadmin = null;
			secGroupDeleteServiceMembSysadminID = null;
		}


		if (secGroupCreateServiceID == null || secGroupCreateServiceID.isNull()) {
			secGroupCreateServiceID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateServiceMembSysadminID == null || secGroupCreateServiceMembSysadminID.isNull()) {
			secGroupCreateServiceMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadServiceID == null || secGroupReadServiceID.isNull()) {
			secGroupReadServiceID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadServiceMembSysadminID == null || secGroupReadServiceMembSysadminID.isNull()) {
			secGroupReadServiceMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateServiceID == null || secGroupUpdateServiceID.isNull()) {
			secGroupUpdateServiceID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateServiceMembSysadminID == null || secGroupUpdateServiceMembSysadminID.isNull()) {
			secGroupUpdateServiceMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteServiceID == null || secGroupDeleteServiceID.isNull()) {
			secGroupDeleteServiceID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteServiceMembSysadminID == null || secGroupDeleteServiceMembSysadminID.isNull()) {
			secGroupDeleteServiceMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateService == null) {
			secGroupCreateService = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateService.setRequiredRevision(1);
			secGroupCreateService.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateService.setRequiredName("CreateService");
			secGroupCreateService.setRequiredIsVisible(true);
			secGroupCreateService.setRequiredSecGroupId(secGroupCreateServiceID);
			secGroupCreateService = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateService);
			secGroupCreateServiceID = secGroupCreateService.getRequiredSecGroupId();
		}

		if (secGroupCreateServiceMembSysadmin == null) {
			secGroupCreateServiceMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateServiceMembSysadmin.setRequiredRevision(1);
			secGroupCreateServiceMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateServiceMembSysadmin.setRequiredContainerGroup(secGroupCreateServiceID);
			secGroupCreateServiceMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateServiceMembSysadmin.setRequiredSecGrpMembId(secGroupCreateServiceMembSysadminID);
			secGroupCreateServiceMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateServiceMembSysadmin);
			secGroupCreateServiceMembSysadminID = secGroupCreateServiceMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadService == null) {
			secGroupReadService = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadService.setRequiredRevision(1);
			secGroupReadService.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadService.setRequiredName("ReadService");
			secGroupReadService.setRequiredIsVisible(true);
			secGroupReadService.setRequiredSecGroupId(secGroupReadServiceID);
			secGroupReadService = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadService);
			secGroupReadServiceID = secGroupReadService.getRequiredSecGroupId();
		}

		if (secGroupReadServiceMembSysadmin == null) {
			secGroupReadServiceMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadServiceMembSysadmin.setRequiredRevision(1);
			secGroupReadServiceMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadServiceMembSysadmin.setRequiredContainerGroup(secGroupReadServiceID);
			secGroupReadServiceMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadServiceMembSysadmin.setRequiredSecGrpMembId(secGroupReadServiceMembSysadminID);
			secGroupReadServiceMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadServiceMembSysadmin);
			secGroupReadServiceMembSysadminID = secGroupReadServiceMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateService == null) {
			secGroupUpdateService = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateService.setRequiredRevision(1);
			secGroupUpdateService.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateService.setRequiredName("UpdateService");
			secGroupUpdateService.setRequiredIsVisible(true);
			secGroupUpdateService.setRequiredSecGroupId(secGroupUpdateServiceID);
			secGroupUpdateService = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateService);
			secGroupUpdateServiceID = secGroupUpdateService.getRequiredSecGroupId();
		}

		if (secGroupUpdateServiceMembSysadmin == null) {
			secGroupUpdateServiceMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateServiceMembSysadmin.setRequiredRevision(1);
			secGroupUpdateServiceMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateServiceMembSysadmin.setRequiredContainerGroup(secGroupUpdateServiceID);
			secGroupUpdateServiceMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateServiceMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateServiceMembSysadminID);
			secGroupUpdateServiceMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateServiceMembSysadmin);
			secGroupUpdateServiceMembSysadminID = secGroupUpdateServiceMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteService == null) {
			secGroupDeleteService = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteService.setRequiredRevision(1);
			secGroupDeleteService.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteService.setRequiredName("DeleteService");
			secGroupDeleteService.setRequiredIsVisible(true);
			secGroupDeleteService.setRequiredSecGroupId(secGroupDeleteServiceID);
			secGroupDeleteService = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteService);
			secGroupDeleteServiceID = secGroupDeleteService.getRequiredSecGroupId();
		}

		if (secGroupDeleteServiceMembSysadmin == null) {
			secGroupDeleteServiceMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteServiceMembSysadmin.setRequiredRevision(1);
			secGroupDeleteServiceMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteServiceMembSysadmin.setRequiredContainerGroup(secGroupDeleteServiceID);
			secGroupDeleteServiceMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteServiceMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteServiceMembSysadminID);
			secGroupDeleteServiceMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteServiceMembSysadmin);
			secGroupDeleteServiceMembSysadminID = secGroupDeleteServiceMembSysadmin.getRequiredSecGrpMembId();
		}

	}		

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableServiceTypeSecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateServiceType;
		CFLibDbKeyHash256 secGroupCreateServiceTypeID;
		ICFSecSecGrpMemb secGroupCreateServiceTypeMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateServiceTypeMembSysadminID;
		ICFSecSecGroup secGroupReadServiceType;
		CFLibDbKeyHash256 secGroupReadServiceTypeID;
		ICFSecSecGrpMemb secGroupReadServiceTypeMembSysadmin;
		CFLibDbKeyHash256 secGroupReadServiceTypeMembSysadminID;
		ICFSecSecGroup secGroupUpdateServiceType;
		CFLibDbKeyHash256 secGroupUpdateServiceTypeID;
		ICFSecSecGrpMemb secGroupUpdateServiceTypeMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateServiceTypeMembSysadminID;
		ICFSecSecGroup secGroupDeleteServiceType;
		CFLibDbKeyHash256 secGroupDeleteServiceTypeID;
		ICFSecSecGrpMemb secGroupDeleteServiceTypeMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteServiceTypeMembSysadminID;

		secGroupCreateServiceType = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateServiceType");
		if (secGroupCreateServiceType != null) {
			secGroupCreateServiceTypeID = secGroupCreateServiceType.getRequiredSecGroupId();
			secGroupCreateServiceTypeMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateServiceTypeID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateServiceTypeMembSysadmin != null) {
				secGroupCreateServiceTypeMembSysadminID = secGroupCreateServiceTypeMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateServiceTypeMembSysadminID = null;
			}
		}
		else {
			secGroupCreateServiceTypeID = null;
			secGroupCreateServiceTypeMembSysadmin = null;
			secGroupCreateServiceTypeMembSysadminID = null;
		}

		secGroupReadServiceType = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadServiceType");
		if (secGroupReadServiceType != null) {
			secGroupReadServiceTypeID = secGroupReadServiceType.getRequiredSecGroupId();
			secGroupReadServiceTypeMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadServiceTypeID, ICFSecSchema.getSysAdminId());
			if (secGroupReadServiceTypeMembSysadmin != null) {
				secGroupReadServiceTypeMembSysadminID = secGroupReadServiceTypeMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadServiceTypeMembSysadminID = null;
			}
		}
		else {
			secGroupReadServiceTypeID = null;
			secGroupReadServiceTypeMembSysadmin = null;
			secGroupReadServiceTypeMembSysadminID = null;
		}

		secGroupUpdateServiceType = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateServiceType");
		if (secGroupUpdateServiceType != null) {
			secGroupUpdateServiceTypeID = secGroupUpdateServiceType.getRequiredSecGroupId();
			secGroupUpdateServiceTypeMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateServiceTypeID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateServiceTypeMembSysadmin != null) {
				secGroupUpdateServiceTypeMembSysadminID = secGroupUpdateServiceTypeMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateServiceTypeMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateServiceTypeID = null;
			secGroupUpdateServiceTypeMembSysadmin = null;
			secGroupUpdateServiceTypeMembSysadminID = null;
		}

		secGroupDeleteServiceType = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteServiceType");
		if (secGroupDeleteServiceType != null) {
			secGroupDeleteServiceTypeID = secGroupDeleteServiceType.getRequiredSecGroupId();
			secGroupDeleteServiceTypeMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteServiceTypeID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteServiceTypeMembSysadmin != null) {
				secGroupDeleteServiceTypeMembSysadminID = secGroupDeleteServiceTypeMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteServiceTypeMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteServiceTypeID = null;
			secGroupDeleteServiceTypeMembSysadmin = null;
			secGroupDeleteServiceTypeMembSysadminID = null;
		}


		if (secGroupCreateServiceTypeID == null || secGroupCreateServiceTypeID.isNull()) {
			secGroupCreateServiceTypeID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateServiceTypeMembSysadminID == null || secGroupCreateServiceTypeMembSysadminID.isNull()) {
			secGroupCreateServiceTypeMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadServiceTypeID == null || secGroupReadServiceTypeID.isNull()) {
			secGroupReadServiceTypeID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadServiceTypeMembSysadminID == null || secGroupReadServiceTypeMembSysadminID.isNull()) {
			secGroupReadServiceTypeMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateServiceTypeID == null || secGroupUpdateServiceTypeID.isNull()) {
			secGroupUpdateServiceTypeID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateServiceTypeMembSysadminID == null || secGroupUpdateServiceTypeMembSysadminID.isNull()) {
			secGroupUpdateServiceTypeMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteServiceTypeID == null || secGroupDeleteServiceTypeID.isNull()) {
			secGroupDeleteServiceTypeID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteServiceTypeMembSysadminID == null || secGroupDeleteServiceTypeMembSysadminID.isNull()) {
			secGroupDeleteServiceTypeMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateServiceType == null) {
			secGroupCreateServiceType = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateServiceType.setRequiredRevision(1);
			secGroupCreateServiceType.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateServiceType.setRequiredName("CreateServiceType");
			secGroupCreateServiceType.setRequiredIsVisible(true);
			secGroupCreateServiceType.setRequiredSecGroupId(secGroupCreateServiceTypeID);
			secGroupCreateServiceType = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateServiceType);
			secGroupCreateServiceTypeID = secGroupCreateServiceType.getRequiredSecGroupId();
		}

		if (secGroupCreateServiceTypeMembSysadmin == null) {
			secGroupCreateServiceTypeMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateServiceTypeMembSysadmin.setRequiredRevision(1);
			secGroupCreateServiceTypeMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateServiceTypeMembSysadmin.setRequiredContainerGroup(secGroupCreateServiceTypeID);
			secGroupCreateServiceTypeMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateServiceTypeMembSysadmin.setRequiredSecGrpMembId(secGroupCreateServiceTypeMembSysadminID);
			secGroupCreateServiceTypeMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateServiceTypeMembSysadmin);
			secGroupCreateServiceTypeMembSysadminID = secGroupCreateServiceTypeMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadServiceType == null) {
			secGroupReadServiceType = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadServiceType.setRequiredRevision(1);
			secGroupReadServiceType.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadServiceType.setRequiredName("ReadServiceType");
			secGroupReadServiceType.setRequiredIsVisible(true);
			secGroupReadServiceType.setRequiredSecGroupId(secGroupReadServiceTypeID);
			secGroupReadServiceType = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadServiceType);
			secGroupReadServiceTypeID = secGroupReadServiceType.getRequiredSecGroupId();
		}

		if (secGroupReadServiceTypeMembSysadmin == null) {
			secGroupReadServiceTypeMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadServiceTypeMembSysadmin.setRequiredRevision(1);
			secGroupReadServiceTypeMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadServiceTypeMembSysadmin.setRequiredContainerGroup(secGroupReadServiceTypeID);
			secGroupReadServiceTypeMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadServiceTypeMembSysadmin.setRequiredSecGrpMembId(secGroupReadServiceTypeMembSysadminID);
			secGroupReadServiceTypeMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadServiceTypeMembSysadmin);
			secGroupReadServiceTypeMembSysadminID = secGroupReadServiceTypeMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateServiceType == null) {
			secGroupUpdateServiceType = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateServiceType.setRequiredRevision(1);
			secGroupUpdateServiceType.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateServiceType.setRequiredName("UpdateServiceType");
			secGroupUpdateServiceType.setRequiredIsVisible(true);
			secGroupUpdateServiceType.setRequiredSecGroupId(secGroupUpdateServiceTypeID);
			secGroupUpdateServiceType = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateServiceType);
			secGroupUpdateServiceTypeID = secGroupUpdateServiceType.getRequiredSecGroupId();
		}

		if (secGroupUpdateServiceTypeMembSysadmin == null) {
			secGroupUpdateServiceTypeMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateServiceTypeMembSysadmin.setRequiredRevision(1);
			secGroupUpdateServiceTypeMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateServiceTypeMembSysadmin.setRequiredContainerGroup(secGroupUpdateServiceTypeID);
			secGroupUpdateServiceTypeMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateServiceTypeMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateServiceTypeMembSysadminID);
			secGroupUpdateServiceTypeMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateServiceTypeMembSysadmin);
			secGroupUpdateServiceTypeMembSysadminID = secGroupUpdateServiceTypeMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteServiceType == null) {
			secGroupDeleteServiceType = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteServiceType.setRequiredRevision(1);
			secGroupDeleteServiceType.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteServiceType.setRequiredName("DeleteServiceType");
			secGroupDeleteServiceType.setRequiredIsVisible(true);
			secGroupDeleteServiceType.setRequiredSecGroupId(secGroupDeleteServiceTypeID);
			secGroupDeleteServiceType = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteServiceType);
			secGroupDeleteServiceTypeID = secGroupDeleteServiceType.getRequiredSecGroupId();
		}

		if (secGroupDeleteServiceTypeMembSysadmin == null) {
			secGroupDeleteServiceTypeMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteServiceTypeMembSysadmin.setRequiredRevision(1);
			secGroupDeleteServiceTypeMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteServiceTypeMembSysadmin.setRequiredContainerGroup(secGroupDeleteServiceTypeID);
			secGroupDeleteServiceTypeMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteServiceTypeMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteServiceTypeMembSysadminID);
			secGroupDeleteServiceTypeMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteServiceTypeMembSysadmin);
			secGroupDeleteServiceTypeMembSysadminID = secGroupDeleteServiceTypeMembSysadmin.getRequiredSecGrpMembId();
		}

	}		

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableSysClusterSecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateSysCluster;
		CFLibDbKeyHash256 secGroupCreateSysClusterID;
		ICFSecSecGrpMemb secGroupCreateSysClusterMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateSysClusterMembSysadminID;
		ICFSecSecGroup secGroupReadSysCluster;
		CFLibDbKeyHash256 secGroupReadSysClusterID;
		ICFSecSecGrpMemb secGroupReadSysClusterMembSysadmin;
		CFLibDbKeyHash256 secGroupReadSysClusterMembSysadminID;
		ICFSecSecGroup secGroupUpdateSysCluster;
		CFLibDbKeyHash256 secGroupUpdateSysClusterID;
		ICFSecSecGrpMemb secGroupUpdateSysClusterMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateSysClusterMembSysadminID;
		ICFSecSecGroup secGroupDeleteSysCluster;
		CFLibDbKeyHash256 secGroupDeleteSysClusterID;
		ICFSecSecGrpMemb secGroupDeleteSysClusterMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteSysClusterMembSysadminID;

		secGroupCreateSysCluster = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateSysCluster");
		if (secGroupCreateSysCluster != null) {
			secGroupCreateSysClusterID = secGroupCreateSysCluster.getRequiredSecGroupId();
			secGroupCreateSysClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateSysClusterID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateSysClusterMembSysadmin != null) {
				secGroupCreateSysClusterMembSysadminID = secGroupCreateSysClusterMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateSysClusterMembSysadminID = null;
			}
		}
		else {
			secGroupCreateSysClusterID = null;
			secGroupCreateSysClusterMembSysadmin = null;
			secGroupCreateSysClusterMembSysadminID = null;
		}

		secGroupReadSysCluster = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadSysCluster");
		if (secGroupReadSysCluster != null) {
			secGroupReadSysClusterID = secGroupReadSysCluster.getRequiredSecGroupId();
			secGroupReadSysClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadSysClusterID, ICFSecSchema.getSysAdminId());
			if (secGroupReadSysClusterMembSysadmin != null) {
				secGroupReadSysClusterMembSysadminID = secGroupReadSysClusterMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadSysClusterMembSysadminID = null;
			}
		}
		else {
			secGroupReadSysClusterID = null;
			secGroupReadSysClusterMembSysadmin = null;
			secGroupReadSysClusterMembSysadminID = null;
		}

		secGroupUpdateSysCluster = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateSysCluster");
		if (secGroupUpdateSysCluster != null) {
			secGroupUpdateSysClusterID = secGroupUpdateSysCluster.getRequiredSecGroupId();
			secGroupUpdateSysClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateSysClusterID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateSysClusterMembSysadmin != null) {
				secGroupUpdateSysClusterMembSysadminID = secGroupUpdateSysClusterMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateSysClusterMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateSysClusterID = null;
			secGroupUpdateSysClusterMembSysadmin = null;
			secGroupUpdateSysClusterMembSysadminID = null;
		}

		secGroupDeleteSysCluster = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteSysCluster");
		if (secGroupDeleteSysCluster != null) {
			secGroupDeleteSysClusterID = secGroupDeleteSysCluster.getRequiredSecGroupId();
			secGroupDeleteSysClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteSysClusterID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteSysClusterMembSysadmin != null) {
				secGroupDeleteSysClusterMembSysadminID = secGroupDeleteSysClusterMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteSysClusterMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteSysClusterID = null;
			secGroupDeleteSysClusterMembSysadmin = null;
			secGroupDeleteSysClusterMembSysadminID = null;
		}


		if (secGroupCreateSysClusterID == null || secGroupCreateSysClusterID.isNull()) {
			secGroupCreateSysClusterID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateSysClusterMembSysadminID == null || secGroupCreateSysClusterMembSysadminID.isNull()) {
			secGroupCreateSysClusterMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadSysClusterID == null || secGroupReadSysClusterID.isNull()) {
			secGroupReadSysClusterID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadSysClusterMembSysadminID == null || secGroupReadSysClusterMembSysadminID.isNull()) {
			secGroupReadSysClusterMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateSysClusterID == null || secGroupUpdateSysClusterID.isNull()) {
			secGroupUpdateSysClusterID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateSysClusterMembSysadminID == null || secGroupUpdateSysClusterMembSysadminID.isNull()) {
			secGroupUpdateSysClusterMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteSysClusterID == null || secGroupDeleteSysClusterID.isNull()) {
			secGroupDeleteSysClusterID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteSysClusterMembSysadminID == null || secGroupDeleteSysClusterMembSysadminID.isNull()) {
			secGroupDeleteSysClusterMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateSysCluster == null) {
			secGroupCreateSysCluster = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateSysCluster.setRequiredRevision(1);
			secGroupCreateSysCluster.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateSysCluster.setRequiredName("CreateSysCluster");
			secGroupCreateSysCluster.setRequiredIsVisible(true);
			secGroupCreateSysCluster.setRequiredSecGroupId(secGroupCreateSysClusterID);
			secGroupCreateSysCluster = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateSysCluster);
			secGroupCreateSysClusterID = secGroupCreateSysCluster.getRequiredSecGroupId();
		}

		if (secGroupCreateSysClusterMembSysadmin == null) {
			secGroupCreateSysClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateSysClusterMembSysadmin.setRequiredRevision(1);
			secGroupCreateSysClusterMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateSysClusterMembSysadmin.setRequiredContainerGroup(secGroupCreateSysClusterID);
			secGroupCreateSysClusterMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateSysClusterMembSysadmin.setRequiredSecGrpMembId(secGroupCreateSysClusterMembSysadminID);
			secGroupCreateSysClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateSysClusterMembSysadmin);
			secGroupCreateSysClusterMembSysadminID = secGroupCreateSysClusterMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadSysCluster == null) {
			secGroupReadSysCluster = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadSysCluster.setRequiredRevision(1);
			secGroupReadSysCluster.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadSysCluster.setRequiredName("ReadSysCluster");
			secGroupReadSysCluster.setRequiredIsVisible(true);
			secGroupReadSysCluster.setRequiredSecGroupId(secGroupReadSysClusterID);
			secGroupReadSysCluster = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadSysCluster);
			secGroupReadSysClusterID = secGroupReadSysCluster.getRequiredSecGroupId();
		}

		if (secGroupReadSysClusterMembSysadmin == null) {
			secGroupReadSysClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadSysClusterMembSysadmin.setRequiredRevision(1);
			secGroupReadSysClusterMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadSysClusterMembSysadmin.setRequiredContainerGroup(secGroupReadSysClusterID);
			secGroupReadSysClusterMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadSysClusterMembSysadmin.setRequiredSecGrpMembId(secGroupReadSysClusterMembSysadminID);
			secGroupReadSysClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadSysClusterMembSysadmin);
			secGroupReadSysClusterMembSysadminID = secGroupReadSysClusterMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateSysCluster == null) {
			secGroupUpdateSysCluster = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateSysCluster.setRequiredRevision(1);
			secGroupUpdateSysCluster.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateSysCluster.setRequiredName("UpdateSysCluster");
			secGroupUpdateSysCluster.setRequiredIsVisible(true);
			secGroupUpdateSysCluster.setRequiredSecGroupId(secGroupUpdateSysClusterID);
			secGroupUpdateSysCluster = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateSysCluster);
			secGroupUpdateSysClusterID = secGroupUpdateSysCluster.getRequiredSecGroupId();
		}

		if (secGroupUpdateSysClusterMembSysadmin == null) {
			secGroupUpdateSysClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateSysClusterMembSysadmin.setRequiredRevision(1);
			secGroupUpdateSysClusterMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateSysClusterMembSysadmin.setRequiredContainerGroup(secGroupUpdateSysClusterID);
			secGroupUpdateSysClusterMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateSysClusterMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateSysClusterMembSysadminID);
			secGroupUpdateSysClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateSysClusterMembSysadmin);
			secGroupUpdateSysClusterMembSysadminID = secGroupUpdateSysClusterMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteSysCluster == null) {
			secGroupDeleteSysCluster = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteSysCluster.setRequiredRevision(1);
			secGroupDeleteSysCluster.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteSysCluster.setRequiredName("DeleteSysCluster");
			secGroupDeleteSysCluster.setRequiredIsVisible(true);
			secGroupDeleteSysCluster.setRequiredSecGroupId(secGroupDeleteSysClusterID);
			secGroupDeleteSysCluster = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteSysCluster);
			secGroupDeleteSysClusterID = secGroupDeleteSysCluster.getRequiredSecGroupId();
		}

		if (secGroupDeleteSysClusterMembSysadmin == null) {
			secGroupDeleteSysClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteSysClusterMembSysadmin.setRequiredRevision(1);
			secGroupDeleteSysClusterMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteSysClusterMembSysadmin.setRequiredContainerGroup(secGroupDeleteSysClusterID);
			secGroupDeleteSysClusterMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteSysClusterMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteSysClusterMembSysadminID);
			secGroupDeleteSysClusterMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteSysClusterMembSysadmin);
			secGroupDeleteSysClusterMembSysadminID = secGroupDeleteSysClusterMembSysadmin.getRequiredSecGrpMembId();
		}

	}		

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableTenantSecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateTenant;
		CFLibDbKeyHash256 secGroupCreateTenantID;
		ICFSecSecGrpMemb secGroupCreateTenantMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateTenantMembSysadminID;
		ICFSecSecGroup secGroupReadTenant;
		CFLibDbKeyHash256 secGroupReadTenantID;
		ICFSecSecGrpMemb secGroupReadTenantMembSysadmin;
		CFLibDbKeyHash256 secGroupReadTenantMembSysadminID;
		ICFSecSecGroup secGroupUpdateTenant;
		CFLibDbKeyHash256 secGroupUpdateTenantID;
		ICFSecSecGrpMemb secGroupUpdateTenantMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateTenantMembSysadminID;
		ICFSecSecGroup secGroupDeleteTenant;
		CFLibDbKeyHash256 secGroupDeleteTenantID;
		ICFSecSecGrpMemb secGroupDeleteTenantMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteTenantMembSysadminID;

		secGroupCreateTenant = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateTenant");
		if (secGroupCreateTenant != null) {
			secGroupCreateTenantID = secGroupCreateTenant.getRequiredSecGroupId();
			secGroupCreateTenantMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateTenantID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateTenantMembSysadmin != null) {
				secGroupCreateTenantMembSysadminID = secGroupCreateTenantMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateTenantMembSysadminID = null;
			}
		}
		else {
			secGroupCreateTenantID = null;
			secGroupCreateTenantMembSysadmin = null;
			secGroupCreateTenantMembSysadminID = null;
		}

		secGroupReadTenant = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadTenant");
		if (secGroupReadTenant != null) {
			secGroupReadTenantID = secGroupReadTenant.getRequiredSecGroupId();
			secGroupReadTenantMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadTenantID, ICFSecSchema.getSysAdminId());
			if (secGroupReadTenantMembSysadmin != null) {
				secGroupReadTenantMembSysadminID = secGroupReadTenantMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadTenantMembSysadminID = null;
			}
		}
		else {
			secGroupReadTenantID = null;
			secGroupReadTenantMembSysadmin = null;
			secGroupReadTenantMembSysadminID = null;
		}

		secGroupUpdateTenant = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateTenant");
		if (secGroupUpdateTenant != null) {
			secGroupUpdateTenantID = secGroupUpdateTenant.getRequiredSecGroupId();
			secGroupUpdateTenantMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateTenantID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateTenantMembSysadmin != null) {
				secGroupUpdateTenantMembSysadminID = secGroupUpdateTenantMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateTenantMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateTenantID = null;
			secGroupUpdateTenantMembSysadmin = null;
			secGroupUpdateTenantMembSysadminID = null;
		}

		secGroupDeleteTenant = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteTenant");
		if (secGroupDeleteTenant != null) {
			secGroupDeleteTenantID = secGroupDeleteTenant.getRequiredSecGroupId();
			secGroupDeleteTenantMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteTenantID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteTenantMembSysadmin != null) {
				secGroupDeleteTenantMembSysadminID = secGroupDeleteTenantMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteTenantMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteTenantID = null;
			secGroupDeleteTenantMembSysadmin = null;
			secGroupDeleteTenantMembSysadminID = null;
		}


		if (secGroupCreateTenantID == null || secGroupCreateTenantID.isNull()) {
			secGroupCreateTenantID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateTenantMembSysadminID == null || secGroupCreateTenantMembSysadminID.isNull()) {
			secGroupCreateTenantMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadTenantID == null || secGroupReadTenantID.isNull()) {
			secGroupReadTenantID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadTenantMembSysadminID == null || secGroupReadTenantMembSysadminID.isNull()) {
			secGroupReadTenantMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateTenantID == null || secGroupUpdateTenantID.isNull()) {
			secGroupUpdateTenantID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateTenantMembSysadminID == null || secGroupUpdateTenantMembSysadminID.isNull()) {
			secGroupUpdateTenantMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteTenantID == null || secGroupDeleteTenantID.isNull()) {
			secGroupDeleteTenantID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteTenantMembSysadminID == null || secGroupDeleteTenantMembSysadminID.isNull()) {
			secGroupDeleteTenantMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateTenant == null) {
			secGroupCreateTenant = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateTenant.setRequiredRevision(1);
			secGroupCreateTenant.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateTenant.setRequiredName("CreateTenant");
			secGroupCreateTenant.setRequiredIsVisible(true);
			secGroupCreateTenant.setRequiredSecGroupId(secGroupCreateTenantID);
			secGroupCreateTenant = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateTenant);
			secGroupCreateTenantID = secGroupCreateTenant.getRequiredSecGroupId();
		}

		if (secGroupCreateTenantMembSysadmin == null) {
			secGroupCreateTenantMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateTenantMembSysadmin.setRequiredRevision(1);
			secGroupCreateTenantMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateTenantMembSysadmin.setRequiredContainerGroup(secGroupCreateTenantID);
			secGroupCreateTenantMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateTenantMembSysadmin.setRequiredSecGrpMembId(secGroupCreateTenantMembSysadminID);
			secGroupCreateTenantMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateTenantMembSysadmin);
			secGroupCreateTenantMembSysadminID = secGroupCreateTenantMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadTenant == null) {
			secGroupReadTenant = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadTenant.setRequiredRevision(1);
			secGroupReadTenant.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadTenant.setRequiredName("ReadTenant");
			secGroupReadTenant.setRequiredIsVisible(true);
			secGroupReadTenant.setRequiredSecGroupId(secGroupReadTenantID);
			secGroupReadTenant = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadTenant);
			secGroupReadTenantID = secGroupReadTenant.getRequiredSecGroupId();
		}

		if (secGroupReadTenantMembSysadmin == null) {
			secGroupReadTenantMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadTenantMembSysadmin.setRequiredRevision(1);
			secGroupReadTenantMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadTenantMembSysadmin.setRequiredContainerGroup(secGroupReadTenantID);
			secGroupReadTenantMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadTenantMembSysadmin.setRequiredSecGrpMembId(secGroupReadTenantMembSysadminID);
			secGroupReadTenantMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadTenantMembSysadmin);
			secGroupReadTenantMembSysadminID = secGroupReadTenantMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateTenant == null) {
			secGroupUpdateTenant = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateTenant.setRequiredRevision(1);
			secGroupUpdateTenant.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateTenant.setRequiredName("UpdateTenant");
			secGroupUpdateTenant.setRequiredIsVisible(true);
			secGroupUpdateTenant.setRequiredSecGroupId(secGroupUpdateTenantID);
			secGroupUpdateTenant = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateTenant);
			secGroupUpdateTenantID = secGroupUpdateTenant.getRequiredSecGroupId();
		}

		if (secGroupUpdateTenantMembSysadmin == null) {
			secGroupUpdateTenantMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateTenantMembSysadmin.setRequiredRevision(1);
			secGroupUpdateTenantMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateTenantMembSysadmin.setRequiredContainerGroup(secGroupUpdateTenantID);
			secGroupUpdateTenantMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateTenantMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateTenantMembSysadminID);
			secGroupUpdateTenantMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateTenantMembSysadmin);
			secGroupUpdateTenantMembSysadminID = secGroupUpdateTenantMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteTenant == null) {
			secGroupDeleteTenant = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteTenant.setRequiredRevision(1);
			secGroupDeleteTenant.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteTenant.setRequiredName("DeleteTenant");
			secGroupDeleteTenant.setRequiredIsVisible(true);
			secGroupDeleteTenant.setRequiredSecGroupId(secGroupDeleteTenantID);
			secGroupDeleteTenant = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteTenant);
			secGroupDeleteTenantID = secGroupDeleteTenant.getRequiredSecGroupId();
		}

		if (secGroupDeleteTenantMembSysadmin == null) {
			secGroupDeleteTenantMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteTenantMembSysadmin.setRequiredRevision(1);
			secGroupDeleteTenantMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteTenantMembSysadmin.setRequiredContainerGroup(secGroupDeleteTenantID);
			secGroupDeleteTenantMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteTenantMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteTenantMembSysadminID);
			secGroupDeleteTenantMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteTenantMembSysadmin);
			secGroupDeleteTenantMembSysadminID = secGroupDeleteTenantMembSysadmin.getRequiredSecGrpMembId();
		}

	}		

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableTSecGroupSecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateTSecGroup;
		CFLibDbKeyHash256 secGroupCreateTSecGroupID;
		ICFSecSecGrpMemb secGroupCreateTSecGroupMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateTSecGroupMembSysadminID;
		ICFSecSecGroup secGroupReadTSecGroup;
		CFLibDbKeyHash256 secGroupReadTSecGroupID;
		ICFSecSecGrpMemb secGroupReadTSecGroupMembSysadmin;
		CFLibDbKeyHash256 secGroupReadTSecGroupMembSysadminID;
		ICFSecSecGroup secGroupUpdateTSecGroup;
		CFLibDbKeyHash256 secGroupUpdateTSecGroupID;
		ICFSecSecGrpMemb secGroupUpdateTSecGroupMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateTSecGroupMembSysadminID;
		ICFSecSecGroup secGroupDeleteTSecGroup;
		CFLibDbKeyHash256 secGroupDeleteTSecGroupID;
		ICFSecSecGrpMemb secGroupDeleteTSecGroupMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteTSecGroupMembSysadminID;

		secGroupCreateTSecGroup = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateTSecGroup");
		if (secGroupCreateTSecGroup != null) {
			secGroupCreateTSecGroupID = secGroupCreateTSecGroup.getRequiredSecGroupId();
			secGroupCreateTSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateTSecGroupID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateTSecGroupMembSysadmin != null) {
				secGroupCreateTSecGroupMembSysadminID = secGroupCreateTSecGroupMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateTSecGroupMembSysadminID = null;
			}
		}
		else {
			secGroupCreateTSecGroupID = null;
			secGroupCreateTSecGroupMembSysadmin = null;
			secGroupCreateTSecGroupMembSysadminID = null;
		}

		secGroupReadTSecGroup = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadTSecGroup");
		if (secGroupReadTSecGroup != null) {
			secGroupReadTSecGroupID = secGroupReadTSecGroup.getRequiredSecGroupId();
			secGroupReadTSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadTSecGroupID, ICFSecSchema.getSysAdminId());
			if (secGroupReadTSecGroupMembSysadmin != null) {
				secGroupReadTSecGroupMembSysadminID = secGroupReadTSecGroupMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadTSecGroupMembSysadminID = null;
			}
		}
		else {
			secGroupReadTSecGroupID = null;
			secGroupReadTSecGroupMembSysadmin = null;
			secGroupReadTSecGroupMembSysadminID = null;
		}

		secGroupUpdateTSecGroup = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateTSecGroup");
		if (secGroupUpdateTSecGroup != null) {
			secGroupUpdateTSecGroupID = secGroupUpdateTSecGroup.getRequiredSecGroupId();
			secGroupUpdateTSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateTSecGroupID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateTSecGroupMembSysadmin != null) {
				secGroupUpdateTSecGroupMembSysadminID = secGroupUpdateTSecGroupMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateTSecGroupMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateTSecGroupID = null;
			secGroupUpdateTSecGroupMembSysadmin = null;
			secGroupUpdateTSecGroupMembSysadminID = null;
		}

		secGroupDeleteTSecGroup = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteTSecGroup");
		if (secGroupDeleteTSecGroup != null) {
			secGroupDeleteTSecGroupID = secGroupDeleteTSecGroup.getRequiredSecGroupId();
			secGroupDeleteTSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteTSecGroupID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteTSecGroupMembSysadmin != null) {
				secGroupDeleteTSecGroupMembSysadminID = secGroupDeleteTSecGroupMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteTSecGroupMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteTSecGroupID = null;
			secGroupDeleteTSecGroupMembSysadmin = null;
			secGroupDeleteTSecGroupMembSysadminID = null;
		}


		if (secGroupCreateTSecGroupID == null || secGroupCreateTSecGroupID.isNull()) {
			secGroupCreateTSecGroupID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateTSecGroupMembSysadminID == null || secGroupCreateTSecGroupMembSysadminID.isNull()) {
			secGroupCreateTSecGroupMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadTSecGroupID == null || secGroupReadTSecGroupID.isNull()) {
			secGroupReadTSecGroupID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadTSecGroupMembSysadminID == null || secGroupReadTSecGroupMembSysadminID.isNull()) {
			secGroupReadTSecGroupMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateTSecGroupID == null || secGroupUpdateTSecGroupID.isNull()) {
			secGroupUpdateTSecGroupID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateTSecGroupMembSysadminID == null || secGroupUpdateTSecGroupMembSysadminID.isNull()) {
			secGroupUpdateTSecGroupMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteTSecGroupID == null || secGroupDeleteTSecGroupID.isNull()) {
			secGroupDeleteTSecGroupID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteTSecGroupMembSysadminID == null || secGroupDeleteTSecGroupMembSysadminID.isNull()) {
			secGroupDeleteTSecGroupMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateTSecGroup == null) {
			secGroupCreateTSecGroup = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateTSecGroup.setRequiredRevision(1);
			secGroupCreateTSecGroup.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateTSecGroup.setRequiredName("CreateTSecGroup");
			secGroupCreateTSecGroup.setRequiredIsVisible(true);
			secGroupCreateTSecGroup.setRequiredSecGroupId(secGroupCreateTSecGroupID);
			secGroupCreateTSecGroup = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateTSecGroup);
			secGroupCreateTSecGroupID = secGroupCreateTSecGroup.getRequiredSecGroupId();
		}

		if (secGroupCreateTSecGroupMembSysadmin == null) {
			secGroupCreateTSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateTSecGroupMembSysadmin.setRequiredRevision(1);
			secGroupCreateTSecGroupMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateTSecGroupMembSysadmin.setRequiredContainerGroup(secGroupCreateTSecGroupID);
			secGroupCreateTSecGroupMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateTSecGroupMembSysadmin.setRequiredSecGrpMembId(secGroupCreateTSecGroupMembSysadminID);
			secGroupCreateTSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateTSecGroupMembSysadmin);
			secGroupCreateTSecGroupMembSysadminID = secGroupCreateTSecGroupMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadTSecGroup == null) {
			secGroupReadTSecGroup = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadTSecGroup.setRequiredRevision(1);
			secGroupReadTSecGroup.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadTSecGroup.setRequiredName("ReadTSecGroup");
			secGroupReadTSecGroup.setRequiredIsVisible(true);
			secGroupReadTSecGroup.setRequiredSecGroupId(secGroupReadTSecGroupID);
			secGroupReadTSecGroup = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadTSecGroup);
			secGroupReadTSecGroupID = secGroupReadTSecGroup.getRequiredSecGroupId();
		}

		if (secGroupReadTSecGroupMembSysadmin == null) {
			secGroupReadTSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadTSecGroupMembSysadmin.setRequiredRevision(1);
			secGroupReadTSecGroupMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadTSecGroupMembSysadmin.setRequiredContainerGroup(secGroupReadTSecGroupID);
			secGroupReadTSecGroupMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadTSecGroupMembSysadmin.setRequiredSecGrpMembId(secGroupReadTSecGroupMembSysadminID);
			secGroupReadTSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadTSecGroupMembSysadmin);
			secGroupReadTSecGroupMembSysadminID = secGroupReadTSecGroupMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateTSecGroup == null) {
			secGroupUpdateTSecGroup = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateTSecGroup.setRequiredRevision(1);
			secGroupUpdateTSecGroup.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateTSecGroup.setRequiredName("UpdateTSecGroup");
			secGroupUpdateTSecGroup.setRequiredIsVisible(true);
			secGroupUpdateTSecGroup.setRequiredSecGroupId(secGroupUpdateTSecGroupID);
			secGroupUpdateTSecGroup = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateTSecGroup);
			secGroupUpdateTSecGroupID = secGroupUpdateTSecGroup.getRequiredSecGroupId();
		}

		if (secGroupUpdateTSecGroupMembSysadmin == null) {
			secGroupUpdateTSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateTSecGroupMembSysadmin.setRequiredRevision(1);
			secGroupUpdateTSecGroupMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateTSecGroupMembSysadmin.setRequiredContainerGroup(secGroupUpdateTSecGroupID);
			secGroupUpdateTSecGroupMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateTSecGroupMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateTSecGroupMembSysadminID);
			secGroupUpdateTSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateTSecGroupMembSysadmin);
			secGroupUpdateTSecGroupMembSysadminID = secGroupUpdateTSecGroupMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteTSecGroup == null) {
			secGroupDeleteTSecGroup = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteTSecGroup.setRequiredRevision(1);
			secGroupDeleteTSecGroup.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteTSecGroup.setRequiredName("DeleteTSecGroup");
			secGroupDeleteTSecGroup.setRequiredIsVisible(true);
			secGroupDeleteTSecGroup.setRequiredSecGroupId(secGroupDeleteTSecGroupID);
			secGroupDeleteTSecGroup = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteTSecGroup);
			secGroupDeleteTSecGroupID = secGroupDeleteTSecGroup.getRequiredSecGroupId();
		}

		if (secGroupDeleteTSecGroupMembSysadmin == null) {
			secGroupDeleteTSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteTSecGroupMembSysadmin.setRequiredRevision(1);
			secGroupDeleteTSecGroupMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteTSecGroupMembSysadmin.setRequiredContainerGroup(secGroupDeleteTSecGroupID);
			secGroupDeleteTSecGroupMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteTSecGroupMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteTSecGroupMembSysadminID);
			secGroupDeleteTSecGroupMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteTSecGroupMembSysadmin);
			secGroupDeleteTSecGroupMembSysadminID = secGroupDeleteTSecGroupMembSysadmin.getRequiredSecGrpMembId();
		}

	}		

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableTSecGrpIncSecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateTSecGrpInc;
		CFLibDbKeyHash256 secGroupCreateTSecGrpIncID;
		ICFSecSecGrpMemb secGroupCreateTSecGrpIncMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateTSecGrpIncMembSysadminID;
		ICFSecSecGroup secGroupReadTSecGrpInc;
		CFLibDbKeyHash256 secGroupReadTSecGrpIncID;
		ICFSecSecGrpMemb secGroupReadTSecGrpIncMembSysadmin;
		CFLibDbKeyHash256 secGroupReadTSecGrpIncMembSysadminID;
		ICFSecSecGroup secGroupUpdateTSecGrpInc;
		CFLibDbKeyHash256 secGroupUpdateTSecGrpIncID;
		ICFSecSecGrpMemb secGroupUpdateTSecGrpIncMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateTSecGrpIncMembSysadminID;
		ICFSecSecGroup secGroupDeleteTSecGrpInc;
		CFLibDbKeyHash256 secGroupDeleteTSecGrpIncID;
		ICFSecSecGrpMemb secGroupDeleteTSecGrpIncMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteTSecGrpIncMembSysadminID;

		secGroupCreateTSecGrpInc = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateTSecGrpInc");
		if (secGroupCreateTSecGrpInc != null) {
			secGroupCreateTSecGrpIncID = secGroupCreateTSecGrpInc.getRequiredSecGroupId();
			secGroupCreateTSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateTSecGrpIncID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateTSecGrpIncMembSysadmin != null) {
				secGroupCreateTSecGrpIncMembSysadminID = secGroupCreateTSecGrpIncMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateTSecGrpIncMembSysadminID = null;
			}
		}
		else {
			secGroupCreateTSecGrpIncID = null;
			secGroupCreateTSecGrpIncMembSysadmin = null;
			secGroupCreateTSecGrpIncMembSysadminID = null;
		}

		secGroupReadTSecGrpInc = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadTSecGrpInc");
		if (secGroupReadTSecGrpInc != null) {
			secGroupReadTSecGrpIncID = secGroupReadTSecGrpInc.getRequiredSecGroupId();
			secGroupReadTSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadTSecGrpIncID, ICFSecSchema.getSysAdminId());
			if (secGroupReadTSecGrpIncMembSysadmin != null) {
				secGroupReadTSecGrpIncMembSysadminID = secGroupReadTSecGrpIncMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadTSecGrpIncMembSysadminID = null;
			}
		}
		else {
			secGroupReadTSecGrpIncID = null;
			secGroupReadTSecGrpIncMembSysadmin = null;
			secGroupReadTSecGrpIncMembSysadminID = null;
		}

		secGroupUpdateTSecGrpInc = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateTSecGrpInc");
		if (secGroupUpdateTSecGrpInc != null) {
			secGroupUpdateTSecGrpIncID = secGroupUpdateTSecGrpInc.getRequiredSecGroupId();
			secGroupUpdateTSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateTSecGrpIncID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateTSecGrpIncMembSysadmin != null) {
				secGroupUpdateTSecGrpIncMembSysadminID = secGroupUpdateTSecGrpIncMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateTSecGrpIncMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateTSecGrpIncID = null;
			secGroupUpdateTSecGrpIncMembSysadmin = null;
			secGroupUpdateTSecGrpIncMembSysadminID = null;
		}

		secGroupDeleteTSecGrpInc = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteTSecGrpInc");
		if (secGroupDeleteTSecGrpInc != null) {
			secGroupDeleteTSecGrpIncID = secGroupDeleteTSecGrpInc.getRequiredSecGroupId();
			secGroupDeleteTSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteTSecGrpIncID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteTSecGrpIncMembSysadmin != null) {
				secGroupDeleteTSecGrpIncMembSysadminID = secGroupDeleteTSecGrpIncMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteTSecGrpIncMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteTSecGrpIncID = null;
			secGroupDeleteTSecGrpIncMembSysadmin = null;
			secGroupDeleteTSecGrpIncMembSysadminID = null;
		}


		if (secGroupCreateTSecGrpIncID == null || secGroupCreateTSecGrpIncID.isNull()) {
			secGroupCreateTSecGrpIncID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateTSecGrpIncMembSysadminID == null || secGroupCreateTSecGrpIncMembSysadminID.isNull()) {
			secGroupCreateTSecGrpIncMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadTSecGrpIncID == null || secGroupReadTSecGrpIncID.isNull()) {
			secGroupReadTSecGrpIncID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadTSecGrpIncMembSysadminID == null || secGroupReadTSecGrpIncMembSysadminID.isNull()) {
			secGroupReadTSecGrpIncMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateTSecGrpIncID == null || secGroupUpdateTSecGrpIncID.isNull()) {
			secGroupUpdateTSecGrpIncID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateTSecGrpIncMembSysadminID == null || secGroupUpdateTSecGrpIncMembSysadminID.isNull()) {
			secGroupUpdateTSecGrpIncMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteTSecGrpIncID == null || secGroupDeleteTSecGrpIncID.isNull()) {
			secGroupDeleteTSecGrpIncID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteTSecGrpIncMembSysadminID == null || secGroupDeleteTSecGrpIncMembSysadminID.isNull()) {
			secGroupDeleteTSecGrpIncMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateTSecGrpInc == null) {
			secGroupCreateTSecGrpInc = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateTSecGrpInc.setRequiredRevision(1);
			secGroupCreateTSecGrpInc.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateTSecGrpInc.setRequiredName("CreateTSecGrpInc");
			secGroupCreateTSecGrpInc.setRequiredIsVisible(true);
			secGroupCreateTSecGrpInc.setRequiredSecGroupId(secGroupCreateTSecGrpIncID);
			secGroupCreateTSecGrpInc = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateTSecGrpInc);
			secGroupCreateTSecGrpIncID = secGroupCreateTSecGrpInc.getRequiredSecGroupId();
		}

		if (secGroupCreateTSecGrpIncMembSysadmin == null) {
			secGroupCreateTSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateTSecGrpIncMembSysadmin.setRequiredRevision(1);
			secGroupCreateTSecGrpIncMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateTSecGrpIncMembSysadmin.setRequiredContainerGroup(secGroupCreateTSecGrpIncID);
			secGroupCreateTSecGrpIncMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateTSecGrpIncMembSysadmin.setRequiredSecGrpMembId(secGroupCreateTSecGrpIncMembSysadminID);
			secGroupCreateTSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateTSecGrpIncMembSysadmin);
			secGroupCreateTSecGrpIncMembSysadminID = secGroupCreateTSecGrpIncMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadTSecGrpInc == null) {
			secGroupReadTSecGrpInc = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadTSecGrpInc.setRequiredRevision(1);
			secGroupReadTSecGrpInc.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadTSecGrpInc.setRequiredName("ReadTSecGrpInc");
			secGroupReadTSecGrpInc.setRequiredIsVisible(true);
			secGroupReadTSecGrpInc.setRequiredSecGroupId(secGroupReadTSecGrpIncID);
			secGroupReadTSecGrpInc = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadTSecGrpInc);
			secGroupReadTSecGrpIncID = secGroupReadTSecGrpInc.getRequiredSecGroupId();
		}

		if (secGroupReadTSecGrpIncMembSysadmin == null) {
			secGroupReadTSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadTSecGrpIncMembSysadmin.setRequiredRevision(1);
			secGroupReadTSecGrpIncMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadTSecGrpIncMembSysadmin.setRequiredContainerGroup(secGroupReadTSecGrpIncID);
			secGroupReadTSecGrpIncMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadTSecGrpIncMembSysadmin.setRequiredSecGrpMembId(secGroupReadTSecGrpIncMembSysadminID);
			secGroupReadTSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadTSecGrpIncMembSysadmin);
			secGroupReadTSecGrpIncMembSysadminID = secGroupReadTSecGrpIncMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateTSecGrpInc == null) {
			secGroupUpdateTSecGrpInc = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateTSecGrpInc.setRequiredRevision(1);
			secGroupUpdateTSecGrpInc.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateTSecGrpInc.setRequiredName("UpdateTSecGrpInc");
			secGroupUpdateTSecGrpInc.setRequiredIsVisible(true);
			secGroupUpdateTSecGrpInc.setRequiredSecGroupId(secGroupUpdateTSecGrpIncID);
			secGroupUpdateTSecGrpInc = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateTSecGrpInc);
			secGroupUpdateTSecGrpIncID = secGroupUpdateTSecGrpInc.getRequiredSecGroupId();
		}

		if (secGroupUpdateTSecGrpIncMembSysadmin == null) {
			secGroupUpdateTSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateTSecGrpIncMembSysadmin.setRequiredRevision(1);
			secGroupUpdateTSecGrpIncMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateTSecGrpIncMembSysadmin.setRequiredContainerGroup(secGroupUpdateTSecGrpIncID);
			secGroupUpdateTSecGrpIncMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateTSecGrpIncMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateTSecGrpIncMembSysadminID);
			secGroupUpdateTSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateTSecGrpIncMembSysadmin);
			secGroupUpdateTSecGrpIncMembSysadminID = secGroupUpdateTSecGrpIncMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteTSecGrpInc == null) {
			secGroupDeleteTSecGrpInc = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteTSecGrpInc.setRequiredRevision(1);
			secGroupDeleteTSecGrpInc.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteTSecGrpInc.setRequiredName("DeleteTSecGrpInc");
			secGroupDeleteTSecGrpInc.setRequiredIsVisible(true);
			secGroupDeleteTSecGrpInc.setRequiredSecGroupId(secGroupDeleteTSecGrpIncID);
			secGroupDeleteTSecGrpInc = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteTSecGrpInc);
			secGroupDeleteTSecGrpIncID = secGroupDeleteTSecGrpInc.getRequiredSecGroupId();
		}

		if (secGroupDeleteTSecGrpIncMembSysadmin == null) {
			secGroupDeleteTSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteTSecGrpIncMembSysadmin.setRequiredRevision(1);
			secGroupDeleteTSecGrpIncMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteTSecGrpIncMembSysadmin.setRequiredContainerGroup(secGroupDeleteTSecGrpIncID);
			secGroupDeleteTSecGrpIncMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteTSecGrpIncMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteTSecGrpIncMembSysadminID);
			secGroupDeleteTSecGrpIncMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteTSecGrpIncMembSysadmin);
			secGroupDeleteTSecGrpIncMembSysadminID = secGroupDeleteTSecGrpIncMembSysadmin.getRequiredSecGrpMembId();
		}

	}		

	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = NoResultException.class, transactionManager = "cfsec31TransactionManager")
	public void bootstrapTableTSecGrpMembSecurity(ICFSecAuthorization auth) {
		LocalDateTime now = LocalDateTime.now();

		ICFSecSecGroup secGroupCreateTSecGrpMemb;
		CFLibDbKeyHash256 secGroupCreateTSecGrpMembID;
		ICFSecSecGrpMemb secGroupCreateTSecGrpMembMembSysadmin;
		CFLibDbKeyHash256 secGroupCreateTSecGrpMembMembSysadminID;
		ICFSecSecGroup secGroupReadTSecGrpMemb;
		CFLibDbKeyHash256 secGroupReadTSecGrpMembID;
		ICFSecSecGrpMemb secGroupReadTSecGrpMembMembSysadmin;
		CFLibDbKeyHash256 secGroupReadTSecGrpMembMembSysadminID;
		ICFSecSecGroup secGroupUpdateTSecGrpMemb;
		CFLibDbKeyHash256 secGroupUpdateTSecGrpMembID;
		ICFSecSecGrpMemb secGroupUpdateTSecGrpMembMembSysadmin;
		CFLibDbKeyHash256 secGroupUpdateTSecGrpMembMembSysadminID;
		ICFSecSecGroup secGroupDeleteTSecGrpMemb;
		CFLibDbKeyHash256 secGroupDeleteTSecGrpMembID;
		ICFSecSecGrpMemb secGroupDeleteTSecGrpMembMembSysadmin;
		CFLibDbKeyHash256 secGroupDeleteTSecGrpMembMembSysadminID;

		secGroupCreateTSecGrpMemb = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "CreateTSecGrpMemb");
		if (secGroupCreateTSecGrpMemb != null) {
			secGroupCreateTSecGrpMembID = secGroupCreateTSecGrpMemb.getRequiredSecGroupId();
			secGroupCreateTSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupCreateTSecGrpMembID, ICFSecSchema.getSysAdminId());
			if (secGroupCreateTSecGrpMembMembSysadmin != null) {
				secGroupCreateTSecGrpMembMembSysadminID = secGroupCreateTSecGrpMembMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupCreateTSecGrpMembMembSysadminID = null;
			}
		}
		else {
			secGroupCreateTSecGrpMembID = null;
			secGroupCreateTSecGrpMembMembSysadmin = null;
			secGroupCreateTSecGrpMembMembSysadminID = null;
		}

		secGroupReadTSecGrpMemb = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "ReadTSecGrpMemb");
		if (secGroupReadTSecGrpMemb != null) {
			secGroupReadTSecGrpMembID = secGroupReadTSecGrpMemb.getRequiredSecGroupId();
			secGroupReadTSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupReadTSecGrpMembID, ICFSecSchema.getSysAdminId());
			if (secGroupReadTSecGrpMembMembSysadmin != null) {
				secGroupReadTSecGrpMembMembSysadminID = secGroupReadTSecGrpMembMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupReadTSecGrpMembMembSysadminID = null;
			}
		}
		else {
			secGroupReadTSecGrpMembID = null;
			secGroupReadTSecGrpMembMembSysadmin = null;
			secGroupReadTSecGrpMembMembSysadminID = null;
		}

		secGroupUpdateTSecGrpMemb = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "UpdateTSecGrpMemb");
		if (secGroupUpdateTSecGrpMemb != null) {
			secGroupUpdateTSecGrpMembID = secGroupUpdateTSecGrpMemb.getRequiredSecGroupId();
			secGroupUpdateTSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupUpdateTSecGrpMembID, ICFSecSchema.getSysAdminId());
			if (secGroupUpdateTSecGrpMembMembSysadmin != null) {
				secGroupUpdateTSecGrpMembMembSysadminID = secGroupUpdateTSecGrpMembMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupUpdateTSecGrpMembMembSysadminID = null;
			}
		}
		else {
			secGroupUpdateTSecGrpMembID = null;
			secGroupUpdateTSecGrpMembMembSysadmin = null;
			secGroupUpdateTSecGrpMembMembSysadminID = null;
		}

		secGroupDeleteTSecGrpMemb = ICFSecSchema.getBackingCFSec().getTableSecGroup().readDerivedByUNameIdx(auth, ICFSecSchema.getSysClusterId(), "DeleteTSecGrpMemb");
		if (secGroupDeleteTSecGrpMemb != null) {
			secGroupDeleteTSecGrpMembID = secGroupDeleteTSecGrpMemb.getRequiredSecGroupId();
			secGroupDeleteTSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().readDerivedByUUserIdx(auth, ICFSecSchema.getSysClusterId(), secGroupDeleteTSecGrpMembID, ICFSecSchema.getSysAdminId());
			if (secGroupDeleteTSecGrpMembMembSysadmin != null) {
				secGroupDeleteTSecGrpMembMembSysadminID = secGroupDeleteTSecGrpMembMembSysadmin.getRequiredSecGrpMembId();
			}
			else {
				secGroupDeleteTSecGrpMembMembSysadminID = null;
			}
		}
		else {
			secGroupDeleteTSecGrpMembID = null;
			secGroupDeleteTSecGrpMembMembSysadmin = null;
			secGroupDeleteTSecGrpMembMembSysadminID = null;
		}


		if (secGroupCreateTSecGrpMembID == null || secGroupCreateTSecGrpMembID.isNull()) {
			secGroupCreateTSecGrpMembID = new CFLibDbKeyHash256(0);
		}
		if (secGroupCreateTSecGrpMembMembSysadminID == null || secGroupCreateTSecGrpMembMembSysadminID.isNull()) {
			secGroupCreateTSecGrpMembMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadTSecGrpMembID == null || secGroupReadTSecGrpMembID.isNull()) {
			secGroupReadTSecGrpMembID = new CFLibDbKeyHash256(0);
		}
		if (secGroupReadTSecGrpMembMembSysadminID == null || secGroupReadTSecGrpMembMembSysadminID.isNull()) {
			secGroupReadTSecGrpMembMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateTSecGrpMembID == null || secGroupUpdateTSecGrpMembID.isNull()) {
			secGroupUpdateTSecGrpMembID = new CFLibDbKeyHash256(0);
		}
		if (secGroupUpdateTSecGrpMembMembSysadminID == null || secGroupUpdateTSecGrpMembMembSysadminID.isNull()) {
			secGroupUpdateTSecGrpMembMembSysadminID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteTSecGrpMembID == null || secGroupDeleteTSecGrpMembID.isNull()) {
			secGroupDeleteTSecGrpMembID = new CFLibDbKeyHash256(0);
		}
		if (secGroupDeleteTSecGrpMembMembSysadminID == null || secGroupDeleteTSecGrpMembMembSysadminID.isNull()) {
			secGroupDeleteTSecGrpMembMembSysadminID = new CFLibDbKeyHash256(0);
		}

		if (secGroupCreateTSecGrpMemb == null) {
			secGroupCreateTSecGrpMemb = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupCreateTSecGrpMemb.setRequiredRevision(1);
			secGroupCreateTSecGrpMemb.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateTSecGrpMemb.setRequiredName("CreateTSecGrpMemb");
			secGroupCreateTSecGrpMemb.setRequiredIsVisible(true);
			secGroupCreateTSecGrpMemb.setRequiredSecGroupId(secGroupCreateTSecGrpMembID);
			secGroupCreateTSecGrpMemb = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupCreateTSecGrpMemb);
			secGroupCreateTSecGrpMembID = secGroupCreateTSecGrpMemb.getRequiredSecGroupId();
		}

		if (secGroupCreateTSecGrpMembMembSysadmin == null) {
			secGroupCreateTSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupCreateTSecGrpMembMembSysadmin.setRequiredRevision(1);
			secGroupCreateTSecGrpMembMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupCreateTSecGrpMembMembSysadmin.setRequiredContainerGroup(secGroupCreateTSecGrpMembID);
			secGroupCreateTSecGrpMembMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupCreateTSecGrpMembMembSysadmin.setRequiredSecGrpMembId(secGroupCreateTSecGrpMembMembSysadminID);
			secGroupCreateTSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupCreateTSecGrpMembMembSysadmin);
			secGroupCreateTSecGrpMembMembSysadminID = secGroupCreateTSecGrpMembMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupReadTSecGrpMemb == null) {
			secGroupReadTSecGrpMemb = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupReadTSecGrpMemb.setRequiredRevision(1);
			secGroupReadTSecGrpMemb.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadTSecGrpMemb.setRequiredName("ReadTSecGrpMemb");
			secGroupReadTSecGrpMemb.setRequiredIsVisible(true);
			secGroupReadTSecGrpMemb.setRequiredSecGroupId(secGroupReadTSecGrpMembID);
			secGroupReadTSecGrpMemb = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupReadTSecGrpMemb);
			secGroupReadTSecGrpMembID = secGroupReadTSecGrpMemb.getRequiredSecGroupId();
		}

		if (secGroupReadTSecGrpMembMembSysadmin == null) {
			secGroupReadTSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupReadTSecGrpMembMembSysadmin.setRequiredRevision(1);
			secGroupReadTSecGrpMembMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupReadTSecGrpMembMembSysadmin.setRequiredContainerGroup(secGroupReadTSecGrpMembID);
			secGroupReadTSecGrpMembMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupReadTSecGrpMembMembSysadmin.setRequiredSecGrpMembId(secGroupReadTSecGrpMembMembSysadminID);
			secGroupReadTSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupReadTSecGrpMembMembSysadmin);
			secGroupReadTSecGrpMembMembSysadminID = secGroupReadTSecGrpMembMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupUpdateTSecGrpMemb == null) {
			secGroupUpdateTSecGrpMemb = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupUpdateTSecGrpMemb.setRequiredRevision(1);
			secGroupUpdateTSecGrpMemb.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateTSecGrpMemb.setRequiredName("UpdateTSecGrpMemb");
			secGroupUpdateTSecGrpMemb.setRequiredIsVisible(true);
			secGroupUpdateTSecGrpMemb.setRequiredSecGroupId(secGroupUpdateTSecGrpMembID);
			secGroupUpdateTSecGrpMemb = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupUpdateTSecGrpMemb);
			secGroupUpdateTSecGrpMembID = secGroupUpdateTSecGrpMemb.getRequiredSecGroupId();
		}

		if (secGroupUpdateTSecGrpMembMembSysadmin == null) {
			secGroupUpdateTSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupUpdateTSecGrpMembMembSysadmin.setRequiredRevision(1);
			secGroupUpdateTSecGrpMembMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupUpdateTSecGrpMembMembSysadmin.setRequiredContainerGroup(secGroupUpdateTSecGrpMembID);
			secGroupUpdateTSecGrpMembMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupUpdateTSecGrpMembMembSysadmin.setRequiredSecGrpMembId(secGroupUpdateTSecGrpMembMembSysadminID);
			secGroupUpdateTSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupUpdateTSecGrpMembMembSysadmin);
			secGroupUpdateTSecGrpMembMembSysadminID = secGroupUpdateTSecGrpMembMembSysadmin.getRequiredSecGrpMembId();
		}

		if (secGroupDeleteTSecGrpMemb == null) {
			secGroupDeleteTSecGrpMemb = ICFSecSchema.getBackingCFSec().getFactorySecGroup().newRec();
			secGroupDeleteTSecGrpMemb.setRequiredRevision(1);
			secGroupDeleteTSecGrpMemb.setRequiredContainerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteTSecGrpMemb.setRequiredName("DeleteTSecGrpMemb");
			secGroupDeleteTSecGrpMemb.setRequiredIsVisible(true);
			secGroupDeleteTSecGrpMemb.setRequiredSecGroupId(secGroupDeleteTSecGrpMembID);
			secGroupDeleteTSecGrpMemb = ICFSecSchema.getBackingCFSec().getTableSecGroup().createSecGroup(auth, secGroupDeleteTSecGrpMemb);
			secGroupDeleteTSecGrpMembID = secGroupDeleteTSecGrpMemb.getRequiredSecGroupId();
		}

		if (secGroupDeleteTSecGrpMembMembSysadmin == null) {
			secGroupDeleteTSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getFactorySecGrpMemb().newRec();
			secGroupDeleteTSecGrpMembMembSysadmin.setRequiredRevision(1);
			secGroupDeleteTSecGrpMembMembSysadmin.setRequiredOwnerCluster(ICFSecSchema.getSysClusterId());
			secGroupDeleteTSecGrpMembMembSysadmin.setRequiredContainerGroup(secGroupDeleteTSecGrpMembID);
			secGroupDeleteTSecGrpMembMembSysadmin.setRequiredParentUser(ICFSecSchema.getSysAdminId());
			secGroupDeleteTSecGrpMembMembSysadmin.setRequiredSecGrpMembId(secGroupDeleteTSecGrpMembMembSysadminID);
			secGroupDeleteTSecGrpMembMembSysadmin = ICFSecSchema.getBackingCFSec().getTableSecGrpMemb().createSecGrpMemb(auth, secGroupDeleteTSecGrpMembMembSysadmin);
			secGroupDeleteTSecGrpMembMembSysadminID = secGroupDeleteTSecGrpMembMembSysadmin.getRequiredSecGrpMembId();
		}

	}		


}

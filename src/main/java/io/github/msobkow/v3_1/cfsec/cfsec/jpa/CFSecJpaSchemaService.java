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
						if (bootstrapSession == null || cursess.getRequiredStart().compareTo(bootstrapSession.getRequiredStart()) < 0) {
							bootstrapSession = cursess;
						}
					}
				}
				if (bootstrapSession == null) {
					throw new CFLibNullArgumentException(getClass(), "bootstrapSchema", 0, "bootstrapSession");
				}
			}
			bootstrapSessionID = bootstrapSession.getPKey();
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
			systemCluster.setPKey(systemClusterID);
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
			adminUser.setPKey(adminUID);
			adminUser.setRequiredLoginId("sysadmin");
			adminUser.setRequiredEMailAddress("sysadmin@" + fqdn);
			adminUser.setRequiredPasswordHash(ICFSecSchema.getPasswordHash("ChangeOnInstall"));
			adminUser = secuserService.create(adminUser);
			adminUID = adminUser.getPKey();
		}
		if (systemTenant == null) {
			systemTenant = new CFSecJpaTenant();
			systemTenant.setRequiredRevision(1);
			systemTenant.setPKey(systemTenantID);
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
			bootstrapSession.setPKey(bootstrapSessionID);
			bootstrapSession.setRequiredContainerSecUser(adminUID);
			bootstrapSession.setRequiredParentSecProxy(adminUID);
			bootstrapSession.setOptionalSecDevName(null);
			bootstrapSession.setRequiredStart(now);
			bootstrapSession.setOptionalFinish(null);
			bootstrapSession = secsessionService.create(bootstrapSession);
			bootstrapSessionID = bootstrapSession.getPKey();
		}
		if (sysCluster == null) {
			sysCluster = new CFSecJpaSysCluster();
			sysCluster.setRequiredContainerCluster(systemClusterID);
			sysCluster = sysclusterService.create(sysCluster);
		}
		if (bootstrapSession.getOptionalFinish() == null) {
			bootstrapSession.setOptionalFinish(LocalDateTime.now());
			bootstrapSession.setRequiredRevision(bootstrapSession.getRequiredRevision() + 1);
			bootstrapSession = secsessionService.update(bootstrapSession);
		}
	}

}

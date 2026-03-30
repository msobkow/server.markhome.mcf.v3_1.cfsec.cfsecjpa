// Description: Java 25 Spring JPA Hooks for CFSec

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
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.jpa.*;

/**
 *	Hooks for schema CFSec Spring resources that need to be used by getter-wrappers for AtomicReference members of the multi-threaded wedge between Spring resources and POJO code.
 *	This implementation wraps the Spring singletons instantiated during Spring's boot process for the server.markhome.mcf.v3_1.cfsec.cfsec.jpa package.  It resolves all known standard Spring resources for the LocalContainerEntityManagerFactoryBean, SchemaService, IdGenService, and the CFSec*Repository and CFSec*Service singletons that the POJOs need to invoke.  However, it relies on late-initialization during dynamic instance creation (i.e. new CFSecJpaSchemaHooks()) instead of being initialized as a Spring singleton. As the Spring instance hierarchy is instantiated before any instances of this class are instantiated, the net result bridges the gap between POJO and Spring JPA.
 */
@Service("cfsec31JpaHooksSchema")
public class CFSecJpaHooksSchema {

	@Autowired
	@Qualifier("cfsec31EntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean cfsec31EntityManagerFactory;

	@Autowired
	private CFSecJpaClusterRepository clusterRepository;

	@Autowired
	private CFSecJpaTenantRepository tenantRepository;

	@Autowired
	private CFSecJpaISOCcyRepository iSOCcyRepository;

	@Autowired
	private CFSecJpaISOCtryRepository iSOCtryRepository;

	@Autowired
	private CFSecJpaISOCtryCcyRepository iSOCtryCcyRepository;

	@Autowired
	private CFSecJpaISOCtryLangRepository iSOCtryLangRepository;

	@Autowired
	private CFSecJpaISOLangRepository iSOLangRepository;

	@Autowired
	private CFSecJpaISOTZoneRepository iSOTZoneRepository;

	@Autowired
	private CFSecJpaSecUserRepository secUserRepository;

	@Autowired
	private CFSecJpaSecUserPasswordRepository secUserPasswordRepository;

	@Autowired
	private CFSecJpaSecUserEMConfRepository secUserEMConfRepository;

	@Autowired
	private CFSecJpaSecUserPWResetRepository secUserPWResetRepository;

	@Autowired
	private CFSecJpaSecUserPWHistoryRepository secUserPWHistoryRepository;

	@Autowired
	private CFSecJpaSecSysGrpRepository secSysGrpRepository;

	@Autowired
	private CFSecJpaSecSysGrpIncRepository secSysGrpIncRepository;

	@Autowired
	private CFSecJpaSecSysGrpMembRepository secSysGrpMembRepository;

	@Autowired
	private CFSecJpaSecClusGrpRepository secClusGrpRepository;

	@Autowired
	private CFSecJpaSecClusGrpIncRepository secClusGrpIncRepository;

	@Autowired
	private CFSecJpaSecClusGrpMembRepository secClusGrpMembRepository;

	@Autowired
	private CFSecJpaSecTentGrpRepository secTentGrpRepository;

	@Autowired
	private CFSecJpaSecTentGrpIncRepository secTentGrpIncRepository;

	@Autowired
	private CFSecJpaSecTentGrpMembRepository secTentGrpMembRepository;

	@Autowired
	private CFSecJpaSecSessionRepository secSessionRepository;

	@Autowired
	private CFSecJpaSysClusterRepository sysClusterRepository;

	@Autowired
	@Qualifier("cfsec31JpaSchemaService")
	private CFSecJpaSchemaService schemaService;

	@Autowired
	@Qualifier("CFSecJpaIdGenService")
	private CFSecJpaIdGenService idGenService;

	@Autowired
	@Qualifier("cfsec31JpaClusterService")
	private CFSecJpaClusterService clusterService;

	@Autowired
	@Qualifier("cfsec31JpaTenantService")
	private CFSecJpaTenantService tenantService;

	@Autowired
	@Qualifier("cfsec31JpaISOCcyService")
	private CFSecJpaISOCcyService iSOCcyService;

	@Autowired
	@Qualifier("cfsec31JpaISOCtryService")
	private CFSecJpaISOCtryService iSOCtryService;

	@Autowired
	@Qualifier("cfsec31JpaISOCtryCcyService")
	private CFSecJpaISOCtryCcyService iSOCtryCcyService;

	@Autowired
	@Qualifier("cfsec31JpaISOCtryLangService")
	private CFSecJpaISOCtryLangService iSOCtryLangService;

	@Autowired
	@Qualifier("cfsec31JpaISOLangService")
	private CFSecJpaISOLangService iSOLangService;

	@Autowired
	@Qualifier("cfsec31JpaISOTZoneService")
	private CFSecJpaISOTZoneService iSOTZoneService;

	@Autowired
	@Qualifier("cfsec31JpaSecUserService")
	private CFSecJpaSecUserService secUserService;

	@Autowired
	@Qualifier("cfsec31JpaSecUserPasswordService")
	private CFSecJpaSecUserPasswordService secUserPasswordService;

	@Autowired
	@Qualifier("cfsec31JpaSecUserEMConfService")
	private CFSecJpaSecUserEMConfService secUserEMConfService;

	@Autowired
	@Qualifier("cfsec31JpaSecUserPWResetService")
	private CFSecJpaSecUserPWResetService secUserPWResetService;

	@Autowired
	@Qualifier("cfsec31JpaSecUserPWHistoryService")
	private CFSecJpaSecUserPWHistoryService secUserPWHistoryService;

	@Autowired
	@Qualifier("cfsec31JpaSecSysGrpService")
	private CFSecJpaSecSysGrpService secSysGrpService;

	@Autowired
	@Qualifier("cfsec31JpaSecSysGrpIncService")
	private CFSecJpaSecSysGrpIncService secSysGrpIncService;

	@Autowired
	@Qualifier("cfsec31JpaSecSysGrpMembService")
	private CFSecJpaSecSysGrpMembService secSysGrpMembService;

	@Autowired
	@Qualifier("cfsec31JpaSecClusGrpService")
	private CFSecJpaSecClusGrpService secClusGrpService;

	@Autowired
	@Qualifier("cfsec31JpaSecClusGrpIncService")
	private CFSecJpaSecClusGrpIncService secClusGrpIncService;

	@Autowired
	@Qualifier("cfsec31JpaSecClusGrpMembService")
	private CFSecJpaSecClusGrpMembService secClusGrpMembService;

	@Autowired
	@Qualifier("cfsec31JpaSecTentGrpService")
	private CFSecJpaSecTentGrpService secTentGrpService;

	@Autowired
	@Qualifier("cfsec31JpaSecTentGrpIncService")
	private CFSecJpaSecTentGrpIncService secTentGrpIncService;

	@Autowired
	@Qualifier("cfsec31JpaSecTentGrpMembService")
	private CFSecJpaSecTentGrpMembService secTentGrpMembService;

	@Autowired
	@Qualifier("cfsec31JpaSecSessionService")
	private CFSecJpaSecSessionService secSessionService;

	@Autowired
	@Qualifier("cfsec31JpaSysClusterService")
	private CFSecJpaSysClusterService sysClusterService;

	public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {
		if ( cfsec31EntityManagerFactory == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getEntityManagerFactoryBean",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( cfsec31EntityManagerFactory );
	}

	public CFSecJpaSchemaService getSchemaService() {
		if ( schemaService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSchemaService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( schemaService );
	}

	public CFSecJpaIdGenService getIdGenService() {
		if ( idGenService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getIdGenService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( idGenService );
	}

	public CFSecJpaClusterRepository getClusterRepository() {
		if ( clusterRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getClusterRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( clusterRepository );
	}

	public CFSecJpaTenantRepository getTenantRepository() {
		if ( tenantRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getTenantRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( tenantRepository );
	}

	public CFSecJpaISOCcyRepository getISOCcyRepository() {
		if ( iSOCcyRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getISOCcyRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( iSOCcyRepository );
	}

	public CFSecJpaISOCtryRepository getISOCtryRepository() {
		if ( iSOCtryRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getISOCtryRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( iSOCtryRepository );
	}

	public CFSecJpaISOCtryCcyRepository getISOCtryCcyRepository() {
		if ( iSOCtryCcyRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getISOCtryCcyRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( iSOCtryCcyRepository );
	}

	public CFSecJpaISOCtryLangRepository getISOCtryLangRepository() {
		if ( iSOCtryLangRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getISOCtryLangRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( iSOCtryLangRepository );
	}

	public CFSecJpaISOLangRepository getISOLangRepository() {
		if ( iSOLangRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getISOLangRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( iSOLangRepository );
	}

	public CFSecJpaISOTZoneRepository getISOTZoneRepository() {
		if ( iSOTZoneRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getISOTZoneRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( iSOTZoneRepository );
	}

	public CFSecJpaSecUserRepository getSecUserRepository() {
		if ( secUserRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSecUserRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secUserRepository );
	}

	public CFSecJpaSecUserPasswordRepository getSecUserPasswordRepository() {
		if ( secUserPasswordRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSecUserPasswordRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secUserPasswordRepository );
	}

	public CFSecJpaSecUserEMConfRepository getSecUserEMConfRepository() {
		if ( secUserEMConfRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSecUserEMConfRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secUserEMConfRepository );
	}

	public CFSecJpaSecUserPWResetRepository getSecUserPWResetRepository() {
		if ( secUserPWResetRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSecUserPWResetRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secUserPWResetRepository );
	}

	public CFSecJpaSecUserPWHistoryRepository getSecUserPWHistoryRepository() {
		if ( secUserPWHistoryRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSecUserPWHistoryRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secUserPWHistoryRepository );
	}

	public CFSecJpaSecSysGrpRepository getSecSysGrpRepository() {
		if ( secSysGrpRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSecSysGrpRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secSysGrpRepository );
	}

	public CFSecJpaSecSysGrpIncRepository getSecSysGrpIncRepository() {
		if ( secSysGrpIncRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSecSysGrpIncRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secSysGrpIncRepository );
	}

	public CFSecJpaSecSysGrpMembRepository getSecSysGrpMembRepository() {
		if ( secSysGrpMembRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSecSysGrpMembRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secSysGrpMembRepository );
	}

	public CFSecJpaSecClusGrpRepository getSecClusGrpRepository() {
		if ( secClusGrpRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSecClusGrpRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secClusGrpRepository );
	}

	public CFSecJpaSecClusGrpIncRepository getSecClusGrpIncRepository() {
		if ( secClusGrpIncRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSecClusGrpIncRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secClusGrpIncRepository );
	}

	public CFSecJpaSecClusGrpMembRepository getSecClusGrpMembRepository() {
		if ( secClusGrpMembRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSecClusGrpMembRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secClusGrpMembRepository );
	}

	public CFSecJpaSecTentGrpRepository getSecTentGrpRepository() {
		if ( secTentGrpRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSecTentGrpRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secTentGrpRepository );
	}

	public CFSecJpaSecTentGrpIncRepository getSecTentGrpIncRepository() {
		if ( secTentGrpIncRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSecTentGrpIncRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secTentGrpIncRepository );
	}

	public CFSecJpaSecTentGrpMembRepository getSecTentGrpMembRepository() {
		if ( secTentGrpMembRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSecTentGrpMembRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secTentGrpMembRepository );
	}

	public CFSecJpaSecSessionRepository getSecSessionRepository() {
		if ( secSessionRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSecSessionRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secSessionRepository );
	}

	public CFSecJpaSysClusterRepository getSysClusterRepository() {
		if ( sysClusterRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSysClusterRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( sysClusterRepository );
	}

	public CFSecJpaClusterService getClusterService() {
		if ( clusterService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getClusterService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( clusterService );
	}

	public CFSecJpaTenantService getTenantService() {
		if ( tenantService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getTenantService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( tenantService );
	}

	public CFSecJpaISOCcyService getISOCcyService() {
		if ( iSOCcyService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getISOCcyService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( iSOCcyService );
	}

	public CFSecJpaISOCtryService getISOCtryService() {
		if ( iSOCtryService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getISOCtryService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( iSOCtryService );
	}

	public CFSecJpaISOCtryCcyService getISOCtryCcyService() {
		if ( iSOCtryCcyService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getISOCtryCcyService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( iSOCtryCcyService );
	}

	public CFSecJpaISOCtryLangService getISOCtryLangService() {
		if ( iSOCtryLangService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getISOCtryLangService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( iSOCtryLangService );
	}

	public CFSecJpaISOLangService getISOLangService() {
		if ( iSOLangService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getISOLangService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( iSOLangService );
	}

	public CFSecJpaISOTZoneService getISOTZoneService() {
		if ( iSOTZoneService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getISOTZoneService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( iSOTZoneService );
	}

	public CFSecJpaSecUserService getSecUserService() {
		if ( secUserService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSecUserService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secUserService );
	}

	public CFSecJpaSecUserPasswordService getSecUserPasswordService() {
		if ( secUserPasswordService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSecUserPasswordService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secUserPasswordService );
	}

	public CFSecJpaSecUserEMConfService getSecUserEMConfService() {
		if ( secUserEMConfService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSecUserEMConfService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secUserEMConfService );
	}

	public CFSecJpaSecUserPWResetService getSecUserPWResetService() {
		if ( secUserPWResetService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSecUserPWResetService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secUserPWResetService );
	}

	public CFSecJpaSecUserPWHistoryService getSecUserPWHistoryService() {
		if ( secUserPWHistoryService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSecUserPWHistoryService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secUserPWHistoryService );
	}

	public CFSecJpaSecSysGrpService getSecSysGrpService() {
		if ( secSysGrpService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSecSysGrpService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secSysGrpService );
	}

	public CFSecJpaSecSysGrpIncService getSecSysGrpIncService() {
		if ( secSysGrpIncService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSecSysGrpIncService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secSysGrpIncService );
	}

	public CFSecJpaSecSysGrpMembService getSecSysGrpMembService() {
		if ( secSysGrpMembService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSecSysGrpMembService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secSysGrpMembService );
	}

	public CFSecJpaSecClusGrpService getSecClusGrpService() {
		if ( secClusGrpService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSecClusGrpService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secClusGrpService );
	}

	public CFSecJpaSecClusGrpIncService getSecClusGrpIncService() {
		if ( secClusGrpIncService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSecClusGrpIncService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secClusGrpIncService );
	}

	public CFSecJpaSecClusGrpMembService getSecClusGrpMembService() {
		if ( secClusGrpMembService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSecClusGrpMembService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secClusGrpMembService );
	}

	public CFSecJpaSecTentGrpService getSecTentGrpService() {
		if ( secTentGrpService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSecTentGrpService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secTentGrpService );
	}

	public CFSecJpaSecTentGrpIncService getSecTentGrpIncService() {
		if ( secTentGrpIncService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSecTentGrpIncService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secTentGrpIncService );
	}

	public CFSecJpaSecTentGrpMembService getSecTentGrpMembService() {
		if ( secTentGrpMembService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSecTentGrpMembService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secTentGrpMembService );
	}

	public CFSecJpaSecSessionService getSecSessionService() {
		if ( secSessionService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSecSessionService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secSessionService );
	}

	public CFSecJpaSysClusterService getSysClusterService() {
		if ( sysClusterService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSysClusterService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( sysClusterService );
	}
}

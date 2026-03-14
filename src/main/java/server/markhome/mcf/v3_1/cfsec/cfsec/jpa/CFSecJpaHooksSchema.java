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
	private CFSecJpaHostNodeRepository hostNodeRepository;

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
	private CFSecJpaSecDeviceRepository secDeviceRepository;

	@Autowired
	private CFSecJpaSecGroupRepository secGroupRepository;

	@Autowired
	private CFSecJpaSecGrpIncRepository secGrpIncRepository;

	@Autowired
	private CFSecJpaSecGrpMembRepository secGrpMembRepository;

	@Autowired
	private CFSecJpaSecSessionRepository secSessionRepository;

	@Autowired
	private CFSecJpaSecUserRepository secUserRepository;

	@Autowired
	private CFSecJpaServiceRepository serviceRepository;

	@Autowired
	private CFSecJpaServiceTypeRepository serviceTypeRepository;

	@Autowired
	private CFSecJpaSysClusterRepository sysClusterRepository;

	@Autowired
	private CFSecJpaTenantRepository tenantRepository;

	@Autowired
	private CFSecJpaTSecGroupRepository tSecGroupRepository;

	@Autowired
	private CFSecJpaTSecGrpIncRepository tSecGrpIncRepository;

	@Autowired
	private CFSecJpaTSecGrpMembRepository tSecGrpMembRepository;

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
	@Qualifier("cfsec31JpaHostNodeService")
	private CFSecJpaHostNodeService hostNodeService;

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
	@Qualifier("cfsec31JpaSecDeviceService")
	private CFSecJpaSecDeviceService secDeviceService;

	@Autowired
	@Qualifier("cfsec31JpaSecGroupService")
	private CFSecJpaSecGroupService secGroupService;

	@Autowired
	@Qualifier("cfsec31JpaSecGrpIncService")
	private CFSecJpaSecGrpIncService secGrpIncService;

	@Autowired
	@Qualifier("cfsec31JpaSecGrpMembService")
	private CFSecJpaSecGrpMembService secGrpMembService;

	@Autowired
	@Qualifier("cfsec31JpaSecSessionService")
	private CFSecJpaSecSessionService secSessionService;

	@Autowired
	@Qualifier("cfsec31JpaSecUserService")
	private CFSecJpaSecUserService secUserService;

	@Autowired
	@Qualifier("cfsec31JpaServiceService")
	private CFSecJpaServiceService serviceService;

	@Autowired
	@Qualifier("cfsec31JpaServiceTypeService")
	private CFSecJpaServiceTypeService serviceTypeService;

	@Autowired
	@Qualifier("cfsec31JpaSysClusterService")
	private CFSecJpaSysClusterService sysClusterService;

	@Autowired
	@Qualifier("cfsec31JpaTenantService")
	private CFSecJpaTenantService tenantService;

	@Autowired
	@Qualifier("cfsec31JpaTSecGroupService")
	private CFSecJpaTSecGroupService tSecGroupService;

	@Autowired
	@Qualifier("cfsec31JpaTSecGrpIncService")
	private CFSecJpaTSecGrpIncService tSecGrpIncService;

	@Autowired
	@Qualifier("cfsec31JpaTSecGrpMembService")
	private CFSecJpaTSecGrpMembService tSecGrpMembService;

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

	public CFSecJpaHostNodeRepository getHostNodeRepository() {
		if ( hostNodeRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getHostNodeRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( hostNodeRepository );
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

	public CFSecJpaSecDeviceRepository getSecDeviceRepository() {
		if ( secDeviceRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSecDeviceRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secDeviceRepository );
	}

	public CFSecJpaSecGroupRepository getSecGroupRepository() {
		if ( secGroupRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSecGroupRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secGroupRepository );
	}

	public CFSecJpaSecGrpIncRepository getSecGrpIncRepository() {
		if ( secGrpIncRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSecGrpIncRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secGrpIncRepository );
	}

	public CFSecJpaSecGrpMembRepository getSecGrpMembRepository() {
		if ( secGrpMembRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSecGrpMembRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secGrpMembRepository );
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

	public CFSecJpaSecUserRepository getSecUserRepository() {
		if ( secUserRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getSecUserRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secUserRepository );
	}

	public CFSecJpaServiceRepository getServiceRepository() {
		if ( serviceRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getServiceRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( serviceRepository );
	}

	public CFSecJpaServiceTypeRepository getServiceTypeRepository() {
		if ( serviceTypeRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getServiceTypeRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( serviceTypeRepository );
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

	public CFSecJpaTenantRepository getTenantRepository() {
		if ( tenantRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getTenantRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( tenantRepository );
	}

	public CFSecJpaTSecGroupRepository getTSecGroupRepository() {
		if ( tSecGroupRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getTSecGroupRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( tSecGroupRepository );
	}

	public CFSecJpaTSecGrpIncRepository getTSecGrpIncRepository() {
		if ( tSecGrpIncRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getTSecGrpIncRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( tSecGrpIncRepository );
	}

	public CFSecJpaTSecGrpMembRepository getTSecGrpMembRepository() {
		if ( tSecGrpMembRepository == null ) {
			// Dynamically resolve the repository by interface type
			throw new CFLibNotImplementedYetException( getClass(), "getTSecGrpMembRepository",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( tSecGrpMembRepository );
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

	public CFSecJpaHostNodeService getHostNodeService() {
		if ( hostNodeService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getHostNodeService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( hostNodeService );
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

	public CFSecJpaSecDeviceService getSecDeviceService() {
		if ( secDeviceService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSecDeviceService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secDeviceService );
	}

	public CFSecJpaSecGroupService getSecGroupService() {
		if ( secGroupService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSecGroupService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secGroupService );
	}

	public CFSecJpaSecGrpIncService getSecGrpIncService() {
		if ( secGrpIncService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSecGrpIncService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secGrpIncService );
	}

	public CFSecJpaSecGrpMembService getSecGrpMembService() {
		if ( secGrpMembService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSecGrpMembService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secGrpMembService );
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

	public CFSecJpaSecUserService getSecUserService() {
		if ( secUserService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getSecUserService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( secUserService );
	}

	public CFSecJpaServiceService getServiceService() {
		if ( serviceService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getServiceService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( serviceService );
	}

	public CFSecJpaServiceTypeService getServiceTypeService() {
		if ( serviceTypeService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getServiceTypeService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( serviceTypeService );
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

	public CFSecJpaTenantService getTenantService() {
		if ( tenantService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getTenantService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( tenantService );
	}

	public CFSecJpaTSecGroupService getTSecGroupService() {
		if ( tSecGroupService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getTSecGroupService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( tSecGroupService );
	}

	public CFSecJpaTSecGrpIncService getTSecGrpIncService() {
		if ( tSecGrpIncService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getTSecGrpIncService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( tSecGrpIncService );
	}

	public CFSecJpaTSecGrpMembService getTSecGrpMembService() {
		if ( tSecGrpMembService == null ) {
			// Dynamically resolve the repository by qualifier name
			throw new CFLibNotImplementedYetException( getClass(), "getTSecGrpMembService",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either",
				"ERROR - do not know how to dynamically resolve Spring beans from POJO code yet and AspectJ did not resolve it either" );
		}
		return( tSecGrpMembService );
	}
}

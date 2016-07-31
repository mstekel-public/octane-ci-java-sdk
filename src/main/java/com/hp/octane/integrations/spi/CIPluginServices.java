package com.hp.octane.integrations.spi;

import com.hp.octane.integrations.dto.configuration.CIProxyConfiguration;
import com.hp.octane.integrations.dto.general.CIPluginInfo;
import com.hp.octane.integrations.dto.general.CIServerInfo;
import com.hp.octane.integrations.dto.pipelines.BuildHistory;
import com.hp.octane.integrations.dto.pipelines.PipelineNode;
import com.hp.octane.integrations.dto.general.CIJobsList;
import com.hp.octane.integrations.dto.snapshots.SnapshotNode;
import com.hp.octane.integrations.dto.configuration.OctaneConfiguration;
import com.hp.octane.integrations.dto.tests.TestsResult;

import java.io.File;

/**
 * Created by gullery on 20/01/2016.
 * <p/>
 * Composite API of all the endpoints to be implemented by a hosting CI Plugin for Octane use cases
 */

public interface CIPluginServices {

	/**
	 * Provides CI Server information
	 *
	 * @return ServerInfo object; MUST NOT return null
	 */
	CIServerInfo getServerInfo();

	/**
	 * Provides Plugin's information
	 *
	 * @return PluginInfo object; MUST NOT return null
	 */
	CIPluginInfo getPluginInfo();

	/**
	 * Provider the folder that the plugin is allowed to write to (logs, temporary stuff etc)
	 *
	 * @return File object of type Directory; if no available storage exists the implementation should return NULL
	 */
	File getAllowedOctaneStorage();

	/**
	 * Provides NGA Server configuration (managed by plugin implementation)
	 *
	 * @return NGAConfiguration object; if no configuration available the implementation should return NULL
	 */
	OctaneConfiguration getOctaneConfiguration();

	/**
	 * Provides CI Server proxy configuration (managed by plugin implementation)
	 *
	 * @param targetHost target host that the proxy, if available, should be relevant to
	 * @return ProxyConfiguration object; if no configuration available the implementation should return NULL
	 */
	CIProxyConfiguration getProxyConfiguration(String targetHost);

	/**
	 * Provides a list of Projects existing on this CI Server
	 *
	 * @param includeParameters
	 * @return ProjectList object holding the list of the projects
	 */
	CIJobsList getJobsList(boolean includeParameters);

	/**
	 * Provides Pipeline (structure) from the root CI Job
	 *
	 * @param rootCIJobId
	 * @return
	 */
	PipelineNode getPipeline(String rootCIJobId);

	/**
	 * Executes the Pipeline, running the root job
	 *
	 * @param ciJobId
	 * @param originalBody
	 * @return
	 */
	void runPipeline(String ciJobId, String originalBody);       //  [YG]: TODO: replace the body thing with parsed parameters/DTO

	/**
	 * Provides Snapshot of the latest CI Build of the specified CI Job
	 *
	 * @param ciJobId
	 * @param subTree
	 * @return
	 */
	SnapshotNode getSnapshotLatest(String ciJobId, boolean subTree);

	/**
	 * Provides Snapshot of the specified CI Build of the specified CI Job
	 *
	 * @param ciJobId
	 * @param buildCiId
	 * @param subTree
	 * @return
	 */
	SnapshotNode getSnapshotByNumber(String ciJobId, String buildCiId, boolean subTree);

	/**
	 * Retrieves aggregated latest builds info                  //  [YG]: TODO: this API should be removed, all the relevant data should be made available in the rest of APIs
	 *
	 * @param ciJobId
	 * @param originalBody
	 * @return
	 */
	BuildHistory getHistoryPipeline(String ciJobId, String originalBody);

	/**
	 * Retrieves tests result report for the specific build
	 *
	 * @param jobId
	 * @param buildNumber
	 * @return TestsResult data; NULL if no tests result available
	 */
	TestsResult getTestsResult(String jobId, String buildNumber);
}
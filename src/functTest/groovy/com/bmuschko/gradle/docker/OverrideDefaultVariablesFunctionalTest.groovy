package com.bmuschko.gradle.docker

import org.gradle.testkit.runner.BuildResult
import spock.lang.Requires

class OverrideDefaultVariablesFunctionalTest extends AbstractFunctionalTest {
    def "Can get Docker info with overriden url"() {
        buildFile << """
            import com.bmuschko.gradle.docker.tasks.DockerInfo

            task dockerInfo(type: DockerInfo) {
                url = "$dockerServerUrl"
            }
        """

        when:
        BuildResult result = build('dockerInfo')

        then:
        result.output.contains('Retrieving Docker info.')
    }
    
    def "Docker info fails with illegal overriden url"() {
        buildFile << """
            import com.bmuschko.gradle.docker.tasks.DockerInfo

            task dockerInfo(type: DockerInfo) {
                url = "{$dockerServerUrl}/url/to/fail"
            }
        """

        expect:
        BuildResult result = buildAndFail('dockerInfo')
    }
}

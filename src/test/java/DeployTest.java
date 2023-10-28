import org.gradle.api.Project;
import org.gradle.internal.impldep.org.junit.Test;
import org.gradle.testfixtures.ProjectBuilder;

import static org.gradle.internal.impldep.junit.framework.TestCase.assertNotNull;
import static org.gradle.internal.impldep.org.testng.Assert.assertTrue;

public class DeployTest {

    @Test
    public void deployTest(){

        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply("pterodactyl-ci-plugin");

        assertTrue(project.getPluginManager()
                .hasPlugin("pterodactyl-ci-plugin"));

        assertNotNull(project.getTasks().getByName("deploy"));
    }


}

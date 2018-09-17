package base;

import ooh.bravo.container.spring.server.StandardServer;

import org.junit.Test;

public class StandardServerTest {

    @Test
    public void testStartUserContainer() throws Exception {
        StandardServer standardServer = new StandardServer();
        standardServer.setContainerDefaultConfigPath("classpath:test/container-spring_config_default.xml");
        standardServer.setContainerConfigPath("classpath:test/container-spring_config.xml");
        standardServer.start();
    }

//    @Test
    public void testStopUserContainer() {
        StandardServer standardServer = new StandardServer();
        standardServer.setContainerConfigPath("classpath:test/container-spring_config.xml");
        standardServer.stopServer();
    }
}

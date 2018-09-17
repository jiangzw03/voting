package base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@ContextConfiguration(locations = { "classpath:test_config.xml", "classpath:test_cache.xml" })
public class BaseTransactionalTestNGTest extends AbstractTransactionalTestNGSpringContextTests {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
}

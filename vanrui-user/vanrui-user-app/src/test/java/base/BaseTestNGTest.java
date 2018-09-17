package base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration(locations = { "classpath:test_config.xml", "classpath:test_cache.xml" })
public class BaseTestNGTest extends AbstractTestNGSpringContextTests {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
}

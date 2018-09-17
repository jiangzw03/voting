package base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:test_config.xml", "classpath:test_cache.xml" })
public class BaseJUnitTest extends AbstractJUnit4SpringContextTests {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
}

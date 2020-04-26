package by.bestlunch.service;

import by.bestlunch.TimingExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

import static by.bestlunch.validation.ValidationUtil.getRootCause;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitWebConfig(AbstractServiceTest.Config.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ExtendWith(TimingExtension.class)
@Transactional
abstract class AbstractServiceTest {

    @Configuration
    @ComponentScan({"by.bestlunch"})
    static class Config {
    }

    <T extends Throwable> void validateRootCause(Runnable runnable, Class<T> exceptionClass) {
        assertThrows(exceptionClass, () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw getRootCause(e);
            }
        });
    }
}
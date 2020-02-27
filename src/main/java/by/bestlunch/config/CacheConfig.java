package by.bestlunch.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public JCacheManagerFactoryBean cacheManagerFactoryBean() throws Exception {
        JCacheManagerFactoryBean jCacheManagerFactoryBean = new JCacheManagerFactoryBean();
        jCacheManagerFactoryBean.setCacheManagerUri(new ClassPathResource("cache/ehcache.xml").getURI());
        return jCacheManagerFactoryBean;
    }

    @Bean
    public CacheManager cacheManager() throws Exception {
        final JCacheCacheManager jCacheCacheManager = new JCacheCacheManager();
        jCacheCacheManager.setCacheManager(cacheManagerFactoryBean().getObject());
        return jCacheCacheManager;
    }
}
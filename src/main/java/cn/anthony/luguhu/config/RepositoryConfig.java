package cn.anthony.luguhu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import cn.anthony.luguhu.domain.Address;

/**
 * https://stackoverflow.com/questions/24839760/spring-boot-responsebody-doesnt-serialize-entity-id
 * @author zj
 *
 */
@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
    @Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Address.class);
    }
}

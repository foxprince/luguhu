package cn.anthony.luguhu.config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

public class UnicodePropertiesPropertySourceLoader implements PropertySourceLoader {
	@Override
	public String[] getFileExtensions() {
		return new String[] { "properties" };
	}

	@Override
	public PropertySource<?> load(String name, Resource resource, String profile) throws IOException {
		System.out.println("========================");
		if (profile == null) {
			Properties properties = new Properties();
			properties.load(new InputStreamReader(resource.getInputStream(), "UTF-8"));
			if (!properties.isEmpty()) {
				return new PropertiesPropertySource(name, properties);
			}
		}
		return null;
	}
}

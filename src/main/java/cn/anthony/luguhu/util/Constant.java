package cn.anthony.luguhu.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("application")
@Data
public class Constant {
	@Inject
	private Environment env;
	private String siteTitle;
	private String uploadTmpDir;
	private String uploadDir;

	public String getUploadAbsoluteDir() {
		return env.getProperty("HOME") + FILE_SEPA + getUploadDir();
	}

	public String getAbsoluteTmpDir() {
		return env.getProperty("HOME") + FILE_SEPA + getUploadTmpDir();
	}

	public static String FILE_SEPA = System.getProperty("file.separator");

	public static final byte USER_LEVEL_PPRODUCER = 3;

	public static final List<String> UNIT_LIST = Arrays.asList("箱", "袋", "打", "个", "公斤", "斤", "两", "克");

	public static Map<String, String> levelMap = new TreeMap<String, String>() {
		private static final long serialVersionUID = 8151676689034026374L;
		{
			put("0", "散户");
			put("1", "份额用户");
			put("2", "股东用户");
			put("3", "生产者");
			put("4", "黑名单用户");
		}
	};

	public static Map<String, String> levelMapWithUrl = new TreeMap<String, String>() {
		private static final long serialVersionUID = -7703743430823217471L;
		{
			put("/user/list?level=0", "普通用户");
			put("/user/list?level=1", "份额用户");
			put("/user/list?level=2", "股东用户");
			put("/user/list?level=3", "生产者");
			put("/user/list?level=4", "黑名单用户");
		}
	};

}

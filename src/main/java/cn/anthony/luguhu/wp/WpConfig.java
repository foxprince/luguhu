package cn.anthony.luguhu.wp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;

@Component
@ConfigurationProperties("wechat.mp")
@Data
public class WpConfig extends WxMpInMemoryConfigStorage{

}

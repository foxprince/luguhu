package cn.anthony.luguhu.wp;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.github.binarywang.wxpay.config.WxPayConfig;

/**
 * wxpay pay properties
 *
 * @author Binary Wang
 */
@Component
@ConfigurationProperties(prefix = "wechat.pay")
public class WxPayProperties extends WxPayConfig{
  /**
   * 设置微信公众号的appid
   */
  private String appId;

  /**
   * 微信支付商户号
   */
  private String mchId;

  /**
   * 微信支付商户密钥
   */
  private String mchKey;

  /**
   * 服务商模式下的子商户公众账号ID，普通模式请不要配置，请在配置文件中将对应项删除
   */
  private String subAppId;

  /**
   * 服务商模式下的子商户号，普通模式请不要配置，最好是请在配置文件中将对应项删除
   */
  private String subMchId;

  /**
   * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
   */
  private String keyPath;

  @Override
public String getAppId() {
    return this.appId;
  }

  @Override
public void setAppId(String appId) {
    this.appId = appId;
  }

  @Override
public String getMchId() {
    return mchId;
  }

  @Override
public void setMchId(String mchId) {
    this.mchId = mchId;
  }

  @Override
public String getMchKey() {
    return mchKey;
  }

  @Override
public void setMchKey(String mchKey) {
    this.mchKey = mchKey;
  }

  @Override
public String getSubAppId() {
    return subAppId;
  }

  @Override
public void setSubAppId(String subAppId) {
    this.subAppId = subAppId;
  }

  @Override
public String getSubMchId() {
    return subMchId;
  }

  @Override
public void setSubMchId(String subMchId) {
    this.subMchId = subMchId;
  }

  @Override
public String getKeyPath() {
    return this.keyPath;
  }

  @Override
public void setKeyPath(String keyPath) {
    this.keyPath = keyPath;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this,
        ToStringStyle.MULTI_LINE_STYLE);
  }
}

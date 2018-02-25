package cn.anthony.luguhu.repository;

import cn.anthony.luguhu.domain.QWxPayOrder;
import cn.anthony.luguhu.domain.WxPayOrder;
public interface WxPayOrderRepository extends BaseRepository<WxPayOrder, QWxPayOrder, Long> {

	WxPayOrder findByTradeNo(String tradeNo);

}

package cn.anthony.luguhu.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import cn.anthony.luguhu.domain.Asset;
import cn.anthony.luguhu.domain.QAsset;
import cn.anthony.luguhu.domain.WxUser;

public interface AssetRepository extends BaseRepository<Asset, QAsset, Long> {

	public List<Asset> findByWxUserAndTagsNotNull(WxUser wxUser,Pageable pageable);
	
}

package cn.anthony.luguhu.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.anthony.luguhu.domain.Asset;
import cn.anthony.luguhu.domain.QAsset;
import cn.anthony.luguhu.repository.AssetRepository;
import cn.anthony.luguhu.repository.BaseRepository;

@Service
public class AssetService extends GenericService<Asset, Long> {

	@Resource
	AssetRepository repsitory;

	@Override
	public BaseRepository<Asset, QAsset, Long> getRepository() {
		return this.repsitory;
	}
}

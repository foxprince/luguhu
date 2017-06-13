package cn.anthony.luguhu.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import cn.anthony.luguhu.domain.Asset;
import cn.anthony.luguhu.domain.QAsset;
import cn.anthony.luguhu.domain.WxUser;
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

	public List<Asset> findByWxUserHaveTag(WxUser wxUser) {
		return repsitory.findByWxUserAndTagsNotNull(wxUser, new PageRequest(0, 1, Sort.Direction.DESC, "id"));
		// return repsitory.findAll(new Specification<Asset>() {
		// @Override
		// public Predicate toPredicate(Root<Asset> root, CriteriaQuery<?>
		// query, CriteriaBuilder cb) {
		// return cb.equal(root.join("wxUser"), wxUser);
		// }
		// }, new PageRequest(0,1,Sort.Direction.DESC,"id")).getContent();
	}
}

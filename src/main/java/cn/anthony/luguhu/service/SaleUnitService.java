package cn.anthony.luguhu.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.anthony.luguhu.domain.SaleUnit;
import cn.anthony.luguhu.repository.BaseRepository;
import cn.anthony.luguhu.repository.SaleUnitRepository;

@Service
public class SaleUnitService extends GenericService<SaleUnit,Long> {

    @Resource
    SaleUnitRepository repsitory;

    @Override
    public BaseRepository getRepository() {
	return this.repsitory;
    }

    public Object findByProduct(Long productId) {
	return repsitory.findByProductId(productId);
    }
}

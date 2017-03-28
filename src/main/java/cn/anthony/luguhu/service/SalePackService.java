package cn.anthony.luguhu.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.anthony.luguhu.domain.QSalePack;
import cn.anthony.luguhu.domain.SalePack;
import cn.anthony.luguhu.repository.BaseRepository;
import cn.anthony.luguhu.repository.SalePackRepository;

@Service
public class SalePackService extends GenericService<SalePack, Long> {

	@Resource
	SalePackRepository repsitory;

	@Override
	public BaseRepository<SalePack, QSalePack, Long> getRepository() {
		return this.repsitory;
	}
}

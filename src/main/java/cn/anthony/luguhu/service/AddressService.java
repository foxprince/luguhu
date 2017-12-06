package cn.anthony.luguhu.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.anthony.luguhu.domain.Address;
import cn.anthony.luguhu.domain.QAddress;
import cn.anthony.luguhu.repository.AddressRepository;
import cn.anthony.luguhu.repository.BaseRepository;

@Service
public class AddressService extends GenericService<Address, Long> {

	@Resource
	AddressRepository repsitory;

	@Override
	public BaseRepository<Address, QAddress, Long> getRepository() {
		return this.repsitory;
	}

}

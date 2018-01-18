package cn.anthony.luguhu.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import cn.anthony.luguhu.domain.Address;
import cn.anthony.luguhu.domain.QAddress;

public interface AddressRepository extends BaseRepository<Address, QAddress, Long> {
	
	public List<Address> findByUserId(@Param("userId")Long userId);
	
}

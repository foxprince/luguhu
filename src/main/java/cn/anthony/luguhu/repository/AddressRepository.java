package cn.anthony.luguhu.repository;

import java.util.List;

import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;

import com.querydsl.core.types.dsl.StringPath;

import cn.anthony.luguhu.domain.Address;
import cn.anthony.luguhu.domain.QAddress;

public interface AddressRepository extends BaseRepository<Address, QAddress, Long> {
	
	@Override
	default public void customize(QuerydslBindings bindings, QAddress p) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}
	
	public List<Address> findByUserId(@Param("userId")Long userId);
}

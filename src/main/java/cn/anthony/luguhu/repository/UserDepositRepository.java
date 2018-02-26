package cn.anthony.luguhu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import cn.anthony.luguhu.domain.QUserDeposit;
import cn.anthony.luguhu.domain.User;
import cn.anthony.luguhu.domain.UserDeposit;
public interface UserDepositRepository extends BaseRepository<UserDeposit, QUserDeposit, Long> {
	@RestResource
	Page<UserDeposit> findByUser(@Param("user") User user, Pageable pageable);
}

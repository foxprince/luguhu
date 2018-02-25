package cn.anthony.luguhu.repository;

import cn.anthony.luguhu.domain.QUserAccount;
import cn.anthony.luguhu.domain.User;
import cn.anthony.luguhu.domain.UserAccount;
public interface UserAccountRepository extends BaseRepository<UserAccount, QUserAccount, Long> {

	UserAccount findByUser(User user);

}

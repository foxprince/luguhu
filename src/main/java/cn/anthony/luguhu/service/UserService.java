package cn.anthony.luguhu.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.anthony.luguhu.domain.QUser;
import cn.anthony.luguhu.domain.User;
import cn.anthony.luguhu.repository.BaseRepository;
import cn.anthony.luguhu.repository.UserRepository;

@Service
public class UserService extends GenericService<User, Long> {

	@Resource
	UserRepository repsitory;

	@Override
	public BaseRepository<User, QUser, Long> getRepository() {
		return this.repsitory;
	}

	public User findByEmail(String email) {
		return repsitory.findByEmail(email);
	}
	
	public User findByWxUser(Long wxUserId) {
		List<User> l = repsitory.findByWxUserId(wxUserId);
		if(l!=null&&l.size()>0)
			return l.get(0);
		return null;
	}

	public boolean emailExists(String email) {
		User u = repsitory.findByEmail(email);
		if (u != null)
			return true;
		return false;
	}

	public List<User> findByLevel(byte level) {
		return repsitory.findByLevel(level);
	}

}

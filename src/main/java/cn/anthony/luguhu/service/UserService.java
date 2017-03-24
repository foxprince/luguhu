package cn.anthony.luguhu.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.anthony.luguhu.domain.User;
import cn.anthony.luguhu.repository.BaseRepository;
import cn.anthony.luguhu.repository.UserRepository;

@Service
public class UserService extends GenericService<User, Long> {

	@Resource
	UserRepository repsitory;

	@Override
	public BaseRepository getRepository() {
		return this.repsitory;
	}

	public User findByEmail(String email) {
		return repsitory.findByEmail(email);
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

package cn.anthony.luguhu.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.anthony.luguhu.domain.User;
import cn.anthony.luguhu.service.UserService;
import cn.anthony.luguhu.web.GenericRestController;

@RestController
@RequestMapping(value = "/api/user")
public class UserApi extends GenericRestController<User, Long> {
	
	public UserApi(UserService service) {
		super(service);
	}
	
}

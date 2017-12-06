package cn.anthony.luguhu.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.anthony.luguhu.domain.User;
import cn.anthony.luguhu.service.UserService;
import cn.anthony.luguhu.web.GenericRestController;

@RestController
@RequestMapping(value = "/api/users")
public class UserApi extends GenericRestController<User, Long> {
	public UserApi(UserService service) {
		super(service);
	}

	//@Override
	@GetMapping(value = "/{wxUserId}")
	public JsonResponse get(@PathVariable Long wxUserId) {
		User user = ((UserService) this.service).findByWxUser(wxUserId);
		if (user != null)
			return new JsonResponse(user);
		else
			return JsonResponse.fail(101, "user hasn't been set");
	}
	
	@GetMapping(value = "/{id}/address")
	public JsonResponse getAddresses(@PathVariable Long id) {
		User user = ((UserService) this.service).findById(id);
		if (user != null)
			return new JsonResponse(user.getAddresses());
		else
			return JsonResponse.fail(101, "user hasn't been set");
	}
}

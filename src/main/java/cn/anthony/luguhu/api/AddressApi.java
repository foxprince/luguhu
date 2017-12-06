package cn.anthony.luguhu.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.anthony.luguhu.domain.Address;
import cn.anthony.luguhu.service.AddressService;
import cn.anthony.luguhu.service.UserService;
import cn.anthony.luguhu.web.GenericRestController;

@RestController
@RequestMapping(value = "/api/addresses")
public class AddressApi extends GenericRestController<Address, Long> {
	public AddressApi(AddressService service) {
		super(service);
	}
	@Autowired
	UserService userService;
	@Override
	@PostMapping
	public JsonResponse create(Address item) {
		Address created = this.service.create(item);
		return new JsonResponse(userService.findById(created.getUser().getId()));
	}
}

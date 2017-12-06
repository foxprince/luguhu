package cn.anthony.luguhu.web;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Throwables;

import cn.anthony.luguhu.api.JsonResponse;
import cn.anthony.luguhu.domain.GenericEntity;
import cn.anthony.luguhu.exception.EntityNotFound;
import cn.anthony.luguhu.service.GenericService;

@RestController
public abstract class GenericRestController<T extends GenericEntity, ID extends Serializable> {
	public Logger logger = LoggerFactory.getLogger(this.getClass());
	protected GenericService<T, ID> service;

	public GenericRestController(GenericService<T, ID> service) {
		this.service = service;
	}

	@ModelAttribute
	private void initCsrf(final HttpServletRequest request) {
	}

	@GetMapping
	public JsonResponse listAll(@PageableDefault(value = 100, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return new JsonResponse(this.service.getRepository().findAll(pageable));
	}

	@GetMapping(value = "/{id}")
	public JsonResponse get(@PathVariable ID id) {
		return new JsonResponse(this.service.findById(id));
	}

	@PostMapping
	public JsonResponse create(T item) {
		T created = this.service.create(item);
		return new JsonResponse(created);
	}

	@PostMapping(value = "/{id}")
	public JsonResponse update(@PathVariable ID id, T item) throws EntityNotFound {
		T entity = this.service.findById(id);
		try {
			copyProperties(item, entity);
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
		T updated = this.service.saveOrUpdate(entity);
		return new JsonResponse(updated);
	}
	public String[] getNullPropertyNames (Object source) {
	    final BeanWrapper src = new BeanWrapperImpl(source);
	    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
	    Set<String> emptyNames = new HashSet<String>();
	    for(java.beans.PropertyDescriptor pd : pds) {
	        if (pd.getPropertyType().getName().indexOf("cn.anthony.luguhu.domain.WxUser")>=0||src.getPropertyValue(pd.getName()) == null) 
	        	emptyNames.add(pd.getName());
	    }
	    String[] result = new String[emptyNames.size()];
	    return emptyNames.toArray(result);
	}
	// then use Spring BeanUtils to copy and ignore null
	public void copyProperties(Object src, Object target) {
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}
	/*
	 * @RequestMapping(value = "/{id}", method = RequestMethod.DELETE) public
	 * JsonResponse delete(@PathVariable ID id) throws EntityNotFound {
	 * this.service.delete(id); return JsonResponse.success(); }
	 */
}

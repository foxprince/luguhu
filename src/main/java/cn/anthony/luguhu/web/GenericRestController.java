package cn.anthony.luguhu.web;

import java.io.Serializable;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Throwables;

import cn.anthony.luguhu.api.JsonResponse;
import cn.anthony.luguhu.domain.GenericEntity;
import cn.anthony.luguhu.exception.EntityNotFound;
import cn.anthony.luguhu.service.ActionLogService;
import cn.anthony.luguhu.service.GenericService;

@RestController
public abstract class GenericRestController<T extends GenericEntity, ID extends Serializable> {

	public Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	ActionLogService actionLogService;

	protected GenericService<T, ID> service;

	public GenericRestController(GenericService<T, ID> service) {
		this.service = service;
	}

	@RequestMapping
	public JsonResponse listAll(@PageableDefault(value = 100, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return new JsonResponse(this.service.getRepository().findAll(pageable));
	}

	@Deprecated
	@RequestMapping(value = { "/listPage" })
	public JsonResponse list(@ModelAttribute("pageRequest") WebPageRequest pageRequest, Model m) {
		return new JsonResponse(this.service.findAll(pageRequest.getPageNumber(), pageRequest.getPageSize()));
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public JsonResponse create(@RequestBody T json) {
		logger.debug("create() with body {} of type {}", json, json.getClass());
		T created = this.service.create(json);
		return new JsonResponse(created);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public JsonResponse get(@PathVariable ID id) {
		return new JsonResponse(this.service.findById(id));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public JsonResponse update(@PathVariable ID id, @RequestBody T json) {
		logger.debug("update() of id#{} with body {}", id, json);
		logger.debug("T json is of type {}", json.getClass());
		T entity = this.service.findById(id);
		try {
			BeanUtils.copyProperties(entity, json);
		} catch (Exception e) {
			logger.warn("while copying properties", e);
			throw Throwables.propagate(e);
		}
		logger.debug("merged entity: {}", entity);
		T updated = this.service.create(entity);
		logger.debug("updated enitity: {}", updated);
		return new JsonResponse(updated);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public JsonResponse delete(@PathVariable ID id) throws EntityNotFound {
		this.service.delete(id);
		return JsonResponse.success();
	}

}

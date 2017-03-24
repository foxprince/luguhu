package cn.anthony.luguhu.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.querydsl.core.types.Predicate;

import cn.anthony.luguhu.api.JsonResponse;
import cn.anthony.luguhu.domain.SalePack;
import cn.anthony.luguhu.service.ProductService;
import cn.anthony.luguhu.service.SalePackService;
import cn.anthony.luguhu.util.Constant;
import cn.anthony.luguhu.util.ControllerUtil;
import lombok.Data;

@Controller
@RequestMapping(value = "/productSalePack")
public class SalePackController extends GenericController<SalePack, Long> {
	@Resource
	SalePackService service;
	@Resource
	ProductService productService;
	@Resource
	Constant constant;

	@Override
	SalePackService getService() {
		return this.service;
	}

	@Override
	public SalePack init(Model m, Object... relateId) {
		m.addAttribute("productList", productService.findAll());
		return new SalePack();
	}

	@Override
	protected String getListView() {
		return "/product/salePackList";
	}

	@Override
	protected String getIndexView() {
		return "/product/salePackList";
	}

	@Override
	protected String getFormView() {
		return "/product/salePackForm";
	}

	@PostMapping(value = { "/upload" })
	@ResponseBody
	public JsonResponse upload(@RequestParam("file") MultipartFile file) throws IOException {
		String fileName = createFileName(file.getOriginalFilename());// file.getOriginalFilename();//
		FileUtils.copyInputStreamToFile(file.getInputStream(),
				new File(constant.getUploadAbsoluteDir() + Constant.FILE_SEPA + fileName));
		return new JsonResponse(fileName);
	}

	@RequestMapping(value = { "/preview" })
	public void preview(String fileName, HttpServletResponse response) throws IOException {
		File f = new File(constant.getUploadAbsoluteDir() + Constant.FILE_SEPA + fileName);
		if (f.exists()&&f.isFile()) {
			InputStream is = new FileInputStream(f);
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
			is.close();
		}
	}

	private String createFileName(String originalName) {
		return DateFormatUtils.format(Calendar.getInstance(), "yyyyMM") + Constant.FILE_SEPA + "_" + UUID.randomUUID().toString() + "."
				+ FilenameUtils.getExtension(originalName);
	}

	@RequestMapping(value = { "list", "listPage" })
	public String list(@ModelAttribute("search") SalePackSearch us, @QuerydslPredicate(root = SalePack.class) Predicate predicate,
			@PageableDefault(value = 100, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable, Model m) {
		ControllerUtil.setPageVariables(m, getService().getRepository().findAll(predicate, pageable));
		return getListView();
	}
}

@Data
class SalePackSearch {
	private String title, description;
}

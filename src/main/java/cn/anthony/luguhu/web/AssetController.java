package cn.anthony.luguhu.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
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
import cn.anthony.luguhu.domain.Asset;
import cn.anthony.luguhu.domain.Tag;
import cn.anthony.luguhu.domain.WxUser;
import cn.anthony.luguhu.repository.TagRepository;
import cn.anthony.luguhu.repository.WxUserRepository;
import cn.anthony.luguhu.service.AssetService;
import cn.anthony.luguhu.service.UserService;
import cn.anthony.luguhu.util.Constant;
import cn.anthony.luguhu.util.ControllerUtil;
import cn.anthony.util.StringTools;
import lombok.Data;
import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping(value = "/asset")
public class AssetController extends GenericController<Asset, Long> {

	@Resource
	AssetService service;
	@Resource
	UserService userService;
	@Resource
	Constant constant;
	@Resource
	WxUserRepository wxUserrepsitory;
	@Resource
	TagRepository tagRepo;
	
	@Override
	AssetService getService() {
		return this.service;
	}

	@Override
	public Asset init(Model m, Object... relateId) {
		return new Asset();
	}

	@Override
	protected String getListView() {
		return "/asset/list";
	}

	@Override
	protected String getIndexView() {
		return "/asset/list";
	}

	@Override
	protected String getFormView() {
		return "/asset/form";
	}

	@RequestMapping(value = { "list", "listPage" })
	public String list(@ModelAttribute("search") AssetSearch search, @QuerydslPredicate(root = Asset.class) Predicate predicate,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable, Model m) {
		ControllerUtil.setPageVariables(m, getService().getRepository().findAll(predicate, pageable));
		if(search!=null&&search.getCreateFrom()!=null&&search.getCreateFrom().equals("WECHAT")) {
			m.addAttribute("wechatUsers", wxUserrepsitory.findDistinctWxUser());
			m.addAttribute("wxUser", search.getWxUser());
			m.addAttribute("tags", tagRepo.findAll());
			m.addAttribute("tag", search.getTag());
			return "/asset/wechatMsg";
		}
		return getListView();
	}

	@PostMapping(value = { "/upload" })
	@ResponseBody
	public JsonResponse upload(@RequestParam("file") MultipartFile file) throws IOException {
		String fileName = StringTools.createFileNameWithYM(file.getOriginalFilename());// file.getOriginalFilename();//
		FileUtils.copyInputStreamToFile(file.getInputStream(),
				new File(constant.getUploadAbsoluteDir() + Constant.FILE_SEPA + fileName));
		Asset item = new Asset();
		item.setLocation(fileName);
		item.setType("IMG");
		item.setSourceName(file.getOriginalFilename());
		item.setTitle(FilenameUtils.getBaseName(file.getOriginalFilename()));
		getService().create(item);
		return new JsonResponse(item);
	}

	@RequestMapping(value = { "/preview" })
	public void preview(String fileName, String size, HttpServletResponse response) throws IOException {
		File f = new File(constant.getUploadAbsoluteDir() + Constant.FILE_SEPA + fileName);
		if (f.exists() && f.isFile()) {
			InputStream is = new FileInputStream(f);
			if (size != null)
				if (size.equals("small"))
					Thumbnails.of(f).size(215, 215).outputFormat("png").toOutputStream(response.getOutputStream());
				else if (size.equals("medium"))
					Thumbnails.of(f).size(430, 430).outputFormat("png").toOutputStream(response.getOutputStream());
				else
					IOUtils.copy(is, response.getOutputStream());
			else
				IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
			is.close();
		}
	}

}

@Data
class AssetSearch {
	private String sourceName,createFrom;
	private String description;
	private WxUser wxUser;
	private Tag tag;
}
package cn.anthony.luguhu.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.anthony.luguhu.domain.Article;
import cn.anthony.luguhu.service.ArticleService;
import cn.anthony.luguhu.web.GenericRestController;

@RestController
@RequestMapping(value = "/api/article")
public class ArticleApi extends GenericRestController<Article, Long> {
	
	public ArticleApi(ArticleService service) {
		super(service);
	}

}

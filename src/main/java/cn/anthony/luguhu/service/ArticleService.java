package cn.anthony.luguhu.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.anthony.luguhu.domain.Article;
import cn.anthony.luguhu.domain.QArticle;
import cn.anthony.luguhu.repository.ArticleRepository;
import cn.anthony.luguhu.repository.BaseRepository;

@Service
public class ArticleService extends GenericService<Article, Long> {

	@Resource
	ArticleRepository repsitory;

	@Override
	public BaseRepository<Article,QArticle,Long> getRepository() {
		return this.repsitory;
	}
}

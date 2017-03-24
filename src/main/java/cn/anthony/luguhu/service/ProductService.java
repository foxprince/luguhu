package cn.anthony.luguhu.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.anthony.luguhu.domain.Product;
import cn.anthony.luguhu.repository.BaseRepository;
import cn.anthony.luguhu.repository.ProductRepository;

@Service
public class ProductService extends GenericService<Product, Long> {

	@Resource
	ProductRepository repsitory;

	@Override
	public BaseRepository getRepository() {
		return this.repsitory;
	}
}

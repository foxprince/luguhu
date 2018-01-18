package cn.anthony.luguhu.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.StringPath;

@NoRepositoryBean
@CrossOrigin
public interface BaseRepository<T, QT extends EntityPath<T>, ID extends Serializable>
		extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>, QuerydslPredicateExecutor<T>, QuerydslBinderCustomizer<QT> {

	@Override
	default public void customize(QuerydslBindings bindings, QT p) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}
	
}
package cn.anthony.luguhu.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.repository.NoRepositoryBean;

import com.querydsl.core.types.EntityPath;

@NoRepositoryBean
public interface BaseRepository<T, QT extends EntityPath<T>, ID extends Serializable>
		extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>, QuerydslPredicateExecutor<T>, QuerydslBinderCustomizer<QT> {
}
package cn.anthony.luguhu.repository;

import cn.anthony.luguhu.domain.QTag;
import cn.anthony.luguhu.domain.Tag;

public interface TagRepository extends BaseRepository<Tag, QTag, Long> {

	public Tag findByLabel(String s);
	
}

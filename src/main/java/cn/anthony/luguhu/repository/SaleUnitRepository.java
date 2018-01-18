package cn.anthony.luguhu.repository;

import java.util.List;

import cn.anthony.luguhu.domain.QSaleUnit;
import cn.anthony.luguhu.domain.SaleUnit;

public interface SaleUnitRepository extends BaseRepository<SaleUnit, QSaleUnit, Long> {
	public List<SaleUnit> findByProductId(Long productId);

	public List<SaleUnit> findBySaleable(Boolean saleable);
}

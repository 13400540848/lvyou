package com.bluesimon.wbf.modules.dict.item;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;

/**
 * Created by Zz on 2021/3/21.
 */
public interface DictItemRepository extends JpaRepository<DictItemEntity, Long>, JpaSpecificationExecutor<DictItemEntity> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<DictItemEntity> findByDictIdInOrderBySortAsc(List<Long> ids);
}

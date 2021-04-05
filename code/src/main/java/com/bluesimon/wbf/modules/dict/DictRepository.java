package com.bluesimon.wbf.modules.dict;

import com.bluesimon.wbf.modules.menu.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * Created by Zz on 2021/3/20.
 */
public interface DictRepository extends JpaRepository<DictEntity, Long>, JpaSpecificationExecutor<DictEntity> {

    int countByCode(String code);

    int countByCodeAndIdNot(String code, Long id);

    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<DictEntity> findByCodeIn(List<String> codes);
}

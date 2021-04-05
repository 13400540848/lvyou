package org.ume.school.modules.moneytype;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.MoneyType;

/**
 * Created by Zz on 2018/8/26.
 */
public interface MoneyTypeRepository extends JpaRepository<MoneyType, String>, JpaSpecificationExecutor<MoneyType> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<MoneyType> findByTypeMode(Integer typeMode);
}

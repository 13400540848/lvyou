package org.ume.school.modules.user.money.buy;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.UserMoneyBuy;

/**
 * 用户买入
 */
public interface UserMoneyBuyRepository extends JpaRepository<UserMoneyBuy, String>, JpaSpecificationExecutor<UserMoneyBuy> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserMoneyBuy> findByUserId(String userId);
}

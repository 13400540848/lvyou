package org.ume.school.modules.user.money.sell;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.UserMoneySell;

/**
 * 用户卖出
 */
public interface UserMoneySellRepository extends JpaRepository<UserMoneySell, String>, JpaSpecificationExecutor<UserMoneySell> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserMoneySell> findByUserId(String userId);
}

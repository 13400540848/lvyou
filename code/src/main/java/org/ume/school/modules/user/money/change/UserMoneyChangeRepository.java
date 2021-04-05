package org.ume.school.modules.user.money.change;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.UserMoneyChange;

/**
 * 用户资产兑换
 */
public interface UserMoneyChangeRepository extends JpaRepository<UserMoneyChange, String>, JpaSpecificationExecutor<UserMoneyChange> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserMoneyChange> findByUserIdOrderByCreateTimeDesc(String userId);
}

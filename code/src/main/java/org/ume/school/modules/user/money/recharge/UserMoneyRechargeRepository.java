package org.ume.school.modules.user.money.recharge;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.UserMoneyRecharge;

/**
 * 用户充值
 */
public interface UserMoneyRechargeRepository extends JpaRepository<UserMoneyRecharge, String>, JpaSpecificationExecutor<UserMoneyRecharge> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserMoneyRecharge> findByUserIdOrderByCreateTimeDesc(String userId);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserMoneyRecharge> findByUserIdAndCheckStatus(String userId, Integer checkStatus);
}

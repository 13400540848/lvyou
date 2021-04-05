package org.ume.school.modules.user.money.cash;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.UserMoneyCash;

/**
 * 用户提现
 */
public interface UserMoneyCashRepository extends JpaRepository<UserMoneyCash, String>, JpaSpecificationExecutor<UserMoneyCash> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserMoneyCash> findByUserId(String userId);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserMoneyCash> findByUserIdAndCheckStatus(String userId, Integer checkStatus);
}

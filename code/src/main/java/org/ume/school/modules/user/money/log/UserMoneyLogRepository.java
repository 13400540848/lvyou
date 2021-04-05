package org.ume.school.modules.user.money.log;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.UserMoneyLog;

/**
 * 用户交易记录
 */
public interface UserMoneyLogRepository extends JpaRepository<UserMoneyLog, String>, JpaSpecificationExecutor<UserMoneyLog> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserMoneyLog> findByUserId(String userId);
}

package org.ume.school.modules.user.money;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.UserMoney;

/**
 * 用户钱包
 */
public interface UserMoneyRepository extends JpaRepository<UserMoney, String>, JpaSpecificationExecutor<UserMoney> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserMoney> findByUserId(String userId);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserMoney> findByUserIdAndTypeId(String userId, String typeId);
}

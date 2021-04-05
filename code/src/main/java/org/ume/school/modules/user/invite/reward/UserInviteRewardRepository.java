package org.ume.school.modules.user.invite.reward;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.UserInviteReward;

/**
 * 用户邀请
 */
public interface UserInviteRewardRepository extends JpaRepository<UserInviteReward, String>, JpaSpecificationExecutor<UserInviteReward> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserInviteReward> findByUserId(String userId);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserInviteReward> findByReferrerId(String referrerId);
    
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<UserInviteReward> findByUserIdAndReferrerId(String userId, String referrerId);
}

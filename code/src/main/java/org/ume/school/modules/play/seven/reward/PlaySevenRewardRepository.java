package org.ume.school.modules.play.seven.reward;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.PlaySevenReward;

/**
 * 双色球
 */
public interface PlaySevenRewardRepository extends JpaRepository<PlaySevenReward, String>, JpaSpecificationExecutor<PlaySevenReward> {
	@QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<PlaySevenReward> findByOrderByRewardCodeAsc();
}

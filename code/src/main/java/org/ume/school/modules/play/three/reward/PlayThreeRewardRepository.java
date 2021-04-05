package org.ume.school.modules.play.three.reward;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.PlayThreeReward;

/**
 * 快3
 */
public interface PlayThreeRewardRepository extends JpaRepository<PlayThreeReward, String>, JpaSpecificationExecutor<PlayThreeReward> {
	@QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<PlayThreeReward> findByOrderByRewardCodeAsc();
}

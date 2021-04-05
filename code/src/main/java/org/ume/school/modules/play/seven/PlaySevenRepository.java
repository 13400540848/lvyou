package org.ume.school.modules.play.seven;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.ume.school.modules.model.entity.PlaySeven;

/**
 * 双色球
 */
public interface PlaySevenRepository extends JpaRepository<PlaySeven, String>, JpaSpecificationExecutor<PlaySeven> {
    
}

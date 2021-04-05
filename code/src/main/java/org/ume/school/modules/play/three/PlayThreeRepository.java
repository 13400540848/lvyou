package org.ume.school.modules.play.three;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.ume.school.modules.model.entity.PlayThree;

/**
 * 快3
 */
public interface PlayThreeRepository extends JpaRepository<PlayThree, String>, JpaSpecificationExecutor<PlayThree> {
    
}

package org.ume.school.modules.advert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.ume.school.modules.model.entity.Advert;

/**
 * Created by Zz on 2018/8/26.
 */
public interface AdvertRepository extends JpaRepository<Advert, String>, JpaSpecificationExecutor<Advert> {
    
}

package org.ume.school.modules.config;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.ume.school.modules.model.entity.Config;

/**
 * Created by Zz on 2018/10/13.
 */
public interface ConfigRepository extends JpaRepository<Config, String>, JpaSpecificationExecutor<Config> {
    @QueryHints(value = {@QueryHint(name = "name", value = "value")}, forCounting = true)
    List<Config> findByCode(String code);
}

package com.bluesimon.wbf.modules.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Zz on 2018/8/27.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    List<UserEntity> findByAccountAndPassword(String account, String password);

    int countByAccount(String code);

    int countByAccountAndIdNot(String code, Long id);

    List<UserEntity> findByAccount(String account);
    
    List<UserEntity> findByAccountAndIdNot(String account, Long id);
}

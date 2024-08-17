package com.samsam.travel.travelcommerce.domain.user.repository;

import com.samsam.travel.travelcommerce.entity.User;
import com.samsam.travel.travelcommerce.entity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.userId = ?1")
    Optional<User> findByUserId(String userId);

    /**
     * 사용자 ID로 역할을 찾아 반환합니다.
     *
     * @param userId 사용자 ID.
     * @return 사용자 ID에 해당하는 역할.
     */
    @Query("SELECT u.role FROM User u WHERE u.userId = ?1")
    Role findRoleByUserId(String userId);

    /**
     * 사용자 ID를 기반으로 데이터베이스에서 사용자의 역할을 업데이트합니다.
     *
     * @param role   새로운 역할로 사용자에게 할당할 역할.
     * @param userId 업데이트할 사용자의 ID.
     * @return 업데이트된 행의 수.
     *         성공적으로 업데이트된 경우 1, 실패한 경우 0 이상.
     */
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.role = ?1 WHERE u.userId = ?2")
    int updateRoleByUserId(Role role, String userId);
}
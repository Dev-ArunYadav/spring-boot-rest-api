package com.spring_boot.spring_boot_rest_api.repository;

import com.spring_boot.spring_boot_rest_api.dto.UserDTO;
import com.spring_boot.spring_boot_rest_api.enums.UserStatus;
import com.spring_boot.spring_boot_rest_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByPhone(String phone);

//    @Query("SELECT new com.spring_boot.spring_boot_rest_api.dto.UserDTO(u.fullName, u.email, u.phone, u.status, u.financialYear, u.role) FROM User u WHERE u.status = :status")
//    List<UserDTO> findUsersWithStatus(@Param("status") UserStatus status);

    List<User> findByStatus(UserStatus status);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = :status, u.financialYear = :financialYear WHERE u.id = :userId")
    int updateUserStatusAndFinancialYear(@Param("userId") Long userId,
                                         @Param("status") UserStatus status,
                                         @Param("financialYear") String financialYear);

//    List<User> findByStatus(UserStatus status);

}

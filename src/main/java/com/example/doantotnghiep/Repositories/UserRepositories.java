package com.example.doantotnghiep.Repositories;
import com.example.doantotnghiep.Models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepositories extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUserName(String userName);
   List<UserEntity> findAllByUserName(String userName);
        Boolean existsByUserName (String userName);
//        @Query()

}

package com.safb.practise.repository;

import com.safb.practise.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Integer>
{
  UserEntity findByEmail(String email);

  UserEntity findByPublicId(String publicId);
}

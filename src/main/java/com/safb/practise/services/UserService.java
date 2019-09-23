package com.safb.practise.services;

import com.safb.practise.dto.*;
import java.util.*;
import org.springframework.security.core.userdetails.*;

public interface UserService extends UserDetailsService
{
  UserDto getUserByPublicId(String publicId);

  UserDto getUserByEmail(String email);

  List<UserDto> getUsers();

  UserDto createUser(UserDto userDto);
}

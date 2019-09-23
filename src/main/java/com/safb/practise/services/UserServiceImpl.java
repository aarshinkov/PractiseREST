package com.safb.practise.services;

import com.safb.practise.dto.*;
import com.safb.practise.entity.*;
import com.safb.practise.errors.*;
import com.safb.practise.exceptions.*;
import com.safb.practise.repository.*;
import com.safb.practise.utils.*;
import java.util.*;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

@Service
public class UserServiceImpl implements UserService
{
  private static final Integer PUBLIC_ID_LENGTH = 30;

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDto getUserByPublicId(String publicId)
  {
    UserEntity storedUser = usersRepository.findByPublicId(publicId);

    if (storedUser == null)
    {
      throw new UserServiceException(ErrorMessages.USER_NO_EXIST.getErrorMessage());
    }

    UserDto result = new UserDto();

    ModelMapper modelMapper = new ModelMapper();

    modelMapper.map(storedUser, result);

    return result;
  }

  @Override
  public UserDto getUserByEmail(String email)
  {
    UserEntity storedUser = usersRepository.findByEmail(email);

    if (storedUser == null)
    {
      throw new UserServiceException(ErrorMessages.USER_NO_EXIST.getErrorMessage());
    }

    UserDto result = new UserDto();

    ModelMapper modelMapper = new ModelMapper();

    modelMapper.map(storedUser, result);

    return result;
  }

  @Override
  public List<UserDto> getUsers()
  {
    List<UserEntity> userEntities = usersRepository.findAll();

    ModelMapper modelMapper = new ModelMapper();

    List<UserDto> result = new ArrayList<>();

    userEntities.stream().map((userEntity) ->
    {
      UserDto user = new UserDto();
      modelMapper.map(userEntity, user);
      return user;
    }).forEachOrdered((user) ->
    {
      result.add(user);
    });

    return result;
  }

  @Override
  public UserDto createUser(UserDto userDto)
  {
    UserEntity storedUser = usersRepository.findByEmail(userDto.getEmail());

    if (storedUser != null)
    {
      throw new UserServiceException(ErrorMessages.USER_EXISTS.getErrorMessage());
    }

    ModelMapper modelMapper = new ModelMapper();

    UserEntity userEntity = new UserEntity();
    modelMapper.map(userDto, userEntity);

    userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
    userEntity.setPublicId(Utils.generateUserId(PUBLIC_ID_LENGTH));

    UserEntity createdUser = usersRepository.save(userEntity);

    UserDto response = new UserDto();

    modelMapper.map(createdUser, response);

    return response;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
  {
    UserEntity userEntity = usersRepository.findByEmail(email);

    if (userEntity == null)
    {
      throw new UsernameNotFoundException(email);
    }

    List<GrantedAuthority> roles = new ArrayList<>();
//    roles.add(new SimpleGrantedAuthority("USER"));

    return new User(userEntity.getEmail(), userEntity.getPassword(), roles);
  }
}

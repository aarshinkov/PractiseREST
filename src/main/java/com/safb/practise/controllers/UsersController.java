package com.safb.practise.controllers;

import com.safb.practise.dto.*;
import com.safb.practise.requests.*;
import com.safb.practise.response.*;
import com.safb.practise.services.*;
import java.util.*;
import org.modelmapper.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users")
public class UsersController
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserService userService;

  @GetMapping(value = "/{publicId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public UserGetResponse getUser(@PathVariable("publicId") String publicId)
  {
    ModelMapper modelMapper = new ModelMapper();

    UserDto user = userService.getUserByPublicId(publicId);

    UserGetResponse result = new UserGetResponse();

    modelMapper.map(user, result);

    return result;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<UserGetResponse> getUsers()
  {
    ModelMapper modelMapper = new ModelMapper();

    List<UserDto> users = userService.getUsers();

    List<UserGetResponse> result = new ArrayList<>();

    users.stream().map((user) ->
    {
      UserGetResponse userGetResponse = new UserGetResponse();
      modelMapper.map(user, userGetResponse);
      return userGetResponse;
    }).forEachOrdered((userGetResponse) ->
    {
      result.add(userGetResponse);
    });

    return result;
  }

  @PostMapping(consumes =
  {
    MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE
  }, produces = MediaType.APPLICATION_JSON_VALUE)
  public UserCreatedResponse createUser(@RequestBody UserCreateRequest user)
  {
    ModelMapper modelMapper = new ModelMapper();

    UserDto userDto = new UserDto();
    modelMapper.map(user, userDto);

    UserDto createdUser = userService.createUser(userDto);

    UserCreatedResponse response = new UserCreatedResponse();
    modelMapper.map(createdUser, response);

    return response;
  }

  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public String updateUser()
  {
    return "updateUser was called";
  }

  @DeleteMapping(value = "/{publicId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public String deleteUser(@PathVariable("publicId") String publicId)
  {
    return "Deleting user with public ID: " + publicId;
  }
}

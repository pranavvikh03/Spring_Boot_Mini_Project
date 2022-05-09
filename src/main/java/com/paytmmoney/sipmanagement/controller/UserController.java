package com.paytmmoney.sipmanagement.controller;

import com.paytmmoney.sipmanagement.dto.UserDto;
import com.paytmmoney.sipmanagement.services.UserService;
import com.paytmmoney.sipmanagement.exceptions.DataUnavailableException;
import com.paytmmoney.sipmanagement.exceptions.ResourceNotCreatedException;
import com.paytmmoney.sipmanagement.exceptions.ResourceNotFoundException;
import com.paytmmoney.sipmanagement.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "API for User", description = "API's for managing user operations")
public class UserController {

    @Autowired
    private UserService userservice;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "API for fetching all Users from User table")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Users are available and all users are fetched successfully.",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "Users are not available or Problem occured in fetching", content = @Content)
    })

    @GetMapping("/Users")
    @Cacheable(value="users")
    public List<UserDto> fetchUsers(){
        List<UserDto> usersList = userservice.fetchAllUsers()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
        if(usersList.isEmpty()) throw new DataUnavailableException("Data Unavailable / No user is available");
//        else return new ResponseEntity<>(usersList, HttpStatus.OK);
        return usersList;
    }

    @Operation(summary = "API for Fetching User by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is available and fetched successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "User Not found with given id", content=@Content)
    })
    @Cacheable(value="users", key = "#id")
    @GetMapping("/Users/{id}")
    public UserDto fetchUniqueUser(@Parameter(description = "Id of user to find") @PathVariable Long id){
        return modelMapper.map(userservice.findUserById(id),UserDto.class);
    }

    @Operation(summary = "API for Create or Insert new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is created", content = @Content),
            @ApiResponse(responseCode = "404", description = "User is not Created", content=@Content)
    })
    @CacheEvict(value = "users",allEntries = true)
    @PostMapping("/Users")
    public UserDto createUser(@Valid @RequestBody UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        User userTemp = userservice.createNewUser(user);
        if(userTemp == null) throw new ResourceNotCreatedException("Problem in User Creation");
//        else return new ResponseEntity<>(modelMapper.map(userTemp,UserDto.class), HttpStatus.CREATED);
        return modelMapper.map(userTemp, UserDto.class);
    }

    @Operation(summary = "API for Update existing user data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data Updated Successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Problem in Updation or User not found with given id", content=@Content)
    })
    @PutMapping("/Users/{id}")
    @CachePut(value="users", key = "#id")
    public UserDto updateUser(@Valid @RequestBody UserDto userDto, @Parameter(description = "ID of user to be Updated") @PathVariable Long id){
        User user = modelMapper.map(userDto, User.class);
        User temp = userservice.updateExistingUser(id, user);
        if(temp!=null)
            return modelMapper.map(temp, UserDto.class);
        else
            throw new ResourceNotFoundException("User Not Found with id: "+id);
    }

    @Operation(summary = "API for Delete User by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is deleted Successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Problem in user deletion", content=@Content)
    })
    @DeleteMapping("/Users/{id}")
    @CacheEvict(value = "users", allEntries = true)
    public void deleteUser(@Parameter(description = "ID of user to be deleted") @PathVariable Long id){
            userservice.deleteExistingUser(id);
    }
}

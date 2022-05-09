package com.paytmmoney.sipmanagement.services;

import com.paytmmoney.sipmanagement.dao.UserRepo;
import com.paytmmoney.sipmanagement.exceptions.ResourceNotFoundException;
import com.paytmmoney.sipmanagement.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    private Logger log = LoggerFactory.getLogger(UserService.class);

    /*
        Function to Fetch All Users
        It fetches all Users from table
    */
    public List<User> fetchAllUsers(){
        return new ArrayList<>(userRepo.findAll());
    }

    /*
        Function to Create New User
        It takes user object as input parameter and saves it to db through user repository
    */
    public User createNewUser(User user){
        User userTemp = null;
        userTemp = userRepo.save(user);
        return userTemp;
    }

    /*
        Function to Find User Uniquely by Id
        It takes userid as input and find that user from table
        If user is available then it returns that user object otherwise throws Exception
    */
    public User findUserById(Long id) {
        return userRepo.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("User Not Found with id: "+id));
    }

    /*
        Function to Update User by Id
        It takes userid and  user object as input parameter
        Firstly check if user exists or not otherwise return null
        If user exist then it updates data in db.
    */
    public User updateExistingUser(Long id, User modifiedUser){
        User temp = null;
        if(userRepo.existsById(id)){
            modifiedUser.setUserId(id);
            temp = userRepo.save(modifiedUser);
        }
        return temp;
    }

    /*
        Function to Delete User by Id
        It takes user id as input and checks user exists or not
        If yes:
            deletes user and update status as true
        If no:
            throws exception resource not found
        finally return status
    */
    public void deleteExistingUser(Long id){

            if(userRepo.existsById(id)) {
                userRepo.deleteById(id);
            }
            else throw new ResourceNotFoundException("User Not Found with id: " + id);
    }
}

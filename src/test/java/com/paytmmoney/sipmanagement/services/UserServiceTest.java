package com.paytmmoney.sipmanagement.services;

import com.paytmmoney.sipmanagement.dao.UserRepo;
import com.paytmmoney.sipmanagement.model.User;
import com.paytmmoney.sipmanagement.model.UserDetails;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Test
    @DisplayName("Test Function for Fetching all Users from DB")
    void fetchAllUsers() {
        assertThat(userRepo.findAll().toArray()).isEqualTo(userService.fetchAllUsers().toArray());
    }

    @Test
    void createNewUser() {
        UserDetails userDetails = new UserDetails("Abc123",
                            new ArrayList<>(List.of("121096488d3","1d398417594")),
                            "pv@gmail.com","Pranav");
        User testUser = User.builder()
                        .userId(1L)
                        .userDetails(userDetails)
                        .build();
//        Mockito.when(userService.createNewUser(testUser)).thenReturn(testUser);
        userService.createNewUser(testUser);
        Mockito.verify(userRepo, Mockito.times(1)).save(userArgumentCaptor.capture());

        assertThat(userArgumentCaptor.getValue().getUserId()).isEqualTo(testUser.getUserId());

        assertThat(userArgumentCaptor.getValue().getUserDetails().getEmail()).isEqualTo(userDetails.getEmail());

    }

    @Test
    void findUserById() {
        UserDetails userDetails = new UserDetails("Abc123",
                new ArrayList<>(List.of("1","2")),
                "pv@gmail.com","Pranav");
        User testUser = User.builder()
                .userId(1L)
                .userDetails(userDetails)
                .build();
        Mockito.when(userRepo.findById(1L)).thenReturn(Optional.of(testUser));
    }

//    @Test
//    void updateExistingUser() {
//
//        //Store new dummy user
//        UserDetails userDetailsBeforeUpdate = new UserDetails("Abc123",
//                new ArrayList<>(List.of("121096488d3","1d398417594")),
//                "pv@gmail.com","Pranav");
//        User testUserBeforeUpdate = User.builder()
//                .userId(1L)
//                .userDetails(userDetailsBeforeUpdate)
//                .build();
//        userRepo.save(testUserBeforeUpdate);
//
//        //Data for dummy user updation
//        UserDetails userDetailsForUpdate = new UserDetails("Abc1234",
//                new ArrayList<>(List.of("121096488d4","1d398417593")),
//                "pvtp3700@gmail.com","Pranav Vikharankar");
//        User testUserForUpdate = User.builder()
//                .userId(1L)
//                .userDetails(userDetailsForUpdate)
//                .build();
//        userService.updateExistingUser(1L,testUserForUpdate);
//        Mockito.verify(userRepo, Mockito.times(1)).save(userArgumentCaptor.capture());
//
//    }

//    @Test
//    void deleteExistingUser() {
//        userService.deleteExistingUser(1L);
//        Mockito.verify(userRepo, Mockito.times(1)).deleteById(1L);
//    }

}
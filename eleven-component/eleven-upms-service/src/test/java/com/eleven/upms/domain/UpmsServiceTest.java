//package com.eleven.upms.domain;
//
//import cn.hutool.core.util.RandomUtil;
//import com.eleven.core.exception.ProcessFailureException;
//import com.eleven.core.security.Principal;
//import com.eleven.upms.configure.UpmsProperties;
//import com.eleven.upms.core.UpmsConstants;
//import com.eleven.upms.domain.model.User;
//import com.eleven.upms.domain.service.UserService;
//import com.eleven.upms.core.action.UserCreateAction;
//import com.eleven.upms.core.action.UserQuery;
//import com.eleven.upms.core.model.UserState;
//import com.eleven.upms.core.action.UserUpdateAction;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.UUID;
//
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class UpmsServiceTest {
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    UpmsProperties upmsProperties;
//
//
//    @BeforeEach
//    void setUp() {
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    @Order(1)
//    @DisplayName("crud")
//    void crudUser() {
//
//        // create
//        var action = (UserCreateAction) new UserCreateAction()
//                .setUsername(RandomUtil.randomString(15))
//                .setNickname(RandomUtil.randomString(15))
//                .setState(UserState.NORMAL)
//                .setRoles(new ArrayList<>());
//        var userDto = userService.createUser(action);
//
//        // load
//        var userDtoFond = userService.getUser(userDto.getId());
//        Assertions.assertNotNull(userDtoFond.orElse(null));
//        Assertions.assertEquals(action.getUsername(), userDto.getUsername());
//        Assertions.assertEquals(action.getNickname(), userDto.getNickname());
//        Assertions.assertEquals(action.getState(), userDto.getState());
//        Assertions.assertEquals(action.getRoles(), userDto.getRoles());
//        Assertions.assertLinesMatch(action.getRoles(), userDto.getRoles());
//        Assertions.assertFalse(userDto.getIsLocked());
//        Assertions.assertNotNull(userDto.getRegisterAt());
//
//        // update
//        var updateAction = (UserUpdateAction) new UserUpdateAction()
//                .setNickname(RandomUtil.randomString(15))
//                .setState(UserState.READONLY);
//        userDto = userService.updateUser(userDto.getId(), updateAction);
//        Assertions.assertEquals(updateAction.getNickname(), userDto.getNickname());
//        Assertions.assertEquals(updateAction.getState(), userDto.getState());
//
//        // delete
//        userService.deleteUser(userDto.getId());
//
//    }
//
//    @Test
//    @Order(2)
//    @DisplayName("login")
//    void loginUser() {
//        var action = (UserCreateAction) new UserCreateAction()
//                .setUsername(RandomUtil.randomString(15))
//                .setNickname(RandomUtil.randomString(15))
//                .setState(UserState.NORMAL)
//                .setRoles(new ArrayList<>());
//        var userDto = userService.createUser(action);
//
//        // login successful
//        var principal = userService.loginUser(userDto.getUsername(), upmsProperties.getDefaultPassword());
//        Assertions.assertEquals(new Principal(User.TYPE_INNER_USER , userDto.getId()), principal);
//
//        // no existing user
//        var error = Assertions.assertThrows(ProcessFailureException.class, () -> userService.loginUser(UUID.randomUUID().toString(), "error_password"));
//        Assertions.assertEquals(UpmsConstants.ERROR_LOGIN_PASSWORD.getError(), error.getError());
//
//        // wrong password
//        error = Assertions.assertThrows(ProcessFailureException.class, () -> userService.loginUser(userDto.getUsername(), "error_password"));
//        Assertions.assertEquals(UpmsConstants.ERROR_LOGIN_PASSWORD.getError(), error.getError());
//
//        // already deleted
//        userService.deleteUser(userDto.getId());
//        error = Assertions.assertThrows(ProcessFailureException.class, () -> userService.loginUser(userDto.getUsername(), upmsProperties.getDefaultPassword()));
//        Assertions.assertEquals(UpmsConstants.ERROR_USER_ALREADY_DELETED.getError(), error.getError());
//    }
//
//    @Test
//    @Order(3)
//    @DisplayName("query")
//    void queryUser() {
//        var query = (UserQuery) new UserQuery()
//                .setType(User.TYPE_INNER_USER)
//                .setIsLocked(false);
//        userService.queryPage(query);
//    }
//
//}

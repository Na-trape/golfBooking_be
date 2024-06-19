package fontys.sem3.school.unit;


import fontys.sem3.school.business.exception.InvalidCredentialsException;
import fontys.sem3.school.business.impl.LoginUseCaseImpl;
import fontys.sem3.school.configuration.security.token.AccessTokenEncoder;
import fontys.sem3.school.configuration.security.token.impl.AccessTokenImpl;
import fontys.sem3.school.domain.LoginRequest;
import fontys.sem3.school.domain.LoginResponse;
import fontys.sem3.school.repository.UserRepository;
import fontys.sem3.school.repository.entity.PlayerEntity;
import fontys.sem3.school.repository.entity.RoleEnum;
import fontys.sem3.school.repository.entity.UserEntity;
import fontys.sem3.school.repository.entity.UserRoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccessTokenEncoder accessTokenEncoder;

    @InjectMocks
    private LoginUseCaseImpl loginUseCaseImpl;

    @Test
    void testLogin_InvalidPassword() {
        String username = "testuser";
        String password = "invalidpassword";
        String encodedPassword = "encodedPassword";

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(encodedPassword)
                .player(PlayerEntity.builder().id(1L).build())
                .userRoles(Set.of(UserRoleEntity.builder().role(RoleEnum.PLAYER).build()))
                .build();

        when(userRepository.findByUsername(username)).thenReturn(userEntity);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        LoginRequest loginRequest = LoginRequest.builder()
                .username(username)
                .password(password)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> loginUseCaseImpl.login(loginRequest));

        verify(userRepository, times(1)).findByUsername(username);
        verify(passwordEncoder, times(1)).matches(password, encodedPassword);
        verify(accessTokenEncoder, never()).encode(any(AccessTokenImpl.class));
    }

    @Test
    void testLogin_ValidCredentials() {
        String username = "testuser";
        String password = "validpassword";
        String encodedPassword = "encodedPassword";
        String accessToken = "accessToken";

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(encodedPassword)
                .player(PlayerEntity.builder().id(1L).build())
                .userRoles(Set.of(UserRoleEntity.builder().role(RoleEnum.PLAYER).build()))
                .build();

        when(userRepository.findByUsername(username)).thenReturn(userEntity);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(accessTokenEncoder.encode(any(AccessTokenImpl.class))).thenReturn(accessToken);

        LoginRequest loginRequest = LoginRequest.builder()
                .username(username)
                .password(password)
                .build();

        LoginResponse loginResponse = loginUseCaseImpl.login(loginRequest);

        assertEquals(accessToken, loginResponse.getAccessToken());

        verify(userRepository, times(1)).findByUsername(username);
        verify(passwordEncoder, times(1)).matches(password, encodedPassword);
        verify(accessTokenEncoder, times(1)).encode(any(AccessTokenImpl.class));
    }

    @Test
    void testLogin_InvalidUsername() {
        String username = "invaliduser";
        String password = "password";

        when(userRepository.findByUsername(username)).thenReturn(null);

        LoginRequest loginRequest = LoginRequest.builder()
                .username(username)
                .password(password)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> loginUseCaseImpl.login(loginRequest));

        verify(userRepository, times(1)).findByUsername(username);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(accessTokenEncoder, never()).encode(any(AccessTokenImpl.class));
    }

//    @Test
//    void testLogin_InvalidPassword() {
//        String username = "testuser";
//        String password = "invalidpassword";
//        String encodedPassword = "encodedPassword";
//
//        UserEntity userEntity = UserEntity.builder()
//                .username(username)
//                .password(encodedPassword)
//                .player(PlayerEntity.builder().id(1L).build())
//                .userRoles((Set<UserRoleEntity>) List.of(UserRoleEntity.builder().role(RoleEnum.PLAYER).build()))
//                .build();
//
//        when(userRepository.findByUsername(username)).thenReturn(userEntity);
//        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);
//
//        LoginRequest loginRequest = LoginRequest.builder()
//                .username(username)
//                .password(password)
//                .build();
//
//        assertThrows(InvalidCredentialsException.class, () -> loginUseCaseImpl.login(loginRequest));
//
//        verify(userRepository, times(1)).findByUsername(username);
//        verify(passwordEncoder, times(1)).matches(password, encodedPassword);
//        verify(accessTokenEncoder, never()).encode(any(AccessTokenImpl.class));
//    }
}

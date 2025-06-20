package com.maxiamikel.userAuthApi.service.auth;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.maxiamikel.userAuthApi.dto.AccessTokenDto;
import com.maxiamikel.userAuthApi.dto.LoginRequestDto;
import com.maxiamikel.userAuthApi.dto.UserRequestDto;
import com.maxiamikel.userAuthApi.entity.Role;
import com.maxiamikel.userAuthApi.entity.User;
import com.maxiamikel.userAuthApi.repository.RoleRepository;
import com.maxiamikel.userAuthApi.repository.UserRepository;
import com.maxiamikel.userAuthApi.utils.InstantConverter;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Override
    public User register(UserRequestDto request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            log.warn("Username already esists in the users table : {}", request.getUsername());
            throw new RuntimeException("Username already taken");
        }

        User user = new User();

        user.setId(null);
        user.setPassword(passwordEncoder.encode(request.getPassword().trim()));
        user.setUsername(request.getUsername().trim());
        assignDefaultRoleToUser(user);

        var newUser = userRepository.save(user);
        log.info("New user: {} successfully created", newUser.getUsername());

        return newUser;
    }

    @Override
    public AccessTokenDto login(LoginRequestDto credentials) {

        User user = userRepository.findByUsername(credentials.getUsername()).orElseThrow(
                () -> new EntityNotFoundException("Username or password incorrect!"));

        if (!chechIsPasswordMatches(credentials.getPassword(), user.getPassword())) {
            throw new EntityNotFoundException("Username or password incorrect!");
        }

        var now = Instant.now();
        var expiration = 60L;
        var expirationDateTime = InstantConverter.convertToString(expiration);

        var scopes = user.getRoles().stream().map(Role::getName).collect(Collectors.joining(" "));

        var claims = JwtClaimsSet
                .builder()
                .issuer("Auth-API")
                .subject(String.valueOf(user.getId()))
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiration))
                .claim("username", user.getUsername())
                .claim("scope", scopes)
                .build();

        var tokenJwt = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        var accessTokenDto = AccessTokenDto
                .builder()
                .expireAt(expirationDateTime)
                .accessToken(tokenJwt)
                .build();

        return accessTokenDto;
    }

    private boolean chechIsPasswordMatches(String rawPassword, String userPassword) {
        return passwordEncoder.matches(rawPassword, userPassword);
    }

    private void assignDefaultRoleToUser(User user) {
        Role guest = roleRepository.findByName("GUEST")
                .orElseThrow(() -> new EntityNotFoundException("Role GUEST not found"));

        if (userRepository.count() == 0) {
            Role admin = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new EntityNotFoundException("Role ADMIN not found"));
            user.setRoles(Set.of(admin, guest));
        } else {
            user.setRoles(Set.of(guest));
        }

    }

}

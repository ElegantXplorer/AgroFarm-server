package com.cts.agrofarmingstore.service;

import java.util.List;

import com.cts.agrofarmingstore.model.AuthenticationResponse;
import com.cts.agrofarmingstore.model.Token;
import com.cts.agrofarmingstore.model.User;
import com.cts.agrofarmingstore.repository.TokenRepository;
import com.cts.agrofarmingstore.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 TokenRepository tokenRepository,
                                 AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User request) {

        // check if user already exist. if exist than authenticate the user
        if (repository.findByEmailId(request.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, "User already exist", null);
        }

        User user = new User();
        user.setEmailId(request.getEmailId());
        user.setAddress(request.getAddress());
        user.setAddressType(request.getAddressType());
        user.setCity(request.getCity());
        user.setMobileNo(request.getMobileNo());
        user.setName(request.getName());
        user.setPinCode(request.getPinCode());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setState(request.getState());
        user.setTotalOrder(request.getTotalOrder());
        user.setRole(request.getRole());

        user = repository.save(user);

        String jwt = jwtService.generateToken(user);

        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "User registration was successful", user);

    }

    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = repository.findByEmailId(request.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
//        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "User login was successful", user);

    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if (validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t -> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }

    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}


package com.app.services;

import com.app.dto.ServiceResponse;
import com.app.dto.UserRegister;
import com.app.dto.UserSignIn;
import com.app.entities.User;
import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findUserByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails
                        .User(user.getUsername(),user.getPassword(),new ArrayList<>()))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username) );
    }

    public ResponseEntity<?> registerUser(UserRegister dto){
        return userRepository.findUserByUsername(dto.getUsername())
                .map(user -> ResponseEntity.ok(ServiceResponse.error("This User is already registered")))
                .orElseGet(()->{
                    User user = new User();
                    user.setUsername(dto.getUsername());
                    user.setPassword(passwordEncoder.encode(dto.getPassword()));
                    user.setFirstName(dto.getFirstName());
                    user.setLastName(dto.getLastName());
                    userRepository.save(user);
                    return ResponseEntity.ok(ServiceResponse.ok(user));
                });
    }

    public ResponseEntity<?> loginUser(UserSignIn dto){

        UserDetails userDetails = loadUserByUsername(dto.getUsername());

        if (userDetails != null && passwordEncoder.matches(dto.getPassword(), userDetails.getPassword())){

            Optional<User> dbUser = userRepository.findUserByUsername(dto.getUsername());

            byte[] encodedBytes = Base64.getEncoder()
                    .encode(String.format("%s %s",dto.getUsername(),dto.getPassword()).getBytes());

            String base64String = new String(encodedBytes);

            return ResponseEntity.ok(ServiceResponse.ok(new Object(){
                public String token = base64String;
                public String firstName = dbUser.get().getFirstName();
                public String lastName = dbUser.get().getLastName();

            }));
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }
}

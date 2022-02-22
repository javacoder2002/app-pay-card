package uz.pdp.apppaycard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.apppaycard.payload.LoginDto;
import uz.pdp.apppaycard.security.JwtProvider;
import uz.pdp.apppaycard.service.MyAuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    MyAuthService myAuthService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public HttpEntity<?> loginToSystem(@RequestBody LoginDto loginDto) {
        /**
         * -> Qo'lda tekshirsh
         UserDetails userDetails = myAuthService.loadUserByUsername(loginDto.getUsername());
         //boolean equals = userDetails.getPassword().equals(loginDto.getPassword());
         boolean matches = passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword());
         if (!matches)
         return ResponseEntity.status(401).body("username or password is wrong!");
         String token = jwtProvider.generateToken(userDetails.getUsername());
         return ResponseEntity.ok(token);*/

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()
                    )
            );
            String token = jwtProvider.generateToken(loginDto.getUsername());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("username or password is wrong!");
        }

    }

}

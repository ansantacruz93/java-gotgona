package com.gorgona.controller;

import com.gorgona.request.LoginRequest;
import com.gorgona.response.LoginResponse;
import com.gorgona.response.Response;
import com.gorgona.service.LoginService;
import com.gorgona.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private LoginService loginService;


    /**
     * Este metodo tiene como fin gestionar el logueo de un usuario
     *
     * @param loginRequest - request con user & pass
     * @return datos del usuario y token de acceso
     * @throws Exception
     */
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) throws Exception {

        LoginResponse response = new LoginResponse();
        try {
            response = this.loginService.login(loginRequest);
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            Response errorResponse = new Response();
            errorResponse.setMensaje(e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }

        final UserDetails userDetails = loginService.loadUserByUsername(loginRequest.getUsername());
        final String token = jwtUtility.generateToken(userDetails);
        response.setToken(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

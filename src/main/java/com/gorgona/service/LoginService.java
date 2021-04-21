package com.gorgona.service;

import com.gorgona.request.LoginRequest;
import com.gorgona.response.LoginResponse;
import com.gorgona.utility.Constantes;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoginService implements UserDetailsService {


    /**
     * Este metodo tiene como fin gestionar el logueo de un usuario
     *
     * @param loginRequest - request con user & pass
     * @return datos del usuario y token de acceso
     * @throws Exception
     */
    public LoginResponse login(LoginRequest loginRequest)  {
        if(loginRequest.getUsername().equals("user") && loginRequest.getPassword().equals("1234@")) {
            LoginResponse response =  this.crearUserDataMock();
            response.setMensaje(Constantes.CREDENCIALES_CORRECTAS);
            return response;
        } else {
            throw new BadCredentialsException(Constantes.CREDENCIALES_INCORRECTAS);
        }
    }

    /**
     * Este metodo tiene como fin crear la data mockeada de un usuario
     * @return
     */
    private LoginResponse crearUserDataMock(){
        LoginResponse response = new LoginResponse();
        response.setNombre("Andres Camilo Santacruz Borda");
        response.setTelefono("31330175065");
        response.setDirección("Calle 123");
        response.setEmail("ansantacruz93@gmail.com");
        return response;
    }


    @Override
    public UserDetails  loadUserByUsername(String userName) throws UsernameNotFoundException {
        return new User(userName,"1234@",new ArrayList<>());
    }
}

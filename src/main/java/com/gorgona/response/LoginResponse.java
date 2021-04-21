package com.gorgona.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponse extends Response{

    private String nombre;
    private String email;
    private String telefono;
    private String dirección;
    private String token;

}

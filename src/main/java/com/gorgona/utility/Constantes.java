package com.gorgona.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
public final class Constantes {

    /* Login */
    public static final String CREDENCIALES_INCORRECTAS = "Usuario y/o contraseña incorrectos";
    public static final String CREDENCIALES_CORRECTAS = "Inicio de sesión exitoso";

    /* Empelados */
    public static final String EMPLEADO_NO_ENCONTRADO = "Empleado no encontrado para el id :";

}

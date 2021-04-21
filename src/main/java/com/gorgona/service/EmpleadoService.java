package com.gorgona.service;

import com.gorgona.exception.ResourceNotFoundException;
import com.gorgona.model.Empleado;
import com.gorgona.repository.EmpleadoRepository;
import com.gorgona.utility.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    /**
     * Este metodo es el encargado de retornar todos los empleados
     *
     * @return
     */
    public List<Empleado> getTodosEmpleados() {
        return empleadoRepository.findAll();
    }

    /**
     * Este metodo es el encargado de buscar um empleado por el ID
     *
     * @param empleadoID
     * @return
     * @throws ResourceNotFoundException
     */
    public Empleado getEmpleadoPorID ( Long empleadoID )
    throws ResourceNotFoundException {
        Empleado empleado = empleadoRepository.findById (empleadoID)
                .orElseThrow (( ) -> new ResourceNotFoundException (Constantes.EMPLEADO_NO_ENCONTRADO + empleadoID));
        return empleado;
    }

    /**
     * Este metodo es el encargado de crear un empleado
     *
     * @param empleado
     * @return
     */
    public Empleado crearEmpleado ( Empleado empleado ) {
        return empleadoRepository.save (empleado);
    }

    /**
     * Este metodo es el encargado de modificar un empleado por su ID
     *
     * @param empleadoID
     * @param empleadoDetalle
     * @return
     * @throws ResourceNotFoundException
     */

    public Empleado actualizarEmpleado (
            Long empleadoID ,
            Empleado empleadoDetalle ) throws ResourceNotFoundException {
        Empleado empleado = empleadoRepository.findById (empleadoID)
                .orElseThrow (( ) -> new ResourceNotFoundException (Constantes.EMPLEADO_NO_ENCONTRADO + empleadoID));
        empleado.setEmailId (empleadoDetalle.getEmailId ());
        empleado.setLastName (empleadoDetalle.getLastName ());
        empleado.setFirstName (empleadoDetalle.getFirstName ());
        empleado.setTelefono (empleadoDetalle.getTelefono ());
        empleado.setDireccion (empleadoDetalle.getDireccion ());
        final Empleado empleadoActualizado = empleadoRepository.save (empleado);
        return empleadoActualizado;
    }

    /**
     * Este metodo es el encargado de eliminar un empleado
     *
     * @param empleadoID
     * @return
     * @throws ResourceNotFoundException
     */

    public Map <String, Boolean> eiminarEMpleado (Long empleadoID)
    throws ResourceNotFoundException {
        Empleado empleado = empleadoRepository.findById (empleadoID)
                .orElseThrow (( ) -> new ResourceNotFoundException (Constantes.EMPLEADO_NO_ENCONTRADO + empleadoID));

        empleadoRepository.delete (empleado);
        Map <String, Boolean> response = new HashMap <> ();
        response.put ("deleted" , Boolean.TRUE);
        return response;
    }
}

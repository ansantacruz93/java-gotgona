package com.gorgona.controller;

import com.gorgona.exception.ResourceNotFoundException;
import com.gorgona.model.Empleado;
import com.gorgona.repository.EmpleadoRepository;

import com.gorgona.response.Response;
import com.gorgona.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin (origins = "http://localhost:4200")
@RestController
@RequestMapping ("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository employeeRepository;
    @Autowired
    private EmpleadoService empleadoService;


    /**
     * Este metodo es el encargado de retornar todos los empleados
     *
     * @return
     */
    @GetMapping ("/employees")
    public List <Empleado> getTodosEmpleados ( ) {
        return empleadoService.getTodosEmpleados ();
    }


    /**
     * Este metodo es el encargado de buscar um empleado por el ID
     *
     * @param empleadoID
     * @return
     * @throws ResourceNotFoundException
     */
    @GetMapping ("/employees/{id}")
    public ResponseEntity <Empleado> getEmpleadoPorID (
            @PathVariable (value = "id") Long empleadoID )
    throws ResourceNotFoundException {
        Empleado empleado = new Empleado ();
        try {
            empleado = this.empleadoService.getEmpleadoPorID (empleadoID);
        } catch (ResourceNotFoundException e) {
            Response response = new Response ();
            response.setMensaje (e.getMessage ());
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(empleado, HttpStatus.OK);
    }

    /**
     * Este metodo es el encargado de crear un empleado
     *
     * @param empleado
     * @return
     */
    @PostMapping ("/employees")
    public Empleado crearEmpleado ( @Validated @RequestBody Empleado empleado ) {
        return this.empleadoService.crearEmpleado (empleado);
    }

    /**
     * Este metodo es el encargado de modificar un empleado por su ID
     *
     * @param empleadoID
     * @param empleadoDetalle
     * @return
     * @throws ResourceNotFoundException
     */
    @PutMapping ("/employees/{id}")
    public ResponseEntity <Empleado> actualizarEmpleado (
            @PathVariable (value = "id") Long empleadoID ,
            @Validated @RequestBody Empleado empleadoDetalle ) throws ResourceNotFoundException {
        Empleado empleadoActualizado;
        try {
            empleadoActualizado = this.empleadoService.actualizarEmpleado(empleadoID, empleadoDetalle);
        } catch (ResourceNotFoundException e) {
            Response response = new Response ();
            response.setMensaje (e.getMessage ());
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(empleadoActualizado, HttpStatus.OK);
    }


    /**
     * Este metodo es el encargado de eliminar un empleado
     *
     * @param empleadoID
     * @return
     * @throws ResourceNotFoundException
     */
    @DeleteMapping ("/employees/{id}")
    public ResponseEntity<Map <String, Boolean>>eiminarEMpleado ( @PathVariable (value = "id") Long empleadoID )
    throws ResourceNotFoundException {
        Map <String, Boolean> response = new HashMap <> ();
        try {
            response =  this.empleadoService.eiminarEMpleado(empleadoID);
        } catch (ResourceNotFoundException e) {
            response.put ("deleted" , Boolean.FALSE);
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }
}

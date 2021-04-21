package com.gorgona;

import com.gorgona.model.Empleado;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith (SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void testGetAllEmployees() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity <String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity <String> response = restTemplate.exchange(getRootUrl() + "/employees",
																 HttpMethod.GET, entity, String.class);

		assertNotNull(response.getBody());
	}


	@Test
	public void testGetEmployeeById() {
		Empleado empleado = restTemplate.getForObject(getRootUrl() + "/employees/1", Empleado.class);
		System.out.println(empleado.getFirstName());
		assertNotNull(empleado);
	}

	@Test
	public void testCreateEmployee() {
		Empleado empleado = new Empleado();
		empleado.setEmailId("admin@gmail.com");
		empleado.setFirstName("admin");
		empleado.setLastName("admin");

		ResponseEntity<Empleado> postResponse = restTemplate.postForEntity(getRootUrl() + "/employees", empleado, Empleado.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateEmployee() {
		int id = 1;
		Empleado empleado = restTemplate.getForObject(getRootUrl() + "/employees/" + id, Empleado.class);
		empleado.setFirstName("admin1");
		empleado.setLastName("admin2");
		restTemplate.put(getRootUrl() + "/employees/" + id, empleado);
		Empleado updatedEmployee = restTemplate.getForObject(getRootUrl() + "/employees/" + id, Empleado.class);
		assertNotNull(updatedEmployee);
	}

	@Test
	public void testDeleteEmployee() {
		int id = 2;
		Empleado empleado = restTemplate.getForObject(getRootUrl() + "/employees/" + id, Empleado.class);
		assertNotNull(empleado);

		restTemplate.delete(getRootUrl() + "/employees/" + id);

		try {
			empleado = restTemplate.getForObject(getRootUrl() + "/employees/" + id, Empleado.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}



}

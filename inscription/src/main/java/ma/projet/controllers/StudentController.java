package ma.projet.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ma.projet.entities.Filiere;
import ma.projet.entities.Role;
import ma.projet.entities.Student;
import ma.projet.repository.RoleRepository;
import ma.projet.services.StudentService;
import ma.projet.services.FiliereService;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private FiliereService filiereService;
	
	
	@Autowired
	private RoleRepository roleRepository;

	@GetMapping("")
	public List<Student> getAllStudent() {
		return studentService.findAll();
	}
	
	
	@GetMapping("/filiere")
	public List<Student> getStudentsByFiliere(@RequestBody Filiere filiere) {
		return studentService.findStudentsByFiliere(filiere);
	}
	
//	@GetMapping("/filiere/{id}")
//	public List<Student> getStudentsByFiliere(@PathVariable Long id) {
//	    Filiere filiere = filiereService.findById(id);
//	    return studentService.findStudentsByFiliere(filiere);
//	}

	


	@GetMapping("/{id}")
	public Student getById(@PathVariable Long id) {
		return studentService.findById(id);

	}

	@PostMapping("")
	public Student createStudent(@RequestBody Student student) {
		Role role = roleRepository.findById(student.getRoles().get(0).getId()).get();
		System.out.println(role.getName());
		student.getRoles().add(role);
		return studentService.create(student);
	}

	@PutMapping("/{id}")
	public ResponseEntity updateStudent(@PathVariable Long id, @RequestBody Student student) {

		if (student == null) {
			return new ResponseEntity<Object>("student avec ID " + id + " n exite pas", HttpStatus.BAD_REQUEST);
		} else {
			studentService.update(student);
			return ResponseEntity.ok("UPDATE AVEC SUCCEs");
			// return ResponseEntity.ok("");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id) {
		Student student = studentService.findById(id);
		
		if (student == null) {
			return new ResponseEntity<Object>("student avec ID " + id + " n exite pas", HttpStatus.BAD_REQUEST);
		} else {
			studentService.delete(student);
			return ResponseEntity.ok(" supression avec succes ");

		}
	}
}

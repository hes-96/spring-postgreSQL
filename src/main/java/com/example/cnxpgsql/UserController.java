package com.example.cnxpgsql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository repository;

    @GetMapping
    public List<User> getAllUsers(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id){
        User user = repository.findById(String.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("User with id : " + id + "not found"));
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public Integer save(@RequestBody User user){
        return repository.save(user).getId();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Integer id , @RequestBody User userDetails) {
        User user = repository.findById(String.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("user with id : " + id + "not found"));
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        User updatedUser = repository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        repository.deleteById(String.valueOf(id));
    }
}

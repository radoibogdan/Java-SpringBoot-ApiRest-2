package com.packt.restwebservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDaoService userService;

    // ------------------------------------------ GET ALL ------------------------------------------
    @GetMapping(path = "/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    // ------------------------------------------ GET by ID ------------------------------------------
    @GetMapping(path = "/users/{id}")
    public User getUserById(@PathVariable int id){
        User user = userService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("id " + id);
        }
        return user;
    }

    // ------------------------------------------ GET by ID HATEOAS ------------------------------------------
    @GetMapping(path = "/users/{id}/hateoas")
    public EntityModel<User> getUserByIdHateoas(@PathVariable int id){
        User user = userService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("id " + id);
        }
        // Create model
        EntityModel<User> model = EntityModel.of(user);
        // Get link of method getAllUsers
        Link methodLink = linkTo(methodOn(this.getClass()).getAllUsers()).withRel("all-users");
        model.add(methodLink);
        return model;
    }

    // ------------------------------------------ POST - Create user ------------------------------------------
    @PostMapping(path = "/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.saveUser(user);
        // Send back in Headers Location the new url to get the new user
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userId}")            // set new path with var
                .buildAndExpand(user.getId()) // set var
                .toUri();                     // return URI
        return ResponseEntity.created(uri).build(); // set code 201
    }

    // ------------------------------------------ DELETE by ID ------------------------------------------
    @DeleteMapping(path = "/users/{id}")
    public void deleteUserById(@PathVariable int id){
       User user = userService.deleteById(id);
        if (user == null) {
            throw new UserNotFoundException("id " + id); // 404
        }
    }

}

package com.packt.restwebservice.user;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component // => Managed by spring
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int usersCount =3;

    static {
        users.add(new User(1, "Alex", new Date()));
        users.add(new User(2, "Baba", new Date()));
        users.add(new User(3, "Cris", new Date()));
    }

    public List<User> findAll() {
        return  users;
    }

    public User saveUser(User user) {
        if (user.getId() == null) {
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    public User findOne(int id) {
        for(User user: users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public User deleteById(int id){
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }

}

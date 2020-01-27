package com.epam.app;

import com.epam.app.dao.UserDao;
import com.epam.app.domain.Role;
import com.epam.app.domain.User;
import com.epam.app.injector.ApplicationInjector;

public class App {

    public static void main(String[] args) {
        ApplicationInjector injector = ApplicationInjector.getInstance();
        UserDao dao = injector.getUserDao();
        User build = User.builder()
                .setName("name")
                .setEmail("email")
                .setRole(new Role(1, "CLIENT"))
                .setPassword("password")
                .build();
        User saved = dao.save(build);
        System.out.println(build);
        System.out.println(saved);

        long count = dao.count();
        System.out.println(count);

        dao.update(User.builder(saved).setEmail("new email").build());
    }
}

package com.epam.app;

import com.epam.app.dao.UserDao;
import com.epam.app.entity.Role;
import com.epam.app.entity.User;
import com.epam.app.context.ApplicationInjector;

public class App {

    public static void main(String[] args) {
        ApplicationInjector injector = ApplicationInjector.getInstance();

        // UserDao
        UserDao dao = injector.getUserDao();
        User build = User.builder()
                .setName("new user")
                .setEmail("email")
                .setRole(new Role(1, "CLIENT"))
                .setPassword("password")
                .build();
        User saved = dao.save(build);
        System.out.println(build);
        System.out.println(saved);

        long count = dao.count();
        System.out.println(count);

        saved.setEmail("new email");
        dao.update(saved);
    }
}

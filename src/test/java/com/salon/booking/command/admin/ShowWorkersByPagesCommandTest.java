package com.salon.booking.command.admin;

import com.salon.booking.command.AbstractCommandTest;
import com.salon.booking.domain.User;
import com.salon.booking.domain.page.Page;
import com.salon.booking.domain.page.PageProperties;
import com.salon.booking.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;

import static com.salon.booking.utility.PageUtility.getViewPathByName;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShowWorkersByPagesCommandTest extends AbstractCommandTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private ShowWorkersByPagesCommand command;

    @Test
    public void processGetShouldCallUserService() throws ServletException, IOException {
        when(request.getRequestDispatcher(eq(getViewPathByName("admin/workers")))).thenReturn(requestDispatcher);
        PageProperties properties = new PageProperties(0, 1);
        Page<User> page = new Page<>(Collections.emptyList(), properties, 0);
        when(userService.findAllWorkers(any())).thenReturn(page);

        command.processGet(request, response);

        verify(request).setAttribute(eq("page"), eq(page));
        verify(requestDispatcher).forward(request, response);
    }
}
package com.salon.booking.command.client;

import com.salon.booking.command.AbstractCommandTest;
import com.salon.booking.domain.Notification;
import com.salon.booking.domain.Role;
import com.salon.booking.domain.User;
import com.salon.booking.service.NotificationService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.salon.booking.utility.PageUtility.getViewPathByName;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShowNotificationsCommandTest extends AbstractCommandTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ShowNotificationsCommand command;

    @After
    public void resetMocks() {
        Mockito.reset(notificationService);
    }

    @Test
    public void processGetShouldForwardNotificationsPageAndCounterShouldBeUpdated() throws ServletException, IOException {
        when(request.getRequestDispatcher(eq(getViewPathByName("client/notifications")))).thenReturn(requestDispatcher);
        when(request.getSession(eq(false))).thenReturn(session);
        when(request.getSession()).thenReturn(session);

        User client = User.builder().setId(1).setRole(Role.CLIENT).build();
        when(session.getAttribute(eq("user"))).thenReturn(client);

        List<Notification> readNotifications = Collections.singletonList(new Notification(null, null, true));
        List<Notification> unreadNotifications = Collections.singletonList(new Notification(null, null, false));
        when(notificationService.findAllRead(eq(1))).thenReturn(readNotifications);
        when(notificationService.findAllUnreadAndMarkAllAsRead(eq(1))).thenReturn(unreadNotifications);

        command.processGet(request, response);

        verify(session).removeAttribute(eq("notificationsCounter"));
        verify(request).setAttribute(eq("readNotifications"), eq(readNotifications));
        verify(request).setAttribute(eq("unreadNotifications"), eq(unreadNotifications));
        verify(requestDispatcher).forward(request, response);
    }
}
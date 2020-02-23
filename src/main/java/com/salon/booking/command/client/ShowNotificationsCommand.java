package com.salon.booking.command.client;

import com.salon.booking.command.GetCommand;
import com.salon.booking.domain.Notification;
import com.salon.booking.domain.Role;
import com.salon.booking.domain.User;
import com.salon.booking.service.NotificationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.salon.booking.utility.PageUtility.getViewPathByName;

public class ShowNotificationsCommand implements GetCommand {

    private final NotificationService notificationService;

    public ShowNotificationsCommand(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User client = getUserWithRoleFromSession(request, Role.CLIENT);

        request.getSession().removeAttribute("notificationsCounter");
        
        List<Notification> readNotifications = notificationService.findAllRead(client.getId());
        request.setAttribute("readNotifications", readNotifications);

        List<Notification> unreadNotifications = notificationService.findAllUnreadAndMarkAllAsRead(client.getId());
        request.setAttribute("unreadNotifications", unreadNotifications);

        forward(getViewPathByName("client/notifications"), request, response);
    }
}

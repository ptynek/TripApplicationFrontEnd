package com.tripapplicationf.tripapplicationf.layout.route;

import com.tripapplicationf.tripapplicationf.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/routes", layout = MainView.class)
@PageTitle("All routes || Vaadin")
public class AllRoutes extends VerticalLayout {
    public AllRoutes(){

        add(new Button("Click me", e -> Notification.show("Hello World")));
    }


}

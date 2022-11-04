package com.tripapplicationf.tripapplicationf.layout.city;

import com.tripapplicationf.tripapplicationf.client.CitiesTripApplicationClient;
import com.tripapplicationf.tripapplicationf.domain.CitiesDto;
import com.tripapplicationf.tripapplicationf.layout.basiclayout.BasicLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.awt.*;

@Route(value = "/city/changeStatus", layout = BasicLayout.class)
@PageTitle("Change status of city || Vaadin")
public class ChangeStatus extends VerticalLayout {

    private CitiesTripApplicationClient client;

    public ChangeStatus(CitiesTripApplicationClient client) {
        this.client = client;
        add(
            changeStatusOfCity()
            );
    }

    private VerticalLayout changeStatusOfCity(){
        VerticalLayout verticalLayout = new VerticalLayout();
        ComboBox<CitiesDto> changeStatus = new ComboBox<>("Change status");
        changeStatus.setItems(client.getCitiesDto());
        changeStatus.setItemLabelGenerator(CitiesDto::getCity);

        Button changeStatusButton = new Button(
                "Change",
                event -> {
                    if(!changeStatus.isEmpty()){
                        client.changeStatusOfCity(changeStatus.getValue().getId());
                        UI.getCurrent().navigate(City.class);
                        Notification.show("Status changed");
                    } else {
                        Notification.show("Please, select city",2000 , Notification.Position.MIDDLE);
                    }
                }
                );
        verticalLayout.add(changeStatus, changeStatusButton);

        return verticalLayout;
    }

}

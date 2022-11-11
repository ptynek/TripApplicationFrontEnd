package com.tripapplicationf.tripapplicationf.layout.passengers;

import com.tripapplicationf.tripapplicationf.client.PassengersTripApplicationClient;
import com.tripapplicationf.tripapplicationf.domain.PassengersDto;
import com.tripapplicationf.tripapplicationf.layout.basiclayout.BasicLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "/passengers", layout = BasicLayout.class)
@PageTitle("Passengers || Vaadin")
public class Passengers extends VerticalLayout {

    private PassengersTripApplicationClient client;

    public Passengers(PassengersTripApplicationClient client) {
        this.client = client;
        add(
            menuBar(),
            createPassengersGrid()
        );

    }

    private Grid createPassengersGrid(){

        List<PassengersDto> list = client.getPassengers();
        Grid<PassengersDto> grid = new Grid<>();

        grid.addColumn(PassengersDto::getId)
                .setHeader("ID")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(PassengersDto::getFirstName)
                .setHeader("First name")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(PassengersDto::getLastName)
                .setHeader("Last name")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(PassengersDto::getPhoneNumber)
                .setHeader("Phone number")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(PassengersDto::getMail)
                .setHeader("Mail")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(PassengersDto::isActive)
                .setHeader("Active")
                .setAutoWidth(true)
                .setSortable(true);

        grid.setItems(list);
        grid.setAllRowsVisible(true);

        return grid;
    }

    private MenuBar menuBar() {
        MenuBar menuBar = new MenuBar();
        MenuItem settings = menuBar.addItem("Settings");
        menuBar.setOpenOnHover(true);
        SubMenu subMenu = settings.getSubMenu();
        subMenu.addItem("Change passenger status", event -> changeStatusDialog().open());

        return menuBar;
    }
    private Dialog changeStatusDialog(){
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Change passenger status");

        ComboBox<PassengersDto> passengersComboBox = new ComboBox<>();
        List<PassengersDto> passengersList = client.getPassengers();

        passengersComboBox.setItems(passengersList);
        passengersComboBox.setItemLabelGenerator(PassengersDto::getPassengerForAdminCombobox);
        passengersComboBox.setWidth("250px");

        Button changeStatusButton = new Button(
                "Change status",
                event -> {
                    if(!passengersComboBox.isEmpty()) {
                        client.changePassengerStatus(passengersComboBox.getValue().getId());
                        UI.getCurrent().getPage().reload();
                    } else {
                        Notification.show("Please, select passenger!", 2000, Notification.Position.MIDDLE);
                    }
                }
        );

        Button cancelButton = new Button("Cancel", event -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(changeStatusButton);

        VerticalLayout dialogLayout = new VerticalLayout(passengersComboBox);
        dialog.setWidth("350px");
        dialog.setHeight("215px");
        dialog.add(dialogLayout);


        return dialog;
    }

}

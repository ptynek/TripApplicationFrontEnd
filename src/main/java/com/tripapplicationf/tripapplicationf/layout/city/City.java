package com.tripapplicationf.tripapplicationf.layout.city;

import com.tripapplicationf.tripapplicationf.client.CitiesTripApplicationClient;
import com.tripapplicationf.tripapplicationf.domain.CitiesDto;
import com.tripapplicationf.tripapplicationf.layout.basiclayout.BasicLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.awt.*;
import java.util.List;

@Route(value = "/city", layout = BasicLayout.class)
@PageTitle("Cities || Vaadin")
public class City extends VerticalLayout {

    private CitiesTripApplicationClient client;

    public City(CitiesTripApplicationClient client) {
        this.client = client;
        add(
            menuBar(),
            buildGrid()
        );
    }

    private Grid buildGrid() {

        List<CitiesDto> list = client.getCitiesDto();

        Grid<CitiesDto> grid = new Grid<>();

        grid.addColumn(CitiesDto::getCity).setHeader("City").setAutoWidth(true);
        grid.addColumn(CitiesDto::getCountry).setHeader("Country").setAutoWidth(true);
        grid.addColumn(CitiesDto::getLatitude).setHeader("Latitude").setAutoWidth(true);
        grid.addColumn(CitiesDto::getLongitude).setHeader("Longitude").setAutoWidth(true);
        grid.addColumn(CitiesDto::isActive).setHeader("Status (active)").setAutoWidth(true);

        grid.setItems(list);
        return grid;
    }

    private MenuBar menuBar(){
        MenuBar menuBar = new MenuBar();
        MenuItem settings = menuBar.addItem("Settings");
        menuBar.setOpenOnHover(true);
        SubMenu subMenu = settings.getSubMenu();
        subMenu.addItem("Add city", event -> UI.getCurrent().navigate(AddCity.class));
        subMenu.addItem("Change status", event -> UI.getCurrent().navigate(ChangeStatus.class));
        return menuBar;
    }
}


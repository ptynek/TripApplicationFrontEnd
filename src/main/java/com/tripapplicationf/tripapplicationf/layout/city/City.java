package com.tripapplicationf.tripapplicationf.layout.city;

import com.tripapplicationf.tripapplicationf.MainView;
import com.tripapplicationf.tripapplicationf.client.TripApplicationClient;
import com.tripapplicationf.tripapplicationf.domain.CitiesDto;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@Route(value = "/city", layout = MainView.class)
@PageTitle("Cities || Vaadin")
public class City extends VerticalLayout {

    @Autowired
    private TripApplicationClient client;

    public City() {
        buildGrid();

    }

    private void buildGrid() {

        Grid<CitiesDto> grid = new Grid<>();

        List<CitiesDto> list = client.getCitiesDto();

        grid.addColumn(CitiesDto::getCity).setHeader("City").setAutoWidth(true);
        grid.addColumn(CitiesDto::getCountry).setHeader("Country").setAutoWidth(true);
        grid.addColumn(CitiesDto::getLatitude).setHeader("Latitude").setAutoWidth(true);
        grid.addColumn(CitiesDto::getLongitude).setHeader("Longitude").setAutoWidth(true);

        grid.setItems(list);

        add(grid);
    }


}

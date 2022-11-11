package com.tripapplicationf.tripapplicationf.layout.route;

import com.tripapplicationf.tripapplicationf.client.RouteTripApplicationClient;
import com.tripapplicationf.tripapplicationf.domain.RouteDto;
import com.tripapplicationf.tripapplicationf.layout.basiclayout.BasicLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "/routes", layout = BasicLayout.class)
@PageTitle("All routes || Vaadin")
public class AllRoutes extends VerticalLayout {

    private RouteTripApplicationClient client;

    public AllRoutes(RouteTripApplicationClient client) {
        this.client = client;
        add(createRoutesGrid());
    }

    private Grid createRoutesGrid(){
        List<RouteDto> list = client.getRoutes();
        Grid<RouteDto> grid = new Grid<>();

        grid.addColumn(RouteDto::getCityFrom)
                .setHeader("Departure city")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(RouteDto::getCityTo)
                        .setHeader("Arrival city")
                        .setAutoWidth(true)
                        .setSortable(true);

        grid.addColumn(RouteDto::getLengthInKilometers)
                .setHeader("Length [kms]")
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(RouteDto::getTravelTime)
                        .setHeader("Travel time")
                        .setSortable(true)
                        .setAutoWidth(true);

        grid.addColumn(RouteDto::getDelayTime)
                        .setHeader("Traffic delay")
                        .setSortable(true)
                        .setAutoWidth(true);

        grid.addColumn(RouteDto::getPassenger)
                        .setHeader("Passenger")
                        .setSortable(true)
                        .setAutoWidth(true);
        grid.addColumn(RouteDto::getStringDateOfTrip)
                        .setHeader("Date/time")
                        .setAutoWidth(true)
                        .setSortable(true);

        grid.setItems(list);
        grid.setAllRowsVisible(true);

        return grid;
    }
}

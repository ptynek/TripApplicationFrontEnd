package com.tripapplicationf.tripapplicationf.layout.route;

import com.tripapplicationf.tripapplicationf.MainView;
import com.tripapplicationf.tripapplicationf.layout.basiclayout.BasicLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;

@Route(value = "/trip", layout = BasicLayout.class)
public class Trip extends VerticalLayout {

    public Trip() {

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("padding-top", "20%");
        createSelect();
        result();

    }

    private void result() {

        Span result = new Span("Information for your trip: ");
        Span result1 = new Span("Information for your trip: ");
        Span result2 = new Span("Information for your trip: ");
        Span result3 = new Span("Information for your trip: ");
        Span result4 = new Span("Information for your trip: ");

        add(result, result1, result2, result3, result4);
    }

    public void createSelect(){
        Select<String> selectFrom = new Select<>();

        selectFrom.setLabel("Departure city");
        selectFrom.setItems("Warszawa", "Kraków", "Berlin");

        Select<String> selectTo = new Select<>("Warszawa", "Kraków", "Berlin");
        selectTo.setLabel("Arrival city");
        selectTo.getStyle().set("padding-left", "30px");
        HorizontalLayout horizontalLayout = new HorizontalLayout(selectFrom, selectTo);
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        add(horizontalLayout);
    }
}

package com.tripapplicationf.tripapplicationf;

import com.tripapplicationf.tripapplicationf.layout.city.City;
import com.tripapplicationf.tripapplicationf.layout.passengers.Passengers;
import com.tripapplicationf.tripapplicationf.layout.route.AllRoutes;
import com.tripapplicationf.tripapplicationf.layout.route.Trip;
import com.tripapplicationf.tripapplicationf.layout.weather.CheckWeather;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.Lumo;


@Route
@PageTitle("Trip application || Vaadin")
public class MainView extends AppLayout {

    public MainView(){

        ThemeList themeList = UI.getCurrent().getElement().getThemeList();
        themeList.set(Lumo.DARK, true);

        createHeader();
        createDrawer();
    }

    private void createDrawer() {

        Accordion accordion = new Accordion();

        VerticalLayout adminPanel = new VerticalLayout(
                allRoutesPage(),
                cityPage(),
                passengers()
        );

        addToDrawer(new VerticalLayout(
                mainPage(),
                tripPage(),
                weatherPage(),
                accordion.add("Admin panel", adminPanel)
                )
        );
    }

    private HorizontalLayout cityPage() {
        RouterLink cityLink = new RouterLink("Cities", City.class);
        Icon cityIcon = new Icon(VaadinIcon.BUILDING);
        cityLink.getStyle().set("margin-left", "5px");
        HorizontalLayout cityPage = new HorizontalLayout(cityIcon, cityLink);
        return cityPage;
    }

    private HorizontalLayout allRoutesPage() {
        RouterLink allRoutesLink = new RouterLink("All routes", AllRoutes.class);
        Icon allRouterIcon = new Icon(VaadinIcon.LIST);
        allRouterIcon.getStyle().set("margin-left", "5px");
        HorizontalLayout routesPage =  new HorizontalLayout(allRouterIcon, allRoutesLink);
        return routesPage;
    }

    private HorizontalLayout weatherPage() {
        RouterLink weather = new RouterLink("Weather", CheckWeather.class);
        Icon weatherIcon = new Icon(VaadinIcon.CLOUD_O);
        weatherIcon.getStyle().set("margin-left", "3px");
        HorizontalLayout weatherPage = new HorizontalLayout(weatherIcon, weather);
        return weatherPage;
    }

    private HorizontalLayout tripPage() {
        RouterLink tripLink = new RouterLink("Trip", Trip.class);
        Icon tripIcon = new Icon(VaadinIcon.CAR);
        tripIcon.getStyle().set("margin-left", "3px");
        HorizontalLayout tripPage = new HorizontalLayout(tripIcon, tripLink);
        return tripPage;
    }

    private HorizontalLayout mainPage() {
        RouterLink mainView = new RouterLink("Home page", MainView.class);
        Icon mainViewIcon = new Icon(VaadinIcon.HOME);
        mainViewIcon.getStyle().set("margin-left", "3px");
        HorizontalLayout mainPage =  new HorizontalLayout(mainViewIcon, mainView);
        return mainPage;
    }

    private HorizontalLayout passengers() {
        RouterLink passengersLink = new RouterLink("Passengers", Passengers.class);
        Icon passengersIcon = new Icon(VaadinIcon.GROUP);
        passengersLink.getStyle().set("margin-left", "5px");
        HorizontalLayout passengers = new HorizontalLayout(passengersIcon, passengersLink);
        return passengers;
    }


    private void createHeader() {

        H1 title = new H1("Trip Application");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        DrawerToggle drawer = new DrawerToggle();

        HorizontalLayout header = new HorizontalLayout(drawer, title);
        header.addClassName("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }
}

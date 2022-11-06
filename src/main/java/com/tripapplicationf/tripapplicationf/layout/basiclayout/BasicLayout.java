package com.tripapplicationf.tripapplicationf.layout.basiclayout;


import com.tripapplicationf.tripapplicationf.MainView;
import com.tripapplicationf.tripapplicationf.client.PassengersTripApplicationClient;
import com.tripapplicationf.tripapplicationf.domain.PassengersDto;
import com.tripapplicationf.tripapplicationf.layout.city.City;
import com.tripapplicationf.tripapplicationf.layout.passengers.Passengers;
import com.tripapplicationf.tripapplicationf.layout.route.AllRoutes;
import com.tripapplicationf.tripapplicationf.layout.route.Trip;
import com.tripapplicationf.tripapplicationf.layout.weather.CheckWeather;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.Lumo;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;


public class BasicLayout extends AppLayout {

    private PassengersTripApplicationClient client;
    private PassengersDto passengerDto;

    public PassengersDto getPassengerDto() {
        return passengerDto;
    }

    public void setPassengerDto(PassengersDto passengerDto) {
        this.passengerDto = passengerDto;
    }

    public BasicLayout(PassengersTripApplicationClient client) {
        this.client = client;

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

        HorizontalLayout header = new HorizontalLayout(drawer, title, newPassengerLayout());
        header.addClassName("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }

    //add passsenger sector

    private Dialog addPassenger(){

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Add new passenger");

        VerticalLayout passengerDialogLayout = createNewPassengerDialogLayout();
        dialog.add(passengerDialogLayout);

        Button addPassengerButton = addPassengerButton(dialog);
        Button cancelButton = createCancelButton(dialog);
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(addPassengerButton);
        dialog.setWidth("430px");
        dialog.setHeight("470px");

        return dialog;
    }

    private Button createCancelButton(Dialog dialog) {
        Button cancelButton = new Button(
                "Cancel",
                event -> dialog.close()
        );
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        return cancelButton;
    }

    private Button addPassengerButton(final Dialog dialog) {
        Button addPassengerButton = new Button(
                "Create passenger",
                event -> {
                        client.createPassenger(getPassengerDto());
                        dialog.close();
                        UI.getCurrent().getPage().reload();
                }
                );
        return addPassengerButton;
    }

    private VerticalLayout createNewPassengerDialogLayout() {

        TextField firstName = new TextField("First name");
        TextField lastName = new TextField("Last name");
        TextField phoneNumber = new TextField("Phone number");
        TextField mail = new TextField("e-Mail");

        setPassengerDto(new PassengersDto(
                firstName.addValueChangeListener(event ->
                        getPassengerDto().setFirstName(event.getValue())).toString(),
                lastName.addValueChangeListener(event ->
                        getPassengerDto().setLastName(event.getValue())).toString(),
                phoneNumber.addValueChangeListener(event ->
                        getPassengerDto().setPhoneNumber(event.getValue())).toString(),
                mail.addValueChangeListener(event ->
                        getPassengerDto().setMail(event.getValue())).toString()));

        VerticalLayout passengerDialogLayout = new VerticalLayout(
                firstName,
                lastName,
                phoneNumber,
                mail
        );
        passengerDialogLayout.setPadding(false);
        passengerDialogLayout.setSpacing(false);
        passengerDialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        return passengerDialogLayout;
    }

    private HorizontalLayout newPassengerLayout(){

        Button newPassengerButton = new Button(
                "New passenger",
                event -> addPassenger().open()
                );

        HorizontalLayout newPassengerLayout = new HorizontalLayout(newPassengerButton);
        newPassengerLayout.getStyle().set("position", "absolute").set("right", "1%");
        newPassengerButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        return newPassengerLayout;
    }
}

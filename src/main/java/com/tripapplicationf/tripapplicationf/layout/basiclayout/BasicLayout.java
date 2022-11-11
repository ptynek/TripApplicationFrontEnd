package com.tripapplicationf.tripapplicationf.layout.basiclayout;


import com.tripapplicationf.tripapplicationf.MainView;
import com.tripapplicationf.tripapplicationf.client.PassengersTripApplicationClient;
import com.tripapplicationf.tripapplicationf.domain.PassengersDto;
import com.tripapplicationf.tripapplicationf.layout.city.City;
import com.tripapplicationf.tripapplicationf.layout.passengers.Passengers;
import com.tripapplicationf.tripapplicationf.layout.route.AllRoutes;
import com.tripapplicationf.tripapplicationf.layout.route.Trip;
import com.tripapplicationf.tripapplicationf.layout.weather.CheckWeather;
import com.tripapplicationf.tripapplicationf.layout.weather.WeatherCodes;
import com.tripapplicationf.tripapplicationf.settings.SettingsPage;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.Lumo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BasicLayout extends AppLayout {

    private PassengersTripApplicationClient client;
    private PassengersDto passengerDto;
    private boolean checkValueOfTextFields = false;

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
                passengers(),
                weatherCode(),
                settings()
        );

        addToDrawer(new VerticalLayout(
                        mainPage(),
                        tripPage(),
                        weatherPage(),
                        accordion.add("Admin panel", adminPanel),
                        createSelectPassenger()
                )
        );
    }

    private VerticalLayout createSelectPassenger(){

        ComboBox<PassengersDto> passengersComboBox = createSelectPassengersComboBox();

        VerticalLayout selectPassengerVerticalLayout = new VerticalLayout(passengersComboBox);

        return selectPassengerVerticalLayout;
    }

    private ComboBox<PassengersDto> createSelectPassengersComboBox() {

        List<PassengersDto> passengersList = client.getPassengers().stream()
                .filter(PassengersDto::isActive)
                .collect(Collectors.toList());

        ComboBox<PassengersDto> passengersComboBox = new ComboBox<>();
        passengersComboBox.setItems(passengersList);
        passengersComboBox.setItemLabelGenerator(PassengersDto::getPassengerForCombobox);
        passengersComboBox.setLabel("Select passenger");
        passengersComboBox.setPlaceholder(findPassengerCurrentlyLoggedIn(passengersList).get(0).getPassengerForCombobox());
        passengersComboBox.getStyle().set("position", "absolute").set("bottom", "5%");

        passengersComboBox.addValueChangeListener(
                event -> {
                    client.logInPassenger(event.getValue().getId());
                    UI.getCurrent().getPage().reload();
                }
        );

        return passengersComboBox;
    }

    private List<PassengersDto> findPassengerCurrentlyLoggedIn(List<PassengersDto> passengersList){
        return passengersList.stream()
                .filter(PassengersDto::isLoggedIn)
                .collect(Collectors.toList());
    }

    private HorizontalLayout cityPage() {
        RouterLink cityLink = new RouterLink("Cities", City.class);
        Icon cityIcon = new Icon(VaadinIcon.BUILDING);
        cityLink.getStyle().set("margin-left", "5px");
        return new HorizontalLayout(cityIcon, cityLink);
    }

    private HorizontalLayout allRoutesPage() {
        RouterLink allRoutesLink = new RouterLink("All routes", AllRoutes.class);
        Icon allRouterIcon = new Icon(VaadinIcon.LIST);
        allRouterIcon.getStyle().set("margin-left", "5px");
        return new HorizontalLayout(allRouterIcon, allRoutesLink);
    }

    private HorizontalLayout weatherPage() {
        RouterLink weather = new RouterLink("Weather", CheckWeather.class);
        Icon weatherIcon = new Icon(VaadinIcon.CLOUD_O);
        weatherIcon.getStyle().set("margin-left", "3px");
        return new HorizontalLayout(weatherIcon, weather);
    }

    private HorizontalLayout tripPage() {
        RouterLink tripLink = new RouterLink("Trip", Trip.class);
        Icon tripIcon = new Icon(VaadinIcon.CAR);
        tripIcon.getStyle().set("margin-left", "3px");
        return new HorizontalLayout(tripIcon, tripLink);
    }

    private HorizontalLayout mainPage() {
        RouterLink mainView = new RouterLink("Home page", MainView.class);
        Icon mainViewIcon = new Icon(VaadinIcon.HOME);
        mainViewIcon.getStyle().set("margin-left", "3px");
        return new HorizontalLayout(mainViewIcon, mainView);
    }

    private HorizontalLayout passengers() {
        RouterLink passengersLink = new RouterLink("Passengers", Passengers.class);
        Icon passengersIcon = new Icon(VaadinIcon.GROUP);
        passengersLink.getStyle().set("margin-left", "5px");
        return new HorizontalLayout(passengersIcon, passengersLink);
    }

    private HorizontalLayout weatherCode(){
        RouterLink weatherCodesLink = new RouterLink("Weather code", WeatherCodes.class);
        Icon weatherCodeIcon = new Icon(VaadinIcon.CLOUD);
        weatherCodesLink.getStyle().set("margin-left", "5px");
        return new HorizontalLayout(weatherCodeIcon, weatherCodesLink);
    }

    private HorizontalLayout settings(){
        RouterLink settingsLink = new RouterLink("Settings", SettingsPage.class);
        Icon settingsIcon = new Icon(VaadinIcon.TOOLS);
        settingsLink.getStyle().set("margin-left", "5px");
        return new HorizontalLayout(settingsIcon, settingsLink);
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
                    if(checkValueOfTextFields) {
                        client.createPassenger(getPassengerDto());
                        dialog.close();
                        UI.getCurrent().getPage().reload();
                    } else  {
                        Notification notification = Notification.show("Please fill out all fields!", 2000, Notification.Position.MIDDLE);
                        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    }
                }
                );
        return addPassengerButton;
    }

    private VerticalLayout createNewPassengerDialogLayout() {

        TextField firstName = new TextField("First name");
        TextField lastName = new TextField("Last name");
        TextField phoneNumber = new TextField("Phone number");
        TextField mail = new TextField("e-Mail");

        if (checkValueOfTextFieldsMethod(firstName, lastName, phoneNumber, mail)){
            setPassengerDto(new PassengersDto(
                    firstName.addValueChangeListener(event ->
                            getPassengerDto().setFirstName(event.getValue())).toString(),
                    lastName.addValueChangeListener(event ->
                            getPassengerDto().setLastName(event.getValue())).toString(),
                    phoneNumber.addValueChangeListener(event ->
                            getPassengerDto().setPhoneNumber(event.getValue())).toString(),
                    mail.addValueChangeListener(event ->
                            getPassengerDto().setMail(event.getValue())).toString()));
        }

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

    public boolean checkValueOfTextFieldsMethod(TextField... fields){

        for(TextField field: fields){
            if (field.getValue().equals("") || field.getValue().equals(null)) {
                return false;
            }
        }
        setCheckValueOfTextFields(true);
        return true;
    }
}

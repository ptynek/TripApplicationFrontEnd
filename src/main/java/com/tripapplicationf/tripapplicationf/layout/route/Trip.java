package com.tripapplicationf.tripapplicationf.layout.route;

import com.tripapplicationf.tripapplicationf.client.CitiesTripApplicationClient;
import com.tripapplicationf.tripapplicationf.client.PriceSettingsTripApplicationClient;
import com.tripapplicationf.tripapplicationf.client.RouteTripApplicationClient;
import com.tripapplicationf.tripapplicationf.client.WeatherTripApplicationClient;
import com.tripapplicationf.tripapplicationf.domain.CitiesDto;
import com.tripapplicationf.tripapplicationf.domain.RouteDto;
import com.tripapplicationf.tripapplicationf.layout.basiclayout.BasicLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Route(value = "/trip", layout = BasicLayout.class)
@PageTitle("Trip || Vaadin")
public class Trip extends VerticalLayout {

    private CitiesTripApplicationClient citiesClient;
    private RouteTripApplicationClient routeClient;
    private PriceSettingsTripApplicationClient priceSettingsClient;
    private WeatherTripApplicationClient weatherClient;

    private long departureCityId;
    private long arrivalCityId;
    public LocalDateTime dateTimeOfTrip;

    public Trip(CitiesTripApplicationClient citiesClient, RouteTripApplicationClient routeClient,
                PriceSettingsTripApplicationClient priceSettingsClient, WeatherTripApplicationClient weatherClient) {
        this.citiesClient = citiesClient;
        this.routeClient = routeClient;
        this.priceSettingsClient = priceSettingsClient;
        this.weatherClient = weatherClient;
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("padding-top", "20%");

        add(
            new HorizontalLayout(
                    departureCityComboBox(),
                    arrivalCityComboBox()
            ),
            createDateTimePicker(),
            createCalculateButton()

        );
    }


    private CitiesDto getCity(long cityId){
        CitiesDto city = citiesClient.getCity(cityId);
        return city;
    }

    private RouteDto getTripInformation(){
        RouteDto route = routeClient.calculateRoute(departureCityId, arrivalCityId);

        return route;
    }

    private List<CitiesDto> getAllCities() {
        List<CitiesDto> citiesList = citiesClient.getCitiesDto().stream()
                .filter(CitiesDto::isActive)
                .collect(Collectors.toList());

        return citiesList;
    }

    private ComboBox<CitiesDto> departureCityComboBox() {

        ComboBox<CitiesDto> departureCityComboBox = new ComboBox<>();
        departureCityComboBox.setItems(getAllCities());
        departureCityComboBox.setItemLabelGenerator(CitiesDto::getCity);
        departureCityComboBox.setLabel("Departure City");
        departureCityComboBox.addValueChangeListener(event ->
               setDepartureCityId(event.getValue().getId())
        );

        return departureCityComboBox;

    }

    private ComboBox<CitiesDto> arrivalCityComboBox(){

        ComboBox<CitiesDto> arrivalCityComboBox = new ComboBox<>();
        arrivalCityComboBox.setItems(getAllCities());
        arrivalCityComboBox.setItemLabelGenerator(CitiesDto::getCity);
        arrivalCityComboBox.setLabel("Arrival city");
        arrivalCityComboBox.addValueChangeListener(event -> {
            if(departureCityId != event.getValue().getId()){
                setArrivalCityId(event.getValue().getId());
            } else {
                Notification.show("You cannot select the same city", 3000, Notification.Position.MIDDLE);
            }
        });

        return arrivalCityComboBox;
    }

    private DateTimePicker createDateTimePicker(){
        DateTimePicker dateTimePickerOfTrip = new DateTimePicker();
        dateTimePickerOfTrip.setLabel("Select date and time of your trip");
        dateTimePickerOfTrip.setStep(Duration.ofMinutes(15));
        dateTimePickerOfTrip.setMin(LocalDateTime.now());
        dateTimePickerOfTrip.addValueChangeListener(event ->
                setDateTimeOfTrip(event.getValue()));
        return dateTimePickerOfTrip;
    }

    private Dialog createTripInformationDialog(){
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Trip information");

        VerticalLayout dialogLayout = createTripInformationLayout();
        dialog.add(dialogLayout);

        Button cancelButton = createCancelButton(dialog);
        Button buyTripButton = createBuyTripButton(dialog);
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(buyTripButton);

        dialog.setWidth("350px");
        dialog.setHeight("300px");

        return dialog;
    }

    private VerticalLayout createTripInformationLayout() {

        BigDecimal priceForTrip = priceSettingsClient.getPricePerKm()
                .multiply(getTripInformation().getLengthInKilometers()).setScale(2, RoundingMode.HALF_UP);

        Span citiesSpan = new Span(getCity(departureCityId).getCity() + " - " + getCity(arrivalCityId).getCity());
        citiesSpan.getStyle().set("margin-bottom", "10px");
        Span distance = new Span("Distance: " + getTripInformation().getLengthInKilometers() + " km");
        Span travelTime = new Span("Travel time: " + getTripInformation().getTravelTime());
        Span delayTime = new Span("Traffic delay: " + getTripInformation().getDelayTime());
        Span price = new Span("Price: " +  priceForTrip + " zł");

        VerticalLayout dialogLayout = new VerticalLayout(
                citiesSpan,
                distance,
                travelTime,
                delayTime,
                price
        );
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(Alignment.STRETCH);

        return dialogLayout;
    }

    private Button createCancelButton(Dialog dialog) {
        Button cancelButton = new Button(
                "Cancel",
                event -> dialog.close()
        );
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        return cancelButton;
    }

    private Button createBuyTripButton(Dialog dialog) {

        Button buyTripButton = new Button(
                "Buy trip",
                event -> {
                    routeClient.postRoute(departureCityId, arrivalCityId, dateTimeOfTrip);
                    dialog.close();
                    UI.getCurrent().getPage().reload();
                }
        );

        return buyTripButton;
    }


    private Button createCalculateButton(){
        Button calculateButton = new Button(
                "Calculate",
                event -> {
                    if (departureCityId != arrivalCityId && dateTimeOfTrip != null) {
                        createTripInformationDialog().open();
                    } else {
                        Notification notification = Notification.show(
                                "Please fill out all fields!",
                                2000,
                                Notification.Position.MIDDLE
                        );
                        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

                    }
                }
        );

        return calculateButton;
    }




}

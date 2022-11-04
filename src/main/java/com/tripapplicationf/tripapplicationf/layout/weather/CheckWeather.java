package com.tripapplicationf.tripapplicationf.layout.weather;

import com.tripapplicationf.tripapplicationf.MainView;
import com.tripapplicationf.tripapplicationf.client.CitiesTripApplicationClient;
import com.tripapplicationf.tripapplicationf.client.WeatherTripApplicationClient;
import com.tripapplicationf.tripapplicationf.domain.CitiesDto;
import com.tripapplicationf.tripapplicationf.domain.WeatherDto;
import com.tripapplicationf.tripapplicationf.layout.basiclayout.BasicLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "/weather", layout = BasicLayout.class)
@PageTitle("Check weather || Vaadin")
public class CheckWeather extends VerticalLayout {

    private CitiesTripApplicationClient client;
    private WeatherTripApplicationClient weatherClient;

    public CheckWeather(CitiesTripApplicationClient client, WeatherTripApplicationClient weatherClient) {
        this.client = client;
        this.weatherClient = weatherClient;

        setAlignItems(Alignment.CENTER);

        add(
                createSelect()
        );
    }

    public ComboBox<CitiesDto> createSelect() {
        List<CitiesDto> citiesList = client.getCitiesDto();
        ComboBox<CitiesDto> selectCity = new ComboBox<>();
        selectCity.setLabel("Select city");
        selectCity.setItems(citiesList);
        selectCity.setItemLabelGenerator(CitiesDto::getCity);

        add(createCheckButton());

        return selectCity;
    }

    private static Button createCheckButton() {
            Button checkButton = new Button("Check weather",
                    event -> createDialog().open());

        return checkButton;
    }

     private static Dialog createDialog() {

        VerticalLayout dialogLayout = new VerticalLayout();

        Span temp = new Span("Temperature: " );
        Span windSpeed = new Span("Windspeed: ");
        Span weatherDescription = new Span("Weather: ");

        Dialog dialog = new Dialog(new VerticalLayout(
                 temp,
                 windSpeed,
                 weatherDescription
        ));
        dialog.setHeaderTitle("Weather in ");

        Button closeButton = new Button("Close", event -> dialog.close());

        dialogLayout.add(
                dialog,
                closeButton
        );

        return dialog;
     }

}

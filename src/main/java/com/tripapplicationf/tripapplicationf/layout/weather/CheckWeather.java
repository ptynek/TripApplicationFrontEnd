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
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Route(value = "/weather", layout = BasicLayout.class)
@PageTitle("Check weather || Vaadin")
public class CheckWeather extends VerticalLayout {

    private CitiesTripApplicationClient client;
    private WeatherTripApplicationClient weatherClient;
    private CitiesDto cityDto;

    public CheckWeather(CitiesTripApplicationClient client, WeatherTripApplicationClient weatherClient) {
        this.client = client;
        this.weatherClient = weatherClient;

        setAlignItems(Alignment.CENTER);

        add(citiesComboBox(), getCityWeather());

    }

    private ComboBox<CitiesDto> citiesComboBox(){

        ComboBox<CitiesDto> citiesComboBox = new ComboBox<>();
        citiesComboBox.setItems(client.getCitiesDto());
        citiesComboBox.setItemLabelGenerator(CitiesDto::getCity);
        citiesComboBox.setLabel("Select city");
        citiesComboBox.addValueChangeListener(event ->
                setCityDto(client.getCity(citiesComboBox.getValue().getId())));

        return citiesComboBox;
    }

    private Dialog resultDialog(){
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Weather in " + getCityDto().getCity());

        VerticalLayout dialogLayout = dialogLayout(getWeather(getCityDto().getId()));
        dialog.add(dialogLayout);

        Button okButton = createOkButton(dialog);
        dialog.getFooter().add(okButton);
        dialog.setWidth("350px");
        return dialog;
    }

    private Button createOkButton(Dialog dialog) {
        Button okButton = new Button(
                "Close",
                event -> {
                    dialog.close();
                });
        return okButton;
    }

    private WeatherDto getWeather(final long cityId){
        WeatherDto weather = weatherClient.checkWeatherInChosenCity(cityId);
        return weather;
    }

    private VerticalLayout dialogLayout(final WeatherDto weatherDto){

        Span temp = new Span("Temperature: " + weatherDto.getTemperature());
        Span windSpeed = new Span("Wind speed: " + weatherDto.getWindspeed());
        Span weather = new Span(weatherDto.getWeatherCodeDto().getDescription());

        VerticalLayout dialogLayout = new VerticalLayout(temp, windSpeed, weather);

        return dialogLayout;
    }

    private Button getCityWeather(){
        Button getWeather = new Button(
                "Get weather",
                event -> {
                    resultDialog().open();
                }
                );
        return getWeather;
    }


}

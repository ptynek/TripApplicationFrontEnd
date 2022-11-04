package com.tripapplicationf.tripapplicationf;

import com.tripapplicationf.tripapplicationf.client.WeatherTripApplicationClient;
import com.tripapplicationf.tripapplicationf.domain.WeatherCodeDto;
import com.tripapplicationf.tripapplicationf.domain.WeatherDto;
import com.tripapplicationf.tripapplicationf.layout.basiclayout.BasicLayout;
import com.vaadin.flow.component.accordion.Accordion;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;


@Route(layout = BasicLayout.class)
@PageTitle("Trip application || Vaadin")
public class MainView extends VerticalLayout {

    private WeatherTripApplicationClient weatherTripApplicationClient;

    public MainView(WeatherTripApplicationClient weatherTripApplicationClient){
        this.weatherTripApplicationClient = weatherTripApplicationClient;
        welcome();
        aboutCreator();

    }

    private void welcome(){
        Span hello = new Span("Hello on My Trip application!");
        Span currentWeather = new Span("Current weather for Warsaw - Poland is: ");
        VerticalLayout welcome = new VerticalLayout();
        welcome.add(hello, currentWeather, weatherInCityBase());
        welcome.setAlignItems(Alignment.CENTER);
        add(welcome);
    }

    private Div weatherInCityBase(){
        WeatherDto weather = weatherTripApplicationClient.getWeatherInCityBase(52.23074, 20.9659);
        WeatherCodeDto weatherCodeDto = weather.getWeatherCodeDto();

        Span temp = new Span("Temperature: " + weather.getTemperature() + " \u2103");
        Span windSpeed = new Span("Wind speed: " + weather.getWindspeed() + " km/h");
        Span weatherDescription = new Span("Weather: " + weatherCodeDto.getDescription().toLowerCase());

        Div div = new Div(
                new VerticalLayout(temp, windSpeed, weatherDescription)
        );

        return div;
    }

    private void aboutCreator(){
        Accordion accordion = new Accordion();

        Span name = new Span("Piotr Tynek");
        Span email = new Span("tynek.piotr@gmail.com");

        VerticalLayout verticalLayout = new VerticalLayout(name, email);
        verticalLayout.setPadding(false);
        verticalLayout.setSpacing(false);

        accordion.add("About creator", verticalLayout);
        accordion.close();
        accordion.getStyle().set("position", "absolute").set("top", "85%").set("right", "1%");
        add(accordion);

    }


}

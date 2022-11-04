package com.tripapplicationf.tripapplicationf.layout.city;

import com.tripapplicationf.tripapplicationf.client.CitiesTripApplicationClient;
import com.tripapplicationf.tripapplicationf.domain.CitiesDto;
import com.tripapplicationf.tripapplicationf.layout.basiclayout.BasicLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;

import java.text.Normalizer;
import java.util.Collection;

@Route(value = "/city/addCity", layout = BasicLayout.class)
@PageTitle("Add city || Vaadin")
public class AddCity extends VerticalLayout {

    private CitiesTripApplicationClient client;

    public AddCity(CitiesTripApplicationClient client) {
        this.client = client;
        add(
            formLayout()
        );
    }


    private VerticalLayout formLayout() {
        TextField city = new TextField("City");
        TextField country = new TextField("Country");
        NumberField latitude = new NumberField("Latitude");
        NumberField longitude = new NumberField("Longitude");

        city.setClearButtonVisible(true);
        country.setClearButtonVisible(true);
        latitude.setClearButtonVisible(true);
        longitude.setClearButtonVisible(true);

        VerticalLayout verticalLayout = new VerticalLayout();
        FormLayout formLayout = new FormLayout();
        formLayout.add(city, country, latitude, longitude);
        formLayout.setColspan(city, 3);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("500px", 3)
        );

        Button addButton =  new Button(
                "Add city",
                event -> addProcess(
                        new CitiesDto(
                                city.getValue(),
                                country.getValue(),
                                latitude.getValue(),
                                longitude.getValue()
                        )
                )
        );

        Button clearButton = new Button(
                "Clear",
                event -> {
                    city.clear();
                    country.clear();
                    latitude.clear();
                    longitude.clear();
                }
        );
        clearButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        verticalLayout.add(
                formLayout,
                new HorizontalLayout(addButton, clearButton));

        return verticalLayout;
    }


    private void addProcess(final CitiesDto citiesDto){
        client.addCity(citiesDto);
        UI.getCurrent().getPage().reload();
    }


}

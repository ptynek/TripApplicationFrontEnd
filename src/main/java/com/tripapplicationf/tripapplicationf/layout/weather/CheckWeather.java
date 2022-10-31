package com.tripapplicationf.tripapplicationf.layout.weather;

import com.tripapplicationf.tripapplicationf.MainView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/weather", layout = MainView.class)
@PageTitle("Check weather || Vaadin")
public class CheckWeather extends VerticalLayout {

    public CheckWeather() {

        Select<String> selectCity = new Select<>("Warszawa", "Kraków", "Berlin");
        selectCity.setLabel("Select city");
        VerticalLayout verticalLayout = new VerticalLayout(selectCity);
        add(verticalLayout);
    }
}

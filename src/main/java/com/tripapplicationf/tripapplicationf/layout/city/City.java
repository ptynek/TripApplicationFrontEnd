package com.tripapplicationf.tripapplicationf.layout.city;

import com.tripapplicationf.tripapplicationf.client.CitiesTripApplicationClient;
import com.tripapplicationf.tripapplicationf.domain.CitiesDto;
import com.tripapplicationf.tripapplicationf.layout.basiclayout.BasicLayout;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Route(value = "/city", layout = BasicLayout.class)
@PageTitle("Cities || Vaadin")
public class City extends VerticalLayout {

    private CitiesTripApplicationClient client;
    private long cityId;
    private String cityName;
    private String country;
    private double latitude;
    private double longitude;

    public City(CitiesTripApplicationClient client) {
        this.client = client;
        add(
            menuBar(),
            buildGrid()
        );
    }

    private Grid buildGrid() {

        List<CitiesDto> list = client.getCitiesDto();

        Grid<CitiesDto> grid = new Grid<>();

        grid.addColumn(CitiesDto::getCity).setHeader("City").setAutoWidth(true).setSortable(true);
        grid.addColumn(CitiesDto::getCountry).setHeader("Country").setAutoWidth(true).setSortable(true);
        grid.addColumn(CitiesDto::getLatitude).setHeader("Latitude").setAutoWidth(true).setSortable(true);
        grid.addColumn(CitiesDto::getLongitude).setHeader("Longitude").setAutoWidth(true).setSortable(true);
        grid.addColumn(CitiesDto::isActive).setHeader("Status (active)").setAutoWidth(true).setSortable(true);

        grid.setItems(list);
        return grid;
    }

    private MenuBar menuBar(){

        MenuBar menuBar = new MenuBar();
        MenuItem settings = menuBar.addItem("Settings");
        menuBar.setOpenOnHover(true);
        SubMenu subMenu = settings.getSubMenu();
        subMenu.addItem("Add city", event -> addCityDialog().open());
        subMenu.addItem("Change status", event -> changeStatusOfCityDialog().open());

        return menuBar;
    }

    // add city sector
    private Dialog addCityDialog(){

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Add city");

        VerticalLayout dialogLayout = createAddCityDialogLayout();
        dialog.add(dialogLayout);

        Button addCityButton = addCityButton(dialog);
        Button cancelButton = createCancelButton(dialog);
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(addCityButton);

        dialog.setWidth("430px");
        dialog.setHeight("380px");

        return dialog;
    }

    private VerticalLayout createAddCityDialogLayout(){

        TextField city = new TextField("City");
        TextField country = new TextField("Country");
        NumberField latitudeNf = new NumberField("Latitude");
        NumberField longitudeNf = new NumberField("Longitude");
        latitudeNf.setPlaceholder("xx.xxxxx");
        longitudeNf.setPlaceholder("xx.xxxxx");
        longitudeNf.getStyle().set("position", "absolute").set("right", "6%");

        city.addValueChangeListener(event -> setCityName(event.getValue()));
        country.addValueChangeListener(event -> setCountry(event.getValue()));
        latitudeNf.addValueChangeListener(event -> setLatitude(event.getValue()));
        longitudeNf.addValueChangeListener(event -> setLongitude(event.getValue()));

        VerticalLayout dialogLayout = new VerticalLayout(city, country, new HorizontalLayout(latitudeNf, longitudeNf));
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(Alignment.STRETCH);

        return dialogLayout;
    }

    private Button addCityButton(final Dialog dialog){

        Button addCityButton = new Button(
                "Add city",
                event -> {client.addCity(
                        new CitiesDto(
                                cityName,
                                country,
                                latitude,
                                longitude
                        )
                );
                    dialog.close();
                    UI.getCurrent().getPage().reload();
                }
        );

        return addCityButton;
    }


    //Change city status sector
    private Dialog changeStatusOfCityDialog(){

        Dialog dialog = new Dialog();
        VerticalLayout dialogLayout = createStatusChangeDialogLayout();
        dialog.add(dialogLayout);

        Button changeStatusButton = changeStatusButton();
        Button cancelButton = createCancelButton(dialog);
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(changeStatusButton);

        return dialog;
    }

    private VerticalLayout createStatusChangeDialogLayout(){

        ComboBox<CitiesDto> changeStatusCombo = new ComboBox<>("Select city");
        changeStatusCombo.setItems(client.getCitiesDto());
        changeStatusCombo.setItemLabelGenerator(CitiesDto::getCity);

        changeStatusCombo.addValueChangeListener(event ->
                   setCityId(event.getValue().getId())

        );

        VerticalLayout dialogLayout = createDialogLayout(changeStatusCombo);

        return dialogLayout;
    }

    private VerticalLayout createDialogLayout(final ComboBox<CitiesDto> citiesComboBox){

        VerticalLayout dialogLayout = new VerticalLayout(citiesComboBox);
        dialogLayout.setSpacing(false);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(Alignment.STRETCH);

        return dialogLayout;
    }

    private Button changeStatusButton(){

        Button changeStatusButton = new Button(
                "Change status",
                event -> {
                    if(getCityId() != 0) {
                        client.changeStatusOfCity(getCityId());
                        UI.getCurrent().getPage().reload();
                    } else {
                        Notification.show("Please select city", 2000, Notification.Position.MIDDLE);
                    }
                }
        );

        return changeStatusButton;
    }

    // end of change city status sector

    private Button createCancelButton(final Dialog dialog){

        Button cancelButton = new Button(
                "Cancel",
                event -> dialog.close()
        );
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        return cancelButton;
    }

}


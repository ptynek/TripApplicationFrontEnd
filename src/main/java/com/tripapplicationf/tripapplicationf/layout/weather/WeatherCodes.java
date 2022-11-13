package com.tripapplicationf.tripapplicationf.layout.weather;

import com.tripapplicationf.tripapplicationf.client.WeatherCodesTripApplicationClient;
import com.tripapplicationf.tripapplicationf.domain.WeatherCodeDto;
import com.tripapplicationf.tripapplicationf.layout.basiclayout.BasicLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.Data;

import java.util.List;

@Data
@Route(value = "/weather_code", layout = BasicLayout.class)
@PageTitle("Weather codes || Vaadin")
public class WeatherCodes extends VerticalLayout {

    private WeatherCodesTripApplicationClient client;
    private WeatherCodeDto weatherCodeDto;
    private boolean fieldsValidator = false;

    public WeatherCodes(WeatherCodesTripApplicationClient client) {
        this.client = client;
        add(
                createMenuBar(),
                buildWeathercodesGrid()
        );
    }

    private MenuBar createMenuBar(){
        MenuBar menuBar = new MenuBar();

        menuBar.addItem(
                "Add weather code",
                event -> addWeatherCodeDialog().open()
        );

        return menuBar;
    }

    private Grid buildWeathercodesGrid(){
        Grid<WeatherCodeDto> grid = new Grid<>();
        List<WeatherCodeDto> list = client.getWeatherCodes();

        grid.addColumn(WeatherCodeDto::getWeatherCode)
                .setHeader("Weather code")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(WeatherCodeDto::getDescription)
                .setHeader("Description")
                .setSortable(true)
                .setAutoWidth(true);

        grid.setItems(list);
        grid.setAllRowsVisible(true);

        return grid;
    }

    private Dialog addWeatherCodeDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Add weather code");

        VerticalLayout dialogLayout = createAddWeatherCodeDialogLayout();
        dialog.add(dialogLayout);

        Button addWeatherCodeButton = addWeatherCodeButton(dialog);
        Button cancelButton = createCancelButton(dialog);
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(addWeatherCodeButton);

        dialog.setWidth("350px");

        return dialog;

    }

    private Button createCancelButton(Dialog dialog) {

        Button cancelButton = new Button(
                "Cancel",
                event -> {
                    dialog.close();
                }
        );
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        return cancelButton;
    }

    private Button addWeatherCodeButton(Dialog dialog) {

        Button addWeatherCodeButton = new Button(
               "Add weather code",
               event -> {
                   if(fieldsValidator) {
                       client.addWeatherCode(getWeatherCodeDto());
                       dialog.close();
                       UI.getCurrent().getPage().reload();
                   } else {
                       Notification notification = Notification.show("Please fill out all fields", 2000, Notification.Position.MIDDLE);
                       notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                   }
               }
       );

        return addWeatherCodeButton;
    }

    private VerticalLayout createAddWeatherCodeDialogLayout() {

        IntegerField weatherCodeIF = new IntegerField("Weather code");
        TextArea descriptionTA = new TextArea("Description");

        if(valueValidationFields(weatherCodeIF, descriptionTA)){
            setWeatherCodeDto(new WeatherCodeDto(
                    weatherCodeIF.addValueChangeListener(event ->
                            getWeatherCodeDto().setWeatherCode(event.getValue())),
                    descriptionTA.addValueChangeListener(event ->
                            getWeatherCodeDto().setDescription(event.getValue())).toString()
            ));
        }

        VerticalLayout dialogLayout = new VerticalLayout(weatherCodeIF, descriptionTA);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(Alignment.STRETCH);

        return dialogLayout;
    }

    private boolean valueValidationFields(IntegerField weatherCodeIf, TextArea descriptionTA){
        if (weatherCodeIf.isEmpty() || descriptionTA.isEmpty()){
            return false;
        } else {
            return true;
        }
    }

}

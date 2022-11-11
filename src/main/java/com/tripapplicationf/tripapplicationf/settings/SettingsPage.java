package com.tripapplicationf.tripapplicationf.settings;

import com.tripapplicationf.tripapplicationf.client.PriceSettingsTripApplicationClient;
import com.tripapplicationf.tripapplicationf.layout.basiclayout.BasicLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Route(value = "/settings", layout = BasicLayout.class)
@PageTitle("Settings || Vaadin")
@Setter
@Getter
public class SettingsPage extends VerticalLayout {

    private PriceSettingsTripApplicationClient client;
    private BigDecimal price;

    public SettingsPage(PriceSettingsTripApplicationClient client) {
        this.client = client;
        add(pricePerKilometer(), setPrice());
    }

    private BigDecimalField pricePerKilometer(){
        BigDecimalField priceNf = new BigDecimalField();
        priceNf.setLabel("Price per kilometer");
        priceNf.setWidth("200px");
        priceNf.setPlaceholder(client.getPricePerKm().toString());
        Div zlotyPrefix = new Div();
        zlotyPrefix.setText(" zł");
        priceNf.setSuffixComponent(zlotyPrefix);
        priceNf.addThemeVariants(TextFieldVariant.LUMO_SMALL);

        priceNf.addValueChangeListener(event -> setPrice(event.getValue())
        );
        return priceNf;
    }

    private Button setPrice(){
        return new Button(
                "Set price",
                event -> {
                    if(price == null || price.equals(new BigDecimal(0))) {
                        Notification notification = Notification.show(
                                "Please enter price",
                                2000,
                                Notification.Position.MIDDLE);
                        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    } else {
                        client.postPriceSettings(price);
                        UI.getCurrent().getPage().reload();
                    }
                }
        );

    }
}
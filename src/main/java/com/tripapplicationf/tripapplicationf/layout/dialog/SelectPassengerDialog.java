package com.tripapplicationf.tripapplicationf.layout.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;

public class SelectPassengerDialog extends Div {

    public SelectPassengerDialog() {
        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("Select passenger");

        VerticalLayout dialogLayout = createDialogLayout();
        dialog.add(dialogLayout);

        Button saveButton = createSaveButton(dialog);
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);

        Button button = new Button("Select passenger", e -> dialog.open());

        add(dialog, button);

        dialog.close();

        button.getStyle().set("position", "relative").set("top", "0").set("right", "0")
                .set("bottom", "10px").set("left", "0").set("display", "flex")
                .set("align-items", "end").set("justify-content", "center");

        dialogLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.END, button);
    }

    private static VerticalLayout createDialogLayout() {

        Select <String> select = new Select<>();
        select.setLabel("Select passenger");
        select.setItems("John Smith", "Jak Nowak", "Kamil Kowalski");
        VerticalLayout dialogLayout = new VerticalLayout(select);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }

    private static Button createSaveButton(Dialog dialog) {
        Button saveButton = new Button("Select", e -> dialog.close());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return saveButton;
    }
}

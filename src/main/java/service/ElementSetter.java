package service;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class ElementSetter {

    // Set Text Field
    public void setTextField(
            TextField settableTF,
            int xSize,
            int ySize,
            int xLayout,
            int yLayout) {

        settableTF.setPrefSize(xSize, ySize);
        settableTF.setLayoutX(xLayout);
        settableTF.setLayoutY(yLayout);
    }

    // Set Text Label
    public void setTextLabel(
            Label settableLabel,
            int xLayout,
            int yLayout) {

        settableLabel.setLayoutX(xLayout);
        settableLabel.setLayoutY(yLayout);
    }

    // Set Button
    public void setButton(
            Button settableB,
            int xSize,
            int ySize,
            int xLayout,
            int yLayout) {

        settableB.setPrefSize(xSize, ySize);
        settableB.setLayoutX(xLayout);
        settableB.setLayoutY(yLayout);
    }

    // Set ComboBox
    public void setComboBox(
            ComboBox<String> settableBox,
            int xSize,
            int ySize,
            int xLayout,
            int yLayout) {

        settableBox.setPrefSize(xSize, ySize);
        settableBox.setLayoutX(xLayout);
        settableBox.setLayoutY(yLayout);
    }

    // Set ImageView
    public void setImageView(
            ImageView imageView,
            int xLayout,
            int yLayout) {

        imageView.setLayoutX(xLayout);
        imageView.setLayoutY(yLayout);
    }
}

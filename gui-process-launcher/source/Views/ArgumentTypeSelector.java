package Views;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Models.Argument.Type;
import Utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** Controller class for the argument type selector */
public class ArgumentTypeSelector implements Initializable {

    // region Attributes

    /** URL for the fxml file representing a the output selector window */
    private URL outputSelectorURL;

    /** The anchor pane, used to retreive the Stage */
    @FXML
    public AnchorPane anchorPane;

    /** Group containing elements to choose an output */
    @FXML
    public Group outputGroup;

    // endregion

    // region Methods

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        outputSelectorURL = getClass().getResource("/fxml/outputSelector.fxml");

        if (Business.Command.getCommandReiceivingArgument() <= 1) {
            outputGroup.setVisible(false);
            outputGroup.setMouseTransparent(true);
        }
    }

    /**
     * Prompt the user for the file to use
     * 
     * @param event
     */
    @FXML
    public void file(MouseEvent event) {
        File file = Utils.getFc().showOpenDialog(new Stage());

        if (file != null) {
            Business.Argument.setAddedArg(new Models.Argument(Type.FILE, file));
        }

        ((Stage) anchorPane.getScene().getWindow()).close();
    }

    /**
     * Prompt the user to enter the desired text value
     * 
     * @param event
     */
    @FXML
    public void text(MouseEvent event) {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Text value");
        dialog.setHeaderText("Please enter your value:");
        dialog.setGraphic(null);
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(text -> {
            Business.Argument.setAddedArg(new Models.Argument(Type.TEXT, text));
        });

        ((Stage) anchorPane.getScene().getWindow()).close();
    }

    /**
     * Prompt the user to choose the desired output
     * 
     * @param event
     * @throws IOException
     */
    @FXML
    public void output(MouseEvent event) throws IOException {
        // Show output selector window
        Parent root = FXMLLoader.load(outputSelectorURL);
        Stage popupStage = new Stage();
        popupStage.setTitle("Choose output");
        // freeze main window
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(root));
        popupStage.showAndWait();
        ((Stage) anchorPane.getScene().getWindow()).close();
    }

    // endregion

}

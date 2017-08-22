package manimage.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import manimage.common.DBInterface;
import manimage.common.ImgInfo;
import manimage.common.OrderBy;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;

public class Main extends Application {

    static final FileFilter IMAGE_FILTER = file -> {
        String name = file.getName().toLowerCase();
        return name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif");
    };
    static final FileChooser.ExtensionFilter EXTENSION_FILTER = new FileChooser.ExtensionFilter("Image Files (*.png, *.jpg, *.jpeg, *.gif)", "*.png", "*.jpg", "*.jpeg", "*.gif");

    static boolean isIDE = false;

    //TODO: Redesign this GUI entirely
    /*Things to take into account:
        Batched editing
        Quick previews
        Complex searches
        Limited space
        Modular pieces
            Duplicate checking
            Comic reading
            Mass editor
            Slideshow
        Other forms of media?
            Video
            Text
        Inline editors?
        CSS Styling?
        Pages
    */

    @Override
    public void start(Stage mainStage) throws Exception {

        //------------ Build main stage --------------------------------------------------------------------------------

        FXMLLoader loader;
        if (isIDE) loader = new FXMLLoader(getClass().getResource("../fxml/application.fxml"));
        else loader = new FXMLLoader(new URL("file:" + new File("fxml/application.fxml").getAbsolutePath()));
        Parent mainRoot = loader.load();
        mainStage.setTitle("ManImage");
        mainStage.setScene(new Scene(mainRoot, 1600, 900));
        mainStage.show();

        MainController mainController = loader.getController();
        mainController.setStage(mainStage);
        mainController.grid.updateSearchContents();

        //-------------- Build SingleEditor stage ----------------------------------------------------------------------

        Stage singleEditorStage = new Stage();
        Parent singleEditorRoot;
        if (isIDE) singleEditorRoot = FXMLLoader.load(getClass().getResource("../fxml/singleeditor.fxml"));
        else singleEditorRoot = FXMLLoader.load(new URL("file:" + new File("fxml/singleeditor.fxml").getAbsolutePath()));
        singleEditorStage.setScene(new Scene(singleEditorRoot));
        singleEditorStage.setTitle("Edit Image");
    }

    public static void main(String[] args) throws MalformedURLException {
        for (String arg : args) {
            if (arg.equalsIgnoreCase("ide")) {
                isIDE = true;
                break;
            }
        }

        launch(args);
//        System.exit(0);
    }

}

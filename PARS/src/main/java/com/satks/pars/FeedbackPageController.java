package com.satks.pars;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

public class FeedbackPageController implements Initializable {

    @FXML
    private WebView webView;

    private static WebEngine webEngine;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*
        try {
            java.awt.Desktop.getDesktop().browse(new URI("www.google.com"));
        } catch (URISyntaxException ex) {
            Logger.getLogger(FeedbackPageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FeedbackPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
        webEngine = webView.getEngine();
        webEngine.load("file://D:/code/GITHUB_Projects/PARS/PARS/src/main/resources/com/satks/pars/FeedbackPage.html");
    }

}

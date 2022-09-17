package com.satks.pars;

import java.net.URL;
import java.util.ResourceBundle;
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
        webEngine = webView.getEngine();
        webEngine.load("file://D:/code/GITHUB_Projects/PARS/PARS/src/main/resources/com/satks/pars/FeedbackPage.html");
    }

}

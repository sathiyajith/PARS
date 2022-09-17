
package com.satks.pars;

import com.satks.pars.Prometheus.AlertManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class ValidatePageController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(AlertManager.json);
    }    
    
}

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FiveChess extends Application {

    @Override
    public void start(Stage app) throws Exception {
        Parent ui = FXMLLoader.load(getClass().getResource("view/FiveChessBoardUI.fxml"));
        app.setTitle("FiveChess");
        app.setScene(new Scene(ui, 800, 660));
        app.show();

    }
}

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group(); // корневой элемент сцены
        stage.setTitle("Main");
        stage.setScene(new Scene(root, 1000, 700, Color.BLACK));
        stage.setResizable(false); // запрещаем изменять размер окна

        // цвета окружностей
        RadialGradient[] colors = {
                // градиент синего
                new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                        new Stop(0, new Color(0, 0, 1, 1)),
                        new Stop(0.3, new Color(0, 0, 1, 0.9)),
                        new Stop(0.6, new Color(0, 0, 1, 0.8)),
                        new Stop(1, new Color(0, 0, 1, 0.7))),

                // градиент красного
                new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                        new Stop(0, new Color(1, 0, 0, 1)),
                        new Stop(0.3, new Color(1, 0, 0, 0.9)),
                        new Stop(0.6, new Color(1, 0, 0, 0.8)),
                        new Stop(1, new Color(1, 0, 0, 0.7))),

                // градиент зеленого
                new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                        new Stop(0, new Color(0, 1, 0, 1)),
                        new Stop(0.3, new Color(0, 1, 0, 0.9)),
                        new Stop(0.6, new Color(0, 1, 0, 0.8)),
                        new Stop(1, new Color(0, 1, 0, 0.7))),

        };

        Group circles = new Group(); // группа для кругов
        // режим "сложение"
        circles.setBlendMode(BlendMode.ADD);

        PathTransition[] ways = new PathTransition[3]; // массив перемещений
        for (int i = 0; i < 3; i++) {
            Circle circle = new Circle(200, colors[i]); // создаем круг
            circles.getChildren().add(circle); // добавляем его в группу
            circle.setBlendMode(BlendMode.ADD); // режим "сложение"

            PathTransition way = new PathTransition(); // создаем анимацию
            way.setNode(circle); // добавляем в нее круг
            way.setDuration(Duration.millis(5000));// устанавливаем продолжительность движения
            way.setPath(new Path(
                    new MoveTo(400, 350),
                    new LineTo(600, 350), // дорога направо
                    new LineTo(400, 350) // дорога налево
            ));
            way.setCycleCount(Animation.INDEFINITE); // перемещение выполняется бесконечно

            ways[i] = way; // сохраняем перемещение
        }

        root.getChildren().add(circles); // добавляем круги в корневой элемент
        stage.show(); // запускаем сцену

        // перематываем перемещения на 0, 1/3, 2/3, чтобы центры кругов изначально находились в разных позициях
        for (int i = 0; i < 3; i++) {
            ways[i].jumpTo(ways[i].getDuration().multiply(i / 3.));
        }

        for (int i = 0; i < 3; i++) {
            ways[i].play(); // запускаем анимацию
        }
    }
}
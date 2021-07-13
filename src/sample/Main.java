package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Main extends Application {
    private static final int w = 600;
    private static final int h = 600;
    private final double speed = 2;

    ImageView image;
    ColorAdjust color = new ColorAdjust();

    @Override
    public void start(Stage primaryStage) {
        Pane canvas = new Pane();

        primaryStage.setTitle("DVD");
        primaryStage.setScene(new Scene(canvas, w, h, Color.BLACK));
        primaryStage.show();

        Random randomInt = new Random();
        image = new ImageView(new Image("DVD_Logo.png"));

        canvas.getChildren().add(image);

        //Rescaling
        Bounds imgBound = image.getBoundsInLocal(); //the bounds of Node in it's own coordinate system
        int scaledWidth = (int) (imgBound.getWidth() * 0.1);
        int scaledHeight = (int) (imgBound.getHeight() * 0.1);
        image.setFitWidth(scaledWidth);
        image.setFitHeight(scaledHeight);

        //Random image relocate
        image.relocate(randomInt.nextInt(w), randomInt.nextInt(h));

        //Change colors
        randomColor();

        //Animation
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<>() {

            // Logo speed
            double dx = speed; // Step on x
            double dy = speed; // Step on y

            @Override
            public void handle(ActionEvent t) {
                // Moving Logo
                image.setLayoutX(image.getLayoutX() + dx);
                image.setLayoutY(image.getLayoutY() + dy);

                Bounds bounds = canvas.getBoundsInLocal();
                Bounds imgBounds = image.getBoundsInLocal();

                //Window Bounds
                //WE
                if (image.getLayoutX() + imgBounds.getWidth() >= bounds.getWidth() || image.getLayoutX() <= 0) {
                    dx = -dx;
                    randomColor();
                }
                //NS
                if (image.getLayoutY() + imgBounds.getHeight() >= bounds.getHeight() || image.getLayoutY() <= 0) {
                    dy = -dy;
                    randomColor();
                }
            }
        }));

        //Start animation
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Escape-Key to stop
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, t -> {
            if (t.getCode() == KeyCode.ESCAPE) primaryStage.close();
        });

    }

        public void randomColor () {
            Random random = new Random();
            color.setContrast(0);
            color.setHue(random.nextDouble() * 2 - 1);
            color.setBrightness(0);
            color.setSaturation(1);
            image.setEffect(color);
        }

        public static void main (String[]args){
            launch(args);
        }
    }


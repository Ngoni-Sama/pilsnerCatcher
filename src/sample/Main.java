package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.net.www.content.audio.wav;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private static final String beerURL = "file:src/beer.png";
    private static final String friesURL = "file:src/fries.png";
    private static final String heartURL = "file:src/heart.png";
    private static final String level1URL = "file:src/level1.png";
    private static final String level2URL = "file:src/level2.png";
    private static final String level3URL = "file:src/level3.png";
    private static final String level4URL = "file:src/level4.png";
    private static final String level5URL = "file:src/level5.png";
    private static final String backdropURL = "file:src/backdrop.png";


    private static final ArrayList<Rectangle> heartsArray = new ArrayList<Rectangle>();
    private static final ArrayList<Image> levelImageArray = new ArrayList<Image>();
    int levelTracker = 0;

        AnimationTimer timer;
        Pane root = new Pane();
        List drop = new ArrayList();
        double mouseX;
        Rectangle cont;
        double speed = 0.5;
        double falling = 1250;
        Label lblMissed;
        int missed;
        int score = 0;

        public static void main(String[] args) {
            launch();

        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            Image level1 = new Image(level1URL);
            Image level2 = new Image(level2URL);
            Image level3 = new Image(level3URL);
            Image level4 = new Image(level4URL);
            Image level5 = new Image(level5URL);

            levelImageArray.add(level1);
            levelImageArray.add(level2);
            levelImageArray.add(level3);
            levelImageArray.add(level4);
            levelImageArray.add(level5);

            Rectangle backdrop = new Rectangle();
            backdrop.setWidth(800);
            backdrop.setHeight(600);
            Image backdropImage = new Image(backdropURL);
            backdrop.setFill(new ImagePattern(backdropImage, 0, 0, 1, 1, true));
            root.getChildren().addAll(backdrop);

            lblMissed = new Label("Missed: 0");
            lblMissed.setLayoutX(10);
            lblMissed.setLayoutY(10);
            missed = 0;

            Image heart = new Image(heartURL);
            Rectangle heart1 = new Rectangle();
            heart1.setLayoutX(5);
            heart1.setLayoutY(5);
            heart1.setHeight(20);
            heart1.setWidth(20);

            Rectangle heart2 = new Rectangle();
            heart2.setLayoutX(30);
            heart2.setLayoutY(5);
            heart2.setHeight(20);
            heart2.setWidth(20);

            Rectangle heart3 = new Rectangle();
            heart3.setLayoutX(55);
            heart3.setLayoutY(5);
            heart3.setHeight(20);
            heart3.setWidth(20);

            heart1.setFill(new ImagePattern(heart, 0, 0, 1, 1, true));
            heart2.setFill(new ImagePattern(heart, 0, 0, 1, 1, true));
            heart3.setFill(new ImagePattern(heart, 0, 0, 1, 1, true));

            Rectangle levelImage = new Rectangle();
            levelImage.setLayoutX(449);
            levelImage.setLayoutY(22);
            levelImage.setHeight(554);
            levelImage.setWidth(327);
            levelImage.setFill(new ImagePattern(levelImageArray.get(0), 0, 0, 1, 1, true));

            root.getChildren().addAll(heart1, heart2, heart3, levelImage);
            heartsArray.add(heart1);
            heartsArray.add(heart2);
            heartsArray.add(heart3);

            System.out.println(heartsArray.size());



            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(falling), event -> {

                //speed += falling / 9000;
                drop.add(beer());
                root.getChildren().add(((Node)drop.get(drop.size() -1)));
            }));

            timeline.setCycleCount(1000);
            timeline.play();

            timer = new AnimationTimer() {
                @Override
                public void handle(long arg0) {
                    gameUpdate();
                }

            };
            timer.start();

            cont = rectangle();

            root.getChildren().addAll(cont);

            Scene scene = new Scene(root, 800, 600);

            scene.setOnMouseMoved(e -> {
                mouseX = e.getX();
            });

            primaryStage.setScene(scene);
            primaryStage.show();

        }

        public void levelTrack() {

            if (score > 19) {
                score = 0;
                levelTracker++;
                System.out.println("Level: " + levelTracker);
                Rectangle levelImageHolder = new Rectangle();
                levelImageHolder.setLayoutX(449);
                levelImageHolder.setLayoutY(22);
                levelImageHolder.setHeight(554);
                levelImageHolder.setWidth(327);
                levelImageHolder.setFill(new ImagePattern(levelImageArray.get(levelTracker), 0, 0, 1, 1, true));
                root.getChildren().addAll(levelImageHolder);
                this.playSound("yay");
                speed = speed * 1.25;
                falling = falling * 1.25;

            }
        }



    public static synchronized void playSound(String url) {
       try{

           String gongFile = "src/" + url + ".wav";
           InputStream in = new FileInputStream(gongFile);

           // create an audiostream from the inputstream
           AudioStream audioStream = new AudioStream(in);

           // play the audio clip with the audioplayer class
           AudioPlayer.player.start(audioStream);

       } catch (Exception e) {
           e.printStackTrace();
       }

    }

        public Rectangle beer() {
            Rectangle beerRectangle = new Rectangle();
            beerRectangle.setLayoutX(rand(15, 367));
            beerRectangle.setLayoutY(1);
            beerRectangle.setHeight(50);
            beerRectangle.setWidth(50);
            Image beer = new Image(beerURL);
            beerRectangle.setFill(new ImagePattern(beer, 0, 0, 1, 1, true));
            return beerRectangle;
        }

        public Rectangle rectangle() {
            Rectangle rectangle = new Rectangle();
            rectangle.setLayoutX(200);
            rectangle.setLayoutY(530);
            rectangle.setHeight(70);
            rectangle.setWidth(70);
            Image fries = new Image(friesURL);
            rectangle.setFill(new ImagePattern(fries, 0, 0, 1, 1, true));

            return rectangle;

        }

        public int rand(int min, int max) {
            return (int)(Math.random() * max + min);
        }
        public void gameUpdate(){

            cont.setLayoutX(mouseX);


            for(int i = 0; i < drop.size(); i++) {
                ((Rectangle) drop.get(i)).setLayoutY(((Rectangle) drop.get(i)).getLayoutY() + speed + ((Rectangle) drop.get(i)).getLayoutY() / 150 );
                //if get droped into square
                if((((Rectangle) drop.get(i)).getLayoutX() > cont.getLayoutX() && ((Rectangle) drop.get(i)).getLayoutX() < cont.getLayoutX() + 70) &&
                        ((Rectangle) drop.get(i)).getLayoutY() >= 525  ) {
                    root.getChildren().remove(((Rectangle) drop.get(i)));
                    drop.remove(i);
                    score ++;
                    this.levelTrack();
                    this.playSound("cheer");
                    System.out.println("Score: " + score);
                }

                //if missed remove
                else if(((Rectangle) drop.get(i)).getLayoutY() >= 590) {
                    root.getChildren().remove(((Rectangle) drop.get(i)));
                    drop.remove(i);
                    missed += 1;
                    root.getChildren().removeAll(heartsArray.get(heartsArray.size()-1));
                    heartsArray.remove(heartsArray.size()-1);
                    this.playSound("shatter");
                    if(heartsArray.size() == 0) {
                        Platform.exit();
                    }

                }
            }
        }

    }

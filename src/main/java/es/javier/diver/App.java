package es.javier.diver;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * JavaFX App
 */
public class App extends Application {
    final short SCENE_HEIGHT = 480;
    final short SCENE_WIDTH = 640;
    int posicionFondo = 0;
    int posicionFondo2 = SCENE_WIDTH;
    int posicionbuzo = 50;
    byte buzodireccion = 0;
    short stickHeight = 50;        
    short stickPosX = (short)((SCENE_HEIGHT-stickHeight)/2);
    byte velocidadbuzo =0 ;
            
    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        var scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        
        //imagen fondo 1
        Image image2 = new Image(getClass().getResourceAsStream("/images/ocean.gif"));
        ImageView imageView2 = new ImageView(image2);
        imageView2.setX(posicionFondo); // POSICION HORIZONTALMENTE 
        imageView2.setY(0); // POSICION DE LA FOTO VERTICALMETE
        imageView2.setFitHeight(SCENE_HEIGHT);
        imageView2.setFitWidth(SCENE_WIDTH);
        root.getChildren().add(imageView2);
      
        //imagen fondo 2
        Image image3 = new Image(getClass().getResourceAsStream("/images/ocean2.gif"));
        ImageView imageView3 = new ImageView(image3);
        imageView3.setX(posicionFondo2);  
        imageView3.setY(0);
        imageView3.setFitHeight(SCENE_HEIGHT);
        imageView3.setFitWidth(SCENE_WIDTH);
        root.getChildren().add(imageView3);
        
        //imagen buzo
        Image image1 = new Image(getClass().getResourceAsStream("/images/diver.png"));
        ImageView imageView1 = new ImageView(image1);
        imageView1.setX(posicionbuzo); // posicion del buzo horizontal
        imageView1.setY(300); // posicion del buzo verticalmente
        root.getChildren().add(imageView1);
        imageView1.setFitHeight(80);
        imageView1.setFitWidth(100);  
        
        // CONTROL DEL TECLADO DEL BUZO
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
                switch(keyEvent.getCode()) {
                    case LEFT:
                        buzodireccion = -1;
                        break;
                    case RIGHT:
                        buzodireccion = 1;
                        break;
                }                
            }
        });
       
        Timeline timeline;
        timeline = new Timeline(
                // 0.017 ~= 60 FPS
                new KeyFrame(Duration.seconds(0.017), new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent ae) {
                        posicionFondo --;
                        posicionFondo2--;
                        
                        imageView2.setX(posicionFondo);
                        imageView3.setX(posicionFondo2);
                        
                            
                        if(posicionFondo2 == 0){
                            posicionFondo = 0;
                            posicionFondo2 = SCENE_WIDTH;
                            imageView2.setX(posicionFondo);
                            imageView2.setX(posicionFondo2);
                            
                            //ANIMACIÓN DEL BUZO
                            imageView1.setX(stickPosX);
                            stickPosX += velocidadbuzo * buzodireccion;
                            if(stickPosX <= 0 || stickPosX >= SCENE_HEIGHT-stickHeight) {
                                buzodireccion = 0;
                            }
                            if(stickPosX <= 0) {
                                buzodireccion = 0;
                                stickPosX = 0;
                            } else if (stickPosX >= SCENE_HEIGHT-stickHeight) {
                                buzodireccion = 0;
                                stickPosX = (short)(SCENE_HEIGHT-stickHeight);
                            }
                            
                            
                        }
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
                    
    public static void main(String[] args) {
        launch();
    }

}
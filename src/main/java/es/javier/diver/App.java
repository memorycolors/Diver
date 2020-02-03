package es.javier.diver;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    int posicionbuzo = 0;
    short buzoHeight = 0;        
    short buzoPosY = (short)((SCENE_HEIGHT-buzoHeight)/2);
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
        ImageView imageOceano1 = new ImageView(image2);
        imageOceano1.setX(posicionFondo); // POSICION HORIZONTALMENTE 
        imageOceano1.setY(0); // POSICION DE LA FOTO VERTICALMETE
        imageOceano1.setFitHeight(SCENE_HEIGHT);
        imageOceano1.setFitWidth(SCENE_WIDTH);
        root.getChildren().add(imageOceano1);
        
        
        
        //imagen fondo 2
        Image image3 = new Image(getClass().getResourceAsStream("/images/ocean2.gif"));
        ImageView imageOceano2 = new ImageView(image3);
        imageOceano2.setX(posicionFondo2);  
        imageOceano2.setY(0);
        imageOceano2.setFitHeight(SCENE_HEIGHT);
        imageOceano2.setFitWidth(SCENE_WIDTH);
        root.getChildren().add(imageOceano2);
        
        //imagen buzo
        Image image1 = new Image(getClass().getResourceAsStream("/images/diver.png"));
        ImageView imageBuzo = new ImageView(image1);
        imageBuzo.setX(posicionbuzo); // posicion del buzo horizontal
        imageBuzo.setY(posicionbuzo); // posicion del buzo verticalmente
        root.getChildren().add(imageBuzo);
        imageBuzo.setFitHeight(80);
        imageBuzo.setFitWidth(100);  
        
        
        // CONTROL DEL TECLADO DEL BUZO
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
                switch(keyEvent.getCode()) {
                    case UP:
                        posicionbuzo -=1;
                        imageBuzo.setY(posicionbuzo);
                        break;
                    
                    case DOWN:
                        posicionbuzo  +=1;
                        imageBuzo.setY(posicionbuzo);
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
                        
                        imageOceano1.setX(posicionFondo);
                        imageOceano2.setX(posicionFondo2);
                        
                            
                        if(posicionFondo2 == 0){
                            posicionFondo = 0;
                            posicionFondo2 = SCENE_WIDTH;
                            imageOceano1.setX(posicionFondo);
                            imageOceano2.setX(posicionFondo2);
                            
                   // ANIMACIÓN DEL BUZO
                    imageBuzo.setY(buzoPosY);
                    buzoPosY += velocidadbuzo * posicionbuzo;
                    if(buzoPosY <= 0 || buzoPosY >= SCENE_HEIGHT-buzoHeight) {
                        posicionbuzo = 0;
                    }
                    if(buzoPosY <= 0) {
                        posicionbuzo = 0;
                        buzoPosY = 0;
                        
                    } else if (buzoPosY >= SCENE_HEIGHT-buzoHeight) {
                        posicionbuzo = 0;
                        buzoPosY = (short)(SCENE_HEIGHT-buzoHeight);
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
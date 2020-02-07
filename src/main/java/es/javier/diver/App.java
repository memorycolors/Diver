package es.javier.diver;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
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
    int tiburones = 0;
    int direcciontiburon =1;
    int velocidadtiburon = 0;
            
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
        
        //Rectangulo tiburon
        Rectangle rectangletiburon = new Rectangle();
            rectangletiburon.setX(420);
            rectangletiburon.setY(365);
            rectangletiburon.setWidth(130);
            rectangletiburon.setHeight(60);
        root.getChildren().add(rectangletiburon);      
        
        //imagen tiburon 
        
        Image image4 = new Image(getClass().getResourceAsStream("/images/tiburon.png"));
        ImageView imagetiburon = new ImageView(image4);
        imagetiburon.setX(420); 
        imagetiburon.setY(365);
        imagetiburon.setFitHeight(60);
        imagetiburon.setFitWidth(130);
        root.getChildren().add(imagetiburon);
        
        //rectangulo para el buzo
        
        Rectangle rectangle = new Rectangle();
            rectangle.setX(posicionbuzo);
            rectangle.setY(posicionbuzo);
            rectangle.setWidth(100);
            rectangle.setHeight(80);
        root.getChildren().add(rectangle);
        
       //imagen buzo
       
        Image image1 = new Image(getClass().getResourceAsStream("/images/diver.png"));
        ImageView imageBuzo = new ImageView(image1);
        imageBuzo.setX(posicionbuzo); // posicion del buzo horizontal
        imageBuzo.setY(posicionbuzo); // posicion del buzo verticalmente
        root.getChildren().add(imageBuzo);
        imageBuzo.setFitHeight(80);
        imageBuzo.setFitWidth(100);
        
        //grupo de personaje 
        Group grupopersonaje = new Group();
        grupopersonaje.getChildren().add(rectangle);
        grupopersonaje.getChildren().add(imageBuzo);
        root.getChildren().add(grupopersonaje);  
        
        //grupo de tiburon
        Group grupotiburon = new Group();
        grupotiburon.getChildren().add(rectangletiburon);
        grupotiburon.getChildren().add(imagetiburon);
        root.getChildren().add(grupotiburon); 
        
        
        // CONTROL DEL TECLADO DEL BUZO
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
                switch(keyEvent.getCode()) {
                    
                    case UP:
                        if(grupopersonaje.getLayoutY() > 0){
                         // buzoPosY = 0;
                        
                            posicionbuzo -=10;
                            grupopersonaje.setLayoutY(posicionbuzo);
                        }                             
                        break;
                    
                    case DOWN:
                        if((imageBuzo.getY()+imageBuzo.getFitHeight()) < 480){
                            // System.out.println(imageBuzo.getY());
                            posicionbuzo  +=10;
                            grupopersonaje.setLayoutY(posicionbuzo);
                        }
                        break;
                }                
            }
        });
       
        Timeline timeline;
        timeline = new Timeline(
                // 0.017 ~= 60 FPS
                new KeyFrame(Duration.seconds(0.017), new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent ae) {
                        
                       //animacion fondo de oceano   
                        posicionFondo --;
                        posicionFondo2--;
                        
                        imageOceano1.setX(posicionFondo);
                        imageOceano2.setX(posicionFondo2);
                        
                            
                        if(posicionFondo2 == 0){
                            posicionFondo = 0;
                            posicionFondo2 = SCENE_WIDTH;
                            imageOceano1.setX(posicionFondo);
                            imageOceano2.setX(posicionFondo2);
                                              
                        }
                    //animacion tiburones 
                     tiburones --  ;
                     grupotiburon.setLayoutX(tiburones);
                        velocidadtiburon -=3;
                     grupotiburon.setLayoutX(velocidadtiburon);
                     
                     // colision de buzo y tiburones 
                    Shape shapeCollision = Shape.intersect(rectangle, rectangletiburon);
                    boolean colisionVacia = shapeCollision.getBoundsInLocal().isEmpty();
                    if(colisionVacia == false && direcciontiburon == 1) {
                        direcciontiburon = -1;
                        //score++;
                       // textScore.setText(String.valueOf(score));
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
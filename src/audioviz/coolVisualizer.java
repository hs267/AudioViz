/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.*;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.effect.MotionBlur;
import java.awt.geom.GeneralPath; 
import javafx.scene.image.Image;
import java.io.InputStream;
import javafx.scene.paint.ImagePattern;
/**
 *
 * @author thehanemperor
 */
public class coolVisualizer implements Visualizer{
    private final String name = "Little Princess";
    private AnchorPane pane;
    private Integer numBands = 0;
    
    private Double height;
    private Double width;
    //URL catURL =getClass().getResource("/audioviz/cat.png");
     InputStream catURL=getClass().getResourceAsStream("/audioviz/cat.png");
    
    private Double bandWidth = 0.0;
    private Double bandHeight = 0.0;
    private Double halfBandHeight = 0.0;
      //GeneralPath line= new GeneralPath( ) ;
    Arc[] cylins;
    
    
    private Canvas canvas = null;
    private Canvas newcanvas;

    
    private GraphicsContext shape = null;
    private GraphicsContext newline;
   
        @Override
        public String getName()
        {
            return this.name;
        }

    @Override
    public void start(Integer numBands, AnchorPane vizPane)
    {
        this.numBands = numBands;
        this.pane = vizPane;
        
        //Image cat= new Image(catURL);
        width = vizPane.getWidth();
        height = vizPane.getHeight();
        bandWidth = width / numBands;
        halfBandHeight = bandHeight / 2;
        
       
       
 
        //ensure no children currently inhabit the pane
        //this.pane.getChildren().clear();
       
        this.canvas = new Canvas(this.width, this.height);
        this.newcanvas= new Canvas(this.width,this.height);
        
        this.shape = canvas.getGraphicsContext2D();
        this.newline= newcanvas.getGraphicsContext2D();
        
        this.pane.getChildren().add(canvas);
        this.pane.getChildren().add(newcanvas);
                
        this.shape.setStroke(Color.LIME);     
        this.shape.setLineWidth(3);
        
        
        //this.line.setStroke(Color.WHITE);
        //vizPane.getChildren().add(line);
       
        
        //arc code
                cylins= new Arc[numBands];
                 for(int i=0;i<numBands;i++)
                 {
                     Arc cylin= new Arc();
                   cylin.setCenterX(bandWidth / 2 + bandWidth * i);
                   cylin.setCenterY(height/2);
                    cylin.setRadiusX(bandWidth/2);
                 cylin.setRadiusY(bandWidth/2);
                   cylin.setFill(Color.HOTPINK);
                   cylin.setStartAngle(45.0f);
                    cylin.setLength(270.0f);
                   cylin.setType(ArcType.ROUND);
                   vizPane.getChildren().add(cylin);
                     cylins[i]=cylin;
                 }
        //arc code ends
        
        
        
        
    }

            @Override
            public void end()
            {

                     canvas = null;
                this.pane.getChildren().clear();


            }
            


    @Override
    public void update(double timestamp, double duration, float[] magnitudes, float[] phases)
    {
        
        if (magnitudes.length < this.numBands)
        {
            this.numBands = magnitudes.length;
        }
        
        
        this.shape.setFill(Color.BLACK);
        this.shape.fillRect(0, 0, this.width, this.height);
        
        
        
        
        this.shape.beginPath();
        this.newline.beginPath();
                    MotionBlur mb = new MotionBlur();
                    mb.setRadius(10.0);
                    mb.setAngle(-300.0);
                    this.shape.setEffect(mb); 
                    
                    
        
        Double sety= this.height/2;
        Double times= this.height/100;
        Double width= this.width/this.numBands;
        int i;
        Double w = this.width / (this.numBands - 1);
        for(i=0;i<=this.numBands/2;i++)
        {
            
           cylins[i].setRadiusY(((60.0 + magnitudes[i])/60.0)*height/2);
         
           //this.shape.moveTo(this.height/2,((60.0 + magnitudes[i])/60.0)*height/2 );
           this.shape.lineTo(w*i, this.height/2-((magnitudes[i] + 60)*this.height / 100));
           //this.line.lineTo(w*i, 2*(this.height/2-((magnitudes[i] + 60)*this.height / 100)));
        }
        for(i=this.numBands-1;i>this.numBands/2;i--)
        {
            cylins[i].setRadiusY(((60.0 + magnitudes[i])/60.0)*height/2);
        
           //this.shape.moveTo(this.height/2,((60.0 + magnitudes[i])/60.0)*height/2 );
           this.shape.lineTo(w*i, this.height/2-((magnitudes[i] + 60)*this.height / 100));
          // this.newline.lineTo(w*i, 2*(this.height/2-((magnitudes[i] + 60)*this.height / 100)));
        }
      
        this.newline.stroke();
        this.shape.stroke();
        this.shape.closePath();

    }
}

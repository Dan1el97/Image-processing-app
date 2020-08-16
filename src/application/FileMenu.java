package application;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Label;  
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;  
public class FileMenu {
	static ImageView myImageView;
	Label filePath;
	static String imagePath;
	
	 static File file = null;
	/*
	 * openFile METHOD - Open a image file from a directory chosen by the user
	 */
	public static void openFile(Stage primaryStage)  {
		 myImageView = new ImageView(); 
		 FileChooser fileChooser = new FileChooser();
         //Set extension filter
         FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
         FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
         FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.BMP");
         fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG, extFilterBMP);
           
         //Show open file dialog
         file = fileChooser.showOpenDialog(null);
         BufferedImage bufferedImage; 
         String path = null;
         if(file != null) {
        	 try {
        		 path = file.getAbsolutePath();
        		 bufferedImage = ImageIO.read(file);
        		 Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        		 myImageView.setImage(image);
        		 myImageView.setFitHeight(500);
        		 myImageView.setFitWidth(800);
        	 } 
        	 catch (IOException ex) {
        		 System.out.println(ex);
        	 }
          }
        
         Pane vBox = new VBox();
         vBox.setLayoutX(100);
         vBox.setLayoutY(100);
         vBox.getChildren().addAll(myImageView);
	     MainClass.layout.getChildren().add(vBox);
	     imagePath = path;
	     primaryStage.setScene(MainClass.scene);
	     primaryStage.show();
	}
	/*
	 * SaveAs MEHTOD - Saves the new picture(with the changes) in a directory chosen by the user
	 */
	public static void saveAs(BufferedImage image, Stage stage) {  
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter
				("JPG files (*.jpg)", "*.jpg");
		FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter
				("PNG files (*.png)", "*.png");
        FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter
        		("BMP files (*.bmp)", "*.bmp");
        if(MainClass.fileFormat.getText().equals("")) {
        	if(ImageProcessing.imagePath.substring(ImageProcessing.imagePath.indexOf(".")+1,
        			ImageProcessing.imagePath.length()).equals("jpg")) {
        		fileChooser.getExtensionFilters().addAll(extFilterJPG);
        	}
        	else if(ImageProcessing.imagePath.substring(ImageProcessing.imagePath.indexOf(".")+1,
        			ImageProcessing.imagePath.length()).equals("png")) {
        		fileChooser.getExtensionFilters().addAll(extFilterPNG);
        	}
        	if(ImageProcessing.imagePath.substring(ImageProcessing.imagePath.indexOf(".")+1,
        			ImageProcessing.imagePath.length()).equals("bmp")) {
        		fileChooser.getExtensionFilters().addAll(extFilterBMP);
        	}
        }
        else {
        	if(MainClass.fileFormat.getText().equals("jpg")) {
        		fileChooser.getExtensionFilters().addAll(extFilterJPG);
        	}
        	else if(MainClass.fileFormat.getText().equals("png")){
        		fileChooser.getExtensionFilters().addAll(extFilterPNG);
        	}
        	else if(MainClass.fileFormat.getText().equals("bmp")){
        		fileChooser.getExtensionFilters().addAll(extFilterBMP);
        	}
        }
        
		try
		{   
			File output_file = fileChooser.showSaveDialog(stage);
			if(MainClass.fileFormat.getText().equals("jpg")) {
				ImageIO.write(image, "jpg", output_file);
        	}
        	else if(MainClass.fileFormat.getText().equals("png")) {
        		ImageIO.write(image, "png", output_file);
        	}
        	else if(MainClass.fileFormat.getText().equals("bmp")){
        		ImageIO.write(image, "bmp", output_file);
        	}
        	else if(MainClass.fileFormat.getText().equals("")) {
        		if(ImageProcessing.imagePath.substring(ImageProcessing.imagePath.indexOf(".")+1,
        				ImageProcessing.imagePath.length()).equals("jpg")) {
        			System.out.println((ImageProcessing.imagePath.substring(ImageProcessing.imagePath.indexOf(".")+1,
            				ImageProcessing.imagePath.length())));
        			ImageIO.write(image, "jpg", output_file);
        		}
        		else if(ImageProcessing.imagePath.equals("png")) {
        			ImageIO.write(image, "png", output_file);
        		}
        		else if(ImageProcessing.imagePath.contentEquals("bmp")) {
        			ImageIO.write(image, "bmp", output_file);
        		}
        	}
		} 
		catch(IOException e) 
		{ 
			//MainClass.ErrorWindow("Image Path Empty", "Input Image" , 2);
		} 
	}
}

package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ConvertImage {
	/*
	 * convertImage - Creates a new image in a new directory with different format(extension).
	 * The old image is not deleted
	 */
	public static void convertImage(String imagePath, Stage stage) {
		int imageWidth = 800;    
		int imageHeight = 500;   
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter
				("JPG files (*.jpg)", "*.jpg");
		FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter
				("PNG files (*.png)", "*.png");
        FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter
        		("BMP files (*.bmp)", "*.bmp");
        if(MainClass.fileFormat.getText().equals("jpg")) {
        	fileChooser.getExtensionFilters().addAll(extFilterJPG);
        }
        else if(MainClass.fileFormat.getText().equals("png")) {
        	fileChooser.getExtensionFilters().addAll(extFilterPNG);
        }
        else if(MainClass.fileFormat.getText().equals("bmp")){
        	fileChooser.getExtensionFilters().addAll(extFilterBMP);
        }
		
		// For storing image in RAM 
		BufferedImage image = null; 
		// READ IMAGE 
		try
		{ 
			File inputFile = new File(imagePath); //image file path
			image = new BufferedImage(imageWidth, imageHeight, 
                               BufferedImage.TYPE_INT_ARGB); 
			
			// Reading input file 
			image = ImageIO.read(inputFile); 
		} 
		catch(IOException e) 
		{ 
			System.out.println("Error: "+e); 
		} 
		
		// WRITE IMAGE
		try
		{   
			File outputFile = fileChooser.showSaveDialog(stage);
			if(MainClass.fileFormat.getText().equals("jpg")) {
				ImageIO.write(image, "jpg", outputFile);
        	}
        	else if(MainClass.fileFormat.getText().equals("png")) {
        		ImageIO.write(image, "png", outputFile);
        	}
        	else if(MainClass.fileFormat.getText().equals("bmp")){
        		ImageIO.write(image, "bmp", outputFile);
        	}
		} 
		catch(IOException e) 
		{ 
        System.out.println("Error: "+e); 
		} 
	}
}

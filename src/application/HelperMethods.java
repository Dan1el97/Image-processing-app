package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HelperMethods {
	/*
	 * Method providing an error window when necessary.
	 */
	public static void ErrorWindow(String title, String text, int option) {
		Stage errorWindow = new Stage();
		errorWindow.initModality(Modality.APPLICATION_MODAL);
		errorWindow.setTitle(title);
		errorWindow.setMinWidth(350);
		errorWindow.setHeight(150);
		Label label = new Label();
		label.setText(text);
		label.setFont(new Font("Arial", 22));
		Button closeButton = new Button("Close");
		File file = null;
		if(option == 1) {
			 file = new File("bin\\application\\Images\\warningIcon.png");
		}
		else {
			 file = new File("bin\\application\\Images\\errorIcon.png");
		}
		BufferedImage bufferedErrorImage= null;
		try {
			bufferedErrorImage = ImageIO.read(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ImageView imageView = new ImageView();
		Image errorImage = SwingFXUtils.toFXImage(bufferedErrorImage, null);
		imageView.setImage(errorImage);
		imageView.setFitHeight(45);
		imageView.setFitWidth(45);
		imageView.setLayoutX(20);
		imageView.setLayoutY(15);
		label.setLayoutX(90);
		label.setLayoutY(20);
		closeButton.setLayoutX(147);
		closeButton.setLayoutY(70);
		
		closeButton.setOnAction(event -> errorWindow.close());
		Pane layout = new Pane();
		layout.getChildren().addAll(label, closeButton, imageView);
		Scene scene = new Scene(layout);
		errorWindow.setScene(scene);
		errorWindow.setResizable(false);
		errorWindow.showAndWait();
	}
	/*
	 * Conversion from JavaFX Image object into OpenCV Mat object
	 */
	public static Mat imageToMat(Image image, int option) {
	    int width = (int) image.getWidth();
	    int height = (int) image.getHeight();
	    byte[] buffer = new byte[width * height * 4];

	    PixelReader reader = image.getPixelReader();
	    WritablePixelFormat<ByteBuffer> format = WritablePixelFormat.getByteBgraInstance();
	    reader.getPixels(0, 0, width, height, format, buffer, 0, width * 4);
	    Mat mat = null;
	    if(option == 1) {
	        mat = new Mat(height, width, CvType.CV_8UC4);
	  	    mat.put(0, 0, buffer);
	  	    return mat;
	    }
	    else {
	        mat = new Mat(height, width,  Imgcodecs.IMREAD_GRAYSCALE);
	  	    mat.put(0, 0, buffer);
	  	    return mat;
	    }
	}
	/*
	 * A method providing the buttons default background
	 */
	public static void defaultBackground( final Button b1, Button b2, Button b3, Button b4, Button b5, Button b6,
			Button b7, Background background) {
		b1.setBackground(background);
		b2.setBackground(background);
		b3.setBackground(background);
		b4.setBackground(background);
		b5.setBackground(background);
		b6.setBackground(background);
		b7.setBackground(background);
		
	}
	/*
	 * Display METHOD - Allows the user to choose if he really wants to close the application
	 */
	static Boolean result = false;
	public static boolean display(String title, String message) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		Label label = new Label();
		label.setText(message);
		
		Button yesButton = new Button("Yes");
		Button noButton = new Button("No");
		
		
		yesButton.setOnAction(e -> {
			result = true;
			window.close();
		});
		
		noButton.setOnAction(e -> {
			result = false;
			window.close();
		});
		
		VBox vBox = new VBox(10);
		vBox.getChildren().addAll(label, yesButton, noButton);
		vBox.setAlignment(Pos.CENTER);
		Scene scene = new Scene(vBox);
		window.setScene(scene);
		window.showAndWait();
		
		return result;
		
	}
}

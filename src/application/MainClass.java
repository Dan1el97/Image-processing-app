package application;
import java.io.FileInputStream;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class MainClass extends Application{
	/*
	 * The main window,scene and layout 
	 * Containers for the changed file format and watermark(and its color and placement on the image)
	 * Scrollbar objects
	 */
	Stage mainWindow;
	static Scene scene;
	static BorderPane layout;
	static Text fileFormat;
	static Text wmColor;
	static Text wmPlacement;
	static int colorOption = 0;
	static int placementOption = 0;
	static TextField inputWatermark = null;
	static ScrollBar red = null;
	static ScrollBar green = null;
	static ScrollBar blue = null;
	
	/*
	 * Buttons
	 */
	Button openButton = null;
	Button saveAsButton = null;
	Button zeroButton = null;
	Button firstButton = null;
	Button secondButton = null;
	Button thirdButton = null;
	Button fourthButton = null;
	Button fifthButton = null;
	Button sixthButton = null;
	Button setRGBButton = null;
	Button setBrButton = null;
	Button convertButton = null;
	Button sepiaButton = null;
	Button negativeButton = null;
	Button redButton = null; 
	Button greenButton = null; 
	Button blueButton = null; 
	Button wmButton = null; 
	Button placement = null;
	Button resetButton = null;
	
	@Override
	public void start(Stage stage) throws Exception {
		mainWindow = stage;
		mainWindow.setTitle("IMGpesc");
		Image appImage = new Image(getClass().getResourceAsStream("\\Images\\appImage.png"));
		mainWindow.getIcons().add(appImage);
        /*
         * Container for a background picture.
         */
        Rectangle picContainer = new Rectangle();
        picContainer.setLayoutX(100);
        picContainer.setLayoutY(100);
        picContainer.setWidth(800);
        picContainer.setHeight(500);
        picContainer.setEffect(new DropShadow(30d, 10d, -12d, Color.BLACK));
        /*
         * Background picture
         */
		FileInputStream inputImage = new FileInputStream("bin\\application\\Images\\backgroundPic.png");
		Image backgroundImage = new Image(inputImage);
		ImagePattern imagePattern = new ImagePattern(backgroundImage);
		picContainer.setFill(imagePattern);
		/*
		 * Buttons background
		 */
		BackgroundImage backgroundIm = new BackgroundImage( new Image( getClass().getResource("\\Images\\defaultBackground.jpg").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background defaultBackground = new Background(backgroundIm);
		/*
		 * Buttons clicked background
		 */
		BackgroundImage backgroundIm2 = new BackgroundImage( new Image( getClass().getResource("\\Images\\onEventOccuredBackground.jpg").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background onEventOccuredBackground = new Background(backgroundIm2);
		/*
		 * File menu
		 */
		Menu fileMenu = new Menu("File");
		/*
		 * Menu items
		 * Open, Save and Exit
		 */
		MenuItem openImgFile = new MenuItem("Open...");
		openImgFile.setOnAction(e -> {
			FileMenu.openFile(mainWindow);
		});
		fileMenu.getItems().add(openImgFile);
		MenuItem saveImage = new MenuItem("Save as...");
		saveImage.setOnAction(e -> {

			if(FileMenu.imagePath == null){
				HelperMethods.ErrorWindow("Empty File Path","Image Not Found", 2);
			}
			else {
				FileMenu.saveAs(ImageProcessing.actualImage, mainWindow);
			}
		});
		fileMenu.getItems().add(saveImage);
		//Separator line
		fileMenu.getItems().add(new SeparatorMenuItem());
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(e -> closeApplication());
		fileMenu.getItems().add(exit);
		mainWindow.setOnCloseRequest(e -> {
			e.consume();
			closeApplication();
		});
		/*
		 * Menu bar containing the file menu
		 */
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu);
		/*
		 * FUNCTIONALITIES
		 * Container for the file format to be chosen.
		 */
		Rectangle fileFormatContainer = new Rectangle();
		fileFormatContainer.setWidth(60);
		fileFormatContainer.setHeight(60);    
		fileFormatContainer.setFill(Color.WHITE.deriveColor(0, 1.2, 1, 0.6));
		fileFormatContainer.setStroke(Color.GRAY);
		
		//MenuButton
		StackPane fileFormatLayout = new StackPane();
		fileFormat = new Text();
		fileFormatContainer.setWidth(110);
		fileFormatContainer.setHeight(40);
		fileFormatLayout.getChildren().addAll(fileFormatContainer, fileFormat);
		fileFormatLayout.setLayoutX(40);
		fileFormatLayout.setLayoutY(130);
		
		//Possible Extensions
		MenuItem jpgFormat = new MenuItem("JPG");
		jpgFormat.setOnAction(e -> {
			fileFormat.setText("jpg");
			fileFormat.setStyle("-fx-font : 28 gothic");
		});
		MenuItem pngFormat = new MenuItem("PNG");
		pngFormat.setOnAction(e -> {
			fileFormat.setText("png");
			fileFormat.setStyle("-fx-font : 28 gothic");
		});
		MenuItem bmpFormat = new MenuItem("BMP");
		bmpFormat.setOnAction(e -> {
			fileFormat.setText("bmp");
			fileFormat.setStyle("-fx-font : 28 gothic");
		});
		MenuButton menuButton = new MenuButton("Choose File Format", null, jpgFormat, pngFormat, bmpFormat);
		menuButton.setStyle("-fx-border-color: green;");
		menuButton.setLayoutY(70);
		menuButton.setLayoutX(-20);
		menuButton.setMinSize(150, 50);
		menuButton.setBackground(defaultBackground);
		/*
		 * Open and Save as buttons
		 */
		Pane pane2 = new Pane();
		openButton = new Button("Open");
		openButton.setMinSize(70, 45);	
		openButton.setLayoutX(-105);
		openButton.setLayoutY(71);
		openButton.setOnAction(e -> FileMenu.openFile(mainWindow));
		saveAsButton = new Button("Save as");
		saveAsButton.setMinSize(70, 45);	
		saveAsButton.setLayoutX(-105);
		saveAsButton.setLayoutY(520);
		saveAsButton.setOnAction(e -> {
			if(FileMenu.imagePath == null){
				HelperMethods.ErrorWindow("Empty File Path","Image Not Found", 2);
			}
			else {
				FileMenu.saveAs(ImageProcessing.actualImage, mainWindow);
			}
		});
		pane2.getChildren().addAll(openButton, saveAsButton);
		
		/*
		 * Set Brightness
		 */
		Pane BrButtons = new HBox(20);
		BrButtons.setLayoutY(675);
		BrButtons.setLayoutX(300);
		Pane BrLayout = new VBox();
		BrLayout.setLayoutY(675);
		BrLayout.setLayoutX(100);
		//---------
		zeroButton = new Button("0");
		zeroButton.setMinWidth(68);
		zeroButton.setOnAction(e->ImageProcessing.removeBrightness());
		//---------
		firstButton = new Button("1");
		firstButton.setMinWidth(68);
		firstButton.setOnAction(e-> ImageProcessing.brightnessEnhancement(1,50));
		//---------
		secondButton = new Button("2");
		secondButton.setMinWidth(68);
		secondButton.setOnAction(e-> ImageProcessing.brightnessEnhancement(1,60));
		//---------
		thirdButton = new Button("3");
		thirdButton.setMinWidth(68);
		thirdButton.setOnAction(e-> ImageProcessing.brightnessEnhancement(1,70));
		//---------
		fourthButton = new Button("4");
		fourthButton.setMinWidth(68);
		fourthButton.setOnAction(e-> ImageProcessing.brightnessEnhancement(1,80));
		//---------
		fifthButton = new Button("5");
		fifthButton.setMinWidth(68);
		fifthButton.setOnAction(e-> ImageProcessing.brightnessEnhancement(2,50));
		//---------
		sixthButton = new Button("6");
		sixthButton.setMinWidth(68);
		sixthButton.setOnAction(e-> ImageProcessing.brightnessEnhancement(2,70));
		//---------
		/*
		 * Set RGB
		 */
		Pane RGBLayout = new VBox(20);
		RGBLayout.setLayoutY(620);
		RGBLayout.setLayoutX(300);
		Pane RGBButtons = new VBox();
		RGBButtons.setLayoutY(620);
		RGBButtons.setLayoutX(100);
		//---------
		red = new ScrollBar();
		red.setMin(0);
		red.setMax(100);
		red.setValue(0);
		red.setMinHeight(20);
		red.setMinWidth(600);  
		red.setOnMouseClicked(e-> ImageProcessing.changeRGB	((int)red.getValue(),1));
		//---------
	    green = new ScrollBar();
		green.setId("green");
		green.setMin(0);
		green.setMax(100);
		green.setValue(0);
		green.setMinHeight(20);
		green.setMinWidth(600);
		green.setOnMouseClicked(e-> ImageProcessing.changeRGB((int)green.getValue(),2));
		//---------
		blue = new ScrollBar();
		blue.setId("blue");
		blue.setMin(0);
		blue.setMax(100);
		blue.setValue(0);
		blue.setMinHeight(20);
		blue.setMinWidth(600);
		blue.setOnMouseClicked(e-> ImageProcessing.changeRGB((int)blue.getValue(),3));
		
		Image RGBImage = new Image(getClass().getResourceAsStream("\\Images\\RGBIcon.png"));
		ImageView RGBIcon = new ImageView(RGBImage);
		RGBIcon.setFitHeight(50);
		RGBIcon.setFitWidth(50);
		
		/*
		 * Set Watermarking
		 * 
		 */
		Pane WMLayout = new HBox(20);
		WMLayout.setLayoutY(620);
		WMLayout.setLayoutX(300);
		Pane WMButtons = new VBox();
		WMButtons.setLayoutY(730);
		WMButtons.setLayoutX(100);
		StackPane colorLayout = new StackPane();
		StackPane placementLayout = new StackPane();
		Pane watermarkLayout = new VBox(10);
		Rectangle wmColorContainer = new Rectangle();  
		Rectangle wmPlacementContainer = new Rectangle();
		wmColorContainer.setFill(Color.WHITE.deriveColor(0, 1.2, 1, 0.6));
		wmColorContainer.setStroke(Color.GRAY);
		wmColorContainer.setWidth(90);
		wmColorContainer.setHeight(38);
		wmColor = new Text();
		
		wmPlacementContainer.setFill(Color.WHITE.deriveColor(0, 1.2, 1, 0.6));
		wmPlacementContainer.setStroke(Color.GRAY);
		wmPlacementContainer.setWidth(90);
		wmPlacementContainer.setHeight(38);
		wmPlacement = new Text();
		
		colorLayout.getChildren().addAll(wmColorContainer, wmColor);
		placementLayout.getChildren().addAll(wmPlacementContainer, wmPlacement);
		
		MenuItem redColor = new MenuItem("Red");
		redColor.setOnAction(e-> {
			wmColor.setText("Red");
			colorOption = 1;
		});
		MenuItem greenColor = new MenuItem("Green");
		greenColor.setOnAction(e-> {
			wmColor.setText("Green");
			colorOption = 2;
		});
		MenuItem blueColor = new MenuItem("Blue");
		blueColor.setOnAction(e-> {
			wmColor.setText("Blue");
			colorOption = 3;
		});
		MenuButton color = new MenuButton("Color", null, redColor, greenColor, blueColor);
		color.setMinSize(90, 42);
		//Watermark placement
		MenuItem up = new MenuItem("Up");
		up.setOnAction(e ->{
			wmPlacement.setText("Up");
			placementOption = 1;
			
		});
		MenuItem down = new MenuItem("Down");
		down.setOnAction(e ->{
			wmPlacement.setText("Down");
			placementOption = 2;
		});
		MenuButton wmPlacement = new MenuButton("Placement", null, up, down);
		wmPlacement.setMinSize(90, 42);
		
		inputWatermark = new TextField();
		inputWatermark.setMinSize(140, 42);
		inputWatermark.setPromptText("input watermark");
		inputWatermark.setOnAction(e->{
			if(inputWatermark.getText() != ImageProcessing.watermark) {
				ImageProcessing.watermark = inputWatermark.getText();
				ImageProcessing.watermaking();
			}
		});
		watermarkLayout.getChildren().addAll(inputWatermark);
		Image resetImage = new Image(getClass().getResourceAsStream("\\Images\\resetIcon.png"));
		ImageView resetIcon = new ImageView(resetImage);
		resetIcon.setFitHeight(50);
		resetIcon.setFitWidth(50);
		resetButton = new Button("Reset", resetIcon);
		resetButton.setLayoutX(0);
		resetButton.setLayoutY(585);
		resetButton.setMinSize(90, 60);
		resetButton.setBackground(defaultBackground);
		resetButton.setStyle("-fx-border-color: green;");
		resetButton.setOnAction(e->{
			if(FileMenu.imagePath == null){
				HelperMethods.ErrorWindow("Error", "Image Not Found",2);
			}
			else {
				HelperMethods.defaultBackground( negativeButton, sepiaButton,setBrButton, redButton, wmButton,
						greenButton, blueButton, defaultBackground);
				resetButton.setOnMousePressed(e1-> resetButton.setBackground(onEventOccuredBackground));
				resetButton.setOnMouseReleased(e1-> resetButton.setBackground(defaultBackground));
				ImageProcessing.reset();
			}
		});
		/*
		 * RGB - On Action
		 */
		setRGBButton = new Button("SET RGB     ", RGBIcon);
		setRGBButton.setStyle("-fx-border-color: green;");
		setRGBButton.setMinHeight(45);
		setRGBButton.setMinWidth(150);
		setRGBButton.setBackground(defaultBackground);
		setRGBButton.setOnAction(e -> {
			if(FileMenu.imagePath == null){
				HelperMethods.ErrorWindow("Error", "Image Not Found",2);
			}
			else {
				HelperMethods.defaultBackground( negativeButton, sepiaButton,setBrButton, redButton, wmButton,
						greenButton, blueButton, defaultBackground);
				if(setRGBButton.getBackground() == defaultBackground) {
					setRGBButton.setBackground(onEventOccuredBackground);
				}
				else {
					setRGBButton.setBackground(defaultBackground);
				}
				if(BrButtons.getChildren().contains(firstButton)){
					BrButtons.getChildren().removeAll(zeroButton, firstButton, secondButton,thirdButton,fourthButton,
							fifthButton,sixthButton);
				}
				if(WMLayout.getChildren().contains(color)) {
					WMLayout.getChildren().removeAll(color,colorLayout,wmPlacement, placementLayout, watermarkLayout);
				}
				if(RGBLayout.getChildren().contains(red)) {
					RGBLayout.getChildren().removeAll(red,green,blue);
				}
				else {
					RGBLayout.getChildren().addAll(red,green,blue);
				}
			}
		});
		RGBButtons.getChildren().add(setRGBButton);				
		/*
		 * Brightness - On Action
		 */
		Image brightnessImage = new Image(getClass().getResourceAsStream("\\Images\\brightnessIcon.png"));
		ImageView brightnessIcon = new ImageView(brightnessImage);
		brightnessIcon.setFitHeight(55);
		brightnessIcon.setFitWidth(55);
		//---------
		setBrButton = new Button("Brightness",brightnessIcon);
		setBrButton.setStyle("-fx-border-color: green;");
		setBrButton.setMinHeight(45);
		setBrButton.setMinWidth(150);
		setBrButton.setBackground(defaultBackground);
		//Image image = new Image(getClass().getResourceAsStream("\\Images\\i2.jpg"));
		setBrButton.setOnAction(e -> {
			if(FileMenu.imagePath == null){
				HelperMethods.ErrorWindow("Error", "Image Not Found",2);
			}
			else {
				HelperMethods.defaultBackground( negativeButton, sepiaButton,setRGBButton, redButton,
						greenButton, blueButton, wmButton, defaultBackground);
				if(setBrButton.getBackground() == defaultBackground) {
					setBrButton.setBackground(onEventOccuredBackground);
				}
				else {
					setBrButton.setBackground(defaultBackground);
				}
				if(RGBLayout.getChildren().contains(red)) {
					RGBLayout.getChildren().removeAll(red,green,blue);
				}
				if(WMLayout.getChildren().contains(color)) {
					WMLayout.getChildren().removeAll(color,colorLayout,wmPlacement, placementLayout, watermarkLayout);
				}
				if(BrButtons.getChildren().contains(firstButton)) {
					BrButtons.getChildren().removeAll(zeroButton, firstButton, secondButton,thirdButton,fourthButton,
							fifthButton,sixthButton);
				}
				else {
					BrButtons.getChildren().addAll(zeroButton, firstButton, secondButton,thirdButton,fourthButton,
							fifthButton,sixthButton);
				}
			}
		});
		BrLayout.getChildren().add(setBrButton);		
		/*
		 * Watermarking - On Action
		 */
		Image watermarkingImage = new Image(getClass().getResourceAsStream("\\Images\\watermarkingIcon.png"));
		ImageView watermarkingIcon = new ImageView(watermarkingImage);
		watermarkingIcon.setFitHeight(30);
		watermarkingIcon.setFitWidth(30);
		
		wmButton = new Button("Watermarking", watermarkingIcon);
		wmButton.setStyle("-fx-border-color: green;");
		wmButton.setMinHeight(45);
		wmButton.setMinWidth(150);
		wmButton.setBackground(defaultBackground);
		wmButton.setBorder(null);
		wmButton.setOnAction(e->{
			if(FileMenu.imagePath == null){
				HelperMethods.ErrorWindow("Error", "Image Not Found",2);
			}
			else {
				HelperMethods.defaultBackground( negativeButton, sepiaButton,setBrButton, setRGBButton, redButton,
						greenButton, blueButton, defaultBackground);
				if(wmButton.getBackground() == defaultBackground) {
					wmButton.setBackground(onEventOccuredBackground);
				}
				else {
					wmButton.setBackground(defaultBackground);
				}
				if(BrButtons.getChildren().contains(firstButton)){
					BrButtons.getChildren().removeAll(zeroButton, firstButton, secondButton,thirdButton,fourthButton,
							fifthButton,sixthButton);
				}
				if(RGBLayout.getChildren().contains(red)) {
					RGBLayout.getChildren().removeAll(red,green,blue);
				}
				if(WMLayout.getChildren().contains(color)) {
					WMLayout.getChildren().removeAll(color,colorLayout,wmPlacement, placementLayout, watermarkLayout);
				}
				else {
					WMLayout.getChildren().addAll(color,colorLayout,wmPlacement, placementLayout, watermarkLayout);
				}
			}
		});
		WMButtons.getChildren().add(wmButton);	
		//------
		
		/*
		 *  Set Convert button
		 */
		Image convertImage = new Image(getClass().getResourceAsStream("\\Images\\convertArrowsIcon.png"));
		ImageView convertIcon = new ImageView(convertImage);
		convertIcon.setFitHeight(30);
		convertIcon.setFitWidth(30);
		//---------
		convertButton = new Button("Convert", convertIcon);
		convertButton.setStyle("-fx-border-color: green;");
		convertButton.setLayoutX(40);
		convertButton.setLayoutY(180);
		convertButton.setMinSize(110, 45);	
		convertButton.setBackground(defaultBackground);
		/*
		 * Convert Button on Action
		 */
		convertButton.setOnAction(event -> {
			HelperMethods.defaultBackground( negativeButton, sepiaButton,setBrButton, redButton, wmButton,
					greenButton, blueButton, defaultBackground);
			if(fileFormat.getText().equals("")) {
				HelperMethods.ErrorWindow("Empty File Format", "Choose File Format", 1);
			}
			else if(FileMenu.imagePath == null) {
				HelperMethods.ErrorWindow("Empty File Path","Image Not Found",2);
			}
			else {
				convertButton.setOnMousePressed(e1-> convertButton.setBackground(onEventOccuredBackground));
				convertButton.setOnMouseReleased(e1-> convertButton.setBackground(defaultBackground));
				ConvertImage.convertImage(FileMenu.imagePath, mainWindow);
			
				
				
			}
			
		});
		
		/*
		 * Set Sepia button
		 */
		Image sepiaImage = new Image(getClass().getResourceAsStream("\\Images\\sepiaIcon.png"));
		ImageView sepiaIcon = new ImageView(sepiaImage);
		sepiaIcon.setFitHeight(25);
		sepiaIcon.setFitWidth(30);
		//---------
		sepiaButton = new Button("Sepia Mode  ", sepiaIcon);
		sepiaButton.setStyle("-fx-border-color: green;");
		sepiaButton.setMinSize(150,30);
		sepiaButton.setLayoutX(0);
		sepiaButton.setLayoutY(270);
		sepiaButton.setBackground(defaultBackground);
		/*
		 * Sepia Button On Action
		 */
		sepiaButton.setOnAction(e ->{
			HelperMethods.defaultBackground( negativeButton, setRGBButton,setBrButton, redButton,
					greenButton, blueButton, wmButton, defaultBackground);
			if(sepiaButton.getBackground() == defaultBackground) {
				sepiaButton.setBackground(onEventOccuredBackground);
			}
			/*else {
				sepiaButton.setBackground(background);
			}*/
			if(FileMenu.imagePath == null) {
				HelperMethods.ErrorWindow("Error", "Image Not Found",2);
			}
			else {
				ImageProcessing.convertToSepia();
			}
		});
	
		/*
		 * Set Negative button
		 */
		Image negativeImage = new Image(getClass().getResourceAsStream("\\Images\\negativeIcon.png"));
		ImageView negativeIcon = new ImageView(negativeImage);
		negativeIcon.setFitHeight(25);
		negativeIcon.setFitWidth(25);
		//---------
		negativeButton = new Button("Negative Mode", negativeIcon);
		negativeButton.setStyle("-fx-border-color: green;");
		negativeButton.setLayoutX(0);
		negativeButton.setLayoutY(320);
		negativeButton.setBackground(defaultBackground);
		/*
		 * Negative Button On Action
		 */
		negativeButton.setOnAction(e -> {
			HelperMethods.defaultBackground( sepiaButton, setRGBButton,setBrButton, redButton,
					greenButton, blueButton, wmButton, defaultBackground);
			if(negativeButton.getBackground() == defaultBackground) {
				negativeButton.setBackground(onEventOccuredBackground);
			}
			else {
				negativeButton.setBackground(defaultBackground);
			}
			if(FileMenu.imagePath == null){
				HelperMethods.ErrorWindow("Error", "Image Not Found",2);
			}
			else {
				ImageProcessing.convertToNegative();
			}
		});
		
		/*
		 * Set Red mode Button
		 */
		Image redImage = new Image(getClass().getResourceAsStream("\\Images\\redCircleIcon.png"));
		ImageView redIcon = new ImageView(redImage);
		redIcon.setFitHeight(25);
		redIcon.setFitWidth(25);
		redButton = new Button("Red Mode    ", redIcon);
		redButton.setStyle("-fx-border-color: green;");
		redButton.setMinSize(150,30);
		redButton.setLayoutX(0);
		redButton.setLayoutY(390);
		redButton.setBackground(defaultBackground);
		/*
		 * Red Mode Button On Action
		 */
		redButton.setOnAction(e -> {
			HelperMethods.defaultBackground( negativeButton, setRGBButton,setBrButton, sepiaButton,
					greenButton, blueButton, wmButton, defaultBackground);
			if(redButton.getBackground() == defaultBackground) {
				redButton.setBackground(onEventOccuredBackground);
			}
			else {
				redButton.setBackground(defaultBackground);
			}
			if(FileMenu.imagePath == null){
				HelperMethods.ErrorWindow("Error", "Image Not Found",2);
			}
			else {
				ImageProcessing.convertToRed();
			}
		});
		/*
		 * Set Green Mode Button
		 */
		Image greenImage = new Image(getClass().getResourceAsStream("\\Images\\greenCircleIcon.png"));
		ImageView greenIcon = new ImageView(greenImage);
		greenIcon.setFitHeight(25);
		greenIcon.setFitWidth(25);
		greenButton = new Button("Green Mode", greenIcon);
		greenButton.setStyle("-fx-border-color: green;");
		greenButton.setMinSize(150,30);
		greenButton.setLayoutX(0);
		greenButton.setLayoutY(440);
		greenButton.setBackground(defaultBackground);
		/*
		 * Green Mode Button On Action
		 */
		greenButton.setOnAction(e -> {
			HelperMethods.defaultBackground( negativeButton, setRGBButton,setBrButton, redButton,
					sepiaButton, blueButton, wmButton, defaultBackground);
			if(greenButton.getBackground() == defaultBackground) {
				greenButton.setBackground(onEventOccuredBackground);
			}
			else {
				greenButton.setBackground(defaultBackground);
			}
			if(FileMenu.imagePath == null){
				HelperMethods.ErrorWindow("Error", "Image Not Found",2);
			}
			else {
				ImageProcessing.convertToGreen();
			}
		});
		/*
		 * Set Blue Mode Button
		 */
		Image blueImage = new Image(getClass().getResourceAsStream("\\Images\\blueCircleIcon.png"));
		ImageView blueIcon = new ImageView(blueImage);
		blueIcon.setFitHeight(25);
		blueIcon.setFitWidth(25);
		blueButton = new Button("Blue Mode   ", blueIcon);
		blueButton.setStyle("-fx-border-color: green;");
		blueButton.setMinSize(150,30);
		blueButton.setLayoutX(0);
		blueButton.setLayoutY(490);
		blueButton.setBackground(defaultBackground);
		/*
		 * Blue Mode Button On Action
		 */
		blueButton.setOnAction(e -> {
			HelperMethods.defaultBackground( negativeButton, setRGBButton,setBrButton, redButton,
					greenButton, sepiaButton, wmButton, defaultBackground);
			if(blueButton.getBackground() == defaultBackground) {
				blueButton.setBackground(onEventOccuredBackground);
			}
			else {
				blueButton.setBackground(defaultBackground);
			}
			if(FileMenu.imagePath == null){
				HelperMethods.ErrorWindow("Error", "Image Not Found",2);
			}
			else {
				ImageProcessing.convertToBlue();
			}
		});
		/*
		 * Layout for the fileFormatLayout plus all the buttons excluding the RGB button
		 */
		Pane pane = new Pane();
		pane.getChildren().addAll(menuButton,convertButton, sepiaButton, negativeButton,redButton,
				greenButton, blueButton, resetButton, fileFormatLayout);

		//BorderPane - the main layout
		layout = new BorderPane();
		layout.setId("pane");
		layout.getChildren().addAll(picContainer, RGBLayout, RGBButtons,BrLayout,BrButtons, WMLayout, WMButtons);
		layout.setTop(menuBar);
		layout.setRight(pane);
		
		//Scene
		scene = new Scene(layout, 1200, 800);
		mainWindow.setScene(scene);
		//CSS
		mainWindow.getScene().getStylesheets().addAll(MainClass.class.getResource("root.css").toExternalForm());
		mainWindow.setResizable(false);
		mainWindow.show();
	}
	/*
	 * Method for closing the application containing the "display" method 
	 */
	private void closeApplication() {
		boolean result = HelperMethods.display("Exit", "Are you sure you want to close the application?");
		if(result) {
			mainWindow.close();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}

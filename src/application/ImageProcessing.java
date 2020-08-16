package application;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
public class ImageProcessing {
	 static BufferedImage img = null; 
	 static BufferedImage img3 = null;
     static File f = null; 
     static boolean flag = false;
     static BufferedImage img2 = null, actualImage = null;
     static int lastIndexOnGreen = 0, lastIndexOnRed = 0, lastIndexOnBlue = 0;
     static int tempGreen = 0, tempRed = 0, tempBlue = 0;
     static boolean isFirst = true;
     static String imagePath = null;
     static int[][] baseGr = fun(2);
     static int[][] baseRed = fun(1);
     static int[][] baseBlue = fun(3);
    // static BufferedImage bi = getBufferedImage();
     
     static int width; 
	   static int height; 
	 static String watermark = null;
   /* public static BufferedImage getBufferedImage() {
    //	 BufferedImage img = null;
    	 try { 
		    f = new File(FileMenu.imagePath);
			 img= ImageIO.read(f); 	
    	 }catch(Exception e) {
		    System.out.println(e);
		 }
    	 return img;
    	 
     }*/
     public static int[][] fun(int index) {
    	 try { 
 		        f = new File(FileMenu.imagePath);
 				img = ImageIO.read(f); 	
 		        }catch(Exception e) {
 		        	System.out.println(e);
 		         }
    	 int[][] baseGr = new int[img.getHeight()][img.getWidth()];
    	 int[][] baseRed = new int[img.getHeight()][img.getWidth()];
    	 int[][] baseBlue = new int[img.getHeight()][img.getWidth()];
    	 for(int y = 0; y < img.getHeight(); y++) { 
				for(int x = 0; x < img.getWidth(); x++) { 
					int p = img.getRGB(x,y); 

					//int a = (p>>24)&0xff; 
					int R = (p>>16)&0xff; 
					
					int G = (p>>8)&0xff; 
					int B = p&0xff; 
					if(index == 2) {
						baseGr[y][x] = G;
					}
					else if(index == 1) {
						baseRed[y][x] = R;
					}
					else if(index == 3){
						baseBlue[y][x] = B;
					}
				}
    	 }
    	 if(index==1) {
    		 return baseRed;
    	 }
    	 else if(index==2) {
    		 return baseGr;
    	 }
    	 else if(index==3){
    		 return baseBlue;
    	 }
    	 return null;
     }
     public static void changeRGB(int index, int index2) {
 			if(!flag) {
 				try { 
 		        	f = new File(FileMenu.imagePath);
 					img = ImageIO.read(f); 
 					imagePath = f.getAbsolutePath();
 		        }catch(Exception e) {
 		        	System.out.println(e);
 		         }
 			}
 			else {
 				img = img2;
 			}
 	        if(img != null) {
 	        	for(int y = 0; y < img.getHeight(); y++) { 
 					for(int x = 0; x < img.getWidth(); x++) { 
 						int p = img.getRGB(x,y); 
 	  
 						int a = (p>>24)&0xff; 
 						int R = (p>>16)&0xff; 
 						
 						int G = (p>>8)&0xff; 
 						int B = p&0xff; 
 						
 						if(index2 == 1) {
 							if(index > lastIndexOnRed) {
								 if(isFirst) {
									 tempRed = index;
									 double newRed = R*(index)/50;
									 if(R+newRed > 255) {
										 R = 255;
									 }
									 else {
										R = R+(int)newRed;
									 }
								 }
								 else {
									 double newRed = baseRed[y][x]*(index-lastIndexOnRed)/50;
									 if(R+newRed > 255) {
										 R = 255;
									 }
									 else {
										 R = R+(int)newRed;
									 }
								 }
							}	
							else if(index < lastIndexOnRed) {
								double newRed = baseRed[y][x]*(lastIndexOnRed-index)/50;
								if(R-newRed < baseRed[y][x]) {
									R = (int)baseRed[y][x];
								}
								else {
									R = R-(int)newRed;
								}
							}
							 p = (a<<24) | (R<<16) | (G<<8) | B; 
				                img.setRGB(x, y, p);           
 						}
 						else if(index2 == 2) {
 							if(index > lastIndexOnGreen) {
 								 if(isFirst) {
 									 tempGreen = index;
 									 double newGreen = G*(index)/50;
 									 if(G+newGreen > 255) {
 										 G = 255;
 									 }
 									 else {
 										G = G+(int)newGreen;
 									 }
 								 }
 								 else {
 									 double newGreen = baseGr[y][x]*(index-lastIndexOnGreen)/50;
 									 if(G+newGreen > 255) {
 										 G = 255;
 									 }
 									 else {
 										 G = G+(int)newGreen;
 									 }
 								 }
 							}	
 							else if(index < lastIndexOnGreen) {
 								double newGreen = baseGr[y][x]*(lastIndexOnGreen-index)/50;
 								if(G-newGreen < baseGr[y][x]) {
 									G = (int)baseGr[y][x];
 								}
 								else {
 									G = G-(int)newGreen;
 								}
 							}
 							 p = (a<<24) | (R<<16) | (G<<8) | B; 
 				                img.setRGB(x, y, p); 
 						}
 						else if(index2 == 3) {
 								if(index > lastIndexOnBlue) {
 									if(isFirst) {
 										tempBlue = index;
 										double newBlue = B*(index)/50;
 										if(B+newBlue > 255) {
 											B = 255;
 										}
									else {
										B = B+(int)newBlue;
									}
								}
								else {
									double newBlue = baseBlue[y][x]*(index-lastIndexOnBlue)/50;
									if(B+newBlue > 255) {
										B = 255;
									}
									else {
										B = B+(int)newBlue;
									}
								}
							}
							else if(index < lastIndexOnBlue) {
								double newBlue = baseBlue[y][x]*(lastIndexOnBlue-index)/50;
								if(B-newBlue < baseBlue[y][x]) {
									B = (int)baseBlue[y][x];
								}
								else {
									B = B-(int)newBlue;
								}
							}
							 p = (a<<24) | (R<<16) | (G<<8) | B; 
				                img.setRGB(x, y, p);           
 						}
 					}
 	        	}
 	        	if(!isFirst) {
 	        		if(index2 == 2) {
 	        			tempGreen+=index-lastIndexOnGreen;
 	        			lastIndexOnGreen = index;
 	        		}
 	        		else if(index2 == 1) {
 	        			tempRed+=index-lastIndexOnRed;
 	        			lastIndexOnRed = index;
 	        		}
 	        		else if(index2 == 3){
 	        			tempBlue+=index-lastIndexOnBlue;
 	        			lastIndexOnBlue = index;
 	        		}
 	        	}
 	        	
 	        }
 	        Image image = SwingFXUtils.toFXImage(img, null);
 			FileMenu.myImageView.setImage(image);
 			img2 = img;
 			actualImage = img2;
 			flag = true;
 			isFirst = false;
 	 }
	public static void convertToSepia() {
		try {
			f = new File(FileMenu.imagePath);
			img = ImageIO.read(f);
			//imagePath = f.getAbsolutePath();
		}catch(Exception e) {
		}
		if(img != null) {
			for(int y = 0; y < img.getHeight(); y++) { 
				for(int x = 0; x < img.getWidth(); x++) { 
					int p = img.getRGB(x,y); 
					
					int a = (p>>24)&0xff; 
					int R = (p>>16)&0xff; 
					int G = (p>>8)&0xff; 
					int B = p&0xff; 
					
					//calculate newRed, newGreen, newBlue 
					int newRed = (int)(0.393*R + 0.769*G + 0.189*B); 
					int newGreen = (int)(0.349*R + 0.686*G + 0.168*B); 
					int newBlue = (int)(0.272*R + 0.534*G + 0.131*B); 
  
					//check condition 
					if (newRed > 255) 
						R = 255; 
					else
						R = newRed; 
  
					if (newGreen > 255) 
						G = 255; 
					else
						G = newGreen; 
  
					if (newBlue > 255) 
						B = 255; 
					else
						B = newBlue; 
  
					//set new RGB value 
					p = (a<<24) | (R<<16) | (G<<8) | B; 
  
					img.setRGB(x, y, p); 
				}
			}
		
			//Override Image
			Image image = SwingFXUtils.toFXImage(img, null);
			FileMenu.myImageView.setImage(image);
		
			actualImage = img;
		}
	}
	
	public static void convertToNegative() {
			if(!flag){
			        // read image 
			        try
			        { 
			        	f = new File(FileMenu.imagePath);
						img = ImageIO.read(f); 
						imagePath = f.getAbsolutePath();
			        } 
			        catch(Exception e) { 
			        }
			}
			else {
				img = img2;
			}
		      if(img != null) {
		        for (int y = 0; y < img.getHeight(); y++)  { 
		            for (int x = 0; x < img.getWidth(); x++) { 
		                int p = img.getRGB(x,y);
		                int a = (p>>24)&0xff; 
		                int r = (p>>16)&0xff; 
		                int g = (p>>8)&0xff; 
		                int b = p&0xff; 
		  
		                //subtract RGB from 255 
		                r = 255 - r; 
		                g = 255 - g; 
		                b = 255 - b; 
		  
		                //set new RGB value 
		                p = (a<<24) | (r<<16) | (g<<8) | b; 
		                img.setRGB(x, y, p); 
		            } 
		        } 
		        // write image 
	        	Image image = SwingFXUtils.toFXImage(img, null);
				FileMenu.myImageView.setImage(image);
				img2 = img;
				actualImage = img2;
	      }
	  }
	
	public static double func(double G,double per) {
		return G*100/(100+per);
	}
	
	public static void brightnessEnhancement(int alpha, int beta) {
		 System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		 if(flag == false) {
			 try
		        { 
					
		        	f = new File(FileMenu.imagePath);
					img = ImageIO.read(f); 
					imagePath = f.getAbsolutePath();
		        } 
			 catch(Exception e) { 
		        } 
		 	}
		 else {
		 	img = img2;
		 }
		 try {
			 Mat source = null;
			 if(!flag) {
				  source = Imgcodecs.imread(imagePath, Imgcodecs.IMREAD_COLOR);
			 }
			 else {
				 Image image =  SwingFXUtils.toFXImage(img2, null);
				 source = HelperMethods.imageToMat(image,1);
			 }
			// System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
			 Mat destination = new Mat(source.rows(), source.cols(), source.type());
			 source.convertTo(destination, -5, alpha, beta); 
			 MatOfByte byteMat = new MatOfByte();
			 Imgcodecs.imencode(".bmp", destination, byteMat);
			 Image result = new Image(new ByteArrayInputStream(byteMat.toArray()));
			 FileMenu.myImageView.setImage(result);
			 img2 = img;
				actualImage = img2;
		 }
		 catch(Exception e) { 
	        } 
	 	
		
       
	}
	
	public static void removeBrightness() {
		Image image =  SwingFXUtils.toFXImage(img, null);
		FileMenu.myImageView.setImage(image);
	}
	/*
	 * Converts an object of type "image" into one of type "Mat"(matrix - openCv property).
	 */
	
	public static void contrastEnchancement() {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME ); 
		 if(flag == false) {
			 try
		        { 
					
		        	f = new File(FileMenu.imagePath);
					img = ImageIO.read(f); 
					imagePath = f.getAbsolutePath();
		        } 
			 catch(Exception e) { 
		        } 
		 	}
		 else {
		 	img = img2;
		 }
		 try{ 
	            Mat source = null;
	            if(!flag) {
	            	 source = Imgcodecs.imread(imagePath, 
	                           Imgcodecs.IMREAD_GRAYSCALE); 
	            }
	            else {
					 Image image =  SwingFXUtils.toFXImage(img2, null);
					 source = HelperMethods.imageToMat(image,2);
					// source
				 }
	            Mat destination = new Mat(source.rows(), 
	                               source.cols(), source.type());
	            Imgproc.equalizeHist(source, destination);
	            //Imgcodecs.imwrite("H:\\output.jpg", destination); 
	           MatOfByte byteMat = new MatOfByte();
				 Imgcodecs.imencode(".bmp", destination, byteMat);
				 Image result = new Image(new ByteArrayInputStream(byteMat.toArray()));
				 
				 FileMenu.myImageView.setImage(result);
				 img2 = img;
					actualImage = img;
	        } 
	        catch (Exception e) 
	        { 
	            System.out.println("error: " + e.getMessage()); 
	        } 
	     
	
	
	}
	public static void convertToRed() {
		if(!flag) { 
			try
		        { 
		        	f = new File(FileMenu.imagePath);
					img = ImageIO.read(f); 
					imagePath = f.getAbsolutePath();
		        } 
		        catch(Exception e) { 
		        } 
		}
		
		else {
			img = img2;
		}
		      if(img != null) {
		    	  for (int y = 0; y < img.getHeight(); y++) { 
		              for (int x = 0; x < img.getWidth(); x++){ 
		                  int p = img.getRGB(x,y); 
		    
		                  int a = (p>>24)&0xff; 
		                  int r = (p>>16)&0xff; 
		    
		                  // set new RGB 
		                  // keeping the r value same as in original 
		                  // image and setting g and b as 0. 
		                  p = (a<<24) | (r<<16) | (0<<8) | 0; 
		    
		                  img.setRGB(x, y, p); 
		              } 
		          } 
		        // write image 
	        	Image image = SwingFXUtils.toFXImage(img, null);
				FileMenu.myImageView.setImage(image);
				img2 = img;
				actualImage = img2;
	      }
		
	}
	public static void convertToGreen() {
		if(!flag) {
			 try
		        { 
		        	f = new File(FileMenu.imagePath);
					img = ImageIO.read(f); 
					imagePath = f.getAbsolutePath();
		        } 
		        catch(Exception e) { 
		        } 
		}
		else {
			img = img2;
		}
		      if(img != null) {
		    	  for (int y = 0; y < img.getHeight(); y++) { 
		              for (int x = 0; x < img.getWidth(); x++){ 
		                  int p = img.getRGB(x,y); 
		    
		                  int a = (p>>24)&0xff; 
		                  int g = (p>>16)&0xff; 
		    
		                  // set new RGB 
		                  // keeping the r value same as in original 
		                  // image and setting g and b as 0. 
		                  p = (a<<24) | (0<<16) | (g<<8) | 0; 
		    
		                  img.setRGB(x, y, p); 
		              } 
		          } 
		        // write image 
	        	Image image = SwingFXUtils.toFXImage(img, null);
				FileMenu.myImageView.setImage(image);
				img2 = img;
				actualImage = img2;
	      }
	}
	public static void convertToBlue() {
		if(!flag) {
			 try
		        { 
		        	f = new File(FileMenu.imagePath);
					img = ImageIO.read(f); 
					imagePath = f.getAbsolutePath();
		        } 
		        catch(Exception e) { 
		        } 
		}
		else {
			img = img2;
		}
		      if(img != null) {
		    	  for (int y = 0; y < img.getHeight(); y++) { 
		              for (int x = 0; x < img.getWidth(); x++){ 
		                  int p = img.getRGB(x,y); 
		    
		                  int a = (p>>24)&0xff; 
		                  int b = (p>>16)&0xff; 
		    
		                  // set new RGB 
		                  // keeping the r value same as in original 
		                  // image and setting g and b as 0. 
		                  p = (a<<24) | (0<<16) | (0<<8) | b; 
		    
		                  img.setRGB(x, y, p); 
		              } 
		          } 
		        // write image 
	        	Image image = SwingFXUtils.toFXImage(img, null);
				FileMenu.myImageView.setImage(image);
				img2 = img;
				actualImage = img2;
	      }
	}
	static boolean flag2 = true;
	public static void watermaking() {
		if(flag2) {
			img3 = img;
		}
		if(!flag) {
			 try
		        { 
		        	f = new File(FileMenu.imagePath);
					img = ImageIO.read(f); 
					imagePath = f.getAbsolutePath();
		        } 
		        catch(Exception e) { 
		        } 
		}
		else {
			img = img2;
		}
		 if(img != null) {
			 BufferedImage temp = new BufferedImage(img.getWidth(), 
	                    img.getHeight(), BufferedImage.TYPE_INT_RGB); 
	  
	        // Create graphics object and add original 
	        // image to it 
	        Graphics graphics = temp.getGraphics(); 
	        graphics.drawImage(img, 0, 0, null); 
	  
	        // Set font for the watermark text 
	        graphics.setFont(new Font("Arial", Font.PLAIN, 80)); 
	        if(MainClass.colorOption == 1) {
	        	graphics.setColor(new Color(255, 0, 0, 100)); 
	        }
	        else if(MainClass.colorOption == 2) {
	        	graphics.setColor(new Color(0, 255, 0, 100)); 
	        }
	        else {
	        	graphics.setColor(new Color(0, 0, 255, 100)); 
	        }
	        graphics.drawString(watermark, img.getWidth()/5, 
	                                   img.getHeight()/3); 
	  
	        // releases any system resources that it is using 
	        graphics.dispose(); 
	        Image image = SwingFXUtils.toFXImage(temp, null);
			FileMenu.myImageView.setImage(image);
			flag = true;
			flag2 = false;
			actualImage = temp;
			img2 = temp;
		 }
	}
	public static void reset() {
		/* try
	        { 
	        	f = new File(FileMenu.imagePath);
				img = ImageIO.read(f); 
				//imagePath = f.getAbsolutePath();
	        } 
	        catch(Exception e) { 
	        } */
		BufferedImage bufferedImage = null;
		//String path = FileMenu.file.getAbsolutePath();
		MainClass.red.setValue(0);
		MainClass.green.setValue(0);
		MainClass.blue.setValue(0);
		ImageProcessing.convertToBlue();
		ImageProcessing.convertToRed();
		ImageProcessing.convertToGreen();
		try {
		 bufferedImage = ImageIO.read(FileMenu.file);
		 Image image = SwingFXUtils.toFXImage(bufferedImage, null);
		 FileMenu.myImageView.setImage(image);
		 FileMenu.myImageView.setFitHeight(500);
		 FileMenu.myImageView.setFitWidth(800);
		}
		catch(Exception e) {}
		/*Image image =  SwingFXUtils.toFXImage(img, null);
		FileMenu.myImageView.setImage(image);*/
		img2 = bufferedImage;
		actualImage = bufferedImage;
	}
}

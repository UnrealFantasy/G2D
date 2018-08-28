package com.insertpunhere1.electron;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sheet {
	public String file;
	
	public int width, height;
	
	public int[] data;
	
	public Sheet(String file) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(Sheet.class.getResourceAsStream(file));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		if(image == null) {
			return;
		}
		
		this.file = file;
		
		this.width = image.getWidth();
		this.height = image.getHeight();
		
		this.data = image.getRGB(0, 0, this.width, this.height, null, 0, this.width);
		
		for(int index = 0; index < data.length; index++) {
			data[index] = (data[index] & 0xFF) / (int) ((float) 255 / (float) 3);
		}
	}
}

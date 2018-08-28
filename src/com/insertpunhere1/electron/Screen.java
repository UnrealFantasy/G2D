package com.insertpunhere1.electron;

public class Screen {
	public static final int MAP_WIDTH = 128;
	
	public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;
	
	public int[] tiles = new int[MAP_WIDTH * MAP_WIDTH];
	public int[] colors = new int[MAP_WIDTH * MAP_WIDTH * 4];
	
	public int xOffset, yOffset;
	
	public int width, height;
	
	public Sheet sheet;
	
	public Screen(Sheet sheet, int width, int height) {
		this.width = width;
		this.height = height;
		
		this.sheet = sheet;
		
		for(int index = 0; index < MAP_WIDTH * MAP_WIDTH; index++) {
			colors[index * 4] = 0x007F0E;
			colors[index * 4 + 1] = 0x00A30E;
			colors[index * 4 + 2] = 0x000000;
			colors[index * 4 + 3] = 0x000000;
		}
	}
	
	public void render(int[] pixels, int row, int offset) {
		for(int yTile = yOffset >> 3; yTile <= (yOffset + height) >> 3; yTile++) {
			int yMin = yTile * 8 - yOffset;
			int yMax = yMin + 8;
			
			if(yMin < 0) yMin = 0;
			if(yMax > height) yMax = height;
			
			for(int xTile = xOffset >> 3; xTile <= (xOffset + width) >> 3; xTile++) {
				int xMin = xTile * 8 - xOffset;
				int xMax = xMin + 8;
				
				if(xMin < 0) xMin = 0;
				if(xMax > width) xMax = width;
				
				int tileIndex = (xTile & (MAP_WIDTH_MASK)) + (yTile & (MAP_WIDTH_MASK)) * MAP_WIDTH;
				
				for(int y = yMin; y < yMax; y++) {
					int tilePixel = offset + xMin + y * row;
					
					int sheetPixel = ((y + yOffset) & 7) * sheet.width + ((xMin + xOffset) & 7);
					 
					for(int x = xMin; x < xMax; x++) {
						int color = tileIndex * 4 + sheet.data[sheetPixel++];
						
						if(yTile > MAP_WIDTH || xTile > MAP_WIDTH || yTile < 0 || xTile < 0) {
							pixels[tilePixel++] = 0;
							
							continue;
						}
						
						pixels[tilePixel++] = colors[color];
					}
				}
			}
		}
	}
}

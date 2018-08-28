package com.insertpunhere1.electron;

public class Colors {
	public static int get(int cf, int cs, int ct, int cl) {
		return ((get(cl) << 24) + (get(ct) << 16) + (get(cs) << 8) + (get(cf)));
	}
	
	private static int get(int color) {
		if(color < 0) 
			return 255;
		
		int red = color / 100 % 10;
		int green = color / 10 % 10;
		int blue = color % 10;
		
		return red * 36 + green * 6 + blue;
	}
}

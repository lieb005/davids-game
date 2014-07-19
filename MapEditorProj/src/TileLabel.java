import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TileLabel extends JPanel {
	int lastX = 0;
	int lastY = 70;
	public static Tile tile;
	JLabel lab;
	public static Decoration decoration;
	public static  ArrayList<Tile> tiles;
	public static ArrayList<Decoration> decor;
	
	public TileLabel(boolean t){//if true tile panel, if false, decorations
		this.setLayout(null);
		this.setBackground(Color.white);
		this.setForeground(Color.white);
		this.setPreferredSize(new Dimension(226, 660));

		if(t){
			tiles = new ArrayList<Tile>();
			lab = new JLabel("Tiles");
			lab.setBounds(70, 30, 100, 37);
			try{
				//getTiles();
			}catch(Exception e){
				System.out.println("Some tiles failed to load!");
			}
		}else{
			decor = new ArrayList<Decoration>();
			lab = new JLabel("Decorations");
			lab.setBounds(30, 30, 300, 37);
			try{
				//getDecor();
			}catch(Exception e){
				System.out.println("Some Decorations failed to load!");
			}
		}
		lab.setFont(new Font("Times", Font.PLAIN, 35));
		lab.setForeground(Color.black);
		this.add(lab);
	}
	
	
	/*public void getTiles(){
		for(int i = 0; i < Controller.getNumObj(true) -1; i++){
			tile = new Tile(i);
			tile.giveListener(false);
			if(lastX + 10 + Tile.size <= 216){
				if( i > 0){
					lastX +=  10 + Tile.size;
				}else{
					lastX += 10;
					
				}
			}else{
				lastX = 10;
				lastY += ( 10 + Tile.size);
			}
			tile.setBounds(lastX, lastY, Tile.size, Tile.size);
			tiles.add(tile);
			this.add(tiles.get(i));
		}
	}
	
	public void getDecor(){
		int startX = 0;
		int startY = 70;
		int maxHeight = 0;
		int prevW = 0;
		for(int i = 0; i < Controller.getNumObj(false); i++){
			decoration = new Decoration(i);
			decoration.giveListener(false);
			int w = decoration.img.getWidth();
			int h = decoration.img.getHeight();
			if(h > maxHeight){
				maxHeight = h;
			}
			if(startX + 10 + prevW + w<= 216){
				if( i > 0){
					startX += 10 + prevW;
				}else{
					startX += 10;
					prevW = w;
				}
			}else{
				startX = 10;
				startY += (10 + maxHeight);
				maxHeight = 0;
			}
			decoration.setBounds(startX, startY, w, h);
			prevW = w;
			decor.add(decoration);
			this.add(decor.get(i));
		}
	}*/
	/*
	public static void blankBorders(){
		
		for(Tile t : TileLabel.tiles){
			t.setBorder(null);
			t.selected = false;
		}
		for(Decoration d : TileLabel.decor){
			d.setBorder(null);
			d.selected = false;
		}
		
	}*/
}

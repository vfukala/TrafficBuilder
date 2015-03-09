package screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Calendar;

import javax.swing.Timer;

import mainPackage.Functions;
import mainPackage.StringDraw;
import mainPackage.Variables;
import screens.city.CityType;

public class LoadCity {
	static String[] names;
	static Calendar[] lastPlays;
	static String[] folders;
	static double listPosition;
	private static ActionListener scrollUp1 = new ActionListener(){
		@Override
		public void actionPerformed(final ActionEvent arg0)
		{
			listPosition = listPosition - 0.05;
			final Polygon theUpPolygon = new Polygon(new int[]{Variables.width - Variables.width / 48, Variables.width - Variables.width / 80, Variables.width - Variables.width / 240},
					new int[]{(int) (Variables.width / 240 + Variables.height / 6 + Math.sqrt(3 * Math.pow(Variables.width / 120, 2))), Variables.width / 240 + Variables.height / 6, (int) (Variables.width / 240 + Variables.height / 6 + Math.sqrt(3 * Math.pow(Variables.width / 120, 2)))}, 3);
			if(theUpPolygon.contains(Variables.lastMousePosition) == false){
				scrollUp.stop();
			}
			if(listPosition < 0){
				listPosition = 0;
				scrollUp.stop();
			}
			Variables.gui.repaint();
		}
	};
	static Timer scrollUp = new Timer(25, scrollUp1);
	private static ActionListener scrollDown1 = new ActionListener(){
		@Override
		public void actionPerformed(final ActionEvent arg0)
		{
			listPosition = listPosition + 0.05;
			final Polygon theDownPolygon = new Polygon(new int[]{Variables.width - Variables.width / 48, Variables.width - Variables.width / 80, Variables.width - Variables.width / 240},
					new int[]{(int) (Variables.height - Variables.width / 240 - Math.sqrt(3 * Math.pow(Variables.width / 120, 2))), Variables.height - Variables.width / 240, (int) (Variables.height - Variables.width / 240 - Math.sqrt(3 * Math.pow(Variables.width / 120, 2)))}, 3);
			if(theDownPolygon.contains(Variables.lastMousePosition) == false){
				scrollDown.stop();
			}
			if(listPosition > names.length - 0.5){
				listPosition = names.length - 0.5;
				scrollUp.stop();
			}
			Variables.gui.repaint();
		}
	};
	static Timer scrollDown = new Timer(25, scrollDown1);

	public static void paint(final Graphics g){
		final Graphics2D graph2 = (Graphics2D) g;
		graph2.setColor(new Color(40, 40, 40));
		graph2.fillRect(0, 0, Variables.width, Variables.height / 6);
		graph2.setColor(Color.WHITE);
		StringDraw.drawMaxString(graph2, "Load your city", new Rectangle(Variables.width / 8, Variables.height / 48, Variables.width / 4 * 3, Variables.height / 8));
		Functions.drawChangRect(graph2, Color.white, Color.lightGray, Variables.width - Variables.width / 40, Variables.height / 6, Variables.width / 40, Variables.height - Variables.height / 6);
		final Polygon theUpPolygon = new Polygon(new int[]{Variables.width - Variables.width / 48, Variables.width - Variables.width / 80, Variables.width - Variables.width / 240},
				new int[]{(int) (Variables.width / 240 + Variables.height / 6 + Math.sqrt(3 * Math.pow(Variables.width / 120, 2))), Variables.width / 240 + Variables.height / 6, (int) (Variables.width / 240 + Variables.height / 6 + Math.sqrt(3 * Math.pow(Variables.width / 120, 2)))}, 3);
		if(theUpPolygon.contains(Variables.lastMousePosition)){
			graph2.setColor(Color.white);
		}
		else{
			graph2.setColor(Color.DARK_GRAY);
		}
		graph2.fillPolygon(theUpPolygon);
		final Polygon theDownPolygon = new Polygon(new int[]{Variables.width - Variables.width / 48, Variables.width - Variables.width / 80, Variables.width - Variables.width / 240},
				new int[]{(int) (Variables.height - Variables.width / 240 - Math.sqrt(3 * Math.pow(Variables.width / 120, 2))), Variables.height - Variables.width / 240, (int) (Variables.height - Variables.width / 240 - Math.sqrt(3 * Math.pow(Variables.width / 120, 2)))}, 3);
		if(theDownPolygon.contains(Variables.lastMousePosition)){
			graph2.setColor(Color.white);
		}
		else{
			graph2.setColor(Color.DARK_GRAY);
		}
		graph2.fillPolygon(theDownPolygon);
		graph2.setColor(Color.black);
		if(names.length == 0){
			StringDraw.drawMaxString(graph2, "You did not create any city yet", StringDraw.Up, new Rectangle(Variables.width / 8, Variables.height * 3 / 12, Variables.width * 3 / 4, Variables.height / 6));
			Rectangle button = new Rectangle(Variables.width / 2 - Variables.width / 12, Variables.height * 5 / 12, Variables.width / 6, Variables.height / 16);
			Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), button);
			graph2.setColor(Color.white);
			StringDraw.drawMaxString(graph2, Variables.width / 128, "Create one", button);
		}
		else{
			final int gapSize;
			final int cityBlockWidth;
			final boolean fullBlocks;
			if(Variables.width - Variables.width / 40 > 900){
				cityBlockWidth = (Variables.width - Variables.width / 40) / 2;
				gapSize = cityBlockWidth / 3 / 10;
				fullBlocks = false;
			}
			else{
				gapSize = (Variables.width - Variables.width / 40) / 32;
				cityBlockWidth = (Variables.width - Variables.width / 40) - 2 * gapSize;
				fullBlocks = true;
			}
			int lastPainted = (int) Math.floor(listPosition);
			int spaceUsed = (int) ((cityBlockWidth / 3 + gapSize) * (1 - (listPosition % 1)));
			graph2.setFont(Variables.nowUsingFont.deriveFont(101f));
			Rectangle s1Size = StringDraw.getStringBounds(graph2, names[lastPainted], 0, 0);
			Double s1Per1Height = ((double) s1Size.height) / 101;
			graph2.setFont(Variables.nowUsingFont.deriveFont((float) (cityBlockWidth / 6 / s1Per1Height)));
			String textToPaint;
			if(StringDraw.getStringBounds(graph2, names[lastPainted], 0, 0).width <= cityBlockWidth - cityBlockWidth / 18){
				textToPaint = names[lastPainted];
			}
			else{
				int counter = 1;
				while(StringDraw.getStringBounds(graph2, names[lastPainted].substring(0, counter + 1) + "...", 0, 0).width <= cityBlockWidth - cityBlockWidth / 18){
					counter++;
					if(counter + 1 > names[lastPainted].length()){
						break;
					}
				}
				textToPaint = names[lastPainted].substring(0, counter) + "...";
			}
			s1Size = StringDraw.getStringBounds(graph2, textToPaint, 0, 0);
			if(fullBlocks == true){
				if(Variables.lastMousePosition.y > Variables.height / 6){
					Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), gapSize, Variables.height / 6 + spaceUsed - cityBlockWidth / 3, cityBlockWidth, cityBlockWidth / 3);
				}
				else{
					graph2.setColor(Color.black);
					graph2.fillRect(gapSize, Variables.height / 6 + spaceUsed - cityBlockWidth / 3, cityBlockWidth, cityBlockWidth / 3);
				}
				graph2.setColor(Color.white);
				graph2.drawString(textToPaint, gapSize + cityBlockWidth / 36, Variables.height / 6 + spaceUsed - cityBlockWidth / 3 + cityBlockWidth * 2 / 9 - cityBlockWidth / 36 - s1Size.y - s1Size.height);
			}
			else{
				if(Variables.lastMousePosition.y > Variables.height / 6){
					Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), cityBlockWidth / 2, Variables.height / 6 + spaceUsed - cityBlockWidth / 3, cityBlockWidth, cityBlockWidth / 3);
				}
				else{
					graph2.setColor(Color.black);
					graph2.fillRect(cityBlockWidth / 2, Variables.height / 6 + spaceUsed - cityBlockWidth / 3, cityBlockWidth, cityBlockWidth / 3);
				}
				graph2.setColor(Color.white);
				graph2.drawString(textToPaint, cityBlockWidth / 2 + cityBlockWidth / 36, Variables.height / 6 + spaceUsed - cityBlockWidth / 3 + cityBlockWidth * 2 / 9 - cityBlockWidth / 36 - s1Size.y - s1Size.height);
			}
			graph2.setFont(Variables.nowUsingFont.deriveFont(Font.PLAIN, 101f));
			s1Size = StringDraw.getStringBounds(graph2, "Last Played: " + calendarToString(lastPlays[lastPainted]), 0, 0);
			Double s1Per1Width = ((double) s1Size.width) / 101;
			s1Per1Height = ((double) s1Size.height) / 101;
			if(s1Per1Width / s1Per1Height > (cityBlockWidth - cityBlockWidth / 36) / (cityBlockWidth / 9 - cityBlockWidth / 36)){
				graph2.setFont(Variables.nowUsingFont.deriveFont(Font.PLAIN, (float) ((cityBlockWidth - cityBlockWidth / 36) / s1Per1Width)));
			}
			else{
				graph2.setFont(Variables.nowUsingFont.deriveFont(Font.PLAIN, (float) ((cityBlockWidth / 9 - cityBlockWidth / 36) / s1Per1Height)));
			}
			s1Size = StringDraw.getStringBounds(graph2, "Last Played: " + calendarToString(lastPlays[lastPainted]), 0, 0);
			if(fullBlocks == true){
				graph2.drawString("Last Played: " + calendarToString(lastPlays[lastPainted]), gapSize + cityBlockWidth / 72 , Variables.height / 6 + spaceUsed - cityBlockWidth / 72 - s1Size.height - s1Size.y);
			}
			else{
				graph2.drawString("Last Played: " + calendarToString(lastPlays[lastPainted]), cityBlockWidth / 2 + cityBlockWidth / 72 , Variables.height / 6 + spaceUsed - cityBlockWidth / 72 - s1Size.height - s1Size.y);
			}
			while(lastPainted != names.length - 1 && spaceUsed < Variables.height - Variables.height / 6){
				graph2.setFont(Variables.nowUsingFont.deriveFont(101f));
				s1Size = StringDraw.getStringBounds(graph2, names[lastPainted + 1], 0, 0);
				s1Per1Height = ((double) s1Size.height) / 101;
				graph2.setFont(Variables.nowUsingFont.deriveFont((float) ((cityBlockWidth / 9 * 2 - cityBlockWidth / 18) / s1Per1Height)));
				if(StringDraw.getStringBounds(graph2, names[lastPainted + 1], 0, 0).width <= cityBlockWidth - cityBlockWidth / 18){
					textToPaint = names[lastPainted + 1];
				}
				else{
					int counter = 1;
					while(StringDraw.getStringBounds(graph2, names[lastPainted + 1].substring(0, counter + 1) + "...", 0, 0).width <= cityBlockWidth - cityBlockWidth / 18){
						counter++;
						if(counter + 1 > names[lastPainted + 1].length()){
							break;
						}
					}
					textToPaint = names[lastPainted + 1].substring(0, counter) + "...";
				}
				s1Size = StringDraw.getStringBounds(graph2, textToPaint, 0, 0);
				if(fullBlocks == true){
					Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), gapSize, spaceUsed + Variables.height / 6 + gapSize, cityBlockWidth, cityBlockWidth / 3);
					graph2.setColor(Color.white);
					graph2.drawString(textToPaint, gapSize + cityBlockWidth / 36, Variables.height / 6 + spaceUsed + cityBlockWidth * 2 / 9 - cityBlockWidth / 36 - s1Size.y - s1Size.height + gapSize);
				}
				else{
					Functions.drawChangRect(graph2, Color.black, new Color(40, 40, 40), cityBlockWidth / 2, spaceUsed + Variables.height / 6 + gapSize, cityBlockWidth, cityBlockWidth / 3);
					graph2.setColor(Color.white);
					graph2.drawString(textToPaint, cityBlockWidth / 2 + cityBlockWidth / 36, Variables.height / 6 + spaceUsed + cityBlockWidth * 2 / 9 - cityBlockWidth / 36 - s1Size.y - s1Size.height + gapSize);
				}
				graph2.setFont(Variables.nowUsingFont.deriveFont(Font.PLAIN, 101f));
				s1Size = StringDraw.getStringBounds(graph2, "Last Played: " + calendarToString(lastPlays[lastPainted + 1]), 0, 0);
				s1Per1Width = ((double) s1Size.width) / 101;
				s1Per1Height = ((double) s1Size.height) / 101;
				if(s1Per1Width / s1Per1Height > (cityBlockWidth - cityBlockWidth / 36) / (cityBlockWidth / 9 - cityBlockWidth / 36)){
					graph2.setFont(Variables.nowUsingFont.deriveFont(Font.PLAIN, (float) ((cityBlockWidth - cityBlockWidth / 36) / s1Per1Width)));
				}
				else{
					graph2.setFont(Variables.nowUsingFont.deriveFont(Font.PLAIN, (float) ((cityBlockWidth / 9 - cityBlockWidth / 36) / s1Per1Height)));
				}
				s1Size = StringDraw.getStringBounds(graph2, "Last Played: " + calendarToString(lastPlays[lastPainted + 1]), 0, 0);
				if(fullBlocks == true){
					graph2.drawString("Last Played: " + calendarToString(lastPlays[lastPainted + 1]), gapSize + cityBlockWidth / 72 , Variables.height / 6 + spaceUsed + gapSize + cityBlockWidth / 3 - cityBlockWidth / 72 - s1Size.height - s1Size.y);
				}
				else{
					graph2.drawString("Last Played: " + calendarToString(lastPlays[lastPainted + 1]), cityBlockWidth / 2 + cityBlockWidth / 72 , Variables.height / 6 + spaceUsed + gapSize + cityBlockWidth / 3 - cityBlockWidth / 72 - s1Size.height - s1Size.y);
				}
				spaceUsed = spaceUsed + cityBlockWidth / 3 + gapSize;
				lastPainted++;
			}
		}
		graph2.setColor(new Color(40, 40, 40));
		graph2.fillRect(0, 0, Variables.width, Variables.height / 6);
		graph2.setColor(Color.WHITE);
		StringDraw.drawMaxString(graph2, "Load your city", new Rectangle(Variables.width / 8, Variables.height / 48, Variables.width / 4 * 3, Variables.height / 8));
		Functions.drawPauseButton(graph2, new Color(100, 100, 100));
	}

	public static void mouseClicked(final MouseEvent event){
		if(Functions.buttonClicked(event, Variables.width / 200, Variables.height / 200, Variables.width / 16, Variables.height / 24)){
			close();
			Title.load();
		}
		else if(names.length == 0){
			Rectangle button = new Rectangle(Variables.width / 2 - Variables.width / 12, Variables.height * 5 / 12, Variables.width / 6, Variables.height / 16);
			if(button.contains(event.getPoint())){
				close();
				NewCity.load(false);
			}
		}
		else if(event.getY() > Variables.height / 6){
			final int gapSize;
			final int cityBlockWidth;
			final boolean fullBlocks;
			if(Variables.width - Variables.width / 40 > 900){
				cityBlockWidth = (Variables.width - Variables.width / 40) / 2;
				gapSize = cityBlockWidth / 3 / 10;
				fullBlocks = false;
			}
			else{
				gapSize = (Variables.width - Variables.width / 40) / 32;
				cityBlockWidth = (Variables.width - Variables.width / 40) - 2 * gapSize;
				fullBlocks = true;
			}
			System.out.println();
			if((fullBlocks && (event.getX() < Variables.width - Variables.width / 40 - gapSize) && (event.getX() > gapSize)) || ((fullBlocks == false) && (event.getX() > cityBlockWidth / 2) && (event.getX() < cityBlockWidth * 1.5))){
				final double relToListClickPos =  listPosition + (double) (event.getY() - Variables.height / 6) / (double) (cityBlockWidth / 3 + gapSize);
				if(relToListClickPos % 1 > (double) (1) / 11){
					try {
						screens.city.City.load(CityType.load(folders[(int) Math.floor(relToListClickPos)]));
						screens.LoadCity.close();
					} catch (final Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void mousePressed(final MouseEvent event){
		final Polygon theUpPolygon = new Polygon(new int[]{Variables.width - Variables.width / 48, Variables.width - Variables.width / 80, Variables.width - Variables.width / 240},
				new int[]{(int) (Variables.width / 240 + Variables.height / 6 + Math.sqrt(3 * Math.pow(Variables.width / 120, 2))), Variables.width / 240 + Variables.height / 6, (int) (Variables.width / 240 + Variables.height / 6 + Math.sqrt(3 * Math.pow(Variables.width / 120, 2)))}, 3);
		final Polygon theDownPolygon = new Polygon(new int[]{Variables.width - Variables.width / 48, Variables.width - Variables.width / 80, Variables.width - Variables.width / 240},
				new int[]{(int) (Variables.height - Variables.width / 240 - Math.sqrt(3 * Math.pow(Variables.width / 120, 2))), Variables.height - Variables.width / 240, (int) (Variables.height - Variables.width / 240 - Math.sqrt(3 * Math.pow(Variables.width / 120, 2)))}, 3);
		if(theUpPolygon.contains(event.getPoint())){
			scrollUp.start();
		}
		else if(theDownPolygon.contains(event.getPoint())){
			scrollDown.start();
		}
	}

	public static void mouseReleased(final MouseEvent event){
		scrollUp.stop();
		scrollDown.stop();
	}

	public static void load(){
		Variables.InLoadCity = true;
		final File directory = new File(System.getenv("APPDATA") + "\\TrafficBuilder\\Saves");
		final File[] listed = directory.listFiles();
		listPosition = 0;
		if(listed.length == 0){
			names = new String[0];
			lastPlays = new Calendar[0];
			folders = new String[0];
		}
		else{
			names = new String[listed.length];
			lastPlays = new Calendar[listed.length];
			folders = new String[listed.length];
			final String[] readedNames = new String[listed.length];
			final Calendar[] readedDates = new Calendar[listed.length];
			final String[] readedFolders = new String[listed.length];
			final int[] used = new int[listed.length];
			int amountOfUsed = 0;
			int counter = 0;
			while(used.length > counter){
				used[counter] = used.length;
				counter++;
			}
			counter = 0;
			while(listed.length > counter){
				readedNames[counter] = Functions.readTextFile(listed[counter].toString() + "\\name.txt");
				try {
					readedDates[counter] = bytToCalendar(Functions.readBytes(listed[counter].toString() + "\\lastPlay.byt"));
				} catch (final Throwable e) {
					e.printStackTrace();
				}
				int counter2 = listed[counter].toString().length() - 1;
				while(listed[counter].toString().toCharArray()[counter2] != '\\'){
					counter2--;
				}
				readedFolders[counter] = listed[counter].toString().substring(counter2 + 1);
				counter++;
			}
			while(amountOfUsed < used.length){
				counter = 0;
				int biggestFound = 0;
				long biggestFoundValue = Long.MIN_VALUE;
				while(readedDates.length > counter){
					if(Functions.contains(used, counter) == false){
						if(readedDates[counter].getTimeInMillis() > biggestFoundValue){
							biggestFoundValue = readedDates[counter].getTimeInMillis();
							biggestFound = counter;
						}
					}
					counter++;
				}
				names[amountOfUsed] = readedNames[biggestFound];
				lastPlays[amountOfUsed] = readedDates[biggestFound];
				folders[amountOfUsed] = readedFolders[biggestFound];
				used[amountOfUsed] = biggestFound;
				amountOfUsed++;
			}
		}
	}

	static Calendar bytToCalendar(final byte[] bytes){
		final Calendar result = Calendar.getInstance();
		result.set(Calendar.YEAR, bytes[0] + 2000);
		result.set(Calendar.MONTH, bytes[1]);
		result.set(Calendar.DAY_OF_MONTH, bytes[2]);
		result.set(Calendar.HOUR_OF_DAY, bytes[3]);
		result.set(Calendar.MINUTE, bytes[4]);
		result.set(Calendar.SECOND, bytes[5]);
		return result;
	}

	public static void close(){
		Variables.InLoadCity = false;
		scrollUp.stop();
		scrollDown.stop();
	}

	public static String calendarToString(final Calendar data){
		final String hour;
		final String minute;
		final String second;
		if(data.get(Calendar.HOUR_OF_DAY) < 10){
			hour = "0" + String.valueOf(data.get(Calendar.HOUR_OF_DAY));
		}
		else{
			hour = String.valueOf(data.get(Calendar.HOUR_OF_DAY));
		}
		if(data.get(Calendar.MINUTE) < 10){
			minute = "0" + String.valueOf(data.get(Calendar.MINUTE));
		}
		else{
			minute = String.valueOf(data.get(Calendar.MINUTE));
		}
		if(data.get(Calendar.SECOND) < 10){
			second = "0" + String.valueOf(data.get(Calendar.SECOND));
		}
		else{
			second = String.valueOf(data.get(Calendar.SECOND));
		}
		return String.valueOf(data.get(Calendar.MONTH) + 1) + "/" + String.valueOf(data.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(data.get(Calendar.YEAR)) + " " + hour + ":" + minute + ":" + second;
	}
}
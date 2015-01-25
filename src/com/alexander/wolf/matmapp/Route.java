package com.alexander.wolf.matmapp;
import java.util.*;
public class Route {

	private String routeName;
	private ArrayList<String> locNames = new ArrayList<String>();
	private ArrayList<Double> latitudes = new ArrayList<Double>();
	private ArrayList<Double> longitudes = new ArrayList<Double>();
	private ArrayList<String> arrivalTimes = new ArrayList<String>();
	private String startServiceTime = "0";
	private String endServiceTime = "0";
	
	public String getName(){
		return routeName;
	}
	
	public void setName(String name){
		routeName = name;
	}
	
	public void addLocation(String name, Double lat, Double lon, String time){
		locNames.add(name);
		latitudes.add(lat);
		longitudes.add(lon);
		arrivalTimes.add(time);
	}
	
	//add several locations at once through a series of String arrays
	public void addLocations(String[][] locations){
		for(int x = 0; x < locations.length; x++){
			locNames.add(locations[x][0]);
			latitudes.add(Double.parseDouble(locations[x][1]));
			longitudes.add(Double.parseDouble(locations[x][2]));
			arrivalTimes.add(locations[x][3]);
		}
	}
	
	//Calculates distance between coordinates
	private Double getDistance(Double lat1, Double lon1, Double lat2, Double lon2){
		Double theta = lon1 - lon2;
    	Double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
    	dist = Math.acos(dist);
    	dist = rad2deg(dist);
    	dist = dist*60*1.1515;
    	return dist;
	}
	
	private Double deg2rad(Double deg){
    	return(deg*Math.PI / 180.0);
    }
    private Double rad2deg(Double rad){
    	return(rad*180/Math.PI);
    }
	
    //returns List with info about the Location nearest to the user.
	public ArrayList<String> getNearestLocation(Double lat, Double lon){
		ArrayList<String> nearest = new ArrayList<String>();
		Double shortest = 1000000.0; //arbitrarily large because I have to initialize variable to a value.
		int index = 0;
		for(int x = 0; x < latitudes.size(); x++){
			Double dist = getDistance(latitudes.get(x), lat, longitudes.get(x), lon);
			if (dist < shortest){
				shortest = dist;
				index = x;
			}
		}
		nearest.add(locNames.get(index));
		nearest.add(latitudes.get(index).toString());
		nearest.add(longitudes.get(index).toString());
		nearest.add(arrivalTimes.get(index));
		nearest.add(shortest.toString());
		return nearest;
	}
	
	public ArrayList<String> getLocationNames(){
		return locNames;
	}
	
	public void setStartServiceTime (String start){
		startServiceTime = start;
	}
	
	public String getStartServiceTime (){
		return startServiceTime;
	}
	
	public void setEndServiceTime (String end){
		endServiceTime = end;
	}
	
	public String getEndServiceTime (){
		return endServiceTime;
	}
	
}

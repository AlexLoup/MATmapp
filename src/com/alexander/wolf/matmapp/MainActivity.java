package com.alexander.wolf.matmapp;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import android.content.Context;
import android.location.*;

// -------------- TO DO ---------------
// Double check that Spinners are set up correctly
// Update the UI with the correct info


public class MainActivity extends Activity {

	
	TextView mTextViewLocation;
	TextView mTextViewDistance;
	TextView mTextViewArrival;
	TextView mTextViewDeparts;
	TextView mTextViewSelectLocation;
	Spinner mSpinView;
	Spinner mSpinRoutes;
	Spinner mSpinLocations;
	Button mButtonStartStop;
	
	double latitude = 0;
	double longitude = 0;
	Route route13 = new Route();
	Route route13u = new Route();
	Route route31 = new Route();
	Route route34 = new Route();
	ArrayList<Object> routeList = new ArrayList<Object>();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                
        route13.setName("Route 13");
        route13u.setName("Route 13U");
        route31.setName("Route 31");
        route34.setName("Route 34");
        final ArrayList<Route> allRoutesList = new ArrayList<Route>();
        allRoutesList.add(route13);
        allRoutesList.add(route13u);
        allRoutesList.add(route31);
        allRoutesList.add(route34);
        //fill Route objects with known Location informations.
        fillRoutes();
        
        mTextViewLocation = (TextView)findViewById(R.id.textViewLocation);
        mTextViewDistance = (TextView)findViewById(R.id.textViewDistance);
        mTextViewArrival = (TextView)findViewById(R.id.textViewArrival);
        mTextViewDeparts = (TextView)findViewById(R.id.textViewDeparts);
        mTextViewSelectLocation = (TextView)findViewById(R.id.textViewSelectLocation);
        mSpinLocations = (Spinner)findViewById(R.id.spinLocations);
        mButtonStartStop = (Button)findViewById(R.id.btnStartStop);
        
        //Spinner that lists routes
        mSpinRoutes = (Spinner)findViewById(R.id.spinRoutes);
        List<String> routeNames = new ArrayList<String>();
        for(Route x: allRoutesList){
        	routeNames.add(x.getName());
        }
        ArrayAdapter<String> routesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, routeNames);
        routesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinRoutes.setAdapter(routesArrayAdapter);
        updateUI(allRoutesList.get(0)); //fills mSpinLocations with data from first route in list
        mSpinRoutes.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				updateUI(allRoutesList.get(mSpinRoutes.getSelectedItemPosition()));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        //End mSpinRoutes
        
        //Spinner that filters routes
        mSpinView = (Spinner)findViewById(R.id.spinView);
        List<String> viewTypes = new ArrayList<String>();
        viewTypes.add("Nearest Bus Stop (default)");
        viewTypes.add("Specific Bus Stop");
        ArrayAdapter<String> viewArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, viewTypes);
        viewArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinView.setAdapter(viewArrayAdapter);
        //This listener hides/shows the Locations Spinner as necessary.
        mSpinView.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
            	String selectedPrio = mSpinView.getSelectedItem().toString();
            	if(selectedPrio.equals("Specific Bus Stop")){
            		mSpinLocations.setVisibility(View.VISIBLE);
            		mTextViewSelectLocation.setVisibility(View.VISIBLE);
            	}else{
            		mSpinLocations.setVisibility(View.GONE);
            		mTextViewSelectLocation.setVisibility(View.GONE);
            	}				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}

        });
        //End spinView
        
        //Spinner that lists bus stop locations
        mSpinLocations = (Spinner)findViewById(R.id.spinLocations);
        //End mSpinLocations
        
        final LocationManager lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        final LocationListener locationListener = new LocationListener() {
        	@Override
        	public void onLocationChanged(Location location){
        		if (location != null){
        			latitude = location.getLatitude();
        			longitude = location.getLongitude();
        		}
        	}

    		@Override
    		public void onStatusChanged(String provider, int status, Bundle extras) {
    			// TODO Auto-generated method stub
    			
    		}

    		@Override
    		public void onProviderEnabled(String provider) {
    			// TODO Auto-generated method stub
    			
    		}

    		@Override
    		public void onProviderDisabled(String provider) {
    			// TODO Auto-generated method stub
    			
    		}
        };
        //End mSpinLocations
        
        mButtonStartStop.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (lm.isProviderEnabled(lm.GPS_PROVIDER) == true){
					lm.removeUpdates(locationListener);
				}
				else {
					lm.requestLocationUpdates(lm.GPS_PROVIDER, 0, 0, locationListener);
				}
			}
		});
    }

    private void updateUI(Route route){
    	
    	List<String> routeLocs = new ArrayList<String>();
        for(String x: route.getLocationNames()){
        	routeLocs.add(x);
        }
        ArrayAdapter<String> locsAdapterArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, routeLocs);
        locsAdapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinView.setAdapter(locsAdapterArray);
    	
    }
    
    private void fillRoutes(){
    	
    	//I have collected this information from MATBUS and Google Maps
    	//Map coordinates had to be collected manually, the process of which
    	//is extremely tedious, so I only did 4 locations.
    	
    	String[][] route34Info = {{"Memorial Union", "46.891874, -96.800060", "44"},
				{"Reed/Johnson", "46.897167, -96.802763", "47"},
				{"Fargodome", "46.901105, -96.802720", "48"},
				{"Skills/Tech Center", "46.905972, -96.799177", "50"},
				{"Stop N Go", "46.905610, -96.798015", "52"},
				{"Niskanen", "46.903249, -96.797950", "57"},
				{"Centennial", "46.894366, -96.798128", "59"},
				{"Memorial Union", "46.891874, -96.800060", "04"},
				{"Reed/Johnson", "46.897167, -96.802763", "07"},
				{"Fargodome", "46.901105, -96.802720", "08"},
				{"Skills/Tech Center", "46.905972, -96.799177", "10"},
				{"Stop N Go", "46.905610, -96.798015", "12"},
				{"Niskanen", "46.903249, -96.797950", "17"},
				{"Centennial", "46.894366, -96.798128", "19"},
				{"Memorial Union", "46.891874, -96.800060", "24"},
				{"Reed/Johnson", "46.897167, -96.802763", "27"},
				{"Fargodome", "46.901105, -96.802720", "28"},
				{"Skills/Tech Center", "46.905972, -96.799177", "30"},
				{"Stop N Go", "46.905610, -96.798015", "32"},
				{"Niskanen", "46.903249, -96.797950", "37"},
				{"Centennial", "46.894366, -96.798128", "39"}};
    	route34.addLocations(route34Info);
    	route34.setStartServiceTime("0744");
    	route34.setEndServiceTime("0500");

    	
    	String[][] route13UInfo = 	{{"Memorial Union", "46.8919416, -96.8002748", "45"},
    			{"Sunmart", "46.883697, -96.798200", "49"},
    			{"Renaissance Hall", "46.875905, -96.790669", "54"},
    			{"GTC", "46.875905, -96.790669", "00"},
    			{"Klai Hall", "46.878474, -96.790958", "04"},
    			{"Barry Hall", "46.878415, -96.793672", "05"},
    			{"Niskanen", "46.902218, -96.798028", "12"},
    			{"University & Centennial", "46.894350, -96.798100", "14"},
    			{"Memorial Union", "46.8919416, -96.8002748", "15"},
    			{"Sunmart", "46.883697, -96.798200", "19"},
    			{"Renaissance Hall", "46.875905, -96.790669", "24"},
    			{"GTC", "46.875905, -96.790669", "30"},
    			{"Klai Hall", "46.878474, -96.790958", "34"},
    			{"Barry Hall", "46.878415, -96.793672", "35"},
    			{"Niskanen", "46.902218, -96.798028", "42"},
    			{"University & Centennial", "46.894350, -96.798100", "44"}};
    	route13u.addLocations(route13UInfo);
    	route13u.setStartServiceTime("0642");
    	route13u.setEndServiceTime("2315");
    	
    	String[][] route13Info = {{"GTC",		"46.875702, -96.785586",	"15"},
				{"Klai Hall",	"46.878522, -96.791368",	"17"},
				{"Barry Hall",	"46.878575, -96.793299",	"20"},
				{"Minard Hall",	"46.891796, -96.802103",	"32"},
				{"10th St & 12th Ave",	"46.890400, -96.793772",	"37"},
				{"Skills & Tech",	"46.906408, -96.798038",	"49"},
				{"Bison Sports Arena",	"46.900552, -96.798001",	"50"},
				{"Memorial Union",	"46.891919, -96.800288",	"58"},
				{"Renaissance Hall",	"46.875876, -96.790577",	"10"},
				{"GTC",		"46.875702, -96.785586",	"11"},
				{"GTC",		"46.875702, -96.785586",	"45"},
				{"Klai Hall",	"46.878522, -96.791368",	"47"},
				{"Barry Hall",	"46.878575, -96.793299",	"50"},
				{"Minard Hall",	"46.891796, -96.802103",	"02"},
				{"10th St & 12th Ave",	"46.890400, -96.793772",	"07"},
				{"Skills & Tech",	"46.906408, -96.798038",	"19"},
				{"Bison Sports Arena",	"46.900552, -96.798001",	"20"},
				{"Memorial Union",	"46.891919, -96.800288",	"28"},
				{"Renaissance Hall:",	"46.875876, -96.790577",	"40"},
				{"GTC",		"46.875702, -96.785586",	"41"}};
    	route13.addLocations(route13Info);
    	route13.setStartServiceTime("0615");
    	route13.setEndServiceTime("2311");
    	
    	String [][] route31Info =  {{"Minard Hall",		"46.891972, -96.802421",	"25"},
				{"Thoreson Hall",	"46.893064, -96.804116",	"26"},
				{"Stevens Hall",		"46.894157, -96.804588",	"27"},
				{"Wellness Center",	"46.894223, -96.808300",	"28"},
				{"Candlewood Suites",	"46.904392, -96.805996",	"30"},
				{"Research Tech Park",	"46.902457, -96.805385",	"31"},
				{"Fargodome",		"46.901137, -96.802896",	"33"},
				{"High Rises",		"46.897136, -96.802854",	"34"},
				{"Loftsgard Hall",	"46.895552, -96.802897",	"35"},
				{"Sheppard Arena",	"46.894247, -96.802843",	"36"},
				{"Minard Hall",		"46.891972, -96.802421",	"37"},
				{"Minard Hall",		"46.891972, -96.802421",	"40"},
				{"Thoreson Hall",	"46.893064, -96.804116",	"41"},
				{"Stevens Hall",		"46.894157, -96.804588",	"42"},
				{"Wellness Center",	"46.894223, -96.808300",	"43"},
				{"Candlewood Suites",	"46.904392, -96.805996",	"45"},
				{"Research Tech Park",	"46.902457, -96.805385",	"46"},
				{"Fargodome",		"46.901137, -96.802896",	"48"},
				{"High Rises",		"46.897136, -96.802854",	"49"},
				{"Loftsgard Hall",	"46.895552, -96.802897",	"50"},
				{"Sheppard Arena",	"46.894247, -96.802843",	"51"},
				{"Minard Hall",		"46.891972, -96.802421",	"52"},
				{"Minard Hall",		"46.891972, -96.802421",	"55"},
				{"Thoreson Hall",	"46.893064, -96.804116",	"56"},
				{"Stevens Hall",		"46.894157, -96.804588",	"57"},
				{"Wellness Center",	"46.894223, -96.808300",	"58"},
				{"Candlewood Suites",	"46.904392, -96.805996",	"00"},
				{"Research Tech Park",	"46.902457, -96.805385",	"01"},
				{"Fargodome",		"46.901137, -96.802896",	"03"},
				{"High Rises",		"46.897136, -96.802854",	"04"},
				{"Loftsgard Hall",	"46.895552, -96.802897",	"05"},
				{"Sheppard Arena",	"46.894247, -96.802843",	"06"},
				{"Minard Hall",		"46.891972, -96.802421",	"07"},
				{"Minard Hall",		"46.891972, -96.802421",	"10"},
				{"Thoreson Hall",	"46.893064, -96.804116",	"11"},
				{"Stevens Hall",		"46.894157, -96.804588",	"12"},
				{"Wellness Center",	"46.894223, -96.808300",	"13"},
				{"Candlewood Suites",	"46.904392, -96.805996",	"15"},
				{"Research Tech Park",	"46.902457, -96.805385",	"16"},
				{"Fargodome",		"46.901137, -96.802896",	"18"},
				{"High Rises",		"46.897136, -96.802854",	"19"},
				{"Loftsgard Hall",	"46.895552, -96.802897",	"20"},
				{"Sheppard Arena",	"46.894247, -96.802843",	"21"},
				{"Minard",		"46.891972, -96.802421",	"22"}};
    	route31.addLocations(route31Info);
    	route34.setStartServiceTime("0725");
    	route34.setEndServiceTime("0752");
    }
    
}

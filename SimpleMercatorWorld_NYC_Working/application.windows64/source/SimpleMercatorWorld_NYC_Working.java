import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SimpleMercatorWorld_NYC_Working extends PApplet {

PImage worldMapImage;
MercatorMap mercatorMap;

Table table;

PVector ballDot;

float lat = 40.712f;
float lng = -74.005f;
PVector newYork1;
int z = 0;

public void setup() {
  size(800, 800);
  frameRate(1);
  smooth();
  worldMapImage = loadImage("nyc_black_gray.png");
  mercatorMap = new MercatorMap(800, 800, 40.9171f, 40.4945f, -74.2566f, -73.699f );
  
  newYork1 = mercatorMap.getScreenLocation(new PVector(lat,lng));

}

public void draw() {
  image(worldMapImage, 0, 0, width, height);
  
for (int x = 1; x < 4; x = x + 1){  
  
  table = loadTable("p"+x+".csv","header");
  println(table.getRowCount() + " total rows in table");
  TableRow rowRecord = table.getRow(z);
  println(rowRecord.getFloat("x"));  
  println(rowRecord.getFloat("y"));
  println(rowRecord.getInt("acc"));
  Float xC = rowRecord.getFloat("x");
  Float yC = rowRecord.getFloat("y");
  int aC = rowRecord.getInt("acc");
  //for (TableRow row : table.rows()) {


    //println("Lat is " + yC + " Lng is " + xC);

  //}
  
  ballDot = mercatorMap.getScreenLocation(new PVector(yC,xC));
  ////noStroke();
  fill(255, 0, 0, 200);
  ellipse(ballDot.x, ballDot.y, 10, 10);
  
  //Float xC = table.row.getFloat("x");
  //Float yC = table.row.getFloat("y");
}
  //noStroke();
  //below is old school move from city hall -> SE
  //fill(0, 255, 0, 200);
  //ellipse(newYork1.x, newYork1.y, 30, 30);
  
  newYork1.x = newYork1.x + 1;
  newYork1.y = newYork1.y + 1;  
  
  // wanna saw if table row count is less than Z add 1, if not stop
  z = z + 1;
}
/**
 * Utility class to convert between geo-locations and Cartesian screen coordinates.
 * Can be used with a bounding box defining the map section.
 *
 * (c) 2011 Till Nagel, tillnagel.com
 */
public class MercatorMap {
  
  public static final float DEFAULT_TOP_LATITUDE = 80;
  public static final float DEFAULT_BOTTOM_LATITUDE = -80;
  public static final float DEFAULT_LEFT_LONGITUDE = -180;
  public static final float DEFAULT_RIGHT_LONGITUDE = 180;
  
  /** Horizontal dimension of this map, in pixels. */
  protected float mapScreenWidth;
  /** Vertical dimension of this map, in pixels. */
  protected float mapScreenHeight;

  /** Northern border of this map, in degrees. */
  protected float topLatitude;
  /** Southern border of this map, in degrees. */
  protected float bottomLatitude;
  /** Western border of this map, in degrees. */
  protected float leftLongitude;
  /** Eastern border of this map, in degrees. */
  protected float rightLongitude;

  private float topLatitudeRelative;
  private float bottomLatitudeRelative;
  private float leftLongitudeRadians;
  private float rightLongitudeRadians;

  public MercatorMap(float mapScreenWidth, float mapScreenHeight) {
    this(mapScreenWidth, mapScreenHeight, DEFAULT_TOP_LATITUDE, DEFAULT_BOTTOM_LATITUDE, DEFAULT_LEFT_LONGITUDE, DEFAULT_RIGHT_LONGITUDE);
  }
  
  /**
   * Creates a new MercatorMap with dimensions and bounding box to convert between geo-locations and screen coordinates.
   *
   * @param mapScreenWidth Horizontal dimension of this map, in pixels.
   * @param mapScreenHeight Vertical dimension of this map, in pixels.
   * @param topLatitude Northern border of this map, in degrees.
   * @param bottomLatitude Southern border of this map, in degrees.
   * @param leftLongitude Western border of this map, in degrees.
   * @param rightLongitude Eastern border of this map, in degrees.
   */
  public MercatorMap(float mapScreenWidth, float mapScreenHeight, float topLatitude, float bottomLatitude, float leftLongitude, float rightLongitude) {
    this.mapScreenWidth = mapScreenWidth;
    this.mapScreenHeight = mapScreenHeight;
    this.topLatitude = topLatitude;
    this.bottomLatitude = bottomLatitude;
    this.leftLongitude = leftLongitude;
    this.rightLongitude = rightLongitude;

    this.topLatitudeRelative = getScreenYRelative(topLatitude);
    this.bottomLatitudeRelative = getScreenYRelative(bottomLatitude);
    this.leftLongitudeRadians = getRadians(leftLongitude);
    this.rightLongitudeRadians = getRadians(rightLongitude);
  }

  /**
   * Projects the geo location to Cartesian coordinates, using the Mercator projection.
   *
   * @param geoLocation Geo location with (latitude, longitude) in degrees.
   * @returns The screen coordinates with (x, y).
   */
  public PVector getScreenLocation(PVector geoLocation) {
    float latitudeInDegrees = geoLocation.x;
    float longitudeInDegrees = geoLocation.y;

    return new PVector(getScreenX(longitudeInDegrees), getScreenY(latitudeInDegrees));
  }

  private float getScreenYRelative(float latitudeInDegrees) {
    return log(tan(latitudeInDegrees / 360f * PI + PI / 4));
  }

  protected float getScreenY(float latitudeInDegrees) {
    return mapScreenHeight * (getScreenYRelative(latitudeInDegrees) - topLatitudeRelative) / (bottomLatitudeRelative - topLatitudeRelative);
  }
  
  private float getRadians(float deg) {
    return deg * PI / 180;
  }

  protected float getScreenX(float longitudeInDegrees) {
    float longitudeInRadians = getRadians(longitudeInDegrees);
    return mapScreenWidth * (longitudeInRadians - leftLongitudeRadians) / (rightLongitudeRadians - leftLongitudeRadians);
  }
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SimpleMercatorWorld_NYC_Working" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}

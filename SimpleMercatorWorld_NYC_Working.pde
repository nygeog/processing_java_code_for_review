PImage worldMapImage;
MercatorMap mercatorMap;

Table table;

PVector ballDot;

float lat = 40.712;
float lng = -74.005;
PVector newYork1;
int z = 0;

void setup() {
  size(800, 800);
  frameRate(1);
  smooth();
  worldMapImage = loadImage("nyc_black_gray.png");
  mercatorMap = new MercatorMap(800, 800, 40.9171, 40.4945, -74.2566, -73.699 );
  
  newYork1 = mercatorMap.getScreenLocation(new PVector(lat,lng));

}

void draw() {
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

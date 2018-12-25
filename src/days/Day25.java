package days;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Day25 {
    private static int constellationIdCounter = 0;
    public static void main(String[] args) {
        ArrayList<Point4D> input = readInput();
        int unlinkedPoints = 0;
        for(int i = 0; i < input.size(); i++) {
            Point4D p1 = input.get(i);
            for (int j = i+1; j < input.size(); j++) {
                Point4D p2 = input.get(j);
                if (calcManhattan(p1,p2)<=3)
                    join(p1,p2, input);
            }
            if (p1.constellation == null) {
                unlinkedPoints++;
            }
        }
        ArrayList<Integer> constellations = new ArrayList<Integer>(500);
        for (Point4D p: input) {
            if (p.constellation != null && !constellations.contains(p.constellation.id))
                constellations.add(p.constellation.id);
        }
        System.out.println("Constellations: "+constellations.size()+" Unlinked points: "+unlinkedPoints+" Total: "+(unlinkedPoints+constellations.size()));
    }

    private static void join(Point4D p1, Point4D p2, ArrayList<Point4D> points) {
        if(p1.constellation == null && p2.constellation == null) {
            Constellation c = new Constellation();
            //c.points.add(p1);
            //c.points.add(p2);
            p1.constellation = c;
            p2.constellation = c;
        } else if (p1.constellation != null && p2.constellation == null){
            //p1.constellation.points.add(p2);
            p2.constellation = p1.constellation;
        } else if (p1.constellation == null && p2.constellation != null){
            //p2.constellation.points.add(p1);
            p1.constellation = p2.constellation;
        } else { //both are in constellations
            Constellation c = new Constellation();
            int id1 = p1.constellation.id;
            int id2 = p2.constellation.id;
            //c.points.addAll(p1.constellation.points);
            //c.points.addAll(p2.constellation.points);
            for (Point4D p: points) {
                if(p.constellation != null && (p.constellation.id == id1 || p.constellation.id == id2))
                    p.constellation = c;
            }
        }

    }

    private static int calcManhattan(Point4D p1, Point4D p2) {
        return Math.abs(p1.x-p2.x)+Math.abs(p1.y-p2.y)+Math.abs(p1.z-p2.z)+Math.abs(p1.t-p2.t);
    }

    public static class Constellation {
        //ArrayList<Point4D> points = new ArrayList<Point4D>();
        int id;
        public Constellation() {
            this.id = constellationIdCounter;
            constellationIdCounter++;
        }
    }

    public static class Point4D {
        int x,y,z,t;
        Constellation constellation = null;

        public Point4D(int x,int y,int z,int t) {
            this.x=x;
            this.y=y;
            this.z=z;
            this.t=t;
        }
    }

    private static ArrayList<Point4D> readInput() {
		BufferedReader br;
		Pattern p = Pattern.compile("(?<x>-?\\d+),(?<y>-?\\d+),(?<z>-?\\d+),(?<t>-?\\d+)");
        ArrayList<Point4D> points = new ArrayList<Point4D>(1000);
		try {
            br = new BufferedReader(new FileReader("src/input/inputday25"));
            String line = br.readLine();
			while (line != null) {
                Matcher m = p.matcher(line);
                m.find();
                points.add(new Point4D(Integer.valueOf(m.group("x")),Integer.valueOf(m.group("y")),
                    Integer.valueOf(m.group("z")),Integer.valueOf(m.group("t"))));
                line = br.readLine();
            }
		} catch (Exception e) {
			e.printStackTrace();
        }
        return points;
    }
}
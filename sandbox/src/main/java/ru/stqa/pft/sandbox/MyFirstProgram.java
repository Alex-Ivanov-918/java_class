package ru.stqa.pft.sandbox;

public class MyFirstProgram {

    public static void main(String[] args) {
        System.out.println("Hello World");

        Point p1 = new Point(14.6,46.5);
        Point p2 = new Point(35.4, 95.9);

        double result = distance(p1,p2);

        System.out.println("Distance result = " + result);

        System.out.println("Distance result class = " + p1.distance(p2));
    }

    public static double distance(Point p1, Point p2){
      return Math.sqrt(Math.pow(p2.x - p1.x,2) + Math.pow(p2.y - p1.y,2));
    }
}

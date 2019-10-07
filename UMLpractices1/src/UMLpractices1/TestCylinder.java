package UMLpractices1;

import java.sql.SQLOutput;

public class TestCylinder {
    public static void main(String[] args) {
        Cylinder cylinder=new Cylinder();
        System.out.println("Cylinder:"+"radius="+cylinder.getRadius()+"height="+cylinder.getHeight()+"base area="+cylinder.getArea()+"volume="+cylinder.getVolume());
        Cylinder c2=new Cylinder();
        System.out.println("Cylinder:"+ " radius=" + c2.getRadius()
                + " height=" + c2.getHeight()
                + " base area=" + c2.getArea()
                + " volume=" + c2.getVolume());
        Cylinder c3 = new Cylinder(2.0, 10.0);
        System.out.println("Cylinder:"
                + " radius=" + c3.getRadius()             + " height=" + c3.getHeight()
                + " base area=" + c3.getArea()
                + " volume=" + c3.getVolume());

    }
}

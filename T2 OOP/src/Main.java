import base.PrintableObject;
import shapes.*;
import solids.*;

void main() {

    PrintableObject[] printableObjects ={
            new Square(3),
            new Triangle(3, 3, 3),
            new Trapezoid(5, 3, 2, 2, 1),
            new Cone(3, 5),
            new Cube(4),
            new Cylinder(4, 6)
    };

    for (PrintableObject printableObject : printableObjects){
        IO.println(printableObject.getInfo());
    }


}

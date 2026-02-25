import shapes.*;
import solids.*;

void main() {
    Square square = new Square(3);
    IO.println(square.getInfo());

    Triangle triangle = new Triangle(3,3,3);
    IO.println(triangle.getInfo());

    Trapezoid trapezoid = new Trapezoid(5, 3, 2, 2, 1);
    IO.println(trapezoid.getInfo());

    Cone cone = new Cone(3, 5);
    IO.println(cone.getInfo());

    Cube cube = new Cube(4);
    IO.println(cube.getInfo());

    Cylinder cylinder = new Cylinder(4, 6);
    IO.println(cylinder.getInfo());

}

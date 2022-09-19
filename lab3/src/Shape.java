public abstract class Shape implements Drawable {
    protected String shapeColor;

    @Override
    public void draw() {
        System.out.println("Draw nothing");
    }

    public abstract double calcArea();

    public Shape(String shapeColor) {
        this.shapeColor = shapeColor;
    }

    @Override
    public String toString() {
        return "Shape{" +
                "shapeColor='" + shapeColor + '\'' +
                '}';
    }
}
class Rectangle extends Shape {
    double sideA;
    double sideB;

    @Override
    public double calcArea() {
        return sideA*sideB;
    }

    public Rectangle(String shapeColor, double sideA, double sideB) {
        super(shapeColor);
        this.sideA = sideA;
        this.sideB = sideB;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "shapeColor='" + shapeColor + '\'' +
                ", sideA=" + sideA +
                ", sideB=" + sideB +
                '}';
    }
}

class Circle extends Shape {
    double radius;

    public Circle(String shapeColor, double radius) {
        super(shapeColor);
        this.radius = radius;
    }

    @Override
    public double calcArea() {
        return Math.PI*Math.pow(radius,2);
    }

    @Override
    public String toString() {
        return "Circle{" +
                "shapeColor='" + shapeColor + '\'' +
                ", radius=" + radius +
                '}';
    }
}

class Triangle extends Shape {
    double sideA;
    double sideB;
    double sideC;

    public Triangle(String shapeColor, double sideA, double sideB, double sideC) {
        super(shapeColor);
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    @Override
    public double calcArea() {
        double p = (sideA + sideB + sideC)/2;
        return  Math.sqrt(p*(p-sideA)*(p-sideB)*(p-sideC));
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "shapeColor='" + shapeColor + '\'' +
                ", sideA=" + sideA +
                ", sideB=" + sideB +
                ", sideC=" + sideC +
                '}';
    }
}
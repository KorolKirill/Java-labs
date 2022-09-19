public abstract class Shape implements Drawable {
    protected String shapeColor;
    public abstract int calcArea();

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

class Triangle extends Shape {

    public Triangle(String shapeColor) {
        super(shapeColor);
    }

    @Override
    public Shape draw() {

    }

    @Override
    public int calcArea() {
        return 0;
    }
}
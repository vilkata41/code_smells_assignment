import java.util.ArrayList;
import java.util.List;

interface IVisitor
{
    void VisitWheels(Wheels wheels);
    void VisitEngine(Engine engine);
    void VisitBody(Body body);
}

class MechanicVisitor implements IVisitor{
    public void VisitWheels(Wheels wheels){
        System.out.println(wheels.getTyrePressure());
    }

    public void VisitEngine(Engine engine){
        System.out.println(engine.getOilLevel());
    }

    public void VisitBody(Body body){

    }
}

class SalesVisitor implements IVisitor{
    public void VisitWheels(Wheels wheels){

    }

    public void VisitEngine(Engine engine){
        System.out.println(engine.getEngineSize());
    }

    public void VisitBody(Body body){
        System.out.println(body.getBodyType());
    }
}



interface IComponent{
    void accept(IVisitor visitor);
}

class Wheels implements IComponent{

    private String tyrePressure;

    public Wheels(String tyrePressure){
        this.tyrePressure = tyrePressure;
    }

    public String getTyrePressure(){
        return tyrePressure;
    }

    public void setTyrePressure(String tyrePressure){
        this.tyrePressure = tyrePressure;
    }

    public void accept(IVisitor visitor)
    {
        visitor.VisitWheels(this);
    }
}

class Engine implements IComponent{
    private String oilLevel;
    private String engineSize;

    public Engine(String oilLevel, String engineSize){
        this.oilLevel = oilLevel;
        this.engineSize = engineSize;
    }

    public String getOilLevel(){
        return oilLevel;
    }

    public void setOilLevel(String oilLevel){
        this.oilLevel = oilLevel;
    }

    public String getEngineSize(){
        return engineSize;
    }

    public void setEngineSize(String engineSize){
        this.engineSize = engineSize;
    }

    public void accept(IVisitor visitor)
    {
        visitor.VisitEngine(this);
    }
}

class Body implements IComponent{
    private String bodyType;

    public Body(String bodyType){
        this.bodyType = bodyType;
    }

    public String getBodyType(){
        return bodyType;
    }

    public void setBodyType(String bodyType){
        this.bodyType = bodyType;
    }
    public void accept(IVisitor visitor)
    {
        visitor.VisitBody(this);
    }
}

class Car{
    List<IComponent> components = new ArrayList<IComponent>();

    public void add(IComponent component){
        components.add(component);
    }

    public void remove(IComponent component){
        components.remove(component);
    }

    public void accept(IVisitor visitor){
        components.forEach((component) -> component.accept(visitor));
    }

    public void Chmock(int first, String second, float third){}
}

class x extends Car{

}


public class Main {
    public static void main(String[] args) {
        Car car = new Car();
        car.add(new Wheels("High"));
        car.add(new Engine("Low", "Big"));
        car.add(new Body("SUV"));
        car.accept(new MechanicVisitor());
        car.accept(new SalesVisitor());
    }
}
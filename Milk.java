public class Milk extends CoffeeDecorator {
    public Milk(Coffee coffee) {
        super(coffee);
    }

    @Override
    public void addTopping(Coffee coffee) {
    }

    @Override
    public String printCoffee() {
        return this.coffee.printCoffee() + " with milk";
    }

    @Override
    public Double Cost(){
        double cost = 0.0;
        cost += 0.15;
        return this.coffee.Cost() + cost;
    }
}

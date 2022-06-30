public class Espresso extends CoffeeDecorator{
    public Espresso(Coffee coffee) {
        super(coffee);
    }

    @Override
    public void addTopping(Coffee coffee) {
    }

    @Override
    public String printCoffee() {
        return this.coffee.printCoffee() + " with espresso";
    }

    @Override
    public Double Cost(){
        double cost = 0.0;
        cost += 0.35;
        return this.coffee.Cost() + cost;
    }
}

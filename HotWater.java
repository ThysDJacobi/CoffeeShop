public class HotWater extends CoffeeDecorator{
    public HotWater(Coffee coffee) {
        super(coffee);
    }

    @Override
    public void addTopping(Coffee coffee) {
    }

    @Override
    public String printCoffee() {
        return this.coffee.printCoffee() + " and hot water";
    }

    @Override
    public Double Cost(){
        double cost = 0.0;
        cost += 0.0;
        return this.coffee.Cost() + cost;
    }
}

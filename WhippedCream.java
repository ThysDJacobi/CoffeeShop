public class WhippedCream extends CoffeeDecorator{
    public WhippedCream(Coffee coffee) {
        super(coffee);
    }

    @Override
    public void addTopping(Coffee coffee) {
    }

    @Override
    public String printCoffee() {
        return this.coffee.printCoffee() + " with whipped cream";
    }

    @Override
    public Double Cost(){
        double cost = 0.0;
        cost += 0.1;
        return this.coffee.Cost() + cost;
    }
}

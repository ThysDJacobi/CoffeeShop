public class Sugar extends CoffeeDecorator {
    public Sugar(Coffee coffee) {
        super(coffee);
    }

    @Override
    public void addTopping(Coffee coffee) {
    }

    @Override
    public String printCoffee() {
        return this.coffee.printCoffee() + " and sugar";
    }

    @Override
    public Double Cost(){
        double cost = 0.0;
        cost += 0.05;
        return this.coffee.Cost() + cost;
    }
}

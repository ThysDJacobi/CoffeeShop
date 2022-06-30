public class BasicCoffee implements Coffee {
    @Override
    public void addTopping(Coffee coffee) {

    }

    @Override
    public String printCoffee() {
        return "A black coffee";
    }

    @Override
    public Double Cost(){ return 0.85;   }
}

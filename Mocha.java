public class Mocha implements Coffee{
    @Override
    public void addTopping(Coffee coffee) {

    }

    @Override
    public String printCoffee() {
        return "A Mocha";
    }

    @Override
    public Double Cost(){ return 1.20;   }
}

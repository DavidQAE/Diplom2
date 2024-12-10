package order;

import java.util.ArrayList;
import java.util.List;

public class CreateOrder {

    private List<Data> ingredients = new ArrayList<Data>();



    public CreateOrder withIngredients(List<Data> ingredients) {
        this.ingredients = ingredients;
        return  this;
    }


    public  List<Data> getIngredients() {
        return ingredients;
    }


}

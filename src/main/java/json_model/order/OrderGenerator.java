package json_model.order;

import java.util.List;

public class OrderGenerator {
    public static Ingredients setUpIngredientsForCreateOrder() {
        return new Ingredients(List.of(new Ingredient("61c0c5a71d1f82001bdaaa6d")));
    }

    public static Ingredients setUpIngredientsWithBadHash() {
        return new Ingredients(List.of(new Ingredient("228133754")));
    }
}

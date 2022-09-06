package json_model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {
    private String _id;
    private String status;
    private String name;
    private String createdAt;
    private String updatedAt;
    private int number;
    private List<Ingredient> ingredients;
}

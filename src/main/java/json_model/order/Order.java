package json_model.order;

import com.google.gson.annotations.SerializedName;
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
    @SerializedName(value="_id")
    private String id;

    private String status;
    private String name;
    private String createdAt;
    private String updatedAt;
    private int number;
    private List<Ingredient> ingredients;
}

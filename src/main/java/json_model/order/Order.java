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
    /* Для десериализации/сериализации нужно именно такое наименование переменной, иначе тесты начинают падать
    Пример:
    "order": {
        "ingredients": [
        {
            "_id": "61c0c5a71d1f82001bdaaa6d",
                "name": "Флюоресцентная булка R2-D3",
                "type": "bun",
                "proteins": 44,
                "fat": 26,
                "carbohydrates": 85,
                "calories": 643,
                "price": 988,
                "image": "https://code.s3.yandex.net/react/code/bun-01.png",
                "image_mobile": "https://code.s3.yandex.net/react/code/bun-01-mobile.png",
                "image_large": "https://code.s3.yandex.net/react/code/bun-01-large.png",
                "__v": 0
        }
        ],
        "_id": "631b87a393ebc2001b4c54b7",
                "owner": {
            "name": "praktikum-student",
                    "email": "praktikum-student@autotest.ru",
                    "createdAt": "2022-09-09T18:36:10.747Z",
                    "updatedAt": "2022-09-09T18:36:10.747Z"
        },
        "status": "done",
                "name": "Флюоресцентный бургер",
                "createdAt": "2022-09-09T18:36:19.243Z",
                "updatedAt": "2022-09-09T18:36:19.519Z",
                "number": 22650,
                "price": 988
    }
    */
    private String _id;

    private String status;
    private String name;
    private String createdAt;
    private String updatedAt;
    private int number;
    private List<Ingredient> ingredients;
}

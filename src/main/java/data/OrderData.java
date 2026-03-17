package data;


import java.util.Arrays;
import java.util.List;

public class OrderData {
    public static final String BASE_URI= "https://stellarburgers.education-services.ru/";
    public static final String CREATE_ORDER = "/api/orders";
    public static final List<String> INGREDIENTS = Arrays.asList(
            "61c0c5a71d1f82001bdaaa6d","61c0c5a71d1f82001bdaaa6f");
    public static final List<String> INVALID_INGREDIENTS = Arrays.asList("invalid_hash_123","invalid_hash_111" );
    public static final String NO_INGREDIENTS = "Ingredient ids must be provided"; // 400 не передали ингредиенты
}

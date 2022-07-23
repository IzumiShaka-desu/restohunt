package com.darkshandev.restohunt.core.utils

import com.darkshandev.restohunt.core.data.local.entities.FavRestaurantEntity
import com.darkshandev.restohunt.core.data.remote.response.detail.*
import com.darkshandev.restohunt.core.data.remote.response.restaurant.RestaurantResponse
import com.darkshandev.restohunt.core.domain.models.*

fun RestaurantResponse.asModel(): Restaurant =
    Restaurant(
        id = id,
        name = name,
        pictureId = pictureId,
        city = city,
        description = description,
        rating = rating
    )


fun List<RestaurantResponse>.mapToModel(): List<Restaurant> {
    return map { it.asModel() }
}

fun CategoryResponse.asModel(): Category =
    Category(
        name = name
    )

fun DetailRestaurant.asRestaurant(): Restaurant = Restaurant(
    id = id,
    name = name,
    pictureId = pictureId,
    city = city,
    description = description,
    rating = rating
)

fun CustomerReviewResponse.asModel(): CustomerReview =
    CustomerReview(
        name = name,
        review = review,
        date = date
    )

fun DrinkResponse.asModel(): Drink = Drink(name = name)

fun FoodResponse.asModel(): Food = Food(name = name)

fun MenusResponse.asModel(): Menus = Menus(
    drinks = drinks.map { it.asModel() },
    foods = foods.map { it.asModel() }
)

fun DetailRestaurantResponse.asModel(): DetailRestaurant =
    DetailRestaurant(
        address = address,
        categories = categories.map { it.asModel() },
        city = city,
        customerReviews = customerReviews.map { it.asModel() },
        description = description,
        id = id,
        menus = menus.asModel(),
        name = name,
        pictureId = pictureId,
        rating = rating
    )

fun List<CustomerReviewResponse>.mapAsModel(): List<CustomerReview> {
    return map { it.asModel() }
}

fun Restaurant.asEntity(): FavRestaurantEntity {
    return FavRestaurantEntity(
        id = id,
        name = name,
        pictureId = pictureId,
        city = city,
        description = description,
        rating = rating
    )
}

fun FavRestaurantEntity.asModel(): Restaurant =
    Restaurant(
        id = id,
        name = name,
        pictureId = pictureId,
        city = city,
        description = description,
        rating = rating
    )




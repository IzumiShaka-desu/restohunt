package com.darkshandev.favorites

import app.cash.turbine.test
import com.darkshandev.restohunt.core.domain.models.Restaurant
import com.darkshandev.restohunt.core.domain.usecase.RestaurantUseCase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
 class FavoriteViewmodelTest{
@Mock
private lateinit var useCase: RestaurantUseCase
private lateinit var viewmodel: FavoriteViewmodel
 @OptIn(ExperimentalCoroutinesApi::class)
 @Before
 fun setUp() {
  Dispatchers.setMain(UnconfinedTestDispatcher())
  viewmodel = FavoriteViewmodel(useCase)
 }

 @OptIn(ExperimentalCoroutinesApi::class)
 @After
 fun down() {
  Dispatchers.resetMain()
 }
 @OptIn(ExperimentalCoroutinesApi::class)
 @Test
 fun `verify favorites items`() {
  runTest {
   val dummyFavs = listOf<Restaurant>()
   Mockito.`when`(useCase.getFavRestaurants())
    .thenReturn(flowOf(dummyFavs))
   assertEquals(dummyFavs.size,viewmodel.favRestaurant.value.size)

  }
 }
}
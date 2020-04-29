package com.teamtreehouse.pizzakeeper

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.teamtreehouse.pizzakeeper.data.Pizza
import com.teamtreehouse.pizzakeeper.data.PizzaDatabase
import com.teamtreehouse.pizzakeeper.data.PizzaTopping

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.util.*


@RunWith(AndroidJUnit4::class)
class PizzaTests {
    val testPizza = Pizza(0, "Hawaiian", Date())
    val testToppingIds = listOf(1, 7)
    val appContext = InstrumentationRegistry.getTargetContext()
    val db = Room.databaseBuilder(appContext, PizzaDatabase::class.java, "PizzaDatabase")
            .build()


    @Test
    fun pizzaTest() {
        // Context of the app under test.
        db.clearAllTables()
        db.pizzaDao().insert(testPizza)
        val returnedPizza = db.pizzaDao().getPizzaById(testPizza.id)

        assertEquals(testPizza, returnedPizza)
    }

    @Test
    fun pizzaToppingTest() {
    db.clearAllTables()
        db.pizzaDao().insert(testPizza)
        for (topping in toppings) {
            db.toppingDao().insert(topping)
        }
        testToppingIds.forEach{
            val pizzaTopping = PizzaTopping(testPizza.id, it)
            db.pizzaToppingDao().insert(pizzaTopping)
        }
        val returnedToppingIds = db.pizzaToppingDao().getToppingIdsForPizzaId(testPizza.id)

        assertEquals(testToppingIds, returnedToppingIds)
    }
}


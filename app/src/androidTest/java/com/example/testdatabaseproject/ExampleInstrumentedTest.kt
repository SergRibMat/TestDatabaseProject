package com.example.testdatabaseproject

import android.util.Log
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testdatabaseproject.database.*
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Ignore
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var databaseDao: DatabaseDAO
    private lateinit var db: TownDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, TownDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        databaseDao = db.databaseDao

        insertTowns()
        insertFamilies()
        insertPersons()
    }



    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Ignore
    @Throws(Exception::class)
    fun getTowns() {
        val list = databaseDao.getAllTowns()
        for (town in list) {
            Log.i("result", "id= ${town.id} name= ${town.name}")
        }
        assertEquals(2, list.size)
    }

    fun insertTowns(){
        databaseDao.insertTown(Town(0, "Ribarroja"))
        databaseDao.insertTown(Town(0, "Villamarchante"))
    }

    @Test
    @Throws(Exception::class)
    fun getFamily(){

        val list = databaseDao.getAllFamilies()
        for (family in list) {
            Log.i("result", "id= ${family.id} surname= ${family.surname} townId= ${family.town_id}")
        }
        assertEquals(3, list.size)

    }

    @Test
    @Throws(Exception::class)
    fun getPersons(){

        val list = databaseDao.getAllPersons()
        for (person in list) {
            Log.i("result", "id= ${person.id} surname= ${person.name} townId= ${person.town_id}")
        }
        assertEquals(5, list.size)

    }

    fun insertFamilies(){
        databaseDao.insertFamily(Family(0, "Ribera", 1))
        databaseDao.insertFamily(Family(0, "Mateu", 1))
        databaseDao.insertFamily(Family(0, "Santamaria", 2))
    }

    private fun insertPersons() {
        databaseDao.insertPerson(Person(0, "Sergio", 1, 1))
        databaseDao.insertPerson(Person(0, "Silvia", 1, 1))
        databaseDao.insertPerson(Person(0, "conchin", 1, 2))
        databaseDao.insertPerson(Person(0, "ruben", 2, 3))
        databaseDao.insertPerson(Person(0, "paula", 2, 3))

    }

    @Test
    fun deleteTown(){
        databaseDao.deleteTown(Town(1, "Ribarroja"))
        databaseDao.deleteTownById(2)
        //getTowns()
    }

    @Test
    fun deleteFamily(){
        databaseDao.deleteFamily(Family(1, "Ribera", 1))
        databaseDao.deleteFamilyById(2)
        //getFamily()
    }

    @Test
    fun deletePerson(){
        databaseDao.deletePerson(Person(3, "conchin", 1, 2))
        databaseDao.deletePersonById(1)
        //getPersons()
    }

    //these 3 tests are made to check if the data is deleted in cascade
    @Test
    fun personsDeletingTown(){
        deleteTown()
        getPersons()
        //expected: 0
    }

    @Test
    fun personsDeletingFamily(){
        deleteFamily()
        getPersons()
        //expected: 2
    }

    @Test
    fun familiesDeletingTown(){
        deleteTown()
        getFamily()
        //expected: 0
    }

    @Test
    fun updateTown(){
        databaseDao.updateTown(Town(1, "La Eliana"))
        getTowns()
    }

    @Test
    fun insertAllList(){//OK

        val myList = listOf(
            Person(1, "Valencia", 1, 1),
            Person(2, "Madrid", 1, 1),
            Person(3, "Barcelona", 1, 1),
            Person(4, "Sevilla", 1, 1)
        )

        databaseDao.insertAllList(myList)

        getPersons()
    }

    @Test
    fun insertAllVarArg(){//OK

        val persons = arrayOf(
            Person(1, "Valencia", 1, 1),
            Person(2, "Madrid", 1, 1),
            Person(3, "Barcelona", 1, 1),
            Person(4, "Sevilla", 1, 1)
        )

        databaseDao.insertAllVarArg(*persons)

        getPersons()
    }



}
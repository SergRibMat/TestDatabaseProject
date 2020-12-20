package com.example.testdatabaseproject.database

import androidx.room.*

@Dao
interface DatabaseDAO {

    @Insert
    fun insertTown(town: Town)

    @Update
    fun updateTown(town: Town)

    @Delete
    fun deleteTown(town: Town)

    @Insert
    fun insertPerson(person: Person)

    @Update
    fun updatePerson(person: Person)

    @Delete
    fun deletePerson(person: Person)

    @Insert
    fun insertFamily(family: Family)

    @Update
    fun updateFamily(family: Family)

    @Delete
    fun deleteFamily(family: Family)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllList(persons: List<Person>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllVarArg(vararg person: Person)

    @Query("SELECT * FROM town ORDER BY id ASC")
    fun getAllTowns(): List<Town>

    @Query("SELECT * FROM person ORDER BY id ASC")
    fun getAllPersons(): List<Person>

    @Query("SELECT * FROM family ORDER BY id ASC")
    fun getAllFamilies(): List<Family>

    @Query("DELETE FROM town WHERE id = :id")
    fun deleteTownById(id: Int)

    @Query("DELETE FROM family WHERE id = :id")
    fun deleteFamilyById(id: Int)

    @Query("DELETE FROM person WHERE id = :id")
    fun deletePersonById(id: Int)


}
package com.example.testdatabaseproject.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "town")
data class Town  (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String = "no name"
)

@Entity(tableName = "person",
    foreignKeys = [ForeignKey(entity = Town::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("town_id"),
        onDelete = ForeignKey.CASCADE), ForeignKey(entity = Family::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("family_id"),
        onDelete = ForeignKey.CASCADE)]
)
data class Person  (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String = "no name",

    @ColumnInfo(name = "town_id")
    var town_id: Int,

    @ColumnInfo(name = "family_id")
    var family_id: Int
)

@Entity(tableName = "family",
    foreignKeys = [ForeignKey(entity = Town::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("town_id"),
        onDelete = ForeignKey.CASCADE)])
data class Family  (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "surname")
    var surname: String = "no surname",

    @ColumnInfo(name = "town_id")
    var town_id: Int
)
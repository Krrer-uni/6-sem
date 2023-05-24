package com.mobilki.studentinvaders

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase


@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "username") val username: String?
)

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid = :id")
    fun findById(id: String): User

    @Insert
    fun insertAll(vararg user: User)

    @Delete
    fun delete(user: User)
}

@Database(entities = [User::class], version = 1)
abstract class GameDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getDatabase(context: Context): GameDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "game_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

@Entity
data class Score(
    @PrimaryKey(autoGenerate = true) val id : Int,
    @ColumnInfo(name = "player") val playername : String,
    @ColumnInfo(name = "Score") val score : Int
)

@Dao
interface ScoreDao{
    @Insert
    fun insertAll(vararg score : Score)

    @Query("SELECT * FROM score ORDER BY score.Score DESC LIMIT 4")
    fun top3() : List<Score>
}

@Database(entities = [Score::class], version = 1)
abstract class ScoreDatabase : RoomDatabase(){

    abstract fun scoreDao(): ScoreDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: ScoreDatabase? = null

        fun getDatabase(context: Context): ScoreDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScoreDatabase::class.java,
                    "score_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
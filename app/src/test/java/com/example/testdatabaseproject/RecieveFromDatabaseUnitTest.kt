package com.example.testdatabaseproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.system.measureTimeMillis

class ArticleViewModel {
    private var likeCount = 0

    suspend fun getLikeCountFromDb(): Int =
        withContext(Dispatchers.IO) {
            delay(10_000) // Assume we get data from database
            likeCount
        }
}

class ArticleViewModelTest {


    //runBlocking is made to test suspend functions
    @Test
    fun testLikeCountFromDb() = runBlocking {
        val articleViewModel = ArticleViewModel()
        val costTimeMillis = measureTimeMillis {
            val likeCount = articleViewModel.getLikeCountFromDb()
            Assert.assertEquals(0, likeCount)
        }
        print("costTimeMillis: $costTimeMillis")
    }
}

class ArticleViewModel2 : ViewModel() {
    private var likeCount = 0

    fun getLikeCount() = likeCount

    fun addLikeCount() {
        viewModelScope.launch {
            likeCount += 1
            likeCount
        }
    }
}

@ExperimentalCoroutinesApi
class ArticleViewModelTest2 {
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        // Sets the given [dispatcher] as an underlying dispatcher of [Dispatchers.Main].
        // All consecutive usages of [Dispatchers.Main] will use given [dispatcher] under the hood.
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        // Resets state of the [Dispatchers.Main] to the original main dispatcher.
        // For example, in Android Main thread dispatcher will be set as [Dispatchers.Main].
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun addLikeCount() = runBlocking {
        //...
        val articleViewModel = ArticleViewModel2()
        articleViewModel.addLikeCount()
        Assert.assertEquals(1, articleViewModel.getLikeCount())
    }
}

class ArticleViewModel3(private val defaultDispatcher: CoroutineDispatcher) : ViewModel() {
    private var likeCount = 0

    fun getLikeCount() = likeCount

    fun addLikeCount() {
        viewModelScope.launch(defaultDispatcher) {
            likeCount += 1
            likeCount
        }
    }
}

@ExperimentalCoroutinesApi
class ArticleViewModelTest3 {
    private val testDispatcher = TestCoroutineDispatcher()

    @Test
    fun addLikeCount() = runBlocking {
        val articleViewModel = ArticleViewModel3(testDispatcher)
        articleViewModel.addLikeCount()
        Assert.assertEquals(1, articleViewModel.getLikeCount())
    }
}
#MockingExample
# KotlinMockKDemo
Kotlin下使用MockK模拟单元测试

#### 库的引入
```kotlin
    testImplementation "io.mockk:mockk:1.9.3"
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.0-M2'
    testImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.0-M2'

```

#### MVP下的测试
```kotlin
package com.example.mockingexample

import com.example.mockingexample.goods.GoodsContract
import com.example.mockingexample.goods.GoodsPresenter
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Kotlin使用MockK来对Presenter逻辑代码进行测试
 */
class MockKGoodsPresenterTest {

    private var presenter: GoodsPresenter? = null

    // @MockK(relaxed = true)
    @RelaxedMockK
    lateinit var view: GoodsContract.View

    /**
     * 测试之前，初始化必要的对象
     */
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        presenter = GoodsPresenter()
        presenter!!.attachView(view)
    }

    /**
     * 使用 MockK 测试 Presenter
     */
    @Test
    fun testGetGoods3() {
        val goods = presenter!!.getGoods(1)
        assertEquals(goods.name, "纸巾")
    }

    /**
     * 验证多个方法被调用
     */
    @Test
    fun testGetGoods6() {
        val goods = presenter!!.getGoods(1)
        verify {
            view.hideLoading()
            view.showLoading()
        }
        assertEquals(goods.name, "纸巾")
    }

    /**
     * 验证方法被调用的次数
     */
    @Test
    fun testGetGoods7() {
        val goods = presenter!!.getGoods(1)
        // 验证调用了两次
//        verify(exactly = 2) {
//            view.showToast("请耐心等待")
//        }

        // 验证调用了最少一次
         verify(atLeast = 1) {
             view.showToast("请耐心等待")
         }

        // 验证最多调用了两次
        // verify(atMost = 1) { view.showToast("请耐心等待") }

        assertEquals(goods.name, "纸巾")
    }


    /**
     * 验证 Mock 方法都被调用了
     */
    @Test
    fun testGetGoods8() {
        val goods = presenter!!.getGoods(1)
        verifyAll {
            view.showToast("请耐心等待")
            view.showToast("请耐心等待")
            view.showLoading()
            view.hideLoading()
        }
        assertEquals(goods.name, "纸巾")
    }

    /**
     * 验证 Mock 方法的调用顺序
     */
    @Test
    fun testGetGoods9() {
        val goods = presenter!!.getGoods(1)
        verifyOrder {
            view.showLoading()
            view.hideLoading()
        }
        assertEquals(goods.name, "纸巾")
    }

    /**
     * 验证全部的 Mock 方法都按特定顺序被调用了
     */
    @Test
    fun testGetGoods10() {
        val goods = presenter!!.getGoods(1)
        verifySequence {
            view.showLoading()
            view.showToast("请耐心等待")
            view.showToast("请耐心等待")
            view.hideLoading()
        }
        assertEquals(goods.name, "纸巾")
    }

    /**
     * 确认所有 Mock 方法都进行了验证
     */
    @Test
    fun testGetGoods11() {
        val goods = presenter!!.getGoods(1)
        verify {
            view.showLoading()
            view.showToast("请耐心等待")
            view.showToast("请耐心等待")
            view.hideLoading()
        }
        confirmVerified(view)
        assertEquals(goods.name, "纸巾")
    }

    /**
     * 验证 Mock 方法接收到的单个参数
     */
    @Test
    fun testCaptureSlot() {
        val slot = slot<String>()
        every {
            view.showToast(capture(slot))
        } returns Unit
        val goods = presenter!!.getGoods(1)
        assertEquals(slot.captured, "请耐心等待")
    }

    /**
     * 验证 Mock 方法每一次被调用接收到参数
     */
    @Test
    fun testCaptureList() {
        val list = mutableListOf<String>()
        every { view.showToast(capture(list)) } returns Unit
        val goods1 = presenter!!.getGoods(1)
        assertEquals(list[0], "请耐心等待")
        assertEquals(list[1], "请耐心等待")
    }


}

```

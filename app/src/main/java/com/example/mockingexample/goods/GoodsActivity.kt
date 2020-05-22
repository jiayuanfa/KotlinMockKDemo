package com.example.mockingexample.goods

import android.os.Bundle
import android.widget.TextView
import com.example.mockingexample.R
import com.example.mockingexample.base.BaseActivity
import com.example.mockingexample.data.Goods
import kotlinx.android.synthetic.main.activity_goods.*

/**
 * 商品列表View
 */
class GoodsActivity : BaseActivity<GoodsContract.View, GoodsContract.Presenter>(), GoodsContract.View {

    var tv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods)
        tv = findViewById(R.id.tv)

        btn_get_goods.setOnClickListener {
            val goods = mPresenter.getGoods(1)
            tv!!.text = goods.name
        }

        btn_request_goods.setOnClickListener {
            mPresenter.requestGoods(1)
        }

    }

    override fun onGoodsRequested(result: Goods) {
        tv!!.text = result.name
    }

    override fun createPresenter(): GoodsContract.Presenter {
        return GoodsPresenter()
    }


}

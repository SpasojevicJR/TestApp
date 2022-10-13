package com.example.testapp

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.adapter.ProductsAdapter
import com.example.testapp.api.RetrofitInstance.Companion.api
import com.example.testapp.util.Constants.Companion.PRODUCT_BRAND
import com.example.testapp.util.Constants.Companion.PRODUCT_DESCR
import com.example.testapp.util.Constants.Companion.PRODUCT_IMAGE
import com.example.testapp.util.Constants.Companion.PRODUCT_NAME
import com.example.testapp.util.Constants.Companion.PRODUCT_PRICE
import com.example.testapp.util.ProductsObj.products
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    var apiCallScope: CoroutineScope? = null
    lateinit var productsAdapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val productsRecyclerView = findViewById<RecyclerView>(R.id.productsRecyclerView)
        val layoutManager = LinearLayoutManager(applicationContext)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        getProductsAPI(progressBar)

        productsAdapter = ProductsAdapter(this, products) {
            progressBar.visibility = View.VISIBLE
            Handler().postDelayed({
                progressBar.visibility = View.GONE
            }, 2000L)
            val productDetailsIntent = Intent(this, ProductDetailsActivity::class.java)
            for (product in products) {
                if (it.id == product.id) {
                    productDetailsIntent.putExtra(PRODUCT_NAME, product.name)
                    productDetailsIntent.putExtra(PRODUCT_BRAND, product.brand)
                    productDetailsIntent.putExtra(PRODUCT_PRICE, product.price)
                    productDetailsIntent.putExtra(PRODUCT_IMAGE, product.image_link)
                    productDetailsIntent.putExtra(PRODUCT_DESCR, product.description)
                }
            }
            startActivity(productDetailsIntent)
        }

        productsRecyclerView.adapter = productsAdapter

        productsRecyclerView.layoutManager = layoutManager
        productsRecyclerView.setHasFixedSize(true)

        productsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                productsRecyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        colorChange(productsRecyclerView)
    }

    private fun getProductsAPI(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
        apiCallScope = CoroutineScope(Dispatchers.IO)
        apiCallScope!!.launch {
            val content = api.getProducts()
            for (result in content) {
                products.add(result)
            }
            withContext(Dispatchers.Main) {
                progressBar.visibility = View.GONE
                productsAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun colorChange(rc: RecyclerView) {
        rc.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    supportActionBar?.hide()
                } else {
                    supportActionBar?.show()
                    supportActionBar?.setBackgroundDrawable(
                        ColorDrawable(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.mainColor
                            )
                        )
                    )
                }
            }
        })
    }
}
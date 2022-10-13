package com.example.testapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.testapp.util.Constants.Companion.PRODUCT_BRAND
import com.example.testapp.util.Constants.Companion.PRODUCT_DESCR
import com.example.testapp.util.Constants.Companion.PRODUCT_IMAGE
import com.example.testapp.util.Constants.Companion.PRODUCT_NAME
import com.example.testapp.util.Constants.Companion.PRODUCT_PRICE
import com.example.testapp.util.Constants.Companion.WEB_URL

class ProductDetailsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_page)

        setupUI()
        goToWeb()
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun goToWeb() {
        val goToWeb = findViewById<TextView>(R.id.goToWebText)
        goToWeb.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(WEB_URL))
            startActivity(i)
        }
    }

    private fun setupUI() {
        val productName = intent.getStringExtra(PRODUCT_NAME)
        val productBrand = intent.getStringExtra(PRODUCT_BRAND)
        val productPrice = intent.getDoubleExtra(PRODUCT_PRICE, 0.0)
        val productImage = intent.getStringExtra(PRODUCT_IMAGE)
        val productDesc = intent.getStringExtra(PRODUCT_DESCR)

        val name = findViewById<TextView>(R.id.productNamePage)
        val brand = findViewById<TextView>(R.id.productBrandPage)
        val price = findViewById<TextView>(R.id.productPricePage)
        val image = findViewById<ImageView>(R.id.productImagePage)
        val desc = findViewById<TextView>(R.id.productDescriptionPage)

        name.text = productName
        brand.text = "Made by: $productBrand"
        price.text = "$${productPrice}"
        desc.text = productDesc
        Glide.with(this)
            .load(productImage)
            .into(image)
    }
}
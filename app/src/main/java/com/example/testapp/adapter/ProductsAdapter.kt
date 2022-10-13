package com.example.testapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapp.R
import com.example.testapp.models.Products

class ProductsAdapter(
    val context: Context,
    val products: MutableList<Products>,
    val itemClick: (Products) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.Holder>() {

    override fun onBindViewHolder(holder: ProductsAdapter.Holder, position: Int) {
        holder.bindPeople(products[position], context)
    }

    override fun getItemCount(): Int {
        return products.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsAdapter.Holder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.product_details, parent, false)
        return Holder(view, itemClick)
    }

    inner class Holder(itemView: View?, val itemClick: (Products) -> Unit) : RecyclerView.ViewHolder(itemView!!) {
        val name = itemView?.findViewById<TextView>(R.id.productName)
        val brand = itemView?.findViewById<TextView>(R.id.productBrand)
        val price = itemView?.findViewById<TextView>(R.id.productPrice)
        val image = itemView?.findViewById<ImageView>(R.id.productImage)


        fun bindPeople(product: Products, context: Context) {
            name?.text = product.name
            brand?.text = product.brand
            price?.text = "$${product.price.toString()}"
            Glide.with(context)
                .load(product.image_link)
                .into(image!!)
            itemView.setOnClickListener { itemClick(product) }
        }
    }
}
package com.mm.ejercicio2examen

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.IntegerRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding

class MainActivity : AppCompatActivity() {
    private var productList = mutableListOf<Product>()

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        productList = mutableListOf(
            Product("Producto 1", 3005),
            Product("Producto 2", 134),
            Product("Producto 3", 5878),
            Product("Producto 4", 1434),
            Product("Producto 5", 76)
        )

        initValues()

        val search = findViewById<EditText>(R.id.search)

        findViewById<Button>(R.id.searchProduct).setOnClickListener {
            val data = search.text
            val result = findViewById<TextView>(R.id.result)

            val product = searchProduct(data.toString())
            if (product.nombre == "") {
                Toast.makeText(this, "No se ha encontrado ningún producto", Toast.LENGTH_SHORT).show()
                result.text = ""
            } else {
                Toast.makeText(this, "Se ha encontrado un producto", Toast.LENGTH_SHORT).show()
                result.text = "Producto: ${product.nombre} - Precio: ${product.precio} €"
            }
        }

        findViewById<Button>(R.id.filterByPrice).setOnClickListener {
            val data = search.text
            val result = findViewById<TextView>(R.id.result)

            // val list = filter(data)
        }
    }

    fun initValues() {
        val numProduct = findViewById<TextView>(R.id.numProducts)
        numProduct.text = productList.size.toString()

        val maxProduct = getMaxPrice()
        val maxPrice = findViewById<TextView>(R.id.maxPrice)
        maxPrice.text = "${maxProduct.nombre} - ${maxProduct.precio} €"

        val minProduct = getMinPrice()
        val minPrice = findViewById<TextView>(R.id.minPrice)
        minPrice.text = "${minProduct.nombre} - ${minProduct.precio} €"

        val avPrice = findViewById<TextView>(R.id.avPrice)
        avPrice.text = getAverage().toString()
    }

    fun getMaxPrice(): Product {
        var mayor: Product = Product("", Int.MIN_VALUE)

        for (product in productList) {
            if (product.precio > mayor.precio) {
                mayor = product
            }
        }

        return mayor
    }

    fun getMinPrice(): Product {
        var menor: Product = Product("", Int.MAX_VALUE)

        for (product in productList) {
            if (menor.precio > product.precio) {
                menor = product
            }
        }

        return menor
    }

    fun getAverage(): Int {
        var suma: Int = 0

        for (product in productList) {
            suma += product.precio
        }

        return suma/productList.size
    }

    fun searchProduct(data: String): Product {
        for (product in productList) {
            if (product.nombre == data) {
                return product
            }
        }

        return Product("", 0)
    }

    fun filter(data: Int): List<Product> {
        var list = mutableListOf<Product>()

        for (product in productList) {
            if (product.precio <= data) {
                list.add(product)
            }
        }

        return list
    }
}
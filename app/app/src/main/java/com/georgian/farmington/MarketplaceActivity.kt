package com.georgian.farmington

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.*

class MarketplaceActivity : AppCompatActivity(), MarketPlaceRecyclerViewAdapter.OnProductListner {

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: MarketPlaceRecyclerViewAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var productRowArrayList : ArrayList<ArrayList<Product>>
    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.supportActionBar?.title = "Farmington - Agri News"
        setContentView(R.layout.activity_marketplace)
        layoutManager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMarketplace)
        recyclerView.layoutManager = layoutManager;

        productRowArrayList = arrayListOf()

        adapter = MarketPlaceRecyclerViewAdapter(productRowArrayList, this)
        recyclerView.adapter = adapter;

        fetchMarketPlaceActivity();

        val NewlistingVar: Button = findViewById (R.id.newlisting)
        NewlistingVar.setOnClickListener()
        {
            val intent = Intent(this, NewProductActivity::class.java)
            startActivity(intent)
        }

        //navigation
        bottomNav = findViewById(R.id.bottom_navigation) as BottomNavigationView
        bottomNav.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.news -> {
                    val intent = Intent(this, AgriNewsHomeActivity::class.java)
                    startActivity(intent)
                }
                R.id.marketplace -> {
                    val intent = Intent(this,MarketplaceActivity::class.java)
                    startActivity(intent)

                }
                R.id.weather -> {
                    val intent = Intent(this,WeatherActivity::class.java)
                    startActivity(intent)

                }
                R.id.home-> {
                    val intent = Intent(this,HomeActivity::class.java)
                    startActivity(intent)

                }
                R.id.profile-> {
                    val intent = Intent(this,ProfilePageActivity::class.java)
                    startActivity(intent)

                }
            }
        }

    }

    private fun fetchMarketPlaceActivity(){
        db = FirebaseFirestore.getInstance();
        db.collection("products").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firebase", error.toString())
                    return
                }

                var i = 0
                var productRow = arrayListOf<Product>()
                for (product : DocumentChange in value?.documentChanges!!){

                    if (product.type == DocumentChange.Type.ADDED){
                        productRow.add(product.document.toObject(Product::class.java))
                        i++
                        if (i == 2) {
                            i = 0
                            productRow = arrayListOf<Product>()
                        }

                        if (i != 0){
                            productRowArrayList.add(productRow)
                        }
                    }
                }

                adapter.notifyDataSetChanged()
            }
        })
    }
    override fun onProductListner(product: Product) {
        val intent = Intent(this, ProductActivity::class.java)
        intent.putExtra("product", product)
        startActivity(intent)

    }
}
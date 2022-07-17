package com.georgian.farmington

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class MarketplaceActivity : AppCompatActivity() {
    lateinit var bottomNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)

        this.supportActionBar?.title = "Farmington - Market Place"
        val productButton: ImageButton = findViewById (R.id.wheatimage)
        productButton.setOnClickListener()
        {
            val product:Product = Product("Wheat","Wheat is an annual grass that usually is planted at the end of the summer. It overwinters and then starts growing and maturing towards the end of spring and beginning of the summer.",
                R.drawable.wheat, 30,415.0F)
            navigateToProductPage(product);
        }

        val RiceButtonVar: ImageButton = findViewById (R.id.ricepic)
        RiceButtonVar.setOnClickListener()
        {
            val product:Product = Product("Rice","Rice is the seed of the monocot plants. As a cereal grain, it is the most widely consumed staple food for a large part of the world’s human population.",
                    R.drawable.ricepic, 44,1215.0F)
            navigateToProductPage(product);
        }
        val CornButtonVar: ImageButton = findViewById (R.id.cornpicture)
        CornButtonVar.setOnClickListener()
        {
            val product:Product = Product("Corn","Corn is a tall annual cereal grass (Zea mays) that is widely grown for its large elongated ears of starchy seeds. The seeds, which are also known as corn, are used as food for humans and livestock and as a source of biofuel and can be processed into a wide range of useful chemicals.", R.drawable.images, 33,400.0F)
            navigateToProductPage(product);
        }

        val BarleyButtonVar: ImageButton = findViewById (R.id.barleypicture)
        BarleyButtonVar.setOnClickListener()
        {
            val product:Product = Product("Barley","Barley is any annual cereal grass  of the genus  Hordeum, and in particular the species Hordeum vulgare. The term also is used for the edible grain of these plants.",
                R.drawable.barley, 60,230.0F)
            navigateToProductPage(product);
        }
        val OatsButtonVar: ImageButton = findViewById (R.id.oatspicture)
        OatsButtonVar.setOnClickListener()
        {
            val product:Product = Product("Oats","Oats are widely cultivated in the temperate regions of the world and are second only to rye in their ability to survive in poor soils.",R.drawable.oats, 72,500.0F)
            navigateToProductPage(product);
        }

        val SoybeanButtonVar: ImageButton = findViewById (R.id.soybeanpicture)
        SoybeanButtonVar.setOnClickListener()
        {
            val product:Product = Product("Soya","The soybean plant is usually an erect bush with woody stems and alternately arranged leaves. The leaves possess three individual leaflets which are oval or lance-like in shape.",
                R.drawable.soybean, 45,650.0F)
            navigateToProductPage(product);
        }
        val ChickpeaButtonVar: ImageButton = findViewById (R.id.chickpeapicture)
        ChickpeaButtonVar.setOnClickListener()
        {
            val product:Product = Product("Chickpea","Chickpea is primarily consumed as a dry pulse. The shelled peas are eaten as snack or vegetable. The seed husks can be used as a feed for animals",
                R.drawable.prducts, 32,340.0F)
            navigateToProductPage(product);
        }

        val MustardButtonVar: ImageButton = findViewById (R.id.mustardpicture)
        MustardButtonVar.setOnClickListener()
        {
            val product:Product = Product("Mustard","Mustards, (Brassica spp.) are herbaceous annual plants in the family Brassicaceae grown for their seeds which are used as a spice. Mustard plants are thin herbaceous herbs with yellow flowers.",
                R.drawable.mustardpic, 21,1435.0F)
            navigateToProductPage(product);
        }

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

    private fun navigateToProductPage(product: Product) {
        val intent = Intent(this, ProductActivity::class.java)
        intent.putExtra("product", product)
        startActivity(intent)
    }
}
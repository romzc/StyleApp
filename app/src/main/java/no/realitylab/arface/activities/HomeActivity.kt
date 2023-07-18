package no.realitylab.arface.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_home.bottomNavigationView
import kotlinx.android.synthetic.main.activity_home.fabRv
import no.realitylab.arface.R
import no.realitylab.arface.ar_activities.GlassesActivity
import no.realitylab.arface.ar_activities.MakeupActivity
import no.realitylab.arface.fragments.home.HomeFragment
import no.realitylab.arface.fragments.home.ProfileFragment

class HomeActivity : AppCompatActivity() {

    //private lateinit var glassesButton: Button
    //private lateinit var makeupButton: Button

    private lateinit var fabRv: FloatingActionButton
    private lateinit var btmNavView: BottomNavigationView
    private lateinit var fragContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initUI()
        initListeners()
    }


    private fun initUI() {
        supportActionBar?.hide()
        //bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(1).isEnabled = false
        fabRv = findViewById(R.id.fabRv)
        btmNavView = findViewById(R.id.bottomNavigationView)
        fragContainer = findViewById(R.id.fragmentContainer)

        pushBackFragment(HomeFragment())
        //glassesButton = findViewById(R.id.button_glasses)
        //makeupButton = findViewById(R.id.button_makeup)
    }

    private fun initListeners() {
        fabRv.setOnClickListener {
            startActivity(Intent(this, GlassesActivity::class.java))
        }

        btmNavView.setOnNavigationItemSelectedListener {menuItem ->  
            when (menuItem.itemId) {
                R.id.myProfile -> {
                    pushBackFragment(ProfileFragment())
                    true
                }
                R.id.myHome -> {
                    pushBackFragment(HomeFragment())
                    true
                }
                else -> false
            }
        }

    /*
        glassesButton.setOnClickListener {
            startActivity(Intent(this, GlassesActivity::class.java))
        }

        makeupButton.setOnClickListener {
            startActivity(Intent(this, MakeupActivity::class.java))
        }
        */
    }

    private fun pushBackFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.setReorderingAllowed(true)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
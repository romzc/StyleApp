package no.realitylab.arface.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_home.bottomNavigationView
import kotlinx.android.synthetic.main.activity_home.fabRv
import no.realitylab.arface.R
import no.realitylab.arface.ar_activities.GlassesActivity
import no.realitylab.arface.ar_activities.MakeupActivity
import no.realitylab.arface.fragments.home.HomeFragment
import no.realitylab.arface.fragments.home.ProfileFragment
import no.realitylab.arface.models.UserData
import no.realitylab.arface.viewmodels.UserViewModel
import androidx.activity.viewModels


class HomeActivity : AppCompatActivity() {

    private lateinit var fabRv: FloatingActionButton
    private lateinit var btmNavView: BottomNavigationView
    private lateinit var fragContainer: FrameLayout
    private lateinit var currentUser : UserData

    val userVideModel : UserViewModel by viewModels()
    //val userViewModel: UserViewModel by viewModelFactory {  }

    //val userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        /**
         * Obtenemos los datos del usuario, luego de iniciar sesion.
         */
        val extras = intent.extras
        if (extras != null ) {
            currentUser = UserData(
                extras.getString("userId"),
                extras.getString("userName"),
                extras.getString("userPhotoUri"),
                extras.getString("userEmail")
            )
        }

        userVideModel.updateUserModel(currentUser)
        initUI()
        initListeners()
    }


    private fun initUI() {
        supportActionBar?.hide()
        bottomNavigationView.menu.getItem(1).isEnabled = false
        fabRv = findViewById(R.id.fabRv)
        btmNavView = findViewById(R.id.bottomNavigationView)
        fragContainer = findViewById(R.id.fragmentContainer)
        replaceFragment(HomeFragment())
    }

    private fun initListeners() {
        fabRv.setOnClickListener {
            startActivity(Intent(this, GlassesActivity::class.java))
        }

        btmNavView.setOnNavigationItemSelectedListener {menuItem ->  
            when (menuItem.itemId) {
                R.id.myProfile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                R.id.myHome -> {
                    replaceFragment(HomeFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }
}
package no.realitylab.arface.activities

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
//import kotlinx.android.synthetic.main.activity_home.bottomNavigationView
//import kotlinx.android.synthetic.main.activity_home.fabRv
import no.realitylab.arface.R
import no.realitylab.arface.fragments.home.HomeFragment
import no.realitylab.arface.fragments.home.ProfileFragment
import no.realitylab.arface.models.UserData
import no.realitylab.arface.viewmodels.UserViewModel
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var fragContainer: FrameLayout
    private lateinit var currentUser : UserData

    private val userVideModel : UserViewModel by viewModels()

    private lateinit var drawer : DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
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

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)


        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        fragContainer = findViewById(R.id.fragmentContainer)
        replaceFragment(HomeFragment())

    }

    private fun initListeners() {

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when ( item.itemId ) {
            R.id.nav_item_home -> {
                replaceFragment(HomeFragment())
            }
            R.id.nav_item_profile-> {
                replaceFragment(ProfileFragment())
            }
            R.id.nav_item_about -> {
                Toast.makeText(this, "Item about", Toast.LENGTH_LONG).show()
            }
            R.id.nav_item_settings -> {
                Toast.makeText(this, "Item settings", Toast.LENGTH_LONG).show()
            }
            R.id.nav_item_logout -> {
                Toast.makeText(this, "Item logout", Toast.LENGTH_LONG).show()
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
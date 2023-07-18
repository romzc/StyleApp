package no.realitylab.arface.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import no.realitylab.arface.R
import no.realitylab.arface.ar_activities.GlassesActivity
import no.realitylab.arface.ar_activities.MakeupActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var glassesButton: Button
    private lateinit var makeupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initUI()
        initListeners()
    }


    private fun initUI() {
        supportActionBar?.hide()
        glassesButton = findViewById(R.id.button_glasses)
        makeupButton = findViewById(R.id.button_makeup)
    }

    private fun initListeners() {
        glassesButton.setOnClickListener {
            startActivity(Intent(this, GlassesActivity::class.java))
        }

        makeupButton.setOnClickListener {
            startActivity(Intent(this, MakeupActivity::class.java))
        }
    }
}
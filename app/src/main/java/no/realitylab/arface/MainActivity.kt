package no.realitylab.arface

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import no.realitylab.arface.activities.HomeActivity
import no.realitylab.arface.ar_activities.FaceLandmarksActivity
import no.realitylab.arface.ar_activities.FaceRegionsActivity
import no.realitylab.arface.ar_activities.GlassesActivity
import no.realitylab.arface.ar_activities.MakeupActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnLogin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initUI()
        initListeners()
    }

    private fun initUI() {
        btnLogin = findViewById(R.id.btnLogin)
    }

    private fun initListeners() {
        btnLogin.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

}

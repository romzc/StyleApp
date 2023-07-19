package no.realitylab.arface

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import no.realitylab.arface.activities.HomeActivity
import no.realitylab.arface.ar_activities.FaceLandmarksActivity
import no.realitylab.arface.ar_activities.FaceRegionsActivity
import no.realitylab.arface.ar_activities.GlassesActivity
import no.realitylab.arface.ar_activities.MakeupActivity


enum class ProviderType {
    BASIC,
    GOOGLE
}

const val REQUEST_CODE_SIGN_IN = 0
class MainActivity : AppCompatActivity() {

    private lateinit var btnLogin : Button
    private lateinit var btnGoogleSign: Button
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        initServices()
        initUI()
        initListeners()
    }

    private fun initServices() {
        auth = FirebaseAuth.getInstance()
    }
    private fun initUI() {
        btnLogin = findViewById(R.id.btnLogin)
        btnGoogleSign = findViewById(R.id.btnGoogleSign)
    }

    private fun initListeners() {
        btnLogin.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        btnGoogleSign.setOnClickListener {
            val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.webclient_id))
                .requestEmail()
                .build()

            val signInClient = GoogleSignIn.getClient(this, options)
            signInClient.signInIntent.also {
                startActivityForResult(it, REQUEST_CODE_SIGN_IN)
            }
        }
    }

    private fun googleAuthForFirebase(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)

        auth.signInWithCredential(credentials).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                Log.d("APP", "${user?.displayName} - ${user?.photoUrl}")
                Toast.makeText(this@MainActivity, "Successfully logged in", Toast.LENGTH_LONG).show()

                val intent = Intent(this@MainActivity, HomeActivity::class.java).apply {
                    putExtra("userId", user?.uid)
                    putExtra("userName", user?.displayName)
                    putExtra("userPhotoUri", user?.photoUrl.toString())
                    putExtra("userEmail", user?.email.toString())
                }
                startActivity(intent)
            } else {
                Toast.makeText(this@MainActivity, "Authentication failed", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ( requestCode == REQUEST_CODE_SIGN_IN ) {
            if ( data != null ) {
                val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
                account?.let {
                    googleAuthForFirebase(it)
                }
            }
        }
    }

}

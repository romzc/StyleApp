package no.realitylab.arface.fragments.main

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import no.realitylab.arface.R
import no.realitylab.arface.REQUEST_CODE_SIGN_IN
import no.realitylab.arface.activities.HomeActivity
import no.realitylab.arface.callbacks.ActivityCallback


class LoginFragment : Fragment() {

    private lateinit var inflate: View
    private lateinit var tvEmail: TextView
    private lateinit var tvPassword: TextView
    private lateinit var btnGoogleLogIn: Button
    private lateinit var btnLogIn: Button
    private lateinit var btnRegister: TextView
    private lateinit var callback: ActivityCallback

    private lateinit var auth : FirebaseAuth
    private lateinit var fireDatabase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        inflate = inflater.inflate(R.layout.fragment_login, container, false)
        initServices()
        initUI()
        initListeners()
        return inflate
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            if ( context is ActivityCallback ) {
                callback = context
            }
        }
        catch (e: Exception) {
            Log.e("Error", e.printStackTrace().toString())
        }
    }

    private fun initUI() {
        tvEmail = inflate.findViewById(R.id.et_login_email)
        tvPassword = inflate.findViewById(R.id.et_login_password)
        btnGoogleLogIn = inflate.findViewById(R.id.btnGoogleSign)
        btnLogIn = inflate.findViewById(R.id.btnLogin)
        btnRegister = inflate.findViewById(R.id.tv_register_button)
    }

    private fun initServices() {
        auth = FirebaseAuth.getInstance()
        fireDatabase = FirebaseDatabase
            .getInstance("https://styleapp-50e33-default-rtdb.firebaseio.com/")
            .reference
    }

    private fun initListeners() {
        btnLogIn.setOnClickListener {
            val email = tvEmail.text
            val password = tvPassword.text
            if ( email.isNotEmpty() && password.isNotEmpty() ) {
                auth.signInWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener {task ->
                        if ( task.isSuccessful ) {
                            val currentUser = auth.currentUser
                            val userId = currentUser?.uid
                            if (userId != null ) {
                                val userRef = fireDatabase.child("users").child(userId)
                                userRef.addValueEventListener (object : ValueEventListener {
                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        val userName = dataSnapshot.child("userName").getValue(String::class.java)
                                        val userEmail = dataSnapshot.child("userEmail").getValue(String::class.java)
                                        val userPhotoUri = dataSnapshot.child("userPhotoUri").getValue(String::class.java)

                                        tvEmail.text = ""
                                        tvPassword.text = ""

                                        showHomeActivity(
                                            userId = userId,
                                            userName = userName?:"",
                                            userEmail = userEmail?:"",
                                            userPhoto = userPhotoUri ?:""
                                        )
                                    }

                                    override fun onCancelled(databaseError: DatabaseError) {
                                        Toast.makeText(requireContext(), "Error al obtener los datos del usuario", Toast.LENGTH_SHORT).show()
                                    }
                                })
                            }
                        }
                    }
            }
        }

        btnGoogleLogIn.setOnClickListener {
            val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.webclient_id))
                .requestEmail()
                .build()

            val signInClient = GoogleSignIn.getClient(requireActivity(), options)
            startActivityForResult(signInClient.signInIntent, REQUEST_CODE_SIGN_IN)
        }

        btnRegister.setOnClickListener {
            callback.onLaunchFragmentFromFragment(CHANGE_TO_SIGN_UP, 1)
        }
    }

    private fun googleAuthForFirebase(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)

        auth.signInWithCredential(credentials).addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                showHomeActivity(
                    userId = user?.uid ?: "",
                    userName = user?.displayName ?: "",
                    userPhoto = user?.photoUrl.toString(),
                    userEmail = user?.email.toString()
                )
            }
            else {
                showAlert()
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error atenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( requestCode == REQUEST_CODE_SIGN_IN ) {
            if ( data != null ) {
                try {
                    val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
                    account?.let {
                        googleAuthForFirebase(it)
                    }
                }
                catch (e: Exception) {
                    showAlert()
                }
            }
        }
    }

    private fun showHomeActivity(userId: String, userName: String, userPhoto: String, userEmail: String) {
        val intent = Intent(activity, HomeActivity::class.java).apply {
            putExtra("userId", userId)
            putExtra("userName",userName)
            putExtra("userPhotoUri", userPhoto)
            putExtra("userEmail",userEmail)
        }
        startActivity(intent)
    }

    companion object {

        const val CHANGE_TO_SIGN_UP = "sign_up_model"
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle()
            }
    }
}
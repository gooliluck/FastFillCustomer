package com.gooliluck.fastfillcustomer.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gooliluck.fastfillcustomer.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var mainViewModel : MainViewModel
    private var listener = View.OnClickListener { v ->
        v?.let {
            Log.e("jptest","show id ${it.id}")

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mainViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MainViewModel::class.java)
        fab.setOnClickListener {
            mainViewModel.showFabMenu.value?.let { show ->
                if(!show) {
                    mainViewModel.setFabMenuShow(show = true)
                } else {
                    mainViewModel.setFabMenuShow(show = false)
                }
            }
        }


        add_new_user.setOnClickListener {
            mainViewModel.setFabMenuShow(show = false)
            mainViewModel.setFabShowing(false)
            Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.addNewUserFragment)
        }

        add_new_order.setOnClickListener {
            mainViewModel.setFabMenuShow(show = false)
            mainViewModel.setFabShowing(false)
            Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.addOrderFragment)
        }

        mainViewModel.navControl.observe(this, Observer {
            it?.let { jpNavControl ->
                Navigation.findNavController(this@MainActivity,R.id.nav_host_fragment).navigate(jpNavControl.id)
                mainViewModel.clearNavControl()
            }
        })

        mainViewModel.showFabMenu.observe(this, Observer {
            when(it){
                true -> {
                    add_new_user.animate().translationY(-resources.getDimension(R.dimen.standard_55)).alpha(1.0f).scaleX(1.0f).scaleY(1.0f)
                    add_new_order.animate().translationY(-resources.getDimension(R.dimen.standard_105)).alpha(1.0f).scaleX(1.0f).scaleY(1.0f)

                }
                false -> {
                    add_new_user.animate().translationY(0.0f).alpha(0.0f).scaleX(0.0f).scaleY(0.0f)
                    add_new_order.animate().translationY(0.0f).alpha(0.0f).scaleX(0.0f).scaleY(0.0f)
                }
            }
        })

        mainViewModel.showFab.observe(this, Observer {
            when(it){
                true -> {
                    showFabFloat()
                }
                false -> {
                    closeFabFloat()
                }
            }
        })
        initFirebaseLogin()
        mainViewModel.userStatus.observe(this) { userStatus ->
            when(userStatus) {
                UserStatus.NONE -> {}
                UserStatus.LOGIN -> TODO()
                UserStatus.SUCCESS -> TODO()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentFirebaseUser = auth.currentUser

    }

    private fun initFirebaseLogin(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]
    }
    private fun showFabFloat(){
        fab.animate().alpha(1.0f).scaleY(1.0f).scaleX(1.0f)
    }
    private fun closeFabFloat(){
        fab.animate().alpha(0.0f).scaleY(0.0f).scaleX(0.0f)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }


    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String) {
        // [START_EXCLUDE silent]
        pbar_loading.visibility = View.VISIBLE
        // [END_EXCLUDE]
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    // [START_EXCLUDE]
//                    val view = binding.mainLayout
                    // [END_EXCLUDE]
                    Snackbar.make(cl_main_activity_layout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // [START_EXCLUDE]
                pbar_loading.visibility = View.GONE
                // [END_EXCLUDE]
            }
    }
    // [END auth_with_google]

    private fun signOut() {
        // Firebase sign out
        auth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this) {
            updateUI(null)
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun revokeAccess() {
        // Firebase sign out
        auth.signOut()

        // Google revoke access
        googleSignInClient.revokeAccess().addOnCompleteListener(this) {
            updateUI(null)
        }
    }


    private fun updateUI(user: FirebaseUser?) {
        pbar_loading.visibility = View.GONE
        when(user){
            null -> {
                mainViewModel.setUserStatus(UserStatus.NONE)
                Toast.makeText(this, "User is logged out or null", Toast.LENGTH_SHORT).show()
            }
            else -> {
                mainViewModel.setUserStatus(UserStatus.SUCCESS)
                Log.e("jptest","user email is ${user.email} \nuserid is ${user.uid}")
            }
        }
//        if (user != null) {
//            binding.status.text = getString(R.string.google_status_fmt, user.email)
//            binding.detail.text = getString(R.string.firebase_status_fmt, user.uid)
//
//            binding.signInButton.visibility = View.GONE
//            binding.signOutAndDisconnect.visibility = View.VISIBLE
//        } else {
//            binding.status.setText(R.string.signed_out)
//            binding.detail.text = null
//
//            binding.signInButton.visibility = View.VISIBLE
//            binding.signOutAndDisconnect.visibility = View.GONE
//        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val RC_SIGN_IN = 9001
    }
}

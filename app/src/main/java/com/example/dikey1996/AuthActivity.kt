package com.example.dikey1996


import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        //analytics event


        // setup
        setup()


    }

    private fun setup() {
        title = "Autenticacióm"

        //Recoge el evento cuando el usuario pulsa registrar
        singUpButton.setOnClickListener {
            // Aqui se comprueba que los datos introducidos son correctos
            // Si se cumplen estas dos condiciones podremos registrar al usuario
            if (EmailEditText.text.isNotEmpty() && PasswordEditText.text.isNotEmpty()){

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(EmailEditText.text.toString(), PasswordEditText.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        showHome(it.result?.user?.email?: "", ProviderType.BASIC)

                    } else {
                        showAlert()

                    }
                }

            }
        }
       LoginButton.setOnClickListener {

           if (EmailEditText.text.isNotEmpty() && PasswordEditText.text.isNotEmpty()){

               FirebaseAuth.getInstance().signInWithEmailAndPassword(EmailEditText.text.toString(), PasswordEditText.text.toString()).addOnCompleteListener {
                   if (it.isSuccessful){
                       showHome(it.result?.user?.email?: "", ProviderType.BASIC)

                   } else {
                       showAlert()

                   }
               }

           }
       }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se produjo un error al querer autenticar al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun showHome(Email: String, provider: ProviderType){

        val homeIntent:Intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("Email", Email)
            putExtra("privider", provider.name)
        }
        startActivity(homeIntent)

    }
}
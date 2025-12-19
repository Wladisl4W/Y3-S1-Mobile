package com.example.contacts

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import AppState
import androidx.activity.viewModels
import Contact
import android.util.Log
import android.widget.Toast
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText

class AddContact : AppCompatActivity() {
    var contactInfo: Contact = Contact();

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v("app", "SIZE onCreate second: " + appState.contactList.size.toString())
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_contact)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup toolbar with back button
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val mode = intent.getStringExtra("mode")

        if (mode == "EditContact") {
            setInfoOfEditableContact(appState.contactList[appState.activeContactId])
        }

        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)
        buttonSubmit.setOnClickListener {
            val newContact: Contact = Contact(
                findViewById<TextInputEditText>(R.id.editTextFirstName).text.toString(),
                findViewById<TextInputEditText>(R.id.editTextSecondName).text.toString(),
                findViewById<TextInputEditText>(R.id.editTextThirdName).text.toString(),
                findViewById<TextInputEditText>(R.id.editTextPhoneNumber).text.toString()
            )
            if (newContact.firstName == "" || newContact.secondName == "" || newContact.thirdName == "" || newContact.phoneNumber == "") {
                Toast.makeText(this, "Поля не могут быть пустыми", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            when (mode) {
                "AddContact" -> addContact(newContact)
                "EditContact" -> editContact(newContact)
                else -> Log.v("how", "how did you end up here")
            }

            val intent = Intent(this, MainActivity::class.java);
            Log.v("app", "SIZE after submit contact: " + appState.contactList.size.toString())
            startActivity(intent)
        }
    }

    fun addContact(newContact: Contact) {
        Log.v("app", "newContactName:" + newContact.firstName)
        appState.contactList.add(newContact)
        appState.activeContactId = appState.contactList.size - 1
    }

    fun editContact(newContact: Contact) {
        appState.contactList[appState.activeContactId] = newContact
    }

    fun setInfoOfEditableContact(editableContact: Contact) {
        findViewById<TextInputEditText>(R.id.editTextFirstName).setText(editableContact.firstName)
        findViewById<TextInputEditText>(R.id.editTextSecondName).setText(editableContact.secondName)
        findViewById<TextInputEditText>(R.id.editTextThirdName).setText(editableContact.thirdName)
        findViewById<TextInputEditText>(R.id.editTextPhoneNumber).setText(editableContact.phoneNumber)
    }
}
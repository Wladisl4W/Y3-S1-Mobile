package com.example.contacts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import AppState
import Contact
import android.widget.ImageButton
import androidx.activity.viewModels
import com.google.android.material.appbar.MaterialToolbar
import kotlin.getValue


enum class Mode {
    ADD_CONTACT,
    EDIT_CONTACT;

    companion object {
        fun fromString(value: String?): Mode? {
            return value?.let { Mode.valueOf(it)}
        }
    }
}

public var appState: AppState = AppState()

class MainActivity : AppCompatActivity() {
    private var textView: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Инициализация всяких null и прочего
        textView = findViewById<TextView>(R.id.textView)
        updateActiveContact()


        // Кнопки смены активности
        val buttonAddContact = findViewById<Button>(R.id.buttonAddContact)
        buttonAddContact.setOnClickListener {
            val intent = Intent(this, AddContact::class.java)
            intent.putExtra("mode", "AddContact")
            startActivity(intent)
        }

        val buttonEditContact = findViewById<Button>(R.id.buttonEditContact)
        buttonEditContact.setOnClickListener {
            if (appState.contactList.isEmpty()) {
                return@setOnClickListener
            }
            val intent = Intent(this, AddContact::class.java)
            intent.putExtra("mode", "EditContact")
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        if (appState.firstTimeLaunch) {
            appState.firstTimeLaunch = false
            return
        }

        updateActiveContact()
    }

    fun getShortName(contact: Contact): String {
        return "${appState.activeContactId + 1}. ${contact.secondName} ${contact.firstName[0]}. ${contact.thirdName[0]}."
    }

    fun handleButtonPreviousClick(clickedButton: View) {
        if (appState.contactList.isEmpty()) {
            return
        }
        appState.activeContactId = (appState.activeContactId - 1 + appState.contactList.size) % appState.contactList.size
        updateActiveContact()
    }

    fun handleButtonNextClick(clickedButton: View) {
        if (appState.contactList.isEmpty()) {
            return
        }
        appState.activeContactId = (appState.activeContactId + 1) % appState.contactList.size
        updateActiveContact()
    }

    fun handleButtonDeleteContactClick(clickedButton: View) {
        if (appState.contactList.isEmpty()) {
            return
        }

        appState.contactList.removeAt(appState.activeContactId)
        appState.activeContactId = if (appState.contactList.isNotEmpty()) {
            (appState.activeContactId - 1 + appState.contactList.size) % appState.contactList.size
        } else {
            0
        }
        updateActiveContact()
    }

    fun updateActiveContact() {
        Log.v("app", "SIZE on update: " + appState.contactList.size.toString())
        textView!!.text = if (appState.contactList.isEmpty()) "У вас нет контактов" else getShortName(
            appState.contactList[appState.activeContactId])
    }
}
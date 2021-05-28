package com.example.mv_ted.expirements

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.mv_ted.R
import com.example.mv_ted.databinding.FragmentContactProviderBinding


class ContactProviderFragment : Fragment() {
    private lateinit var binding: FragmentContactProviderBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentContactProviderBinding.bind(inflater.inflate(R.layout.fragment_contact_provider, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    private fun checkPermission() {
        context?.let {
              when(PackageManager.PERMISSION_GRANTED){
                  ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) ->{
                         getContacts()
                  }
                  else -> {
                         requestPermission()
                  }
              }
        }
    }

    private fun requestPermission() {
          requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode ){
              REQUEST_CODE -> {
                  if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                      getContacts()
              }
              } else -> {
                  context?.let {
                      AlertDialog.Builder(it)
                          .setTitle(getString(R.string.title_alert_explonation_contacts_rermission))
                          .setMessage(getString(R.string.content_alert_explonation_contact_permission))
                          .setNegativeButton(getString(R.string.negative_button_alert_contact_permission)){ dialog, _ -> dialog.dismiss()}
                          .create()
                          .show()
                  }
              }
        }
    }

    @SuppressLint("Recycle")
    private fun getContacts() {
        context?.let {
             val cursorWithContacts: Cursor? = it.contentResolver.query(
                 ContactsContract.Contacts.CONTENT_URI,
                 null,
                 null,
                 null,
                  ContactsContract.Contacts.DISPLAY_NAME + " ASC"
             )

            cursorWithContacts?.let { cursor ->
                for (i in 0..cursor.count){
                     if (cursor.moveToPosition(i)){
                        val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        addView(it, name)
                     }
                }
            }
        }

    }

    private fun addView(context: Context, name: String)= with(binding) {
       contactsContainer.addView(AppCompatTextView(context).apply {
           text = name
           textSize = 16f
       })
    }

    companion object {
        private const val  REQUEST_CODE = 100
        fun newInstance() = ContactProviderFragment()
    }
}
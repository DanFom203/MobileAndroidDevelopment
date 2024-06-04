package com.itis.android_homework.presentation.screens.share_with_contacts

import android.content.Context
import android.provider.ContactsContract
import com.itis.android_homework.presentation.base.BaseViewModel
import com.itis.android_homework.presentation.model.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ContactViewModel @Inject constructor(

) : BaseViewModel() {

    private val _contactFlow = MutableStateFlow<List<Contact>?>(null)
    val contactFlow : StateFlow<List<Contact>?>
        get() = _contactFlow

    fun fetchContactList(context: Context) {
        val contacts = mutableListOf<Contact>()
        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            ),
            null,
            null,
            "${ContactsContract.Contacts.DISPLAY_NAME} ASC"
        )

        cursor?.let {
            val contactNameSet = hashSetOf<String>()
            cursor.use { cursor ->
                val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                val phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                var name: String
                var phone: String
                while (cursor.moveToNext()) {
                    name = cursor.getString(nameIndex)
                    phone = cursor.getString(phoneIndex).replace(" ", "")
                    if (!contactNameSet.contains(name)) {
                        contacts.add(Contact(name, phone))
                        contactNameSet.add(name)
                    }
                }
            }
        }

        _contactFlow.value = contacts
    }
}
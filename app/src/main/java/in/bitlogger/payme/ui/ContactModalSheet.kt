package `in`.bitlogger.payme.ui

import `in`.bitlogger.payme.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.provider.ContactsContract
import android.widget.ListView
import android.widget.SimpleCursorAdapter

/*
 * Defines an array that contains column names to move from
 * the Cursor to the ListView.
 */
@SuppressLint("InlinedApi")
private val FROM_COLUMNS: Array<String> = arrayOf(
    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
    ContactsContract.CommonDataKinds.Phone.NUMBER,
    ContactsContract.CommonDataKinds.Phone._ID
)

/*
 * Defines an array that contains resource ids for the layout views
 * that get the Cursor column contents. The id is pre-defined in
 * the Android framework, so it is prefaced with "android.R.id"
 */
private val TO_IDS: IntArray = intArrayOf(android.R.id.text1)

class ContactModalSheet: BottomSheetDialogFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.sheet_contact, container, false)
        readContacts(view)
        return view
    }

    private fun readContacts(view: View) {
        val result = requireActivity().contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            FROM_COLUMNS,
            null,
            null,
            null
        )

        val adapter = SimpleCursorAdapter(
            requireContext(), R.layout.card_contact, result, FROM_COLUMNS, TO_IDS, 0)

        val list = view.findViewById<ListView>(R.id.list_view)
        list.adapter = adapter
    }
}
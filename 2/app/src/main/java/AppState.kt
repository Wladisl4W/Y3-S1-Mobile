import android.app.Application
import android.media.tv.interactive.AppLinkInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

data class Contact(
    val firstName: String = "",
    val secondName: String = "",
    val thirdName: String = "",
    val phoneNumber: String = ""
)

class AppState() : ViewModel() {
    var firstTimeLaunch = true
    var activeContactId: Int = 0
    var contactList: ArrayList<Contact> = arrayListOf<Contact>(
        Contact("Иван", "Иванов", "Иванович", "89123456789"),
        Contact("Петр", "Петров", "Петрович", "777"),
        Contact("Василий", "Васильев", "Васильевич", "666999666999")
    )
}
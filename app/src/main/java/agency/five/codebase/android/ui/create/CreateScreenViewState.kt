package agency.five.codebase.android.ui.create

import agency.five.codebase.android.model.Category
import java.time.LocalDate
import java.time.LocalTime

data class CreateScreenViewState(
    val date: LocalDate? = null,
    val dateError: String? = null,
    val time: LocalTime? = null,
    val timeError: String? = null,
    val category: Category? = null,
    val categoryError: String? = null,
    val todoTitle: String = "",
    val todoTitleError: String? = null,
    val todoNote: String = "",
    val todoNoteError: String? = null,
    val isSuccess: Boolean = false,
    val createFinished: Boolean = false
)

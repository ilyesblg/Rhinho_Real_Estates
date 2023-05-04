package devsec.app.rhinhorealestates.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import devsec.app.rhinhorealestates.data.repositories.FileRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.File

class FileViewModel(private val repository: FileRepository = FileRepository()):ViewModel() {

    suspend fun uploadFile(id: String, file: MultipartBody.Part) = repository.uploadFile(id, file)

    fun uploadFile(id: String, file: File) = viewModelScope.launch {
        repository.uploadFile(id, MultipartBody.Part.createFormData("file", file.name))
    }
}


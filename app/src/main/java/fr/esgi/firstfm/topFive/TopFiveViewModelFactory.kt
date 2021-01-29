package fr.esgi.firstfm.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.esgi.firstfm.data.UserDataSource
import fr.esgi.firstfm.data.UserRepository
import fr.esgi.firstfm.topFive.TopFiveViewModel

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class TopFiveViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopFiveViewModel::class.java)) {
            return TopFiveViewModel(
                userRepository = UserRepository(
                    dataSource = UserDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}